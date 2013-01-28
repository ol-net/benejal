
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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import association.model.AssociationDataTransfer;
import association.model.Group;

import mail.controller.ReceiptActionListener;
import mail.controller.ReceiptMouseListener;

import mail.model.ReceiptTableModel;
import moneybook.model.KassenBuch;

import net.java.dev.designgridlayout.DesignGridLayout;

import association.view.ButtonDesign;

/**
 * GUI class represents receipts view
 * 
 * @author Leonid Oldenburger
 */
public class MassDonationReceipt extends JPanel implements Observer{
	
	protected static final long serialVersionUID = 1L;
	protected JTextArea textArea;
	
	protected JTextField textfield1;
	protected JTextField textfield2;
	protected JTextField textfield3;
	
	protected JPanel mainpanel;
	protected JPanel mailpanel;
	protected JPanel buttonpanel;
	protected JPanel table_panel;
	protected JPanel panel1;
	protected JPanel panel2;
	protected JPanel panel3;
	protected JPanel panel4;
	protected JPanel panel5;
	protected JPanel textpanel1;
	
	protected JRadioButton yes_radio_button;
	protected JRadioButton no_radio_button;
	
	protected JRadioButton radio_button_1;
	protected JRadioButton radio_button_2;
	protected JRadioButton radio_button_3;
	
	protected JCheckBox checkbox3;
	
	protected ButtonGroup checkBoxGroup1;
	protected ButtonGroup checkBoxGroup2;
	protected ButtonGroup checkBoxGroup3;
	
	protected LinkedList<Group> group_list;
    
	protected DesignGridLayout layout;
	
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
	
	/**
	 * constructor creates receipt view
	 * 
	 * @param amailview
	 * @param moneyBook
	 * @param adatatrans
	 */
	public MassDonationReceipt(MView amailview){
		
		setLayout(new BorderLayout());
		setOpaque(false);
		
		this.association_data_transfer = AssociationDataTransfer.getInstance();
		this.association_data_transfer.addObserver(this);
		this.money_book = KassenBuch.getInstance("jdbc:derby:.//derbydbs;create=true");
		this.allmailview = amailview;
		
		createView();

		next_button = new ButtonDesign("images/create_pdf_icon.png");
		next_button.addActionListener(new ReceiptActionListener(allmailview, this, money_book, association_data_transfer));
		back_button = new ButtonDesign("images/back_icon.png");
		back_button.addActionListener(new ReceiptActionListener(allmailview, this, money_book, association_data_transfer));
		
		GridBagConstraints b = new GridBagConstraints();
		b.fill=GridBagConstraints.HORIZONTAL;
		
		b.insets =new Insets(2,3,2,2);
		
		b.gridx=0;
		b.gridy=0;
		buttonpanel.add(back_button, b);
		
		b.gridx=1;
		b.gridy=0;
		buttonpanel.add(next_button, b);
		
		b.gridx=2;
		b.gridy=0;
		buttonpanel.add(new JLabel("Bescheinigung an: "), b);
		
		b.gridx=3;
		b.gridy=0;
		buttonpanel.add(combobox, b);
		
		createButPanel();
	}
	
	public MassDonationReceipt(){}
	
	public void createButPanel(){
		
		GridBagConstraints x = new GridBagConstraints();
		x.fill=GridBagConstraints.HORIZONTAL;
		
		x.insets =new Insets(2,11,2,2);
		
		x.gridx=0;
		x.gridy=0;
		mainpanel.add(mailpanel, x);
		
		x.gridx=0;
		x.gridy=2;
		mainpanel.add(panel2, x);
		
		x.gridx=0;
		x.gridy=3;
		mainpanel.add(buttonpanel, x);
		
		add(mainpanel, BorderLayout.CENTER);
	}
	
	public void createView(){
		table_panel = new JPanel();
		table_panel.setLayout(new BorderLayout());
		table_panel.setOpaque(false);
		table_panel.setSize(400,600);
		
		model = new ReceiptTableModel(association_data_transfer);
		setTable();
		
	    table.getColumnModel().getColumn(0).setPreferredWidth(80); 
		table.getColumnModel().getColumn(1).setPreferredWidth(80); 
		table.getColumnModel().getColumn(2).setPreferredWidth(120); 
		table.getTableHeader().setResizingAllowed(false);
		table.getTableHeader().setReorderingAllowed(false);
		
	    table.setPreferredScrollableViewportSize(new Dimension(800, 47));
	    // only one cell to select
	    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    
	    table.addMouseListener(new ReceiptMouseListener(this, table, money_book, association_data_transfer));
	    
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
		
	    textArea = new JTextArea(2, 4);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		
		panel1 = new JPanel();
		panel1.setLayout(new FlowLayout());
		panel1.setOpaque(false);
		
	    checkBoxGroup1 = new ButtonGroup();
		
	    yes_radio_button = new JRadioButton("Ja", false);
	    yes_radio_button.setOpaque(false);
	    no_radio_button = new JRadioButton("Nein", true);
	    no_radio_button.setOpaque(false);

		checkBoxGroup1.add(yes_radio_button);
		checkBoxGroup1.add(no_radio_button);
		
		panel1.add(new JLabel("Es handelt sich um den Verzicht auf Erstattung von Aufwendungen    "));
		panel1.add(yes_radio_button);
		panel1.add(no_radio_button);
		
		panel2 = new JPanel();
		panel2.setLayout(new GridBagLayout());
		panel2.setOpaque(false);
		
		checkBoxGroup2 = new ButtonGroup();
		
		radio_button_1 = new JRadioButton();
		radio_button_1.setOpaque(false);
		
		radio_button_2 = new JRadioButton();
		radio_button_2.setOpaque(false);
		
		checkBoxGroup2.add(radio_button_1);
		checkBoxGroup2.add(radio_button_2);
		
		panel3 = new JPanel();
		panel3.setLayout(new FlowLayout());
		panel3.setOpaque(false);
		
		//String default_text = "(Angabe des begünstigten Zwecks/der begünstigten Zwecke)";
		String default_text = association_data_transfer.getReceipt().get(1).getObject1();
		
		String text5 = "Wir sind wegen Förderung";
		textfield1 = new JTextField(45);
		textfield1.setText(default_text);
		panel3.add(new JLabel(text5));
		panel3.add(textfield1);
		
        SimpleDateFormat dateformatMMDDYYYY = new SimpleDateFormat("dd.MM.yyyy");
        StringBuilder vMMDDYYYY = new StringBuilder(dateformatMMDDYYYY.format(association_data_transfer.getAssociation().getFinacneOffice().getV()));
        StringBuilder aMMDDYYYY = new StringBuilder(dateformatMMDDYYYY.format(association_data_transfer.getAssociation().getFinacneOffice().getA()));

		String text6 = "  nach dem letzten uns zugegangenen Freistellungsbescheid bzw. nach der Anlage zum Körperschaftsteuerbescheid ";
		String text7 = "  des Finanzamt " + association_data_transfer.getAssociation().getFinacneOffice().getAdress().getCity() + ", StNr. " + association_data_transfer.getAssociation().getFinacneOffice().getTaxNumber()+ ", vom "+vMMDDYYYY+" nach § 5 Abs. 1 Nr. 9 des Körperschaftsteuergesetzes von der";
		String text8 = "  Körperschaftsteuer und nach § 3 Nr.6 des Gewerbesteuergesetzes von der Gewerbesteuer befreit.";
		
		panel4 = new JPanel();
		panel4.setLayout(new FlowLayout());
		panel4.setOpaque(false);
		
		textfield2 = new JTextField(45);
		textfield2.setText(default_text);
		panel4.add(new JLabel(text5));
		panel4.add(textfield2);
		
		String text11 = "  durch vorläufige Bescheinigung des Finanzamt "+ association_data_transfer.getAssociation().getFinacneOffice().getAdress().getCity() + ", StNr. " + association_data_transfer.getAssociation().getFinacneOffice().getTaxNumber() + ", vom " +vMMDDYYYY;
		String text12 = "  ab "+aMMDDYYYY+" als steuerbegünstigten Zwecken dienend anerkannt.";
		
		
		panel5 = new JPanel();
		panel5.setLayout(new GridBagLayout());
		panel5.setBorder(new TitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY )));
		panel5.setOpaque(false);
		
		String text13 = "  Nur für steuerbegünstigte Einrichtungen, bei denen die Mitgliedsbeiträge steuerlich nicht abziehbar sind: ";
		String text14 = "  Es wird bestätigt, dass es sich nicht um einen Mitgliedsbeitrag i.S.v § 10b Abs. 1 Satz 2 ";
		String text15 = "  Einkommensteuergesetzes handlet.";
		
		textfield3 = new JTextField(15);;
		textfield3.setText(default_text);
		
		textpanel1 = new JPanel();
		textpanel1.setLayout(new FlowLayout());
		textpanel1.setOpaque(false);
		
		textpanel1.add(new JLabel("Es wird bestätigt, dass die Zuwendung nur zur Förderung"));
		textpanel1.add(textfield3);
		textpanel1.add(new JLabel("verwendet wird."));
		
		checkBoxGroup3 = new ButtonGroup();
		
		radio_button_3 = new JRadioButton();
		radio_button_3.setOpaque(false);
		
		checkbox3 = new JCheckBox();
		checkbox3.setOpaque(false);
		
		checkBoxGroup3.add(radio_button_3);
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill=GridBagConstraints.HORIZONTAL;
		
		c.insets =new Insets(2,2,2,2);
		
		c.gridx=1;
		c.gridy=0;
		panel5.add(textpanel1, c);
		
		c.gridx=1;
		c.gridy=1;
		panel5.add(new JLabel(text13), c);
		
		c.gridx=0;
		c.gridy=2;
		panel5.add(checkbox3, c);
		
		c.gridx=1;
		c.gridy=2;
		panel5.add(new JLabel(text14), c);
		
		c.gridx=1;
		c.gridy=3;
		panel5.add(new JLabel(text15), c);
		
		GridBagConstraints a = new GridBagConstraints();
		a.fill=GridBagConstraints.HORIZONTAL;
		
		a.insets =new Insets(2,2,2,2);
		
		a.gridx=1;
		a.gridy=0;
		panel2.add(panel1, a);
		
		a.gridx=0;
		a.gridy=1;
		panel2.add(radio_button_1, a);
		
		a.gridx=1;
		a.gridy=1;
		panel2.add(panel3, a);
		
		a.gridx=1;
		a.gridy=2;
		panel2.add(new JLabel(text6), a);
		
		a.gridx=1;
		a.gridy=3;
		panel2.add(new JLabel(text7), a);
		
		a.gridx=1;
		a.gridy=4;
		panel2.add(new JLabel(text8), a);
		
		a.gridx=0;
		a.gridy=6;
		panel2.add(radio_button_2, a);
		
		a.gridx=1;
		a.gridy=6;
		panel2.add(panel4, a);
		
		a.gridx=1;
		a.gridy=7;
		panel2.add(new JLabel(text11), a);
		
		a.gridx=1;
		a.gridy=8;
		panel2.add(new JLabel(text12), a);
		
		a.gridx=1;
		a.gridy=9;
		panel2.add(panel5, a);
	    
	    group_list = association_data_transfer.getGroup();
	    
		String[] mgroup = new String[group_list.size()+2];
		
		mgroup[0] = "Alle Mitglieder";
		mgroup[1] = "Bekannte Spender";
		
		for(int j = 0; j < group_list.size(); j++){
			mgroup[j+2] = group_list.get(j).getGroup();
		}
		
		combobox = new JComboBox(mgroup);
	    
		layout.row().grid(new JLabel("Bescheinigungarchiv:"), 3).add(table_panel);
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		table.setModel(new ReceiptTableModel(association_data_transfer));
		setText1(association_data_transfer.getReceipt().get(1).getObject1());
		setText2(association_data_transfer.getReceipt().get(1).getObject1());
		setText3(association_data_transfer.getReceipt().get(1).getObject1());
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
	 * method returns save button
	 * 
	 * @return save_button
	 */
	public JButton getSaveButton(){
		return next_button;
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
	 * method returns member groups.
	 * 
	 * @return member groups
	 */
	public int getMemberGroup(){
		return combobox.getSelectedIndex();
	}
	
	/**
	 * method returns textfield1.
	 * 
	 * @return textfield1
	 */
	public JTextField getText1(){
		return textfield1;
	}
	
	/**
	 * method sets textfield1
	 * 
	 * @param text
	 */
	public void setText1(String text){
		textfield1.setText(text);
	}
	
	/**
	 * method returns textfield2.
	 * 
	 * @return textfield2
	 */
	public JTextField getText2(){
		return textfield2;
	}
	
	/**
	 * method sets textfield2
	 * 
	 * @param text
	 */
	public void setText2(String text){
		textfield2.setText(text);
	}
	
	/**
	 * method returns textfield3.
	 * 
	 * @return textfield3
	 */
	public JTextField getText3(){
		return textfield3;
	}
	
	/**
	 * method sets textfield3
	 * 
	 * @param text
	 */
	public void setText3(String text){
		textfield3.setText(text);
	}
	
	/**
	 * method returns radio_button_1.
	 * 
	 * @return radion_button_1
	 */
	public JRadioButton getRadioButton1(){
		return radio_button_1;
	}
	
	/**
	 * set radio button 1
	 * 
	 * @param value
	 */
	public void set1(boolean value){
		radio_button_1.setSelected(value);
	}
	
	/**
	 * method returns radio_button_2.
	 * 
	 * @return radion_button_2
	 */
	public JRadioButton getRadioButton2(){
		return radio_button_2;
	}
	
	/**
	 * set radio button 2
	 * 
	 * @param value
	 */
	public void set2(boolean value){
		radio_button_2.setSelected(value);
	}
	
	/**
	 * method returns radio_button_3.
	 * 
	 * @return radion_button_3
	 */
	public JRadioButton getRadioButton3(){
		return radio_button_3;
	}
	
	/**
	 * set radio button 3
	 * 
	 * @param value
	 */
	public void set3(boolean value){
		radio_button_3.setSelected(value);
	}
	
	/**
	 * set checkbox3
	 * 
	 * @param value
	 */
	public void setCheckbox3(boolean value){
		checkbox3.setSelected(value);
	}
	
	/**
	 * method returns checkbox3.
	 * 
	 * @return checkbox3
	 */
	public JCheckBox getCheckbox3(){
		return checkbox3;
	}
	
	/**
	 * method returns yes_radio_button.
	 * 
	 * @return yes_radio_button
	 */
	public JRadioButton getRadioButtonYes(){
		return yes_radio_button;
	}
	
	/**
	 * set button yes
	 * 
	 * @param value
	 */
	public void setYesButton(boolean value){
		yes_radio_button.setSelected(value);
	}
	
	/**
	 * method returns no_radio_button.
	 * 
	 * @return no_radio_button
	 */
	public JRadioButton getRadioButtonNo(){
		return no_radio_button;
	}
	
	/**
	 * set button no
	 * 
	 * @param value
	 */
	public void setNoButton(boolean value){
		no_radio_button.setSelected(value);
	}
	
	/**
	 * method returns checkBoxGroup1.
	 * 
	 * @return checkBoxGroup1
	 */
	public ButtonGroup getButtonGroup1(){
		return checkBoxGroup1;
	}
	
	/**
	 * method returns checkBoxGroup2.
	 * 
	 * @return checkBoxGroup2
	 */
	public ButtonGroup getButtonGroup2(){
		return checkBoxGroup2;
	}
	
	/**
	 * method returns checkBoxGroup3.
	 * 
	 * @return checkBoxGroup3
	 */
	public ButtonGroup getButtonGroup3(){
		return checkBoxGroup3;
	}
}
