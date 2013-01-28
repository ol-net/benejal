
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
package finstatement.model;

/**
 * Repräsentiert einen Buchungsunterbereich
 * 
 * @author Artem Petrov
 *
 */
public class BookingSubArea {
	
	private int area;
	private String name;
	private double balance;
	private boolean debit;
	
	/**
	 * Erstellt einen Buchungsunterbereich
	 * 
	 * @param name Name
	 * @param area Nummer des übergeordneten Bereichs
	 * @param amount Saldo
	 * @param debit Typ
	 */
	public BookingSubArea(String name, int area, double amount, boolean debit) {
		
		this.area = area;
		this.name = name;
		this.balance = amount;
		this.debit = debit;
		
	}

	/**
	 * Stezt den Name des U-Bereichs
	 * @param name Name
	 * @return true, falls der Name geändert wurde
	 */
	public boolean setName(String name) {
		
		if (name.length() < 4) {
			return false;
		}
		
		this.name = name;
		return true;
		
	}
	
	/**
	 * Liefert den Name des Unterbereichs zurück
	 * 
	 * @return Name des Unterbereichs
	 */
	public String getName() {
		
		return name;
		
	}
	
	/**
	 * Liefert die Nummer des übergeordneten Bereichs zurück
	 * 
	 * @return Nummer des übergeordneten Bereichs zurück
	 */
	public int getArea() {
		
		return area;
		
	}
	
	/**
	 * Liefert den Saldo des Unterbereichs zurück
	 * 
	 * @return Saldo des Unterbereichs
	 */
	public double getBalance() {
		
		return balance;
		
	}

	/**
	 * Liefert den Typ des neuen Unterbereichs zurück
	 * 
	 * @return Typ des neuen Unterbereichs
	 */
	public boolean isDebit() {
		
		return debit;
		
	}

	/**
	 * Setzt den Saldo des Unterbereichs
	 * 
	 */
	public void setBalance(double balance) {
		
		this.balance = balance;
		
	}
	
	

}
