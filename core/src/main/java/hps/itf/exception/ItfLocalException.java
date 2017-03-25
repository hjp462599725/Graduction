package hps.itf.exception;

/**
 * 
 * @name ItfException
 * @description 自定义用于处理接口管理本地发生的异常 
 * @author jianping.huo@hand-china.com  2016年8月22日下午1:42:03
 * @version 1.0
 */
public class ItfLocalException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -130976524585647053L;
	
	private String code;
	private String descriptionKey;
	private Object[] parameters;
	/**
	 * 本地错误代码
	 */
	public static final String LOCAL_ERROR_CODE = "10448";
	/**
	 * 没有合适的系统
	 */
	public static final String NO_SUITABLE_SYS = "itf.error.no_suitable_sys";
	/**
	 * 系统已停用
	 */
	public static final String SYS_IS_NOT_ACTIVE = "itf.error.sys_is_not_active";
	/**
	 * 没有合适的接口
	 */
	public static final String NO_SUITABLE_IFS = "itf.error.no_suitable_ifs";
	/**
	 * 接口已停用
	 */
	public static final String IFS_IS_NOT_ACTIVE = "itf.error.ifs_is_not_active";
	/**
	 * 没有具体实现类
	 */
	public static final String NO_WEBSERVICE_IMPL = "itf.error.no_webservice_impl";
	/**
	 * 没有具体的接口实现方法
	 */
	public static final String NO_INTERFACE_METHOD = "itf.error.no_interface_method";
	/**
	 * 权限不足
	 */
	public static final String NOT_HAVE_ENOUGH_ACCESS = "itf.error.not_have_enough_access";
	/**
	 * 无法创建Class<T>的实例，该类可能是虚拟类或没有无参构造方法
	 */
	public static final String CAN_NOT_CREATE_NEWINSTANCE = "itf.error.can_not_create_newinstance";
	/**
	 * 不合法的参数
	 */
	public static final String NO_MATCHING_ARGUMENT = "itf.error.no_matching_argument";
	/**
	 * 报文封装出错
	 */
	public static final String XML_WRAP_ERROR = "itf.error.xml_wrap_error";
	/**
	 * 报文解析出错
	 */
	public static final String XML_PARSE_ERROR = "itf.error.xml_parse_error";
	/**
	 * 不合法的soap报文消息
	 */
	public static final String ILLEGAL_SOAP_MESSAGE = "itf.error.illegal_soap_message";
	/**
	 * 不合法的xml格式消息
	 */
	public static final String ILLEGAL_XML_MESSAGE = "itf.error.illegal_xml_message";
	/**
	 * 封装soap报文时没有对应的namespace
	 */
	public static final String XML_NO_NAMESPACE = "itf.error.xml_no_namespace";
	/**
	 * 封装soap报文时没有对应的方法
	 */
	public static final String XML_NO_METHOD = "itf.error.xml_no_method";
	/**
	 * 输入输出流错误
	 */
	public static final String IN_OUT_STREAM_ERROR = "itf.error.in_out_stream_error";
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescriptionKey() {
		return descriptionKey;
	}
	public void setDescriptionKey(String descriptionKey) {
		this.descriptionKey = descriptionKey;
	}
	public Object[] getParameters() {
		return parameters;
	}
	public void setParameters(Object[] parameters) {
		this.parameters = parameters;
	}
	public ItfLocalException() {
		super();
	}
	public ItfLocalException(String message, String code, String descriptionKey, Object[] parameters) {
		super(message);
		this.code = code;
		this.descriptionKey = descriptionKey;
		this.parameters = parameters;
	}
}
