
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
package projects.view;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import projects.controller.ProjectDeleteListener;
import projects.controller.ProjectFinishListener;
import projects.controller.ProjectRenameListener;
import projects.model.Project;

import moneybook.model.KassenBuch;

/**
 * Ein Popup-Menü
 * @author ap
 *
 */
@SuppressWarnings("serial")
public class ProjectPopUpMenu extends JPopupMenu {
	
	private String finish = "Projekt beenden";
	private String rename = "Projekt umbenennen";
	private String delete = "Projekt löschen";
	
	/**
	 * Erszeugt ein Menü
	 * @param kassenbuch Kassenbuch
	 * @param p Projekt
	 */
	public ProjectPopUpMenu(KassenBuch kassenbuch, Project p) {
		
		JMenuItem item1 = new JMenuItem(rename);
		JMenuItem item2 = new JMenuItem(finish);
		JMenuItem item3 = new JMenuItem(delete);
		
		item1.addActionListener(new ProjectRenameListener());
		item2.addActionListener(new ProjectFinishListener(kassenbuch, p));
		item3.addActionListener(new ProjectDeleteListener(kassenbuch, p));
		
//		this.add(item1);
		this.add(item2);
		this.add(item3);
		
	}
	
}
