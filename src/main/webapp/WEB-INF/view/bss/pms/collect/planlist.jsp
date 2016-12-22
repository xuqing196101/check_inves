<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<script type="text/javascript">
			/*分页  */
			$(function() {
				laypage({
					cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
					pages: "${info.pages}", //总页数
					skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
					total: "${info.total}",
					startRow: "${info.startRow}",
					endRow: "${info.endRow}",
					skip: true, //是否开启跳页
					groups: "${info.pages}" >= 3 ? 3 : "${info.pages}", //连续显示分页数
					curr: function() { //通过url获取当前页，也可以同上（pages）方式获取
						//			        var page = location.search.match(/page=(\d+)/);
						//			        return page ? page[1] : 1;
						return "${info.pageNum}";
					}(),
					jump: function(e, first) { //触发分页后的回调
						if(!first) { //一定要加此判断，否则初始时会无限刷新
							$("#page").val(e.curr);
							$("#add_form").submit();

							<%--  location.href = '<%=basePath%>
							purchaser / list.do ? page = '+e.curr; --%>
						}
					}
				});
			});

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

			//下载
			function down() {
				var id = [];
				$('input[name="chkItem"]:checked').each(function() {
					id.push($(this).val());
				});
				if(id.length == 1) {
					window.location.href = "${pageContext.request.contextPath }/set/excel.html?id=" + id;
				} else if(id.length > 1) {
					layer.alert("只能选择一个", {
						offset: ['222px', '390px'],
						shade: 0.01
					});
				} else {
					layer.alert("请选中一条", {
						offset: ['222px', '390px'],
						shade: 0.01
					});
				}
			}

			//打印
			function print() {
				var id = [];
				$('input[name="chkItem"]:checked').each(function() {
					id.push($(this).val());
				});
				if(id.length == 1) {
					window.location.href = "${pageContext.request.contextPath }/look/print.html?id=" + id;
				} else if(id.length > 1) {
					layer.alert("只能选择一个", {
						offset: ['222px', '390px'],
						shade: 0.01
					});
				} else {
					layer.alert("请选中一条", {
						offset: ['222px', '390px'],
						shade: 0.01
					});
				}

			}

			var index;

			function sets() {
				var id = [];
				$('input[name="chkItem"]:checked').each(function() {
					id.push($(this).val());
				});
				if(id.length == 1) {
					index = layer.open({
						type: 1, //page层
						area: ['300px', '200px'],
						title: '审核设置',
						closeBtn: 1,
						shade: 0.01, //遮罩透明度
						moveType: 1, //拖拽风格，0是默认，1是传统拖动
						shift: 1, //0-6的动画形式，-1不开启
						offset: ['80px', '600px'],
						content: $('#content'),
					});
				} else if(id.length > 1) {
					layer.alert("只能选择一个", {
						offset: ['222px', '390px'],
						shade: 0.01
					});
				} else {
					layer.alert("请选中一条", {
						offset: ['222px', '390px'],
						shade: 0.01
					});
				}
			}
			
			//审核
			function audit() {
				var id = [];
				$('input[name="chkItem"]:checked').each(function() {
					id.push($(this).val());
				});
				if(id.length == 1) {
					$.ajax({
						type: "POST",
						dataType: "json",
						url: "${pageContext.request.contextPath }/look/auditId.do?id=" + id,
						success: function(data) {
						 if(data!=1){
								layer.alert("请选择状态需要审核的", {
									offset: ['30%', '40%']
								});
							}
							if(data==1){ 
								window.location.href = "${pageContext.request.contextPath }/look/auditlook.html?id=" + id;
								}
							/* } */
						}
					});
					
				} else if(id.length > 1) {
					layer.alert("只能选择一个", {
						offset: ['222px', '390px'],
						shade: 0.01
					});
				} else {
					layer.alert("请选中一条", {
						offset: ['222px', '390px'],
						shade: 0.01
					});
				}
			}

			function closeLayer() {
				var type = $("#wtype").val();
				var id = [];
				$('input[name="chkItem"]:checked').each(function() {
					id.push($(this).val());
				});
				if(type==""||type==null){
					layer.alert("您未选择，请重新选择", {
						offset: ['222px', '390px'],
						shade: 0.01
					});
					layer.close(index);
				}
				else if(type == 4) {
					window.location.href = "${pageContext.request.contextPath }/look/audit.html?id=" + id+"&status=13";
				} else {
					window.location.href = "${pageContext.request.contextPath }/set/list.html?id=" + id + "&type=" + type;
				}
			}

			function cant() {
				layer.close(index);
			}

			function resetQuery() {
				$("#add_form").find(":input").not(":button,:submit,:reset,:hidden").val("").removeAttr("checked").removeAttr("selected");
			}
			
			function auditturns(obj){
				var vals=$(obj).val();
				var id=$('input[name="chkItem"]:checked').val();
				
				$.ajax({
					type: "POST",
					dataType: "json",
					url: "${pageContext.request.contextPath }/look/status.do",
					data:{
						id:id,
						auditTurns:vals
					},
					success: function(data) {
						if(data=='1'){
							$("#wtype option:first").prop("selected", 'selected');
							
							layer.alert("审核进行中", {
								offset: ['222px', '390px'],
								shade: 0.01
							});
							
							
							layer.close(index);
						}
					/* 	if(data==0){
							layer.alert("请选择状态为已编制为采购计划的审核", {
								offset: ['30%', '40%']
							});
							$(".layui-layer-shade").remove();
						}else if(data==1){ */
							// window.location.href = "${pageContext.request.contextPath }/look/auditlook.html?id=" + id;
						/* } */
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
					<li>
						<a href="#">首页</a>
					</li>
					<li>
						<a href="#">保障作业系统</a>
					</li>
					<li>
						<a href="#">采购计划管理</a>
					</li>
					<li class="active">
						<a href="#">采购计划列表</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>

		<!-- 录入采购计划开始-->
		<div class="container">
			<div class="headline-v2 fl">
				<h2>采购计划列表</h2>
			</div>
			<h2 class="search_detail">
    	<form id="add_form" action="${pageContext.request.contextPath }/look/list.html" class="mb0" method="post" >
					<input type="hidden" name="page" id="page">
	   			<ul class="demand_list">
			    	  <li>
				    		<label class="fl">采购计划名称：</label><span>
				  	  	<input type="text" name="fileName" value="${inf.fileName }"/> 
				    		</span>
				      </li>
				   		<li>
				    		<label class="fl">采购方式：</label><span>
				  	   	<input type="text" name="" value=""/>
				    	</span>
				      </li>
				      <li>
				    		<label class="fl">采购金额：</label><span>
				  	    <input type="text" name="budget" value="${inf.budget }"/> 
				    	</span>
				      </li>
			    </ul>
	   	 		<input class="btn fl" type="submit" value="查询" /> 
				<input class="btn fl" type="button" value="重置" onclick="resetQuery()"  />	
   		</form>
 		</h2>

			<div class="col-md-12 pl20 mt10">
				<button class="btn btn-windows check" onclick="sets()">审核设置</button>
				<button class="btn btn-windows check" onclick="audit()">审核</button>
				<button class="btn btn-windows input" onclick="down()">下载</button>
				<button class="btn btn-windows output" onclick="print()">打印</button>
			</div>
			<div class="content table_box">
				<table class="table table-bordered table-condensed table-hover table-striped">
					<thead>
						<tr>
							<th class="info w30"><input type="checkbox" id="checkAll" onclick="selectAll()" alt=""></th>
							<th class="info w50">序号</th>
							<th class="info">采购计划名称</th>
							<th class="info">预算总金额</th>
							<th class="info">汇总时间</th>
							<th class="info">状态</th>
						</tr>
					</thead>
					<c:forEach items="${info.list}" var="obj" varStatus="vs">
						<tr style="cursor: pointer;">
							<td class="tc w30">
							 <c:if test="${obj.status=='1' || obj.status==7 || obj.status==8 || obj.status==9 || obj.status==10 || obj.status==11 || obj.status==12 }">  
								<input type="checkbox" value="${obj.id }" name="chkItem" onclick="check()" alt="">
							  </c:if>
					          <c:if test="${obj.status!='1' }">
 					           </c:if>  
							</td>
							<td class="tc w50">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
							<td class="tc">${obj.fileName }</td>
							<td class="tc">
								<fmt:formatNumber>${obj.budget }</fmt:formatNumber>
							</td>
							<td class="tc">
								<fmt:formatDate value="${obj.createdAt }" />
							</td>
							<td class="tc">
								<c:if test="${obj.status=='1' }">
									待审核设置
								</c:if>
								<%-- <c:if test="${obj.status=='2' }">
									已审核
								</c:if> --%>
								<c:if test="${obj.status==12 }">
									三轮已审核
								</c:if>
									<c:if test="${obj.status==11 }">
									二轮已审核
								</c:if>
							  <c:if test="${obj.status==10 }">
								一轮已审核
								</c:if>
								
							<%-- 	<c:if test="${obj.status=='4' }">
									已下达
								</c:if> --%>
								<c:if test="${obj.status=='7' }">
									一轮审核设置完毕
								</c:if>
								<c:if test="${obj.status=='8' }">
									二轮审核设置完毕
								</c:if>
								<c:if test="${obj.status=='9' }">
									三轮审核设置完毕
								</c:if>
							</td>
						</tr>

					</c:forEach>

				</table>

				<div id="pagediv" align="right"></div>
			</div>
		</div>

		<div id="content" class="dnone" style="text-align: center;">
 
			<span style="padding-top:50px;">直接下达采购任务或者设置审核轮次</span> <select style="margin-top: 15px;" onchange="auditturns(this)"
			name="planType" id="wtype">
			<option value="">请选择</option>
			<option value="4">直接下达任务</option>
			<c:forEach items="${dic }" var="obj">
				<option value="${obj.id }">${obj.name }</option>
			</c:forEach>
		</select>
		<div class="col-md-12 col-sm-12 col-xs-12 tc mt10">
			    <button style="margin-top: 15px;" class="btn padding-left-10 padding-right-10 btn_back" onclick="closeLayer()">确定</button>
			    <button style="margin-top: 15px;" class="btn padding-left-10 padding-right-10 btn_back" onclick="cant()">取消</button>
		    </div>
		    
		 
		</div>

	</body>

</html>