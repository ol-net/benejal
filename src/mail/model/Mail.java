
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
package mail.model;

/**
 * class mail
 * 
 * @author Leonid Oldenburger
 */
public class Mail {
	
	protected String subject;
	protected String text;
	protected String membergroup;
	protected String creation_date;
	protected int author;
	
	/**
	 * super constructor creates empty mail.
	 */
	public Mail(){
		
	}
	
	/**
	 * constructor creates new mail.
	 * 
	 * @param sub (subject)
	 * @param tex (text)
	 * @param fname (filename)
	 * @param cdate (creation date)
	 */
	public Mail(String sub, String tex, String fname, String cdate, int a){
		this.subject = sub;
		this.text = tex;
		this.membergroup = fname;
		this.creation_date = cdate;
		this.author = a;
	}
	
	/**
	 * methode sets subject.
	 * 
	 * @param sub (subject)
	 */
	public void setSubject(String sub){
		this.subject = sub;
	}
	
	/**
	 * methode returns subject.
	 */
	public String getSubject(){
		return subject;
	}
	
	/**
	 * methode sets text.
	 * 
	 * @param tex (text)
	 */
	public void setText(String tex){
		this.text = tex;
	}
	
	/**
	 * methode returns text.
	 * 
	 * @return text
	 */
	public String getText(){
		return text;
	}
	
	/**
	 * methode sets filename.
	 * 
	 * @param fname (filename)
	 */
	public void setMemberGroup(String fname){
		this.membergroup = fname;
	}
	
	/**
	 * methode returns filename.
	 * 
	 * @return filename
	 */
	public String getMemberGroup(){
		return membergroup;
	}
	
	/**
	 * methode sets creation date.
	 * 
	 * @param d (date)
	 */
	public void setDate(String cdate){
		this.creation_date = cdate;
	}
	
	/**
	 * methode returns creation date.
	 * 
	 * @return creation date
	 */
	public String getDate(){
		return creation_date;
	}
	
	/**
	 * methode returns an author
	 * 
	 * @return author
	 */
	public int getAuthor(){
		return author;
	}
	
	/**
	 * methode sets an author
	 * 
	 * @param author
	 */
	public void setAuthor(int a){
		this.author = a;
	}
}
