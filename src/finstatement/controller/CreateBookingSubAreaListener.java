
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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import payments.view.ErrorFrame;

import moneybook.model.KassenBuch;
import finstatement.model.BookingSubArea;
import finstatement.view.BookingAreasPanel;

/**
 * Erstellt einen neuen Buchungsunterbereich
 * 
 * @author Artem Petrov
 *
 */
public class CreateBookingSubAreaListener implements ActionListener {

	private BookingAreasPanel panel;
	private KassenBuch kassenbuch;
	
	
	/**
	 *  Erstellt einen Listener
	 *  
	 * @param kassenbuch Kassenbuch
	 * @param panel Panel des JA-Moduls
	 */
	public CreateBookingSubAreaListener(KassenBuch kassenbuch,
			BookingAreasPanel panel) {
		this.kassenbuch = kassenbuch;
		this.panel = panel;
	}

	@Override
	/**
	 * Erstellt einen neuen Buchungsunterbereich
	 */
	public void actionPerformed(ActionEvent arg0) {

		if(panel.getSubAreaName().trim().length() < 3) {
			
			new ErrorFrame("Der Name muss mindestens drei Zeichen lang sein!", "Fehler!");
		
		}
		
		else {
		
			kassenbuch.addBookingSubArea(new BookingSubArea(panel.getSubAreaName().trim(), panel.getArea(), 0.0, panel.isDebit()));
		}
		
	}

}
