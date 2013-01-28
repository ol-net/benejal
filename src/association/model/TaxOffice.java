
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
 * class represent a finance office.
 * 
 * @author Leonid Oldenburger
 */
public class TaxOffice {
	
	private static TaxOffice tax_office_instance; 
	
	private Date v;
	private Date a;
	private String taxnumber;
	private String currency;
	private Adress adress;
	
	/**
	 * super constructor create a empty finaceoffice
	 */
	private TaxOffice(){
		
		this.taxnumber = "";
		this.currency = "";
		this.adress = new Adress();
		this.v = null;
		this.a = null;
	}
	
	public synchronized static TaxOffice getInstance(){
		if(tax_office_instance == null){
			tax_office_instance = new TaxOffice();
		}
		return tax_office_instance;
	}
	
	/**
	 * set v date
	 * 
	 * @param date
	 */
	public void setV(Date date){
		this.v = date;
	}
	
	/**
	 * get v date
	 * 
	 * @return v date
	 */
	public Date getV(){
		return v;
	}
	
	/**
	 * set a date
	 * 
	 * @param date
	 */
	public void setA(Date date){
		this.a = date;
	}
	
	/**
	 * get a date
	 * 
	 * @return a date
	 */
	public Date getA(){
		return a;
	}
	
	/**
	 * methode set tax number.
	 * 
	 * @param tnumber (tax number)
	 */
	public void setTaxNumber(String tnumber){
		this.taxnumber = tnumber;
	}
	
	/**
	 * methode get tax number.
	 * 
	 * @return taxnumber
	 */
	public String getTaxNumber(){
		return taxnumber;
	}
	
	/**
	 * methode set currency.
	 * 
	 * @param current (currency)
	 */
	public void setCurrency(String current){
		this.currency = current;
	}
	
	/**
	 * methode get currency.
	 * 
	 * @return currency
	 */
	public String getCurrency(){
		return currency;
	}
	
	/**
	 * methode set adress.
	 * 
	 * @param adr (adress)
	 */
	public void setAdress(Adress adr){
		this.adress = adr;
	}
	
	/**
	 * methode get adress.
	 * 
	 * @return adress
	 */
	public Adress getAdress(){
		return adress;
	}
}
