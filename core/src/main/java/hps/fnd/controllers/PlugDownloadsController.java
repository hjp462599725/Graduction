package hps.fnd.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;

import hps.fnd.dto.PlugDownloads;
import hps.fnd.service.IPlugDownloadsService;

/**
 * 
 * @name PlugDownloadsController
 * @description 插件下载信息controller层
 * @author jieyang03@hand-china.com  2016年8月30日15:34:22
 * @version 1.0
 */
@Controller
public class PlugDownloadsController extends BaseController{
	@Autowired
	private IPlugDownloadsService plugDownloadsService;
	/**
	 * 保存下载目的信息
	 * @param plugDownloads 实体类
	 * @param result 验证
	 * @param request 请求
	 * @param plugId 插件头ID
	 * @param plugVersionId 插件版本ID
	 * @return ResponseData
	 */
	@ResponseBody
	@RequestMapping(value = "/fnd/plugDownloads/save", method = RequestMethod.POST)
	public ResponseData savePlugDownloads(@RequestBody List<PlugDownloads> plugDownloads,BindingResult result,HttpServletRequest request,
			String plugId,String plugVersionId){
		getValidator().validate(plugDownloads, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        } else {
        	
        	IRequest requestContext = createRequestContext(request);
        	for(PlugDownloads p : plugDownloads){
        		p.setPlugId(Long.valueOf(plugId));
            	p.setPlugVersionId(Long.valueOf(plugVersionId));
            	plugDownloadsService.savePlugDownloads(requestContext, p);
        	}
        }
        return new ResponseData(plugDownloads);
	}
}
