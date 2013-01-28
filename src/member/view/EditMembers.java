
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

import association.model.AssociationDataTransfer;
import association.model.Group;
import association.view.BackgroundPanel;
import association.view.ButtonDesign;

import member.controller.BackToMainMenueActionListener;
import member.controller.EditMemberActionListener;
import member.controller.EditMemberMouseListener;

import member.model.Member;
import member.model.MemberTableModel;
import moneybook.model.KassenBuch;

/**
 * class represents panel where the user can view some members
 * 
 * @author Leonid Oldenburger
 */
public class EditMembers extends BackgroundPanel implements Observer {

	private static final long serialVersionUID = 1L;
	
	private DefaultTableModel model;
	private JTable group_table;
	private JScrollPane pane;
	
	private JPanel table_panel;
	private JPanel button_panel;
	private JPanel combobox_panel;
	
	private JButton mainmenu_button;
	private JButton search_button;
	private JButton create_pdf_button;
	
	private JTextField search_text;
	
	private LinkedHashMap<Integer, Member> association_member_map;
	
	private MemberAndDonatorFrame mainframe;
	private LinkedList<Group> group_list;
	private JComboBox combobox;
	
	private AssociationDataTransfer association_data_transfer;
	
	private KassenBuch money_book;
	
	private int group_value = -1;
	
	private int group;
	
	/**
	 * 
	 * @param mFrame
	 * @param mamepanel
	 * @param moneyBook
	 * @param adatatrans
	 */
	public EditMembers(MemberAndDonatorFrame mFrame, ManageMembers mamepanel, KassenBuch moneyBook, AssociationDataTransfer adatatrans){
		
		setLayout(new GridBagLayout());
		setOpaque(false);
		
		this.mainframe = mFrame;
		this.money_book = moneyBook;
		this.money_book.addObserver(this);
		this.association_data_transfer = adatatrans;
		this.association_data_transfer.addObserver(this);
		
        this.association_member_map = association_data_transfer.getMember(-1, null);
        
		setBorder(new TitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY ), "Mitgliederverwaltung"));
        
        makeTable();
		
		mainmenu_button = new ButtonDesign("images/mainmenu_icon.png");
		mainmenu_button.addActionListener(new BackToMainMenueActionListener(mainframe, this));
		
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
		
		model = new MemberTableModel(money_book, association_data_transfer, group_value, null);
		group_table = new JTable(model){ 
			public boolean isCellEditable(int rowIndex, int vColIndex){ 
				return false; 
			} 
		};
		
	    group_table.setPreferredScrollableViewportSize(new Dimension(700, 335));
	    
	    group_table.getColumnModel().getColumn(0).setPreferredWidth(50); 
		group_table.getColumnModel().getColumn(1).setPreferredWidth(100); 
		group_table.getColumnModel().getColumn(2).setPreferredWidth(80); 
		group_table.getColumnModel().getColumn(3).setPreferredWidth(100); 
		group_table.getColumnModel().getColumn(4).setPreferredWidth(40);
		group_table.getColumnModel().getColumn(5).setPreferredWidth(50);
		group_table.getColumnModel().getColumn(6).setPreferredWidth(40);
		group_table.getColumnModel().getColumn(7).setPreferredWidth(40);

		group_table.getTableHeader().setResizingAllowed(false);
		group_table.getTableHeader().setReorderingAllowed(false);
	    
	    // only one cell to select
	    group_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    
	    group_table.addMouseListener(new EditMemberMouseListener(mainframe, group_table, association_member_map, money_book, association_data_transfer));
	    
		pane = new JScrollPane(group_table);		
		
		table_panel.add(pane, BorderLayout.CENTER);
	}
	
	/**
	 * methode makes combobox
	 */
	public void makeComboBox(){
		
		group_list = new LinkedList<Group>();
		
		group_list = association_data_transfer.getGroup();
		
		String[] mgroup = new String[group_list.size()+3];
		
		mgroup[0] = "Alle Mitglieder";
		mgroup[1] = "Offene Beiträge";
		mgroup[2] = "Schwebende Mitglieder";
		
		for(int j = 0; j < group_list.size(); j++){
			mgroup[j+3] = group_list.get(j).getGroup();
		}
		
	    ActionListener actionListener = new ActionListener() {
	        public void actionPerformed(ActionEvent event) {
	        	setView(combobox.getSelectedIndex());
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
	     };
		
		combobox = new JComboBox(mgroup);
		combobox.addActionListener(actionListener);
		//combobox.setSelectedIndex(combobox.getSelectedIndex());
		combobox_panel = new JPanel();
		combobox_panel.setLayout(new FlowLayout());
		combobox_panel.setOpaque(false);
		combobox_panel.add(new JLabel("Ansicht: "));
		combobox_panel.add(combobox);
		combobox_panel.add(new JLabel("   "));
		combobox_panel.add(new JLabel("Mitgliederliste: "));
		create_pdf_button = new ButtonDesign("images/create_pdf_icon.png");
		create_pdf_button.addActionListener(new EditMemberActionListener(this));
		combobox_panel.add(create_pdf_button);
		search_button = new ButtonDesign("images/search_icon.png");
		search_text = new JTextField(12);
		combobox_panel.add(new JLabel("   "));
		combobox_panel.add(new JLabel("Suche nach Nachname:"));
		
		KeyListener keylistener = new KeyListener(){

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
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

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
			}
		};
		
		search_text.addKeyListener(keylistener);
		combobox_panel.add(search_text);
				
		ActionListener actionListener2 = new ActionListener() {
	        public void actionPerformed(ActionEvent event) {		
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
	     };
		search_button.addActionListener(actionListener2);
		//combobox_panel.add(search_button);	
	}
	
	/**
	 * @return member button
	 */
	public JButton getBackButton(){
		return mainmenu_button;
	}
	
	/**
	 * @return pdf button
	 */
	public JButton getPDFButton(){
		return create_pdf_button;
	}

	/**
	 * update methode
	 */
	public void update(Observable arg0, Object arg1) {
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
	
	public void setView(int g){
		this.group = combobox.getSelectedIndex();
	}
	
	public int getView(){
		return group;
	}
}