<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
  </head>
  
  <body>
  
  <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')" target="_blank"> 首页</a></li><li><a href="javascript:void(0)">须知文档管理</a></li><li class="active"><a href="javascript:jumppage('${ pageContext.request.contextPath }/noticeDocument/getAll.html')">须知文档管理</a></li><li class="active"><a href="javascript:void(0)">须知文档详情</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   
  <div class="container content pt0">
	 <div class="row magazine-page">
	   <div class="col-md-12 col-sm-12 col-cs-12 tab-v2">
	        <div class="padding-top-10">
	        <ul class="nav nav-tabs bgwhite">
	            <li class="active"><a aria-expanded="true" href="javascript:void(0)" data-toggle="tab" class="f18">须知文档详情</a></li>
            </ul>
            <div class="tab-content padding-top-20 over_hideen">
            <div class="tab-pane fade active in" id="tab-1">
                <h2 class="count_flow jbxx">基本信息</h2>
                <table class="table table-bordered">
                 <tbody>
                     <tr>
	                  <td class="bggrey " width="12%">须知文档名称：</td>
	                  <td width="38%">${noticeDocument.name}</td>
	                  <td class="bggrey " width="12%">须知文档类型：</td>
	                  <td width="38%">
	                    <c:forEach items="${noticeType}" var="type">
       			          <c:if test="${type.id == noticeDocument.docType}">${type.name} </c:if>
       			        </c:forEach>
	                  </td>
	                 </tr>
	                 <tr>
	                  <td class="bggrey " width="12%">创建时间：</td>
	                  <td width="38%"><fmt:formatDate value='${noticeDocument.createdAt}' pattern='yyyy-MM-dd'/></td>
	                  <td class="bggrey " width="12%">修改时间：</td>
	                  <td width="38%"><fmt:formatDate value='${noticeDocument.updatedAt}' pattern='yyyy-MM-dd'/></td>
	                 </tr> 
                 </tbody>
                 </table>
                 <h2 class="count_flow jbxx">须知文档内容</h2>
                 <div class="col-md-12 col-sm-12 col-cs-12 p0">
                     <script id="editor" name="content" type="text/plain" class="mt20"></script>
                </div>
                 </div>
                 	<!-- 底部按钮 -->			          
 				 <div class="col-md-12 col-sm-12 col-cs-12 mt10 tc">
  					<button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
 				 </div>
               </div>

	  	 
	</div>  	
     </div>
     </div>
     </div>
     <script type="text/javascript">
    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var ue = UE.getEditor('editor');
    var content='${noticeDocument.content}';
	ue.ready(function(){
  		ue.setContent(content);    
  		ue.setDisabled([]);
	});
    
</script>
  </body>
</html>
