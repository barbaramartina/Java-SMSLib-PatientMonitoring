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
package database.objects.parameter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.Vector;

import org.apache.log4j.Logger;

import database.connections.ConnectionPool;
import entities.category.Category;
import entities.parameter.Parameter;

/**
 * Provide access to measure type parameters
 * 
 * @version 1.0
 * @date 2010-04-06
 * @author Barbara M. Rodeker
 */
public class ParameterDB {

    /**
     * QUERY select all parameter for a measure type
     */
    private static String querySelectParametersForMeasure = "SELECT descripcion, validacion, id_categoria, id, valor_minimo, valor_maximo FROM parametro_categoria_medicion where id_categoria = ? order by posicion";

    /**
     * Properties and messages file
     */
   private Properties props = null;

    /**
     * Logger
     */
    private static Logger logger = Logger.getLogger(ParameterDB.class);

    /**
     * Connections pool
     */
    ConnectionPool pool = null;

    /**
     * 
     * Constructor
     */
    public ParameterDB() {
	loadProperties();
	pool = new ConnectionPool();
    }
    
    /**
     * 
     * Constructor
     * 
     * @param ConnectionPool
     */
    public ParameterDB(ConnectionPool pool){
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
		    .load(ParameterDB.class
			    .getResourceAsStream("/database/objects/parameter/resources.properties"));
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
     * Select all parameters registered for a category of measure
     * 
     * @param cat Category
     * @return Vector<Parameter>
     */
    public Vector<Parameter> getParameters(Category cat){
	Vector<Parameter> parameters = null;
	try {
	    Connection conn = pool.getConnection().getConnection();
	    PreparedStatement st = conn
		    .prepareStatement(querySelectParametersForMeasure);
	    st.setInt(1, cat.getId());
	    ResultSet rs = st.executeQuery();
	    parameters = new Vector<Parameter>();
	    while (rs.next()) {
		//we build a Parameter
		Parameter p = new Parameter();
		
		p.setDescription(rs.getString(1));
		p.setValidation(rs.getString(2));
		p.setCategory(cat);
		p.setId(rs.getInt(4));
		p.setVal_min(rs.getFloat(5));
		p.setVal_max(rs.getFloat(6));
		
		//we ad the parameter to the list
		parameters.add(p);
		
	    }
	    
	    //we finish the Statement and resultSet
	    rs.close();
	    st.close();
	} catch (Exception e) {
	    logger.error(props.getProperty("parameterDB.getParameters"));
	    logger.error(e.toString());
	    parameters = null;
	}

	return parameters;
    }
    
}
