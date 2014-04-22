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
package sms;

/**
 * Represent the data contained in a text message
 * @version 1.0
 * @date    2010-04-06
 * @author  Barbara M. Rodeker 
 */
public class SMS {
    
    /**
     * Sender number
     */
    private String celularFrom = "";
    /**
     * Receptor number
     */
    private String celularTo = "";
    /**
     * Sent text 
     */
    private String text = "";
    
    /**
     * Constructor
     */
    public SMS(String celularFrom, String celularTo, String text) {
	this.celularFrom = celularFrom;
	this.celularTo = celularTo;
	this.text = text;
    }

    /**
     * Constructor
     */
    public SMS() {
    }

    /**
     * 
     * @return int Sender celular
     */
    public String getCelularFrom() {
        return celularFrom;
    }

    /**
     * 
     * @param celularFrom Sender celular
     */
    public void setCelularFrom(String celularFrom) {
        this.celularFrom = celularFrom;
    }

    /**
     * 
     * @return Receptor celular
     */
    public String getCelularTo() {
        return celularTo;
    }

    /**
     * 
     * @param celularTo Receptor celular
     */
    public void setCelularTo(String celularTo) {
        this.celularTo = celularTo;
    }

    /**
     * 
     * @return String sent text
     */
    public String getText() {
        return text;
    }

    /**
     * 
     * @param text Sent text
     */
    public void setText(String text) {
        this.text = text;
    }
    
 
    
    
    

}
