<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
  <head>
  	<%@ include file="/WEB-INF/view/common.jsp"%>
    <base href="${pageContext.request.contextPath}/">
    <script type="text/javascript">
    var result;
    $(function(){
    	var packageId=	$("input[name='packageId']").val();
    	var flag="${flag}";
    	if(flag=="success"){
    		layer.msg("关联成功",{offset: ['322px', '390px']});
    	}else if(flag=="error"){
    		layer.msg("请选择一条初审项！",{offset: ['322px', '490px']});
    	}
    })
    
	 /** 全选全不选 */
    function selectAll(obj){
    	var form = obj.parentNode.parentNode.parentNode.parentNode.parentNode;
    	var checkbox = $(form).find("input[type='checkbox']");
         var checklist = document.getElementsByName ("chkItem");
         if(obj.checked){
               for(var i=0;i<checkbox.length;i++)
               {
            	   checkbox[i].checked = true;
               }
             }else{
              for(var j=0;j<checkbox.length;j++)
              {
            	  checkbox[j].checked = false;
              }
            }
        }
	 function submit1(obj){
		 
		 var count = 0;
	  	  var ids = document.getElementsByName("chkItem");
	   
	       for(i=0;i<ids.length;i++) {
	     		 if(document.getElementsByName("chkItem")[i].checked){
	     		 var id = document.getElementsByName("chkItem")[i].value;
	     		//var value = id.split(",");
	     		 count++;
	      }
	    }
	       //获取当前的form表单对象
	       var parent = obj.parentNode; 
	      /*  alert(parent.tagName);
	        while(parent .tagName == "form")
	       {
	           parent = parent .parentNode;
	           break;
	       }  */
	       if(count>0){
	    	   var packageId=	$("input[name='packageId']").val();
	    	   $("#packageIds").val(packageId);
	    	   parent.submit();
	    	   
	       }else{
	    	   layer.alert("请选择一条初审项",{offset: ['222px', '390px'],shade:0.01});
	    	   return;
	       }
	 }
	 
	 function jump(url){
       	 $("#open_bidding_main").load(url);
	   }
</script>
  </head>
  
  <body>
	                     <div class="col-md-12 p0">
						   <ul class="flow_step">
						     <li >
							   <a  href="${pageContext.request.contextPath}/firstAudit/toAdd.html?projectId=${projectId}&flowDefineId=${flowDefineId}" >01、资格性和符合性审查</a>
							   <i></i>
							 </li>
							 
							<%--  <li class="active">
							   <a  href="${pageContext.request.contextPath}/firstAudit/toPackageFirstAudit.html?projectId=${projectId}&flowDefineId=${flowDefineId}" >02、符合性关联</a>
							   <i></i>							  
							 </li> --%>
						     <li>
							   <a  href="${pageContext.request.contextPath}/intelligentScore/packageList.html?projectId=${projectId}&flowDefineId=${flowDefineId}">02、经济和技术评审细则</a>
							   <i></i>
							 </li>
							 <li>
							   <a  href="${pageContext.request.contextPath}/open_bidding/bidFile.html?id=${projectId}&flowDefineId=${flowDefineId}" >
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
							 </li>
						   </ul>
						 </div>
						 <div class="tab-content clear step_cont">
						 <div class="col-md-12 tab-pane active"  id="tab-1">
						 	<div class="headline-v2">
						   <h2>关联初审项</h2>
						   </div>
						 	   <div class="container clear margin-top-30" id="package">
						 	   
								   <c:forEach items="${packageList }" var="pack" varStatus="p">
								   		<form action="${pageContext.request.contextPath}/packageFirstAudit/relate.html" method="post" id="form1">
								   		<input type="hidden" name="packageIds" id="packageIds">
								   		
								   		<input type="hidden" id="packageId" name="packageId" value="${pack.id }"/>
								   		<input type="hidden" name="projectId" value="${projectId}">
								   		<h4><span>包名:<span>${pack.name }</span></h4>
								   		</span>
									       <table class="table table-bordered table-condensed table-hover table-striped">
								 	            <h5>项目初审项信息</h5>
											    <thead>
											      <tr>
											      <c:if test="${project.confirmFile != 1}">
											      	<th class="info w30"><input type="checkbox" id="checkAll" onclick="selectAll(this)"  alt=""></th>
											      </c:if>
											       <c:if test="${project.confirmFile == 1}">
											      	<th class="info w30"><input type="checkbox" id="checkAll" disabled="disabled" onclick="selectAll(this)"  alt=""></th>
											      </c:if>
											        <th class="info">初审项名称</th>
											        <th class="info">要求类型</th>
											        <th class="info">创建人</th>
											        <th class="info">创建时间</th>
											      </tr>
											     </thead>
											     <c:if test="${project.confirmFile != 1}">
										      	  <input type="button" onclick="submit1(this);" value="关联" class="btn btn-windows add"><br/>
								   		  		  </c:if>
											      <c:forEach items="${list }" var="l" varStatus="vs">
												       <tr>
												        <td class="tc w30">
												        <c:if test="${project.confirmFile != 1}">
													      <input  type="checkbox" value="${l.id }" name="chkItem"
													         <c:forEach items="${idList }" var="id" varStatus="p">
														 	      <c:if test="${id.firstAuditId==l.id && id.packageId==pack.id }"> checked</c:if>
														 	 </c:forEach>
													      >
													    </c:if>
													     <c:if test="${project.confirmFile == 1}">
													      <input  type="checkbox" value="${l.id }" name="chkItem" disabled="disabled"
													         <c:forEach items="${idList }" var="id" varStatus="p">
														 	      <c:if test="${id.firstAuditId==l.id && id.packageId==pack.id }"> checked</c:if>
														 	 </c:forEach>
													      >
													    </c:if>
												        </td>
												        <td align="center">${l.name } </td>
												        <td align="center">${l.kind }</td>
												        <td align="center">${l.creater }</td>
												        <td align="center"><fmt:formatDate type='date' value='${l.createdAt }' dateStyle="default" pattern="yyyy-MM-dd"/></td>
												      </tr>
										      	  </c:forEach>
								   		  </table>
						   		      </form>
								   </c:forEach>
							   </div> 
							<div class="container clear margin-top-30" id="package">
						 	
						 </div>	
						</div>
                      </div>
  </body>
</html>
