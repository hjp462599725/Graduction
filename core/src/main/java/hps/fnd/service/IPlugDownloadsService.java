package hps.fnd.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.system.service.IBaseService;

import hps.fnd.dto.PlugDownloads;

public interface IPlugDownloadsService extends IBaseService<PlugDownloads>, ProxySelf<IPlugDownloadsService> {
	/**
	 * 保存下载信息
	 * @param request 请求
	 * @param plugDownloads 实体
	 */
	void savePlugDownloads(IRequest request,@StdWho PlugDownloads plugDownloads);
}
