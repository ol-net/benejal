
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
package payments.view;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import payments.model.KontoauszugTableModel;

import association.model.AssociationDataTransfer;

import moneybook.model.KassenBuch;
import moneybook.model.Payment;

/**
 * Ein Popup Fenster, in dem die Zahlungsinformationen beim Verbuchen einer Zahlung angezeigt werden
 * @author Artem Petrov
 *
 */
@SuppressWarnings("serial")
public class VerbuchenFrame extends JDialog {
	
	private String title = "Verbuchen";
	
	/**
	 * Erzeugt ein Popup-Fenster
	 * @param associationDataDransfer AssociationDataTransfer
	 * @param kassenbuch Kassenbuch
	 * @param z Zahlung (Einnahme oder Ausgabe)
	 * @param tableModel Tabellen-Modell des Kontoauszugsmoduls
	 */
	public  VerbuchenFrame(AssociationDataTransfer associationDataDransfer, KassenBuch kassenbuch, Payment z, KontoauszugTableModel tableModel) {

		JPanel panel;
		if(z.getBetrag() < 0) {
			panel = new AusgabePanel(associationDataDransfer, kassenbuch, z, tableModel);
		}
		else {
			panel = new EinnahmePanel(associationDataDransfer, kassenbuch, z, tableModel);
		}
		this.setTitle(title);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.add(panel);

		this.pack();
		this.setModal(true);
		this.setResizable(false);
		this.setVisible(true);

	}
	
}
