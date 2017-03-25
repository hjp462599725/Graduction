package hps.fnd.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.system.service.IBaseService;

import hps.fnd.dto.PlugVersions;
import hps.fnd.dto.Plugs;
/**
 * 
 * @name IPlugVersionsService
 * @description 插件行Service
 * @author jieyang03@hand-china.com 2016年9月2日09:49:59
 * @version 1.0
 */
public interface IPlugVersionsService extends IBaseService<PlugVersions>, ProxySelf<IPlugVersionsService> {
	/**
	 * 保存插件行信息
	 * @param request 请求
	 * @param plugVersions 实体
	 */
	void savePlugVersions(IRequest request,@StdWho PlugVersions plugVersions);
	
	/**
	 * 更新插件行信息
	 * @param request 请求
	 * @param plugVersions 实体
	 */
	void updataPlugVersions(IRequest request,@StdWho PlugVersions plugVersions);
	/**
	 * 查询下载页面插件行信息
	 * @param request 请求
	 * @param plug 实体
	 * @param page 分页
	 * @param pageSize 分页
	 * @return 实体集合
	 */
	List<PlugVersions> queryPlugVersions(IRequest request,PlugVersions plugVersions, int page, int pageSize);
	/**
	 * 查询上传页面插件行信息
	 * @param request 请求
	 * @param plug 实体
	 * @param page 分页
	 * @param pageSize 分页
	 * @return 实体集合
	 */
	List<PlugVersions> queryUploadPlugVersions(IRequest request,PlugVersions plugVersions, int page, int pageSize);
	/**
	 * 版本唯一性验证
	 * @param plugVersions 实体
	 * @return 个数
	 */
	int SelectVersionsOnlyValidate(PlugVersions plugVersions);
	
}
