package hps.itf.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;

import hps.itf.dto.ItfServiceLine;
import hps.itf.service.IItfServiceLineService;

@Service
public class ItfServiceLineServiceImpl extends BaseServiceImpl<ItfServiceLine> implements IItfServiceLineService {
	
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public List<ItfServiceLine> myBatchUpdate(IRequest request, List<ItfServiceLine> lines) {
		// TODO Auto-generated method stub
		for (ItfServiceLine line : lines) {
            if (line.get__status() != null) {
                switch (line.get__status()) {
                case DTOStatus.ADD:
                	self().insertSelective(request, line);
                    break;
                case DTOStatus.UPDATE:
                	self().updateByPrimaryKeySelective(request, line);
                    break;
                default:
                    break;
                }
            }
        }
        return lines;
	}
}
