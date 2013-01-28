
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
 * class represent a adress.
 * 
 * @author Leonid Oldenburger
 */
public class Adress {
	
	private String adressingadditional;
	private String street;
	private String housenumber;
	private String zipcode;
	private String city;
	private String country;
	private String email;
	
	/**
	 * super constructor creates a adress.
	 */
	public Adress(){
		
		this.adressingadditional = "";
		this.street = "";
		this.housenumber = "";
		this.zipcode = "";
		this.city = "";
		this.country = "";
		this.email = "";
	}
	
	/**
	 * constructor creates a adress.
	 */
	public Adress(String aadditional, String str, String hnumber, String zcode, String c, String count, String eMail){
		
		this.adressingadditional = aadditional;
		this.street = str;
		this.housenumber = hnumber;
		this.zipcode = zcode;
		this.city = c;
		this.country = count;
		this.email = eMail;
	}
	
	/**
	 *	methode set adressingadditional.
	 *
	 *  @param aadditional
	 */
	public void setAdressAdditional(String aadditional){
		this.adressingadditional = aadditional;
	}
	
	/**
	 *	methode get adressingadditional.
	 *
	 *  @return adressingadditional
	 */
	public String getAdressAdditional(){
		return adressingadditional;
	}
	
	/**
	 * methode set street.
	 * 
	 * @param str (street)
	 */
	public void setStreet(String str){
		this.street = str;
	}
	/**
	 * methode get street.
	 * 
	 * @return street
	 */
	public String getStreet(){
		return street;
	}
	
	/**
	 * methode set house number.
	 * 
	 * @param hNumber
	 */
	public void setHouseNumber(String hNumber){
		this.housenumber = hNumber;
	}
	
	/**
	 * methode get house number.
	 * 
	 * @return housenumber
	 */
	public String getHouseNumber(){
		return housenumber;
	}
	
	/**
	 * methode set zip code.
	 * 
	 * @param zcode (zip code)
	 */
	public void setZipCode(String zCode){
		this.zipcode = zCode;
	}
	
	/**
	 * methode get zip code.
	 * 
	 * @return zip code
	 */
	public String getZipCode(){
		return zipcode;
	}
	
	/**
	 * methode set city.
	 * 
	 * @param cit (city)
	 */
	public void setCity(String cit){
		this.city = cit;
	}
	/**
	 * methode get city.
	 * 
	 * @return	city
	 */
	public String getCity(){
		return city;
	}
	
	/**
	 * methode set country.
	 * 
	 * @param count (country)
	 */
	public void setCountry(String count){
		this.country = count;
	}
	/**
	 * methode get country.
	 * 
	 * @return country
	 */
	public String getCountry(){
		return country;
	}
	
	/**
	 * methode set email.
	 * 
	 * @param eMail 
	 */
	public void setEMail(String eMail){
		this.email = eMail;
	}
	/**
	 * methode get email.
	 * 
	 * @return email
	 */
	public String getEmail(){
		return email;
	}
}
