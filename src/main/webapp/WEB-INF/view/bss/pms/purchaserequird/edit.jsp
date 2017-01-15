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
		    var val = $(obj).val();
		    $("select option").each(function() {
		      var opt = $(this).val();
		      if (val == opt) {
		        $(this).attr("selected", "selected");
		      }
		    });
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
<script type="text/javascript" src="http://code.jquery.com/jquery-1.6.1.min.js"></script>
<script src="${pageContext.request.contextPath}/public/backend/js/lock_table_head.js" ></script>


<!-- textarea 自适应高度js1 -->
<!--    <script type="text/javascript">
			var autoTextarea = function(elem, extra, maxHeight) {
				extra = extra || 0;
				var isFirefox = !!document.getBoxObjectFor || 'mozInnerScreenX' in window,
					isOpera = !!window.opera && !!window.opera.toString().indexOf('Opera'),
					addEvent = function(type, callback) {
						elem.addEventListener ?
							elem.addEventListener(type, callback, false) :
							elem.attachEvent('on' + type, callback);
					},
					getStyle = elem.currentStyle ? function(name) {
						var val = elem.currentStyle[name];

						if(name === 'height' && val.search(/px/i) !== 1) {
							var rect = elem.getBoundingClientRect();
							return rect.bottom - rect.top -
								parseFloat(getStyle('paddingTop')) -
								parseFloat(getStyle('paddingBottom')) + 'px';
						};

						return val;
					} : function(name) {
						return getComputedStyle(elem, null)[name];
					},
					minHeight = parseFloat(getStyle('height'));

				elem.style.resize = 'none';

				var change = function() {
					var scrollTop, height,
						padding = 0,
						style = elem.style;

					if(elem._length === elem.value.length) return;
					elem._length = elem.value.length;

					if(!isFirefox && !isOpera) {
						padding = parseInt(getStyle('paddingTop')) + parseInt(getStyle('paddingBottom'));
					};
					scrollTop = document.body.scrollTop || document.documentElement.scrollTop;

					elem.style.height = minHeight + 'px';
					if(elem.scrollHeight > minHeight) {
						if(maxHeight && elem.scrollHeight > maxHeight) {
							height = maxHeight - padding;
							style.overflowY = 'auto';
						} else {
							height = elem.scrollHeight - padding;
							style.overflowY = 'hidden';
						};
						style.height = height + extra + 'px';
						scrollTop += parseInt(style.height) - elem.currHeight;
						document.body.scrollTop = scrollTop;
						document.documentElement.scrollTop = scrollTop;
						elem.currHeight = parseInt(style.height);
					};
				};

				addEvent('propertychange', change);
				addEvent('input', change);
				addEvent('focus', change);
				change();
			};
	</script> -->
  
  
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
		<div class="content mt0 ul_list">
	
             <div class="content" id="content">
                 <table id="table" style="border-bottom-color: #dddddd; border-top-color: #dddddd; color: #333333; border-right-color: #dddddd; width:1600px; font-size: medium; border-left-color: #dddddd; max-width:10000px"
  border="1" cellspacing="0" cellpadding="0" class="table table-bordered table-condensed table_input left_table lockout">
					<thead>
						<tr class="space_nowrap" id="scroll_top">
							<th class="info w50">序号</th>
							<th class="info">需求部门</th>
							<th class="info">物资类别<br>及名称</th>
							<th class="info">规格型号</th>
							<th class="info">质量技术标准</br>（技术参数）</th>
							<th class="info">计量单位</th>
							<th class="info w100">采购数量</th>
							<th class="info">单位</br>（元）</th>
							<th class="info">预算金额</br>（万元）</th>
							<th class="info">交货期限</th>
							<th class="info">采购方式</br>建议</th>
							<th class="info">供应商名称</th>
							<th class="info">是否申请</br>办理免税</th>
					<!-- 		<th class="info">物资用途</br>（仅进口）</th>
							<th class="info">使用单位</br>（仅进口）</th> -->
							<th class="info">备注</th>
					<!-- 		<th class="w100">状态</th> -->
						</tr>
					</thead>
					<form   id="edit_form"  action="${pageContext.request.contextPath}/purchaser/update.html" method="post">
					<c:forEach items="${list }" var="obj" varStatus="vs">
						<tr style="cursor: pointer;">
                           <td class="tc w50">${obj.seq}  <input style="border: 0px;" type="hidden" name="list[${vs.index }].id" value="${obj.id }"></td>
                           <td class="tl"><%-- <input type="text" name="list[0].department" value="${obj.department}"> --%>
                          <div class="p0_5">${obj.department}</div>
                          <%--  <c:forEach items="${requires }" var="re" >
					         <c:if test="${obj.department==re.name }"> <input readonly='readonly' type="text"  value="${re.name}" > </c:if>
			               </c:forEach> --%>
                  </td>
                  <td class="tl ">
                  		<textarea name="list[${vs.index }].goodsName" class="target">${obj.goodsName}</textarea>
                  </td>
                  <td class="tl "><input type="text" name="list[${vs.index }].stand" value="${obj.stand}"></td>
                  <td class="tc "><input type="text" name="list[${vs.index }].qualitStand" value="${obj.qualitStand}"></td>
                  <td class="tl "><input type="text" name="list[${vs.index }].item" value="${obj.item}" class="tc"></td>
                  
                  <td class="tl ">
                    <c:if test="${obj.purchaseCount!=null}">
                      <input   type="hidden" name="ss"   value="${obj.id }" >
                      <input maxlength="11" class="tc" onblur="sum2(this);" type="text" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')" name="list[${vs.index }].purchaseCount"   value="${obj.purchaseCount}"/>
                      <input type="hidden" name="ss" value="${obj.parentId }">
                    </c:if>
                    <c:if test="${obj.purchaseCount==null }">
                      <input class="tc" type="text" name="list[${vs.index }].purchaseCount"   value="${obj.purchaseCount }">
                    </c:if>
                  </td>
                  <td class="tl ">
                    <c:if test="${obj.price!=null}">
                      <input   type="hidden" name="ss"   value="${obj.id }">
                      <input maxlength="11" class="tr"   name="list[${vs.index }].price"  onblur="sum1(this);"  value="${obj.price}" type="text" />
                      <input type="hidden" name="ss"   value="${obj.parentId }">
                    </c:if>
                    <c:if test="${obj.price==null}">
                      <input class="tr" readonly="readonly"   type="text" name="list[${vs.index }].price" value="${obj.price }">
                    </c:if>
                  </td>
                  <td>
                    <input   type="hidden" name="ss"   value="${obj.id }">
                    <input maxlength="11" id="budget" name="list[${vs.index }].budget" type="text" readonly="readonly"  value="${obj.budget}" class="tr"/>
                    <input type="hidden" name="ss"   value="${obj.parentId }">
                  </td>
                  <td class="tc"><textarea name="list[${vs.index }].deliverDate" class="target">${obj.deliverDate}</textarea></td>
                  <td class="tc">
             <%--       <c:if test="${obj.price!=null}"> --%>
                      <select name="list[${vs.index }].purchaseType"  <c:if test="${obj.price==null}"> onchange="sel(this);" </c:if>  style="width:100px" id="select">
                        <option value="">请选择</option>
                        <c:forEach items="${kind}" var="kind" >
                           <option value="${kind.id}" <c:if test="${kind.id == obj.purchaseType}">selected="selected" </c:if>> ${kind.name}</option>
                        </c:forEach>
                      </select> 
                  <%--    </c:if> --%>
                  </td>
                  <td class="tl "><textarea name="list[${vs.index }].supplier" class="target">${obj.supplier}</textarea></td>
                  <td class="tl "><input type="text" name="list[${vs.index }].isFreeTax" value="${obj.isFreeTax}"></td>
                 <%--  <td class="tl "><input type="text" name="list[${vs.index }].goodsUse" value="${obj.goodsUse}"></td>
                  <td class="tl "><input type="text" name="list[${vs.index }].userUnit" value="${obj.userUnit}"></td> --%>
                  <td class="tl "><div class="p0_5">${obj.memo}</div>
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
	<!-- textarea 自适应高度js2 --> 
		<script>
			var text = document.getElementsByClassName("target");
			for(var i=0;i<text.length;i++){
				autoTextarea(text[i]);
			}
			
		</script>
</body>
</html>
