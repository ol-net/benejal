
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

import javax.swing.JPanel;

import member.view.ManageMembers;
import member.view.MemberAndDonatorFrame;
import association.view.BackgroundPanel;

/**
 * gui class represents mail view
 * 
 * @author Leonid Oldenburger
 */
public class MView extends BackgroundPanel{

	private static final long serialVersionUID = 1L;
	private JPanel mainpanel;
	private MemberAndDonatorFrame mainframe;
	private ManageMembers mame;
	
	/**
	 * constructor creates a new mail view
	 * 
	 * @param frame
	 * @param mm
	 * @param moneyBook
	 * @param adatatrans
	 */
	public MView(MemberAndDonatorFrame frame, ManageMembers mm){
		
		setLayout(new BorderLayout());
		setOpaque(false);
		this.mainframe = frame;
		this.mame = mm;
		
		this.mainpanel = new MailView(this);
		
		add(mainpanel, BorderLayout.CENTER);
	}
	
	/**
	 * get mainframe
	 * 
	 * @return mainframe
	 */
	public MemberAndDonatorFrame getFrame(){
		return mainframe;
	}
	
	/**
	 * get manage member panel
	 * 
	 * @return mame
	 */
	public ManageMembers getManagePanel(){
		return mame;
	}
	
	/**
	 * methode to set a new Panel
	 * 
	 * @param panel
	 */
	public void setPanel(JPanel panel){
		this.mainpanel = panel;
		add(mainpanel, BorderLayout.CENTER);
	}
	
	/**
	 * methode to remove a panel
	 */
	public void removePanel(){
		this.remove(mainpanel);
		mainframe.setVisibleTrue();
	}	
}
