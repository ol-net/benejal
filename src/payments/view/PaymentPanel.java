
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

import moneybook.model.KassenBuch;
import association.model.AssociationDataTransfer;
import association.view.BackgroundPanel;

/**
 * Repräsentiert GUI eines Zahlungs-Panels
 * @author Artem Petrov
 *
 */
@SuppressWarnings("serial")
public abstract class PaymentPanel extends BackgroundPanel {
	

	private AssociationDataTransfer associationDataTransfer;
	private KassenBuch kassenbuch;
	
	public final static int TYPE_MEMBER = 0;
	public final static int TYPE_DONATOR = 1;
	
	public PaymentPanel(AssociationDataTransfer associationDataTransfer, KassenBuch kassenbuch) {
		
		this.kassenbuch = kassenbuch;
		this.associationDataTransfer = associationDataTransfer;
	}
	
	
	public AssociationDataTransfer getAssociationDataTransfer() {

		return this.associationDataTransfer;
	}
	
	public KassenBuch getKassenBuch() {

		return this.kassenbuch;
	}
	
	public abstract boolean setPersonNumber (int number);
	
	public abstract int getDonatorType ();

	
}
