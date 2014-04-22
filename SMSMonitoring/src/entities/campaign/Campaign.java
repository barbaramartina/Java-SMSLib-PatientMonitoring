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

import entities.doctor.Doctor;
import entities.news.New;
import entities.user.User;

/**
 * Information and functions related to registered health campaigns
 * 
 * @version 1.0
 * @date 2010-04-06
 * @author Barbara M. Rodeker
 */
public class Campaign {

    /**
     * Database identifier
     */
    private int id = -1;
    /**
     * Campaign´s editores
     */
    private Vector<Doctor> editors = null;
    /**
     * Subscribers
     */
    private Vector<User> subscribers = null;
    /**
     * Registered news
     */
    private Vector<New> news = null;
    /**
     * Description
     */
    private String description = "";
    /**
     * Date from
     */
    private String dateFrom = "";
    /**
     * Date to
     */
    private String dateTo = "";

    /**
     * 
     * Constructor
     */
    public Campaign() {

    }

    /**
     * 
     * Constructor
     * 
     * @param int Identifier
     * @param Vector Editors
     * @param Vector Subscribers
     * @param Vector News
     * @param String Description
     * @param String Date from
     * @param String Date to
     * 
     */
    public Campaign(int id, Vector<Doctor> editors,
	    Vector<User> subs, Vector<New> news, String desc,
	    String dateFrom, String dateTo) {
	this.id = id;
	this.editors = editors;
	this.subscribers = subs;
	this.news = news;
	this.description = desc;
	this.dateFrom = dateFrom;
	this.dateTo = dateTo;
    }

    /**
     * 
     * @return int Database identifier
     */
    public int getId() {
        return id;
    }

    /**
     * 
     * @return Vector Editors group
     */
    public Vector<Doctor> getEditors() {
        return editors;
    }

    /**
     * 
     * @return Vector Subscribers
     */
    public Vector<User> getSubscribers() {
        return subscribers;
    }

    /**
     * 
     * @return Vector Registered news
     */
    public Vector<New> getNews() {
        return news;
    }

    /**
     * 
     * @return String Description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @return String Date from
     */
    public String getDateFrom() {
        return dateFrom;
    }

    /**
     * 
     * @return String Date to
     */
    public String getDateTo() {
        return dateTo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEditors(Vector<Doctor> editors) {
        this.editors = editors;
    }

    public void setSubscribers(Vector<User> subscribers) {
        this.subscribers = subscribers;
    }

    public void setNews(Vector<New> news) {
        this.news = news;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    
}
