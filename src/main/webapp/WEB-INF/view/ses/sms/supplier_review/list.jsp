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
		  function shenhe() {
			  var supplierId = $(":radio:checked").val();
	      if(supplierId == null) {
	          layer.msg("请选择供应商！", {
	            offset: '100px',
	          });
	          return;
	      }
	      
	      $.ajax({
 	        url: "${pageContext.request.contextPath}/supplierReview/reviewAudit.do",
 	        type: "post",
 	        data: {"supplierId" : supplierId},
 	        success: function(){
	          window.setTimeout(function() {
	          	$("input[name='supplierId']").val(supplierId);
	            $("#submitform").attr("action", "${pageContext.request.contextPath}/supplierAudit/essential.html");
	            $("#submitform").submit();
	          }, 1000);
 	        },
 	        error: function(){
 	          layer.msg("操作失败！", {offset: '100px'});
 	        }
	      });
		  }
		  
		  //重置
		  function clearSearch(){
			  $("input[name='supplierName']").attr("value", "");
			  $("input[name='reviewAt']").attr("value", "");
			  $("#status option:selected").removeAttr("selected");
			  $("#businessNature option:selected").removeAttr("selected");
		  }
		  
		//重新复核
    function restartReview(){
    	var supplierId = $(":radio:checked").val();
      $.ajax({
	      url: "${pageContext.request.contextPath}/supplierReview/restartReview.do",
	      type: "post",
	      data: {"supplierId" : supplierId},
	      success: function(result){
	    	  if(result.status == 200){
	    		  layer.msg(result.msg, {offset: '100px'});
            window.setTimeout(function() {
              $("#submitform").attr("action", "${pageContext.request.contextPath}/supplierReview/list.html");
              $("#submitform").submit();
            }, 1000);
	    	  }else{
	    		  layer.msg(result.msg, {offset: '100px'});
	    	  }
	      },
	      error: function(){
	        layer.msg("操作失败！", {offset: '100px'});
	      }
      });
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
        <div class="m_row_5">
    	  <div class="row">
          <div class="col-xs-2 col-sm-4 col-md-4 col-lg-3 mb10">
	          <div class="row">
	            <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">供应商名称：</div>
	            <div class="col-xs-8 f0 lh0">
	              <input class="w100p h32 f14 mb0" type="text" name="supplierName" value="${supplier.supplierName}">
	            </div>
	          </div>
	        </div>
	        
          <div class="col-xs-2 col-sm-4 col-md-4 col-lg-3 mb10">
	          <div class="row">
	            <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">状态：</div>
	            <div class="col-xs-8 f0 lh0">
	              <select name="status" id="status" class="w100p h32 f14 hand">
	                <option value="">全部</option>
	                <option <c:if test="${supplier.auditTemporary == 2 }">selected</c:if> value="-1">复核中</option>
	                <option <c:if test="${supplier.status == 1 and supplier.auditTemporary != 2}">selected</c:if> value="1">入库（待复核）</option>
	                <option <c:if test="${supplier.status == 5 }">selected</c:if> value="5">复核合格（待考察）</option>
	                <option <c:if test="${supplier.status == 6 }">selected</c:if> value="6">复核不合格</option>
	              </select>
	            </div>
	          </div>
	        </div>
	        
	        <div class="col-xs-2 col-sm-4 col-md-4 col-lg-3 mb10">
		        <div class="row">
		          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">企业性质：</div>
		          <div class="col-xs-8 f0 lh0">
		            <select name="businessNature" id="businessNature" class="w100p h32 f14 hand">
		              <option value="">全部</option>
		              <c:forEach var="business" varStatus="vs" items="${businessNatureList}">
		                <option <c:if test="${supplier.businessNature eq business.id }">selected</c:if> value="${business.id}">${business.name}</option>
		              </c:forEach>
		            </select>
		          </div>
		        </div>
		      </div>
		      
		      <div class="col-xs-2 col-sm-4 col-md-4 col-lg-3 mb10">
		        <div class="row">
		          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">审核时间：</div>
		          <div class="col-xs-8 f0 lh0">
		            <input name="reviewAt" class="Wdate w100p h32 f14 mb0 hand" value='<fmt:formatDate value="${supplier.reviewAt}" pattern="YYYY-MM-dd"/>' type="text" onClick="WdatePicker()">
		          </div>
		        </div>
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
        <button class="btn btn-windows check" type="button" onclick="shenhe()">复核</button>
        <button class="btn btn-windows check" type="button" onclick="restartReview();">重新复核</button>
      </div>
    
	    <!-- 列表 -->
	    <div class="content table_box">
	      <table class="table table-bordered table-hover">
	        <thead>
	          <tr>
	            <th class="tc w50">选择</th>
	            <th class="tc w50">序号</th>
	            <th>供应商名称</th>
	            <th>供应商类型</th>
	            <th class="w100">企业性质</th>
	            <th class="w100">最新审核时间</th>
	            <th class="w100">审核人</th>
	            <th class="w100">状态</th>
	          </tr>
	        </thead>
	        <c:forEach items="${result.list}" var="s" varStatus="vs">
	          <tr>
	            <td class="tc"><input name="id" type="radio" value="${s.id}"></td>
	            <td class="tc">${(vs.index+1)+(result.pageNum-1)*(result.pageSize)}</td>
	            <td <c:if test="${s.reviewStatus == 1}">class="red"</c:if>>${s.supplierName}</td>
	            <td>${s.supplierTypeNames}</td>
	            <td class="tc">${s.businessNature}</td>
	            <td class="tc"><fmt:formatDate value="${s.reviewAt}" pattern="yyyy-MM-dd"/></td>
	            <td class="tc">${s.reviewPeople}</td>
	            <td class="tc">
	              <c:if test="${s.status == 1 and s.auditTemporary == 2}"><span class="label rounded-2x label-dark">复核中</span></c:if>
	              <c:if test="${s.status == 1 and s.auditTemporary != 2}"><span class="label rounded-2x label-dark">入库（待复核）</span></c:if>
	              <c:if test="${s.status == 5}"><span class="label rounded-2x label-u">复核合格（待考察）</span></c:if>
	              <c:if test="${s.status == 6}"><span class="label rounded-2x label-dark">复核不合格</span></c:if>
	            </td>
	          </tr>
	        </c:forEach>
	      </table>
	      <div id="pagediv" align="right"></div>
	    </div>
	  </div>
  </body>

  <form id="submitform" action="" method="post">
    <input name="supplierId" type="hidden"/>
    <input type="hidden" name="sign" value="2">
  </form>
</html>