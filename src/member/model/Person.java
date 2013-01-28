
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

/**
 * class represent a person
 * 
 * @author Leonid Oldenburger
 */
public class Person {

	protected String title;
	protected String gender;
	protected String firstname;
	protected String lastname;
	protected String comment;
	
	/**
	 * super constructor creates a empty person.
	 */
	public Person(){
		
		this.title = "";
		this.gender = "";
		this.firstname = "";
		this.lastname = "";
		this.comment = "";
	}	
	
	/**
	 * Constructor creates a new person.
	 * 
	 * @param tit title 
	 * @param gen gender
	 * @param fname firstname
	 * @param lname lastname
	 * @param comm comment
	 */
	public Person(String tit, String gen, String fname, String lname, String comm){
		
		this.title = tit;
		this.gender = gen;
		this.firstname = fname;
		this.lastname = lname;
		this.comment = comm;
	}	
	
	/**
	 * Methode set title.
	 * 
	 * @param tit (title)
	 */
	public void setTitle(String tit){
		this.title = tit;
	}
	
	/**
	 * Methode get title.
	 * 
	 * @param tit (title)
	 * @return title
	 */
	public String getTitle(){
		return title;
	}
	
	/**
	 * Methode set gender.
	 * 
	 * @param gen (gender)
	 */
	public void setGender(String gen){
		this.gender = gen;
	}
	
	/**
	 * Methode get gender.
	 * 
	 * @return gender
	 */
	public String getGender(){
		return gender;
	}
	
	/**
	 * Methode set firstname.
	 * 
	 * @param fname (firstname)
	 */
	public void setFirstName(String fname){
		this.firstname = fname;
	}
	
	/**
	 * Methode get firsttname.
	 * 
	 * @return firstname
	 */
	public String getFirstName(){
		return firstname;
	}
	
	/**
	 * Methode set lastname.
	 * 
	 * @param lname (lastname)
	 */
	public void setLastName(String lname){
		this.lastname = lname;
	}
	
	/**
	 * Methode get lasttname.
	 * 
	 * @return lastname
	 */
	public String getLastName(){
		return lastname;
	}
	
	/**
	 * Methode set comment.
	 * 
	 * @param comm (comment)
	 */
	public void setComment(String comm){
		this.comment = comm;
	}
	
	/**
	 * Methode get comment.
	 * 
	 * @return comment
	 */
	public String getComment(){
		return comment;
	}
}
