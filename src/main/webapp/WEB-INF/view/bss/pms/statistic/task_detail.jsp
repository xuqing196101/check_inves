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
			<h2>按明细查询</h2>
		</div>

		<h2 class="search_detail">
			<form id="add_form"
				action="${pageContext.request.contextPath }/statistic/taskDetailList.html"
				method="post">
				<input type="hidden" name="page" id="page">
				<ul class="demand_list">
				<li><label class="fl"> 需求部门：</label><span> 
                        <select name="department" id="department" style="width: 173px;">
                             <option value="">请选择</option>
                             <c:forEach items="${allXq}" var="org" >
                                <option value="${org.shortName}" <c:if test="${org.shortName==detail.department}">selected="selected"</c:if>>${org.shortName}</option>
                             </c:forEach>
                          
                          </select>


					</span></li>
					<li><label class="fl"> 任务类型：</label><span> <input
							type="text" name="" value="" />
					</span></li>
					<li><label class="fl"> 产品名称：</label><span> <input
							type="text" name="goodsName" value="${detail.goodsName }" />
					</span></li>
					<li><label class="fl"> 任务文号：</label><span> 
					
					<input
							type="text" name="taskNumber" value="${detail.taskNumber }" />
					</span></li>
					<li><label class="fl"> 采购方式：</label><span> 
                     <select name="purchaseType" id="purchaseTypes" style="width: 173px;">
                         <option value="">请选择</option>
                         <c:forEach items="${dataType}" var="d" >
                              <option value="${d.id}" <c:if test="${d.id==detail.purchaseType}">selected="selected"</c:if>>${d.name}</option>
                         </c:forEach>
                     </select>

					</span></li>
					<li><label class="fl"> 采购机构：</label><span> 

                          <select name="organization" id="organization" style="width: 173px;">
                             <option value="">请选择</option>
                             <c:forEach items="${allOrg}" var="org" >
                                <option value="${org.id}" <c:if test="${org.id==detail.organization}">selected="selected"</c:if>>${org.shortName}</option>
                             </c:forEach>
                          
                          </select>
					</span></li>
					<li><label class="fl"> 下达时间：</label> <input type="text" name="beginDate" id="draftReviewedAt" value="${detail.beginDate}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate mb0 w150"/>
					<span class="fl" style="font-size: 14px;">至</span><span> <input type="text" name="endDate" id="draftReviewedAt" value="${detail.endDate}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate mb0 w150"/>
					</span></li>
					
					<li><label class="fl"> 项目编号：</label><span> <input
							type="text" name="" value="" />
					</span></li>
					<li><label class="fl"> 开标时间：</label><span> <input
							type="text" name="" value="" />
					</span></li>
					<li><label class="fl"> 合同编号：</label><span> <input
							type="text" name="" value="" />
					</span></li>
					<li><label class="fl"> 供应商名称：</label><span> <input
							type="text" name="" value="" />
					</span></li>
					</ul>
					<div class="tc mt5 clear" width="100%">
				<input class="btn" type="submit" name="" value="查询" /> <input
					type="button"  class="btn" onclick="restValue();" value="重置" />
					</div>
					
			</form>
		</h2>
		<div class="col-md-12 pl20 mt10">
				<input class="btn-u" type="button" name="" value="按任务查询" onclick="javascript:location.href='${pageContext.request.contextPath}/statistic/taskList.html'" />
				<input class="btn-u" type="button" name="" value="按明细查询" onclick="javascript:location.href='${pageContext.request.contextPath}/statistic/taskDetailList.html'" />
				<input class="btn-u" type="button" name="" value="按需求部门统计" onclick="javascript:location.href='${pageContext.request.contextPath}/statistic/charDept.html'" />
				<input class="btn-u" type="button" name="" value="按采购方式统计" onclick="javascript:location.href='${pageContext.request.contextPath}/statistic/charType.html'" />
				<input class="btn-u" type="button" name="" value="按月统计" onclick="javascript:location.href='${pageContext.request.contextPath}/statistic/charMonth.html'" />
			</div>
		<div class="content table_box">
			<table class="table table-bordered table-condensed table-hover">
				<thead>
					<tr>
						<th class="info w50">序号</th>
						<th class="info" width="13%">任务名称</th>
						<th class="info" width="13%">任务文号</th>
						<th class="info" width="5%">需求部门</th>
						<th class="info" width="5%">产品编号</th>
						<th class="info" width="15%">产品名称</th>
						<th class="info" width="4%">单位</th>
						<th class="info" width="5%">采购数量</th>
						<th class="info" width="6%">单价(元)</th>
						<th class="info" width="6%">金额(万元)</th>
						<th class="info" width="6%">采购方式</th>
						<th class="info" width="7%">采购机构</th>
						<th class="info" width="11%">下达时间</th>
					</tr>
				</thead>
				<c:forEach items="${info.list}" var="obj" varStatus="vs">
				
					<tr  <c:if test="${obj.isParent=='true'}">class="red" </c:if> >
						<td class="tc w50">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
						<td title="${obj.taskName }">
						    <c:if test="${fn:length (obj.taskName) > 15}">${fn:substring(obj.taskName,0,14)}...</c:if>
                            <c:if test="${fn:length(obj.taskName) <=15}">${obj.taskName}</c:if>
						</td>
						<td title="${obj.taskNumber }">
						   <c:if test="${fn:length (obj.taskNumber) > 15}">${fn:substring(obj.taskNumber,0,14)}...</c:if>
                            <c:if test="${fn:length(obj.taskNumber) <=15}">${obj.taskNumber}</c:if>
						</td>
						<td>${obj.department }</td>
						<td>${obj.seq }</td>
						<td title="${obj.goodsName }">
						    <c:if test="${fn:length (obj.goodsName) > 18}">${fn:substring(obj.goodsName,0,17)}...</c:if>
                            <c:if test="${fn:length(obj.goodsName) <=18}">${obj.goodsName}</c:if>
						</td>
						<td>${obj.item}</td>
						<td>${obj.purchaseCount }</td>
						<td>${obj.price }</td>
						<td>${obj.budget }</td>
						<td>
						   <c:if test="${obj.purchaseType eq '空值' }"></c:if>
						   <c:if test="${obj.purchaseType ne '空值' }">${obj.purchaseType }</c:if>
						</td>
						<td>${obj.organization }</td>
						<td><fmt:formatDate value="${obj.taskGiveTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					</tr>
				</c:forEach>


			</table>

			<div id="pagediv" align="right"></div>
		</div>
	</div>


</body>
</html>
