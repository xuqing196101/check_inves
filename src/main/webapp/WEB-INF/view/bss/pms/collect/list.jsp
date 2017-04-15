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

			function view(no) {
				window.location.href = "${pageContext.request.contextPath }/purchaser/queryByNo.html?planNo=" + no + "&&type=1";
			}

			function edit() {
				var id = [];
				$('input[name="chkItem"]:checked').each(function() {
					id.push($(this).val());
				});
				if(id.length == 1) {
					window.location.href = "${pageContext.request.contextPath }/purchaser/queryByNo.html?planNo=" + id + "&&type=2";;
				} else if(id.length > 1) {
					layer.alert("只能选择一个", {
						offset: ['222px', '390px'],
						shade: 0.01
					});
				} else {
					layer.alert("请选择需要修改的版块", {
						offset: ['222px', '390px'],
						shade: 0.01
					});
				}
			}

			function del() {
				var id = [];
				$('input[name="chkItem"]:checked').each(function() {
					id.push($(this).val());
				});
				if(id.length > 0) {
					layer.confirm('您确定要删除吗?', {
						title: '提示',
						offset: ['222px', '360px'],
						shade: 0.01
					}, function(index) {
						layer.close(index);
						$.ajax({
							url: "${pageContext.request.contextPath }/purchaser/delete.html",
							type: "post",
							data: {
								planNo: $('input[name="chkItem"]:checked').val()
							},
							success: function() {
								window.location.reload();

							},
							error: function() {

							}
						});
					});
				} else {
					layer.alert("请选择要删除的版块", {
						offset: ['222px', '390px'],
						shade: 0.01
					});
				}
			}
			var index;

			function add() {
				index = layer.open({
					type: 1, //page层
					area: ['300px', '200px'],
					title: '',
					closeBtn: 1,
					shade: 0.01, //遮罩透明度
					moveType: 1, //拖拽风格，0是默认，1是传统拖动
					shift: 1, //0-6的动画形式，-1不开启
					offset: ['80px', '600px'],
					content: $('#content'),
				});
			}

			//鼠标移动显示全部内容
			function out(content) {
				if(content.length > 10) {
					layer.msg(content, {
						icon: 6,
						shade: false,
						area: ['600px'],
						time: 1000 //默认消息框不关闭
					}); //去掉msg图标
				} else {
					layer.closeAll(); //关闭消息框
				}
			}

			function closeLayer() {
				var val = $("input[name='goods']:checked").val();
				window.location.href = "${pageContext.request.contextPath }/purchaser/add.html?type=" + val;
				layer.close(index);
			}

			function exports() {
				var id = [];
				$('input[name="chkItem"]:checked').each(function() {
					id.push($(this).val());
				});
				if(id.length == 1) {
					window.location.href = "${pageContext.request.contextPath }/purchaser/exports.html?planNo=" + id + "&&type=2";
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
			
			//受理
			function sub() {
				var id = [];
				$('input[name="chkItem"]:checked').each(function() {
					id.push($(this).val());
				});
				var status = $("input[name='chkItem']:checked").parents("tr").find("td").eq(7).text();
				if(id.length == 1) {
					if($.trim(status)=="待受理"){
						window.location.href = "${pageContext.request.contextPath }/accept/submit.html?planNo=" + id;
					}else{
						layer.alert("请选择待受理计划！", {
							offset: ['222px', '390px'],
							shade: 0.01
						});
					}
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
			
			//重置
			function resetQuery() {
				$("#add_form").find(":input").not(":button,:submit,:reset,:hidden").val("").removeAttr("checked").removeAttr("selected");
			}
			
			//下载
			function down() {
				var id = [];
				$('input[name="chkItem"]:checked').each(function() {
					id.push($(this).val());
				});
				if(id.length == 1) {
					window.location.href = "${pageContext.request.contextPath }/accept/excel.html?uniqueId=" + id;
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
		</script>

		
	</head>

	<body>
	 
		<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="#"> 首页</a>
					</li>
					<li>
						<a href="#">保障作业系统</a>
					</li>
					<li>
						<a href="#">采购计划管理</a>
					</li>
					<li class="active">
						<a href="#">采购需求受理</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<!-- 录入采购计划开始-->
		<div class="container">
			<div class="headline-v2 fl">
				<h2>采购需求列表
		  </h2>
			</div>
			<h2 class="search_detail">
    		<form id="add_form" action="${pageContext.request.contextPath }/accept/list.html" class="mb0" method="post" >
	 				<ul class="demand_list">
			    	<li>
				    	<label class="fl">采购需求名称：</label>
				    		<span>
				  	  		<input type="text" name="planName" value="${inf.planName }"/> 
				    	 		<input type="hidden" name="page" id="page">
				    		</span>
				    </li>
				    
				     <li>  	
					 <label class="fl">状态：</label>
			              <select  name="status" id="status">
			                <option selected="selected" value="total">全部</option>
			                <option value="2" <c:if test="${'2'==status}">selected="selected"</c:if>>待受理</option>
			                <option value="3" <c:if test="${'3'==status}">selected="selected"</c:if>>已受理 </option>
			                <option value="4" <c:if test="${'4'==status}">selected="selected"</c:if>>受理退回</option>
			              </select>
			          </li>
			          
			          
		  		</ul>
 					<input class="btn fl mt1" type="submit" value="查询" /> 
 					<input class="btn fl mt1" type="button" onclick="resetQuery()" value="重置"/>	
   			</form>
 			</h2>

			<div class="col-md-12 pl20 mt10">
				<button class="btn btn-windows git" onclick="sub()">受理</button>
			    <button class="btn btn-windows output" onclick="down()">下载打印</button>  
			</div>
			<div class="content table_box">
				<table class="table table-bordered table-condensed table-hover table-striped">
					<thead>
						<tr class="info">
							<th class="w30"><input type="checkbox" id="checkAll" onclick="selectAll()" alt=""></th>
							<th class="w50">序号</th>
							<th>需求部门</th>
							<th>采购需求名称</th>
							<th>编报人</th>
							<th>提交日期</th>
							<th>预算总金额（万元）</th>
							<th>状态</th>
						</tr>
					</thead>
					<c:forEach items="${info.list}" var="obj" varStatus="vs">
						<tr class="pointer tc">
							<td class="w30">
							<%-- 	<c:if test="${obj.status=='2' }"> --%>
									<input type="checkbox" value="${obj.uniqueId }" name="chkItem" onclick="check()" alt="">
								<%-- </c:if>
								<c:if test="${obj.status!='2' }">
								 
								</c:if> --%>
							</td>
							<td class="w50">${(vs.index+1)+(info.pageNum-1)*(info.pageSize)}</td>
							<td onclick="view('${obj.uniqueId}')" class="tl pl20">${obj.department}</td >
							<%-- <td class="tl pl20">
			    			<c:forEach items="${requires }" var="re" >
					  			<c:if test="${obj.department==re.id }"> ${re.name }</c:if>
			  				</c:forEach>
			 				</td> --%>
							<td onclick="view('${obj.uniqueId}')" class="tl pl20">${obj.planName }</td>
							<td onclick="view('${obj.uniqueId}')" class="tl pl20">${obj.userId }</td>
							<td  class="tc">
							<%-- <div class="left20" ><fmt:formatDate value="${obj.createdAt }"/></div> --%>
								 <fmt:formatDate value="${obj.createdAt }" pattern="yyyy-MM-dd" /> 
							</td>
							<td class="tr pr20">
							<%-- <div class="left20" fmt:formatDate >${obj.budget }</fmt:formatNumber></div> --%>

								<fmt:formatNumber type="number"  pattern="#,##0.00"  value="${obj.budget}"  />
							</td>
							<td class="tc">
							 
							    <c:if test="${obj.status=='2' }">
							 		待受理
							  	</c:if>
							  
							  	
							  	<c:if test="${obj.status=='4' }">
							 		受理退回
							  	</c:if>
							  	
							  	<c:if test="${obj.status=='3' || obj.status=='5' }">
							 		已受理
							  	</c:if>
							  	
							  	
							<%-- 	<c:if test="${obj.status=='4'||obj.status=='5'||obj.status=='6' }">
									已受理
								</c:if> --%>
							</td>
						</tr>
					</c:forEach>
				</table>
				<div id="pagediv" align="right"></div>
			</div>
		</div>
	</body>

</html>