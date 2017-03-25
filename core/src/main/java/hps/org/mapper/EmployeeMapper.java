package hps.org.mapper;

import java.util.List;
import java.util.Map;
import com.hand.hap.mybatis.common.Mapper;

import hps.org.dto.Employee;


/**
 * @name EmployeeMapper
 * @description 
 * @author xing.gong@hand-china.com 2016年9月8日上午10:12:06
 * @version 1.0
 */
public interface EmployeeMapper extends Mapper<Employee>{
	
	/**
	 * 查询所有员工信息
	 * @param employees
	 * @return
	 */
	public List<Employee> selectEmployees(Employee employees);
	
	
	/**
	 * 新增员工信息
	 * @param e
	 */
    public void insertEmpl(Employee e);
    
  
    /**
     * 修改员工信息
     * @param e
     */
  	public void updateEmpl(Employee e);
  	
  
  	/**
  	 * 删除员工信息
  	 * @param e
  	 */
  	public void removeEmpl(Employee e);
  	
  
  	/**
  	 * 根据id查询员工信息 
  	 * @param employeeId
  	 * @return
  	 */
  	public List<Employee> selectEmployeesById(Long employeeId);
  	
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
  

}
