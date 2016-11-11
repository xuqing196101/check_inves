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
    <base href="${pageContext.request.contextPath}/">
    
    <title>项目评分</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="${pageContext.request.contextPath}/public/ZHH/css/bootstrap.min.css" media="screen" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/public/ZHH/css/common.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/style.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/line-icons.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/app.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/application.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/header-v4.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/header-v5.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/footer-v2.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/img-hover.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/page_job.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/shop.style.css" media="screen" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/public/ZHH/css/brand-buttons.css" media="screen" rel="stylesheet" type="text/css">
    <script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/public/ZHH/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/layer/extend/layer.ext.js"></script>
	<script src="${pageContext.request.contextPath}/public/laypage-v1.3/laypage/laypage.js"></script>
	<link href="${pageContext.request.contextPath}/public/layer/skin/layer.css" media="screen" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath}/public/layer/skin/layer.ext.css" media="screen" rel="stylesheet" type="text/css">
<script type="text/javascript">
	//查看理由
   function reason(firstAuditId,supplierId){
	   var projectId="${extension.projectId }";
	   var packageId="${extension.packageId }";
	   //查找该数据的理由
	   var reason;
	   $.ajax({
	    	url:'${pageContext.request.contextPath}/reviewFirstAudit/getReason.do',
	    	data:{'projectId':projectId,'packageId':packageId,'firstAuditId':firstAuditId,'supplierId':supplierId},
	    	type:'post',
	    	success:function(obj){
	    		reason=obj.rejectReason;
	    		if(reason!=null){
	    		layer.open({
	    			   type: 1,
	    			   skin: 'layui-layer-rim', //加上边框
	    			   area: ['420px', '240px'], //宽高
	    			   shade: false,
	    			   shift: 3,
	    			   title: '理由', //不显示标题
	    			   content: reason, //捕获的元素
	    			 });
	    		}else{
	    			 layer.msg('没有理由！');
	    		}
	    	},
	    	error:function(obj){}
	    	
	    });
	   
   }

  //不合格的弹框
   function isPass(obj){
	  layer.prompt({title: '请填写理由！', formType: 2,cancel:function(){$(obj).attr("checked",false);}}, function(text){
	    layer.msg('您的理由为：'+ text);
	    var value = $(obj).val();
	    var ids = new Array();
	    var ids= value.split(",");
	    var projectId="${extension.projectId }";
	    var packageId="${extension.packageId }";
	    $.ajax({
	    	url:'${pageContext.request.contextPath}/reviewFirstAudit/add.do',
	    	data:{'projectId':projectId,'packageId':packageId,'firstAuditId':ids[0],'supplierId':ids[1],'isPass':ids[2],'rejectReason':text},
	    	type:'post',
	    	success:function(obj){
	    		layer.msg('审核成功');
	    	},
	    	error:function(){}
	    	
	    });
	    
	  });
	}
  //合格
  function pass(obj){
	  var value = $(obj).val();
	    var ids = new Array();
	    var ids= value.split(",");
	    var projectId="${extension.projectId }";
	    var packageId="${extension.packageId }";
	    $.ajax({
	    	url:'${pageContext.request.contextPath}/reviewFirstAudit/add.do',
	    	data:{'projectId':projectId,'packageId':packageId,'firstAuditId':ids[0],'supplierId':ids[1],'isPass':ids[2]},
	    	type:'post',
	    	success:function(obj){
	    		layer.msg('审核成功');
	    	},
	    	error:function(){}
	    	
	    });
  }
  //全部合格
  function addAll(obj){
	  var projectId="${extension.projectId }";
	  var packageId="${extension.packageId }";
	  //获取供应商id 和状态
	  var value = $(obj).val();
	  var ids = new Array();
	  var ids= value.split(",");
	  $.ajax({
	    	url:'${pageContext.request.contextPath}/reviewFirstAudit/addAll.do',
	    	data:{'projectId':projectId,'packageId':packageId,'supplierId':ids[0],'flag':ids[1]},
	    	type:'post',
	    	success:function(obj){
	    		window.location.href="${pageContext.request.contextPath}/reviewFirstAudit/toAudit.html?projectId="+projectId+"&packageId="+packageId;
	    	},
	    	error:function(){}
	    	
	    });
  }
  //全部不合格
  function addNotAll(obj){
	  var projectId="${extension.projectId }";
	  var packageId="${extension.packageId }";
	  //获取供应商id 和状态
	  var value = $(obj).val();
	  var ids = new Array();
	  var ids= value.split(",");
	  layer.prompt({title: '请填写理由！', formType: 2,cancel:function(){$(obj).attr("checked",false);}}, function(text){
	  $.ajax({
	    	url:'${pageContext.request.contextPath}/reviewFirstAudit/addAll.do',
	    	data:{'projectId':projectId,'packageId':packageId,'supplierId':ids[0],'flag':ids[1],'rejectReason':text},
	    	type:'post',
	    	success:function(obj){
	    		window.location.href="${pageContext.request.contextPath}/reviewFirstAudit/toAudit.html?projectId="+projectId+"&packageId="+packageId;
	    	},
	    	error:function(){}
	    	
	    });
	  });
  }
  function submit1(){
	  var ll = $("#table2 tr").length;
	  var aaa = ll-1;
	  var flag = 0;
	  //获取table
	  $("#table2").each(function(i,o){ 
		  //获取该table中的非最后一个tr
		  var w2 = $(o).find('tr:not(:last)');
		  $(w2).each(function(a,b){
			  //获取每行的非第一个td
			  var td = $(b).find('td:not(:first)');
			  //循环td
			  $(td).each(function(c,d){
				  //获取td下的第一个元素，就是第一个单选按钮
				  var abc = $(this).find(":first").is(":checked");
				  //获取该td下的后一个单选按钮
				  var bcd = $(this).find(":first").next().is(":checked");
				 // alert($(this).find(":first").val());
				  //alert($(this).find(":first").next().val());	
				  //如果选中状态都是false 证明该项没有审核
				  if(abc==false && bcd == false){
					 // alert("行索引"+a);
					  //alert("列索引"+c);
					  flag=1;
				  }
			  });
		  });
	  
	  });
	  var projectId = "${extension.projectId }";
	  var packageId = "${extension.packageId }";
	 if(flag==1){
		 layer.msg('还有未审核的数据，请完善！');
		 return ;
	 }else{
		 <%-- window.location.href="${pageContext.request.contextPath}/expert/toFirstAudit.html?projectId="+projectId+"&packageId="+packageId; --%>
		 window.location.href="${pageContext.request.contextPath}/expert/saveProgress.html?projectId="+projectId+"&packageId="+packageId;
	 }
  }
  </script>
  </head>
  
  <body>
  
						 <div class="tab-content clear step_cont">
						 <div class=class="col-md-12 tab-pane active"  id="tab-1">
						 	   <div class="container clear margin-top-30" id="package">
						 	       <table>
						 	         <thead>
						 	            <th><h2>项目名称：</h2></th><th><h2>${extension.projectName }（${extension.packageName }）&nbsp;&nbsp;&nbsp;&nbsp;</h2></th>
						 	            
						 	            <th><h2>编号：</h2></th><th><h2>${extension.projectCode }</h2></th>
						 	            <tr>
						 	              <th></th>
						 	            </tr>
						 	         </thead>
						 	       </table>
									   <form action="" method="post" >
									   <!--包id  -->
									   <input type="hidden" id="packageId" name="packageId" value=""/>
								   	   <input type="hidden" name="projectId" id="projectId" value="${extension.projectId }">
								   	   <input type="hidden" name="packageId" id="packageId" value="${extension.packageId }">
										   <table class="table table-bordered table-condensed mt5" id="table2">
										   		<thead>
										   		  <tr>
										   		  <th></th>
										   		  <th></th>
										   		  <th></th>
										   		  <c:forEach items="${extension.supplierList }" var="supplier" varStatus="vs">
										   		    <th colspan="5">
										   		      ${supplier.suppliers.supplierName }
										   		    </th>
										   		  </c:forEach>
										   		   </tr>
										   		   <tr>
										   		     <th>序号</th>
										   		     <th>评审项目</th>
										   		     <th>计分模型</th>
										   		      <c:forEach items="${extension.supplierList }" var="supplier" varStatus="vs">
										   		        <th>标准分值</th>
										   		        <th>投标人填写</th>
										   		        <th>投标文件页码</th>
										   		        <th>评委填写</th>
										   		        <th>系统生成得分</th>
										   		      </c:forEach>
										   		   </tr>
										   		</thead>
										 	      
										 	          <c:forEach items="${extension.firstAuditList }" var="audit" varStatus="vs">
										 	            <tr align="center">
										 	           <td>${vs.count }</td>
										 	           <td>${audit.name }</td>
										 	         
										 	           <td>按项加减分</td>
										 	           <c:forEach items="${extension.supplierList }" var="supplier" varStatus="vs">
										 	             <td>5</td>
										 	             <td>200</td>
										 	             <td>20</td>
										 	             <td><input type="text"  style="width: 50px;"/> </td>
										 	             <td><input type="text" readonly="readonly"  style="width: 50px;"/></td>
										 	           </c:forEach>
												       </tr> 
												    </c:forEach>
										   </table>
													      	<input type="button" onclick="submit1();"  value="提交" class="btn btn-windows git">
													      	<input class="btn btn-windows back" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'"><br/>
									   </form>
							   </div> 
						</div>
                      </div>
  </body>
  
</html>
