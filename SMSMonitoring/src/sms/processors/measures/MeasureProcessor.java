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
package sms.processors.measures;

import java.io.IOException;
import java.util.Calendar;
import java.util.Properties;

import org.apache.log4j.Logger;

import sms.SMS;
import sms.validators.MeasureValidator;
import database.objects.measure.MeasureDB;
import database.objects.measuretype.MeasureTypeDB;
import database.objects.patient.PatientDB;
import entities.measure.Measure;
import etm.core.configuration.EtmManager;
import etm.core.monitor.EtmMonitor;
import etm.core.monitor.EtmPoint;

/**
 * Process an arriving measure
 * 
 * @version 1.0
 * @date 2010-04-06
 * @author Barbara M. Rodeker
 */
public class MeasureProcessor {

    /**
     * Validator
     */
    private MeasureValidator validator = null;
 
    /**
     * Measure database object
     */
    private MeasureDB mesuresDB = null;
    /**
     * Testing times variable
     */
    private static final EtmMonitor etmMonitor = EtmManager.getEtmMonitor();

    /**
     * Logger
     */
    private static Logger logger = Logger.getLogger(MeasureProcessor.class);
    /**
     * Properties file
     */
    private Properties props;

    /**
     * 
     * Constructor
     */
    public MeasureProcessor() {
	loadProperties();
	this.validator = new MeasureValidator();
	this.mesuresDB = new MeasureDB();

    }

    /**
     * Constructor
     */
    public MeasureProcessor(MeasureValidator validator, PatientDB patientsDB,
	    MeasureTypeDB typesDb, MeasureDB mesuresDB) {
	loadProperties();
	this.validator = validator;
	this.mesuresDB = mesuresDB;
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
			    .getResourceAsStream("/sms/processors/measures/resources.properties"));
	} catch (IOException e) {
	    logger.error("Error cargando propiedades y recursos");
	    logger.error(e.getStackTrace());
	}

    }

    /**
     * Takes an sms as in parameter to read the data for patient and measure.
     * Validate measure and if it is ok insert it in database.
     * 
     * 
     * @param sms
     *            SMS to process
     * @return Measure inserted or null
     */
    public Measure processMeasure(SMS sms) {
	// testing times code
	EtmPoint point = etmMonitor
		.createPoint("MeasureProcessor::processMeasure");

	try {

	    Measure m = null;

	    logger.info("Procesando nuevo mensaje y medición: -Remitente "
		    + sms.getCelularFrom() + " -Mensaje " + sms.getText());

	    // we build a measure object and insert it in database later
	    m = new Measure();
	    // we check if the measure is correctly formed
	    boolean valid = validator.isValid(sms, m);
	    if (valid) {

		m.setText(sms.getText());
	
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		String monthStr = Integer.toString(month);
		if (month <= 10) {
		    monthStr = "0" + monthStr;
		}
		int date = cal.get(Calendar.DATE);
		String dateStr = Integer.toString(date);
		if (date <= 10) {
		    dateStr = "0" + dateStr;
		}
		m.setDate(Integer.toString(cal.get(Calendar.YEAR)) + "-"
			+ monthStr + "-" + dateStr + " "
			+ Integer.toString(cal.get(Calendar.HOUR)) + ":"
			+ Integer.toString(cal.get(Calendar.MINUTE)) + ":"
			+ Integer.toString(cal.get(Calendar.SECOND)));

		// we insert the new measure in database
		logger.info("Por insertar medición: -Remitente "
			+ sms.getCelularFrom() + " -Mensaje " + sms.getText());
		mesuresDB.insertMeasure(m);

	    } else {
		logger
			.error(props
				.getProperty("sms.processors.measures.NotValidMeasureError")
				+ "Datos procesados: -Remitente "
				+ sms.getCelularFrom()
				+ "- contenido "
				+ sms.getText());
	    }

	    return m;
	} finally {
	    point.collect();
	}
	
	
    }

    /**
     * 
     * @param validator
     */
    public void setValidator(MeasureValidator validator) {
	this.validator = validator;
    }

  
    /**
     * 
     * @param mesuresDB
     */
    public void setMesuresDB(MeasureDB mesuresDB) {
	this.mesuresDB = mesuresDB;
    }

}
