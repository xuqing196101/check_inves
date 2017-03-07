<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
  <head>
    
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <script type="text/javascript">
        
      function confirmOk(obj, id, flowDefineId){
      	   layer.confirm('您已经确认了吗?', {title:'提示',offset: ['100px'],shade:0.01}, function(index){
	 			layer.close(index);
	 			$.ajax({
	 				url:"${pageContext.request.contextPath}/Adopen_bidding/confirmOk.html?projectId="+id+"&flowDefineId="+flowDefineId,
	 				dataType: 'json',  
	 	       		success:function(result){
	                   	layer.msg(result.msg,{offset: '222px'});
	                   	$("#queren").after("<a href='javascript:volid(0);' >05、已确认</a>");
	                    $("#queren").remove();
	                },
	                error: function(result){
	                    layer.msg("确认失败",{offset: '222px'});
	                }
	 	       	});
	 		});
      }
      
      function jump(url){
       	$("#open_bidding_main").load(url);
	   }
  </script>
  </head>
  <body>
	 <div class="col-md-12 p0">
	   <ul class="flow_step">
	     <li class="active">
		   <a  href="${pageContext.request.contextPath}/adFirstAudit/toAdd.html?projectId=${projectId}&flowDefineId=${flowDefineId}" >01、资格性和符合性审查</a>
         <i></i>
       </li>
       
       <%-- <li >
         <a  href="${pageContext.request.contextPath}/firstAudit/toPackageFirstAudit.html?projectId=${project.id}&flowDefineId=${flowDefineId}" >02、符合性关联</a>
         <i></i>                
       </li> --%>
         <li>
         <a  href="${pageContext.request.contextPath}/adIntelligentScore/packageList.html?projectId=${projectId}&flowDefineId=${flowDefineId}">02、经济和技术评审细则</a>
         <i></i>
       </li>
       <li class="active">
         <a  href="${pageContext.request.contextPath}/Adopen_bidding/bidFile.html?id=${projectId}&flowDefineId=${flowDefineId}" >
           03、招标文件
		         <%-- <c:if test="${project.dictionary.code eq 'GKZB' }">
			     03、招标文件
			     </c:if>
			     <c:if test="${project.dictionary.code eq 'XJCG' }">
			     03、询价文件
			     </c:if>
			     <c:if test="${project.dictionary.code eq 'YQZB' }">
			     03、招标文件
			     </c:if>
			     <c:if test="${project.dictionary.code eq 'JZXTP' }">
			     03、竞谈文件
			     </c:if>
			     <c:if test="${project.dictionary.code eq 'DYLY' }">
			     03、单一来源文件
			     </c:if> --%>
		   </a>
		   <i></i>
		 </li>
		 <li>
		    <c:if test="${project.confirmFile == 0 || project.confirmFile==null}"><a onclick="confirmOk(this,'${projectId}');" id="queren">05、确认</a></c:if>
		    <c:if test="${project.confirmFile == 1 }"><a>05、已确认</a></c:if>
		 </li>
	   </ul>
	 </div>
	 <div class="content">
    	<table class="table table-bordered table-condensed table-hover table-striped">
			<thead>
				<tr>
				  <th class="w50 info">序号</th>
				  <th class="info">初审项名称</th>
		          <th class="info">要求类型</th>
		          <th class="info">创建人</th>
				</tr>
			</thead>
			<c:forEach items="${list }" var="l" varStatus="vs">
			    <tr>
			    	<td class="tc w50">${vs.index+1 }</td>
			        <td >${l.name }</td>
			        <td class="tc">${l.kind }</td>
			        <td class="tc">${l.creater }</td>
			    </tr>
		    </c:forEach>
		</table>
   	</div>
  </body>
</html>
