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
package sms.monitoring.news;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.log4j.Logger;

import sms.processors.news.NewsProcessor;
import sms.senders.SMSLibSenderException;
import database.connections.ConnectionPool;
import database.objects.campaign.CampaignDB;
import database.objects.measure.MeasureDB;

/**
 * Monitors database news table to detect changes arriving in order to
 * distribute the news.
 * 
 * @version 1.0
 * @date 2010-04-06
 * @author Barbara M. Rodeker
 */
public class NewsMonitor extends Thread {

    /**
     * Campaign database object
     */
    private CampaignDB campaignDB = null;
    /**
     * News processor
     */
    private NewsProcessor processor = null;
  
    /**
     * Pool
     */
    private ConnectionPool pool = null;
    /**
     * Postgre connection
     */
    private org.postgresql.PGConnection pgconn;
    /**
     * Logger
     */
    private static Logger logger = Logger.getLogger(NewsMonitor.class);
    /**
     * Properties and messages file
     */
    private Properties props = null;

    /**
     * 
     * Constructor
     * 
     * @throws SMSLibSenderException
     */
    public NewsMonitor() throws SMSLibSenderException {
	loadProperties();
	campaignDB = new CampaignDB();
	processor = new NewsProcessor();
	pool = new ConnectionPool();

    }

    /**
     * 
     * Constructor
     * 
     * @param cdb
     *            CampaignDB database object
     * @param processor
     *            NewsProcessor
     */
    public NewsMonitor(CampaignDB cdb, NewsProcessor processor,
	    ConnectionPool pool) {
	loadProperties();
	this.campaignDB = cdb;
	this.processor = processor;
	this.pool = pool;

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
     * This Thread run forever listening for incoming news in database in order
     * to process them
     */
    public void run() {
	logger.info("Starting run method....");
	try {
	    startListening();
	} catch (SQLException e1) {
	    logger.fatal(props
		    .getProperty("sms.monitoring.news.ErrorFatalListeningDB"));
	    logger.fatal(e1.toString());
	    return;
	}

	while (true) {
	    try {
		listenForNews();
		sleep(3000);
	    } catch (SQLException e1) {
		logger.error(props
			.getProperty("sms.monitoring.news.ErrorProcessing"));
		logger.error(e1.toString());
	    } catch (InterruptedException e) {
		logger.error(props
			.getProperty("sms.monitoring.news.ErrorSleeping"));
		logger.error(e.toString());
	    }
	}

    }

    /**
     * Execute the necessary query for start listening to database table
     * 
     */
    private void startListening() throws SQLException { 	    
	Connection conn = pool.getConnection().getConnection();
	this.pgconn = (org.postgresql.PGConnection) conn;
	Statement stmt = conn.createStatement();
	stmt.execute("LISTEN campania_novedad"); 
	stmt.close();

    }

    /**
     * Listens for changes in database. Process news related to campaigns.
     * 
     * @throws SQLException
     * 
     */
    private void listenForNews() throws SQLException {

	Statement stmt = pool.getConnection().getConnection().createStatement();
	ResultSet rs = stmt.executeQuery("SELECT 1");
	rs.close();
	stmt.close();

	org.postgresql.PGNotification notifications[] = pgconn
		.getNotifications();
	if (notifications != null) {
	    for (int i = 0; i < notifications.length; i++) {
		logger.info("Got notification: " + notifications[i].getName());
		processor.processNew();
	    }
	}

    }

    public void stopMonitoring() {
	processor.stopProcessing();

    }

    /**
     * 
     * @return Processor
     */
    public NewsProcessor getProcessor() {
	return processor;
    }

    /**
     * 
     * @param processor
     */
    public void setProcessor(NewsProcessor processor) {
	this.processor = processor;
    }

    /**
     * 
     * @return CampaignDB
     */
    public CampaignDB getCampaignDB() {
	return campaignDB;
    }

    /**
     * 
     * @param campaignDB
     */
    public void setCampaignDB(CampaignDB campaignDB) {
	this.campaignDB = campaignDB;
    }

}
