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
package sms;

import junit.framework.TestCase;

/**
 * Testing SMS methods
 * 
 * @version 1.0
 * @date    2010-04-06
 * @author  Barbara M. Rodeker
 */
public class SMSTest extends TestCase {

    public void testSMSIntIntString() {
	SMS sms = new SMS("1232","1234","Text");
	assertNotNull(sms);
		
    }

    public void testSMS() {
	SMS sms = new SMS();
	assertNotNull(sms);
    }

    public void testGetCelularFrom() {
	SMS sms = new SMS("1232","1234","Text");
	assertEquals(1232, sms.getCelularFrom());
    }

    public void testSetCelularFrom() {
	SMS sms = new SMS("1232","1234","Text");
	sms.setCelularFrom("3333");
	assertEquals(3333, sms.getCelularFrom());
    }

    public void testGetCelularTo() {
	SMS sms = new SMS("1232","1234","Text");
	assertEquals(1234, sms.getCelularTo());
    }

    public void testSetCelularTo() {
	SMS sms = new SMS("1232","1234","Text");
	sms.setCelularTo("3333");
	assertEquals(3333, sms.getCelularTo());
    }

    public void testGetText() {
	SMS sms = new SMS("1232","1234","Text");
	assertEquals("Text", sms.getText());
    }

    public void testSetText() {
	SMS sms = new SMS("1232","1234","Text");
	sms.setText("");
	assertEquals("", sms.getText());
    }

}
