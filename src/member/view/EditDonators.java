
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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.LinkedHashMap;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import database.DonatorDB;

import association.model.AssociationDataTransfer;

import association.view.BackgroundPanel;
import association.view.ButtonDesign;

import member.controller.BackToMainMenueActionListener;
import member.controller.EditDonatorMouseListener;

import member.model.DonatorTableModel;
import member.model.DonatorWithAdress;
//import member.model.MemberTableModel;
import moneybook.model.KassenBuch;

/**
 * class represents panel where the user can view some members
 * 
 * @author Leonid Oldenburger
 */
public class EditDonators extends BackgroundPanel implements Observer{

	private static final long serialVersionUID = 1L;
	
	private DefaultTableModel model;
	private JTable group_table;
	private JScrollPane pane;
	
	private JPanel table_panel;
	private JPanel button_panel;
	private JPanel combobox_panel;
	
	private JButton mainmenu_button;
	private JButton search_button;
	
	private JTextField search_text;
	
	private LinkedHashMap<Integer, DonatorWithAdress> association_donator_map;
	
	private MemberAndDonatorFrame mainframe;
	@SuppressWarnings("unused")
	private ManageMembers mmpanel;
	private JComboBox combobox;
	
	private AssociationDataTransfer association_data_transfer;
	private KassenBuch money_book;
	
	/**
	 * 
	 * @param mFrame
	 * @param mamepanel
	 * @param moneyBook
	 * @param adatatrans
	 */
	public EditDonators(MemberAndDonatorFrame mFrame, ManageMembers mamepanel, KassenBuch moneyBook, AssociationDataTransfer adatatrans){
		
		this.mainframe = mFrame;
		this.mmpanel = mamepanel;
		this.association_data_transfer = adatatrans;
		this.association_data_transfer.addObserver(this);
		this.money_book = moneyBook;
		this.money_book.addObserver(this);
		this.association_donator_map = association_data_transfer.getDonatorMap(DonatorDB.ALLE, null);
		
		setBorder(new TitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY ), "Spenderverwaltung"));
		
		setLayout(new GridBagLayout());
		setOpaque(false);
		
		table_panel = new JPanel();
		table_panel.setLayout(new BorderLayout());
		table_panel.setOpaque(false);
		table_panel.setSize(200,600);
		
		model = new DonatorTableModel(money_book, association_data_transfer, DonatorDB.ALLE, null);
		setTable();
		
	    group_table.getColumnModel().getColumn(0).setPreferredWidth(70); 
		group_table.getColumnModel().getColumn(1).setPreferredWidth(120); 
		group_table.getColumnModel().getColumn(2).setPreferredWidth(120); 
		group_table.getColumnModel().getColumn(3).setPreferredWidth(90); 
		group_table.getColumnModel().getColumn(4).setPreferredWidth(70);
		group_table.getTableHeader().setResizingAllowed(false);
		group_table.getTableHeader().setReorderingAllowed(false);
		
	    group_table.setPreferredScrollableViewportSize(new Dimension(700, 335));
	    // only one cell to select
	    group_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    
	    group_table.addMouseListener(new EditDonatorMouseListener(mainframe, group_table, association_donator_map, money_book, association_data_transfer));
	    
		pane = new JScrollPane(group_table);		
		
		table_panel.add(pane, BorderLayout.CENTER);
		
		mainmenu_button = new ButtonDesign("images/mainmenu_icon.png");
		mainmenu_button.addActionListener(new BackToMainMenueActionListener(mainframe, this));
		button_panel = new JPanel();
		button_panel.setLayout(new FlowLayout());
		button_panel.setOpaque(false);
		button_panel.add(mainmenu_button);
		
		String[] mgroup = new String[3];
		
		mgroup[0] = "Alle Spender";
		mgroup[1] = "Bekannte Spender";
		mgroup[2] = "Unbekannte Spender";

	    ActionListener actionListener = new ActionListener() {
	        public void actionPerformed(ActionEvent event) {		
	        	group_table.setModel(new DonatorTableModel(money_book, association_data_transfer, combobox.getSelectedIndex(), null));
	    	    group_table.getColumnModel().getColumn(0).setPreferredWidth(70); 
	    		group_table.getColumnModel().getColumn(1).setPreferredWidth(120); 
	    		group_table.getColumnModel().getColumn(2).setPreferredWidth(120); 
	    		group_table.getColumnModel().getColumn(3).setPreferredWidth(90); 
	    		group_table.getColumnModel().getColumn(4).setPreferredWidth(70);
	        }
	     };
		
		combobox = new JComboBox(mgroup);
		combobox.addActionListener(actionListener);
		combobox.setSelectedIndex(combobox.getSelectedIndex());

		combobox_panel = new JPanel();
		combobox_panel.setLayout(new FlowLayout());
		combobox_panel.setOpaque(false);
		combobox_panel.add(new JLabel("Ansicht: "));
		combobox_panel.add(combobox);
		search_button = new ButtonDesign("images/search_icon.png");
		search_text = new JTextField(12);
		combobox_panel.add(new JLabel("       "));
		combobox_panel.add(new JLabel("Suche nach Nachname:"));
		
		KeyListener keylistener = new KeyListener(){
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
	        	group_table.setModel(new DonatorTableModel(money_book, association_data_transfer, 3, search_text.getText()));
	    	    group_table.getColumnModel().getColumn(0).setPreferredWidth(70); 
	    		group_table.getColumnModel().getColumn(1).setPreferredWidth(120); 
	    		group_table.getColumnModel().getColumn(2).setPreferredWidth(120); 
	    		group_table.getColumnModel().getColumn(3).setPreferredWidth(90); 
	    		group_table.getColumnModel().getColumn(4).setPreferredWidth(70);
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
			}
		};
		
		search_text.addKeyListener(keylistener);
		combobox_panel.add(search_text);
		
	    ActionListener actionListener2 = new ActionListener() {
	        public void actionPerformed(ActionEvent event) {		
	        	group_table.setModel(new DonatorTableModel(money_book, association_data_transfer, 3, search_text.getText()));
	    	    group_table.getColumnModel().getColumn(0).setPreferredWidth(70); 
	    		group_table.getColumnModel().getColumn(1).setPreferredWidth(120); 
	    		group_table.getColumnModel().getColumn(2).setPreferredWidth(120); 
	    		group_table.getColumnModel().getColumn(3).setPreferredWidth(90); 
	    		group_table.getColumnModel().getColumn(4).setPreferredWidth(70);
	        }
	     };
		
	    search_button.addActionListener(actionListener2); 
		//combobox_panel.add(search_button);
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill=GridBagConstraints.HORIZONTAL;
		
		c.insets =new Insets(2,2,2,2);
		
		c.gridx=0;
		c.gridy=0;
		add(table_panel, c);
		
		c.gridx=0;
		c.gridy=1;
		add(combobox_panel, c);
		
		c.gridx=0;
		c.gridy=2;
		add(button_panel, c);
	}

	/**
	 * Back Button
	 * 
	 * @return member button
	 */
	public JButton getBackButton(){
		return mainmenu_button;
	}

	/**
	 * update methode
	 */
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		group_table.setModel(new DonatorTableModel(money_book, association_data_transfer, DonatorDB.ALLE, null));
	    group_table.getColumnModel().getColumn(0).setPreferredWidth(70); 
		group_table.getColumnModel().getColumn(1).setPreferredWidth(120); 
		group_table.getColumnModel().getColumn(2).setPreferredWidth(120); 
		group_table.getColumnModel().getColumn(3).setPreferredWidth(90); 
		group_table.getColumnModel().getColumn(4).setPreferredWidth(70);
		association_donator_map = association_data_transfer.getDonatorMap(DonatorDB.ALLE, null);
	}
	
	@SuppressWarnings("serial")
	public void setTable(){
		group_table = new JTable(model){ 
			public boolean isCellEditable(int rowIndex, int vColIndex){ 
				return false; 
			} 
		};
	}
}
