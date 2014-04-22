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
package entities.parameter;

import junit.framework.TestCase;

public class ParameterTest extends TestCase {

    private Parameter parameter;

    protected void setUp() throws Exception {
	super.setUp();
	parameter = new Parameter();
    }

    public void testParametro() {
	Parameter p = new Parameter();
	assertNotNull(p);
    }

    public void testParametroStringStringTipoMedicionIntFloatFloat() {
	Parameter p = new Parameter("S","S",null,1,225,225);
	assertNotNull(p);
    }

    public void testGetDescription() {
	assertEquals("", parameter.getDescription());
    }

    public void testGetValidation() {
	assertEquals("", parameter.getValidation());
    }

    public void testGetCategory() {
	assertEquals(null, parameter.getCategory());
    }

    public void testGetId() {
	assertEquals(-1, parameter.getId());
    }

    public void testGetVal_min() {
	assert( parameter.getVal_min() == 0);
    }

    public void testGetVal_max() {
	assert( parameter.getVal_max() == 0);
    }

}
