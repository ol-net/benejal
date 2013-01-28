
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

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import javax.swing.table.AbstractTableModel;

/**
 * Tabellen-Modell der KB-Übersicht-Tabelle
 * @author Artem Petrov
 *
 */
@SuppressWarnings("serial")
public class UebersichtTableModel extends AbstractTableModel {

	private String[] columnNames = {"Datum", "Verwendungszweck", "Haben", "Soll"};
	private List<Payment> zahlungen;
	private Object[][] data;
	
	/**
	 * Erstellt ein Tabellen-Modell
	 * @param zahlungen Zahlungen, die in der Tabelle angezeigt werden
	 */
	public UebersichtTableModel(List<Payment> zahlungen) {
		this.zahlungen = zahlungen;
		data = new Object[zahlungen.size()][getColumnCount()];
		fillData();
	}
	
	public String getColumnName(int col) {
        return columnNames[col];
    }
	

	public int getColumnCount() {
		return 4;
	}


	public int getRowCount() {
		return zahlungen.size();
	}


	public Object getValueAt(int row, int col) {
		return data[row][col];
	}
	
	/**
	 * Liefert die in der Tabelle enthaltene Zahlungen zurück
	 * @return in der Tabelle enthaltene Zahlungen
	 */
	public List<Payment> getPayments() {
		
		return this.zahlungen;
		
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
			double betrag = z.getBetrag();
			
			if (betrag > 0.0) {
				data[row][2] = String.format("%.2f", betrag);
			}
			else {
				data[row][3] = String.format("%.2f", betrag);
			}
			
			row++;
		}

	}
	
}
