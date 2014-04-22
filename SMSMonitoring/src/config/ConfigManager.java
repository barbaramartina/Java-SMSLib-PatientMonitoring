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
 
 */package config;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

/**
 * Clase encargada de las lectura de valores configurables en la aplicación.
 * Singleton.
 * 
 * @date 2010-06-04
 * @author Barbara M. Rodeker
 * @version 1.0
 * 
 */
public class ConfigManager {

    /**
     * Instancia de la clase - Singleton - .
     */
    private static ConfigManager configManager = null;

    /**
     * Logger
     */
    private static Logger logger = Logger.getLogger(ConfigManager.class);

    /**
     * Properties and messages file
     */
    private Properties props = new Properties();

    /**
     * Database values
     */
    private String databaseName = "";
    private String databaseHost = "";
    private String databaseHostAddress = "";
    private String databaseUser = "";
    private String databasePassword = "";
    private String databaseURL = "";
    private String databaseDriver = "";

    private int databasePort = 0;
    private int databaseMaxConnections = -1;

    /**
     * Modem values
     */
    private String modemId = "";
    private String modemPort = "";
    private String modemManufacturer = "";
    private int modemBaudRate = -1;
    private String modemModel = "";
    private String SMSC = "";
    private String simPin = "";

    /**
     * Constructor privado. Carga en la clase el archivo de propiedades con los
     * recursos a usar. Lee desde el archivo XML de la aplicación los datos
     * configurables.
     * 
     * @throws ConfigurationException
     *             En caso de ocurrir algún error en la lectura del XML o del
     *             archivo de propiedades
     * 
     */
    private ConfigManager() throws ConfigurationException {

	try {
	    props.load(ConfigManager.class
		    .getResourceAsStream("/config/resources.properties"));
	} catch (IOException e1) {
	    logger.error("Error cargando clase de properties asociadas.");

	    throw new ConfigurationException();
	}

	try {
	    SAXBuilder builder = new SAXBuilder(false);
	    // we use the parser without dtd validation
	    Document doc = builder.build(ConfigManager.class
		    .getResourceAsStream("/config/config.xml"));
	    Element root = doc.getRootElement();
	    Element database = root.getChild("database");

	    // we read database parameters
	    databaseUser = database.getChild("user").getText();
	    databaseDriver = database.getChild("driver").getText();
	    databaseHost = database.getChild("host").getText();
	    databaseHostAddress = database.getChild("hostaddress").getText();
	    databaseMaxConnections = Integer.parseInt(database.getChild(
		    "max_connections").getText());
	    databaseName = database.getChild("name").getText();
	    databasePassword = database.getChild("password").getText();
	    databasePort = Integer
		    .parseInt(database.getChild("port").getText());
	    databaseURL = database.getChild("url").getText();

	    // we read modem parameters
	    Element modem = root.getChild("modem");

	    modemId = modem.getChild("id").getText();
	    modemBaudRate = Integer.parseInt(modem.getChild("baudRate")
		    .getText());
	    modemManufacturer = modem.getChild("manufacturer").getText();
	    modemModel = modem.getChild("model").getText();
	    modemPort = modem.getChild("port").getText();
	    SMSC = modem.getChild("SMSC").getText();
	    simPin = modem.getChild("simPin").getText();

	} catch (Exception e) {
	    logger
		    .error(props
			    .getProperty("config.ConfigManager.ExceptionReadingConfigurations"));
	    throw new ConfigurationException();
	}

    }

    /**
     * Crea la instancia en caso de ser nula llamando al constructor privado.
     * 
     * @throws ConfigurationException
     */
    private synchronized static void createInstance()
	    throws ConfigurationException {
	if (configManager == null) {
	    configManager = new ConfigManager();
	}

    }

    /**
     * Controla si existe una instancia creada en cuyo caso la retorna o bien
     * pida la creación de una nueva instancia.
     * 
     * @return ConfigManager Instancia creada
     * @throws ConfigurationException
     *             En caso de problemas al inicializar la instancia.
     */
    public static ConfigManager getInstance() throws ConfigurationException {
	if (configManager == null) {
	    createInstance();
	}
	return configManager;

    }

    /**
     * 
     * @return String nombre de la base de datos
     */
    public String getDatabaseName() {
	return databaseName;
    }

    /**
     * 
     * @return String Host donde se aloja la base de datos
     */
    public String getDatabaseHost() {
	return databaseHost;
    }

    /**
     * 
     * @return String Dirección del host donde se aloja la base
     */
    public String getDatabaseHostAddress() {
	return databaseHostAddress;
    }

    /**
     * 
     * @return String Usuario de conexión para la base de datos
     */
    public String getDatabaseUser() {
	return databaseUser;
    }

    /**
     * 
     * @return String Password de conexión de la base
     */
    public String getDatabasePassword() {
	return databasePassword;
    }

    /**
     * 
     * @return String Puerto a donde conectarse a la base
     */
    public int getDatabasePort() {
	return databasePort;
    }

    /**
     * 
     * @return int Máxima cantidad de conexiones simultáneas a la base
     */
    public int getDatabaseMaxConnections() {
	return databaseMaxConnections;
    }

    /**
     * 
     * @return String URL para conectarse a la base
     */
    public String getDatabaseURL() {
	return databaseURL;
    }

    /**
     * 
     * @return String Driver de base de datos a utilizar
     */
    public String getDatabaseDriver() {
	return databaseDriver;
    }

    /**
     * 
     * @return Identifier
     */
    public String getModemId() {
	return modemId;
    }

    /**
     * 
     * @return Modem port
     */
    public String getModemPort() {
	return modemPort;
    }

    /**
     * 
     * @return Modem manufacturer
     */
    public String getModemManufacturer() {
	return modemManufacturer;
    }

    /**
     * 
     * @return Modem baud rate
     */
    public int getModemBaudRate() {
	return modemBaudRate;
    }

    /**
     * 
     * @return Modem model
     */
    public String getModemModel() {
	return modemModel;
    }

    /**
     * 
     * @return SMSC
     */
    public String getSMSC() {
        return SMSC;
    }

    /**
     * 
     * @return Sim Pin
     */
    public String getSimPin() {
        return simPin;
    }
    
    

}
