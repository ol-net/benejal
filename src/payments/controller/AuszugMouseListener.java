
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



import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;

import payments.model.KontoauszugTableModel;
import payments.view.VerbuchenFrame;

import association.model.AssociationDataTransfer;

import moneybook.model.KassenBuch;
import moneybook.model.Payment;

/**
 * Verbucht eine Zahlung, auf deren Zeile in der Kontoauszugstabelle zweimal geklickt wurde 
 * @author Artem Petrov
 *
 */
public class AuszugMouseListener  extends MouseAdapter {

	private KassenBuch kassenbuch;
	private AssociationDataTransfer associationDataTransfer;
	
	/**
	 * Erzeugt einen Listener
	 * @param associationDataTransfer AssociationDataTransfer
	 * @param kassenbuch Kassenbuch
	 */
	public AuszugMouseListener (AssociationDataTransfer associationDataTransfer, KassenBuch kassenbuch) {
		this.kassenbuch = kassenbuch;
		this.associationDataTransfer = associationDataTransfer;
	}
	
	/**
	 * Verbucht die Zahlung
	 */
	public void mouseClicked(MouseEvent e) {
        
		if (e.getClickCount() == 2) 
        {
            int row = ((JTable)(e.getSource())).getSelectedRow();
        
            Payment z =((KontoauszugTableModel)((JTable)e.getSource()).getModel()).getZahlung(row);
            
            if(!z.getVerbucht())
            {
            	new VerbuchenFrame(associationDataTransfer, kassenbuch, z, (KontoauszugTableModel)((JTable)e.getSource()).getModel());
            }
        }
    } 
}
