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
 
 */package database.objects.campaign;

import java.util.Enumeration;
import java.util.Vector;

import database.connections.ConnectionPool;
import entities.campaign.Campaign;
import entities.news.New;
import entities.user.User;
import junit.framework.TestCase;

/**
 * Testing CampaignDB methods
 * 
 * @version 1.0
 * @date 2010-04-06
 * @author Barbara M. Rodeker
 */
public class CampaignDBTest extends TestCase {

    /**
     * Campaign DB
     */
    private CampaignDB camp;

    protected void setUp() throws Exception {
	super.setUp();
	ConnectionPool pool = new ConnectionPool();
	camp = new CampaignDB(pool);
    }

    /**
     * Testing constructor
     */
    public void testCampaignDB() {
	CampaignDB campaign = new CampaignDB();
	assertNotNull(campaign);
    }

    /**
     * Testing constructor
     */
    public void testCampaignDBConnectionPool() {
	ConnectionPool pool = new ConnectionPool();
	CampaignDB campaign = new CampaignDB(pool);
	assertNotNull(campaign);

    }

    /**
     * Testing pool connection setup
     */
    public void testSetPool() {
	ConnectionPool pool2 = new ConnectionPool();
	camp.setPool(pool2);

	System.out.println("Testing connection pool");
    }

    /**
     * Testing select campaigns
     */
    public void testGetCampaigns() {
	Vector<Campaign> campaigns = camp.getCampaigns();

	assertNotNull(campaigns);

	for (Enumeration<Campaign> e = campaigns.elements(); e
		.hasMoreElements();) {
	    Campaign c = e.nextElement();
	    System.out.println(c.getDescription());

	}
    }

    /**
     * Testing select campaing by identifier
     */
    public void testGetCampaignById() {
	Campaign c = camp.getCampaignById(1);

	assertNotNull(c);

	System.out.println(c.getDescription());
    }

    /**
     * Testing select campaigns´news
     */
    public void testGetCampaignNews() {
	Campaign c = new Campaign();
	c.setId(1);

	Vector<New> news = camp.getCampaignNews(c);

	for (Enumeration<New> e = news.elements(); e.hasMoreElements();) {
	    New elem = e.nextElement();
	    System.out.println(elem.getText());

	}
	assertNotNull(news);

    }

    /**
     * Testing backup table
     */
    public void testSelectAndDeletebackupNew() {
	New n = camp.selectbackupNew();
	System.out.println("Testing select beckup new");
	System.out.println(n);

	if (n != null) {
	    camp.deletebackupNew(n);
	    n = null;
	}

	assertNull(n);

    }

    /**
     * Testing select subscribers
     */
    public void testGetSuscribers() {
	Campaign c = new Campaign();
	c.setId(1);
	Vector<User> subs = camp.getSuscribers(c);

	for (Enumeration<User> e = subs.elements(); e.hasMoreElements();) {
	    User u = e.nextElement();
	    System.out.println(u.getName() + u.getSurname());
	}

	assertNotNull(subs);

    }

}
