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
    
    
    <title>采购需求管理</title>  
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	


<link href="<%=basePath%>public/ZHH/css/common.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/bootstrap.min.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/style.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/line-icons.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/app.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/application.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/header-v4.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/header-v5.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/brand-buttons.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/footer-v2.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/img-hover.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/page_job.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/shop.style.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/purchase/css/purchase.css" media="screen" rel="stylesheet" type="text/css" >

<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery_ujs.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>public/layer/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/public/layer/extend/layer.ext.js"></script>
<script src="<%=basePath%>public/laypage-v1.3/laypage/laypage.js"></script>

 
  <script type="text/javascript">


    

	
	function closed(){
		var id  = $('input[name="chkItem"]:checked').val(); 
		var planNo=parent.ids;
		var index = parent.layer.getFrameIndex(window.name); 

		if(id==""||id==null){
			layer.alert("请选择要汇总的计划",{offset: ['100px', '100px'], shade:0.01});
		}else{
			$("#id").val(id);
			$("#planNo").val(planNo);

			$.ajax({
			url: "${pageContext.request.contextPath}/collect/update.html",
			type: "post",
			data:$("#collected_form").serialize(),
			success: function(result) {
				parent.location.reload(); // 父页面刷新
				parent.layer.close(index);
			
		
			},
			error: function(message){
				layer.msg("汇总失败",{offset: ['222px', '390px']});
				parent.layer.close(index);
			}
			
			
		});
		
		}
			
 	
		 
		 
			
		 
	}
	
 	function cancel(){
 		 var index = parent.layer.getFrameIndex(window.name); 
 		 
		 parent.layer.close(index);  
 	}
	
 	function ss(){
 		
 	}
  </script>
  </head>
  
  <body>

<!-- 录入采购计划开始-->
<!--  <div class="container">
   <div class="headline-v2">
      <h2>查询条件</h2>
   </div> -->
<!-- 项目戳开始 -->
<%--   <div class="container clear margin-top-30">
    <form id="add_form" action="<%=basePath%>accept/list.html" method="post" >
   <h2 class="padding-10 border1">

	 <ul class="demand_list" >
	   <li class="fl"><label class="fl">需求计划名称：</label><span><input type="text" name="planName" value=""/></span></li>
	   	 <input class="btn padding-left-10 padding-right-10 btn_back"   type="submit" name="" value="查询" /> 
	 </ul>

	
   </h2>
   </form>
  </div> --%>
   <div class="headline-v2 fl">
      <h2>需求计划列表
	  </h2>
   </div> 
  <!--   <span class="fr option_btn margin-top-10">
		<button class="btn padding-left-10 padding-right-10 btn_back" onclick="sub()">受理</button>
	  </span> -->
   <div class="container clear margin-top-30">
        <table class="table table-bordered table-condensed mt5">
		<thead>
		<tr>
		  <th class="info w30"></th>
		  <th class="info w50">序号</th>
		  <th class="info">下达状态</th>
		  <th class="info">采购计划名称</th>
	<!-- 	  <th class="info">编报人</th>
		  <th class="info">提交日期</th>
		  <th class="info">预算总金额</th>
		  <th class="info">状态</th> -->
		</tr>
		</thead>
		<c:forEach items="${info.list}" var="obj" varStatus="vs">
			<tr style="cursor: pointer;">
			  <td class="tc w30"><input type="radio" value="${obj.id }" name="chkItem"></td>
			  <td class="tc w50"   >${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
			    <td class="tc"  >
			    <c:if test="${obj.status ==1}">
			    	未下达
			    </c:if>
			      <c:if test="${obj.status ==2}">
			    	已下达
			    </c:if>
			    
			    </td>
			    <td class="tc"  >${obj.fileName }</td>
			    
			</tr>
	 
		 </c:forEach>
		 

      </table>
      
      <div id="pagediv" align="right"></div>
   </div>
<!--  </div> -->


 <button class="btn padding-left-10 padding-right-10 btn_back goods" style="margin-bottom: 30px" onclick="closed()" >确定</button>
      		<button class="btn padding-left-10 padding-right-10 btn_back goods" style="margin-bottom: 30px" onclick="cancel()" >取消</button>
 
	 </body>
	<form id="collected_form" action="<%=basePath%>collect/add.html" method="post" style="margin-top: 20px;display: none;">
	 <input type="hidden" value="" name="id" id="id">
	 <input type="hidden" value=""  name="planNo" id="planNo">
	 </form>
	 
</html>
