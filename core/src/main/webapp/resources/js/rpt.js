/**
 * 输出报表
 * 
 * 
 * 
 */
 function printReport(options)
{
	var form = options.form;
	if (!Hap.validateForm(form)) return; 
	var reportId = options.reportId;
	var reportCode = options.reportCode;
	var reportTemplateId = options.reportTemplateId;
	var reportType = options.reportType;
	// 判断是否有repostId 为空
	var formId = "thisForm";
	var flag = reportId == null;
	var _url = options.url;
	
	if($('#myRptDiv').length<=0){
		$('body').append("<div id='myRptDiv'></div>");
	}
	if ($("#"+formId).html() == null) {
			
			var htmltext = "<form method='post' id='" + formId + "'  action='" + _url
					+ "' target ='leftFrame' accept-charset='utf-8' onsubmit=\"document.charset='utf-8';\">";
			var d = form.getData();
			for ( var key in d) {
				if (!d[key]) {
					delete d[key];
				} else {
					// 将参数的名称和必须的字段渲染进入Form中
					htmltext += ("<input name ='" + key
							+ "' type ='hidden' value ='" + d[key] + "' />");
					htmltext += ("<input name='paramNames' type='hidden' value='"
							+ key + "'/>");
				}
			}
			if (flag) {
				htmltext += "<input name='reportCode' type= 'hidden' value='"
						+ reportCode + "'/>";
			} else {
				htmltext += "<input name='reportId' type= 'hidden' value='"
						+ reportId + "'/>";
			}
			htmltext += "<input name='templateId' type= 'hidden' value='"
				+ reportTemplateId + "'/>";
			htmltext += "<input name='reportType' type= 'hidden' value='"
				+ reportType + "'/>";
			htmltext += "</from>";
			$("#myRptDiv").html(htmltext);
			$("#" + formId).submit();
			$("#myRptDiv").html("");
			out.clear;
			out = pageContext.pushBody();
		}
}
