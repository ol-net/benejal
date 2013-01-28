
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
 * class represent a telephone.
 * 
 * @author Leonid Oldenburger
 */
public class Telephone {
	
	private String privatephone;
	private String officialphone;
	private String mobilephone;
	private String fax;
	
	/**
	 * super constructor creates a empty telephone.
	 */
	public Telephone(){
		this.privatephone = "";
		this.officialphone = "";
		this.mobilephone = "";
		this.fax = "";
	}
	
	/**
	 * constructor creates a telephone.
	 * 
	 * @param pphone (private phone)
	 * @param ophone (office phone)
	 * @param mphone (mobil phone)
	 * @param f		(fax)
	 */
	public Telephone(String pphone, String ophone, String mphone, String f){
		
		this.privatephone = pphone;
		this.officialphone = ophone;
		this.mobilephone = mphone;
		this.fax = f;
	}
	
	/**
	 *  methode set private telephone.
	 * 
	 * @param pphone
	 */
	public void setPrivatePhone(String pphone){
		this.privatephone = pphone;
	}
	
	/**
	 *  methode get private telephone.
	 * 
	 * @return privatephone
	 */
	public String getPrivatePhone(){
		return privatephone;
	}
	
	/**
	 *  methode set official telephone.
	 * 
	 * @param ophone (official phone)
	 */
	public void setOfficePhone(String ophone){
		this.officialphone = ophone;
	}
	
	/**
	 *  methode get official telephone.
	 * 
	 * @return officialphone
	 */
	public String getOfficePhone(){
		return officialphone;
	}

	/**
	 *  methode set mobile phone.
	 * 
	 * @param mphone (mobile phone)
	 */
	public void setMobilePhone(String mphone){
		this.mobilephone = mphone;
	}
	
	/**
	 *  methode get mobile phone.
	 * 
	 * @return mobilephone
	 */
	public String getMobilePhone(){
		return mobilephone;
	}
	
	/**
	 *  methode set fax number.
	 * 
	 * @param f (faxnumber)
	 */
	public void setFaxNumber(String f){
		this.fax = f;
	}
	
	/**
	 *  methode get fax number.
	 * 
	 * @return fax
	 */
	public String getFaxNumber(){
		return fax;
	}
}
