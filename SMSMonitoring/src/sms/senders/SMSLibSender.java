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
package sms.senders;

import org.apache.log4j.Logger;
import org.smslib.OutboundMessage;
import org.smslib.Service;

import sms.SMS;
import sms.services.smslib.SMSLibInterface;

/**
 * Process outgoing text messages using SMSLib libraries {@see
 * http://smslib.org/}
 * 
 * @version 1.0
 * @date 2010-04-06
 * @author Barbara M. Rodeker
 */
public class SMSLibSender extends Sender {
    
 
    /**
     * SMS service
     */
    private Service srv = null;

    /**
     * 
     * Constructor
     * 
     * @throws SMSLibSenderException
     */
    public SMSLibSender() throws SMSLibSenderException {

	srv = SMSLibInterface.getInstance().getSrv();	

    }
    
 
    @Override
    public void sendSMS(SMS sms) {
	OutboundMessage msg;

	// Send a message asynchronously.
	msg = new OutboundMessage(String.valueOf(sms.getCelularTo()), sms
		.getText());
	srv.queueMessage(msg);

    }


    /**
     * Stop sending message service
     * 
     */
    @Override
    public void stopSending() {
	try {
	    this.srv.stopService();
	    SMSLibInterface.getInstance().stopService();

	} catch (Exception e) {
	    Logger.getRootLogger().error(e.toString());
	}
    }

}
