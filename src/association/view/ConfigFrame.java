
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

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

import association.view.BackgroundPanel;
import association.view.MainMenuPanel;

/**
 * class for the main frame.
 * 
 * @author Leonid Oldenburger
 */
public class ConfigFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;

	private JPanel mainpanel;
	
	private MainMenuPanel mainmenupanel;
	
	// window-size
	private int height;
	private int width;
	private Font style_font;
	
	private JTabbedPane tabbedpane;
	
	/**
	 * constructor creates a new window.
	 * 
	 * @param styleFont
	 */
	public ConfigFrame() {
		
		this.height = 560;
		this.width = 800;
		this.style_font = null;
				
		setLayout(new BorderLayout());
		setMinimumSize(new Dimension(width, height)); 

		mainpanel = new BackgroundPanel();
		mainpanel.setLayout(new BorderLayout());
		mainpanel.setOpaque(false);

		add(mainpanel, BorderLayout.CENTER);
        
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		pack();
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
}
