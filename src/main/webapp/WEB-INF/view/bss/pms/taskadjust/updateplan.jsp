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
	
  	function view(no){
  		
  		
  		window.location.href="${pageContext.request.contextPath}/purchaser/queryByNo.html?planNo="+no;
  	}
  	
  	 function aadd(){
		  var  s=$("#count").val();
	      	s++;
	      	$("#count").val(s);
	        var tr = $("input[name=dyadds]").parent().parent();
	        $(tr).before("<tr><td class='tc'><input type='text' name='list["+s+"].seq' /></td>"+
		       "<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].department' /> </td>"+
		       "<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].goodsName' /> </td>"+ 
		       "<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].stand' /> </td>"+ 
		       "<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].qualitStand' /> </td>"+ 
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].item' /> </td>"+  
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].purchaseCount' /> </td>"+  
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].price' /> </td>"+  
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].budget' /> </td>"+  
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].deliverDate' /> </td>"+  
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].purchaseType' /> </td>"+  
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].supplier' /> </td>"+  
		       	"<td class='tc'> <input style='border: 0px;'type='text' name='list["+s+"].isFreeTax' /> </td>"+  
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].goodsUse' /> </td>"+  
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].useUnit' /> </td>"+
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].memo' /> </td>"+  
	        +"<tr/>");
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
	
	function sub(){
	 
			$("#adjust").submit();
		 
	}
	
	function sum2(obj){  //数量
        var purchaseCount = $(obj).val()-0;//数量
        var price2 = $(obj).parent().next().children(":last").prev();//价钱
        var price = $(price2).val()-0;
        var sum = purchaseCount*price/10000;
        var budget = $(obj).parent().next().next().children(":last").prev();
        $(budget).val(sum);
      	var id=$(obj).next().val(); //parentId
      	aa(id);
    } 
    
       function sum1(obj){
        var purchaseCount = $(obj).val()-0; //价钱
         var price2 = $(obj).parent().prev().children(":last").prev().val()-0;//数量
      	 var sum = purchaseCount*price2/10000;
         $(obj).parent().next().children(":last").prev().val(sum);
	     	var id=$(obj).next().val(); //parentId
	     	aa(id);
    }

       function aa(id){// id是指当前的父级parentid
    	   var budget=0;
    	   $("#table tr").each(function(){
 	    		var cid= $(this).find("td:eq(8)").children(":last").val(); //parentId
 	    		var same= $(this).find("td:eq(8)").children(":last").prev().val()-0; //价格
	 	       if(id==cid){
	 	    	 
	 	    	  budget=budget+same; //查出所有的子节点的值
	 	       }
    	   });
    	   budget = budget.toFixed(2); 
     
    	    $("#table tr").each(function(){
	    	  var  pid= $(this).find("td:eq(8)").children(":first").val();//上级id
	    		
	    		if(id==pid){
	    			$(this).find("td:eq(8)").children(":first").next().val(budget);
	    			 var spid= $(this).find("td:eq(8)").children(":last").val();
	    			 calc(spid);
	    		}  
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
       
  	  function calc(id){
    	var bud=0;
 	   	    $("#table tr").each(function(){
 	   	           var pid= $(this).find("td:eq(8)").children(":last").val() ;
	 	   	       if(id==pid){
	 	   	         	var currBud=$(this).find("td:eq(8)").children(":first").next().val()-0;
	 	   	            bud=bud+currBud;
	 	   	            bud = bud.toFixed(2);
	 	   	            
	 	   	              var spid= $(this).find("td:eq(8)").children(":last").val();
	 	   	              aa(spid);
	 	   	   
	 	   	      }
     		}); 
 	    	   
 	     }     
	       
	       
	       function sel(obj){
	    	   var val=$(obj).val();
	    	   $("select option").each(function(){
	    		   var opt=$(this).val();
	    		   if(val==opt){
	    			   $(this).attr("selected", "selected");  
	    		   }
	    	   });
	       }  
	       
	/*        function ss(obj){
	    	   var val=$(obj).val();
	    	   $(obj).find().each(function(){
	    		   var opt=$(this).val();
	    		   if(val==opt){
	    			   $(this).attr("selected", "selected");  
	    		   }
	    	   });
	       } */
	      
	    function org(obj){
	    	   var val=$(obj).val();
	    	   $(".org option").each(function(){
	    		   var opt=$(this).val();
	    		   if(val==opt){
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
			<h2>采购任务明细</h2>
		</div>
	
		  <div class="content table_box">
			<form id="adjust" action="${pageContext.request.contextPath}/adjust/updatePlan.html" method="post"  >
				<!-- 前半部分 -->
				<div class="col-md-12 col-sm-12 col-xs-12 p0 over_scroll h365">
					<table id="table" class="table table-bordered table-condensed mt5 table_input">
						<thead>
							<tr>
								<th class="info" colspan="17">事业部门需求</th>
							</tr>
							<tr>
								<th class="info w50">序号</th>
								<th class="info">需求部门</th>
								<th class="info">物资类别及物种名称</th>
								<th class="info">规格型号</th>
								<th class="info">质量技术标准（技术参数）</th>
								<th class="info">计量单位</th>
								<th class="info">采购数量</th>
								<th class="info">单位（元）</th>
								<th class="info">预算金额（万元）</th>
								<th class="info">交货期限</th>
								<th class="info">采购方式建议</th>
								<th class="info">采购机构</th>
								<th class="info">供应商名称</th>
								<th class="info">是否申请办理免税</th>
								<th class="info">物资用途（仅进口）</th>
								<th class="info">使用单位（仅进口）</th>
								<th class="info">备注</th>
							</tr>
						</thead>
						<c:forEach items="${list }" var="obj" varStatus="vs">
						<tr>
							<td class="tc w50"><input style="border: 0px;" readonly="readonly" type="text" name="listDetail[${vs.index }].seq" value="${obj.seq }" class="w50 tc">
							<input style="border: 0px;" type="hidden" name="listDetail[${vs.index }].id" value="${obj.id }">
							</td>
							<td>
							<input type="text" readonly="readonly" value="${obj.department}" >
							
						<%-- 	    <c:forEach items="${requires }" var="re" >
										  <c:if test="${obj.department==re.id }">  --%>
<%-- 										    <input style="border: 0px;" readonly="readonly" type="text" name="list[${vs.index }].department" onblur="checks(this)" value="${re.name }">
 --%>					<%-- 					  </c:if>
								  	</c:forEach> --%>
			  	
			  	
							
							</td>
							<td class="tl pl20"><input type="text" name="listDetail[${vs.index }].goodsName"  value="${obj.goodsName }"></td>
							<td class="tl pl20"><input style="border: 0px;" readonly="readonly" type="text" name="listDetail[${vs.index }].stand" value="${obj.stand }"></td>
							<td class="tl pl20"><input  type="text" name="listDetail[${vs.index }].qualitStand" value="${obj.qualitStand }"></td>
							<td class="tl pl20"><input  type="text" name="listDetail[${vs.index }].item" value="${obj.item }"></td>
							<td class="tl pl20">
							  <c:if test="${obj.price!=null}">
								<input   type="hidden" name="ss"   value="${obj.id }">
								<input  onblur="sum2(this)"  type="text" name="listDetail[${vs.index }].purchaseCount" onblur="checks(this)"  value="${obj.purchaseCount }">
								<input type="hidden" name="ss"   value="${obj.parentId }">
							  </c:if>
							  <c:if test="${obj.price==null}">
		                       <input   readonly="readonly"   type="text" >
		                     </c:if>
							</td>
							<td class="tl pl20">
							  <c:if test="${obj.price!=null}">
								<input   type="hidden" name="ss"   value="${obj.id }">
								<input onblur="sum1(this)"  type="text" name="listDetail[${vs.index }].price" value="${obj.price }">
								<input type="hidden" name="ss"   value="${obj.parentId }">
							</c:if>
							<c:if test="${obj.price==null}">
		                        <input  readonly="readonly"  type="text" >
		                    </c:if>
                    
							</td>
							<td class="tr pr20">
								<input type="hidden" name="ss"    value="${obj.id}">
								<input   type="text" name="listDetail[${vs.index }].budget" onblur="checks(this)"  value="${obj.budget }">
								<input type="hidden" name="ss"  value="${obj.parentId }">
							</td>
							<td><input type="text" name="listDetail[${vs.index }].deliverDate" onblur="checks(this)" value="${obj.deliverDate }"></td>
							<td>
								<select name="listDetail[${vs.index }].purchaseType" <c:if test="${obj.price==null}"> onchange="sel(this)"  </c:if> style="width:100px" id="select">
	              				    <option value="" >请选择</option>
		                            <c:forEach items="${types }" var="mt">
									  <option value="${mt.id }"<c:if test="${mt.id==obj.purchaseType }"> selected="selected"</c:if> >${mt.name}</option>
									</c:forEach>	
				                </select>
							</td>
							<td class="tc">
								<%--<input type="hidden" name="listDetail[${vs.index }].organization" value="${obj.organization }">--%>
								<select class="org"  <c:if test="${obj.price==null}"> onchange="org(this)"  </c:if>   name="listDetail[${vs.index }].organization">
		 							<option value="">请选择</option>
									<c:forEach items="${orgs }" var="ss">
										<c:if test="${obj.organization==ss.orgId }">
										<option value="${ss.orgId }" selected="selected">${ss.name}</option>
										</c:if>
										<c:if test="${obj.organization!=ss.orgId }">
										 <option value="${ss.orgId }" >${ss.name}</option>
										</c:if>
									</c:forEach>
								</select>
							</td>
							<td class="tl pl20"><input style="border: 0px;" readonly="readonly" type="text" name="listDetail[${vs.index }].supplier" value="${obj.supplier }"></td>
							<td class="tl pl20"><input style="border: 0px;" readonly="readonly" type="text" name="listDetail[${vs.index }].isFreeTax" value="${obj.isFreeTax }"></td>
							<td class="tl pl20"><input style="border: 0px;" readonly="readonly" type="text" name="listDetail[${vs.index }].goodsUse" value="${obj.goodsUse }"></td>
							<td class="tl pl20"><input style="border: 0px;" readonly="readonly" type="text" name="listDetail[${vs.index }].useUnit" value="${obj.useUnit }"></td>
							<td class="tl pl20"><input style="border: 0px;" readonly="readonly" type="text" name="listDetail[${vs.index }].memo" value="${obj.memo }">
					<%-- 			<input type="hidden" name="listDetail[${vs.index }].planName" value="${obj.planName }">
								<input type="hidden" name="listDetail[${vs.index }].planNo" value="${obj.planNo }">
								<input type="hidden" name="listDetail[${vs.index }].planType" value="${obj.planType }">
								<input type="hidden" name="listDetail[${vs.index }].parentId" value="${obj.parentId }">
								<input type="hidden" name="listDetail[${vs.index }].historyStatus" value="${obj.historyStatus }">
								<input type="hidden" name="listDetail[${vs.index }].goodsType" value="${obj.goodsType }">
								<input type="hidden" name="listDetail[${vs.index }].auditDate" value="${obj.auditDate }">
								<input type="hidden" name="listDetail[${vs.index }].isMaster" value="${obj.isMaster }">
								<input type="hidden" name="listDetail[${vs.index }].isDelete" value="${obj.isDelete }">
								<input type="hidden" name="listDetail[${vs.index }].status" value="${obj.status }">
								<input type="hidden" name="listDetail[${vs.index }].threePurchaseType" value="${obj.threePurchaseType }">
								<input type="hidden" name="listDetail[${vs.index }].threeOrganiza" value="${obj.threeOrganiza }">
								<input type="hidden" name="listDetail[${vs.index }].threeAdvice" value="${obj.threeAdvice }">
								<input type="hidden" name="listDetail[${vs.index }].createAt" value="${obj.createdAt }">
								<input type="hidden" name="listDetail[${vs.index }].isCollect" value="${obj.isCollect }"> --%>
							</td>
						</tr>
						</c:forEach>
					</table>	
				</div>

			</form>
		</div>
			<div class="col-md-12 col-sm-12 col-xs-12 tc mt10">
                <!-- <div class=""><a class="upload">上传附件</a><input id="required" type="file" name="file"> </div> -->
                <input class="btn btn-windows edit"  type="button" value="修改" onclick="sub()">
                <input class="btn btn-windows back" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
		  </div>
		  
		<!-- <input class="btn btn-windows save"  type="button" value="提交" onclick="sub()">
			 <input class="btn btn-windows reset" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'"> -->
	</div>
<%-- 	<input type="hidden" id="pNo" name="" value="${planNo }">
 --%></body>
</html>
