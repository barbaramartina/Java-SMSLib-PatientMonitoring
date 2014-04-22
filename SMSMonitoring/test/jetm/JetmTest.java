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
package jetm;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import org.apache.log4j.Logger;

import sms.SMS;
import sms.processors.measures.MeasureProcessor;
import sms.processors.news.NewsProcessor;
import sms.processors.responses.ResponsesProcessor;
import sms.senders.SMSLibSenderException;
import etm.core.configuration.BasicEtmConfigurator;
import etm.core.configuration.EtmManager;
import etm.core.monitor.EtmMonitor;
import etm.core.renderer.SimpleTextRenderer;

public class JetmTest {

    public static void main(String[] args) throws FileNotFoundException {

	EtmMonitor monitorE;

	// configure measurement framework
	BasicEtmConfigurator.configure();
	monitorE = EtmManager.getEtmMonitor();
	monitorE.start();
	ResponsesProcessor responsesProcessor = null;
	NewsProcessor newsProcessor = null;
	MeasureProcessor measuresProcessor = null;

	try {
	    // test for processing responses
	    responsesProcessor = new ResponsesProcessor();
	    for (int i = 50; i >= 0; --i) {
		responsesProcessor.processResponse();
	    }

	    // test for processing news
	    newsProcessor = new NewsProcessor();
	    for (int i = 50; i >= 0; --i) {
		newsProcessor.processNew();
	    }

	    // testing receiving measure
	    measuresProcessor = new MeasureProcessor();
	    SMS smsA = new SMS();
	    SMS smsB = new SMS();

	    smsB.setText("GAD 7");
	    smsA.setText("GAD 7");
	    smsB.setCelularFrom("+5492293658110");
	    smsA.setCelularFrom("+5492293658110");

	    for (int i = 100; i >= 0; --i) {
		measuresProcessor.processMeasure(smsA);
		measuresProcessor.processMeasure(smsB);
	    }

	} catch (SMSLibSenderException e) {
	    Logger.getRootLogger().error(e.toString());
	}

	// visualize results
	monitorE.render(new SimpleTextRenderer(new OutputStreamWriter(
		new FileOutputStream("C:\\performance.log"))));
	
	responsesProcessor.stopProcessing();
	newsProcessor.stopProcessing();


    }

}
