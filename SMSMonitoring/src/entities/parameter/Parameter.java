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
package entities.parameter;

import entities.category.Category;

/**
 * Information and functions related to parameters 
 * 
 * @version 1.0
 * @date 2010-04-06
 * @author Barbara M. Rodeker
 */
public class Parameter {

    /**
     * Description
     */
    private String description = "";
    /**
     * Validation text
     */
    private String validation = "";
    /**
     * Measure type 
     */
    private Category category = null;
    /**
     * Database identifier
     */
    private int id = -1;
    /**
     * Minimun value
     */
    private float val_min = 0;
    /**
     * Maximun value
     */
    private float val_max = 0;

    /**
     * 
     * Constructor
     */
    public Parameter() {

    }

    /**
     * 
     * Constructor
     * 
     * @param String
     *            Description
     * @param String
     *            Validation text
     * @param Category
     *            Measure Category
     * @param int Identifier
     * @param float Minimum value
     * @param float Maximum value
     * 
     */
    public Parameter(String descr, String val, Category cat, int id,
	    float valMin, float valMax) {
	this.description = descr;
	this.validation = val;
	this.category = cat;
	this.id = id;
	this.val_min = valMin;
	this.val_max = valMax;
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
     * @return String Validation text
     */
    public String getValidation() {
	return validation;
    }

    /**
     * 
     * @return Category of measure
     */
    public Category getCategory() {
	return category;
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
     * @return float Minimum value
     */
    public float getVal_min() {
	return val_min;
    }

    /**
     * 
     * @return float Maximum value
     */
    public float getVal_max() {
	return val_max;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setValidation(String validation) {
        this.validation = validation;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVal_min(float valMin) {
        val_min = valMin;
    }

    public void setVal_max(float valMax) {
        val_max = valMax;
    }

    
}
