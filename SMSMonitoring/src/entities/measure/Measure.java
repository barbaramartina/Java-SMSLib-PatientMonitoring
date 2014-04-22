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
package entities.measure;

import entities.measuretype.MeasureType;
import entities.user.User;

/**
 * 
 * Information and functions related to measures sent by patients
 * 
 * @version 1.0
 * @date 2010-04-06
 * @author Barbara M. Rodeker
 */
public class Measure {

    /**
     * Identifier
     */
    private int id = -1;
    /**
     * Measure text
     */
    private String text = "";
    /**
     * Patient
     */
    private User patient = null;
    /**
     * Arriving date
     */
    private String date = "";
    /**
     * Type
     */
    private MeasureType mt = null;
    
    
    /**
     * 
     * Constructor
     */
    public Measure(){

    }
    
    /**
     * 
     * Constructor
     * 
     * @param int Identifier
     * @param String Text
     * @param Paciente Patient 
     * @param String Arriving date 
     * @param TipoMedicion Measure type
     */
    public Measure(int id, String text, User pac, String date, MeasureType tm){
	this.id = id;
	this.text = text;
	this.patient = pac;
	this.date = date;
	this.mt = tm;
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
     * @return String Text
     */
    public String getText() {
        return text;
    }

    /**
     * 
     * @return Patient
     */
    public User getPatient() {
        return patient;
    }

    /**
     * 
     * @return String Arriving date 
     */
    public String getDate() {
        return date;
    }

    /**
     * 
     * @return MeasureType Type
     */
    public MeasureType getMt() {
        return mt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setPatient(User patient) {
        this.patient = patient;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setMt(MeasureType mt) {
        this.mt = mt;
    }

    
}
