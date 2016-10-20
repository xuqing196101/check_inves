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
    
    <title>项目评审</title>
    
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
   
	 /** 全选全不选 */
    function selectAll(){
         var checklist = document.getElementsByName ("chkItem");
         var checkAll = document.getElementById("checkAll");
         if(checkAll.checked){
               for(var i=0;i<checklist.length;i++)
               {
                  checklist[i].checked = true;
               }
             }else{
              for(var j=0;j<checklist.length;j++)
              {
                 checklist[j].checked = false;
              }
            }
        }
	
</script>
  </head>
  
  <body>
						 <div class="tab-content clear step_cont">
						 <div class=class="col-md-12 tab-pane active"  id="tab-1">
						 	<h1 class="f16 count_flow">专家评审</h1>
						 	   <div class="container clear margin-top-30" id="package">
						 	       <table >
						 	         <thead>
						 	            <th><h2>项目名称：</h2></th><th><h2>${extension.projectName }（${extension.packageName }）&nbsp;&nbsp;&nbsp;&nbsp;</h2></th>
						 	            
						 	            <th><h2>编号：</h2></th><th><h2>${extension.projectCode }</h2></th>
						 	         </thead>
						 	       </table>
									   <form action="" method="post" >
									   <!--包id  -->
									   <input type="hidden" id="packageId" name="packageId" value=""/>
								   	   <input type="hidden" name="projectId" value="">
								   	   <input type="hidden" name="packageIds" id="packageIds">
										   <table class="table table-bordered table-condensed mt5">
										   		<thead>
										   		  <th>评审内容</th>
										   		  <c:forEach items="${extension.supplierList }" var="supplier" varStatus="vs">
										   		    <th>
										   		      ${supplier.supplierName }
										   		    </th>
										   		  </c:forEach>
										   		</thead>
										 	            <h5>技术</h5>
										 	            <c:forEach items="${extension.firstAuditList }" var="first" varStatus="vs">
										 	              <c:if test="${first.kind eq '商务' }">
													      	<tr>
													      	  <td>${first.name }</td>
													      	  <c:forEach items="${extension.supplierList }" var="supplier" varStatus="vs">
										   		                <td align="center">
										   		                  <input type="radio" name="" value="0">合格
										   		                  <input type="radio" name="" value="1">不合格
										   		                </td>
										   		              </c:forEach>
													      	</tr>
										 	              </c:if>
										 	            </c:forEach>
										   </table>
													      	<input type="button" onclick=""  value="分配" class="btn btn-windows add"><br/>
									   </form>
							   </div> 
						</div>
                      </div>
  </body>
</html>
