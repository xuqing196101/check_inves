<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
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
					groups: "${result.pages}" >= 5 ? 5 : "${result.pages}", //连续显示分页数
					curr: function() { //通过url获取当前页，也可以同上（pages）方式获取
						//var page = location.search.match(/page=(\d+)/);
						//return page ? page[1] : 1;
						return "${result.pageNum}";
					}(),
					jump: function(e, first) { //触发分页后的回调
						if(!first) { //一定要加此判断，否则初始时会无限刷新
							$("#page").val(e.curr);
							$("#formSearch").submit();
						}
					}
				});
			});
			/** 全选全不选 */
			function selectAll() {
				var checklist = document.getElementsByName("check");
				var checkAll = document.getElementById("allId");
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

			//修改
			function edit() {
				var count = 0;
				var ids = document.getElementsByName("check");

				for(i = 0; i < ids.length; i++) {
					if(document.getElementsByName("check")[i].checked) {
						var id = document.getElementsByName("check")[i].value;
						var value = id.split(",");
						count++;
					}
				}
				if(count > 1) {
					layer.alert("只能选择一条记录", {
						offset: ['222px', '390px'],
						shade: 0.01
					});
				} else if(count < 1) {
					layer.alert("请选择一条记录", {
						offset: ['222px', '390px'],
						shade: 0.01
					});
				} else if(count == 1) {
					window.location.href = "${pageContext.request.contextPath}/expert/toEditBasicInfo.html?id=" + value[0];
				}
			}
			//删除
			function dell() {
				var count = 0;
				var ids = document.getElementsByName("check");
				var id2 = "";
				var num = 0;
				for(i = 0; i < ids.length; i++) {
					if(document.getElementsByName("check")[i].checked) {
						id2 += document.getElementsByName("check")[i].value + ",";
						num++;
					}
					//id.push(document.getElementsByName("check")[i].value);
					count++;
				}
				var id = id2.substring(0, id2.length - 1);
				if(num > 0) {
					layer.confirm('您确定要删除吗?', {
						title: '提示',
						offset: ['222px', '360px'],
						shade: 0.01
					}, function(index) {
						layer.close(index);
						$.ajax({
							url: "${pageContext.request.contextPath}/expert/deleteAll.html",
							data: {
								"ids": id
							},
							type: "post",
							success: function() {
								layer.msg('删除成功', {
									offset: ['222px', '390px']
								});
								window.setTimeout(function() {
									window.location.reload();
									for(var i = 0; i < info.length; i++) {
										info[i].checked = false;
									}
								}, 1000);
							},
							error: function() {
								layer.msg("删除失败", {
									offset: ['222px', '390px']
								});
							}
						});
					});
				} else {
					layer.alert("请选择一条记录", {
						offset: ['222px', '390px'],
						shade: 0.01
					});
				}

			}

			function clearSearch() {
				$("#relName").attr("value", "");
				//还原select下拉列表只需要这一句
				$("#expertsFrom option:selected").removeAttr("selected");
				$("#status option:selected").removeAttr("selected");
				$("#expertsTypeId option:selected").removeAttr("selected");
				$("#mobile").attr("value", "");
				$("#formSearch").submit();
			}
			//查看信息
			function view(id) {
				window.location.href = "${pageContext.request.contextPath}/expert/view.html?id=" + id;
			}

			function shenhe() {
				var count = 0;
				var ids = document.getElementsByName("check");

				for(i = 0; i < ids.length; i++) {
					if(document.getElementsByName("check")[i].checked) {
						var id = document.getElementsByName("check")[i].value;
						var value = id.split(",");
						count++;
					}
				}
				if(count > 1) {
					layer.alert("只能选择一条记录", {
						offset: ['222px', '390px'],
						shade: 0.01
					});
				} else if(count < 1) {
					layer.alert("请选择一条记录", {
						offset: ['222px', '390px'],
						shade: 0.01
					});
				} else if(count == 1) {
					if(value[1] == 0) {
						window.location.href = "${pageContext.request.contextPath}/expert/toShenHe.do?id=" + value[0];
					} else {
						layer.alert("请选择未审核的", {
							offset: ['222px', '390px'],
							shade: 0.01
						});
					}

				}
			}
			//诚信登记
			function creadible() {
				var id = $(":radio:checked").val();
				if(id == null) {
					layer.msg("请选择专家！", {offset: '100px'});
				} else {
					var state = $("#" + id + "").parents("tr").find("td").eq(9).text().trim();
					alert(state);
					if(state == "复审通过" || state == "复审未通过" || state == "待复查" || state == "复查通过" || state == "复查未通过") {
						index = layer.open({
							type: 2, //page层
							area: ['700px', '440px'],
							title: '诚信登记',
							shade: 0.01, //遮罩透明度
							moveType: 1, //拖拽风格，0是默认，1是传统拖动
							shift: 1, //0-6的动画形式，-1不开启
							offset: ['5px', '300px'],
							shadeClose: true,
							content: "${pageContext.request.contextPath}/credible/findAll.html?id=" + id,
								//数组第二项即吸附元素选择器或者DOM $('#openWindow')
						});
					} else {
						layer.msg("请选择入库的专家！", {offset: '100px'});
					}

				}
			}
			
			/**重置密码*/
	 		function resetPwd(){
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
            layer.msg("请选择专家！",{offset: '100px'});
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
		</script>
	</head>

	<body>
		<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="javascript:void(0)"> 首页</a>
					</li>
					<li>
						<a href="javascript:void(0)">支撑环境</a>
					</li>
					<li>
						<a href="javascript:void(0)">专家管理</a>
					</li>
					<li>
						<a href="javascript:void(0)">专家列表</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<!-- 我的订单页面开始-->
		<div class="container">
			<div class="headline-v2">
				<h2>专家列表</h2>
			</div>
			<h2 class="search_detail">  
   		<form action="${pageContext.request.contextPath}/expert/findAllExpert.html"  method="post" id="formSearch"  class="mb0"> 
		  	<input type="hidden" name="page" id="page">
		  	<input type="hidden" name="flag" value="0">
		  	<ul class="demand_list">
     		<li>
          <label class="fl">专家姓名：</label><span><input type="text" id="relName" name="relName" value="${expert.relName }"></span>
        </li>
        <li>
          <label class="fl">手机号：</label><span><input type="text" id="mobile" name="mobile" value="${expert.mobile }"></span>
        </li>
        <%-- <li>
        	<label class="fl">专家来源：</label>
          <span class="fl">
            <select  name="expertsFrom" id="expertsFrom" class="w178">
            	<option selected="selected" value="">-请选择-</option>
							<c:forEach items="${lyTypeList }" var="ly" varStatus="vs"> 
					    	<option <c:if test="${expert.expertsFrom eq ly.id }">selected="selected"</c:if> value="${ly.id}">${ly.name}</option>
							</c:forEach>
						</select>          
          </span>
        </li> --%>
        <li>
          <label class="fl">专家类型：</label>
          <span class="fl">
            <select name="expertsTypeId" id="expertsTypeId" class="">
              <option selected="selected"  value=''>全部</option>
              <c:forEach items="${expTypeList}" var="exp">
                <option <c:if test="${expert.expertsTypeId == exp.id}">selected</c:if> value="${exp.id}">${exp.name}</option>
              </c:forEach>          
            </select>
          </span>
        </li>
  			<li>
        <label class="fl">审核状态：</label>
        <span class="fl">
          <select name="status" id="status" class="">
             <option selected="selected" value=''>全部</option>
             <option <c:if test="${expert.status =='-1' }">selected</c:if> value="-1">暂存</option>
             <option <c:if test="${expert.status =='0' }">selected</c:if> value="0">待初审</option>
             <option <c:if test="${expert.status =='1' }">selected</c:if> value="1">初审通过</option>
             <option <c:if test="${expert.status =='2' }">selected</c:if> value="2">初审未通过</option>
             <option <c:if test="${expert.status =='3' }">selected</c:if> value="3">退回修改</option>
             <%-- <option <c:if test="${expert.status =='1' }">selected</c:if> value="1">待复审</option> --%>
             <option <c:if test="${expert.status =='4' }">selected</c:if> value="4">复审通过</option>
             <option <c:if test="${expert.status =='5' }">selected</c:if> value="5">复审未通过</option>
             <option <c:if test="${expert.status =='6' }">selected</c:if> value="6">待复查</option>
             <option <c:if test="${expert.status =='7' }">selected</c:if> value="7">复查通过</option>
             <option <c:if test="${expert.status =='8' }">selected</c:if> value="8">复查未通过</option>
           </select>
        </span>
     	 </li>
		  </ul>
		  <div class="col-md-12 clear tc mt10">
			  <input class="btn mt1"  value="查询" type="submit">
	      <input class="btn mt1" id="button" onclick="clearSearch();" value="重置" type="reset">
	    </div>  
    </form>
   </h2>
			<!-- 表格开始-->
			<div class="col-md-12 pl20 mt10">
				<%-- <button class="btn btn-windows edit" type="button" onclick="edit();">修改</button>
				<button class="btn btn-windows delete" type="button" onclick="dell();">删除</button>
			  <button class="btn btn-windows check" type="button" onclick="shenhe();">审核</button>--%>
				<button class="btn btn-windows git" type="button" onclick="creadible();">诚信登记</button>
				<button class="btn btn-windows edit" type="button" onclick="resetPwd()">重置密码</button>
			</div>

			<div class="content table_box">
				<table class="table table-bordered table-condensed table-hover table-striped">
					<thead>
						<tr>
							<th class="info w50">选择</th>
							<th class="info w50">序号</th>
							<th class="info">专家姓名</th>
							<th class="info">用户名</th>
							<th class="info w50">性别</th>
							<th class="info">类别</th>
							<th class="info">毕业院校及专业</th>
							<th class="info">手机</th>
							<th class="info">积分</th>
							<th class="info">审核状态</th>
						</tr>
					</thead>
					<c:forEach items="${result.list }" var="e" varStatus="vs">
						<tr class="pointer">
							<td class="tc w30"><input type="radio" name="check" id="checked" alt="" value="${e.id }"></td>
							<td class="tc w50" onclick="view('${e.id}');" class="tc w50">${(vs.index+1)+(result.pageNum-1)*(result.pageSize)}</td>
							<td class="tl pl20" onclick="view('${e.id}');">${e.relName}</td>
							<td class="tl pl20" onclick="view('${e.id}');">${e.loginName}</td>
							<td class="tc w50" onclick="view('${e.id}');" class="tc">${e.gender}</td>
							<td class="tl pl20" onclick="view('${e.id}');">${e.expertsTypeId}</td>
							<td class="tl pl20" onclick="view('${e.id}');">${e.graduateSchool }</td>
							<td class="tl pl20" onclick="view('${e.id}');">${e.mobile }</td>
							<td class="tc" onclick="view('${e.id}');" class="tc">${e.honestyScore }</td>
							<td onclick="view('${e.id}');" class="tc" id="${e.id}">
								<c:if test="${e.status eq '4' and e.isProvisional eq '1'}">
									<span class="label rounded-2x label-dark">临时</span>
								</c:if>
								<c:if test="${e.status eq '-1' and e.isSubmit eq '0'}">
									<span class="label rounded-2x label-dark">暂存</span>
								</c:if>
								<c:if test="${e.status eq '0' }">
									<span class="label rounded-2x label-dark">待初审</span>
								</c:if>
								<c:if test="${e.status eq '1' }">
									<span class="label rounded-2x label-u">初审通过</span>
								</c:if>
								<c:if test="${e.status eq '2' }">
									<span class="label rounded-2x label-dark">初审未通过</span>
								</c:if>
								<c:if test="${e.status eq '3' }">
									<span class="label rounded-2x label-dark">退回修改</span>
								</c:if>
								<%-- <c:if test="${e.status eq '1' }">
									<td onclick="view('${e.id}');" class="tc"><span class="label rounded-2x label-dark">待复审</span></td>
								</c:if> --%>
								<c:if test="${e.status eq '4' and e.isProvisional eq '0'}">
									<span class="label rounded-2x label-u">复审通过</span>
								</c:if>
								<c:if test="${e.status eq '5' }">
									<span class="label rounded-2x label-dark">复审未通过</span>
								</c:if>
								<c:if test="${e.status eq '6' }">
									<span class="label rounded-2x label-dark">待复查</span>
								</c:if>
								<c:if test="${e.status eq '7' }">
									<span class="label rounded-2x label-u">复查通过</span>
								</c:if>
								<c:if test="${e.status eq '8' }">
									<span class="label rounded-2x label-dark">复查未通过</span>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</table>
				<div id="pagediv" align="right"></div>
			</div>
		</div>
		
	</body>

</html>