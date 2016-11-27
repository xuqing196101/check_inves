
//判断专家类型
function validateType(){
	var expertsTypeId = $("#expertsTypeId").val();
	var categoryId = $("#categoryId").val();
	if(!expertsTypeId){
		layer.msg("请选择专家类型 !" ,{offset: ['222px', '390px']});
		return false;
	}
	if(expertsTypeId==1 && categoryId==""){
		layer.msg("请选择产品类型" ,{offset: ['222px', '390px']});
		return false;
	}else{
		var array =  categoryId.split(",");
		var count=false;
		var flag=false;
		for(var i=0;i<array.length;i++){
			if(array[i]=='GOODS'){
				count=true;
			}
		}
		for(var i=0;i<array.length;i++){
				if(array[i]=="SALES" || array[i]=="PRODUCT" ){
					flag=true;
				}
		}
		if( count==true && flag==false ){
			layer.msg("请选择货物类型" ,{offset: ['222px', '390px']});
			return false;
		}
			
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

//错误提示
function validateBase(){
	if($("#error1").val() != ""){
		layer.tips($("#error1").val(),"#relName", {
			  tipsMore: true
		});
	}
	if($("#error2").val() != ""){
		layer.tips($("#error2").val(),"#nation", {
			  tipsMore: true
		});
	}
	if($("#error3").val() != ""){
		layer.tips($("#error3").val(),"#gender", {
			  tipsMore: true
		});
	}
	if($("#error4").val() != ""){
		layer.tips($("#error4").val(),"#idType", {
			  tipsMore: true
		});
	}
	if($("#error5").val() != ""){
		layer.tips($("#error5").val(),"#idNumber", {
			  tipsMore: true
		});
	}
	if($("#error6").val() != ""){
		layer.tips($("#error6").val(),"#add", {
			  tipsMore: true
		});
	}
	if($("#error7").val() != ""){
		layer.tips($("#error7").val(),"#hightEducation", {
			  tipsMore: true
		});
	}
	if($("#error8").val() != ""){
		layer.tips($("#error8").val(),"#graduateSchool", {
			  tipsMore: true
		});
	}
	if($("#error9").val() != ""){
		layer.tips($("#error9").val(),"#major", {
			  tipsMore: true
		});
	}
	if($("#error10").val() != ""){
		layer.tips($("#error10").val(),"#expertsFrom", {
			  tipsMore: true
		});
	}
	if($("#error11").val() != ""){
		layer.tips($("#error11").val(),"#unitAddress", {
			  tipsMore: true
		});
	}
	if($("#error12").val() != ""){
		layer.tips($("#error12").val(),"#telephone", {
			  tipsMore: true
		});
	}
	if($("#error13").val() != ""){
		layer.tips($("#error13").val(),"#mobile", {
			  tipsMore: true
		});
	}
	if($("#error14").val() != ""){
		layer.tips($("#error14").val(),"#healthState", {
			  tipsMore: true
		});
	}
	if($("#error15").val() != ""){
		layer.tips($("#error15").val(),"#mobile", {
			  tipsMore: true
		});
	}
	if($("#error16").val() != ""){
		layer.tips($("#error16").val(),"#idNumber", {
			  tipsMore: true
		});
	}
}
