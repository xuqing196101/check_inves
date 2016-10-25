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
    		layer.msg("分配成功。",{offset: ['285px', '550px']});
    	}else if(flag=="error"){
    		layer.alert("错误操作！请重新选择！",{offset: ['222px', '390px'],shade:0.01});
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
		    //获取复选框
			var checkbox = $(obj).prev().prev();
			//获取复选框的name
			var checkboxName = $(checkbox).prop("name");
		    var count = 0;
	  	  var ids = document.getElementsByName(""+checkboxName+"");
	       for(i=0;i<ids.length;i++) {
	     		 if(document.getElementsByName(""+checkboxName+"")[i].checked){
	     		 var id = document.getElementsByName(""+checkboxName+"")[i].value;
	     		 count++;
	      }
	    }
	       
	       //获取当前的form对象
	       var parent = obj.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode; 
	      /*  alert(parent .tagName);
	       while(parent.tagName == "TABLE")
	       {
	           parent = parent .parentNode;
	           alert("aaa");
	           break;
	       } */
	       //获取按钮上面的下拉框对象
	       var select = $(obj).prev();
	       if($(select).val()=="0"){
	    	   layer.alert("请指定一名组长",{offset: ['222px', '390px'],shade:0.01});
	    	   return ;
	       }
	       if(count>0){
	    	   parent.submit();
	    	 // return true;
	       }else{
	    	   layer.alert("请选择一名专家",{offset: ['222px', '390px'],shade:0.01});
	    	   return ;
	       }
	      
	 }
	 //下拉框校验
	 function selectClick(obj){
		 //下拉框选中的值
		var selectValue = $(obj).val();
		 //获取复选框
		var checkbox = $(obj).prev();
		 //获取复选框的name
		var checkboxName = $(checkbox).prop("name");
		 //定义变量
		 var flag;
		 //根据复选框的name 获取选中复选框的value值
		 $('input[name="'+checkboxName+'"]:checked').each(function(){ 
			 //判断下拉框的选的值是否复选框也选中
			 if(selectValue==$(this).val()){
				 flag=1;
			 }
		}); 
		 if(flag!=1){
			 layer.alert("请选择勾选的专家为组长！",{offset: ['222px', '390px'],shade:0.01});
			 $(obj).val("0");
		 }
	 }
	 //点击复选框还原下拉框
	 /* function clearSelect(obj){
		var select =  $(obj).next();
		$(select).val("0");
	 } */
</script>
  </head>
  
  <body>
						 <div class="tab-content clear step_cont" >
						 <div class=class="col-md-12 tab-pane active"  id="tab-1" style="display: block;">
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
								   </c:forEach>
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
										 	            <h5>03、各包分配评委</h5>
								   <c:forEach items="${packageList }" var="pack" varStatus="p">
									   <form action="<%=basePath%>packageExpert/relate.html" method="post" >
									   <!--包id  -->
									   <input type="hidden" id="packageId" name="packageId" value="${pack.id }"/>
								   	   <input type="hidden" name="projectId" value="${project.id}">
								   	   <input type="hidden" name="packageIds" id="packageIds">
								   	   <div style="height:110px; overflow:auto;">
										   <table >
										 	            
													      	<tr >
													      	  <td style="white-space: nowrap;overflow: hidden;word-spacing: keep-all;">
													      	<span style=" font-size: 18;">包名:${pack.name }</span>&nbsp;&nbsp;
													      	<c:forEach items="${expertList }" var="expert" varStatus="vs">
													      	<input type="checkbox" name="chkItem" value="${expert.expert.id }" 
													      		        <c:forEach items="${expertIdList }" var="e" varStatus="p">
														      	    	  <c:if test="${e.expertId==expert.expert.id && e.projectId==project.id && e.packageId==pack.id }">
														      	    	  checked
														      	    	  </c:if>
														      	    	</c:forEach>
													      	>${expert.expert.relName }&nbsp;
													      	</c:forEach>
													      	<!-- <input type="checkbox" name="chkItem" value="222">专家2
													      	<input type="checkbox" name="chkItem" value="333" onchange="clearSelect(this);">专家3 -->
													      	  &nbsp;&nbsp;  组长：<select name="groupId" onchange="selectClick(this);">
													      	    	<option value="0">-请选择-</option>
													      	    	<c:forEach items="${expertList }" var="expert" varStatus="vs">
													      	    	<option value="${expert.expert.id }"
														      	    	<c:forEach items="${expertIdList }" var="e" varStatus="p">
														      	    	  <c:if test="${e.isGroupLeader==1 && e.expertId==expert.expert.id && e.projectId==project.id && e.packageId==pack.id }">
														      	    	  selected
														      	    	  </c:if>
														      	    	</c:forEach>
													      	    	>${expert.expert.relName }</option>
													      	    	</c:forEach>
													      	      </select>&nbsp;&nbsp;
													      	<input type="button" onclick="submit1(this);"  value="分配" class="btn btn-windows add"><br/>
													      	  </td>
													      	</tr>
										   </table>
									     </div>
									   </form>
								   </c:forEach>
							   </div> 
							<div class="container clear margin-top-30" id="package">
						 </div>	
						</div>
                      </div>
  </body>
</html>
