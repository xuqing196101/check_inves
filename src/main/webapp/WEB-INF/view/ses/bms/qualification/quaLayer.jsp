<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE html>
<html>
  <head>
  <%@ include file="/WEB-INF/view/common.jsp"%>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/ses/qualification/quaLayer.js"></script>
  </head>
  <body>
    <div class="container">
      <div class="tab-content mt10">
        <div class="tab-v2">
          <input type="hidden" id="type"  name="type" value="${type}"/>
          <input type="hidden" id="ids" name="ids" value="${ids}"/>
          <input type="hidden" id="names" name="names" value="${names}"/>
          <input type="hidden" id="choseIds">
	 	  <input type="hidden" id="choseNames">
          <div class="tab-content">
		      <h2 class="search_detail">
				<ul class="demand_list">
		    	  <li>
			    	<label class="fl">名称：</label>
			    	<span>
			    	  <input type="text" id="name"  name="name" />
			    	</span>
			      </li>
			    	<button type="button" onclick="search();" class="btn f1 mt1">查询</button>
			    	<button type="button" onclick="resetQuery()" class="btn f1 mt1">重置</button>  	
				  </ul>
				  <div class="clear"></div>
			  </h2>
			  <div class="content table_box">
		        <table class="table table-bordered table-condensed table-hover table-striped" id="dataTable">
		          <thead>
					<tr>
					  <th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
					  <th class="info w50">序号</th>
					  <th class="info">名称</th>
					</tr>
				 <thead>
				 <tbody>
				   <c:forEach items="${list.list}" var="qualification" varStatus="vs">
				     <tr>
					   <td class="tc"><input type="checkbox" name="chkItem" value="${qualification.id}" /></td>
					   <td class="tc">${vs.index+1}</td>
					   <td class="tl pl20">${qualification.name}</td>
					  </tr>
				    </c:forEach>
				  </tbody>
				</table>
			  </div>
		      <div id="pagediv" align="right"></div>
		   </div>
	    </div>
	  </div>
	  <div class="tc mt10">
	    <button class="btn btn-windows save" onclick="ok();">确认</button>
	    <button class="btn btn-windows cancel" onclick="cancel();" >取消</button>
	  </div>
	</div>
  </body>
</html>
