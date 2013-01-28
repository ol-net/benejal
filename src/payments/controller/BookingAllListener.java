
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
import java.util.List;

import payments.model.KontoauszugTableModel;
import payments.view.VerbuchenFrame;

import moneybook.model.KassenBuch;
import moneybook.model.Payment;
import association.model.AssociationDataTransfer;

/**
 * Verbucht alle Zahlungen, die in der Kontoauszugstabelle enthalten sind und noch nicht verbucht wurden 
 * @author Artem Petrov
 *
 */
public class BookingAllListener implements ActionListener {

	private KassenBuch kassenbuch;
	private AssociationDataTransfer associationDataTransfer;
	private KontoauszugTableModel tableModel;
	
	/**
	 * Erzeugt einen Listener
	 * @param associationDataTransfer AssociationDataTransfer
	 * @param kassenbuch Kassenbuch
	 * @param tableModel Tabellen-Modell des Kontoauszugmoduls
	 */
	public BookingAllListener(AssociationDataTransfer associationDataTransfer, KassenBuch kassenbuch, KontoauszugTableModel tableModel) {
		
		this.kassenbuch = kassenbuch;
		this.associationDataTransfer = associationDataTransfer;
		this.tableModel = tableModel;
			
	}

	/**
	 * Verbucht alle Zahlungen
	 */
	public void actionPerformed(ActionEvent e) {
		
		List<Payment> payments = tableModel.getPayments();
		
		for(Payment p : payments) {
			if(!p.getVerbucht()) {
				new VerbuchenFrame(associationDataTransfer, kassenbuch, p, tableModel);
			}
		}
		
	}

}
