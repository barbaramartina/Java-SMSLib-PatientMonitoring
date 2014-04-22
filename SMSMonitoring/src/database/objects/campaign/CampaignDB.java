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

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.util.Vector;

import org.apache.log4j.Logger;

import database.connections.ConnectionPool;
import entities.campaign.Campaign;
import entities.news.New;
import entities.user.User;

/**
 * Provide database access to campaigns table.
 * 
 * @version 1.0
 * @date 2010-04-06
 * @author Barbara M. Rodeker
 */
public class CampaignDB {

    /**
     * QUERY select every campaign inserted
     */
    private static String querySelectAllCampaigns = "SELECT id, descripcion, date_part('day',fecha_desde), date_part('month',fecha_desde), date_part('year',fecha_desde), date_part('day',fecha_hasta), date_part('month',fecha_hasta), date_part('year',fecha_hasta) FROM campania";
    /**
     * QUERY select a campaign according to its identifier
     */
    private static String querySelectCampaignById = "SELECT id, descripcion, date_part('day',fecha_desde), date_part('month',fecha_desde), date_part('year',fecha_desde), date_part('day',fecha_hasta), date_part('month',fecha_hasta), date_part('year',fecha_hasta) FROM campania WHERE id = ?";
    /**
     * QUERY select the news related to a campaign
     */
    private static String querySelectNewsFromCampaign = "SELECT id, contenido, date_part('day',fecha_publicacion), date_part('month',fecha_publicacion), date_part('year',fecha_publicacion), id_campania FROM campania_novedad WHERE id_campania = ?";
    /**
     * QUERY select the readers
     */
    private static String querySelectCampaignFollowers = "SELECT nombre, apellido,  id, celular FROM usuario u LEFT JOIN campania_usuario cu ON u.id = cu.id_usuario  WHERE id_campania = ?";
    /**
     * QUERY delete a campaign from the backup campaign table
     */
    private static String queryDeleteFromNovedadesBackup = "DELETE FROM campania_novedad_backup where id = ?";
    /**
     * QUERY select a campaign from the backup table
     */
    private static String querySelectFromNovedadesBackup = "SELECT id, contenido, date_part('day',fecha_publicacion), date_part('month',fecha_publicacion), date_part('year',fecha_publicacion), id_campania FROM campania_novedad_backup ORDER BY id LIMIT 1";

    /**
     * Properties and messages file
     */
    private Properties props = null;

    /**
     * Logger
     */
    private static Logger logger = Logger.getLogger(CampaignDB.class);

    /**
     * Connection pool
     */
    ConnectionPool pool = null;

    /**
     * 
     * Constructor
     */
    public CampaignDB() {
	loadProperties();
	pool = new ConnectionPool();

    }

    /**
     * 
     * Constructor
     * 
     * @param ConnectionPool
     */
    public CampaignDB(ConnectionPool pool) {
	loadProperties();
	this.pool = pool;
    }

    /**
     * Load the properties and messages file
     * 
     */
    private void loadProperties() {

	// Properties are inicialized
	props = new Properties();
	try {
	    props
		    .load(CampaignDB.class
			    .getResourceAsStream("/database/objects/campaign/resources.properties"));
	} catch (IOException e) {
	    logger.error("Error cargando propiedades y recursos");
	    logger.error(e.getStackTrace());
	}

    }

    /**
     * 
     * @param pool
     *            Connections pool
     */
    public void setPool(ConnectionPool pool) {
	this.pool = pool;
    }

    /**
     * Return all the registered campaigns
     * 
     * @return Vector<> Campaigns
     */
    public Vector<Campaign> getCampaigns() {
	Vector<Campaign> camps = null;
	try {
	    Connection conn = pool.getConnection().getConnection();
	    Statement st = conn.createStatement();
	    ResultSet rs = st.executeQuery(querySelectAllCampaigns);
	    camps = new Vector<Campaign>();
	    while (rs.next()) {
		// we build a campaign
		Campaign c = new Campaign();
		c.setId(rs.getInt(1));
		c.setDescription(rs.getString(2));
		c.setDateFrom(rs.getString(3).concat("/").concat(
			rs.getString(4)).concat("/").concat(rs.getString(5)));
		c.setDateTo(rs.getString(6).concat("/").concat(rs.getString(7))
			.concat("/").concat(rs.getString(8)));

		// set editors
		c.setEditors(null);

		// set subscribers
		Vector<User> subscribers = this.getSuscribers(c);
		c.setSubscribers(subscribers);

		// set news
		Vector<New> news = this.getCampaignNews(c);
		c.setNews(news);

		// we add the campaing to the list
		camps.add(c);

	    }

	    // we finish the Statement and resultSet
	    rs.close();
	    st.close();
	} catch (Exception e) {
	    logger.error(props.getProperty("campaignDB.getAllErrorMsg"));
	    logger.error(e.toString());
	    camps = null;
	}

	return camps;

    }

    /**
     * Return the campaign which identifier match the received parameter or null
     * if it does not exist in database
     * 
     * @param id
     *            Identifier
     * @return Campaign
     */
    public Campaign getCampaignById(int id) {
	Campaign camp = null;
	try {
	    Connection conn = pool.getConnection().getConnection();
	    PreparedStatement st = conn
		    .prepareStatement(querySelectCampaignById);
	    st.setInt(1, id);
	    ResultSet rs = st.executeQuery();
	    if (rs.next()) {
		// we build a campaign instance
		camp = new Campaign();
		camp.setId(rs.getInt(1));
		camp.setDescription(rs.getString(2));
		camp.setDateFrom(rs.getString(3).concat("/").concat(
			rs.getString(4)).concat("/").concat(rs.getString(5)));
		camp.setDateTo(rs.getString(6).concat("/").concat(
			rs.getString(7)).concat("/").concat(rs.getString(8)));

		// set editors
		camp.setEditors(null);

		// set subscribers
		Vector<User> subscribers = this.getSuscribers(camp);
		camp.setSubscribers(subscribers);

		// set news
		Vector<New> news = this.getCampaignNews(camp);
		camp.setNews(news);
	    }
	    rs.close();
	    st.close();
	} catch (Exception e) {
	    logger.error(props.getProperty("campaignDB.getByIdErrorMsg"));
	    logger.error(e.toString());
	    camp = null;
	}

	return camp;
    }

    /**
     * Return the registered news for a certain campaign
     * 
     * @param Campaign
     * @return Vector<New> News
     */
    public Vector<New> getCampaignNews(Campaign c) {
	Vector<New> news = null;
	try {
	    Connection conn = pool.getConnection().getConnection();
	    PreparedStatement st = conn
		    .prepareStatement(querySelectNewsFromCampaign);
	    st.setInt(1, c.getId());
	    ResultSet rs = st.executeQuery();
	    news = new Vector<New>();
	    while (rs.next()) {
		// we build each new
		New n = new New();
		n.setId(rs.getInt(1));
		n.setText(rs.getString(2));
		n.setDate(rs.getString(3).concat("/").concat(rs.getString(4))
			.concat("/").concat(rs.getString(5)));
		n.setCampaign(c);

		// we add the new to the list
		news.add(n);

	    }

	    // Statament and ResultSet are closed
	    rs.close();
	    st.close();
	} catch (Exception e) {
	    logger.error(props.getProperty("campaignDB.getNewsErrorMsg"));
	    logger.error(e.toString());
	    news = null;
	}

	return news;
    }

    /**
     * Delete the received new from backup database table. Return success code
     * o. Or other value for non-successful cases.
     * 
     * @param n
     *            New
     * @return int success code. 1 = success = 1 row deleted
     */
    public int deletebackupNew(New n) {
	int code = 1;

	try {
	    Connection conn = pool.getConnection().getConnection();
	    PreparedStatement st = conn
		    .prepareStatement(queryDeleteFromNovedadesBackup);
	    st.setInt(1, n.getId());
	    code = st.executeUpdate();
	    st.close();
	} catch (Exception e) {
	    logger.error(props.getProperty("campaignDB.deleteNewErrorMsg"));
	    e.printStackTrace();
	    logger.error(e.toString());
	    code = -1;
	}

	return code;
    }

    /**
     * Select a new from backup table
     * 
     * @return New
     */
    public New selectbackupNew() {
	New n = null;

	try {
	    Connection conn = pool.getConnection().getConnection();
	    PreparedStatement st = conn
		    .prepareStatement(querySelectFromNovedadesBackup);
	    ResultSet rs = st.executeQuery();
	    if (rs.next()) {
		n = new New();

		n.setId(rs.getInt(1));
		n.setText(rs.getString(2));
		n.setDate(rs.getString(3).concat("/").concat(rs.getString(4))
			.concat("/").concat(rs.getString(5)));
		Campaign campaign = new Campaign();
		campaign.setId(rs.getInt(6));
		n.setCampaign(campaign);

	    }
	    rs.close();
	    st.close();
	} catch (Exception e) {
	    logger.error(props.getProperty("campaignDB.selectNewErrorMsg"));
	    logger.error(e.toString());
	    n = null;
	}

	return n;
    }

    /**
     * Return all the suscriptors for a campaign.
     * 
     * @param Campaign
     * @return Vector<> Subscribers
     */
    public Vector<User> getSuscribers(Campaign c) {
	Vector<User> subs = null;
	try {
	    Connection conn = pool.getConnection().getConnection();
	    PreparedStatement st = conn
		    .prepareStatement(querySelectCampaignFollowers);
	    st.setInt(1, c.getId());
	    ResultSet rs = st.executeQuery();
	    subs = new Vector<User>();
	    while (rs.next()) {
		// we build each user
		User u = new User();

		u.setName(rs.getString(1));
		u.setSurname(rs.getString(2));
		u.setId(rs.getInt(3));
		u.setCelularPhone(rs.getString(4));

		// we add the user to the list
		subs.add(u);

	    }
	    rs.close();
	    st.close();
	} catch (Exception e) {
	    logger.error(props.getProperty("campaignDB.getSusbsErrorMsg"));
	    logger.error(e.toString());
	    subs = null;
	}

	return subs;
    }

}
