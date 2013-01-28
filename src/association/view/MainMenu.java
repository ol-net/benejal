
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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import moneybook.model.KassenBuch;

import association.model.AssociationDataTransfer;

/**
 * class for the main frame.
 * 
 * @author Leonid Oldenburger
 */
public class MainMenu extends JFrame implements Observer{
	
	private static final long serialVersionUID = 1L;

	private JPanel mainpanel;
	
	private MainMenuPanel mainmenupanel;
	
	// window-size
	private int height;
	private int width;
	private Font style_font;
	
	private JTabbedPane tabbedpane;
	
	private AssociationDataTransfer association_data_transfer;
	private KassenBuch money_book;
	
	/**
	 * constructor creates a new window.
	 * 
	 * @param width
	 * @param height
	 * @param styleFont
	 */
	public MainMenu(int w, int h, Font styleFont, KassenBuch moneyBook, AssociationDataTransfer adatatrans) {
		
		this.height = 560;
		this.width = 800;
		this.style_font = styleFont;
		this.association_data_transfer = adatatrans;
		this.association_data_transfer.addObserver(this);
		this.money_book = moneyBook; 
		this.money_book.addObserver(this);
		
		setLayout(new BorderLayout());
		setMinimumSize(new Dimension(width, height)); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mainpanel = new BackgroundPanel();
		mainpanel.setLayout(new BorderLayout());
		mainpanel.setOpaque(false);

		mainmenupanel = new MainMenuPanel(this, money_book, association_data_transfer);

		setJMenuBar(new VereinsMenueBar(this, mainmenupanel, money_book, association_data_transfer));
		
		mainpanel.add(mainmenupanel, BorderLayout.CENTER);
		add(mainpanel, BorderLayout.CENTER);
        
		// Centering a Window
		setResizable(true);
		setSize(width, height);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	/**
	 * methode to close the window
	 */
	public void schliessen() {
		dispose();
		System.exit(0);
	}
	
	/**
	 * methode to add Panel to mainpanel
	 */
	public void addTabbedPanel(JTabbedPane pane){
		tabbedpane = pane;
		mainpanel.add(tabbedpane, BorderLayout.CENTER);
		setVisible(true);
	}
	
	/**
	 * methode to add Panel to mainpanel
	 */
	public void addPanel(MainMenuPanel panel){
		mainmenupanel = panel;
		mainpanel.add(mainmenupanel, BorderLayout.CENTER);
		setVisible(true);
	}
	
	/**
	 * methode remove mainmenuepanel from container
	 */
	public void removePanel(){
		mainpanel.remove(mainmenupanel);
	}
	
	/**
	 * methode remove mainmenuepanel from container
	 */
	public void removePane(){
		mainpanel.remove(tabbedpane);
	}
	
	/**
	 * methode to set the JFrame visiable true
	 */
	public void setVisibleTrue(){
		setVisible(true);
	}
	
	/**
	 * 
	 * @return fontstyle
	 */
	public Font getFontStyle(){
		return style_font;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub		
	}
}
