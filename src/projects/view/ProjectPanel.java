
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
package projects.view;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import moneybook.controller.MoneyBookFrameDisposeListener;
import moneybook.model.KassenBuch;
import net.java.dev.designgridlayout.DesignGridLayout;
import projects.controller.CreateProjectListener;
import projects.controller.ProjectMouseListener;
import projects.model.ProjectTableModel;
import association.view.BackgroundPanel;
import association.view.ButtonDesign;

/**
 * Repräsentiert die GUI des Projekte-Moduls
 * @author Artem Petrov
 *
 */
@SuppressWarnings("serial")
public class ProjectPanel extends BackgroundPanel implements Observer {

	private String title = "Projekte";
	private String newTitle =  "Neues Projekt erstellen";
	private String project = "Unterprojekt von";
	private String name = "Name";
	
	private JTable projekteTable;
	
	private JScrollPane tablePane;
	private JPanel newProjectPanel;
	
	private JButton createBtn;
	private JButton mainMenuBtn;
	
	private JTextField nameFeld;
	private JLabel nameLbl;
	private JLabel projectLbl;
	private JComboBox projectBox;
	
	private KassenBuch kassenbuch;
	
	/**
	 * Erzeugt ein Panel
	 * @param kassenbuch Kassenbcuh
	 */
	public ProjectPanel(KassenBuch kassenbuch) {
		
		this.kassenbuch = kassenbuch;
		
		kassenbuch.addObserver(this);
		
		this.setBorder(new TitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY ), title));
		
		DesignGridLayout thisLayout = new DesignGridLayout(this);
		
		projekteTable = new JTable(new ProjectTableModel(kassenbuch));
		projekteTable.getColumnModel().getColumn(0).setPreferredWidth(200);
		
		projekteTable.addMouseListener(new ProjectMouseListener(kassenbuch));
		
		tablePane = new JScrollPane(projekteTable);
		
		tablePane.setPreferredSize(new Dimension(600, 200));
		
		createBtn = new ButtonDesign("images/save_icon.png");
		createBtn.addActionListener(new CreateProjectListener(kassenbuch, this));
		
		mainMenuBtn = new ButtonDesign("images/mainmenu_icon.png");
		mainMenuBtn.addActionListener(new MoneyBookFrameDisposeListener());
		
		nameLbl = new JLabel(name);
		
		nameFeld = new JTextField(45);
		
		projectLbl = new JLabel(project);
		
		projectBox = new JComboBox(kassenbuch.getProjectNames(2, true));
		
		newProjectPanel = new JPanel();
		newProjectPanel.setOpaque(false);
		
		newProjectPanel.setBorder(new TitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY ), newTitle));
		
		DesignGridLayout bottLayout = new DesignGridLayout(newProjectPanel);
		bottLayout.row().grid(nameLbl).add(nameFeld);
		bottLayout.row().grid(projectLbl).add(projectBox);
		bottLayout.row().center().add(createBtn);
		
		thisLayout.row().grid().add(tablePane);
		thisLayout.row().grid().add(newProjectPanel);
		
		thisLayout.row().center().add(mainMenuBtn);
	}

	/**
	 * Aktualisiert das Panel
	 */
	@Override
	public void update(Observable o, Object arg) {
		
		projekteTable.setModel(new ProjectTableModel(kassenbuch));
		projekteTable.getColumnModel().getColumn(0).setPreferredWidth(200);
		
		projectBox.removeAllItems();
		
		Vector<String> v = kassenbuch.getProjectNames(2, true);
		Iterator<String> it = v.iterator();
		
		while(it.hasNext()) {
		
			projectBox.addItem(it.next());
		
		}
		
	}
	
	/**
	 * Liefert den Name des Vaterprojekts zurück
	 * @return Name des Vaterprojekts
	 */
	public String getParentName() {
	
		String name = (String) projectBox.getSelectedItem();
		
		if (name.equals("-"))
			name = null;
		
		return name;

	}
	
	/**
	 * Liefert den Name des neuen Projekts zurück
	 * @return Name des neuen Projekts
	 */
	public String getProjectName() {
		
		return (String) nameFeld.getText();
	
	}

	
}
