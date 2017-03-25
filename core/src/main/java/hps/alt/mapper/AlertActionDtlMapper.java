package hps.alt.mapper;

import java.util.List;

import com.hand.hap.mybatis.common.Mapper;

import hps.alt.dto.AlertActionDtl;
/**
 * 
 * @name AlertActionDtlMapper
 * @description 预警活动详情Mapper
 * @author jieyang03@hand-china.com  2016年9月19日19:50:00
 * @version 1.0
 */
public interface AlertActionDtlMapper extends Mapper<AlertActionDtl> {

	
	AlertActionDtl queryAlertActionDtl(AlertActionDtl alertActionDtl);
	
	List<AlertActionDtl> queryAlertActionDtls(AlertActionDtl alertActionDtl);
}
