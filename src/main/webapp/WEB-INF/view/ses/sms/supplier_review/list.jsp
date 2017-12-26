<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<html>
  <head>
    <%@ include file="/WEB-INF/view/common.jsp" %>
    <script type="text/javascript">
			$(function() {
			  laypage({
			    cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
			    pages: "${result.pages}", //总页数
			    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
			    skip: true, //是否开启跳页
			    total: "${result.total}",
			    startRow: "${result.startRow}",
			    endRow: "${result.endRow}",
			    groups: "${result.pages}" >= 5 ? 5 : "${result.pages}", //连续显示分页数
			    curr: function() { //合格url获取当前页，也可以同上（pages）方式获取
			      return "${result.pageNum}";
			    }(),
			    jump: function(e, first) { //触发分页后的回调
			      if(!first) { //一定要加此判断，否则初始时会无限刷新
			        $("#page").val(e.curr);
			        $("#formSearch").submit();
			      }
			    }
			  });
			});
		</script>
		<script type="text/javascript">
		  //审核
		  function shenhe(id) {
	      var id = $(":radio:checked").val();
	      if(id == null) {
	          layer.msg("请选择供应商！", {
	            offset: '100px',
	          });
	          return;
	      }
        $("input[name='supplierId']").val(id);
        $("#shenhe_form").attr("action", "${pageContext.request.contextPath}/supplierAudit/essential.html");
        $("#shenhe_form").submit();
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
            <a href="javascript:void(0)">支撑环境</a>
          </li>
          <li>
            <a href="javascript:void(0)">供应商管理</a>
          </li>
          <li>
            <a href="javascript:void(0)">供应商复核</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>
    
    <!-- 搜索 -->
    <div class="container">
      <div class="headline-v2">
        <h2>供应商复核列表</h2>
      </div>
      <h2 class="search_detail">
        <form action="${pageContext.request.contextPath}/supplierReview/list.html"  method="post" id="formSearch"  class="mb0"> 
          <input type="hidden" name="page" id="page">
          
          <div class="col-xs-2 col-sm-4 col-md-4 col-lg-3 mb10">
	          <div class="row">
	            <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">供应商名称：</div>
	            <div class="col-xs-8 f0 lh0">
	              <input class="w100p h32 f14 mb0" type="text" id=supplierName name="supplierName" value="${supplier.supplierName }">
	            </div>
	          </div>
	        </div>
	        
	        <div class="tc">
		        <input class="btn mb0"  value="查询" type="submit">
		        <input class="btn mb0" onclick="clearSearch();" value="重置" type="reset">
          </div>
        </form>
      </h2>
    
      <div class="col-md-12 pl20 mt10">
        <button class="btn btn-windows check" type="button" onclick="shenhe(id)">复核</button>
        <button class="btn btn-windows check" type="button" onclick="jumppage('${pageContext.request.contextPath}/supplierReview/review.html')">重新复核</button>
      </div>
    
	    <!-- 列表 -->
	    <div class="content table_box">
	      <table class="table table-bordered table-condensed table-hover table-striped">
	        <thead>
	          <tr>
	            <td>选择</td>
	            <td>序号</td>
	            <td>供应商名称</td>
	            <td>供应商类型</td>
	            <td>企业性质</td>
	            <td>最新审核时间</td>
	            <td>审核人</td>
	            <td>状态</td>
	          </tr>
	        </thead>
	        <c:forEach items="${result.list}" var="s" varStatus="vs">
	          <tr>
	            <td class="tc w20"><input name="id" type="radio" value="${s.id}"></td>
	            <td class="tc w50">${(vs.index+1)+(result.pageNum-1)*(result.pageSize)}</td>
	            <td>${s.supplierName}</td>
	            <td>${s.supplierTypeNames}</td>
	            <td>${s.businessNature}</td>
	            <td>${s.reviewAt}</td>
	            <td>${s.reviewPeople}</td>
	            <td>${s.status}</td>
	          </tr>
	        </c:forEach>
	      </table>
	      <div id="pagediv" align="right"></div>
	    </div>
	  </div>
  </body>

  <form id="shenhe_form" action="" method="post">
    <input name="supplierId" type="hidden"/>
    <input type="hidden" name="sign" value="1">
  </form>
</html>