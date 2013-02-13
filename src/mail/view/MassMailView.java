
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
package mail.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import association.model.AssociationDataTransfer;
import association.model.Group;

import mail.controller.MailMouseListener;
import mail.controller.MassMailActionListener;

import mail.model.MailTableModel;
import moneybook.model.KassenBuch;

import net.java.dev.designgridlayout.DesignGridLayout;

import association.view.ButtonDesign;

/**
 * gui class for the mass mail view 
 * 
 * @author Leonid Oldenburger
 */
public class MassMailView extends JPanel implements Observer{
	
	protected static final long serialVersionUID = 1L;
	protected JTextArea textArea;
	
	protected JPanel mainpanel;
	protected JPanel mailpanel;
	protected JPanel buttonpanel;
	protected JPanel table_panel;
	
	protected JTextField subject;
	
	protected LinkedList<Group> group_list;
    
    protected DesignGridLayout layout;
	
	@SuppressWarnings("rawtypes")
	protected JComboBox combobox;
	
	// JButton
	protected JButton next_button;
	protected JButton back_button;
	
	protected MView allmailview;
	
	protected DefaultTableModel model;
	protected JTable table;
	protected JScrollPane pane;
	
	protected AssociationDataTransfer association_data_transfer;
	protected KassenBuch money_book;
	
	@SuppressWarnings("rawtypes")
	protected JComboBox by;
	
	protected JScrollPane scrollPane;
	
	public MassMailView(){}
	
	/**
	 * constructor creates a mass mail view
	 * 
	 * @param amailview
	 * @param moneyBook
	 * @param adatatrans
	 */
	public MassMailView(MView amailview){
		
		setLayout(new BorderLayout());
		setOpaque(false);
		
		this.association_data_transfer = AssociationDataTransfer.getInstance();
		this.money_book = KassenBuch.getInstance("jdbc:derby:.//derbydbs;create=true");
		this.association_data_transfer.addObserver(this);
		this.allmailview = amailview;
		
		createView();
		
		layout.row().grid(new JLabel("Briefarchiv:"), 3).add(table_panel);
	    layout.emptyRow();
		layout.row().grid(new JLabel("Betreff: "), 3).add(subject);
		layout.emptyRow();
		layout.row().grid(new JLabel("Brief: "), 3).add(scrollPane);
		layout.emptyRow();
		layout.row().grid(new JLabel("erstellt vom: "), 1).add(by);
		
		next_button = new ButtonDesign("images/create_pdf_icon.png");
		next_button.addActionListener(new MassMailActionListener(allmailview, this));
		back_button = new ButtonDesign("images/back_icon.png");
		back_button.addActionListener(new MassMailActionListener(allmailview, this));
		
		GridBagConstraints b = new GridBagConstraints();
		b.fill=GridBagConstraints.HORIZONTAL;
		
		b.insets =new Insets(5,3,2,2);
		
		b.gridx=0;
		b.gridy=0;
		buttonpanel.add(back_button, b);
		
		b.gridx=1;
		b.gridy=0;
		buttonpanel.add(next_button, b);
		
		b.gridx=2;
		b.gridy=0;
		buttonpanel.add(new JLabel("Serienbrief an: "), b);
		
		b.gridx=3;
		b.gridy=0;
		buttonpanel.add(combobox, b);
		
		createButView();
	}
	
	public void createButView(){
		GridBagConstraints c = new GridBagConstraints();
		c.fill=GridBagConstraints.HORIZONTAL;
		
		c.insets =new Insets(2,11,2,2);
		
		c.gridx=0;
		c.gridy=0;
		mainpanel.add(mailpanel, c);
		
		c.gridx=0;
		c.gridy=1;
		mainpanel.add(buttonpanel, c);
		
		add(mainpanel, BorderLayout.CENTER);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void createView(){
		table_panel = new JPanel();
		table_panel.setLayout(new BorderLayout());
		table_panel.setOpaque(false);
		table_panel.setSize(200,600);
		
		model = new MailTableModel(association_data_transfer);
		this.setTable();
		
	    table.getColumnModel().getColumn(0).setPreferredWidth(70); 
		table.getColumnModel().getColumn(1).setPreferredWidth(120); 
		table.getColumnModel().getColumn(2).setPreferredWidth(120); 
		table.getColumnModel().getColumn(3).setPreferredWidth(120); 
		table.getTableHeader().setResizingAllowed(false);
		table.getTableHeader().setReorderingAllowed(false);
		
	    table.setPreferredScrollableViewportSize(new Dimension(800, 80));
	    // only one cell to select
	    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    
	    table.addMouseListener(new MailMouseListener(this, table, money_book, association_data_transfer));
	    
		pane = new JScrollPane(table);		
		
		table_panel.add(pane, BorderLayout.CENTER);
		
		mainpanel = new JPanel();
		mainpanel.setLayout(new GridBagLayout());
		mainpanel.setOpaque(false);
		
		mailpanel = new JPanel();
		mailpanel.setLayout(new BorderLayout());
		mailpanel.setOpaque(false);
		
		buttonpanel = new JPanel();
		buttonpanel.setOpaque(false);
		buttonpanel.setLayout(new GridBagLayout());
		
		layout = new DesignGridLayout(mailpanel);
		
	    textArea = new JTextArea(13, 50);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);

	    scrollPane = new JScrollPane(textArea);
	    
	    subject = new JTextField(25);
	    group_list = association_data_transfer.getGroup();
	    
		String[] mgroup = new String[group_list.size()+3];
		
		mgroup[0] = "alle Mitglieder";
		mgroup[1] = "offene Beiträge";
		mgroup[2] = "bekannte Spender";
		
		for(int j = 0; j < group_list.size(); j++){
			mgroup[j+3] = group_list.get(j).getGroup();
		}
		
		combobox = new JComboBox(mgroup);
	    
		String[] by_array = new String[3];
		
		by_array[0] = "";
		by_array[1] = "Vorstand";
		by_array[2] = "Kassenwart";
		
		by = new JComboBox(by_array);		
	}
	
	/**
	 * update methode
	 */
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		table.setModel(new MailTableModel(association_data_transfer));
		
	}
	
	@SuppressWarnings("serial")
	public void setTable(){
		table = new JTable(model){ 
			public boolean isCellEditable(int rowIndex, int vColIndex){ 
				return false; 
			} 
		};
	}
	
	/**
	 * methode return save button
	 * 
	 * @return save_button
	 */
	public JButton getSaveButton(){
		return next_button;
	}
	
	/**
	 * methode return back button
	 * 
	 * @return backbutton
	 */
	public JButton getBackButton(){
		return back_button;
	}
	
	/**
	 * methode returns member groups.
	 * 
	 * @return member groups
	 */
	public int getMemberGroup(){
		return combobox.getSelectedIndex();
	}
	
	/**
	 * methode returns subject.
	 * 
	 * @return subject
	 */
	public JTextField getSubject(){
		return subject;
	}
	
	/**
	 *  methode set subject
	 * 
	 * @param sub
	 */
	public void setSubject(String sub){
		subject.setText(sub);
	}
	
	/**
	 * methode returns textArea.
	 * 
	 * @return textArea
	 */
	public JTextArea getMail(){
		return textArea;
	}
	
	/**
	 * methode set textarea
	 * 
	 * @param mail
	 */
	public void setMail(String mail){
		textArea.setText(mail);
	}
	
	/**
	 * methode returns a number of selected author
	 * 
	 * @return number
	 */
	public int getAuthor(){
		return by.getSelectedIndex();
	}
}
