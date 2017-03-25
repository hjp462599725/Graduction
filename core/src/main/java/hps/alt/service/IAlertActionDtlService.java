package hps.alt.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;

import hps.alt.dto.AlertActionDtl;
/**
 * 
 * @name IAlertActionDtlService
 * @description 预警活动详情Service
 * @author jieyang03@hand-china.com  2016年9月19日19:50:00
 * @version 1.0
 */
public interface IAlertActionDtlService extends IBaseService<AlertActionDtl>, ProxySelf<IAlertActionDtlService> {
	/**
	 * 保存预警消息通知
	 * @param request 请求
	 * @param alertActionDtls 实体
	 */
	void saveAction(IRequest request, AlertActionDtl alertActionDtls);
	/**
	 * 更新预警消息通知
	 * @param request 请求
	 * @param alertActionDtls 实体
	 */
	void updateAction(IRequest request, AlertActionDtl alertActionDtls);
	
	public List<AlertActionDtl> queryAlertActionDtls(IRequest request,AlertActionDtl alertActionDtls);
	
	public AlertActionDtl queryAlertActionDtl(IRequest request,AlertActionDtl alertActionDtls);
}
