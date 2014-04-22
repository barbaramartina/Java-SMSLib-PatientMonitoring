/**
   @version 1.0
   @COPYRIGTH 2010	Barbara Martina Rodeker	barbararodeker@gmail.com
   @license	 See COPYING.txt file included into the programm files.

   This file is part of SMPW (System for Monitoring Patients using a Web interface)
    SMPW is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    SMPW is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
    You should have received a copy of the GNU General Public License
    along with SMPW.  If not, see <http://www.gnu.org/licenses/>.
 
 */
package sms.services.smslib;

import org.apache.log4j.Logger;
import org.smslib.AGateway;
import org.smslib.IOutboundMessageNotification;
import org.smslib.Library;
import org.smslib.OutboundMessage;
import org.smslib.Service;
import org.smslib.AGateway.Protocols;
import org.smslib.OutboundMessage.FailureCauses;
import org.smslib.OutboundMessage.MessageStatuses;
import org.smslib.modem.SerialModemGateway;

import sms.senders.SMSLibSenderException;
import config.ConfigManager;
import config.ConfigurationException;

/**
 * SMSLib libraries interface
 * 
 * @version 1.0
 * @date 2010-04-06
 * @author Barbara M. Rodeker
 */
public class SMSLibInterface {

    /**
     * Modem configuration values
     */
    private String modemId = "";
    private String modemPort = "";
    private String modemManufacturer = "";
    private int modemBaudRate = -1;
    private String modemModel = "";
    private String SMSC = "";
    private String simPin = "";
    
    private static int usersCount = 0;

    /**
     * Outbound callback processor
     * 
     */
    private OutboundNotification outboundNotification = null;
    /**
     * SMS service
     */
    private Service srv = null;

    /**
     * Serial Modem Gateway
     */
    private SerialModemGateway gateway = null;

    /**
     * Logger
     */
    private static Logger logger = Logger.getLogger(SMSLibInterface.class);
    /**
     * Instancia
     */
    private static SMSLibInterface instance = null;

    /**
     * Create instance
     * 
     * @throws SMSLibSenderException
     */
    private synchronized static void createInstance()
	    throws SMSLibSenderException {
	if (instance == null) {
	    instance = new SMSLibInterface();

	}

    }

    /**
     * Private constructor
     */
    private SMSLibInterface() throws SMSLibSenderException {

	// we initialize modem parameters
	try {
	    ConfigManager configurations = ConfigManager.getInstance();

	    modemBaudRate = configurations.getModemBaudRate();
	    modemId = configurations.getModemId();
	    modemPort = configurations.getModemPort();
	    modemModel = configurations.getModemModel();
	    modemManufacturer = configurations.getModemManufacturer();
	    SMSC = configurations.getSMSC();
	    simPin = configurations.getSimPin();

	} catch (ConfigurationException e) {
	    logger
		    .error("Error leyendo parámetros de configuración del modem.");
	}

	// create outbound notifications class
	outboundNotification = new OutboundNotification();

	// create service
	srv = new Service();

	logger.info("Creando instancia de SMSLibInterface.");
	logger.info(Library.getLibraryDescription());
	logger.info("Version: " + Library.getLibraryVersion());

	// create gateway object
	gateway = new SerialModemGateway(modemId, modemPort, modemBaudRate,
		modemManufacturer, modemModel);

	gateway.setInbound(true);
	gateway.setOutbound(true);
	gateway.setSimPin(simPin);
	gateway.setSmscNumber(SMSC);

	// Set the modem protocol to PDU (alternative is TEXT). PDU is the
	// default, anyway...
	gateway.setProtocol(Protocols.PDU);

	gateway.getATHandler().setStorageLocations("SMME");

	srv.setOutboundMessageNotification(outboundNotification);

	try {
	    srv.addGateway(gateway);
	    srv.startService();

	    logger.info("Modem Information:");
	    logger.info("  Manufacturer: " + gateway.getManufacturer());
	    logger.info("  Model: " + gateway.getModel());
	    logger.info("  Serial No: " + gateway.getSerialNo());
	    logger.info("  SIM IMSI: " + gateway.getImsi());
	    logger.info("  Signal Level: " + gateway.getSignalLevel() + "%");
	    logger.info("  Battery Level: " + gateway.getBatteryLevel() + "%");

	} catch (Exception e) {
	    logger.error(e.toString());
	    throw new SMSLibSenderException(e);
	}
    }

    /**
     * 
     * Return class instance
     * 
     * @return SMSLibInterface
     * @throws SMSLibSenderException
     */
    public static SMSLibInterface getInstance() throws SMSLibSenderException {
	if (instance == null) {
	    createInstance();
	}
	usersCount++; 
	return instance;

    }

    /**
     * 
     * @return SMS Service
     */
    public Service getSrv() {
	return srv;
    }

    /**
     * 
     * @return Gateway
     */
    public SerialModemGateway getGateway() {
	return gateway;
    }

    /**
     * Class for processing call back from Service when a text message is sent.
     * 
     * @version 1.0
     * @date 2010-04-06
     * @author Barbara M. Rodeker
     */
    public static class OutboundNotification implements
	    IOutboundMessageNotification {

	/**
	 * Logger
	 */
	private static Logger logger = Logger.getLogger(OutboundNotification.class);

	/**
	 * Process out bound message after sending them. If there is a failure
	 * in sending the message it is sent again.
	 */
	public void process(AGateway gateway, OutboundMessage msg) {
	    // the status is checked
	    MessageStatuses status = msg.getMessageStatus();
	    if (status.equals(MessageStatuses.FAILED)) {
		// we log the failure cause
		FailureCauses cause = msg.getFailureCause();

		// we check the failure cause
		// if the cause is a fatal one we can not send the message again
		if (cause.equals(FailureCauses.BAD_FORMAT)
			|| cause.equals(FailureCauses.BAD_NUMBER)
			|| cause.equals(FailureCauses.GATEWAY_AUTH)
			|| cause.equals(FailureCauses.NO_CREDIT)) {
		    logger
			    .fatal("Error enviando el mensaje. Ha fallado el envío - Datos del mensaje procesado: "
				    + msg.getFrom()
				    + " "
				    + msg.getPduUserData()
				    + " "
				    + msg.getRefNo()
				    + "RetryCount"
				    + msg.getRetryCount());
		    logger.fatal(cause.toString());
		} else {
		    // the cause is not a fatal one, so we try to enqueue the
		    // message again
		    logger
			    .error("Error enviando el mensaje. Ha fallado el envío - Datos del mensaje procesado: "
				    + msg.getFrom()
				    + " "
				    + msg.getPduUserData()
				    + " "
				    + msg.getRefNo()
				    + "RetryCount"
				    + msg.getRetryCount());
		    logger.warn("Se tratará de encolar el mensaje nuevamente.");

		    gateway.getService().queueMessage(msg);

		}

	    }
	}
    }

    /**
     * Stop sending message service
     * 
     */
    public void stopService() {
	try {
	    usersCount--;
	    
	    if (usersCount == 0){
		this.srv.stopService();
	    }
		
	    

	} catch (Exception e) {
	    Logger.getRootLogger().error(e.toString());
	}

    }

}
