
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

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * class represents a DocumentFilter
 * 
 * @author Leonid Oldenburger
 */
public class DocumentSizeFilter extends DocumentFilter {
    
	private final int maxCharacters;
    private final String pattern;
    
    /**
     * constructor 
     * 
     * @param maxChars
     * @param pattern
     */
    public DocumentSizeFilter(final int maxChars, final String pattern) {
        maxCharacters = maxChars;
        this.pattern = pattern;
    }
    
    /**
     * methode check the correct string
     */
    public void replace(FilterBypass fb, int offs, int length, String str, AttributeSet a) throws BadLocationException {
    	
    	if (str.matches(pattern) && (fb.getDocument().getLength() + str.length() - length) <= maxCharacters) {
            super.replace(fb, offs, length, str, a);
        }
    }
}
