package hps.itf.exception;

import java.net.HttpURLConnection;
import java.util.Map;

public class ItfRemoteException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8755433289902815351L;
	
	private String code;
	private String descriptionKey;
	private Object[] parameters;
	private Map<String,Object> map;
	
	/**
	 * 错误code
	 */
	public static final String REMOTE_ERROR = "10449";
	/**
	 * HTTP Status-Code 202: Accepted
	 */
	public static final String HTTP_ACCEPTED = "itf.error.http_accepted";
	/**
	 * HTTP Status-Code 502: Bad Gateway
	 */
	public static final String HTTP_BAD_GATEWAY = "itf.error.http_bad_gateway";
	/**
	 * HTTP Status-Code 405: Method Not Allowed
	 */
	public static final String HTTP_BAD_METHOD = "itf.error.http_bad_method";
	/**
	 * HTTP Status-Code 400: Bad Request
	 */
	public static final String HTTP_BAD_REQUEST = "itf.error.http_bad_request";
	/**
	 * HTTP Status-Code 408: Request Time-Out
	 */
	public static final String HTTP_CLIENT_TIMEOUT = "itf.error.http_client_timeout";
	/**
	 * HTTP Status-Code 409: Conflict
	 */
	public static final String HTTP_CONFLICT = "itf.error.http_conflict";
	/**
	 * HTTP Status-Code 201: Created
	 */
	public static final String HTTP_CREATED = "itf.error.http_created";
	/**
	 * HTTP Status-Code 413: Request Entity Too Large
	 */
	public static final String HTTP_ENTITY_TOO_LARGE = "itf.error.http_entity_too_large";
	/**
	 * HTTP Status-Code 403: Forbidden
	 */
	public static final String HTTP_FORBIDDEN = "itf.error.http_forbidden";
	/**
	 * HTTP Status-Code 504: Gateway Timeout
	 */
	public static final String HTTP_GATEWAY_TIMEOUT = "itf.error.http_gateway_timeout";
	/**
	 * HTTP Status-Code 410: Gone
	 */
	public static final String HTTP_GONE = "itf.error.http_gone";
	/**
	 * HTTP Status-Code 500: Internal Server Error
	 */
	public static final String HTTP_INTERNAL_ERROR = "itf.error.http_internal_error";
	/**
	 * HTTP Status-Code 411: Length Required
	 */
	public static final String HTTP_LENGTH_REQUIRED = "itf.error.http_length_required";
	/**
	 * HTTP Status-Code 301: Moved Permanently
	 */
	public static final String HTTP_MOVED_PERM = "itf.error.http_moved_perm";
	/**
	 * HTTP Status-Code 302: Temporary Redirect
	 */
	public static final String HTTP_MOVED_TEMP = "itf.error.http_moved_temp";
	/**
	 * HTTP Status-Code 300: Multiple Choices
	 */
	public static final String HTTP_MULT_CHOICE = "itf.error.http_mult_choice";
	/**
	 * HTTP Status-Code 204: No Content
	 */
	public static final String HTTP_NO_CONTENT = "itf.error.http_no_content";
	/**
	 * HTTP Status-Code 406: Not Acceptable
	 */
	public static final String HTTP_NOT_ACCEPTABLE = "itf.error.http_not_acceptable";
	/**
	 * HTTP Status-Code 203: Non-Authoritative Information
	 */
	public static final String HTTP_NOT_AUTHORITATIVE = "itf.error.http_not_authoritative";
	/**
	 * HTTP Status-Code 404: Not Found
	 */
	public static final String HTTP_NOT_FOUND = "itf.error.http_not_found";
	/**
	 * HTTP Status-Code 501: Not Implemented
	 */
	public static final String HTTP_NOT_IMPLEMENTED = "itf.error.http_not_implemented";
	/**
	 * HTTP Status-Code 304: Not Modified
	 */
	public static final String HTTP_NOT_MODIFIED = "itf.error.http_not_modified";
	/**
	 * HTTP Status-Code 206: Partial Content
	 */
	public static final String HTTP_PARTIAL = "itf.error.http_partial";
	/**
	 * HTTP Status-Code 402: Payment Required
	 */
	public static final String HTTP_PAYMENT_REQUIRED = "itf.error.http_payment_required";
	/**
	 * HTTP Status-Code 412: Precondition Failed
	 */
	public static final String HTTP_PRECON_FAILED = "itf.error.http_precon_failed";
	/**
	 * HTTP Status-Code 407: Proxy Authentication Required
	 */
	public static final String HTTP_PROXY_AUTH = "itf.error.http_proxy_auth";
	/**
	 * HTTP Status-Code 414: Request-URI Too Large
	 */
	public static final String HTTP_REQ_TOO_LONG = "itf.error.http_req_too_long";
	/**
	 * HTTP Status-Code 205: Reset Content
	 */
	public static final String HTTP_RESET = "itf.error.http_reset";
	/**
	 * HTTP Status-Code 303: See Other
	 */
	public static final String HTTP_SEE_OTHER = "itf.error.http_see_other";
	/**
	 * HTTP Status-Code 401: Unauthorized
	 */
	public static final String HTTP_UNAUTHORIZED = "itf.error.http_unauthorized";
	/**
	 * HTTP Status-Code 503: Service Unavailable
	 */
	public static final String HTTP_UNAVAILABLE = "itf.error.http_unavailable";
	/**
	 * HTTP Status-Code 415: Unsupported Media Type
	 */
	public static final String HTTP_UNSUPPORTED_TYPE = "itf.error.http_unsupported_type";
	/**
	 * HTTP Status-Code 305: Use Proxy
	 */
	public static final String HTTP_USE_PROXY = "itf.error.http_use_proxy";
	/**
	 * HTTP Status-Code 505: HTTP Version Not Supported
	 */
	public static final String HTTP_VERSION = "itf.error.http_version";
	
	public ItfRemoteException() {
		super();
	}

	public ItfRemoteException(Map<String, Object> map) {
		super();
		this.code = REMOTE_ERROR;
		this.map = map;
		switch ((int)map.get("statusCode")) {
		case HttpURLConnection.HTTP_ACCEPTED:
			this.descriptionKey = HTTP_ACCEPTED;
			break;
		case HttpURLConnection.HTTP_BAD_GATEWAY:
			this.descriptionKey = HTTP_BAD_GATEWAY;
			break;
		case HttpURLConnection.HTTP_BAD_METHOD:
			this.descriptionKey = HTTP_BAD_METHOD;
			break;
		case HttpURLConnection.HTTP_BAD_REQUEST:
			this.descriptionKey = HTTP_BAD_REQUEST;
			break;
		case HttpURLConnection.HTTP_CLIENT_TIMEOUT:
			this.descriptionKey = HTTP_CLIENT_TIMEOUT;
			break;
		case HttpURLConnection.HTTP_CONFLICT:
			this.descriptionKey = HTTP_CONFLICT;
			break;
		case HttpURLConnection.HTTP_CREATED:
			this.descriptionKey = HTTP_CREATED;
			break;
		case HttpURLConnection.HTTP_ENTITY_TOO_LARGE:
			this.descriptionKey = HTTP_ENTITY_TOO_LARGE;
			break;
		case HttpURLConnection.HTTP_FORBIDDEN:
			this.descriptionKey = HTTP_FORBIDDEN;
			break;
		case HttpURLConnection.HTTP_GATEWAY_TIMEOUT:
			this.descriptionKey = HTTP_GATEWAY_TIMEOUT;
			break;
		case HttpURLConnection.HTTP_GONE:
			this.descriptionKey = HTTP_GONE;
			break;
		case HttpURLConnection.HTTP_INTERNAL_ERROR:
			this.descriptionKey = HTTP_INTERNAL_ERROR;
			break;
		case HttpURLConnection.HTTP_LENGTH_REQUIRED:
			this.descriptionKey = HTTP_LENGTH_REQUIRED;
			break;
		case HttpURLConnection.HTTP_MOVED_PERM:
			this.descriptionKey = HTTP_MOVED_PERM;
			break;
		case HttpURLConnection.HTTP_MOVED_TEMP:
			this.descriptionKey = HTTP_MOVED_TEMP;
			break;
		case HttpURLConnection.HTTP_MULT_CHOICE:
			this.descriptionKey = HTTP_MULT_CHOICE;
			break;
		case HttpURLConnection.HTTP_NO_CONTENT:
			this.descriptionKey = HTTP_NO_CONTENT;
			break;
		case HttpURLConnection.HTTP_NOT_ACCEPTABLE:
			this.descriptionKey = HTTP_NOT_ACCEPTABLE;
			break;
		case HttpURLConnection.HTTP_NOT_AUTHORITATIVE:
			this.descriptionKey = HTTP_NOT_AUTHORITATIVE;
			break;
		case HttpURLConnection.HTTP_NOT_FOUND:
			this.descriptionKey = HTTP_NOT_FOUND;
			break;
		case HttpURLConnection.HTTP_NOT_IMPLEMENTED:
			this.descriptionKey = HTTP_NOT_IMPLEMENTED;
			break;
		case HttpURLConnection.HTTP_NOT_MODIFIED:
			this.descriptionKey = HTTP_NOT_MODIFIED;
			break;
		case HttpURLConnection.HTTP_PARTIAL:
			this.descriptionKey = HTTP_PARTIAL;
			break;
		case HttpURLConnection.HTTP_PAYMENT_REQUIRED:
			this.descriptionKey = HTTP_PAYMENT_REQUIRED;
			break;
		case HttpURLConnection.HTTP_PRECON_FAILED:
			this.descriptionKey = HTTP_PRECON_FAILED;
			break;
		case HttpURLConnection.HTTP_PROXY_AUTH:
			this.descriptionKey = HTTP_PROXY_AUTH;
			break;
		case HttpURLConnection.HTTP_REQ_TOO_LONG:
			this.descriptionKey = HTTP_REQ_TOO_LONG;
			break;
		case HttpURLConnection.HTTP_RESET:
			this.descriptionKey = HTTP_RESET;
			break;
		case HttpURLConnection.HTTP_SEE_OTHER:
			this.descriptionKey = HTTP_SEE_OTHER;
			break;
		case HttpURLConnection.HTTP_UNAUTHORIZED:
			this.descriptionKey = HTTP_UNAUTHORIZED;
			break;
		case HttpURLConnection.HTTP_UNAVAILABLE:
			this.descriptionKey = HTTP_UNAVAILABLE;
			break;
		case HttpURLConnection.HTTP_UNSUPPORTED_TYPE:
			this.descriptionKey = HTTP_UNSUPPORTED_TYPE;
			break;
		case HttpURLConnection.HTTP_USE_PROXY:
			this.descriptionKey = HTTP_USE_PROXY;
			break;
		case HttpURLConnection.HTTP_VERSION:
			this.descriptionKey = HTTP_VERSION;
		}
	}

	public ItfRemoteException(String code, String descriptionKey, Object[] parameters) {
		super();
		this.code = code;
		this.descriptionKey = descriptionKey;
		this.parameters = parameters;
	}

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
	public Map<String, Object> getMap() {
		return map;
	}
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
}
