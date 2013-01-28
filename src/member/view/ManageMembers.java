
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
package member.view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import mail.view.MView;

import member.model.DonatorWithAdress;
import member.model.Member;
import moneybook.model.KassenBuch;

import association.model.AssociationDataTransfer;

/**
 * class represents a JTabbedPane to manage the members
 * 
 * @author Leonid Oldenburger
 */
public class ManageMembers extends JTabbedPane implements Observer{

	private String title = "Förderverein";
	private String member_title = "Mitglieder- und Spenderverwaltung";
	
	private	JPanel addmemberpanel;
	private JPanel editmemberpanel;
	private JPanel mail;
	private JPanel editdonator;
	private JPanel adddonatorpanel;
	private Member association_member;
	@SuppressWarnings("unused")
	private DonatorWithAdress association_donator;
	
	private MemberAndDonatorFrame mainframe;
	
	private AssociationDataTransfer association_data_transfer;
	private KassenBuch money_book;
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 * @param frame
	 * @param selectTab
	 * @param aMember
	 * @param donator
	 * @param moneyBook
	 * @param adatatrans
	 */
	public ManageMembers(MemberAndDonatorFrame frame, int selectTab, Member aMember, DonatorWithAdress donator, KassenBuch moneyBook, AssociationDataTransfer adatatrans){
		
		setOpaque(false);
		this.association_member = aMember;
		this.association_donator = donator;
		this.mainframe = frame;
		this.association_data_transfer = adatatrans;
		this.money_book = moneyBook;
		this.association_data_transfer.addObserver(this);

		mainframe.setTitle(title+" - "+association_data_transfer.getAssociation().getName()+ " - " + member_title);
		
		addmemberpanel = new AddMember(mainframe, this, association_member, money_book, association_data_transfer);
		editmemberpanel = new EditMembers(mainframe, this, this.money_book, association_data_transfer);
		editdonator = new EditDonators(mainframe, this, money_book, association_data_transfer);
		adddonatorpanel = new AddDonator(mainframe, this, donator, money_book, association_data_transfer);
		mail = new MView(mainframe, this);
		
		addTab("Mitglieder anzeigen", editmemberpanel);
		addTab("Mitglied erfassen", addmemberpanel);
		addTab("Spender anzeigen", editdonator);
		addTab("Spender erfassen", adddonatorpanel);
		addTab("Schriftverkehr", mail);

		setSelectedIndex(selectTab);
		setTabPlacement(3);
	}	
	
	/**
	 * setVisible true
	 */
	public void setVisible(){
		setVisible(true);
	}

	/**
	 * update methode
	 */
	public void update(Observable arg0, Object arg1) {
		mainframe.setTitle(title+" - "+association_data_transfer.getAssociation().getName()+ " - " + member_title);	
	}
}
