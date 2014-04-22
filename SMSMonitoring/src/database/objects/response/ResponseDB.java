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
package database.objects.response;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.util.Vector;

import org.apache.log4j.Logger;

import database.connections.ConnectionPool;
import database.objects.patient.PatientDB;
import entities.doctor.Doctor;
import entities.response.Response;
import entities.user.User;

/**
 * Provide access to response database table
 * 
 * @version 1.0
 * @date 2010-04-06
 * @author Barbara M. Rodeker
 */
public class ResponseDB {

    /**
     * QUERY select all response
     */
    private static String querySelectAllResponses = "SELECT rm.id, cantidad_avisos, enviado, respuesta, date_part('day',fecha_hora_respuesta), date_part('month',fecha_hora_respuesta), date_part('year',fecha_hora_respuesta), id_paciente, id_medico FROM medicion_respuesta rm LEFT JOIN paciente p ON rm.id_paciente = p.id LEFT JOIN medico med ON rm.id_medico = rm.id  ";
    /**
     * QUERY select all response for a patient
     */
    private static String querySelectResponsesByPatient = "SELECT rm.id, cantidad_avisos, enviado, respuesta, date_part('day',fecha_hora_respuesta), date_part('month',fecha_hora_respuesta), date_part('year',fecha_hora_respuesta), id_paciente, id_medico FROM medicion_respuesta rm LEFT JOIN paciente p ON rm.id_paciente = p.id LEFT JOIN medico med ON rm.id_medico = rm.id  WHERE id_paciente = ?";
    /**
     * QUERY select all response registered by a doctor
     */
    private static String querySelectResponsesByDoctor = "SELECT rm.id, cantidad_avisos, enviado, respuesta, date_part('day',fecha_hora_respuesta), date_part('month',fecha_hora_respuesta), date_part('year',fecha_hora_respuesta), id_paciente, id_medico FROM medicion_respuesta rm LEFT JOIN paciente p ON rm.id_paciente = p.id LEFT JOIN medico med ON rm.id_medico = rm.id  WHERE id_medico = ?";
    /**
     * QUERY delete a response from the response backup table
     */
    private static String queryDeleteFromRespuestasBackup = "DELETE FROM medicion_respuesta_backup where id = ?";
    /**
     * QUERY select a response from backup table
     */
    private static String querySelectFromRespuestasBackup = "SELECT id, cantidad_avisos, enviado, respuesta, date_part('day',fecha_hora_respuesta), date_part('month',fecha_hora_respuesta), date_part('year',fecha_hora_respuesta), id_paciente, id_medico FROM medicion_respuesta_backup ORDER BY id LIMIT 1";
    
    /**
     * UserDB
     */
    private PatientDB patientDB = null;

    /**
     * Properties and messages file
     */
    private Properties props = null;

    /**
     * Logger
     */

    private static Logger logger = Logger.getLogger(ResponseDB.class);

    /**
     * Pool de conexiones
     */
    ConnectionPool pool = null;

    /**
     * 
     * Constructor
     */
    public ResponseDB() {
	loadProperties();
	pool = new ConnectionPool();
	patientDB = new PatientDB(this.pool);
    }
    
    /**
     * 
     * Constructor
     * 
     * @param ConnectionPool pool 
     */
    public ResponseDB(ConnectionPool pool){
	loadProperties();
	this.pool = pool;
	patientDB = new PatientDB(this.pool);
    }

    /**
     * Loads properties and message file
     * 
     */
    private void loadProperties() {

	props = new Properties();
	try {
	    props
		    .load(ResponseDB.class
			    .getResourceAsStream("/database/objects/response/resources.properties"));
	} catch (IOException e) {
	    logger.error("Error cargando propiedades y recursos");
	    logger.error(e.toString());
	}

    }
    /**
     * 
     * @param pool Connections pool
     */
    public void setPool(ConnectionPool pool) {
        this.pool = pool;
    }
    
    /**
     * Select all registered response
     * 
     * @return Vector<Response>
     */
    public Vector<Response> getResponses(){
	Vector<Response> responses = null;
	try {
	    Connection conn = pool.getConnection().getConnection();
	    Statement st = conn.createStatement();
	    ResultSet rs = st.executeQuery(querySelectAllResponses);
	    responses = new Vector<Response>();
	    while (rs.next()) {
		//we build a response
		Response resp = new Response();

		resp.setId(rs.getInt(1));
		resp.setNotifications(rs.getInt(2));
		resp.setSent(rs.getBoolean(3));
		resp.setResponse(rs.getString(4));
		resp.setDate(rs.getString(5).concat("/").concat(rs.getString(6)).concat("/").concat(rs.getString(7)));
		
		User p = new User();
		p.setId(rs.getInt(8));
		resp.setPatient(p);
		
		Doctor doc = new Doctor();
		doc.setId(rs.getInt(9));
		resp.setDoctor(doc);
		
		//we ad the response to the list
		responses.add(resp);
		
	    }
	    
	    //we finish the Statement and resultSet
	    rs.close();
	    st.close();
	} catch (Exception e) {
	    logger.error(props.getProperty("responseDB.getAllErrorMsg"));
	    logger.error(e.toString());
	    responses = null;
	}

	return responses;
    }
    
    /**
     * Return all response sent to a patient
     * 
     * @return Vector<Response>
     */
    public Vector<Response> getPatientResponse(User p){
	Vector<Response> responses = null;
	try {
	    Connection conn = pool.getConnection().getConnection();
	    PreparedStatement st = conn
		    .prepareStatement(querySelectResponsesByPatient);
	    st.setInt(1, p.getId());
	    ResultSet rs = st.executeQuery();
	    responses = new Vector<Response>();
	    if (rs.next()) {
		//we build a response
		Response resp = new Response();

		resp.setId(rs.getInt(1));
		resp.setNotifications(rs.getInt(2));
		resp.setSent(rs.getBoolean(3));
		resp.setResponse(rs.getString(4));
		resp.setDate(rs.getString(5).concat("/").concat(rs.getString(6)).concat("/").concat(rs.getString(7)));
		
		resp.setPatient(p);
		
		Doctor doc = new Doctor();
		doc.setId(rs.getInt(9));
		resp.setDoctor(doc);
		
		//we ad the response to the list
		responses.add(resp);
				
	    }
	    rs.close();
	    st.close();
	} catch (Exception e) {
	    logger.error(props.getProperty("responseDB.getByPatientErrorMsg"));
	    logger.error(e.toString());
	    responses = null;
	}

	return responses;
    }
    
    /**
     * Return all response sent by a doctor
     * 
     * @return Vector<Response>
     */
    public Vector<Response> getDoctorResponse(Doctor d){
	Vector<Response> responses = null;
	try {
	    Connection conn = pool.getConnection().getConnection();
	    PreparedStatement st = conn
		    .prepareStatement(querySelectResponsesByDoctor);
	    st.setInt(1, d.getId());
	    ResultSet rs = st.executeQuery();
	    responses = new Vector<Response>();
	    if (rs.next()) {
		//we build a response
		Response resp = new Response();

		resp.setId(rs.getInt(1));
		resp.setNotifications(rs.getInt(2));
		resp.setSent(rs.getBoolean(3));
		resp.setResponse(rs.getString(4));
		resp.setDate(rs.getString(5).concat("/").concat(rs.getString(6)).concat("/").concat(rs.getString(7)));
		
		User p = new User();
		p.setId(rs.getInt(8));
		resp.setPatient(p);
		
		resp.setDoctor(d);
		
		//we ad the response to the list
		responses.add(resp);
				
	    }
	    rs.close();
	    st.close();
	} catch (Exception e) {
	    logger.error(props.getProperty("responseDB.getByDoctorErrorMsg"));
	    logger.error(e.toString());
	    responses = null;
	}

	return responses;
    }
    
    /**
     * Return a backup from response table
     * 
     * @return Response
     */
    public Response getBackupResponse(){
	Response resp = null;

	try {
	    Connection conn = pool.getConnection().getConnection();
	    PreparedStatement st = conn
		    .prepareStatement(querySelectFromRespuestasBackup);
	    ResultSet rs = st.executeQuery();
	    if (rs.next()) {
		resp = new Response();

		
		resp.setId(rs.getInt(1));
		resp.setNotifications(rs.getInt(2));
		resp.setSent(rs.getBoolean(3));
		resp.setResponse(rs.getString(4));
		resp.setDate(rs.getString(5).concat("/").concat(rs.getString(6)).concat("/").concat(rs.getString(7)));
		
		resp.setPatient(patientDB.getPatientById(rs.getInt(8)));
		
		Doctor doc = new Doctor();
		doc.setId(rs.getInt(9));
		resp.setDoctor(doc);		
		
	    }
	    rs.close();
	    st.close();
	} catch (Exception e) {
	    logger.error(props.getProperty("responseDB.selectResponseErrorMsg"));
	    logger.error(e.toString());
	    resp = null;
	}

	return resp;
    }
    
    /**
     * Delete a response from backup table
     * 
     * @param res Response to delete
     * @return int succes code. 1 = success
     */
    public int deleteBackupResponse(Response res){
	int code = 1;

	try {
	    Connection conn = pool.getConnection().getConnection();
	    PreparedStatement st = conn
		    .prepareStatement(queryDeleteFromRespuestasBackup);
	    st.setInt(1, res.getId());

	    code = st.executeUpdate();
	    
	    st.close();
	} catch (Exception e) {
	    logger.error(props.getProperty("responseDB.deleteResponseErrorMsg"));
	    logger.error(e.toString());
	    code = -1;
	}

	return code;
    }
}
