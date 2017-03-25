package hps.alt.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.IBaseService;

import hps.alt.dto.AlertOutput;

public interface IAlertOutputService extends IBaseService<AlertOutput>{
	
	int insertAlertOutput(AlertOutput alertOutput);
	
	int deleteAlertOutput(Long alertId);
	
	List<AlertOutput> queruyAlertOutputs(IRequest request,AlertOutput alertOutput);
}
