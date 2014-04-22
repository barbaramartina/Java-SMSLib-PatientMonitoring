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
package sms.receivers;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.smslib.AGateway;
import org.smslib.ICallNotification;
import org.smslib.IGatewayStatusNotification;
import org.smslib.IInboundMessageNotification;
import org.smslib.IOrphanedMessageNotification;
import org.smslib.InboundMessage;
import org.smslib.Service;
import org.smslib.AGateway.GatewayStatuses;
import org.smslib.InboundMessage.MessageClasses;
import org.smslib.Message.MessageTypes;

import database.objects.measure.MeasureDB;
import database.objects.measuretype.MeasureTypeDB;
import database.objects.patient.PatientDB;

import sms.SMS;
import sms.processors.measures.MeasureProcessor;
import sms.senders.SMSLibSenderException;
import sms.services.smslib.SMSLibInterface;
import sms.validators.MeasureValidator;

/**
 * Receive entry text messages using SMSLibs libraries {@see http://smslib.org/}
 * 
 * @version 1.0
 * @date 2010-04-06
 * @author Barbara M. Rodeker
 */
public class SMSLibReceiver extends Receiver {

    /**
     * SMS service
     */
    private static Service srv = null;
    /**
     * Processor of measures
     */
    private static MeasureProcessor processor = null;
    /**
     *  Logger
     */
    private static Logger logger = Logger.getLogger(SMSLibReceiver.class);

    /**
     * Constructor
     */
    public SMSLibReceiver(MeasureValidator validator, PatientDB patientsDB, MeasureTypeDB typesDb, MeasureDB measuresDB) {
	super();

	processor = new MeasureProcessor(validator, patientsDB, typesDb, measuresDB);

	try {
	    srv = SMSLibInterface.getInstance().getSrv();
	} catch (SMSLibSenderException e) {
	    logger.fatal(e.toString());
	}

	// Create the notification callback method for inbound & status report
	// messages.
	InboundNotification inboundNotification = new InboundNotification();

	// Create the notification callback method for inbound voice calls.
	CallNotification callNotification = new CallNotification();

	// Create the notification callback method for gateway statuses.
	GatewayStatusNotification statusNotification = new GatewayStatusNotification();

	OrphanedMessageNotification orphanedMessageNotification = new OrphanedMessageNotification();

	// Set up the notification methods.
	srv.setInboundMessageNotification(inboundNotification);
	srv.setCallNotification(callNotification);
	srv.setGatewayStatusNotification(statusNotification);
	srv.setOrphanedMessageNotification(orphanedMessageNotification);


    }

    /**
     * Constructor
     */
    public SMSLibReceiver() {
	super();

	processor = new MeasureProcessor();

	try {
	    srv = SMSLibInterface.getInstance().getSrv();
	} catch (SMSLibSenderException e) {
	    logger.fatal(e.toString());
	}

	// Create the notification callback method for inbound & status report
	// messages.
	InboundNotification inboundNotification = new InboundNotification();

	// Create the notification callback method for inbound voice calls.
	CallNotification callNotification = new CallNotification();

	// Create the notification callback method for gateway statuses.
	GatewayStatusNotification statusNotification = new GatewayStatusNotification();

	OrphanedMessageNotification orphanedMessageNotification = new OrphanedMessageNotification();

	// Set up the notification methods.
	srv.setInboundMessageNotification(inboundNotification);
	srv.setCallNotification(callNotification);
	srv.setGatewayStatusNotification(statusNotification);
	srv.setOrphanedMessageNotification(orphanedMessageNotification);

	// start to listen entry messages
	this.run();
    }

    @Override
    public void receiveSMS() {
	List<InboundMessage> msgList;
	msgList = new ArrayList<InboundMessage>();

	try {
	    srv.readMessages(msgList, MessageClasses.ALL);
	} catch (Exception e) {
	    logger.error("Error leyendo mensajes.");
	    logger.error(e.toString());
	}

	// process all messages
	for (InboundMessage msg : msgList) {
	    SMS sms = new SMS();
	    sms.setCelularFrom(msg.getOriginator());
	    sms.setText(msg.getText());

	    processor.processMeasure(sms);

	    try {
		srv.deleteMessage(msg);
	    } catch (Exception e) {
		logger
			.error("Error eliminando mensaje de la bandeja de entrada.");
		logger.error(e.toString());
	    }

	}

    }

    /**
     * Call back class for processing incoming message
     * 
     * @version 1.0
     * @date    2010-04-06
     * @author  Barbara M. Rodeker
     */
    public static class InboundNotification implements
	    IInboundMessageNotification {
	
	/**
	 * Process an incoming message
	 * 
	 * @param AGateway
	 * @param MessageTypes 
	 * @param InboundMessage
	 */
	public void process(AGateway gateway, MessageTypes msgType,
		InboundMessage msg) {
	    SMS sms = new SMS();
	    sms.setCelularFrom(msg.getOriginator());
	    sms.setText(msg.getText());

	    processor.processMeasure(sms);

	    try {
		srv.deleteMessage(msg);
	    } catch (Exception e) {
		logger
			.error("Error eliminando mensaje de la bandeja de entrada.");
		logger.error(e.toString());
	    }
    }
    }

    
    /**
     * Call back class for processing incoming calls
     * 
     * @version 1.0
     * @date    2010-04-06
     * @author  Barbara M. Rodeker
     */
    public static class CallNotification implements ICallNotification {
	/**
	 * We just log an incoming call detected
	 */
	public void process(AGateway gateway, String callerId) {
	   logger.info(">>> New call detected from Gateway: "
		    + gateway.getGatewayId() + " : " + callerId);
	}
    }

    /**
     * Call back class for processing incoming status notification messages
     * 
     * @version 1.0
     * @date    2010-04-06
     * @author  Barbara M. Rodeker
     */
    public static class GatewayStatusNotification implements
	    IGatewayStatusNotification {
	/**
	 * We just log the message
	 * 
	 */
	public void process(AGateway gateway, GatewayStatuses oldStatus,
		GatewayStatuses newStatus) {
	    logger.info(">>> Gateway Status change for "
		    + gateway.getGatewayId() + ", OLD: " + oldStatus
		    + " -> NEW: " + newStatus);
	}
    }

    /**
     * Call back class for processing orphaned messages
     * 
     * @version 1.0
     * @date    2010-04-06
     * @author  Barbara M. Rodeker
     */
    public static class OrphanedMessageNotification implements
	    IOrphanedMessageNotification {
	public boolean process(AGateway gateway, InboundMessage msg) {
	    logger.info(">>> Orphaned message part detected from "
		    + gateway.getGatewayId());
	    logger.info(msg);
	    // Since we are just interested in inbounds text message, return FALSE and keep the orphaned
	    // message part.
	    return false;
	}
    }

    @Override
    public void stopReceiving() {
	try {
	    this.srv.stopService();
	    SMSLibInterface.getInstance().stopService();

	} catch (Exception e) {
	    logger.error(e.toString());
	}
    }

}
