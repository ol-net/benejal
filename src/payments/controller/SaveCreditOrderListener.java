
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
import java.util.Date;

import javax.swing.JButton;

import moneybook.model.Payment;
import payments.view.ErrorFrame;
import payments.view.OkFrame;
import payments.view.ZahlungPanel;
import utils.io.BankOrderWriter;
import utils.io.DTAprinter;
import association.model.Account;

/**
 * Erstellt und speichert einen Überweisungsauftrag
 * @author Artem Petrov
 *
 */
public class SaveCreditOrderListener implements ActionListener {

	/**
	 * Speichert einen Überweisungsauftrag
	 */
	public void actionPerformed(ActionEvent e) {
		
		ZahlungPanel panel = (ZahlungPanel) ((JButton)e.getSource()).getParent();
		
		Date date = null;
		
		if (panel.getName().length() == 0) {
			
			new ErrorFrame("Bitte Name eingeben!", "Fehler!");
			
		}
		else if (panel.getKTO().length() < 6) {
			
			new ErrorFrame("Kontonummer ist zu kurz!", "Fehler!");
			
		}
		else if (panel.getBLZ().length() < 8) {
			
			new ErrorFrame("BLZ ist zu kurz!", "Fehler!");
			
		}
		else if (panel.getAmount().length() == 0) {
			
			new ErrorFrame("Bitte Betrag eingeben!", "Fehler!");
			
		}
		else if (panel.getPurpose().length() == 0) {
			
			new ErrorFrame("Bitte Verwendungszweck eingeben!", "Fehler!");
			
		}

		else {
		
			long kto = Long.parseLong(panel.getKTO());
			long blz = Long.parseLong(panel.getBLZ());
	        String amountStr = panel.getAmount();
	        amountStr = amountStr.replace(",", ".");
			double amount = Double.parseDouble(amountStr);
			String name = panel.getName();
			String purpose = panel.getPurpose();

			if (panel.getDate() != null) {
				
				date = panel.getDate();
				if (date.before(new Date())) {
					new ErrorFrame("Ausführungszeitpunkt muss in der Zukunft liegen!", "Fehler!");
				}
				
			}
			
			Payment p = new Payment(date, kto , blz, amount, name, purpose, "");
			
			Account account = panel.getAssociationDataTransfer().getAssociation().getAccount();
			
			BankOrderWriter dta = new DTAprinter(account, date);
			
			dta.setTyp(DTAprinter.TYP_GUTSCHRIFTEN);
			
//			SimpleDateFormat df = new SimpleDateFormat("dd_MM_yyyy");
			
			String filename = "UEBERWEISUNG_" + name.replace(" ", "_") + ".txt"; //+ "_" + df.format(panel.getDate());;
			
			filename = panel.getAssociationDataTransfer().getAssociation().getDataDir() + filename;
			
			dta.addPayment(p);
			
			dta.writeOrder(filename);
			
			new OkFrame("Der Auftrag ist unter " + filename + " gespeichert!", "Ok");
			
			panel.clear();
		
		}
			
	}

}
