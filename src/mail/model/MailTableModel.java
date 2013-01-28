
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
 * class represents mail table
 * 
 * @author Leonid Oldenburger
 */
public class MailTableModel extends DefaultTableModel{
	
	private static final long serialVersionUID = 1L;
	private LinkedHashMap<Integer, Mail> association_mail_map;
	private Mail association_mail;
	private AssociationDataTransfer association_data_transfer;
	
	/**
	 * constructor creates a mail table
	 * 
	 * @param adatatrans
	 */
	public MailTableModel(AssociationDataTransfer adatatrans){
		
		this.association_data_transfer = adatatrans;
		this.association_mail_map = association_data_transfer.getMail();
		
		int size;
		
        if (association_mail_map.size()<6){
        	size = 5;
        }else{
        	size = association_mail_map.size();
        }
		
		String column[] = {"Brief Nr.", "Betreff", "erstellt am", "an Mitglieder"};
		String row[][]= new String[size][4];
		
		/**
		 * get Collection of values contained in HashMap using
		 * Collection values() method of HashMap class
		 */
		Iterator<Map.Entry<Integer,Mail>> i = association_mail_map.entrySet().iterator();
		
		int counter = 0;
		while (i.hasNext() ) {
			Map.Entry<Integer, Mail> entry = i.next();
			association_mail = entry.getValue();
			row[counter][0] = Integer.toString(entry.getKey());
			row[counter][1] = association_mail.getSubject();
			row[counter][2] = association_mail.getMemberGroup();
			row[counter][3] = association_mail.getDate();
			counter++;
		}
		
		this.setDataVector(row, column);
	}
}
