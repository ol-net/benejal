
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
import java.sql.Date;
import java.text.DecimalFormat;

import mail.model.Receipt;
import moneybook.model.KassenBuch;

import association.model.Account;
import association.model.Adress;
import association.model.Association;
import association.model.AssociationDataTransfer;
import association.model.TaxOffice;

import association.view.ConfigFrame;
import association.view.InfoDialog;
import association.view.UpdateAssociationBudget;

import register.model.Purpose;
import register.view.RegisterAssociationAdmin;
import register.view.RegisterAssociationBudget;
import register.view.RegisterAssociationGroup;
import register.view.RegisterStartWindow;

/**
 * class represents ActionListener for association budget
 * 
 * @author Leonid Oldenburger
 */
public class AssociationBudgetActionListener implements ActionListener{
	
	private Association association_object;
	private RegisterAssociationBudget associationbudget;
	private RegisterStartWindow mainframe;
	private Font font_style;
	private Account association_account;
	private TaxOffice association_finance_office;
	private Adress association_finance_office_adress;
	private UpdateAssociationBudget associationBudget;
	private ConfigFrame menuFrame;
	private AssociationDataTransfer association_data_transfer;
	private KassenBuch money_book;
	private DecimalFormat decimalformat;
	private Purpose text_purpose;
	private Receipt new_receipt;
	
	/**
	 * 
	 * 
	 * @param aBudget
	 * @param hMenue
	 * @param aObject
	 * @param moneyBook
	 * @param adatatrans
	 */
	public AssociationBudgetActionListener(UpdateAssociationBudget aBudget, ConfigFrame hMenue, Association aObject, KassenBuch moneyBook, AssociationDataTransfer adatatrans){
		this.menuFrame = hMenue;
		this.association_object = aObject;
		this.associationBudget = aBudget;
		this.associationbudget = aBudget;
		this.association_data_transfer = adatatrans;
		this.money_book = moneyBook;
		this.decimalformat = new DecimalFormat("#0.00");
		//this.text_purpose = Purpose.getInstance();
		this.new_receipt = new Receipt();
	}
	
	/**
	 * 
	 * @param abudget
	 * @param frame
	 * @param associationObject
	 * @param moneyBook
	 * @param adatatrans
	 */
	public AssociationBudgetActionListener(RegisterAssociationBudget abudget, RegisterStartWindow frame, Association associationObject, KassenBuch moneyBook, AssociationDataTransfer adatatrans){
		
		this.association_object = associationObject;
		this.associationbudget = abudget;
		this.mainframe = frame;
		this.font_style = associationbudget.getFontStyle();
		this.association_data_transfer = adatatrans;
		this.money_book = moneyBook;
		this.decimalformat = new DecimalFormat("#0.00");
		this.text_purpose = Purpose.getInstance();
	}
	
	/**
	 * methode handles events
	 */
	public void actionPerformed(ActionEvent event) {
		
		createAssociation();
		
		// next
		if (associationBudget == null){
			if (associationbudget.getNextButton() == event.getSource()) {
				try {
					text_purpose.setText(associationbudget.getPurposeText());
					if(associationbudget.getAccountNumber().getText().length() >= 3 && associationbudget.getBankCode().getText().length() >= 3){
						mainframe.removePanel();
						mainframe.addPanel(new RegisterAssociationGroup(font_style, mainframe, association_object, money_book, association_data_transfer));
					}else{
						new InfoDialog("Bitte füllen Sie die Pflichtfelder aus (min. 3 Zeichen)!");
					}
				} catch (Exception e) {
				}
			}
		}
		// back
		if (associationBudget == null){
			if (associationbudget.getBackButton() == event.getSource()) {
				try {
					mainframe.removePanel();
					mainframe.addPanel(new RegisterAssociationAdmin(font_style, mainframe, association_object, money_book, association_data_transfer));
				} catch (Exception e) {
				}
			}
		}
		
		if (associationBudget != null){
			if (associationBudget.getBackButton() == event.getSource()) {
				try {
					menuFrame.removePane();
					//menuFrame.addPanel(new MainMenuPanel(menuFrame, money_book, association_data_transfer));
				} catch (Exception e) {
				}
			}
		}
		
		// update
		if (associationBudget != null){
			if (associationBudget.getSaveButton() == event.getSource()) {
				try {
					if(associationbudget.getAccountNumber().getText().length() >= 3 && associationbudget.getBankCode().getText().length() >= 3){

						association_data_transfer.updateAssociationBudget(association_object);
						
						//update purpose db
						new_receipt.setObject1(associationBudget.getPurposeText());
				        association_data_transfer.updateReceipt(new_receipt);
				        
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
		
		association_account = new Account();
		association_finance_office = TaxOffice.getInstance();
		association_finance_office_adress = new Adress();
		
		if (!associationbudget.getAccountNumber().getText().isEmpty()){
			association_account.setAccountNumber(associationbudget.getAccountNumber().getText());
		}
		
		if (!associationbudget.getBankCode().getText().isEmpty()){
			association_account.setBankCodeNumber(associationbudget.getBankCode().getText());
		}
		
		if (!associationbudget.getBankName().getText().isEmpty()){
			association_account.setBankName(associationbudget.getBankName().getText());
		}
		
		if (!associationbudget.getSwift().getText().isEmpty()){
			association_account.setBicSwift(associationbudget.getSwift().getText());
		}
		
		if (!associationbudget.getIban().getText().isEmpty()){
			association_account.setiBan(associationbudget.getIban().getText());
		}
		
		if (!associationbudget.getBankName().getText().isEmpty()){
			association_account.setBankName(associationbudget.getBankName().getText());
		}
		
		if (!associationbudget.getStreet().getText().isEmpty()){
			association_finance_office_adress.setStreet(associationbudget.getStreet().getText());
		}
		
		if (!associationbudget.getNumber().getText().isEmpty()){
			association_finance_office_adress.setHouseNumber(associationbudget.getNumber().getText());
		}
		
		if (!associationbudget.getOfficeCity().getText().isEmpty()){
			association_finance_office_adress.setCity(associationbudget.getOfficeCity().getText());
		}
		
		if (!associationbudget.getZipCode().getText().isEmpty()){
			association_finance_office_adress.setZipCode(associationbudget.getZipCode().getText());
		}
		
		association_finance_office.setAdress(association_finance_office_adress);
		
		if(!associationbudget.getTaxNumber().getText().isEmpty()){
			association_finance_office.setTaxNumber(associationbudget.getTaxNumber().getText());
		}
		
		if(!associationbudget.getCurrency().getText().isEmpty()){
			association_finance_office.setCurrency(associationbudget.getCurrency().getText());
		}
		
		if (associationbudget.getVDate() != null){
			association_finance_office.setV(new java.sql.Date(associationbudget.getVDate().getTime()));
		}else{
			association_finance_office.setV(new Date(0));
		}
	
		if (associationbudget.getADate() != null){
			association_finance_office.setA(new java.sql.Date(associationbudget.getADate().getTime()));
		}else{
			association_finance_office.setA(new Date(0));
		}
		if (!associationbudget.getAccountBalance().getText().isEmpty()){
			Double s = new Double(associationbudget.getAccountBalance().getText().replace(",", "."));
			association_object.setAccountBalance(new Double(decimalformat.format(s).replace(",", ".")));
		}
		
		association_object.setAccount(association_account);
		association_object.setFinanceOffice(association_finance_office);
	}	
}
