
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
package member.view;

import java.awt.Color;
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

import association.model.Adress;
import association.model.AssociationDataTransfer;
import association.model.DocumentSizeFilter;
import association.model.Telephone;
import association.view.ButtonDesign;


import member.controller.AddDonatorActionListener;
import member.controller.BackToMainMenueActionListener;
import member.model.DonatorWithAdress;
import moneybook.model.KassenBuch;
import net.java.dev.designgridlayout.DesignGridLayout;

/**
 * JPanel to add a new Member
 * 
 * @author Leonid Oldenburger
 */
public class AddDonatorData extends JPanel{

	private static final long serialVersionUID = 1L;
	private JPanel memberpanel;
	private JPanel buttonpanel;
	private DesignGridLayout layout;
	
	//Text
	private String m_title_label = "Titel: ";
	private String m_gender_label = "Anrede: ";
	private String m_lname_label = "Nachname:*";
	private String m_fname_label = "Vorname:";
	private String m_street_label = "Straﬂe:";
	private String m_eadress_label = "Adressierungszusatz: ";
	private String m_hnumber_label = "Nr.:";
	private String m_postcode_label = "PLZ:";
	private String m_city_label = "Ort:";
	private String m_country_label = "Land: ";
	private String m_phone_label = "Telefon: ";
	private String m_email_label = "EMail: ";
	// Textfields
	private JTextField member_title;
	private JTextField member_gender;
	private JTextField member_lastname;
	private JTextField member_firstname;
	private JTextField member_extraadress;
	private JTextField member_street;
	private JTextField member_homenumber;
	private JTextField member_postcode;
	private JTextField member_city;
	private JTextField member_country;
	private JTextField member_phone;
	private JTextField member_email;
	
	// JButton
	private JButton next_button;
	private JButton main_button;
	
	private AddDonator adddonator;
	private MemberAndDonatorFrame mainframe;
	private ManageMembers managemembers;
	
	private DonatorWithAdress association_donator;
	private Adress association_member_adress;
	private Telephone association_member_phone;
	private AssociationDataTransfer association_data_transfer;
	private KassenBuch money_book;
	
	public AddDonatorData(DonatorWithAdress donator){
		this.association_donator = donator;
		
		buildPanel();
	}
	
	/**
	 * constructor for gui to add a member.
	 * 
	 * @param frame
	 * @param a_donator
	 * @param managem
	 * @param aDonator
	 * @param moneyBook
	 * @param adatatrans
	 */
	public AddDonatorData(MemberAndDonatorFrame frame, AddDonator a_donator, ManageMembers managem, DonatorWithAdress aDonator, KassenBuch moneyBook, AssociationDataTransfer adatatrans){
		
		this.mainframe = frame;
		this.adddonator = a_donator;
		this.managemembers = managem;
		this.association_donator = aDonator;
		this.association_data_transfer = adatatrans;
		this.money_book = moneyBook;
				
		buildPanel();
		
		next_button = new ButtonDesign("images/next_icon.png");
		next_button.addActionListener(new AddDonatorActionListener(this, association_donator, money_book, association_data_transfer));
		main_button = new ButtonDesign("images/mainmenu_icon.png");
		main_button.addActionListener(new  BackToMainMenueActionListener(mainframe, this));
		
		GridBagConstraints b = new GridBagConstraints();
		b.fill=GridBagConstraints.HORIZONTAL;
		
		b.insets =new Insets(70,2,2,2);
		
		b.gridx=0;
		b.gridy=0;
		buttonpanel.add(main_button, b);
		
		b.gridx=1;
		b.gridy=0;
		buttonpanel.add(next_button, b);
	}
	
	/**
	 * build panel
	 */
	public void buildPanel(){
		
		setLayout(new GridBagLayout());
		setOpaque(false);
		
		memberpanel = new JPanel();
		memberpanel.setBorder(new TitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY ), "Spenderdaten"));
		memberpanel.setOpaque(false);
		
		buttonpanel = new JPanel();
		buttonpanel.setOpaque(false);
		buttonpanel.setLayout(new GridBagLayout());
		
		layout = new DesignGridLayout(memberpanel);
		makeBlank(layout);
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill=GridBagConstraints.HORIZONTAL;
		
		c.insets =new Insets(20,15,2,2);
		
		c.gridx=0;
		c.gridy=0;
		add(memberpanel, c);
		
		c.gridx=0;
		c.gridy=1;
		add(buttonpanel, c);
	}
	
	/**
	 * methode creates a blank.
	 * 
	 * @param layout
	 */
	public void makeBlank(DesignGridLayout layout){
		
		member_title = new JTextField(12);
		member_gender = new JTextField(12);
		member_lastname = new JTextField(12);
		member_firstname = new JTextField(12);
		member_extraadress = new JTextField(12);
		member_street = new JTextField(12);
		member_homenumber = new JTextField(12);
		member_postcode = new JTextField(12);
		member_city = new JTextField(12);
		member_country = new JTextField(12);
		member_phone = new JTextField(12);
		member_email = new JTextField(12);
		
		if(association_donator != null)
			initTextField();
		
		((AbstractDocument) member_title.getDocument()).setDocumentFilter(new DocumentSizeFilter(30, "."));
		((AbstractDocument) member_gender.getDocument()).setDocumentFilter(new DocumentSizeFilter(30, "."));
		((AbstractDocument) member_firstname.getDocument()).setDocumentFilter(new DocumentSizeFilter(30, "."));
		((AbstractDocument) member_lastname.getDocument()).setDocumentFilter(new DocumentSizeFilter(30, "."));
		((AbstractDocument) member_extraadress.getDocument()).setDocumentFilter(new DocumentSizeFilter(30, "."));
		((AbstractDocument) member_street.getDocument()).setDocumentFilter(new DocumentSizeFilter(30, "."));
		((AbstractDocument) member_city.getDocument()).setDocumentFilter(new DocumentSizeFilter(30, "."));
		((AbstractDocument) member_country.getDocument()).setDocumentFilter(new DocumentSizeFilter(30, "."));
		((AbstractDocument) member_phone.getDocument()).setDocumentFilter(new DocumentSizeFilter(30, "[0-9\\-\\+]"));
		((AbstractDocument) member_email.getDocument()).setDocumentFilter(new DocumentSizeFilter(30, "."));
		((AbstractDocument) member_homenumber.getDocument()).setDocumentFilter(new DocumentSizeFilter(30, "."));
		((AbstractDocument) member_postcode.getDocument()).setDocumentFilter(new DocumentSizeFilter(30, "[0-9]"));
		
		layout.row().grid(new JLabel(m_title_label), 1).add(member_title);
		layout.row().grid(new JLabel(m_gender_label), 1).add(member_gender);
		layout.row().grid(new JLabel(m_fname_label), 1).add(member_firstname);
		layout.row().grid(new JLabel(m_lname_label), 1).add(member_lastname);
		layout.row().grid(new JLabel(m_eadress_label), 1).add(member_extraadress);
		layout.row().grid(new JLabel(m_street_label)).add(member_street).grid(new JLabel(m_hnumber_label)).add(member_homenumber);
		layout.row().grid(new JLabel(m_city_label)).add(member_city).grid(new JLabel(m_postcode_label)).add(member_postcode);
		layout.row().grid(new JLabel(m_country_label), 1).add(member_country);
		layout.row().grid(new JLabel(m_phone_label), 1).add(member_phone);
		layout.row().grid(new JLabel(m_email_label), 1).add(member_email);
	}
	
	/**
	 * methode return next button.
	 * 
	 * @return nextbutton
	 */
	public JButton getNextButton(){
		return next_button;
	}
	
	/**
	 * methode return next button.
	 * 
	 * @return nextbutton
	 */
	public JButton getBackButton(){
		return main_button;
	}
	
	/**
	 * methode return mainframe.
	 * 
	 * @return mainframe
	 */
	public MemberAndDonatorFrame getJFrame(){
		return mainframe;
	}
	
	/**
	 * methode return managemembers.
	 * 
	 * @return managemembers
	 */
	public ManageMembers getManageMembers(){
		return managemembers;
	}
	
	/**
	 * methode return addmember.
	 * 
	 * @return addmembers
	 */
	public AddDonator getAddDonator(){
		return adddonator;
	}

	public JTextField getEmail(){
		return member_email;
	}
	
	public JTextField getPhone(){
		return member_phone;
	}
	
	public JTextField getCountry(){
		return member_country;
	}
	
	public JTextField getCity(){
		return member_city;
	}
	
	public JTextField getZipCode(){
		return member_postcode;
	}
	
	public JTextField getHouseNumber(){
		return member_homenumber;
	}
	
	public JTextField getStreet(){
		return member_street;
	}
	
	public JTextField getExtraAdress(){
		return member_extraadress;
	}
	
	public JTextField getLastName(){
		return member_lastname;
	}
	
	public JTextField getFirstName(){
		return member_firstname;
	}
	
	public JTextField getGender(){
		return member_gender;
	}
	
	public JTextField getTitle(){
		return member_title;
	}
	
	/**
	 * methode initializes textfields.
	 */
	public void initTextField(){
		
		// get association member values 
		if (association_donator.getTitle() != null)
			member_title.setText(association_donator.getTitle());
		
		if (association_donator.getGender() != null)
			member_gender.setText(association_donator.getGender());
		
		if (association_donator.getFirstName() != null)
			member_firstname.setText(association_donator.getFirstName());

		if (association_donator.getLastName() != null)
			member_lastname.setText(association_donator.getLastName());
		
		if(association_donator.getAdress() != null)
			association_member_adress = association_donator.getAdress();
		
		if(association_member_adress.getAdressAdditional() != null)
			member_extraadress.setText(association_member_adress.getAdressAdditional());
		
		if(association_member_adress.getStreet() != null)
			member_street.setText(association_member_adress.getStreet());
		
		if(association_member_adress.getHouseNumber() != "")
			member_homenumber.setText(association_member_adress.getHouseNumber());
		
		if(association_member_adress.getCity() != null)
			member_city.setText(association_member_adress.getCity());
		
		if(association_member_adress.getZipCode() != "")
			member_postcode.setText(association_member_adress.getZipCode());
		
		if(association_member_adress.getCountry() != null)
			member_country.setText(association_member_adress.getCountry());
		
		if(association_member_adress.getEmail() != null)
			member_email.setText(association_member_adress.getEmail());
		
		if(association_donator.getPhone() != null)
			association_member_phone = association_donator.getPhone();
		
		if(association_member_phone.getPrivatePhone() != "")
			member_phone.setText(association_member_phone.getPrivatePhone());
	}
}
