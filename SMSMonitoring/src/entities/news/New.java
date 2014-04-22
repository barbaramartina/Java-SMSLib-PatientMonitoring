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

import entities.campaign.Campaign;

/**
 * Information and functions related to published news 
 * 
 * @version 1.0
 * @date 2010-04-06
 * @author Barbara M. Rodeker
 */
public class New {

    /**
     * Identifier
     */
    private int id = -1;
    /**
     * Campaign
     */
    private Campaign campaign = null;
    /**
     * Text
     */
    private String text = "";
    /**
     * Publishing date
     */
    private String date = "";

    /**
     * 
     * Constructor
     */
    public New() {

    }

    /**
     * 
     * Constructor
     * 
     * @param int Identifier
     * @param Campania Campaign
     * @param String Text
     * @param String Date
     * 
     */
    public New(int id, Campaign c, String text, String date) {
	this.id = id;
	this.campaign = c;
	this.text = text;
	this.date = date;
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
     * @return Campaign
     */
    public Campaign getCampaign() {
        return campaign;
    }

    /**
     * 
     * @return String Text
     */
    public String getText() {
        return text;
    }

    /**
     * 
     * @return String Date
     */
    public String getDate() {
        return date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
}
