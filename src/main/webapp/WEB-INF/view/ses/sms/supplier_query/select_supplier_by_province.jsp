<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML >
<html>

	<head>
		<%@ include file="../../../common.jsp"%>
		<script type="text/javascript">
			$(function() {
				laypage({
					cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
					pages: "${listSupplier.pages}", //总页数
					skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
					skip: true, //是否开启跳页
					total: "${listSupplier.total}",
					startRow: "${listSupplier.startRow}",
					endRow: "${listSupplier.endRow}",
					groups: "${listSupplier.pages}" >= 5 ? 5 : "${listSupplier.pages}", //连续显示分页数
					curr: function() { //通过url获取当前页，也可以同上（pages）方式获取
						/* var page = location.search.match(/page=(\d+)/);
						return page ? page[1] : 1; */
						return "${listSupplier.pageNum}";
					}(),
					jump: function(e, first) { //触发分页后的回调
						if(!first) { //一定要加此判断，否则初始时会无限刷新
							$("#page").val(e.curr);
							$("#form1").submit();
							/* location.href = '${pageContext.request.contextPath}/supplierQuery/findSupplierByPriovince.do?page=' + e.curr + "&address=" + encodeURI(encodeURI('${address}')); */
						}
					}
				});
			});

			function fanhui() {
				window.location.href = "${pageContext.request.contextPath}/supplierQuery/highmaps.html";
			}

			function chongzhi() {
				$("#supplierName").val('');
				/* $("#loginName").val(''); */
				$("#startDate").val('');
				$("#mobile").val('');
				$("#endDate").val('');
				$("#contactName").val('');
				$("#categoryIds").val('');
				$("#supplierTypeIds").val('');
				$("#category").val('');
				$("#supplierType").val('');
				$("#isProvisional").val('');
				$("#creditCode").val('');
				$("#orgName").val('');
				$("#status option:selected").removeAttr("selected");
				$("#address option:selected").removeAttr("selected");
				$("#businessNature option:selected").removeAttr("selected");
				/* var address = '${address}';
				address = encodeURI(address);
				window.location.href = "${pageContext.request.contextPath}/supplierQuery/findSupplierByPriovince.html?address=" + address; */
				$("#form1").submit();
			}

			$(function() {
				var optionStatus = $("#status").find("option");
				for(var i = 1; i < optionStatus.length; i++) {
					if("${supplier.status}" == $(optionStatus[i]).val()) {
						optionStatus[i].selected = true;
					}
				}
				var optionScore = $("#score").find("option");
				for(var i = 1; i < optionScore.length; i++) {
					if("${supplier.score}" == $(optionScore[i]).val()) {
						optionScore[i].selected = true;
					}
				}
			});
		</script>
		<script type="text/javascript">
			var key;

			function showCategory() {
				var zTreeObj;
				var zNodes;
				var setting = {
					async: {
						autoParam: ["id"],
						enable: true,
						url: "${pageContext.request.contextPath}/category/createtree.do",
						otherParam: {
							categoryIds: "${categoryIds}",
						},
						dataType: "json",
						type: "post",
					},
					check: {
						enable: true,
						chkboxType: {
							"Y": "s",
							"N": "s"
						}
					},
					callback: {
						beforeClick: beforeClickCategory,
						onCheck: onCheckCategory
					},
					data: {
						simpleData: {
							enable: true,
							idKey: "id",
							pIdKey: "parentId"
						}
					},
					view: {
						fontCss: getFontCss
					}
				};
				zTreeObj = $.fn.zTree.init($("#treeRole"), setting, zNodes);
				key = $("#key");
				key.bind("focus", focusKey)
					.bind("blur", blurKey)
					.bind("propertychange", searchNode)
					.bind("input", searchNode);

				var cityObj = $("#category");
				var cityOffset = $("#category").offset();
				$("#roleContent").css({
					left: cityOffset.left + "px",
					top: cityOffset.top + cityObj.outerHeight() + "px"
				}).slideDown("fast");
				$("body").bind("mousedown", onBodyDownOrg);
			}

			function focusKey(e) {
				if(key.hasClass("empty")) {
					key.removeClass("empty");
				}
			}

			function blurKey(e) {
				if(key.get(0).value === "") {
					key.addClass("empty");
				}
			}
			var lastValue = "",
				nodeList = [],
				fontCss = {};

			function clickRadio(e) {
				lastValue = "";
				searchNode(e);
			}

			function searchNode(e) {
				var zTree = $.fn.zTree.getZTreeObj("treeRole");
				var value = $.trim(key.get(0).value);
				var keyType = "name";
				if(key.hasClass("empty")) {
					value = "";
				}
				if(lastValue === value) return;
				lastValue = value;
				if(value === "") return;
				updateNodes(false);
				nodeList = zTree.getNodesByParamFuzzy(keyType, value);
				updateNodes(true);
			}

			function updateNodes(highlight) {
				var zTree = $.fn.zTree.getZTreeObj("treeRole");
				for(var i = 0, l = nodeList.length; i < l; i++) {
					nodeList[i].highlight = highlight;
					zTree.updateNode(nodeList[i]);
				}
			}

			function getFontCss(treeId, treeNode) {
				return(!!treeNode.highlight) ? {
					color: "#A60000",
					"font-weight": "bold"
				} : {
					color: "#333",
					"font-weight": "normal"
				};
			}

			function filter(node) {
				return !node.isParent && node.isFirstNode;
			}

			function beforeClickCategory(treeId, treeNode) {
				var zTree = $.fn.zTree.getZTreeObj("treeRole");
				zTree.checkNode(treeNode, !treeNode.checked, null, true);
				return false;
			}

			function onCheckCategory(e, treeId, treeNode) {
				var zTree = $.fn.zTree.getZTreeObj("treeRole"),
					nodes = zTree.getCheckedNodes(true),
					v = "";
				var rid = "";
				for(var i = 0, l = nodes.length; i < l; i++) {
					v += nodes[i].name + ",";
					rid += nodes[i].id + ",";
				}
				if(v.length > 0) v = v.substring(0, v.length - 1);
				if(rid.length > 0) rid = rid.substring(0, rid.length - 1);
				var cityObj = $("#category");
				cityObj.attr("value", v);
				$("#categoryIds").val(rid);
			}

			function onBodyDownOrg(event) {
				if(!(event.target.id == "menuBtn" || event.target.id == "roleSel" || event.target.id == "roleContent" || $(event.target).parents("#roleContent").length > 0)) {
					hideRole();
				}
			}

			function hideRole() {
				$("#roleContent").fadeOut("fast");
				$("body").unbind("mousedown", onBodyDownOrg);

			}
		</script>
		<script type="text/javascript">
			function beforeClick(treeId, treeNode) {
				var zTree = $.fn.zTree.getZTreeObj("treeSupplierType");
				zTree.checkNode(treeNode, !treeNode.checked, null, true);
				return false;
			}

			function onCheck(e, treeId, treeNode) {
				var zTree = $.fn.zTree.getZTreeObj("treeSupplierType"),
					nodes = zTree.getCheckedNodes(true),
					v = "";
				var rid = "";
				for(var i = 0, l = nodes.length; i < l; i++) {
					v += nodes[i].name + ",";
					rid += nodes[i].id + ",";
				}
				if(v.length > 0) v = v.substring(0, v.length - 1);
				if(rid.length > 0) rid = rid.substring(0, rid.length - 1);
				var cityObj = $("#supplierType");
				cityObj.attr("value", v);
				$("#supplierTypeIds").val(rid);
			}

			function showSupplierType() {
				var setting = {
					check: {
						enable: true,
						chkboxType: {
							"Y": "",
							"N": ""
						}
					},
					view: {
						dblClickExpand: false
					},
					data: {
						simpleData: {
							enable: true,
							idKey: "id",
							pIdKey: "parentId"
						}
					},
					callback: {
						beforeClick: beforeClick,
						onCheck: onCheck
					}
				};

				$.ajax({
					type: "GET",
					async: false,
					url: "${pageContext.request.contextPath}/supplierQuery/find_supplier_type.do?supplierId=''",
					dataType: "json",
					success: function(zNodes) {
						for(var i = 0; i < zNodes.length; i++) {
							if(zNodes[i].isParent) {

							} else {
								//zNodes[i].icon = "${ctxStatic}/images/532.ico";//设置图标  
							}
						}
						tree = $.fn.zTree.init($("#treeSupplierType"), setting, zNodes);
						tree.expandAll(true); //全部展开
					}
				});
				var cityObj = $("#supplierType");
				var cityOffset = $("#supplierType").offset();
				$("#supplierTypeContent").css({
					left: cityOffset.left + "px",
					top: cityOffset.top + cityObj.outerHeight() + "px"
				}).slideDown("fast");
				$("body").bind("mousedown", onBodyDownSupplierType);
			}

			function hideSupplierType() {
				$("#supplierTypeContent").fadeOut("fast");
				$("body").unbind("mousedown", onBodyDownSupplierType);

			}

			function onBodyDownSupplierType(event) {
				if(!(event.target.id == "menuBtn" || $(event.target).parents("#supplierTypeContent").length > 0)) {
					hideSupplierType();
				}
			}
			

			
			
			
		//撤销
   /* 	function cancellation(){
   		var id = $(":radio:checked").val();
			var state = $("#" + id + "").parents("tr").find("td").eq(10).text().trim();
   		if(id != null){
   			if(state == "待审核" || state== "审核退回修改"){
   			layer.confirm('您确定要注销吗?', {title:'提示！',offset: ['200px']}, function(index){
   	 			layer.close(index);
   	 			$.ajax({
   	 				url:"${pageContext.request.contextPath}/supplierQuery/cancellation.html",
   	 				data:"supplierId=" + id,
   	 				type:"post",
   	 	      	success:function(){
   	 	       		layer.msg("注销成功!",{offset : '100px'});
   	 		      	window.setTimeout(function(){
   	 		      		$("#form1").submit();
   	 		       		}, 1000);
   	 	       		},
   	 	       		error: function(){
   	 							layer.msg("注销失败！",{offset : '100px'});
   	 					}
   	 	     });
   	 		});
   		}else{
   			layer.alert("只有【待审核】或【审核退回修改】的供应商才能注销！",{offset : '100px'});
      	}
   		}else{
   			layer.msg("请选择供应商！",{offset : '100px'});
   		}
   		
   	} */
   	
   		//禁用F12键及右键
  		 /* function click(e) {
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
			}; */
			
			
			/**重置密码*/
	 		/* function resetPwd(){
 	   		var id = $(":radio:checked").val();
        if(id !=null){
     	  	$.ajax({
	          url: "${pageContext.request.contextPath}/user/setPassword.do",
	          data: {"typeId":id},
	          type: "post",
	          dataType: "json",
	          success: function(data){
	      	    if("sccuess" == data){
	              layer.alert("重置成功！默认密码：123456",{offset: '100px'});
	                 }else{
	               	   layer.msg("重置失败！",{offset: '100px'});
	                 }
	               }
            	});
        	}else{
            layer.msg("请选择供应商！",{offset: '100px'});
        	}
	 		} */
	 		
	 		//窗口
	 		function openDiy(){
				layer.open({
				  type: 1,
				  title: 'DIY',
				  area: ['840px', '400px'],
				  closeBtn: 1,
				  shade:0.01, //遮罩透明度
				  moveType: 1, //拖拽风格，0是默认，1是传统拖动
				  shift: 1, //0-6的动画形式，-1不开启
				  offset: '10px',
				  shadeClose: false,
				  content: $("#openDiv"),
				});
			}
		</script>
	</head>
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
				<li class="active">
					<a href="javascript:void(0);" onclick="jumppage('${pageContext.request.contextPath}/supplierQuery/findSupplierByPriovince.html?sign=1')">供应商列表</a>
				</li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>
	<div id="roleContent" class="roleContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
		<input type="text" id="key" value="" class="empty" /><br/>
		<ul id="treeRole" class="ztree" style="margin-top:0;"></ul>
	</div>
	<div id="supplierTypeContent" class="supplierTypeContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
		<ul id="treeSupplierType" class="ztree" style="margin-top:0;"></ul>
	</div>

	<body>
		<div class="container">
			<div class="headline-v2">
				<h2>供应商信息</h2>
			</div>
			<h2 class="search_detail">
	      <form id="form1" action="${pageContext.request.contextPath}/supplierQuery/findSupplierByPriovince.html?sign=${sign}" method="post" class="mb0">
	      	<c:if test="${sign != 1 }">
	      		<input type="hidden" name="address" value="${address }">
	      	</c:if>
	        <input type="hidden" name="page" id="page">
	        <ul class="demand_list">
	        	<li>
	          	<label class="fl">供应商名称：</label><span><input class="w220" id="supplierName" name="supplierName" value="${supplier.supplierName }" type="text"></span>
	          </li>
	          <%-- <li>
	          	<label class="fl">用户名：</label><span><input class="w220" id="loginName" name="loginName" value="${supplier.loginName }" type="text"></span>
	          </li> --%>
	          <%-- <li>
	            <label class="fl">联系人：</label><span><input class="w220" id="contactName" name="contactName" value="${supplier.contactName }" type="text"></span>
	          </li>
	          <li>
							<label class="fl">手机号：</label>
							<input id="mobile" class="w220" name="mobile" value="${supplier.mobile }" type="text">
						</li> --%>
            <li>
            	<label class="fl">企业性质：</label>
	            <select name="businessNature" id="businessNature" class="w220">
	              <option value=''>全部</option>
	              <c:forEach items="${businessNature}" var="list">
	              	<option <c:if test="${supplier.businessNature eq list.id }">selected</c:if> value="${list.id }">${list.name }</option>
	              </c:forEach>
	            </select>
         	  </li>
         	  <li>
              <label class="fl">供应商类型：</label><span><input  class="w220" id="supplierType" class="span2 mt5" type="text" name="supplierType"  readonly value="${supplierType }" onclick="showSupplierType();" />
              <input   type="hidden" name="supplierTypeIds"  id="supplierTypeIds" value="${supplierTypeIds }" /></span>
            </li>
	          <li>
              <label class="fl">供应商状态：</label>
              <span>
                <select id="status" name="status" class="w220">
	                <option  selected="selected" value=''>全部</option>
	                <option value="-1">暂存</option>
	                <option value="0">待审核</option>
									<option value="-3">公示中</option>
									<option value="1">审核通过</option>
									<option value="2">退回修改</option>
									<option value="3">审核未通过</option>
									<option value="4">待复核</option>
									<option value="5">复核通过</option>
									<option value="6">复核未通过</option>
									<option value="7">待考察</option>
									<option value="8">考察合格</option>
									<option value="9">考察不合格</option>
                </select>
              </span>
            </li>
            <%-- <li>
              <label class="fl">品目：</label><span><input id="category" type="text" name="categoryNames" value="${categoryNames }" readonly onclick="showCategory();" class="w220"/>
              <input type="hidden" name="categoryIds"  id="categoryIds" value="${categoryIds }" /></span>
            </li> --%>
	          <!-- <li>
			        <label class="fl">供应商级别:</label>
			        	<span>
			          	<select id="score" name="score" class="w220">
	                  <option  selected="selected" value=''>-请选择-</option>
	                  <option  value="1">一级</option>
	                  <option  value="2">二级</option>
	                  <option  value="3">三级</option>
	                  <option  value="4">四级</option>
	                  <option  value="5">五级</option>
			            </select>
			       	 </span>
	      		</li> -->
	          <li>
            	<label class="fl">临时供应商：</label>
	            <select name="isProvisional" id="isProvisional" class="w220">
	              <option value=''>全部</option>
	              <option value='1' <c:if test="${supplier.isProvisional eq '1' }">selected</c:if>>是</option>
	              <option value='0' <c:if test="${supplier.isProvisional eq '0' }">selected</c:if>>否</option>
	            </select>
	         	</li>
	         	<li>
	          	<label class="fl">社会信用代码：</label><span><input class="w220" id="creditCode" name="creditCode" value="${supplier.creditCode }" type="text"></span>
	          </li>
	          <li>
              <label class="fl">采购机构：</label><span><input class="w220" id="orgName" name="orgName" value="${supplier.orgName }" type="text"></span>
            </li>
	          <c:if test ="${sign == 1 }">
              <li>
                <label class="fl">地区：</label>
                <select name="address" id="address" class="w220">
                  <option value=''>全部</option>
                  <c:forEach items="${privnce}" var="list">
                    <option <c:if test="${supplier.address eq list.name }">selected</c:if> value="${list.name }">${list.name }</option>
                  </c:forEach>
                </select>
              </li>
            </c:if>
	        </ul>
	          <div class="col-md-12 clear tc mt10">
            	<button type="button" onclick="submit()" class="btn">查询</button>
              <button type="reset" onclick="chongzhi()" class="btn">重置</button>
              <!-- <button type="reset" onclick="openDiy()" class="btn">自定义查询</button> -->
              <c:choose>
								<c:when test="${sign == 1 }">
								 		<a href="${pageContext.request.contextPath}/supplierQuery/highmaps.html" class="btn">切换到地图</a>
								</c:when>
								<c:otherwise>
										<button class="btn btn-windows back" type="button" onclick="location.href='${pageContext.request.contextPath}/supplierQuery/highmaps.html'">返回</button>
										<!-- <button class="btn btn-windows delete" type="button" onclick="cancellation();">注销</button> -->
								</c:otherwise>
							</c:choose>
							<!-- <button class="btn btn-windows edit" type="button" onclick="resetPwd()">重置密码</button> -->
							<!-- <button class="btn btn-windows delete" type="button" onclick="cancellation();">注销</button> -->
	          </div>
	          <div class="clear"></div>
	       </form>
     </h2>
			<div class="col-md-12 pl20 mt10">
				
			</div>

			<div class="content table_box">
				<table class="table table-bordered table-condensed table-hover table-striped">
					<thead>
						<tr>
							<!-- <th class="info w50">选择</th> -->
							<th class="info w50">序号</th>
							<th class="info" width="17%">供应商名称</th>
							<th class="info" width="10%">采购机构</th>
							<th class="info" width="8%">地区</th>
							<th class="info w90">注册日期</th>
							<th class="info w90">提交日期</th>
							<th class="info w90">审核日期</th>
							<th class="info" width="17%">供应商类型</th>
							<th class="info" width="10%">企业性质</th>
							<th class="info">供应商状态</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${listSupplier.list }" var="list" varStatus="vs">
							<tr>
								<%-- <td class="tc w30"><input type="radio" value="${list.id }" name="chkItem"  id="${list.id}"></td> --%>
								<td class="tc">${(vs.count)+(listSupplier.pageNum-1)*(listSupplier.pageSize)}</td>
								<td>
									<c:choose>
							       <c:when test="${list.status ==5 and list.isProvisional == 1 }">
							       	 <a href="javascript:jumppage('${pageContext.request.contextPath}/supplierQuery/temporarySupplier.html?supplierId=${list.id}&sign=${sign}')">${list.supplierName }</a>
							       </c:when>
							       <c:otherwise>
							       	 <a href="javascript:jumppage('${pageContext.request.contextPath}/supplierQuery/essential.html?supplierId=${list.id}&sign=${sign}')">${list.supplierName }</a>
							       </c:otherwise>
									</c:choose>
								</td>
								<td class="tl">${list.orgName}</td>
								<td class="tl">${list.name }</td>
								<td class="tc">
									<fmt:formatDate value="${list.createdAt }" pattern="yyyy-MM-dd" />
								</td>
								<td class="tc">
									<fmt:formatDate value="${list.submitAt }" pattern="yyyy-MM-dd" />
								</td>
								<td class="tc">
									<fmt:formatDate value="${list.auditDate }" pattern="yyyy-MM-dd" />
								</td>
								<td class="">${list.supplierType }</td>
								<td class="tc">${list.businessNature }</td>
								<td class="tc">
									<c:if test="${list.status==5 and list.isProvisional == 1 }"><span class="label rounded-2x label-dark">临时</span></c:if>
									<c:if test="${list.status==-1 }"><span class="label rounded-2x label-dark">暂存</span></c:if>
									<c:if test="${list.status==0 }"><span class="label rounded-2x label-dark">待审核</span></c:if>
									<c:if test="${list.status==-3 }"><span class="label rounded-2x label-u">公示中</span></c:if>
									<c:if test="${list.status==1 }"><span class="label rounded-2x label-u">审核通过</span></c:if>
									<c:if test="${list.status==2 }"><span class="label rounded-2x label-dark">退回修改</span></c:if>
									<c:if test="${list.status==3 }"><span class="label rounded-2x label-dark">审核未通过</span></c:if>
									<c:if test="${list.status==4 }"><span class="label rounded-2x label-dark">待复核</span></c:if>
									<c:if test="${list.status==5 and list.isProvisional == 0}"><span class="label rounded-2x label-u">复核通过</span></c:if>
									<c:if test="${list.status==6 }"><span class="label rounded-2x label-dark">复核未通过</span></c:if>
									<c:if test="${list.status==7 }"><span class="label rounded-2x label-dark">待考察</span></c:if>
									<c:if test="${list.status==8 }"><span class="label rounded-2x label-u">考察合格</span></c:if>
									<c:if test="${list.status==9 }"><span class="label rounded-2x label-dark">考察不合格</span></c:if>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div id="pagediv" align="right"></div>
			</div>
		</div>
		
		<div id="openDiv" class="dnone layui-layer-wrap" >
		  <form id="form2" action="${pageContext.request.contextPath}/supplierQuery/findSupplierByPriovince.html?sign=${sign}" method="post" class="mb0">
		  	<div class="drop_window">
		  		<input type="hidden" name="typeId" id="typeId" >
				  <ul class="demand_list">
	          <li>
	          	<label class="fl">联系人：</label><span><input class="w220" id="contactName" name="contactName" value="${supplier.contactName }" type="text"></span>
	          </li>
	          <li>
	          	<label class="fl">手机号：</label><span><input class="w220" id="mobile" name="mobile" value="${supplier.mobile }" type="text"></span>
	          </li>
	          <li>
	          	<label class="fl">注册日期：</label><span><input id="startDate" name="startDate" class="Wdate w110 fl" type="text"  value='<fmt:formatDate value="${supplier.startDate }" pattern="YYYY-MM-dd"/>' onFocus="var endDate=$dp.$('endDate');WdatePicker({onpicked:function(){endDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})"/>
	            <span class="f14">至</span>
	            <input id="endDate" name="endDate" value='<fmt:formatDate value="${supplier.endDate }" pattern="YYYY-MM-dd"/>' class="Wdate w100 fl" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})"/>
	            </span>
	          </li>
	          <li>
	          	<label class="fl">提交日期：</label><span><input id="startSubimtDate" name="startSubimtDate" class="Wdate w110 fl" type="text"  value='<fmt:formatDate value="${supplier.startSubimtDate }" pattern="YYYY-MM-dd"/>' onFocus="var endDate=$dp.$('startSubimtDate');WdatePicker({onpicked:function(){endDate.focus();},maxDate:'#F{$dp.$D(\'startSubimtDate\')}'})"/>
	            <span class="f14">至</span>
	            <input id="endSubimtDate" name="endSubimtDate" value='<fmt:formatDate value="${supplier.endSubimtDate }" pattern="YYYY-MM-dd"/>' class="Wdate w100 fl" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'endSubimtDate\')}'})"/>
	            </span>
	          </li>
	          <li>
	          	<label class="fl">审核日期：</label><span><input id="startAuditDate" name="startAuditDate" class="Wdate w110 fl" type="text"  value='<fmt:formatDate value="${supplier.startAuditDate }" pattern="YYYY-MM-dd"/>' onFocus="var endDate=$dp.$('startAuditDate');WdatePicker({onpicked:function(){endDate.focus();},maxDate:'#F{$dp.$D(\'startAuditDate\')}'})"/>
	            <span class="f14">至</span>
	            <input id="endAuditDate" name="endAuditDate" value='<fmt:formatDate value="${supplier.endAuditDate }" pattern="YYYY-MM-dd"/>' class="Wdate w100 fl" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'endAuditDate\')}'})"/>
	            </span>
	          </li>
				  </ul>
          <div class="tc col-md-12 col-sm-12 col-xs-12 mt10">
            <div class="col-md-12 clear tc mt10">
            	<button type="button" onclick="submit()" class="btn">查询</button>
            </div>
        	</div>
		    </div>
			 </form>
	  </div>
	</body>

</html>