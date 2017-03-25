package hps.alt.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.system.service.IBaseService;

import hps.alt.dto.AltAlert;

public interface IAlertService extends IBaseService<AltAlert>,ProxySelf<IAlertService>{
	
	public List<AltAlert> selectAlert(AltAlert altAlert, int page,int pagesize);

	
	int insertAlert(AltAlert altAlert);
	
	int updateAlert(AltAlert altAlert);
	
	public List<AltAlert> selectTableOwner(AltAlert altAlert);
	
	List<AltAlert> myBatchUpdate(IRequest request, @StdWho List<AltAlert> altAlerts);
	
	
}
