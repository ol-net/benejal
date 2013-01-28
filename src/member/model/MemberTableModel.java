
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

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.table.DefaultTableModel;

import moneybook.model.KassenBuch;

import association.model.AssociationDataTransfer;
import association.model.Interval;

/**
 * class represents member table
 * 
 * @author Leonid Oldenburger
 */
public class MemberTableModel extends DefaultTableModel  {
	
	private static final long serialVersionUID = 1L;
	private String scontribution = "Beitrag";
	private String hcontribution = "Bezahlt";
	private String zcontribution = "Gesamt";
	private String contributionstate = "Status";
	private String m_lname_label = "Nachname";
	private String m_fname_label = "Vorname";
	private String group = "Mitgliedergruppe";
	private String id = "Mitglieds Nr.";
	private DecimalFormat df;
	
	//private LinkedHashMap<Integer, Member> member_map;
	
	private LinkedHashMap<Integer, Member> association_member_map;
	private Member association_member;
	
	//private List<Beitrag> list;
	
	private AssociationDataTransfer association_data_transfer;
	
	private KassenBuch money_book;
	
	private Date start_date;
	private Date end_date;
	
	/**
	 * constructor creates member table
	 * 
	 * @param moneyBook
	 * @param adatatrans
	 * @param g
	 * @param lastname
	 */
	public MemberTableModel(KassenBuch moneyBook, AssociationDataTransfer adatatrans, int g, String lastname){
		
		this.association_data_transfer = adatatrans;
		association_member_map = association_data_transfer.getMember(g, lastname);
		this.money_book = moneyBook;
		
		this.start_date = new Interval(association_data_transfer).getStartDate();
		this.end_date = new Interval(association_data_transfer).getEndDate();
		
		//initContribution();
		
		int size;
		
        if (association_member_map.size()<25){
        	size = 24;
        }else{
        	size = association_member_map.size();
        }
        
		df = new DecimalFormat("#0.00");
        
		String column[] = {id, m_fname_label, m_lname_label, group, scontribution, contributionstate, hcontribution, zcontribution};
		String row[][]= new String[size][8];
		
		/**
		 * get Collection of values contained in HashMap using
		 * Collection values() method of HashMap class
		 */
		Iterator<Map.Entry<Integer,Member>> i = association_member_map.entrySet().iterator();

		int counter = 0;
		while (i.hasNext() ) {
			Map.Entry<Integer, Member> entry = i.next();
			association_member = entry.getValue();
			row[counter][0] = Integer.toString(10000 + association_member.getNumber()).replaceFirst("1", "0");
			row[counter][1] = association_member.getFirstName();
			row[counter][2] = association_member.getLastName();
			row[counter][3] = association_member.getMemberGroup().getGroup();

			row[counter][4] = df.format(association_member.getSumContribution());
			if (association_member.getPayStatus() == 0){
				row[counter][5] = "fällig";
			}else if((association_member.getPayStatus() == 1)){
				row[counter][5] = "bearbeitet";
			}else if((association_member.getPayStatus() == 2)){
				row[counter][5] = "bezahlt";
			}else{
				row[counter][5] = "";
			}
			row[counter][6] = getPeriodSum(association_member.getNumber());
			row[counter][7] = getSum(association_member.getNumber());
			counter++;
		}
		
		this.setDataVector(row, column);
	}
	
	/**
	 * methode returns periode contribution sum 
	 * 
	 * @param number
	 * @return contribution sum
	 */
	public String getPeriodSum(int number){
		
		double sum = 0;
		
		for (int i= 0; i < money_book.getMemberPremiums(number).size(); i++){
//			System.out.println(start_date);
//			System.out.println(end_date);
//			System.out.println(money_book.getMemberPremiums(number).get(i).getDatum());
			//System.out.println(association_member.getContributionDid()+ " und2 " + i);
			
			if (start_date.getTime() <= money_book.getMemberPremiums(number).get(i).getDatum().getTime() && 
				end_date.getTime() >= money_book.getMemberPremiums(number).get(i).getDatum().getTime()){
				sum += money_book.getMemberPremiums(association_member.getNumber()).get(i).getBetrag();
			}
		}
		
		return df.format(sum);
	}
	
	/**
	 * methode returns contribution sum
	 * 
	 * @param number
	 * @return contribution sum
	 */
	public String getSum(int number){
		
		double sum = 0;
		for (int i= 0; i < money_book.getMemberPremiums(number).size(); i++){
			sum += money_book.getMemberPremiums(association_member.getNumber()).get(i).getBetrag();
		}
		return df.format(sum);
	}
	
	/**
	 * methode returns date as string
	 * 
	 * @param date
	 * @return date
	 */
	public String getDate(Date date){
		
        SimpleDateFormat dateformatMMDDYYYY = new SimpleDateFormat("dd.MM.yyyy");
        StringBuilder MMDDYYYY = new StringBuilder(dateformatMMDDYYYY.format(date));
        
		return MMDDYYYY.toString();
	}
	
//	/**
//	 * methode inits contribution
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
//			association_member = entry.getValue();
//			periodsum = getPSum(association_member.getNumber());
//			
//			if(association_member.getSumContribution() - periodsum <= 0){
//				association_data_transfer.updateContributionMember(0.0, association_member.getNumber());
//				association_data_transfer.updateContributionDid(0, association_member.getNumber());
//				association_data_transfer.updateStatusMember(Member.BEZAHLT, association_member.getNumber());
//			}else{
//				
//				association_data_transfer.updateContributionMember(association_member.getSumContribution() - periodsum, association_member.getNumber());
////				System.out.println("sum: " + association_member.getSumContribution());
////				System.out.println("periode: "+ periodsum);
//				if (association_member.getPayStatus() == Member.BEZAHLT && association_member.getStatus() == Member.FEST){
//					setMemberStatus();
//				} else if (association_member.getPayStatus() == Member.EMPTY && association_member.getStatus() == Member.FEST){
//					setMemberStatus();
//				}	
//				
//			}
//		}
//	}
//
//	/**
//	 * methode sets an new member status
//	 */
//	public void setMemberStatus(){
//		association_member.setPayStatus(Member.FAELLIG);
//		association_data_transfer.updateMember(association_member);
//	}
//	
//	/**
//	 * methode returns contribution sum
//	 * 
//	 * @param number
//	 * @return contribution sum
//	 */
//	public Double getPSum(int number){
//		
//		double sum = 0;
//		int i;
//		int contribution_did = 0;
//		
//		list = money_book.getMemberPremiums(association_member.getNumber());
//		Collections.reverse(list);
//		
//		for (i= 0; i < money_book.getMemberPremiums(number).size(); i++){
////			System.out.println("2Startdatum: "+getDate(start_date));
////			System.out.println("2Enddatum: "+getDate(end_date));
////			System.out.println("2Buchungsdatum: "+association_data_transfer.getAssociation().getPamentTime());
////			System.out.println("2booking date: " + getDate(money_book.getMemberPremiums(number).get(i).getDatum()));
//			System.out.println(association_member.getContributionDid()+ " und2 " + i);
//			if (start_date.getTime() <= money_book.getMemberPremiums(number).get(i).getDatum().getTime() && end_date.getTime() >= money_book.getMemberPremiums(number).get(i).getDatum().getTime() && association_member.getContributionDid() <= i){
//				System.out.println("ok2");
//				sum += list.get(i).getBetrag();
//				contribution_did++;
//			}
//		}
//	
//		
//		association_data_transfer.updateContributionDid(contribution_did + association_member.getContributionDid(), association_member.getNumber());
//		return sum;
//	}
}
