<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../../../common.jsp"%>
<script type="text/javascript">
	  	  $(function(){
		  laypage({
			    cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
			    pages: "${list.pages}", //总页数
			    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
			    skip: true, //是否开启跳页
			    total: "${list.total}",
			    startRow: "${list.startRow}",
			    endRow: "${list.endRow}",
			    groups: "${list.pages}">=5?5:"${list.pages}", //连续显示分页数
			    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
			        return "${list.pageNum}";
			    }(), 
			    jump: function(e, first){ //触发分页后的回调
			        if(!first){ //一定要加此判断，否则初始时会无限刷新
			             $("#page").val(e.curr);
                   $("#form1").submit();
			        }
			    }
			});
	  });
	  function back(){
	  	window.location.href="${pageContext.request.contextPath}/resAnalyze/analyzeOrgs.html";
	  }
		function chongzhi(){
			$("#name").val('');
			$("#quaStartDate").val('');
			$("#quaEdndate").val('');
			$("option")[0].selected = true;
		}

</script>
</head>
  <body>
  	<div class="margin-top-10 breadcrumbs ">
	      <div class="container">
			  <ul class="breadcrumb margin-left-0">
				  <li><a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a></li>
				  <li><a href="javascript:void(0);">支撑系统</a></li>
				  <li><a href="javascript:void(0);">机构管理</a></li>
				  <li class="active"><a href="javascript:void(0);" onclick="jumppage('${pageContext.request.contextPath}/purchaseManage/purchaseDepMapList.html')">采购机构查询管理</a></li>
			  </ul>
			<div class="clear"></div>
		  </div>
	   </div>
	   <div class="container">
		   <div class="headline-v2">
		     <h2>采购机构信息</h2>
		   </div>  
			<h2 class="search_detail">
			<form id="form1" action="${pageContext.request.contextPath}/purchaseManage/readOnlyList.html" method="post" class="mb0">
			<input type="hidden" name="page" id="page">
			<input type="hidden" name="provinceId" value="${purchaseDep.provinceId }">
			<input type="hidden" name="orgId" value="${purchaseDep.orgId }">
			<div class="m_row_5">
	    <div class="row">
	      <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3 mb10">
	        <div class="row">
	          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">采购机构名称：</div>
	          <div class="col-xs-8 f0 lh0">
							<input id="name" name="name" value="${purchaseDep.name }" type="text" class="w100p h32 f14 mb0">
	          </div>
	        </div>
	      </div>
	      
	      <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3 mb10">
	        <div class="row">
	          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">资质起始日期：</div>
	          <div class="col-xs-8 f0 lh0">
							<input id="quaStartDate" name="quaStartDate" class="Wdate w100p h32 f14 mb0" type="text" value='<fmt:formatDate value="${purchaseDep.quaStartDate }" pattern="YYYY-MM-dd"/>'
							onFocus="var endDate=$dp.$('endDate');WdatePicker({onpicked:function(){quaStartDate.focus();},maxDate:'#F{$dp.$D(\'quaStartDate\')}'})">
	          </div>
	        </div>
	      </div>
	      
	      <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3 mb10">
	        <div class="row">
	          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">资质结束日期：</div>
	          <div class="col-xs-8 f0 lh0">
							<input id="quaEdndate" name="quaEdndate" value='<fmt:formatDate value="${purchaseDep.quaEdndate }" pattern="YYYY-MM-dd"/>' class="Wdate w100p h32 f14 mb0" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'quaEdndate\')}'})">
	          </div>
	        </div>
	      </div>
				
				<div class="col-xs-12 col-sm-4 col-md-4 col-lg-3 mb10">
	        <div class="row">
	          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">上级监管部门：</div>
	          <div class="col-xs-8 f0 lh0">
							<select name="" class="w100p h32 f14">
								<option selected="selected" value=''>-请选择-</option>
								<option value="生产型">部门1</option>
								<option value="销售型">部门2</option>
							</select>
	          </div>
	        </div>
	      </div>
	    </div>
	    </div>
			
			<div class="tc">
				<input class="btn mb0" onclick="submit()" type="button" value="查询">
				<input class="btn mb0" onclick="chongzhi()" type="button" value="重置">
				<input class="btn back mb0 mr0" onclick="back()" value="返回" type="button">
			</div>
			</form>
			</h2>
		      <div class="content table_box">
                 <table id="tb1" class="table table-bordered table-condensed table-hover table-striped">
		      <thead>
				<tr>
					<th class="info w50">序号</th>
					<th class="info" width="18%">采购机构名称</th>
					<th class="info" width="13%">采购资质编号</th>
					<th class="info w80">等级</th>
					<th class="info" width="15%">地址</th>
					<th class="info" width="12%">采购资质开始日期</th>
					<th class="info" width="12%">采购资质截止日期</th>
					<th class="info">上级监管部门</th>
				</tr>
			  </thead>
			  <tbody>
				 <c:forEach items="${list.list }" var="list" varStatus="vs">
					<tr>
						<td class="tc w50">${vs.index+1 }</td>
						<td><a href="${pageContext.request.contextPath}/purchaseManage/purchaseDepMapShow.html?orgId=${list.orgId}">${list.name }</a></td>
						<td>${list.quaCode }</td>
						<td>
							<c:forEach items="${kind}" var="status">
							  <c:if test="${status.id eq list.levelDep}">${status.name}</c:if>
							</c:forEach>
						</td>
						<td>${list.address }</td>
						<td><fmt:formatDate value="${list.quaStartDate }" pattern="yyyy-MM-dd" /></td>
						<td><fmt:formatDate value="${list.quaEdndate }" pattern="yyyy-MM-dd" /></td>
						<td>军区采购</td>
					</tr>
				</c:forEach> 
			  </tbody>
		 </table>
		 <div id="pagediv" align="right"></div>
     </div>
  </body>
</html>
