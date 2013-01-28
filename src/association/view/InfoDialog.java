
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
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * gui-class represent InfoDialog
 * 
 * @author Leonid Oldenburger
 */
public class InfoDialog extends JDialog implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	private JPanel buttonPanel;
	private JPanel textPanel;
	private JPanel mainpanel;
	private JButton button;
	private JPanel dummy;
	
	/**
	 * constructor creates an info dialog
	 * 
	 * @param text
	 */
	public InfoDialog(String text){
		
		setTitle("Meldung");
		
		setLayout(new BorderLayout());
		
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
		button.addActionListener(this);
		buttonPanel.add(button);
		
		mainpanel.add(textPanel, BorderLayout.NORTH);
		mainpanel.add(buttonPanel, BorderLayout.CENTER);
		
		JLabel d = new JLabel(" ");
		d.setOpaque(false);
		
		dummy = new JPanel();
		dummy.setLayout(new FlowLayout());
		dummy.setOpaque(false);
		
		dummy.add(d);
		
		//add(dummy, BorderLayout.NORTH);
		add(mainpanel, BorderLayout.CENTER);
		
		setSize(400, 130 ); 
		setLocationRelativeTo(null);
		setModal(true);
		setVisible( true );
	}

	/**
	 * methode handle events
	 */
	public void actionPerformed(ActionEvent event) {
		
		if (button == event.getSource()) {
			try {
				dispose();
			} catch (Exception e) {
			}
		}
		
	}
}
