
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
package payments.controller;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.swing.JButton;

import payments.view.ErrorFrame;
import payments.view.BeitraegePanel;
import payments.view.OkFrame;

import member.model.Member;
import moneybook.model.Payment;
import utils.io.BankOrderWriter;
import utils.io.DTAprinter;
import association.model.Account;
import association.model.AssociationDataTransfer;
import association.model.BookingDay;
import association.model.BookingDayDonator;

/**
 * Erstellt und speichert einen (Sammel-)Lastschriftauftrag
 * @author Artem Petrov
 *
 */
public class SaveDebitOrderListener implements ActionListener {


	/**
	 * Erstellt und speichert einen (Sammel-)Lastschriftauftrag
	 */
	public void actionPerformed(ActionEvent e) {
		
		BeitraegePanel panel = (BeitraegePanel) ((JButton)e.getSource()).getParent();
	
		AssociationDataTransfer adt = panel.getAssociationDataTransfer();
		
		Account account = adt.getAssociation().getAccount();
		
		BookingDay day = new BookingDay(adt, adt.getAssociation(), panel.getMoneyBook());
		
		BookingDayDonator donatorday = new BookingDayDonator(adt, panel.getMoneyBook());
		
		Collection<Member> members = day.getMemberMap().values();
		
		Collection<Member> donators = donatorday.getMemberMap().values();
		
		if(members.isEmpty() && donators.isEmpty()) {
			new ErrorFrame("Mitglieder-/Spenderliste ist leer!", "Fehler");
		}
		
		else {
			Date date = day.getBookingDate();
			
			if(date.before(new Date())) {
				date = null;
			}
			// TODO Test (alle Buchungssätze in separaten logischen Dateien)
			BankOrderWriter dta = new DTAprinter(account, date);

			dta.setTyp(DTAprinter.TYP_LASTSCHRIFTEN);
			
			String filename = "";
			
			if(!members.isEmpty() && !donators.isEmpty()) {
				filename = "Mitgliedsbeitraege_Spenden";
			}
			else if(!members.isEmpty()) {
				filename = "Mitgliedsbeitraege";
			}
			else if(!donators.isEmpty()) {
				filename = "Spenden";
			}
			
			SimpleDateFormat df = new SimpleDateFormat("_dd_MM_yyyy");
			Date now = new Date();
			
			filename += df.format(now) + ".txt";
			
			filename = panel.getAssociationDataTransfer().getAssociation().getDataDir() + filename;
		
			long kto;
			long blz;
	        double premium;
			String name;
			String purpose;
			Payment p;
				
			// beiträge zu dta hinzufügen
			for(Member m : members) {
				
				kto = Long.parseLong(m.getAccount().getAccountNumber());
				blz = Long.parseLong(m.getAccount().getBankCodeNumber());
				premium = m.getMemberGroup().getPremium().getVlaue();
				name = m.getAccount().getOwner();
				purpose = "Mitgliedsbeitrag " + adt.getAssociation().getName() + " Nr " + m.getNumber();
				
				p = new Payment(null, kto , blz, premium, name, purpose, "");
				
				dta.addPayment(p);
				
			}
			
			// beiträge zu dta hinzufügen
			for(Member d : donators) {
				
				kto = Long.parseLong(d.getAccount().getAccountNumber());
				blz = Long.parseLong(d.getAccount().getBankCodeNumber());
				premium = d.getSpecialContribution().getVlaue();
				name = d.getAccount().getOwner();
				purpose = "Spende " + adt.getAssociation().getName() + " Nr " + d.getNumber();
				
				p = new Payment(null, kto , blz, premium, name, purpose, "");
				
				dta.addPayment(p);
				
			}
			
			if(dta.writeOrder(filename)) {
				// alles ok, 
				for(Member m : members) {
					m.setPayStatus(Member.BEARBEITUNG);
					adt.updateMember(m);
					adt.ok();
				}
				BookingDayDonator bdd = new BookingDayDonator(adt, panel.getMoneyBook());


				for(Member m : donators) {
					bdd.updateBookingDateForSpecialContribution(m);
				}
				
				
				new OkFrame("Der Auftrag ist unter " + filename + " gespeichert!", "Ok");
			}
			else {
				new ErrorFrame("Der Auftrag konnte nicht gespeichert werden!", "Fehler");
			}
		}
		
		
			
	}

}
