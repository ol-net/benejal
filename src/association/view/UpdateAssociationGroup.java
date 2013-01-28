
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
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

import moneybook.model.KassenBuch;

import association.controller.BackConfigActionListener;
import association.model.Association;
import association.model.AssociationDataTransfer;

import register.controller.AssociationGroupActionListener;
import register.view.RegisterAssociationGroup;

/**
 * gui-class represent update panel for member groups
 * 
 * @author Leonid Oldenburger
 */
public class UpdateAssociationGroup extends RegisterAssociationGroup {
	
	private static final long serialVersionUID = 1L;
	private JButton back_button;
	private JButton save_button;
	private JPanel buttonpanel;
	private JPanel radio_panel;
	private ConfigFrame mainframe;
	private KassenBuch money_book;
	private JRadioButton yes_radio_button;
	private JRadioButton no_radio_button;
	private ButtonGroup checkBoxGroup;
	
	/**
	 * constructor creates a new group panel
	 * 
	 * @param mFrame
	 * @param aObject
	 * @param moneyBook
	 * @param adatatrans
	 */
	public UpdateAssociationGroup(ConfigFrame mFrame, Association aObject, KassenBuch moneyBook, AssociationDataTransfer adatatrans){
		
		this.association_object = aObject;
		this.mainframe = mFrame;
		this.association_data_transfer = adatatrans;
		this.association_data_transfer.addObserver(this);
		this.money_book = moneyBook;
		
		setBorder(new TitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY ), "Mitgliedergruppen"));
		
		buildPanel();
		
		radio_panel = new JPanel();
		radio_panel.setLayout(new FlowLayout());
		radio_panel.setOpaque(false);
		
	    checkBoxGroup = new ButtonGroup();
		
	    yes_radio_button = new JRadioButton("Ja", false);
	    yes_radio_button.setOpaque(false);
	    no_radio_button = new JRadioButton("Nein", true);
	    no_radio_button.setOpaque(false);

		checkBoxGroup.add(yes_radio_button);
		checkBoxGroup.add(no_radio_button);
		
		radio_panel.add(new JLabel("Mitgliedsbeiträge bereits für die aktuelle Periode übernehmen? "));
		radio_panel.add(no_radio_button);
		radio_panel.add(yes_radio_button);

		// Buttons
		save_button = new ButtonDesign("images/save_icon.png");
		back_button = new ButtonDesign("images/mainmenu_icon.png");
		back_button.addActionListener(new BackConfigActionListener(mainframe, this));
		save_button.addActionListener(new AssociationGroupActionListener(this, mainframe, association_object, money_book, association_data_transfer));

		buttonpanel = new JPanel();
		buttonpanel.setLayout(new FlowLayout());
		buttonpanel.setOpaque(false);
		buttonpanel.add(back_button);	
		buttonpanel.add(save_button);
		
		GridBagConstraints b = new GridBagConstraints();
		b.fill=GridBagConstraints.HORIZONTAL;
		b.insets =new Insets(75,1,2,2);
		
		b.gridx=0;
		b.gridy=0;
		button_panel.add(radio_panel, b);
		
		b.gridx=0;
		b.gridy=1;
		button_panel.add(buttonpanel, b);
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
	
	/**
	 * methode returns yes_radio_button.
	 * 
	 * @return yes_radio_button
	 */
	public JRadioButton getRadioButtonYes(){
		return yes_radio_button;
	}
	
	/**
	 * methode returns no_radio_button.
	 * 
	 * @return no_radio_button
	 */
	public JRadioButton getRadioButtonNo(){
		return no_radio_button;
	}
	
	/**
	 * methode returns checkBoxGroup.
	 * 
	 * @return checkBoxGroup
	 */
	public ButtonGroup getButtonGroup(){
		return checkBoxGroup;
	}
}