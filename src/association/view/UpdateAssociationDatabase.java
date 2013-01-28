
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
package association.view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import moneybook.model.KassenBuch;

import association.controller.BackConfigActionListener;
import association.model.Association;
import association.model.AssociationDataTransfer;

import register.controller.AssociationDatabaseActionListener;
import register.view.RegisterAssociationDatabase;

/**
 * gui class represents a panel for security
 * 
 * @author Leonid Oldenburger
 */
public class UpdateAssociationDatabase extends RegisterAssociationDatabase{
	
	private static final long serialVersionUID = 1L;
	private JButton back_button;
	private JButton save_button;
	private JPanel butpanel;
	private ConfigFrame mainframe;
	
	private AssociationDataTransfer association_data_transfer;
	private KassenBuch money_book;
	
	/**
	 * constructor creates update security panel
	 * 
	 * @param mFrame
	 * @param aObject
	 * @param moneyBook
	 * @param adatatrans
	 */
	public UpdateAssociationDatabase(ConfigFrame mFrame, Association aObject, KassenBuch moneyBook, AssociationDataTransfer adatatrans){
		
		this.association_object = aObject;
		this.mainframe = mFrame;
		this.association_data_transfer = adatatrans;
		this.money_book = moneyBook;
		
		setBorder(new TitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY ), "Vereinssicherheit"));
		
		buildPanel();
		
		// Buttons
		save_button = new ButtonDesign("images/save_icon.png");
		back_button = new ButtonDesign("images/mainmenu_icon.png");
		save_button.addActionListener(new AssociationDatabaseActionListener(this, mainframe, association_object, money_book, association_data_transfer));
		back_button.addActionListener(new BackConfigActionListener(mainframe, this));
		butpanel = new JPanel();
		butpanel.setLayout(new FlowLayout());
		butpanel.setOpaque(false);
		butpanel.add(back_button);	
		butpanel.add(save_button);
		
		GridBagConstraints b = new GridBagConstraints();
		b.fill=GridBagConstraints.HORIZONTAL;
		b.insets =new Insets(4,1,2,2);
		b.gridx=0;
		b.gridy=0;
		buttonpanel.add(butpanel, b);
	}
	
	/**
	 * methode returns back button.
	 * 
	 * @return back_button
	 */
	public JButton getBackButton(){
		return back_button;
	}
	
	/**
	 * methode returns save button.
	 * 
	 * @return save_button
	 */
	public JButton getSaveButton(){
		return save_button;
	}
}
