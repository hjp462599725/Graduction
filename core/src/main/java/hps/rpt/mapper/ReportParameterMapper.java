package hps.rpt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hand.hap.mybatis.common.Mapper;

import hps.rpt.dto.ReportParameter;

/**
 * @name ReportParameterMapper
 * @description 报表参数Mapper
 * @author dezhi.shen@hand-china.com 2016年8月25日下午4:16:13
 * @version 1.0
 */
public interface ReportParameterMapper extends Mapper<ReportParameter> {
	/**
	 * 查询数据库中的下一个序列的值
	 * 
	 * @return 数据库中的下一个序列的值
	 */
	public Long selectId();

	/**
	 * 自定义插入方法,此处提前获得了ParamterId,不要调用系统自带的插入
	 * 
	 * @param parameter
	 *            等待插入数据库的报表参数
	 * @return 影响的行数
	 */
	public Integer insertExm(ReportParameter parameter);
	/**
	 * 将行号数组,reportId(报表头Id)传入数据库,返回1 则代表数据库中已存在某个行号 返回空则说明没有
	 * @param rowNums
	 *            所有的行号
	 * @param reportId
	 *            对应的报表头Id
	 * @return 返回1 则代表数据库中已存在某个行号 返回空则说明没有
	 */
	
	/**
	 * 将查询的条件放入sql中，查看计数是否为0
	 * @param conditions
	 * @return count
	 */
	public List<Integer> isUniqueRowNum(@Param(value = "reportId")Long reportId);
}
