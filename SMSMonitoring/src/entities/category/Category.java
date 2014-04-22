package entities.category;

import java.util.Vector;

import entities.parameter.Parameter;

public class Category {
    
    /**
     * Identifier 
     */
    private int id = -1;
    /**
     * Name
     */
    private String name = "";
    
    /**
     * Parameters of the measure category
     */
    private Vector<Parameter> parameters;

    
    /**
     * Constructor
     */
    public Category() {

    }
    
    /**
     * Constructor
     */
    public Category(int id, String name) {
	this.id = id;
	this.name = name;
    }

    /**
     * Constructor
     */
    public Category(int id, String name, Vector<Parameter> params) {
	this.id = id;
	this.name = name;
	this.parameters = params;
    }

    /**
     * 
     * @return int Identifier
     */
    public int getId() {
        return id;
    }

    /**
     * 
     * @param id int identifier
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 
     * @return name String
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name String 
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return parameter Vector of Parameter
     */
    public Vector<Parameter> getParameters() {
        return parameters;
    }

    /**
     * 
     * @param parameters Vector<Parameter>
     */
    public void setParameters(Vector<Parameter> parameters) {
        this.parameters = parameters;
    }
    
    
        

}
