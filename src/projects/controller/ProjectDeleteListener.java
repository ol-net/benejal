
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


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import payments.view.ErrorFrame;
import projects.model.Project;

import moneybook.model.KassenBuch;

/**
 * Löscht ein Projekt
 * @author Artem Petrov
 *
 */
public class ProjectDeleteListener implements ActionListener {

	private KassenBuch kassenbuch;
	private Project p;
	
	/**
	 * Konstruktor, erzeugt einen Listener
	 * @param kassenbuch Kassenbuch
	 * @param p Projekt
	 */
	public ProjectDeleteListener(KassenBuch kassenbuch, Project p){
		this.p = p;
		this.kassenbuch = kassenbuch;
	}
	
	/**
	 * Löscht ein Projekt
	 */
	public void actionPerformed(ActionEvent e) {
		
		if(p != null) {
			List<Project> l = p.getChild();
		
			if(!(l.isEmpty())) {
				
				new ErrorFrame("Zuerst alle Unterprojekte löschen!", "Fehler");
				
			}
			
			else if(p.getCredit() != 0 || p.getDebit() != 0) {
				
				new ErrorFrame("Einige Zahlungen sind dem Projekt zugeordnet.\nLöschen nicht möglich!", "Fehler");
				
			}
			
			else {
				
				kassenbuch.deleteProjekt(p);
			
			}
			
			
			
		}
	}

}
