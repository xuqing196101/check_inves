<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<%@ page import="ses.constants.SupplierConstants" %>

<html>
  <head>
    <%@ include file="/WEB-INF/view/common.jsp" %>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/ses/sms/supplier_inves/list.js"></script>
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
            <a href="javascript:void(0)">供应商实地考察</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>
    
    <!-- 搜索 -->
    <div class="container">
      <div class="headline-v2">
        <h2>供应商考察列表</h2>
      </div>
      <h2 class="search_detail">
        <form action="${pageContext.request.contextPath}/supplierInves/list.html"  method="post" id="formSearch"  class="mb0"> 
          <input type="hidden" name="pageNum" id="page">
          
          <div class="m_row_5">
				    <div class="row">
				      <div class="col-xs-2 col-sm-4 col-md-4 col-lg-3 mb10">
				        <div class="row">
				          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">供应商名称：</div>
				          <div class="col-xs-8 f0 lh0">
				            <input class="w100p h32 f14 mb0" name="supplierName" type="text" value="${supplierName }">
				          </div>
				        </div>
				      </div>
				      
				      <div class="col-xs-2 col-sm-4 col-md-4 col-lg-3 mb10">
				        <div class="row">
				          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">状态：</div>
				          <div class="col-xs-8 f0 lh0">
				            <select name="status" class="w100p h32 f14" id="status">
				                <option value="">全部</option>
		                    <c:forEach items="<%=SupplierConstants.STATUSMAP_KAOCHA %>" var="item">
		                        <option value="${item.key}" <c:if test="${status == item.key }">selected</c:if>>${item.value}</option>
		                    </c:forEach>
		                    <option value="300"
		                        <c:if test="${status == 300 }">selected</c:if>><%=SupplierConstants.STATUSMAP_AUDITTEMPORARY.get(3) %>
		                    </option>
				            </select>
				          </div>
				        </div>
				      </div>
				      
				      <div class="col-xs-2 col-sm-4 col-md-4 col-lg-3 mb10">
				        <div class="row">
				          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">企业性质：</div>
				          <div class="col-xs-8 f0 lh0">
				            <select name="businessNature" id="businessNature" class="w100p h32 f14">
				              <option value="">全部</option>
				              <c:forEach var="business" varStatus="vs" items="${businessNatureList}">
				                <option <c:if test="${businessNature eq business.id }">selected</c:if> value="${business.id}">${business.name}</option>
				              </c:forEach>
				            </select>
				          </div>
				        </div>
				      </div>
				      
				      <div class="col-xs-2 col-sm-4 col-md-4 col-lg-3 mb10">
				        <div class="row">
				          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">生产经营地址：</div>
				          <div class="col-xs-8 f0 lh0">
				            <input class="w100p h32 f14 mb0" name="addressName" type="text" value="${addressName}">
				          </div>
				        </div>
				      </div>
				      
				      <div class="col-xs-2 col-sm-4 col-md-4 col-lg-3 mb10">
				        <div class="row">
				          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">考察时间：</div>
				          <div class="col-xs-8 f0 lh0">
				            <input id="invesAt" name="invesAt" class="Wdate w100p h32 f14 mb0" value='<fmt:formatDate value="${invesAt}" pattern="YYYY-MM-dd"/>' type="text" onClick="WdatePicker()">
				          </div>
				        </div>
				      </div>
				    </div>
				    </div>
	        
	        <div class="tc">
		        <input class="btn mb0"  value="查询" type="submit">
		        <input class="btn mb0" onclick="resetForm();" value="重置" type="reset">
          </div>
        </form>
      </h2>
    
      <div class="col-md-12 pl20 mt10">
        <button class="btn btn-windows check" type="button" onclick="shenhe()">考察</button>
        <a class="btn btn-windows input" onclick='downloadByType(1)' href="javascript:void(0)">下载考察记录表</a>
        <a class="btn btn-windows input" onclick='downloadByType(2)' href="javascript:void(0)">下载供应商资质材料扫描件</a>
        <a class="btn btn-windows input" onclick='downloadByType(3)' href="javascript:void(0)">下载意见函</a>
      </div>
    
	    <!-- 列表 -->
	    <div class="content table_box">
        <table class="table table-bordered table-condensed table-hover hand m_table_fixed_border">
	        <thead>
	          <tr>
	            <th class="info w50">选择</th>
              <th class="info w50">序号</th>
              <th class="info">供应商名称</th>
              <th class="info">供应商类型</th>
              <th class="info">企业性质</th>
              <th class="info">考察时间</th>
              <th class="info">考察人</th>
              <th class="info">状态</th>
	          </tr>
	        </thead>
	        <c:set var="supplierStatusMap" value="<%=SupplierConstants.STATUSMAP %>"/>
          <c:set var="supplierAuditTemporaryStatusMap" value="<%=SupplierConstants.STATUSMAP_AUDITTEMPORARY %>"/>
	        <c:forEach items="${result.list}" var="s" varStatus="vs">
	          <tr>
	            <td class="tc w20"><input name="id" type="radio" value="${s.id}"></td>
	            <td class="tc w50">${(vs.index+1)+(result.pageNum-1)*(result.pageSize)}</td>
	            <td class="tl">${s.supplierName}</td>
	            <td class="tl">${s.supplierTypeNames}</td>
	            <td class="tc">${s.businessNature}</td>
	            <td class="tc"><fmt:formatDate value="${s.invesAt}" pattern="yyyy-MM-dd" /></td>
	            <td class="tc">${s.invesPeople}</td>
	            <td class="tc" id="${s.id}" sts="${s.status}">
	            	<c:if test="${s.status == 5 and s.auditTemporary != 3}"><span
                      class="label rounded-2x label-u">${supplierStatusMap[s.status]}</span></c:if>
	            	<c:if test="${s.status == 7 or s.status == 8}"><span
                      class="label rounded-2x label-u">${supplierStatusMap[s.status]}</span></c:if>
              	<c:if test="${s.status == 5 and s.auditTemporary == 3}"><span
                      class="label rounded-2x label-u">${supplierAuditTemporaryStatusMap[s.auditTemporary]}</span></c:if>
	            </td>
	          </tr>
	        </c:forEach>
	      </table>
	      <div id="pagediv" align="right"></div>
	    </div>
	  </div>
  </body>

  <form id="shenhe_form" action="" method="post">
    <input name="supplierId" type="hidden"/>
    <input type="hidden" name="sign" value="3">
  </form>
</html>