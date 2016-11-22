<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:include page="/WEB-INF/view/common.jsp"></jsp:include>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="${pageContext.request.contextPath}/">
    
    <title>各包分配专家</title>
    <script type="text/javascript">
    var result;
    $(function(){
    	var packageId=	$("input[name='packageId']").val();
    	var flag=$("#flag").val();
    	if(flag=="success"){
    		layer.msg("分配成功。",{offset: ['285px', '550px']});
    	}else if(flag=="error"){
    		layer.alert("错误操作！请重新选择！",{offset: ['222px', '390px'],shade:0.01});
    	}
    	$("#flag").val("");
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
					success:function(data){
						layer.msg(data);
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
					success:function(data){
						if(data=='0'){
							layer.msg("不能退回！");
						}else{
							layer.msg("已退回！");
							setTimeout(function(){  //使用  setTimeout（）方法设定定时2000毫秒
								window.location.reload();//页面刷新
								},1000);
						}
						
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
		         area: ['80%', '50%'],
		           title: '添加临时专家',
		           closeBtn: 1,
		           shade:0.01, //遮罩透明度
		           shadeClose: true,
		           offset: '30px',
		           move:false,
		           content: '${pageContext.request.contextPath}/ExpExtract/showTemporaryExpert.html?projectId=${project.id}'
		         });
		 }
		 //查看供应商报价
		 function supplierView(supplierId){
				    var projectId=$("#projectId").val();
					location.href="${pageContext.request.contextPath}/packageExpert/supplierQuote.html?projectId="+projectId+"&supplierId="+supplierId;
		 }
		
		 //评分确认或退回
		 function querenOrTuiHUi(obj,packageId,supplierId,scoreModelId,flag){
				 //得到点击坐标。
				    var x,y;  
				    oRect = obj.getBoundingClientRect();  
					 x=oRect.left-400;  
					 y=oRect.top-100;  
					 var projectId=$("#projectId").val();
					 $.ajax({
						 url:'${pageContext.request.contextPath}/packageExpert/isBackScore.html',
						 data:{'packageId':packageId,'projectId':projectId,'supplierId':supplierId,'scoreModelId':scoreModelId,'flag':flag},
						 success:function(data){
							 if(data == "tuihui"){
								 layer.alert("不能退回！",{offset: [y, x], shade:0.01});
							 }else if(data=="success"){
								 layer.alert("确认成功！",{offset: [y, x], shade:0.01});
							 }else if(data=="tuihuisuccess"){
								 layer.alert("退回成功！",{offset: [y, x], shade:0.01});
							 }else {
								 layer.alert("不能确认！",{offset: [y, x], shade:0.01});
							 }
						 },
						 error:function(data){
							 
						 }
					 });
		 }
 </script>
</head>
<body>
<input id="flag" type="hidden" value="${flag}">
<div class="tab-content clear step_cont">
 <div class= "col-md-12 tab-pane active"  id="tab-1" style="display: block;">
   <div class="" id="package">
	<table class="table table-bordered table-condensed table-hover table-striped">
		<h1 class="f16 count_flow"><i>01</i>专家名单</h1>
		<div align="right">
			<button class="btn btn-windows back" onclick="resetPwd();" type="button">重置密码</button>
			<button class="btn btn-windows add" onclick="addexp();" type="button">添加临时专家</button>
		</div>
		<thead>
			<tr>
				<th class="info w30"><input type="checkbox" id="checkAllExp"
					onclick="selectAllExp()" alt=""></th>
				<th class="info w50">序号</th>
				<th class="info">专家姓名</th>
				<th class="info">专家类型</th>
				<th class="info">证件号</th>
				<th class="info">现任职务</th>
				<th class="info">联系地址</th>
				<th class="info">联系电话</th>
			</tr>
		</thead>
		<tbody id="tbody">
		<c:forEach items="${expertList }" var="expert" varStatus="vs">
			<tr>
				<td class="tc w30"><input type="checkbox"
					value="${expert.expert.id}" name="chkItemExp" onclick="checkExp()"></td>
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
   <h1 class="f16 count_flow"><i>02</i>各包分配专家</h1>
   <c:forEach items="${packageList }" var="pack" varStatus="p">
	   <form action="${pageContext.request.contextPath}/packageExpert/relate.html" method="post" >
	   <!--包id  -->
	   <input type="hidden" id="packageId" name="packageId" value="${pack.id }"/>
   	   <input type="hidden" id="projectId" name="projectId" value="${project.id}">
   	   <input type="hidden" name="packageIds" id="packageIds">
   	   <div style="height:110px; overflow:auto;">
		   <table class="table table-bordered table-condensed table-hover table-striped">
	      	<tr >
	      	  <th style="white-space: nowrap;overflow: hidden;word-spacing: keep-all;">
	      	<span style=" font-size: 18;">包名:${pack.name }</span><br/>
	      	<c:forEach items="${expertList }" var="expert" varStatus="vs">
	      	<input type="checkbox" name="chkItem" value="${expert.expert.id }" 
	      		        <c:forEach items="${expertIdList }" var="e" varStatus="p">
		      	    	  <c:if test="${e.expertId==expert.expert.id && e.projectId==project.id && e.packageId==pack.id }">
		      	    	  checked
		      	    	  </c:if>
		      	    	</c:forEach>
	      	>${expert.expert.relName }&nbsp;
	      	</c:forEach>
	      	        组长：<select name="groupId" onchange="selectClick(this);">
	      	    	<option value="0">-请选择-</option>
	      	    	<c:forEach items="${expertList }" var="expert" varStatus="vs">
	      	    	<option value="${expert.expert.id }"
		      	    	<c:forEach items="${expertIdList }" var="e" varStatus="p">
		      	    	  <c:if test="${e.isGroupLeader==1 && e.expertId==expert.expert.id && e.projectId==project.id && e.packageId==pack.id }">
		      	    	  selected
		      	    	  </c:if>
		      	    	</c:forEach>
	      	    	>${expert.expert.relName }
	      	    	</option>
	      	    	</c:forEach>
	      	      </select>&nbsp;&nbsp;
	      	<input type="button" onclick="submit1(this);"  value="分配" class="btn btn-windows add">
	      	  </th>
	      	</tr>
		   </table>
	     </div>
	   </form>
   </c:forEach>
   <table class="table table-bordered table-condensed table-hover table-striped">
           <h1 class="f16 count_flow"><i>03</i>供应商报价</h1>
		    <thead>
		      <tr>
		      	<th class="info w50">序号</th>
		        <th class="info">供应商名称</th>
		        <th class="info">联系人</th>
		        <th class="info">联系电话</th>
		        <th class="info">报价</th>
		      </tr>
		     </thead>
		      <c:forEach items="${supplierList }" var="supplier" varStatus="vs">
			       <tr>
			        <td class="tc w30">${vs.count } </td>
			        <td align="center">${supplier.suppliers.supplierName } </td>
			        <td align="center">${supplier.suppliers.contactName }</td>
			        <td align="center">${supplier.suppliers.contactTelephone }</td>
			        <td align="center">
			          <input class="btn" type="button" value="查看" onclick="supplierView('${supplier.suppliers.id}')">
			        </td>
			      </tr>
	      	  </c:forEach>
  </table>
      <h1 class="f16 count_flow"><i>04</i>评审进度</h1>
   <c:forEach items="${packageList }" var="pack" varStatus="vs">
 		   <table class="table table-bordered table-condensed table-hover table-striped">
		    <thead>
		      <tr>
		      	<th class="info" rowspan="2">${pack.name }</th>
		        <th class="info">状态</th>
		        <th class="info">总进度</th>
		        <th class="info">符合性审查进度</th>
		        <th class="info">评分进度</th>
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
		    </thead>
  </table>
</c:forEach>
   <h1 class="f16 count_flow"><i>05</i>符合性审查</h1>
   	 <c:forEach items="${packageList }" var="pack" varStatus="vs">
  		   <table class="table table-bordered table-condensed table-hover table-striped">
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
		        <th class="info">评委</th>
		        <th class="info">符合性审查完成</th>
		        <c:forEach items="${supplierList }" var="supplier" varStatus="vs">
		        <th class="info">${supplier.suppliers.supplierName }</th>
		        </c:forEach>
		      </tr>
		      </thead>
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
  		  </table>
	</c:forEach>
	  <h1 class="f16 count_flow"><i>06</i>评分汇总</h1>
									  <!-- 循环包 -->
   	 <c:forEach items="${packageList }" var="pack" varStatus="vs">
   	 <!--循环供应商  -->
   	   <c:forEach items="${supplierList }" var="supplier" varStatus="vs" >
   	   		<h4>供应商名称：${supplier.suppliers.supplierName }</h4>
   		   <table class="table table-bordered table-condensed table-hover table-striped">
	    <thead>
	    <tr align="right">
  		   		<td align="right" colspan="${2+packExpertExtList.size() }">
  		   		   <button class="btn btn-windows back" type="button">评分汇总</button>
 	               <button class="btn btn-windows input" onclick="window.print();" type="button">打印信息</button>
 	            </td>
  		   </tr>
	      <tr>
	      	<th colspan="${packExpertExtList.size()+2 }">${pack.name }评分汇总</th>
	      </tr>
	      <tr>
	        <th class="info">评审项</th>
	        <c:forEach items="${packExpertExtList }" var="ext" varStatus="vs">
	        <th class="info">${ext.expert.relName }</th>
	        </c:forEach>
	        <th class="info">操作</th>
	      </tr>
	      </thead>
	         <c:set var="TOTAL" value="0"></c:set>
	       <c:forEach items="${auditModelListAll }" var="model" varStatus="vs">
	         <c:if test="${model.packageId eq pack.id }">
		       <tr align="center">
		       <td>${model.markTermName }</td>
		        <c:forEach items="${packExpertExtList }" var="ext" varStatus="vs">
	               
	               <td align="center">
	                 <c:forEach items="${expertScoreList }" var="score" varStatus="vs">
	                 	<c:if test="${score.expertId eq ext.expert.id && score.packageId eq pack.id && score.supplierId eq supplier.suppliers.id && score.scoreModelId eq model.scoreModelId }">
	                 	${score.score }
	                 	<c:set var="TOTAL" value="${TOTAL+score.score }"></c:set>
	                 	</c:if>
	                 	
	                 </c:forEach>
	               </td>
	            </c:forEach>
	            <td width="150px">
	                 <input type="button" class="btn" onclick="querenOrTuiHUi(this,'${pack.id}','${supplier.suppliers.id }','${model.scoreModelId }',2)" value="确认">
	                 <input type="button" class="btn" onclick="querenOrTuiHUi(this,'${pack.id}','${supplier.suppliers.id }','${model.scoreModelId }',1)" value="退回">
	            </td>
		      </tr>
		     </c:if>
      	  </c:forEach>
   		  </table>
   		  </c:forEach>
	</c:forEach>
 			</div> 
		</div>
	</div>
  </body>
</html>
