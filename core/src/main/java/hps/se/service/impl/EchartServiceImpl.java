package hps.se.service.impl;

import hps.se.dto.Echart;

import hps.se.mapper.EchartMapper;
import hps.se.service.IEchartService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
/**
 * 
 * @author wenhao
 *
 */
@Service
public class EchartServiceImpl extends BaseServiceImpl<Echart> implements IEchartService{

	@Autowired
	private EchartMapper echartMapper;
	/**
	 * @author wenhao
	 */
	@Override
	public List<Echart> selectEchart(IRequest requset,Echart echart){		
		return echartMapper.selectEchart(echart);
		
	}
}
