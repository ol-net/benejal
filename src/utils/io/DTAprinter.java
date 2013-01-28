
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

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import moneybook.model.Payment;
import association.model.Account;
import de.jost_net.OBanToo.Dtaus.CSatz;
import de.jost_net.OBanToo.Dtaus.DtausDateiWriter;
import de.jost_net.OBanToo.Dtaus.DtausException;

/**
 * Erstellt einen DTAUS-Bankauftrag
 * Alle S�tze werden in einer logischen Datei abgelegt
 * @author Artem Petrov
 *
 */
public class DTAprinter implements BankOrderWriter {
	
	private DtausDateiWriter dtausDateiWriter;

	private List<Payment> payments;
	
	private Account account;
	
	private int typ = 0;
	
	private Date date;
	
	public static final int TYP_LASTSCHRIFTEN = 0;
	
	public static final int TYP_GUTSCHRIFTEN = 1;
	
	/**
	 * Erzeugt einen DTA-Printer
	 */
	public DTAprinter() {
		
		this.payments = new LinkedList<Payment>();
	
	}
	
	/**
	 * Erzeugt einen DTA-Printer
	 * @param account Vereinskonto
	 * @param date Ausf�hrungsdatum
	 */
	public DTAprinter(Account account, Date date) {

		this.payments = new LinkedList<Payment>();
		this.account = account;
		this.date = date;
	
	}
	
	/**
	 * Setzt Typ des Auftrags (Last- oder Gutschriften)
	 * @param typ Typ
	 */
	public void setTyp(int typ) {

		this.typ = typ;
		
	}
	
	/**
	 * F�gt eine Liste von Zahlungen dem Bankauftrag hinzu
	 * @param p Liste von Zahlungen
	 */
	public void addPayments(List<Payment> p) {

		payments.addAll(p);
		
	}
	
	/**
	 * F�gt eine Zahlung dem Bankauftrag hinzu
	 * @param p Zahlung
	 */
	public void addPayment(Payment p) {

		payments.add(p);
		
	}

	/**
	 * Erstellt einen Auftrag
	 * @param path Pfad
	 * @return true, falls der Auftrag erstellt wurde
	 */
	public boolean writeOrder(String path) {

		FileOutputStream fos;
		
		try {
			fos = new FileOutputStream(path);
			dtausDateiWriter = new DtausDateiWriter(fos);
			
			// A-Satz
			addASatz();
			// C-Satz
			addCSatz();
			// E-Satz
			dtausDateiWriter.writeESatz();
			
			fos.close();
			
			return true;
			
		} catch (Exception e) {
			
			e.printStackTrace();
			return false;
		}
		
	}

	private void addASatz() throws Exception {
		
		if(date != null) {
			dtausDateiWriter.setAAusfuehrungsdatum(date);
		}
		
		// GK f�r �berweisung, LK f�r Lastschriften
		if (typ == TYP_LASTSCHRIFTEN) {
			dtausDateiWriter.setAGutschriftLastschrift("LK");
		}
		else {
			dtausDateiWriter.setAGutschriftLastschrift("GK");
		}
			
		dtausDateiWriter.setABLZBank(Long.parseLong(account.getBankCodeNumber()));
		dtausDateiWriter.setAKonto(Long.parseLong(account.getAccountNumber()));
		dtausDateiWriter.setAKundenname(replace(account.getOwner()));

		dtausDateiWriter.writeASatz();
	
	}
	
	private void addCSatz() throws DtausException, IOException {
		
		for(Payment p : payments) {
		
			dtausDateiWriter.setCBLZEndbeguenstigt(p.getBLZ());
			dtausDateiWriter.setCKonto(p.getKTO());
			if (typ == TYP_LASTSCHRIFTEN) {
				dtausDateiWriter.setCTextschluessel(CSatz.TS_LASTSCHRIFT_EINZUGSERMAECHTIGUNGSVERFAHREN);
			}
			else {
				dtausDateiWriter.setCTextschluessel(CSatz.TS_UEBERWEISUNGSGUTSCHRIFT);
			}
			
			dtausDateiWriter.setCInterneKundennummer(1);
			dtausDateiWriter.setCBetragInEuro(p.getBetrag());
			dtausDateiWriter.setCName(replace(p.getInhaber()));

			String zweck = replace(p.getZweck());
			int anzahlZwecke = Math.min((zweck.length() + 26)/27, 14);
			
			for(int i = 0; i < anzahlZwecke; i++){
				
				dtausDateiWriter.addCVerwendungszweck(zweck.substring(0, Math.min(zweck.length(), 27)));
				if (i < anzahlZwecke - 1) {
					zweck = zweck.substring(27);
				}
			}
			
			dtausDateiWriter.writeCSatz();
		
		}
		
	}
	
	
	private String replace(String str) {
		
		String replaced = str.replaceAll("�" , "ae");
		replaced = replaced.replaceAll("�" , "oe");
		replaced = replaced.replaceAll("�" , "ue");
		replaced = replaced.replaceAll("�" , "ss");
		
		replaced = replaced.replaceAll("�" , "ae");
		replaced = replaced.replaceAll("�" , "oe");
		replaced = replaced.replaceAll("�" , "ue");
		
		return replaced;
		
	}
	
	
}
