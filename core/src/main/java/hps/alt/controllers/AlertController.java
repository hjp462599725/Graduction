package hps.alt.controllers;

import java.sql.Connection;
import hps.alt.util.SqlUtil;
import hps.fnd.dto.MailReceiver;
import hps.fnd.util.MailHelper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.job.AbstractJob;
import com.hand.hap.job.dto.JobData;
import com.hand.hap.job.dto.JobDetailDto;
import com.hand.hap.job.exception.JobException;
import com.hand.hap.job.mapper.JobDetailMapper;
import com.hand.hap.job.service.IQuartzService;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;

import hps.alt.dto.AlertAction;
import hps.alt.dto.AlertActionDtl;
import hps.alt.dto.AlertInput;
import hps.alt.dto.AlertOutput;
import hps.alt.dto.AltAlert;
import hps.alt.service.IAlertActionDtlService;
import hps.alt.service.IAlertActionService;
import hps.alt.service.IAlertInputService;
import hps.alt.service.IAlertOutputService;
import hps.alt.service.IAlertService;
import static com.hand.hap.job.exception.JobException.JOB_EXCEPTION;
import static com.hand.hap.job.exception.JobException.NOT_SUB_CLASS;


@Controller
public class AlertController extends BaseController {
	
	private static Logger logger = LoggerFactory.getLogger("AlertController");
	private static String loggerHeader = "alertValidateData ";
	
	@Autowired
	private IAlertService alertService;
	
	@Autowired
	private IAlertInputService alertInputService;
	
	@Autowired
	private IAlertOutputService alertOutputService;
	
	@Autowired
	private BeanFactory beanFactory; 
	
	@Autowired
	private IAlertActionService alertActionService;
	@Autowired
	private IAlertActionDtlService alertActionDtlService;
	
	@Autowired
	private MailHelper mailHelper;
	@Autowired
    private Scheduler quartzScheduler;
	@Autowired
	private IQuartzService quartzService;
	@Autowired
	private JobDetailMapper jobDetailMapper;
	
	/**
	 * @description  预警汇总查询
	 * @param altAlert
	 * @param page
	 * @param pagesize
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/alt/alert/query")
	@ResponseBody
	public ResponseData selectAlert(AltAlert altAlert, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
			@RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request) {
		
		@SuppressWarnings("unused")
		IRequest requestCtx = createRequestContext(request);
		PageHelper.startPage(page, pagesize);
		List<AltAlert> list = alertService.selectAlert(altAlert, page, pagesize);
		return new ResponseData(list);
	}
	
	@RequestMapping(value="/alt/alert/queryone")
	@ResponseBody
	public ResponseData select(AltAlert altAlert, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
			@RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request) {
		
		IRequest requestCtx = createRequestContext(request);
		List<AltAlert> list = alertService.select(requestCtx, altAlert, page, pagesize);
		return new ResponseData(list);
	}
	
	
	@RequestMapping(value="/alt/alert/remove")
	public ResponseData removeAlert(HttpServletRequest request,@RequestBody List<AltAlert> altAlerts){
		alertService.batchDelete(altAlerts);
		for (AltAlert altAlert : altAlerts) {
			alertInputService.deleteAlertInput(altAlert.getAlertId());
			alertOutputService.deleteAlertOutput(altAlert.getAlertId());
		}
		return new ResponseData();
	}
	
	
	/**
	 * @description 预警信息保存与修改
	 * @param request
	 * @param altAlert
	 * @return
	 */
	@RequestMapping(value="/alt/alert/submit")
	public ResponseData submitAlert(HttpServletRequest request,AltAlert altAlert){
		if (altAlert.getAlertId()==null) {
			alertService.insertAlert(altAlert);
		}else{
			alertService.updateAlert(altAlert);
		}
		List<AltAlert> alertList = new ArrayList<AltAlert>();
		alertList.add(altAlert);
		return new ResponseData(alertList);
	}
	
	
	/**
	 * @description 根据表用户查询
	 * @param request
	 * @param altAlert
	 * @param page
	 * @param pagesize
	 * @return
	 */
	@RequestMapping(value="/alt/alert/table")
	public ResponseData selectTable(HttpServletRequest request,AltAlert altAlert,@RequestParam(defaultValue = DEFAULT_PAGE) int page,
			@RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize){
		PageHelper.startPage(page, pagesize);

		return new ResponseData(alertService.selectTableOwner(altAlert));
		
		
	}
	
	/**
	 * @description  预警活动的批量增改
	 * @param altAlerts
	 * @param result
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/alt/alert/action/submit")
	public ResponseData submitAlertAction(@RequestBody List<AltAlert> altAlerts, BindingResult result, HttpServletRequest request) {
		
		IRequest requestContext = createRequestContext(request);
		
		
		return new ResponseData(alertService.myBatchUpdate(requestContext, altAlerts));
		
	}
	
	/**
	 * @description 预警活动查询
	 * @param request
	 * @param alertAction
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/alt/alert/action/query")
	public ResponseData selectAlertAction(HttpServletRequest request, AlertAction alertAction ,
			@RequestParam(defaultValue = DEFAULT_PAGE) int page,
			@RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
		IRequest requestContext = createRequestContext(request);
		if(alertAction.getAlertId() == null){
			return null;
		}else{
			return new ResponseData(alertActionService.select(requestContext, alertAction, page, pageSize));
		}
		
	}
	
	/**
	 * @description	预警活动删除
	 * @param request
	 * @param alertAction
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/alt/alert/action/delete")
	public ResponseData deleteAlertAction(HttpServletRequest request, @RequestBody List<AlertAction> alertActions) {
		alertActionService.batchDelete(alertActions);
		return new ResponseData();
	}
	/**
	 * 保存预警活动详情通知信息
	 * @param alertActionDtls 实体集合
	 * @param alertId 预警ID
	 * @param actionId 预警活动ID
	 * @param result 验证
	 * @param request 请求
	 * @return 实体集合
	 */
	@ResponseBody
	@RequestMapping(value = "/alt/alert/actionDtl/save", method = RequestMethod.POST)
	public ResponseData saveAction(@RequestBody List<AlertActionDtl> alertActionDtls,String alertId,String actionId,BindingResult result, HttpServletRequest request){
		getValidator().validate(alertActionDtls, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        
        IRequest requestContext = createRequestContext(request);
        for(AlertActionDtl a : alertActionDtls){
        	a.setActionId(Long.parseLong(actionId));
        	a.setAlertId(Long.parseLong(alertId));
        	if(a.getActionDtlId() == null){
        		alertActionDtlService.saveAction(requestContext, a);
        	}else{
        		alertActionDtlService.updateAction(requestContext, a);
        	}
        	
        }
		return new ResponseData(alertActionDtls);
	}
	/**
	 * 查询预警活动详细资料
	 * @param request 请求
	 * @param alertId 预警Id
	 * @param actionId 预警活动ID
	 * @param noticeTypeCode 预警通知类型
	 * @param alertActionDtl 实体
	 * @return 实体集合
	 */
	@ResponseBody
	@RequestMapping(value = "/alt/alert/actionDtl/query", method = RequestMethod.POST)
	public ResponseData queryAlertActionDtl(HttpServletRequest request,String alertId,String actionId,String noticeTypeCode ,AlertActionDtl alertActionDtl){
		alertActionDtl.setActionId(Long.parseLong(actionId));
		alertActionDtl.setAlertId(Long.parseLong(alertId));
		alertActionDtl.setNoticeTypeCode(noticeTypeCode);
		IRequest requestContext = createRequestContext(request);
		return new ResponseData(alertActionDtlService.queryAlertActionDtls(requestContext, alertActionDtl));
	}
	
	/**
	 * @description  SELECT语句验证
	 * @param request
	 * @param altAlert
	 * @return
	 * @throws JobException 
	 */
	@RequestMapping(value="/alt/alert/validate")
	public ResponseData validateAltSql(HttpServletRequest request, AltAlert altAlert) throws JobException{
		IRequest requestCtx = createRequestContext(request);
		String save = request.getParameter("save");
		
		

		Long alertId = altAlert.getAlertId();
		String sqlText = altAlert.getSqlStatementText();
		if (sqlText != null) {
			StringBuffer sql = new StringBuffer();

			AlertInput alertInput = new AlertInput();
			AlertOutput alertOutput = new AlertOutput();
			alertInput.setAlertId(altAlert.getAlertId());
			alertOutput.setAlertId(altAlert.getAlertId());
			//查询input变量
			List<AlertInput> alertInputs = alertInputService.select(requestCtx, alertInput,1,100);
			//查询output变量
			List<AlertOutput> alertOutputs = alertOutputService.queruyAlertOutputs(requestCtx, alertOutput);
			String[] sqlInput = sqlText.split("INTO|FROM");
			sql.append(sqlInput[0]);
			sql.append(" FROM ");
			String input = sqlInput[2];
			//验证SQL中变量的个数
			SqlUtil sqlUtil = new SqlUtil();
			List list = sqlUtil.getInputOutput(sqlText);
			List<AlertOutput> outputList = (List<AlertOutput>) list.get(1);
			List variables = sqlUtil.getVariable(sqlText);
			List<String> values = new ArrayList<String>();
			if(alertOutputs.size() > 0){
				if(sqlInput[0].indexOf("*") != -1){
					ResponseData rd = new ResponseData(false);
					rd.setMessage("SQL语句错误！");
					return rd;
				}else{
					//判断SQL中变量和查询结果数量是否对应
					if(outputList.size() == variables.size()){
						for (AlertInput altInput : alertInputs) {											
							if ("C".equals(altInput.getInputTypeCode())) {
								if ("D".equals(altInput.getDataTypeCode())) {
									input = input.replace(":"+altInput.getInputName(), " TO_DATE(\'"+altInput.getDefaultValue()+"\','yy-mm-dd hh24:mi:ss') ");            
								} else {
									input = input.replace(":"+altInput.getInputName(), " "+altInput.getDefaultValue()+" ");
								}
								

							}else{
								input = input.replace(":"+altInput.getInputName(), " ("+altInput.getSqlStatement()+") ");
							}						
						}
						sql.append(input);
						logger.info(loggerHeader + "Finnish query sql and the querySql:" + sql.toString());
						DataSource dataSource = (DataSource) beanFactory.getBean("dataSource");
						Connection con = null;
						try {
							con = dataSource.getConnection();
							PreparedStatement ps = con.prepareStatement(sql.toString());						
							ResultSet rs = ps.executeQuery();
							int n = 0;
							Map map = new HashMap();
							
							String massage = "";
							String systemContent = "";
							List<String> contents = new ArrayList<String>();
							while(rs.next()){
								String content = "";
								for(int i=0;i<alertOutputs.size();i++){
									values.add(rs.getString((String) variables.get(i)));
									//map.put(alertOutputs.get(i), rs.getString((String) variables.get(i)));
									content =content+alertOutputs.get(i).getOutputTitle() + " = " + rs.getString((String) variables.get(i)) + "\n";
									massage = massage+alertOutputs.get(i).getOutputTitle() + " = " + rs.getString((String) variables.get(i)) + "\n";
									systemContent = systemContent+alertOutputs.get(i).getOutputTitle() + " = " + rs.getString((String) variables.get(i)) + "<br>";
								}
								n++;
								contents.add(content);
							}
							ResponseData rd = new ResponseData(true);
							if ("save".equals(save)) {
								
								//查询JOB是否存在
								JobDetailDto jobDetail = new JobDetailDto();
								//jobDetail.setJobName(altAlert.getAlertName());
								jobDetail.setJobGroup(altAlert.getAlertName());
								List<JobDetailDto> jobDetails  = jobDetailMapper.selectJobDetails(jobDetail);
								
								//将邮件内容存到预警活动详情里去
								AlertActionDtl tempActionDtl = new AlertActionDtl();
								tempActionDtl.setAlertId(alertId);
								List<AlertActionDtl> alertActionDtls = alertActionDtlService.queryAlertActionDtls(requestCtx, tempActionDtl);
								for(AlertActionDtl a : alertActionDtls){
									a.setAlertMessage(massage);
									alertActionDtlService.updateByPrimaryKeySelective(requestCtx, a);
								}
								//获取预警信息的cron表达式
						        AltAlert a = alertService.selectByPrimaryKey(requestCtx, altAlert);
						        String name = altAlert.getAlertName();
								String cron  = a.getCronExpression();
								
								if(jobDetails.size() > 0){
									//删除旧的job
					        		if(jobDetails.size() > 0){
					        			try {
											quartzService.deleteJobs(jobDetails);
										} catch (SchedulerException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
					        		}
									if(cron.indexOf("$") != -1)  
							        {  
							        	String[] sourceCronArray = cron.split("\\$");
							        	for(int i=1;i<sourceCronArray.length;i++){
							        		altAlert.setAlertName(name+"_"+i);
							        		//创建新的job
											job(requestCtx,altAlert,alertOutputs,values,contents,systemContent,sourceCronArray[i]);
							        	}
							        }else{
							        	job(requestCtx,altAlert,alertOutputs,values,contents,systemContent,cron);
							        }
								}else{
									if(cron.indexOf("$") != -1)  
							        {  
							        	String[] sourceCronArray = cron.split("\\$");
							        	for(int i=1;i<sourceCronArray.length;i++){
							        		altAlert.setAlertName(name+"_"+i);
							        		//创建新的job
											job(requestCtx,altAlert,alertOutputs,values,contents,systemContent,sourceCronArray[i]);
							        	}
							        }else{
							        	job(requestCtx,altAlert,alertOutputs,values,contents,systemContent,cron);
							        }
									
								}
								
								rd.setMessage(n+" 行被选择");
							}else{
								rd.setMessage("成功");
							}				
							return rd;
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							
							ResponseData rd = new ResponseData(false);
							String message = e.getCause()==null?e.getMessage():e.getCause().getMessage();
							rd.setMessage(message);
							return rd;
						}finally {
							try {
								con.close();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}else{
						ResponseData rd = new ResponseData(false);
						rd.setMessage("SQL语句变量对应不正确！");
						return rd;
					}
				}
			}
			return null;	
		}else{
			ResponseData rd = new ResponseData(false);
			rd.setMessage("请先保存数据");
			return rd;
		}
		
	}
	
	public void job(IRequest requestCtx,AltAlert altAlert,List<AlertOutput> alertOutputs,List values,List contents,String systemContent,String myCorn) throws JobException, SQLException{
		
		
		String jobClassName = "hps.alt.util.MailJob";
		boolean assignableFrom = false;
        Class forName = null;
        try {
            forName = Class.forName(jobClassName);
            assignableFrom = AbstractJob.class.isAssignableFrom(forName);
        } catch (ClassNotFoundException e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
        }
        if (!assignableFrom || forName == null) {
            String name = AbstractJob.class.getName();
            throw new JobException(JOB_EXCEPTION, NOT_SUB_CLASS, new Object[] { jobClassName, name });
        }

        AltAlert a = alertService.selectByPrimaryKey(requestCtx, altAlert);
        JobBuilder jb = JobBuilder.newJob(forName).withIdentity(altAlert.getAlertName(), a.getAlertName())
                .withDescription(altAlert.getDescription());

        //传递数据
        JobDataMap data = new JobDataMap();
        data.put("alertId",Long.toString(altAlert.getAlertId()));
        data.put("content", systemContent);
        for(int i = 0;i<contents.size();i++){
        	data.put(Integer.toString(i), contents.get(i));
        }
        data.put("count", Integer.toString(contents.size()));
     
        jb = jb.usingJobData(data);
        

        JobDetail jobDetail = jb.build();

        TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger()
                .withIdentity(altAlert.getAlertName(), a.getAlertName()).forJob(jobDetail);
        
        
        ScheduleBuilder sche = null;
        //获取预警信息的cron表达式 
        sche = CronScheduleBuilder.cronSchedule(myCorn);
        
        Trigger trigger = triggerBuilder.withSchedule(sche).build();
        try {
			quartzScheduler.scheduleJob(jobDetail, trigger);
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
