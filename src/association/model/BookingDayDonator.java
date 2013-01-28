
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
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import member.model.Member;
import moneybook.model.KassenBuch;

/**
 * class represents an Observer and handles operations for booking
 * 
 * @author Leonid Oldenburger
 */
public class BookingDayDonator implements Observer{
	
	private Date start_date;
	private Date end_date;
	private Date booking_date;
	private Date bookingdate_for_donator;
	private Date nowDate;
	private int days;
	
	private static final int YEAR = 12;
	private static final int HALFYEAR = 6;
	private static final int FOURTHYEAR = 3;
	private static final int MONTH = 1;
	
	static final long ONE_HOUR = 60 * 60 * 1000L;
	
	private DateFormat df;
	
	private java.sql.Date startSqlDate;
	private java.sql.Date endSqlDate;
	
	private Calendar calendar;
	
	private SpecialContribution special_contribution;
	
	private AssociationDataTransfer association_data_transfer;
	private KassenBuch money_book;
	
	private Calendar nowDate_calendar;
	
	private LinkedHashMap<Integer, Member> member_map;
	
	//private DonatorWithAdress donator;
	private Member member;
	
	/**
	 * constructor 
	 * 
	 * @param adatatrans
	 * @param moneyBook
	 */
	public BookingDayDonator(AssociationDataTransfer adatatrans, KassenBuch moneyBook){
		
		this.association_data_transfer = adatatrans;
		this.money_book = moneyBook;
		this.association_data_transfer.addObserver(this);
		this.money_book.addObserver(this);
		this.nowDate = new Date();
		
		nowDate_calendar = Calendar.getInstance();
		nowDate_calendar.setTime(nowDate);
		
		df = new SimpleDateFormat("dd.MM.yyyy"); 
		
		if(association_data_transfer.getAssociation().getPamentTime() != null){
			try{
				booking_date = df.parse(association_data_transfer.getAssociation().getPamentTime());             
				//System.out.println("Today = " + df.format(date)); 
			} catch (ParseException e) { 
				//e.printStackTrace(); 
			} 
		}else{
			booking_date = nowDate;
		}
		
		calendar = Calendar.getInstance();
		calendar.setTime(booking_date);
		
		int booking_day = calendar.get(Calendar.DAY_OF_MONTH);
		int booking_month = nowDate_calendar.get(Calendar.MONTH) + 1;
		int year = nowDate_calendar.get(Calendar.YEAR);
		
		String next_booking = booking_day + "." + booking_month + "." + year;
		
		//System.out.println(" next booking day: " + next_booking);
		
		Date next_Date = new Date();
		
		try {
			next_Date = df.parse(next_booking);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		days = (int) daysBetween(nowDate, next_Date);
		
		startSqlDate = new java.sql.Date(getStartDate().getTime());
    	endSqlDate = new java.sql.Date(getEndDate().getTime());
		
    	initDonatorBookingDate();
    	
		//initBookingDay();
	}
	
	/**
	 * init new booking day for special contribution
	 * 
	 * @param specialContribution
	 * @return
	 */
	public Date initBookingDay(SpecialContribution specialContribution, int constant){
		
		this.special_contribution = specialContribution;
		
		int booking_day = calendar.get(Calendar.DAY_OF_MONTH);
		
		int payrhythm = getPayRhythm(special_contribution.getPayTime());
		int booking_month = nowDate_calendar.get(Calendar.MONTH) + constant;
		int year = nowDate_calendar.get(Calendar.YEAR);
		
		int new_booking_month;
		int new_booking_year;
		
		if(payrhythm + booking_month > 12){
			new_booking_month = payrhythm + booking_month - 12;
			new_booking_year = year + 1;
		}else{
			new_booking_month = payrhythm + booking_month;
			new_booking_year = year;
		}
		
		String bd = booking_day + "." + new_booking_month + "." + new_booking_year;
		//System.out.println(booking_month+" new booking day for donator: " + bd);
		
		try {
			this.bookingdate_for_donator = df.parse(bd);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		int booking_month = calendar2.get(Calendar.MONTH)+1;
		return bookingdate_for_donator;
	}
	
	/**
	 * methode returns a map with members
	 * 
	 * @return map
	 */
    public LinkedHashMap<Integer, Member> getMemberMap(){
		
    	if(days <= 14){
    		return association_data_transfer.getMemberListForBooking(startSqlDate, endSqlDate);
    	}else{
        	return new LinkedHashMap<Integer, Member>();
    	}
    }
    
    /**
     * methode returns donator number
     * 
     * @return
     */
	public int getDonatorNumber(){
		return association_data_transfer.getMemberListForBooking(startSqlDate, endSqlDate).size();
	}
	
	/**
	 * methode returns boking day
	 * 
	 * @return
	 */
	public int getBookingDay(){
		
		if (days < 0)
			days = 0;

		return days;
	}
	
	/**
	 * methode return start date
	 * 
	 * @return
	 */
	public Date getStartDate(){
		
		start_date = new Date();
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		
		String sdate = "1." + (nowDate_calendar.get(Calendar.MONTH) + 1) + "." + nowDate_calendar.get(Calendar.YEAR);
		
		//System.out.println("StartDate Intervall SpecialContribution: "+sdate);
		
		try{
			start_date = df.parse(sdate);             
		} catch (ParseException e) { 
		}
		
		return start_date;
	}
	
	/**
	 * methode return end date
	 * 
	 * @return
	 */
	public Date getEndDate(){
		
		end_date = new Date();
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		
		Calendar calendar_last_day = Calendar.getInstance();
		calendar_last_day.setTime(nowDate);
		calendar_last_day.set(Calendar.DAY_OF_MONTH, calendar_last_day.getActualMaximum(Calendar.DAY_OF_MONTH));
		
		String edate = calendar_last_day.get(Calendar.DAY_OF_MONTH) +"." + (nowDate_calendar.get(Calendar.MONTH) + 1) + "." + nowDate_calendar.get(Calendar.YEAR);
		
		//System.out.println("EndDate Intervall SpecialContribution: "+edate);
		
		try{
			end_date = df.parse(edate);             
		} catch (ParseException e) { 
		}
		
		return end_date;
	}
	
	/**
	 * methode returns difference betweer two dates
	 * 
	 * @param d1
	 * @param d2
	 * @return
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
	    	
		if (value == 4){ 		
	   		return YEAR;
	    }else if(value == 3){
	    	return HALFYEAR;		
	    }else if(value == 2){
	    	return FOURTHYEAR;	
	    }else if(value == 1){	
	    	return MONTH;
	    }
		return 0;
	}
	
	/**
	 * init booking date for each donator
	 */
	public void initDonatorBookingDate(){
		
		member_map = association_data_transfer.getMember(-7, null);
		
		/**
		 * get Collection of values contained in HashMap using
		 * Collection values() method of HashMap class
		 */
		Iterator<Map.Entry<Integer,Member>> i = member_map.entrySet().iterator();
		
		while (i.hasNext() ) {
			Map.Entry<Integer, Member> entry = i.next();
			member = entry.getValue();

			//System.out.println("COn: " +df.format(donator.getSpecialContribution().getSpecialPremiumDate()));          

			
			//System.out.println("STarte: "+df.format(startSqlDate.getTime()));
			if(member.getSpecialContribution().getSpecialPremiumDate().getTime() < startSqlDate.getTime()){
				
				SpecialContribution s_contribution = new SpecialContribution();
				s_contribution.setPaymentTime(member.getSpecialContribution().getPayTime());
				java.sql.Date sqlDate = new java.sql.Date(initBookingDay(s_contribution, 0).getTime());
				association_data_transfer.updateMemberSpecialContribution(member.getNumber(), sqlDate);
			
			}			
		}
	}
	
	/**
	 * init booking Date for special Contribution
	 * 
	 * @param aMember
	 */
	public void updateBookingDateForSpecialContribution(Member aMember){
		
		Member association_member = aMember;
		SpecialContribution s_contribution = new SpecialContribution();
		s_contribution.setPaymentTime(association_member.getSpecialContribution().getPayTime());
		java.sql.Date sqlDate = new java.sql.Date(initBookingDay(s_contribution, 1).getTime());
		association_data_transfer.updateMemberSpecialContribution(association_member.getNumber(), sqlDate);
	}
	
	/**
	 * update methode
	 */
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
}
