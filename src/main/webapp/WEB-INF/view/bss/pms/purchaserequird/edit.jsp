<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
<%@ include file="/WEB-INF/view/common.jsp" %>
<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
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
	    	   var bud;
	     
	    	    $("#table tr").each(function(){
		    	  var  pid= $(this).find("td:eq(8)").children(":first").val();//上级id
		    		
		    		if(id==pid){
		    			$(this).find("td:eq(8)").children(":first").next().val(budget);
		    			 var spid= $(this).find("td:eq(8)").children(":last").val();
		    			bud= calc(spid);
		    		}  
	    		}); 
	    /*     $("#table tr").each(function(){
	 	    		var cid= $(this).find("td:eq(8)").children(":first").val(); //的值
	 	    		   if(id==cid){ */
	 	    			 //  var sameBud=$(this).find("td:eq(8)").children(":last").next().val();
	 	    			   // alert(sameBud);
	 	    			 //   var   pid= $(this).find("td:eq(8)").children(":first").val();  
	 	    		  	    /*  calc(id); */
	 	    			   
	 	    		 //  $(this).find("td:eq(8)").children(":first").next().val(budget);
	 	    		 /*  } 
	 	    		}); */    
	    	     
	    	  var did=$("#table tr:eq(1)").find("td:eq(8)").children(":first").val();
	    	    var total=0;
	    	    $("#table tr").each(function(){
	 	    		var cid= $(this).find("td:eq(8)").children(":last").val();
	 	    		var same= $(this).find("td:eq(8)").children(":last").prev().val()-0;
	 	    		 if(did==cid){
	 	    			total=total+same;
	 	    		 }
	    	   }); 
	    	    $("#table tr:eq(1)").find("td:eq(8)").children(":first").next().val(total);
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
		 	   	             /*  var did= $(this).find("td:eq(8)").children(":first").val();
			 	   	           if(did=='1'){
			 	   	        	  return bud; 
			 	   	            }  
			 	   	 		    calc(spid); */ 
		 	   	           
		 	   	        // 	$(this).find("td:eq(8)").children(":first").next().val(budget);
		 	   	      }
	     		}); 
	 	    	   
	 	     }     
	  	  
	  	 
	      function sel(obj) {
		   /*  var val = $(obj).val();
		    $("select option").each(function() {
		      var opt = $(this).val();
		      if (val == opt) {
		        $(this).attr("selected", "selected");
		      }
		    }); */
	    		 var org=$(obj).val();
	    		 var price=$(obj).parent().prev().prev().prev().prev().val();
	    		 if(price==""){
	    			var id=$(obj).prev().val();
	    		 	  $.ajax({
	    		          url: "${pageContext.request.contextPath}/accept/detail.html",
	    		          data: "id=" + id,
	    		          type: "post",
	    		          dataType: "json",
	    		          success: function(result) {
	    		            for(var i = 0; i < result.length; i++) {
	    		                var v1 = result[i].id;
	    		                $("#table tr").each(function(){
	    		      			  var opt= $(this).find("td:eq(10)").children(":first").val() ;
	    		      	 		   if(v1==opt){
	    		      	 			 var td=$(this).find("td:eq(10)");
	    		      	 			var options= $(td).find("option");
	    			      	 		  $(options).each(function(){
	    			      	  		   var opt=$(this).val();
	    			      	  		   if(org==opt){
	    			      	  			$(this).prop("selected",true);
	    			      	  			//   $(this).attr("selected", "selected");  
	    			      	  		   }else{
	    			      	  			  $(this).removeAttr("selected");
	    			      	  		   }
	    				      	  	   });
	    		      	 		   }  
	    		      	 	   });
	    		            }
	    		           }
	    		          });
	    		          
	    		          
	    		 }
		  }
	       
	
	      function submit(){
	    	  
	    	  var mc=$("#jhmc").val();
	    	  var bh=$("#jhbh").val();
	    	  var no=$("#referenceNo").val();
	    	  var type=$("#wtype").val();
	    	  var mobile=$("#rec_mobile").val();
	    	  
	    	  $("input[name='planName']").val(mc);
	    	  $("input[name='planNo']").val(bh);
	    	  $("input[name='referenceNo']").val(no);
	    	  $("input[name='planType']").val(type);
	    	  $("input[name='mobile']").val(mobile);
	    	  
	    	  $("#table").find("#edit_form").submit();
	    	 // $("#edit_form").submit();
	      }
</script>
<!-- <script type="text/javascript" src="http://code.jquery.com/jquery-1.6.1.min.js"></script> -->
<%-- <script src="${pageContext.request.contextPath}/public/backend/js/lock_table_head.js" ></script>
 --%>  
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
	<div class="container container_box" id="container">
		
		 <div>
				<h2 class="count_flow"><i>1</i>计划主信息</h2>
				<ul class="ul_list">
					<li class="col-md-3 col-sm-6 col-xs-12 pl15">
						<span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">计划名称</span>
						<div class="input-append input_group col-sm-12 col-xs-12 p0">
							<input type="text" class="input_group"  id="jhmc" value="${list[0].planName}">
							<span class="add-on">i</span>
						</div>
					</li>
					<li class="col-md-3 col-sm-6 col-xs-12">
						<span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">计划编号</span>
						<div class="input-append input_group col-sm-12 col-xs-12 p0">
							<input type="text" class="input_group" id="jhbh" value="${list[0].planNo}" >
							<span class="add-on">i</span>
						</div>
					</li>
					
					<li class="col-md-3 col-sm-6 col-xs-12">
						<span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">计划文号</span>
						<div class="input-append input_group col-sm-12 col-xs-12 p0">
							<input type="text" class="input_group"  id="referenceNo" name="referenceNo"   value="${list[0].referenceNo}" >
							<span class="add-on">i</span>
						</div>
					</li>
					
					
					
					<li class="col-md-3 col-sm-6 col-xs-12">
						<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">类别</span>
						<div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
							<select name="planType" id="wtype" onchange="gtype(this)"  >
								<c:forEach items="${types }" var="tp" >
									<option value="${tp.id }">${tp.name }</option>
								</c:forEach>
							</select>
						</div>
					</li>
					
				  
					<li class="col-md-3 col-sm-6 col-xs-12">
						<span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">录入人手机号</span>
						<div class="input-append input_group col-sm-12 col-xs-12 p0">
							<input type="text" class="input_group" id="rec_mobile"  name="mobile" value="${list[0].recorderMobile }"> 
							<span class="add-on">i</span>
						</div>
					</li>
					<li class="col-md-3 col-sm-6 col-xs-12 mt25 ml5" style="display:none" id="dnone" >
			            <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
			                <input type="checkbox" name="" onchange="" value="进口" />进口
			            </div>
			         </li>
          
             <li class="col-md-3 col-sm-6 col-xs-12">
                     <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">计划附件</span>
                       <u:upload id="detail"  multiple="true" buttonName="上传附件"    businessId="${fileId}" sysKey="2" typeId="${typeId}" auto="true" />
                        <u:show showId="detailshow"  businessId="${fileId}" sysKey="2" typeId="${typeId}" />
             </li>
          
	   </ul>
	 </div>

		<h2 class="count_flow"><i>2</i>计划明细</h2>
		<div class="content mt0 require_ul_list">
	
             <div class="content " id="content">
                 <table id="table" class="table table-bordered table-condensed lockout" >
					<thead>
						<tr class="" id="scroll_top">
							<th class="info seq">序号</th>
							<th class="info department">需求部门</th>
							<th class="info goodsname">物资类别<br>及名称</th>
							<th class="info stand">规格型号</th>
							<th class="info qualitstand">质量技术标准</br>（技术参数）</th>
							<th class="info item">计量<br>单位</th>
							<th class="info purchasecount">采购<br>数量</th>
							<th class="info price">单价</br>（元）</th>
							<th class="info budget">预算金额</br>（万元）</th>
							<th class="info deliverdate">交货期限</th>
							<th class="info purchasetype">采购方式</th>
							<th class="info purchasename">供应商名称</th>
							<th class="info freetax">是否申请</br>办理免税</th>
					<!-- 		<th class="info">物资用途</br>（仅进口）</th>
							<th class="info">使用单位</br>（仅进口）</th> -->
							<th class="info memo">备注</th>
							   <th  class="extrafile">附件</th>  
					<!-- 		<th class="w100">状态</th> -->
						</tr>
					</thead>
					<form   id="edit_form"  action="${pageContext.request.contextPath}/purchaser/update.html" method="post">
					<c:forEach items="${list }" var="obj" varStatus="vs">
						<tr style="cursor: pointer;">
                           <td class="tc">${obj.seq}<input class="seq" type="hidden" name="list[${vs.index }].id" value="${obj.id }"></td>
                           <td><%-- <input type="text" name="list[0].department" value="${obj.department}"> --%>
                               <div class="department">${obj.department}</div>
                          <%--  <c:forEach items="${requires }" var="re" >
					         <c:if test="${obj.department==re.name }"> <input readonly='readonly' type="text"  value="${re.name}" > </c:if>
			               </c:forEach> --%>
                  </td>
                  <td>
                      <div class="goodsname">
                  		<textarea name="list[${vs.index }].goodsName" class="target">${obj.goodsName}</textarea>
                      </div>
                  </td>
                  <td><input type="text" name="list[${vs.index }].stand" value="${obj.stand}" class="stand"></td>
                  <td><input type="text" name="list[${vs.index }].qualitStand" value="${obj.qualitStand}" class="qualitstand"></td>
                  <td><input type="text" name="list[${vs.index }].item" value="${obj.item}" class="item"></td>
                  
                  <td>
                    <c:if test="${obj.purchaseCount!=null}">
                      <input   type="hidden" name="ss"   value="${obj.id }" >
                      <input maxlength="11" class="purchasecount" onblur="sum2(this);" type="text" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')" name="list[${vs.index }].purchaseCount"   value="${obj.purchaseCount}"/>
                      <input type="hidden" name="ss" value="${obj.parentId }">
                    </c:if>
                    <c:if test="${obj.purchaseCount==null }">
                      <input class="purchasecount" type="text" name="list[${vs.index }].purchaseCount"  class="w80" value="${obj.purchaseCount }">
                    </c:if>
                  </td>
                  <td class="tl w80">
                    <c:if test="${obj.price!=null}">
                      <input   type="hidden" name="ss"   value="${obj.id }">
                      <input maxlength="11" class="price"   name="list[${vs.index }].price"  onblur="sum1(this);"  value="${obj.price}" type="text" />
                      <input type="hidden" name="ss"   value="${obj.parentId }">
                    </c:if>
                    <c:if test="${obj.price==null}">
                      <input class="price" readonly="readonly"   type="text" name="list[${vs.index }].price" value="${obj.price }">
                    </c:if>
                  </td>
                  <td>
                    <input   type="hidden" name="ss"   value="${obj.id }">
                    <input maxlength="11" id="budget" name="list[${vs.index }].budget" type="text" readonly="readonly"  value="${obj.budget}" class="budget"/>
                    <input type="hidden" name="ss"   value="${obj.parentId }">
                  </td>
                  <td class="tc"><textarea name="list[${vs.index }].deliverDate" class="target deliverdate">${obj.deliverDate}</textarea></td>
                  <td>
             <%--       <c:if test="${obj.price!=null}"> --%>
                     <input type="hidden" name="ss" value="${obj.id}"  >
                      <select name="list[${vs.index }].purchaseType"  <c:if test="${obj.price==null}"> onchange="sel(this);" </c:if> class="purchasetype" id="select">
                        <option value="" >请选择</option>
                        <c:forEach items="${kind}" var="kind" >
                           <option value="${kind.id}" <c:if test="${kind.id == obj.purchaseType}">selected="selected" </c:if>> ${kind.name}</option>
                        </c:forEach>
                      </select> 
                  <%--    </c:if> --%>
                  </td>
                  <td><textarea name="list[${vs.index }].supplier" class="target purchasename">${obj.supplier}</textarea></td>
                  <td><input type="text" name="list[${vs.index }].isFreeTax" value="${obj.isFreeTax}" class="freetax"></td>
                 <%--  <td><input type="text" name="list[${vs.index }].goodsUse" value="${obj.goodsUse}"></td>
                  <td><input type="text" name="list[${vs.index }].userUnit" value="${obj.userUnit}"></td> --%>
                  <td><div class="memo">${obj.memo}</div>
                  <td>
                 <%--  ${obj.id} --%>
							  <%--  <div class="extrafile">
									 <u:upload id="up_${vs.index}"  multiple="true"  businessId="${obj.id}" buttonName="上传文件" sysKey="2" typeId="${typeId}" auto="true" />
									 <u:show showId="show_${vs.index}"  businessId="${obj.id}" sysKey="2" typeId="${typeId}" />
							  </div>	 --%>										
					 </td>
                  <%--
                     <input type="hidden" name="list[${vs.index }].seq" value="${obj.seq }">
                     <input type="hidden" name="list[${vs.index }].department" value="${obj.department }">
                     <input type="hidden" name="list[${vs.index }].goodsName" value="${obj.goodsName }">
                    <input type="hidden" name="list[${vs.index }].stand" value="${obj.stand }">
                     <input type="hidden" name="list[${vs.index }].qualitStand" value="${obj.qualitStand }">
                   <input type="hidden" name="list[${vs.index }].item" value="${obj.item }"> 
                     <input type="hidden" name="list[${vs.index }].deliverDate" value="${obj.deliverDate }"> 
                     <input type="hidden" name="list[${vs.index }].supplier" value="${obj.supplier }"> 
                     <input type="hidden" name="list[${vs.index }].isFreeTax" value="${obj.isFreeTax }">
                     <input type="hidden" name="list[${vs.index }].goodsUse" value="${obj.goodsUse }">
                     <input type="hidden" name="list[${vs.index }].useUnit" value="${obj.useUnit }">
                     <input type="hidden" name="list[${vs.index }].memo" value="${obj.memo }">--%>
          <%--            <input type="hidden" name="list[${vs.index }].planName" value="${obj.planName }">
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
                     <input type="hidden" name="list[${vs.index }].seq" value="${obj.seq}">
                     <input type="hidden" name="list[${vs.index }].userId" value="${obj.userId}">
                     <input type="hidden" name="list[${vs.index }].createdAt" value="${obj.createdAt}">
                     <input type="hidden" name="list[${vs.index }].department" value="${obj.department}">  --%>
                   </td>
             <!--       <td class="tc w100"><input type="text" value="暂存" readonly="readonly"></td> -->
                 </tr>

				 </c:forEach>
				 
				 		    <input type="hidden" name="planName">
							<input type="hidden" name="planNo"  >
							<input type="hidden" name="planType"  >
							<input type="hidden" name="mobile"  >
						    <input type="hidden" name="referenceNo"  />
						    
						    
			   </form>
				</table>
				</div>
				<div class="col-md-12  mt10 col-sm-12 col-xs-12 tc">
			    <input class="btn btn-windows git" type="button" onclick="submit()" value="保存">
                <input class="btn btn-windows back" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
             </div>
		
		</div>
    </div>

</body>
</html>
