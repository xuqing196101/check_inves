<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/view/common.jsp"%>
<script type="text/javascript">
  /** 分页  */
  $(function() {
    laypage({
      cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
      pages: "${info.pages}", //总页数
      skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
      skip: true, //是否开启跳页
      total: "${info.total}",
      startRow: "${info.startRow}",
      endRow: "${info.endRow}",
      groups: "${info.pages}" >= 5 ? 5 : "${info.pages}", //连续显示分页数
      curr: function() { //通过url获取当前页，也可以同上（pages）方式获取
        var page = location.search.match(/page=(\d+)/);
        if(page == null) {
          page = {};
          page[0] = "${info.pageNum}";
          page[1] = "${info.pageNum}";
        }
        return page ? page[1] : 1;
      }(),
      jump: function(e, first) { //触发分页后的回调
        if(!first) { //一定要加此判断，否则初始时会无限刷新
          $("#page").val(e.curr);
          $("#add_form").submit();
        }
      }
    });
  });
  
  function restValue(){
	  $(':input','#add_form') 
	  .not(':button, :submit, :reset, :hidden') 
	  .val('') 
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
				<li><a href="javascript:void(0);">保障作业系统</a></li>
				<li><a href="javascript:void(0);">采购计划管理</a></li>
				<li class="active"><a
					href="javascript:jumppage('${pageContext.request.contextPath}/statistic/taskList.html');">任务查询统计</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>
	<!-- 录入采购计划开始-->
	<div class="container">
		<div class="headline-v2 fl">
			<h2>按任务查询</h2>
		</div>

		<h2 class="search_detail">
			<form id="add_form" action="${pageContext.request.contextPath }/statistic/taskList.html" method="post">
				<input type="hidden" name="page" id="page">
				<ul class="demand_list">
					<li><label class="fl"> 任务名称：</label><span> <input
							type="text" name="name" value="${task.name}" />
					</span></li>
					<li><label class="fl"> 任务文号：</label><span> <input
							type="text" name="documentNumber" value="${task.documentNumber }" />
					</span></li>
					<li><label class="fl"> 下达时间：</label> 
					<input type="text" name="beginDate" id="draftReviewedAt" value="${task.beginDate}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate mb0 w150"/>
					<span class="fl" style="font-size: 14px;">至</span><span> <input type="text" name="endDate" id="draftReviewedAt" value="${task.endDate}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate mb0 w150"/>
					</span></li>
					<li><label class="fl"> 下达部门：</label><span> 
                          <select name="purchaseId" id="purchaseId" class="w220">
                             <option value="">请选择</option>
                             <c:forEach items="${allOrg}" var="org" >
                                <option value="${org.id}" <c:if test="${org.id==task.purchaseId}">selected="selected"</c:if>>${org.shortName}</option>
                             </c:forEach>
                          
                          </select>
					</span></li>
				</ul>
				<div class="tc mt5 clear" width="100%">
				<input class="btn" type="submit" name="" value="查询" /> <input
					type="button"  class="btn" onclick="restValue();" value="重置" />
				<div class="clear"></div>
				</div>
			</form>
		</h2>
		<div class="col-md-12 pl20 mt10">
				<input class="btn-u" type="button" name="" value="按明细查询" onclick="javascript:location.href='${pageContext.request.contextPath}/statistic/taskDetailList.html'" />
				<input class="btn-u" type="button" name="" value="按任务查询" onclick="javascript:location.href='${pageContext.request.contextPath}/statistic/taskList.html'" />
				<input class="btn-u" type="button" name="" value="按需求部门统计" onclick="javascript:location.href='${pageContext.request.contextPath}/statistic/charDept.html'" />
				<input class="btn-u" type="button" name="" value="按采购方式统计" onclick="javascript:location.href='${pageContext.request.contextPath}/statistic/charType.html'" />
				<input class="btn-u" type="button" name="" value="按月统计" onclick="javascript:location.href='${pageContext.request.contextPath}/statistic/charMonth.html'" />
			</div>
		<div class="content table_box">
			<table class="table table-bordered table-condensed table-hover">
				<thead>
					<tr>
						<th class="info w50">序号</th>
						<th class="info" width="30%">任务名称</th>
						<th class="info" width="20%">任务文号</th>
						<th class="info" width="10%">预算金额（万元）</th>
						<th class="info" width="10%">下达部门</th>
						<th class="info" width="15%">下达时间</th>
						<th class="info w80">状态</th>
					</tr>
				</thead>
				<c:forEach items="${info.list}" var="obj" varStatus="vs">
					<tr>
						<td class="tc w50">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
						<td class="tl" title="${obj.name}">
						  <c:if test="${fn:length (obj.name) > 20}">${fn:substring(obj.name,0,19)}...</c:if>
                          <c:if test="${fn:length(obj.name) <=20}">${obj.name}</c:if>
						</td>
						<td class="tl" title="${obj.documentNumber }">
						  <c:if test="${fn:length (obj.documentNumber) > 20}">${fn:substring(obj.documentNumber,0,19)}...</c:if>
                          <c:if test="${fn:length(obj.documentNumber) <=20}">${obj.documentNumber}</c:if>
						</td>
						<td class="tr"><fmt:formatNumber type="number"  pattern="#,##0.0000"  value="${obj.budget}"  /></td>
						<td class="tc">${obj.orgName }</td>
						<td class="tc"><fmt:formatDate value="${obj.giveTime }" pattern="yyyy-MM-dd HH:mm:ss"/>  </td>
						<td>
						<c:if test="${obj.status eq '0'}"> 未受领</c:if>
		                <c:if test="${obj.status eq '1'}">已受领</c:if>
		                <c:if test="${obj.status eq '2'}">已取消</c:if>
						</td>
					</tr>
				</c:forEach>


			</table>

			<div id="pagediv" align="right"></div>
		</div>
	</div>


</body>
</html>
