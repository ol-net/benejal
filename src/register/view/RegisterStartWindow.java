
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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;

import moneybook.model.KassenBuch;

import association.model.Association;
import association.model.AssociationDataTransfer;
import association.view.BackgroundPanel;

/**
 * gui class represent the frame for registration
 * 
 * @author Leonid Oldenburger
 */
public class RegisterStartWindow extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	// window-size
	private int height;
	private int width;
	private Font style_font;
	private JPanel mainpanel;
	private JPanel associationdata;
	private Association association;
	private KassenBuch money_book;
	
	private AssociationDataTransfer association_data_transfer;
	
	/**
	 * constructor
	 * 
	 * @param height
	 * @param width
	 * @param styleFont
	 * @param moneyBook
	 * @param adatatrans
	 */
	public RegisterStartWindow(int height, int width, Font styleFont, KassenBuch moneyBook, AssociationDataTransfer adatatrans){

		this.association = null;
		this.height = height;
		this.width = width;
		this.style_font = styleFont;
		this.association_data_transfer = adatatrans;
		this.money_book = moneyBook;

		//frame.setTitle(title);
		setLayout(new BorderLayout());
		setMinimumSize(new Dimension(width, height)); 
		setContentPane(new BackgroundPanel());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mainpanel = new JPanel();
		mainpanel.setLayout(new BorderLayout());
		mainpanel.setOpaque(false);
		
		//new RegisterVereinsDaten(width, height, dim_width, dim_height, style_font);
		this.associationdata = new RegisterAssociationData(style_font, this, association, money_book, association_data_transfer);
		mainpanel.add(associationdata, BorderLayout.CENTER);
		add(mainpanel, BorderLayout.CENTER);
		
		// Centering a Window
		setResizable(false);
		pack();
		setSize(width, height);
		setLocationRelativeTo(null);
		setVisible(true);	
	}
	
	/**
	 * methode to add Panel to mainpanel
	 */
	public void addPanel(JPanel panel){
		this.associationdata = panel;
		mainpanel.add(associationdata, BorderLayout.CENTER);
		setVisible(true);
	}
	
	/**
	 * methode remove mainmenuepanel from container
	 */
	public void removePanel(){
		mainpanel.remove(associationdata);
	}
	
	/**
	 * methode to set the JFrame visiable true
	 */
	public void setVisibleTrue(){
		setVisible(true);
	}
	
	/**
	 * methode to close the window
	 */
	public void schliessen() {
		dispose();
	}
	
	/**
	 * getter methode
	 * 
	 * @return height
	 */
	public int getHeight(){
		return height;
	}
	
	/**
	 * getter methode
	 * 
	 * @return width
	 */
	public int getWidth(){
		return width;
	}
}
