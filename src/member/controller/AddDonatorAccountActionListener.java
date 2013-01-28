
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

import member.view.AddDonator;
import member.view.AddDonatorBudget;
import member.view.AddDonatorData;
import member.view.ManageMembers;
import member.view.MemberAndDonatorFrame;
import moneybook.model.KassenBuch;

import association.model.Account;
import association.model.AssociationDataTransfer;

import association.view.InfoDialog;

/**
 * class represents ActionListener for adding donator account
 * 
 * @author Leonid Oldenburger
 */
public class AddDonatorAccountActionListener implements ActionListener{

	private MemberAndDonatorFrame mainframe;
	private AddDonatorBudget addbudgetdonator;
	private AddDonator adonator;
	private ManageMembers managemember;
	private AddDonatorData donatordata;
	private DonatorWithAdress association_donator;
	private DonatorWithAdress test_donator;
	private Account association_member_account;
	private AssociationDataTransfer association_data_transfer;
	private KassenBuch money_book; 
	
	/**
	 * constructor creates an action listener
	 * 
	 * @param adataDonator
	 * @param adonator
	 * @param moneyBook
	 * @param adatatrans
	 */
	public AddDonatorAccountActionListener(AddDonatorBudget adataDonator, DonatorWithAdress adonator, KassenBuch moneyBook, AssociationDataTransfer adatatrans){
		this.mainframe = adataDonator.getJFrame();
		this.addbudgetdonator = adataDonator;
		this.adonator = adataDonator.getAddDonator();
		this.managemember = adataDonator.getManageMembers();
		this.association_donator = adonator;
		this.association_data_transfer = adatatrans;
		this.money_book = moneyBook;
	}
	
	/**
	 * methode handles events.
	 */
	public void actionPerformed(ActionEvent event) {
		
		createDonatorsAccount();
		//createDonator();
		
		// next
		if (addbudgetdonator.getNextButton() == event.getSource()) {
			try {
				
				if(addbudgetdonator.getAccountNumber().getText().length() >= 3 && 
				   addbudgetdonator.getBankCode().getText().length() >= 3){
				
				    test_donator = association_data_transfer.getDonatorByName(association_donator.getFirstName(), association_donator.getLastName(), Long.valueOf(association_donator.getAccount().getAccountNumber()).longValue(), Long.valueOf(association_donator.getAccount().getBankCodeNumber()).longValue());
				
					if (test_donator != null){
						new InfoDialog("Dieser Spender bereits vorhanden!");
					}else {
						association_data_transfer.insertNewDonator(association_donator);
		        
						this.managemember = new ManageMembers(mainframe, 2, new Member(), association_donator, money_book, association_data_transfer);
				
						mainframe.removePane();
						mainframe.addTabbedPanel(managemember);
					}
				}else {
					new InfoDialog("Bitte füllen Sie die Pflichtfelder aus (min. 3 Zeichen)!");
				}
			} catch (Exception e) {
			}
		}
		
		// back button
		if (addbudgetdonator.getBackButton() == event.getSource()) {
			try {
				adonator.removePanel();
				donatordata = new AddDonatorData(mainframe, adonator, managemember, association_donator, money_book, association_data_transfer);
				adonator.setPanel(donatordata);
				mainframe.removePane();
				mainframe.addTabbedPanel(managemember);
			} catch (Exception e) {
			}
		}
	}
	
	/**
	 * methode creates a new member account.
	 */
	public void createDonatorsAccount(){
		
		if(association_member_account == null)
			association_member_account = new Account(); 
		
		if(!addbudgetdonator.getAccountOwner().getText().isEmpty())
			association_member_account.setOwner(addbudgetdonator.getAccountOwner().getText());
		if(!addbudgetdonator.getAccountNumber().getText().isEmpty())
			association_member_account.setAccountNumber(addbudgetdonator.getAccountNumber().getText());
		if(!addbudgetdonator.getBankCode().getText().isEmpty())
			association_member_account.setBankCodeNumber(addbudgetdonator.getBankCode().getText());
		if(!addbudgetdonator.getBankname().getText().isEmpty())
			association_member_account.setBankName(addbudgetdonator.getBankname().getText());
		association_donator.setAccount(association_member_account);
	}	
}
