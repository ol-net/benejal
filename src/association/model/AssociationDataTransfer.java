
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

import java.sql.Date;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Observable;

import mail.model.Mail;
import mail.model.Receipt;
import member.model.DonatorWithAdress;
import member.model.Member;

import database.AssociationDB;
import database.DonatorDB;
import database.MailDB;
import database.MemberDB;
import database.ReceiptDB;

/**
 * class represents Observable for the communication between 
 * database and model klasses
 * 
 * @author Leonid Oldenburger
 */
public class AssociationDataTransfer extends Observable{
	
	private static AssociationDataTransfer association_d_t_instance; 
	private SpecialContribution special_contribution;
	private LinkedHashMap<Integer, DonatorWithAdress> association_donator_map;
	private LinkedHashMap<Integer, Member> association_member_map;
	private LinkedHashMap<Integer, Mail> association_mail_map;
	private LinkedHashMap<Integer, Receipt> association_receipt_map;
	private LinkedList<Group> group_list;
	private Association association_object;
	private DonatorWithAdress donator_wa;
	private Member member;
	
	/**
	 * constructor
	 */
	public AssociationDataTransfer(){}
	
	public synchronized static AssociationDataTransfer getInstance(){
		if(association_d_t_instance == null){
			association_d_t_instance = new AssociationDataTransfer();
		}
		return association_d_t_instance;
	}
	
	// Association 
	/**
	 * methode show tables
	 */
	public boolean showAssociationTable(){
		AssociationDB derby = new AssociationDB();
		return derby.showTables();
	}
	
	/**
	 * methode init date
	 * 
	 * @param initDate
	 */
	public void initStatusDate(String initDate){
		AssociationDB derby = new AssociationDB();
		derby.initStatusDate(initDate);
	}
	
	/**
	 * ok methode
	 */
	public void ok(){
		setChanged();  
		notifyObservers();
	}
	
	/**
	 * methode to save a new association object
	 * 
	 * @param association_object
	 */
	public void insertAssociation(Association association_object){
			
		AssociationDB derby = new AssociationDB();
		derby.createTable();
		derby.insertAssociation(association_object);
	}
	
	/**
	 * methode to get an association object
	 * 
	 * @return association object
	 */
	public Association getAssociation(){
	
		AssociationDB derby = new AssociationDB();
	    this.association_object = derby.getAssociation();
	    return association_object;
	}
	
	/**
	 * update association basic data
	 * 
	 * @param association_object
	 */
	public void updateAssociationData(Association association_object){
		
		AssociationDB derby = new AssociationDB();
		derby.updateAssociationData(association_object);
		setChanged();  
		notifyObservers();
	}
	
	/**
	 * update association basic data
	 * 
	 * @param association_object
	 */
	public void updateBookingDate(String bookingdate){
		
		AssociationDB derby = new AssociationDB();
		derby.updateAssociationBookingDay(bookingdate);
	}
	
	/**
	 * update association group
	 * 
	 * @param association_object
	 */
	public void updateAssociationGroup(Association association_object){
		
		AssociationDB derby = new AssociationDB();
		derby.updateAssociationGroup(association_object);
		setChanged();  
		notifyObservers();
	}
	
	/**
	 * update association admin
	 * 
	 * @param association_object
	 */
	public void updateAssociationAdmin(Association association_object){
		
		AssociationDB derby = new AssociationDB();
		derby.updateAssociationAdmin(association_object);
		setChanged();  
		notifyObservers();
	}
	
	/**
	 * update association finance
	 * 
	 * @param association_object
	 */
	public void updateAssociationBudget(Association association_object){
		
		AssociationDB derby = new AssociationDB();
		derby.updateAssociationBudget(association_object);
		setChanged();  
		notifyObservers();
	}
	
	// Member
	
	/**
	 * methode save a new member
	 */
	public void insertMember(Member association_member){
        MemberDB member_db = new MemberDB();
        member_db.insertMember(association_member);
        setChanged();
		notifyObservers();
	}
	
	/**
	 * methode deletes a member
	 * 
	 * @param userID
	 */
	public void deleteMember(int userID){
        MemberDB member_db = new MemberDB();
        member_db.deleteMember(userID);
		setChanged();  
		notifyObservers();
	}
	
	/**
	 * methode updates a member
	 * 
	 * @param association_member
	 */
	public void updateMember(Member association_member){
		MemberDB memDB = new MemberDB();
		memDB.updateMember(association_member);
	}
	
	/**
	 * methode updates a contribution did attribute
	 * 
	 * @param cd
	 * @param user_id
	 */
	public void updateContributionDid(int cd, int user_id){
        MemberDB member_db = new MemberDB();
        member_db.updateContributionDid(cd, user_id);
	}
	
	/**
	 * methode updates a contribution 
	 * 
	 * @param contribution
	 * @param user_id
	 */
	public void updateContributionMember(double contribution, int user_id){
		MemberDB memDB = new MemberDB();
		memDB.updateMemberContribution(contribution, user_id);
	}
	
	/**
	 * methode updates a member state
	 * 
	 * @param state
	 * @param user_id
	 */
	public void updateStateMember(int state, int user_id){
		MemberDB memDB = new MemberDB();
		memDB.updateMemberStatus(state, user_id);
		setChanged();  
		notifyObservers();
	}
	
	/**
	 * methode updates a member status
	 * 
	 * @param state
	 * @param user_id
	 */
	public void updateStatusMember(int state, int user_id){
		MemberDB memDB = new MemberDB();
		memDB.updateMemberStatus(state, user_id);
	}
	
	/**
	 * methode return a linkedhashmap with members
	 * 
	 * @param value
	 * @param lastname
	 * @return
	 */
	public LinkedHashMap<Integer, Member> getMember(int value, String lastname){
		
        MemberDB derby = new MemberDB();
        
        if (value > 2){
        	association_member_map = derby.getMemberFromDB(value-3, null);
        }else if(value == -5){
        	association_member_map = derby.getMemberFromDB(-5, null);
        }else if(value == -7){
        	association_member_map = derby.getMemberFromDB(-7, null);
        }else if(value == 1){
        	association_member_map = derby.getMemberFromDB(-2, null);
        }else if(value == 2){
        	association_member_map = derby.getMemberFromDB(-3, null);
        }else if(value == -8){
        	association_member_map = derby.getMemberFromDB(-8, lastname);
        }else if(value == -9){
        	association_member_map = derby.getMemberFromDB(-9, null);
        }else if(value == -10){
        	association_member_map = derby.getMemberFromDB(-10, lastname);
        }else{
        	association_member_map = derby.getMemberFromDB(-1, null);
        }
		return association_member_map;
	}
	
	/**
	 * methode select member 
	 * 
	 * @param kto
	 * @param blz
	 * @return
	 */
	public Member getMember(long kto, long blz){
		MemberDB derby = new MemberDB();
		member = derby.getMember(kto, blz);
		return member;
	}
	
	/**
	 * methode select member 
	 * 
	 * @param firstname
	 * @param lastname
	 * @param kto
	 * @param blz
	 * @return
	 */
	public Member getMember(String firstname, String lastname, long kto, long blz){
		MemberDB derby = new MemberDB();
		member = derby.getMemberByName(firstname, lastname, kto, blz);
		return member;
	}
	
	/**
	 * methode select member 
	 * 
	 * @param firstname
	 * @param lastname
	 * @param kto
	 * @param blz
	 * @return
	 */
	public Member getMemberByID(int mem){
		MemberDB derby = new MemberDB();
		member = derby.getMemberById(mem);
		return member;
	}
	
	/**
	 * methode return member map
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public LinkedHashMap<Integer, Member> getMemberListForBooking(Date start, Date end){
        MemberDB derby = new MemberDB();
        association_member_map = derby.getMemberListForBooking(start, end);
        return association_member_map;
	}
	
	/**
	 * methode update contrubution
	 * 
	 * @param member_id
	 * @param booking_date
	 */
	public void updateMemberSpecialContribution(int member_id, Date booking_date){
		
		MemberDB donatorDB = new MemberDB();
		donatorDB.updateSpecialContribution(member_id, booking_date);
		setChanged();  
		notifyObservers();
	}
	
	/**
	 * methode returns a special contribution
	 */
	public SpecialContribution getSpecialContribution(int user_id){
		MemberDB memDB = new MemberDB();
		special_contribution = memDB.getSpecialContribution(user_id);
		
		return special_contribution;
	}
	
	// Receipt
	/**
	 * methode gets receipts
	 */
	public LinkedHashMap<Integer, Receipt> getReceipt(){
		ReceiptDB receiptdb = new ReceiptDB();
		association_receipt_map = receiptdb.getReceipt();
		return association_receipt_map;
	}
	
	/**
	 * methode set receipt
	 * 
	 * @param new_receipt
	 * @throws SQLException
	 */
	public void updateReceipt(Receipt new_receipt) throws SQLException{
		ReceiptDB receiptdb = new ReceiptDB();
		receiptdb.updateReceipt(new_receipt);
		setChanged();  
		notifyObservers();
	}
	
	/**
	 * methode set receipt
	 * 
	 * @param new_receipt
	 * @throws SQLException
	 */
	public void setReceipt(Receipt new_receipt) throws SQLException{
		ReceiptDB receiptdb = new ReceiptDB();
		receiptdb.insertReceipt(new_receipt);
		setChanged();  
		notifyObservers();
	}
	
	/**
	 * methode get date of a receipt by group
	 * 
	 * @param group
	 * @return
	 */
	public String getReceiptDate(String group){
		ReceiptDB receiptdb = new ReceiptDB();
		return receiptdb.getReceiptDate(group);
	}
	
	// Mail
	/**
	 * methode get mail
	 */
	public LinkedHashMap<Integer, Mail> getMail(){
		MailDB maildb = new MailDB();
		association_mail_map = maildb.getMail();
		return association_mail_map;
	}
	
	/**
	 * methose set mail
	 * 
	 * @param newmail
	 * @throws SQLException
	 */
	public void setMail(Mail newmail) throws SQLException{
		MailDB maildb = new MailDB();
		maildb.insertMail(newmail);
		setChanged();  
		notifyObservers();
	}
	
	// Donator 
	/**
	 * methode to insert a new donator 
	 * 
	 * @param association_donator
	 */
	public void insertNewDonator(DonatorWithAdress association_donator){
		DonatorDB memDB = new DonatorDB();
		memDB.insertNewDonator(association_donator);
		setChanged();  
		notifyObservers();
	}
	
	/**
	 * methode to get a donator
	 * 
	 * @param kto
	 * @param blz
	 * @return
	 */
	public DonatorWithAdress getDonator(long kto, long blz){
		
		DonatorDB derby = new DonatorDB();
		donator_wa = derby.getDonator(kto, blz);
		return donator_wa; 
	}
	
	/**
	 * methode to get a donator by name and account data
	 * 
	 * @param fname
	 * @param lname
	 * @param kto
	 * @param blz
	 * @return
	 */
	public DonatorWithAdress getDonatorByName(String fname, String lname, long kto, long blz){
		
		DonatorDB derby = new DonatorDB();
		donator_wa = derby.getDonatorByName(fname, lname, kto, blz);
		return donator_wa; 
	}
	
	/**
	 * methode to get a donator map
	 * 
	 * @param select
	 * @param lastname
	 * @return
	 */
	public LinkedHashMap<Integer, DonatorWithAdress> getDonatorMap(int select, String lastname){
        DonatorDB derby = new DonatorDB();
        association_donator_map = derby.getDonatorFromDB(select, lastname);
        return association_donator_map;
	}
	
	/**
	 * methode update donator
	 * 
	 * @param association_donator
	 */
	public void updateDonator(DonatorWithAdress association_donator){
		
		DonatorDB donatorDB = new DonatorDB();
		donatorDB.updateDonator(association_donator);
		setChanged();  
		notifyObservers();
	}
	
	/**
	 * methode deletes a donator
	 * 
	 * @param donatorID
	 */
	public void deleteDonator(int donatorID){
        DonatorDB donator_db = new DonatorDB();
        donator_db.deleteDonator(donatorID);
		setChanged();  
		notifyObservers();
	}
	
	// Group
	/**
	 * methode get group
	 */
	public LinkedList<Group> getGroup(){
		AssociationDB association_group = new AssociationDB();
		group_list = association_group.getGroup();
		return group_list;
	}
}
