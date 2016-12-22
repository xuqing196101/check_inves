<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
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
	
	/** 单选 */
	function check(){
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
  	
  	 
  	 
	
   	var flag=true;
	function checks(obj){
		  var name=$(obj).attr("name");
		  var planNo=$("#pNo").val();
		  var val=$(obj).val();
		  var defVal=obj.defaultValue;
			if(val!=defVal){
				$.ajax({
					url:"${pageContext.request.contextPath}/adjust/filed.html",
					type:"post",
					data:{
						planNo:planNo,
						name:name
					},
					success: function(data){
						 if(data=='exit'){
							 flag=false;
							 layer.tips("该字段不允许修改",obj);
						 }
					 },
					error:function(data){
						 
					 }
					 
				});
			} 
	}
	
	
	 function sum2(obj){  //数量
	        var purchaseCount = $(obj).val()-0;//数量
	        var price2 = $(obj).parent().next().children(":last").prev();//价钱
	        var price = $(price2).val()-0;
	        var sum = purchaseCount*price;
	        var budget = $(obj).parent().next().next().children(":last").prev();
	        $(budget).val(sum);
	      	var id=$(obj).next().val();
	      	aa(id);
	    } 
	    
	       function sum1(obj){
	        var purchaseCount = $(obj).val()-0; //价钱
	         var price2 = $(obj).parent().prev().children(":last").prev().val()-0;//数量
	      	 var sum = purchaseCount*price2;
	         $(obj).parent().next().children(":last").prev().val(sum);
		     	var id=$(obj).next().val();
		     	aa(id);
	    }
	
	       function aa(id){// id是指当前的父级parentid
	    	  
	    	   var budget=0;
	    	   $("#table tr").each(function(){
	 	    		var cid= $(this).find("td:eq(8)").children(":last").val();
	 	    		var same= $(this).find("td:eq(8)").children(":last").prev().val()-0;
		 	       if(id==cid){
		 	    	 
		 	    	  budget=budget+same; //查出所有的子节点的值
		 	       }
	    	   });
	    	  
	    	    $("#table tr").each(function(){
	    		var pid= $(this).find("td:eq(8)").children(":first").val();//上级id
	    		
	    		if(id==pid){
	    			$(this).find("td:eq(8)").children(":first").next().val(budget);
	    		}
	    	  	 $("#table tr").each(function(){
	 	    		var cid= $(this).find("td:eq(8)").children(":last").val();
	 	    		  if(pid==cid&&id!=pid){
	 	    		
	 	    			 $(this).find("td:eq(8)").children(":first").next().val(budget);
	 	    		  }
	 	    		});  
	    		});  
	   
	    	  var did=$("table tr:eq(1)").find("td:eq(8)").children(":first").val();
	    	    var total=0;
	    	    $("#table tr").each(function(){
	    	    	
	 	    		var cid= $(this).find("td:eq(8)").children(":last").val();
	 	    		var same= $(this).find("td:eq(8)").children(":last").prev().val()-0;
	 	    		 if(did==cid){
	 	    			
	 	    			total=total+same;
	 	    		 }
	    	   }); 
	    	    $("table tr:eq(1)").find("td:eq(8)").children(":first").next().val(total);
	       }
	         
	      function sel(obj) {
		    var val = $(obj).val();
		    $("select option").each(function() {
		      var opt = $(this).val();
		      if (val == opt) {
		        $(this).attr("selected", "selected");
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
				<li><a href="#"> 首页</a></li>
				<li><a href="#">保障作业系统</a></li>
				<li><a href="#">采购计划管理</a></li>
				<li class="active"><a href="#">采购需求管理</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>
	<div class="container">
		<div class="headline-v2 fl">
			<h2>计划明细</h2>
		</div>
			<form action="${pageContext.request.contextPath}/purchaser/update.html" method="post">
			<div class="col-md-12 pl20 mt10">
			 <input class="btn btn-windows git" type="submit" value="提交">
                <input class="btn btn-windows back" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
             </div>
             <div class="content table_box over_scroll">
                 <table id="table" class="table table-bordered table-condensed table_input">
					<thead>
						<tr>
							<th class="info w50">序号</th>
							<th class="info">需求部门</th>
							<th class="info">物资类别及物种名称</th>
							<th class="info">规格型号</th>
							<th class="info">质量技术标准（技术参数）</th>
							<th class="info">计量单位</th>
							<th class="info w100">采购数量</th>
							<th class="info">单位（元）</th>
							<th class="info">预算金额（万元）</th>
							<th class="info">交货期限</th>
							<th class="info">采购方式建议</th>
							<th class="info">供应商名称</th>
							<th class="info">是否申请办理免税</th>
							<th class="info">物资用途（仅进口）</th>
							<th class="info">使用单位（仅进口）</th>
							<th class="info">备注</th>
							<th class="w100">状态</th>
						</tr>
					</thead>

					<c:forEach items="${list }" var="obj" varStatus="vs">
						<tr style="cursor: pointer;">
                  <td class="tc w50">${obj.seq}  <input style="border: 0px;" type="hidden" name="list[${vs.index }].id" value="${obj.id }"></td>
                  <td class="tc"><%-- <input type="text" name="list[0].department" value="${obj.department}"> --%>
                    <c:forEach items="${requires }" var="re" >
					  <c:if test="${obj.department==re.id }"> <input readonly='readonly' type="text"  value="${re.name}"> </c:if>
			  	</c:forEach>
			  	
			  	
                  </td>
                  <td class="tc"><input type="text" name="list[${vs.index }].goodsName" value="${obj.goodsName}"></td>
                  <td class="tc"><input type="text" name="list[${vs.index }].stand" value="${obj.stand}"></td>
                  <td class="tc"><input type="text" name="list[${vs.index }].qualitStand" value="${obj.qualitStand}"></td>
                  <td class="tc"><input type="text" name="list[${vs.index }].item" value="${obj.item}"></td>
                  
                  <td class="tc">
                    <c:if test="${obj.purchaseCount!=null}">
                     
                      <input   type="hidden" name="ss"   value="${obj.id }" >
                      <input maxlength="11" id="purchaseCount" onblur="sum2(this);" type="text" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')" name="list[${vs.index }].purchaseCount"   value="${obj.purchaseCount}"/>
                      <input type="hidden" name="ss" value="${obj.parentId }">
                    </c:if>
                    <c:if test="${obj.purchaseCount==null }">
                      <input  class="border0"    type="text" name="list[${vs.index }].purchaseCount"   value="${obj.purchaseCount }">
                    </c:if>
                  </td>
                  <td class="tc">
                    <c:if test="${obj.price!=null}">
                      <input   type="hidden" name="ss"   value="${obj.id }">
                      <input maxlength="11" id="price"  name="list[${vs.index }].price"  onblur="sum1(this);"  value="${obj.price}" type="text" />
                      <input type="hidden" name="ss"   value="${obj.parentId }">
                    </c:if>
                    <c:if test="${obj.price==null}">
                      <input class="border0"  readonly="readonly"   type="text" name="list[${vs.index }].price" value="${obj.price }">
                    </c:if>
                  </td>
                  <td class="tc">
                    <input   type="hidden" name="ss"   value="${obj.id }">
                    <input maxlength="11" id="budget" name="list[${vs.index }].budget" type="text" readonly="readonly"  value="${obj.budget}"/>
                    <input type="hidden" name="ss"   value="${obj.parentId }">
                  </td>
                  <td class="tc"><input type="text" name="list[${vs.index }].deliverDate" value="${obj.deliverDate}"></td>
                  <td class="tc">
                      <select name="list[${vs.index }].purchaseType" onchange="sel(this);" style="width:100px" id="select">
                        <c:forEach items="${kind}" var="kind" >
                           <option value="${kind.id}" <c:if test="${kind.id == obj.purchaseType}">selected="selected" </c:if>> ${kind.name}</option>
                        </c:forEach>
                      </select> 
                  </td>
                  <td class="tc"><input type="text" name="list[${vs.index }].supplier" value="${obj.supplier}" disabled="disabled"></td>
                  <td class="tc"><input type="text" name="list[${vs.index }].isFreeTax" value="${obj.isFreeTax}"></td>
                  <td class="tc"><input type="text" name="list[${vs.index }].goodsUse" value="${obj.goodsUse}"></td>
                  <td class="tc"><input type="text" name="list[${vs.index }].useUnit" value="${obj.useUnit}"></td>
                  <td class="tc">${obj.memo }<%--
                     <input type="hidden" name="list[${vs.index }].seq" value="${obj.seq }">
                     <input type="hidden" name="list[${vs.index }].department" value="${obj.department }">
                     <input type="hidden" name="list[${vs.index }].goodsName" value="${obj.goodsName }">
                     --%><input type="hidden" name="list[${vs.index }].stand" value="${obj.stand }">
                     <input type="hidden" name="list[${vs.index }].qualitStand" value="${obj.qualitStand }">
                     <input type="hidden" name="list[${vs.index }].item" value="${obj.item }">
                     <input type="hidden" name="list[${vs.index }].deliverDate" value="${obj.deliverDate }">
                     <input type="hidden" name="list[${vs.index }].supplier" value="${obj.supplier }"><%--
                     <input type="hidden" name="list[${vs.index }].isFreeTax" value="${obj.isFreeTax }">
                     --%><input type="hidden" name="list[${vs.index }].goodsUse" value="${obj.goodsUse }">
                     <input type="hidden" name="list[${vs.index }].useUnit" value="${obj.useUnit }">
                     <input type="hidden" name="list[${vs.index }].memo" value="${obj.memo }">
                     <input type="hidden" name="list[${vs.index }].planName" value="${obj.planName }">
                     <input type="hidden" name="list[${vs.index }].planNo" value="${obj.planNo }">
                     <input type="hidden" name="list[${vs.index }].planType" value="${obj.planType }">
                     <input type="hidden" name="list[${vs.index }].parentId" value="${obj.parentId }">
                     <input type="hidden" name="list[${vs.index }].historyStatus" value="${obj.historyStatus }">
                     <input type="hidden" name="list[${vs.index }].goodsType" value="${obj.goodsType }">
                     <input type="hidden" name="list[${vs.index }].organization" value="${obj.organization }">
                     <input type="hidden" name="list[${vs.index }].auditDate" value="${obj.auditDate }">
                     <input type="hidden" name="list[${vs.index }].isMaster" value="${obj.isMaster }">
                     <input type="hidden" name="list[${vs.index }].isDelete" value="${obj.isDelete }">
                     <input type="hidden" name="list[${vs.index }].status" value="${obj.status }">
                   </td>
                   <td class="tc w100"><input type="text" value="暂存" readonly="readonly"></td>
                 </tr>

					</c:forEach>
				
				</table>
				<div { overflow: scroll; height: 100px; width: 100px; } ></div>
				</div>
			</form>
		</div>

</body>
</html>
