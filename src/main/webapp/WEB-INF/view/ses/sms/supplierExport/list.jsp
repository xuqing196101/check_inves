<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en">
<!--<![endif]-->

<head>
<%@ include file="../../../common.jsp"%>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title></title>
<!-- Meta -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<script type="text/javascript">
function liActive2(){
	  $("#liActive2").attr("class","active");
    $("#liActive1").attr("class","");
    $("#liActive3").attr("class","");
	  $("#liActive4").attr("class","");
    $("#dep_tab-1").attr("class","tab-pane fade in active");
    $("#dep_tab-0").attr("class","tab-pane fade in");
    $("#dep_tab-2").attr("class","tab-pane fade in");
	  $("#dep_tab-3").attr("class","tab-pane fade in");
}
function liActive1(){
	$("#liActive1").attr("class","active");
	  $("#liActive2").attr("class","");
	  $("#liActive3").attr("class","");
	  $("#liActive4").attr("class","");
	  $("#dep_tab-0").attr("class","tab-pane fade in active");
	  $("#dep_tab-1").attr("class","tab-pane fade in");
	  $("#dep_tab-2").attr("class","tab-pane fade in");
	  $("#dep_tab-3").attr("class","tab-pane fade in");
}
function liActive3(){
	  $("#liActive3").attr("class","active");
	  $("#liActive2").attr("class","");
	  $("#liActive1").attr("class","");
	  $("#liActive4").attr("class","");
	  $("#dep_tab-2").attr("class","tab-pane fade in active");
	  $("#dep_tab-1").attr("class","tab-pane fade in");
	  $("#dep_tab-3").attr("class","tab-pane fade in");
	  $("#dep_tab-0").attr("class","tab-pane fade in");
}
function liActive4(){
	$("#liActive4").attr("class","active");
	  $("#liActive2").attr("class","");
	  $("#liActive1").attr("class","");
	  $("#liActive3").attr("class","");
	  $("#dep_tab-3").attr("class","tab-pane fade in active");
	  $("#dep_tab-1").attr("class","tab-pane fade in");
	  $("#dep_tab-2").attr("class","tab-pane fade in");
	  $("#dep_tab-0").attr("class","tab-pane fade in");
}
function page(){
	laypage({
		cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
		pages: "${list.pages}", //总页数
		skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
		skip: true, //是否开启跳页
		total: "${list.total}",
		startRow: "${list.startRow}",
		endRow: "${list.endRow}",
		groups: "${list.pages}" >= 5 ? 5 : "${list.pages}", //连续显示分页数
		curr: function() { //通过url获取当前页，也可以同上（pages）方式获取
			return "${list.pageNum}";
		}(),
		jump: function(e, first) { //触发分页后的回调
			if(!first) { //一定要加此判断，否则初始时会无限刷新
				$("#page").val(e.curr);
				$("#type").val(1);
			  $("#form1").submit();
			}
		}
	});
	laypage({
		cont: $("#pagediv1"), //容器。值支持id名、原生dom对象，jquery对象,
		pages: "${listExpert.pages}", //总页数
		skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
		skip: true, //是否开启跳页
		total: "${listExpert.total}",
		startRow: "${listExpert.startRow}",
		endRow: "${listExpert.endRow}",
		groups: "${listExpert.pages}" >= 5 ? 5 : "${listExpert.pages}", //连续显示分页数
		curr: function() { //通过url获取当前页，也可以同上（pages）方式获取
			return "${listExpert.pageNum}";
		}(),
		jump: function(e, first) { //触发分页后的回调
			if(!first) { //一定要加此判断，否则初始时会无限刷新
				$("#pageEx").val(e.curr);
			  $("#type").val(2);
			  $("#formEx").submit();
			}
		}
	});
	laypage({
		cont: $("#pagediv2"), //容器。值支持id名、原生dom对象，jquery对象,
		pages: "${supFormallist.pages}", //总页数
		skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
		skip: true, //是否开启跳页
		total: "${supFormallist.total}",
		startRow: "${supFormallist.startRow}",
		endRow: "${supFormallist.endRow}",
		groups: "${supFormallist.pages}" >= 5 ? 5 : "${supFormallist.pages}", //连续显示分页数
		curr: function() { //通过url获取当前页，也可以同上（pages）方式获取
			return "${supFormallist.pageNum}";
		}(),
		jump: function(e, first) { //触发分页后的回调
			if(!first) { //一定要加此判断，否则初始时会无限刷新
				$("#pageSupFormal").val(e.curr);
			  $("#type").val(3);
			  $("#formSupFormal").submit();
			}
		}
	});
	laypage({
		cont: $("#pagediv3"), //容器。值支持id名、原生dom对象，jquery对象,
		pages: "${expFormallist.pages}", //总页数
		skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
		skip: true, //是否开启跳页
		total: "${expFormallist.total}",
		startRow: "${expFormallist.startRow}",
		endRow: "${expFormallist.endRow}",
		groups: "${expFormallist.pages}" >= 5 ? 5 : "${expFormallist.pages}", //连续显示分页数
		curr: function() { //通过url获取当前页，也可以同上（pages）方式获取
			return "${expFormallist.pageNum}";
		}(),
		jump: function(e, first) { //触发分页后的回调
			if(!first) { //一定要加此判断，否则初始时会无限刷新
				$("#pageExpFormal").val(e.curr);
			  $("#type").val(4);
			  $("#formExpFormal").submit();
			}
		}
	});
}
function supTable(){
	var sup_statusOne=0,sup_statusThree=0,sup_statusFour=0,
    sup_statusFive=0,sup_statusTwo=0,sup_statusSix=0,
    sup_statusSeven=0,sup_statusNine=0,sup_statusEight=0;
	$("#supTable").find("tr").each(function(i,obj){
		  if(i>1&&i<$("#supTable").find("tr").length-1){
			  sup_statusOne+=parseInt($($(obj).children()[2]).text());
			  sup_statusThree+=parseInt($($(obj).children()[3]).text());
			  sup_statusFour+=parseInt($($(obj).children()[4]).text());
			  sup_statusFive+=parseInt($($(obj).children()[5]).text());
			  sup_statusTwo+=parseInt($($(obj).children()[6]).text());
			  sup_statusSix+=parseInt($($(obj).children()[7]).text());
			  sup_statusSeven+=parseInt($($(obj).children()[8]).text());
			  sup_statusNine+=parseInt($($(obj).children()[9]).text());
			  sup_statusEight+=parseInt($($(obj).children()[10]).text());
		  }
	});
	$("#sup_statusOne").text(sup_statusOne);
	$("#sup_statusThree").text(sup_statusThree);
	$("#sup_statusFour").text(sup_statusFour);
	$("#sup_statusFive").text(sup_statusFive);
	$("#sup_statusTwo").text(sup_statusTwo);
	$("#sup_statusSix").text(sup_statusSix);
	$("#sup_statusSeven").text(sup_statusSeven);
	$("#sup_statusNine").text(sup_statusNine);
	$("#sup_statusEight").text(sup_statusEight);
}
function expTable(){
	var exp_statusOne=0,exp_statusThree=0,exp_statusFour=0,
    exp_statusFive=0,exp_statusTwo=0,exp_statusSix=0,
    exp_statusSeven=0,exp_statusEight=0,exp_statusNine=0;
$("#expTable").find("tr").each(function(i,obj){
	  if(i>1&&i<$("#expTable").find("tr").length-1){
		  exp_statusOne+=parseInt($($(obj).children()[2]).text());
		  exp_statusThree+=parseInt($($(obj).children()[3]).text());
		  exp_statusFour+=parseInt($($(obj).children()[4]).text());
		  exp_statusFive+=parseInt($($(obj).children()[5]).text());
		  exp_statusTwo+=parseInt($($(obj).children()[6]).text());
		  exp_statusSix+=parseInt($($(obj).children()[7]).text());
		  exp_statusSeven+=parseInt($($(obj).children()[8]).text());
		  exp_statusNine+=parseInt($($(obj).children()[10]).text());
		  exp_statusEight+=parseInt($($(obj).children()[9]).text());
	  }
});
$("#exp_statusOne").text(exp_statusOne);
$("#exp_statusThree").text(exp_statusThree);
$("#exp_statusFour").text(exp_statusFour);
$("#exp_statusFive").text(exp_statusFive);
$("#exp_statusTwo").text(exp_statusTwo);
$("#exp_statusSix").text(exp_statusSix);
$("#exp_statusSeven").text(exp_statusSeven);
$("#exp_statusNine").text(exp_statusNine);
$("#exp_statusEight").text(exp_statusEight);
}
function supTableFormal(){
	var sup_formalMr=0,sup_formalMs=0,sup_formalPr=0,
	sup_formalSe=0,sup_formalSum=0,sup_checkMr=0,
	sup_checkMs=0,sup_checkPr=0,sup_checkSe=0,
	sup_checkSum=0,sup_inspectMr=0,sup_inspectMs=0,
	sup_inspectPr=0,sup_inspectSe=0,sup_inspectsum=0;
	$("#supTableFormal").find("tr").each(function(i,obj){
		if(i>1&&i<$("#supTableFormal").find("tr").length-1){
			sup_formalMr+=parseInt($($(obj).children()[2]).text());
			sup_formalMs+=parseInt($($(obj).children()[3]).text());
			sup_formalPr+=parseInt($($(obj).children()[4]).text());
			sup_formalSe+=parseInt($($(obj).children()[5]).text());
			sup_formalSum+=parseInt($($(obj).children()[6]).text());
			sup_checkMr+=parseInt($($(obj).children()[7]).text());
			sup_checkMs+=parseInt($($(obj).children()[8]).text());
			sup_checkPr+=parseInt($($(obj).children()[9]).text());
			sup_checkSe+=parseInt($($(obj).children()[10]).text());
			sup_checkSum+=parseInt($($(obj).children()[11]).text());
			sup_inspectMr+=parseInt($($(obj).children()[12]).text());
			sup_inspectMs+=parseInt($($(obj).children()[13]).text());
			sup_inspectPr+=parseInt($($(obj).children()[14]).text());
			sup_inspectSe+=parseInt($($(obj).children()[15]).text());
			sup_inspectsum+=parseInt($($(obj).children()[16]).text());
		}
	});
	$("#sup_formalMr").text(sup_formalMr);
	$("#sup_formalMs").text(sup_formalMs);
	$("#sup_formalPr").text(sup_formalPr);
	$("#sup_formalSe").text(sup_formalSe);
	$("#sup_formalSum").text(sup_formalSum);
	$("#sup_checkMr").text(sup_checkMr);
	$("#sup_checkMs").text(sup_checkMs);
	$("#sup_checkPr").text(sup_checkPr);
	$("#sup_checkSe").text(sup_checkSe);
	$("#sup_checkSum").text(sup_checkSum);
	$("#sup_inspectMr").text(sup_inspectMr);
	$("#sup_inspectMs").text(sup_inspectMs);
	$("#sup_inspectPr").text(sup_inspectPr);
	$("#sup_inspectSe").text(sup_inspectSe);
	$("#sup_inspectsum").text(sup_inspectsum);
}
function expTableFormal(){
	var exp_checkMr=0,exp_checkPr=0,exp_checkSe=0,exp_checkMs=0,
	exp_checkPs=0,exp_checkSum=0,exp_formalMr=0,exp_formalPr=0,
	exp_formalSe=0,exp_formalMs=0,exp_formalPs=0,exp_formalSum=0,
	exp_inspectMr=0,exp_inspectPr=0,exp_inspectSe=0,exp_inspectMs=0,
	exp_inspectPs=0,exp_inspectsum=0;
	$("#expTableFormal").find("tr").each(function(i,obj){
	  if(i>1&&i<$("#expTableFormal").find("tr").length-1){
	    exp_checkMr+=parseInt($($(obj).children()[2]).text());
	    exp_checkPr+=parseInt($($(obj).children()[3]).text());
	    exp_checkSe+=parseInt($($(obj).children()[4]).text());
	    exp_checkMs+=parseInt($($(obj).children()[5]).text());
	    exp_checkPs+=parseInt($($(obj).children()[6]).text());
	    exp_checkSum+=parseInt($($(obj).children()[7]).text());
	    exp_formalMr+=parseInt($($(obj).children()[8]).text());
	    exp_formalPr+=parseInt($($(obj).children()[9]).text());
	    exp_formalSe+=parseInt($($(obj).children()[10]).text());
	    exp_formalMs+=parseInt($($(obj).children()[11]).text());
	    exp_formalPs+=parseInt($($(obj).children()[12]).text());
	    exp_formalSum+=parseInt($($(obj).children()[13]).text());
	    exp_inspectMr+=parseInt($($(obj).children()[14]).text());
	    exp_inspectPr+=parseInt($($(obj).children()[15]).text());
	    exp_inspectSe+=parseInt($($(obj).children()[16]).text());
	    exp_inspectMs+=parseInt($($(obj).children()[17]).text());
	    exp_inspectPs+=parseInt($($(obj).children()[18]).text());
	    exp_inspectsum+=parseInt($($(obj).children()[19]).text());
	   }
	});
	
	$("#exp_checkMr").text(exp_checkMr);
	$("#exp_checkPr").text(exp_checkPr);
	$("#exp_checkSe").text(exp_checkSe);
	$("#exp_checkMs").text(exp_checkMs);
	$("#exp_checkPs").text(exp_checkPs);
	$("#exp_checkSum").text(exp_checkSum);
	$("#exp_formalMr").text(exp_formalMr);
	$("#exp_formalPr").text(exp_formalPr);
	$("#exp_formalSe").text(exp_formalSe);
	$("#exp_formalMs").text(exp_formalMs);
	$("#exp_formalPs").text(exp_formalPs);
	$("#exp_formalSum").text(exp_formalSum);
	$("#exp_inspectMr").text(exp_inspectMr);
	$("#exp_inspectPr").text(exp_inspectPr);
	$("#exp_inspectSe").text(exp_inspectSe);
	$("#exp_inspectMs").text(exp_inspectMs);
	$("#exp_inspectPs").text(exp_inspectPs);
	$("#exp_inspectsum").text(exp_inspectsum);
}
			$(function() {
				if("${type}"==2){
					  liActive2();
				  }else if("${type}"==1){
					  liActive1();
				  }else if("${type}"==3){
					  liActive3();
				  }else if("${type}"==4) {
					  liActive4();
				  }
				page();//分页
				supTable();//供应商审核合计
				expTable();//专家审核合计
				supTableFormal();//供应商入库合计
				expTableFormal();//专家入库合计
			});
			function clearValue(){
				$("#name").val("");
			}
			function clearExValue(){
				$("#nameEx").val("");
			}
			function clearSupFormalValue(){
				$("#nameSupFormal").val("");
			}
			function clearExpFormalValue(){
				$("#nameExpFormal").val("");
			}
		</script>
</head>

<body>

	<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a
					href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')">
						首页</a></li>
				<li><a href="javascript:void(0);">支撑环境</a></li>
				<%--<li>
						<a href="javascript:void(0);">进口代理商</a>
					</li>
					<li>
						<a href="javascript:void(0);">进口代理商管理</a>
					</li>--%>
				<li class="active"><a href="javascript:void(0);">两库审核状态</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>
	<!-- 我的页面开始-->
	<div class="container">
		<div class="headline-v2">
			<h2>两库审核状态列表</h2>
		</div>
		<div class="tab-content mt10">
			<div class="tab-v2">
				<ul class="nav nav-tabs bgwhite">
					<li class="active" id="liActive1"><a href="#dep_tab-0" data-toggle="tab"
						class="f18">供应商审核</a></li>
					<li id="liActive2"><a href="#dep_tab-1" data-toggle="tab" class="f18">专家审核</a></li>
					<li id="liActive3"><a href="#dep_tab-2" data-toggle="tab" class="f18">供应商入库</a></li>
					<li id="liActive4"><a href="#dep_tab-3" data-toggle="tab" class="f18">专家入库</a></li>
				</ul>
				<div class="tab-content">
					<div class="tab-pane fade in active" id="dep_tab-0">
						<h2 class="search_detail">
							<form id="form1"
								action="${pageContext.request.contextPath}/supplierExport/list.html"
								method="post" class="mb0">
								<input type="hidden" name="page" id="page"/>
								<input type="hidden" name="type" id="type" value="1"/>
								<ul class="demand_list">
									<li class="fl"><label class="fl">采购机构：</label><span>
									<select name="name" id="name" class="w220">
									  <option value=''>全部</option>
					           <c:forEach items="${allOrg}" var="org">
					             <option value="${org.shortName}" <c:if test="${name eq org.shortName}">selected</c:if>>${org.shortName}</option>
					           </c:forEach>
					           </select>
									</li>
								</ul>
								<button type="submit" class="btn fl">查询</button>
								<button type="button" onclick="clearValue();" class="btn fl">重置</button>
								<div class="clear"></div>
							</form>
						</h2>
						<!-- 表格开始-->
<%-- 						<div class="fr mt5 b">
	      	合计：${contractSum}
	          </div>
 --%>						<div class="content table_box">
						<table id="supTable"
								class="table table-bordered table-condensed table-hover table-striped">
								<thead >
									<tr>
										<th class="w50 info" rowspan="2">序号</th>
										<th class="info" rowspan="2" width="20%">采购机构</th>
										<th class="info" rowspan="2">待审核</th>
										<th class="info" rowspan="2">退回修改</th>
										<th class="info" colspan="3">审核阶段</th>
										<th class="info" colspan="2">复核阶段</th>
										<th class="info" colspan="2">考察阶段</th>
									<!-- 	<th class="info">待审核数量</th>
										<th class="info">审核通过数量</th>
										<th class="info">退回修改数量</th>
										<th class="info">审核不通过数量</th>
										<th class="info">总数</th> -->
									</tr>
									<tr>
									<th class="info">审核不通过</th>
										<th class="info">公示中</th>
										<th class="info">已入库</th>
										<th class="info">复核不通过</th>
										<th class="info">复核通过</th>
										<th class="info">考察合格</th>
										<th class="info">考察不合格</th>
									</tr>
									</thead>
									<c:forEach items="${list.list}" var="su" varStatus="vs">
								 <tr>
								  <td>${(vs.index+1)+(list.pageNum-1)*(list.pageSize)} </td>
								  <td>${su.shortName}</td>
								  <td>${su.statusOne}</td>
								  <td>${su.statusThree}</td>
								  <td>${su.statusFour}</td>
								  <td>${su.statusFive}</td>
								  <td>${su.statusTwo}</td>
								  <td>${su.statusSix}</td>
								  <td>${su.statusSeven }</td>
								  <td>${su.statusNine }</td>
								  <td>${su.statusEight }</td>
								 </tr>
								</c:forEach>
								<tr>
									   <th colspan="2">合计</th>
									   <th id="sup_statusOne"></th>
									   <th id="sup_statusThree"></th>
									   <th id="sup_statusFour"></th>
									   <th id="sup_statusFive"></th>
									   <th id="sup_statusTwo"></th>
									   <th id="sup_statusSix"></th>
									   <th id="sup_statusSeven"></th>
									   <th id="sup_statusNine"></th>
									   <th id="sup_statusEight"></th>
									</tr>
								</table>
							<%-- <table
								class="table table-bordered table-condensed table-hover table-striped">
								<thead>
									<tr>
										<th class="w50 info">序号</th>
										<th class="info" width="30%">采购机构</th>
										<th class="info">注册数量</th>
										<th class="info">待审核数量</th>
										<th class="info">审核通过数量</th>
										<th class="info">退回修改数量</th>
										<th class="info">审核不通过数量</th>
										<th class="info">总数</th>
									</tr>
								</thead>
								<c:forEach items="${list.list}" var="su" varStatus="vs">
								 <tr>
								  <td>${(vs.index+1)+(list.pageNum-1)*(list.pageSize)} </td>
								  <td>${su.shortName}</td>
								  <td>${su.reg}</td>
								  <td>${su.statusOne}</td>
								  <td>${su.statusTwo}</td>
								  <td>${su.statusThree}</td>
								  <td>${su.statusFour}</td>
								  <td>${su.sums}</td>
								 </tr>
								</c:forEach>
							</table> --%>
							<div id="pagediv" align="right"></div>
						</div>
					</div>
					<div class="tab-pane fade in" id="dep_tab-1">
					   <h2 class="search_detail">
							<form id="formEx"
								action="${pageContext.request.contextPath}/supplierExport/list.html"
								method="post" class="mb0">
								<input type="hidden" name="pageEx" id="pageEx"/>
								<input type="hidden" name="type" id="type" value="2"/>
								<ul class="demand_list">
									<li class="fl"><label class="fl">采购机构：</label><span>
									<select name="nameEx" id="nameEx" class="w220">
									  <option value=''>全部</option>
					           <c:forEach items="${allOrg}" var="org">
					             <option value="${org.shortName}" <c:if test="${nameEx eq org.shortName}">selected</c:if>>${org.shortName}</option>
					           </c:forEach>
					           </select>
									</span>
									</li>
								</ul>
								<button type="submit" class="btn fl">查询</button>
								<button type="button" onclick="clearExValue();" class="btn fl">重置</button>
								<div class="clear"></div>
							</form>
						</h2>
						<!-- 表格开始-->
						<%-- <div class="fr mt5 b">
	      	       合计：${contractSum}
	          </div> --%>
						<div class="content table_box">
							<table id="expTable"
								class="table table-bordered table-condensed table-hover table-striped">
								<thead>
									<tr>
										<th class="w50 info" rowspan="2">序号</th>
										<th class="info" width="20%" rowspan="2">采购机构</th>
										<th class="info" rowspan="2">待初审</th>
										<th class="info" rowspan="2">退回修改</th>
										<th class="info" colspan="3">初审阶段</th>
										<th class="info" colspan="2">复审阶段</th>
										<th class="info" colspan="2">复查阶段</th>
										<!-- <th class="info">注册数量</th>
										<th class="info">待审核数量</th>
										<th class="info">审核通过数量</th>
										<th class="info">退回修改数量</th>
										<th class="info">审核不通过数量</th>
										<th class="info">军队数量</th>
										<th class="info">地方数量</th>
										<th class="info">总数</th> -->
									</tr>
									<tr>
									  <th>初审不合格</th>
									  <th>公示中</th>
									  <th>初审合格</th>
									  <th>复审不合格</th>
									  <th>已入库</th>
									  <th>复查不合格</th>
									  <th>复查合格</th>
									</tr>
								</thead>
								<c:forEach items="${listExpert.list}" var="su" varStatus="vs">
								 <tr>
								  <td>${(vs.index+1)+(listExpert.pageNum-1)*(listExpert.pageSize)} </td>
								  <td>${su.shortName}</td>
								  <td>${su.statusOne}</td>
								  <td>${su.statusThree }</td>
								  <td>${su.statusFour}</td>
								  <td>${su.statusFive}</td>
								  <td>${su.statusTwo}</td>
								  <td>${su.statusSix}</td>
								  <td>${su.statusSeven}</td>
								  <td>${su.statusEight}</td>
								  <td>${su.statusNine}</td>
								 </tr>
								</c:forEach>
								<tr>
									   <th colspan="2">合计</th>
									   <th id="exp_statusOne"></th>
									   <th id="exp_statusThree"></th>
									   <th id="exp_statusFour"></th>
									   <th id="exp_statusFive"></th>
									   <th id="exp_statusTwo"></th>
									   <th id="exp_statusSix"></th>
									   <th id="exp_statusSeven"></th>
									   <th id="exp_statusEight"></th>
									   <th id="exp_statusNine"></th>
									</tr>
							</table>
							<div id="pagediv1" align="right"></div>
					</div>
				</div>
				<div class="tab-pane fade in active" id="dep_tab-2">
				   <h2 class="search_detail">
							<form id="formSupFormal"
								action="${pageContext.request.contextPath}/supplierExport/list.html"
								method="post" class="mb0">
								<input type="hidden" name="pageSupFormal" id="pageSupFormal"/>
								<input type="hidden" name="type" id="type" value="3"/>
								<ul class="demand_list">
									<li class="fl"><label class="fl">采购机构：</label><span>
									<select name="nameSupFormal" id="nameSupFormal" class="w220">
									  <option value=''>全部</option>
					           <c:forEach items="${allOrg}" var="org">
					             <option value="${org.shortName}" <c:if test="${nameSupFormal eq org.shortName}">selected</c:if>>${org.shortName}</option>
					           </c:forEach>
					           </select>
									</span>
									</li>
								</ul>
								<button type="submit" class="btn fl">查询</button>
								<button type="button" onclick="clearSupFormalValue();" class="btn fl">重置</button>
								<div class="clear"></div>
							</form>
						</h2>
						<div class="content table_box">
							<table id="supTableFormal"
								class="table table-bordered table-condensed table-hover table-striped">
								<thead>
									<tr>
										<th class="w50 info" rowspan="2">序号</th>
										<th class="info" width="20%" rowspan="2">采购机构</th>
										<th class="info" colspan="5">已入库</th>
										<th class="info" colspan="5">复核通过</th>
										<th class="info" colspan="5">考察合格</th>
									</tr>
									<tr>
									  <th>物资生产</th>
									  <th>物资销售</th>
									  <th>工程</th>
									  <th>服务</th>
									  <th>小计</th>
									  <th>物资生产</th>
									  <th>物资销售</th>
									  <th>工程</th>
									  <th>服务</th>
									  <th>小计</th>
									  <th>物资生产</th>
									  <th>物资销售</th>
									  <th>工程</th>
									  <th>服务</th>
									  <th>小计</th>
									</tr>
								</thead>
								<c:forEach items="${supFormallist.list}" var="su" varStatus="vs">
								 <tr>
								  <td>${(vs.index+1)+(supFormallist.pageNum-1)*(supFormallist.pageSize)} </td>
								  <td>${su.shortName}</td>
								  <td>${su.formalMr}</td>
								  <td>${su.formalMs }</td>
								  <td>${su.formalPr}</td>
								  <td>${su.formalSe}</td>
								  <td>${su.formalSum }</td>
								  <td>${su.checkMr}</td>
								  <td>${su.checkMs}</td>
								  <td>${su.checkPr}</td>
								  <td>${su.checkSe}</td>
								  <td>${su.checkSum}</td>
								  <td>${su.inspectMr}</td>
								  <td>${su.inspectMs}</td>
								  <td>${su.inspectPr}</td>
								  <td>${su.inspectSe}</td>
								  <td>${su.inspectsum}</td>
								 </tr>
								</c:forEach>
								<tr>
								  <th colspan="2">合计</th>
								  <th id="sup_formalMr"></th>
								  <th id="sup_formalMs"></th>
								  <th id="sup_formalPr"></th>
								  <th id="sup_formalSe"></th>
								  <th id="sup_formalSum"></th>
								  <th id="sup_checkMr"></th>
								  <th id="sup_checkMs"></th>
								  <th id="sup_checkPr"></th>
								  <th id="sup_checkSe"></th>
								  <th id="sup_checkSum"></th>
								  <th id="sup_inspectMr"></th>
								  <th id="sup_inspectMs"></th>
								  <th id="sup_inspectPr"></th>
								  <th id="sup_inspectSe"></th>
								  <th id="sup_inspectsum"></th>
								</tr>
							</table>
							<div id="pagediv2" align="right"></div>
					</div>
				</div>
				<div class="tab-pane fade in active" id="dep_tab-3">
				     <h2 class="search_detail">
							<form id="formExpFormal"
								action="${pageContext.request.contextPath}/supplierExport/list.html"
								method="post" class="mb0">
								<input type="hidden" name="pageExpFormal" id="pageExpFormal"/>
								<input type="hidden" name="type" id="type" value="4"/>
								<ul class="demand_list">
									<li class="fl"><label class="fl">采购机构：</label><span>
									<select name="nameExpFormal" id="nameExpFormal" class="w220">
									  <option value=''>全部</option>
					           <c:forEach items="${allOrg}" var="org">
					             <option value="${org.shortName}" <c:if test="${nameExpFormal eq org.shortName}">selected</c:if>>${org.shortName}</option>
					           </c:forEach>
					           </select>
									</span>
									</li>
								</ul>
								<button type="submit" class="btn fl">查询</button>
								<button type="button" onclick="clearExpFormalValue();" class="btn fl">重置</button>
								<div class="clear"></div>
							</form>
						</h2>
						<div class="content table_box">
							<table id="expTableFormal"
								class="table table-bordered table-condensed table-hover table-striped">
								<thead>
									<tr>
										<th class="w50 info" rowspan="2">序号</th>
										<th class="info" width="10%" rowspan="2">采购机构</th>
										<th class="info" colspan="6">初审合格</th>
										<th class="info" colspan="6">已入库</th>
										<th class="info" colspan="6">复查合格</th>
									</tr>
									<tr>
									  <th>物资技术</th>
									  <th>工程技术</th>
									  <th>服务技术</th>
									  <th>物质工程服务</th>
									  <th>工程经济</th>
									  <th>小计</th>
									  <th>物资技术</th>
									  <th>工程技术</th>
									  <th>服务技术</th>
									  <th>物质工程服务</th>
									  <th>工程经济</th>
									  <th>小计</th>
									  <th>物资技术</th>
									  <th>工程技术</th>
									  <th>服务技术</th>
									  <th>物质工程服务</th>
									  <th>工程经济</th>
									  <th>小计</th>
									</tr>
								</thead>
								<c:forEach items="${expFormallist.list}" var="su" varStatus="vs">
								  <tr>
								    <td>${(vs.index+1)+(expFormallist.pageNum-1)*(expFormallist.pageSize)} </td>
								    <td>${su.shortName}</td>
								    <td>${su.checkMr }</td>
								    <td>${su.checkPr }</td>
								    <td>${su.checkSe }</td>
								    <td>${su.checkMs }</td>
								    <td>${su.checkPs }</td>
								    <td>${su.checkSum }</td>
								    <td>${su.formalMr }</td>
								    <td>${su.formalPr }</td>
								    <td>${su.formalSe }</td>
								    <td>${su.formalMs }</td>
								    <td>${su.formalPs }</td>
								    <td>${su.formalSum }</td>
								    <td>${su.inspectMr }</td>
								    <td>${su.inspectPr }</td>
								    <td>${su.inspectSe }</td>
								    <td>${su.inspectMs }</td>
								    <td>${su.inspectPs }</td>
								    <td>${su.inspectsum }</td>
								  </tr>
								
								</c:forEach>
								<tr>
								  <th colspan="2">合计</th>
								  <th id="exp_checkMr"></th>
								  <th id="exp_checkPr"></th>
								  <th id="exp_checkSe"></th>
								  <th id="exp_checkMs"></th>
								  <th id="exp_checkPs"></th>
								  <th id="exp_checkSum"></th>
								  <th id="exp_formalMr"></th>
								  <th id="exp_formalPr"></th>
								  <th id="exp_formalSe"></th>
								  <th id="exp_formalMs"></th>
								  <th id="exp_formalPs"></th>
								  <th id="exp_formalSum"></th>
								  <th id="exp_inspectMr"></th>
								  <th id="exp_inspectPr"></th>
								  <th id="exp_inspectSe"></th>
								  <th id="exp_inspectMs"></th>
								  <th id="exp_inspectPs"></th>
								  <th id="exp_inspectsum"></th>
								  </tr>
							</table>
							<div id="pagediv3" align="right"></div>
					</div>
				</div>
			</div>
		</div>

	</div>
</body>

</html>