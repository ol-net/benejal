
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
package projects.model;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import moneybook.model.KassenBuch;

/**
 * Tabellen-Modell der Projekte-Tabelle
 * @author Artem Petrov
 *
 */
@SuppressWarnings("serial")
public class ProjectTableModel extends AbstractTableModel {

	private String[] columnNames = {"Projektname", "Einnahmen", "Ausgaben", "Start", "Ende"};
	private List<Project> projekte;
	private Object[][] data;
	
	public ProjectTableModel(KassenBuch kassenbuch) {
		
		this.projekte = kassenbuch.getProjekteAsList();
	
		data = new Object[projekte.size()][getColumnCount()];
	
		fillData();
	
	}
	
	public String getColumnName(int col) {
        return columnNames[col];
    }
	
	public int getColumnCount() {
		return 5;
	}


	public int getRowCount() {
		return data.length;
	}


	public Object getValueAt(int row, int col) {
		
		return data[row][col];
	
	}
	
	private void fillData() {
		
		Iterator<Project> it = projekte.iterator();
		
		int row = 0;
		
		Project p;
		
		SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		
		while(it.hasNext()) {
			
			String name;
			p = it.next();
			
			if(p.getParent() == null) {
				name = p.getName();
			}
			else if(p.getParent().getParent() == null) {
				name = "  " + p.getName();
			}
			else {
				name = "    " + p.getName();
			}
			
			data[row][0] = name;
			data[row][1] = String.format("%.2f", p.getCredit());
			data[row][2] = String.format("%.2f", p.getDebit());
			data[row][3] = df.format(p.getStart());
			if (p.getFinish() == null) {
				data[row][4] = "---";
			}
			else {
				data[row][4] = df.format(p.getFinish());
			}
			
			row++;
		}
	}
	
    /**
     * Liefert ein Projekt, das in der Zeile row enthalten ist, zurück
     * @param row Zeilennummer
     * @return Projekt
     */
	public Project getProjekt(int row) {
		
		return projekte.get(row);
		
	}
	
}
