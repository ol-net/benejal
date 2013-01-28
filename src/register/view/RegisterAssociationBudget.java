
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
package register.view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.DecimalFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.text.AbstractDocument;

import com.toedter.calendar.JDateChooser;

import association.model.Account;
import association.model.Adress;
import association.model.Association;
import association.model.AssociationDataTransfer;
import association.model.DocumentSizeFilter;
import association.model.DoubleDocument;
import association.model.TaxOffice;
import association.view.BackgroundPanel;
import association.view.ButtonDesign;

import register.controller.AssociationBudgetActionListener;
import register.model.Purpose;

import moneybook.model.KassenBuch;
import net.java.dev.designgridlayout.DesignGridLayout;

/**
 * gui class for the association finance
 * 
 * @author Leonid Oldenburger
 */
public class RegisterAssociationBudget extends BackgroundPanel{

	private static final long serialVersionUID = 1L;
	
	// language
	protected String title = "Registrierung - Vereinsfinanzen - Schritt 3 von 6";
	protected String account = "Kontodaten";
	protected String collection_office = "Finanzamt";
	protected String account_number = "  Kontonummer:*";
	protected String bank_code = "Bankleitzahl:*";
	protected String bank_name = "Kreditinstitut:";
	protected String collection_office_city = "Ort:";
	protected String tax_number = "Steuernummer:";
	protected String currency = "Währung:";
	protected String account_balance = "Saldo:";
	protected String swift = "bicSWIFT:";
	protected String iban = "iBAN:";
	
	// TextFields bank
	protected JTextField text_account_balance;
	protected JTextField text_bank_name;
	protected JTextField text_bank_code;
	protected JTextField text_account_number;
	protected JTextField text_swift;
	protected JTextField text_iban;
	
	// TextFields collection office
	protected JTextField text_collection_office_city;
	protected JTextField text_collection_office_zipcode;
	protected JTextField text_collection_office_street;
	protected JTextField text_collection_office_number;
	protected JTextField text_tax_number;
	protected JTextField text_currency;
	protected JTextField text_purpose;
	
	// Button
	protected JButton next_button;
	protected JButton back_button;
	
	// window option
	protected Font style_font;
	protected DesignGridLayout bank_layout;
	protected DesignGridLayout office_layout;
	protected RegisterStartWindow mainframe;
	
	// Panel
	protected JPanel buttonpanel;
	protected JPanel bankpanel;
	protected JPanel officepanel;	
	protected JPanel butpanel;

	protected Association association_object;
	protected Adress association_adress;
	protected Account association_account;
	protected TaxOffice association_office;
	
	private AssociationDataTransfer association_data_transfer;
	private KassenBuch money_book;
	
	private DecimalFormat df;
	
	protected JDateChooser v;
	protected JDateChooser a;
	
	protected Purpose string_text_purpose;
	
	/**
	 * constructor		
	 */
	public RegisterAssociationBudget(){
		this.association_object = null;
		this.style_font = null;
		this.mainframe = null;
	}
	
	/**
	 * constructor with some parameters
	 * 
	 * @param fontStyle
	 * @param frame
	 * @param associationObject
	 * @param moneyBook
	 * @param adatatrans
	 */
	public RegisterAssociationBudget(Font fontStyle, RegisterStartWindow frame, Association associationObject, KassenBuch moneyBook, AssociationDataTransfer adatatrans){
		
		this.association_object = associationObject;
		this.style_font = fontStyle;
		this.mainframe = frame;
		this.mainframe.setTitle(title);
		this.association_data_transfer = adatatrans;
		this.money_book = moneyBook;
		this.string_text_purpose = Purpose.getInstance();
		
		buildPanel();
		
		// Next-Button
		next_button = new ButtonDesign("images/next_icon.png");
		back_button = new ButtonDesign("images/back_icon.png");
		back_button.addActionListener(new AssociationBudgetActionListener(this, mainframe, association_object, money_book, association_data_transfer));
		next_button.addActionListener(new AssociationBudgetActionListener(this, mainframe, association_object, money_book, association_data_transfer));
		
		butpanel = new JPanel();
		butpanel.setLayout(new FlowLayout());
		butpanel.setOpaque(false);
		butpanel.add(back_button);	
		butpanel.add(next_button);
		
		GridBagConstraints b = new GridBagConstraints();
		b.fill=GridBagConstraints.HORIZONTAL;
		b.insets =new Insets(18,2,2,2);
		b.gridx=0;
		b.gridy=0;
		buttonpanel.add(butpanel, b);
	}
	
	/**
	 * build panel
	 */
	public void buildPanel(){
		
		setLayout(new GridBagLayout());
		setOpaque(false);
		
		bankpanel = new JPanel();
		bankpanel.setBorder(new TitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY ), account));
		bankpanel.setOpaque(false);
		
		bank_layout = new DesignGridLayout(bankpanel);
		makeBankBlank(bank_layout);
		
		officepanel = new JPanel();
		officepanel.setBorder(new TitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY ), collection_office));
		officepanel.setOpaque(false);
		
		office_layout = new DesignGridLayout(officepanel);
		makeOfficeBlank(office_layout);
		
		buttonpanel = new JPanel();
		buttonpanel.setLayout(new GridBagLayout());
		buttonpanel.setOpaque(false);
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill=GridBagConstraints.HORIZONTAL;
		c.insets =new Insets(0,2,2,2);
		
		c.gridx=0;
		c.gridy=0;
		add(bankpanel, c);
		
		c.gridx=0;
		c.gridy=1;
		add(officepanel, c);
		
		c.gridx=0;
		c.gridy=2;
		add(buttonpanel, c);	
	}
	
	/**
	 * methode to create a blank for treasurer.
	 * 
	 * @param layout
	 */
	public void makeBankBlank(DesignGridLayout layout){
		
		text_bank_name = new JTextField(12);
		text_bank_code = new JTextField(12);
		text_account_number = new JTextField(12);
		text_swift = new JTextField(12);
		text_iban = new JTextField(12);
		text_account_balance = new JTextField(12);
		text_currency = new JTextField("€");
		JTextField dummy = new JTextField(12);
		dummy.setVisible(false);
		JTextField dummy1 = new JTextField(12);
		dummy1.setVisible(false);
		JTextField dummy2 = new JTextField(12);
		dummy2.setVisible(false);
		JTextField dummy3 = new JTextField(12);
		dummy3.setVisible(false);
		JTextField dummy4 = new JTextField(12);
		dummy4.setVisible(false);
		
		if (association_object != null)
			association_account = association_object.getAccount();
		
		text_account_balance.setDocument(new DoubleDocument(text_account_balance));
		
		if (association_account != null)
			initBankTextfield();
		
		((AbstractDocument) text_account_number.getDocument()).setDocumentFilter(new DocumentSizeFilter(10, "[0-9]"));
		((AbstractDocument) text_bank_code.getDocument()).setDocumentFilter(new DocumentSizeFilter(8, "[0-9]"));
		((AbstractDocument) text_bank_name.getDocument()).setDocumentFilter(new DocumentSizeFilter(30, "."));
		((AbstractDocument) text_swift.getDocument()).setDocumentFilter(new DocumentSizeFilter(30, "."));
		((AbstractDocument) text_iban.getDocument()).setDocumentFilter(new DocumentSizeFilter(30, "."));
		((AbstractDocument) text_currency.getDocument()).setDocumentFilter(new DocumentSizeFilter(30, "."));
		
		layout.row().grid(new JLabel(account_number), 1).add(text_account_number).grid(new JLabel("       ")).add(dummy);
		layout.row().grid(new JLabel(bank_code)).add(text_bank_code).grid(new JLabel("       ")).add(dummy1);
		layout.row().grid(new JLabel(bank_name)).add(text_bank_name).grid(new JLabel("       ")).add(dummy2);
		layout.row().grid(new JLabel(swift)).add(text_swift).grid(new JLabel("       ")).add(dummy3);
		layout.row().grid(new JLabel(iban)).add(text_iban).grid(new JLabel("       ")).add(dummy4);
		layout.row().grid(new JLabel(account_balance)).add(text_account_balance).grid(new JLabel(currency)).add(text_currency);
	}
	
	/**
	 * methode to create a blank for leader.
	 * 
	 * @param layout
	 */
	public void makeOfficeBlank(DesignGridLayout layout){

		v = new JDateChooser();
		a = new JDateChooser();
		
		text_collection_office_city = new JTextField(12);
		text_tax_number = new JTextField(12);
		text_collection_office_street = new JTextField(12);
		text_collection_office_number = new JTextField(12);
		text_collection_office_zipcode = new JTextField(12);
		text_purpose = new JTextField();
		
		if (association_object != null)
			association_office = association_object.getFinacneOffice();
		
		if (association_office != null)
			association_adress = association_office.getAdress();
		
		if (association_adress != null)
			initOfficeTextfield();
		
		if (v.getDate() == null)
			v.setDate(new Date());
		
		if (a.getDate() == null)
			a.setDate(new Date());
		
		((AbstractDocument) text_collection_office_street.getDocument()).setDocumentFilter(new DocumentSizeFilter(30, "."));
		((AbstractDocument) text_collection_office_city.getDocument()).setDocumentFilter(new DocumentSizeFilter(30, "."));
		((AbstractDocument) text_collection_office_number.getDocument()).setDocumentFilter(new DocumentSizeFilter(30, "."));
		((AbstractDocument) text_collection_office_zipcode.getDocument()).setDocumentFilter(new DocumentSizeFilter(30, "[0-9]"));
		((AbstractDocument) text_tax_number.getDocument()).setDocumentFilter(new DocumentSizeFilter(30, "."));
		
		layout.row().grid(new JLabel("Straße:")).add(text_collection_office_street).grid(new JLabel("            Nr.:")).add(text_collection_office_number);
		layout.row().grid(new JLabel(collection_office_city)).add(text_collection_office_city).grid(new JLabel("PLZ:")).add(text_collection_office_zipcode);
		layout.row().grid(new JLabel(tax_number), 1).add(text_tax_number);
		layout.row().grid(new JLabel("Förderungszweck:"), 2).add(text_purpose);
		layout.row().grid(new JLabel("Bescheid vom:"), 1).add(v).grid(new JLabel("vorläufig ab:")).add(a);
	}
	
    /**
     * methode set purpose text
     * 
     * @param text
     */
	public void setPurposeTex(String text){
		this.text_purpose.setText(text);
	}
	
    /**
     * methode returns purpose text
     * 
     * @return purpose text
     */
	public String getPurposeText(){
		return text_purpose.getText();
	}
	
    /**
     * methode set v date
     * 
     * @param date
     */
	public void setVDate(Date date){
		this.v.setDate(date);
	}
	
    /**
     * methode set v date
     * 
     * @param date
     */
	public void setADate(Date date){
		this.a.setDate(date);
	}
	
	/**
	 * get v date
	 * 
	 * @return date 
	 */
	public Date getVDate(){
		
		return v.getDate();
	}
	
	/**
	 * get a date
	 * 
	 * @return date 
	 */
	public Date getADate(){
		
		return a.getDate();
	}
	
	/**
	 * AccountBalance
	 * 
	 * @return AccountBalance
	 */
	public JTextField getAccountBalance(){
		return text_account_balance;
	}
	
	/**
	 * ZipCode
	 * 
	 * @return ZipCode
	 */
	public JTextField getZipCode(){
		return text_collection_office_zipcode;
	}
	
	/**
	 * Street
	 * 
	 * @return Street
	 */
	public JTextField getStreet(){
		return text_collection_office_street;
	}
	
	/**
	 * House number
	 *  
	 * @return house number
	 */
	public JTextField getNumber(){
		return text_collection_office_number;
	}
	
	/**
	 * OfficeCity
	 * 
	 * @return OfficeCity
	 */
	public JTextField getOfficeCity(){
		return text_collection_office_city;
	}
	
	/**
	 * tax_number
	 * 
	 * @return tax_number
	 */
	public JTextField getTaxNumber(){
		return text_tax_number;
	}
	
	/**
	 * Currency
	 * 
	 * @return currency
	 */
	public JTextField getCurrency(){
		return text_currency;
	}
	
	/**
	 * 
	 * @return fontstyle
	 */
	public Font getFontStyle(){
		return style_font;
	}
	
	/**
	 * 
	 * @return bankname
	 */
	public JTextField getBankName(){
		return text_bank_name;
	}
	
	/**
	 * 
	 * @return bankcode
	 */
	public JTextField getBankCode(){
		return text_bank_code;
	}
	
	/**
	 * 
	 * @return account number
	 */
	public JTextField getAccountNumber(){
		return text_account_number;
	}
	
	/**
	 * 
	 * @return swift
	 */
	public JTextField getSwift(){
		return text_swift;
	}

	/**
	 * 
	 * @return iban
	 */
	public JTextField getIban(){
		return text_iban;
	}
	
	/**
	 * 
	 * @return backbutton
	 */
	public JButton getBackButton(){
		return back_button;
	}
	
	/**
	 * 
	 * @return nextbutton
	 */
	public JButton getNextButton(){
		return next_button;
	}
	
	/**
	 * methode inits bank fields
	 */
	public void initBankTextfield(){
		
		this.df = new DecimalFormat("#0.00");
		
		if (association_account.getBankName() != null)
			text_bank_name.setText(association_account.getBankName());
		if (association_account.getBankCodeNumber() != "")
			text_bank_code.setText(association_account.getBankCodeNumber());
		if (association_account.getAccountNumber() != "")
			text_account_number.setText(association_account.getAccountNumber());
		if (association_account.getBicSwift() != null)
			text_swift.setText(association_account.getBicSwift());
		if (association_account.getiBan() != null)
			text_iban.setText(association_account.getiBan());
		if (association_object.getAccountBalance() != null){
			text_account_balance.setText(df.format(association_object.getAccountBalance()));
		}
	}
	
	/**
	 * methode inits office fields
	 */
	public void initOfficeTextfield(){
		
		text_purpose.setText(string_text_purpose.getText());
		   
		if (association_adress.getStreet() != null )
			text_collection_office_street.setText(association_adress.getStreet());
		if (association_adress.getHouseNumber() != "")
			text_collection_office_number.setText(association_adress.getHouseNumber());
		if (association_adress.getCity() != null)
			text_collection_office_city.setText(association_adress.getCity());
		if (association_adress.getZipCode() != "")
			text_collection_office_zipcode.setText(association_adress.getZipCode());
		if (association_office.getCurrency() != null)
			text_currency.setText(association_office.getCurrency());
		if (association_office.getTaxNumber() != null)
			text_tax_number.setText(association_office.getTaxNumber());
		if (association_office.getV() != null)
			v.setDate(association_office.getV());
		if (association_office.getA() != null)
			a.setDate(association_office.getA());
		}
}