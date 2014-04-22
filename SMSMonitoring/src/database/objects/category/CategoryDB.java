package database.objects.category;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import org.apache.log4j.Logger;

import database.connections.ConnectionPool;
import database.objects.measuretype.MeasureTypeDB;
import database.objects.parameter.ParameterDB;
import entities.category.Category;



public class CategoryDB {
    
    
    /**
     * QUERY select a category by identifier
     */
    private static String querySelectCategoryById = "SELECT id, nombre FROM categoria_medicion WHERE id = ?";
    
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
    
    /**
     * Connection to Parameters DB
     */
    ParameterDB parameterDB = null;
    
    

    /**
     * Constructor
     */
    public CategoryDB() {
	loadProperties();
	this.pool = new ConnectionPool();
	this.parameterDB = new ParameterDB(this.pool);
    }

    /**
     * Constructor
     */
    public CategoryDB(ConnectionPool pool) {
	loadProperties();
	this.pool = pool;
	this.parameterDB = new ParameterDB(this.pool);
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
			    .getResourceAsStream("/database/objects/category/resources.properties"));
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
     * Return the category object for the id parameter
     * 
     * @param categoryId Identifier of the category
     * @return Category object
     */
    public Category getCategory(int categoryId) {
	Category category = null;
	
	try {
	    Connection conn = pool.getConnection().getConnection();
	    PreparedStatement st = conn
		    .prepareStatement(querySelectCategoryById);
	    st.setInt(1, categoryId);
	    ResultSet rs = st.executeQuery();
	    category = new Category();
	    if (rs.next()) {
		//we build the Category
		category = new Category();
		
		category.setId(rs.getInt(1));
		category.setName(rs.getString(2));
		category.setParameters(parameterDB.getParameters(category));
		
	    }
	    
	    //we finish the Statement and resultSet
	    rs.close();
	    st.close();
	} catch (Exception e) {
	    logger.error(props.getProperty("categoryDB.getCategoryById"));
	    logger.error(e.toString());
	    
	}

	return category;
	
    }
    
}
