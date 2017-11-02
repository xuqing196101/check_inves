<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:include page="/WEB-INF/view/common.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="${pageContext.request.contextPath}/">
    <title>项目评审</title>
    <%-- <script src="${pageContext.request.contextPath }/public/backend/js/lock_table_head.js"></script> --%>
<script type="text/javascript">
	//查看理由
   function reason(firstAuditId,supplierId,expertId){
	   var projectId="${extension.projectId }";
	   var packageId="${extension.packageId }";
	   //查找该数据的理由
	   var reason;
	   $.ajax({
	    	url:'${pageContext.request.contextPath}/reviewFirstAudit/getReason.do',
	    	data:{'projectId':projectId,'packageId':packageId,'firstAuditId':firstAuditId,'supplierId':supplierId,'expertId':expertId},
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
	    			   offset: '100px',
	    			   title: '理由', //不显示标题
	    			   content: reason, //捕获的元素
	    			 });
	    		}else{
	    			 layer.msg('没有理由！',{offset: '100px'});
	    		}
	    	},
	    	error:function(obj){}
	    	
	    });
	   
   }

  //不合格的弹框
   function isPass(obj,position,index){
	  layer.prompt({title: '请填写理由！',offset: '100px', formType: 2,cancel:function(){$(obj).attr("checked",false);}}, function(text){
	    layer.msg('您的理由为：'+ text);
	    var value = $(obj).val();
	    var ids = new Array();
	    var ids= value.split(",");
	    var projectId="${extension.projectId}";
	    var packageId="${extension.packageId}";
	    $.ajax({
	    	url:'${pageContext.request.contextPath}/reviewFirstAudit/add.do',
	    	data:{'projectId':projectId,'packageId':packageId,'firstAuditId':ids[0],'supplierId':ids[1],'isPass':ids[2],'rejectReason':text},
	    	type:'post',
	    	success:function(obj){
	    		layer.msg('审核成功',{offset: '100px'});
	    		$("#notPassReason_"+position+"_"+index).removeAttr("class");
	    	},
	    	error:function(){}
	    	
	    });
	    
	  });
	}
  //合格
  function pass(obj,position,index){
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
	    		//layer.msg('审核成功');
	    		$("#notPassReason_"+position+"_"+index).attr("class","dnone");
	    	},
	    	error:function(){}
	    });
  }
  //全部合格
  function addAll(obj,supplierId,flag,index){
	  var projectId="${extension.projectId }";
	  var packageId="${extension.packageId }";
	  //获取供应商id 和状态
	  var value = $(obj).val();
	  var ids = new Array();
	  var ids= value.split(",");
	  $.ajax({
	    	url:'${pageContext.request.contextPath}/reviewFirstAudit/addAll.do',
	    	/* data:{'projectId':projectId,'packageId':packageId,'supplierId':ids[0],'flag':ids[1]}, */
	    	data:{'projectId':projectId,'packageId':packageId,'supplierId':supplierId,'flag':flag},
	    	type:'post',
	    	success:function(obj){
	    		/* $("#table").find("tr").each(function(){
	    			 if($(this).children()[index]!='undefined'){
	    				 $($(this).children()[index]).find("input[type='radio']").each(function(){
	    					 if($(this).val().split(",")[2]=="0"){
	    						 $(this).attr("checked","checked");
	    					 }else{
	    						 $(this).removeAttr("checked");
	    					 }
	    				 })
	    			 }
	    			
	    		});
	    		layer.msg('审核成功'); */
	    		window.location.href="${pageContext.request.contextPath}/reviewFirstAudit/toAudit.html?projectId="+projectId+"&packageId="+packageId; 
	    	},
	    	error:function(){}
	    	
	    });
  }
  //全部不合格
  function addNotAll(obj,supplierId,flag,index){
	  var projectId="${extension.projectId }";
	  var packageId="${extension.packageId }";
	  //获取供应商id 和状态
	  var value = $(obj).val();
	  var ids = new Array();
	  var ids= value.split(",");
	  layer.prompt({title: '请填写理由！',offset: '100px', formType: 2,cancel:function(){$(obj).attr("checked",false);}}, function(text){
	  $.ajax({
	    	url:'${pageContext.request.contextPath}/reviewFirstAudit/addAll.do',
	    	/* data:{'projectId':projectId,'packageId':packageId,'supplierId':ids[0],'flag':ids[1],'rejectReason':text}, */
	    	data:{'projectId':projectId,'packageId':packageId,'supplierId':supplierId,'flag':flag,'rejectReason':text},
	    	type:'post',
	    	success:function(obj){
	    		/* $("#table").find("tr").each(function(){
	    			 if($(this).children()[index]!='undefined'){
	    				 $($(this).children()[index]).find("input[type='radio']").each(function(){
	    					 if($(this).val().split(",")[2]=="1"){
	    						 $(this).attr("checked","checked");
	    					 }else{
	    						 $(this).removeAttr("checked");
	    					 }
	    				 })
	    			 }
	    			
	    		});
	    		layer.msg('审核成功'); */
	    		 window.location.href="${pageContext.request.contextPath}/reviewFirstAudit/toAudit.html?projectId="+projectId+"&packageId="+packageId; 
	    	},
	    	error:function(){}
	    	
	    });
	  });
  }
  
  //提交
  function submit1(){
	  var ll = $("#table tr").length;
	  var aaa = ll-1;
	  var flag = 0;
	  //获取table
	  $("#table").each(function(i,o){ 
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
		 layer.msg('还有未审核的数据，请完善！',{offset: '100px'});
		 return ;
	 }else{
		 <%-- window.location.href="${pageContext.request.contextPath}/expert/toFirstAudit.html?projectId="+projectId+"&packageId="+packageId; --%>
		 window.location.href="${pageContext.request.contextPath}/expert/saveProgress.html?projectId="+projectId+"&packageId="+packageId;
	 }
  }
  
  //暂存
  function tempSave(){
  	var projectId = "${extension.projectId}";
	var packageId = "${extension.packageId}";
  	$.ajax({
		url: "${pageContext.request.contextPath}/packageExpert/tempSave.html",
		data: {"projectId": projectId, "packageId": packageId},
		dataType:'json',
		success:function(result){
		   	layer.msg(result.msg,{offset: ['100px']});
        },
        error: function(result){
            layer.msg("暂存失败",{offset: ['100px']});
        }
	});
  }
  </script>
  </head>
  
  <body>
  	<!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   	<li><a href="javascript:void(0);">首页</a></li><li><a href="javascript:void(0);">项目评审</a></li><li><a href="javascript:void(0);">符合性审查</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
    </div>
	<div class="container">
		<div class="container clear" id="package">
			<div class="headline-v2">
		     <h2>资格性和符合性检查</h2>
		    </div>
			<div class="mt10 tc">
		   		<h2>${extension.projectName }（${extension.packageName }）</h2>
		   	</div>
			<form action="" method="post" >
			   <!--包id  -->
			   <input type="hidden" id="packageId" name="packageId" value=""/>
		   	   <input type="hidden" name="projectId" id="projectId" value="${extension.projectId }">
		   	   <input type="hidden" name="packageId" id="packageId" value="${extension.packageId }">
		   	   <div class="content " id="content">
				   <table class="table table-bordered table-condensed table-hover space_nowrap" id="table">
				   		<thead>
				   		  <th class="info space_nowrap">资格性和符合性审查项</th>
				   		  <c:set var="suppliers" value="0" />
				   		  <c:forEach items="${extension.supplierList}" var="supplier" varStatus="vs">
				   		  	<c:if test="${fn:contains(supplier.packages,extension.packageId)}">
					   		    <c:set var="suppliers" value="${suppliers+1}" />
					   		    <th class="info">
					   		      ${supplier.suppliers.supplierName }
					   		    </th>
				   		    </c:if>
				   		  </c:forEach>
				   		</thead>
				   		<c:forEach items="${dds}" var="d">
				   			<tr><td class="info" colspan="${suppliers+1}"><b>${d.name}</b></td></tr>
				   			<c:forEach items="${extension.firstAuditList }" var="first" varStatus="vs">
					      	<c:if test="${first.kind == d.id}">
					      	<tr>
					      	  <td class="w260"><a href="javascript:void(0);" title="${first.content}">${first.name}</a></td>
					      	  <c:forEach items="${extension.supplierList }" var="supplier" varStatus="v">
					      	  	<c:if test="${fn:contains(supplier.packages,extension.packageId)}">
			   		                <td class="tc space_nowrap">
			   		                  <input type="radio" onclick="pass(this,'${v.index}','${vs.index}');" name="${supplier.id }${vs.index}" value="${first.id },${supplier.suppliers.id  },0"
			   		                    <c:forEach items="${reviewFirstAuditList }" var="r" >
			   		                      <c:if test="${r.supplierId eq supplier.suppliers.id && r.firstAuditId eq first.id && r.expertId eq sessionScope.loginUser.typeId && r.isPass==0 }">checked</c:if>
			   		                    </c:forEach>
			   		                  >合格&nbsp;
			   		                  <input type="radio" onclick="isPass(this,'${v.index}','${vs.index}');" name="${supplier.id }${vs.index}" value="${first.id },${supplier.suppliers.id  },1"
			   		                     <c:forEach items="${reviewFirstAuditList }" var="r" >
			   		                       <c:if test="${r.supplierId eq supplier.suppliers.id && r.firstAuditId eq first.id && r.expertId eq sessionScope.loginUser.typeId && r.isPass==1 }">checked</c:if>
			   		                     </c:forEach>
			   		                  >不合格
			   		                  <c:forEach items="${reviewFirstAuditList }" var="r" >
		   		                       <c:if test="${r.supplierId eq supplier.suppliers.id && r.firstAuditId eq first.id && r.expertId eq sessionScope.loginUser.typeId && r.isPass==1 }">
			   		                  		<a id="notPassReason_${v.index}_${vs.index}" name="notPassReason" href="javascript:void(0);" onclick="reason('${first.id}','${supplier.suppliers.id }','${sessionScope.loginUser.typeId}');">查看理由</a>
										</c:if>
		   		                      </c:forEach>
		   		                      <a id="notPassReason_${v.index}_${vs.index}" name="notPassReason" class="dnone" href="javascript:void(0);" onclick="reason('${first.id}','${supplier.suppliers.id }','${sessionScope.loginUser.typeId}');">查看理由</a>
			   		                </td>
		   		                </c:if>
		   		              </c:forEach>
					      	</tr>
					      	</c:if>
		 	            </c:forEach>
				   		
				   		</c:forEach>
				   		
		 	            
		 	            <tr class="tc">
		 	              <td class="tc"></td>
		 	              <c:forEach items="${extension.supplierList }" var="supplier" varStatus="vs">
			 	            <c:if test="${fn:contains(supplier.packages,extension.packageId)}">
				 	            <td class="tc space_nowrap">
				 	            	<input type="button" class="btn" onclick="addAll(this,'${supplier.suppliers.id  }',0,'${vs.index+1}');" name="${vs.index}" value="全合格">
				 	            	<input type="button" class="btn" onclick="addNotAll(this,'${supplier.suppliers.id  }',1,'${vs.index+1}');" name="${vs.index}" value="全不合格">
<%-- 				 	            	<input type="radio"  onclick="addAll(this);" name="${vs.index}" value="${supplier.suppliers.id  },0">全部合格&nbsp;
				 	            	<input type="radio" onclick="addNotAll(this);" name="${vs.index}" value="${supplier.suppliers.id  },1">全部不合格  --%>
				 	            </td>
			 	            </c:if>
			 	          </c:forEach>
		 	           </tr>
				   </table>

			</form>
		</div> 
		<div class="col-md-12 col-sm-12 col-xs-12 clear tc mt10">
			<input type="button" onclick="submit1();"  value="提交" class="btn btn-windows git">
			<input type="button" onclick="tempSave();"  value="暂存"  class="btn btn-windows save">
			<input class="btn btn-windows back" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'"><br/>
		</div>
			   </div>
	</div>
  </body>
</html>
