package hps.se.mapper;

import hps.se.dto.Echart;

import java.util.List;

import com.hand.hap.mybatis.common.Mapper;
/**
 * 
 * @author wenhao
 * @version 1.0
 */
public interface EchartMapper extends Mapper<Echart>{
	/**
	 * @author wenhao
	 * @param echart
	 * @return
	 */
	List<Echart> selectEchart(Echart echart);

}
