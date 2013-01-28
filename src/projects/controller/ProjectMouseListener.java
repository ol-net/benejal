
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
package projects.controller;



import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;

import projects.model.ProjectTableModel;
import projects.model.Project;
import projects.view.ProjectPopUpMenu;

import moneybook.model.KassenBuch;

/**
 * Reagiert auf Mouse-Events innerhalb der Projekten-Tabelle
 * @author Artem Petrov
 *
 */
public class ProjectMouseListener extends MouseAdapter{

	private KassenBuch kassenbuch;
	
	/**
	 * Erzeugt einen Listener
	 * @param kassenbuch Kassenbuch
	 */
	public ProjectMouseListener (KassenBuch kassenbuch) {
	
		this.kassenbuch = kassenbuch;
	
	}
	
	/**
	 * Zeigt ein Popup-Menü an
	 */
	public void mousePressed(MouseEvent e) {
      
		if (e.isPopupTrigger()) {
        	
        	Point p = new Point(e.getX(), e.getY());

        	int row = ((JTable)(e.getSource())).rowAtPoint(p);
        	
        	Project projekt =((ProjectTableModel)((JTable)e.getSource()).getModel()).getProjekt(row);
        	
        	ProjectPopUpMenu menu = new ProjectPopUpMenu(kassenbuch, projekt);
        	
            menu.show(e.getComponent(), e.getX(), e.getY());
        
        }
    }
	
	/**
	 * Zeigt ein Popup-Menü an
	 */
    public void mouseReleased(MouseEvent e) {
    
    	if (e.isPopupTrigger()) {
        	
        	Point p = new Point(e.getX(), e.getY());

        	int row = ((JTable)(e.getSource())).rowAtPoint(p);
        	
        	Project projekt =((ProjectTableModel)((JTable)e.getSource()).getModel()).getProjekt(row);
        	
        	ProjectPopUpMenu menu = new ProjectPopUpMenu(kassenbuch, projekt);
            
        	menu.show(e.getComponent(), e.getX(), e.getY());
            
        }
    
    } 
    
}
