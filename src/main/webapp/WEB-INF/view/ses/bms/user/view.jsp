<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
	<%@ include file="/WEB-INF/view/common.jsp" %>
</head>
<script type="text/javascript">
	function resetPaw(){
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
	}
	
	function ajaxOldPassword(){
		var is_error = 0;
		var userId = $("#userId").val();
		var oldPassord = $("#oldPassword").val();
		$.ajax({
           type: "GET",
           async: false, 
           url: "${pageContext.request.contextPath}/user/ajaxOldPassword.do?id="+userId+"&password="+oldPassord,
           dataType: "json",
           success: function(data){
                 if (!data.success) {
                 	is_error = 1;
					layer.msg(data.msg,{offset: ['150px']});
				 }
             }
       	});
       	return is_error;
	}
	
	function resetPasswSubmit(){
		var is_error = ajaxOldPassword();
		if (is_error == 1) {
			return false;
		} else {
			$.ajax({   
		            type: "POST",  
		            url: "${pageContext.request.contextPath}/user/resetPwd.html",        
		           	data : $('#form2').serializeArray(),
				    dataType:'json',
				    success:function(result){
				    	if(!result.success){
	                    	layer.msg(result.msg,{offset: ['150px']});
				    	}else{
				    		layer.closeAll();
				    		layer.msg(result.msg,{offset: ['222px']});
				    	}
	                },
	                error: function(result){
	                    layer.msg("重置失败",{offset: ['222px']});
	                }
		     });    
		}
	}
	
	function cancel(){
		layer.closeAll();
	}
</script>
<body>

<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <c:if test="${flag == 0}">
		   	<li><a href="javascript:void(0);"> 首页</a></li><li><a href="javascript:void(0);">个人中心</a></li><li><a href="javascript:void(0);">个人信息</a></li>
		   </c:if>
		   <c:if test="${flag == 1}">
			   <li><a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')" target="_blank"> 首页</a></li><li><a href="javascript:void(0);">支撑系统</a></li><li><a href="javascript:void(0);">后台管理</a></li><li class="active"><a href="javascript:void(0) " onclick="jumppage('${pageContext.request.contextPath}/user/list.html')">用户管理</a></li><li class="active"><a href="javascript:void(0);">查看用户</a></li>
		   </c:if>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   
   <div class="container bggrey border1 mt20">
   <form action="" method="post">
   <div>
   <h2 class="list_title">用户基本信息</h2>
   <div class="tag-box tag-box-v4 col-md-12">
   		<!-- <div class="mb10">	
   			<button class="btn" type="button" onclick="resetPaw()">重置密码</button>
   		</div> -->
	 	<table class="table table-bordered">
		 	<tbody>
		 		<tr>
		 			<td class="bggrey" width="15%">用户名：</td>
		 			<td width="35%">${user.loginName}</td>
		 			<td class="bggrey" width="15%">真实姓名：</td>
		 			<td width="35%">${user.relName}</td>
		 		</tr>
		 		<tr>
		 			<td class="bggrey">性别：</td>
		 			<td>
		 				<c:forEach items="${genders}" var="g" varStatus="vs">
					  		<c:if test="${g.id eq user.gender}">
					  			${g.name}
					  		</c:if>
			        	</c:forEach>
		 			</td>
		 			<td class="bggrey">手机：</td><td>${user.mobile }</td>
		 		</tr>
		 		<tr>
		 			<td class="bggrey">邮箱：</td><td>${user.email }</td>
		 			<td class="bggrey">职务：</td><td>${user.duties }</td>
		 		</tr>
		 		<%-- <tr>
		 			<td class="bggrey">类型：</td>
		 			<td>
		 				<c:forEach items="${typeNames}" var="t" varStatus="vs">
					  		<c:if test="${t.id eq user.typeName}">
					  			${t.name}
					  		</c:if>
			        	</c:forEach>
		 			</td>
		 			<td class="bggrey">所属机构：</td><td>${user.org.name }</td>
		 		</tr> --%>
		 		<tr>
		 			<td class="bggrey">创建日期：</td><td><fmt:formatDate value='${user.createdAt}' pattern='yyyy-MM-dd  HH:mm:ss'/></td>
		 			<td class="bggrey">修改日期：</td><td><fmt:formatDate value='${user.updatedAt}' pattern='yyyy-MM-dd  HH:mm:ss'/></td>
		 		</tr>
		 		<tr>
		 			<td class="bggrey">座机：</td><td>${user.telephone}</td>
		 			<td class="bggrey">发布类型：</td>
		 			<td >
		 				<c:if test="${user.publishType == '1' }">部队采购</c:if>
		 				<c:if test="${user.publishType == '0' }">集中采购</c:if>
		 			</td>
		 		</tr>
		 		<tr>
		 			<td class="bggrey">身份证号：</td><td>${user.idNumber}</td>
		 			<td class="bggrey">军官证号：</td><td>${user.officerCertNo}</td>
		 		</tr>
		 		<tr>
		 			<td class="bggrey">机构类型：</td>
		 			<td>
							        	<c:if test="${user.typeName == '1'}">采购机构</c:if>
						        		<c:if test="${user.typeName == '2'}">采购管理部门</c:if>
						        		<c:if test="${user.typeName == '0'}">需求部门</c:if>
						        		<c:if test="${user.typeName == '4'}">资源服务中心</c:if>
						        		<c:if test="${user.typeName == '5'}">监管部门</c:if>
						        		<c:if test="${user.typeName == '3'}">其他</c:if>
		 			</td>
		 			<td class="bggrey">单位：</td>
		 			<td colspan="3">
		 				<c:if test="${user.org == null }">${user.orgName}</c:if>
		 				<c:if test="${user.org != null && user.org.fullName != null && user.org.fullName != ''}">
					  		${user.org.fullName}
					  	</c:if>
					  	<c:if test="${user.org != null && (user.org.fullName == null || user.org.fullName == '')}">
					  		${user.org.name}
					  	</c:if>
		 			</td>
		 		</tr>
		 		<tr>
		 			
		 			<td class="bggrey">角色：</td><td colspan="3">${roleName}</td>
		 		</tr>
		 		<tr>
		 			<td class="bggrey w160">详细地址：</td><td colspan="3">${user.address}</td>
		 		</tr>
		 	</tbody>
	 	</table>
   </div>
  </div> 
  <c:if test="${flag == 1}">
	  <div class="col-md-12 col-sm-12 col-xs-12 tc" >
	    	<button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
	  </div>
  </c:if>
  </form>
 </div>
 <div id="openDiv" class="dnone layui-layer-wrap" >
	  <form id="form2" method="post" >
	  	<div class="drop_window">
	  		  <input type="hidden" name="id" id="userId" value="${user.id}">
			  <ul class="list-unstyled">
			  	  <div class="col-md-6 col-sm-6 col-xs-12 pl15">
	                <label class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><a class="star_red">*</a>输入原密码：</label> 
	                <div class="col-md-12 col-sm-12 col-xs-12 input-append input_group p0">
	                 	<input type="password" id="oldPassword" name="oldPassword" type="text" onblur="ajaxOldPassword()">
	                </div>
	              </div>
	              <div class="clear">
	              </div>
	          	  <div class="col-md-6 col-sm-6 col-xs-12 pl15">
	                <label class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><a class="star_red">*</a>输入新密码：</label> 
	                <div class="col-md-12 col-sm-12 col-xs-12 input-append input_group p0">
	                 	<input type="password" id="password" name="password" type="password">
	                </div>
	              </div>
	              <div class="col-md-6  col-sm-6 col-xs-12 ">
	                <label class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><a class="star_red">*</a>确认新密码：</label> 
	                <div class="col-md-12 col-sm-12 col-xs-12 input-append input_group p0">
	                  <input type="password" id="password2" name="password2"  class="">
	                </div>
	              </div>
			  </ul>
              <div class="tc col-md-12 col-sm-12 col-xs-12 mt10">
                <input class="btn" id="inputb" name="addr" onclick="resetPasswSubmit();" value="确定" type="button"> 
				<input class="btn" id="inputa" name="addr" onclick="cancel();" value="取消" type="button"> 
              </div>
		    </div>
		 </form>
	  </div>
</body>
</html>
