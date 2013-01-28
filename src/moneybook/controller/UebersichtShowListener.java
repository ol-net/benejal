
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
import java.util.Date;

import javax.swing.JButton;

import moneybook.model.KassenBuch;
import moneybook.model.UebersichtTableModel;
import moneybook.view.KassenBuchPanel;

/**
 * Aktualisiert die Kassenbuchübersicht
 * 
 * @author Artem Petrov
 *
 */
public class UebersichtShowListener implements ActionListener {

	
	/**
	 * Aktualisiert die Kassenbuchübersicht
	 */
	public void actionPerformed(ActionEvent e) {
		
		KassenBuchPanel panel = (KassenBuchPanel)((JButton)e.getSource()).getParent();
		
		KassenBuch kassenbuch = panel.getKassenBuch();
		
		Date start = panel.getStartDate();
		Date finish = panel.getEndDate();
		
		if (start != null && finish != null && finish.after(start))
			panel.setModel(new UebersichtTableModel(kassenbuch.getZahlungen(start, finish)));

	}

}
