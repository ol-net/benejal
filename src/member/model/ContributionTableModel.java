
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
import java.util.List;

import javax.swing.table.DefaultTableModel;

import moneybook.model.Beitrag;
import moneybook.model.KassenBuch;
import moneybook.model.Spende;

/**
 * class represents contribution table
 * 
 * @author Leonid Oldenburger
 */
public class ContributionTableModel extends DefaultTableModel{
	

	private static final long serialVersionUID = 1L;
	private List<Beitrag> paylist;
	private List<Spende> donation_list;
	private int member_id;
	private DecimalFormat df;
	
	/**
	 * constructor creates contribution table
	 * 
	 * @param moneyBook
	 * @param memberID
	 */
	public ContributionTableModel(KassenBuch moneyBook, int memberID){
		
		this.member_id = memberID;
		this.paylist = moneyBook.getMemberPremiums(member_id);
		this.donation_list = moneyBook.getDonations(member_id);
		
		int size;
		
        if (paylist.size() + donation_list.size() < 25){
        	size = 24;
        }else{
        	size = paylist.size() + donation_list.size();
        }
		
		df = new DecimalFormat("#0.00");
        
		String column[] = {"Nummer", "Beitrag/Spende", "Typ", "Datum"};
		String row[][]= new String[size][4];
		
		int counter = 1;
		int b_index = 0;
		int s_index = 0;
		
		while(paylist.size() + donation_list.size() != counter-1) {
			
			if(paylist.size() != b_index && donation_list.size() != s_index){ 
			
				if(paylist.get(b_index).getDatum().getTime() > donation_list.get(s_index).getDatum().getTime()) {
						
						row[counter-1][0] = new Integer(counter).toString();
						row[counter-1][1] = df.format(paylist.get(b_index).getBetrag());
						row[counter-1][2] = "Beitrag";
					
						SimpleDateFormat dateformatMMDDYYYY = new SimpleDateFormat("dd.MM.yyyy");
						StringBuilder MMDDYYYY = new StringBuilder(dateformatMMDDYYYY.format(paylist.get(b_index).getDatum()));
		   
						row[counter-1][3] = MMDDYYYY.toString();
						b_index++;
				}else{
					
						row[counter-1][0] = new Integer(counter).toString();
						row[counter-1][1] = df.format(donation_list.get(s_index).getBetrag());
						row[counter-1][2] = "Spende";
				
						SimpleDateFormat dateformatMMDDYYYY = new SimpleDateFormat("dd.MM.yyyy");
						StringBuilder MMDDYYYY = new StringBuilder(dateformatMMDDYYYY.format(donation_list.get(s_index).getDatum()));
	   
						row[counter-1][3] = MMDDYYYY.toString();
						s_index++;
				}
			}else if(paylist.size() != b_index && donation_list.size() == s_index){
				
				row[counter-1][0] = new Integer(counter).toString();
				row[counter-1][1] = df.format(paylist.get(b_index).getBetrag());
				row[counter-1][2] = "Beitrag";
			
				SimpleDateFormat dateformatMMDDYYYY = new SimpleDateFormat("dd.MM.yyyy");
				StringBuilder MMDDYYYY = new StringBuilder(dateformatMMDDYYYY.format(paylist.get(b_index).getDatum()));
   
				row[counter-1][3] = MMDDYYYY.toString();
				b_index++;
			
			}else if(paylist.size() == b_index && donation_list.size() != s_index){
				
				row[counter-1][0] = new Integer(counter).toString();
				row[counter-1][1] = df.format(donation_list.get(s_index).getBetrag());
				row[counter-1][2] = "Spende";
		
				SimpleDateFormat dateformatMMDDYYYY = new SimpleDateFormat("dd.MM.yyyy");
				StringBuilder MMDDYYYY = new StringBuilder(dateformatMMDDYYYY.format(donation_list.get(s_index).getDatum()));
				
				row[counter-1][3] = MMDDYYYY.toString();
				s_index++;
			}
			counter++;
		}
		this.setDataVector(row, column);
	}
}
