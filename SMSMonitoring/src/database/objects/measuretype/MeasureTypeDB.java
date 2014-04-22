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
package database.objects.measuretype;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.util.Vector;

import org.apache.log4j.Logger;

import database.connections.ConnectionPool;
import database.objects.category.CategoryDB;
import entities.measuretype.MeasureType;

/**
 * Provide access to measure type table in database
 * 
 * @version 1.0
 * @date 2010-04-06
 * @author Barbara M. Rodeker
 */
public class MeasureTypeDB {

    /**
     * QUERY select all measures types
     */
    private static String querySelectAllMeasureTypes = "SELECT valor, descripcion, id, id_categoria FROM tipo_medicion";
    /**
     * QUERY select a measure type by identifier
     */
    private static String querySelectMeasureTypesById = "SELECT valor, descripcion, id, id_categoria FROM tipo_medicion WHERE id = ?";
    /**
     * QUERY select a measure type by value
     */
    private static String querySelectMeasureTypesByValue = "SELECT valor, descripcion, id, id_categoria FROM tipo_medicion WHERE valor ILIKE ?";
    /**
     * Parameter database access object
     */
    CategoryDB categoryDB = null;

    /**
     * Properties and messages file
     */
   private Properties props = null;

    /**
     * Logger
     */
    private static Logger logger = Logger.getLogger(MeasureTypeDB.class);

    /**
     * Connections pool
     */
    ConnectionPool pool = null;

    public MeasureTypeDB() {
	loadProperties();
	pool = new ConnectionPool();
	categoryDB = new CategoryDB(pool);
    }
    
    public MeasureTypeDB(ConnectionPool pool){
	loadProperties();
	this.pool = pool;
	categoryDB = new CategoryDB(pool);
    }

    /**
     * Loads properties and message file
     * 
     */
    private void loadProperties() {

	props = new Properties();
	try {
	    props
		    .load(MeasureTypeDB.class
			    .getResourceAsStream("/database/objects/measuretype/resources.properties"));
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
     * 
     * @param CategoryDB Category database object
     */
    public void setCategoryDB(CategoryDB categoryDB) {
        this.categoryDB = categoryDB;
    }

    /**
     * Return all the registered measure types 
     * 
     * @return Vector<MeasureType>
     */
    public Vector<MeasureType> getMeasureTypes(){
	Vector<MeasureType> measuresT = null;
	try {
	    Connection conn = pool.getConnection().getConnection();
	    Statement st = conn.createStatement();
	    ResultSet rs = st.executeQuery(querySelectAllMeasureTypes);
	    measuresT = new Vector<MeasureType>();
	    while (rs.next()) {
		//we build a measure type
		MeasureType m = new MeasureType();

		m.setValue(rs.getString(1));
		m.setDescription(rs.getString(2));
		m.setId(rs.getInt(3));
		
		m.setCategory(categoryDB.getCategory(rs.getInt(4)));
		
		//we ad the type measure to the list
		measuresT.add(m);
		
	    }
	    
	    //we finish the Statement and resultSet
	    rs.close();
	    st.close();
	} catch (Exception e) {
	    logger.error(props.getProperty("measureTypeDB.getAllErrorMsg"));
	    logger.error(e.toString());
	    measuresT = null;
	}

	return measuresT;
    }
    
    /**
     * Return a type that matches with the parameter
     * 
     * @param id Identifier
     * @return
     */
    public MeasureType getMeasureTypeById(int id){
	MeasureType measure = null;
	try {
	    Connection conn = pool.getConnection().getConnection();
	    PreparedStatement st = conn
		    .prepareStatement(querySelectMeasureTypesById);
	    st.setInt(1, id);
	    ResultSet rs = st.executeQuery();
	    if (rs.next()) {
		//we build a measure type instance
		measure = new MeasureType();
		
		measure.setValue(rs.getString(1));
		measure.setDescription(rs.getString(2));
		measure.setId(rs.getInt(3));
		
		measure.setCategory(categoryDB.getCategory(rs.getInt(4)));
		
	    }
	    rs.close();
	    st.close();
	} catch (Exception e) {
	    logger.error(props.getProperty("measureTypeDB.getByIdErrorMsg"));
	    logger.error(e.toString());
	    measure = null;
	}

	return measure;	
    }
    
    /**
     * Return a measure type that matches with the parameter
     * 
     * @param value Value
     * @return MeasureType
     */
    public MeasureType getMeasureTypeByValue(String value){
	MeasureType measure = null;
	try {
	    Connection conn = pool.getConnection().getConnection();
	    PreparedStatement st = conn
		    .prepareStatement(querySelectMeasureTypesByValue);
	    st.setString(1, value);
	    ResultSet rs = st.executeQuery();
	    if (rs.next()) {
		//we build a measure type instance
		measure = new MeasureType();
		
		measure.setValue(rs.getString(1));
		measure.setDescription(rs.getString(2));
		measure.setId(rs.getInt(3));
		
		measure.setCategory(categoryDB.getCategory(rs.getInt(4)));
		
	    }
	    rs.close();
	    st.close();
	} catch (Exception e) {
	    logger.error(props.getProperty("measureTypeDB.getByValueErrorMsg"));
	    logger.error(e.toString());
	    measure = null;
	}

	return measure;	
    }
    
    
    
    
}
