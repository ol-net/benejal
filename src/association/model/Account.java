
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
 * class represent a donor's account.
 * 
 * @author Leonid Oldenburger
 */
public class Account {
	
	private String bankname;
	private String accountnumber;
	private String bankcodenumber;
	private String bicSWIFT;
	private String iBAN;
	private String account_owner;
	
	/**
	 * super constructor creates a empty account.
	 */
	public Account(){
		
		this.bankname = "";
		this.accountnumber = "";
		this.bankcodenumber = "";
		this.bicSWIFT = "";
		this.iBAN = "";
		this.account_owner = "";
	}
	
	/**
	 * constructor creates a new account.
	 * 
	 * @param bname
	 * @param anumber
	 * @param bcnumber
	 * @param bSwift
	 * @param iBan
	 */
	public Account(String owner, String bname, String anumber, String bcnumber, String bSwift, String iBan){
		
		this.account_owner = owner;
		this.bankname = bname;
		this.accountnumber = anumber;
		this.bankcodenumber = bcnumber;
		this.bicSWIFT = bSwift;
		this.iBAN = iBan;
	}
	
	/**
	 * methode set a bankname.
	 * 
	 * @param bname (bankname)
	 */
	public void setBankName(String bname){
		this.bankname = bname;
	}
	
	/**
	 * methode get bankname.
	 * 
	 * @return bankname
	 */
	public String getBankName(){
		return bankname;
	}
	
	/**
	 * methode set a account number.
	 * 
	 * @param anumber (account number)
	 */
	public void setAccountNumber(String anumber){
		this.accountnumber = anumber;
	}
	
	/**
	 * methode get account number.
	 * 
	 * @return bankname
	 */
	public String getAccountNumber(){
		return accountnumber;
	}
	
	/**
	 * methode set a bank code number.
	 * 
	 * @param bcnumber (bank code number)
	 */
	public void setBankCodeNumber(String bcnumber){
		this.bankcodenumber = bcnumber;
	}
	
	/**
	 * methode get bank code number.
	 * 
	 * @return bank code number
	 */
	public String getBankCodeNumber(){
		return bankcodenumber;
	}
	
	/**
	 * methode set a bicSWIFT.
	 * 
	 * @param bSWIFT (bicSWIFT)
	 */
	public void setBicSwift(String bSWIFT){
		this.bicSWIFT = bSWIFT;
	}
	
	/**
	 * methode get bicSWIFT.
	 * 
	 * @return bicSWIFT
	 */
	public String getBicSwift(){
		return bicSWIFT;
	}
	
	/**
	 * methode set a iBAN.
	 * 
	 * @param iBan (iBAN)
	 */
	public void setiBan(String iBan){
		this.iBAN = iBan;
	}
	
	/**
	 * methode get iBAN.
	 * 
	 * @return iBAN
	 */
	public String getiBan(){
		return iBAN;
	}
	
	public void setOwner(String name){
		account_owner = name;
	}
	
	public String getOwner(){
		return account_owner;
	}
}
