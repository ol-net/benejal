
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

import javax.swing.JButton;

import payments.model.KontoauszugTableModel;
import payments.view.AusgabePanel;
import payments.view.ErrorFrame;
import payments.view.VerbuchenFrame;

import association.model.AssociationDataTransfer;

import member.model.Member;
import moneybook.model.Beitrag;
import moneybook.model.KassenBuch;
import moneybook.model.PayingOut;
import moneybook.model.Payment;

/**
 * Verbucht eine Ausgabe
 * @author Artem Petrov
 *
 */
public class AusgabeVerbuchenListener implements ActionListener {

	private KassenBuch kassenbuch;
	private Payment z;
	private KontoauszugTableModel tableModel;
	private AssociationDataTransfer associationDataTransfer;
	
	/**
	 * Erzeugt einen Listener
	 * @param associationDataTransfer AssociationDataTransfer
	 * @param kassenbuch Kassenbuch
	 * @param z Zahlung
	 * @param tableModel Tabellen-Modell des Kontoauszugmoduls
	 */
	public AusgabeVerbuchenListener(AssociationDataTransfer associationDataTransfer, KassenBuch kassenbuch, Payment z, KontoauszugTableModel tableModel) {
		
		this.associationDataTransfer = associationDataTransfer;
		this.kassenbuch = kassenbuch;
		this.z = z;
		this.tableModel = tableModel;
			
	}

	/**
	 * Speichert die Ausgabe
	 */
	public void actionPerformed(ActionEvent e) {
		
		AusgabePanel panel = (AusgabePanel)((JButton)e.getSource()).getParent();
		String projectName = panel.getProject();
		String area = panel.getBookingArea();
		
		VerbuchenFrame frame = ((VerbuchenFrame)((JButton) e.getSource()).getParent().getParent().getParent().getParent().getParent());
		
		// TODO evtl ComboBox Modell anpassen?
		// für Projekt, aber name == "-"
		if(projectName == null && panel.getPaymentTyp() == AusgabePanel.FOR_PROJECT) {
			new ErrorFrame("Bitte ein Projekt wählen!", "Fehler");
		}
		
		// nicht für Projekt, aber area = "Projektbezogene Ausgabe"
		else if(area.equals(KassenBuch.projectPayOut) && panel.getPaymentTyp() == AusgabePanel.NOT_FOR_PROJECT) {
			new ErrorFrame("Bitte einen Buchungsbereich wählen!", "Fehler");
		}
		
		// projektbezogen
		else if(panel.getPaymentTyp() == AusgabePanel.FOR_PROJECT) {
			
			PayingOut g = new PayingOut(z.getDatum(), z.getKTO(), z.getBLZ(), z.getBetrag(), z.getInhaber(), z.getZweck(), area, projectName.trim());
			
			if(kassenbuch.saveProjectPayOut(g)) {
				z.setVerbucht();
				tableModel.updateStatusRow();
				frame.setVisible(false);
				frame.dispose();
			}
			else {
				new ErrorFrame("Die Zahlung wurde bereits verbucht!", "Fehler");
				z.setVerbucht();
				frame.setVisible(false);
				frame.dispose();
			}
			
		}
		// sonst. ausgabe
		else if(panel.getPaymentTyp() == AusgabePanel.NOT_FOR_PROJECT) {
			
			z.setBookingArea(area);
			
			if(kassenbuch.savePayment(z)) {
				z.setVerbucht();
				tableModel.updateStatusRow();
				frame.setVisible(false);
				frame.dispose();
			}
			else {
				new ErrorFrame("Die Zahlung wurde bereits verbucht!", "Fehler");
				z.setVerbucht();
				frame.setVisible(false);
				frame.dispose();
			}
			
		}
		
		
		else if(panel.getPaymentTyp() == AusgabePanel.RETURN) {
			
			// hier nur MB-Rückgabe
			// TODO
			// Spenden-Rück, sonstige Rück.
			// TODO member not found exception
			Member member = associationDataTransfer.getMember(z.getKTO(), z.getBLZ());
			Beitrag b = new Beitrag(z.getDatum(), z.getKTO(), z.getBLZ(), z.getBetrag(), z.getInhaber(), z.getZweck(), panel.getBookingArea(), member.getNumber());
			
			if(kassenbuch.savePremium(b)) {
				
				z.setVerbucht();
				tableModel.updateStatusRow();
				// TODO saldo
				frame.setVisible(false);
				frame.dispose();
			}
			else {
				new ErrorFrame("Error!", "Fehler");
				frame.setVisible(false);
				frame.dispose();
			}
			
		}
				
	}

}
