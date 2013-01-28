
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

import java.util.Observable;
import java.util.Observer;

import javax.swing.JTabbedPane;

import moneybook.model.KassenBuch;

import association.model.Association;
import association.model.AssociationDataTransfer;

/**
 * gui-class represent association update panel
 * 
 * @author Leonid Oldenburger
 */
public class AssociationUpdatePanel extends JTabbedPane implements Observer{
	
	private static final long serialVersionUID = 1L;
	
	private String title = "Förderverein";
    private String con = "Konfiguration";
	private UpdateAssociationData association_data;
	private UpdateAssociationAdmin association_admin;
	private UpdateAssociationBudget association_budget;
	private UpdateAssociationGroup association_group;
	private UpdateAssociationConfig association_config;
	private UpdateAssociationDatabase association_database;
	private ConfigFrame mainframe;
	private Association association_object;

	private AssociationDataTransfer association_data_transfer;
	private KassenBuch money_book;
	
	/**
	 * constructor creates a update panel
	 * 
	 * @param frame
	 * @param moneyBook
	 * @param adatatrans
	 */
	public AssociationUpdatePanel(ConfigFrame frame, KassenBuch moneyBook, AssociationDataTransfer adatatrans){ 

		this.association_data_transfer = adatatrans;
		this.association_object = association_data_transfer.getAssociation();
		this.money_book = moneyBook;
		this.mainframe = frame;
		this.association_data_transfer.addObserver(this);

		mainframe.setTitle(title+" - "+association_data_transfer.getAssociation().getName()+ " - " + con);

		setOpaque(false);

		association_data = new UpdateAssociationData(mainframe, association_object, money_book, association_data_transfer);
		association_admin = new UpdateAssociationAdmin(mainframe, association_object, money_book, association_data_transfer);
		association_budget = new UpdateAssociationBudget(mainframe, association_object, money_book, association_data_transfer);
		association_group = new UpdateAssociationGroup(mainframe, association_object, money_book, association_data_transfer);
		association_config = new UpdateAssociationConfig(mainframe, association_object, money_book, association_data_transfer);
		association_database = new UpdateAssociationDatabase(mainframe, association_object, money_book, association_data_transfer);

		addTab("Vereinsdaten", association_data);
		addTab("Vereinsleitung", association_admin);
		addTab("Vereinsfinanzen", association_budget);
		addTab("Vereinsgruppen", association_group);
		addTab("Vereinskonfiguration", association_config);
		addTab("Vereinssicherheit", association_database);
		
		setTabPlacement(3);
	}

	/**
	 * update methode
	 */
	public void update(Observable arg0, Object arg1) {
		mainframe.setTitle(title+" - "+association_data_transfer.getAssociation().getName()+ " - " + con);
	}
}
