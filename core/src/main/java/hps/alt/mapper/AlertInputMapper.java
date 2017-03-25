package hps.alt.mapper;

import java.util.List;

import com.hand.hap.mybatis.common.Mapper;

import hps.alt.dto.AlertInput;

public interface AlertInputMapper extends Mapper<AlertInput>{

	int insertAlertInputName(AlertInput input);
	
	int deleteAlertInputName(Long alertId);
	
	int updateAltInput(AlertInput input);
	
	
}
