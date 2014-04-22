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
package sms.receivers;

import org.apache.log4j.Logger;

/**
 * Receive entry text message and process them.
 * This class is a Thread running and continously listening entry data.
 * 
 * @version 1.0
 * @date    2010-04-06
 * @author  Barbara M. Rodeker 
 */
public abstract class Receiver extends Thread{
    
    
    /**
     * Reads entry data from modem and process it 
     */
    public abstract void receiveSMS();
    
    /**
     * Process entry data 
     */
    public void run(){
	while (true){
	    receiveSMS();
	    try {
		sleep(3000);
	    } catch (InterruptedException e) {
		Logger.getRootLogger().error("Error durmiendo thread de recepci�n...");
	    }
	}
	
    }
    
    public abstract void stopReceiving();
    
}
