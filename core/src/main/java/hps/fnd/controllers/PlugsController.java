package hps.fnd.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hand.hap.attachment.dto.SysFile;
import com.hand.hap.attachment.exception.FileReadIOException;
import com.hand.hap.attachment.service.ISysFileService;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;

import hps.fnd.dto.PlugCount;
import hps.fnd.dto.PlugVersions;
import hps.fnd.dto.Plugs;
import hps.fnd.service.IPlugVersionsService;
import hps.fnd.service.IPlugsService;

/**
 * 
 * @name PlugsController
 * @description 插件上传controller层
 * @author jieyang03@hand-china.com  2016年8月30日15:34:22
 * @version 1.0
 */
@Controller
public class PlugsController extends BaseController{
	
    /**
     * 文件下载默认编码.
     */
    private static final String ENC = "UTF-8";
    /**
     * buffer 大小.
     */
    private static final Integer BUFFER_SIZE = 1024;
    /**
     * 文件不存在提示.
     */
    private static final String FILE_NOT_EXSIT = "file_not_exsit";
	
	@Autowired 
	private IPlugsService plugsService;
	@Autowired
	private IPlugVersionsService plugVersionsService;
	@Autowired
	private ISysFileService fileService;
	/**
	 * 保存插件头信息
	 * @param plugs 实体集合
	 * @param result 验证
	 * @param request 请求
	 * @return 实体集合
	 */
	@ResponseBody
	@RequestMapping(value = "/fnd/plugs/save", method = RequestMethod.POST)
	public ResponseData savePlugs(@RequestBody List<Plugs> plugs,BindingResult result,HttpServletRequest request){
		getValidator().validate(plugs, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        } else {
        	IRequest requestCtx = createRequestContext(request);
			for(Plugs p : plugs){
				if(p.getPlugId() == null){
					plugsService.savePlugs(requestCtx, p);
				}else{
					plugsService.updataPlugs(requestCtx, p);
				}
				
			}
        }
		return new ResponseData(plugs);
	}
	/**
	 * 查询上传页面头信息
	 * @param plug 实体
	 * @param page 分页
	 * @param pagesize 分页
	 * @param request 请求
	 * @return 实体集合
	 */
	@ResponseBody
	@RequestMapping(value = "/fnd/upload/plugs/query", method = RequestMethod.POST)
	public ResponseData queryUploadPlugs(Plugs plug , @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request){
		IRequest requestContext = createRequestContext(request);
		return new ResponseData(plugsService.queryUploadPlugs(requestContext, plug, page, pagesize));
	}
	
	/**
	 * 查询下载页面头信息
	 * @param plug 实体
	 * @param page 分页
	 * @param pagesize 分页
	 * @param request 请求
	 * @return 实体集合
	 */
	@ResponseBody
	@RequestMapping(value = "/fnd/download/plugs/query", method = RequestMethod.POST)
	public ResponseData queryDownloadPlugs(Plugs plug , @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request){
		IRequest requestContext = createRequestContext(request);
		return new ResponseData(plugsService.queryDownloadPlugs(requestContext, plug, page, pagesize));
	}
	
	/**
	 * 批量保存行
	 * @param plugId 头ID
	 * @param plugVersions 实体
	 * @param result 验证
	 * @param request 请求 
	 * @return 实体集合
	 */
	@ResponseBody
	@RequestMapping(value = "/fnd/plugVersions/save", method = RequestMethod.POST)
	public ResponseData savePlugVersions(@RequestBody List<PlugVersions> plugVersions,String plugId,BindingResult result,HttpServletRequest request){
		getValidator().validate(plugVersions, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        } else {
        	IRequest requestCtx = createRequestContext(request);
        	for(PlugVersions p : plugVersions){
        		if(p.getPlugVersionId() == null){
        			p.setPlugId(Long.parseLong(plugId));
        			plugVersionsService.savePlugVersions(requestCtx, p);
        		}else{
        			plugVersionsService.updataPlugVersions(requestCtx, p);
        		}
        	}
        }
		return new ResponseData(plugVersions);
	}
	/**
	 * 查询上传页面行数据
	 * @param plugVersions 行实体
	 * @param page 分页
	 * @param pagesize 分页
	 * @param request 请求
	 * @return 集合
	 */
	@ResponseBody
	@RequestMapping(value = "/fnd/upload/plugVersions/query", method = RequestMethod.POST)
	public ResponseData queryUploadPlugVersions(PlugVersions plugVersions , @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request){
		IRequest requestContext = createRequestContext(request);
		return new ResponseData(plugVersionsService.queryUploadPlugVersions(requestContext, plugVersions, page, pagesize));
	}
	
	/**
	 * 查询下载页面行数据
	 * @param plugVersions 行实体
	 * @param page 分页
	 * @param pagesize 分页
	 * @param request 请求
	 * @return 集合
	 */
	@ResponseBody
	@RequestMapping(value = "/fnd/download/plugVersions/query", method = RequestMethod.POST)
	public ResponseData queryDownloadPlugVersions(PlugVersions plugVersions , @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request){
		IRequest requestContext = createRequestContext(request);
		return new ResponseData(plugVersionsService.queryPlugVersions(requestContext, plugVersions, page, pagesize));
	}
	
	
	/**
     * 将文件对象的流写入Responsne对象.
     * 
     * @param response
     *            HttpServletResponse
     * @param file
     *            File
     * @throws FileNotFoundException
     *             找不到文件异常
     * @throws IOException
     *             IO异常
     */
    private void writeFileToResp(HttpServletResponse response, File file) throws FileNotFoundException, IOException {
        byte[] buf = new byte[BUFFER_SIZE];
        try (InputStream inStream = new FileInputStream(file);
                ServletOutputStream outputStream = response.getOutputStream()) {
            int readLength;
            while (((readLength = inStream.read(buf)) != -1)) {
                outputStream.write(buf, 0, readLength);
            }
            outputStream.flush();
        }
    }
	
	/**
	 * 下载
	 * @param request 请求
	 * @param response 响应
	 * @param sourceKey 关联外键
	 * @throws FileReadIOException 异常
	 */
	@RequestMapping(value = "/fnd/plugs/download")
	public void downloadPlugs(HttpServletRequest request, HttpServletResponse response,String sourceKey) throws FileReadIOException{
		IRequest requestContext = createRequestContext(request);
		Long fileId = plugsService.selectFileId(requestContext,Long.valueOf(sourceKey));
		PlugVersions p = new PlugVersions();
		p.setPlugVersionId(Long.valueOf(sourceKey));
		p = plugVersionsService.selectByPrimaryKey(requestContext, p);
		SysFile sysFile = fileService.selectByPrimaryKey(requestContext,fileId);
		try {
            if (sysFile != null && StringUtils.isNotBlank(sysFile.getFilePath())) {
            	System.out.println(sysFile.getFilePath());
                File file = new File(sysFile.getFilePath());
                if (file.exists()) {
                    // 下载
                    if (StringUtils.isNotBlank(sysFile.getFileType())) {
                        response.addHeader("Content-Disposition",
                                "attachment;filename=\"" + URLEncoder.encode(sysFile.getFileName(), ENC) + "\"");
                    }
                    response.setContentType(sysFile.getFileType() + ";charset=" + ENC);
                    response.setHeader("Accept-Ranges", "bytes");
                    int fileLength = (int) file.length();
                    response.setContentLength(fileLength);
                    if (fileLength > 0) {
                        writeFileToResp(response, file);
                        //每次下载次数递增
                        Long count;
                        if(p.getDownloadCount() == null){
                        	 count = new Long(1);
                        }else{
                        	 count = p.getDownloadCount() + 1;
                        }
                        p.setDownloadCount(count);
                        plugVersionsService.updataPlugVersions(requestContext, p);
                    }
                } else {
                    response.getWriter().write(FILE_NOT_EXSIT);
                }
            } else {
                response.getWriter().write(FILE_NOT_EXSIT);
            }
        } catch (IOException e) {
            throw new FileReadIOException();
        }
	}
	/**
	 * 版本唯一性验证
	 * @param plugVersions 实体
	 * @return 查询出的个数
	 */
	@ResponseBody
	@RequestMapping(value = "/fnd/plugVersions/onlyValidate", method = RequestMethod.POST)
	public int onlyValidateValue(PlugVersions plugVersions){
		return plugVersionsService.SelectVersionsOnlyValidate(plugVersions);
	}
	
	/**
	 * 获取插件统计数据
	 * @param request
	 * @param response
	 * @return 插件统计实体集合
	 * @throws Exception
	 * xianzhi.chen@hand-china.com
	 */
	@RequestMapping(value="/fnd/plugCount/query")
	public ResponseData queryPlugCount(HttpServletRequest request,HttpServletResponse response)throws Exception{
		IRequest requestContext = createRequestContext(request);
		return  new ResponseData(plugsService.queryPlugCount(requestContext, new PlugCount()));
		
	}
	
}
