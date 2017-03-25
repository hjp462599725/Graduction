package hps.itf.dto;

/**
 * 
 * @name ItfDetail
 * @description WebService的详细信息
 * @author jianping.huo@hand-china.com  2016年8月19日下午2:43:54
 * @version 1.0
 */
public class ItfDetail {
	private ItfServiceHeader header;
	private ItfServiceLine line;
	
	public ItfServiceHeader getHeader() {
		return header;
	}
	public void setHeader(ItfServiceHeader header) {
		this.header = header;
	}
	public ItfServiceLine getLine() {
		return line;
	}
	public void setLine(ItfServiceLine line) {
		this.line = line;
	}
}
