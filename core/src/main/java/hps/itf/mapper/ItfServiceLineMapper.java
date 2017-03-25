package hps.itf.mapper;

import com.hand.hap.mybatis.common.Mapper;

import hps.itf.dto.ItfServiceLine;

/**
 * 
 * @name ItfServiceLineMapper
 * @description 接口行表CRUD 
 * @author jianping.huo@hand-china.com  2016年8月22日下午1:33:56
 * @version 1.0
 */
public interface ItfServiceLineMapper extends Mapper<ItfServiceLine> {
	/**
	 * 根据接口头Id和接口代码查询行信息
	 * @param line 接口行DTO
	 * @return ItfServiceLine 接口行DTO
	 */
	ItfServiceLine selectByHeaderIdAndCode(ItfServiceLine line);
	
	/**
	 * 用于取出所有行表信息存放到redis中
	 * @return
	 */
	ItfServiceLine selectAllLine();
}