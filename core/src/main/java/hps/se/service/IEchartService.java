package hps.se.service;

import hps.se.dto.Echart;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
/**
 * 
 * @author wenhao
 *
 */
public interface IEchartService extends IBaseService<Echart>,ProxySelf<IEchartService>{
    /**
     * @author wenhao
     * @param request
     * @param echart
     * @return
     */
	List<Echart> selectEchart(IRequest request,Echart echart);
}
