//校验基本信息 不能为空的字段
function validateForm1(){
	var relName = $("#relName").val();
	if(!relName){
		layer.tips("请输入姓名 !", "#relName");
		return false;
	}
	var gender = $("#gender").val();
	if(!gender){
		layer.tips("请选择性别 !", "#gender");
		return false;
	}
	var expertsFrom = $("#expertsFrom").val();
	if(!expertsFrom){
		layer.tips("请选择来源 !", "#expertsFrom");
		return false;
	}
	var idType = $("#idType").val();
	if(!idType){
		layer.tips("请选择证件类型 !", "#idType");
		return false;
	}
	var idNumber = $("#idNumber").val();
	if(!idNumber){
		layer.tips("请填写证件号码 !", "#idNumber");
		return false;
	}
	var id_areaSelect = $("#id_areaSelect").val();
	if(!id_areaSelect){
		layer.tips("请选择区域 !", "#id_areaSelect");
		return false;
	}
	var file1=$("#file1");
    if($.trim(file1.val())==''){
    	layer.tips("请选择要上传的附件 !", "#file1");
           return false;
     }
    var file2=$("#file2");
    if($.trim(file2.val())==''){
    	layer.tips("请选择要上传的附件 !", "#file2");
           return false;
     }
    var file3=$("#file3");
    if($.trim(file3.val())==''){
    	layer.tips("请选择要上传的附件 !", "#file3");
           return false;
     }
    var file4=$("#file4");
    if($.trim(file4.val())==''){
    	layer.tips("请选择要上传的附件 !", "#file4");
           return false;
     }
    var file5=$("#file5");
    if($.trim(file5.val())==''){
    	layer.tips("请选择要上传的附件 !", "#file5");
           return false;
     }
	return true;
}
//判断专家类型
function validateType(){
	var expertsTypeId = $("#expertsTypeId").val();
	if(!expertsTypeId){
		layer.tips("请选择专家类型 !", "#expertsTypeId");
		return false;
	}
	return true;
}
//判断采购机构
function validateJiGou(){
	 var jigou = document.getElementsByName("purchaseDepId");
		    var msglen=jigou.length;  
		    for(i=0;i<msglen;i++){  
		      if(jigou[i].checked==true){  
		        return true;  
		      }  
		    }
		    layer.tips("请选择一个采购机构 !", "#purchaseDepId2");
		    return false;
}
//判断申请表  合同书
function validateHeTong(){
	
	var regIdentity1=$("#regIdentitys1");
    if($.trim(regIdentity1.val())==''){
    	layer.tips("请选择要上传的附件 !", "#regIdentitys1");
           return false;
     }
    var regIdentity2=$("#regIdentitys2");
    if($.trim(regIdentity2.val())==''){
    	layer.tips("请选择要上传的附件 !", "#regIdentitys2");
           return false;
     }
    return true;
}
