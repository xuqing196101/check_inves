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
    
    <title>各包分配专家</title>
    
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
    		layer.msg("关联成功",{offset: ['222px', '390px']});
    	}
    })
    
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
	       var parent = obj.parentNode; 
	       while(parent .tagName == "form")
	       {
	           parent = parent .parentNode;
	           break;
	       }
	       if(count>0){
	    	   var packageId=	$("input[name='packageId']").val();
	    	   $("#packageIds").val(packageId);
	    	   parent.submit();
	    	   
	       }else{
	    	   layer.alert("请选择一名专家",{offset: ['222px', '390px'],shade:0.01});
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
							   <a  href="#tab-3" data-toggle="tab">03、评标细则</a>
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
						 	<h1 class="f16 count_flow"><i>01</i>各包分配专家</h1>
						 	   <div class="container clear margin-top-30" id="package">
						 	   
							   <h5>01、项目分包信息</h5>
								   <c:forEach items="${packageList }" var="pack" varStatus="p">
								   		<span>包名:<span>${pack.name }</span>
								   		</span>
								   		
								   		<table class="table table-bordered table-condensed mt5">
							        	<thead>
							        		<tr class="info">
							          			<th class="w50">序号</th>
							         			<th>需求部门</th>
										        <th>物资名称</th>
										        <th>规格型号</th>
										        <th>质量技术标准</th>
										        <th>计量单位</th>
										        <th>采购数量</th>
										        <th>单价（元）</th>
										        <th>预算金额（万元）</th>
										        <th>交货期限</th>
										        <th>采购方式建议</th>
										        <th>供应商名称</th>
										        <th>是否申请办理免税</th>
											    <th>物资用途（进口）</th>
											    <th>使用单位（进口）</th>
							        		</tr>
							        	</thead>
							          <c:forEach items="${pack.projectDetails}" var="obj">
							            <tr class="tc">
								            <td class="w50">${obj.serialNumber }</td>
								            <td>${obj.department}</td>
								            <td>${obj.goodsName}</td>
								            <td>${obj.stand}</td>
								            <td>${obj.qualitStand}</td>
								            <td>${obj.item}</td>
								            <td>${obj.purchaseCount}</td>
								            <td>${obj.price}</td>
								            <td>${obj.budget}</td>
								            <td>${obj.deliverDate}</td>
								            <td>${obj.purchaseType}</td>
								            <td>${obj.supplier}</td>
								            <td>${obj.isFreeTax}</td>
									        <td>${obj.goodsUse}</td>
									        <td>${obj.useUnit}</td>
							            </tr>
							         </c:forEach> 
							      </table>
									       <table class="table table-bordered table-condensed mt5">
								 	            <h5>02、唱标信息</h5>
											    <thead>
											      <tr>
											      	<th class="info w50">序号</th>
											        <th>供应商名称</th>
											        <th>联系人</th>
											        <th>联系电话</th>
											        <th>报价</th>
											      </tr>
											     </thead>
											      <c:forEach items="${list }" var="l" varStatus="vs">
												      <thead>
												       <tr>
												        <td class="tc w30"> </td>
												        <td align="center">${l.name } </td>
												        <td align="center">${l.kind }</td>
												        <td align="center">${l.creater }</td>
												        <td align="center">${l.creater }</td>
												        <td align="center">${l.creater }</td>
												      </tr>
												      </thead>
										      	  </c:forEach>
								   		  </table>
								   </c:forEach>
								   <c:forEach items="${packageList }" var="pack" varStatus="p">
									   <form action="<%=basePath%>packageExpert/toPackageExpert.html">
									   <!--包id  -->
									   
									   <input type="text" id="packageId" name="packageId" value="${pack.id }"/>
								   	   <input type="text" name="projectId" value="${project.id}">
								   	   <input type="text" name="packageIds" id="packageIds">
										   <table class="table table-bordered table-condensed mt5">
										 	            <h5>03、各包分配评委</h5>
													      <c:forEach items="${packageList }" var="pack" varStatus="p">
													      	<span>包名:<span>${pack.name }</span>
													      	
													      	<input type="button" onclick="submit1(this);" value="关联" class="btn btn-windows add"><br/>
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
