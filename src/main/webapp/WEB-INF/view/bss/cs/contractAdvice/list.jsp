<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
	<jsp:include page="/WEB-INF/view/common.jsp"/>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<script type="text/javascript">
		/*分页  */
      $(function() {
        laypage({
          cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
          pages: "${info.pages}", //总页数
          skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
          skip: true, //是否开启跳页
          total: "${info.total}",
          startRow: "${info.startRow}",
          endRow: "${info.endRow}",
          groups: "${info.pages}" >= 3 ? 3 : "${info.pages}", //连续显示分页数
          curr: function() { //通过url获取当前页，也可以同上（pages）方式获取
            return "${info.pageNum}";
          }(),
          jump: function(e, first) { //触发分页后的回调
            if(!first) { //一定要加此判断，否则初始时会无限刷新
              $("#page").val(e.curr);
              $("#form1").submit();
            }
          }
        });
      });
      
      function clearSearch() {
      	$("#name").attr("value", "");
      	$("#projectName").attr("value", "");
        $("#code").attr("value", "");
        $("#status option:selected").removeAttr("selected");
      }
      
      function view(id,status) {
      	window.location.href = "${pageContext.request.contextPath}/contractAdvice/audit.html?id=" + id + "&status=" + status;
      }
	</script>
  </head>
  
  <body>
    <!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
          <li>
            <a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a>
          </li>
          <li>
            <a href="javascript:void(0)">保障作业系统</a>
          </li>
          <li>
            <a href="javascript:void(0)">采购合同管理</a>
          </li>
          <li class="active">
            <a href="javascript:jumppage('${pageContext.request.contextPath}/contractAdvice/list.html')">合同审核</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>
    <!-- 录入采购计划开始-->
    <div class="container">
      <div class="headline-v2">
        <h2>审核列表</h2>
      </div>
      <!-- 项目戳开始 -->
      <h2 class="search_detail">
    <form action="${pageContext.request.contextPath}/contractAdvice/list.html" id="form1" method="post" class="mb0">
      <ul class="demand_list">
      <li>
        <label class="fl">项目名称： </label>
        <span>
          <input type="hidden" name="page" id="page">
          <input type="text" name="projectName" id="projectName" value="${contract.projectName}" />
        </span>
      </li>
      <li>
        <label class="fl">合同编号：</label> 
        <span>
         <input type="text" name="code" id="code" value="${contract.code}" />
        </span>
      </li>
      <li>
        <label class="fl">合同名称：</label> 
        <span>
         <input type="text" name="name" id="name" value="${contract.name}" />
        </span>
      </li>
      <li>
        <label class="fl">状态：</label>
            <span class="">
              <select name="status" id="status">
                <option value="0" <c:if test="${contractAdvice.status == 0}">selected="selected"</c:if>>全部</option>
                <option value="1" <c:if test="${contractAdvice.status == 1}">selected="selected"</c:if>>待审核</option>
                <option value="2" <c:if test="${contractAdvice.status == 2}">selected="selected"</c:if>>审核中</option>
                <option value="3" <c:if test="${contractAdvice.status == 3}">selected="selected"</c:if>>审核通过</option>
                <option value="4" <c:if test="${contractAdvice.status == 4}">selected="selected"</c:if>>审核不通过</option>
              </select>
            </span>
      </li>
    </ul>
    <div class="clear tc">
	    	<button class="btn mb0" type="submit">查询</button>
	      <button type="reset" class="btn mr0 mb0" onclick="clearSearch();">重置</button>
	    </div>
    <div class="clear"></div>
    </form>
    </h2>
      <div class="content table_box">
        <table class="table table-bordered table-condensed table-hover table-striped">
          <thead>
            <tr class="info">
              <th class="w50">序号</th>
              <th width="25%">合同编号</th>
              <th width="15%">合同名称</th>
              <th width="15%">项目名称</th>
              <th width="10%">预算（万元）</th>
              <th width="5%">年度</th>
              <th width="5%">顶级预算科目</th>
              <th>提交人</th>
              <th width="10%">提交审核时间</th>
              <th>审核状态</th>
            </tr>
          </thead>
          <tbody id="tbody_id">
            <c:forEach items="${info.list}" var="obj" varStatus="vs">
              <tr class="pointer">
                <td class="tc w50">${(vs.index+1)+(info.pageNum-1)*(info.pageSize)}</td>
                <td class="tl" onclick="view('${obj.id}','${obj.status}')">${obj.purchaseContract.name}</td>
                <td class="tl" onclick="view('${obj.id}','${obj.status}')">${obj.purchaseContract.code}</td>
                <td class="tl" onclick="view('${obj.id}','${obj.status}')">${obj.project.name}</td>
                <td class="tc" onclick="view('${obj.id}','${obj.status}')">${obj.purchaseContract.budget}</td>
                <td class="tc" onclick="view('${obj.id}','${obj.status}')">${obj.purchaseContract.year}</td>
                <td class="tl" onclick="view('${obj.id}','${obj.status}')">${obj.purchaseContract.budgetSubjectItem}</td>
                <td class="tl" onclick="view('${obj.id}','${obj.status}')">${obj.proposer}</td>
                <td class="tl" onclick="view('${obj.id}','${obj.status}')">
                	<fmt:formatDate type='date' value='${obj.createdAt}' pattern=" yyyy-MM-dd HH:mm:ss " />
                </td>
                <td class="tl" onclick="view('${obj.id}','${obj.status}')">
                	<c:if test="${obj.status == 1}">待审核</c:if>
                  <c:if test="${obj.status == 2}">审核中</c:if>
                  <c:if test="${obj.status == 3}">审核通过</c:if>
                  <c:if test="${obj.status == 4}">审核不通过</c:if>
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
