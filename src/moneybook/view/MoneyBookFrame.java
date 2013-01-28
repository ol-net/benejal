
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
package moneybook.view;

import java.awt.BorderLayout;

import finstatement.view.BookingAreasPanel;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

import payments.view.KontoauszugPanel;
import payments.view.BeitraegePanel;
import payments.view.ZahlungPanel;
import projects.view.ProjectPanel;

import association.model.AssociationDataTransfer;
import association.view.BackgroundPanel;

import moneybook.model.KassenBuch;

/**
 * Hauptfenster des Kassenbuchmoduls
 * @author Artem Petrov
 *
 */
public class MoneyBookFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	
	private String title = "Kassenbuch - ";
	private String book = "Übersicht";
	private String projects = "Projekte";
	
	private JTabbedPane tabs = new JTabbedPane();
	
	/**
	 * Erstellt das Hauptfenster des Kassenbuchmoduls
	 * @param width Breite
	 * @param heigth Höhe
	 */
	public MoneyBookFrame(int width, int heigth, KassenBuch kassenbuch, AssociationDataTransfer association_data_transfer) {
		
		
		BackgroundPanel mainpanel = new BackgroundPanel();
		mainpanel.setLayout(new BorderLayout());
		mainpanel.setOpaque(false);

		this.add(mainpanel, BorderLayout.CENTER);
		
		tabs.add(book, new KassenBuchPanel(association_data_transfer, kassenbuch));
		tabs.add("Beiträge einziehen", new BeitraegePanel(association_data_transfer, kassenbuch));
		tabs.add("Kontoauszug", new KontoauszugPanel(association_data_transfer, kassenbuch));		
		tabs.add("Zahlung", new ZahlungPanel(association_data_transfer));
		tabs.add(projects, new ProjectPanel(kassenbuch));
		tabs.add("Jahresabschluss", new BookingAreasPanel(association_data_transfer, kassenbuch));
		
		tabs.setTabPlacement(JTabbedPane.BOTTOM);
		
//		this.getContentPane().add(tabs);
		mainpanel.add(tabs);
		this.setTitle(title + association_data_transfer.getAssociation().getName());
		this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		
		this.pack();
		this.setSize(width, heigth);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	

}
