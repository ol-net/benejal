
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
package moneybook.controller;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JButton;

import payments.view.ErrorFrame;

import moneybook.model.Payment;
import moneybook.view.KassenBuchPanel;
import utils.io.UebersichtPrinter;

import com.itextpdf.text.DocumentException;

/**
 * Speichert die Kassenbuchübersicht als eine PDF-Datei
 * 
 * @author Artem Petrov
 *
 */
public class UebersichtPrintListener implements ActionListener {

	private String error = "Die Datei kann nicht gespeichert werden!";
	private String errorTitle = "Fehler";
	
	
	/**
	 * Speichert die Kassenbuchübersicht als eine PDF-Datei
	 */
	public void actionPerformed(ActionEvent e) {
		 
		KassenBuchPanel panel = (KassenBuchPanel)((JButton)e.getSource()).getParent();

		List<Payment> payments = panel.getUebersichtTableModel().getPayments();
		
		UebersichtPrinter printer = new UebersichtPrinter(payments, panel.getSaldo(), panel.getName());
		
		try {
			SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		
			String start = df.format(payments.get(0).getDatum()).replace(".", "_");
			String finish = df.format(payments.get(payments.size() - 1).getDatum()).replace(".", "_");
			String filename = "Kassenbuch_vom_" + start + "_bis_" + finish + ".pdf";
			filename = panel.getAssociationDataTransfer().getAssociation().getDataDir() + filename;
			
			printer.printPdf(filename);
			
//			new OkFrame("Die Übersicht ist unter " + filename + " gespeichert!", "Ok");
		
		} catch (FileNotFoundException e1) {
		
			new ErrorFrame(error, errorTitle);
			e1.printStackTrace();
		
		} catch (DocumentException e1) {
		
			new ErrorFrame(error, errorTitle);
			e1.printStackTrace();
		
		}
		
		
	}

}
