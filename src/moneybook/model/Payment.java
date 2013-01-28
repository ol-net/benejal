
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
 * Repräsentiert eine (Ein)Zahlung
 * @author Artem Petrov
 *
 */
public class Payment implements Comparable<Payment>{
	
	private Date datum;
	private Date bookingDate;
	private long kontonummer;
	private long blz;
	private double betrag;
	private String inhaber;
	private String zweck;
	private String bookingArea;
	private boolean verbucht;
	
	/**
	 * Erstellt eine Zahlung
	 * @param datum Datum
	 * @param kto Kontonummer
	 * @param blz BLZ
	 * @param betrag Betrag
	 * @param inhaber Kontoinhaber
	 * @param zweck Verwendungszweck
	 * @param bookingArea Buchungsunterbereich
	 */
	public Payment(Date datum, long kto, long blz, double betrag, String inhaber, String zweck, String bookingArea) {
		
		if (datum == null) {
			datum = new Date();
		}
		this.datum = datum;
		this.bookingDate = new Date();
		this.kontonummer = kto;
		this.blz = blz;
		this.betrag = betrag;
		this.inhaber = inhaber;
		this.zweck = zweck;
		this.verbucht = false;
		this.bookingArea = bookingArea;
		
	}
	
	/**
	 * Setzt Verbucht-Status
	 */
	public void setVerbucht() {
		
		this.verbucht = true;
		
	}
	
	/**
	 * Setzt den Buchungsunterbereich
	 * @param bookingArea Buchungsunterbereich
	 */
	public void setBookingArea(String bookingArea) {
		
		this.bookingArea = bookingArea;
		
	}

	/**
	 * Liefert das Buchungsdatum (auf dem Bankkonto) zurück
	 * @return Datum
	 */
	public Date getDatum () {
		
		return this.datum;
	}
	
	/**
	 * Liefert die Kontonummer zurück
	 * @return Kontonummer
	 */
	public long getKTO () {
		
		return this.kontonummer;
	}
	
	/**
	 * Liefert die BLZ zurück
	 * @return BLZ
	 */
	public long getBLZ () {
		
		return this.blz;
	}
	
	/**
	 * Liefert den Betrag zurück
	 * @return Betrag
	 */
	public double getBetrag() {
		
		return this.betrag;
		
	}
	
	/**
	 * Liefert den Inhaber zurück
	 * @return Inhaber
	 */
	public String getInhaber() {
		
		return this.inhaber;
	}
	
	/**
	 * Liefert den Verwendungszweck zurück
	 * @return Verwendungszweck
	 */
	public String getZweck() {
		
		return this.zweck;
		
	}
	
	/**
	 * Liefert den Verbucht-Status zurück
	 * @return Verbucht-Status
	 */
	public boolean getVerbucht() {
		
		return this.verbucht;
		
	}
	
	/**
	 * Liefert den Name des Buchungsunterbereichs zurück
	 * @return Name des Buchungsunterbereichs
	 */
	public String getBookingArea() {
		
		return this.bookingArea;
		
	}
	
	/**
	 * Liefert das Buchungsdatum zurück, an dem die Zahlung im Kassenbuch verbucht wurde
	 * @return Buchungsdatum
	 */
	public Date getBookingDate() {
		
		return this.bookingDate;
		
	}
	
	/**
	 * Setzt das Datum, an dem die Zahlung im Kassenbuch verbucht wurde
	 * @param date Buchungsdatum 
	 */
	public void setBookingDate(Date date) {
		
		this.bookingDate = date;
		
	}
	
    /**
     * Vergleicht zwei Zahlungen
     * 
     */
	
	public int compareTo(Payment z) {

		if (this.datum.compareTo(z.getDatum()) != 0){
			return this.datum.compareTo(z.getDatum());
		}
		
		else if (this.blz != z.getBLZ()) {
			return new Double(this.blz).compareTo(new Double(z.getBLZ()));
		}
		
		else if (this.kontonummer != z.getKTO()) {
			return new Double(this.kontonummer).compareTo(new Double(z.getKTO()));
		}
		
		else if (this.betrag != z.getBetrag()) {
			return new Double(this.betrag).compareTo(new Double(z.getBetrag()));
		}

		if (this.zweck.compareTo(z.getZweck()) != 0){
			return this.zweck.compareTo(z.getZweck());

		}

		else return 0;
	
	}

}
