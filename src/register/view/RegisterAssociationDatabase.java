
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
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import register.controller.AssociationDatabaseActionListener;

import com.toedter.calendar.JCalendar;

import association.model.Association;
import association.model.AssociationDataTransfer;
import association.view.BackgroundPanel;
import association.view.ButtonDesign;

import moneybook.model.KassenBuch;
import net.java.dev.designgridlayout.DesignGridLayout;

/**
 * gui class for the association security data 
 * 
 * @author Leonid Oldenburger
 */
public class RegisterAssociationDatabase extends BackgroundPanel{
	
	private static final long serialVersionUID = 1L;
	
	// languagepack
	protected String title = "Registrierung - Vereinsdatenbank - Schritt 6 von 6";
	protected String password = "Passwort:";
	protected String passwordw = "Passwort wiederholen:";
	protected String passwordinfo = "Vereinsschutz";
	protected String configinfo = "Externe Datenbank";
	protected String text_server = "Server:";
	protected String text_port = "Port:";
	protected String text_user = "User:";
	
	// Textfelder
	protected JPasswordField text_password;
	protected JPasswordField text_passwordw;
	protected JPasswordField db_password;
	protected JTextField server;
	protected JTextField port;
	protected JTextField user;
	
	// Button
	protected JButton next_button;
	protected JButton back_button;
	
	// checkbox
	protected JCheckBox check_box_pass;
	protected JCheckBox check_box_db;
	
	//Panels
	protected JPanel passwordpanel;
	protected JPanel configurationpanel;
	protected JPanel buttonpanel;
	
	// window option
	protected Font style_font;
	protected DesignGridLayout password_layout;
	protected DesignGridLayout config_layout;
	protected RegisterStartWindow mainframe;
	protected Association association_object;
	
	private AssociationDataTransfer association_data_transfer;
	private KassenBuch money_book;
	
	/**
	 * constructor
	 */
	public RegisterAssociationDatabase(){
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
	public RegisterAssociationDatabase(Font fontStyle, RegisterStartWindow frame, Association associationObject, KassenBuch moneyBook, AssociationDataTransfer adatatrans){
		
		this.association_object = associationObject;
		this.style_font = fontStyle;
		this.mainframe = frame;
		this.mainframe.setTitle(title);
		this.association_data_transfer = adatatrans;
		this.money_book = moneyBook;
		
		buildPanel();
		
		// Next-Button
		next_button = new ButtonDesign("images/save_icon.png");
		back_button = new ButtonDesign("images/back_icon.png");
		back_button.addActionListener(new AssociationDatabaseActionListener(this, mainframe, association_object, money_book, association_data_transfer));
		next_button.addActionListener(new AssociationDatabaseActionListener(this, mainframe, association_object, money_book, association_data_transfer));
		
		GridBagConstraints b = new GridBagConstraints();
		b.fill=GridBagConstraints.HORIZONTAL;
		b.insets =new Insets(15,2,2,2);
		b.gridx=0;
		b.gridy=0;
		buttonpanel.add(back_button, b);
		
		b.gridx=1;
		b.gridy=0;
		buttonpanel.add(next_button, b);
	}
	
	/**
	 * methode creates panel
	 */
	public void buildPanel(){

		setLayout(new GridBagLayout());
		setOpaque(false);
		
		passwordpanel = new JPanel();
		passwordpanel .setBorder(new TitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY ), passwordinfo));
		passwordpanel .setOpaque(false);
		
		password_layout = new DesignGridLayout(passwordpanel);
		makePasswordBlank(password_layout);
		
		configurationpanel = new JPanel();
		configurationpanel.setBorder(new TitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY ), configinfo));
		configurationpanel.setOpaque(false);
		
		config_layout = new DesignGridLayout(configurationpanel);
		makeConfigBlank(config_layout);
		
		buttonpanel = new JPanel();
		buttonpanel.setLayout(new GridBagLayout());
		buttonpanel.setOpaque(false);
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill=GridBagConstraints.HORIZONTAL;
		c.insets =new Insets(30,2,2,2);
		
		c.gridx=0;
		c.gridy=0;
		add(passwordpanel, c);
		
		c.gridx=0;
		c.gridy=1;
		add(configurationpanel, c);
		
		c.gridx=0;
		c.gridy=2;
		add(buttonpanel, c);			
	}
	
	/**
	 * methode to create a blank for password.
	 * 
	 * @param layout
	 */
	public void makePasswordBlank(DesignGridLayout layout){
		
		text_password = new JPasswordField(12);
		text_passwordw = new JPasswordField(12);
		check_box_pass = new JCheckBox();
		check_box_pass.setOpaque(false);

		
		
		layout.row().grid(new JLabel(password)).add(text_password);
		layout.row().grid(new JLabel(passwordw)).add(text_passwordw);
		layout.emptyRow();
		layout.row().grid(new JLabel("Passwort setzen:"), 1).add(check_box_pass).empty(1);
	}
	
	/**
	 * methode to create a blank for configuration.
	 * 
	 * @param layout
	 */
	public void makeConfigBlank(DesignGridLayout layout){
		
		check_box_db = new JCheckBox();
		check_box_db.setOpaque(false);
		
		server = new JTextField(12);
		port = new JTextField(12);
		user = new JTextField(12);
		
		db_password = new JPasswordField(12);
		
		JPanel pan = new JPanel();
		pan.setOpaque(false);
		JCalendar calendar = new JCalendar();
		pan.add(calendar);
		
		layout.row().grid(new JLabel("        MySQL Datenbank:"), 1).add(check_box_db).empty(1);
		layout.row().grid(new JLabel(text_server)).add(server);
		layout.row().grid(new JLabel(text_port)).add(port);
		layout.row().grid(new JLabel(text_user)).add(user);
		layout.row().grid(new JLabel(password)).add(db_password);
		
		if (association_object != null){
			initTextField();
		}
	}
	
	/**
	 * Backbutton
	 * 
	 * @return backbutton
	 */
	public JButton getBackButton(){
		return back_button;
	}
	
	/**
	 * Nextbutton
	 * 
	 * @return nextbutton
	 */
	public JButton getNextButton(){
		return next_button;
	}
	
	/**
	 * Password
	 * 
	 * @return text_password
	 */
	public JPasswordField getPassword(){
		return text_password;
	}
	
	/**
	 * Password again
	 * 
	 * @return text_passwordw
	 */
	public JPasswordField getPasswordW(){
		return text_passwordw;
	}
	
	/**
	 * checkbox for password
	 * 
	 * @return 
	 */
	public int getCheckBoxValue(){
		
		if (check_box_pass.isSelected()){
			return 1;
		}
		else{
			return 0;
		}
	}
	
	/**
	 * set checkbox
	 * 
	 * @param wert
	 */
	public void setCheckBoxValue(int wert){
		
		if (wert == 1){
			this.check_box_pass.setSelected(true);
		}
		else{
			this.check_box_pass.setSelected(false);
		}

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
		
		if (association_object != null){
//			
//			this.text_dir.setText(association_object.getDataDir());
//			setStatus(association_object.getPaymentRhythm());
			setCheckBoxValue(association_object.getPassword());
		}	
	}
}
