
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

import payments.view.ErrorFrame;
import projects.model.Project;
import projects.view.ProjectPanel;

import moneybook.model.KassenBuch;

/**
 * Erstellt ein neues Projekt
 * @author Artem Petrov
 *
 */
public class CreateProjectListener implements ActionListener {

	private ProjectPanel panel;
	private KassenBuch kassenbuch;
	
	public CreateProjectListener (KassenBuch kassenbuch, ProjectPanel panel) {
		this.kassenbuch = kassenbuch;
		this.panel = panel;
	}
	
	@Override
	/**
	 * Erstellt ein neues Projekt
	 */
	public void actionPerformed(ActionEvent arg0) {

		if(panel.getProjectName().trim().length() < 3) {
			
			new ErrorFrame("Der Name muss mindestens drei Zeichen lang sein!", "Fehler!");
		
		}
		
		else {
			
			String name = panel.getProjectName().trim();
			String parentName = panel.getParentName();
			
			Project project;
			Project parent;
			
			if (parentName == null) {
			
				parent = null;
				
			}
			else {
				
				parent = new Project(parentName, null);
				
			}
			
			project = new Project(name, parent); 
			
			if(!kassenbuch.saveProjekt(project)) {
				
				new ErrorFrame("Ein Projekt mit diesem Namen ist bereits vorhanden!", "Fehler!");
			}
		}
		
	}

}
