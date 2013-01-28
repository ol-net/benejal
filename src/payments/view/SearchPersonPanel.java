
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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
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

import member.model.DonatorTableModel;
import member.model.MemberTableModel;
import moneybook.model.KassenBuch;
import association.model.AssociationDataTransfer;
import association.model.Group;
import association.view.BackgroundPanel;
import association.view.ButtonDesign;

/**
 * class represents panel where the user can view some members
 * 
 * @author Leonid Oldenburger
 */
public class SearchPersonPanel extends BackgroundPanel implements Observer {

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

	private LinkedList<Group> group_list;
	private JComboBox combobox;
	
	private AssociationDataTransfer association_data_transfer;
	
	private KassenBuch money_book;
	
	private int group_value = -1;
	
	private int personType;
	
	
	/**
	 * 
	 * @param mFrame
	 * @param mamepanel
	 * @param moneyBook
	 * @param adatatrans
	 */
	public SearchPersonPanel (final PaymentPanel paymentPanel){
		
		setLayout(new GridBagLayout());
		setOpaque(false);

		this.association_data_transfer = paymentPanel.getAssociationDataTransfer();
		this.association_data_transfer.addObserver(this);
		this.money_book = paymentPanel.getKassenBuch();
		this.money_book.addObserver(this);
		
		this.personType = paymentPanel.getDonatorType();
			
		setBorder(new TitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY ), "Bitte wählen"));
        
        makeTable();
		
		mainmenu_button = new ButtonDesign("images/ok_icon.png");
		mainmenu_button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				
				try {
					
					int number = Integer.parseInt((String) group_table.getModel().getValueAt(group_table.getSelectedRow(), 0));
					
					// nummer übergeben
					// offcet für DonatorNumber (intern haben die Donatornummer folgendes format: 2XXXX, Members 0XXXX,
					// hier wird jedoch die Nummer von der PersonenTabelle übernommen, die für alle Personen folgendes Format haben
					// 0XXXX)
					if (personType == PaymentPanel.TYPE_DONATOR) {
						number += 20000;
					}
					
					paymentPanel.setPersonNumber(number);
					// Vaterframe schließen
					SearchPersonFrame frame = ((SearchPersonFrame)((JButton)event.getSource()).getParent().getParent().getParent().getParent().getParent().getParent());
					frame.dispose();
					
				} catch (NumberFormatException e) {
					
				}
			}
			
		});
		
		button_panel = new JPanel();
		button_panel.setLayout(new FlowLayout());
		button_panel.setOpaque(false);
		button_panel.add(mainmenu_button);
		
		makeComboBox();
		
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
	 * make table
	 */
	@SuppressWarnings("serial")
	public void makeTable(){

		table_panel = new JPanel();
		table_panel.setLayout(new BorderLayout());
		table_panel.setOpaque(false);
		table_panel.setSize(200,600);
		
		// TODO nach Persontyp unterscheiden
		if (personType == PaymentPanel.TYPE_MEMBER) {
			model = new MemberTableModel(money_book, association_data_transfer, group_value, null);
		}
		else if (personType == PaymentPanel.TYPE_DONATOR) {
			model = new DonatorTableModel(money_book, association_data_transfer, DonatorDB.ALLE, null);
		}
		
		group_table = new JTable(model){ 
			public boolean isCellEditable(int rowIndex, int vColIndex){ 
				return false; 
			} 
		};
		
	    group_table.setPreferredScrollableViewportSize(new Dimension(700, 335));
	    
	    
	    if (personType == PaymentPanel.TYPE_MEMBER) {
	    	
	    	group_table.getColumnModel().getColumn(0).setPreferredWidth(50); 
			group_table.getColumnModel().getColumn(1).setPreferredWidth(100); 
			group_table.getColumnModel().getColumn(2).setPreferredWidth(80); 
			group_table.getColumnModel().getColumn(3).setPreferredWidth(100); 
			group_table.getColumnModel().getColumn(4).setPreferredWidth(40);
			group_table.getColumnModel().getColumn(5).setPreferredWidth(50);
			group_table.getColumnModel().getColumn(6).setPreferredWidth(40);
			group_table.getColumnModel().getColumn(7).setPreferredWidth(40);
		
	    }
		else if (personType == PaymentPanel.TYPE_DONATOR) {
			group_table.getColumnModel().getColumn(0).setPreferredWidth(70); 
			group_table.getColumnModel().getColumn(1).setPreferredWidth(120); 
			group_table.getColumnModel().getColumn(2).setPreferredWidth(120); 
			group_table.getColumnModel().getColumn(3).setPreferredWidth(90); 
			group_table.getColumnModel().getColumn(4).setPreferredWidth(70);
		}
	    

		group_table.getTableHeader().setResizingAllowed(false);
		group_table.getTableHeader().setReorderingAllowed(false);
	    

	    // only one cell to select
	    group_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    
	    // evtl TODO doppelklick
//	    group_table.addMouseListener(new EditMemberMouseListener(mainframe, group_table, association_member_map, money_book, association_data_transfer));
	    
		pane = new JScrollPane(group_table);		
		
		table_panel.add(pane, BorderLayout.CENTER);
	}
	
	/**
	 * methode makes combobox
	 */
	// TODO nach Persontyp unterscheiden
	public void makeComboBox(){
		
		group_list = new LinkedList<Group>();
		
		group_list = association_data_transfer.getGroup();
		
		String[] mgroup = new String[0];

		 if (personType == PaymentPanel.TYPE_MEMBER) {
 	    	
			 mgroup = new String[group_list.size()+3];
			 
			mgroup[0] = "Alle Mitglieder";
			mgroup[1] = "Offene Beiträge";
			mgroup[2] = "Schwebende Mitglieder";
			
			for(int j = 0; j < group_list.size(); j++){
				mgroup[j+3] = group_list.get(j).getGroup();
			}
 		
 	    }
 		else if (personType == PaymentPanel.TYPE_DONATOR) {
 			
 			mgroup = new String[3];
 			
 			mgroup[0] = "Alle Spender";
 			mgroup[1] = "Bekannte Spender";
 			mgroup[2] = "Unbekannte Spender";
 			
 		}
	
		
		
	    ActionListener actionListener = new ActionListener() {
	        public void actionPerformed(ActionEvent event) {		
	        	// TODO nach Persontyp unterscheiden
	    	    if (personType == PaymentPanel.TYPE_MEMBER) {
	    	    	
	    	    	group_table.setModel(new MemberTableModel(money_book, association_data_transfer, combobox.getSelectedIndex(), null));
	    	    	group_table.getColumnModel().getColumn(0).setPreferredWidth(50); 
	    			group_table.getColumnModel().getColumn(1).setPreferredWidth(100); 
	    			group_table.getColumnModel().getColumn(2).setPreferredWidth(80); 
	    			group_table.getColumnModel().getColumn(3).setPreferredWidth(100); 
	    			group_table.getColumnModel().getColumn(4).setPreferredWidth(40);
	    			group_table.getColumnModel().getColumn(5).setPreferredWidth(50);
	    			group_table.getColumnModel().getColumn(6).setPreferredWidth(40);
	    			group_table.getColumnModel().getColumn(7).setPreferredWidth(40);
	    		
	    	    }
	    		else if (personType == PaymentPanel.TYPE_DONATOR) {
	    			group_table.setModel(new DonatorTableModel(money_book, association_data_transfer, combobox.getSelectedIndex(), null));
	    			group_table.getColumnModel().getColumn(0).setPreferredWidth(70); 
	    			group_table.getColumnModel().getColumn(1).setPreferredWidth(120); 
	    			group_table.getColumnModel().getColumn(2).setPreferredWidth(120); 
	    			group_table.getColumnModel().getColumn(3).setPreferredWidth(90); 
	    			group_table.getColumnModel().getColumn(4).setPreferredWidth(70);
	    		}
	        	
	        }
	     };
		
	     // TODO
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
		combobox_panel.add(new JLabel("Nachname:"));
		combobox_panel.add(search_text);
	    
		ActionListener actionListener2 = new ActionListener() {
	        public void actionPerformed(ActionEvent event) {		
	        	
	        	if (personType == PaymentPanel.TYPE_MEMBER) {
	    	    	
	        		group_table.setModel(new MemberTableModel(money_book, association_data_transfer, -8, search_text.getText()));
		    	    group_table.getColumnModel().getColumn(0).setPreferredWidth(50); 
		    		group_table.getColumnModel().getColumn(1).setPreferredWidth(100); 
		    		group_table.getColumnModel().getColumn(2).setPreferredWidth(80); 
		    		group_table.getColumnModel().getColumn(3).setPreferredWidth(100); 
		    		group_table.getColumnModel().getColumn(4).setPreferredWidth(40);
		    		group_table.getColumnModel().getColumn(5).setPreferredWidth(50);
		    		group_table.getColumnModel().getColumn(6).setPreferredWidth(40);
		    		group_table.getColumnModel().getColumn(7).setPreferredWidth(40);
	    		
	    	    }
	    		else if (personType == PaymentPanel.TYPE_DONATOR) {

	    			group_table.setModel(new DonatorTableModel(money_book, association_data_transfer, 3, search_text.getText()));
		    	    group_table.getColumnModel().getColumn(0).setPreferredWidth(70); 
		    		group_table.getColumnModel().getColumn(1).setPreferredWidth(120); 
		    		group_table.getColumnModel().getColumn(2).setPreferredWidth(120); 
		    		group_table.getColumnModel().getColumn(3).setPreferredWidth(90); 
		    		group_table.getColumnModel().getColumn(4).setPreferredWidth(70);
	    			
	    		}
	        	
	        	
	        	
	        }
	     };
		search_button.addActionListener(actionListener2);
		combobox_panel.add(search_button);
		
	}
	
	/**
	 * @return member button
	 */
	public JButton getBackButton(){
		return mainmenu_button;
	}

	/**
	 * update methode
	 */
	// TODO nach Persontyp unterscheiden
	public void update(Observable arg0, Object arg1) {
		
		if (personType == PaymentPanel.TYPE_MEMBER) {
	    	
			group_table.setModel(new MemberTableModel(money_book, association_data_transfer, group_value, null));
		    group_table.getColumnModel().getColumn(0).setPreferredWidth(50); 
			group_table.getColumnModel().getColumn(1).setPreferredWidth(100); 
			group_table.getColumnModel().getColumn(2).setPreferredWidth(80); 
			group_table.getColumnModel().getColumn(3).setPreferredWidth(100); 
			group_table.getColumnModel().getColumn(4).setPreferredWidth(40);
			group_table.getColumnModel().getColumn(5).setPreferredWidth(50);
			group_table.getColumnModel().getColumn(6).setPreferredWidth(40);
			group_table.getColumnModel().getColumn(7).setPreferredWidth(40);
		
	    }
		else if (personType == PaymentPanel.TYPE_DONATOR) {
			
			group_table.setModel(new DonatorTableModel(money_book, association_data_transfer, DonatorDB.ALLE, null));
		    group_table.getColumnModel().getColumn(0).setPreferredWidth(70); 
			group_table.getColumnModel().getColumn(1).setPreferredWidth(120); 
			group_table.getColumnModel().getColumn(2).setPreferredWidth(120); 
			group_table.getColumnModel().getColumn(3).setPreferredWidth(90); 
			group_table.getColumnModel().getColumn(4).setPreferredWidth(70);
			
	
		}
		
	
	}
}
