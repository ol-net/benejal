
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
package association.model;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * class represents DoubleDocument
 * 
 * @author Leonid Oldenburger
 */
public class DoubleDocument extends PlainDocument {

  private static final long serialVersionUID = 1L;
  private boolean comma = false;
  private JTextField text = null;
  
  /**
   * constructor 
   * 
   * @param txt
   */
  public DoubleDocument(JTextField txt){
    this.text = txt;
  }
  
  /**
   * methode check strings for double
   */
  public void insertString(int offset, String s, AttributeSet attributeSet) throws BadLocationException {
   
	if(text.getText().toString().length() > 0){
		comma = text.getText().toString().indexOf(",") == -1 ? false : true;
	}
	try{
		if(s.indexOf(",") != -1 && text.getText().length() > 1000000000){ //überprüfe einen ganzen String und nich 1 Zeichen
			comma = true;
		}
		if(!s.equals(",")){       //wenn es sich um keinen KommaPunkt handelt
			Double.parseDouble(s.replace(",", "."));
		}else if(comma){
			throw new Exception();
		}else{
			comma = true;
		}
    }catch(Exception ex){   //only allow integer values
    	return ;
    }
    super.insertString(offset,s, attributeSet);
  }
}
