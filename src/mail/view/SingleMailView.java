package mail.view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;

import association.model.AssociationDataTransfer;
import association.view.ButtonDesign;
import mail.controller.MassMailActionListener;
import member.view.DonatorInformation;
import member.view.MemberInformation;
import moneybook.model.KassenBuch;

public class SingleMailView extends MassMailView{

	private static final long serialVersionUID = 1L;
	private MemberInformation member_information;
	private DonatorInformation donator_information;
	
	public SingleMailView(MemberInformation m_info){
		
		this.association_data_transfer = AssociationDataTransfer.getInstance();
		this.association_data_transfer.addObserver(this);
		this.money_book = KassenBuch.getInstance("jdbc:derby:.//derbydbs;create=true");
		this.member_information = m_info;
		
		setLayout(new BorderLayout());
		setOpaque(false);
		
		createView();
		
		layout.row().grid(new JLabel("Briefarchiv:"), 4).add(table_panel);
	    layout.emptyRow();
		layout.row().grid(new JLabel("Betreff: "), 4).add(subject);
		layout.emptyRow();
		layout.row().grid(new JLabel("Brief: "), 4).add(scrollPane);
		layout.emptyRow();
		layout.row().grid(new JLabel("erstellt vom: "), 1).add(by);
				
		next_button = new ButtonDesign("images/create_pdf_icon.png");
		next_button.addActionListener(new MassMailActionListener(member_information, this));
		back_button = new ButtonDesign("images/back_icon.png");
		back_button.addActionListener(new MassMailActionListener(member_information, this));
		
		GridBagConstraints b = new GridBagConstraints();
		b.fill=GridBagConstraints.HORIZONTAL;
		
		b.insets =new Insets(5,3,2,2);
		
		b.gridx=0;
		b.gridy=0;
		buttonpanel.add(back_button, b);
		
		b.gridx=1;
		b.gridy=0;
		buttonpanel.add(next_button, b);
		
		createButView();
	}
	
	public SingleMailView(DonatorInformation d_info){
		this.association_data_transfer = AssociationDataTransfer.getInstance();
		this.association_data_transfer.addObserver(this);
		this.money_book = KassenBuch.getInstance("jdbc:derby:.//derbydbs;create=true");
		this.donator_information = d_info;
		
		setLayout(new BorderLayout());
		setOpaque(false);
		
		createView();
		
		layout.row().grid(new JLabel("Briefarchiv:"), 4).add(table_panel);
	    layout.emptyRow();
		layout.row().grid(new JLabel("Betreff: "), 4).add(subject);
		layout.emptyRow();
		layout.row().grid(new JLabel("Brief: "), 4).add(scrollPane);
		layout.emptyRow();
		layout.row().grid(new JLabel("erstellt vom: "), 1).add(by);
		
		next_button = new ButtonDesign("images/create_pdf_icon.png");
		next_button.addActionListener(new MassMailActionListener(donator_information, this));
		back_button = new ButtonDesign("images/back_icon.png");
		back_button.addActionListener(new MassMailActionListener(donator_information, this));
		
		GridBagConstraints b = new GridBagConstraints();
		b.fill=GridBagConstraints.HORIZONTAL;
		
		b.insets =new Insets(5,3,2,2);
		
		b.gridx=0;
		b.gridy=0;
		buttonpanel.add(back_button, b);
		
		b.gridx=1;
		b.gridy=0;
		buttonpanel.add(next_button, b);
		
		createButView();
	}
	
	/**
	 * method returns back button
	 * 
	 * @return back button
	 */
	public JButton getBButton(){
		return back_button;
	}
	
	/**
	 * method returns next button
	 * 
	 * @return next button
	 */
	public JButton getNButton(){
		return next_button;
	}
}
