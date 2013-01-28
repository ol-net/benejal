
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;
import javax.swing.text.AbstractDocument;

import association.model.Account;
import association.model.Adress;
import association.model.AssociationDataTransfer;
import association.model.DocumentSizeFilter;
import association.model.DoubleDocument;
import association.model.Group;
import association.model.SpecialContribution;
import association.model.Telephone;

import association.view.BackgroundPanel;
import association.view.ButtonDesign;

import java.text.DecimalFormat;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import net.java.dev.designgridlayout.DesignGridLayout;

import member.controller.MemberActionListener;

import member.model.Member;
import moneybook.model.KassenBuch;

/**
 * class represents the frame for detailed user information
 * 
 * @author Leonid Oldenburger
 */
public class MemberInformation extends JFrame implements Observer{
	
	private static final long serialVersionUID = 1L;
	private String title = "Mitglied";
	private String title_association = "Förderverein";
	private JPanel mainpanel;

	private JPanel memberpanel;
	private JPanel memberaccountpanel;
	private JPanel membercontributionpanel;
	private JPanel buttonpanel;
	private JPanel rightpanel;
	private JPanel bpanel;
	private JPanel centerpanel;
	private JPanel comboboxpanel;
	private JPanel payed_button_panel;
	
	private DesignGridLayout layout;
	private DesignGridLayout layout_account;
	private DesignGridLayout layout_contribution;
	
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
	private String m_value_label = "Spendenbeitrag: ";
	private String m_contribution_date_label = "Zahlungsrhythmus: ";
	private String m_payment_form_label = "Zahlungsart: ";
	
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
	
	//language
	private String m_bank_label = "Bankverbindung";
	private String m_bankname_label = "Kreditinstitut: ";
	private String m_accountowner_label = "Kontoinhaber: ";
	private String m_accountnumber_label = "Kontonummer:*";
	private String m_bankcode_label = "Bankleitzahl:*";
	private String m_group_label = "Mitgliedergruppe:*";
	private String m_status_label = "Mitgliedstatus:*";
	
	// Textfields
	private JTextField member_bankname;
	private JTextField member_accountowner;
	private JTextField member_accountnumber;
	private JTextField member_bankcode;
	private JTextField member_value;
	private JComboBox member_group;
	private JComboBox member_status;
	private JComboBox member_date_payment;
	private JComboBox member_payment_form;
	private JComboBox member_contribution_form;
	
	// window-size
	private int height;
	private int width;
	
	private Member association_member;
	private Adress association_member_adress;
	private Telephone association_member_phone;
	private Account association_member_account;
	
	private JButton save_button;
	private JButton back_button;
	private JButton contribution_button;
	private JButton remove_button;
	private JButton payed_button;
	private JButton receipt_button;
	private JButton mail_button;
	
	private LinkedList<Group> group;
	private JLabel budget;
	
	private MemberAndDonatorFrame mframe;
	
	private SpecialContribution special_contribution;
	
	private JTextArea textArea;
	
	private AssociationDataTransfer association_data_transfer;
	
	@SuppressWarnings("unused")
	private KassenBuch money_book;
	
	private DecimalFormat df;
	
	private int MAX = 10;
	
	private int MAXBLZ = 8;
	 
    /**
     * constructor creates a new window.
     * 
     * @param aMember
     * @param mainf
     * @param moneyBook
     * @param adatatrans
     */
    
	public MemberInformation(Member aMember, MemberAndDonatorFrame mainf, KassenBuch moneyBook, AssociationDataTransfer adatatrans){
		
		this.mframe = mainf;
		this.height = 560;
		this.width = 850;
		this.association_member = aMember;
		this.special_contribution = association_member.getSpecialContribution();
		this.association_data_transfer = adatatrans;
		this.money_book = moneyBook;
		this.df = new DecimalFormat("#0.00");
		
		makePanel();
	}
	
	/**
	 * method makes panel
	 */
	public void makePanel(){
		setTitle(title_association+" - "+association_data_transfer.getAssociation().getName()+" - "+title+" - "+association_member.getFirstName()+" "+association_member.getLastName());
		setLayout(new BorderLayout());
		setMinimumSize(new Dimension(width, height)); 
		//setModal(true);
		
		mainpanel = new BackgroundPanel();
		mainpanel.setLayout(new BorderLayout());
		mainpanel.setOpaque(false);
		mainpanel.setBorder(new TitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY ), ""));
		
		rightpanel = new JPanel();
		rightpanel.setLayout(new GridBagLayout());
		rightpanel.setOpaque(false);
		
		centerpanel = new JPanel();
		centerpanel.setLayout(new GridBagLayout());
		centerpanel.setOpaque(false);
		
		memberpanel = new JPanel();
		memberpanel.setBorder(new TitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY ), "Mitgliederdaten"));
		memberpanel.setOpaque(false);
		
		memberaccountpanel = new JPanel();
		memberaccountpanel.setBorder(new TitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY ), m_bank_label));
		memberaccountpanel.setOpaque(false);
		
		membercontributionpanel = new JPanel();
		membercontributionpanel.setBorder(new TitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY ), "Zusatzspenden"));
		membercontributionpanel.setOpaque(false);
		
		bpanel = new JPanel();
		bpanel.setLayout(new GridBagLayout());
		bpanel.setOpaque(false);
		
		buttonpanel = new JPanel();
		buttonpanel.setLayout(new FlowLayout());
		buttonpanel.setOpaque(false);
		
		layout = new DesignGridLayout(memberpanel);
		makeBlank(layout);
		layout_account = new DesignGridLayout(memberaccountpanel);
		makeBlankAccount(layout_account);
		layout_contribution = new DesignGridLayout(membercontributionpanel);
		makeBlankContribution(layout_contribution);
		
		save_button = new ButtonDesign("images/save_icon.png");
		save_button.addActionListener(new MemberActionListener(association_member, this, mframe, null));
		back_button = new ButtonDesign("images/back_icon.png");
		back_button.addActionListener(new MemberActionListener(association_member, this, mframe, null));	
		contribution_button = new ButtonDesign("images/donation_icon.png");
		contribution_button.addActionListener(new MemberActionListener(association_member, this, mframe, null));
		remove_button = new ButtonDesign("images/remove_icon.png");
		remove_button.addActionListener(new MemberActionListener(association_member, this, mframe, null));
		receipt_button = new ButtonDesign("images/quittung_icon_small.png");
		receipt_button.addActionListener(new MemberActionListener(association_member, this, mframe, null));
		mail_button = new ButtonDesign("images/mail_icon_small.png");
		mail_button.addActionListener(new MemberActionListener(association_member, this, mframe, null));
		
		buttonpanel.add(back_button);
		buttonpanel.add(remove_button);
		buttonpanel.add(mail_button);
		buttonpanel.add(receipt_button);
		buttonpanel.add(contribution_button);
		buttonpanel.add(save_button);
		
		GridBagConstraints a = new GridBagConstraints();
		a.fill=GridBagConstraints.HORIZONTAL;
		
		a.insets =new Insets(2,2,2,2);
		
		a.gridx=1;
		a.gridy=0;
		rightpanel.add(memberaccountpanel, a);
		
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
		
		c.insets =new Insets(20,2,2,2);
		
		c.gridx=0;
		c.gridy=0;
		centerpanel.add(memberpanel, c);
		
		c.gridx=1;
		c.gridy=0;
		centerpanel.add(rightpanel, c);
		
		mainpanel.add(centerpanel, BorderLayout.NORTH);
		mainpanel.add(bpanel, BorderLayout.CENTER);
		
		add(mainpanel, BorderLayout.CENTER);
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		pack();
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
		
		String[] mstatus = {"schwebend", "fest", "gekündigt"};
		
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
		
		member_status = new JComboBox(mstatus);
		setStatus(association_member.getStatus());
		
	    textArea = new JTextArea(3, 3);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);

	    JScrollPane scrollPane = new JScrollPane(textArea);

	    JTextField dummy = new JTextField();
	    dummy.setVisible(false);
	    JTextField dummy2 = new JTextField();
	    dummy2.setVisible(false);
		
		if(association_member != null)
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
		layout.row().grid(new JLabel(" ")).add(dummy2);
		layout.row().grid(new JLabel(m_status_label), 1).add(member_status);
		layout.emptyRow();
		layout.row().grid(new JLabel("Bemerkung: "), 2).add(scrollPane);
		layout.emptyRow();
	}
	
	/**
	 * method creates a blank.
	 * 
	 * @param layout
	 */
	public void makeBlankAccount(DesignGridLayout layout){
			
		group = association_data_transfer.getGroup();
		
		String[] mcontributionform = {"Abbuchung", "Überweisung"};
		
		String[] mgroup = new String[group.size()];
		
		for(int i = 0; i < group.size(); i++){
			mgroup[i] = group.get(i).getGroup();
		}

		budget = new JLabel("");
		
		try{
			budget.setText(df.format(group.get(association_member.getGroupID()).getPremium().getVlaue())+ " "+association_data_transfer.getAssociation().getFinacneOffice().getCurrency());
		}catch(NullPointerException e){
		}
		
	    ActionListener actionListener = new ActionListener() {
	        public void actionPerformed(ActionEvent event) {
	        	budget.setText(df.format(group.get(member_group.getSelectedIndex()).getPremium().getVlaue())+ " "+association_data_transfer.getAssociation().getFinacneOffice().getCurrency());
	        	setVisible(true);
	        }
	     };
		JTextField dummy = new JTextField(4);
		dummy.setVisible(false);
		JTextField dummy2 = new JTextField(4);
		dummy2.setVisible(false);
		
		member_bankname = new JTextField(5);
		member_accountowner = new JTextField(5);
		member_accountnumber = new JTextField(10);
		member_bankcode = new JTextField(10);
		member_group = new JComboBox(mgroup);
		setGroup(association_member.getGroupID());
	    member_group.addActionListener(actionListener);
		member_contribution_form = new JComboBox(mcontributionform);
		setPayWay(association_member.getPaymentWay());
		
		payed_button = new ButtonDesign("images/payed__icon.png");
		payed_button.addActionListener(new MemberActionListener(association_member, this, mframe, null));

		FlowLayout fl = new FlowLayout();
		fl.setHgap(1);
		fl.setVgap(1);
		fl.setAlignment(FlowLayout.LEFT);

		payed_button_panel = new JPanel();
		payed_button_panel.setLayout(fl);
		payed_button_panel.setOpaque(false);
		
		payed_button_panel.add(payed_button);
		
		if(association_member != null)
			initTextFieldsAccount();
		
		((AbstractDocument) member_accountowner.getDocument()).setDocumentFilter(new DocumentSizeFilter(30, "."));
		((AbstractDocument) member_accountnumber.getDocument()).setDocumentFilter(new DocumentSizeFilter(10, "[0-9]+"));
		((AbstractDocument) member_bankcode.getDocument()).setDocumentFilter(new DocumentSizeFilter(8, "[0-9]+"));
		((AbstractDocument) member_bankname.getDocument()).setDocumentFilter(new DocumentSizeFilter(30, "."));
		
		layout.row().grid(new JLabel(m_accountowner_label)).add(member_accountowner);
		((AbstractDocument) member_accountnumber.getDocument()).setDocumentFilter(new DocumentSizeFilter(MAX, "[0-9]+"));
		layout.row().grid(new JLabel(m_accountnumber_label)).add(member_accountnumber);
		((AbstractDocument) member_bankcode.getDocument()).setDocumentFilter(new DocumentSizeFilter(MAXBLZ, "[0-9]+"));
		layout.row().grid(new JLabel(m_bankcode_label)).add(member_bankcode);
		layout.row().grid(new JLabel(m_bankname_label)).add(member_bankname);
		layout.row().grid(new JLabel(m_group_label)).add(member_group);
		layout.row().grid(new JLabel("Beitrag: ")).grid(budget);
		layout.row().grid(new JLabel(m_payment_form_label)).add(member_contribution_form);
		layout.row().grid().add(payed_button_panel);
	}
	
	/**
	 * method creates a blank.
	 * 
	 * @param layout
	 */
	public void makeBlankContribution(DesignGridLayout layout){
		
		String[] d_payment = {"keine Spenden", "monatlich", "quartalweise", "halbjährlich", "jährlich"};
		String[] p_form = {"Abbuchung", "Barzahlung"};
		member_value = new JTextField(5);
		member_value.setDocument(new DoubleDocument(member_value));
		
		comboboxpanel = new BackgroundPanel();
		comboboxpanel.setLayout(new BorderLayout());
		comboboxpanel.setOpaque(false);
		
	    ActionListener actionListener = new ActionListener() {
	        public void actionPerformed(ActionEvent event) {		

	    		String[] d_payment = {"keine Spenden", "monatlich", "quartalweise", "halbjährlich", "jährlich"};
	    		String[] d_payment1 = {"keine Spenden", "einmalig"};
	    		
	    		comboboxpanel.remove(member_date_payment);
	    	
	        	if(member_payment_form.getSelectedIndex() == 0){
	        		member_date_payment = new JComboBox(d_payment);
	        	}else{
	        		member_date_payment = new JComboBox(d_payment1);
	        	}
	        	
	    		if (special_contribution != null && member_payment_form.getSelectedIndex() != 1){
	    			member_value.setText(df.format(special_contribution.getVlaue()));
	    			member_date_payment.setSelectedIndex(special_contribution.getPayTime());
	    			member_payment_form.setSelectedIndex(special_contribution.getPayForm());
	    		}
	        	
	        	comboboxpanel.add(member_date_payment);
	        	setVisible(true);
	        }
	    };
		
		member_date_payment = new JComboBox(d_payment);
		member_payment_form = new JComboBox(p_form);
		member_payment_form.addActionListener(actionListener);
		
		comboboxpanel.add(member_date_payment);
		
		initTextDonation();

		layout.row().grid(new JLabel(m_value_label), 1).add(member_value);
		layout.emptyRow();
		layout.row().grid(new JLabel(m_payment_form_label)).add(member_payment_form);
		layout.emptyRow();
		layout.row().grid(new JLabel(m_contribution_date_label)).add(comboboxpanel);
		layout.emptyRow();
	}
	
	/**
	 * method initializes text fields.
	 */
	public void initTextField(){
		
		// get association member values 
		if (association_member.getTitle() != null)
			member_title.setText(association_member.getTitle());
		
		if (association_member.getGender() != null)
			member_gender.setText(association_member.getGender());
		
		if (association_member.getFirstName() != null)
			member_firstname.setText(association_member.getFirstName());

		if (association_member.getLastName() != null)
			member_lastname.setText(association_member.getLastName());
		
		if (association_member.getComment() != null)
			setTextArea(association_member.getComment());
				
		if(association_member.getAdress() != null)
			association_member_adress = association_member.getAdress();
		
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
		
		if(association_member.getPhone() != null)
			association_member_phone = association_member.getPhone();
		
		if(association_member_phone.getPrivatePhone() != "")
			member_phone.setText(association_member_phone.getPrivatePhone());
	}
	
	/**
	 * method initializes text fields.
	 */
	public void initTextDonation(){
		
		if (special_contribution != null){
			member_value.setText(df.format(special_contribution.getVlaue()));
			member_date_payment.setSelectedIndex(special_contribution.getPayTime());
			member_payment_form.setSelectedIndex(special_contribution.getPayForm());
		}
	}
	
	/**
	 * method initializes text fields.
	 */
	public void initTextFieldsAccount(){
		
		if(association_member.getAccount() != null)
			association_member_account = association_member.getAccount();
		
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
	 * set notice for member
	 * 
	 * @param text
	 */
	public void setTextArea(String text){
		textArea.setText(text);
	}
	
	/**
	 * get notice for member 
	 * 
	 * @return text
	 */
	public String getTextArea(){
		return textArea.getText();	
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
	 * @return remove_button
	 */
	public JButton getRemoveButton(){
		return remove_button;
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
	 * method returns back button
	 * 
	 * @return back button
	 */
	public JButton getPayedButton(){
		return payed_button;
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
	 * method returns receipt button
	 * 
	 * @return receipt button
	 */
	public JButton getReceiptButton(){
		return receipt_button;
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
	 * get member id 
	 * 
	 * @return member id
	 */
	public int getMemberID(){
		return association_member.getNumber();
	}
	
	/**
	 * get email
	 * 
	 * @return email
	 */
	public JTextField getEmail(){
		return member_email;
	}
	
	/**
	 * get fon number
	 * 
	 * @return fon number 
	 */
	public JTextField getPhone(){
		return member_phone;
	}
	
	/**
	 * country
	 * 
	 * @return country
	 */
	public JTextField getCountry(){
		return member_country;
	}
	
	/**
	 * city 
	 * 
	 * @return city
	 */
	public JTextField getCity(){
		return member_city;
	}
	
	/**
	 * zipcode
	 * 
	 * @return zipcode
	 */
	public JTextField getZipCode(){
		return member_postcode;
	}
	
	/**
	 * home number
	 * 
	 * @return home number
	 */
	public JTextField getHouseNumber(){
		return member_homenumber;
	}
	
	/**
	 * street
	 * 
	 * @return street
	 */
	public JTextField getStreet(){
		return member_street;
	}
	
	/**
	 * extra address
	 * 
	 * @return extra address
	 */
	public JTextField getExtraAdress(){
		return member_extraadress;
	}
	
	/**
	 * last name
	 * 
	 * @return last name
	 */
	public JTextField getLastName(){
		return member_lastname;
	}
	
	/**
	 * first name
	 * 
	 * @return first name
	 */
	public JTextField getFirstName(){
		return member_firstname;
	}
	
	/**
	 * gender
	 * 
	 * @return gender 
	 */
	public JTextField getGender(){
		return member_gender;
	}
	
	/**
	 * title
	 * 
	 * @return title
	 */
	public JTextField getMemberTitle(){
		return member_title;
	}
	
	/**
	 * payment time
	 * 
	 * @return selected index
	 */
	public int getPaymentTime(){
		return member_date_payment.getSelectedIndex();
	}
	
	/**
	 * payment form
	 * 
	 * @return selected index
	 */
	public int getPaymentForm(){
		return member_payment_form.getSelectedIndex();
	}
	
	/**
	 * value
	 * 
	 * @return value
	 */
	public JTextField getMemberValue(){
		return member_value;
	}
	
	/**
	 * method returns member bank name.
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
	
	public void removePanel(){
		remove(mainpanel);
	}
	
	/**
	 * method returns member_contribution_form.
	 * 
	 * @return member_contribution_form
	 */
	public int getPayWay(){
		return member_contribution_form.getSelectedIndex();
	}
	
	/**
	 * method sets member_contribution_form.
	 * 
	 * @param member_contribution´_form
	 */
	public void setPayWay(int state){
		member_contribution_form.setSelectedIndex(state);
	}
	
	/**
	 * method creates panel
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

	/**
	 * update method
	 */
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		makeBlankAccount(layout_account);
	}	
}