
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

import moneybook.model.KassenBuch;

import association.model.Adress;
import association.model.Association;
import association.model.AssociationDataTransfer;
import association.model.Telephone;

import association.view.ConfigFrame;
import association.view.InfoDialog;
import association.view.UpdateAssociationData;

import register.view.RegisterAssociationAdmin;
import register.view.RegisterAssociationData;
import register.view.RegisterStartWindow;

/**
 * class represents ActionListener for association data
 * 
 * @author Leonid Oldenburger
 */
public class AssociationDataActionListener implements ActionListener{

	private RegisterAssociationData association;
	private RegisterStartWindow mainframe;
	private Font font_style;
	private Association association_object;
	private Adress adress_object;
	private Telephone telephone_object;
	private ConfigFrame menuFrame;
	private UpdateAssociationData associationData;
	private AssociationDataTransfer association_data_transfer;
	private KassenBuch money_book;
	
	/**
	 * 
	 * @param aData
	 * @param hMenue
	 * @param aObject
	 * @param moneyBook
	 * @param adatatrans
	 */
	public AssociationDataActionListener(UpdateAssociationData aData, ConfigFrame hMenue, Association aObject, KassenBuch moneyBook, AssociationDataTransfer adatatrans){
		this.menuFrame = hMenue;
		this.association_object = aObject;
		this.associationData = aData;
		this.association = aData;
		this.association_data_transfer = adatatrans;
		this.money_book = moneyBook;
	}
	
	/**
	 * 
	 * @param asso
	 * @param frame
	 * @param associationObject
	 * @param moneyBook
	 * @param adatatrans
	 */
	public AssociationDataActionListener(RegisterAssociationData asso, RegisterStartWindow frame, Association associationObject, KassenBuch moneyBook, AssociationDataTransfer adatatrans){
		this.mainframe = frame;
		this.association = asso;
		this.font_style = association.getFontStyle();
		this.association_object = associationObject;
		this.associationData = null;
		this.association_data_transfer = adatatrans;
		this.money_book = moneyBook; 
	}

	/**
	 * methode to handle events
	 * 
	 * @param event
	 */
	public void actionPerformed(ActionEvent event) {
		
		// next
		if (associationData == null){
			if (this.association.getButton() == event.getSource()) {
				try {
					if (association_object == null){
						this.association_object = Association.getInstance();
					}
					if (association.getTextName().getText().length() >= 3 && 
						association.getTextStreet().getText().length() >= 3 && 
						association.getTextNumber().getText().length() >= 1 && 
						association.getTextCity().getText().length() >= 3 &&
						association.getTextPostcode().getText().length() >= 3){
						
						createAssociation();
				
						mainframe.removePanel();
						mainframe.addPanel(new RegisterAssociationAdmin(font_style, mainframe, association_object, money_book, association_data_transfer));
					}else{
						new InfoDialog("Bitte füllen Sie die Pflichtfelder aus (min. 3 Zeichen)!");
					}
					
				} catch (Exception e) {
				}
			}
		}
		// back
		if (associationData != null){
			if (this.associationData.getBackButton() == event.getSource()) {
				try {
					menuFrame.removePane();
					//menuFrame.addPanel(new MainMenuPanel(menuFrame, money_book, association_data_transfer));
				} catch (Exception e) {
				}
			}
		}
		
		// update
		if (associationData != null){
			if (this.associationData.getSaveButton() == event.getSource()) {
				try {
					
					if (association_object == null){
						this.association_object = Association.getInstance();
					}
					
					if (association.getTextName().getText().length() >= 3 && 
							association.getTextStreet().getText().length() >= 3 && 
							association.getTextNumber().getText().length() >= 1 && 
							association.getTextCity().getText().length() >= 3 &&
							association.getTextPostcode().getText().length() >= 3){
							
							createAssociation();
						
							association_data_transfer.updateAssociationData(association_object);
							
							new InfoDialog("Vereinsdaten gespeichert");
					}else{
							new InfoDialog("Bitte füllen Sie die Pflichtfelder aus (min. 3 Zeichen)!");
					}
	
				} catch (Exception e) {
				}
			}
		}
	}
	
	/**
	 * methode creates a new association object 
	 */
	public void createAssociation(){
		
		// set name 
		if (!association.getTextName().getText().isEmpty()){
			association_object.setName(association.getTextName().getText());
		}
		// set adress
		adress_object = new Adress();
		if (!association.getTextStreet().getText().isEmpty()){
			adress_object.setStreet(association.getTextStreet().getText());
		}
		if(!association.getTextNumber().getText().isEmpty()){
			adress_object.setHouseNumber(association.getTextNumber().getText());	
		}
		if (!association.getTextPostcode().getText().isEmpty()){
			adress_object.setZipCode(association.getTextPostcode().getText());
		}
		if(!association.getTextCity().getText().isEmpty()){
			adress_object.setCity(association.getTextCity().getText());
		}
		if(!association.getTextCountry().getText().isEmpty()){
			adress_object.setCountry(association.getTextCountry().getText());
		}
		if(!association.getTextEmail().getText().isEmpty()){
			adress_object.setEMail(association.getTextEmail().getText());
		}
		
		// add new adress to association
		association_object.setAdress(adress_object);
		
		// set telephone
		telephone_object = new Telephone();
		if(!association.getTextTel().getText().isEmpty()){
			telephone_object.setPrivatePhone(association.getTextTel().getText());
		}
		if(!association.getTextFax().getText().isEmpty()){
			telephone_object.setFaxNumber(association.getTextFax().getText());
		}
		
		// add new telephone to association
		association_object.setTelephone(telephone_object);
	}
}
