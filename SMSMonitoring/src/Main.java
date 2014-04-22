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
import org.apache.log4j.Logger;

import sms.monitoring.news.NewsMonitor;
import sms.monitoring.responses.ResponsesMonitor;
import sms.processors.news.NewsProcessor;
import sms.processors.responses.ResponsesProcessor;
import sms.receivers.SMSLibReceiver;
import sms.senders.SMSLibSender;
import sms.senders.SMSLibSenderException;
import sms.senders.Sender;
import sms.validators.MeasureValidator;
import database.connections.ConnectionPool;
import database.objects.campaign.CampaignDB;
import database.objects.measure.MeasureDB;
import database.objects.measuretype.MeasureTypeDB;
import database.objects.patient.PatientDB;
import database.objects.response.ResponseDB;

/**
 * Main to start application
 * 
 * @version 1.0
 * @date 2010-04-06
 * @author Barbara M. Rodeker
 */
public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {
	Logger.getRootLogger().info("Inicializando thread MAIN............");

	ConnectionPool pool = new ConnectionPool();
	Sender sender = null;
	try {
	    sender = new SMSLibSender();
	} catch (SMSLibSenderException e) {
	    Logger.getRootLogger().fatal("ERROR INICIALIZANDO SENDER.....");
	    Logger.getRootLogger().fatal(e.toString());
	}
	
	ResponsesMonitor responseMonitor = null;
	NewsMonitor newsMonitor = null;
	SMSLibReceiver receiver = null;

	try {

	    // We create the response monitor object
	    Logger.getRootLogger().info(
		    "Creando clases de monitoreo de respuestas............");
	    ResponseDB responsesDB = new ResponseDB(pool);
	    ResponsesProcessor responsesProcessor = new ResponsesProcessor(
		    responsesDB, sender);

	    responseMonitor = new ResponsesMonitor(
		    responsesDB, responsesProcessor, pool);

	    // We create the news monitor object
	    Logger.getRootLogger().info(
		    "Creando clases de monitoreo de novedades............");
	    CampaignDB campaignDB = new CampaignDB(pool);
	    NewsProcessor newsProcessor = new NewsProcessor(campaignDB, sender);

	    newsMonitor = new NewsMonitor(campaignDB,
		    newsProcessor, pool);

	    // We create the measures receiving object
	    Logger
		    .getRootLogger()
		    .info(
			    "Creando clases de procesamiento de mediciones............");
	    PatientDB patientsDB = new PatientDB(pool);
	    MeasureTypeDB typesDb = new MeasureTypeDB(pool);
	    MeasureDB measuresDB = new MeasureDB(pool);
	    MeasureValidator validator = new MeasureValidator(typesDb,
		    patientsDB);

	    receiver = new SMSLibReceiver(validator, patientsDB,
		    typesDb, measuresDB);
	    

	    // We start the objects
	    newsMonitor.start();
	    responseMonitor.start();
	    receiver.start();

	} catch (Exception e) {
	    Logger.getRootLogger().fatal("Error en thread MAIN....");
	    Logger.getRootLogger().fatal(e.toString());
	    
	    responseMonitor.stopMonitoring();
	    newsMonitor.stopMonitoring();
	    receiver.stopReceiving();

	}

    }

}
