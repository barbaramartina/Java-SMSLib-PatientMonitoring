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
 package config;

import junit.framework.TestCase;

public class TestConfigManager extends TestCase {

    public void testGetInstance() {
	ConfigManager configManager = null;
	try {
	    configManager = ConfigManager.getInstance();
	    
	    System.out.println(configManager.getDatabaseDriver());
	    System.out.println(configManager.getDatabaseHost());
	    System.out.println(configManager.getDatabaseHostAddress());
	    System.out.println(configManager.getDatabaseMaxConnections());
	    System.out.println(configManager.getDatabaseName());
	    System.out.println(configManager.getDatabasePassword());
	    System.out.println(configManager.getDatabasePort());
	    System.out.println(configManager.getDatabaseURL());
	    System.out.println(configManager.getModemBaudRate());
	    System.out.println(configManager.getModemId());
	    System.out.println(configManager.getModemManufacturer());
	    System.out.println(configManager.getModemModel());
	    System.out.println(configManager.getModemPort());


	} catch (ConfigurationException e) {
	    fail("Problemas en la lectura de la configuración");
	}
	
	assertNotNull(configManager);
	
    }
    
    


}
