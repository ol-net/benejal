
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
package payments.model;

import java.text.SimpleDateFormat;
import java.util.Collection;

import java.util.LinkedHashMap;

import javax.swing.table.AbstractTableModel;

import member.model.Member;
import moneybook.model.KassenBuch;
import association.model.AssociationDataTransfer;
import association.model.BookingDay;
import association.model.BookingDayDonator;


/**
 * Tabellen-Modell der Beitrags-Tabelle
 * @author Artem Petrov
 *
 */
@SuppressWarnings("serial")
public class BeitraegeTableModel extends AbstractTableModel {

	private String[] columnNames = {"Name", "Betrag", "Typ", "Fällig am"};
	private LinkedHashMap<Integer, Member> memberMap;
	private LinkedHashMap<Integer, Member> special_donation_form_member_Map;
	private Object[][] data;
	private String bookDatePremiums;
	
	/**
	 * Erzeugt ein Tabellen-Modell
	 * @param associationDataTransfer AssociationDataTransfer
	 * @param moneybook Kassenbuch
	 */
	public BeitraegeTableModel(AssociationDataTransfer associationDataTransfer, KassenBuch moneybook) {
		
		BookingDay bd = new BookingDay(associationDataTransfer, associationDataTransfer.getAssociation(), moneybook);
		BookingDayDonator bdd = new BookingDayDonator(associationDataTransfer, moneybook);
		
		this.memberMap = bd.getMemberMap();
		this.special_donation_form_member_Map = bdd.getMemberMap();
		
		 
		//SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		
//		Date day = associationDataTransfer.getAssociation();
//		
//		if(day != null)
			this.bookDatePremiums = associationDataTransfer.getAssociation().getPamentTime();
		
		
		this.data = new Object[memberMap.size() + special_donation_form_member_Map.size()][getColumnCount()];
		
		
		fillData();

		
	}
	
	private void fillData() {
		
		Collection<Member> members = memberMap.values();
		
		Collection<Member> donators = special_donation_form_member_Map.values();
		
		int i = 0;
		
		for (Member m : members) {
			data[i][0] = m.getFirstName() + " " + m.getLastName();
			data[i][1] = m.getMemberGroup().getPremium().getVlaue();
			data[i][2] = "Mitgliedsbeitrag";
			data[i][3] = bookDatePremiums;
			i++;
		}
		
		SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		
		for (Member d : donators) {
			data[i][0] = d.getFirstName() + " " + d.getLastName();
			data[i][1] = d.getSpecialContribution().getVlaue();
			data[i][2] = "Spende";
			data[i][3] = df.format(d.getSpecialContribution().getSpecialPremiumDate());;
			i++;
		}

	}


	public int getColumnCount() {
		return 4;
	}
	
	public String getColumnName(int col) {
        return columnNames[col];
    }

	public int getRowCount() {
		return data.length;
	}


	public Object getValueAt(int row, int col) {
		return data[row][col];
	}

}
