package mail.view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;

import com.toedter.calendar.JCalendar;

import mail.controller.ReceiptActionListener;
import member.view.DonatorInformation;
import member.view.MemberInformation;
import moneybook.model.KassenBuch;
import association.model.AssociationDataTransfer;
import association.view.ButtonDesign;

public class SingleDonationReceipt extends MassDonationReceipt{

	private static final long serialVersionUID = 1L;
	private MemberInformation member_information;
	private DonatorInformation donator_information;
	
	private JCalendar calendar;
	
	// JButton
	private JButton next_button;
	private JButton back_button;
	
	private int donationDate;
	
	public SingleDonationReceipt(MemberInformation m_info){
		setLayout(new BorderLayout());
		setOpaque(false);
		
		calendar = new JCalendar();
		donationDate = calendar.getYearChooser().getValue();
		
		calendar.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
			
			public void propertyChange(java.beans.PropertyChangeEvent evt) {
		    	setDonationDate();
			}
		});
		
		this.association_data_transfer = AssociationDataTransfer.getInstance();
		this.association_data_transfer.addObserver(this);
		this.money_book = KassenBuch.getInstance("jdbc:derby:.//derbydbs;create=true");
		this.member_information = m_info;
		
		createView();

		next_button = new ButtonDesign("images/create_pdf_icon.png");
		next_button.addActionListener(new ReceiptActionListener(member_information, this));
		back_button = new ButtonDesign("images/back_icon.png");
		back_button.addActionListener(new ReceiptActionListener(member_information, this));
		
		GridBagConstraints b = new GridBagConstraints();
		b.fill=GridBagConstraints.HORIZONTAL;
		
		b.insets =new Insets(2,3,2,2);
		
		b.gridx=0;
		b.gridy=0;
		buttonpanel.add(back_button, b);
		
		b.gridx=1;
		b.gridy=0;
		buttonpanel.add(next_button, b);
		
		b.gridx=2;
		b.gridy=0;
		buttonpanel.add(new JLabel("   für das Jahr:"), b);
		
		b.gridx=3;
		b.gridy=0;
		buttonpanel.add(calendar.getYearChooser(), b);
		
		createButPanel();
	}
	
	public void setDonationMemberDate() {
    	next_button.removeActionListener(new ReceiptActionListener(member_information, this));

    	this.donationDate = calendar.getYearChooser().getValue();
		next_button.addActionListener(new ReceiptActionListener(member_information, this));

	}
	
	public void setDonationDate() {
    	next_button.removeActionListener(new ReceiptActionListener(donator_information, this));

    	this.donationDate = calendar.getYearChooser().getValue();
		next_button.addActionListener(new ReceiptActionListener(donator_information, this));

	}
	
	public SingleDonationReceipt(DonatorInformation d_info){
		
		calendar = new JCalendar();
		donationDate = calendar.getYearChooser().getValue();
		
		calendar.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
			
			public void propertyChange(java.beans.PropertyChangeEvent evt) {
		    	setDonationDate();
			}
		});
		
		setLayout(new BorderLayout());
		setOpaque(false);
		
		this.association_data_transfer = AssociationDataTransfer.getInstance();
		this.association_data_transfer.addObserver(this);
		this.money_book = KassenBuch.getInstance("jdbc:derby:.//derbydbs;create=true");
		this.donator_information = d_info;
		
		createView();

		next_button = new ButtonDesign("images/create_pdf_icon.png");
		next_button.addActionListener(new ReceiptActionListener(donator_information, this));
		back_button = new ButtonDesign("images/back_icon.png");
		back_button.addActionListener(new ReceiptActionListener(donator_information, this));
		
		GridBagConstraints b = new GridBagConstraints();
		b.fill=GridBagConstraints.HORIZONTAL;
		
		b.insets =new Insets(2,3,2,2);
		
		b.gridx=0;
		b.gridy=0;
		buttonpanel.add(back_button, b);
		
		b.gridx=1;
		b.gridy=0;
		buttonpanel.add(next_button, b);
		
		b.gridx=2;
		b.gridy=0;
		buttonpanel.add(new JLabel("   für das Jahr:"), b);
		
		b.gridx=3;
		b.gridy=0;
		buttonpanel.add(calendar.getYearChooser(), b);
		
		createButPanel();
	}
	
	/**
	 * method returns back button
	 * 
	 * @return back button
	 */
	public JButton getBButton2(){
		return back_button;
	}
	
	/**
	 * method returns next button
	 * 
	 * @return next button
	 */
	public JButton getNButton2(){
		return next_button;
	}
	
	/**
	 * donation date
	 * 
	 * @return
	 */
	public int getMemberDonationDate() {
		return donationDate;
	}
	
	/**
	 * donation date
	 * 
	 * @return
	 */
	public int getDonatorDonationDate() {
		return donationDate;
	}
}