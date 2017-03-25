package hps.org.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.system.service.impl.BaseServiceImpl;

import hps.org.dto.Employee;
import hps.org.mapper.EmployeeMapper;
import hps.org.service.IEmployeeService;

@Service
@Transactional
public class EmployeeServiceImpl extends BaseServiceImpl<Employee> implements IEmployeeService {

	

	@Autowired
	private EmployeeMapper employeesMapper;

	@Override
	public List<Employee> selectEmployees(Employee employees, int page, int pagesize, IRequest request) {
		PageHelper.startPage(page, pagesize);
		return employeesMapper.selectEmployees(employees);
	}

	@Override
	public void insertEmpl(IRequest request,Employee e) {		
		employeesMapper.insertEmpl(e);
 
	}

	@Override
	public void updateEmpl(IRequest request,Employee e) {
		employeesMapper.updateEmpl(e);

	}

	@Override
	public void removeEmpl(Employee e) {
		employeesMapper.removeEmpl(e);

	}

	@Override
	public List<Employee> selectEmployeesById(Long employeeId) {
		return employeesMapper.selectEmployeesById(employeeId);
	}
	
	@Override
	public void deleteEmpl(Employee e) {
		 employeesMapper.deleteEmpl(e);
	}
	
	@Override
	public Long selectEmployeesUnique(Employee employees) {
		return employeesMapper.selectEmployeesUnique(employees);
	}

	@Override
	public Long selectEmployeesUniqueCertificate(Employee employees) {
		return employeesMapper.selectEmployeesUniqueCertificate(employees);
	}

	@Override
	public void batchSubmit(IRequest request, @StdWho Employee e) {
		if(e.getEmployeeId()==null){
			employeesMapper.insertEmpl(e);
		}else{
			employeesMapper.updateEmpl(e);
		}

}
}