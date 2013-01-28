
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

import java.util.Iterator;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import projects.model.Project;

import moneybook.model.KassenBuch;


/**
 * Tabellen-Modell der Jahresabschluss-Tabelle
 * @author Artem Petrov
 *
 */
@SuppressWarnings("serial")
public class BookingAreasTableModel extends AbstractTableModel {
	
	public static final String idealisticArea = "Ideeller Bereich (Steuerfrei)";
	public static final String propertyArea = "Vermögensverwaltung";
	public static final String purposeArea = "Zweckbetrieb";
	public static final String economicArea = "Wirtschaftlicher Geschäftsbetrieb";
	private String summe = "Summe";
	
	private String[] columnNames = {"Buchungsbereich", "Einnahmen", "Ausgaben"};
	private Object[][] data;
	private KassenBuch kassenbuch;
	private int viewMode;
	private int year;
	
	public static final int NOT_SHOW_PROJEKTS = 0;
	public static final int SHOW_ROOT_PROJECTS = 1;
	public static final int SHOW_PROJECTS_AND_SUBPROJECTS = 2;
	
	public BookingAreasTableModel(KassenBuch kassenbuch, int viewMode, int year) {
		
		this.kassenbuch = kassenbuch;
		this.viewMode = viewMode;
		this.year = year;
		
		
		int rowCount = 0;
		
		if(viewMode == NOT_SHOW_PROJEKTS) {
			rowCount = kassenbuch.getBookingSubAreasCount() + 6;
		}
		else if(viewMode == SHOW_ROOT_PROJECTS) {
			// TODO
			rowCount = kassenbuch.getBookingSubAreasCount() + kassenbuch.getProjekteAsList(year).size() + 10;
		}
		else if(viewMode == SHOW_PROJECTS_AND_SUBPROJECTS){
			rowCount = kassenbuch.getBookingSubAreasCount() + kassenbuch.getProjekteAsList(year).size() + 10;
		}
			
		data = new Object[rowCount][getColumnCount()];
		
		fillData();
		
	}
	
	public String getColumnName(int col) {
        return columnNames[col];
    }
	
	public int getColumnCount() {
		return 3;
	}


	public int getRowCount() {
		return data.length;
	}


	public Object getValueAt(int row, int col) {
		return data[row][col];
	}
	
	/**
	 * Füllt die Tabelle
	 */
	private void fillData() {
		
		int i = 0;
		
		data[i++][0] = "<html><b>" + idealisticArea + "</b></html>";
		i = fillAreas(i, KassenBuch.IDEALISTIC_AREA);
		
		data[i][0] = "<html><b>" + summe + "</b></html>";
		data[i][1] = "<html><b>" + 
						String.format("%.2f", kassenbuch.getBookingAreaCredit(KassenBuch.IDEALISTIC_AREA, year)) 
						+ "</b></html>";
		data[i][2] = "<html><b>" + 
						String.format("%.2f", kassenbuch.getBookingAreaDebit(KassenBuch.IDEALISTIC_AREA, year)) 
						+ "</b></html>";
		i++;
		i++;
		     
		
		data[i++][0] = "<html><b>" + propertyArea + "</b></html>";
		i = fillAreas(i, KassenBuch.PROPERTY_AREA);
		
		data[i][0] = "<html><b>" + summe + "</b></html>";
		data[i][1] = "<html><b>" + 
			String.format("%.2f", kassenbuch.getBookingAreaCredit(KassenBuch.PROPERTY_AREA, year)) 
			+ "</b></html>";
		data[i][2] = "<html><b>" + 
		String.format("%.2f", kassenbuch.getBookingAreaDebit(KassenBuch.PROPERTY_AREA, year)) 
		+ "</b></html>";
		i++;
		i++;
		
		
		data[i++][0] = "<html><b>" + purposeArea + "</b></html>";
		i = fillAreas(i, KassenBuch.PURPOSE_AREA);
		data[i][0] = "<html><b>" + summe + "</b></html>";
		data[i][1] = "<html><b>" + 
			String.format("%.2f", kassenbuch.getBookingAreaCredit(KassenBuch.PURPOSE_AREA, year)) 
			+ "</b></html>";
		data[i][2] = "<html><b>" + 
		String.format("%.2f", kassenbuch.getBookingAreaDebit(KassenBuch.PURPOSE_AREA, year)) 
		+ "</b></html>";
		i++;
		i++;
		
		
		data[i++][0] = "<html><b>" + economicArea + "</b></html>";
		i = fillAreas(i, KassenBuch.ECONOMIC_AREA);
		data[i][0] = "<html><b>" + summe + "</b></html>";
		data[i][1] = "<html><b>" + 
			String.format("%.2f", kassenbuch.getBookingAreaCredit(KassenBuch.ECONOMIC_AREA, year)) 
			+ "</b></html>";
		data[i][2] = "<html><b>" + 
		String.format("%.2f", kassenbuch.getBookingAreaDebit(KassenBuch.ECONOMIC_AREA, year)) 
		+ "</b></html>";
		
		
	}
	
	private int fillAreas(int i, int area) {
		
		List<BookingSubArea> areas;
		BookingSubArea subarea;
		Iterator<BookingSubArea> it;
		
		// Einnahmen
		areas = kassenbuch.getBookingSubAreas(area, false, year);
		it = areas.iterator();
		
		while(it.hasNext()) {
		
			subarea = it.next();
			
			if (!subarea.getName().equals(KassenBuch.donations) && this.viewMode != NOT_SHOW_PROJEKTS) {
				data[i][0] = "  " + subarea.getName();
				data[i++][1] = String.format("%.2f", subarea.getBalance());
			}
			else {
				i = fillProjects(i);
			}
	
		}
		
		// Ausgaben
		areas = kassenbuch.getBookingSubAreas(area, true, year);
		it = areas.iterator();
		
		while(it.hasNext()) {
		
			subarea = it.next();

			if (!subarea.getName().equals(KassenBuch.projectPayOut) && this.viewMode != NOT_SHOW_PROJEKTS) {
				data[i][0] = "            " + subarea.getName();
				data[i++][2] = String.format("%.2f", subarea.getBalance());
			}
					
		}
		
		return i;
		
	}
	
	private int fillProjects(int row) {
		
		List<Project> projekte = kassenbuch.getProjekteAsList(year);
		Iterator<Project> it = projekte.iterator();

		Project p;
		
		while(it.hasNext()) {
			
			String name;
			p = it.next();
			
//			System.out.println(p.getName() + " " + p.getCredit() + " " + p.getDebit());
			
			if(p.getParent() == null) {
				name = "  " + p.getName();
			}
			else if(p.getParent().getParent() == null) {
				name = "    " + p.getName();
			}
			else {
				name = "      " + p.getName();
			}
			
			if (viewMode == SHOW_ROOT_PROJECTS) {
		
				if (p.getParent() == null) {
					data[row][0] = name;
					data[row][1] = String.format("%.2f", p.getCreditTotal());
					data[row][2] = String.format("%.2f", p.getDebitTotal());
					row++;
				}
			
			}
			else {
				data[row][0] = name;
				data[row][1] = String.format("%.2f", p.getCredit());
				data[row][2] = String.format("%.2f", p.getDebit());
				row++;
			}
			
		}
		
		return row;
	}
	
}
