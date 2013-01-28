
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
package register.controller;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import member.model.Person;
import moneybook.model.KassenBuch;

import association.model.Association;
import association.model.AssociationDataTransfer;

import association.view.ConfigFrame;
import association.view.InfoDialog;
import association.view.UpdateAssociationAdmin;

import register.view.RegisterAssociationAdmin;
import register.view.RegisterAssociationBudget;
import register.view.RegisterAssociationData;
import register.view.RegisterStartWindow;

/**
 * class represents ActionListener for association administration
 * 
 * @author Leonid Oldenburger
 */
public class AssociationAdminActionListener implements ActionListener{

	private Association association_object;
	private RegisterAssociationAdmin associationadmin;
	private RegisterStartWindow mainframe;
	private Font font_style;
	private Person association_leader;
	private Person association_treasurer;
	private ConfigFrame menuFrame;
	private UpdateAssociationAdmin associationAdmin;
	private AssociationDataTransfer association_data_transfer;
	private KassenBuch money_book;
	
	/**
	 * 
	 * @param aAdmin
	 * @param hMenue
	 * @param aObject
	 * @param moneyBook
	 * @param adatatrans
	 */
	public AssociationAdminActionListener(UpdateAssociationAdmin aAdmin, ConfigFrame hMenue, Association aObject, KassenBuch moneyBook, AssociationDataTransfer adatatrans){
		this.menuFrame = hMenue;
		this.association_object = aObject;
		this.associationAdmin = aAdmin;
		this.associationadmin = aAdmin;
		this.association_data_transfer = adatatrans;
		this.money_book = moneyBook;
	}
	
	/**
	 * 
	 * @param association
	 * @param frame
	 * @param associationObject
	 * @param moneyBook
	 * @param adatatrans
	 */
	public AssociationAdminActionListener(RegisterAssociationAdmin association, RegisterStartWindow frame, Association associationObject, KassenBuch moneyBook, AssociationDataTransfer adatatrans){
		
		this.association_object = associationObject;
		this.associationadmin = association;
		this.mainframe = frame;
		this.font_style = associationadmin.getFontStyle();
		this.associationAdmin = null;
		this.association_data_transfer = adatatrans;
		this.money_book = moneyBook;
	}
	
	/**
	 * methode handles events
	 */
	public void actionPerformed(ActionEvent event) {
		
		createAssociation();
		
		// next
		if (associationAdmin == null){
			if (associationadmin.getNextButton() == event.getSource()) {
				try {
					mainframe.removePanel();
					mainframe.addPanel(new RegisterAssociationBudget(font_style, mainframe, association_object, money_book, association_data_transfer));
				} catch (Exception e) {
				}
			}
		}
		// back
		if (associationAdmin == null){
			if (associationadmin.getBackButton() == event.getSource()) {
				try {
					mainframe.removePanel();
					mainframe.addPanel(new RegisterAssociationData(font_style, mainframe, association_object, money_book, association_data_transfer));
				} catch (Exception e) {
				}
			}
		}
		
		if (associationAdmin != null){
			if (associationAdmin.getBackButton() == event.getSource()) {
				try {
					menuFrame.removePane();
					//menuFrame.addPanel(new MainMenuPanel(menuFrame, money_book, association_data_transfer));
				} catch (Exception e) {
				}
			}
		}
		
		// update
		if (associationAdmin != null){
			if (associationAdmin.getSaveButton() == event.getSource()) {
				try {
					association_data_transfer.updateAssociationAdmin(association_object);
						
					new InfoDialog("Vereinsdaten gespeichert");
				} catch (Exception e) {
				}
			}
		}
	}
	
	/**
	 * methode creates a new association object 
	 */
	public void createAssociation(){
		
		association_leader = new Person();
		association_treasurer = new Person();
		
		if (!associationadmin.getTitleLeader().getText().isEmpty()){
			association_leader.setTitle(associationadmin.getTitleLeader().getText());
		}
		
		if (!associationadmin.getGenderLeader().getText().isEmpty()){
			association_leader.setGender(associationadmin.getGenderLeader().getText());
		}
		
		if (!associationadmin.getFirstNameLeader().getText().isEmpty()){
			association_leader.setFirstName(associationadmin.getFirstNameLeader().getText());
		}
		
		if (!associationadmin.getLastNameLeader().getText().isEmpty()){
			association_leader.setLastName(associationadmin.getLastNameLeader().getText());
		}
		
		if (!associationadmin.getTitleTreasurer().getText().isEmpty()){
			association_treasurer.setTitle(associationadmin.getTitleTreasurer().getText());
		}
		
		if (!associationadmin.getGenderTreasurer().getText().isEmpty()){
			association_treasurer.setGender(associationadmin.getGenderTreasurer().getText());
		}
		
		if (!associationadmin.getFirstNameTreasurer().getText().isEmpty()){
			association_treasurer.setFirstName(associationadmin.getFirstNameTreasurer().getText());
		}
		
		if (!associationadmin.getLastNameTreasurer().getText().isEmpty()){
			association_treasurer.setLastName(associationadmin.getLastNameTreasurer().getText());
		}
		association_object.setLeader(association_leader);
		association_object.setTreasurer(association_treasurer);
	}
}
