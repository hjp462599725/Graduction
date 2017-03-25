package hps.rpt.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.IBaseService;

import hps.rpt.dto.ReportParameter;
import hps.rpt.util.RptException;
/**
 * @name IReportParameterService
 * @description 报表参数Service
 * @author dezhi.shen@hand-china.com	2016年8月25日下午4:23:08
 * @version 1.0
 */
public interface IReportParameterService extends IBaseService<ReportParameter> {
	/**
	 * 自定义插入方法,此处提前获得了ParamterId,不要调用系统自带的插入
	 * 
	 * @param parameter
	 *            等待插入数据库的报表参数
	 * @return 影响的行数
	 */
	Long selectId();

	/**
	 * 将待验证rowNum唯一性的参数对象,在数据库中进行验证
	 * 
	 * @param parameter
	 *            待验证rowNum唯一性的参数对象
	 * @return 
	 */
	boolean isUniqueRowNum(ReportParameter parameter) ;
	/**
	 * 保存更新批量参数
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public List<ReportParameter> batchUpdateRptParam(IRequest arg0, List<ReportParameter> arg1) throws RptException;

}
