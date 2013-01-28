
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
package moneybook.model;

import java.util.Date;

/**
 * Repräsentiert einen Mitgliedsbeitrag
 * @author Artem Petrov
 *
 */
public class Beitrag extends Payment {

	private int mitglied;
	
	/**
	 * Erstellt einen Beitrag
	 * @param datum Datum
	 * @param kto Kontonummer
	 * @param blz BLZ
	 * @param betrag Betrag
	 * @param inh Kontoinhaber
	 * @param zweck Verwendungszweck
	 * @param bookingArea Buchungsbereich
	 * @param mitglied Mitgliedsnummer
	 */
	public Beitrag(Date datum, long kto, long blz, double betrag, String inh,  String zweck, String bookingArea, int mitglied) {

		super(datum, kto, blz, betrag, inh, zweck, bookingArea);
		this.mitglied = mitglied;
	
	}
	
	/**
	 * Setzt die Mitgliedsnummer
	 * @param mitglied Mitgliedsnummer
	 */
	public void setMitglied(int mitglied) {
		
		this.mitglied = mitglied;
		
	}
	
	/**
	 * Liefert die Mitgliedsnummer zurück
	 * @return Mitgliedsnummer
	 */
	public int getMitglied() {
		
		return this.mitglied;
		
	}

	
	

}
