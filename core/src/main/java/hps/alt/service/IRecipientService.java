package hps.alt.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.system.service.IBaseService;

import hps.alt.dto.Recipient;
import hps.org.dto.Employee;


/**
 * @name IRecipientService
 * @description 
 * @author xing.gong@hand-china.com 2016年9月8日上午10:05:12
 * @version 1.0
 */
public interface IRecipientService extends IBaseService<Recipient>, ProxySelf<IRecipientService>{
	
	/**
	 * 查询所有收件人列表信息
	 * @param Recipient
	 * @param page
	 * @param pagesize
	 * @param request
	 * @return
	 */
	public List<Recipient> selectRecipient(Recipient recipient,int page,int pagesize,IRequest request);
	
	
	/**
	 * 新增收件人列表信息
	 * @param request
	 * @param r
	 */
	public void insertRecipient(IRequest request,@StdWho Recipient r);
	
	
	/**
	 * 修改收件人列表信息
	 * @param request
	 * @param r
	 */
	public void updateRecipient(IRequest request,@StdWho Recipient r);
	
	
	/**
	 * 删除收件人列表信息
	 * @param r
	 */
	public void removeRecipient(Recipient r);
	
	
	/**
	 * 根据id查询收件人列表信息
	 * @param recipientListId
	 * @return
	 */
	public List<Recipient> selectRecipientById(Long recipientListId);

	public List<Employee> queryRecipientDatil(IRequest request,Long recipientListId,int page, int pagesize);
}
