
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
package association.view;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import moneybook.model.KassenBuch;
import association.controller.MainMenuActionListener;
import association.model.AssociationDataTransfer;

/**
 * class represent a menue bar in main menu.
 * 
 * @author Leonid Oldenburger
 */
public class VereinsMenueBar extends JMenuBar {
		
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private String config = "Konfiguration";
	
	private MainMenuPanel mainmenupanel;
	private MainMenu mainMenue;
	private KassenBuch moneybook;
	private AssociationDataTransfer association_data_transfer;
	
	String[] fileItems = new String[]{"Datei öffnen", "Programm beenden"};
	String[] editItems = new String[]{"Mitglieder/Spender", "Kassenbuch", "Konfiguration"};
	String[] memberEditItems = new String[] {"Mitglieder anzeigen", "Mitglied erfassen", "Spender anzeigen", "Spender erfassen", "Schriftverkehr"};
    String[] configEditItems = new String[] {"Basisdaten", "Administration", "Finanzen", "Mitgliedergruppen", "Konfiguration", "Sicherheit"};
	String[] moneyEditItems = new String[] {"Übersicht", "Beiträge einziehen", "Kontoauszug", "Zahlung", "Projekte", "Jahresabschluss"};
    String[] infoItems = new String[]{"Benutzerhandbuch", "Über"};
	
    /**
     * constructor creates a new MenuBar object
     * 
     * @param hMenue
     * @param hMenuePanel
     * @param money_book
     * @param adatatrans
     */
	public VereinsMenueBar(MainMenu hMenue, MainMenuPanel hMenuePanel, KassenBuch money_book, AssociationDataTransfer adatatrans) {
		 
		mainMenue = hMenue;
		mainmenupanel = hMenuePanel;
		moneybook = money_book;
		association_data_transfer = adatatrans;
		
	      JMenu fileMenu = new JMenu("Datei");
	      JMenu editMenu = new JMenu("Verwaltung");
	      JMenu otherMenu = new JMenu("Hilfe");

	      for (int i=0; i < fileItems.length; i++) {
	         JMenuItem item = new JMenuItem(fileItems[i]);
	        	 item.addActionListener(new MainMenuActionListener(mainMenue, mainmenupanel, association_data_transfer, moneybook, this));
	         fileMenu.add(item);
	      }
	      
	      // Insert a separator in the Edit menu in Position 1 after "Undo".
	      fileMenu.insertSeparator(1);
	      
	      JMenu member = new JMenu("Mitglieder/Spender");
	      
	      for (int i=0; i < memberEditItems.length; i++) {
	         JMenuItem item = new JMenuItem(memberEditItems[i]);
	         item.addActionListener(new MainMenuActionListener(mainMenue, mainmenupanel, association_data_transfer, moneybook, this));
	         member.add(item);
	      }
	      
	      JMenu moneyBook = new JMenu("Kassenbuch");
	      
	      for (int i=0; i < moneyEditItems.length; i++) {
		         JMenuItem item2 = new JMenuItem(moneyEditItems[i]);
		         item2.addActionListener(new MainMenuActionListener(mainMenue, mainmenupanel, association_data_transfer, moneybook, this));
		         moneyBook.add(item2);
		  }
	 
	      JMenu config = new JMenu("Konfiguration");
	      
	      for (int i=0; i < configEditItems.length; i++) {
		         JMenuItem item3 = new JMenuItem(configEditItems[i]);
		         item3.addActionListener(new MainMenuActionListener(mainMenue, mainmenupanel, association_data_transfer, moneybook, this));
		         config.add(item3);
		  }
	      
	      editMenu.add(member);
	      editMenu.add(moneyBook);
	      editMenu.add(config);
 
	      for (int i = 0; i < infoItems.length; i++) {
		     JMenuItem item4 = new JMenuItem(infoItems[i]);
	         item4.addActionListener(new MainMenuActionListener(mainMenue, mainmenupanel, association_data_transfer, moneybook, this));
		     otherMenu.add(item4);
	      }

	      add(fileMenu);
	      add(editMenu);
	      add(otherMenu);
	   }
}
