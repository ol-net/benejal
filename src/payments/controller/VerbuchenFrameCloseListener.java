
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
package payments.controller;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import payments.view.VerbuchenFrame;

/**
 * Schlieﬂt das Verbuchen-Fenster
 * @author Artem Petrov
 *
 */
public class VerbuchenFrameCloseListener implements ActionListener {
	
	/**
	 * Schlieﬂt das Verbuchen-Fenster
	 */
	public void actionPerformed(ActionEvent e) {
		
		VerbuchenFrame frame = ((VerbuchenFrame)((JButton) e.getSource()).getParent().getParent().getParent().getParent().getParent());
		frame.setVisible(false);
		frame.dispose();
		
	}

}
