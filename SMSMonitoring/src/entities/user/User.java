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

/**
 * Information and funstions related to a registered user
 * 
 * @version 1.0
 * @date 2010-04-06
 * @author Barbara M. Rodeker
 */
public class User {

    /**
     * Name
     */
    private String name = "";
    /**
     * Surname
     */
    private String surname = "";
    /**
     * Password
     */
    private String password = "";
    /**
     * Database identifier
     */
    private int id = -1;
    /**
     * Profile identifier
     */
    private int profile = -1;
    
    /**
     * First Phone 
     */
    private String firstPhone = "";
    /**
     * Celular Phone 
     */
    private String celularPhone = "";
    

    /**
     * 
     * Constructor
     */
    public User() {

    }

    /**
     * 
     * Constructor
     * 
     * @param int Identifier
     * @param String Name
     * @param String Surname
     * @param String Password
     * @param int Profile identifier
     * 
     */
    public User(int id, String name, String surname, String passw, int idProfile, String phone, String celular) {
	this.id = id;
	this.name = name;
	this.surname = surname;
	this.password = passw;
	this.profile = idProfile;
	this.firstPhone = phone;
	this.celularPhone = celular;
	
	
    }

    /**
     * 
     * @return String Name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @return String Surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * 
     * @return String Password
     */
    public String getPassword() {
        return password;
    }

    /**
     * 
     * @return int Id
     */
    public int getId() {
        return id;
    }

    /**
     * 
     * @return int Profile id
     */
    public int getProfile() {
        return profile;
    }

    /**
     * 
     * @return int First phone
     */
    public String getFirstPhone() {
        return firstPhone;
    }

    /**
     * 
     * @return int Celular phone
     */
    public String getCelularPhone() {
        return celularPhone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProfile(int profile) {
        this.profile = profile;
    }

    public void setFirstPhone(String firstPhone) {
        this.firstPhone = firstPhone;
    }

    public void setCelularPhone(String celularPhone) {
        this.celularPhone = celularPhone;
    }
    
}
