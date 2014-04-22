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
 
 */package database.objects.measure;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Properties;
import java.util.Vector;

import org.apache.log4j.Logger;

import database.connections.ConnectionPool;
import entities.measure.Measure;
import entities.measuretype.MeasureType;
import entities.user.User;

/**
 * Provide database access to registered measures
 * 
 * @version 1.0
 * @date 2010-04-06
 * @author Barbara M. Rodeker
 */
public class MeasureDB {

    /**
     * QUERY select all measures
     */
    private static String querySelectAllMeasures = "SELECT m.id, contenido, date_part('day',fecha_hora), date_part('month',fecha_hora), date_part('year',fecha_hora), id_paciente,  tipo_medicion FROM medicion m LEFT JOIN paciente p ON m.id_paciente = p.id";
    /**
     * QUERY select a measure by identifier
     */
    private static String querySelectMeasureById = "SELECT m.id, contenido, date_part('day',fecha_hora), date_part('month',fecha_hora), date_part('year',fecha_hora), id_paciente,  tipo_medicion FROM medicion m LEFT JOIN paciente p ON m.id_paciente = p.id WHERE m.id = ?";
    /**
     * QUERY select a measure by type
     */
    private static String querySelectMeasureByType = "SELECT m.id, contenido, date_part('day',fecha_hora), date_part('month',fecha_hora), date_part('year',fecha_hora), id_paciente, tipo_medicion FROM medicion m LEFT JOIN paciente p ON m.id_paciente = p.id WHERE tipo_medicion = ?";
    /**
     * QUERY select all measure for a patient
     */
    private static String querySelectPatientMeasures = "SELECT m.id, contenido, date_part('day',fecha_hora), date_part('month',fecha_hora), date_part('year',fecha_hora), id_paciente,  tipo_medicion FROM medicion m LEFT JOIN paciente p ON m.id_paciente = p.id WHERE p.id = ?";
    /**
     * QUERY insert a measure
     */
    private static String queryInsertMeasure = "INSERT INTO medicion (contenido,id_paciente,tipo_medicion,fecha_hora) VALUES (?,?,?,?); COMMIT";

    /**
     * Properties and messages file
     */
    private Properties props = null;

    /**
     * Logger
     */
    private static Logger logger = Logger.getLogger(MeasureDB.class);

    /**
     * Pool de conexiones
     */
    ConnectionPool pool = null;

    public MeasureDB() {
	loadProperties();
	pool = new ConnectionPool();

    }

    /**
     * 
     * Constructor
     * 
     * @param ConnectionPool
     *            pool
     */
    public MeasureDB(ConnectionPool pool) {
	loadProperties();
	this.pool = pool;
    }

    /**
     * Properties and messages are loaded
     * 
     */
    private void loadProperties() {

	// we load the properties and messages
	props = new Properties();
	try {
	    props
		    .load(MeasureDB.class
			    .getResourceAsStream("/database/objects/measure/resources.properties"));
	} catch (IOException e) {
	    logger.error("Error cargando propiedades y recursos");
	    logger.error(e.toString());
	}

    }

    /**
     * 
     * @param pool
     *            Pool
     */
    public void setPool(ConnectionPool pool) {
	this.pool = pool;
    }

    /**
     * Insert a new measure
     * 
     * @param m
     *            Measure to insert
     * @return success code. 1 = success = 1 row inserted
     */
    public int insertMeasure(Measure m) {
	int code = 1;

	try {
	    Connection conn = pool.getConnection().getConnection();
	    PreparedStatement st = conn.prepareStatement(queryInsertMeasure);

	    st.setString(1, m.getText());
	    st.setInt(2, m.getPatient().getId());
	    st.setInt(3, m.getMt().getId());
	    st.setTimestamp(4, Timestamp.valueOf(m.getDate()));

	    code = st.executeUpdate();

	    st.close();
	} catch (Exception e) {
	    logger.error(props.getProperty("measureDB.InsertErrorMsg"));
	    logger.error(e.toString());
	    code = -1;
	}

	return code;

    }

    /**
     * Select all measures
     * 
     * @return Vector<Measure>
     */
    public Vector<Measure> getMeasures() {
	Vector<Measure> measures = null;
	try {
	    Connection conn = pool.getConnection().getConnection();
	    Statement st = conn.createStatement();
	    ResultSet rs = st.executeQuery(querySelectAllMeasures);
	    measures = new Vector<Measure>();
	    while (rs.next()) {
		// we build a measure
		Measure m = new Measure();

		m.setId(rs.getInt(1));
		m.setText(rs.getString(2));
		m.setDate(rs.getString(3).concat("/").concat(rs.getString(4))
			.concat("/").concat(rs.getString(5)));

		User p = new User();
		p.setId(rs.getInt(6));
		m.setPatient(p);

		MeasureType mt = new MeasureType();
		mt.setId(rs.getInt(7));
		m.setMt(mt);

		// we ad the measure to the list
		measures.add(m);

	    }

	    // we finish the Statement and resultSet
	    rs.close();
	    st.close();
	} catch (Exception e) {
	    logger.error(props.getProperty("measureDB.getAllErrorMsg"));
	    logger.error(e.toString());
	    measures = null;
	}

	return measures;
    }

    /**
     * Get a measure which match with the parameter identifier.
     * 
     * @param id
     *            Identifier
     * @return Measure
     */
    public Measure getMeasureById(int id) {
	Measure measure = null;
	try {
	    Connection conn = pool.getConnection().getConnection();
	    PreparedStatement st = conn
		    .prepareStatement(querySelectMeasureById);
	    st.setInt(1, id);
	    ResultSet rs = st.executeQuery();
	    if (rs.next()) {
		// we build a measure instance
		measure = new Measure();

		measure.setId(rs.getInt(1));
		measure.setText(rs.getString(2));
		measure.setDate(rs.getString(3).concat("/").concat(
			rs.getString(4)).concat("/").concat(rs.getString(5)));

		User p = new User();
		p.setId(rs.getInt(6));
		measure.setPatient(p);

		MeasureType mt = new MeasureType();
		mt.setId(rs.getInt(7));
		measure.setMt(mt);

	    }
	    rs.close();
	    st.close();
	} catch (Exception e) {
	    logger.error(props.getProperty("measureDB.getByIdErrorMsg"));
	    logger.error(e.toString());
	    measure = null;
	}

	return measure;
    }

    /**
     * Get all measures registered by a patient
     * 
     * @param Patient
     * @return Vector<Measure>
     */
    public Vector<Measure> getPatientMeasures(User p) {
	Vector<Measure> measures = null;
	try {
	    Connection conn = pool.getConnection().getConnection();
	    PreparedStatement st = conn
		    .prepareStatement(querySelectPatientMeasures);
	    st.setInt(1, p.getId());
	    ResultSet rs = st.executeQuery();
	    measures = new Vector<Measure>();
	    if (rs.next()) {
		// we build a measure instance
		Measure measure = new Measure();

		measure.setId(rs.getInt(1));
		measure.setText(rs.getString(2));
		measure.setDate(rs.getString(3).concat("/").concat(
			rs.getString(4)).concat("/").concat(rs.getString(5)));

		measure.setPatient(p);

		MeasureType mt = new MeasureType();
		mt.setId(rs.getInt(7));
		measure.setMt(mt);

		// we add a new Measure to the list
		measures.add(measure);

	    }
	    rs.close();
	    st.close();
	} catch (Exception e) {
	    logger.error(props.getProperty("measureDB.getByPatientErrorMsg"));
	    logger.error(e.toString());
	    measures = null;
	}

	return measures;
    }

    /**
     * Get a measure by type
     * 
     * @param mt
     *            MeasureType
     * @return Measure
     */
    public Measure getMeasureByType(MeasureType mt) {
	Measure measure = null;
	try {
	    Connection conn = pool.getConnection().getConnection();
	    PreparedStatement st = conn
		    .prepareStatement(querySelectMeasureByType);
	    st.setInt(1, mt.getId());
	    ResultSet rs = st.executeQuery();
	    if (rs.next()) {
		// we build the measure instance
		measure = new Measure();

		measure.setId(rs.getInt(1));
		measure.setText(rs.getString(2));
		measure.setDate(rs.getString(3).concat("/").concat(
			rs.getString(4)).concat("/").concat(rs.getString(5)));

		User p = new User();
		p.setId(rs.getInt(6));
		measure.setPatient(p);

		measure.setMt(mt);

	    }
	    rs.close();
	    st.close();
	} catch (Exception e) {
	    logger.error(props.getProperty("measureDB.getByTypeErrorMsg"));
	    logger.error(e.toString());
	    measure = null;
	}

	return measure;
    }

}
