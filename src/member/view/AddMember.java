
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
package member.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import member.model.Member;
import moneybook.model.KassenBuch;

import association.model.AssociationDataTransfer;
import association.view.BackgroundPanel;

/**
 * JPanel to add a new Member
 * 
 * @author Leonid Oldenburger
 */
public class AddMember extends BackgroundPanel{

	private static final long serialVersionUID = 1L;
	
	private JPanel memberpanel;
	private MemberAndDonatorFrame mainframe;
	private ManageMembers mame;
	private Member association_member;
	
	private AssociationDataTransfer association_data_transfer;
	
	private KassenBuch money_book;
	
    /**
     * constructor for gui to add a member.
     * 
     * @param frame
     * @param mm
     * @param aMember
     * @param moneyBook
     * @param adatatrans
     */
	public AddMember(MemberAndDonatorFrame frame, ManageMembers mm, Member aMember, KassenBuch moneyBook, AssociationDataTransfer adatatrans){
		
		setLayout(new BorderLayout());
		setOpaque(false);
		
		setBorder(new TitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY ), "Mitglied erfassen"));
		this.mainframe = frame;
		this.mame = mm;
		this.association_member = aMember;
		
		this.money_book = moneyBook;
		this.association_data_transfer = adatatrans;
		
		memberpanel = new AddMemberData(mainframe, this, mame, association_member, money_book, association_data_transfer);
		add(memberpanel, BorderLayout.CENTER);
	}
	
	/**
	 * methode to set a new Panel
	 * 
	 * @param panel
	 */
	public void setPanel(JPanel panel){
		this.memberpanel = panel;
		add(memberpanel, BorderLayout.CENTER);
	}
	
	/**
	 * methode to remove a panel
	 */
	public void removePanel(){
		this.remove(memberpanel);
		mainframe.setVisibleTrue();
	}
}
