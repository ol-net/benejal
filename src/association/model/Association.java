
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

import java.util.Date;
import java.util.LinkedList;

import mail.model.Mail;
import member.model.Donator;
import member.model.Member;
import member.model.Person;

/**
 * Class Association
 * 
 * @author Leonid Oldenburger
 */
public class Association {

	private static Association association_instance; 
		
	private String association_name;
	private Double account_balance;
	private Date premiumday;
	private String paymenttime;
	private String datadir;
	private int password;
	private int paymentrhythm;
	private String init_date;
	private Adress association_adress;
	private Telephone association_telephone;
	private Account association_account;
	private Person association_leader;
	private Person association_treasurer;
	private TaxOffice association_finance_office;
	private LinkedList<Member> association_member_list;
	private LinkedList<Donator> association_donator_list;
	private LinkedList<Group> association_member_group;
	private LinkedList<Mail> associaton_mail;
	private int logo;
	
	/**
	 * super constructor creates an empty association.
	 * singleton
	 */
	private Association(){
		this.association_name = "";
		this.premiumday = null;
		this.association_adress = null;
		this.association_telephone = null;
		this.association_account = null;
		this.association_leader = null;
		this.association_treasurer = null;
		this.association_finance_office = null;
		this.association_member_list = null;
		this.association_donator_list = null;
		this.association_member_group = null;
		this.associaton_mail = null;
		this.paymenttime = null;
		this.datadir = null;
		this.password = 0;
		this.paymentrhythm = 0;
		this.account_balance = 0.0;
		this.init_date = null;
		this.logo = 0;
	}
	
	public synchronized static Association getInstance(){
		if(association_instance == null){
			association_instance = new Association();
		}
		return association_instance;
	}
	
	public void setInitDate(String date){
		this.init_date = date;
	}
	
	public void setLogo(int l){
		this.logo = l;
	}
	
	public int getLogo(){
		return logo;
	}
	
	public String getInitDate(){
		return this.init_date;
	}
	
	public void setPassword(int pass){
		this.password = pass;
	}
	
	public int getPassword(){
		return password;
	}
	
	public void setPaymentRhythm(int prhythm){
		this.paymentrhythm = prhythm;
	}
	
	public int getPaymentRhythm(){
		return paymentrhythm;
	}
	
	public void setDatadir(String ddir){
		this.datadir = ddir;
	}
	
	public String getDataDir(){
		return datadir;
	}
	
	public void setPaymentTime(String ptime){
		this.paymenttime = ptime;
	}
	
	public String getPamentTime(){
		return paymenttime;
	}
	
	public void setName(String aname){
		this.association_name = aname;
	}
	
	public String getName(){
		return association_name;
	}
	
	public void setPremiumDay(Date pday){
		this.premiumday = pday;
	}
	
	public Date getPremiumDay(){
		return premiumday;
	}
	
	public void setAdress(Adress adress){
		this.association_adress = adress;
	}
	
	public Adress getAdress(){
		return association_adress;
	}
	
	public void setTelephone(Telephone phone){
		this.association_telephone = phone;
	}
	
	public Telephone getTelephone(){
		return association_telephone;
	}
	
	public void setAccount(Account account){
		this.association_account = account;
	}
	
	public Account getAccount(){
		return association_account;
	}
	
	public void setLeader(Person leader){
		this.association_leader = leader;
	}
	
	public Person getLeader(){
		return association_leader;
	}
	
	public void setTreasurer(Person treasurer){
		this.association_treasurer = treasurer;
	}
	
	public Person getTreasurer(){
		return association_treasurer;
	}
	
	public void setFinanceOffice(TaxOffice office){
		this.association_finance_office = office;
	}
	
	public TaxOffice getFinacneOffice(){
		return association_finance_office;
	}
	
	public void setMemberList(LinkedList<Member> member_list){
		this.association_member_list = member_list;
	}
	
	public LinkedList<Member> getMemberList(){
		return association_member_list;
	}
	
	public void setDonatorList(LinkedList<Donator> donator_list){
		this.association_donator_list = donator_list;
	}
	
	public LinkedList<Donator> getDonatorList(){
		return association_donator_list;
	}
	
	public void setGroupList(LinkedList<Group> group_list){
		this.association_member_group = group_list;
	}
	
	public LinkedList<Group> getGroupList(){
		return association_member_group;
	}
	
	public void setMailList(LinkedList<Mail> mail_list){
		this.associaton_mail = mail_list;
	}
	
	public LinkedList<Mail> getMailList(){
		return associaton_mail;
	}
	
	public void setAccountBalance(double balance){
		this.account_balance = balance;
	}
	
	public Double getAccountBalance(){
		return account_balance;
	}
}