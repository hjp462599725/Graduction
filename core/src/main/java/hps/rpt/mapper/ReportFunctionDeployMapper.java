package hps.rpt.mapper;

import java.util.List;

import com.hand.hap.function.dto.Function;
import com.hand.hap.function.dto.Resource;
/**
 * @name ReportFunctionDeployMapper
 * @description 报表功能部署Mapper
 * @author chengye.hu@hand-china.com	2016年8月25日下午4:17:54
 * @version 1.0
 */
public interface ReportFunctionDeployMapper {
	/**
	 * 查找资源
	 * @param resource
	 * @return
	 */
	Resource selectResourceKey(Resource resource);
	/**
	 * 查找功能
	 * @param function
	 * @return
	 */
	Function selectFunctionKey(Function function);
	/**
	 * 查找所有资源
	 * @return
	 */
	List<Resource> selectResourceAll();
	/**
	 * 查找所有功能
	 * @return
	 */
	List<Function> selectFunctionAll();

}
