<%@page import="bss.model.ppms.ScoreModel"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/view/common.jsp"%>
       
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
ScoreModel scoreModel = (ScoreModel)request.getAttribute("scoreModel");
System.out.print(scoreModel);
%>
<script type="text/javascript">
	function choseModel(){
		var model = $("#model").val();
		console.dir(model);
		$("#showParamButton").hide();
		$("#model73").hide();//隐藏区间参数table
		if(model==""){
			$("#showbutton").hide();
			$("#show_table tbody tr").remove();
		}else if(model=="0"){
			$("#show_table tbody tr").remove();
			$("#model1 tbody tr").clone().appendTo("#show_table tbody");
			$("#showbutton").show();
		}else if(model=="1"){
			var addSubtractTypeName = $("#sm2").val();
			$("#show_table tbody tr").remove();
			if(addSubtractTypeName=="0"){
				$("#model21 tbody tr").clone().appendTo("#show_table tbody");
			}else if(addSubtractTypeName=="1"){
				$("#model22 tbody tr").clone().appendTo("#show_table tbody");
			}else{
				//默认加分实例
				$("#model21 tbody tr").clone().appendTo("#show_table tbody");
			}
			$("#showbutton").show();
		}else if(model=="2"){
			$("#show_table tbody tr").remove();
			$("#model3 tbody tr").clone().appendTo("#show_table tbody");
			$("#showbutton").show();
		}else if(model=="3"){
			$("#show_table tbody tr").remove();
			$("#model4 tbody tr").clone().appendTo("#show_table tbody");
			$("#showbutton").show();
		}else if(model=="4"){
			$("#show_table tbody tr").remove();
			$("#model5 tbody tr").clone().appendTo("#show_table tbody");
			$("#showbutton").show();
		}else if(model=="5"){
			$("#show_table tbody tr").remove();
			$("#model6 tbody tr").clone().appendTo("#show_table tbody");
			$("#showbutton").show();
		}else if(model=="6"){
			$("#show_table tbody tr").remove();
			//$("#model7 tbody tr").clone().appendTo("#show_table tbody");
			var intervalTypeName71 = $("#sm7").val();
			if(intervalTypeName71!=undefined && intervalTypeName71=="1"){
				$("#showParamButton").show();
				$("#showbutton").hide();
				$("#model72 tbody tr").clone().appendTo("#show_table tbody");
				$("#model73").show();
			}else if(intervalTypeName71=="0"){
				$("#showbutton").show();
				$("#showParamButton").hide();
				$("#model71 tbody tr").clone().appendTo("#show_table tbody");
				$("#model73 tr:not(:first)").remove();//删除除了第一行的所有tr 
				$("#model73").hide();
			}else{
				$("#showbutton").show();
				$("#showParamButton").hide();
				$("#model71 tbody tr").clone().appendTo("#show_table tbody");
				$("#model73 tr:not(:first)").remove();//删除除了第一行的所有tr 
				$("#model73").hide();
			}
		}else if(model=="7"){
			$("#show_table tbody tr").remove();
			var intervalTypeName71 = $("#sm7").val();
			if(intervalTypeName71!=undefined && intervalTypeName71=="1"){
				$("#showParamButton").show();
				$("#showbutton").hide();
				$("#model82 tbody tr").clone().appendTo("#show_table tbody");
				$("#model73").show();
			}else if(intervalTypeName71=="0"){
				$("#showbutton").show();
				$("#showParamButton").hide();
				$("#model81 tbody tr").clone().appendTo("#show_table tbody");
				$("#model73 tr:not(:first)").remove();//删除除了第一行的所有tr 
				$("#model73").hide();
			}else{
				$("#showbutton").show();
				$("#showParamButton").hide();
				$("#model81 tbody tr").clone().appendTo("#show_table tbody");
				$("#model73 tr:not(:first)").remove();//删除除了第一行的所有tr 
				$("#model73").hide();
			}
		}
	}
	function modelTwoAddSubstact21(){
		var model = $("#addSubtractTypeName").val();
		if(model=="0"){
			$("#show_table tbody tr").remove();
			$("#model21 tbody tr").clone().appendTo("#show_table tbody");
		}else{
			$("#show_table tbody tr").remove();
			$("#model22 tbody tr").clone().appendTo("#show_table tbody");
		}
	}
	function modelTwoAddSubstact22(){
		var model = $("#addSubtractTypeName").val();
		if(model=="0"){
			$("#show_table tbody tr").remove();
			$("#model21 tbody tr").clone().appendTo("#show_table tbody");
		}else{
			$("#show_table tbody tr").remove();
			$("#model22 tbody tr").clone().appendTo("#show_table tbody");
		}
	}
	function modelSevenAddSubstact71(){
		var model = $("#intervalTypeName71").val();
		if(model=="0"){
			$("#show_table tbody tr").remove();
			$("#model71 tbody tr").clone().appendTo("#show_table tbody");
			$("#model73 tr:not(:first)").remove();//删除除了第一行的所有tr 
			$("#model73").hide();
			$("#showParamButton").hide();
			$("#showbutton").show();
		}else{
			$("#show_table tbody tr").remove();
			$("#model72 tbody tr").clone().appendTo("#show_table tbody");
			$("#model73").show();
			$("#showParamButton").show();
			$("#showbutton").hide();
		}
	}
	function modelSevenAddSubstact72(){
		var model = $("#intervalTypeName72").val();
		if(model=="0"){
			$("#show_table tbody tr").remove();
			$("#model71 tbody tr").clone().appendTo("#show_table tbody");
			$("#model73 tr:not(:first)").remove();//删除除了第一行的所有tr 
			$("#model73").hide();
			$("#showParamButton").hide();
			$("#showbutton").show();
		}else{
			$("#show_table tbody tr").remove();
			$("#model72 tbody tr").clone().appendTo("#show_table tbody");
			$("#model73").show();
			$("#showParamButton").show();
			$("#showbutton").hide();
		}
	}
	function modelSevenAddSubstact81(){
		var model = $("#intervalTypeName81").val();
		if(model=="0"){
			$("#show_table tbody tr").remove();
			$("#model81 tbody tr").clone().appendTo("#show_table tbody");
			$("#model73 tr:not(:first)").remove();//删除除了第一行的所有tr 
			$("#model73").hide();
			$("#showParamButton").hide();
			$("#showbutton").show();
		}else{
			$("#show_table tbody tr").remove();
			$("#model82 tbody tr").clone().appendTo("#show_table tbody");
			$("#model73").show();
			$("#showParamButton").show();
			$("#showbutton").hide();
		}
	}
	function modelSevenAddSubstact82(){
		var model = $("#intervalTypeName82").val();
		if(model=="0"){
			$("#show_table tbody tr").remove();
			$("#model81 tbody tr").clone().appendTo("#show_table tbody");
			$("#model73 tr:not(:first)").remove();//删除除了第一行的所有tr 
			$("#model73").hide();
			$("#showParamButton").hide();
			$("#showbutton").show();
		}else{
			$("#show_table tbody tr").remove();
			$("#model82 tbody tr").clone().appendTo("#show_table tbody");
			$("#model73").show();
			$("#showParamButton").show();
			$("#showbutton").hide();
		}
	}
	function gernerator(){
		var model = $("#model").val();
		if(model=="0"){
			gerneratorOne();
		}else if(model=="1"){
			gerneratorTwo();
		}else if(model=="2"){
			gerneratorThree();
		}else if(model=="3"){
			gerneratorFour();
		}else if(model=="4"){
			gerneratorFive();
		}else if(model=="5"){
			gerneratorSix();
		}else if(model=="6"){
			gerneratorSeven();
		}else if(model=="7"){
			gerneratorEight();
		}
	}
	//动态添加参数区间
	var num2 =1;
	function addParamInterval(){
		var pinum = $("#num2").val();
		if(pinum>0){
			num2 = Number(pinum) + Number(1);
		}
		var tr ="";
		tr += "<tr>";
		//tr += "<td class='w30'><input type='checkbox'></td>";
		tr += "<td class='w30'>"+num2+"</td>";
		tr += "<td ><input class='w40' type='text' id=startParam"+num2+" name='pi.startParam'></td>";
		//tr += "<td><select name='startRelation'><option value='0'>>=</option></select></td>";
		//tr += "<td>参数值</td>";
		//tr += "<td><select name='endRelation'><option value='0'><=</option></select></td>";
		tr += "<td ><input class='w40' type='text' id=endParam"+num2+" name='pi.endParam'></td>";
		tr += "<td ><input class='w40' type='text' id=score"+num2+" name='pi.score'></td>";
		tr += "<td ><textarea class='' id="+num2+" name='pi.explain'></textarea></td>";
		tr += "<td ><a href='javascript:void(0);' onclick='delTr(this)'>删除</a></td>";
		tr += "</tr>";
		$("#model73 tbody").append(tr);
		num2++;
	}
	function delTr(obj){
		var tr=obj.parentNode.parentNode;
        tr.parentNode.removeChild(tr);
		//$(obj).parent.remove();//删除当前行   
		var num = $("#model73 tbody tr").length;
		var trs = $("#model73 tbody tr");
		console.dir(trs.find("td:eq(0)"));
		for (i = 0; i < num; i++) {
			trs.find("td:eq(0)").each(function(i) {
				$(this).text(i + 1);
			});
		}  
		num2--;
	}
	function gerneratorOne(){
		var judgeContent = $("#judgeContent").val();
		var standardScore = $("#standardScore").val();
		//var judgeNumber = $("#judgeNumber").val();
		var str = judgeContent  + " "+"是"+standardScore+"分 "+"否0分";
		$("#easyUnderstandContent1").val(str);
	}
	function gerneratorTwo(){
		var reviewParam = $("#reviewParam").val();
		var addSubtractTypeName = $("#addSubtractTypeName").val();
		var reviewStandScore = $("#reviewStandScore").val();
		var maxScore = $("#maxScore").val();
		var minScore = $("#minScore").val();
		var unitScore = $("#unitScore").val();
		var unit = $("#unit").val();
		
		var type ="";
		if(addSubtractTypeName=="0"){
			type = " 加分类型" + " 每单位得" +unitScore +"分" + " 起始分值为" + reviewStandScore+"分"+" 最高分不超过"+maxScore+"分";
			var str = reviewParam + type ; 
			$("#easyUnderstandContent21").val(str);
		}else{
			type = " 减分类型" +" 基准分值为"+reviewStandScore+"分" +" 每单位减"+unitScore+"分"+" 最低分值为"+minScore+"分";
			var str = reviewParam + type ; 
			$("#easyUnderstandContent22").val(str);
		}
		
	}
	function gerneratorThree(){
		var reviewParam = $("#reviewParam").val();
		var unit = $("#unit").val();
		var score = $("#score").val();
		//var addSubtractTypeName = $("#addSubtractTypeName").val();
		var maxScore = $("#maxScore").val();
		var minScore = $("#minScore").val();
		var str = "减分实例:以"+reviewParam+"最高值为基准排序递减，第一名得"+maxScore+"分,依次递减"+score+"分,最低分为"+minScore+"分";
		$("#easyUnderstandContent3").val(str);
	}
	function gerneratorFour(){
		var reviewParam = $("#reviewParam").val();
		var unit = $("#unit").val();
		var score = $("#score").val();
		var maxScore = $("#maxScore").val();
		var minScore = $("#minScore").val();
		var str = "加分实例:以"+reviewParam+"最低值为基准排序递增，第一名得"+minScore+"分,依次递增"+score+"分,最高分为"+maxScore+"分";
		$("#easyUnderstandContent4").val(str);
	}
	function gerneratorFive(){
		var reviewParam = $("#reviewParam").val();
		var standardScore = $("#standardScore").val();
		var unit = $("#unit").val();
		var str = "以" + reviewParam +"最高为基准,得分=("+reviewParam+"/基准值)*"+standardScore;
		$("#easyUnderstandContent5").val(str);
	}
	function gerneratorSix(){
		var reviewParam = $("#reviewParam").val();
		var standardScore = $("#standardScore").val();
		var unit = $("#unit").val();
		var str = "以" + reviewParam +"最低为基准,得分=(基准值/"+reviewParam+")*"+standardScore;
		$("#easyUnderstandContent6").val(str);
	}
	function gerneratorSeven(){
		var reviewParam  = $("#reviewParam").val();
		var unit   = $("#unit").val();
		var reviewStandScore   = $("#reviewStandScore").val();
		var intervalNumber    = $("#intervalNumber").val();
		var score   = $("#score").val();
		var deadlineNumber   = $("#deadlineNumber").val();
		var maxScore   = $("#maxScore").val();
		var minScore  = $("#minScore").val();
		var str =  reviewParam +",低于" +reviewStandScore+"为0分,没增加"+intervalNumber+"加"+score+ " 最高分"+maxScore+" 最低分"+minScore+" 高于"+deadlineNumber+ "得"+maxScore+"分";
		$("#easyUnderstandContent7").val(str);
	}
	function gerneratorEight(){
		var reviewParam  = $("#reviewParam").val();
		var unit   = $("#unit").val();
		var reviewStandScore   = $("#reviewStandScore").val();
		var intervalNumber    = $("#intervalNumber").val();
		var score   = $("#score").val();
		var deadlineNumber   = $("#deadlineNumber").val();
		var maxScore   = $("#maxScore").val();
		var minScore  = $("#minScore").val();
		var str =  reviewParam +",高于" +reviewStandScore+"为"+maxScore+"分,没减少"+intervalNumber+"减"+score+ " 最低分分"+minScore+" 低于"+deadlineNumber+ "得"+minScore+"分";
		$("#easyUnderstandContent8").val(str);
	}
	function associate(){
	    var standScore = $("#standardScore").val();
	    var maxScore = $("#maxScore").val();
	    var id = $("#id").val();
	    var moxing2 = $("#moxing2").val();
		var s = validteModel().form();
		console.dir(s);
		if(s){
			$.ajax({   
	            type: "get",  
	            url: "${pageContext.request.contextPath}/intelligentScore/checkScore.do?standScore="+standScore+"&moxing2="+moxing2+"&id="+id+"&maxScore="+maxScore+"&projectId=${projectId}"+"&packageId=${packageId}",        
	            dataType:'json',
	            success:function(result){
	                  if (result == 0){
					     layer.msg("评分项已超过100分,请检查",{offset: ['150px']});     	
	                  } else {
	                  	$("#formID").attr('action','${pageContext.request.contextPath}/intelligentScore/operatorScoreModel.do').submit();
	                  }
	            },
	            error: function(result){
	                layer.msg("添加失败",{offset: ['150px']});
	            }
       		});   
			
		}else{
			return;
		}
	}
	function pageOnLoad(){
		var model = $("#sm").val();
		$("#showParamButton").hide();
		if('${addStatus}' !=1){
			$("#model").val(model);
		}
		//console.dir(model==undefined);
		if(model !=undefined && model==""){
			$("#showbutton").hide();
			$("#show_table tbody tr").remove();
		}else if(model=="0"){
			$("#show_table tbody tr").remove();
			if('${addStatus}' !=1){
				$("#model1 tbody tr").clone().appendTo("#show_table tbody");
			}
			$("#showbutton").show();
		}else if(model=="1"){
			var addSubtractTypeName = $("#sm2").val();
			$("#addSubtractTypeName").val(addSubtractTypeName);
			$("#show_table tbody tr").remove();
			if('${addStatus}' !=1){
				if(addSubtractTypeName!=undefined && addSubtractTypeName=="0"){
					$("#model21 tbody tr").clone().appendTo("#show_table tbody");
				}else if(addSubtractTypeName=="1"){
					$("#model22 tbody tr").clone().appendTo("#show_table tbody");
				}
			}
			$("#showbutton").show();
		}else if(model=="2"){
			$("#show_table tbody tr").remove();
			if('${addStatus}' !=1){
				$("#model3 tbody tr").clone().appendTo("#show_table tbody");
			}
			$("#showbutton").show();
		}else if(model=="3"){
			$("#show_table tbody tr").remove();
			if('${addStatus}' !=1){
				$("#model4 tbody tr").clone().appendTo("#show_table tbody");
			}
			$("#showbutton").show();
		}else if(model=="4"){
			$("#show_table tbody tr").remove();
			if('${addStatus}' !=1){
				$("#model5 tbody tr").clone().appendTo("#show_table tbody");
			}
			$("#showbutton").show();
		}else if(model=="5"){
			$("#show_table tbody tr").remove();
			if('${addStatus}' !=1){
				$("#model6 tbody tr").clone().appendTo("#show_table tbody");
			}	
			$("#showbutton").show();
		}else if(model=="6"){
			$("#show_table tbody tr").remove();
			//$("#model7 tbody tr").clone().appendTo("#show_table tbody");
			var intervalTypeName71 = $("#sm7").val();
			if(intervalTypeName71!=undefined && intervalTypeName71=="1"){
				$("#showParamButton").show();
				$("#showbutton").hide();
				if('${addStatus}' !=1){
					$("#model72 tbody tr").clone().appendTo("#show_table tbody");
				}
				$("#model73").show();
				$("#model73").append('${scoreStr}');
			}else if(intervalTypeName71=="0"){
				$("#showbutton").show();
				$("#showParamButton").hide();
				if('${addStatus}' !=1){
					$("#model71 tbody tr").clone().appendTo("#show_table tbody");
				}	
				$("#model73 tr:not(:first)").remove();//删除除了第一行的所有tr 
				$("#model73").hide();
			}else{
				$("#showbutton").show();
				$("#showParamButton").hide();
				if('${addStatus}' !=1){
					$("#model71 tbody tr").clone().appendTo("#show_table tbody");
				}
				$("#model73 tr:not(:first)").remove();//删除除了第一行的所有tr 
				$("#model73").hide();
			}
		}else if(model=="7"){
			/* $("#show_table tbody tr").remove();
			$("#model8 tbody tr").clone().appendTo("#show_table tbody");
			//$("#showbutton").show();
			$("#showParamButton").show(); */
			$("#show_table tbody tr").remove();
			var intervalTypeName71 = $("#sm7").val();
			if(intervalTypeName71!=undefined && intervalTypeName71=="1"){
				$("#showParamButton").show();
				$("#showbutton").hide();
				if('${addStatus}' !=1){
					$("#model82 tbody tr").clone().appendTo("#show_table tbody");
				}
				$("#model73").append('${scoreStr}');
				$("#model73").show();
			}else if(intervalTypeName71=="0"){
				$("#showbutton").show();
				$("#showParamButton").hide();
				if('${addStatus}' !=1){
					$("#model81 tbody tr").clone().appendTo("#show_table tbody");
				}
				$("#model73 tr:not(:first)").remove();//删除除了第一行的所有tr 
				$("#model73").hide();
			}else{
				$("#showbutton").show();
				$("#showParamButton").hide();
				if('${addStatus}' !=1){
					$("#model81 tbody tr").clone().appendTo("#show_table tbody");
				}
				$("#model73 tr:not(:first)").remove();//删除除了第一行的所有tr 
				$("#model73").hide();
			}
		}
	}
</script>  
<script type="text/javascript">
	//validate
	function validteModel(){
		return $("#formID").validate({
			ignore: [],
			focusInvalid : false, //当为false时，验证无效时，没有焦点响应  
			onkeyup : false,
			rules : {
				standardScore : {
					required : true,
					number:true
				},
				judgeContent : {
					required : true
				},
				judgeNumber :{
					required : true,
					number:true
				},
				/* easyUnderstandContent : {
					required : true
				}, */
				reviewParam : {
					required : true
				},
				reviewStandScore : {
					required : true,
					number:true
				},
				maxScore : {
					required : true,
					number:true
				},
				unitScore : {
					required : true,
					number:true
				},
				
				minScore : {
					required : true,
					number:true
				},
				intervalNumber : {
					required : true,
					number:true
				},
				"pi.startParam" : {
					required : true,
					number:true
				},
			    "pi.endParam" : {
					required : true,
					number:true
				}, 
				"pi.score" : {
					required : true,
					number:true
				},
				reviewContent : {
					required : true
				},
				name : {
					required : true
				}
			},
			messages : {
				standardScore : {
					required : "该项满分值为必填项",
					number:"必须为数字"
				},
				judgeContent : {
					required : "该项内容为必填项"
				},
				judgeNumber :{
					required : "该项内容为必填项",
					number:"必须为数字"
				},
				/* easyUnderstandContent : {
					required : "请点击生成白话文"
				}, */
				reviewParam : {
					required : "该项内容为必填项"
				},
				reviewStandScore : {
					required : "该项内容为必填项",
					number:"必须为数字"
				},
				maxScore : {
					required : "该项内容为必填项",
					number:"必须为数字"
				},
				unitScore : {
					required : "该项内容为必填项",
					number:"必须为数字"
				},
				minScore : {
					required : "该项内容为必填项",
					number:"必须为数字"
				},
				intervalNumber : {
					required : "该项内容为必填项",
					number:"必须为数字"
				},
			    "pi.startParam" : {
					required : "必填",
					number:"数字项"
				},
				"pi.endParam" : {
					required : "必填",
					number:"数字项"
				},  
				"pi.score" : {
					required : "必填",
					number:"数字项"
				},
				reviewContent : {
					required : "必填"
				},
				name : {
					required : "必填"
				}
			},
			showErrors: function(errorMap, errorList) {
	           $.each(this.successList, function(index, value) {
	             return $(value).popover("hide");
	           });
           	   return $.each(errorList, function(index, value) {
             		var _popover;
             		_popover = $(value.element).popover({
                    trigger: "manual",
                    placement: "top",
                    content: value.message,
                    template: "<div class=\"popover\"><div class=\"arrow\"></div> <div class=\"popover-inner\"><div class=\"popover-content\"><p></p></div></div></div>"
               });
             _popover.data("bs.popover").options.content = value.message;
             return _popover.popover("show");
           });
         }
		}); 
	}
</script>
    </head>
<body onload="pageOnLoad();">
	<input type="hidden" id="sm" value="${scoreModel.typeName }">
	<input type="hidden" id="sm2" value="${scoreModel.addSubtractTypeName }">
	<input type="hidden" id="sm7" value="${scoreModel.intervalTypeName }">
	<div>
		<form action="" method="post"  id="formID">
		   <div class="mt50">
		    	<span>评审指标名称:</span>
		   		<input name="name" id="name" class="" type="text" value="${scoreModel.name}" >
		   </div>
		   
		   <div class="mt5 mb10 mr20">
				<span>选择模型: </span>
				 <select id="model" name="typeName" onchange="choseModel();">
					<option value="">请选择</option>
					<option value="0">模型1:是否判断</option>
					<option value="1">模型2:按项加减分</option>
					<option value="2">模型3:评审数额最高递减</option>
					<option value="3">模型4:评审数额最低递增</option>
					<option value="4">模型5:评审数额高计算</option>
					<option value="5">模型6:评审数额低计算</option>
					<option value="6">模型7:评审数额低区间递增</option>
					<option value="7">模型8:评审数额高区间递减</option>
				</select>
			</div>
			<div class="fl">评审指标内容及规则说明: </div>
		       <div>
		   	   <textarea  class="col-md-12 col-sm-12 col-xs-12 h80 w500" name="reviewContent" id="reviewContent" >${scoreModel.reviewContent}</textarea>
		       </div>
			<input id="packageId" name="packageId" type="hidden" value="${packageId }">
			<input id="projectId" name="projectId" type="hidden" value="${projectId }">
			<input id="markTermId" name="markTermId" type="hidden" value="${markTermId }">
			<c:if test="${addStatus != 1 }">
				<input id="id" type="hidden" name="id" value="${scoreModel.id}">
			</c:if>
			<input type="hidden" id="num2" value="${fn:length(scoreModel.paramIntervalList)}">
			<table class="table table-striped table-bordered table-hover mt20"  id="show_table">
				<tbody>
				</tbody>
			</table>
			<table id="model73" style="display: none;" class="table table-striped table-bordered table-hover mt20 w499">
				<thead>
					<tr id="paramIntervalTr">
						<!-- <th class="w30"><input type="checkbox">
						</th> -->
						<th class="">序号</th>
						<th class="">起始值</th>
						<!-- <th class="w500">与起始参数值关系</th>
						<th class="w500">评审参数对应数值</th>
						<th class="w500">与结束参数值关系</th> -->
						<th class="">结束值</th>
						<th class="">得分</th>
						<th class="">解释</th>
						<th class="">操作</th>
					</tr>
				</thead>
				
				<tbody>
					<c:forEach items="${scoreModel.paramIntervalList }" var="pi" varStatus="vs">
							<tr>
								<td align="center">${vs.index+1 }</td>
								<td align="center"><input class='w40' type='text' value="${pi.startParam }" id="startParam${vs.index+1 }" name='pi.startParam'></td>
								<td align="center"><input class='w40' type='text' value="${pi.endParam }" id="endParam${vs.index+1 }" name='pi.endParam'></td>
								<td align="center"><input class='w40' type='text' value="${pi.score }" id="score${vs.index+1 }" name='pi.score'></td>
								<td align="center"><textarea class='w40' id="explain${vs.index+1 }" name='pi.explain'>${pi.explain }</textarea></td>
								<td ><a href='javascript:void(0);' onclick='delTr(this)'>删除</a></td>
							</tr>
					</c:forEach>
				</tbody>
			</table>
		</form>
	</div>
	<div class="col-md-12" id="showbutton" style="display: none;">
		<div class="mt40 tc mb50">
			<!-- <input type="button" class="btn  padding-right-20 btn_back margin-5" onclick="gernerator();" value="翻译成白话文">  -->
			<input type="button" class="btn btn-windows save" onclick="associate();" value="保存">
			<button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
		</div>
	</div>
	<div class="col-md-12" id="showParamButton" style="display: none;">
		<div class="mt40 tc mb50">
			<input type="button" class="btn  padding-right-20 btn_back margin-5" onclick="addParamInterval();" value="添加参数区间"> 
			<input type="button" class="btn btn-windows save" onclick="associate();" value="保存">
		</div>
	</div>
	<!-- 八大模型 -->
	<table id="model1" style="display: none;" class="w499">
		<tbody>
			<tr>
				<td style="">标准分值</td>
				<td><input name="standardScore" onkeyup="gernerator();" id="standardScore" value="${scoreModel.standardScore }" title="该项的满分值为多少"><span class="blue">*该项的满分值为多少</span></td>
			</tr>
			<tr>
				<td>判断内容</td>
				<td>
				  <textarea class="col-md-12 col-sm-12 col-xs-12 h80" onkeyup="gernerator();" name="judgeContent" id="judgeContent"  title="该项内容为判断的唯一依据">${scoreModel.judgeContent }</textarea>
				  <span class="blue">*该项内容为判断的唯一依据</span>
				</td>
			</tr>
			<tr>
				<td>翻译成白话文内容</td>
				<td><textarea readonly="readonly" class="col-md-12 col-sm-12 col-xs-12 h80" name="easyUnderstandContent" id="easyUnderstandContent1" >${scoreModel.easyUnderstandContent }</textarea></td>
			</tr>
			<tr>
				<td>当前模型标准解释</td>
				<td><textarea class="col-md-12 col-sm-12 col-xs-12 h80" name="standExplain" id="standExplain" value="" readonly="readonly">是否判断.采购文件明确满足或不满足项的临界值或有无的项目要求。评审系统自动识别满足不满足，生成通过或否决的结果，如(必要设备，关键技术，员工人数等)</textarea></td>
			</tr>
		</tbody>
	</table>
	<table id="model21" style="display: none;" class="w499">
		<tbody>
			<tr>
				<td style="width: 300px;">评审参数<input type="hidden" name="judgeModel" value="2" /></td>
				<td>
					<input name="reviewParam" onkeyup="gernerator();" id="reviewParam" value="${scoreModel.reviewParam }" title="例如，近五年获得省以上工商部门颁发知名品牌的数量，一个得1分">
					<br/>
					<span class="blue">*例如，近五年获得省以上工商部门颁发知名品牌的数量，一个得1分</span>
				</td>
			</tr>
			<tr>
				<td>加减分类型</td>
				<td><select name="addSubtractTypeName" id="addSubtractTypeName" onchange="modelTwoAddSubstact21();"><option value="0" selected="selected">加分</option><option value="1">减分</option></select></td>
			</tr>
			<tr>
				<td style="width: 300px;">起始参数</td>
				<td><input name="reviewStandScore" onkeyup="gernerator();" id="reviewStandScore" value="${scoreModel.reviewStandScore }" title="该项的起始分值为多少，默认是0">
				    <br/>
					<span class="blue">*该项的起始分值为多少，默认是0</span>
				</td>
			</tr>
			<tr>
				<td style="width: 300px;">最高分</td>
				<td><input name="maxScore" onkeyup="gernerator();" id="maxScore" value="${scoreModel.maxScore }" title="该项的满分值是多少">
					<br/>
					<span class="blue">*该项的满分值是多少</span>
				</td>
			</tr>
			<tr>
				<td style="width: 300px;">每单位分值</td>
				<td><input name="unitScore" onkeyup="gernerator();" id="unitScore" value="${scoreModel.unitScore }" title="每项单位得分值是多少">
					<br/>
					<span class="blue">*每项单位得分值是多少</span>
				</td>
			</tr>
			<tr>
				<td style="width: 300px;">单位</td>
				<td><input name="unit" id="unit" value="${scoreModel.unit }" title="评审参数的单位">
					<br/>
					<span class="blue">*评审参数的单位</span>
				</td>
			</tr>
			<tr>
				<td>翻译成白话文内容</td>
				<td><textarea readonly="readonly" class="col-md-12 col-sm-12 col-xs-12 h80" name="easyUnderstandContent" id="easyUnderstandContent21">${scoreModel.easyUnderstandContent }</textarea></td>
			</tr>
			<tr>
				<td>当前模型标准解释</td>
				<td><textarea class="col-md-12 col-sm-12 col-xs-12 h80" name="standExplain" id="standExplain"  readonly="readonly">按项加减分.采购文件明确标准分值，加减分项，加减分值和最高最低分值限制，按照加减分项的项目名称，系统自动计算得分。如(正偏离，负偏离)</textarea></td>
			</tr>
		</tbody>
	</table>
	<table id="model22" style="display: none;" class="w499">
		<tbody>
			<tr>
				<td style="width: 300px;">评审参数<input type="hidden" name="judgeModel" value="2" /></td>
				<td><input name="reviewParam" id="reviewParam" onkeyup="gernerator();" value="${scoreModel.reviewParam }" title="例如，近五年获得省以上工商部门颁发知名品牌的数量，一个得1分">
					<br/>
					<span class="blue">*例如，近五年获得省以上工商部门颁发知名品牌的数量，一个得1分</span>
				</td>
			</tr>
			<tr>
				<td>加减分类型</td>
				<td><select name="addSubtractTypeName" id="addSubtractTypeName" onchange="modelTwoAddSubstact22();"><option value="0">加分</option><option value="1" selected="selected">减分</option></select></td>
			</tr>
			<tr>
				<td style="width: 300px;">基准分值</td>
				<td><input name="reviewStandScore" onkeyup="gernerator();" id="moxing2" value="${scoreModel.reviewStandScore }"  title="该项从基准分值往下开始扣分">
					<br/>
					<span class="blue">*该项从基准分值往下开始扣分</span>
				</td>
			</tr>
			<tr>
				<td style="width: 300px;">最低分</td>
				<td><input name="minScore" id="minScore" onkeyup="gernerator();" title="该项的最低分是多少" value="${scoreModel.minScore }">
					<br/>
					<span class="blue">*该项的最低分是多少</span>
				</td>
			</tr>
			<tr>
				<td style="width: 300px;">每单位分值</td>
				<td><input name="unitScore" onkeyup="gernerator();" id="unitScore" value="${scoreModel.unitScore }" title="每项单位减分值是多少">
					<br/>
					<span class="blue">*每项单位减分值是多少</span>
				</td>
			</tr>
			<tr>
				<td style="width: 300px;">单位</td>
				<td><input name="unit" id="unit" value="${scoreModel.unit }" title="评审参数的单位">
					<br/>
					<span class="blue">*评审参数的单位</span>
				</td>
			</tr>
			<tr>
				<td>翻译成白话文内容</td>
				<td><textarea readonly="readonly" class="col-md-12 col-sm-12 col-xs-12 h80" name="easyUnderstandContent" id="easyUnderstandContent22">${scoreModel.easyUnderstandContent }</textarea></td>
			</tr>
			<tr>
				<td>当前模型标准解释</td>
				<td><textarea name="standExplain" id="standExplain" class="col-md-12 col-sm-12 col-xs-12 h80" readonly="readonly">按项加减分.采购文件明确标准分值，加减分项，加减分值和最高最低分值限制，按照加减分项的项目名称，系统自动计算得分。如(正偏离，负偏离)</textarea></td>
			</tr>
		</tbody>
	</table>
	<table id="model3" style="display: none;" class="w499">
		<tbody>
			<tr>
				<td style="width: 300px;">评审参数</td>
				<td><input name="reviewParam" id="reviewParam" value="${scoreModel.reviewParam }" title="例如，矿泉水容量从小到大排列，第一名得最低分，依次递增">
					<br/>
					<span class="blue">*例如，矿泉水容量从小到大排列，第一名得最低分，依次递增</span>
				</td>
			</tr>
			<tr>
				<td style="width: 300px;">单位</td>
				<td><input name="unit" id="unit" value="${scoreModel.unit }" title="评审参数的单位">
					<br/>
					<span class="blue">*评审参数的单位</span>
				</td>
			</tr>
			<tr>
				<td style="width: 300px;">分差</td>
				<td><input name="unitScore" onkeyup="gernerator();" id="score" value="${scoreModel.unitScore }" title="依次加多少分">
					<br/>
					<span class="blue">*依次加多少分</span>
				</td>
			</tr>
			<tr>
				<td>加减分类型</td>
				<td><select name="addSubtractTypeName" id="addSubtractTypeName3"><option value="1" selected="selected">减分</option></select>
					<br/>
					<span class="blue">*以最高分为基准值排序递减</span>
				</td>
			</tr>
			<tr>
				<td style="width: 300px;">最高分</td>
				<td><input name="maxScore" id="maxScore" onkeyup="gernerator();" value="${scoreModel.maxScore }" title="该项的最高值是多少">
					<br/>
					<span class="blue">*该项的最高值是多少</span>
				</td>
			</tr>
			<tr>
				<td style="width: 300px;">最低分</td>
				<td><input name="minScore" id="minScore" onkeyup="gernerator();" value="${scoreModel.minScore }" title="该项的最低分是多少">
					<br/>
					<span class="blue">*该项的最低分是多少</span>
				</td>
			</tr>
			<tr>
				<td>翻译成白话文内容</td>
				<td><textarea readonly="readonly" class="col-md-12 col-sm-12 col-xs-12 h80" name="easyUnderstandContent" id="easyUnderstandContent3" >${scoreModel.easyUnderstandContent }</textarea></td>
			</tr>
			<tr>
				<td>当前模型标准解释</td>
				<td><textarea class="col-md-12 col-sm-12 col-xs-12 h80" name="standExplain" id="standExplain" value="" readonly="readonly">以评审数额最低值位基准排序递增，采购文件明确标准分值，排序分差和最高最低分限制，评审系统按照评审参数值，由高到低按照分差计算得分</textarea></td>
			</tr>
		</tbody>
	</table>
	<table id="model4" style="display: none;" class="w499">
		<tbody>
			<tr>
				<td style="width: 300px;">评审参数</td>
				<td><input name="reviewParam" onkeyup="gernerator();" id="reviewParam" value="${scoreModel.reviewParam }" title="例如自行车重量从小到大排序，第一名得最高分，依次递减分值">
					<br/>
					<span class="blue">*例如自行车重量从小到大排序，第一名得最高分，依次递减分值</span>
				</td>
			</tr>
			<tr>
				<td style="width: 300px;">单位</td>
				<td><input name="unit" id="unit" value="${scoreModel.unit }" title="评审参数的单位">
					<br/>
					<span class="blue">*评审参数的单位</span>
				</td>
			</tr>
			<tr>
				<td style="width: 300px;">分差</td>
				<td><input name="unitScore" id="score" onkeyup="gernerator();" value="${scoreModel.unitScore }" title="依次递减多少分">
					<br/>
					<span class="blue">*依次递减多少分</span>
				</td>
			</tr>
			<tr>
				<td>加减分类型</td>
				<td><select name="addSubtractTypeName" id="addSubtractTypeName4"><option value="0" selected="selected">加分</option></select>
					<br/>
					<span class="blue">*以最低分为基准值排序递增</span>
				</td>
			</tr>
			<tr>
				<td style="width: 300px;">最高分</td>
				<td><input name="maxScore" id="maxScore" onkeyup="gernerator();" value="${scoreModel.maxScore }" title="该项的最高值是多少">
					<br/>
					<span class="blue">*该项的最高值是多少</span>
				</td>
			</tr>
			<tr>
				<td style="width: 300px;">最低分</td>
				<td><input name="minScore" id="minScore" onkeyup="gernerator();" value="${scoreModel.minScore }" title="该项的最低值是多少">
					<br/>
					<span class="blue">*该项的最低值是多少</span>
				</td>
			</tr>
			<tr>
				<td>翻译成白话文内容</td>
				<td><textarea readonly="readonly" class="col-md-12 col-sm-12 col-xs-12 h80" name="easyUnderstandContent" id="easyUnderstandContent4" >${scoreModel.easyUnderstandContent }</textarea></td>
			</tr>
			<tr>
				<td>当前模型标准解释</td>
				<td><textarea class="col-md-12 col-sm-12 col-xs-12 h80" name="standExplain" id="standExplain" value="" readonly="readonly">以评审数额最高值位基准排序递减，采购文件明确标准分值，排序分差和最高最低分限制，评审系统按照评审参数值，由高到低按照分差计算得分</textarea></td>
			</tr>
		</tbody>
	</table>
	<table id="model5" style="display: none;" class="w499">
		<tbody>
			<tr>
				<td style="width: 300px;">评审参数</td>
				<td><input name="reviewParam" onkeyup="gernerator();" id="reviewParam" value="${scoreModel.reviewParam }" title="例：根据企业近三年平均资产总额评分，最高值为基准分值,得分=(企业资产总额/基准值)*标准分值">
					<br/>
					<span class="blue">*例：根据企业近三年平均资产总额评分，最高值为基准分值,得分=(企业资产总额/基准值)*标准分值</span>
				</td>
			</tr>
			<tr>
				<td style="width: 300px;">标准分值</td>
				<td><input name="standardScore" onkeyup="gernerator();" id="standardScore" value="${scoreModel.standardScore }" title="该项的满分值为多少">
					<br/>
					<span class="blue">*该项的满分值为多少</span>
				</td>
			</tr>
			<tr>
				<td style="width: 300px;">单位</td>
				<td><input name="unit" id="unit" value="${scoreModel.unit }" title="评审参数的单位">
					<br/>
					<span class="blue">*评审参数的单位</span>
				</td>
			</tr>
			<tr>
				<td>翻译成白话文内容</td>
				<td><textarea readonly="readonly" class="col-md-12 col-sm-12 col-xs-12 h80" name="easyUnderstandContent" id="easyUnderstandContent5" >${scoreModel.easyUnderstandContent }</textarea></td>
			</tr>
			<tr>
				<td>当前模型标准解释</td>
				<td><textarea class="col-md-12 col-sm-12 col-xs-12 h80" name="standExplain" id="standExplain" value="" readonly="readonly">以评审数额最高值为基准，系统自动按照公式计算得分，得分=(投标人数值/评审参数的最高数额)*满分值</textarea></td>
			</tr>
		</tbody>
	</table>
	<table id="model6" style="display: none;" class="w499">
		<tbody>
			<tr>
				<td style="width: 300px;">评审参数</td>
				<td><input name="reviewParam" onkeyup="gernerator();" id="reviewParam" value="${scoreModel.reviewParam }" title="例:满足招标文件要求且报价最低为基准值，得分=(基准值/最低报价)*标准分值 ">
					<br/>
					<span class="blue">*例:满足招标文件要求且报价最低为基准值，得分=(基准值/最低报价)*标准分值</span>
				</td>
			</tr>
			<tr>
				<td style="width: 300px;">标准分值</td>
				<td><input name="standardScore" onkeyup="gernerator();" id="standardScore" value="${scoreModel.standardScore }" title="该项的满分值为多少">
					<br/>
					<span class="blue">*该项的满分值为多少</span>
				</td>
			</tr>
			<tr>
				<td style="width: 300px;">单位</td>
				<td><input name="unit" id="unit" value="${scoreModel.unit }" title="评审参数的单位">
					<br/>
					<span class="blue">*评审参数的单位</span>
				</td>
			</tr>
			<!-- <tr>
				<td>加减分类型</td>
				<td><select name="addSubtractTypeName" id="addSubtractTypeName4"><option value="0">加分</option><option value="1" selected="selected">减分</option></select></td>
			</tr> -->
			<tr>
				<td>翻译成白话文内容</td>
				<td><textarea readonly="readonly" class="col-md-12 col-sm-12 col-xs-12 h80" name="easyUnderstandContent" id="easyUnderstandContent6" >${scoreModel.easyUnderstandContent }</textarea></td>
			</tr>
			<tr>
				<td>当前模型标准解释</td>
				<td><textarea class="col-md-12 col-sm-12 col-xs-12 h80" name="standExplain" id="standExplain" value="" readonly="readonly">以评审数额最低值为基准，系统自动按照公式计算得分，得分=(基准值/评审参数额)*满分值</textarea></td>
			</tr>
		</tbody>
	</table>
	<table id="model71" style="display: none;" class="w499">
		<tbody>
			<tr>
				<td style="width: 300px;">评审参数</td>
				<td><input name="reviewParam" onkeyup="gernerator();" id="reviewParam" value="${scoreModel.reviewParam }" title="例:百公里油耗,6升以下为满分，每增加一升扣0.5分，其中百公里油耗为评审参数">
					<br/>
					<span class="blue">*例:百公里油耗,6升以下为满分，每增加一升扣0.5分，其中百公里油耗为评审参数</span>
				</td>
			</tr>
			<tr>
				<td style="width: 300px;">单位</td>
				<td><input name="unit" id="unit" value="${scoreModel.unit }" title="评审参数的单位">
					<br/>
					<span class="blue">*评审参数的单位</span>
				</td>
			</tr>
			<tr>
				<td style="width: 300px;">区间类型</td>
				<td><select name="intervalTypeName" id="intervalTypeName71" onchange="modelSevenAddSubstact71();"><option value="0" selected="selected">差额相等</option><option value="1">差额区间</option></select></td>
			</tr>
			<tr>
				<td style="width: 300px;">评审基准数</td>
				<td><input name="reviewStandScore" onkeyup="gernerator();" id="reviewStandScore" value="${scoreModel.reviewStandScore }" title="该项内容为评审参数参照值">
					<br/>
					<span class="blue">*该项内容为评审参数参照值</span>
				</td>
			</tr>
			<tr>
				<td style="width: 300px;">每区间等差额</td>
				<td><input name="intervalNumber" onkeyup="gernerator();" id="intervalNumber" value="${scoreModel.intervalNumber }" title="每个区间之间的差额">
					<br/>
					<span class="blue">*每个区间之间的差额</span>
				</td>
			</tr>
			<tr>
				<td>加减分类型</td>
				<td><select name="addSubtractTypeName" id="addSubtractTypeName7"><option value="0" selected="selected">加分</option><option value="1">减分</option></select>
					<br/>
					<span class="blue">*如果为[加分]，那么高于[评审基准数]为0分，低于[评审基准数]按照规则加分；如果为[减分]，那么高于[评审基准数]为满分，低于[评审基准数]按照规则减分</span>
				</td>
			</tr>
			<tr>
				<td style="width: 300px;">加减分分值</td>
				<td><input name="score"  id="score" onkeyup="gernerator();" value="${scoreModel.score }" title="加减多少分">
					<br/>
					<span class="blue">*加减多少分</span>
				</td>
			</tr>
			<tr>
				<td style="width: 300px;">评审参数截止数</td>
				<td><input name="deadlineNumber" onkeyup="gernerator();" id="deadlineNumber" value="${scoreModel.deadlineNumber }" title="如果加分，高于截止数为满分，如果减分，高于截止数为0分">
					<br/>
					<span class="blue">*如果加分，高于截止数为满分，如果减分，高于截止数为0分</span>
				</td>
			</tr>
			<tr>
				<td style="width: 300px;">最高分</td>
				<td><input name="maxScore" id="maxScore" onkeyup="gernerator();" value="${scoreModel.maxScore }" title="该项的满分值是多少">
					<br/>
					<span class="blue">*该项的满分值是多少</span>
				</td>
			</tr>
			<tr>
				<td style="width: 300px;">最低分</td>
				<td><input name="minScore" id="minScore" onkeyup="gernerator();" value="${scoreModel.minScore }" title="该项的最低分是多少">
					<br/>
					<span class="blue">*该项的最低分是多少</span>
				</td>
			</tr>
			<tr>
				<td>翻译成白话文内容</td>
				<td><textarea readonly="readonly" class="col-md-12 col-sm-12 col-xs-12 h80" name="easyUnderstandContent" id="easyUnderstandContent7" >${scoreModel.easyUnderstandContent }</textarea></td>
			</tr>
			<tr>
				<td>当前模型标准解释</td>
				<td><textarea class="col-md-12 col-sm-12 col-xs-12 h80" name="standExplain" id="standExplain" value="" readonly="readonly">以评审数额最低区间为基准递增排序，采购文件明确规定标准分值，分差和最低最高分值限制，并按分差计算规则计算得分</textarea></td>
			</tr>
			
		</tbody>
	</table>
	<table id="model72" style="display: none;" class="w499">
		<tbody>
			<tr>
				<td style="width: 300px;">评审参数</td>
				<td><input name="reviewParam" id="reviewParam" value="${scoreModel.reviewParam }" title="例:百公里油耗,6升以下为满分，每增加一升扣0.5分，其中百公里油耗为评审参数">
					<br/>
					<span class="blue">*例:百公里油耗,6升以下为满分，每增加一升扣0.5分，其中百公里油耗为评审参数</span>
				</td>
			</tr>
			<tr>
				<td style="width: 300px;">单位</td>
				<td><input name="unit" id="unit" value="${scoreModel.unit }" title="评审参数的单位">
					<br/>
					<span class="blue">*评审参数的单位</span>
				</td>
			</tr>
			<tr>
				<td style="width: 300px;">区间类型</td>
				<td><select name="intervalTypeName" id="intervalTypeName72" onchange="modelSevenAddSubstact72();"><option value="0">差额相等</option><option value="1" selected="selected">差额区间</option></select></td>
			</tr>
		</tbody>
	</table>
	<table id="model81" style="display: none;" class="w499">
		<tbody>
			<tr>
				<td style="width: 300px;">评审参数</td>
				<td><input name="reviewParam" onkeyup="gernerator();" id="reviewParam" value="${scoreModel.reviewParam }" title="例:生产工序，十道以上得满分，每减少两项减0.5分，最低分为0分，其中生产工序为评审参数">
					<br/>
					<span class="blue">*例:生产工序，十道以上得满分，每减少两项减0.5分，最低分为0分，其中生产工序为评审参数</span>
				</td>
			</tr>
			<tr>
				<td style="width: 300px;">单位</td>
				<td><input name="unit" id="unit" value="${scoreModel.unit }" title="评审参数的单位">
					<br/>
					<span class="blue">*评审参数的单位</span>
				</td>
			</tr>
			<tr>
				<td style="width: 300px;">区间类型</td>
				<td><select name="intervalTypeName" id="intervalTypeName81" onchange="modelSevenAddSubstact81();"><option value="0" selected="selected">差额相等</option><option value="1">差额区间</option></select></td>
			</tr>
			<tr>
				<td style="width: 300px;">评审基准数</td>
				<td><input name="reviewStandScore" id="reviewStandScore" value="${scoreModel.reviewStandScore }" title="该项内容为评审参数参照值">
					<br/>
					<span class="blue">*该项内容为评审参数参照值</span>
				</td>
			</tr>
			<tr>
				<td style="width: 300px;">每区间等差额</td>
				<td><input name="intervalNumber" onkeyup="gernerator();" id="intervalNumber" value="${scoreModel.intervalNumber }" title="每个区间之间的差额">
					<br/>
					<span class="blue">*每个区间之间的差额</span>
				</td>
			</tr>
			<tr>
				<td>加减分类型</td>
				<td><select name="addSubtractTypeName" id="addSubtractTypeName8"><option value="0" selected="selected">加分</option><option value="1">减分</option></select>
					<br/>
					<span class="blue">*如果为[加分]，那么低于[评审基准数]为0分，高于[评审基准数]按照规则加分；如果为减分，那么低于[评审基准数]为满分，高于[评审基准数]按照规则减分</span>
				</td>
			</tr>
			<tr>
				<td style="width: 300px;">加减分分值</td>
				<td><input name="score" id="score" onkeyup="gernerator();" value="${scoreModel.score }" title="加减多少分">
					<br/>
					<span class="blue">*加减多少分</span>
				</td>
			</tr>
			<tr>
				<td style="width: 300px;">评审参数截止数</td>
				<td><input name="deadlineNumber" onkeyup="gernerator();" id="deadlineNumber" value="${scoreModel.deadlineNumber }" title="如果加分，低于截止数为满分，如果减分，低于截止数为0分">
					<br/>
					<span class="blue">*如果加分，低于截止数为满分，如果减分，低于截止数为0分</span>
				</td>
			</tr>
			<tr>
				<td style="width: 300px;">最高分</td>
				<td><input name="maxScore" onkeyup="gernerator();" id="maxScore" value="${scoreModel.maxScore }" title="该项的满分值是多少">
					<br/>
					<span class="blue">*该项的满分值是多少</span>
				</td>
			</tr>
			<tr>
				<td style="width: 300px;">最低分</td>
				<td><input name="minScore" onkeyup="gernerator();" id="minScore" value="${scoreModel.minScore }" title="该项的最低分是多少">
					<br/>
					<span class="blue">*该项的最低分是多少</span>
				</td>
			</tr>
			<tr>
				<td>翻译成白话文内容</td>
				<td><textarea readonly="readonly" class="col-md-12 col-sm-12 col-xs-12 h80" name="easyUnderstandContent" id="easyUnderstandContent8" >${scoreModel.easyUnderstandContent }</textarea></td>
			</tr>
			<tr>
				<td>当前模型标准解释</td>
				<td><textarea class="col-md-12 col-sm-12 col-xs-12 h80" name="standExplain" id="standExplain" value="" readonly="readonly">以评审数额最低区间为基准递增排序，采购文件明确规定标准分值，分差和最低最高分值限制，并按分差计算规则计算得分</textarea></td>
			</tr>
			
		</tbody>
	</table>
	<table id="model82" style="display: none;" class="w499">
		<tbody>
			<tr>
				<td style="width: 300px;">评审参数</td>
				<td><input name="reviewParam" id="reviewParam" value="${scoreModel.reviewParam }" title="例:百公里油耗,6升以下为满分，每增加一升扣0.5分，其中百公里油耗为评审参数">
					<br/>
					<span class="blue">*例:百公里油耗,6升以下为满分，每增加一升扣0.5分，其中百公里油耗为评审参数</span>
				</td>
			</tr>
			<tr>
				<td style="width: 300px;">单位</td>
				<td><input name="unit" id="unit" value="${scoreModel.unit }" title="评审参数的单位">
					<br/>
					<span class="blue">*评审参数的单位</span>
				</td>
			</tr>
			<tr>
				<td style="width: 300px;">区间类型</td>
				<td><select name="intervalTypeName" id="intervalTypeName82" onchange="modelSevenAddSubstact82();"><option value="0">差额相等</option><option value="1" selected="selected">差额区间</option></select></td>
			</tr>
		</tbody>
	</table>
	<!-- 八大模型 -->
</body>
</html>