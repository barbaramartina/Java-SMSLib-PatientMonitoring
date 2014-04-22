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
package sms.monitoring.responses;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.log4j.Logger;

import sms.processors.news.NewsProcessor;
import sms.processors.responses.ResponsesProcessor;
import sms.senders.SMSLibSenderException;
import database.connections.ConnectionPool;
import database.objects.measure.MeasureDB;
import database.objects.response.ResponseDB;

/**
 * Monitor responses arriving from database after a doctor registers them
 * 
 * @version 1.0
 * @date 2010-04-06
 * @author Barbara M. Rodeker
 */
public class ResponsesMonitor extends Thread {

    /**
     * Response processor
     */

    private ResponsesProcessor processor = null;
    /**
     * Postgre connection
     */
    private org.postgresql.PGConnection pgconn;
    /**
     * Pool
     */
    private ConnectionPool pool = null;
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
    public ResponsesMonitor() throws SMSLibSenderException {
	loadProperties();
	this.processor = new ResponsesProcessor();
	this.pool = new ConnectionPool();

    }

    /**
     * Constructor
     * 
     * @param responsesDB
     *            Database object
     * @param processor
     *            Response processor
     * @param pool
     *            Connection pool
     */
    public ResponsesMonitor(ResponseDB responsesDB,
	    ResponsesProcessor processor, ConnectionPool pool) {
	loadProperties();
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
			    .getResourceAsStream("/sms/processors/responses/resources.properties"));
	} catch (IOException e) {
	    logger.error("Error cargando propiedades y recursos");
	    logger.error(e.toString());
	}

    }

    /**
     * This Thread run forever listening for incoming responses in database in
     * order to process them
     */
    public void run() {
	logger.info("Starting run method....");
	try {
	    startListening();
	} catch (SQLException e1) {
	    logger
		    .fatal(props
			    .getProperty("sms.monitoring.responses.ErrorFatalListeningDB"));
	    logger.fatal(e1.toString());
	    return;
	}

	while (true) {
	    try {
		listenForResponses();
		sleep(3000);
	    } catch (SQLException e1) {
		logger
			.error(props
				.getProperty("sms.monitoring.responses.ErrorProcessing"));
		logger.error(e1.toString());
	    } catch (InterruptedException e) {
		logger.error(props
			.getProperty("sms.monitoring.responses.ErrorSleeping"));
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
	stmt.execute("LISTEN medicion_respuesta");
	stmt.close();

    }

    /**
     * Listens for changes in database. Process news related to responses.
     * 
     * @throws SQLException
     * 
     */
    private void listenForResponses() throws SQLException {

	Statement stmt = pool.getConnection().getConnection().createStatement();
	ResultSet rs = stmt.executeQuery("SELECT 1");
	rs.close();
	stmt.close();

	org.postgresql.PGNotification notifications[] = pgconn
		.getNotifications();
	if (notifications != null) {
	    for (int i = 0; i < notifications.length; i++) {
		logger.info("Got notification: "
			+ notifications[i].getName());
	
		    processor.processResponse();
	
	    }
	}

    }

    public void stopMonitoring() {
	 processor.stopProcessing(); 

    }

 
}
