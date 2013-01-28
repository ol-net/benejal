
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
package register.view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.text.AbstractDocument;

import association.model.Association;
import association.model.AssociationDataTransfer;
import association.model.DocumentSizeFilter;
import association.view.BackgroundPanel;
import association.view.ButtonDesign;

import register.controller.AssociationAdminActionListener;

import member.model.Person;
import moneybook.model.KassenBuch;
import net.java.dev.designgridlayout.DesignGridLayout;

/**
 * gui class for the association administration
 * 
 * @author Leonid Oldenburger
 */
public class RegisterAssociationAdmin extends BackgroundPanel{

	private static final long serialVersionUID = 1L;
	
	// languagepack
	protected String title = "Registrierung - Vereinsleitung - Schritt 2 von 6";
	protected String leader = "Vorsitzender";
	protected String treasurer = "Kassenwart";
	protected String first_name = "Vorname:";
	protected String last_name = "Nachname:";
	protected String title_label = "Titel:";
	protected String gender_label = "Anrede:";
	
	// TextFields
	protected JTextField text_gender_leader;
	protected JTextField text_title_leader;
	protected JTextField text_firstname_leader;
	protected JTextField text_lastname_leader;
	protected JTextField text_gender_treasurer;
	protected JTextField text_title_treasurer;
	protected JTextField text_firstname_treasurer;
	protected JTextField text_lastname_treasurer;
	
	// Button
	protected JButton next_button;
	protected JButton back_button;
	
	// window option
	protected Font style_font;
	protected DesignGridLayout leader_layout;
	protected DesignGridLayout treasurer_layout;
	protected RegisterStartWindow mainframe;
	
	// Panel
	protected JPanel buttonpanel;
	protected JPanel butpanel;
	protected JPanel leaderpanel;
	protected JPanel treasurerpanel;	
	
	protected Association association_object;
	protected Person association_leader;
	protected Person association_treasurer;
	
	private AssociationDataTransfer association_data_transfer;
	private KassenBuch money_book;
	
	/**
	 * constructor
	 */
	public RegisterAssociationAdmin(){
		this.association_object = null;
		this.style_font = null;
		this.mainframe = null;
	}
	
	/**
	 * constructor with same parameters 
	 * 
	 * @param fontStyle
	 * @param frame
	 * @param associationObject
	 * @param moneyBook
	 * @param adatatrans
	 */
	public RegisterAssociationAdmin(Font fontStyle, RegisterStartWindow frame, Association associationObject, KassenBuch moneyBook, AssociationDataTransfer adatatrans){
	
		this.association_object = associationObject;
		this.style_font = fontStyle;
		this.mainframe = frame;
		this.mainframe.setTitle(title);
		this.association_data_transfer = adatatrans;
		this.money_book = moneyBook;
		
		buildPanel();
		// Next-Button
		next_button = new ButtonDesign("images/next_icon.png");
		back_button = new ButtonDesign("images/back_icon.png");
		back_button.addActionListener(new AssociationAdminActionListener(this, mainframe, association_object, money_book, association_data_transfer));
		next_button.addActionListener(new AssociationAdminActionListener(this, mainframe, association_object, money_book, association_data_transfer));

		butpanel = new JPanel();
		butpanel.setLayout(new FlowLayout());
		butpanel.setOpaque(false);
		butpanel.add(back_button);	
		butpanel.add(next_button);
		
		GridBagConstraints b = new GridBagConstraints();
		b.fill=GridBagConstraints.HORIZONTAL;
		b.insets =new Insets(6,25,2,2);
		b.gridx=0;
		b.gridy=0;
		buttonpanel.add(butpanel, b);
	}
	
	/**
	 * build panel
	 */
	public void buildPanel(){
		setLayout(new GridBagLayout());
		setOpaque(false);
		
		leaderpanel = new JPanel();
		leaderpanel.setBorder(new TitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY ), leader));
		leaderpanel.setOpaque(false);
//      Dimension panelD = new Dimension(500,100);  
//      leaderpanel.setPreferredSize(panelD);  
//      leaderpanel.setMaximumSize(panelD);  
		
		leader_layout = new DesignGridLayout(leaderpanel);
		makeLeaderBlank(leader_layout);
		
		treasurerpanel = new JPanel();
		treasurerpanel.setBorder(new TitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY ), treasurer));
		treasurerpanel.setOpaque(false);
		
		treasurer_layout = new DesignGridLayout(treasurerpanel);
		makeTreasurerBlank(treasurer_layout);
		
		buttonpanel = new JPanel();
		buttonpanel.setLayout(new GridBagLayout());
		buttonpanel.setOpaque(false);
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill=GridBagConstraints.HORIZONTAL;
		c.insets =new Insets(30,2,2,25);
		
		c.gridx=0;
		c.gridy=0;
		add(leaderpanel, c);
		
		c.gridx=0;
		c.gridy=1;
		add(treasurerpanel, c);
		
		c.gridx=0;
		c.gridy=2;
		add(buttonpanel, c);		
	}
	
	/**
	 * methode to create a blank for treasurer.
	 * 
	 * @param layout
	 */
	public void makeTreasurerBlank(DesignGridLayout layout){
		
		text_title_treasurer = new JTextField(12);
		text_gender_treasurer = new JTextField(12);
		text_firstname_treasurer = new JTextField(12);
		text_lastname_treasurer = new JTextField(12);
		
		if (association_object != null)
			association_treasurer = association_object.getTreasurer();
		
		if (association_treasurer != null)
			initTreasurerTextField();
		
		((AbstractDocument) text_title_treasurer.getDocument()).setDocumentFilter(new DocumentSizeFilter(30, "."));
		((AbstractDocument) text_gender_treasurer.getDocument()).setDocumentFilter(new DocumentSizeFilter(30, "."));
		((AbstractDocument) text_firstname_treasurer.getDocument()).setDocumentFilter(new DocumentSizeFilter(30, "."));
		((AbstractDocument) text_lastname_treasurer.getDocument()).setDocumentFilter(new DocumentSizeFilter(30, "."));
		
		layout.row().grid(new JLabel(title_label), 1).add(text_title_treasurer).empty(1);
		layout.row().grid(new JLabel(gender_label), 1).add(text_gender_treasurer).empty(1);
		layout.row().grid(new JLabel(first_name), 1).add(text_firstname_treasurer).empty(1);
		layout.row().grid(new JLabel(last_name), 1).add(text_lastname_treasurer).empty(1);
	}
	
	/**
	 * methode to create a blank for leader.
	 * 
	 * @param layout
	 */
	public void makeLeaderBlank(DesignGridLayout layout){

		text_title_leader = new JTextField(6);
		text_gender_leader = new JTextField(12);
		text_firstname_leader = new JTextField(12);
		text_lastname_leader = new JTextField(12);
		
		if (association_object != null)
			association_leader = association_object.getLeader();
		
		if (association_leader != null)
			initLeaderTextField();
		
		((AbstractDocument) text_title_leader.getDocument()).setDocumentFilter(new DocumentSizeFilter(30, "."));
		((AbstractDocument) text_gender_leader.getDocument()).setDocumentFilter(new DocumentSizeFilter(30, "."));
		((AbstractDocument) text_firstname_leader.getDocument()).setDocumentFilter(new DocumentSizeFilter(30, "."));
		((AbstractDocument) text_lastname_leader.getDocument()).setDocumentFilter(new DocumentSizeFilter(30, "."));
		
		
		layout.row().grid(new JLabel(title_label), 1).add(text_title_leader).empty(1);
		layout.row().grid(new JLabel(gender_label), 1).add(text_gender_leader).empty(1);
		layout.row().grid(new JLabel(first_name), 1).add(text_firstname_leader).empty(1);
		layout.row().grid(new JLabel(last_name), 1).add(text_lastname_leader).empty(1);
	}
	
	
	/**
	 * methode returns treasurers gender.
	 * 
	 * @return text_gender_treasurer
	 */
	public JTextField getGenderTreasurer(){
		return text_gender_treasurer;
	}
	
	/**
	 * methode returns leaders gender.
	 * 
	 * @return text_gender_leader
	 */
	public JTextField getGenderLeader(){
		return text_gender_leader;
	}
	
	/**
	 * methode returns leaders title.
	 * 
	 * @return text_title_leader
 	 */
	public JTextField getTitleLeader(){
		return text_title_leader;
	}
	
	/**
	 * methode returns treasurers title.
	 * 
	 * @return text_title_treasurer
 	 */
	public JTextField getTitleTreasurer(){
		return text_title_treasurer;
	}
	
	/**
	 * 
	 * @return backbutton
	 */
	public JButton getBackButton(){
		return back_button;
	}
	
	/**
	 * 
	 * @return nextbutton
	 */
	public JButton getNextButton(){
		return next_button;
	}
	
	/**
	 *  methode to get leader firstname
	 * 
	 * @return firstname
	 */
	public JTextField getFirstNameLeader(){
		return text_firstname_leader;
	}
	
	/**
	 * methode to get leader lastname
	 * 
	 * @return lastname
	 */
	public JTextField getLastNameLeader(){
		return text_lastname_leader;
	}

	/**
	 * methode to get treasurer firstname
	 * 
	 * @return firstname
	 */
	public JTextField getFirstNameTreasurer(){
		return text_firstname_treasurer;
	}
	
	/**
	 * methode to get treasurer lastname
	 * 
	 * @return lastname 
	 */
	public JTextField getLastNameTreasurer(){
		return text_lastname_treasurer;
	}
	
	/**
	 * 
	 * @return fontstyle
	 */
	public Font getFontStyle(){
		return style_font;
	}
	
	/**
	 * methode inits treasurers textfield
	 */
	public void initTreasurerTextField(){
			
		if (association_treasurer.getTitle() != null)
			text_title_treasurer.setText(association_treasurer.getTitle());
		if (association_treasurer.getGender() != null)
			text_gender_treasurer.setText(association_treasurer.getGender());
		if (association_treasurer.getFirstName() != null)
			text_firstname_treasurer.setText(association_treasurer.getFirstName());
		if (association_treasurer.getLastName() != null)
			text_lastname_treasurer.setText(association_treasurer.getLastName());
	}
	
	/**
	 * methode inits leaders textfield
	 */
	public void initLeaderTextField(){

		if (association_leader.getTitle() != null)
			text_title_leader.setText(association_leader.getTitle());
		if (association_leader.getGender() != null)
			text_gender_leader.setText(association_leader.getGender());
		if (association_leader.getFirstName() != null)
			text_firstname_leader.setText(association_leader.getFirstName());
		if (association_leader.getLastName() != null)
			text_lastname_leader.setText(association_leader.getLastName());
	}
}
