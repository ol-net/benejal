
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

import register.view.RegisterAssociationConfig;
import register.view.RegisterAssociationDatabase;
import register.view.RegisterAssociationGroup;
import register.view.RegisterStartWindow;

import association.model.Association;
import association.model.AssociationDataTransfer;

import association.view.ConfigFrame;
import association.view.InfoDialog;
import association.view.UpdateAssociationConfig;

/**
 * class represents ActionListener for association configuration
 * 
 * @author Leonid Oldenburger
 */
public class AssociationConfigActionListener implements ActionListener{
	
	private Association association_object;
	private RegisterAssociationConfig associationconfig;
	private RegisterStartWindow mainframe;
	private Font font_style;
	private ConfigFrame menuFrame;
	private UpdateAssociationConfig associationConfig;
	private AssociationDataTransfer association_data_transfer;
	private KassenBuch money_book;
	
	/**
	 * 
	 * @param aConfig
	 * @param hMenue
	 * @param aObject
	 * @param moneyBook
	 * @param adatatrans
	 */
	public AssociationConfigActionListener(UpdateAssociationConfig aConfig, ConfigFrame hMenue, Association aObject, KassenBuch moneyBook, AssociationDataTransfer adatatrans){
		this.menuFrame = hMenue;
		this.association_object = aObject;
		this.associationConfig = aConfig;
		this.associationconfig = aConfig;
		this.association_data_transfer = adatatrans;
		this.money_book = moneyBook;
	}
	
	/**
	 * 
	 * @param aconfig
	 * @param frame
	 * @param associationObject
	 * @param moneyBook
	 * @param adatatrans
	 */
	public AssociationConfigActionListener(RegisterAssociationConfig aconfig, RegisterStartWindow frame, Association associationObject, KassenBuch moneyBook, AssociationDataTransfer adatatrans){
		
		this.association_object = associationObject;
		this.associationconfig = aconfig;
		this.mainframe = frame;
		this.font_style = associationconfig.getFontStyle();
		this.association_data_transfer = adatatrans;
		this.money_book = moneyBook;
	}
	
	/**
	 * methode handles events
	 */
	public void actionPerformed(ActionEvent event) {
		
		// next
		if (associationConfig == null){
			if (associationconfig.getNextButton() == event.getSource()) {
				try {
					createAssociation();
					mainframe.removePanel();
					mainframe.addPanel(new RegisterAssociationDatabase(font_style, mainframe, association_object, money_book, association_data_transfer));
					
				} catch (Exception e) {
				}
			}
		}
		
		// back
		if (associationConfig == null){
			if (associationconfig.getBackButton() == event.getSource()) {
				try {
					mainframe.removePanel();
					mainframe.addPanel(new RegisterAssociationGroup(font_style, mainframe, association_object, money_book, association_data_transfer));
				} catch (Exception e) {
				}
			}
		}
		
		if (associationConfig != null){
			if (this.associationConfig.getBackButton() == event.getSource()) {
				try {
					menuFrame.removePane();
					//menuFrame.addPanel(new MainMenuPanel(menuFrame, money_book, association_data_transfer));
				} catch (Exception e) {
				}
			}
		}
		
		if (associationConfig != null){
			if (this.associationConfig.getSaveButton() == event.getSource()) {
				try {
	
					createAssociation();

					association_data_transfer.updateAssociationData(association_object);
					new InfoDialog("Vereinsdaten gespeichert");
					//String code = security.getCode();
				} catch (Exception e) {
				}
			}
		}
	}
	
	/**
	 * methode creates a new association object 
	 */
	public void createAssociation(){

			// set paymentrhythm
			association_object.setPaymentRhythm(associationconfig.getStatus());
			association_object.setPaymentTime(associationconfig.getBookingDate());
			if(associationconfig.getLogo() == 1 && associationconfig.getSignature() == 1){
				association_object.setLogo(3);
			}else if(associationconfig.getLogo() == 1 && associationconfig.getSignature() == 0){
				association_object.setLogo(1);
			}else if(associationconfig.getLogo() == 0 && associationconfig.getSignature() == 1){
				association_object.setLogo(2);
			}else{
				association_object.setLogo(0);
			}

			if (!associationconfig.getDirText().getText().isEmpty()){
				association_object.setDatadir(associationconfig.getDirText().getText());
			}else{
				association_object.setDatadir("");
			}
			

	}
}
