
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
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.text.AbstractDocument;

import association.model.Account;
import association.model.AssociationDataTransfer;
import association.model.DocumentSizeFilter;

import association.model.SpecialContribution;
import association.view.BackgroundPanel;
import association.view.ButtonDesign;

import member.controller.AddDonatorAccountActionListener;
import member.model.DonatorWithAdress;
import moneybook.model.KassenBuch;

import net.java.dev.designgridlayout.DesignGridLayout;

/**
 * JPanel to add a new Member
 * 
 * @author Leonid Oldenburger
 */
public class AddDonatorBudget extends JPanel{

	private static final long serialVersionUID = 1L;
	private JPanel memberpanel;
	private JPanel contributionpanel;
	private JPanel buttonpanel;
	private JPanel comboboxpanel;
	
	private DesignGridLayout layout;
	private DesignGridLayout layoutcontribution;
	
	private String m_value_label = "Spendenbeitrag: ";
	private String m_contribution_date_label = "Spendenzeitraum: ";
	private String m_payment_form_label = "Zahlungsweg: ";
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
	@SuppressWarnings("rawtypes")
	private JComboBox member_group;
	@SuppressWarnings("rawtypes")
	private JComboBox member_status;
	
	// JButton
	private JButton next_button;
	private JButton back_button;
	
	private AddDonator adddonator;
	private MemberAndDonatorFrame mainframe;
	private ManageMembers managemembers;
	private DonatorWithAdress association_donator;
	private Account association_member_account;
	
	// Textfields

	private JTextField member_value;
	@SuppressWarnings("rawtypes")
	private JComboBox member_date_payment;
	@SuppressWarnings("rawtypes")
	private JComboBox member_payment_form;
	
	private SpecialContribution special_contribution;
	
	private AssociationDataTransfer association_data_transfer;
	private KassenBuch money_book;
	
	/**
	 * constructor
	 * 
	 * @param adonator
	 * @param moneyBook
	 * @param adatatrans
	 */
	public AddDonatorBudget(DonatorWithAdress adonator, KassenBuch moneyBook, AssociationDataTransfer adatatrans){
		this.association_donator = adonator;
		this.association_data_transfer = adatatrans;
		this.money_book = moneyBook;
		
		buildPanel();
	}
	
	/**
	 * constructor for gui to add a member.
	 * 
	 * @param frame
	 * @param donator
	 * @param mm
	 * @param adonator
	 * @param moneyBook
	 * @param adatatrans
	 */
	public AddDonatorBudget(MemberAndDonatorFrame frame, AddDonator donator, ManageMembers mm, DonatorWithAdress adonator, KassenBuch moneyBook, AssociationDataTransfer adatatrans){
		
		this.association_donator = adonator;
		this.mainframe = frame;
		this.adddonator = donator;
		this.managemembers = mm;
		this.association_data_transfer = adatatrans;
		this.money_book = moneyBook;
				
		buildPanel();
		
		next_button = new ButtonDesign("images/save_icon.png");
		next_button.addActionListener(new AddDonatorAccountActionListener(this, association_donator, money_book, association_data_transfer));
		back_button = new ButtonDesign("images/back_icon.png");
		back_button.addActionListener(new AddDonatorAccountActionListener(this, association_donator, money_book, association_data_transfer));
		
		GridBagConstraints b = new GridBagConstraints();
		b.fill=GridBagConstraints.HORIZONTAL;
		
		b.insets =new Insets(213,2,2,2);
		
		b.gridx=0;
		b.gridy=0;
		buttonpanel.add(back_button, b);
		
		b.gridx=1;
		b.gridy=0;
		buttonpanel.add(next_button, b);
	}
	
	/**
	 * build panel
	 */
	public void buildPanel(){
		
		setLayout(new GridBagLayout());
		//setBorder(new TitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY ), m_bank_label));
		setOpaque(false);
		
		memberpanel = new JPanel();
		memberpanel.setBorder(new TitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY ), m_bank_label));
		memberpanel.setOpaque(false);
		memberpanel.setLayout(new FlowLayout());
//		Dimension panelD = new Dimension(500,400);  
//		memberpanel.setPreferredSize(panelD);  
//		memberpanel.setMaximumSize(panelD);  
		
		contributionpanel = new JPanel();
		contributionpanel.setBorder(new TitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY ), "Spenden"));
		contributionpanel.setOpaque(false);
		contributionpanel.setLayout(new FlowLayout());
		
		buttonpanel = new JPanel();
		buttonpanel.setOpaque(false);
		buttonpanel.setLayout(new GridBagLayout());
		
		layout = new DesignGridLayout(memberpanel);
		makeBlank(layout);
		
		layoutcontribution = new DesignGridLayout(contributionpanel);
		makeBlankContribution(layoutcontribution);
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill=GridBagConstraints.HORIZONTAL;
		
		c.insets =new Insets(25,13,2,2);
		
		c.gridx=0;
		c.gridy=0;
		add(memberpanel, c);
		
//		c.gridx=0;
//		c.gridy=1;
//		add(contributionpanel, c);
		
		c.gridx=0;
		c.gridy=2;
		add(buttonpanel, c);
	}
	
	/**
	 * methode creates a blank.
	 * 
	 * @param layout
	 */
	public void makeBlank(DesignGridLayout layout){
		
		
		member_bankname = new JTextField(10);
		member_accountowner = new JTextField(10);
		member_accountowner.setText(association_donator.getFirstName()+ " " + association_donator.getLastName());
		member_accountnumber = new JTextField(10);
		member_bankcode = new JTextField(10);
		
		if(association_donator != null)
			initTextFields();
		
		((AbstractDocument) member_accountowner.getDocument()).setDocumentFilter(new DocumentSizeFilter(30, "."));
		((AbstractDocument) member_accountnumber.getDocument()).setDocumentFilter(new DocumentSizeFilter(10, "[0-9]+"));
		((AbstractDocument) member_bankcode.getDocument()).setDocumentFilter(new DocumentSizeFilter(8, "[0-9]+"));
		((AbstractDocument) member_bankname.getDocument()).setDocumentFilter(new DocumentSizeFilter(30, "."));

		layout.row().grid(new JLabel(m_accountowner_label), 2).add(member_accountowner).empty(1);
		layout.row().grid(new JLabel(m_accountnumber_label), 1).add(member_accountnumber);
		layout.row().grid(new JLabel(m_bankcode_label), 1).add(member_bankcode);
		layout.row().grid(new JLabel(m_bankname_label), 1).add(member_bankname);
	}
	
	
	/**
	 * methode creates a blank.
	 * 
	 * @param layout
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void makeBlankContribution(DesignGridLayout layout){
		
		String[] d_payment = {"keine Spenden", "monatlich", "quartalweise", "halbjährlich", "jährlich"};
		String[] p_form = {"Abbuchung", "Barzahlung"};
		member_value = new JTextField(10);
		
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
	    			member_value.setText((new Double(special_contribution.getVlaue())).toString());
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
		
		layout.row().grid(new JLabel(m_value_label), 2).add(member_value).empty(1);
		layout.row().grid(new JLabel(m_payment_form_label), 1).add(member_payment_form);
		layout.row().grid(new JLabel(m_contribution_date_label), 1).add(comboboxpanel);
	}
	
	/**
	 * methode return nex button
	 * 
	 * @return nextbutton
	 */
	public JButton getNextButton(){
		return next_button;
	}
	
	/**
	 * methode return back button
	 * 
	 * @return backbutton
	 */
	public JButton getBackButton(){
		return back_button;
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
	
	/**
	 * methode returns member bankname.
	 * 
	 * @return member_bankname
	 */
	public JTextField getBankname(){
		return member_bankname;
	}
	
	/**
	 * methode returns account owner.
	 * 
	 * @return member_accountowner
 	 */
	public JTextField getAccountOwner(){
		return member_accountowner;
	}
	
	/**
	 * methode returns account number.
	 * 
	 * @return member_accountnumber
	 */
	public JTextField getAccountNumber(){
		return member_accountnumber;
	}

	/**
	 * methode returns bankcode.
	 * 
	 * @return member_bankcode
	 */
	public JTextField getBankCode(){
		return member_bankcode;
	}

	/**
	 * methode returns member group.
	 * 
	 * @return member_group
	 */
	public int getGroup(){
		return member_group.getSelectedIndex();
	}
	
	/**
	 * methode returns member_status.
	 * 
	 * @return member_status
	 */
	public int getStatus(){
		return member_status.getSelectedIndex();
	}
	
	
	public int getPaymentTime(){
		return member_date_payment.getSelectedIndex();
	}
	
	public int getPaymentForm(){
		return member_payment_form.getSelectedIndex();
	}
	
	public JTextField getMemberValue(){
		return member_value;
	}
	
	/**
	 * methode inits textfields.
	 */
	public void initTextFields(){
		
		if(association_donator.getAccount() != null)
			association_member_account = association_donator.getAccount();
		
		if(association_member_account.getOwner() != "")
			member_accountowner.setText(association_member_account.getOwner());
		
		if(association_member_account.getAccountNumber() != "")
			member_accountnumber.setText(association_member_account.getAccountNumber());
		
		if(association_member_account.getBankCodeNumber() != "")
			member_bankcode.setText(association_member_account.getBankCodeNumber());
		
		if(association_member_account.getBankName() != "")
			member_bankname.setText(association_member_account.getBankName());
	}
	
	/**
	 * methode inits textfields.
	 */
	public void initTextDonation(){
		
		if (special_contribution != null){
			member_value.setText((new Double(special_contribution.getVlaue())).toString());
			member_date_payment.setSelectedIndex(special_contribution.getPayTime());
			member_payment_form.setSelectedIndex(special_contribution.getPayForm());
		}
	}
}
