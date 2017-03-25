package hps.org.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.system.service.IBaseService;

import hps.org.dto.Employee;


/**
 * @name IEmployeeService
 * @description 
 * @author xing.gong@hand-china.com 2016年9月8日上午10:12:18
 * @version 1.0
 */
public interface IEmployeeService extends IBaseService<Employee>, ProxySelf<IEmployeeService>{
	
	/**
	 * 查询所有员工信息
	 * @param employees
	 * @param page
	 * @param pagesize
	 * @param request
	 * @return
	 */
	public List<Employee> selectEmployees(Employee employees,int page,int pagesize,IRequest request);
	
	
	/**
	 * 新增员工信息
	 * @param request
	 * @param e
	 */
	public void insertEmpl(IRequest request,@StdWho Employee e);
	
	
	/**
	 * 修改员工信息
	 * @param request
	 * @param e
	 */
	public void updateEmpl(IRequest request,@StdWho Employee e);
	
	
	/**
	 * 清除员工信息
	 * @param e
	 */
	public void removeEmpl(Employee e);
	
	
	/**
	 * 根据id查询员工信息
	 * @param employeeId
	 * @return
	 */
	public List<Employee> selectEmployeesById(Long employeeId);
	
	/**
	 * 删除员工信息
	 * @param e
	 */
	public void deleteEmpl(Employee e);
	


	/**
	 * 校验员工的唯一性
	 * @param employees
	 * @return
	 */
	public Long selectEmployeesUnique(Employee employees);

	/**
	 * 验证员工身份证号的唯一性
	 * @param employees
	 * @return
	 */
	public Long selectEmployeesUniqueCertificate(Employee employees);

	/**
	 * 批量更新
	 * @param request
	 * @param e
     */
	public void batchSubmit(IRequest request,@StdWho Employee e);
	
 
}
