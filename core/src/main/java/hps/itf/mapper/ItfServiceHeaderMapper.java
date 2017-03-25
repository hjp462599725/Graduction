package hps.itf.mapper;

import com.hand.hap.mybatis.common.Mapper;

import hps.itf.dto.ItfServiceHeader;

/**
 * 
 * @name ItfServiceHeaderMapper
 * @description 接口头表CRUD
 * @author jianping.huo@hand-china.com  2016年8月22日下午1:30:50
 * @version 1.0
 */
public interface ItfServiceHeaderMapper extends Mapper<ItfServiceHeader> {
	/**
	 * 增添接口头信息
	 * @param header 接口头DTO
	 * @return int 受影响的数据
	 */
	int insertHeader(ItfServiceHeader header);
	/**
	 * 更新接口头数据
	 * @param header 接口头DTO
	 * @return int 受影响的数据
	 */
	int updateHeader(ItfServiceHeader header);
	/**
	 * 根据系统代码和enableFlag查找接口头信息
	 * @param header 接口头DTO
	 * @return ItfServiceHeader 接口头DTO
	 */
	ItfServiceHeader selectByCodeAndEnableFlag(ItfServiceHeader header);
	
	/**
	 * 用于取出所有行表信息存放到redis中
	 * @return
	 */
	ItfServiceHeader selectAllHeader();
}
