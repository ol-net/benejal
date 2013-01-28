
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
package association.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.DecimalFormat;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import moneybook.model.KassenBuch;

import association.controller.MainMenuActionListener;
import association.model.AssociationDataTransfer;
import association.model.BookingDay;
import association.model.BookingDayDonator;

/**
 * gui-class represent main menu panel
 * 
 * @author Leonid Oldenburger
 */
public class MainMenuPanel extends JPanel implements Observer{
	
	private static final long serialVersionUID = 1L;
	
	private String title = "Förderverein";
	
	private JPanel button_panel1;
	private JPanel button_panel2;
	private JPanel button_panel3;
	private JPanel button_panel4;
	private JPanel menue_panel;
	private JPanel infopanel;
	private JPanel mainpanel;
	
	private JLabel info_label;
	private JLabel info_label_contribution;
	private JLabel info_label_donation;

	private JLabel info_saldo;
	
	private ButtonDesign member_button;
	private ButtonDesign moneybook_button;
	private ButtonDesign end_button;
	private ButtonDesign config_button;
	
	private GridBagConstraints b;
	
	private MainMenu hauptmenue;
	
	private AssociationDataTransfer association_data_transfer;
	
	private KassenBuch moneybook;
	
	private BookingDay bookingday;
	
	private BookingDayDonator donator_booking_day;
	
	private String info;
	private String info_contribution;
	private String info_donation;
	private DecimalFormat df;
	
	/**
	 * constructor creates main menu panel
	 * 
	 * @param mMenu
	 * @param moneyBook
	 * @param adatatrans
	 */
	public MainMenuPanel(MainMenu mMenu, KassenBuch moneyBook, AssociationDataTransfer adatatrans){

		this.moneybook = moneyBook;;
		this.moneybook.addObserver(this);
		this.association_data_transfer = adatatrans;
		this.association_data_transfer.addObserver(this);
		this.df = new DecimalFormat("#0.00");
		this.hauptmenue = mMenu;
		
		hauptmenue.setTitle(title+" - "+association_data_transfer.getAssociation().getName());
		
		setLayout(new GridBagLayout());
		setOpaque(false);
		
		mainpanel = new JPanel();
		mainpanel.setLayout(new GridBagLayout());
		mainpanel.setOpaque(false);
		
		infopanel = new JPanel();
		infopanel.setLayout(new GridBagLayout());
		infopanel.setOpaque(false);
		
		menue_panel = new JPanel();
		menue_panel.setLayout(new BorderLayout());
		menue_panel.setOpaque(false);
		
		member_button = new ButtonDesign("images/members_icon.png");
		moneybook_button = new ButtonDesign("images/account_icon.png");
		end_button = new ButtonDesign("images/end_icon.png");
		config_button = new ButtonDesign("images/config_icon.png");
		
		member_button.addActionListener(new MainMenuActionListener(hauptmenue, this, association_data_transfer, moneybook, null));
		moneybook_button.addActionListener(new MainMenuActionListener(hauptmenue, this, association_data_transfer, moneybook, null));
		end_button.addActionListener(new MainMenuActionListener(hauptmenue, this, association_data_transfer, moneybook, null));
		config_button.addActionListener(new MainMenuActionListener(hauptmenue, this, association_data_transfer, moneybook, null));
		
		button_panel1 = new JPanel();
		button_panel1.setLayout(new FlowLayout());
		button_panel1.setOpaque(false);
		button_panel1.add(member_button);
		menue_panel.add(button_panel1, BorderLayout.NORTH);
		
		button_panel2 = new JPanel();
		button_panel2.setLayout(new FlowLayout());
		button_panel2.setOpaque(false);
		button_panel2.add(moneybook_button);
		menue_panel.add(button_panel2, BorderLayout.WEST);
		
		button_panel3 = new JPanel();
		button_panel3.setLayout(new FlowLayout());
		button_panel3.setOpaque(false);
		button_panel3.add(end_button);
		menue_panel.add(button_panel3, BorderLayout.EAST);
		
		button_panel4 = new JPanel();
		button_panel4.setLayout(new FlowLayout());
		button_panel4.setOpaque(false);
		button_panel4.add(config_button);
		menue_panel.add(button_panel4, BorderLayout.SOUTH);
		
		bookingday = new BookingDay(association_data_transfer, association_data_transfer.getAssociation(), moneybook);
		donator_booking_day = new BookingDayDonator(association_data_transfer,  moneybook); 
		
		b = new GridBagConstraints();
		b.fill=GridBagConstraints.HORIZONTAL;
		
		b.insets =new Insets(3,2,2,2);
		
		info ="Nächstes Buchungsdatum: ";
		
		info_label = new JLabel(info);
		
		info_contribution ="Beiträge in "+bookingday.getBookingDay()+" Tagen für "+ bookingday.getMemberNumber()+" Mitglieder";
		
		info_label_contribution = new JLabel(info_contribution);
		
		info_donation ="Zusatzspenden in "+donator_booking_day.getBookingDay()+" Tagen für "+ donator_booking_day.getDonatorNumber()+" Mitglieder";
		
		info_label_donation = new JLabel(info_donation);
		
		info_saldo = new JLabel(df.format((moneybook.getSaldo())) + " " + association_data_transfer.getAssociation().getFinacneOffice().getCurrency());
		
		b.gridx=0;
		b.gridy=0;
		b.gridheight = 1;
		infopanel.add(info_label, b);
		
		b.gridx=1;
		b.gridy=0;
		b.gridheight = 1;
		infopanel.add(info_label_contribution, b);
		
		b.gridx=1;
		b.gridy=1;
		b.gridheight = 1;
		infopanel.add(info_label_donation, b);
		
		b.gridx=0;
		b.gridy=2;
		b.gridheight = 1;
		infopanel.add(new JLabel("Vereinssaldo: "), b);
		
		b.gridx=1;
		b.gridy=2;
		b.gridheight = 1;
		infopanel.add(info_saldo, b);
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill=GridBagConstraints.HORIZONTAL;
		
		c.insets =new Insets(10,40,10,30);
		
		c.gridx=2;
		c.gridy=0;
		c.gridheight = 1;
		mainpanel.add(button_panel1, c);
		
		c.gridx=0;
		c.gridy=2;
		c.gridheight = 1;
		mainpanel.add(button_panel2, c);
		
		c.gridx=3;
		c.gridy=2;
		c.gridheight = 1;
		mainpanel.add(button_panel3, c);
		
		c.gridx=2;
		c.gridy=4;
		c.gridheight = 1;
		mainpanel.add(button_panel4, c);
		
		GridBagConstraints d = new GridBagConstraints();
		d.fill=GridBagConstraints.HORIZONTAL;
		
		d.insets =new Insets(10,40,10,30);
		
		d.gridx=0;
		d.gridy=0;
		d.gridheight = 1;
		add(mainpanel, d);
		
		d.gridx=0;
		d.gridy=1;
		d.gridheight = 1;
		add(infopanel, d);
	}
	
	/**
	 * 
	 * @return member button
	 */
	public JButton getMemberButton(){
		return member_button;
	}
	
	/**
	 * 
	 * @return member button
	 */
	public JButton getMoneyBookButton(){
		return moneybook_button;
	}
	
	/**
	 * 
	 * @return member button
	 */
	public JButton getEndButton(){
		return end_button;
	}
	
	/**
	 * 
	 * @return member button
	 */
	public JButton getConfigButton(){
		return config_button;
	}
	
	/**
	 * update methode
	 */
	public void update(Observable arg0, Object arg1) {
		
		bookingday = new BookingDay(association_data_transfer, association_data_transfer.getAssociation(), moneybook);
		info_contribution ="Mitgliedsbeitrag in "+bookingday.getBookingDay()+" Tagen für "+ bookingday.getMemberNumber()+" Mitglieder";	
		
		try{
			info_label_contribution.setText(info_contribution);
		}catch (NullPointerException e){
		}
		
		donator_booking_day = new BookingDayDonator(association_data_transfer,  moneybook); 
		info_donation ="Zusatzspenden in "+donator_booking_day.getBookingDay()+" Tagen für "+ donator_booking_day.getDonatorNumber()+" Mitglieder";
		try{
			info_label_donation.setText(info_donation);
		}catch (NullPointerException e){
		}

		try{
			info_saldo.setText(df.format((moneybook.getSaldo())) + " " + association_data_transfer.getAssociation().getFinacneOffice().getCurrency());
		}catch (NullPointerException e){
		}
		
		hauptmenue.setTitle(title+" - "+association_data_transfer.getAssociation().getName());

	}	
}
