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
package entities.doctor;

import entities.user.User;

/**
 * Information and functions about doctors
 * 
 * @version 1.0
 * @date 2010-04-06
 * @author Barbara M. Rodeker
 */
public class Doctor extends User {

    /**
     * Registration
     */
    private String registration = "";
    /**
     * Institution
     */
    private int institution = -1;
    /**
     * Area
     */
    private int specialty = -1;

    /**
     * 
     * Constructor
     */
    public Doctor() {
	super();

    }

    /**
     * 
     * Constructor
     * 
     * @param int Identifier
     * @param String Name
     * @param String Surname
     * @param String Password
     * @param int Profile Identifier
     * @param String Registration
     * @param int Institution
     * @param int Area
     * 
     */
    public Doctor(int id, String n, String sn,
	    String passw, int idP, String m, int inst, int specialty,String phone, String cel) {
	super(id, n, sn, passw, idP,phone, cel);

	this.registration = m;
	this.specialty = specialty;
	this.institution = inst;

    }

    /**
     * 
     * @return String Registration
     */
    public String getRegistration() {
        return registration;
    }

    /**
     * 
     * @return int Institution
     */
    public int getInstitution() {
        return institution;
    }

    /**
     * 
     * @return int Area
     */
    public int getSpecialty() {
        return specialty;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public void setInstitution(int institution) {
        this.institution = institution;
    }

    public void setSpecialty(int specialty) {
        this.specialty = specialty;
    }

    
}
