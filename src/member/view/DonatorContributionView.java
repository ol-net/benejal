
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
package member.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import member.controller.ContributionViewActionListener;
import member.controller.DonatorActionListener;
import member.model.DonationTableModel;
import moneybook.model.KassenBuch;

import association.model.AssociationDataTransfer;
import association.view.ButtonDesign;


public class DonatorContributionView extends JPanel {
		

	private static final long serialVersionUID = 1L;
	private DefaultTableModel model;
	private JTable group_table;
	private JScrollPane pane;
	
	private JPanel table_panel;
	private JPanel button_panel;
	
	private JButton back_button;
	private JButton receipt_but;
	private DonatorInformation donator_information;
	
	private AssociationDataTransfer association_data_transfer;
	private KassenBuch money_book; 
	private int donator_id;
	
	public DonatorContributionView(DonatorInformation donInformation, KassenBuch moneyBook, AssociationDataTransfer adatatrans){
		
		this.donator_information = donInformation;
		this.association_data_transfer = adatatrans;
		this.money_book = moneyBook;
		
		setLayout(new GridBagLayout());
		setOpaque(false);
		
		donator_id = donator_information.getDonatorID();
		
		table_panel = new JPanel();
		table_panel.setLayout(new BorderLayout());
		table_panel.setOpaque(false);
		table_panel.setSize(200,600);
		
		model = new DonationTableModel(money_book, donator_id);
		group_table = new JTable(model){ 

			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int vColIndex){ 
				return false; 
			} 
		};
		
	    group_table.setPreferredScrollableViewportSize(new Dimension(600, 350));
	    // only one cell to select
	    group_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    
		group_table.getTableHeader().setResizingAllowed(false);
		group_table.getTableHeader().setReorderingAllowed(false);
	    
		pane = new JScrollPane(group_table);		
		
		table_panel.add(pane, BorderLayout.CENTER);
		
		back_button = new ButtonDesign("images/back_icon.png");
		back_button.addActionListener(new ContributionViewActionListener(donator_information, this, association_data_transfer));
		receipt_but = new ButtonDesign("images/quittung_icon_small.png");
		receipt_but.addActionListener(new DonatorActionListener(donator_information, this));
		
		
		button_panel = new JPanel();
		button_panel.setLayout(new FlowLayout());
		button_panel.setOpaque(false);
		button_panel.add(back_button);
		button_panel.add(receipt_but);
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill=GridBagConstraints.HORIZONTAL;
		
		c.insets =new Insets(2,2,2,2);
		
		c.gridx=0;
		c.gridy=0;
		add(table_panel, c);
		
		c.gridx=0;
		c.gridy=2;
		add(button_panel, c);
	}	
	
	/**
	 * method returns back button
	 * 
	 * @return back button
	 */
	public JButton getBackButton(){
		return back_button;
	}
	
	/**
	 * method returns receipt button
	 * 
	 * @return receipt button
	 */
	public JButton getReceiptButton(){
		return receipt_but;
	}
}
