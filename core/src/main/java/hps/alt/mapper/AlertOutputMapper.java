package hps.alt.mapper;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Mapper;

import hps.alt.dto.AlertOutput;

public interface AlertOutputMapper extends Mapper<AlertOutput>{
	
	int insertAltOutputName(AlertOutput alertOutput);

	int deleteAltOutputName(Long alertId);
	
	List<AlertOutput> queruyAlertOutputs(AlertOutput alertOutput);
}
