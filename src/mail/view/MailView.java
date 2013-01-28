
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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import association.view.ButtonDesign;

import mail.controller.MailActionListener;

import member.controller.BackToMainMenueActionListener;

/**
 * gui class represents the mail view 
 * 
 * @author Leonid Oldenburger
 */
public class MailView extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JPanel mainpanel;
	private JPanel backpanel;
	private MView allmailview;
	
	private ButtonDesign mail_button;
	private ButtonDesign receipt_button;
	private ButtonDesign back_button;
	
	/**
	 * constructor creates a mail view
	 * 
	 * @param amailview
	 * @param moneyBook
	 * @param adatatrans
	 */
	public MailView(MView amailview){
		
		setBorder(new TitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY ), "Schriftverkehr"));
		
		setLayout(new BorderLayout());
		setOpaque(false);
		
		allmailview = amailview;
		
		mainpanel = new JPanel();
		mainpanel.setLayout(new GridBagLayout());
		mainpanel.setOpaque(false);
		
		backpanel = new JPanel();
		backpanel.setLayout(new GridBagLayout());
		backpanel.setOpaque(false);

		mail_button = new ButtonDesign("images/massmail_icon.png");
		mail_button.addActionListener(new MailActionListener(allmailview, this));
		receipt_button = new ButtonDesign("images/quittung_icon.png");
		receipt_button.addActionListener(new MailActionListener(allmailview, this));
		back_button = new ButtonDesign("images/mainmenu_icon.png");
		back_button.addActionListener(new BackToMainMenueActionListener(allmailview.getFrame(), this));
		
		GridBagConstraints d = new GridBagConstraints();
		d.fill=GridBagConstraints.HORIZONTAL;
		
		d.insets =new Insets(25,2,26,2);
		
		d.gridx=0;
		d.gridy=0;
		backpanel.add(back_button, d);
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill=GridBagConstraints.HORIZONTAL;
		
		c.insets =new Insets(2,40,2,40);
		
		c.gridx=0;
		c.gridy=0;
		mainpanel.add(mail_button, c);
		
		c.gridx=1;
		c.gridy=0;
		mainpanel.add(receipt_button, c);
		
		add(mainpanel, BorderLayout.CENTER);	
		add(backpanel, BorderLayout.SOUTH);
	}
	
	/**
	 * methode return mail button
	 * 
	 * @return mailbutton
	 */
	public JButton getMailButton(){
		return mail_button;
	}
	
	/**
	 * methode return receipt button
	 * 
	 * @return receipt_button
	 */
	public JButton getReceiptButton(){
		return receipt_button;
	}
	
	/**
	 * methode return back button
	 * 
	 * @return backbutton
	 */
	public JButton getBackButton(){
		return back_button;
	}
}
