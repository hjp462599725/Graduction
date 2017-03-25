package hps.org.mapper;

import java.util.List;

import com.hand.hap.mybatis.common.Mapper;

import hps.org.dto.HpsUser;

public interface HpsUserMapper extends Mapper<HpsUser> {
	public List<HpsUser> selectHpsUser(HpsUser hpsUser);
}
