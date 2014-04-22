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
package sms.monitoring.responses;

import sms.senders.SMSLibSenderException;
import junit.framework.TestCase;

public class ResponsesMonitorTest extends TestCase {

    public void testRun() {
	ResponsesMonitor monitor = null;
	try {
	    monitor = new ResponsesMonitor();
	} catch (SMSLibSenderException e) {
	    e.printStackTrace();
	}
	assertNotNull(monitor);
    }

    public void testResponsesMonitor() {
	ResponsesMonitor monitor = null;
	try {
	    monitor = new ResponsesMonitor();
	} catch (SMSLibSenderException e) {
	    e.printStackTrace();
	}
	monitor.run();
    }

}
