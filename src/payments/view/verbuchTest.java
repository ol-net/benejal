package payments.view;

import java.util.Date;

import association.model.AssociationDataTransfer;

import moneybook.model.KassenBuch;
import moneybook.model.Payment;

public class verbuchTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		KassenBuch kb = new KassenBuch("jdbc:derby:.//derbydbs;create=true");
		AssociationDataTransfer adt = new AssociationDataTransfer();
		
		String zweck = "b";
		
		for(int i = 0; i < 127; i++) {
			zweck += "b";
		}
		
		Payment p = new Payment(new Date(), 1111, 26570024, 100.11, "Name Vorname", zweck, null);
		Payment p2 = new Payment(new Date(), 1111, 26570024, -100.11, "Name Vorname", zweck, null);
		
		new VerbuchenFrame(adt, kb, p, null);
		new VerbuchenFrame(adt, kb, p2, null);

	}

}
