package py.una.pol.vone.excepcions;

public class ValidationsExceptions extends Exception{

	private static final long serialVersionUID = 1L;
	String msg;
	
	public ValidationsExceptions(String msg){
		super();
		this.msg = msg;
	}
}
