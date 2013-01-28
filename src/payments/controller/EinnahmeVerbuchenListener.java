
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
import payments.view.EinnahmePanel;
import payments.view.ErrorFrame;
import payments.view.VerbuchenFrame;

import moneybook.model.Beitrag;
import moneybook.model.KassenBuch;
import moneybook.model.Payment;
import moneybook.model.Spende;

/**
 * Verbucht eine Einnahme
 * @author Artem Petrov
 *
 */
public class EinnahmeVerbuchenListener implements ActionListener {

	private KassenBuch kassenbuch;
	private Payment z;
	private KontoauszugTableModel tableModel;
	
	/**
	 * Erzeugt einen Listener
	 * @param kassenbuch Kassenbuch
	 * @param z Zahlung
	 * @param tableModel Tabellen-Modell des Kontoauszugmoduls
	 */
	public EinnahmeVerbuchenListener(KassenBuch kassenbuch, Payment z, KontoauszugTableModel tableModel) {
		
		this.kassenbuch  = kassenbuch;
		this.z = z;
		this.tableModel = tableModel;
			
	}

	/**
	 * Speichert die Einnahme
	 */
	public void actionPerformed(ActionEvent e) {
		
		EinnahmePanel panel = (EinnahmePanel)((JButton)e.getSource()).getParent();
		
		VerbuchenFrame frame = ((VerbuchenFrame)((JButton) e.getSource()).getParent().getParent().getParent().getParent().getParent());
		
		
		switch (panel.getMemberTyp()) {
		
			case EinnahmePanel.MEMEBERSHIP_FEE : 
			{
		
				
				int memberNr = panel.getMemberNumber();
				
				
				// Mitglied unbekannt
				if(memberNr == -1) {
					
					new ErrorFrame("Mitglied unbekannt!", "Fehler");
				
				}
				
				else if(memberNr != -1) {
					
					Beitrag b = new Beitrag(z.getDatum(), z.getKTO(), z.getBLZ(), z.getBetrag(), z.getInhaber(), z.getZweck(), panel.getBookingArea(), memberNr);
					
					if(kassenbuch.savePremium(b)) {
						z.setVerbucht();
						tableModel.updateStatusRow();
						frame.setVisible(false);
						frame.dispose();
					}
					else {
						new ErrorFrame("Error!", "Fehler");
						frame.setVisible(false);
						frame.dispose();
					}
					
				}
				
			} break;
			
			case EinnahmePanel.DONATION: 
			{
			
				int donatorNr = panel.getMemberNumber();
				
				
				// Spender unbekannt
				if(donatorNr == -1) {
					
					// neuen Spender Anlegen
					new ErrorFrame("Spender unbekannt, bitte einen neuen Spender anlegen!", "Fehler");
				
				}
				
				else if(panel.getProject() == null) {
					new ErrorFrame("Bitte ein Projekt wählen!", "Fehler");
				}
				
				else if(donatorNr != -1) {
				
					Spende s = new Spende(z.getDatum(), z.getKTO(), z.getBLZ(), z.getBetrag(), z.getInhaber(), z.getZweck(), panel.getBookingArea(), donatorNr, panel.getProject().trim());
					
					if(kassenbuch.saveDonation(s)) {
						z.setVerbucht();
						tableModel.updateStatusRow();
						frame.setVisible(false);
						frame.dispose();
					}
					else {
						new ErrorFrame("Error!", "Fehler");
						frame.setVisible(false);
						frame.dispose();
					}
				}
				

			} break;
		
			case EinnahmePanel.OTHER: default: 
			{

				z.setBookingArea(panel.getBookingArea());
				
				if(panel.getBookingArea().equals(KassenBuch.donations) || panel.getBookingArea().equals(KassenBuch.premiums)) {
					new ErrorFrame("Bitte einen Buchungsbereich wählen!", "Fehler");
				}
				
				else if(kassenbuch.savePayment(z)) {
					z.setVerbucht();
					tableModel.updateStatusRow();
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

}
