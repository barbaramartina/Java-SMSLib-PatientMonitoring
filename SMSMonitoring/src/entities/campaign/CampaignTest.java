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
package entities.campaign;

import java.util.Vector;

import junit.framework.TestCase;
import entities.doctor.Doctor;
import entities.news.New;
import entities.user.User;

public class CampaignTest extends TestCase {
    
    private Campaign camp;
    
    protected void setUp() throws Exception {
	super.setUp();
	
	camp = new Campaign();
    }

    public void testCampaign() {
	Campaign c;
	c = new Campaign();
	assertNotNull(c);
	
    }

    public void testCampaniaIntVectorOfMedicoVectorOfUsuarioVectorOfNovedadStringStringString() {
	Campaign c;
	c = new Campaign(1,new Vector<Doctor>(),new Vector<User>(),new Vector<New>(), "A", "B", "C");
	assertNotNull(c);
    }

    public void testGetId() {
	assertEquals(camp.getId(), -1);
    }

    public void testGetEditors() {
	assertEquals(camp.getEditors(), null);
    }

    public void testGetSubscribers() {
	assertEquals(camp.getSubscribers(), null);
    }

    public void testGetNews() {
	assertEquals(camp.getNews(), null);
    }

    public void testGetDescription() {
	assertEquals(camp.getDescription(), "");
    }

    public void testGetDateFrom() {
	assertEquals(camp.getDateFrom(), "");

    }

    public void testGetDateTo(){
	assertEquals(camp.getDateTo(), "");
    }

}
