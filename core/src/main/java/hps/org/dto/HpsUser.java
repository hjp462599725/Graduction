package hps.org.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.hand.hap.mybatis.annotation.Condition;
import com.hand.hap.system.dto.BaseDTO;

/**
 * @name HpsUser
 * @description 用户对象.
 * @author jie.yang03@hand-china.com 2016年10月9日14:36:04
 * version 1.0
 */
@Table(name = "sys_user")
public class HpsUser extends BaseDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3680051108039283440L;
	public static final String FIELD_USER_ID = "userId";
    public static final String FIELD_USER_NAME = "userName";

    public static final String STATUS_ACTV = "ACTV";
    public static final String STATUS_EXPR = "EXPR";
    public static final String STATUS_LOCK = "LOCK";


    @Id
    @Column
    @GeneratedValue(generator = GENERATOR_TYPE)
    private Long userId;

    // 用户名
    @NotEmpty
    @Column
    private String userName;

    @Column
    @Condition(exclude = true)
    private String passwordEncrypted;

    @Transient
    private String password;

    private String userType;

    // 邮箱
    @Column
    private String email;
    // 手机
    @Column
    private String phone;

    @Column
    @Condition(operator = ">=")
    private Date startActiveDate;

    @Column
    @Condition(operator = "<=")
    private Date endActiveDate;

    // 状态
    @JsonInclude(Include.NON_NULL)
    @Column
    private String status;
    
    @Transient
    private String enableFlag;
    
    @Transient
    private String empId;
    
    @Transient
    private String empName;
    
    @Transient
    private String empNumber;
    
    @Column
    private Date lastUpdateDate;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPasswordEncrypted() {
		return passwordEncrypted;
	}

	public void setPasswordEncrypted(String passwordEncrypted) {
		this.passwordEncrypted = passwordEncrypted;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}


	public Date getStartActiveDate() {
		return startActiveDate;
	}

	public void setStartActiveDate(Date startActiveDate) {
		this.startActiveDate = startActiveDate;
	}

	public Date getEndActiveDate() {
		return endActiveDate;
	}

	public void setEndActiveDate(Date endActiveDate) {
		this.endActiveDate = endActiveDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEnableFlag() {
		return enableFlag;
	}

	public void setEnableFlag(String enableFlag) {
		this.enableFlag = enableFlag;
	}
	

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpNumber() {
		return empNumber;
	}

	public void setEmpNumber(String empNumber) {
		this.empNumber = empNumber;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
    
    
}
