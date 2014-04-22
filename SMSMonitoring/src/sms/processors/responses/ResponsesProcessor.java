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
package sms.processors.responses;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import sms.SMS;
import sms.senders.SMSLibSender;
import sms.senders.SMSLibSenderException;
import sms.senders.Sender;
import database.objects.measure.MeasureDB;
import database.objects.response.ResponseDB;
import entities.response.Response;
import entities.user.User;
import etm.core.configuration.EtmManager;
import etm.core.monitor.EtmMonitor;
import etm.core.monitor.EtmPoint;

/**
 * Process responses registered recently by a doctor. Listens to response table
 * in database.
 * 
 * @version 1.0
 * @date 2010-04-06
 * @author Barbara M. Rodeker
 */
public class ResponsesProcessor {

    /**
     * Database response object
     */
    private ResponseDB responsesDB = null;
    /**
     * Sender sms
     */
    private Sender sender = null;
    /**
     * Testing times variable
     */
    private static final EtmMonitor etmMonitor = EtmManager.getEtmMonitor();

    /**
     * Logger
     */
    private static Logger logger = Logger.getLogger(ResponsesProcessor.class);
    /**
     * Properties file
     */
    private Properties props = null;

    /**
     * 
     * Constructor
     * 
     * @throws SMSLibSenderException
     */
    public ResponsesProcessor() throws SMSLibSenderException {
	loadProperties();
	responsesDB = new ResponseDB();
	sender = new SMSLibSender();
    }

    /**
     * Constructor
     */
    public ResponsesProcessor(ResponseDB responsesDB, Sender sender) {
	loadProperties();
	this.responsesDB = responsesDB;
	this.sender = sender;
    }

    /**
     * Reads a response from backup table and send it to the correct patient.
     */
    public void processResponse() {

	// testing times code
	EtmPoint point = etmMonitor
		.createPoint("ResponseProcessor::processResponse");

	try {

	    logger.info("Por procesar nueva respuesta....");

	    // select a response from backup table to process
	    Response response = responsesDB.getBackupResponse();

	    if (response != null) {
		// we check patient information
		User p = response.getPatient();

		if (p != null) {
		    // we send an sms to the patient
		    SMS sms = new SMS("00000", p.getCelularPhone(), response
			    .getResponse());

		    logger.info("Por enviar SMS al paciente "
			    + p.getCelularPhone() + "Respuesta: "
			    + response.getResponse() + "Doctor: "
			    + response.getDoctor().getName() + " "
			    + response.getDoctor().getSurname());

		    sender.sendSMS(sms);

		} else {
		    logger
			    .error(props
				    .getProperty("sms.processors.responses.ErrorProcessingNotPatient")
				    + "Respuesta: "
				    + response.getId()
				    + " "
				    + response.getId());
		}

		// remove the response from backup table for not processing it
		// twice
		responsesDB.deleteBackupResponse(response);

	    } else {
		logger
			.error(props
				.getProperty("sms.processors.responses.ErrorProcessingNULL"));
	    }

	} finally {
	    point.collect();
	}

    }

    /**
     * Properties and messages are loaded
     * 
     */
    private void loadProperties() {

	// we load the properties and messages
	props = new Properties();
	try {
	    props
		    .load(MeasureDB.class
			    .getResourceAsStream("/sms/processors/responses/resources.properties"));
	} catch (IOException e) {
	    logger.error("Error cargando propiedades y recursos");
	    logger.error(e.toString());
	}

    }

    public void stopProcessing() {
	this.sender.stopSending();
    }

    /**
     * 
     * @param responsesDB
     */
    public void setResponsesDB(ResponseDB responsesDB) {
	this.responsesDB = responsesDB;
    }

    /**
     * 
     * @param sender
     */
    public void setSender(Sender sender) {
	this.sender = sender;
    }

}
