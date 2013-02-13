
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
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

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
import association.model.Group;

import association.view.ButtonDesign;

import member.controller.AddMemberAccountActionListener;

import member.model.Member;
import moneybook.model.KassenBuch;

import net.java.dev.designgridlayout.DesignGridLayout;

/**
 * JPanel to add a new Member
 * 
 * @author Leonid Oldenburger
 */
public class AddMemberBudget extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	private JPanel memberpanel;
	private JPanel buttonpanel;
	private DesignGridLayout layout;
	
	//language
	private String m_bank_label = "Bankverbindung";
	private String m_bankname_label = "Kreditinstitut: ";
	private String m_accountowner_label = "Kontoinhaber: ";
	private String m_accountnumber_label = "Kontonummer:*";
	private String m_bankcode_label = "Bankleitzahl:*";
	private String m_group_label = "Mitgliedergruppe:*";
	private String m_status_label = "Mitgleidstatus:*";
	
	// Textfields
	private JTextField member_bankname;
	private JTextField member_accountowner;
	private JTextField member_accountnumber;
	private JTextField member_bankcode;
	@SuppressWarnings("rawtypes")
	private JComboBox member_group;
	@SuppressWarnings("rawtypes")
	private JComboBox member_status;
	@SuppressWarnings("rawtypes")
	private JComboBox member_contribution_form;
	
	// JButton
	private JButton next_button;
	private JButton back_button;
	
	private AddMember addmember;
	private MemberAndDonatorFrame mainframe;
	private ManageMembers managemembers;
	private Member association_member;
	private Account association_member_account;
	
	private LinkedList<Group> group;
	private JLabel budget;
	
	private AssociationDataTransfer association_data_transfer;
	private KassenBuch money_book;
	
	private DecimalFormat df;
	
	/**
	 * 
	 * @param amember
	 * @param moneyBook
	 * @param adatatrans
	 */
	public AddMemberBudget(Member amember, KassenBuch moneyBook, AssociationDataTransfer adatatrans){
		this.association_member = amember;
		this.association_data_transfer = adatatrans;
		this.money_book = moneyBook;
		this.df = new DecimalFormat("#0.00");
		buildPanel();
	}
	
	/**
	 * constructor for gui to add a member.
	 */
	public AddMemberBudget(MemberAndDonatorFrame frame, AddMember mem, ManageMembers mm, Member amember, KassenBuch moneyBook, AssociationDataTransfer adatatrans){
		
		this.association_member = amember;
		this.mainframe = frame;
		this.addmember = mem;
		this.managemembers = mm;
		this.association_data_transfer = adatatrans;
		this.money_book = moneyBook;
		
		this.df = new DecimalFormat("#0.00");
				
		buildPanel();
		
		next_button = new ButtonDesign("images/save_icon.png");
		next_button.addActionListener(new AddMemberAccountActionListener(this, association_member, money_book, association_data_transfer));
		back_button = new ButtonDesign("images/back_icon.png");
		back_button.addActionListener(new AddMemberAccountActionListener(this, association_member, money_book, association_data_transfer));
		
		GridBagConstraints b = new GridBagConstraints();
		b.fill=GridBagConstraints.HORIZONTAL;
		
		b.insets =new Insets(76,3,2,2);
		
		b.gridx=0;
		b.gridy=0;
		buttonpanel.add(back_button, b);
		
		b.gridx=1;
		b.gridy=0;
		buttonpanel.add(next_button, b);
	}
	
	/**
	 * methode builds panel
	 */
	public void buildPanel(){
		
		setLayout(new GridBagLayout());
		//setBorder(new TitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY ), m_bank_label));
		setOpaque(false);
		
		memberpanel = new JPanel();
		memberpanel.setBorder(new TitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY ), m_bank_label));
		memberpanel.setOpaque(false);
		memberpanel.setLayout(new FlowLayout());
		Dimension panelD = new Dimension(500,400);  
		memberpanel.setPreferredSize(panelD);  
		memberpanel.setMaximumSize(panelD);  
		
		buttonpanel = new JPanel();
		buttonpanel.setOpaque(false);
		buttonpanel.setLayout(new GridBagLayout());
		
		layout = new DesignGridLayout(memberpanel);
		makeBlank(layout);
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill=GridBagConstraints.HORIZONTAL;
		
		c.insets =new Insets(29,11,2,2);
		
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void makeBlank(DesignGridLayout layout){
			
		group = association_data_transfer.getGroup();
		
		String[] mgroup = new String[group.size()];
		
		for(int i = 0; i < group.size(); i++){
			mgroup[i] = group.get(i).getGroup();
		}
		
		//String[] mgroup = {"normales Mitglied", "Student/Schüler", "Rentner", "Ehrenmitglied"};
		String[] mstatus = {"schwebend", "fest"};
		
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
		
		member_bankname = new JTextField(5);
		member_accountowner = new JTextField(5);
		member_accountowner.setText(association_member.getFirstName()+ " " + association_member.getLastName());
		member_accountnumber = new JTextField(10);
		member_bankcode = new JTextField(10);
		member_group = new JComboBox(mgroup);
	    member_group.addActionListener(actionListener);
		member_status = new JComboBox(mstatus);
		
		String[] mcontributionform = {"Abbuchung", "Überweisung"};
		
		member_contribution_form = new JComboBox(mcontributionform);
		
		if(association_member != null)
			initTextFields();
		
		((AbstractDocument) member_accountowner.getDocument()).setDocumentFilter(new DocumentSizeFilter(30, "."));
		((AbstractDocument) member_accountnumber.getDocument()).setDocumentFilter(new DocumentSizeFilter(10, "[0-9]+"));
		((AbstractDocument) member_bankcode.getDocument()).setDocumentFilter(new DocumentSizeFilter(8, "[0-9]+"));
		((AbstractDocument) member_bankname.getDocument()).setDocumentFilter(new DocumentSizeFilter(30, "."));
		
		layout.row().grid(new JLabel(m_accountowner_label), 2).add(member_accountowner).empty(1);
		layout.row().grid(new JLabel(m_accountnumber_label), 1).add(member_accountnumber);
		layout.row().grid(new JLabel(m_bankcode_label), 1).add(member_bankcode);
		layout.row().grid(new JLabel(m_bankname_label), 1).add(member_bankname);
		layout.emptyRow();
		layout.row().grid(new JLabel(m_group_label), 1).add(member_group);
		layout.emptyRow();
		layout.row().grid(new JLabel("Beitrag:"), 1).add(budget);
		layout.emptyRow();
		layout.row().grid(new JLabel("Zahlungsart:"), 1).add(member_contribution_form);
		layout.row().grid(new JLabel(m_status_label), 1).add(member_status);
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
	public AddMember getAddMember(){
		return addmember;
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
	 * methode returns member_contribution_form.
	 * 
	 * @return member_contribution_form
	 */
	public int getPayWay(){
		return member_contribution_form.getSelectedIndex();
	}
	
	/**
	 * methode returns member_status.
	 * 
	 * @return member_status
	 */
	public int getStatus(){
		return member_status.getSelectedIndex();
	}
	
	/**
	 * methode inits textfields.
	 */
	public void initTextFields(){
		
		if(association_member.getAccount() != null)
			association_member_account = association_member.getAccount();
		
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
	 * update methode
	 */
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		makeBlank(layout);
	}
}
