
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
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import moneybook.controller.MoneyBookFrameDisposeListener;
import moneybook.model.KassenBuch;
import net.java.dev.designgridlayout.DesignGridLayout;
import payments.controller.SaveDebitOrderListener;
import payments.model.BeitraegeTableModel;
import association.model.AssociationDataTransfer;
import association.view.BackgroundPanel;
import association.view.ButtonDesign;

/**
 * Repräsentiert GUI der Übersicht über die offenen M-Beiträge/Spenden
 * @author Artem Petrov
 *
 */
@SuppressWarnings("serial")
public class BeitraegePanel extends BackgroundPanel implements Observer {
	
	private String title = "Offene Beiträge";
	
	private JTable mitgliedTable;
	
	private JScrollPane tablePane;

	private JButton einzugBtn;
	private JButton mainMenuBtn;
	
	private AssociationDataTransfer associationDataTransfer;
	private KassenBuch moneybook;
	
	/**
	 * Erzeugt das Panel der Übersicht über die offenen M-Beiträge/Spenden
	 * @param associationDataTransfer AssociationDataTransfer
	 * @param moneybook Kassenbuch
	 */
	public BeitraegePanel(AssociationDataTransfer associationDataTransfer, KassenBuch moneybook) {
		
		this.moneybook = moneybook;
		this.associationDataTransfer = associationDataTransfer;
		
		associationDataTransfer.addObserver(this);
		
		this.setBorder(new TitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY ), title));
		
		DesignGridLayout thisLayout = new DesignGridLayout(this);
		
		mitgliedTable = new JTable();
		
		BeitraegeTableModel m = new BeitraegeTableModel(associationDataTransfer, moneybook);
		
		setModel(m);
		
		tablePane = new JScrollPane(mitgliedTable);
		
		tablePane.setPreferredSize(new Dimension(600, 200));
		
		einzugBtn = new ButtonDesign("images/DTAUS_file_icon.png");
		
		einzugBtn.addActionListener(new SaveDebitOrderListener());

		mainMenuBtn = new ButtonDesign("images/mainmenu_icon.png");
		mainMenuBtn.addActionListener(new MoneyBookFrameDisposeListener());
		
		thisLayout.row().grid().add(tablePane);
		thisLayout.row().center().add(mainMenuBtn).add(einzugBtn);

	}
	
	/**
	 * Setzt das Tabellen-Modell
	 * @param m Tabellen-Modell
	 */
	public void setModel(BeitraegeTableModel m) {
		
		mitgliedTable.setModel(m);
		mitgliedTable.getTableHeader().setReorderingAllowed(false); 
		
	}

	/**
	 * Aktualisiert die Tabelle
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		
		BeitraegeTableModel m = new BeitraegeTableModel(associationDataTransfer, moneybook);
		setModel(m);
		
	}
	
	/**
	 * Liefert den AssociationDataTransfer zurück
	 * @return AssociationDataTransfer
	 */
	public AssociationDataTransfer getAssociationDataTransfer() {
		return this.associationDataTransfer;
	}
	
	/**
	 * Liefert das Kassenbuch zurück
	 * @return Kassenbuch
	 */
	public KassenBuch getMoneyBook() {
		return this.moneybook;
	}

	
}
