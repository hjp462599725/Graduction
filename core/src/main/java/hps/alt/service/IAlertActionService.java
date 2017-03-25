package hps.alt.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;

import hps.alt.dto.AlertAction;
import hps.alt.dto.AlertActionDtl;

public interface IAlertActionService extends IBaseService<AlertAction>,ProxySelf<IAlertActionService>{
	/**
	 * 查询对应的预警活动
	 * @param request 请求
	 * @param alertAction 实体
	 * @return 实体对象集合
	 */
	List<AlertAction> selectAlertActions(IRequest request,AlertAction alertAction);
}
