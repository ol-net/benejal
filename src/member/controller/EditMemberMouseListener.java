
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

import member.model.Member;
import member.view.MemberAndDonatorFrame;
import member.view.MemberInformation;
import moneybook.model.KassenBuch;

/**
 * class represents MouseListener which edits the members
 * 
 * @author Leonid Oldenburger
 */
public class EditMemberMouseListener extends MouseAdapter{

	private JTable group_table;
	private HashMap<Integer, Member> association_member_map;
	private Member association_member;
	private MemberAndDonatorFrame mframe;
	private KassenBuch money_book;
	
	private AssociationDataTransfer association_data_transfer;
	
	/**
	 * constructor creates mouse listener 
	 * 
	 * @param mainf
	 * @param gTable
	 * @param a_member_map
	 * @param moneyBook
	 * @param adatatrans
	 */
	public EditMemberMouseListener(MemberAndDonatorFrame mainf, JTable gTable, HashMap<Integer, Member> a_member_map, KassenBuch moneyBook, AssociationDataTransfer adatatrans){
		
		this.mframe = mainf;
		this.group_table = gTable;
		this.association_member_map = a_member_map;
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
           
            if(row < association_member_map.size()){
            	int number = Integer.parseInt(((String)group_table.getValueAt(row, 0)).replaceFirst("0", "1"))-10000;
            	
            	association_member = association_member_map.get(number);
            	new MemberInformation(association_member, mframe, money_book, association_data_transfer);
            }
        }
    } 
}
