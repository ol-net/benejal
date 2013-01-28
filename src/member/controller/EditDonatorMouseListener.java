
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
package member.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.HashMap;

import javax.swing.JTable;

import association.model.AssociationDataTransfer;

import member.model.DonatorWithAdress;

import member.view.DonatorInformation;
import member.view.MemberAndDonatorFrame;
import moneybook.model.KassenBuch;

/**
 * class represents MouseListener which edits donators
 * 
 * @author Leonid Oldenburger
 */
public class EditDonatorMouseListener extends MouseAdapter{

	private JTable group_table;
	private HashMap<Integer, DonatorWithAdress> association_donator_map;
	private DonatorWithAdress association_donator;
	private MemberAndDonatorFrame mframe;
	
	private AssociationDataTransfer association_data_transfer;
	private KassenBuch money_book;

	/**
	 * constructor creates mouselistener
	 * 
	 * @param mainf
	 * @param gTable
	 * @param a_donator_map
	 * @param moneyBook
	 * @param adatatrans
	 */
	public EditDonatorMouseListener(MemberAndDonatorFrame mainf, JTable gTable, HashMap<Integer, DonatorWithAdress> a_donator_map, KassenBuch moneyBook, AssociationDataTransfer adatatrans){
		
		this.mframe = mainf;
		this.group_table = gTable;
		this.association_donator_map = a_donator_map;
		this.association_data_transfer = adatatrans;
		this.money_book = moneyBook;
	}
	
	/**
	 * methode handles mouse events
	 */
	public void mouseClicked(MouseEvent e) {
        if (e.getClickCount()==2) 
        {
            int row = group_table.getSelectedRow();
           
            if(row < association_donator_map.size()){
            	int number = Integer.parseInt(((String)group_table.getValueAt(row, 0)).replaceFirst("0", "2"));

            	association_donator = association_donator_map.get(number);  
            	new DonatorInformation(association_donator, mframe, money_book, association_data_transfer);
            }
        }
    } 
}
