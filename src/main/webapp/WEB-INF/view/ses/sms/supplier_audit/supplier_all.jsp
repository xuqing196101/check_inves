<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>


		<head>
			<%@ include file="/WEB-INF/view/common.jsp" %>
			<title>供应商列表</title>
			<script type="text/javascript">
				$(function() {
					laypage({
						cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
						pages: "${result.pages}", //总页数
						skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
						skip: true, //是否开启跳页
						total: "${result.total}",
						startRow: "${result.startRow}",
						endRow: "${result.endRow}",
						groups: "${result.pages}" >= 3 ? 3 : "${result.pages}", //连续显示分页数
						curr: function() { //通过url获取当前页，也可以同上（pages）方式获取
							return "${result.pageNum}";
						}(),
						jump: function(e, first) { //触发分页后的回调
							if(!first) { //一定要加此判断，否则初始时会无限刷新
								$("#page").val(e.curr);
								$("#form1").submit();
							}
						}
					});
				});
				
				//Ma Mingwei
				function trim(str){ //删除左右两端的空格
					return str.replace(/(^\s*)|(\s*$)/g, "");
				}
			
				/** 全选全不选 */
				function selectAll() {
					var checklist = document.getElementsByName("chkItem");
					var checkAll = document.getElementById("checkAll");
					if(checkAll.checked) {
						for(var i = 0; i < checklist.length; i++) {
							checklist[i].checked = true;
						}
					} else {
						for(var j = 0; j < checklist.length; j++) {
							checklist[j].checked = false;
						}
					}
				}

				/** 单选 */
				function check() {
					var count = 0;
					var checklist = document.getElementsByName("chkItem");
					var checkAll = document.getElementById("checkAll");
					for(var i = 0; i < checklist.length; i++) {
						if(checklist[i].checked == false) {
							checkAll.checked = false;
							break;
						}
						for(var j = 0; j < checklist.length; j++) {
							if(checklist[j].checked == true) {
								checkAll.checked = true;
								count++;
							}
						}
					}
				}
				//审核
				function shenhe(id) {
					if(id == null) {
						var size = $(":checkbox:checked").size();
						if(size == 0) {
							layer.msg("请选择供应商 !", {
								offset: '100px',
							});
							return;
						}
						var id = $(":checkbox:checked").val();
						if(size > 1){
							layer.msg("只能选择一项 !", {
								offset: '100px',
							});
							return;
						}
					}
					var state = $("#" + id + "").parents("tr").find("td").eq(10).text();//.trim();
					state = trim(state);
					/* var state = $("#"+id+"").text().trim(); */
					var isExtract = $("#" + id + "_isExtract").text();
					if(state == "公示中" || state == "审核通过" || state == "退回修改" || state == "审核未通过" || state == "复核通过" || state == "复核未通过" || state == "合格" || state == "不合格") {
						layer.msg("请选择待审核项 !", {
							offset: '100px',
						});
						return;
					}
					//抽取之后的才能复核
					/*   if(state == "未抽取"){
					 	layer.msg("该供应商未抽取 !", {
					     	offset : '100px',
					     });
					     return;
					 } */ 
					$.ajax({
							url: "${pageContext.request.contextPath}/supplierAudit/auditNotReason.do",
							data: {"supplierId" : id},
							success: function(result) {
							
								if(result == "" || result == null){
									layer.alert('点击审核项,弹出不通过理由框！', {
										title: '审核操作说明：',
										skin: 'layui-layer-molv', //样式类名
										closeBtn: 0,
										offset: '100px',
										shift: 4 //动画类型
									},
										function(){
											$("input[name='supplierId']").val(id);
											$("#shenhe_form_id").attr("action", "${pageContext.request.contextPath}/supplierAudit/essential.html");
											$("#shenhe_form_id").submit();
									});
									
								}else{
									layer.alert (result, {
											title: '上次未通过审核的原因：',
											skin: 'layui-layer-molv', //样式类名
											closeBtn: 0,
											offset: '100px',
											shift: 4 //动画类型
										},
										function(){
											$("input[name='supplierId']").val(id);
											$("#shenhe_form_id").attr("action", "${pageContext.request.contextPath}/supplierAudit/essential.html");
											$("#shenhe_form_id").submit();
									});
								}
							}
						});
					}
				
				
				
				/* $.ajax({
							url: "${pageContext.request.contextPath}/supplierAudit/auditNotReason.do",
							data: {"supplierId" : id},
							success: function(result) {
							alert(result);
							if(result != null){
								layer.alert(result, {
									title: '上次未通过审核的原因：',
									skin: 'layui-layer-molv', //样式类名
									closeBtn: 0,
									offset: '100px',
									shift: 4 //动画类型
								},
								function(){
									$("input[name='supplierId']").val(id);
									$("#shenhe_form_id").submit();
									aert("1");
								});
							}else{}
								$("input[name='supplierId']").val(id);
								$("#shenhe_form_id").submit();
								alert("2");
							}
						}); */
				
				//重置搜索栏
				function resetForm() {
					$("input[name='supplierName']").val("");
					$("input[name='auditDate']").val("");
					$("input[name='addressName']").val("");
					//还原select下拉列表只需要这一句-//但是这一句话不支持IE8即
					//$("#status option:selected").removeAttr("selected");
					//$("#businessNature option:selected").removeAttr("selected");
					//都改成js代码,测试IE8也行的通
					document.getElementById("status")[0].selected = true;
					document.getElementById("businessNature")[0].selected = true;
					$("#form1").submit();
				}
				
				//发布
				function publish(){
			  	var id = $(":checkbox:checked").val();
			  	var size = $(":checkbox:checked").size();
					var state = $("#" + id + "").parents("tr").find("td").eq(10).text();//.trim();
					state = trim(state);
					if(size == 1){
			  			if(state != "待审核" && state != "退回修改" && state != "审核未通过"){
			  	 			$.ajax({
			  	 				url:"${pageContext.request.contextPath}/supplierAudit/publish.html",
			  	 				data:"supplierId=" +id,
			  	 				type:"post",
			  	 				datatype:"json",
			  	 	      	success:function(result){
			  	 	      		result = eval("(" + result + ")");
			  	 	      		if(result == "yes"){
			  	 	      			layer.msg("发布成功!",{offset : '100px'});
			  	 	      			window.setTimeout(function(){
					       					$("#form1").submit();
				       					}, 1000);
			  	 	      		}else{
			  	 	      			layer.msg('该供应商已发布过！', {	            
							             shift: 6,
							             offset:'100px'
							          });
			  	 	      		}
			  	 	       	},
			  	 	       		error: function(){
			  	 							layer.msg("发布失败！",{offset : '100px'});
			  	 					}
			  	 	     });
			  		}else{
			  			layer.alert("只有入库供应商才能发布！",{offset : '100px'});
			     	}
			  		}else if(size > 1){
			  			layer.msg("只能选择一项！",{offset : '100px'});
			  		}else{
			  			layer.msg("请选择供应商！",{offset : '100px'});
			  		}
			  		
			  	}
			  	
			  	//禁用F12键及右键
		  		function click(e) {
					if (document.layers) {
							if (e.which == 3) {
							oncontextmenu='return false';
							}
						}
					}
					if (document.layers) {
						document.captureEvents(Event.MOUSEDOWN);
					}
					document.onmousedown=click;
					document.oncontextmenu = new Function("return false;");
					document.onkeydown =document.onkeyup = document.onkeypress=function(){ 
						if(window.event.keyCode == 123) { 
							window.event.returnValue=false;
							return(false); 
						} 
					};
			  	
			
			//下载审核/复核/意见函/考察表
			function downloadTable(str) {
				var size = $(":checkbox:checked").size();
				var id = $(":checkbox:checked").val();
				var state = $("#" + id + "").parents("tr").find("td").eq(10).text();//.trim();
        state = trim(state);
				if(size == 0) {
					layer.msg("请选供应商 !", {offset: '100px',});
				}else if(size > 1){
					layer.msg("只能选择一项 !", {offset: '100px',});
				}else{
				  if(state == "预审核结束" || state == "审核通过" || state == "退回修改" || state == "审核未通过" || state == "复核通过" || state == "复核未通过" || state=="合格" || state=="不合格"){
				    var id = $(":checkbox:checked").val();
	          $("input[name='supplierId']").val(id);
	          $("input[name='tableType']").val(str);
	          $("#shenhe_form_id").attr("action", "${pageContext.request.contextPath}/supplierAudit/downloadTable.html");
	          $("#shenhe_form_id").submit();
				  }else{
				    layer.msg("请选择审核过的供应商！", {offset: '100px',});
				  }
				}
			}
			
			//添加签字人员
			function tianjia() {
				var ids=[];
				$('input[type="checkbox"]:checked').each(function(i){ 
					ids.push($(this).val()); 
				});
				if(ids.length>0){
				$.ajax({
					url: "${pageContext.request.contextPath}/supplierAudit/signature.do?ids="+ids,
					type: "post",
					success: function(result) {
						if(result == "yes"){
							layer.open({
					      	type : 2,
					        title : '填写签字人员信息',
					        // skin : 'layui-layer-rim', //加上边框
					        area : [ '800px', '500px' ], //宽高
					        offset : '20px',
					        scrollbar : false,
					        content : '${pageContext.request.contextPath}/supplierAudit/addSignature.html?ids='+ids, //url
					        closeBtn : 1, //不显示关闭按钮
					     });
						}else{
							layer.msg(result+"已添加过！", {offset: '100px',});
								}
							}
					});
				}else{
					layer.msg("请选择供应商 !", {offset: '100px',});
					}	    	
		   };
			
				//入库申请表下载
				function downloadApplication(){
				  var size = $(":checkbox:checked").size();
	        if(size == 0) {
	          layer.msg("请选供应商 !", {offset: '100px',});
	        }else if(size > 1){
	          layer.msg("只能选择一项 !", {offset: '100px',});
	        }else{
	          var supplierId = $(":checkbox:checked").val();
		        $("#supplierJson").val(supplierId);
            $("#download_form").submit();
	        }
				}
			
		  </script>
		</head>

		<body>
			<!--面包屑导航开始-->
			<div class="margin-top-10 breadcrumbs ">
				<div class="container">
					<ul class="breadcrumb margin-left-0">
						<li>
							<a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a>
						</li>
						<li>
							<a href="javascript:void(0);">支撑系统</a>
						</li>
						<li>
							<a href="javascript:void(0);">供应商管理</a>
						</li>
						<c:choose>
							<c:when test="${sign == 3}">
								<li>
									<a href="javascript:void(0);" onclick="jumppage('${pageContext.request.contextPath}/supplierAudit/supplierAll.html?sign=3&status=5')">供应商实地考察</a>
								</li>
							</c:when>
							<c:otherwise>
								<li>
									<a href="javascript:void(0);" onclick="jumppage('${pageContext.request.contextPath}/supplierAudit/supplierAll.html?sign=1&status=0')">供应商审核</a>
								</li>
							</c:otherwise>
						</c:choose>

							<c:if test="${sign == 1}">
							<li class="active">
								<a href="javascript:void(0);" onclick="jumppage('${pageContext.request.contextPath}/supplierAudit/supplierAll.html?sign=1&status=0')">供应商审核列表</a>
							</li>
							</c:if>
							<c:if test="${sign == 2}">
							<li class="active">
								<a href="javascript:void(0);" onclick="jumppage('${pageContext.request.contextPath}/supplierAudit/supplierAll.html?sign=2&status=4')">供应商复核列表</a>
							</li>
							</c:if>
					</ul>
				</div>
			</div>
			<div class="container">
				<div class="headline-v2">
					<h2>供应商列表</h2>
				</div>
				<!-- 搜索 -->
				<h2 class="search_detail">
		      <form action="${pageContext.request.contextPath}/supplierAudit/supplierAll.html"  method="post" id="form1" class="mb0"> 
		      <input type="hidden" name="page" id="page">
		      <input type="hidden" name="sign" value="${sign}">
		      <ul class="demand_list">
		      <li class="fl">
			      <label class="fl">供应商名称：</label> 
			      <input class="w220" name="supplierName" type="text" value="${supplierName }">
		      </li>
		      <li class="fl">
			      <label class="fl">状态：</label> 
			      <select name="status" class="span2" id="status">
			        <option value="">全部</option>
		        	<c:if test="${sign eq '1' }">
		        		<option <c:if test="${state == 0 }">selected</c:if> value="0">待审核</option>
		        		<option <c:if test="${state == -2 }">selected</c:if> value="-2">预审核结束</option>
		            <option <c:if test="${state == -3 }">selected</c:if> value="-3">公示中</option>
		            <option <c:if test="${state == 1 }">selected</c:if> value="1">审核通过 </option>
		            <option <c:if test="${state == 2 }">selected</c:if> value="2">退回修改</option>
		            <option <c:if test="${state == 3 }">selected</c:if> value="3">审核未通过</option>
		        	</c:if>
		        	<c:if test="${sign eq '2' }">
		        		<option <c:if test="${state == 4 }">selected</c:if> value="4">待复核</option>
		            <option <c:if test="${state == 5 }">selected</c:if> value="5">复核通过</option>
		            <option <c:if test="${state == 6 }">selected</c:if> value="6">复核未通过 </option>
		        	</c:if>
		        	<c:if test="${sign eq '3' }">
		        	  <option <c:if test="${state == 5 }">selected</c:if> value="5">待考察</option>
		        		<option <c:if test="${state == 7 }">selected</c:if> value="7">合格</option>
		             <option <c:if test="${state == 8 }">selected</c:if> value="8">不合格</option>
		        	</c:if>
			      </select> 
		       </li>
		       <li class="fl">
				      <label class="fl">企业性质：</label> 
				        <select name="businessNature" id="businessNature" class="span2">
				          <option value="">全部</option>
				          <c:forEach var="business" varStatus="vs" items="${businessNatureList}">
				            <option <c:if test="${businessNature eq business.id }">selected</c:if> value="${business.id}">${business.name}</option>
				          </c:forEach>
				       </select> 
				    </li>
				    <li class="fl">
              <label class="fl">生产经营地址：</label> 
              <input class="w220" name="addressName" type="text" value="${addressName}">
            </li>
				    <li>
	          	<label class="fl">审核时间：</label>
	          		<span>
	          			<input id="auditDate" name="auditDate" class="Wdate w220 fl" value='<fmt:formatDate value="${auditDate}" pattern="YYYY-MM-dd"/>' type="text" onClick="WdatePicker()" />
		            </span>
	          </li>
				   <%-- <li class="fl">
				      <label class="fl">企业类型：</label> 
				        <select name="supplierType" class="mb0 mt5">
				          <option value="">全部</option>
				          <c:forEach var="type" varStatus="vs" items="${supplierType}">
				            <option value="${type.name}">${type.name}</option>
				          </c:forEach>
				       </select> 
				    </li> --%>
		        </ul>
		        <div class="col-md-12 clear tc mt10">
						  <input type="submit" class="btn" value="查询" />
						  <button onclick="resetForm();" class="btn" type="button">重置</button>
						</div>
					  <div class="clear"></div>
		      </form>
    		</h2>
				<!-- 表格开始-->
				<div class="col-md-12 pl20 mt10">
					<c:if test="${sign == 1 || sign == 2}"><button class="btn btn-windows check" type="button" onclick="shenhe();">审核</button></c:if>
					<c:if test="${sign == 3}"><button class="btn btn-windows check" type="button" onclick="shenhe();">考察</button></c:if>
					<c:if test="${sign == 2 or sign == 3}">
						<button class="btn btn-windows apply" type="button" onclick="publish();">发布</button>
					</c:if>
					<!-- 表格开始-->
					<c:if test="${sign == 3}">
						<a class="btn btn-windows input" onclick='downloadTable(1)' href="javascript:void(0)">下载考察记录表</a>
						<a class="btn btn-windows input" onclick='downloadTable(2)' href="javascript:void(0)">下载意见函</a>
						<button class="btn btn-windows add" type="button" onclick="tianjia();">添加签字人员</button>
					</c:if>
				  <c:if test="${sign == 1}">
					  <a class="btn btn-windows input" onclick='downloadTable(3)' href="javascript:void(0)">下载审核表</a>
					</c:if>
					<c:if test="${sign == 2}">
            <a class="btn btn-windows input" onclick='downloadTable(4)' href="javascript:void(0)">下载复核表</a>
          </c:if>
					<c:if test="${sign == 2 || sign == 3}">
					  <a class="btn btn-windows input" onclick="downloadApplication()" href="javascript:void(0)">下载入库申请表</a>
					</c:if>
					
				</div>
				<div class="content table_box">
					<table class="table table-bordered table-condensed table-hover hand">
						<thead>
							<tr>
								<th class="info w50">选择</th>
								<th class="info w50">序号</th>
								<th class="info">供应商名称</th>
								<th class="info">手机号</th>
								<th class="info">企业类型</th>
								<th class="info">企业性质</th>
								<th class="info">审核时间</th>
								<th class="info">提交时间</th>
								<th class="info">审核人</th>
								<th class="info">发布</th>
								<th class="info">状态</th>
							</tr>
						</thead>
						<c:forEach items="${result.list }" var="list" varStatus="page">
							<tr>
								<td class="tc w30"><input name="id" type="checkbox" value="${list.id}"></td>
								<td class="tc w50" onclick="shenhe('${list.id }');">${(page.count)+(result.pageNum-1)*(result.pageSize)}</td>
								<td class="tl"><a href="javascript:jumppage('${pageContext.request.contextPath}/supplierAudit/essential.html?supplierId=${list.id}&sign=${sign}')">${list.supplierName }</a></td>
								<td class="tc" onclick="shenhe('${list.id }');">${list.mobile }</td>
								<td class="tl" onclick="shenhe('${list.id }');">${list.supplierTypeNames}</td>
								<td class="tc" onclick="shenhe('${list.id }');">${list.businessNature}</td>
								<td class="tc" onclick="shenhe('${list.id }');">
									<fmt:formatDate value="${list.auditDate }" pattern="yyyy-MM-dd" />
								</td>
								<td class="tc">
                  <fmt:formatDate value="${list.submitAt}" pattern="yyyy-MM-dd" />
                </td>
								<td class="tc" onclick="shenhe('${list.id }');">
								  <c:choose>
			              <c:when test="${list.auditor ==null or list.auditor == ''}">无</c:when>
			              <c:otherwise>${list.auditor}</c:otherwise>
			            </c:choose>
								</td>
								<td class="tc" onclick="shenhe('${list.id }');">
									<c:if test="${list.isPublish == 1 }"><span class="label rounded-2x label-u">已发布</span></c:if>
									<c:if test="${list.isPublish == 0 }"><span class="label rounded-2x label-dark">未发布</span></c:if>
								</td>
								<td class="tc" id="${list.id}" onclick="shenhe('${list.id }');">
									<c:if test="${list.status == 0 and list.auditTemporary != 1}"><span class="label rounded-2x label-u">待审核</span></c:if>
									<c:if test="${list.status == 0 and list.auditTemporary == 1}"><span class="label rounded-2x label-u">审核中</span></c:if>
									<c:if test="${list.status == -3}"><span class="label rounded-2x label-dark">公示中</span></c:if>
									<c:if test="${list.status == -2}"><span class="label rounded-2x label-u">预审核结束</span></c:if>
									<c:if test="${list.status == 1}"><span class="label rounded-2x label-dark">审核通过</span></c:if>
									<c:if test="${list.status == 2}"><span class="label rounded-2x label-dark">退回修改</span></c:if>
									<c:if test="${list.status == 3}"><span class="label rounded-2x label-dark">审核未通过</span></c:if>
									<c:if test="${list.status == 4 and list.auditTemporary != 2}"><span class="label rounded-2x label-u">待复核</span></c:if>
									<c:if test="${list.status == 4 and list.auditTemporary == 2}"><span class="label rounded-2x label-u">复核中</span></c:if>
									<c:if test="${list.status == 5 and sign == 2}"><span class="label rounded-2x label-dark">复核通过</span></c:if>
									<c:if test="${list.status == 6}"><span class="label rounded-2x label-dark">复核未通过</span></c:if>
									<c:if test="${list.status == 5 and sign == 3 and list.auditTemporary != 3}"><span class="label rounded-2x label-u">待考察</span></c:if>
									<c:if test="${list.status == 5 and list.auditTemporary == 3}"><span class="label rounded-2x label-u">考察中</span></c:if>
									<c:if test="${list.status == 7}"><span class="label rounded-2x label-dark">合格</span></c:if>
									<c:if test="${list.status == 8}"><span class="label rounded-2x label-dark">不合格</span></c:if>
								</td>
								<%-- <td class="tc" id="${list.id }_isExtract" >${list.isExtract}</td> --%>
							</tr>
						</c:forEach>
					</table>
					<div id="pagediv" align="right"></div>
				</div>
			</div>
			<form id="shenhe_form_id" action="" method="post">
				<input name="supplierId" type="hidden"/>
				<input type="hidden" name="sign" value="${sign}">
				<input type="hidden" name="tableType">
			</form>
			
			<form action="${pageContext.request.contextPath}/expert/downloadSupplier.html" method="post" id="download_form">
        <input type="hidden" value="" name="supplierJson" id="supplierJson">
      </form>
	</body>

</html>