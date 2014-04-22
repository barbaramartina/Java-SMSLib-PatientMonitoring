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
package entities.measuretype;

import entities.category.Category;

/**
 * Information and functions related to types of measures registered
 * 
 * @version 1.0
 * @date 2010-04-06
 * @author Barbara M. Rodeker
 */
public class MeasureType {

    /**
     * Identifier
     */
    private int id = -1;
 
    /**
     * Value
     */
    private String value = "";
    /**
     * Description
     */
    private String description = "";
    /**
     * Category of parameters    
     */
    private Category category = null;
    
    /**
     * 
     * Constructor
     */
    public MeasureType(){
	
	
    }
    
    /**
     * 
     * Constructor
     * 
     * @param int Identifier
     * @param String Value
     * @param String Description
     * @param Vector Parameters
     * 
     */
    public MeasureType(int id, String val, String descr, Category cat){
	this.id = id;
	this.value = val;
	this.description = descr;
	this.category = cat;
	
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
     * @return String Value
     */
    public String getValue() {
        return value;
    }

    /**
     * 
     * @return String Description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 
     * @return Category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * 
     * @param category Category
     */
    public void setCategory(Category category) {
        this.category = category;
    }


}
