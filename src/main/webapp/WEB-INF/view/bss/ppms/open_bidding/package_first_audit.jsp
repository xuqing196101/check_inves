<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>包关联初审项</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="<%=basePath%>public/ZHH/css/bootstrap.min.css" media="screen" rel="stylesheet">
	<link href="<%=basePath%>public/ZHH/css/common.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/style.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/line-icons.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/app.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/application.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/header-v4.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/header-v5.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/footer-v2.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/img-hover.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/page_job.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/shop.style.css" media="screen" rel="stylesheet">
	<link href="<%=basePath%>public/ZHH/css/brand-buttons.css" media="screen" rel="stylesheet" type="text/css">
    <script src="<%=basePath%>public/ZHH/js/jquery.min.js"></script>
    <script src="<%=basePath%>public/ZHH/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>public/layer/layer.js"></script>
<script src="<%=basePath%>public/laypage-v1.3/laypage/laypage.js"></script>
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
</script>
  </head>
  
  <body>
	                     <div class="col-md-12 p0">
						   <ul class="flow_step">
						     <li >
							   <a  href="<%=basePath%>firstAudit/toAdd.html?projectId=${projectId}" >01、符合性</a>
							   <i></i>
							 </li>
							 
							 <li class="active">
							   <a  href="<%=basePath%>firstAudit/toPackageFirstAudit.html?projectId=${projectId}" >02、符合性关联</a>
							   <i></i>							  
							 </li>
						     <li>
							   <a  href="<%=basePath%>intelligentScore/packageList.html?projectId=${projectId}">03、评标细则</a>
							   <i></i>
							 </li>
							 <li>
							   <a  href="#tab-4" data-toggle="tab">04、招标文件</a>
							 </li>
						   </ul>
						 </div>
						 <div class="tab-content clear step_cont">
						 <!--第一个  -->
						 <!--第二个 -->
						 <div class=class="col-md-12 tab-pane active"  id="tab-1">
						 	<h1 class="f16 count_flow"><i>02</i>关联初审项</h1>
						 	   <div class="container clear margin-top-30" id="package">
						 	   
								   <c:forEach items="${packageList }" var="pack" varStatus="p">
								   		<form action="<%=basePath%>packageFirstAudit/relate.html" method="post" id="form1">
								   		<input type="hidden" name="packageIds" id="packageIds">
								   		
								   		<input type="hidden" id="packageId" name="packageId" value="${pack.id }"/>
								   		<input type="hidden" name="projectId" value="${projectId}">
								   		<h4><span>包名:<span>${pack.name }</span></h4>
								   		</span>
									       <table class="table table-bordered table-condensed mt5">
								 	            <h5>项目初审项信息</h5>
											    <thead>
											      <tr>
											      	<th class="info w30"><input type="checkbox" id="checkAll" onclick="selectAll(this)"  alt=""></th>
											        <th>初审项名称</th>
											        <th>要求类型</th>
											        <th>创建人</th>
											        <th>创建时间</th>
											      </tr>
											     </thead>
											      <c:forEach items="${list }" var="l" varStatus="vs">
												      <thead>
												       <tr>
												        <td class="tc w30">
													      <input  type="checkbox" value="${l.id }" name="chkItem"
													         <c:forEach items="${idList }" var="id" varStatus="p">
														 	      <c:if test="${id.firstAuditId==l.id && id.packageId==pack.id }"> checked</c:if>
														 	 </c:forEach>
													      >
												        </td>
												        <td align="center">${l.name } </td>
												        <td align="center">${l.kind }</td>
												        <td align="center">${l.creater }</td>
												        <td align="center"><fmt:formatDate type='date' value='${l.createdAt }' dateStyle="default" pattern="yyyy-MM-dd"/></td>
												      </tr>
												      </thead>
										      	  </c:forEach>
										      	  <input type="button" onclick="submit1(this);" value="关联" class="btn btn-windows add"><br/>
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
