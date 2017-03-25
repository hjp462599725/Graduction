package hps.se.dto;

import javax.persistence.Column;
import javax.persistence.Transient;

import com.hand.hap.system.dto.BaseDTO;

/**@name EchartParameter
 * @description Echart参数列DTO
 * @author wenhao
 * @version 1.0
 */
public class Echart extends BaseDTO{


	private static final long serialVersionUID = -4466486965653754759L;


	@Transient
	@Column
	private String functionName;
	
	@Transient
	@Column
	private Long fnCount;

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public Long getFnCount() {
		return fnCount;
	}

	public void setFnCount(Long fnCount) {
		this.fnCount = fnCount;
	}
	




	

}
