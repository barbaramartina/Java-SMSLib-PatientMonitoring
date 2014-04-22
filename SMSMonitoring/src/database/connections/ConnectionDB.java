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
 
 */package database.connections;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

import config.ConfigManager;
import config.ConfigurationException;

/**
 * Established database connection
 * 
 * @version 1.0
 * @date 2010-04-06
 * @author Barbara M. Rodeker
 */
public class ConnectionDB {

    /**
     * Connection
     */
    private Connection connection = null;

    /**
     * Logger
     */
    private static Logger logger = Logger.getLogger(ConnectionDB.class);

    /**
     * Properties and messsages file
     */
    private Properties props = null;

    /**
     * 
     * Constructor
     */
    public ConnectionDB() {

	try {
	    props = new Properties();
	    props
		    .load(ConnectionDB.class
			    .getResourceAsStream("/database/connections/resources.properties"));

	    ConfigManager configurations = ConfigManager.getInstance();
	    // Database driver is loaded
	    Class.forName(configurations.getDatabaseDriver());

	    // connection is created
	    connection = DriverManager.getConnection(configurations
		    .getDatabaseURL(), configurations.getDatabaseUser(),
		    configurations.getDatabasePassword());

	} catch (ConfigurationException configExc) {
	    logger
		    .error(props
			    .getProperty("database.connections.ConfigurationExceptionMsg"));
	} catch (SQLException sqlExc) {
	    logger.error(props
		    .getProperty("database.connections.SQLExceptionMsg"));
	} catch (ClassNotFoundException e) {
	    logger
		    .error(props
			    .getProperty("database.connections.ClassNotFoundExceptionMsg"));
	} catch (IOException e) {
	    logger.error("Error cargando propiedades y recursos");
	}

    }

    /**
     * 
     * @return java.sql.Connection Established connection
     */
    public Connection getConnection() {
	return connection;
    }

}
