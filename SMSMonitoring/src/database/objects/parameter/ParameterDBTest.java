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
 
 */package database.objects.parameter;

import java.util.Enumeration;
import java.util.Vector;

import junit.framework.TestCase;
import database.connections.ConnectionPool;
import entities.category.Category;
import entities.parameter.Parameter;

/**
 * Testing ParameterDB methods
 * 
 * @version 1.0
 * @date    2010-04-06
 * @author  Barbara M. Rodeker
 */
public class ParameterDBTest extends TestCase {

   /**
    * Constructor
    */
    public void testParameterDB() {
	ParameterDB pdb = new ParameterDB();
	assertNotNull(pdb);
    }

    /**
     * Constructor
     */
    public void testParameterDBConnectionPool() {
	ConnectionPool pool = new ConnectionPool();
	ParameterDB pdb2 = new ParameterDB(pool);
	assertNotNull(pdb2);
    }

    /**
     * Select parameters for a type
     */
    public void testGetParameters() {
	ParameterDB pdb = new ParameterDB();
	Category cat = new Category();
	cat.setId(1);
	Vector<Parameter> parameters = pdb.getParameters(cat);
	
	for (Enumeration<Parameter> e = parameters.elements(); e.hasMoreElements(); ){
	    Parameter param = e.nextElement();
	    System.out.println(param.getDescription());
	}
	
	assertNotNull(parameters);
    }

}
