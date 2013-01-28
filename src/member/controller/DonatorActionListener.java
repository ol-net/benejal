
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

import association.model.Account;
import association.model.Adress;
import association.model.AssociationDataTransfer;
import association.model.Telephone;

import association.view.InfoDialog;

import mail.view.SingleDonationReceipt;
import mail.view.SingleMailView;
import member.model.DonatorWithAdress;
import member.model.Member;

import member.view.DonatorContributionView;
import member.view.DonatorInformation;
import member.view.ManageMembers;
import member.view.MemberAndDonatorFrame;
import moneybook.model.KassenBuch;

import member.view.DeleteDonator;

/**
 * class represents ActionListener for donators
 * 
 * @author Leonid Oldenburger
 */
public class DonatorActionListener implements ActionListener{
	
	@SuppressWarnings("unused")
	private String title = "Spendenübersicht";
	private String title_receipt = "Spendenbescheinigung";
	private String title_association = "Förderverein";
	private DonatorWithAdress association_donator;
	private DonatorInformation donator_information;
	@SuppressWarnings("unused")
	private int userID;
	private Account association_donator_account;
	private Adress association_donator_adress;
	private Telephone association_donator_phone;
	private MemberAndDonatorFrame mframe;
	private ManageMembers mmpanel;
	private AssociationDataTransfer association_data_transfer;	
	private KassenBuch money_book;
	@SuppressWarnings("unused")
	private DonatorWithAdress donator;
	private int donator_id;
	private Member member;
	private DonatorContributionView dContributionView;
	
	/**
	 * constructor creates a donator actionlistener
	 * 
	 * @param donatorID
	 * @param dInfo
	 * @param mainframe
	 * @param moneyBook
	 * @param adatatrans
	 */
	public DonatorActionListener(DonatorInformation dInfo, MemberAndDonatorFrame mainframe){

		this.mframe = mainframe;
		this.donator_id = dInfo.getDonatorID();
		this.association_donator = new DonatorWithAdress();
		this.donator_information = dInfo;
		this.association_data_transfer = AssociationDataTransfer.getInstance();
		this.money_book = KassenBuch.getInstance("jdbc:derby:.//derbydbs;create=true");
		this.dContributionView = null;
	}
	
	/**
	 * constructor creates a donator actionlistener
	 * 
	 * @param donatorID
	 * @param dInfo
	 * @param mainframe
	 * @param moneyBook
	 * @param adatatrans
	 * @param dContributionView
	 */
	public DonatorActionListener(DonatorInformation dInfo, DonatorContributionView dCView){

		this.dContributionView = dCView;
		this.donator_id = dInfo.getDonatorID();;
		this.association_donator = new DonatorWithAdress();
		this.donator_information = dInfo;
		this.association_data_transfer = AssociationDataTransfer.getInstance();
		this.money_book = KassenBuch.getInstance("jdbc:derby:.//derbydbs;create=true");
	}

	/**
	 * methode handles events
	 */
	public void actionPerformed(ActionEvent event) {
		
		if(dContributionView == null){
			if (donator_information.getContributionButton() == event.getSource()) {
				try {
					
					donator_information.removePanel();
					//donator_information.setTitle(title_association+" - "+association_data_transfer.getAssociation().getName()+" - "+title+" - "+donator.getFirstName()+" "+donator.getLastName());
					donator_information.createPanel(new DonatorContributionView(donator_information, money_book, association_data_transfer));
				} catch (Exception e) {
				}
			}
			
			if (donator_information.getBackButton() == event.getSource()) {
				try {
					donator_information.dispose();		
					mframe.removePane();
					this.mmpanel = new ManageMembers(mframe, 2, new Member(), association_donator, money_book, association_data_transfer);
					mframe.addTabbedPanel(mmpanel);
			
				} catch (Exception e) {
				}
			}
			
			if (donator_information.getRemoveButton() == event.getSource()) {
				try {
					new DeleteDonator(donator_id, donator_information);
				}catch (Exception e) {
				}
			}
			
			if (donator_information.getReceiptButton() == event.getSource() && dContributionView == null) {
				try {
					donator_information.removePanel();
					donator_information.setTitle(title_association+" - "+association_data_transfer.getAssociation().getName()+" - "+title_receipt+" für "+association_donator.getFirstName()+" "+association_donator.getLastName());

					donator_information.createPanel(new SingleDonationReceipt(donator_information));
				} catch (Exception e) {
				}
			}
			
			if (donator_information.getSaveButton() == event.getSource() && dContributionView == null) {
				try {
				
					if (donator_information.getFirstName().getText().length() >= 3 && 
						donator_information.getLastName().getText().length() >= 3 && 
						donator_information.getStreet().getText().length() >= 3 && 
						donator_information.getHouseNumber().getText().length() >= 1 && 
						donator_information.getCity().getText().length() >= 3 &&
						donator_information.getZipCode().getText().length() >= 3 && 
						donator_information.getAccountNumber().getText().length() >= 3 && 
						donator_information.getBankCode().getText().length() >= 3){
				
						createDonatorAccount();
						createDonator();
						
						association_donator.setType(1);
				
						association_data_transfer.updateDonator(association_donator);
				
						new InfoDialog("Benutzerdaten gespeichert");
					}else{
						new InfoDialog("Bitte füllen Sie die Pflichtfelder aus (min. 3 Zeichen)!");
					}
				} catch (Exception e) {
				}
			}
			
			if (donator_information.getMemberButton() == event.getSource() && dContributionView == null) {
				try {
					createDonatorAccount();
					createDonator();
					createMember();
				
					donator_information.dispose();
				
					mframe.removePane();

					this.mmpanel = new ManageMembers(mframe, 1, member, association_donator, money_book, association_data_transfer);
				
					mframe.addTabbedPanel(mmpanel);
				} catch (Exception e) {
				}
			}
			
			if (donator_information.getMailButton() == event.getSource()) {
				try {
					donator_information.removePanel();
					donator_information.setTitle(title_association+" - "+association_data_transfer.getAssociation().getName()+" - Brief an "+association_donator.getFirstName()+" "+association_donator.getLastName());

					donator_information.createPanel(new SingleMailView(donator_information));
				} catch (Exception e) {
				}
			}
		}
		
		if(dContributionView != null){
			if (dContributionView.getReceiptButton() == event.getSource()) {
				try {
					donator_information.removePanel();
					donator_information.setTitle(title_association+" - "+association_data_transfer.getAssociation().getName()+" - "+title_receipt+" für "+association_donator.getFirstName()+" "+association_donator.getLastName());

					donator_information.createPanel(new SingleDonationReceipt(donator_information));
				} catch (Exception e) {
				}
			}
		}
	}
	
	/**
	 * methode create a new member
	 */
	public void createMember(){
		
		member = new Member();
		
		if(association_donator.getTitle() != null)
			member.setTitle(association_donator.getTitle());
		if(association_donator.getGender() != null)
			member.setGender(association_donator.getGender());
		if(association_donator.getFirstName() != null)
			member.setFirstName(association_donator.getFirstName());
		if(association_donator.getLastName() != null)
			member.setLastName(association_donator.getLastName());
		if(association_donator.getAccount() != null)
			member.setAccount(association_donator.getAccount());
		if(association_donator.getAdress() != null)
			member.setAdress(association_donator.getAdress());
		if(association_donator.getPhone() != null)
			member.setPhone(association_donator.getPhone());
	}
	
	/**
	 * methode creates a new member account.
	 */
	public void createDonatorAccount(){
		
		if(association_donator_account == null)
			association_donator_account = new Account(); 
		
		if(!donator_information.getAccountOwner().getText().isEmpty())
			association_donator_account.setOwner(donator_information.getAccountOwner().getText());
		if(!donator_information.getAccountNumber().getText().isEmpty())
			association_donator_account.setAccountNumber(donator_information.getAccountNumber().getText());
		if(!donator_information.getBankCode().getText().isEmpty())
			association_donator_account.setBankCodeNumber(donator_information.getBankCode().getText());
		if(!donator_information.getBankname().getText().isEmpty())
			association_donator_account.setBankName(donator_information.getBankname().getText());
		association_donator.setAccount(association_donator_account);
	}	
	
	/**
	 * methode creates new member.
	 */
	public void createDonator(){
		// set person
	
		if (donator_id != 0)
			association_donator.setNumber(donator_id);
		if(donator_information .getDonatorTitle().getText() != null)
			association_donator.setTitle(donator_information.getDonatorTitle().getText());
		if(donator_information .getGender().getText() != null)
			association_donator.setGender(donator_information.getGender().getText());
		if(donator_information .getFirstName().getText() != null)
			association_donator.setFirstName(donator_information.getFirstName().getText());
		if(donator_information.getLastName().getText() != null)
			association_donator.setLastName(donator_information.getLastName().getText());
		
		// set adress
		if(association_donator_adress == null)
			association_donator_adress = new Adress();
		
		if(!donator_information.getExtraAdress().getText().isEmpty())
			association_donator_adress.setAdressAdditional(donator_information.getExtraAdress().getText()); 
		
		if(!donator_information.getStreet().getText().isEmpty())
			association_donator_adress.setStreet(donator_information.getStreet().getText()); 
		if(!donator_information.getHouseNumber().getText().isEmpty())
			association_donator_adress.setHouseNumber(donator_information.getHouseNumber().getText());
		if(!donator_information.getCity().getText().isEmpty())
			association_donator_adress.setCity(donator_information.getCity().getText());
		if(!donator_information.getZipCode().getText().isEmpty())
			association_donator_adress.setZipCode(donator_information.getZipCode().getText());
		if(!donator_information.getCountry().getText().isEmpty())
			association_donator_adress.setCountry(donator_information.getCountry().getText());
		if(!donator_information.getEmail().getText().isEmpty())
			association_donator_adress.setEMail(donator_information.getEmail().getText());
//		
		association_donator.setAdress(association_donator_adress);
		
		// set phone
		if(association_donator_phone == null)
			association_donator_phone = new Telephone();
		
		if(!donator_information.getPhone().getText().isEmpty())
			association_donator_phone.setPrivatePhone(donator_information.getPhone().getText());
		association_donator.setPhone(association_donator_phone);
	}
}
