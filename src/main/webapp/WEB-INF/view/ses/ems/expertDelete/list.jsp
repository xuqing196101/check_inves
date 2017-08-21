<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<script type="text/javascript" src="${pageContext.request.contextPath}/public/common/RSA.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/ses/bms/user/initPWD.js"></script>
		<title>专家列表</title>
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
					curr: function() { //合格url获取当前页，也可以同上（pages）方式获取
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
		</script>
		<script type="text/javascript">
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


			function trim(str){ //删除左右两端的空格
					return str.replace(/(^\s*)|(\s*$)/g, "");
				}

			//注销
			function cancellation() {
				var ids = $(":radio:checked").val();
				var state = $("#" + ids + "").parents("tr").find("td").eq(7).text();
				state = trim(state);
				if(ids != null) {
				if(state == "暂存" || state == "待初审" || state == "退回修改" || state=="临时"){
					layer.confirm('您确定要注销吗?', {
						title: '提示！',
						offset: ['200px']
					}, function(index) {
						/* if(state=="临时"){
								state = 1;
							} */
							layer.close(index);
						$.ajax({
							url: "${pageContext.request.contextPath}/expertDelete/cancellation.html",
							/* data: {"expertId" : ids, "sign" : state}, */
							data: {"expertId" : ids},
							type: "post",
							success: function() {
								layer.msg("注销成功!", {
									offset: '100px'
								});
								window.setTimeout(function() {
									$("#form1").submit();
								}, 1000);
							},
							error: function() {
								layer.msg("注销失败！", {
									offset: '100px'
								});
							}
						});
					});
				}else{
					layer.msg("该专家不能注销！", {
						offset: '100px'
					});
				}
				} else {
					layer.msg("请选择专家！", {
						offset: '100px'
					});
				}

			}
			
			//重置密码
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
            layer.msg("请选择专家！",{offset: '100px'});
        	}
	 		} */
	
			function resetForm() {
				$("input[name='relName']").val("");
				$("input[name='loginName']").val("");
				$("input[name='mobile']").val("");
				$("input[name='idCardNumber']").val("");
				$("#form1").submit();
			}
			
			
			
			//重置窗口
	 		function openResetPwd(){
				var id = $(":radio:checked").val();
				if(id !=null){
					$("#typeId").val(id);
					layer.open({
					  type: 1,
					  title: '重置密码',
					  area: ['270', '260px'],
					  closeBtn: 1,
					  shade:0.01, //遮罩透明度
					  moveType: 1, //拖拽风格，0是默认，1是传统拖动
					  shift: 1, //0-6的动画形式，-1不开启
					  offset: '150px',
					  shadeClose: false,
					  content: $("#openDiv"),
					});
				}else{
					layer.msg("请选择专家！",{offset: '100px'});
				}
			
			}
			
			
			//关闭重口
	 		function cancel(){
				layer.closeAll();
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
					
		//给密码输入错误次数超过5次被锁住的用户解锁
		function unlock(){
			var ids = $(":radio:checked").val();
			if(ids != null){
				$.ajax({  
	               type: "POST",  
	               url: "${pageContext.request.contextPath}/user/unlock.html?ids="+ids+"&type=expertOrSupplier",  
	               dataType: 'json',  
	               success:function(result){
	               		if(result.success){
	               			$("#"+ids).html('<span class="label rounded-2x label-u">正常</span>');
	                    	layer.msg(result.msg,{offset: '222px'});
	               		}else {
							layer.msg("解锁失败",{offset: '222px'});
						}
	                },
	                error: function(result){
	                    layer.msg("操作失败",{offset: '222px'});
	                }
	            });
			}else{
				layer.alert("请选择",{offset: '222px', shade:0.01});
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
						<a href="javascript:void(0);"> 首页</a>
					</li>
					<li>
						<a href="javascript:void(0);">支撑环境</a>
					</li>
					<li>
						<a href="javascript:void(0);">专家管理</a>
					</li>
					<li class="active">
						<a href="javascript:void(0);">专家注销</a>
					</li>
				</ul>
			</div>
		</div>
		<div class="container">
			<!-- 搜索 -->
			<h2 class="search_detail">
	      <form action="${pageContext.request.contextPath}/expertDelete/logoutList.html"  method="post" id="form1" class="mb0"> 
	      <input type="hidden" name="page" id="page">
	      <ul class="demand_list">
		      <li class="fl">
			      <label class="fl">专家姓名：</label> 
			      <input class="w220" name="relName" type="text" value="${expert.relName }">
		      </li>
		      <li class="fl">
			      <label class="fl">身份证号：</label> 
			      <input class="w220" name="idCardNumber" type="text" value="${expert.idCardNumber }">
		      </li>
		      <li class="fl">
			      <label class="fl">用户名：</label> 
			      <input class="w220" name="loginName" type="text" value="${expert.loginName }">
		      </li>
		      <li class="fl">
			      <label class="fl">手机号：</label> 
			      <input class="w220" name="mobile" type="text" value="${expert.mobile }">
		      </li>
	      </ul>
	      <div class="col-md-12 clear tc mt10">
	        <input type="submit" class="btn mt1" value="查询" />
				  <button onclick="resetForm();" class="btn mt1" type="button">重置</button>
				</div>
				  <div class="clear"></div>
	      </form>
    	</h2>
			<!-- 表格开始-->
			<div class="col-md-12 pl20 mt10">
				<button class="btn btn-windows check" type="button" onclick="cancellation();">注销</button>
				<button class="btn btn-windows edit" type="button" onclick="openResetPwd()">重置密码</button>
				<button class="btn btn-windows reset" type="button" onclick="unlock();">解锁</button>
			</div>
			<div class="content table_box">
				<table class="table table-bordered table-condensed table-hover hand">
					<thead>
						<tr>
							<th class="info w50">选择</th>
							<th class="info w50">序号</th>
							<th class="info">专家姓名</th>
							<th class="info">用户名</th>
							<th class="info w50">性别</th>
							<th class="info">手机号</th>
							<th class="info">账号状态</th>
							<th class="info w120">状态</th>
							<th class="info">类别</th>
							<th class="info w90">注册日期</th>
							<th class="info">身份证号</th>
						</tr>
					</thead>
					<c:forEach items="${result.list }" var="list" varStatus="page">
						<tr>
							<td class="tc w30"><input name="id" type="radio" value="${list.id}"></td>
							<td class="tc w50">${(page.count)+(result.pageNum-1)*(result.pageSize)}</td>
							<td class="tl">${list.relName }</td>
							<td class="tl">${list.loginName}</td>
							<td class="tc w50">${list.sex}</td>
							<td class="tc">${list.mobile }</td>
							<td class="tc" id="${list.id}">
							  	<c:if test="${list.errorNum >= 5}">
									<span class="label rounded-2x label-dark" >锁住</span>
								</c:if> 
								<c:if test="${list.errorNum < 5}">
									<span class="label rounded-2x label-u">正常</span>
								</c:if>
							</td>
							<td class="tl w120" id="${list.id}">
								<c:if test="${list.status eq '4' and list.isProvisional eq '1'}">
									<span class="label rounded-2x label-dark">临时</span>
								</c:if>
								<c:if test="${list.status eq '-1' and list.isSubmit eq '0'}">
									<span class="label rounded-2x label-dark">暂存</span>
								</c:if>
								<c:if test="${list.status eq '0' }">
									<span class="label rounded-2x label-dark">待初审</span>
								</c:if>
								<c:if test="${list.status eq '1' }">
									<span class="label rounded-2x label-u">初审合格</span>
								</c:if>
								<c:if test="${list.status eq '2' }">
									<span class="label rounded-2x label-dark">初审未合格</span>
								</c:if>
								<c:if test="${list.status eq '3' }">
									<span class="label rounded-2x label-dark">退回修改</span>
								</c:if>
								<c:if test="${list.status eq '-3'}">
									<span class="label rounded-2x label-dark">公示中</span>
								</c:if>
								<c:if test="${list.status eq '-2'}">
									<span class="label rounded-2x label-dark">预复审合格</span>
								</c:if>
								<c:if test="${list.status eq '4' and list.isProvisional eq '0'}">
									<span class="label rounded-2x label-u">复审合格</span>
								</c:if>
								<c:if test="${e.status eq '5' }">
									<span class="label rounded-2x label-dark">复审未合格</span>
								</c:if>
								<c:if test="${list.status eq '6' }">
									<span class="label rounded-2x label-dark">待复查</span>
								</c:if>
								<c:if test="${list.status eq '7' }">
									<span class="label rounded-2x label-u">复查合格</span>
								</c:if>
								<c:if test="${list.status eq '8' }">
									<span class="label rounded-2x label-dark">复查未合格</span>
								</c:if>
							</td>
							<td class="tc">${list.fromType }</td>
							<td class="tc">
                <fmt:formatDate value="${list.createdAt }" pattern="yyyy-MM-dd" />
              </td>
							<td class="tc">${list.idCardNumber}</td>
						</tr>
					</c:forEach>
				</table>
				<div id="pagediv" align="right"></div>
			</div>
		</div>
		
		
		<div id="openDiv" class="dnone layui-layer-wrap" >
		  <form id="form2" method="post" >
		  	<div class="drop_window">
		  		<input type="hidden" name="typeId" id="typeId" >
				  <ul class="list-unstyled">
       	  <div class="col-md-6 col-sm-6 col-xs-12 pl15">
	          <label class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><a class="star_red">*</a>输入新密码：</label> 
	          <div class="col-md-12 col-sm-12 col-xs-12 input-append input_group p0">
	           	<input type="password" name="password" id="password" maxlength="50">
	          </div>
          </div>
          	<div class="col-md-6  col-sm-6 col-xs-12 ">
            	<label class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><a class="star_red">*</a>确认新密码：</label> 
            	<div class="col-md-12 col-sm-12 col-xs-12 input-append input_group p0">
              	<input type="password" name="password2"  id="password2" maxlength="50">
            	</div>
          	</div>
				  </ul>
	          <div class="tc col-md-12 col-sm-12 col-xs-12 mt10">
	            <input class="btn" id="inputb" name="addr" onclick="supplierResetPasswSubmit();" value="确定" type="button"> 
							<input class="btn" id="inputa" name="addr" onclick="cancel();" value="取消" type="button"> 
	        	</div>
			    </div>
			 </form>
	  </div>
		
	</body>

</html>