
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

/**
 * class represents Interval
 * 
 * @author Leonid Oldenburger
 */
public class Interval {
	
	private static final int YEAR = 12;
	private static final int HALFYEAR = 6;
	private static final int FOURTHYEAR = 3;
	private static final int MONTH = 1;
	
	private AssociationDataTransfer association_data_transfer;
	
	private String start_string_date;
	private String end_string_date;
	private String status_date;
	
	/**
	 * constructor creates an interval object
	 * 
	 * @param adatatrans
	 */
	public Interval(AssociationDataTransfer adatatrans){
		
		association_data_transfer = adatatrans;
		
		int rhythm = association_data_transfer.getAssociation().getPaymentRhythm();
		
		Date bookingdate = new Date();
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy"); 
		
		try{
			bookingdate = df.parse(association_data_transfer.getAssociation().getPamentTime());             
				//System.out.println("Today = " + df.format(date)); 
		} catch (ParseException e) { 
				//e.printStackTrace(); 
		} 
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(bookingdate);
	
		int start_month = 1;
		int end_month = calendar.get(Calendar.MONTH)+1;
		int start_year = 1;
		int end_year = calendar.get(Calendar.YEAR);
//		
//		System.out.println("Monat: "+calendar.get(Calendar.MONTH));
//		System.out.println("Buchungsdatum: " + bookingdate);
//		System.out.println("Rhytmus:"+ getPayRhythm(rhythm));
		
		// Algorithmus
		// if(BD - PR + 2 >= 1)
		// 	  SM = BD - PR + 2
		//    SY = BDY 
		// else
		//    SM = 12 + BD - PR + 2
		// 	  SY = BDY - 1 
		if (calendar.get(Calendar.MONTH)- getPayRhythm(rhythm) + 2 >= 1){
			start_month = calendar.get(Calendar.MONTH) - getPayRhythm(rhythm) + 2;
			start_year = calendar.get(Calendar.YEAR);
		}else{
			start_month = 12 + calendar.get(Calendar.MONTH)- getPayRhythm(rhythm) + 2;
			start_year = calendar.get(Calendar.YEAR) - 1;
		}
		
		// last month ended on 
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(bookingdate);
		calendar2.set(Calendar.DAY_OF_MONTH, calendar2.getActualMaximum(Calendar.DAY_OF_MONTH));
		
		
		start_string_date = "01." + new Integer(start_month).toString() + "." + new Integer(start_year).toString();
		end_string_date = calendar2.get(Calendar.DAY_OF_MONTH) +"."+ new Integer(end_month) + "." + new Integer(end_year).toString();
		
		if(association_data_transfer.getAssociation().getInitDate() == null){
	       
			SimpleDateFormat dateformatMMDDYYYY = new SimpleDateFormat("dd.MM.yyyy");
	        StringBuilder MMDDYYYY = new StringBuilder(dateformatMMDDYYYY.format(new Date()));    
	        status_date = MMDDYYYY.toString();
		}else{
			status_date = association_data_transfer.getAssociation().getInitDate();
		}
//		System.out.println(start_string_date);
//		System.out.println(end_string_date);
	}
	
	/**
	 * methode returns the date in a periode
	 * 
	 * @return
	 */
	public Date getStatusDate(){
		Date statusdate = new Date();
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		
		try{
			statusdate = df.parse(status_date);             
		} catch (ParseException e) { 
		} 
		return statusdate; 
	}
	
	/**
	 * methode returns end date 
	 * 
	 * @return
	 */
	public Date getEndDate(){
		
		Date edate = new Date();
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy"); 
		
		try{
			edate = df.parse(end_string_date);             
		} catch (ParseException e) { 
		} 
		return edate; 
	}
	
	/**
	 * methode returns start date
	 * 
	 * @return
	 */
	public Date getStartDate(){
		
		Date sdate = new Date();
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy"); 
		
		try{
			sdate = df.parse(start_string_date);             
		} catch (ParseException e) { 
		} 
		return sdate; 
	}
	
	/**
	 * methode return payrhythm in month
	 * 
	 * @param value
	 * @return
	 */
    public int getPayRhythm(int value){
    	
    	if (value == 0){
    		
    		return YEAR;
    		
    	}else if(value == 1){
    		
    		return HALFYEAR;
    		
    	}else if(value == 2){
    		
    		return FOURTHYEAR;
    		
    	}else if(value == 3){
    		
    		return MONTH;
    	}
		return 0;
    }
}
