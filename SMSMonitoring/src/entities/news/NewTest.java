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
package entities.news;

import junit.framework.TestCase;

public class NewTest extends TestCase {
    
    private New novedad;

    protected void setUp() throws Exception {
	super.setUp();
	
	novedad = new New();
    }

    public void testNew() {
	New n = new New();
	assertNotNull(n);
    }

    public void testNovedadIntCampaniaStringString() {
	New n = new New(1, null, "S", "S");
	assertNotNull(n);
    }

    public void testGetId() {
	assertEquals(-1, novedad.getId());
    }

    public void testGetCampaign() {
	assertEquals(null, novedad.getCampaign());
    }

    public void testGetText() {
	assertEquals("", novedad.getText());
    }

    public void testGetDate() {
	assertEquals("", novedad.getDate());
    }

}
