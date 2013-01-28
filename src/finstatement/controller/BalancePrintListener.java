
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
package finstatement.controller;

import finstatement.view.BookingAreasPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import payments.view.ErrorFrame;

import utils.io.JahresAbschlussPrinter;


/**
 * Speichert einen JA als eine PDF-Datei
 * 
 * @author Artem Petrov
 *
 */
public class BalancePrintListener implements ActionListener {

	private String error = "Die Datei kann nicht gespeichert werden!";
	private String errorTitle = "Fehler";
	
	/**
	 * Speichert einen JA als eine PDF-Datei
	 */
	public void actionPerformed(ActionEvent e) {
		 
		BookingAreasPanel panel = (BookingAreasPanel)((JButton)e.getSource()).getParent();
		
		
		try {
			
			JahresAbschlussPrinter printer = new JahresAbschlussPrinter(panel.getKassenBuch(), panel.getAssociationDataTransfer(), panel.getYear(), panel.getViewType());
			
			String filename = "Jahresabschluss_" + panel.getYear() + ".pdf";
			
			filename = panel.getAssociationDataTransfer().getAssociation().getDataDir() + filename;
			
			printer.printPdf(filename);
			
		} catch (Exception e1) {
		
			new ErrorFrame(error, errorTitle);
			e1.printStackTrace();
	
		}
	}

}
