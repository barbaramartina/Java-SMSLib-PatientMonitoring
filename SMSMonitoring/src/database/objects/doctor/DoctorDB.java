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
 
 */package database.objects.doctor;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.util.Vector;

import org.apache.log4j.Logger;

import database.connections.ConnectionPool;
import entities.doctor.Doctor;

/**
 * Provee acceso a la base de datos de médicos
 * 
 * @version 1.0
 * @date 2010-04-06
 * @author Barbara M. Rodeker
 */
public class DoctorDB {

    /**
     * QUERY select all doctors
     */
    private static String querySelectAllDoctors = "SELECT nombre, apellido, matricula, institucion, especialidad, id FROM medico";
    /**
     * QUERY select a doctor by identifier
     */
    private static String querySelectDoctorById = "SELECT nombre, apellido, matricula, institucion, especialidad, id FROM medico WHERE id = ?";

    /**
     * Properties and messages file
     */
    private Properties props = null;

    /**
     * Logger
     */
    private static Logger logger = Logger.getLogger(DoctorDB.class);

    /**
     * Connections pool
     */
    ConnectionPool pool = null;

    /**
     * 
     * Constructor
     */
    public DoctorDB() {
	loadProperties();
	pool = new ConnectionPool();

    }

    /**
     * 
     * Constructor
     * 
     * @param pool
     */
    public DoctorDB(ConnectionPool pool) {
	loadProperties();
	this.pool = pool;

    }

    /**
     * Properties and messages are loaded
     * 
     */
    private void loadProperties() {

	// we load the properties
	props = new Properties();
	try {
	    props
		    .load(DoctorDB.class
			    .getResourceAsStream("/database/objects/doctor/resources.properties"));
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
     * Select all registered doctors
     * 
     * @return Vector<Doctor>
     */
    public Vector<Doctor> getDoctors() {
	Vector<Doctor> doctors = null;

	try {
	    Connection conn = pool.getConnection().getConnection();
	    Statement st = conn.createStatement();
	    ResultSet rs = st.executeQuery(querySelectAllDoctors);
	    doctors = new Vector<Doctor>();
	    while (rs.next()) {
		// we build a doctor
		Doctor doc = new Doctor();

		doc.setName(rs.getString(1));
		doc.setSurname(rs.getString(2));
		doc.setRegistration(rs.getString(3));
		doc.setInstitution(rs.getInt(4));
		doc.setSpecialty(rs.getInt(5));
		doc.setId(rs.getInt(6));

		// we ass the doctor to the list
		doctors.add(doc);

	    }

	    // we finish the Statement and resultSet
	    rs.close();
	    st.close();
	} catch (Exception e) {
	    logger.error(props.getProperty("doctorDB.getAllErrorMsg"));
	    logger.error(e.toString());
	    doctors = null;
	}

	return doctors;

    }

    /**
     * Return a doctor according to his id
     * 
     * @param id
     *            Identifier
     * @return Doctor
     */
    public Doctor getDoctorById(int id) {
	Doctor doc = null;
	try {
	    Connection conn = pool.getConnection().getConnection();
	    PreparedStatement st = conn.prepareStatement(querySelectDoctorById);
	    st.setInt(1, id);
	    ResultSet rs = st.executeQuery();
	    if (rs.next()) {
		// we build a doctor instance
		doc = new Doctor();

		doc.setName(rs.getString(1));
		doc.setSurname(rs.getString(2));
		doc.setRegistration(rs.getString(3));
		doc.setInstitution(rs.getInt(4));
		doc.setSpecialty(rs.getInt(5));
		doc.setId(rs.getInt(6));

	    }
	    rs.close();
	    st.close();
	} catch (Exception e) {
	    logger.error(props.getProperty("doctorDB.getByIdErrorMsg"));
	    logger.error(e.toString());
	    doc = null;
	}

	return doc;
    }

}
