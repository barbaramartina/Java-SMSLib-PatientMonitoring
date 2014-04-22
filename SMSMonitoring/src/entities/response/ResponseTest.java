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
package entities.response;

import junit.framework.TestCase;

public class ResponseTest extends TestCase {

    
    private Response response;
    
    protected void setUp() throws Exception {
	super.setUp();
	response = new Response();
    }

    public void testRespuesta() {
	Response rta = new Response();
	assertNotNull(rta);
    }

    public void testRespuestaStringIntIntBooleanStringPacienteMedico() {
	Response rta = new Response("S",1,1,true,"S",null,null);
	assertNotNull(rta);
    }

    public void testGetDate() {
	assertEquals("", response.getDate());
    }

    public void testGetId() {
	assertEquals(-1, response.getId());
    }

    public void testGetNotifications() {
	assertEquals(-1, response.getNotifications());
    }

    public void testIsSent() {
	assertEquals(false, response.isSent());
    }

    public void testGetResponse() {
	assertEquals("", response.getResponse());
    }

    public void testGetPatient() {
	assertEquals(null, response.getPatient());
    }

    public void testGetDoctor() {
	assertEquals(null, response.getDoctor());
    }

}
