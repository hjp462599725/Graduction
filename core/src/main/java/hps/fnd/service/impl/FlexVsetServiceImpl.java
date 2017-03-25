package hps.fnd.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;

import hps.fnd.controllers.FlexVsetController;
import hps.fnd.dto.FlexValue;
import hps.fnd.dto.FlexVset;
import hps.fnd.mapper.FlexValueMapper;
import hps.fnd.mapper.FlexVsetMapper;
import hps.fnd.service.IFlexVsetService;
import hps.fnd.util.ValidateTableException;

/**
 * @author 作者:fuchun.hu@hand-china.com
 * @version 创建时间：2016年7月2日 下午4:44:14 类说明
 */
@Service
@Transactional
public class FlexVsetServiceImpl extends BaseServiceImpl<FlexVset> implements IFlexVsetService {
	@Autowired
	private FlexVsetMapper flexValueSetMapper;
	@Autowired
	private FlexValueMapper flexValueMapper;

	@Override
	public List<FlexVset> selectFlexValueById(Long flexValueSetId) {
		return flexValueSetMapper.selectFlexValueById(flexValueSetId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * hfs.fnd.service.IFlexValueSetService#selectAllFlexValue(com.hand.hap.core
	 * .IRequest, hfs.fnd.dto.FlexValueSet, int, int)
	 */
	@Override
	public List<FlexVset> selectFlexValueByExm(IRequest iRequest, FlexVset flexVset, int page, int pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(page, pageSize);
		return flexValueSetMapper.selectFlexValueByExm(flexVset);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public Integer saveFlexVset(FlexVset flexValueSet) {
		// TODO Auto-generated method stub
		return flexValueSetMapper.insertSelective(flexValueSet);

	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ValidateTableException.class)
	@Override
	public List<FlexVset> batchUpdateFlexVset(List<FlexVset> flexValueSets, IRequest iRequest)
			throws ValidateTableException {
		// TODO Auto-generated method stub
		for (FlexVset fvs : flexValueSets) {
			if (fvs.getFlexValueSetId() == null) {
				insertSelective(iRequest, fvs);
				if (fvs.getFlexValues() != null) {
					processFlexValue(fvs);
				}
			} else if (fvs.getFlexValueSetId() != null) {
				updateByPrimaryKeySelective(iRequest, fvs);
				if (fvs.getFlexValues() != null) {
					processFlexValue(fvs);
				}
			}
		}
		return flexValueSets;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public void processFlexValue(FlexVset flexValueSet) throws ValidateTableException {
		for (FlexValue fv : flexValueSet.getFlexValues()) {
			fv.setFlexValueSetId(flexValueSet.getFlexValueSetId());// 设置头行ID一致
			int i = -1;
			StringBuffer conditions = new StringBuffer();
			if (fv.getFlexValueId() == null) {
				conditions.append(" flex_value =  '" + fv.getFlexValue() + "'");
				i = flexValueMapper.selectCountF(conditions.toString());
				if (i > 0) {
					throw new ValidateTableException(FlexVsetController.NOT_UNIQUE_FLEX_VALUE, null);
				}
				flexValueMapper.insertSelective(fv);
			} else if (fv.getFlexValueId() != null) {
				conditions.append(
						"  flex_value =  '" + fv.getFlexValue() + "' and flex_value_id <> " + fv.getFlexValueId());
				i = flexValueMapper.selectCountF(conditions.toString());
				if (i > 0) {
					throw new ValidateTableException(FlexVsetController.NOT_UNIQUE_FLEX_VALUE, null);
				}
				flexValueMapper.updateByPrimaryKeySelective(fv);
			}
		}
	}

	@Override
	public List<FlexVset> selectSets(FlexVset flexVset, IRequest iRequest) {
		return flexValueSetMapper.selectFlexValueByExm(flexVset);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public Integer updataFlexVset(FlexVset flexValueSet) {
		// TODO Auto-generated method stub
		return flexValueSetMapper.updateByPrimaryKeySelective(flexValueSet);

	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ValidateTableException.class)
	@Override
	public List<FlexVset> batchUpdateF(IRequest iRequest, List<FlexVset> flexVsets) throws ValidateTableException {
		for (FlexVset flexVset : flexVsets) {
			if (DTOStatus.ADD.equals(flexVset.get__status())) {
				FlexVset condition = new FlexVset();
				condition.setFlexValueSetName(flexVset.getFlexValueSetName());
				List<?> list = flexValueSetMapper.select(condition);
				if (list.size() != 0) {
					throw new ValidateTableException(FlexVsetController.NOT_UNIQUE_FLEX_SET_NAME, null);
				}
				flexValueSetMapper.insert(flexVset);
			} else if (DTOStatus.UPDATE.equals(flexVset.get__status())) {
				flexValueSetMapper.updateByPrimaryKeySelective(flexVset);
			}
		}
		return flexVsets;
	}

}
