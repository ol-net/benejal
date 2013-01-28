
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

/**
 * class represent a premium.
 * 
 * @author Leonid Oldenburger
 */
public class Contribution {
	
	
	private double value;
	private String paymenttime;
	private String methodeofpayment;
	
	/**
	 * super constructor creates a empty premium.
	 */
	public Contribution(){
		this.value = 0;
		this.paymenttime = "";
		this.methodeofpayment = "";
	}
	
	/**
	 * constructor creates a new premium.
	 * 
	 * @param v (value)
	 * @param ptime (paymenttime)
	 * @param mpayment (methodeofpayment)
	 */
	public Contribution(double v, String ptime, String mpayment){
		this.value = v;
		this.paymenttime = ptime;
		this.methodeofpayment = mpayment;
	}
	
	/**
	 * methode set value.
	 * 
	 * @param v (value)
	 */
	public void setValue(double v){
		this.value = v;
	}
	
	/**
	 * methode get value.
	 * 
	 * @return value
	 */
	public double getVlaue(){
		return value;
	}
	
	
	/**
	 * methode set paymenttime.
	 * 
	 * @param ptime (value)
	 */
	public void setPaymentTime(String ptime){
		this.paymenttime = ptime;
	}
	
	/**
	 * methode get paymenttime.
	 * 
	 * @return paymenttime
	 */
	public String getPaymentTime(){
		return paymenttime;
	}
	
	/**
	 * methode set methode of payment.
	 * 
	 * @param mopayment (methode of payment)
	 */
	public void setMethodeOfPayment(String mopayment){
		this.methodeofpayment = mopayment;
	}
	
	/**
	 * methode get methode of payment.
	 * 
	 * @return methodeofpayment
	 */
	public String getMethodeOfPayment(){
		return methodeofpayment;
	}
}
