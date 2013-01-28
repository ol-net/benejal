
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
import java.text.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

import mail.model.Receipt;
import moneybook.model.KassenBuch;

import register.model.Purpose;
import register.view.RegisterAssociationConfig;
import register.view.RegisterAssociationDatabase;
import register.view.RegisterStartWindow;

import association.model.Association;
import association.model.AssociationDataTransfer;
import association.model.Security;

import association.view.ConfigFrame;
import association.view.InfoDialog;
import association.view.MainMenu;

import association.view.UpdateAssociationDatabase;

/**
 * class represents ActionListener for association security
 * 
 * @author Leonid Oldenburger
 */
public class AssociationDatabaseActionListener implements ActionListener{
	
	private Association association_object;
	private RegisterAssociationDatabase associationconfig;
	private RegisterStartWindow mainframe;
	private Font font_style;
	private ConfigFrame menuFrame;
	private UpdateAssociationDatabase associationConfig;
	private Security security;
	private AssociationDataTransfer association_data_transfer;
	private KassenBuch money_book;
	private DateFormat df;
	private Purpose text_purpose;
	private Receipt new_receipt;
	
	/**
	 * 
	 * @param aConfig
	 * @param hMenue
	 * @param aObject
	 * @param moneyBook
	 * @param adatatrans
	 */
	public AssociationDatabaseActionListener(UpdateAssociationDatabase aConfig, ConfigFrame hMenue, Association aObject, KassenBuch moneyBook, AssociationDataTransfer adatatrans){
		this.menuFrame = hMenue;
		this.association_object = aObject;
		this.associationConfig = aConfig;
		this.associationconfig = aConfig;
		this.association_data_transfer = adatatrans;
		this.money_book = moneyBook;
		this.text_purpose = Purpose.getInstance();
		this.new_receipt = new Receipt();
	}
	
	/**
	 * 
	 * @param aconfig
	 * @param frame
	 * @param associationObject
	 * @param moneyBook
	 * @param adatatrans
	 */
	public AssociationDatabaseActionListener(RegisterAssociationDatabase aconfig, RegisterStartWindow frame, Association associationObject, KassenBuch moneyBook, AssociationDataTransfer adatatrans){
		
		this.association_object = associationObject;
		this.associationconfig = aconfig;
		this.mainframe = frame;
		this.font_style = associationconfig.getFontStyle();
		this.association_data_transfer = adatatrans;
		this.money_book = moneyBook;
		this.text_purpose = Purpose.getInstance();
		this.new_receipt = new Receipt();
	}
	
	/***
	 * methode to handle events
	 */
	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent event) {
		
		// next
		if (associationConfig == null){
			if (associationconfig.getNextButton() == event.getSource()) {
				try {
					createAssociation();
					money_book.setStartBalance(association_object.getAccountBalance());
					association_data_transfer.insertAssociation(association_object);
					
					//init purpose text 
					new_receipt.setObject1(text_purpose.getText());
			        association_data_transfer.setReceipt(new_receipt);
					
					df = new SimpleDateFormat("dd.MM.yyyy"); 
					association_data_transfer.initStatusDate(df.format(new Date()));

					new MainMenu(800, 800, font_style, money_book, association_data_transfer);
					mainframe.schliessen();
					
				} catch (Exception e) {
				}
			}
		}
		
		// back
		if (associationConfig == null){
			if (associationconfig.getBackButton() == event.getSource()) {
				try {
					mainframe.removePanel();
					mainframe.addPanel(new RegisterAssociationConfig(font_style, mainframe, association_object, money_book, association_data_transfer));
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

					security = new Security(associationConfig);
					
					if (associationConfig.getCheckBoxValue() == 1 && !associationConfig.getPassword().getText().isEmpty()){
						
						if(!security.comparePassword()){
							new InfoDialog("Passwort stimmt nicht überein!");
						}
						else if(new String (associationConfig.getPassword().getPassword()).length() < 5){
							new InfoDialog("Bitte mindestens 5 Zeichen für ein Passwort eingeben!");
						}
						else{
							security.setCode();
							association_data_transfer.updateAssociationData(association_object);
							new InfoDialog("Vereinsdaten gespeichert");
						}
					} 
					else if(associationConfig.getCheckBoxValue() == 1 && associationConfig.getPassword().getText().isEmpty()){
						new InfoDialog("Passworteingabe überprüfen!");
					}else{
						association_data_transfer.updateAssociationData(association_object);
						new InfoDialog("Vereinsdaten gespeichert!");
					}
					

					
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
//			association_object.setPaymentRhythm(associationconfig.getStatus());
			association_object.setPassword(associationconfig.getCheckBoxValue());
		
//			if (!associationconfig.getDirText().getText().isEmpty()){
//				association_object.setDatadir(associationconfig.getDirText().getText());
//			}
//		
//			if (!associationconfig.getBookingDate().getText().isEmpty()){
//				association_object.setPaymentTime(associationconfig.getBookingDate().getText());
//			}
	}
}
