
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
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import moneybook.model.KassenBuch;

import association.controller.SecurityActionListener;
import association.model.AssociationDataTransfer;

/**
 * gui-class represent update frame for security view
 * 
 * @author Leonid Oldenburger
 */
public class SecurityView extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private JPanel mainpanel;
	private JPanel textpanel;
	private JPanel buttonpanel;
	private JPasswordField password_text;
	private JButton password_button;
	private JLabel password_label;
	private AssociationDataTransfer association_data_transfer;
	private KassenBuch money_book;
	
	/**
	 * constructor for security view
	 * 
	 * @param moneyBook
	 * @param adatatrans
	 */
	public SecurityView(KassenBuch moneyBook, AssociationDataTransfer adatatrans){
		
		setTitle("Passwortabfrage");
		setLayout(new BorderLayout());
		setMinimumSize(new Dimension(300, 150)); 
		setContentPane(new BackgroundPanel());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.association_data_transfer = adatatrans;
		this.money_book = moneyBook;
		
		mainpanel = new JPanel();
		mainpanel.setLayout(new BorderLayout());
		mainpanel.setOpaque(false);
		
		textpanel = new JPanel();
		textpanel.setLayout(new FlowLayout());
		textpanel.setOpaque(false);
		
		buttonpanel = new JPanel();
		buttonpanel.setLayout(new FlowLayout());
		buttonpanel.setOpaque(false);
		
		password_label = new JLabel("Passwort:");
		password_text = new JPasswordField(10);
		
		textpanel.add(password_label);
		textpanel.add(password_text);
		
		password_button = new ButtonDesign("images/ok_icon.png");
		password_button.addActionListener(new SecurityActionListener(this, money_book, association_data_transfer));

		buttonpanel.add(password_button);
		
		mainpanel.add(textpanel, BorderLayout.CENTER);
		mainpanel.add(new JLabel(" "), BorderLayout.WEST);
		mainpanel.add(new JLabel(" "), BorderLayout.NORTH);
		mainpanel.add(new JLabel(" "), BorderLayout.EAST);
		mainpanel.add(buttonpanel, BorderLayout.SOUTH);
		
		add(mainpanel, BorderLayout.CENTER);
		add(new JLabel(" "), BorderLayout.SOUTH);
		add(new JLabel(" "), BorderLayout.NORTH);
		
		// Centering a Window
		setResizable(false);
		pack();
		setSize(300, 150);
		setLocationRelativeTo(null);
		setVisible(true);	
	}
	
	/**
	 * Button
	 * 
	 * @return
	 */
	public JButton getButton(){
		return password_button;
	}
	
	/**
	 * password
	 * 
	 * @return
	 */
	public JPasswordField getPassword(){
		return password_text;
	}
}
