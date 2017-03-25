package hps.rpt.util;

public class RptException extends Exception{
	private static final long serialVersionUID = -634648866748933490L;
	private String code;
	private Object args[];
	public RptException() {
		// TODO Auto-generated constructor stub
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Object[] getArgs() {
		return args;
	}
	public void setArgs(Object[] args) {
		this.args = args;
	}
	public RptException(String code, Object[] args) {
		super();
		this.code = code;
		this.args = args;
	}
	
}
