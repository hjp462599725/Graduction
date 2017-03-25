package hps.fnd.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;

import hps.fnd.dto.FlexValue;
import hps.fnd.mapper.FlexValueMapper;
import hps.fnd.service.IFlexValueService;
/**
 * @name FlexValueServiceImpl
 * @description 
 * @author dezhi.shen@hand-china.com	2016年9月7日下午1:52:19
 * @version 1.0
 */
@Service
@Transactional
public class FlexValueServiceImpl extends BaseServiceImpl<FlexValue> implements IFlexValueService {
	@Autowired
	private FlexValueMapper flexValueMapper;

	@Override
	public List<FlexValue> selectEnableFlexValuesByExm(IRequest iRequest, FlexValue flexValue) {
		// TODO Auto-generated method stub
		return flexValueMapper.selectEnableFlexValuesByExm(flexValue);
	}

	@Override
	public List<FlexValue> selectFlexValuesByExm(IRequest iRequest, FlexValue flexValue) {
		// TODO Auto-generated method stub
		return flexValueMapper.selectFlexValuesByExm(flexValue);
	}

	@Override
	public List<FlexValue> selectEnableFlexValuesByExm(IRequest iRequest, FlexValue flexValue, int page, int pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(page, pageSize);
		return flexValueMapper.selectEnableFlexValuesByExm(flexValue);
	}

	@Override
	public List<FlexValue> selectFlexValuesByExm(IRequest iRequest, FlexValue flexValue, int page, int pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(page, pageSize);
		return flexValueMapper.selectFlexValuesByExm(flexValue);
	}

	/*
	 * @Override public List<FlexValue> selectFlexValue(Long flexValueSetId,int
	 * page,int pagesize) { PageHelper.startPage(page, pagesize); return
	 * flexValueMapper.selectFlexValue(flexValueSetId); }
	 * 
	 * @Override public List<FlexValue> selectFlexValues(FlexValue flexValue,
	 * int page, int pagesize) { PageHelper.startPage(page, pagesize); return
	 * flexValueMapper.selectFlexValues(flexValue); }
	 * 
	 * @Override public List<FlexValue> selectFlexValueById(Long flexValueSetId,
	 * int page, int pagesize) { // TODO Auto-generated method stub
	 * PageHelper.startPage(page, pagesize); return
	 * flexValueMapper.selectFlexValueById(flexValueSetId); }
	 */

}
