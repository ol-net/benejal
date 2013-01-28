
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
package member.model;

import association.model.Adress;
import association.model.Telephone;

/**
 * class represent a donator with adress,
 * extends from class Donator
 * 
 * @author Leonid Oldenburger
 */
public class DonatorWithAdress extends Donator{
	
	protected Adress donatorsadress;
	protected Telephone phone;
	
	/**
	 * super constructor creates a empty donator.
	 */
	public DonatorWithAdress(){
		this.donatorsadress = new Adress();
		this.phone = new Telephone();
	}
	
	/**
	 *constructor creates a donator with adress.
	 */
	public DonatorWithAdress(Adress adress){
		this.donatorsadress = adress;
	}
	
	/**
	 * methode set donator adress.
	 * 
	 * @param adress (donator adress)
	 */
	public void setAdress(Adress adress){
		this.donatorsadress = adress;
	}
	
	/**
	 * methode get donator adress.
	 * 
	 * @return donatoradress
	 */
	public Adress getAdress(){
		return donatorsadress;
	}
	
	/**
	 * methode set donator phone.
	 * 
	 * @param phone
	 */
	public void setPhone(Telephone tphone){
		this.phone = tphone;
	}
	
	/**
	 * methode get donator telephone.
	 * 
	 * @return telephone
	 */
	public Telephone getPhone(){
		return phone;
	}
}
