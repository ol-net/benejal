
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

import java.util.Date;

import association.model.Account;
import association.model.Adress;
import association.model.Group;
import association.model.Telephone;


/**
 * class represent a member,
 * extends from class DonatorWithAdress.
 * 
 * @author Leonid Oldenburger
 */
public class Member extends DonatorWithAdress{
	
	private int membernumber;
	private int status;
	private int paystatus;
	private Date dateofentry;
	private Date dateofseparation; 
	private Group membergroup;
	private Telephone membertelephone;
	private int groupid;
	private int paymentway;
	private int contribution_did;
	private double sumcontribution;
	private double contribution;
	
	public static final int EMPTY = -1;
	public static final int FAELLIG = 0;
	public static final int BEARBEITUNG = 1;
	public static final int BEZAHLT = 2;
	public static final int OFFEN = 3;
	public static final int FEST = 1;
	public static final int SCHWEBEND = 0;
	public static final int GEKUENDIGT = 2;
	
	/**
	 * super constructor creates a empty member.
	 */
	public Member(){
		this.membernumber = 0;
		this.status = 0;
		this.dateofentry = null;
		this.dateofseparation = null;
		this.membergroup = null;
		this.groupid = 0;
		this.paymentway = 0;
		this.sumcontribution = 0;
		this.contribution = 0; 
		this.contribution_did = 0;
	}
	
	/**
	 * constructor creates a new member.
	 */
	public Member(int mnumber, int state, int pstate, Date dofentry, Date dofseperation, Adress a, Telephone t, Account acc, String tit, String gen, String fn, String ln, Group g, int gid, int pway, double sc, double c, int cd){
		
		this.title = tit;
		this.gender = gen;
		this.firstname = fn;
		this.lastname = ln;
		this.membernumber = mnumber;
		this.status = state;
		this.paystatus = pstate;
		this.dateofentry = dofentry;
		this.dateofseparation = dofseperation;
		this.donatorsadress = a;
		this.phone = t;
		this.account = acc;
		this.membergroup = g;
		this.groupid = gid;
		this.paymentway = pway;
		this.sumcontribution = sc;
		this.contribution = c;
		this.contribution_did = cd;
	}
	
	/**
	 * methode set contribution_did;.
	 * 
	 * @param contribution_did;
	 */
	public void setContributionDid(int cd){
		this.contribution_did = cd;
	}
	
	/**
	 * methode get contribution_did.
	 * 
	 * @return contribution_did
	 */
	public int getContributionDid(){
		return contribution_did;
	}
	
	public void setContribution(double c){
		this.contribution = c;
	}
	
	public double getContribution(){
		return contribution;
	}
	
	public void setSumContribution(double sum){
		this.sumcontribution = sum;
	}
	
	public double getSumContribution(){
		return sumcontribution;
	}
	
	/**
	 * methode set paymentway.
	 * 
	 * @param paymentway
	 */
	public void setPaymentWay(int pway){
		this.paymentway = pway;
	}
	
	/**
	 * methode get paymentway.
	 * 
	 * @return paymentway
	 */
	public int getPaymentWay(){
		return paymentway;
	}
	
	/**
	 * methode set member number.
	 * 
	 * @param membernumber (member number)
	 */
	public void setNumber(int mnumber){
		this.membernumber = mnumber;
	}
	
	/**
	 * methode get member number.
	 * 
	 * @return membernumber
	 */
	public int getNumber(){
		return membernumber;
	}
	
	/**
	 * methode set member paystatus.
	 * 
	 * @param paystate (member status)
	 */
	public void setPayStatus(int state){
		this.paystatus = state;
	}
	
	/**
	 * methode get member paystatus.
	 * 
	 * @return paystatus
	 */
	public int getPayStatus(){
		return paystatus;
	}
	
	/**
	 * methode set member status.
	 * 
	 * @param state (member status)
	 */
	public void setStatus(int state){
		this.status = state;
	}
	
	/**
	 * methode get member status.
	 * 
	 * @return status
	 */
	public int getStatus(){
		return status;
	}
	
	/**
	 * methode set member date of entry.
	 * 
	 * @param doentry (dateofentry)
	 */
	public void setDateOfEntry(Date doentry){
		this.dateofentry = doentry;
	}
	
	/**
	 * methode get member date of entry.
	 * 
	 * @return dateofentry
	 */
	public Date getDateOfEntry(){
		return dateofentry;
	}
	
	/**
	 * methode set member date of seperation.
	 * 
	 * @param dosepearation (date of seperation)
	 */
	public void setDateOfSeperation(Date doseperation){
		this.dateofseparation = doseperation;
	}
	
	/**
	 * methode get member date of seperation.
	 * 
	 * @return dateofseparation
	 */
	public Date getDateOfSeperation(){
		return dateofseparation;
	}
	
	/**
	 * methode set member group.
	 * 
	 * @param g (member group)
	 */
	public void setGroup(Group g){
		this.membergroup = g;
	}
	
	/**
	 * methode get memmber group.
	 * 
	 * @return membergroup
	 */
	public Group getMemberGroup(){
		return membergroup;
	}
	
	/**
	 * methode set member telephone.
	 * 
	 * @param phone (member telephone)
	 */
	public void setMemberPhone(Telephone phone){
		this.membertelephone = phone;
	}
	
	/**
	 * methode get memmber telephone.
	 * 
	 * @return membertelephone
	 */
	public Telephone getMemberPhone(){
		return membertelephone;
	}
	
	public void setGroupID(int gid){
		groupid = gid;
	}
	
	public int getGroupID(){
		return groupid;
	}
}
