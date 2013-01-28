
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
package mail.model;

/**
 * class represents a reaceipt
 * 
 * @author Leonid Oldenburger
 */
public class Receipt extends Mail {
	
	private int expense;
	private int base_1;
	private int base_2;
	private String object_one;
	private String object_two;
	private String object_three;
	
	/**
	 * constructor 
	 */
	public Receipt(){}
	
	/**
	 * constructor creates a new receipt
	 * 
	 * @param e
	 * @param b_1
	 * @param b_2
	 * @param oOne
	 * @param oTwo
	 * @param oThree
	 */
	public Receipt(int e, int b_1, int b_2, String oOne, String oTwo, String oThree){
		
		this.expense = e;
		this.base_1 = b_1;
		this.base_2 = b_2;
		this.object_one = oOne;
		this.object_two = oTwo;
		this.object_three = oThree;
	}
	
	/**
	 * set text number one
	 * 
	 * @param oOne
	 */
	public void setObject1(String oOne){
		this.object_one = oOne;
	}
	
	/**
	 * return text number one
	 * 
	 * @return
	 */
	public String getObject1(){
		return object_one;
	}
	
	/**
	 * set text number two
	 * 
	 * @param oTwo
	 */
	public void setObject2(String oTwo){
		this.object_two = oTwo;
	}
	
	/**
	 * return text number two
	 * 
	 * @return two
	 */
	public String getObject2(){
		return object_two;
	}
	
	/**
	 * set text number tree
	 * 
	 * @param oThree
	 */
	public void setObject3(String oThree){
		this.object_three = oThree;
	}
	
	/**
	 * return text number three
	 * 
	 * @return text three
	 */
	public String getObject3(){
		return object_three;
	}
	
	/**
	 * yes or no expense
	 * 
	 * @param e
	 */
	public void setExpense(int e){
		this.expense = e;
	}
	
	/**
	 * get yes or no expense
	 * 
	 * @return e
	 */
	public int getExpense(){
		return expense;
	}
	
	/**
	 * set option one
	 * 
	 * @param b_1
	 */
	public void setBase1(int b_1){
		this.base_1 = b_1;
	}
	
	/**
	 * return option one
	 * 
	 * @return
	 */
	public int getBase1(){
		return base_1;
	}
	
	/**
	 * set option 2
	 * 
	 * @param b_2
	 */
	public void setBase2(int b_2){
		this.base_2 = b_2;
	}
	
	/**
	 * return option two
	 * 
	 * @return
	 */
	public int getBase2(){
		return base_2;
	}
}
