package hps.fnd.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.system.service.IBaseService;

import hps.fnd.dto.PlugCount;
import hps.fnd.dto.Plugs;
/**
 * 
 * @name IPlugsService
 * @description 插件头Service
 * @author jieyang03@hand-china.com 2016年9月2日09:49:59
 * @version 1.0
 */
public interface IPlugsService extends IBaseService<Plugs>, ProxySelf<IPlugsService> {
	/**
	 * 保存插件头
	 * @param request 请求
	 * @param plug 实体
	 */
	void savePlugs(IRequest request,@StdWho Plugs plug);
	/**
	 * 查询插件头信息
	 * @param request 请求
	 * @param plug 实体
	 * @param page 分页
	 * @param pageSize 分页
	 * @return 实体集合
	 */
	List<Plugs> queryUploadPlugs(IRequest request,Plugs plug, int page, int pageSize);
	/**
	 * 查询插件头信息
	 * @param request 请求
	 * @param plug 实体
	 * @param page 分页
	 * @param pageSize 分页
	 * @return 实体集合
	 */
	List<Plugs> queryDownloadPlugs(IRequest request,Plugs plug, int page, int pageSize);
	/**
	 * 更新插件头信息
	 * @param request 请求
	 * @param plug 实体
	 */
	void updataPlugs(IRequest request,@StdWho Plugs plug);
	/**
	 * 查询文件ID
	 * @param request 请求
	 * @param sourceKey 文件关联外键
	 * @return ID
	 */
	Long selectFileId(IRequest request,Long sourceKey);
	
    /**
     * 
     * @param request
     * @param plugCount
     * @return 组件统计实体集合
     * xianzhi.chen@hand-china.com
     */
	List<PlugCount> queryPlugCount(IRequest request,PlugCount plugCount);
}
