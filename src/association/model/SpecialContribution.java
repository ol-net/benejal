
// software for charitable associations 
// Copyright (C) 2010  Artem Petrov (artpetro@uos.de) & Leonid Oldenburger (loldenbu@uos.de)

// This program is free software; you can redistribute it and/or modify it under the terms 
// of the GNU General Public License as published by the Free Software Foundation; either 
// version 3 of the License, or (at your option) any later version.

// This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
// without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
// See the GNU General Public License for more details.

// You should have received a copy of the GNU General Public License along with this program; 
// if not, see <http://www.gnu.org/licenses/>.
package association.model;

import java.sql.Date;


/**
 * class special premium
 * 
 * @author Leonid Oldenburger
 */
public class SpecialContribution extends Contribution{
	
	private String special_premium_number;
	private Date permanent_special_premium_date;
	protected int payment_form;
	protected int payment_time;
	
	/**
	 * super constructor creates empty special premium class.
	 */
	public SpecialContribution(){
		this.special_premium_number = "";
		this.permanent_special_premium_date = null;
		this.payment_time = 0;
		this.payment_form = 0;
	}
	
	/**
	 * constructor creates a new special premium class.
	 * 
	 * @param spnumber
	 * @param pspremium_date
	 */
	public SpecialContribution(String spnumber, Date pspremium_date, int p_time, int p_form){
		this.special_premium_number = spnumber;
		this.permanent_special_premium_date = pspremium_date;
		this.payment_time = p_time;
		this.payment_form = p_form;
	}
	
	/**
	 * methode sets special premium number.
	 * 
	 * @param spn special premium number
	 */
	public void setSpecialPremiumNumber(String spn){
		special_premium_number = spn;
	}
	
	/**
	 * methode returns special premium number.
	 * 
	 * @return special_premium_number
	 */
	public String getSpecialPremiumNumber(){
		return special_premium_number;
	}
	
	/**
	 * methode sets special premium date
	 * 
	 * @param pspdate (permanent special premium date)
	 */
	public void setSpecialPremiumDate(Date pspdate){
		this.permanent_special_premium_date = pspdate;
	}
	
	/**
	 * methode returns permanent_special_premium_date
	 * 
	 * @return permanent_special_premium_date
	 */
	public Date getSpecialPremiumDate(){
		return permanent_special_premium_date;
	}
	
	/**
	 * methode get payment form.
	 * 
	 * @return payment_form
	 */
	public int getPayForm(){
		return payment_form;
	}
	
	/**
	 * methode get payment time.
	 * 
	 * @return payment_time
	 */
	public int getPayTime(){
		return payment_time;
	}
	
	/**
	 * methode sets payment time.
	 * 
	 * @param payment time
	 */
	public void setPaymentForm(int f){
		this.payment_form = f;
	}
	
	/**
	 * methode sets payment time.
	 * 
	 * @param payment time
	 */
	public void setPaymentTime(int p){
		this.payment_time = p;
	}
}
