
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
package utils.io;

import java.util.List;

import moneybook.model.Payment;

/**
 * Ein Interface, das von allen Bankauftrag-Writer implementiert werden muss
 * @author Artem Petrov
 *
 */
public interface BankOrderWriter {
	
	/**
	 * Fügt eine Zahlung dem Bankauftrag hinzu
	 * @param p Zahlung
	 */
	public void addPayment(Payment p);

	/**
	 * Fügt eine Liste von Zahlungen dem Bankauftrag hinzu
	 * @param p Liste von Zahlungen
	 */
	public void addPayments(List<Payment> p);
	
	/**
	 * Erstellt einen Auftrag
	 * @param path Pfad
	 * @return true, falls der Auftrag erstellt wurde
	 */
	public boolean writeOrder(String path);
	
	/**
	 * Setzt Typ des Auftrags (Last- oder Gutschriften)
	 * @param typ Typ
	 */
	public void setTyp(int typ);

}
