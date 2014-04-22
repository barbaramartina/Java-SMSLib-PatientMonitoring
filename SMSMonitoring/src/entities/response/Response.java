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

import entities.doctor.Doctor;
import entities.user.User;

/**
 * 
 * Information and functions related to responses sent
 * 
 * @version 1.0
 * @date 2010-04-06
 * @author Barbara M. Rodeker
 */
public class Response {

    /**
     * Registering date
     */
    private String date = "";
    /**
     * Identifier
     */
    private int id = -1;
    /**
     * Number of notifications sent
     */
    private int notifications = -1;
    /**
     * Sent flag
     */
    private boolean sent = false;
    /**
     * Response text
     */
    private String response = "";
    /**
     * Patient
     */
    private User patient = null;
    /**
     * Doctor
     */
    private Doctor doctor = null;
    
    /**
     * 
     * Constructor
     */
    public Response(){
	
    }

    /**
     * 
     * Constructor
     * 
     * @param String Date
     * @param int Identifier
     * @param int Notifications
     * @param boolean Sent flag
     * @param String Response
     * @param Paciente Patient
     * @param Medico Doctor
     */
    public Response(String date, int id, int notif, boolean sent, String response, User p , Doctor m){
	this.id = id;
	this.date = date;
	this.notifications = notif;
	this.sent = sent;
	this.response = response;
	this.patient = p;
	this.doctor = m;
	
    }

    /**
     * 
     * @return String Registering date
     */
    public String getDate() {
        return date;
    }

    /**
     * 
     * @return int Identifier
     */
    public int getId() {
        return id;
    }

    /**
     * 
     * @return int Number of notifications sent
     */
    public int getNotifications() {
        return notifications;
    }

    /**
     * 
     * @return boolean Sent flag
     */
    public boolean isSent() {
        return sent;
    }

    /**
     * 
     * @return String Response
     */
    public String getResponse() {
        return response;
    }

    /**
     * 
     * @return Patient
     */
    public User getPatient() {
        return patient;
    }

    /**
     * 
     * @return Doctor
     */
    public Doctor getDoctor() {
        return doctor;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNotifications(int notifications) {
        this.notifications = notifications;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public void setPatient(User patient) {
        this.patient = patient;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
    
    
}
