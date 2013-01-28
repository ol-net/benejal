
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
package payments.model;


import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import payments.view.KontoauszugPanel;

import moneybook.model.Payment;

/**
 * Tabellen-Modell der Kontoauszug-Tabelle
 * @author Artem Petrov
 *
 */
@SuppressWarnings("serial")
public class KontoauszugTableModel extends AbstractTableModel {

	private String[] columnNames = {"Datum", "Verwendungszweck", "Betrag", "Verbucht"};
	private List<Payment> zahlungen;
	private Object[][] data;
	KontoauszugPanel panel;
	
	/**
	 * Erzeugt ein Tabellen-Modell
	 */
	public KontoauszugTableModel() {
		data = new Object[0][getColumnCount()];
	}
	
	/**
	 * Erzeugt ein Tabellen-Modell
	 * @param zahlungen Lieste der im Kontoauszug enthaltenen Zahlungen
	 * @param panel Kontoauszug-Panel
	 */
	public KontoauszugTableModel(List<Payment> zahlungen, KontoauszugPanel panel) {
		this.panel = panel;
		this.zahlungen = zahlungen;
		data = new Object[zahlungen.size()][getColumnCount()];
		fillData();
	}
	
	public String getColumnName(int col) {
        return columnNames[col];
    }
	
	
	public Payment getZahlung(int row) {
		return zahlungen.get(row);
	}
	
	public List<Payment> getPayments() {
		return zahlungen;
	}
	

	public int getColumnCount() {
		return 4;
	}


	public int getRowCount() {
		if(zahlungen != null)
			return zahlungen.size();
		else
			return 0;
	}


	public Object getValueAt(int row, int col) {
		return data[row][col];
	}
	
	private void fillData() {
		
		Iterator<Payment> it = zahlungen.iterator();
		Payment z;
		int row = 0;
		SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		
		while(it.hasNext()) {
			z = it.next();
			data[row][0] = df.format(z.getDatum());
			data[row][1] = z.getZweck();
			data[row][2] = String.format("%.2f", z.getBetrag());
			if(z.getVerbucht()) {
				data[row][3] = "ja";	
			}
			else {
				data[row][3] = "nein";
			}

			row++;

		}

	}

	/**
	 * Aktualisiert den Status von jeder Zahlung (verbucht / nicht verbucht)
	 */
	public void updateStatusRow() {
		Iterator<Payment> it = zahlungen.iterator();
		Payment z;
		int row = 0;
		
		boolean bookingAll = true;
		
		while(it.hasNext()) {
			z = it.next();
			if(z.getVerbucht()) {
				data[row][3] = "ja";	
			}
			else {
				data[row][3] = "nein";
				bookingAll = false;
			}
			row++;
		}
		
		if(bookingAll) {
			panel.enableReadBtn();
			panel.clear();
		}
		
		fireTableDataChanged();
		
	}
		
	

}
