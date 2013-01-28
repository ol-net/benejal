
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
//import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import association.view.InfoDialog;

import member.model.Member;
//import moneybook.model.Beitrag;
import moneybook.model.KassenBuch;

/**
 * class represents Observer and handles operations for booking
 * 
 * @author Leonid Oldenburger
 */
public class BookingDay implements Observer{
	
	private Association association_object;
	private int days;
	private LinkedHashMap<Integer, Member> member_map;
	private Member member;
	
	private AssociationDataTransfer association_data_transfer;
	private KassenBuch money_book;
	//private List<Beitrag> list;
	
	private static final int year = 12;
	private static final int halfyear = 6;
	private static final int fourthyear = 3;
	private static final int month = 1;
	static final long ONE_HOUR = 60 * 60 * 1000L;

	private Date datenow;
	private Date date;
	private DateFormat df;
	
	private Date start_date;
	private Date end_date;
	
	private Date status_date;
	
	private Date tmp;
	
	/**
	 * constructor creates a new booking object
	 * 
	 * @param adatatrans
	 * @param association
	 * @param moneyBook
	 */
	public BookingDay(AssociationDataTransfer adatatrans, Association association, KassenBuch moneyBook){
		
		this.association_data_transfer = adatatrans;
		this.association_object = association;
		this.money_book = moneyBook;
		this.association_data_transfer.addObserver(this);
		this.money_book.addObserver(this);
		
		datenow = new Date();
		date = new Date();
		df = new SimpleDateFormat("dd.MM.yyyy"); 
		if(association_object.getPamentTime() != null){
			try{
				date = df.parse(association_object.getPamentTime());             
			} catch (ParseException e) { 
			} 
		}else{
			date = new Date();
		}
		
		days = (int) daysBetween(datenow, date);
		// init Bookingdate
		if (association_data_transfer.showAssociationTable()){

			start_date = new Interval(association_data_transfer).getStartDate();

			tmp = start_date;

			getBookingDay();
			
			start_date = new Interval(association_data_transfer).getStartDate();
			end_date = new Interval(association_data_transfer).getEndDate();
			status_date = new Interval(association_data_transfer).getStatusDate();
			
			if(start_date.getTime() > status_date.getTime() || status_date == null || end_date.getTime() < status_date.getTime()){
				association_data_transfer.initStatusDate(df.format(datenow));
				initBookingStatus();
				
				//initContribution();
				//System.out.println("neue Periode");
			}
			//initContribution();
			checkToDeleteMember();
		}
	}
    
	/**
	 * methode check if user has to be deleted
	 */
	public void checkToDeleteMember(){

		this.member_map = association_data_transfer.getMember(-9, null);
		
		/**
		 * get Collection of values contained in HashMap using
		 * Collection values() method of HashMap class
		 */
		Iterator<Map.Entry<Integer,Member>> i = member_map.entrySet().iterator();
		
		while (i.hasNext() ) {
			
			Map.Entry<Integer, Member> entry = i.next();
			member = entry.getValue();
			
			Date date_1 = null;
			Date date_2 = null;

			try{
				if(association_data_transfer.getReceiptDate("Mitglieder") != null){
					date_1 = df.parse(association_data_transfer.getReceiptDate("Mitglieder"));             
				}
			} catch (ParseException e) {} 

			try{
				if(association_data_transfer.getReceiptDate(association_data_transfer.getAssociation().getGroupList().get(member.getGroupID()).getGroup()) != null){
					date_2 = df.parse(association_data_transfer.getReceiptDate(association_data_transfer.getAssociation().getGroupList().get(member.getGroupID()).getGroup()));             
				}
			} catch (ParseException e) {} 

			Date tmp = new Date();
			
			if(date_1 != null || date_2 != null){

				if(date_1 == null){
					tmp = date_2;
				}else if(date_2 == null){
					tmp = date_1;
				}else{
					if(date_1.getTime() > date_2.getTime()){
						tmp = date_1;
					}else{
						tmp = date_2;
					}
				}

				if(new Interval(association_data_transfer).getStartDate().getTime() 
						<= tmp.getTime() && 
						new Interval(association_data_transfer).getEndDate().getTime() 
						>= tmp.getTime() && member.getSumContribution() == 0){
					new InfoDialog("Das Mitglied ("+member.getFirstName()+" "+member.getLastName()+") wird gelöscht!");
					association_data_transfer.deleteMember(member.getNumber());
				}
			}
		}
	}
	
	/**
	 * methode inits a new booking status
	 */
	public void initBookingStatus(){
		
		this.member_map = association_data_transfer.getMember(-6, null);
		
		/**
		 * get Collection of values contained in HashMap using
		 * Collection values() method of HashMap class
		 */
		Iterator<Map.Entry<Integer,Member>> i = member_map.entrySet().iterator();
		
		while (i.hasNext() ) {
			Map.Entry<Integer, Member> entry = i.next();
			member = entry.getValue();
			
			double dif = (int) daysBetween(tmp, datenow);
			
			if(dif < 0){
				dif = 0;
			}
			
			int x = (int)dif / 30;

			x = x / getPayRhythm(association_data_transfer.getAssociation().getPaymentRhythm());
						
			double value = member.getSumContribution() + member.getContribution() * x;

			association_data_transfer.updateContributionMember(value, member.getNumber());

			association_data_transfer.updateStatusMember(Member.FAELLIG, member.getNumber());
			association_data_transfer.updateContributionDid(0, member.getNumber());
		}
	}
	
	/**
	 * methode to set the member status
	 */
	public void setMemberStatus(){
		member.setPayStatus(Member.FAELLIG);
		association_data_transfer.updateMember(member);
		association_data_transfer.ok();
	}
	
	/**
	 * methode returns booking day
	 * 
	 * @return days
	 */
	public int getBookingDay(){
		
		while (days < 0){
			
	        SimpleDateFormat dateformatMMDDYYYY = new SimpleDateFormat("dd.MM.yyyy");
	        StringBuilder MMDDYYYY = new StringBuilder(dateformatMMDDYYYY.format(getBookingNewDate(getPayRhythm(association_data_transfer.getAssociation().getPaymentRhythm()))));
	          
	        association_data_transfer.updateBookingDate(MMDDYYYY.toString());

			if(association_object.getPamentTime() != null){
				try{
					date = df.parse(association_data_transfer.getAssociation().getPamentTime());             
				} catch (ParseException e) { 
				} 
			}else{
				date = new Date();
			}
	        
			start_date = new Interval(association_data_transfer).getStartDate();
			end_date = new Interval(association_data_transfer).getEndDate();
			status_date = new Interval(association_data_transfer).getStatusDate();
			
	        days = (int) daysBetween(datenow, date); 
		}
		return days;
	}
	
	/**
	 * methode calculate the difference between to dates
	 * 
	 * @param d1
	 * @param d2
	 * @return difference
	 */
    public long daysBetween(Date d1, Date d2){
        return ((d2.getTime() - d1.getTime() + ONE_HOUR) / (ONE_HOUR * 24));
    }
	
    /**
     * methode return payrhythm in month
     * 
     * @param value
     * @return
     */
    public int getPayRhythm(int value){
    	
    	if (value == 0){
    		
    		return year;
    		
    	}else if(value == 1){
    		
    		return halfyear;
    		
    	}else if(value == 2){
    		
    		return fourthyear;
    		
    	}else if(value == 3){
    		
    		return month;
    	}
		return 0;
    }
    
    /**
     * methode returns a map with members
     * 
     * @return
     */
    public LinkedHashMap<Integer, Member> getMemberMap(){
		
    	if(days <= 14){
    		return association_data_transfer.getMember(-5, null);
    	}else{
        	return new LinkedHashMap<Integer, Member>();
    	}
    }
    
    /**
     * methode returns a mapsize
     * 
     * @return
     */
	public int getMemberNumber(){
		return association_data_transfer.getMember(-5, null).size();
	}
	
	/**
	 * get new booking date
	 * 
	 * @param d
	 * @return
	 */
	public Date getBookingNewDate(int d){
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH,d);
		date = calendar.getTime();

		return date;
	}
	
	/**
	 * methode returns new booking date
	 * 
	 * @return
	 */
	public Date getBookingDate(){
		return date;
	}
	
//	/**
//	 * init contribution
//	 */
//	public void initContribution(){
//		
//		member_map = association_data_transfer.getMember(-1, null);
//		
//		/**
//		 * get Collection of values contained in HashMap using
//		 * Collection values() method of HashMap class
//		 */
//		Iterator<Map.Entry<Integer,Member>> i = member_map.entrySet().iterator();
//
//		double periodsum = 0;
//		while (i.hasNext() ) {
//			Map.Entry<Integer, Member> entry = i.next();
//			member = entry.getValue();
//			periodsum = getPeriodSum(member.getNumber());
//
//			if(member.getSumContribution() - periodsum <= 0){
//
//				association_data_transfer.updateContributionMember(0.0, member.getNumber());
//				association_data_transfer.updateContributionDid(0, member.getNumber());
//				association_data_transfer.updateStatusMember(Member.BEZAHLT, member.getNumber());
//			}else{
//				association_data_transfer.updateContributionMember(member.getSumContribution() - periodsum, member.getNumber());
//				if (member.getPayStatus() == Member.BEZAHLT && member.getStatus() == Member.FEST){
//					setMemberStatus();
//				} else if (member.getPayStatus() == Member.EMPTY && member.getStatus() == Member.FEST){
//					setMemberStatus();
//				}
//			}
//		}
////		end = new java.util.Date();
////		dif = end.getSeconds() - start.getSeconds();
////		System.out.println("InitSchleifeDif: "+ dif);
//	}
	
//	/**
//	 * methode get periode sum from member
//	 * 
//	 * @param number
//	 * @return
//	 */
//	public Double getPeriodSum(int number){
//		
//		if(association_object.getPamentTime() != null){
//			try{
//				date = df.parse(association_data_transfer.getAssociation().getPamentTime());             
//			} catch (ParseException e) { 
//			} 
//		}else{
//			date = new Date();
//		}
//        
//		start_date = new Interval(association_data_transfer).getStartDate();
//		end_date = new Interval(association_data_transfer).getEndDate();
//		status_date = new Interval(association_data_transfer).getStatusDate();
//
//		double sum = 0;
//		int i;
//		int contribution_did = 0;
//		list = money_book.getMemberPremiums(member.getNumber());
//		//Collections.reverse(list);
//		
//		for (i= 0; i < money_book.getMemberPremiums(number).size(); i++){
////			System.out.println("Startdatum: "+start_date);
////			System.out.println("Enddatum: "+end_date);
////			System.out.println("Buchungsdatum: "+association_data_transfer.getAssociation().getPamentTime());
////			System.out.println("booking date: " + money_book.getMemberPremiums(number).get(i).getDatum());
////			System.out.println(member.getContributionDid()+ " und " + i);
//
//			if (start_date.getTime() <= money_book.getMemberPremiums(number).get(i).getBookingDate().getTime() && 
//				end_date.getTime() >= money_book.getMemberPremiums(number).get(i).getBookingDate().getTime() && 
//				member.getContributionDid() <= i){
//				sum = list.get(0).getBetrag();
//				contribution_did++;
//			}
//		}
//		association_data_transfer.updateContributionDid(contribution_did + member.getContributionDid(), member.getNumber());
//		return sum;
//	}

	/**
	 * update methode
	 */
	public void update(Observable arg0, Object arg1) {
		
		//initContribution();
	}
}