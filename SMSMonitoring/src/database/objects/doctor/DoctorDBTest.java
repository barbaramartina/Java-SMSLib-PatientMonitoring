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
 package database.objects.doctor;

import java.util.Enumeration;
import java.util.Vector;

import database.connections.ConnectionPool;
import entities.doctor.Doctor;
import junit.framework.TestCase;

/**
 * Testing DoctorDB methods
 * 
 * @version 1.0
 * @date    2010-04-06
 * @author  Barbara M. Rodeker
 */
public class DoctorDBTest extends TestCase {

    private DoctorDB doctor = null;

    protected void setUp() throws Exception {
	super.setUp();
	ConnectionPool pool = new ConnectionPool();
	doctor = new DoctorDB(pool);
    }

    /**
     * Testing constructor
     */
    public void testDoctorDB() {
	DoctorDB doc2 = new DoctorDB();
	assertNotNull(doc2);
    }

    /**
     * Testing set pool
     */
    public void testSetPool() {
	ConnectionPool pool = new ConnectionPool();
	doctor.setPool(pool);

	System.out.println("Testing connection pool");
    }

    /**
     * Testing select doctors
     */
    public void testDoctoroctors() {
	Vector<Doctor> doctors = doctor.getDoctors();

	assertNotNull(doctors);

	for (Enumeration<Doctor> e = doctors.elements(); e.hasMoreElements();) {
	    Doctor d = e.nextElement();
	    System.out.println("Doctor: "+d.getName() + " " + d.getSurname());
	}
    }

    /**
     * Testing select doctor by id
     */
    public void testGetDoctorById() {
	Doctor d = doctor.getDoctorById(2);

	assertNotNull(d);

	System.out.println("Dcotor by id: "+ d.getName() + " " + d.getSurname());
    }

}
