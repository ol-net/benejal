
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
package register.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

import moneybook.model.KassenBuch;

import association.model.Association;
import association.model.AssociationDataTransfer;
import association.model.Contribution;
import association.model.DoubleDocument;
import association.model.Group;
import association.view.BackgroundPanel;
import association.view.ButtonDesign;

import register.controller.AssociationGroupActionListener;
import register.model.RegisterAssociationGroupTable;

/**
 * gui class for the group panel
 * 
 * @author Leonid Oldenburger
 */
public class RegisterAssociationGroup extends BackgroundPanel implements Observer{
	
	private static final long serialVersionUID = 1L;
	
	// languagepack
	protected String title = "Registrierung - Vereinsgruppen - Schritt 4 von 6";
	protected String group = "Gruppe";
	protected String premium = "Beitrag";
	protected String currency = "Währung";
	protected String membergroup = "Mitgliedergruppe";
	
	//JPanels
	protected JPanel button_panel;
	protected JPanel table_panel;
	protected JPanel main_panel;
	
	// Button
	protected JButton next_button;
	protected JButton back_button;
	
	// window-size
	protected Font style_font;
	
	protected DefaultTableModel model;
	protected JTable group_table;
	protected JScrollPane pane;
	protected RegisterStartWindow mainframe;
	
	protected Association association_object;
	protected Group association_group;
	protected Contribution association_premium;
	protected LinkedList<Group> association_group_list;
	
	protected Group association_group_0;
	protected Contribution association_premium_0;
	protected Group association_group_1;
	protected Contribution association_premium_1;
	protected Group association_group_2;
	protected Contribution association_premium_2;
	protected Group association_group_3;
	protected Contribution association_premium_3;
	protected Group association_group_4;
	protected Contribution association_premium_4;
	protected Group association_group_5;
	protected Contribution association_premium_5;
	
	protected String column[] = {group, premium, currency};
	protected String row[][]= {{"Reguläres Mitglied ","0,00","Euro"}, {"Ermäßigtes Mitglied","0,00","Euro"}, {"Ehrenmitglied","0,00","Euro"}, {"","0,00","Euro"}, {"","0,00","Euro"}, {"","0,00","Euro"}};
	
	protected AssociationDataTransfer association_data_transfer;
	protected KassenBuch money_book;
	
	protected int[] width_array = {100, 100};
	protected int[] length = {4, 60};
	
	/**
	 * constructor
	 */
	public RegisterAssociationGroup(){
		this.association_object = null;
		this.style_font = null;
		this.mainframe = null;
		this.association_data_transfer = null;
	}
	
	/**
	 * constructor with some parameters
	 * 
	 * @param fontStyle
	 * @param frame
	 * @param associationObject
	 * @param moneyBook
	 * @param adatatrans
	 */
	public RegisterAssociationGroup(Font fontStyle, RegisterStartWindow frame, Association associationObject, KassenBuch moneyBook, AssociationDataTransfer adatatrans){
		
		this.association_object = associationObject;
		this.style_font = fontStyle;
		this.mainframe = frame;
		this.mainframe.setTitle(title);

		this.association_data_transfer = adatatrans;
		this.association_data_transfer.addObserver(this);
		this.money_book = moneyBook;
		
		buildPanel();
		
		// Next-Button
		next_button = new ButtonDesign("images/next_icon.png");
		back_button = new ButtonDesign("images/back_icon.png");
		next_button.addActionListener(new AssociationGroupActionListener(this, mainframe, association_object, money_book, association_data_transfer));
		// Back-Button
		back_button.addActionListener(new AssociationGroupActionListener(this, mainframe, association_object, money_book, association_data_transfer));

		GridBagConstraints b = new GridBagConstraints();
		b.fill=GridBagConstraints.HORIZONTAL;

		b.insets =new Insets(196,2,2,2);
		
		b.gridx=0;
		b.gridy=0;
		button_panel.add(back_button, b);

		b.gridx=1;
		b.gridy=0;
		button_panel.add(next_button, b);
	}
	
	/**
	 * methode build group panel
	 */
	public void buildPanel(){
		
		setLayout(new GridBagLayout());
		setOpaque(false);
		
		table_panel = new JPanel();
		table_panel.setLayout(new FlowLayout());
		table_panel.setOpaque(false);
		table_panel.setSize(100,150);
		
		// Button Panel
		button_panel = new JPanel();
		button_panel.setLayout(new GridBagLayout());
		button_panel.setOpaque(false);

		model = new RegisterAssociationGroupTable(association_data_transfer, association_object){
		private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int col) {
				if(col == 2){ 
					return false;
				}else{
					return true;
				}
			}
		};
		
		group_table = new JTable(model);
	    group_table.setPreferredScrollableViewportSize(new Dimension(400, 96));
	    
	    group_table.getColumnModel().getColumn(0).setPreferredWidth(120); 
		group_table.getColumnModel().getColumn(1).setPreferredWidth(120); 
		group_table.getColumnModel().getColumn(2).setPreferredWidth(120); 
		group_table.getTableHeader().setResizingAllowed(false);
		group_table.getTableHeader().setReorderingAllowed(false);
		
        initColumn(group_table, width_array, length, column.length);
        
	    pane = new JScrollPane(group_table);

		table_panel.add(pane);
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill=GridBagConstraints.HORIZONTAL;
		
		c.insets =new Insets(40,2,2,2);
		
		c.gridx=0;
		c.gridy=0;
		add(table_panel, c);
		
		c.gridx=0;
		c.gridy=1;
		add(button_panel, c);
		
	}
	
	/**
	 * methode returns back button
	 * 
	 * @return backbutton
	 */
	public JButton getBackButton(){
		return back_button;
	}
	
	/**
	 * methode returns next button
	 * 
	 * @return nextbutton
	 */
	public JButton getNextButton(){
		return next_button;
	}
	
	/**
	 * methode returns fontstyle
	 * 
	 * @return fontstyle
	 */
	public Font getFontStyle(){
		return style_font;
	}
	
	/**
	 * methode returns a value of a table cell.
	 * 
	 * @param table
	 * @param row_index
	 * @param col_index
	 * @return
	 */
	public Object getData(int row_index, int col_index){
		 //nach klick auf Button auch den Wert für das Fenster 
		 //übernehmen welches gerade editiert wird
		TableCellEditor tce = null;
		  if (group_table.isEditing()) {
		    tce = group_table.getCellEditor();
		  }
		  if (tce != null) {
		    tce.stopCellEditing();
		  }
		return group_table.getModel().getValueAt(row_index, col_index);
	}  
	
	/**
	 * methode to handle table values
	 * 
	 * @param table
	 * @param _width_array
	 * @param _char_length
	 * @param lang
	 */
	public void initColumn(final JTable table, int[] _width_array, int[] _char_length, int lang) {
	        TableColumn column = null;

	        // hier wird setDocument() aufgerufen notwendig wegen der Prüfungen während der Eingabe
	        for (int i = 0; i < 2; i++) {
	             
	        	final JFormattedTextField tf = new JFormattedTextField();
	            //tf.setDocument(new LimitedDecimalDocument(_char_length[i]));
	    		tf.setDocument(new DoubleDocument(tf));

	            tf.addFocusListener(new FocusListener(){
	            
	            public void focusGained(FocusEvent arg0) {
	            	 tf.setBackground(Color.WHITE);
	            	 tf.setSelectionStart(1);
	            	 tf.setSelectionEnd(tf.getText().length());
	            }
	             
	            public void focusLost(FocusEvent arg0) {
	             
	            	tf.setBackground(Color.WHITE);
	                //Feld verlassen
	            }
	        });	
	        
	        //Spalten und Zeilen 	
	        for(int j = 0; j <lang; j++){
	        	
	        		if (j != lang-3){
	        			column = table.getColumnModel().getColumn(j);
	        			column.setCellEditor(new DefaultCellEditor(tf));
	        			column.setPreferredWidth(_width_array[i]);
	        		}
	        		else{
	        			//Operator Spalte nicht editieren nur Größe festlegen
	        			column = table.getColumnModel().getColumn(j);
	        			column.setPreferredWidth(_width_array[i]);
	        		}
	        	}
	        }
	 }
	
	/**
	 * update observable 
	 */
	public void update(Observable arg0, Object arg1) {
		
		// TODO Auto-generated method stub
		model = new RegisterAssociationGroupTable(association_data_transfer, association_object){
			private static final long serialVersionUID = 1L;

				public boolean isCellEditable(int row, int col) {
					if(col == 2){ 
						return false;
					}else{
						return true;
					}
				}
			};
		group_table.setModel(new RegisterAssociationGroupTable(association_data_transfer, association_object));

		initColumn(group_table, width_array, length, column.length);
	}
}
