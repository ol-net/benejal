
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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.toedter.calendar.JCalendar;

import association.model.Association;
import association.model.AssociationDataTransfer;
import association.model.BookingDay;
import association.view.BackgroundPanel;
import association.view.ButtonDesign;

import register.controller.AssociationConfigActionListener;
import register.controller.FileChooserActionListener;

import moneybook.model.KassenBuch;
import net.java.dev.designgridlayout.DesignGridLayout;

/**
 * gui class for the association configuration
 * 
 * @author Leonid Oldenburger
 */
public class RegisterAssociationConfig extends BackgroundPanel{
	
	private static final long serialVersionUID = 1L;
	
	// languagepack
	protected String title = "Registrierung - Vereinskonfiguration - Schritt 5 von 6";
	protected String password = "Passwort:";
	protected String passwordw = "Passwort wiederholen:";
	protected String passwordinfo = "Vereinsschutz:";
	protected String configinfo = "Vereinskonfigurationen:";
	protected String bookingdate = "Buchungsdatum:";
	protected String dir = "Dateiverzeichnis:";
	protected String bookingtime = "Zahlungsrhythmus:";
	
	// Textfelder
	protected JPasswordField text_password;
	protected JPasswordField text_passwordw;
	protected JTextField text_bookingdate;
	protected JTextField text_dir;
	
	// Button
	protected JButton next_button;
	protected JButton back_button;
	protected JButton dir_button;
	protected JButton logo_button;
	protected JButton signature_button;
	
	// checkbox
	protected JCheckBox check_logo;
	protected JCheckBox check_signature;
	
	// combobox
	protected JComboBox booking_time_box;
	
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
	
	protected AssociationDataTransfer association_data_transfer;
	protected KassenBuch money_book;
	
	protected JCalendar calendar;
	
	protected JLabel booking;
	
	protected BookingDay day;
	
	/**
	 * constructor
	 */
	public RegisterAssociationConfig(){
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
	public RegisterAssociationConfig(Font fontStyle, RegisterStartWindow frame, Association associationObject, KassenBuch moneyBook, AssociationDataTransfer adatatrans){
		
		this.association_object = associationObject;
		this.style_font = fontStyle;
		this.mainframe = frame;
		this.mainframe.setTitle(title);
		this.association_data_transfer = adatatrans;
		this.money_book = moneyBook;

		buildPanel();
		
		// Next-Button
		next_button = new ButtonDesign("images/next_icon.png");
		back_button = new ButtonDesign("images/back_icon.png");
		back_button.addActionListener(new AssociationConfigActionListener(this, mainframe, association_object, money_book, association_data_transfer));
		next_button.addActionListener(new AssociationConfigActionListener(this, mainframe, association_object, money_book, association_data_transfer));
		
		GridBagConstraints b = new GridBagConstraints();
		b.fill=GridBagConstraints.HORIZONTAL;
		b.insets =new Insets(14,2,2,2);
		b.gridx=0;
		b.gridy=0;
		buttonpanel.add(back_button, b);
		
		b.gridx=1;
		b.gridy=0;
		buttonpanel.add(next_button, b);
	}
	
	/**
	 * build panel
	 */
	public void buildPanel(){
		
		setLayout(new GridBagLayout());
		setOpaque(false);
		
		passwordpanel = new JPanel();
		passwordpanel .setBorder(new TitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY ), passwordinfo));
		passwordpanel .setOpaque(false);
		
		password_layout = new DesignGridLayout(passwordpanel);
		
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
		c.insets =new Insets(6,0,2,2);
		
		c.gridx=0;
		c.gridy=1;
		add(configurationpanel, c);
		
		c.gridx=0;
		c.gridy=2;
		add(buttonpanel, c);		
	}
	
	
	/**
	 * methode to create a blank for configuration.
	 * 
	 * @param layout
	 */
	public void makeConfigBlank(DesignGridLayout layout){

		text_dir = new JTextField(12);
		text_dir.setEditable(false);
		dir_button = new ButtonDesign("images/path_icon.png");
		dir_button.addActionListener(new FileChooserActionListener(this));
		String items[] = {"jährlich", "halbjährlich", "quartalweise", "monatlich"};
		booking_time_box = new JComboBox(items);
		
		booking = new JLabel();

		//day = new BookingDay(association_data_transfer, association_object, money_book);
    	
		calendar = new JCalendar();
		calendar.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
		    public void propertyChange(java.beans.PropertyChangeEvent evt) {
		    	setBooking();
			    }
			 });
		
	    ActionListener actionListener = new ActionListener() {
	        public void actionPerformed(ActionEvent event) {
	        	setBooking();
	        }
	    };
		
	    booking_time_box.addActionListener(actionListener);

    	
		JPanel bookingpanel = new JPanel();
		bookingpanel.setLayout(new BorderLayout());
		bookingpanel.setOpaque(false);
		bookingpanel.add(booking, BorderLayout.CENTER);
		
		check_logo = new JCheckBox();
		check_logo.setOpaque(false);
		
		check_signature = new JCheckBox();
		check_signature.setOpaque(false);
		
		logo_button = new ButtonDesign("images/logo_icon.png");
		logo_button.addActionListener(new FileChooserActionListener(this));
		
		signature_button = new ButtonDesign("images/signature_icon.png");
		signature_button.addActionListener(new FileChooserActionListener(this));
		
		layout.row().grid(new JLabel(bookingdate), 1).add(calendar).empty(1);
		layout.row().grid(new JLabel(bookingtime), 1).add(booking_time_box).empty(1);
		layout.row().grid(new JLabel(dir), 1).add(text_dir).add(dir_button);
		layout.row().grid(new JLabel("Logo verwenden:"), 1).add(check_logo).add(logo_button);
		layout.row().grid(new JLabel("Unterschrift verwenden:"), 1).add(check_signature).add(signature_button);
		
		if (association_object != null){
			initTextField();
		}
	}
	
	/**
	 * set booking 
	 */
	public void setBooking(){
				
		association_object.setPaymentTime(getBookingDate());
		association_object.setPaymentRhythm(getStatus());
    	//day = new BookingDay(association_data_transfer, association_object, money_book);
	}
	
	/**
	 * Back button
	 * 
	 * @return backbutton
	 */
	public JButton getBackButton(){
		return back_button;
	}
	
	/**
	 * Next button
	 * 
	 * @return nextbutton
	 */
	public JButton getNextButton(){
		return next_button;
	}
	
	/**
	 * dir
	 * 
	 * @return setdir
	 */
	public void setDirText(String text){
		this.text_dir.setText(text);
        setVisible(true);
	}
	
	/**
	 * get dir
	 * 
	 * @return text_dir
	 */
	public JTextField getDirText(){
		return text_dir;
	}
	
	/**
	 * get logo
	 * 
	 * @return button logo
	 */
	public JButton getLogoButton(){
		return logo_button;
	}
	
	/**
	 * get dir
	 * 
	 * @return button dir
	 */
	public JButton getDirButton(){
		return dir_button;
	}
	
	/**
	 * get signature
	 * 
	 * @return button signature
	 */
	public JButton getSignatureButton(){
		return signature_button;
	}

	/**
	 * get booking date
	 * 
	 * @return date 
	 */
	public String getBookingDate(){
		
        SimpleDateFormat dateformatMMDDYYYY = new SimpleDateFormat("dd.MM.yyyy");
        StringBuilder MMDDYYYY = new StringBuilder(dateformatMMDDYYYY.format(calendar.getDate()));
        
		return MMDDYYYY.toString();
	}
	
    /**
     * methode set booking date
     * 
     * @param date
     */
	public void setBookindDate(Date date){
		this.calendar.setDate(date);
	}
	
	/**
	 * methode returns status.
	 * 
	 * @return status
	 */
	public int getStatus(){
		return booking_time_box.getSelectedIndex();
	}
	
	/**
	 * methode sets status.
	 * 
	 * @param status
	 */
	public void setStatus(int state){
		booking_time_box.setSelectedIndex(state);
	}
	
	/**
	 * 
	 * @return fontstyle
	 */
	public Font getFontStyle(){
		return style_font;
	}
	
	/**
	 * checkbox for logo
	 * 
	 * @return 
	 */
	public int getLogo(){
		
		if (check_logo.isSelected()){
			return 1;
		}
		else{
			return 0;
		}
	}
	
	/**
	 * checkbox for signature
	 * 
	 * @return 
	 */
	public int getSignature(){
		
		if (check_signature.isSelected()){
			return 1;
		}
		else{
			return 0;
		}
	}
	
	/**
	 * set checkbox logo
	 * 
	 * @param value
	 */
	public void setLogo(int value){
		
		if (value == 1){
			this.check_logo.setSelected(true);
		}
		else{
			this.check_logo.setSelected(false);
		}

	}
	
	/**
	 * set checkbox logo
	 * 
	 * @param value
	 */
	public void setSignature(int value){
		
		if (value == 1){
			this.check_signature.setSelected(true);
		}
		else{
			this.check_signature.setSelected(false);
		}

	}
	
	/**
	 * methode initializes textfields.
	 */
	public void initTextField(){

		if (association_object != null){

			if(association_object.getPamentTime() != null){
				
				DateFormat df = new SimpleDateFormat("dd.MM.yyyy"); 

				Date date = new Date();
				try{
					date = df.parse(association_object.getPamentTime());             
				} catch (ParseException e) { 
					e.printStackTrace(); 
				} 
				setStatus(association_object.getPaymentRhythm());
				setBookindDate(date);
			}
			this.text_dir.setText(association_object.getDataDir());
						
			if(association_object.getLogo() == 1 || association_object.getLogo() == 3){
			    setLogo(1);
			}else{
				setLogo(0);
			}
			
			if(association_object.getLogo() == 2 || association_object.getLogo() == 3){
			    setSignature(1);
			}else{
				setSignature(0);
			}
		}	
	}
}