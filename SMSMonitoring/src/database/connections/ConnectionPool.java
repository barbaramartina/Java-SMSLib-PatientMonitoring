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
import java.util.Properties;
import java.util.Vector;

import org.apache.log4j.Logger;

import config.ConfigManager;
import config.ConfigurationException;

/**
 * manager of established connections group
 * 
 * @version 1.0
 * @date 2010-04-06
 * @author Barbara M. Rodeker
 */
public class ConnectionPool {

    /**
     * maximum numbers of allowed connections
     */
    private int maxConnections = 0;

    /**
     * Connections vector
     */
    private Vector<ConnectionDB> connections = null;

    /**
     * Active connection number
     */
    private int actualConnection = 0;

    /**
     * Logger
     */
    private static Logger logger = Logger.getLogger(ConnectionPool.class);

    /**
     * Properties and messages file
     */
    Properties props = null;

    /**
     * 
     * Constructor
     */
    public ConnectionPool() {
	ConfigManager configurations = null;
	try {
	    props = new Properties();
	    props
		    .load(ConnectionPool.class
			    .getResourceAsStream("/database/connections/resources.properties"));

	    configurations = ConfigManager.getInstance();

	    maxConnections = configurations.getDatabaseMaxConnections();
	    
	    connections = new Vector<ConnectionDB>();

	    for (int i = 0; i < maxConnections; ++i) {
		connections.add(new ConnectionDB());
	    }
	} catch (ConfigurationException e) {
	    logger
		    .error(props
			    .getProperty("database.connections.ConfigurationExceptionMsg"));
	} catch (IOException e) {
	    logger.error("Error cargando propiedades y recursos");
	}

    }

    /**
     * Return a database connection. 
     * Update the actual connection number for returning the next connections.
     * 
     * @return ConnectionDB Established connection
     */
    public synchronized ConnectionDB getConnection() {
	ConnectionDB conn = connections.get(actualConnection);

	++actualConnection;

	if (actualConnection >= maxConnections) {
	    actualConnection = 0;
	}

	return conn;
    }
}
