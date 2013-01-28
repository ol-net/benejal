
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

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import member.controller.MemberActionListener;
import member.model.Member;
import moneybook.model.KassenBuch;

import association.model.AssociationDataTransfer;
import association.view.BackgroundPanel;
import association.view.ButtonDesign;

/**
 * gui-class represent InfoPayedStatus
 * 
 * @author Leonid Oldenburger
 */
public class InfoPayedStatus extends JDialog{
	
	private static final long serialVersionUID = 1L;
	private JPanel buttonPanel;
	private JPanel textPanel;
	private JPanel mainpanel;
	private JButton button;
	private JButton button_2;
	private JPanel dummy;
	private Member association_member;
	private MemberAndDonatorFrame mframe;
	private MemberInformation member_information;
	
	/**
	 * constructor creates an info dialog
	 * 
	 * @param text
	 */
	public InfoPayedStatus (String text, Member aMember, MemberInformation mInfo, MemberAndDonatorFrame mainframe, KassenBuch moneyBook, AssociationDataTransfer adatatrans){
		
		setTitle("Meldung");
		
		setLayout(new BorderLayout());
		
		this.mframe = mainframe;
		this.association_member = aMember;
		this.member_information = mInfo;
		
		mainpanel = new BackgroundPanel();
		mainpanel.setLayout(new BorderLayout());
		mainpanel.setOpaque(false);
		
		textPanel = new JPanel();
		textPanel.setLayout(new FlowLayout());
		textPanel.setOpaque(false);
		
		textPanel.add(new JLabel(text));
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.setOpaque(false);
		
		button = new ButtonDesign("images/ok_icon.png");
		button.addActionListener(new MemberActionListener(association_member, member_information, mframe, this));
		buttonPanel.add(button);
		
		button_2 = new ButtonDesign("images/abbrechen_icon.png");
		button_2.addActionListener(new MemberActionListener(association_member, member_information, mframe, this));
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
	
	/**
	 * methode return payed button
	 * 
	 * @return backbutton
	 */
	public JButton getButton(){
		return button_2;
	}
	
	/**
	 * methode return payed button
	 * 
	 * @return backbutton
	 */
	public JButton getPayedButton(){
		return button;
	}
}
