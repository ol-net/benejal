
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
package member.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import member.model.DonatorWithAdress;
import member.model.Member;

import member.view.AddMember;
import member.view.AddMemberBudget;
import member.view.AddMemberData;
import member.view.ManageMembers;
import member.view.MemberAndDonatorFrame;
import moneybook.model.KassenBuch;

import association.model.Account;
import association.model.AssociationDataTransfer;

import association.view.InfoDialog;

/**
 * class represents ActionListener
 * 
 * @author Leonid Oldenburger
 */
public class AddMemberAccountActionListener implements ActionListener{

	private MemberAndDonatorFrame mainframe;
	private AddMemberBudget addbudgetmember;
	private AddMember amember;
	private ManageMembers managemember;
	private AddMemberData memberdata;
	private Member association_member;
	private Member test_member;
	private Account association_member_account;
	private DonatorWithAdress test_donator;
	
	private KassenBuch money_book;
	private AssociationDataTransfer association_data_transfer;
	
	/**
	 * constructor creates an actionlistener 
	 * 
	 * @param adataMember
	 * @param amember
	 * @param moneyBook
	 * @param adatatrans
	 */
	public AddMemberAccountActionListener(AddMemberBudget adataMember, Member amember, KassenBuch moneyBook, AssociationDataTransfer adatatrans){
		this.mainframe = adataMember.getJFrame();
		//this.font_style = mainframe.getFontStyle();
		this.addbudgetmember = adataMember;
		this.amember = adataMember.getAddMember();
		this.managemember = adataMember.getManageMembers();
		this.association_member = amember;
		this.association_data_transfer = adatatrans;
		this.money_book = moneyBook;
	}
	
	/**
	 * methode handles events.
	 */
	public void actionPerformed(ActionEvent event) {
		
		createMembersAccount();
		
		// next
		if (addbudgetmember.getNextButton() == event.getSource()) {
			try {
				if(addbudgetmember.getAccountNumber().getText().length() >= 3 && 
						addbudgetmember.getBankCode().getText().length() >= 3 || addbudgetmember.getPayWay() == 1){
					
					if(!addbudgetmember.getBankCode().getText().isEmpty() && !addbudgetmember.getAccountNumber().getText().isEmpty())
						test_member = association_data_transfer.getMember(association_member.getFirstName(), association_member.getLastName(), Long.valueOf(association_member.getAccount().getAccountNumber()).longValue(), Long.valueOf(association_member.getAccount().getBankCodeNumber()).longValue());
					
					if(test_member != null){
						new InfoDialog("Dieses Mitglied bereits vorhanden!");
					}else {
						association_data_transfer.insertMember(association_member);
						
						if(!addbudgetmember.getBankCode().getText().isEmpty() && !addbudgetmember.getAccountNumber().getText().isEmpty())
							test_donator = association_data_transfer.getDonatorByName(association_member.getFirstName(), association_member.getLastName(), Long.valueOf(association_member.getAccount().getAccountNumber()).longValue(), Long.valueOf(association_member.getAccount().getBankCodeNumber()).longValue());
						
					    // delete donator after transform to member
					    if(test_donator != null){
					    	association_data_transfer.deleteDonator(test_donator.getNumber());
					    }
					    
						this.managemember = new ManageMembers(mainframe, 0, new Member(), null, money_book, association_data_transfer);
					    
						mainframe.removePane();
						mainframe.addTabbedPanel(managemember);
					    
						if(association_member.getAccount().getAccountNumber() != null && association_member.getAccount().getBankCodeNumber() != null)
							test_member = association_data_transfer.getMember(association_member.getFirstName(), association_member.getLastName(), Long.valueOf(association_member.getAccount().getAccountNumber()).longValue(), Long.valueOf(association_member.getAccount().getBankCodeNumber()).longValue());
						
					    if (money_book.getDonations(test_donator.getNumber()).size() != 0)
					    	money_book.updateMemberNr(test_donator.getNumber(), test_member.getNumber());
					}
				}else {
					new InfoDialog("Bitte füllen Sie die Pflichtfelder aus (min. 3 Zeichen)!");
				}
			} catch (Exception e) {
			}
		}
		
		// back button
		if (addbudgetmember.getBackButton() == event.getSource()) {
			try {
				amember.removePanel();
				memberdata = new AddMemberData(mainframe, amember, managemember, association_member, money_book, association_data_transfer);
				amember.setPanel(memberdata);
				mainframe.removePane();
				mainframe.addTabbedPanel(managemember);
			} catch (Exception e) {
			}
		}
	}
	
	/**
	 * methode creates a new member account.
	 */
	public void createMembersAccount(){
		
		if(association_member_account == null)
			association_member_account = new Account(); 
		
		if(!addbudgetmember.getAccountOwner().getText().isEmpty())
			association_member_account.setOwner(addbudgetmember.getAccountOwner().getText());
		if(!addbudgetmember.getAccountNumber().getText().isEmpty())
			association_member_account.setAccountNumber(addbudgetmember.getAccountNumber().getText());
		if(!addbudgetmember.getBankCode().getText().isEmpty())
			association_member_account.setBankCodeNumber(addbudgetmember.getBankCode().getText());
		if(!addbudgetmember.getBankname().getText().isEmpty())
			association_member_account.setBankName(addbudgetmember.getBankname().getText());
		association_member.setAccount(association_member_account);
		
		association_member.setGroupID(addbudgetmember.getGroup());
		association_member.setContribution(association_data_transfer.getAssociation().getGroupList().get(addbudgetmember.getGroup()).getPremium().getVlaue());
		association_member.setSumContribution(association_data_transfer.getAssociation().getGroupList().get(addbudgetmember.getGroup()).getPremium().getVlaue());
		association_member.setStatus(addbudgetmember.getStatus());
		association_member.setPaymentWay(addbudgetmember.getPayWay());
		if(addbudgetmember.getStatus() == Member.SCHWEBEND){
			association_member.setPayStatus(Member.EMPTY);
		}else if(addbudgetmember.getStatus() == Member.SCHWEBEND){
			association_member.setPayStatus(Member.EMPTY);
		}else{
			association_member.setPayStatus(Member.FAELLIG);
		}
		association_member.setContributionDid(0);
	}	
}
