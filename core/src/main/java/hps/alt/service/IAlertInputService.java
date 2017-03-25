package hps.alt.service;

import java.util.List;

import com.hand.hap.system.service.IBaseService;

import hps.alt.dto.AlertInput;

public interface IAlertInputService extends IBaseService<AlertInput>{

	int insertAlertInput(AlertInput alertInput);
	
	int deleteAlertInput(Long alertId);
	
	int updateAltInput(AlertInput alertInput);
	
	
	
}
