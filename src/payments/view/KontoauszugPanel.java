
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
package payments.view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import moneybook.controller.MoneyBookFrameDisposeListener;
import moneybook.model.KassenBuch;
import net.java.dev.designgridlayout.DesignGridLayout;
import payments.controller.AuszugButtonListener;
import payments.controller.AuszugMouseListener;
import payments.controller.BookingAllListener;
import payments.model.KontoauszugTableModel;
import association.model.AssociationDataTransfer;
import association.view.BackgroundPanel;
import association.view.ButtonDesign;

/**
 * Repräsentiert GUI des Kontoauszug-Moduls
 * @author Artem Petrov
 *
 */
@SuppressWarnings("serial")
public class KontoauszugPanel extends BackgroundPanel {
	
	private String title = "Kontoauszug";
	private JTable kontoauszugTable;
	
	private JScrollPane tablePane;
	
	private JButton auszugBtn;
	private JButton bookAllBtn;
	private JButton mainMenuBtn;
	
	private KassenBuch kassenbuch;
	private AssociationDataTransfer associationDataTransfer;
	
	/**
	 * Erzeugt ein Kontoauszug-Panel
	 * @param associationDataTransfer AssociationDataTransfer
	 * @param kassenbuch Kassenbuch
	 */
	public KontoauszugPanel(AssociationDataTransfer associationDataTransfer, KassenBuch kassenbuch) {
		
		this.associationDataTransfer = associationDataTransfer;
		
		this.kassenbuch = kassenbuch;
		
		this.setBorder(new TitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY ), title));
		
		DesignGridLayout thisLayout = new DesignGridLayout(this);
		
		kontoauszugTable = new JTable();
		
		tablePane = new JScrollPane(kontoauszugTable);
		
		tablePane.setPreferredSize(new Dimension(600, 200));
		
		auszugBtn = new ButtonDesign("images/kontoauszug_einlesen_icon.png");
		auszugBtn.addActionListener(new AuszugButtonListener(this));
		
		bookAllBtn = new ButtonDesign("images/alle_zahlungen_verbuchen_icon.png");
		bookAllBtn.setEnabled(false);
		
		mainMenuBtn = new ButtonDesign("images/mainmenu_icon.png");
		mainMenuBtn.addActionListener(new MoneyBookFrameDisposeListener());
		
		thisLayout.row().grid().add(tablePane);
		thisLayout.row().center().add(mainMenuBtn).add(auszugBtn).add(bookAllBtn);

	}
	
	/**
	 * Setzt das Tabellen-Modell
	 * @param m Tabellen-Modell
	 */
	public void setModel(KontoauszugTableModel m) {
		
		kontoauszugTable.setModel(m);

		kontoauszugTable.getColumnModel().getColumn(0).setPreferredWidth(70);
		kontoauszugTable.getColumnModel().getColumn(1).setPreferredWidth(300);
		kontoauszugTable.getColumnModel().getColumn(2).setPreferredWidth(50);
		kontoauszugTable.getColumnModel().getColumn(3).setPreferredWidth(50);
		kontoauszugTable.getTableHeader().setReorderingAllowed(false); 
		
		kontoauszugTable.addMouseListener(new AuszugMouseListener(associationDataTransfer, kassenbuch));
		
		bookAllBtn.addActionListener(new BookingAllListener(associationDataTransfer, kassenbuch, m));
		
		
	}

	/**
	 * Blockiert den Read-Button
	 */
	public void disableReadBtn() {
		
		auszugBtn.setEnabled(false);
		bookAllBtn.setEnabled(true);
		
	}
	/**
	 * Gibt den Read-Button frei
	 */
	public void enableReadBtn() {
		
		auszugBtn.setEnabled(true);
		bookAllBtn.setEnabled(false);
		
	}

	/**
	 * Liefert das Kassenbuch zurück
	 * @return Kassenbuch
	 */
	public KassenBuch getKassenBuch() {
		return this.kassenbuch;
	}
	
	/**
	 * Liefert AssociationDataTransfer zurück
	 * @return AssociationDataTransfer
	 */
	public AssociationDataTransfer getAssociationDataTransfer() {
		return this.associationDataTransfer;
	}
	
	
	/**
	 * Leert die Kontoauszugstabelle
	 */
	public void clear() {
		
		kontoauszugTable.setModel(new KontoauszugTableModel());
		
	}
	
}
