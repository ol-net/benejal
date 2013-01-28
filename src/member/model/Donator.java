
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

import association.model.Account;
import association.model.SpecialContribution;

/**
 * class represent a donator extends from class person
 * 
 * @author Leonid Oldenburger
 */
public class Donator extends Person{
	
	protected int donatornumber;
	protected int donatortype;
	protected SpecialContribution special_contribution;
	protected Account account;
	
	/**
	 * super constructor creates a empty donator.
	 */
	public Donator(){
		this.donatornumber = 0;
		this.donatortype = 0;
		this.account = new Account();
		this.special_contribution = new SpecialContribution();
	}
	
	/**
	 * constructor creates a new donator.
	 * 
	 * @param account
	 */
	public Donator(int dnumber, int dtype, Account account, SpecialContribution s_contribution){
		this.donatornumber = dnumber;
		this.donatortype = dtype;
		this.account = account;
		this.special_contribution = s_contribution;
	}
	
	/**
	 * methode set donator number.
	 * 
	 * @param dnumber (donator number)
	 */
	public void setNumber(int dnumber){
		this.donatornumber = dnumber;
	}
	
	/**
	 * methode get donator number.
	 * 
	 * @return donatornumber
	 */
	public int getNumber(){
		return donatornumber;
	}
	
	/**
	 * methode set donator type.
	 * 
	 * @param dtype (donator type)
	 */
	public void setType(int dtype){
		this.donatortype = dtype;
	}
	
	/**
	 * methode get donator type.
	 * 
	 * @return donatortype
	 */
	public int getType(){
		return donatortype;
	}
	
	/**
	 * methode set donator account.
	 * 
	 * @param daccount (donator account)
	 */
	public void setAccount(Account daccount){
		this.account = daccount;
	}
	
	/**
	 * methode get donator account.
	 * 
	 * @return donatoraccount
	 */
	public Account getAccount(){
		return account;
	}	
	
	public SpecialContribution getSpecialContribution(){
		return special_contribution;
	}
	
	public void setSpecialContribution(SpecialContribution s_contribution){
		this.special_contribution = s_contribution;
	}
}
