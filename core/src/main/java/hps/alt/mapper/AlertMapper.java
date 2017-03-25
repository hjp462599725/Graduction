package hps.alt.mapper;

import java.util.List;

import com.hand.hap.mybatis.common.Mapper;

import hps.alt.dto.AltAlert;

public interface AlertMapper extends Mapper<AltAlert>{
	
	List<AltAlert> selectAlert(AltAlert altAlert); 
	
	int insertAlert(AltAlert altAlert);
	
	int updateAlert(AltAlert altAlert);
	
	List<AltAlert> selectTable(AltAlert altAlert);
	
}
