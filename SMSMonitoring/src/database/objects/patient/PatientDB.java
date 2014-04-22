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
package database.objects.patient;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.util.Vector;

import org.apache.log4j.Logger;

import database.connections.ConnectionPool;
import entities.user.User;

/**
 * Provide access to patient table
 * 
 * @version 1.0
 * @date 2010-04-06
 * @author Barbara M. Rodeker
 */
public class PatientDB {

    /**
     * QUERY select all patients
     */
    private static String querySelectAllPatients = "SELECT id,nombre, apellido, telefono, celular FROM usuario";
    /**
     * QUERY select a patient by identifier
     */
    private static String querySelectPatientById = "SELECT id, nombre, apellido, telefono, celular FROM usuario WHERE id = ?";
    /**
     * QUERY select a patient by cel
     */
    private static String querySelectPatientByCel = "SELECT id, nombre, apellido, telefono, celular FROM usuario WHERE POSITION(celular IN ?) > 0";

    /**
     * Properties and messages file
     */
    private Properties props = null;

    /**
     * Logger
     */
    private static Logger logger = Logger.getLogger(PatientDB.class);

    /**
     * Connections pool
     */
    ConnectionPool pool = null;

    /**
     * 
     * Constructor
     */
    public PatientDB() {
	loadProperties();
	pool = new ConnectionPool();
    }
    
    public PatientDB(ConnectionPool pool){
	loadProperties();	
	this.pool = pool;
    }

    /**
     * Loads properties and messages
     * 
     */
    private void loadProperties() {

	props = new Properties();
	try {
	    props
		    .load(PatientDB.class
			    .getResourceAsStream("/database/objects/patient/resources.properties"));
	} catch (IOException e) {
	    logger.error("Error cargando propiedades y recursos");
	    logger.error(e.toString());
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
     * Get all patients
     * 
     * @return Vector<Patient> Patients
     */
    public Vector<User> getPatients() {
	Vector<User> patients = null;
	try {
	    Connection conn = pool.getConnection().getConnection();
	    Statement st = conn.createStatement();
	    ResultSet rs = st.executeQuery(querySelectAllPatients);
	    patients = new Vector<User>();
	    while (rs.next()) {
		// we build a patient
		User pac = new User();

		pac.setId(rs.getInt(1));
		pac.setName(rs.getString(2));
		pac.setSurname(rs.getString(3));
		pac.setFirstPhone(rs.getString(4));
		pac.setCelularPhone(rs.getString(5));

		// we ad the patient to the list
		patients.add(pac);

	    }

	    // we finish the Statement and resultSet
	    rs.close();
	    st.close();
	} catch (Exception e) {
	    logger.error(props.getProperty("patientDB.getAllErrorMsg"));
	    logger.error(e.toString());
	    patients = null;
	}

	return patients;
    }

    /**
     * Get a patient that matches with the identifier paramter
     * 
     * @param id
     *            Identifier
     * @return Patient
     */
    public User getPatientById(int id) {
	User patient = null;
	try {
	    Connection conn = pool.getConnection().getConnection();
	    PreparedStatement st = conn
		    .prepareStatement(querySelectPatientById);
	    st.setInt(1, id);
	    ResultSet rs = st.executeQuery();
	    if (rs.next()) {
		// we build a patient instance
		patient = new User();

		patient.setId(rs.getInt(1));
		patient.setName(rs.getString(2));
		patient.setSurname(rs.getString(3));
		patient.setFirstPhone(rs.getString(4));
		patient.setCelularPhone(rs.getString(5));

	    }
	    rs.close();
	    st.close();
	} catch (Exception e) {
	    logger.error(props.getProperty("patientDB.getByIdErrorMsg"));
	    logger.error(e.toString());
	    patient = null;
	}

	return patient;
    }

    /**
     * Get a patient that matches with the cellular parameter
     * 
     * @param cel
     *            Cellular phone
     * @return Patient
     */
    public User getPatientByCel(String cel) {
	User patient = null;
	try {
	    Connection conn = pool.getConnection().getConnection();
	    PreparedStatement st = conn
		    .prepareStatement(querySelectPatientByCel);
	    st.setString(1, cel);
	    ResultSet rs = st.executeQuery();
	    if (rs.next()) {
		// we build a patient instance
		patient = new User();

		patient.setId(rs.getInt(1));
		patient.setName(rs.getString(2));
		patient.setSurname(rs.getString(3));
		patient.setFirstPhone(rs.getString(4));
		patient.setCelularPhone(rs.getString(5));

	    }
	    rs.close();
	    st.close();
	} catch (Exception e) {
	    logger.error(props.getProperty("patientDB.getByCelErrorMsg"));
	    logger.error(e.toString());
	    patient = null;
	}

	return patient;
    }
}
