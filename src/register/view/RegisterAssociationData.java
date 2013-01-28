
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

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.text.AbstractDocument;

import association.model.Adress;
import association.model.Association;
import association.model.AssociationDataTransfer;
import association.model.DocumentSizeFilter;
import association.model.Telephone;
import association.view.BackgroundPanel;
import association.view.ButtonDesign;

import register.controller.AssociationDataActionListener;


import moneybook.model.KassenBuch;
import net.java.dev.designgridlayout.DesignGridLayout;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

/**
 * gui class for the association basic data 
 * 
 * @author Leonid Oldenburger
 */
public class RegisterAssociationData extends BackgroundPanel {
	
	protected static final long serialVersionUID = 1L;
	
	// language
	protected String title = "Registrierung - Basisdaten - Schritt 1 von 6";
	protected String data = "Basisdaten";
	protected String name = "Vereinsname:*";
	protected String street = "Straﬂe:*";
	protected String number = "Nr.:*";
	protected String postcode = "PLZ:*";
	protected String city = "Ort:*";
	protected String country = "Land: ";
	protected String email = "EMail :";
	protected String tel = "Telefon: ";
	protected String fax = "Fax :";
	
	// button
	protected ButtonDesign next_button;

	// panels
	protected JPanel datapanel;
	protected JPanel buttonpanel;
	
	// textfield
	protected JTextField text_name;
	protected JTextField text_street;
	protected JTextField text_number;
	protected JTextField text_city;
	protected JTextField text_postcode; 
	protected JTextField text_country;
	protected JTextField text_tel;
	protected JTextField text_fax;
	protected JTextField text_email;
	
	// window option
	protected Font style_font;
	protected DesignGridLayout layout;
	protected RegisterStartWindow mainframe;
	
	protected Association association_object;
	protected Adress association_adress;
	protected Telephone association_telephone;
	protected KassenBuch money_book;
	
	private AssociationDataTransfer association_data_transfer;
	
	public RegisterAssociationData(){
		this.association_object = null;
		this.style_font = null;
		this.mainframe = null;
	}
	
	/**
	 * create a new Window
	 * 
	 * @param width
	 * @param height
	 * @param dimWidth
	 * @param dimHeight
	 */
	public RegisterAssociationData(Font fontStyle, RegisterStartWindow frame, Association associationObject, KassenBuch moneyBook, AssociationDataTransfer adatatrans){
		
		this.association_object = associationObject;
		this.style_font = fontStyle;
		this.mainframe = frame;
		this.mainframe.setTitle(title);
		this.association_data_transfer = adatatrans;
		this.money_book = moneyBook;
		buildPanel();
		
		// Next-Button
		next_button = new ButtonDesign("images/next_icon.png");
		next_button.addActionListener(new AssociationDataActionListener(this, mainframe, association_object, money_book, association_data_transfer));
		
		GridBagConstraints b = new GridBagConstraints();
		b.fill=GridBagConstraints.HORIZONTAL;
		b.insets =new Insets(114,2,2,2);
		b.gridx=0;
		b.gridy=0;
		buttonpanel.add(next_button, b);
	}
	
	/**
	 * build panel
	 */
	public void buildPanel(){
		
		setLayout(new GridBagLayout());
		setOpaque(false);
		
		datapanel = new JPanel();
		datapanel.setBorder(new TitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY ), data));
		datapanel.setOpaque(false);
		
		layout = new DesignGridLayout(datapanel);
		makeBlank(layout);
		
		buttonpanel = new JPanel();
		buttonpanel.setLayout(new GridBagLayout());
		buttonpanel.setOpaque(false);
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill=GridBagConstraints.HORIZONTAL;
		c.insets =new Insets(30,2,2,2);
		
		c.gridx=0;
		c.gridy=0;
		add(datapanel, c);
		
		c.gridx=0;
		c.gridy=1;
		add(buttonpanel, c);
	}
	
	
	/**
	 * methode to create a blank.
	 * 
	 * @param layout
	 */
	public void makeBlank(DesignGridLayout layout){
		
		text_name = new JTextField(12);
		text_street = new JTextField(12);
		text_number = new JTextField(6);
		text_city = new JTextField(12);
		text_postcode = new JTextField(6);
		text_country = new JTextField(12);
		text_tel = new JTextField(12);
		text_fax = new JTextField(12);
		text_email = new JTextField(12);
		
		if (association_object != null){
			initTextField();
		}
		
		((AbstractDocument) text_name.getDocument()).setDocumentFilter(new DocumentSizeFilter(50, "."));
		((AbstractDocument) text_street.getDocument()).setDocumentFilter(new DocumentSizeFilter(30, "."));
		((AbstractDocument) text_city.getDocument()).setDocumentFilter(new DocumentSizeFilter(30, "."));
		((AbstractDocument) text_country.getDocument()).setDocumentFilter(new DocumentSizeFilter(30, "."));
		((AbstractDocument) text_email.getDocument()).setDocumentFilter(new DocumentSizeFilter(30, "."));
		((AbstractDocument) text_number.getDocument()).setDocumentFilter(new DocumentSizeFilter(25, "."));
		((AbstractDocument) text_postcode.getDocument()).setDocumentFilter(new DocumentSizeFilter(25, "[0-9]"));
		((AbstractDocument) text_tel.getDocument()).setDocumentFilter(new DocumentSizeFilter(25, "[0-9\\-\\+]"));
		((AbstractDocument) text_fax.getDocument()).setDocumentFilter(new DocumentSizeFilter(25, "[0-9\\-\\+]"));

		
		layout.row().grid(new JLabel(name), 2).add(text_name);
		layout.row().grid(new JLabel(street), 1).add(text_street).grid(new JLabel(number)).add(text_number, 1);
		layout.row().grid(new JLabel(city), 1).add(text_city).grid(new JLabel(postcode)).add(text_postcode);
		layout.row().grid(new JLabel(country), 1).add(text_country);
		layout.row().grid(new JLabel(tel), 1).add(text_tel);
		layout.row().grid(new JLabel(fax), 1).add(text_fax);
		layout.row().grid(new JLabel(email), 1).add(text_email);
	}
	
	/**
	 *  Name
	 *  
	 * @return name
	 */
	public JTextField getTextName(){
		return text_name;
	}
	
	/**
	 * Street
	 * 
	 * @return street
	 */
	public JTextField getTextStreet(){
		return text_street;
	}
	
	/**
	 * Housenumber
	 * 
	 * @return street number
	 */
	public JTextField getTextNumber(){
		return text_number;
	}

	/**
	 * City
	 * 
	 * @return city
	 */
	public JTextField getTextCity(){
		return text_city;
	}
	
	/**
	 * Zipcode
	 * 
	 * @return postcode
	 */
	public JTextField getTextPostcode(){
		return text_postcode;
	}
	
	/**
	 * Country
	 * 
	 * @return country
	 */
	public JTextField getTextCountry(){
		return text_country;
	}
	 
	/**
	 * Telefon
	 * 
	 * @return telefon
	 */
	public JTextField getTextTel(){
		return text_tel;
	}
	
	/**
	 * Fax
	 * 
	 * @return fax
	 */
	public JTextField getTextFax(){
		return text_fax;
	}
	
	/**
	 * Email
	 * 
	 * @return email
	 */
	public JTextField getTextEmail(){
		return text_email;
	}
	
	/**
	 * Next button
	 * 
	 * @return button
	 */
	public JButton getButton(){
		return next_button;
	}
	
	/**
	 * 
	 * @return fontstyle
	 */
	public Font getFontStyle(){
		return style_font;
	}
	
	/**
	 * methode initializes textfields.
	 */
	public void initTextField(){
	
		// get association name 
		if (association_object.getName() != null)
			text_name.setText(association_object.getName());
		
		// get association adress
		association_adress = association_object.getAdress();
		if (association_adress.getStreet() != null)
			text_street.setText(association_adress.getStreet());
		if (association_adress.getHouseNumber() != "")
			text_number.setText(association_adress.getHouseNumber());
		if (association_adress.getCity() != null)
			text_city.setText(association_adress.getCity());
		if (association_adress.getZipCode() != "")
			text_postcode.setText(association_adress.getZipCode());
		if (association_adress.getCountry() != null)
			text_country.setText(association_adress.getCountry());
		if (association_adress.getEmail() != null)
			text_email.setText(association_adress.getEmail());
		
		// get association telephone object
		association_telephone = association_object.getTelephone();
		if (association_telephone.getPrivatePhone() != "")
			text_tel.setText(association_telephone.getPrivatePhone());
		if (association_telephone.getFaxNumber() != "")
			text_fax.setText(association_telephone.getFaxNumber());
	}
}
