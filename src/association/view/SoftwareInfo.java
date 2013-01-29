
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class SoftwareInfo extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JPanel mainpanel;
	private JPanel button_panel;
	private JButton ok_button;
	
	
	public SoftwareInfo(){
		
		this.mainpanel = new JPanel();
		mainpanel.setLayout(new BoxLayout(mainpanel, BoxLayout.PAGE_AXIS));
		mainpanel.setOpaque(false);
		setTitle("Informationen zu diesem Programm");
		setLayout(new BorderLayout());
		setMinimumSize(new Dimension(500, 300)); 
		
		mainpanel.add(new JLabel(" "));
		mainpanel.add(new JLabel("Benejal - Software für gemeinnützige Fördervereine Version 2.0.1"));
		mainpanel.add(new JLabel(" "));
		mainpanel.add(new JLabel("(c) Copyright 2010 Universität Osnabrück. Alle Rechte vorbehalten."));
		mainpanel.add(new JLabel(" "));
		mainpanel.add(new JLabel("(c) Copyright 2013 www.benejal.de. Alle Rechte vorbehalten."));
		mainpanel.add(new JLabel(" "));
		mainpanel.add(new JLabel("Autoren: Artem Petrov (artpetro@uos.de) & Leonid Oldenburger (loldenbu@uos.de)"));
		mainpanel.add(new JLabel(" "));
		mainpanel.add(new JLabel("Lizenz: GNU General Public License"));
		mainpanel.add(new JLabel(" "));

		this.button_panel = new JPanel();
		button_panel.setLayout(new FlowLayout());
		button_panel.setOpaque(false);
		
		this.ok_button = new ButtonDesign("images/ok_icon.png");
		ok_button.addActionListener(this);
		button_panel.add(ok_button);
		
		
		this.panel = new BackgroundPanel();
		panel.setLayout(new BorderLayout());
		panel.setOpaque(false);
		
		panel.add(mainpanel, BorderLayout.NORTH);
		panel.add(button_panel, BorderLayout.CENTER);
		panel.add(new JLabel(" "), BorderLayout.SOUTH);
		
		add(new BackgroundPanel(), BorderLayout.WEST);
		add(panel, BorderLayout.CENTER);
		
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		pack();
		
		// Centering a Window
		setResizable(true);
		setSize(500, 230);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent ev){
		if (ev.getSource() == ok_button)
			this.dispose();
	}
}