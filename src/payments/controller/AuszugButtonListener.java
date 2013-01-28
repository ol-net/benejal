
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
import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFileChooser;

import payments.model.KontoauszugTableModel;
import payments.view.ErrorFrame;
import payments.view.KontoauszugPanel;

import moneybook.model.Payment;
import utils.io.KontoauszugParser;
import utils.io.MT940parser;

/**
 * Initiiert das Einlesen eines Kontoauszuges
 * @author Artem Petrov
 *
 */
public class AuszugButtonListener implements ActionListener {

	private String error = "Die MT940-Datei kann nicht geöffent werden!";
	private String errorTitle = "Lesefehler";
	
	KontoauszugPanel panel;
	
	/**
	 * Erzeugt einen Listener
	 * @param panel KontoauszugPanel
	 */
	public AuszugButtonListener(KontoauszugPanel panel) {

		this.panel = panel;
	}

	/**
	 * Liest einen Kontoauszug ein
	 */
	public void actionPerformed(ActionEvent arg0) {
		
		JFileChooser chooser = new JFileChooser(panel.getAssociationDataTransfer().getAssociation().getDataDir());
		int returnVal = chooser.showOpenDialog(null);
		
		if(returnVal == JFileChooser.APPROVE_OPTION) {
        
			File file = chooser.getSelectedFile();
			
			try {
				KontoauszugParser parser = new MT940parser(file.getAbsolutePath(), panel.getKassenBuch());
				List<Payment> l = parser.getZahlungen();
				panel.setModel(new KontoauszugTableModel(l, panel));
	
				if(!l.isEmpty() && !checkAllRecorded(l)){
					panel.disableReadBtn();
				}
				
			} catch (Exception e) {
				
				new ErrorFrame(error, errorTitle);
				e.printStackTrace();
			
			}
        } 
		
	}
	
	private boolean checkAllRecorded(List<Payment> l) {
		if(l.isEmpty()) {
			return true;
		}
		
		Iterator<Payment> it = l.iterator();
		
		while(it.hasNext()) {
			if(!it.next().getVerbucht()) {
				return false;
			}
		}
		
		return true;
	}

}
