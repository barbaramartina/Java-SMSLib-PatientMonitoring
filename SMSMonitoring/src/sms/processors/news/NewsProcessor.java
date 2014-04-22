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
package sms.processors.news;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

import org.apache.log4j.Logger;

import sms.SMS;
import sms.senders.SMSLibSender;
import sms.senders.SMSLibSenderException;
import sms.senders.Sender;
import database.objects.campaign.CampaignDB;
import database.objects.measure.MeasureDB;
import entities.news.New;
import entities.user.User;
import etm.core.configuration.EtmManager;
import etm.core.monitor.EtmMonitor;
import etm.core.monitor.EtmPoint;

/**
 * Process news published recently by listening the news table in database
 * 
 * @version 1.0
 * @date 2010-04-06
 * @author Barbara M. Rodeker
 */
public class NewsProcessor {

    /**
     * Database campaign object
     */
    private CampaignDB campaignDB = null;
    /**
     * text message sender
     */
    private Sender sender = null;
    /**
     * Testing times variable
     */
    private static final EtmMonitor etmMonitor = EtmManager.getEtmMonitor();

    /**
     * Logger
     */
    private static Logger logger = Logger.getLogger(NewsProcessor.class);
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
    public NewsProcessor() throws SMSLibSenderException {
	loadProperties();
	campaignDB = new CampaignDB();
	sender = new SMSLibSender();
    }

    /**
     * 
     * Constructor
     * 
     * @param cdb
     *            Campaign database access object
     * @param s
     *            Sender sms
     */
    public NewsProcessor(CampaignDB cdb, Sender s) {
	loadProperties();
	this.campaignDB = cdb;
	this.sender = s;
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
			    .getResourceAsStream("/sms/processors/news/resources.properties"));
	} catch (IOException e) {
	    logger.error("Error cargando propiedades y recursos");
	    logger.error(e.toString());
	}

    }

    /**
     * Process a backup new registered by a doctor or an administrator. Check if
     * there is any user subscribed to the campaign and send them the news.
     * 
     */
    public void processNew() {
	// testing times code
	EtmPoint point = etmMonitor
		.createPoint("NewsProcessor::processNew");

	try {

	    logger.info("Por procesar nueva novedad...");

	    // we select the last new from backup table
	    New n = campaignDB.selectbackupNew();

	    if (n != null) {
		Vector<User> subscribers = campaignDB.getSuscribers(n.getCampaign());

		// if there are people interested in campaign
		if (subscribers != null) {

		    logger
			    .info("Enviando nueva novedad a los suscriptores de la campaña...Novedad: "
				    + n.getText()
				    + "Corresponde a la campaña"
				    + n.getCampaign().getDescription());

		    SMS sms = new SMS();
		    sms.setText(n.getText());
		    sms.setCelularFrom("000000");

		    // an sms is sent to each of them with the new´s text
		    for (Enumeration<User> e = subscribers.elements(); e
			    .hasMoreElements();) {
			User u = e.nextElement();
			sms.setCelularTo(u.getCelularPhone());
			sender.sendSMS(sms);
		    }

		} else {
		    logger
			    .error(props
				    .getProperty("sms.processsors.news.NotSubcribersError"));
		}

		// delete the new from backup table for not processing it twice
		campaignDB.deletebackupNew(n);
	    } else {
		logger.error(props
			.getProperty("sms.processsors.news.NotBackupNewError"));
	    }

	} finally {
	    point.collect();
	}

    }

    /**
     * 
     * @return CampaingDB database access object
     */
    public CampaignDB getCampaignDB() {
	return campaignDB;
    }

    /**
     * 
     * @param campaignDB
     *            Database access object
     */
    public void setCampaignDB(CampaignDB campaignDB) {
	this.campaignDB = campaignDB;
    }

    /**
     * 
     * @param sender
     *            Sender sms
     */
    public void setSender(Sender sender) {
	this.sender = sender;
    }

    public void stopProcessing() {
	this.sender.stopSending();
    }

}
