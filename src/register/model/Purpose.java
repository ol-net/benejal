package register.model;

/**
 * Class Purpose
 * 
 * @author Leonid Oldenburger
 */
public class Purpose {

	private static Purpose purpose_instance; 
	private String purpose_text;
	
	public Purpose(){
		this.purpose_text = "Angabe des begünstigten Zwecks";
	}
	
	public synchronized static Purpose getInstance(){

		if(purpose_instance == null){
			purpose_instance = new Purpose();

		}
		return purpose_instance;
	}
	
	public void setText(String text){
		this.purpose_text = text;
	}
	
	public String getText(){
		return purpose_text;
	}
}