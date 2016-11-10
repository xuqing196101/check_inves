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
    
    <title>各包分配专家</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
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
<script src="${pageContext.request.contextPath}/public/laypage-v1.3/laypage/laypage.js"></script>
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
	 /** 全选全不选 */
	    function selectAll(obj){
	    	var table = obj.parentNode.parentNode.parentNode.parentNode;
	    	var checkbox = $(table).find("input[type='checkbox']");
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
	 //符合汇总
		function gather(obj){
		    var table = obj.parentNode.parentNode.parentNode.parentNode;
		    var checkbox = $(table).find("input[name='chkItem']");
			var id; 
			 var count = 0;
			$(checkbox).each(function(){ 
				
				if($(this).is(":checked")){
					id=$(this).val();
					count++;
				}
			}); 
			if(count==1){
				var index = layer.confirm('确定汇总吗?', {icon: 3, title:'提示'}, function(index){
				var value = id.split(",");
				var projectId = "${project.id}";
				$.ajax({
					url:"${pageContext.request.contextPath}/packageExpert/gather.html?projectId="+projectId+"&expertId="+value[0]+"&packageId="+value[1],
					success:function(){
						layer.msg("已汇总");
					},
					error:function(){
						
					}
				});
				  layer.close(index);
				});
			}else if(count>1){
				layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
			}else{
				layer.alert("请选择一条",{offset: ['222px', '390px'], shade:0.01});
			}
			
		}
		 //退回
		function isBack(obj){
		    var table = obj.parentNode.parentNode.parentNode.parentNode;
		    var checkbox = $(table).find("input[name='chkItem']");
			var id; 
			 var count = 0;
			$(checkbox).each(function(){ 
				
				if($(this).is(":checked")){
					id=$(this).val();
					count++;
				}
			}); 
			if(count==1){
				var index = layer.confirm('确定退回吗?', {icon: 3, title:'提示'}, function(index){
				var value = id.split(",");
				var projectId = "${project.id}";
				$.ajax({
					url:"${pageContext.request.contextPath}/packageExpert/isBack.html?projectId="+projectId+"&expertId="+value[0]+"&packageId="+value[1],
					success:function(){
						layer.msg("已退回");
						setTimeout(function(){  //使用  setTimeout（）方法设定定时2000毫秒
							window.location.reload();//页面刷新
							},1000);
					},
					error:function(){
						
					}
				});
				layer.close(index);
				});
			}else if(count>1){
				layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
			}else{
				layer.alert("请选择一条",{offset: ['222px', '390px'], shade:0.01});
			}
			
		}
		
		 /** 全选全不选 */
		 function selectAllExp(){
		      var checklist = document.getElementsByName ("chkItemExp");
		      var checkAll = document.getElementById("checkAllExp");
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
		 
		 /** 单选 */
		 function checkExp(){
		      var count=0;
		      var checklist = document.getElementsByName ("chkItem");
		      var checkAll = document.getElementById("checkAll");
		      for(var i=0;i<checklist.length;i++){
		            if(checklist[i].checked == false){
		                checkAll.checked = false;
		                break;
		            }
		            for(var j=0;j<checklist.length;j++){
		                  if(checklist[j].checked == true){
		                        checkAll.checked = true;
		                        count++;
		                    }
		              }
		        }
		 }
		 /**重置密码*/
		 function resetPwd(){
		 	   var id=[]; 
		        $('input[name="chkItemExp"]:checked').each(function(){ 
		            id.push($(this).val());
		        }); 
		        if(id.length==1){
		     	   $.ajax({
		                type: "GET",
		                url: "${pageContext.request.contextPath}//ExpExtract/resetPwd.do?eid"+id,
		                dataType: "json",
		                success: function(data){
		             	   if("sccuess"==data){
		                        layer.alert("重置成功！默认密码：123456",{offset: ['222px', '390px'], shade:0.01});
		                           }else{
		                         	   layer.alert("重置失败！请尝试重新重置",{offset: ['222px', '390px'], shade:0.01});
		                           }
		                         }
		            });
		        }else if(id.length>1){
		            layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
		        }else{
		            layer.alert("请选择需要重置密码的专家",{offset: ['222px', '390px'], shade:0.01});
		        }
		 }
		 function addexp(){
		 	  layer.open({
		           type: 2, //page层
		         area: ['70%', '30%'],
		           title: '添加临时专家',
		           closeBtn: 1,
		           shade:0.01, //遮罩透明度
		           shadeClose: true,
		           offset: '30px',
		           move:false,
		           content: '${pageContext.request.contextPath}/ExpExtract/showTemporaryExpert.html?projectId=${project.id}'
		         });
		 }
		 </script>
		 </head>
		 
		 <body>
		 	<div class="tab-content clear step_cont">
		 		<div class=class= "col-md-12 tab-pane active"  id="tab-1"
		 			style="display: block;">
		 
		 			<div class="container clear margin-top-30" id="package">
		 				<table class="table table-bordered table-condensed mt5">
		 					<h3>01、专家名单</h3>
		 					<div align="right">
		 						<button class="btn btn-windows back" onclick="resetPwd();" type="button">重置密码</button>
		 						<button class="btn btn-windows back" onclick="addexp();" type="button">添加临时供应商</button>
		 					</div>
		 					<thead>
		 						<tr>
		 							<th class="info w30"><input type="checkbox" id="checkAllExp"
		 								onclick="selectAllExp()" alt=""></th>
		 							<th class="info w50">序号</th>
		 							<th>专家姓名</th>
		 							<th>专家类型</th>
		 							<th>证件号</th>
		 							<th>现任职务</th>
		 							<th>联系地址</th>
		 							<th>联系电话</th>
		 						</tr>
		 					</thead>
		 					<tbody id="tbody">
		 					<c:forEach items="${expertList }" var="expert" varStatus="vs">
		 							<tr>
		 								<td class="tc w30"><input type="checkbox"
		 									value="${expert.expert.id}" name="chkItemExp" onclick="checkExp()"
		 									alt=""></td>
		 								<td class="tc w30">${vs.count }</td>
		 								<td align="center">${expert.expert.relName }</td>
		 								<c:if test="${expert.expert.expertsTypeId eq '1' }">
		 									<td align="center">技术</td>
		 								</c:if>
		 								<c:if test="${expert.expert.expertsTypeId eq '2' }">
		 								<td align="center">法律</td>
		 								</c:if>
		 								<c:if test="${expert.expert.expertsTypeId eq '3' }">
		 									<td align="center">商务</td>
		 								</c:if>
		 								<td align="center">${expert.expert.idNumber }</td>
		 								<td align="center">${expert.expert.atDuty }</td>
		 								<td align="center">${expert.expert.unitAddress }</td>
		 								<td align="center">${expert.expert.mobile }</td>
		 							</tr>
		 					</c:forEach>
		 					</tbody>
		 				</table>
										 	            <h3>02、各包分配评委</h3>
								   <c:forEach items="${packageList }" var="pack" varStatus="p">
									   <form action="${pageContext.request.contextPath}/packageExpert/relate.html" method="post" >
									   <!--包id  -->
									   <input type="hidden" id="packageId" name="packageId" value="${pack.id }"/>
								   	   <input type="hidden" id="projectId" name="projectId" value="${project.id}">
								   	   <input type="hidden" name="packageIds" id="packageIds">
								   	   <div style="height:110px; overflow:auto;">
										   <table >
										 	            
													      	<tr >
													      	  <td style="white-space: nowrap;overflow: hidden;word-spacing: keep-all;">
													      	&nbsp;&nbsp;<span style=" font-size: 18;">包名:${pack.name }</span>&nbsp;&nbsp;
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
													      	<input type="button" onclick="submit1(this);"  value="分配" class="btn btn-windows add">
													      	  </td>
													      	</tr>
										   </table>
									     </div>
									   </form>
								   </c:forEach>
								    <table class="table table-bordered table-condensed mt5">
								 	            <h3>03、供应商报价表</h3>
											    <thead>
											      <tr>
											      	<th class="info w50">序号</th>
											        <th>供应商名称</th>
											        <th>联系人</th>
											        <th>联系电话</th>
											        <th>报价</th>
											      </tr>
											     </thead>
											      <c:forEach items="${supplierList }" var="supplier" varStatus="vs">
												      <thead>
												       <tr>
												        <td class="tc w30">${vs.count } </td>
												        <td align="center">${supplier.suppliers.supplierName } </td>
												        <td align="center">${supplier.suppliers.contactName }</td>
												        <td align="center">${supplier.suppliers.contactTelephone }</td>
												        <td align="center"></td>
												      </tr>
												      </thead>
										      	  </c:forEach>
								   		  </table>
								 	            <h3>04、评审进度</h3>
								   	 <c:forEach items="${packageList }" var="pack" varStatus="vs">
								   		   <table class="table table-bordered table-condensed mt5">
											    <thead>
											      <tr>
											      	<th rowspan="2">${pack.name }</th>
											        <th>状态</th>
											        <th>总进度</th>
											        <th>符合性审查进度</th>
											        <th>评分进度</th>
											      </tr>
											      <tr>
											            <td align="center">&nbsp;
											            <c:if test="${reviewProgressList == null || reviewProgressList.size()<1 }">未评审</c:if>
											            <c:forEach items="${reviewProgressList }" var="progress">
												           <c:if test="${progress.packageId eq pack.id }">
												            <c:choose>
												              <c:when test="${progress.auditStatus != null }">
												               ${progress.auditStatus}
												              </c:when>
												              <c:otherwise>
												                                     未评审
												              </c:otherwise>
												            </c:choose>
											               </c:if>
											              </c:forEach>
											            </td>
												        <td align="center">
												        <c:if test="${reviewProgressList == null || reviewProgressList.size()<1 }">0%</c:if>
												          <c:forEach items="${reviewProgressList }" var="progress">
												          <c:choose>
												           <c:when test="${progress.packageId eq pack.id }">
												            <c:choose>
												              <c:when test="${progress.totalProgress != null }">
												               ${progress.totalProgress*100}%
												              </c:when>
												              <c:otherwise>
												              0%
												              </c:otherwise>
												            </c:choose>
											               </c:when>
											               <c:otherwise> </c:otherwise>
											               </c:choose>
											              </c:forEach>
												        </td>
												        <td align="center">
												        <c:if test="${reviewProgressList == null || reviewProgressList.size()<1 }">0%</c:if>
												         <c:forEach items="${reviewProgressList }" var="progress">
												          <c:choose>
												           <c:when test="${progress.packageId eq pack.id }">
												            <c:choose>
												              <c:when test="${progress.firstAuditProgress != null }">
												                ${progress.firstAuditProgress*100}%
												              </c:when>
												              <c:otherwise>
												                0%
												              </c:otherwise>
												            </c:choose>
											               </c:when>
											               <c:otherwise> </c:otherwise>
											               </c:choose>
											              </c:forEach>
												        </td>
												        <td align="center">
												        <c:if test="${reviewProgressList == null || reviewProgressList.size()<1 }">0%</c:if>
												        <c:forEach items="${reviewProgressList }" var="progress">
												          <c:choose>
												           <c:when test="${progress.packageId eq pack.id }">
												            <c:choose>
												              <c:when test="${progress.scoreProgress != null }">
												                ${progress.scoreProgress*100}%
												              </c:when>
												              <c:otherwise>
												                0%
												              </c:otherwise>
												            </c:choose>
											               </c:when>
											               <c:otherwise> </c:otherwise>
											               </c:choose>
											              </c:forEach>
												        </td>
											      </tr>
												       <tr>
												        <td align="center">中标供应商 </td>
												        <td align="center" colspan="4">aa</td>
												      </tr>
												      </thead>
								   		  </table>
									</c:forEach>
									   <h3>05、符合性审查</h3>
								   	 <c:forEach items="${packageList }" var="pack" varStatus="vs">
								   		   <table class="table table-bordered table-condensed mt5">
								   		   <thead>
									   		   <tr align="right">
									   		   		<td align="right" colspan="${3+supplierList.size() }">
									   		   		<button  class="btn btn-windows git" onclick="gather(this);" type="button">符合汇总</button>
									   	 	         <button class="btn btn-windows back" onclick="isBack(this);" type="button">退回重审</button>
									   		   		</td>
									   		   </tr>
											    
											      <tr>
											      	<th colspan="${3+supplierList.size() }">${pack.name }初审情况</th>
											      </tr>
											      <tr>
											        <th class="info w30"><input value="" name="checkAll" id="checkAll" type="checkbox" onclick="selectAll(this)" /></th>
											        <th>评委</th>
											        <th>符合性审查完成</th>
											        <c:forEach items="${supplierList }" var="supplier" varStatus="vs">
											        <th>${supplier.suppliers.supplierName }</th>
											        </c:forEach>
											      </tr>
											      </thead>
											      <thead>
											       <c:forEach items="${packExpertExtList }" var="ext" varStatus="vs">
											        <c:if test="${ext.packageId eq pack.id }">
												       <tr>
												        <td class="tc opinter"><input  type="checkbox" name="chkItem" value="${ext.expert.id},${pack.id}" /></td>
												        <td align="center">${ext.expert.relName } </td>
												        <td align="center">${ext.isPass } </td>
												       <c:forEach items="${supplierList }" var="supplier" varStatus="vs">
											        	<td align="center">
											        	<c:if test="${ext.isPass eq '已评审'}">
											        	  <c:forEach items="${supplierExtList }" var="supplierExt">
											        	  	<c:if test="${supplierExt.supplierId eq supplier.suppliers.id && ext.expert.id eq supplierExt.expertId && supplierExt.packageId eq pack.id}">
											        	  	${supplierExt.suppIsPass }
											        	  	</c:if>
											        	  </c:forEach>
											        	 </c:if>
											        	 <c:if test="${ext.isPass eq '未评审'}">未评审 </c:if>
											        	</td>
											        	</c:forEach>
												      </tr>
											        </c:if>
											        
										      	  </c:forEach>
												      <%--  <tr>
												        <td align="center" colspan="3">初审结果 </td>
												        <c:forEach items="${supplierList }" var="supplier" varStatus="vs">
												        <td align="center">
												           <c:forEach items="${supplierExtList }" var="supplierExt">
											        	  	<c:if test="${supplierExt.supplierId eq supplier.suppliers.id && supplierExt.packageId eq pack.id}">
											        	  	${supplierExt.isAudit }
											        	  	</c:if>
											        	  </c:forEach>
												        </td>
												        </c:forEach>
												      </tr> --%>
												      </thead>
								   		  </table>
									</c:forEach>
									
									
									  <h3>06、评分汇总</h3>
								   	 <c:forEach items="${packageList }" var="pack" varStatus="vs">
								   		   <table class="table table-bordered table-condensed mt5">
											    <thead>
											    <tr align="right">
									   		   		<td align="right" colspan="${3+supplierList.size() }">
									   		   		<button class="btn btn-windows back" type="button">评分汇总</button>
								   	                <button class="btn btn-windows input" onclick="window.print();" type="button">打印评分信息</button>
								   	                </td>
									   		   </tr>
											      <tr>
											      	<th colspan="${supplierList.size()+2 }">${pack.name }评分汇总</th>
											      </tr>
											      <tr>
											        <th>评委</th>
											        <th>评分完成</th>
											        <c:forEach items="${supplierList }" var="supplier" varStatus="vs">
											        <th>${supplier.suppliers.supplierName }</th>
											        </c:forEach>
											      </tr>
											      </thead>
											      <thead>
											       <c:forEach items="${packExpertExtList }" var="ext" varStatus="vs">
											         <c:if test="${ext.packageId eq pack.id }">
												       <tr>
												        <td align="center">${ext.expert.relName } </td>
												        <td align="center"> </td>
												        <td align="center"></td>
												        <td align="center"></td>
												        <td align="center"></td>
												      </tr>
												     </c:if>
										      	  </c:forEach>
												       <tr>
												        <td align="center" colspan="2">评分结果 </td>
												        <c:forEach items="${supplierList }" var="supplier" varStatus="vs">
												        <td align="center">aa</td>
												        </c:forEach>
												      </tr>
												       <tr>
												        <td align="center" colspan="2">该包报价 </td>
												        <c:forEach items="${supplierList }" var="supplier" varStatus="vs">
												        <td align="center">aa</td>
												        </c:forEach>
												      </tr>
												      </thead>
								   		  </table>
									</c:forEach>
							   </div> 
							<div class="container clear margin-top-30" id="package">
						 </div>	
						</div>
                      </div>
  </body>
</html>
