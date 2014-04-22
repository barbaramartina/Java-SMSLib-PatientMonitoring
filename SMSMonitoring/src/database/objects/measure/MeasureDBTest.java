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
 
 */package database.objects.measure;

import java.util.Enumeration;
import java.util.Vector;

import junit.framework.TestCase;
import database.connections.ConnectionPool;
import entities.measure.Measure;
import entities.measuretype.MeasureType;
import entities.user.User;

/**
 * Testing MeasureDB methods
 * 
 * @version 1.0
 * @date    2010-04-06
 * @author  Barbara M. Rodeker
 */
public class MeasureDBTest extends TestCase {

    /**
     * MeasureDB object
     */
    private MeasureDB measure = null;
    
    protected void setUp() throws Exception {
	super.setUp();
	ConnectionPool pool = new ConnectionPool();
	measure = new MeasureDB(pool);
    }

    /**
     * Testing constructor
     */
    public void testMeasureDB() {
	MeasureDB m = new MeasureDB();
	assertNotNull(m);
    }

    /**
     * Testing constructor
     */
    public void testMeasureDBConnectionPool() {
	ConnectionPool pool2 = new ConnectionPool();
	MeasureDB m2 = new MeasureDB(pool2);

	assertNotNull(m2);
    }

    /**
     * Testing connection pool setup
     */
    public void testSetPool() {
	ConnectionPool pool2 = new ConnectionPool();
	measure.setPool(pool2);
	
	System.out.println("Testing connection pool");
    }

    /**
     * Inserting a measure
     */
    public void testInsertMeasure() {
	Measure m = new Measure();
	
	m.setDate("2010-06-06 00:00:00");
	
	MeasureType mt = new MeasureType();
	mt.setId(1);
	m.setMt(mt);
	
	m.setText("GAD 80");
	
	User p = new User();
	p.setId(5);
	m.setPatient(p);
	
	int code = measure.insertMeasure(m);
	
	assertEquals(1, code);
	
    }

    /**
     * Select all measure
     */
    public void testGetMeasures() {
	Vector<Measure> ms = measure.getMeasures();
	
	assertNotNull(ms);
	
	for (Enumeration<Measure> e = ms.elements(); e.hasMoreElements(); ){
	    Measure m = e.nextElement();
	    System.out.println(m.getText() + m.getDate());
	}
    }

    /**
     * Select a measure by id
     */
    public void testGetMeasureById() {
	Measure m = measure.getMeasureById(133);
	assertNotNull(m);
    }

    /**
     * Select all measure for a patient
     */
    public void testGetPatientMeasures() {
	User p = new User();
	p.setId(5);
	Vector<Measure> ms = measure.getPatientMeasures(p);
	
	assertNotNull(ms);
    }

    /**
     * Select all measure according to its type
     */
    public void testGetMeasureByType() {
	MeasureType mt = new MeasureType();
	mt.setId(1);
	Measure m = measure.getMeasureByType(mt);
	
	assertNotNull(m);
    }

}
