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
 
 */package database.objects.patient;

import java.util.Vector;

import junit.framework.TestCase;
import database.connections.ConnectionPool;
import entities.user.User;

/**
 * Testing PatientDB methods
 * 
 * @version 1.0
 * @date 2010-04-06
 * @author Barbara M. Rodeker
 */
public class PatientDBTest extends TestCase {

    /**
     * Constructor
     */
    public void testPatientDBConnectionPool() {
	ConnectionPool pool = new ConnectionPool();
	PatientDB pdb = new PatientDB(pool);
	assertNotNull(pdb);
    }

    /**
     * Select all patient
     */
    public void testGetPatients() {
	PatientDB pdb = new PatientDB();
	Vector<User> patients = pdb.getPatients();
	assertNotNull(patients);
    }

    /**
     * Select by identifier
     */
    public void testGetPatientById() {
	PatientDB pdb = new PatientDB();
	User p = pdb.getPatientById(5);
	assertNotNull(p);
    }

    public void testGetPatientByCel() {
	PatientDB pdb = new PatientDB();
	User p = pdb.getPatientByCel("222");
	assertNotNull(p);
    }

}
