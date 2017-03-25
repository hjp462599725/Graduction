package hps.alt.mapper;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Mapper;

import hps.alt.dto.Recipient;
import hps.org.dto.Employee;


/**
 * @name RecipientMapper
 * @description 
 * @author xing.gong@hand-china.com 2016年9月8日上午10:04:48
 * @version 1.0
 */
public interface RecipientMapper extends Mapper<Recipient>{
	
	/**
	 * 查询所有收件人列表信息
	 * @param Recipient
	 * @return
	 */
	public List<Recipient> selectRecipient(Recipient recipient);
	
	
	/**
	 * 新增收件人列表信息
	 * @param r
	 */
    public void insertRecipient(Recipient r);
    
  
    /**
     * 修改收件人列表信息
     * @param r
     */
  	public void updateRecipient(Recipient r);
  	
  
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
  	
  	
  	public List<Employee> queryRecipientDatil(Long recipientListId);

}
