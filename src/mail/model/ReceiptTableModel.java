
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
package mail.model;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.table.DefaultTableModel;

import association.model.AssociationDataTransfer;

/**
 * class represents a reaceipt table
 * 
 * @author Leonid Oldenburger
 */
public class ReceiptTableModel extends DefaultTableModel{
	
	private static final long serialVersionUID = 1L;
	private LinkedHashMap<Integer, Receipt> association_receipt_map;
	private Receipt association_receipt;
	private AssociationDataTransfer association_data_transfer;
	
	/**
	 * constructor creates a reaceipt table
	 * 
	 * @param adatatrans
	 */
	public ReceiptTableModel(AssociationDataTransfer adatatrans){
		
		this.association_data_transfer = adatatrans;
		this.association_receipt_map = association_data_transfer.getReceipt();
		
		int size;
		
        if (association_receipt_map.size()<6){
        	size = 5;
        }else{
        	size = association_receipt_map.size();
        }
		
		String column[] = {"Bescheinigungs Nr.", "an Mitglieder/Spender", "erstellt am"};
		String row[][]= new String[size][4];
		
		/**
		 * get Collection of values contained in HashMap using
		 * Collection values() method of HashMap class
		 */
		Iterator<Map.Entry<Integer, Receipt>> i = association_receipt_map.entrySet().iterator();
		
		int counter = 0;
		int l = association_receipt_map.size();
		
		while (i.hasNext() ) {
			
			Map.Entry<Integer, Receipt> entry = i.next();
			association_receipt = entry.getValue();
			
			counter++;
			l--;
			if(counter < association_receipt_map.size()){
				row[counter-1][0] = Integer.toString(l);
				row[counter-1][1] = association_receipt.getMemberGroup();
				row[counter-1][2] = association_receipt.getDate();
			}
		}
		
		this.setDataVector(row, column);
	}
}