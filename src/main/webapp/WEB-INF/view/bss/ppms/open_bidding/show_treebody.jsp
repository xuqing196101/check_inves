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
		var model = $("#addSubtractTypeName21").val();
		if(model=="0"){
			$("#show_table tbody tr").remove();
			$("#model21 tbody tr").clone().appendTo("#show_table tbody");
		}else{
			$("#show_table tbody tr").remove();
			$("#model22 tbody tr").clone().appendTo("#show_table tbody");
		}
	}
	function modelTwoAddSubstact22(){
		var model = $("#addSubtractTypeName22").val();
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
		var judgeNumber = $("#judgeNumber").val();
		var str = judgeContent + judgeNumber + " "+"是"+standardScore+"分 "+"否0分";
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
		var s = validteModel().form();
		console.dir(s);
		if(s){
			$("#formID").attr('action','${pageContext.request.contextPath}/intelligentScore/operatorScoreModel.do').submit();
		}else{
			return;
		}
	}
	function pageOnLoad(){
		var model = $("#sm").val();
		$("#showParamButton").hide();
		$("#model").val(model);
		$("#interval_prarm").hide();
		//console.dir(model==undefined);
		if(model !=undefined && model==""){
			$("#showbutton").hide();
			$("#show_table tbody tr").remove();
		}else if(model=="0"){
			$("#show_table tbody tr").remove();
			$("#model1 tbody tr").clone().appendTo("#show_table tbody");
			$("#showbutton").show();
		}else if(model=="1"){
			var addSubtractTypeName = $("#sm2").val();
			$("#addSubtractTypeName").val(addSubtractTypeName);
			$("#show_table tbody tr").remove();
			if(addSubtractTypeName!=undefined && addSubtractTypeName=="0"){
				$("#model21 tbody tr").clone().appendTo("#show_table tbody");
			}else if(addSubtractTypeName=="1"){
				$("#model22 tbody tr").clone().appendTo("#show_table tbody");
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
				$("#interval_prarm").show();
			}else if(intervalTypeName71=="0"){
				$("#showbutton").show();
				$("#showParamButton").hide();
				$("#model71 tbody tr").clone().appendTo("#show_table tbody");
				$("#model73 tr:not(:first)").remove();//删除除了第一行的所有tr 
				$("#model73").hide();
				$("#interval_prarm").hide();
			}else{
				$("#showbutton").show();
				$("#showParamButton").hide();
				$("#model71 tbody tr").clone().appendTo("#show_table tbody");
				$("#model73 tr:not(:first)").remove();//删除除了第一行的所有tr 
				$("#model73").hide();
				$("#interval_prarm").hide();
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
				$("#model82 tbody tr").clone().appendTo("#show_table tbody");
				$("#model73").show();
				$("#interval_prarm").show();
			}else if(intervalTypeName71=="0"){
				$("#showbutton").show();
				$("#showParamButton").hide();
				$("#model81 tbody tr").clone().appendTo("#show_table tbody");
				$("#model73 tr:not(:first)").remove();//删除除了第一行的所有tr 
				$("#model73").hide();
				$("#interval_prarm").hide();
			}else{
				$("#showbutton").show();
				$("#showParamButton").hide();
				$("#model81 tbody tr").clone().appendTo("#show_table tbody");
				$("#model73 tr:not(:first)").remove();//删除除了第一行的所有tr 
				$("#model73").hide();
				$("#interval_prarm").hide();
			}
		}
	}
</script>  
<script type="text/javascript">
	
</script>
    </head>
<body onload="pageOnLoad();">
	<input type="hidden" id="sm" value="${scoreModel.typeName }">
	<input type="hidden" id="sm2" value="${scoreModel.addSubtractTypeName }">
	<input type="hidden" id="sm7" value="${scoreModel.intervalTypeName }">
	<div>
		<!-- 八大模型 -->

		<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li><a href="javascript:void(0);"> 首页</a></li>
					<li><a href="javascript:void(0);">保障作业</a></li>
					<li><a href="javascript:void(0);">采购项目管理</a></li>
					<li><a href="javascript:void(0);">立项管理</a></li>
					<li class="active"><a href="javascript:void(0);">评分细则</a></li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<!--详情开始-->
		<div class="container content pt0">
			<div class="row magazine-page">
				<div class="col-md-12 tab-v2">
					<div class="padding-top-10">
						<ul class="nav nav-tabs bgwhite">
							<li class="active"><a aria-expanded="true" href="#tab-1"
								data-toggle="tab" class="s_news f18">详细信息</a></li>
						</ul>
						<div class="tab-content padding-top-20">
							<div class="tab-pane fade active in" id="tab-1">
								<h2 class="count_flow jbxx">基本信息</h2>
								<table
									class="table table-bordered" 
									id="show_table">
									<tbody>
									</tbody>
								</table>
								<h2 class="count_flow jbxx" id="interval_prarm">参数区间信息</h2>
								<table id="model73" style="display: none;"
									class="table table-striped table-bordered table-hover mt20">
									<thead>
										<tr id="paramIntervalTr">
											<!-- <th class="w30"><input type="checkbox">
						</th> -->
											<th class="info w50">序号</th>
											<th class="info">起始值</th>
											<!-- <th class="w500">与起始参数值关系</th>
						<th class="w500">评审参数对应数值</th>
						<th class="w500">与结束参数值关系</th> -->
											<th class="info">结束值</th>
											<th class="info">得分</th>
											<th class="info">解释</th>
										</tr>
									</thead>

									<tbody>
										<c:forEach items="${scoreModel.paramIntervalList }" var="pi"
											varStatus="vs">
											<tr class="cursor">
												<td class="tc">${vs.index+1 }</td>
												<td class="tc" id="startParam${vs.index+1}">${pi.startParam}</td>
												<td class="tc" id="endParam${vs.index+1}">${pi.endParam}</td>
												<td class="tc" id="score${vs.index+1}">${pi.score}</td>
												<td class="tc">${pi.explain}</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<!-- 八大模型 -->
							<table id="model1" style="display: none;" >
								<tbody>
									<tr>
										<td class="bggrey">模型</td>
										<td>
											<c:choose>
												<c:when test="${scoreModel.typeName==0}">
												模型1:是否判断
												</c:when>
												<c:when test="${scoreModel.typeName==1}">
												模型2:按项加减分
												</c:when>
												<c:when test="${scoreModel.typeName==2}">
												模型3:评审数额最高递减
												</c:when>
												<c:when test="${scoreModel.typeName==3}">
												模型4:评审数额最低递增
												</c:when>
												<c:when test="${scoreModel.typeName==4}">
												模型5:评审数额高计算
												</c:when>
												<c:when test="${scoreModel.typeName==5}">
												模型6:评审数额低计算
												</c:when>
												<c:when test="${scoreModel.typeName==6}">
												模型7:评审数额低区间递增
												</c:when>
												<c:when test="${scoreModel.typeName==7}">
												模型8:评审数额高区间递减
												</c:when>
												<c:otherwise>

												</c:otherwise>
											</c:choose>
										</td>
									</tr>
									<tr>
										<td class="bggrey">标准分值</td>
										<td>${scoreModel.standardScore }</td>
									</tr>
									<tr>
										<td class="bggrey">判断内容</td>
										<td>${scoreModel.judgeContent }
										</td>
									</tr>
									<tr>
										<td class="bggrey">翻译成白话文内容</td>
										<td>${scoreModel.easyUnderstandContent }
										</td>
									</tr>
									<tr>
										<td class="bggrey">当前模型标准解释</td>
										<td>
											是否判断.采购文件明确满足或不满足项的临界值或有无的项目要求。评审系统自动识别满足不满足，生成通过或否决的结果，如(必要设备，关键技术，员工人数等)
										</td>
									</tr>
								</tbody>
							</table>
							<table id="model21" style="display: none;" >
								<tbody>
									<tr>
										<td class="bggrey">模型</td>
										<td>
											<c:choose>
												<c:when test="${scoreModel.typeName==0}">
												模型1:是否判断
												</c:when>
												<c:when test="${scoreModel.typeName==1}">
												模型2:按项加减分
												</c:when>
												<c:when test="${scoreModel.typeName==2}">
												模型3:评审数额最高递减
												</c:when>
												<c:when test="${scoreModel.typeName==3}">
												模型4:评审数额最低递增
												</c:when>
												<c:when test="${scoreModel.typeName==4}">
												模型5:评审数额高计算
												</c:when>
												<c:when test="${scoreModel.typeName==5}">
												模型6:评审数额低计算
												</c:when>
												<c:when test="${scoreModel.typeName==6}">
												模型7:评审数额低区间递增
												</c:when>
												<c:when test="${scoreModel.typeName==7}">
												模型8:评审数额高区间递减
												</c:when>
												<c:otherwise>

												</c:otherwise>
											</c:choose>
										</td>
									</tr>
									<tr>
										<td class="bggrey">评审参数</td>
										<td>${scoreModel.reviewParam }</td>
									</tr>
									<tr>
										<td class="bggrey">加减分类型</td>
										<td>加分
										</td>
									</tr>
									<tr>
										<td class="bggrey">起始参数</td>
										<td>${scoreModel.reviewStandScore }</td>
									</tr>
									<tr>
										<td class="bggrey">最高分</td>
										<td>${scoreModel.maxScore }</td>
									</tr>
									<tr>
										<td class="bggrey">每单位分值</td>
										<td>${scoreModel.unitScore }
										</td>
									</tr>
									<tr>
										<td class="bggrey">单位</td>
										<td>${scoreModel.unit }</td>
									</tr>
									<tr>
										<td class="bggrey">翻译成白话文内容</td>
										<td>${scoreModel.easyUnderstandContent }
										</td>
									</tr>
									<tr>
										<td class="bggrey">当前模型标准解释</td>
										<td>按项加减分.采购文件明确标准分值，加减分项，加减分值和最高最低分值限制，按照加减分项的项目名称，系统自动计算得分。如(正偏离，负偏离)
										</td>
									</tr>
								</tbody>
							</table>
							<table id="model22" style="display: none;" >
								<tbody>
									<tr>
										<td class="bggrey">模型</td>
										<td>
											<c:choose>
												<c:when test="${scoreModel.typeName==0}">
												模型1:是否判断
												</c:when>
												<c:when test="${scoreModel.typeName==1}">
												模型2:按项加减分
												</c:when>
												<c:when test="${scoreModel.typeName==2}">
												模型3:评审数额最高递减
												</c:when>
												<c:when test="${scoreModel.typeName==3}">
												模型4:评审数额最低递增
												</c:when>
												<c:when test="${scoreModel.typeName==4}">
												模型5:评审数额高计算
												</c:when>
												<c:when test="${scoreModel.typeName==5}">
												模型6:评审数额低计算
												</c:when>
												<c:when test="${scoreModel.typeName==6}">
												模型7:评审数额低区间递增
												</c:when>
												<c:when test="${scoreModel.typeName==7}">
												模型8:评审数额高区间递减
												</c:when>
												<c:otherwise>

												</c:otherwise>
											</c:choose>
										</td>
									</tr>
									<tr>
										<td class="bggrey">评审参数</td>
										<td>${scoreModel.reviewParam }</td>
									</tr>
									<tr>
										<td class="bggrey">加减分类型</td>
										<td>减分</td>
									</tr>
									<tr>
										<td class="bggrey">基准分值</td>
										<td>${scoreModel.reviewStandScore }
										</td>
									</tr>
									<tr>
										<td class="bggrey">最低分</td>
										<td>${scoreModel.minScore }</td>
									</tr>
									<tr>
										<td class="bggrey">每单位分值</td>
										<td>${scoreModel.unitScore }
										</td>
									</tr>
									<tr>
										<td class="bggrey">单位</td>
										<td>${scoreModel.unit }</td>
									</tr>
									<tr>
										<td class="bggrey">翻译成白话文内容</td>
										<td>${scoreModel.easyUnderstandContent }
										</td>
									</tr>
									<tr>
										<td class="bggrey">当前模型标准解释</td>
										<td>按项加减分.采购文件明确标准分值，加减分项，加减分值和最高最低分值限制，按照加减分项的项目名称，系统自动计算得分。如(正偏离，负偏离)
										</td>
									</tr>
								</tbody>
							</table>
							<table id="model3" style="display: none;" >
								<tbody>
									<tr>
										<td class="bggrey">模型</td>
										<td>
											<c:choose>
												<c:when test="${scoreModel.typeName==0}">
												模型1:是否判断
												</c:when>
												<c:when test="${scoreModel.typeName==1}">
												模型2:按项加减分
												</c:when>
												<c:when test="${scoreModel.typeName==2}">
												模型3:评审数额最高递减
												</c:when>
												<c:when test="${scoreModel.typeName==3}">
												模型4:评审数额最低递增
												</c:when>
												<c:when test="${scoreModel.typeName==4}">
												模型5:评审数额高计算
												</c:when>
												<c:when test="${scoreModel.typeName==5}">
												模型6:评审数额低计算
												</c:when>
												<c:when test="${scoreModel.typeName==6}">
												模型7:评审数额低区间递增
												</c:when>
												<c:when test="${scoreModel.typeName==7}">
												模型8:评审数额高区间递减
												</c:when>
												<c:otherwise>

												</c:otherwise>
											</c:choose>
										</td>
									</tr>
									<tr>
										<td class="bggrey">评审参数</td>
										<td>${scoreModel.reviewParam }</td>
									</tr>
									<tr>
										<td class="bggrey">单位</td>
										<td>${scoreModel.unit }</td>
									</tr>
									<tr>
										<td class="bggrey">分差</td>
										<td>${scoreModel.score }</td>
									</tr>
									<tr>
										<td class="bggrey">加减分类型</td>
										<td>减分</td>
									</tr>
									<tr>
										<td class="bggrey">最高分</td>
										<td>${scoreModel.maxScore }</td>
									</tr>
									<tr>
										<td class="bggrey">最低分</td>
										<td>${scoreModel.minScore }</td>
									</tr>
									<tr>
										<td class="bggrey">翻译成白话文内容</td>
										<td>${scoreModel.easyUnderstandContent }
										</td>
									</tr>
									<tr>
										<td class="bggrey">当前模型标准解释</td>
										<td>以评审数额最低值位基准排序递增，采购文件明确标准分值，排序分差和最高最低分限制，评审系统按照评审参数值，由高到低按照分差计算得分
										</td>
									</tr>
								</tbody>
							</table>
							<table id="model4" style="display: none;" >
								<tbody>
									<tr>
										<td class="bggrey">模型</td>
										<td>
											<c:choose>
												<c:when test="${scoreModel.typeName==0}">
												模型1:是否判断
												</c:when>
												<c:when test="${scoreModel.typeName==1}">
												模型2:按项加减分
												</c:when>
												<c:when test="${scoreModel.typeName==2}">
												模型3:评审数额最高递减
												</c:when>
												<c:when test="${scoreModel.typeName==3}">
												模型4:评审数额最低递增
												</c:when>
												<c:when test="${scoreModel.typeName==4}">
												模型5:评审数额高计算
												</c:when>
												<c:when test="${scoreModel.typeName==5}">
												模型6:评审数额低计算
												</c:when>
												<c:when test="${scoreModel.typeName==6}">
												模型7:评审数额低区间递增
												</c:when>
												<c:when test="${scoreModel.typeName==7}">
												模型8:评审数额高区间递减
												</c:when>
												<c:otherwise>

												</c:otherwise>
											</c:choose>
										</td>
									</tr>
									<tr>
										<td class="bggrey">评审参数</td>
										<td>${scoreModel.reviewParam }</td>
									</tr>
									<tr>
										<td class="bggrey">单位</td>
										<td>${scoreModel.unit }</td>
									</tr>
									<tr>
										<td class="bggrey">分差</td>
										<td>${scoreModel.score }</td>
									</tr>
									<tr>
										<td class="bggrey">加减分类型</td>
										<td>加分</td>
									</tr>
									<tr>
										<td class="bggrey">最高分</td>
										<td>${scoreModel.maxScore }</td>
									</tr>
									<tr>
										<td class="bggrey">最低分</td>
										<td>${scoreModel.minScore }</td>
									</tr>
									<tr>
										<td class="bggrey">翻译成白话文内容</td>
										<td>${scoreModel.easyUnderstandContent }
										</td>
									</tr>
									<tr>
										<td class="bggrey">当前模型标准解释</td>
										<td>以评审数额最高值位基准排序递减，采购文件明确标准分值，排序分差和最高最低分限制，评审系统按照评审参数值，由高到低按照分差计算得分
										</td>
									</tr>
								</tbody>
							</table>
							<table id="model5" style="display: none;">
								<tbody>
									<tr>
										<td class="bggrey">模型</td>
										<td>
											<c:choose>
												<c:when test="${scoreModel.typeName==0}">
												模型1:是否判断
												</c:when>
												<c:when test="${scoreModel.typeName==1}">
												模型2:按项加减分
												</c:when>
												<c:when test="${scoreModel.typeName==2}">
												模型3:评审数额最高递减
												</c:when>
												<c:when test="${scoreModel.typeName==3}">
												模型4:评审数额最低递增
												</c:when>
												<c:when test="${scoreModel.typeName==4}">
												模型5:评审数额高计算
												</c:when>
												<c:when test="${scoreModel.typeName==5}">
												模型6:评审数额低计算
												</c:when>
												<c:when test="${scoreModel.typeName==6}">
												模型7:评审数额低区间递增
												</c:when>
												<c:when test="${scoreModel.typeName==7}">
												模型8:评审数额高区间递减
												</c:when>
												<c:otherwise>

												</c:otherwise>
											</c:choose>
										</td>
									</tr>
									<tr>
										<td class="bggrey">评审参数</td>
										<td>${scoreModel.reviewParam }
										</td>
									</tr>
									<tr>
										<td class="bggrey">标准分值</td>
										<td>${scoreModel.standardScore }
										</td>
									</tr>
									<tr>
										<td class="bggrey">单位</td>
										<td>${scoreModel.unit }</td>
									</tr>
									<tr>
										<td class="bggrey">翻译成白话文内容</td>
										<td>${scoreModel.easyUnderstandContent }
										</td>
									</tr>
									<tr>
										<td class="bggrey">当前模型标准解释</td>
										<td>以评审数额最高值为基准，系统自动按照公式计算得分，得分=(投标人数值/评审参数的最高数额)*满分值
										</td>
									</tr>
								</tbody>
							</table>
							<table id="model6" style="display: none;" >
								<tbody>
									<tr>
										<td class="bggrey">模型</td>
										<td>
											<c:choose>
												<c:when test="${scoreModel.typeName==0}">
												模型1:是否判断
												</c:when>
												<c:when test="${scoreModel.typeName==1}">
												模型2:按项加减分
												</c:when>
												<c:when test="${scoreModel.typeName==2}">
												模型3:评审数额最高递减
												</c:when>
												<c:when test="${scoreModel.typeName==3}">
												模型4:评审数额最低递增
												</c:when>
												<c:when test="${scoreModel.typeName==4}">
												模型5:评审数额高计算
												</c:when>
												<c:when test="${scoreModel.typeName==5}">
												模型6:评审数额低计算
												</c:when>
												<c:when test="${scoreModel.typeName==6}">
												模型7:评审数额低区间递增
												</c:when>
												<c:when test="${scoreModel.typeName==7}">
												模型8:评审数额高区间递减
												</c:when>
												<c:otherwise>

												</c:otherwise>
											</c:choose>
										</td>
									</tr>
									<tr>
										<td class="bggrey">评审参数</td>
										<td>${scoreModel.reviewParam }</td>
									</tr>
									<tr>
										<td class="bggrey">标准分值</td>
										<td>${scoreModel.standardScore }
										</td>
									</tr>
									<tr>
										<td class="bggrey">单位</td>
										<td>${scoreModel.unit }</td>
									</tr>
									<tr>
										<td class="bggrey">翻译成白话文内容</td>
										<td>${scoreModel.easyUnderstandContent }
										</td>
									</tr>
									<tr>
										<td class="bggrey">当前模型标准解释</td>
										<td>以评审数额最低值为基准，系统自动按照公式计算得分，得分=(基准值/评审参数额)*满分值
										</td>
									</tr>
								</tbody>
							</table>
							<table id="model71" style="display: none;" >
								<tbody>
									<tr>
										<td class="bggrey">模型</td>
										<td>
											<c:choose>
												<c:when test="${scoreModel.typeName==0}">
												模型1:是否判断
												</c:when>
												<c:when test="${scoreModel.typeName==1}">
												模型2:按项加减分
												</c:when>
												<c:when test="${scoreModel.typeName==2}">
												模型3:评审数额最高递减
												</c:when>
												<c:when test="${scoreModel.typeName==3}">
												模型4:评审数额最低递增
												</c:when>
												<c:when test="${scoreModel.typeName==4}">
												模型5:评审数额高计算
												</c:when>
												<c:when test="${scoreModel.typeName==5}">
												模型6:评审数额低计算
												</c:when>
												<c:when test="${scoreModel.typeName==6}">
												模型7:评审数额低区间递增
												</c:when>
												<c:when test="${scoreModel.typeName==7}">
												模型8:评审数额高区间递减
												</c:when>
												<c:otherwise>

												</c:otherwise>
											</c:choose>
										</td>
									</tr>
									<tr>
										<td class="bggrey">评审参数</td>
										<td>${scoreModel.reviewParam }</td>
									</tr>
									<tr>
										<td class="bggrey">单位</td>
										<td>${scoreModel.unit }</td>
									</tr>
									<tr>
										<td class="bggrey">区间类型</td>
										<td>
											<c:choose>
												<c:when test="${scoreModel.intervalTypeName==0}">
													差额相等
												</c:when>
												<c:when test="${scoreModel.intervalTypeName==1}">
													差额区间
												</c:when>
												<c:otherwise>

												</c:otherwise>
											</c:choose>
										</td>
									</tr>
									<tr>
										<td class="bggrey">评审基准数</td>
										<td>${scoreModel.reviewStandScore }
										</td>
									</tr>
									<tr>
										<td class="bggrey">每区间等差额</td>
										<td>${scoreModel.intervalNumber }
										</td>
									</tr>
									<tr>
										<td class="bggrey">加减分类型</td>
										<td>
											<c:choose>
												<c:when test="${scoreModel.addSubtractTypeName==0}">
													加分
												</c:when>
												<c:when test="${scoreModel.addSubtractTypeName==1}">
													减分
												</c:when>
												<c:otherwise>

												</c:otherwise>
											</c:choose>
										</td>
									</tr>
									<tr>
										<td class="bggrey">加减分分值</td>
										<td>${scoreModel.score }</td>
									</tr>
									<tr>
										<td class="bggrey">评审参数截止数</td>
										<td>${scoreModel.deadlineNumber }</td>
									</tr>
									<tr>
										<td class="bggrey">最高分</td>
										<td>${scoreModel.maxScore }</td>
									</tr>
									<tr>
										<td class="bggrey">最低分</td>
										<td>${scoreModel.minScore }</td>
									</tr>
									<tr>
										<td class="bggrey">翻译成白话文内容</td>
										<td>${scoreModel.easyUnderstandContent }
										</td>
									</tr>
									<tr>
										<td class="bggrey">当前模型标准解释</td>
										<td>以评审数额最低区间为基准递增排序，采购文件明确规定标准分值，分差和最低最高分值限制，并按分差计算规则计算得分
										</td>
									</tr>
								</tbody>
							</table>
							<table id="model72" style="display: none;" >
								<tbody>
									<tr>
										<td class="bggrey">模型</td>
										<td>
											<c:choose>
												<c:when test="${scoreModel.typeName==0}">
												模型1:是否判断
												</c:when>
												<c:when test="${scoreModel.typeName==1}">
												模型2:按项加减分
												</c:when>
												<c:when test="${scoreModel.typeName==2}">
												模型3:评审数额最高递减
												</c:when>
												<c:when test="${scoreModel.typeName==3}">
												模型4:评审数额最低递增
												</c:when>
												<c:when test="${scoreModel.typeName==4}">
												模型5:评审数额高计算
												</c:when>
												<c:when test="${scoreModel.typeName==5}">
												模型6:评审数额低计算
												</c:when>
												<c:when test="${scoreModel.typeName==6}">
												模型7:评审数额低区间递增
												</c:when>
												<c:when test="${scoreModel.typeName==7}">
												模型8:评审数额高区间递减
												</c:when>
												<c:otherwise>

												</c:otherwise>
											</c:choose>
										</td>
									</tr>
									<tr>
										<td class="bggrey">评审参数</td>
										<td>${scoreModel.reviewParam}</td>
									</tr>
									<tr>
										<td class="bggrey">单位</td>
										<td>${scoreModel.unit }</td>
									</tr>
									<tr>
										<td class="bggrey">区间类型</td>
										<td id="intervalTypeName72">
											<c:choose>
												<c:when test="${scoreModel.intervalTypeName==0}">
													差额相等
												</c:when>
												<c:when test="${scoreModel.intervalTypeName==1}">
													差额区间
												</c:when>
												<c:otherwise>

												</c:otherwise>
											</c:choose>
										</td>
									</tr>
								</tbody>
							</table>
							<table id="model81" style="display: none;" >
								<tbody>
									<tr>
										<td class="bggrey">模型</td>
										<td>
											<c:choose>
												<c:when test="${scoreModel.typeName==0}">
												模型1:是否判断
												</c:when>
												<c:when test="${scoreModel.typeName==1}">
												模型2:按项加减分
												</c:when>
												<c:when test="${scoreModel.typeName==2}">
												模型3:评审数额最高递减
												</c:when>
												<c:when test="${scoreModel.typeName==3}">
												模型4:评审数额最低递增
												</c:when>
												<c:when test="${scoreModel.typeName==4}">
												模型5:评审数额高计算
												</c:when>
												<c:when test="${scoreModel.typeName==5}">
												模型6:评审数额低计算
												</c:when>
												<c:when test="${scoreModel.typeName==6}">
												模型7:评审数额低区间递增
												</c:when>
												<c:when test="${scoreModel.typeName==7}">
												模型8:评审数额高区间递减
												</c:when>
												<c:otherwise>

												</c:otherwise>
											</c:choose>
										</td>
									</tr>
									<tr>
										<td class="bggrey">评审参数</td>
										<td>${scoreModel.reviewParam }</td>
									</tr>
									<tr>
										<td class="bggrey">单位</td>
										<td>${scoreModel.unit }</td>
									</tr>
									<tr>
										<td class="bggrey">区间类型</td>
										<td>
											<c:choose>
												<c:when test="${scoreModel.intervalTypeName==0}">
													差额相等
												</c:when>
												<c:when test="${scoreModel.intervalTypeName==1}">
													差额区间
												</c:when>
												<c:otherwise>

												</c:otherwise>
											</c:choose>
										</td>
									</tr>
									<tr>
										<td class="bggrey">评审基准数</td>
										<td>${scoreModel.reviewStandScore }
										</td>
									</tr>
									<tr>
										<td class="bggrey">每区间等差额</td>
										<td>${scoreModel.intervalNumber }
										</td>
									</tr>
									<tr>
										<td class="bggrey">加减分类型</td>
										<td>
											<c:choose>
												<c:when test="${scoreModel.addSubtractTypeName==0}">
													加分
												</c:when>
												<c:when test="${scoreModel.addSubtractTypeName==1}">
													减分
												</c:when>
												<c:otherwise>
1
												</c:otherwise>
											</c:choose>
										</td>
									</tr>
									<tr>
										<td class="bggrey">加减分分值</td>
										<td>${scoreModel.score }</td>
									</tr>
									<tr>
										<td class="bggrey">评审参数截止数</td>
										<td>${scoreModel.deadlineNumber }</td>
									</tr>
									<tr>
										<td class="bggrey">最高分</td>
										<td>${scoreModel.maxScore }</td>
									</tr>
									<tr>
										<td class="bggrey">最低分</td>
										<td>${scoreModel.minScore }</td>
									</tr>
									<tr>
										<td class="bggrey">翻译成白话文内容</td>
										<td>${scoreModel.easyUnderstandContent }
										</td>
									</tr>
									<tr>
										<td class="bggrey">当前模型标准解释</td>
										<td>以评审数额最低区间为基准递增排序，采购文件明确规定标准分值，分差和最低最高分值限制，并按分差计算规则计算得分
										</td>
									</tr>

								</tbody>
							</table>
							<table id="model82" style="display: none;" >
								<tbody>
									<tr>
										<td class="bggrey">模型</td>
										<td>
											<c:choose>
												<c:when test="${scoreModel.typeName==0}">
												模型1:是否判断
												</c:when>
												<c:when test="${scoreModel.typeName==1}">
												模型2:按项加减分
												</c:when>
												<c:when test="${scoreModel.typeName==2}">
												模型3:评审数额最高递减
												</c:when>
												<c:when test="${scoreModel.typeName==3}">
												模型4:评审数额最低递增
												</c:when>
												<c:when test="${scoreModel.typeName==4}">
												模型5:评审数额高计算
												</c:when>
												<c:when test="${scoreModel.typeName==5}">
												模型6:评审数额低计算
												</c:when>
												<c:when test="${scoreModel.typeName==6}">
												模型7:评审数额低区间递增
												</c:when>
												<c:when test="${scoreModel.typeName==7}">
												模型8:评审数额高区间递减
												</c:when>
												<c:otherwise>

												</c:otherwise>
											</c:choose>
										</td>
									</tr>
									<tr>
										<td class="bggrey">评审参数</td>
										<td>${scoreModel.reviewParam }</td>
									</tr>
									<tr>
										<td class="bggrey">单位</td>
										<td>${scoreModel.unit }</td>
									</tr>
									<tr>
										<td class="bggrey">区间类型</td>
										<td>
											<c:choose>
												<c:when test="${scoreModel.intervalTypeName==0}">
													差额相等
												</c:when>
												<c:when test="${scoreModel.intervalTypeName==1}">
													差额区间
												</c:when>
												<c:otherwise>

												</c:otherwise>
											</c:choose>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	</div>
</body>
</html>
