package hps.itf.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;

import hps.itf.dto.ItfServiceHeader;
import hps.itf.mapper.ItfServiceHeaderMapper;
import hps.itf.service.IItfServiceHeaderService;

@Service
public class ItfServiceHeaderServiceImpl extends BaseServiceImpl<ItfServiceHeader> implements IItfServiceHeaderService {
	@Autowired
	private ItfServiceHeaderMapper headMapper;
	
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public List<ItfServiceHeader> myBatchUpdate(IRequest request, List<ItfServiceHeader> headers) {
		// TODO Auto-generated method stub
		for (ItfServiceHeader header : headers) {
			if(header.getServiceHeaderId() != null){
				headMapper.updateHeader(header);
			}else if(header.getServiceHeaderId() == null){
				headMapper.insertHeader(header);
			}
		}
		return headers;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public List<ItfServiceHeader> pasue(IRequest request, List<ItfServiceHeader> headers) {
		// TODO Auto-generated method stub
		for (ItfServiceHeader itfServiceHeader : headers) {
			if(itfServiceHeader.getEnableFlag() != null && itfServiceHeader.getEnableFlag().equals("Y")){
				itfServiceHeader.setEnableFlag("N");
			}
			headMapper.updateHeader(itfServiceHeader);
		}
		return headers;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public List<ItfServiceHeader> resume(IRequest request, List<ItfServiceHeader> headers) {
		// TODO Auto-generated method stub
		for (ItfServiceHeader itfServiceHeader : headers) {
			if(itfServiceHeader.getEnableFlag() != null && itfServiceHeader.getEnableFlag().equals("N")){
				itfServiceHeader.setEnableFlag("Y");
			}
			headMapper.updateHeader(itfServiceHeader);
		}
		return headers;
	}
}
