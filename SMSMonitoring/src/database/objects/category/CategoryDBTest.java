package database.objects.category;

import java.util.Enumeration;

import database.connections.ConnectionPool;
import entities.category.Category;
import entities.parameter.Parameter;
import junit.framework.TestCase;

public class CategoryDBTest extends TestCase {

    public void testGetCategory() {
	ConnectionPool pool = new ConnectionPool();
	CategoryDB categoryDB = new CategoryDB(pool);
	
	Category category = categoryDB.getCategory(2);
	
	assertNotNull(category);
	
	System.out.println(category.getId());
	System.out.println(category.getName());
	
	for (Enumeration<Parameter> e = category.getParameters().elements(); e.hasMoreElements(); ){
	    Parameter param = (Parameter)e.nextElement();
	    System.out.println("Parametro: "+param.getId()+param.getDescription());	    
	}

    }

}
