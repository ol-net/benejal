
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
package mail.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedHashMap;

import javax.swing.JTable;

import association.model.AssociationDataTransfer;

import mail.model.Mail;
import mail.view.MassMailView;
import moneybook.model.KassenBuch;

/**
 * class represents MouseListener for the mail table
 * 
 * @author Leonid Oldenburger
 */
public class MailMouseListener extends MouseAdapter{
	
	private JTable table;
	private MassMailView mMailView;
	private AssociationDataTransfer association_data_transfer;
	private LinkedHashMap<Integer, Mail> association_mail_map;
	private Mail newMail;
	
	/**
	 * constuctor creates a mouselistener for the mail table
	 * 
	 * @param massMailView
	 * @param gTable
	 * @param moneyBook
	 * @param adatatrans
	 */
	public MailMouseListener(MassMailView massMailView, JTable gTable, KassenBuch moneyBook, AssociationDataTransfer adatatrans){
		this.table = gTable;
		this.mMailView = massMailView;
		this.association_data_transfer = adatatrans;
		this.association_mail_map = association_data_transfer.getMail();
	}
	
	/**
	 * methode handle events
	 */
	public void mouseClicked(MouseEvent e) {
        if (e.getClickCount()==2) 
        {
            int row = table.getSelectedRow();
           
            this.association_mail_map = association_data_transfer.getMail();
            
            if(row < association_mail_map.size()){
            	int number = Integer.parseInt(((String)table.getValueAt(row, 0)));
            	
            	newMail = association_mail_map.get(number);

            	try{
            		mMailView.setSubject(newMail.getSubject());
            	}catch(NullPointerException ex){}
            	
            	try{
                	mMailView.setMail(newMail.getText());
            	}catch(NullPointerException ex){}

            }
        }
    } 
}
