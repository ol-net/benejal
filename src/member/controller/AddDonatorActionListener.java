
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
import member.view.AddDonator;
import member.view.AddDonatorBudget;
import member.view.AddDonatorData;
import member.view.ManageMembers;
import member.view.MemberAndDonatorFrame;
import moneybook.model.KassenBuch;

import association.model.Adress;
import association.model.AssociationDataTransfer;
import association.model.Telephone;
import association.view.InfoDialog;

/**
 * class represents ActionListener
 * 
 * @author Leonid Oldenburger
 */
public class AddDonatorActionListener implements ActionListener{

	private MemberAndDonatorFrame mainframe;
	private AddDonatorData adddatadonator;
	private AddDonator adonator;
	private ManageMembers managemember;
	private AddDonatorBudget donatorbudget;
	private DonatorWithAdress association_donator;
	private Adress association_member_adress;
	private Telephone association_member_phone;
	private AssociationDataTransfer association_data_transfer;
	private KassenBuch money_book;
	
	public AddDonatorActionListener(AddDonatorData adataDonator, DonatorWithAdress adonator, KassenBuch moneyBook, AssociationDataTransfer adatatrans){
		this.association_donator = adonator;
		this.mainframe = adataDonator.getJFrame();
		this.adddatadonator = adataDonator;
		this.adonator = adataDonator.getAddDonator();
		this.managemember = adataDonator.getManageMembers();
		this.association_data_transfer = adatatrans;
		this.money_book = moneyBook;
	}
	
	/**
	 * methode handles events.
	 */
	public void actionPerformed(ActionEvent event) {
		
		createDonator();
		this.donatorbudget = new AddDonatorBudget(mainframe, adonator, managemember, association_donator, money_book, association_data_transfer);
		// next
		if (adddatadonator.getNextButton() == event.getSource()) {
			try {
				
				if (adddatadonator.getLastName().getText().length() >= 3){
						
						adonator.removePanel();
						adonator.setPanel(donatorbudget);
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
	public void createDonator(){
		// set person
		
		if(association_donator == null){
			association_donator = new DonatorWithAdress();
		}
		
		if(!adddatadonator.getTitle().getText().isEmpty())
			association_donator.setTitle(adddatadonator.getTitle().getText());
		if(!adddatadonator.getGender().getText().isEmpty())
			association_donator.setGender(adddatadonator.getGender().getText());
		if(!adddatadonator.getFirstName().getText().isEmpty())
			association_donator.setFirstName(adddatadonator.getFirstName().getText());
		if(!adddatadonator.getLastName().getText().isEmpty())
			association_donator.setLastName(adddatadonator.getLastName().getText());
		
		// set adress
		if(association_member_adress == null)
			association_member_adress = new Adress();
		
		if(!adddatadonator.getExtraAdress().getText().isEmpty())
			association_member_adress.setAdressAdditional(adddatadonator.getExtraAdress().getText()); 
		
		if(!adddatadonator.getStreet().getText().isEmpty())
			association_member_adress.setStreet(adddatadonator.getStreet().getText()); 
		if(!adddatadonator.getHouseNumber().getText().isEmpty())
			association_member_adress.setHouseNumber(adddatadonator.getHouseNumber().getText());
		if(!adddatadonator.getCity().getText().isEmpty())
			association_member_adress.setCity(adddatadonator.getCity().getText());
		if(!adddatadonator.getZipCode().getText().isEmpty())
			association_member_adress.setZipCode(adddatadonator.getZipCode().getText());
		if(!adddatadonator.getCountry().getText().isEmpty())
			association_member_adress.setCountry(adddatadonator.getCountry().getText());
		if(!adddatadonator.getEmail().getText().isEmpty())
			association_member_adress.setEMail(adddatadonator.getEmail().getText());
		
		if(association_donator == null)
			association_donator = new DonatorWithAdress();
		
		if(association_member_adress != null)
			association_donator.setAdress(association_member_adress);
		
		if(!adddatadonator.getLastName().getText().isEmpty() &&
			!adddatadonator.getStreet().getText().isEmpty() &&
			!adddatadonator.getHouseNumber().getText().isEmpty() &&
			!adddatadonator.getCity().getText().isEmpty() &&
			!adddatadonator.getZipCode().getText().isEmpty() ){
			// known
			association_donator.setType(1);
		}else{
			// unknown
			association_donator.setType(0);
		}
		
		// set phone
		if(association_member_phone == null)
			association_member_phone = new Telephone();
		
		if(!adddatadonator.getPhone().getText().isEmpty())
			association_member_phone.setPrivatePhone(adddatadonator.getPhone().getText());
		
		if(association_member_phone != null)
			association_donator.setPhone(association_member_phone);
	}
}
