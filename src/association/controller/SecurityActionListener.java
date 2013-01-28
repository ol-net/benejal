
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
package association.controller;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import moneybook.model.KassenBuch;

import association.model.AssociationDataTransfer;
import association.model.Security;
import association.view.InfoDialog;
import association.view.MainMenu;
import association.view.SecurityView;

/**
 * class represents ActionListener for the security class
 * 
 * @author Leonid Oldenburger
 */
public class SecurityActionListener implements ActionListener{
	
	private SecurityView security_view;
	private Security security;
	private AssociationDataTransfer association_data_transfer;
	private KassenBuch money_book;
	
	/**
	 * creates a security action listener
	 * 
	 * @param secview
	 * @param moneyBook
	 * @param adatatrans
	 */
	public SecurityActionListener(SecurityView secview, KassenBuch moneyBook, AssociationDataTransfer adatatrans){
		this.security_view = secview;
		this.association_data_transfer = adatatrans;
		this.money_book = moneyBook;
	}

	/**
	 * methode handles events
	 */
	public void actionPerformed(ActionEvent event) {

		if (this.security_view.getButton() == event.getSource()) {
			try {
				String hashcode;
				String saved_hashcode;
				security = new Security(security_view.getPassword());
				hashcode = security.generateCode();
				saved_hashcode = security.getCode();
				
				int width = 550;
				int height = 520;
				Font font_style = new Font("mono", Font.BOLD, 12);
				
				if(saved_hashcode == null){
					new MainMenu(height, width, font_style, money_book, association_data_transfer);
					security_view.dispose();
				}
				else if(hashcode.equals(saved_hashcode)){
					new MainMenu(height, width, font_style, money_book, association_data_transfer);
					security_view.dispose();
				}
				else{
					new InfoDialog("Falsches Passwort!");
				}
				
			} catch (Exception e) {
			}
		}
	}
}
