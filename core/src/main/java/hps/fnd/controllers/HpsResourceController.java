package hps.fnd.controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.exception.BaseException;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;


import hps.fnd.dto.HpsResource;
import hps.fnd.dto.HpsResourceItem;

import hps.fnd.service.IHpsResourceItemService;
import hps.fnd.service.IHpsResourceService;
/**
 * @name HpsResourceController
 * @description 资源管理Controller
 * @author jie.yang03@hand-china.com  2016年8月19日下午1:16:24
 * @version 1.0
 */
@Controller
public class HpsResourceController extends BaseController {
	@Autowired
	private IHpsResourceService resourceService;
	@Autowired
	private IHpsResourceItemService resourceItemService;
	/**
	 * 查询所有资源页面
	 * @param resource 资源页面对象
	 * @param page 分页
	 * @param pagesize 分页
	 * @param request 请求
	 * @return ResponseData 资源页面对象集合
	 */
	@ResponseBody
	@RequestMapping(value = "/fnd/hpsResource/query", method = RequestMethod.POST)
	public ResponseData selectResource(HpsResource resource, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request){
		IRequest requestContext = createRequestContext(request);
		return new ResponseData(resourceService.selectResource(requestContext, resource, page, pagesize));
	}
	/**
	 * 查询所有资源页面下的控件
	 * @param resourceItem 资源页面控件对象
	 * @param page 分页
	 * @param pagesize 分页
	 * @param request 请求
	 * @return ResponseData 资源页面对象集合
	 */
	@ResponseBody
	@RequestMapping(value = "/fnd/hpsResourceItem/query", method = RequestMethod.POST)
	public ResponseData selectResourceItem(HpsResourceItem resourceItem, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request){
		
		IRequest requestContext = createRequestContext(request);
		return new ResponseData(resourceItemService.selectHpsResourceItem(requestContext, resourceItem, page, pagesize));
	}
	/**
	 * 保存资源信息
	 * @param hpsResources 资源页面对象集合
	 * @param result 验证结果
	 * @param request 请求
	 * @return ResponseData 资源页面对象集合
	 * @throws BaseException
	 */
	@ResponseBody
	@RequestMapping(value = "/fnd/hpsResource/sumit", method = RequestMethod.POST)
	public ResponseData sumitHpsResource(@RequestBody List<HpsResource> hpsResources, BindingResult result, HttpServletRequest request) 
			throws BaseException{
		for(int i=0;i<hpsResources.size();i++){
			if(hpsResources.get(i).get__status() != null){
				if(hpsResources.get(i).get__status().equals("delete")){
					hpsResources.remove(i);
					i--;
				}
			}
			
		}
		
		getValidator().validate(hpsResources, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest requestContext = createRequestContext(request);
		return new ResponseData(resourceService.BatchUpdateHpsResource(requestContext, hpsResources));
	}
	/**
	 * 爬虫爬出页面上的所有form和grid
	 * @param request
	 * @param url
	 * @return form和grid组成的map
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/fnd/hpsResource/import", method = RequestMethod.POST)
	public Map importHpsResource(HttpServletRequest request,String url) throws IOException{
		String basePath = request.getSession().getServletContext()
				.getRealPath("/");
		BufferedReader buff = new BufferedReader(new FileReader(basePath + "WEB-INF//view//"+url));
		String line = null;
		String searchForm = ".*ligerForm";
		String searchGrid = ".*ligerGrid";
		
		Pattern pForm = Pattern.compile(searchForm);
		Pattern pGrid = Pattern.compile(searchGrid);
		
		List<String> formNames = new ArrayList<String>();
		List<String> gridNames = new ArrayList<String>();
		Map map = new HashMap();
		while((line = buff.readLine())!=null){
			Matcher mForm = pForm.matcher(line);
			Matcher mGrid = pGrid.matcher(line);
			while(mGrid.find()){
				String[] tempGridName = mGrid.group().split("#");
				String[] gridName = tempGridName[1].split("\"");
				System.out.println(gridName);
				gridNames.add(gridName[0]);
			}
			while(mForm.find()){
				String[] tempFormName = mForm.group().split("#");
				String[] formName = tempFormName[1].split("\"");
				System.out.println(formName);
				formNames.add(formName[0]);
				
			}
			
			map.put("form", formNames);
			map.put("grid", gridNames);
		}
		
		return map;
		
	}
}
