package member.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import member.model.MemberPDFList;
import member.view.EditMembers;

public class EditMemberActionListener implements ActionListener{
	
	private EditMembers editMembers;
	private int mGroup;
	
	public EditMemberActionListener(EditMembers eMembers){
		this.editMembers = eMembers;
	}
	
	/**
	 * methode handels events
	 */
	public void actionPerformed(ActionEvent event) {
		
		if (editMembers.getPDFButton() == event.getSource()) {
			try {
				this.mGroup = editMembers.getView();
				new MemberPDFList(mGroup);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}