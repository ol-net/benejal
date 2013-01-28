
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

import moneybook.model.KassenBuch;
import moneybook.model.Spende;

/**
 * class represents donation table
 * 
 * @author Leonid Oldenburger
 */
public class DonationTableModel extends DefaultTableModel{
	
	private static final long serialVersionUID = 1L;
	private List<Spende> donation_list;
	private int donator_id;
	private DecimalFormat df;
	
	/**
	 * constructor creates table for contribution
	 * 
	 * @param moneyBook
	 * @param donatorID
	 */
	public DonationTableModel(KassenBuch moneyBook, int donatorID){
		
		this.donator_id = donatorID;
		this.donation_list = moneyBook.getDonations(donator_id);
		
		int size;
		
        if (donation_list.size()<25){
        	size = 24;
        }else{
        	size = donation_list.size();
        }
		
		String column[] = {"Nummer", "Spende", "Datum", "für Projekte"};
		String row[][]= new String[size][4];
		
		int counter = 1;
		
		df = new DecimalFormat("#0.00");
		
		for (Spende item: donation_list) {
		   row[counter-1][0] = new Integer(counter).toString();
		   row[counter-1][1] = df.format(item.getBetrag());
	       
		   SimpleDateFormat dateformatMMDDYYYY = new SimpleDateFormat("dd.MM.yyyy");
	       StringBuilder MMDDYYYY = new StringBuilder(dateformatMMDDYYYY.format(item.getDatum()));
		   
	       row[counter-1][2] = MMDDYYYY.toString();
	       row[counter-1][3] = item.getProjekt();
	       counter++;
		}
		this.setDataVector(row, column);
	}
}
