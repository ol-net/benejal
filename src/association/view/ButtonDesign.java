
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

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

/**
 * gui-class represent Buttos with icons
 * 
 * @author Leonid Oldenburger
 */
public class ButtonDesign extends JButton{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * constructor creates new design button
	 * 
	 * @param image
	 */
	public ButtonDesign(String image){
		
		ImageIcon bild = new ImageIcon(image);
		
		setIcon(bild);
		setHorizontalAlignment(SwingConstants.CENTER);
		setVerticalAlignment(SwingConstants.CENTER);
		
		Dimension d = new Dimension(bild.getIconWidth(), bild.getIconHeight());
		
		setPreferredSize(d);	
	}
}
