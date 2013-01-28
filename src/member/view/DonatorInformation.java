
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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.text.AbstractDocument;

import net.java.dev.designgridlayout.DesignGridLayout;

import member.controller.DonatorActionListener;
import member.controller.MemberActionListener;
import member.model.DonatorWithAdress;

import moneybook.model.KassenBuch;

import association.model.Account;
import association.model.Adress;
import association.model.AssociationDataTransfer;
import association.model.DocumentSizeFilter;
import association.model.Telephone;

import association.view.BackgroundPanel;
import association.view.ButtonDesign;

/**
 * class for the main frame.
 * 
 * @author Leonid Oldenburger
 */
public class DonatorInformation extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private String title = "Spender";
	private String title_association = "Förderverein";
	private JPanel mainpanel;

	private JPanel memberpanel;
	private JPanel memberaccountpanel;
	private JPanel membercontributionpanel;
	private JPanel buttonpanel;
	private JPanel rightpanel;
	private JPanel bpanel;
	private JPanel centerpanel;
	
	private DesignGridLayout layout;
	private DesignGridLayout layout_account;
	
	//Text
	private String m_title_label = "Titel: ";
	private String m_gender_label = "Anrede: ";
	private String m_lname_label = "Nachname:*";
	private String m_fname_label = "Vorname:*";
	private String m_street_label = "Straße:*";
	private String m_eadress_label = "Adressierungszusatz: ";
	private String m_hnumber_label = "Nr.:*";
	private String m_postcode_label = "PLZ:*";
	private String m_city_label = "Ort:*";
	private String m_country_label = "Land: ";
	private String m_phone_label = "Telefon: ";
	private String m_email_label = "EMail: ";
	
	// Textfields
	private JTextField member_title;
	private JTextField member_gender;
	private JTextField member_lastname;
	private JTextField member_firstname;
	private JTextField member_extraaddress;
	private JTextField member_street;
	private JTextField member_homenumber;
	private JTextField member_postcode;
	private JTextField member_city;
	private JTextField member_country;
	private JTextField member_phone;
	private JTextField member_email;
	
	//language
	private String m_bank_label = "Bankverbindung";
	private String m_bankname_label = "Kreditinstitut: ";
	private String m_accountowner_label = "Kontoinhaber: ";
	private String m_accountnumber_label = "Kontonummer:*";
	private String m_bankcode_label = "Bankleitzahl:*";
	
	// Textfields
	private JTextField member_bankname;
	private JTextField member_accountowner;
	private JTextField member_accountnumber;
	private JTextField member_bankcode;
	private JComboBox member_group;
	private JComboBox member_status;
	
	// window-size
	private int height;
	private int width;
	
	private DonatorWithAdress association_donator;
	private Adress association_member_address;
	private Telephone association_member_phone;
	private Account association_member_account;
	
	private JButton save_button;
	private JButton back_button;
	
	private JButton contribution_button;
	private JButton member_button;
	
	private JButton receipt_button;
	private JButton mail_button;
	
	private JButton remove_button;
	
	private MemberAndDonatorFrame mframe;
	
	private AssociationDataTransfer association_data_transfer;
	@SuppressWarnings("unused")
	private KassenBuch money_book;
	
	/**
	 * constructor creates a new window.
	 */
	public DonatorInformation(DonatorWithAdress aDonator, MemberAndDonatorFrame mainf, KassenBuch moneyBook, AssociationDataTransfer adatatrans){
		
		this.mframe = mainf;
		this.height = 560;
		this.width = 850;
		this.association_donator = aDonator;
		this.association_data_transfer = adatatrans;
		this.money_book = moneyBook;
		
		makePanel();
	}
	
	/**
	 * makes panel
	 */
	public void makePanel(){
		
		mainpanel = new BackgroundPanel();
		mainpanel.setLayout(new BorderLayout());
		mainpanel.setOpaque(false);
		
		mainpanel.setBorder(new TitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY ), ""));
		
		setTitle(title_association+" - "+association_data_transfer.getAssociation().getName()+" - "+title+" - "+association_donator.getFirstName()+" "+association_donator.getLastName());
		setLayout(new BorderLayout());
		setMinimumSize(new Dimension(width, height)); 
		
		memberpanel = new JPanel();
		memberpanel.setBorder(new TitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY ), "Spenderdaten"));
		memberpanel.setOpaque(false);
		
		memberaccountpanel = new JPanel();
		memberaccountpanel.setBorder(new TitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY ), m_bank_label));
		memberaccountpanel.setOpaque(false);
		
		membercontributionpanel = new JPanel();
		membercontributionpanel.setBorder(new TitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY ), "Zusatzspenden"));
		membercontributionpanel.setOpaque(false);
		
		rightpanel = new JPanel();
		rightpanel.setLayout(new GridBagLayout());
		rightpanel.setOpaque(false);
		
		bpanel = new JPanel();
		bpanel.setLayout(new GridBagLayout());
		bpanel.setOpaque(false);
		
		buttonpanel = new JPanel();
		buttonpanel.setOpaque(false);
		buttonpanel.setLayout(new GridBagLayout());
		
		centerpanel = new JPanel();
		centerpanel.setLayout(new GridBagLayout());
		centerpanel.setOpaque(false);
		
		layout = new DesignGridLayout(memberpanel);
		makeBlank(layout);
		layout_account = new DesignGridLayout(memberaccountpanel);
		makeBlankAccount(layout_account);
		
		save_button = new ButtonDesign("images/save_icon.png");
		save_button.addActionListener(new DonatorActionListener(this, mframe));
		back_button = new ButtonDesign("images/back_icon.png");
		back_button.addActionListener(new DonatorActionListener(this, mframe));		
		contribution_button = new ButtonDesign("images/donation_icon.png");
		contribution_button.addActionListener(new DonatorActionListener(this, mframe));
		member_button = new ButtonDesign("images/transform_to_member_icon.png");
		member_button.addActionListener(new DonatorActionListener(this, mframe));
		receipt_button = new ButtonDesign("images/quittung_icon_small.png");
		receipt_button.addActionListener(new DonatorActionListener(this, mframe));
		mail_button = new ButtonDesign("images/mail_icon_small.png");
		mail_button.addActionListener(new DonatorActionListener(this, mframe));
		
		buttonpanel.add(back_button);
		buttonpanel.add(new JLabel("  "));
		buttonpanel.add(member_button);
		buttonpanel.add(new JLabel("  "));
		buttonpanel.add(mail_button);
		buttonpanel.add(new JLabel("  "));
		buttonpanel.add(receipt_button);
		buttonpanel.add(new JLabel("  "));
		buttonpanel.add(contribution_button);
		buttonpanel.add(new JLabel("  "));
		buttonpanel.add(save_button);
		
		GridBagConstraints a = new GridBagConstraints();
		a.fill=GridBagConstraints.HORIZONTAL;
		
		a.insets =new Insets(2,2,2,2);
		
		a.gridx=1;
		a.gridy=0;
		rightpanel.add(memberaccountpanel, a);
		
		membercontributionpanel.setVisible(false);
		
		a.gridx=1;
		a.gridy=1;
		rightpanel.add(membercontributionpanel, a);
		
		GridBagConstraints b = new GridBagConstraints();
		b.fill=GridBagConstraints.HORIZONTAL;
		
		b.insets = new Insets(2,2,2,2);
		
		b.gridx=0;
		b.gridy=0;
		bpanel.add(buttonpanel, b);
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill=GridBagConstraints.HORIZONTAL;
		
		c.insets =new Insets(20,2,5,2);
		
		c.gridx=0;
		c.gridy=0;
		centerpanel.add(memberpanel, c);
		
		c.gridx=1;
		c.gridy=0;
		centerpanel.add(rightpanel, c);
		
		mainpanel.add(centerpanel, BorderLayout.NORTH);
		mainpanel.add(bpanel, BorderLayout.CENTER);
		
		add(mainpanel, BorderLayout.CENTER);
		add(mainpanel, BorderLayout.CENTER);
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		
		// Centering a Window
		setResizable(true);
		setSize(width, height);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	/**
	 * method creates a blank.
	 * 
	 * @param layout
	 */
	public void makeBlank(DesignGridLayout layout){
		
		remove_button = new ButtonDesign("images/remove_icon.png");
		remove_button.addActionListener(new DonatorActionListener(this, mframe));
		
		member_title = new JTextField(12);
		member_gender = new JTextField(12);
		member_lastname = new JTextField(12);
		member_firstname = new JTextField(12);
		member_extraaddress = new JTextField(12);
		member_street = new JTextField(12);
		member_homenumber = new JTextField(12);
		member_postcode = new JTextField(12);
		member_city = new JTextField(12);
		member_country = new JTextField(12);
		member_phone = new JTextField(12);
		member_email = new JTextField(12);
		
	    JTextField dummy = new JTextField();
	    dummy.setVisible(false);
	    JTextField dummy1 = new JTextField();
	    dummy1.setVisible(false);
	    JTextField dummy2 = new JTextField();
	    dummy2.setVisible(false);
	    JTextField dummy3 = new JTextField();
	    dummy3.setVisible(false);
	    
		FlowLayout fl = new FlowLayout();
		fl.setHgap(1);
		fl.setVgap(1);
		fl.setAlignment(FlowLayout.LEFT);
	    
		JPanel button_panel = new JPanel();
		button_panel.setLayout(fl);
		button_panel.setOpaque(false);
		
		button_panel.add(remove_button);

		if(association_donator != null)
			initTextField();
		
		((AbstractDocument) member_title.getDocument()).setDocumentFilter(new DocumentSizeFilter(30, "."));
		((AbstractDocument) member_gender.getDocument()).setDocumentFilter(new DocumentSizeFilter(30, "."));
		((AbstractDocument) member_firstname.getDocument()).setDocumentFilter(new DocumentSizeFilter(30, "."));
		((AbstractDocument) member_lastname.getDocument()).setDocumentFilter(new DocumentSizeFilter(30, "."));
		((AbstractDocument) member_extraaddress.getDocument()).setDocumentFilter(new DocumentSizeFilter(30, "."));
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
		layout.emptyRow();
		layout.row().grid(new JLabel(m_eadress_label), 1).add(member_extraaddress);
		layout.row().grid(new JLabel(m_street_label)).add(member_street).grid(new JLabel(m_hnumber_label)).add(member_homenumber);
		layout.row().grid(new JLabel(m_city_label)).add(member_city).grid(new JLabel(m_postcode_label)).add(member_postcode);
		layout.row().grid(new JLabel(m_country_label), 1).add(member_country);
		layout.emptyRow();
		layout.row().grid(new JLabel(m_phone_label), 1).add(member_phone);
		layout.row().grid(new JLabel(m_email_label), 1).add(member_email);
		layout.row().grid(new JLabel(" "), 2).add(dummy);
		layout.row().grid(new JLabel(" "), 2).add(button_panel);
		layout.row().grid(new JLabel(" "), 1).add(dummy2);
		//layout.row().grid(new JLabel(" "), 1).add(dummy3);

	}
	
	
	/**
	 * method creates a blank.
	 * 
	 * @param layout
	 */
	public void makeBlankAccount(DesignGridLayout layout){
		
	    JTextField dummy = new JTextField();
	    dummy.setVisible(false);
	    JTextField dummy1 = new JTextField();
	    dummy1.setVisible(false);
	    JTextField dummy2 = new JTextField();
	    dummy2.setVisible(false);
	    JTextField dummy3 = new JTextField();
	    dummy3.setVisible(false);
	    JTextField dummy4 = new JTextField();
	    dummy4.setVisible(false);
	    JTextField dummy5 = new JTextField();
	    dummy5.setVisible(false);
	    JTextField dummy6 = new JTextField();
	    dummy6.setVisible(false);
	    JTextField dummy7 = new JTextField();
	    dummy7.setVisible(false);
	    JTextField dummy8 = new JTextField();
	    dummy8.setVisible(false);
	    JTextField dummy9 = new JTextField();
	    dummy9.setVisible(false);
	    JTextField dummy10 = new JTextField();
	    dummy10.setVisible(false);
	    JTextField dummy11 = new JTextField();
	    dummy11.setVisible(false);
		
		member_bankname = new JTextField(5);
		member_accountowner = new JTextField(5);
		member_accountnumber = new JTextField(10);
		member_bankcode = new JTextField(10);
		
		if(association_donator != null)
			initTextFieldsAccount();
		
		((AbstractDocument) member_accountowner.getDocument()).setDocumentFilter(new DocumentSizeFilter(30, "."));
		((AbstractDocument) member_accountnumber.getDocument()).setDocumentFilter(new DocumentSizeFilter(10, "[0-9]+"));
		((AbstractDocument) member_bankcode.getDocument()).setDocumentFilter(new DocumentSizeFilter(8, "[0-9]+"));
		((AbstractDocument) member_bankname.getDocument()).setDocumentFilter(new DocumentSizeFilter(30, "."));
		
		layout.row().grid(new JLabel(m_accountowner_label)).add(member_accountowner);
		layout.row().grid(new JLabel(m_accountnumber_label)).add(member_accountnumber);
		layout.row().grid(new JLabel(m_bankcode_label)).add(member_bankcode);
		layout.row().grid(new JLabel(m_bankname_label)).add(member_bankname);
		layout.row().grid(new JLabel(" "), 2).add(dummy);
		layout.row().grid(new JLabel(" "), 1).add(dummy1);
		layout.row().grid(new JLabel(" "), 1).add(dummy2);
		layout.row().grid(new JLabel(" "), 1).add(dummy3);
		layout.row().grid(new JLabel(" "), 2).add(dummy4);
		layout.row().grid(new JLabel(" "), 1).add(dummy5);
		layout.row().grid(new JLabel(" "), 1).add(dummy6);
		layout.row().grid(new JLabel(" "), 1).add(dummy7);
		layout.row().grid(new JLabel(" "), 2).add(dummy8);
		layout.row().grid(new JLabel(" "), 1).add(dummy9);
		
	}
	
	/**
	 * method initializes textfields.
	 */
	public void initTextField(){		
		
		if (association_donator.getTitle() != null)
			member_title.setText(association_donator.getTitle());
		
		if (association_donator.getGender() != null)
			member_gender.setText(association_donator.getGender());
		
		if (association_donator.getFirstName() != null)
			member_firstname.setText(association_donator.getFirstName());

		if (association_donator.getLastName() != null)
			member_lastname.setText(association_donator.getLastName());
		
		if(association_donator.getAdress() != null)
			association_member_address = association_donator.getAdress();
		
		if(association_member_address.getAdressAdditional() != null)
			member_extraaddress.setText(association_member_address.getAdressAdditional());
		
		if(association_member_address.getStreet() != null)
			member_street.setText(association_member_address.getStreet());
		
		if(association_member_address.getHouseNumber() != null)
			member_homenumber.setText(association_member_address.getHouseNumber());
		
		if(association_member_address.getCity() != null)
			member_city.setText(association_member_address.getCity());
		
		if(association_member_address.getZipCode() != null)
			member_postcode.setText(association_member_address.getZipCode());
		
		if(association_member_address.getCountry() != null)
			member_country.setText(association_member_address.getCountry());
		
		if(association_member_address.getEmail() != null)
			member_email.setText(association_member_address.getEmail());
		
		if(association_donator.getPhone() != null)
			association_member_phone = association_donator.getPhone();
		
		if(association_member_phone.getPrivatePhone() != null)
			member_phone.setText(association_member_phone.getPrivatePhone());
	}
	
	/**
	 * method initializes textfields.
	 */
	public void initTextFieldsAccount(){
		
		if(association_donator.getAccount() != null)
			association_member_account = association_donator.getAccount();
		
		if(association_member_account.getOwner() != null)
			member_accountowner.setText(association_member_account.getOwner());
		
		if(association_member_account.getAccountNumber() != "")
			member_accountnumber.setText(association_member_account.getAccountNumber());
		
		if(association_member_account.getBankCodeNumber() != "")
			member_bankcode.setText(association_member_account.getBankCodeNumber());
		
		if(association_member_account.getBankName() != "")
			member_bankname.setText(association_member_account.getBankName());
	}
	
	/**
	 * method to close the window
	 */
	public void schliessen() {
		dispose();
	}
	
	/**
	 * method returns save button
	 * 
	 * @return save button
	 */
	public JButton getSaveButton(){
		return save_button;
	}
	
	/**
	 * method returns remove button
	 * 
	 * @return save button
	 */
	public JButton getRemoveButton(){
		return remove_button;
	}
	
	/**
	 * method returns member button
	 * 
	 * @return member button
	 */
	public JButton getMemberButton(){
		return member_button;
	}
	
	/**
	 * method returns back button
	 * 
	 * @return back button
	 */
	public JButton getBackButton(){
		return back_button;
	}
	
	/**
	 * method returns mail button
	 * 
	 * @return mail button
	 */
	public JButton getMailButton(){
		return mail_button;
	}
	
	/**
	 * method returns receipt button
	 * 
	 * @return receipt button
	 */
	public JButton getReceiptButton(){
		return receipt_button;
	}
	
	/**
	 * method returns member email
	 * 
	 * @return member email
	 */
	public JTextField getEmail(){
		return member_email;
	}
	
	/**
	 * method returns member phone
	 * 
	 * @return member phone 
	 */
	public JTextField getPhone(){
		return member_phone;
	}
	
	/**
	 * method returns member country
	 * 
	 * @return member country
	 */
	public JTextField getCountry(){
		return member_country;
	}
	
	/**
	 * method returns member city 
	 * 
	 * @return member city 
	 */
	public JTextField getCity(){
		return member_city;
	}
	
	/**
	 * method returns member postcode
	 * 
	 * @return member postcode
	 */
	public JTextField getZipCode(){
		return member_postcode;
	}
	
	/**
	 * method returns member home number
	 * 
	 * @return home number
	 */
	public JTextField getHouseNumber(){
		return member_homenumber;
	}
	
	/**
	 * method returns member street
	 * 
	 * @return member street
	 */
	public JTextField getStreet(){
		return member_street;
	}
	
	/**
	 * method return member extra address 
	 * 
	 * @return
	 */
	public JTextField getExtraAdress(){
		return member_extraaddress;
	}
	
	/**
	 * method returns lastname
	 * 
	 * @return member lastname
	 */
	public JTextField getLastName(){
		return member_lastname;
	}
	
	/**
	 * method returns member firstname
	 * 
	 * @return member_firsname
	 */
	public JTextField getFirstName(){
		return member_firstname;
	}
	
	/**
	 * method returns member gender
	 * 
	 * @return member gender
	 */
	public JTextField getGender(){
		return member_gender;
	}
	
	/**
	 * method returns donator title
	 * 
	 * @return donator title
	 */
	public JTextField getDonatorTitle(){
		return member_title;
	}
	
	/**
	 * method returns donator id
	 * 
	 * @return donator id
	 */
	public int getDonatorID(){
		return association_donator.getNumber();
	}
	
	/**
	 * method returns member bankname.
	 * 
	 * @return member_bankname
	 */
	public JTextField getBankname(){
		return member_bankname;
	}
	
	/**
	 * method returns account owner.
	 * 
	 * @return member_accountowner
 	 */
	public JTextField getAccountOwner(){
		return member_accountowner;
	}
	
	/**
	 * method returns account number.
	 * 
	 * @return member_accountnumber
	 */
	public JTextField getAccountNumber(){
		return member_accountnumber;
	}

	/**
	 * method returns bankcode.
	 * 
	 * @return member_bankcode
	 */
	public JTextField getBankCode(){
		return member_bankcode;
	}

	/**
	 * method returns member group.
	 * 
	 * @return member_group
	 */
	public int getGroup(){
		return member_group.getSelectedIndex();
	}
	
	/**
	 * method sets member group.
	 * 
	 * @param index
	 */
	public void setGroup(int index){
		member_group.setSelectedIndex(index);
	}
	
	/**
	 * method returns member_status.
	 * 
	 * @return member_status
	 */
	public int getStatus(){
		return member_status.getSelectedIndex();
	}
	
	/**
	 * method sets member_status.
	 * 
	 * @param status
	 */
	public void setStatus(int state){
		member_status.setSelectedIndex(state);
	}
	
	/**
	 * method returns contribution button
	 * 
	 * @return contribution button
	 */
	public JButton getContributionButton(){
		return contribution_button;
	}
	
	/**
	 * method removes main panel from frame
	 */
	public void removePanel(){
		remove(mainpanel);
	}
	
	/**
	 * method creates a main panel
	 * 
	 * @param panel
	 */
	public void createPanel(JPanel panel){
		
		mainpanel = new BackgroundPanel();
		mainpanel.setLayout(new GridBagLayout());
		mainpanel.setOpaque(false);
		this.add(mainpanel, BorderLayout.CENTER);
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill=GridBagConstraints.HORIZONTAL;
		
		c.insets =new Insets(2,2,2,2);
		
		c.gridx=0;
		c.gridy=0;
		mainpanel.add(panel, c);
		
		this.setVisible(true);
	}
}