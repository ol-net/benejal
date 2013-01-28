
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
 * class represent a member group.
 * 
 * @author Leonid Oldenburger
 */
public class Group {

	private String membergroup;
	private Contribution premium;
	
	/**
	 * super constructor creates a empty group.
	 */
	public Group(){
		this.membergroup = "";
		this.premium = new Contribution();
	}
	
	/**
	 * constructor creates a new group.
	 * 
	 * @param g (group)
	 * @param p (premium)
	 */
	public Group(String g, Contribution p){
		this.membergroup = g;
		this.premium = p;
	}
	
	/**
	 * methode set group.
	 * 
	 * @param g (group)
	 */
	public void setGroup(String g){
		this.membergroup = g;
	}
	
	/**
	 * methode get group
	 * 
	 * @return membergroup
	 */
	public String getGroup(){
		return membergroup;
	}
	
	/**
	 * methode set premium.
	 * 
	 * @param p (premium)
	 */
	public void setPremium(Contribution p){
		this.premium = p;
	}
	
	/**
	 * methode get premium
	 * 
	 * @return premium
	 */
	public Contribution getPremium(){
		return premium;
	}
	
}
