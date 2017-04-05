<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp"%>
<!DOCTYPE HTML>
<html>

<head>
<%@ include file="/WEB-INF/view/common.jsp"%>
<title>需求监管列表</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<script src="${pageContext.request.contextPath}/public/easyui/jquery.easyui.min.js"></script>
<link href="${pageContext.request.contextPath}/public/easyui/themes/icon.css" media="screen" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/public/easyui/themes/default/easyui.css" media="screen" rel="stylesheet" type="text/css">
 <script type="text/javascript">
	/*分页  */
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


	//重置
	function resetResult() {
		$("#name").val("");
		$("#archiveCode").val("");
		$("#contractCode").val("");
		//$("#planCode").val("");
		var status = document.getElementById("status").options;
		status[0].selected = true;
	}
	//查看
	function view(id){
	   window.location.href = "${pageContext.request.contextPath}/supervision/demandSupervisionView.html?id="+id;
	}
</script>
</head>

<body>
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a href="javascript:void(0);">首页</a></li>
				<li><a href="javascript:void(0);">业务监管</a></li>
				<li><a href="javascript:void(0);">采购业务监督</a></li>
				<li><a href="javascript:void(0);">采购需求监督</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>
	<div class="container">
		<div class="headline-v2">
			<h2>采购需求列表</h2>
		</div>
		<%-- <h2 class="search_detail">
      <form id="add_form" action="${pageContext.request.contextPath }/planSupervision/list.html" class="mb0" method="post" >
        <input type="hidden" name="page" id="page">
        <ul class="demand_list">
          <li>
            <label class="fl">采购计划名称：</label>
            <span><input type="text" name="fileName" value="${collectPlan.fileName}"/></span>
          </li>
               
          <li>
            <label class="fl">采购金额：</label>
            <span><input type="text" name="budget" value="${collectPlan.budget}"/> </span>
          </li>
          
          <li>
            <label class="fl">状态：</label>
            <span>
              <select name="status">
                <option selected="selected" value="">请选择</option>
                <option value="1" <c:if test="${collectPlan.status=='1'}"> selected</c:if> >审核轮次设置</option>
		            <option value="3" <c:if test="${collectPlan.status=='3'}"> selected</c:if> > 第一轮审核</option>
		            <option value="4" <c:if test="${collectPlan.status=='4'}"> selected</c:if> > 第二轮审核人员设置</option>
		            <option value="5" <c:if test="${collectPlan.status=='5'}"> selected</c:if> > 第二轮审核</option>
		            <option value="6" <c:if test="${collectPlan.status=='6'}"> selected</c:if> > 第三轮审核人员设置</option>
		            <option value="7" <c:if test="${collectPlan.status=='7'}"> selected</c:if> > 第三轮审核</option>
		            <option value="8" <c:if test="${collectPlan.status=='8'}"> selected</c:if> > 审核结束</option>
		            <option value="12" <c:if test="${collectPlan.status=='12'}"> selected</c:if> > 未下达</option>
		            <option value="2" <c:if test="${collectPlan.status=='2'}"> selected</c:if> > 已下达</option>
              </select>
            </span>
          </li>       
        </ul>
        <input class="btn fl mt1" type="submit" value="查询" /> 
        <input class="btn fl mt1" type="button" value="重置" onclick="resetQuery()"  /> 
      </form>
    </h2> --%>

		<div class="content table_box">
			<table class="table table-bordered table-condensed table-hover">
				<thead>
					<tr class="info">
						<th class="w50">序号</th>
						<th>采购需求名称</th>
						<th>采购需求文号</th>
						<th>需求单位</th>
						<th>预算总金额（万元）</th>
						<th>任务性质</th>
						<th>填报人</th>
						<th>状态</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${list.list}" var="items" varStatus="status">
						<tr class="tc">
							<td class="tc w50"  >${(status.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
							<td class="tl">
							<a href="javascript:void(0)" onclick="view('${items.id}',2);" >${items.planName}</a>
							</td>
							<td class="tl">${items.referenceNo}</td>
							<td class="tl">${items.department}</td>
							<td class="tr">${items.budget}</td>
							<td>${items.goodsType}</td>
							<td>${items.userName}</td>
							<td>
							<c:if test="${items.status=='1' }">
			 		                               未提交
			  	            </c:if> 
			  	            <c:if test="${items.status=='4' }">
			 		                               受理退回
			  	             </c:if> 
			  	             <c:if test="${items.status =='2' || items.status =='3' || items.status=='5' }">
			 		                                 已提交
			  	             </c:if>
							</td>
							<td class="tc" >
							<a href="javascript:void(0)" onclick="view('${items.id}');"  >查看</a>
                            </td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="pagediv" align="right"></div>
	</div>
</body>

</html>