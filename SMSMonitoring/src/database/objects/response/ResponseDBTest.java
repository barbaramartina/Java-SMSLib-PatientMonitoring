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
 
 */package database.objects.response;

import java.util.Vector;

import junit.framework.TestCase;
import database.connections.ConnectionPool;
import entities.doctor.Doctor;
import entities.response.Response;
import entities.user.User;

/**
 * Testing ResponseDB methods
 * 
 * @version 1.0
 * @date    2010-04-06
 * @author  Barbara M. Rodeker
 */
public class ResponseDBTest extends TestCase {

    /**
     * Constructor
     */
    public void testResponseDBConnectionPool() {
	ConnectionPool pool = new ConnectionPool();
	ResponseDB resp = new ResponseDB(pool);
	
	assertNotNull(resp);
    }

    /**
     * Select all response
     */
    public void testGetResponses() {
	ResponseDB resp = new ResponseDB();
	Vector<Response> responses = resp.getResponses();
	
	assertNotNull(responses);
    }

    /**
     * Select responses for a patient
     */
    public void testGetPatientResponse() {
	ResponseDB resp = new ResponseDB();
	User p = new User();
	p.setId(5);
	Vector<Response> responses = resp.getPatientResponse(p);
	
	assertNotNull(responses);
    }

    /**
     * Select responses for a doctor
     */
    public void testGetDoctorResponse() {
	ResponseDB resp = new ResponseDB();
	Doctor d = new Doctor();
	d.setId(1);
	Vector<Response> responses = resp.getDoctorResponse(d);
	
	assertNotNull(responses);
    }

    /**
     * Select a backup response
     */
    public void testGetBackupResponse() {
	ResponseDB resp = new ResponseDB();
	Response r = resp.getBackupResponse();
	
	if ( r != null){
	    resp.deleteBackupResponse(r);
	    r = null;
	}
	
	assertNull(r);
    }

}
