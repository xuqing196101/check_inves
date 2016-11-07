//校验基本信息 不能为空的字段
function validateForm1(){
	var relName = $("#relName").val();
	if(!relName){
		layer.msg("请输入姓名 !",{offset: ['222px', '390px']});
		return false;
	}
	var gender = $("#gender").val();
	if(!gender){
		layer.msg("请选择性别 !",{offset: ['222px', '390px']});
		return false;
	}
	var expertsFrom = $("#expertsFrom").val();
	if(!expertsFrom){
		layer.msg("请选择来源 !",{offset: ['222px', '390px']});
		return false;
	}
	
	var nation = $("#nation").val();
	if(!nation){
		layer.msg("请填写民族 !",{offset: ['222px', '390px']});
		return false;
	}
	var graduateSchool = $("#graduateSchool").val();
	if(!graduateSchool){
		layer.msg("请填写毕业院校 !",{offset: ['222px', '390px']});
		return false;
	}
	var hightEducation = $("#hightEducation").val();
	if(!hightEducation){
		layer.msg("请选择最高学历!",{offset: ['222px', '390px']});
		return false;
	}
	
	var major = $("#major").val();
	if(!major){
		layer.msg("请填写专业!",{offset: ['222px', '390px']});
		return false;
	}
	
	var unitAddress = $("#unitAddress").val();
	if(!unitAddress){
		layer.msg("请填写单位地址!",{offset: ['222px', '390px']});
		return false;
	}
	var telephone = $("#telephone").val();
	if(!telephone){
		layer.msg("请填写联系电话!",{offset: ['222px', '390px']});
		return false;
	}
	
	var mobile = $("#mobile").val();
	if(!mobile){
		layer.msg("请填写手机号!",{offset: ['222px', '390px']});
		return false;
	}
	
	var healthState = $("#healthState").val();
	if(!healthState){
		layer.msg("请填写健康状态!",{offset: ['222px', '390px']});
		return false;
	}
	
	var idType = $("#idType").val();
	if(!idType){
		layer.msg("请选择证件类型 !",{offset: ['222px', '390px']});
		return false;
	}
	var idNumber = $("#idNumber").val();
	if(!idNumber){
		layer.msg("请填写证件号码 !",{offset: ['222px', '390px']});
		return false;
	}
	var id_areaSelect = $("#haha").val();
	if(!id_areaSelect){
		layer.msg("请选择区域 !",{offset: ['222px', '390px']});
		return false;
	}
	/*var file1=$("#file1");
    if($.trim(file1.val())==''){
    	layer.msg("请选择身份证附件 !",{offset: ['222px', '390px']});
           return false;
     }
    var file2=$("#file2");
    if($.trim(file2.val())==''){
    	layer.msg("请选择学历证书附件 !",{offset: ['222px', '390px']});
           return false;
     }
    var file3=$("#file3");
    if($.trim(file3.val())==''){
    	layer.msg("请选择职称证书附件 !",{offset: ['222px', '390px']});
           return false;
     }
    var file4=$("#file4");
    if($.trim(file4.val())==''){
    	layer.msg("请选择学位证书附件 !",{offset: ['222px', '390px']});
           return false;
     }
    var file5=$("#file5");
    if($.trim(file5.val())==''){
    	layer.msg("请选择本人照片附件 !" ,{offset: ['222px', '390px']});
           return false;
     }*/
	return true;
}
//判断专家类型
function validateType(){
	var expertsTypeId = $("#expertsTypeId").val();
	var categoryId = $("#categoryId").val();
	if(!expertsTypeId){
		layer.msg("请选择专家类型 !" ,{offset: ['222px', '390px']});
		return false;
	}
	if(expertsTypeId==1 && categoryId==""){
		layer.msg("请选择品目类别或子类目!" ,{offset: ['222px', '390px']});
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
		    layer.msg("请选择一个采购机构 !",{offset: ['222px', '390px']});
		    return false;
}
//判断申请表  合同书
function validateHeTong(){
	
	/*var regIdentity1=$("#regIdentitys1");
    if($.trim(regIdentity1.val())==''){
    	layer.msg("请选择申请表附件 !",{offset: ['222px', '390px']});
           return false;
     }
    var regIdentity2=$("#regIdentitys2");
    if($.trim(regIdentity2.val())==''){
    	layer.msg("请选择要合同书附件 !",{offset: ['222px', '390px']});
           return false;
     }*/
    return true;
}
