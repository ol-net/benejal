
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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Date;
import java.util.LinkedList;

import mail.model.MassReceipt;
import mail.model.Receipt;

import mail.view.MView;
import mail.view.MailView;
import mail.view.MassDonationReceipt;
import mail.view.SingleDonationReceipt;

import member.view.DonatorInformation;
import member.view.ManageMembers;
import member.view.MemberAndDonatorFrame;
import member.view.MemberInformation;
import moneybook.model.KassenBuch;

import association.model.AssociationDataTransfer;
import association.model.Group;

import java.text.SimpleDateFormat;

import javax.swing.ButtonModel;

/**
 * class represents ActionListener for the receipt
 * 
 * @author Leonid Oldenburger
 */
public class ReceiptActionListener implements ActionListener{
	
	private MemberAndDonatorFrame mainframe;
	private ManageMembers mame;
	private MassDonationReceipt receiptview; 
	private MView allmailview;
	private MailView mailview;
	private Date dateNow;
	private Receipt new_receipt;
	private LinkedList<Group> group_list;
	private AssociationDataTransfer association_data_transfer;
	@SuppressWarnings("unused")
	private KassenBuch money_book;
	private MemberInformation member_information;
	private SingleDonationReceipt one_receipt_view;
	private DonatorInformation donator_information;
	
	/**
	 * constructor creates a actionlistener for the receipt
	 * 
	 * @param amailview
	 * @param rview
	 * @param moneyBook
	 * @param adatatrans
	 */
	public ReceiptActionListener(MView amailview, MassDonationReceipt rview, KassenBuch moneyBook, AssociationDataTransfer adatatrans){
		this.association_data_transfer = adatatrans;
		this.allmailview = amailview;
		this.one_receipt_view = null;
		this.mainframe = allmailview.getFrame();
		this.mame = allmailview.getManagePanel();
		this.receiptview = rview;
		this.money_book = moneyBook;
	}
	
	/**
	 * constructor creates a actionlistener for the receipt
	 * 
	 * @param amailview
	 * @param rview
	 * @param moneyBook
	 * @param adatatrans
	 */
	public ReceiptActionListener(MemberInformation m_info, SingleDonationReceipt rview){
		this.association_data_transfer = AssociationDataTransfer.getInstance();
		this.member_information = m_info;
		this.donator_information = null;
		this.receiptview = rview;
		this.one_receipt_view = rview;
		this.money_book = KassenBuch.getInstance("jdbc:derby:.//derbydbs;create=true");
	}
	
	/**
	 * constructor creates a actionlistener for the receipt
	 * 
	 * @param amailview
	 * @param rview
	 * @param moneyBook
	 * @param adatatrans
	 */
	public ReceiptActionListener(DonatorInformation d_info, SingleDonationReceipt rview){
		this.association_data_transfer = AssociationDataTransfer.getInstance();
		this.donator_information = d_info;
		this.member_information = null;
		this.receiptview = rview;
		this.one_receipt_view = rview;
		this.money_book = KassenBuch.getInstance("jdbc:derby:.//derbydbs;create=true");
	}

	/**
	 * method handles events
	 */
	public void actionPerformed(ActionEvent event) {
		
		if(one_receipt_view == null){
			if (receiptview.getBackButton() == event.getSource()) {
				try {
					this.mailview = new MailView(allmailview);					
					allmailview.removePanel();
					allmailview.setPanel(mailview);
				
					mainframe.removePane();
					mainframe.addTabbedPanel(mame);
			
				} catch (Exception e) {				}
			}
			
			if (receiptview.getSaveButton() == event.getSource()) {
				try {

					dateNow = new Date();
					SimpleDateFormat dateformatMMDDYYYY = new SimpleDateFormat("dd.MM.yyyy");
					StringBuilder nowMMDDYYYY = new StringBuilder(dateformatMMDDYYYY.format(dateNow));
		        
					group_list = association_data_transfer.getGroup();
				
					String[] mgroup = new String[group_list.size()+2];
				
					mgroup[0] = "Mitglieder";
					mgroup[1] = "Spender";
				
					for(int j = 0; j < group_list.size(); j++){
						mgroup[j+2] = group_list.get(j).getGroup();
					}	
		        
					String group = mgroup[receiptview.getMemberGroup()];

					createReceipt();
					new_receipt.setMemberGroup(group);
					new_receipt.setDate(nowMMDDYYYY.toString());

					new MassReceipt(new_receipt, group, receiptview.getMemberGroup(), -10);
			
					association_data_transfer.setReceipt(new_receipt);
		        
				} catch (Exception e) {
				}
			}
		}
		
		if(one_receipt_view != null){
			
			if (one_receipt_view.getBButton() == event.getSource()) {
				try {
					if(donator_information == null){
						member_information.removePanel();
						member_information.makePanel();
					}else if(member_information == null){
						donator_information.removePanel();
						donator_information.makePanel();
					}
				} catch (Exception e) {
				}
			}
			
			if (one_receipt_view.getNButton() == event.getSource()) {
				try {
			        
					dateNow = new Date();
					SimpleDateFormat dateformatMMDDYYYY = new SimpleDateFormat("dd.MM.yyyy");
					StringBuilder nowMMDDYYYY = new StringBuilder(dateformatMMDDYYYY.format(dateNow));
		        
					String name = "";
					
					if(donator_information == null){
						name = member_information.getFirstName().getText()+" "+member_information.getLastName().getText();
					}else if(member_information == null){
						name = donator_information.getFirstName().getText()+" "+donator_information.getLastName().getText();
					}
					createReceipt();

					
					new_receipt.setMemberGroup(name);
					new_receipt.setDate(nowMMDDYYYY.toString());

					if(donator_information == null){
						new MassReceipt(new_receipt, name, -1, member_information.getMemberID());
					}else if(member_information == null){
						new MassReceipt(new_receipt, name, -1, donator_information.getDonatorID());
					}
			
					association_data_transfer.setReceipt(new_receipt);
		        
				} catch (Exception e) {
				}
			}
		}
	}
	
	/**
	 * method creates a new receipt
	 */
	public void createReceipt(){
		
		ButtonModel selectedModel = receiptview.getButtonGroup1().getSelection();
		ButtonModel selectedModel2 = receiptview.getButtonGroup2().getSelection();
		
		int yes = 0;
		int no = 0;
		int radio_button_1 = 0;
		int radio_button_2 = 0;
		
		if (receiptview.getRadioButtonYes().getModel() == selectedModel){
			yes = 1;
		}else if(receiptview.getRadioButtonNo().getModel() == selectedModel){
			no = 1;
		}
		
		if(receiptview.getRadioButton1().getModel() == selectedModel2){
			radio_button_1 = 1;
		}else if(receiptview.getRadioButton2().getModel() == selectedModel2){
			radio_button_2 = 1;
		}
		
		new_receipt = new Receipt();
		
		if(yes == 1){
			new_receipt.setExpense(1);
		}else if(no == 1){
			new_receipt.setExpense(0);
		}else{
			new_receipt.setExpense(-1);
		}
		
		if(radio_button_1 == 1){
			new_receipt.setBase1(1);
		}else if(radio_button_2 == 1){
			new_receipt.setBase1(0);
		}else{
			new_receipt.setBase1(-1);
		}
		
		if(receiptview.getCheckbox3().isSelected() == true){
			new_receipt.setBase2(1);
		}else{
			new_receipt.setBase2(0);
		}
		
		if(!receiptview.getText1().getText().isEmpty()){
			new_receipt.setObject1(receiptview.getText1().getText());
		}
		
		if(!receiptview.getText2().getText().isEmpty()){
			new_receipt.setObject2(receiptview.getText2().getText());
		}
		
		if(!receiptview.getText3().getText().isEmpty()){
			new_receipt.setObject3(receiptview.getText3().getText());
		}
	}
}