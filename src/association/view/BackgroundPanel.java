
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

import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;

import javax.swing.JPanel;

/**
 * gui-class represent Backgroundpanel
 * 
 * @author Leonid Oldenburger
 */
public class BackgroundPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	Image img = null;
	
	/**
	 * constructor
	 */
	public BackgroundPanel() {
		
		String imagefile = "images/b_g.png";
		
		if (imagefile != null) {
			
			MediaTracker mt = new MediaTracker(this);
			
			img = Toolkit.getDefaultToolkit().getImage(imagefile);
			
			mt.addImage(img, 0);
			
			try {
				mt.waitForAll();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * methode for graphics
	 */
	protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		g.drawImage(img,0,0,this.getWidth(),this.getHeight(),this);
	}
}

