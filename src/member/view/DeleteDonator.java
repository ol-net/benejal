
// software for charitable associations 
// Copyright (C) 2010  Artem Petrov (artpetro@uos.de) & Leonid Oldenburger (loldenbu@uos.de)

// This program is free software; you can redistribute it and/or modify it under the terms 
// of the GNU General Public License as published by the Free Software Foundation; either 
// version 3 of the License, or (at your option) any later version.

// This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
// without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
// See the GNU General Public License for more details.

package member.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import association.model.AssociationDataTransfer;
import association.view.BackgroundPanel;
import association.view.ButtonDesign;
import association.view.InfoDialog;
import member.view.DonatorInformation;

/**
 * gui-class represent InfoPayedStatus
 * 
 * @author Leonid Oldenburger
 */
public class DeleteDonator extends JDialog{
	
	private static final long serialVersionUID = 1L;
	private JPanel buttonPanel;
	private JPanel textPanel;
	private JPanel mainpanel;
	private JButton button;
	private JButton button_2;
	private JPanel dummy;
	private int association_donator_id;
	private DonatorInformation d_info;
	
	/**
	 * constructor creates an info dialog
	 * 
	 * @param text
	 */
	public DeleteDonator (int aDonator, DonatorInformation di){
		
		setTitle("Meldung");
		
		setLayout(new BorderLayout());

		this.association_donator_id = aDonator;
		
		d_info = di;
		
		mainpanel = new BackgroundPanel();
		mainpanel.setLayout(new BorderLayout());
		mainpanel.setOpaque(false);
		
		textPanel = new JPanel();
		textPanel.setLayout(new FlowLayout());
		textPanel.setOpaque(false);
		
		textPanel.add(new JLabel("Diser Spender wird unverzüglich gelöscht!"));
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.setOpaque(false);
		
	    ActionListener actionListener = new ActionListener() {
	        public void actionPerformed(ActionEvent event) {	
	        	
	        	AssociationDataTransfer dataT = AssociationDataTransfer.getInstance();
	        	
	        	dataT.deleteDonator(association_donator_id);
	        	
				dispose();		
				
				new InfoDialog("Spender gelöscht!");
				
				d_info.dispose();

	        }
	    };
	    
		button = new ButtonDesign("images/ok_icon.png");
		button.addActionListener(actionListener);
		
		buttonPanel.add(button);
		
	    ActionListener actionListener2 = new ActionListener() {
	        public void actionPerformed(ActionEvent event) {		
				dispose();
	        }
	    };
		
		button_2 = new ButtonDesign("images/abbrechen_icon.png");
		button_2.addActionListener(actionListener2);
		buttonPanel.add(button_2);
		
		mainpanel.add(textPanel, BorderLayout.NORTH);
		mainpanel.add(buttonPanel, BorderLayout.CENTER);
		
		JLabel d = new JLabel(" ");
		d.setOpaque(false);
		
		dummy = new JPanel();
		dummy.setLayout(new FlowLayout());
		dummy.setOpaque(false);
		
		dummy.add(d);
		
		add(mainpanel, BorderLayout.CENTER);
		
		setSize(400, 130 ); 
		setLocationRelativeTo(null);
		setModal(true);
		setVisible( true );
	}
}