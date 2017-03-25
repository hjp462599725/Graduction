
/**
 * added by:hongan.dong@hand-china
 * 
 * 页面组件权限控制
 */
function hpsAuthControl(params){
	if(params.roleId==null||params.roleId==''){
		console.log('auth error:roleId is null!');
		hpsShowLoading();
		return;
	}
	if(params.resourceUrl==null||params.resourceUrl==''){
		console.log('auth error:resourceUrl is null!');
		hpsShowLoading();
		return;
	}
	$.ajax({
		type : "POST",
		data :{
			roleId:params.roleId,
			resourceUrl:params.resourceUrl
		},
		url:params.targetUrl,
		cache:false,
		dataType:'json',
		async:false,
		success:function(data){
			//alert("data:"+JSON2.stringify(data));
			if(JSON2.stringify(data)=="[1]"){
				console.log('auth error:condition is not sufficient!');
				hpsShowLoading();
				return;
			}
			if(JSON2.stringify(data)=="[]"){
				console.log('auth error:ajax return empty data!');
				return;
			}
			var len = data.length;
       	    for(var i=0;i<len;i++){
       	    	var ligerObj = liger.get(data[i].itemRegion);
       	    	if(data[i].itemRegion.indexOf("grid")>=0||data[i].itemRegion.indexOf("Grid")>=0){
    				if(data[i].isHide=="Y"){
    					//toolbar隐藏
           	    		if(data[i].itemCode=="add"){
           	    			addToolHide(data[i].itemRegion);
           	    			continue;
           	    		}
           	    		if(data[i].itemCode=="del"){
           	    			delToolHide(data[i].itemRegion);
           	    			continue;
           	    		}
           	    		if(data[i].itemCode=="save"){
           	    			saveToolHide(data[i].itemRegion);
           	    			continue;
           	    		}
           	    		if(data[i].itemCode=="clear"){
           	    			clearToolHide(data[i].itemRegion);
           	    			continue;
           	    		}
           	    		//column隐藏
           	    		$("table[class='l-grid-header-table'] td[columnname=\'"+data[i].itemCode+"\']").css("display", "none");
           	    		ligerObj.getColumnByName(data[i].itemCode)._hide=true;
    					continue;
    				}else{
    					if(data[i].itemType!="toolbar"){
               	    		$("table[class='l-grid-header-table'] td[columnname=\'"+data[i].itemCode+"\']").css("display", "yes");
               	    		ligerObj.getColumnByName(data[i].itemCode)._hide=false;
    						if(data[i].isEdit=="Y"){
    	    					ligerObj.getColumnByName(data[i].itemCode).editor.type=data[i].itemType;
    	    				}else{
    	    					ligerObj.getColumnByName(data[i].itemCode).editor.type='null';
    	    				}
    	    				if(data[i].isRequired=="Y"){
    	    					ligerObj.getColumnByName(data[i].itemCode).validate.required = true;
    	    				}else{
    	    					ligerObj.getColumnByName(data[i].itemCode).validate.required = false;
    	    				}
    					}
    				}
       	    	}else{
       	    		if(data[i].itemType=='button'){
   	    				if(data[i].isHide=="Y"){
   	    					//button隐藏
   	    					$('div[ligeruiid="'+data[i].itemCode+'"]').parent().css({"display":"none"});
   	    					continue;
   	    				}else{
   	   	    				if(data[i].isOperate=="N"){
   	   	    					liger.get(data[i].itemCode).setDisabled();
   	   	    				}
   	    				}
       	    		}else if(data[i].itemType == "radiolist"){
						if (data[i].isHide == "N") {
							ligerObj.setVisible(data[i].itemCode, true);
							if(data[i].isEdit == "Y"){
							}else{
								radioCantEdit(data[i].itemRegion,data[i].itemCode);
							}
						}else{
							ligerObj.setVisible(data[i].itemCode, false);
						}
					}else{
   	    				if(data[i].isHide=="Y"){
   	    					ligerObj.setVisible(data[i].itemCode,false);
   	    					continue;
   	    				}else{
   	    					ligerObj.setVisible(data[i].itemCode,true);
   	   	    				if(data[i].isEdit=="Y"){
   	   	    					ligerObj.setEnabled(data[i].itemCode,true);
   	   	    					var len = ligerObj.options.fields.length;
   	   	    					for(var j=0;j<len;j++){
   	   	    						if(ligerObj.getField(j).name==data[i].itemCode){
   	   	    							if(ligerObj.getField(j).id){
   	   	    								//form中必输
   	   	    	   	   	    				if(data[i].isRequired=="Y"){
   	   	    	   	   	    					ligerObj.setFieldValidate(data[i].itemCode+"Id",{required:true},"required");
   	   	    	   	   	    				}else{
   	   	    	   	   	    					ligerObj.setFieldValidate(data[i].itemCode+"Id",{required:false},"noRequired");
   	   	    	   	   	    				}
   	   	    							}else{
   	   	    								console.log("no found the id property for "+ligerObj.getField(j).name);
   	   	    							}
   	   	    						}
   	   	    					}
   	   	    				}else{
   	   	    					ligerObj.setEnabled(data[i].itemCode,false);
   	   	    				}
   	    				}
       	    		}
       	    	}
       	    }
		},
		error:function(){
			console.log('auth error!');
			hpsShowLoading();
		}
	})
}

/**
 * added by:hongan.dong@hand-china time:2016年8月18日15:12:25
 *
 * 显示加载
 */
function hpsShowLoading() {
	$('body').append("<div class='l-panel-loading' style='display:block;'></div>");
	$.ligerui.win.mask();
};

/**
 * added by:hongan.dong@hand-china time:2016年8月18日15:12:25
 *
 * 隐藏加载
 */
function hpsHideLoading() {
	$('body > div.l-panel-loading').remove();
	$.ligerui.win.unmask();
}

/**
 * 表格新建隐藏
 * by:taotao.xu time:2016年9月5日20:40
 * @param gridName
 */
function addToolHide(gridName) {
	var obj = $("#" + gridName).find("div.l-icon.l-icon-add").parent();
	obj.css({"display":"none"});
}

/**
 * 表格删除隐藏
 * by:taotao.xu time:2016年9月5日20:40
 * @param gridName
 */
function delToolHide(gridName) {
	var obj = $("#" + gridName).find("div.l-icon.l-icon-delete").parent();
	obj.css({"display":"none"});
}

/**
 * 表格保存隐藏
 * by:taotao.xu time:2016年9月5日20:40
 * @param gridName
 */
function saveToolHide(gridName) {
	var obj = $("#" + gridName).find("div.l-icon.l-icon-save").parent();
	obj.css({"display":"none"});
}

/**
 * 表格保存隐藏
 * by:taotao.xu time:2016年9月5日20:40
 * @param gridName
 */
function clearToolHide(gridName) {
	var obj = $("#" + gridName).find("div.l-icon.l-icon-clear").parent();
	obj.css({"display":"none"});
}

/* type=radiolist不可编辑设置方法 */
function radioCantEdit(formName, name) {
	var obj = $("#" + formName).find("input[name='" + name + "']");
	obj.attr("disabled", true);
}

/* type=radiolist验证必选方法 */
function radioValidate(formName, name) {
	var obj = $("#" + formName).find("input[name='" + name + "']");
	elem = obj.parent().parent().parent().parent();
    if(typeof(elem.attr("style")) == "undefined" && typeof(obj.attr("disabled")) == "undefined"){
        if(typeof(liger.get(name).getValue()) == "undefined"){
            return false;
        }
	}
    return true;
}