
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
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.table.DefaultTableModel;

import moneybook.model.KassenBuch;

import association.model.AssociationDataTransfer;

/**
 * class represents donator table
 * 
 * @author Leonid Oldenburger
 */
public class DonatorTableModel extends DefaultTableModel {
	
	private static final long serialVersionUID = 1L;
	private String contribution = "Spenden";
	private String m_lname_label = "Nachname";
	private String m_fname_label = "Vorname";
	private String id = "Spender Nr.";
	private String typ = "Spender";
	
	private AssociationDataTransfer association_data_transfer;
	
	private LinkedHashMap<Integer, DonatorWithAdress> association_donator_map;
	private DonatorWithAdress association_donator;
	private KassenBuch money_book;
	
	private DecimalFormat df;
	
	/**
	 * constructor creates table
	 * 
	 * @param moneyBook
	 * @param adatatrans
	 * @param value
	 * @param lastname
	 */
	public DonatorTableModel(KassenBuch moneyBook, AssociationDataTransfer adatatrans, int value, String lastname){
		
		this.association_data_transfer = adatatrans;
		association_donator_map = association_data_transfer.getDonatorMap(value, lastname);
		this.money_book = moneyBook;
		int size;
		df = new DecimalFormat("#0.00");
        if (association_donator_map.size()<25){
        	size = 24;
        }else{
        	size = association_donator_map.size();
        }
        
		String column[] = {id, m_fname_label, m_lname_label, typ, contribution};
		String row[][]= new String[size][5];
		
		/**
		 * get Collection of values contained in HashMap using
		 * Collection values() method of HashMap class
		 */
		Iterator<Map.Entry<Integer,DonatorWithAdress>> i = association_donator_map.entrySet().iterator();
		
		int counter = 0;
		while (i.hasNext() ) {
			Map.Entry<Integer, DonatorWithAdress> entry = i.next();
			association_donator = entry.getValue();
			row[counter][0] = Integer.toString(association_donator.getNumber()).replaceFirst("2", "0");
			row[counter][1] = association_donator.getFirstName();
			row[counter][2] = association_donator.getLastName();
			if(association_donator.getType() == 1){
				row[counter][3] = "bekannt";
			}else{
				row[counter][3] = "unbekannt";
			}
			row[counter][4] = getSum(association_donator.getNumber());
			counter++;
		}
		
		this.setDataVector(row, column);
	}
	
	/**
	 * methode returns contribution sum
	 * 
	 * @param number
	 * @return contribution sum
	 */
	public String getSum(int number){
		
		double sum = 0;
		for (int i= 0; i < money_book.getDonations(number).size(); i++){
			sum += money_book.getDonations(association_donator.getNumber()).get(i).getBetrag();
		}
		return df.format(sum);
	}
}
