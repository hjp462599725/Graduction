package hps.se.controllers;

import hps.se.dto.Echart;
import hps.se.service.IEchartService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
/**
 * 
 * @author wenhao
 * @version 1.0
 */
@Controller
@RequestMapping(value="/echart")
public class EchartController extends BaseController{
	
	@Autowired
	private IEchartService echartService;
    /**
     * @author wenhao
     * @param requestContext
     * @param response
     * @return
     * @throws Exception
     */
	@RequestMapping(value="/histogram")
	public ResponseData gethistogram(HttpServletRequest requestContext,HttpServletResponse response)throws Exception{
		IRequest request = createRequestContext(requestContext);
		Echart echart=new Echart();						
		return  new ResponseData(echartService.selectEchart(request, echart));
		
	}
	

}
