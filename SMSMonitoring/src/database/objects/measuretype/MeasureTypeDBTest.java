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
 
 */package database.objects.measuretype;

import java.util.Enumeration;
import java.util.Vector;

import junit.framework.TestCase;
import database.connections.ConnectionPool;
import entities.measuretype.MeasureType;

/**
 * Testing MeasureTypeDB methods
 * 
 * @version 1.0
 * @date    2010-04-06
 * @author  Barbara M. Rodeker
 */
public class MeasureTypeDBTest extends TestCase {

    /**
     * MeasureTypeDB object
     */
    private MeasureTypeDB mt = null;
    
    protected void setUp() throws Exception {
	super.setUp();
	mt = new MeasureTypeDB();
    }

    /**
     * Constructor test
     */
    public void testMeasureTypeDB() {
	MeasureTypeDB mt2 = new MeasureTypeDB();
	assertNotNull(mt2);
    }

    /**
     * Constructor test
     */
    public void testMeasureTypeDBConnectionPool() {
	ConnectionPool pool2 = new ConnectionPool();
	MeasureTypeDB mt = new MeasureTypeDB(pool2);
	
	assertNotNull(mt);
    }

    /**
     * Connection pool setup
     */
    public void testSetPool() {
	ConnectionPool p2 = new ConnectionPool();
	
	mt.setPool(p2);
	
	System.out.println("Testing Measure type Connection Pool");
    }

    /**
     * Select all measure types
     */
    public void testGetMeasureTypes() {
	Vector<MeasureType> types = mt.getMeasureTypes();
	assertNotNull(types);
	
	for (Enumeration<MeasureType> e = types.elements(); e.hasMoreElements();){
	    MeasureType type1 = e.nextElement();
	    System.out.println(type1.getDescription());
	}
	
    }

    /**
     * Select a measure by id
     */
    public void testGetMeasureTypeById() {
	MeasureType type = mt.getMeasureTypeById(1);
	assertNotNull(type);
    }

    /**
     * Select a measure by value
     */
    public void testGetMeasureTypeByValue() {
	MeasureType type = mt.getMeasureTypeByValue("GAD");
	assertNotNull(type);
    }

}
