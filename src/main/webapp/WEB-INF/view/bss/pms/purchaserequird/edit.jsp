<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
<%@ include file="/WEB-INF/view/common.jsp" %>
<%@ include file="/WEB-INF/view/common/webupload.jsp"%>

   <script type="text/javascript" src="${pageContext.request.contextPath}/public/lock_table/moment.js"></script>
   
   <script type="text/javascript" src="${pageContext.request.contextPath}/public/lock_table/pikaday.js"></script>
   <script type="text/javascript" src="${pageContext.request.contextPath}/public/lock_table/ZeroClipboard.js"></script>
   
   <link href="${pageContext.request.contextPath}/public/lock_table/handsontable.min.css" media="screen" rel="stylesheet" type="text/css">
   <link href="${pageContext.request.contextPath}/public/lock_table/pikaday.css" media="screen" rel="stylesheet" type="text/css">


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
		    	var defValue;
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
						      	 		defValue=$(this).parent().parent().parent().children(":last").children(":last").val();
					      	 		 
	    			      	  		   var opt=$(this).val();
	    			      	  		   if(org==opt){
	    			      	  			$(this).prop("selected",true);
	    			      	  			
	    			      	  			if(defValue==org){
	    			      	  			    $(this).parent().next().val("");	
	    			      	  			}else{
	    			      	  			    var prevId=$(this).parent().prev().val();
	    			      	  			    $(this).parent().next().val(prevId);	
	    			      	  			}
	    			      	  		 
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
	       
	
	      function historys(obj){
	        	var val = $(obj).val();
	            var  id=$(obj).prev().val();
	    		var defVal = obj.defaultValue;
	    		if(val != defVal) {
	    			$(obj).next().val(id);
	    		}else{
	    			$(obj).next().val("");
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
	       function gtype(obj){
	    	    var vals=$(obj).val();
				$("#import").attr("checked",false);
				/* $("#import").attr("checked",false); */
				$("td[name='userNone']").attr("style","display:none");
				$("th[name='userNone']").attr("style","display:none");
				if(vals == 'FC9528B2E74F4CB2A9E74735A8D6E90A'){
					  $("#dnone").show();
					  $("#dnone").next().attr("class","col-md-3 col-sm-6 col-xs-12");
				}else{
					 $("#dnone").hide();
					 $("#dnone").next().attr("class","col-md-3 col-sm-6 col-xs-12 mt25 ml5");
				}
			}
	       function imports(obj){
				var bool=$(obj).is(':checked');
				if(bool==true){
					$("td[name='userNone']").attr("style","");
					$("th[name='userNone']").attr("style","");
				}else{
					$("td[name='userNone']").attr("style","display:none");
					$("th[name='userNone']").attr("style","display:none");
				}
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
				<h2 class="count_flow"><i>1</i>需求主信息</h2>
				<ul class="ul_list">
					<li class="col-md-3 col-sm-6 col-xs-12 pl15">
						<span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">需求名称</span>
						<div class="input-append input_group col-sm-12 col-xs-12 p0">
							<input type="text" class="input_group"  id="jhmc" value="${list[0].planName}">
							<span class="add-on">i</span>
						</div>
					</li>
					<li class="col-md-3 col-sm-6 col-xs-12 hide">
						<span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">需求编号</span>
						<div class="input-append input_group col-sm-12 col-xs-12 p0">
							<input type="text" class="input_group" id="jhbh" value="${list[0].planNo}" >
							<span class="add-on">i</span>
						</div>
					</li>
					
					<li class="col-md-3 col-sm-6 col-xs-12">
						<span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">需求文号</span>
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
								  <c:if test="${tp.id==list[0].planType }">
									<option value="${tp.id}" selected="selected">${tp.name }</option>
								 </c:if>
								  <c:if test="${tp.id!=list[0].planType }">
									<option value="${tp.id}">${tp.name }</option>
								 </c:if>
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
					<c:if test="${list[0].enterPort==1}"> 
					<li class="col-md-3 col-sm-6 col-xs-12 mt25 ml5"  id="dnone" >
			            <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
			                <input type="checkbox" id="import" name=""  value="进口" checked="checked"  onchange="imports(this)"  />进口
			            </div>
			         </li>
			       </c:if>
             <li class="col-md-3 col-sm-6 col-xs-12">
                     <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">需求附件</span>
                       <u:upload id="detail"  multiple="true" buttonName="上传附件"    businessId="${fileId}" sysKey="2" typeId="${typeId}" auto="true" />
                        <u:show showId="detailshow"  businessId="${fileId}" sysKey="2" typeId="${detailId}" />
             </li>
          
	   </ul>
	 </div>

		<h2 class="count_flow"><i>2</i>需求明细</h2>
		<div class="content mt0 require_ul_list">
	
             <div class="content " id="content">
                 <table id="table" class="table table-bordered table-condensed lockout" >
					<thead>
						<tr id="scroll_top">
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
							<c:if test="${list[0].enterPort==1}"> 
							<th  name="userNone" class="info">物资用途</br>（仅进口）</th>
							<th  name="userNone" class="info">使用单位</br>（仅进口）</th>
							</c:if>
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
                        <input   type="hidden" name="ss"   value="${obj.id }">
                  		<textarea name="list[${vs.index }].goodsName"  onblur="historys(this)" class="target">${obj.goodsName}</textarea>
                  		 <input type="hidden"    name="history" value=""/>
                      </div>
                  </td>
                  <td>
                   <input   type="hidden" name="ss"   value="${obj.id }">
                  <input type="text" name="list[${vs.index }].stand" value="${obj.stand}" onblur="historys(this)" class="stand">
                   <input type="hidden"    name="history" value=""/>
                  </td>
                  <td>
                  <input   type="hidden" name="ss"   value="${obj.id }">
                  <input type="text" name="list[${vs.index }].qualitStand" value="${obj.qualitStand}"  onblur="historys(this)" class="qualitstand">
                  <input type="hidden"    name="history" value=""/>
                  </td>
                  <td>
                  <input   type="hidden" name="ss"   value="${obj.id }">
                  <input type="text" name="list[${vs.index }].item" value="${obj.item}" onblur="historys(this)" class="item"></td>
                   <input type="hidden"    name="history" value=""/>
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
                  <td class="tc">
                   <input   type="hidden" name="ss"   value="${obj.id }">
                  <textarea name="list[${vs.index }].deliverDate" onblur="historys(this)"  class="target deliverdate">${obj.deliverDate}</textarea>
                    <input type="hidden"    name="history" value=""/>
                  </td>
                 
                  <td>
             <%--       <c:if test="${obj.price!=null}"> --%>
                     <input type="hidden" name="ss" value="${obj.id}"  >
                      <select name="list[${vs.index }].purchaseType"  <c:if test="${obj.price==null}"> onchange="sel(this);" </c:if> class="purchasetype" id="select">
                        <option value="" >请选择</option>
                        <c:forEach items="${kind}" var="kind" >
                           <option value="${kind.id}" <c:if test="${kind.id == obj.purchaseType}">selected="selected" </c:if>> ${kind.name}</option>
                        </c:forEach>
                      </select>
                      <input type="hidden"    name="history" value=""/> 
                  <%--    </c:if> --%>
                  </td>
                  <td>
                  <input   type="hidden" name="ss"   value="${obj.id }">
                  <textarea name="list[${vs.index }].supplier" onblur="historys(this)"  class="target purchasename">${obj.supplier}</textarea>
                  <input type="hidden"    name="history" value=""/>
                  </td>
                  <td><input type="text" name="list[${vs.index }].isFreeTax" onblur="historys(this)"  value="${obj.isFreeTax}" class="freetax"></td>
                 <c:if test="${list[0].enterPort==1}"> 
                  <td name="userNone"> <input type="text" name="list[${vs.index }].goodsUse" value="${obj.goodsUse}"></td>
                  <td name="userNone"><input type="text" name="list[${vs.index }].userUnit" value="${obj.useUnit}"></td>
                  </c:if>
                  <td><div class="memo">
                  <input   type="hidden" name="ss"   value="${obj.id }">
                    <textarea name="list[${vs.index }].memo"  onblur="historys(this)" class="target purchasename">${obj.memo}</textarea>
                    <input type="hidden"    name="history" value=""/>
                  </div>
                  </td>
                  <td>
                <%--   <c:if test="${obj.purchaseCount!=null}">
							   <div class="extrafile">
									 <u:upload id="up_${vs.index}"  multiple="true"  businessId="${obj.id}" buttonName="上传文件" sysKey="2" typeId="${detailId}" auto="true" />
									 <u:show showId="show_${vs.index}"  businessId="${obj.id}" sysKey="2" typeId="${detailId}" />
							  </div>
				</c:if>	 --%>		  
							  	
						<input type="hidden" class="ptype" name="ptype" value="${obj.purchaseType}"/>									
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
<script type="text/javascript">
/*  	window.onload = function () {
    $.ajax({
        url: "${pageContext.request.contextPath}/purchaser/getInfoByNo.html",
        type: "post",
        data: {
            planNo: "${planNo}",
            type: "${org_advice}"
        },
        dataType: "json",
        success: function (data) {
            var kind = [], sources = [], kinds = data.kind;
            kinds.forEach(function (item) {
                kind.push(item.name);
            });
            var check = function (_kind, _purchaseType) {
                var type = "";
                _kind.forEach(function (item) {
                    if (item.id == _purchaseType) {
                        type = item.name;
                        return;
                    }
                });
                return type;
            }
            data.list.forEach(function (item) {
                var source = {
                    id:item.id,
                    department: item.department,
                    goodsName: item.goodsName,
                    stand: item.stand,
                    qualitstand: item.qualitStand,
                    item: item.item,
                    purchaseCount: item.purchaseCount,
                    price: item.price,
                    budget: item.budget,
                    deliverDate: item.deliverDate,
                    purchaseType: check(kinds, item.purchaseType),
                    purchasename: item.supplier,
                    isFreeTax: item.isFreeTax,
                    memo: item.memo,
                    extrafile: "<a href='http://www.amazon.com/Professional-JavaScript-Developers-Nicholas-Zakas/dp/1118026691'>点击查看附件</a>"
                }
                sources.push(source);
            });
            var args = {
                id: "container",
                data: sources,
                colHeaders: ["需求部门", "物资类别及名称", "规格型号", "质量技术标准(技术参数)", "计量单位", "采购数量", "单价(元)", "预算金额(万元)", "交货日期", "采购方式", "供应商名称", "是否申请办理免税", "备注", "附件"],
                columns: {
                    source: kind
                }
            };
            var table = showData(args);

        },
        error: function (data) {
        }
    });
}
var showData = function (options) {
    options.config = {
        data: options.data,
        rowHeaders: true,
        colHeaders: options.colHeaders,
        colWidths: 120,
        manualColumnMove: false,
        manualRowMove: true,
        minSpareRows: 1,
        persistentState: true,
        manualColumnResize: true,
        manualRowResize: true,
        fixedColumnsLeft: 1,
        autoColumnSize: true,
        contextMenu: false,
        search: true,
        columns: [
            {data: 'department', type: "text"},
            {data: 'goodsname', type: "text"},
            {data: 'stand', type: "text"},
            {data: 'qualitstand', type: "text"},
            {data: 'item', type: "text"},
            {data: 'purchasecount', type: "numeric", format: "0"},
            {data: 'price', type: "numeric", format: "0.00"},
            {data: 'budget', type: "numeric", format: "0,000.00"},
            {data: 'deliverdate', type: "date", dateFormat: "YYYY-DD-MM", correctFormat: true},
            {data: 'purchasetype', type: 'dropdown', source: options.columns.source},
            {data: 'purchasename', type: "text"},
            {data: 'freetax', type: "dropdown", source: ["是", "否"]},
            {data: 'memo', type: "text"},
            {data: 'extrafile', renderer: "html"}
        ],
        cells: function (row, col, prop) {
            var cellProperties = {};
            if (col === 0) {
                cellProperties.readOnly = true;
            }
            return cellProperties;
        },
        afterChange: function (change, source) {
            if (source === 'loadData') {
                return;
            }
            options.data.forEach(function (item, index, array) {
                if (index === change[0][0]) {
                    item[change[0][1]] = change[0][3];
                    array[index] = item;
                    options.updata = array;  //TODO  更新数据
                    return;
                }
            });

        }
    };
    options.container = document.getElementById(options.id);
    options.handsonTable = new Handsontable(options.container, options.config);
    return options;
}  */
</script>
</html>
