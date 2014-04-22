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
package sms.validators;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import sms.SMS;
import database.objects.measuretype.MeasureTypeDB;
import database.objects.patient.PatientDB;
import entities.measure.Measure;
import entities.measuretype.MeasureType;
import entities.parameter.Parameter;
import entities.user.User;

/**
 * @version 1.0
 * @date    2010-04-06
 * @author  Barbara M. Rodeker 
 */
public class MeasureValidator {
    
    /**
     * MeasureType database object
     */
    private MeasureTypeDB typesDB = null;
    /**
     * Patient database object
     */
    private PatientDB patientDB = null;
    
    /**
     * Logger
     */
    private static Logger logger = Logger.getLogger(MeasureValidator.class);
    /**
     * Properties file
     */
    private Properties props;
    
    
    /**
     * Constructor
     */
    public MeasureValidator(MeasureTypeDB typesDB, PatientDB patientDB) {
	loadProperties();
	this.typesDB = typesDB;
	this.patientDB = patientDB;
    }
    
    /**
     * Constructor
     */
    public MeasureValidator() {
	loadProperties();
	this.typesDB = new MeasureTypeDB();
	this.patientDB = new PatientDB();
    }
    
    /**
     * Load the properties and messages file
     * 
     */
    private void loadProperties() {

	// Properties are initialized
	props = new Properties();
	try {
	    props
		    .load(MeasureValidator.class
			    .getResourceAsStream("/sms/validators/resources.properties"));
	} catch (IOException e) {
	    logger.error("Error cargando propiedades y recursos");
	    logger.error(e.getStackTrace());
	}

    }    
    
    /**
     * Validate the patient and text of a measure
     * 
     * @param SMS Message to validate
     * @param Measure to set patient and measure type
     * @return true if valid
     */
    public boolean isValid(SMS sms,Measure m){
	if (sms == null){
	    logger.warn(props.getProperty("validators.NotValidMsgNULL"));
	    return false;
	}
	
	boolean valid = true;
	valid = ((validPatient(sms,m)) && (validText(sms,m)));
	return valid;
	
    }
    
    /**
     * Takes the message´s text and validate it 
     * 
     * @param sms SMS to validate text
     * @param Measure to set measure type
     * @return true is valid
     */
    private boolean validText(SMS sms, Measure m){
	boolean valid = true;
	
	//we check the type of the measure
	int blankPosition = sms.getText().indexOf(' ');
	
	//if there is no space in the text it is not a valid measure 
	if (blankPosition == -1){
	    logger.warn(props.getProperty("validators.NotValidTextNoSpace"));
	    return false;
	}
	
	// Now we check the header which indicates the type of measure
	String header = sms.getText().substring(0, blankPosition);
	MeasureType type = typesDB.getMeasureTypeByValue(header);
	
	if (type == null){
	    logger.warn(props.getProperty("validators.NotValidTextTYPE"));
	    return false;
	}
	
	//now we check if the measure has parameters
	Vector<Parameter> parameters = type.getCategory().getParameters();
	
	if (parameters == null) {
	    logger.warn(props.getProperty("validators.NotValidTextPARAMS")+"Tipo: "+header);
	    return false;
	}
	
	//now we iterate over each parameter checking it against the received text
	String paramsReceived = sms.getText();
	for (Enumeration<Parameter> e = parameters.elements(); e.hasMoreElements() && valid; ){
	    if (paramsReceived.length() > 0){
		
		    Parameter param = e.nextElement();
		    String regularExpresion = param.getValidation();
		    
		    //we compile the validation regular expression
		    Pattern pattern = Pattern.compile(regularExpresion);
		    int pos = paramsReceived.indexOf(" ");
		    if (pos == -1){
			pos = paramsReceived.length();
		    }
	       	    String nextParam = paramsReceived.substring(0, pos);
	       	    paramsReceived = paramsReceived.substring(paramsReceived.indexOf(" ")+1, paramsReceived.length());
	       	    Matcher match = pattern.matcher(nextParam);
	        	    
	       	    //we check the matching with the received parameter
	       	    valid = match.matches();

	    }else{
		
		valid = false;
	    }
	    
	    
	}
	
	m.setMt(type);
	return valid;
	
    }
    
    /**
     * Take the patient of the message and validates it
     * 
     * @param sms SMS to validate
    * @param Measure to set patient 
      * @return true if valid
     */
    private boolean validPatient(SMS sms, Measure m){
	boolean valid = true;
	
	//we check if the patient is registered in database
	String patientCel = sms.getCelularFrom();
	User p = patientDB.getPatientByCel(patientCel);
	
	if (p == null){
	    logger.warn(props.getProperty("validators.NotValidPatientNULL"));
	    valid = false;
	}
	
	m.setPatient(p);
	return valid;
    }

    /**
     * 
     * @param typesDB Measure type to access database
     */
    public void setTypesDB(MeasureTypeDB typesDB) {
        this.typesDB = typesDB;
    }

    /**
     * 
     * @param patientDB Patient database object
     */
    public void setPatientDB(PatientDB patientDB) {
        this.patientDB = patientDB;
    }
     
    
    
    
    

}
