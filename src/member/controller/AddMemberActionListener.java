
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

import member.model.Member;

import member.view.AddMember;
import member.view.AddMemberBudget;
import member.view.AddMemberData;
import member.view.ManageMembers;
import member.view.MemberAndDonatorFrame;

import moneybook.model.KassenBuch;

import association.model.Adress;
import association.model.AssociationDataTransfer;
import association.model.Telephone;

import association.view.InfoDialog;

/**
 * class represents ActionListener for adding member
 * 
 * @author Leonid Oldenburger
 */
public class AddMemberActionListener implements ActionListener{

	private MemberAndDonatorFrame mainframe;
	private AddMemberData adddatamember;
	private AddMember amember;
	private ManageMembers managemember;
	private AddMemberBudget memberbudget;
	private Member association_member;
	private Adress association_member_adress;
	private Telephone association_member_phone;
	
	private KassenBuch money_book;
	private AssociationDataTransfer association_data_transfer;
	
	/**
	 * constructor creates new action listener
	 * 
	 * @param adataMember
	 * @param amember
	 * @param moneyBook
	 * @param adatatrans
	 */
	public AddMemberActionListener(AddMemberData adataMember, Member amember, KassenBuch moneyBook, AssociationDataTransfer adatatrans){
		this.association_member = amember;
		this.mainframe = adataMember.getJFrame();
		this.adddatamember = adataMember;
		this.amember = adataMember.getAddMember();
		this.managemember = adataMember.getManageMembers();
		this.association_data_transfer = adatatrans;
		this.money_book = moneyBook;
	}
	
	/**
	 * methode handles events.
	 */
	public void actionPerformed(ActionEvent event) {
		
		createMember();
		this.memberbudget = new AddMemberBudget(mainframe, amember, managemember, association_member, money_book, association_data_transfer);
		// next
		if (adddatamember.getNextButton() == event.getSource()) {
			try {
				
				if (adddatamember.getFirstName().getText().length() >= 3 && 
						adddatamember.getLastName().getText().length() >= 3 && 
						adddatamember.getStreet().getText().length() >= 3 && 
						adddatamember.getHouseNumber().getText().length() >= 1 && 
						adddatamember.getCity().getText().length() >= 3 &&
						adddatamember.getZipCode().getText().length() >= 3){
					
						amember.removePanel();
						amember.setPanel(memberbudget);
						mainframe.removePane();
						mainframe.addTabbedPanel(managemember);
					}else{
						new InfoDialog("Bitte füllen Sie die Pflichtfelder aus (min. 3 Zeichen)!");
					}
			} catch (Exception e) {
			}
		}
	}
	
	/**
	 * methode creates new member.
	 */
	public void createMember(){
		// set person
		if(adddatamember.getTitle().getText() != null)
			association_member.setTitle(adddatamember.getTitle().getText());
		if(adddatamember.getGender().getText() != null)
			association_member.setGender(adddatamember.getGender().getText());
		if(adddatamember.getFirstName().getText() != null)
			association_member.setFirstName(adddatamember.getFirstName().getText());
		if(adddatamember.getLastName().getText() != null)
			association_member.setLastName(adddatamember.getLastName().getText());
		
		// set adress
		if(association_member_adress == null)
			association_member_adress = new Adress();
		
		if(!adddatamember.getExtraAdress().getText().isEmpty())
			association_member_adress.setAdressAdditional(adddatamember.getExtraAdress().getText()); 
		
		if(!adddatamember.getStreet().getText().isEmpty())
			association_member_adress.setStreet(adddatamember.getStreet().getText()); 
		if(!adddatamember.getHouseNumber().getText().isEmpty())
			association_member_adress.setHouseNumber(adddatamember.getHouseNumber().getText());
		if(!adddatamember.getCity().getText().isEmpty())
			association_member_adress.setCity(adddatamember.getCity().getText());
		if(!adddatamember.getZipCode().getText().isEmpty())
			association_member_adress.setZipCode(adddatamember.getZipCode().getText());
		if(!adddatamember.getCountry().getText().isEmpty())
			association_member_adress.setCountry(adddatamember.getCountry().getText());
		if(!adddatamember.getEmail().getText().isEmpty())
			association_member_adress.setEMail(adddatamember.getEmail().getText());
		
		association_member.setAdress(association_member_adress);
		
		// set phone
		if(association_member_phone == null)
			association_member_phone = new Telephone();
		
		if(!adddatamember.getPhone().getText().isEmpty())
			association_member_phone.setPrivatePhone(adddatamember.getPhone().getText());
		association_member.setPhone(association_member_phone);
	}
}
