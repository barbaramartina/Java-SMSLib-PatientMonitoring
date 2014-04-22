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
package entities.user;

import junit.framework.TestCase;

public class UserTest extends TestCase {

    private User user; 
    
    protected void setUp() throws Exception {
	super.setUp();
	user = new User();
    }

    public void testUsuario() {
	User u = new User();
	assertNotNull(u);
    }

    public void testUsuarioIntStringStringStringInt() {
	User u = new User(1,"S","S","S",1,"","");
	assertNotNull(u);
    }

    public void testGetName() {
	assertEquals("", user.getName());
    }

    public void testGetSurname() {
	assertEquals("", user.getSurname());
    }

    public void testGetPassword() {
	assertEquals("", user.getPassword());
    }

    public void testGetId() {
	assertEquals(-1, user.getId());
    }

    public void testGetProfile() {
	assertEquals(-1, user.getProfile());
    }

    public void testGetFirstPhone() {
	assertEquals(-1, user.getFirstPhone());
    }

    public void testGetCelularPhone() {
	assertEquals(-1, user.getCelularPhone());
    }

}
