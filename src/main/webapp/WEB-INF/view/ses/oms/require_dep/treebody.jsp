<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>
<html class=" js cssanimations csstransitions" lang="en">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <script type="text/javascript">
    	function addUser(){
    		window.location.href= "${pageContext.request.contextPath}/purchaseManage/addUser.do?orgId="+selectedTreeId;
    	}
    	
    	function editUser(){
    		var ids = getSelectIds();
    		var len = ids.length;
    		var titles="";
    		if(ids.length != 1){
    			titles="请选择一条记录";
    			truealert(titles,5);
    			return;
    		}
    		window.location.href= "${pageContext.request.contextPath}/user/edit.do?origin=origin&userId=" + ids + "&orgId=" + selectedTreeId;  
    	}
    	
    	/** 删除用户 **/
    	function delUser(id){
    		var ids = getSelectIds();
    		var len = ids.length;
    		var titles="请选择需要删除的记录";;
    		if(len<=0){
    			truealert(titles,5);
    			return;
		    }
		    var idstr="";
		    for(var i=0;i<len;i++){
		    	idstr += ids[i];
		    	idstr += ",";
		    }
		    idstr = idstr.substr(0,idstr.length-1);
			$.ajax({
				type : 'post',
				url : "${pageContext.request.contextPath}/purchaseManage/deleteUser.do",
				data : {ids:idstr},
				success : function(data) {
					showRes(data.success);
				}
			});
    	}
    	
    	/** 获取选择的id **/
    	function getSelectIds(){
    		var array=[];
    		var arrc = $("#user input[type=checkbox]:checked");
    		for(var j=0;j<arrc.length;j++){
    			if(arrc[j].defaultValue!=undefined && arrc[j].defaultValue!=""){
    				array.push(arrc[j].defaultValue);
    			}
    		}
    		return array;
    		
    	}
    	
    	/** 全选 **/
    	function selectAll(){
			if ($("#allId").prop("checked")) {  
	            $("input[name=chkItem]").each(function() {  
	                $(this).prop("checked", true);  
	            });  
	        } else {  
	            $("input[name=chkItem]").each(function() {  
	                $(this).prop("checked", false);  
	            });  
	        }   
		}
    	
    	/** 提示 */
		function truealert(text,iconindex){
			if(top == null || top == "" || top == "underfined"){
			  top = 120;
			}
			layer.open({
			    content: text,
			    icon: iconindex,
			    offset: top+"px",
			    shade: [0.3, '#000'],
			    yes: function(index){
			        //do something
			    	 layer.closeAll();
			    }
			});
		}
		
		/** 删除成功后调用 */
		function showRes(res){
			if (res){
				layer.msg("删除成功");
				$("#user tbody input[type=checkbox]:checked").each(function(){
					$(this).parents('tr').remove();
				});
				calculateIndex();
			}
		}
		
		/** 计算下标 **/
		function calculateIndex(){
			var count = 0;
			$("#user tbody input[type=checkbox]").each(function(){
				count ++;
				$(this).parents('tr').find('td').eq(1).text(count);
			});
		}
</script>
</head>
<body>
  <input id="type_name" value="${orgnization.typeName }" type="hidden">
  <div class="tab-content">
	<div class="tab-pane fade active in" id="show_ztree_content">
	  <div class="panel-heading overflow-h margin-bottom-20 no-padding" id="ztree_title">
	    <h2 class="panel-title heading-sm pull-left">
		  <i class="fa fa-bars"></i> ${orgnization.name} <span class="label rounded-2x label-u">正常</span>
		</h2>
	  </div>
	  <div id="ztree_content">
	    <div class="tab-v2">
		  <div class="tab-content">
			<div class="tab-pane fade in active" id="dep_tab-0">
			  <div class="show_obj">
			    <table class="table table-striped table-bordered">
				  <tbody>
					<!-- 伪表单 跳转编辑页面 post传参数-->
					<form id="hform" action="${pageContext.request.contextPath}/purchaseManage/addUser.do" method="post">
					  <input type="hidden" id="defaultid" name="org.id" value="${orgnization.id }"/>
					  <input type="hidden" id="typeName" name="org.typeName" value="${orgnization.typeName }"/>
					</form>
					<!-- 伪表单-->
					<tr>
					  <td width="25%" class="bggrey tl">名称：</td>
					  <td width="25%">${orgnization.name }</td>
					  <td width="25%" class="bggrey tl">邮编：</td>
					  <td width="25%">${orgnization.shortName }</td>
					</tr>
					<tr>
					  <td width="25%" class="bggrey tl">地址：</td>
					  <td width="25%">${orgnization.address}</td>
					  <td width="25%" class="bggrey tl">电话：</td>
					  <td width="25%">${orgnization.mobile}</td>
					</tr>
				  </tbody>
				</table>
			  </div>
			  <div class="panel-heading overflow-h margin-bottom-20 no-padding" id="ztree_title">
			    <h2 class="panel-title heading-sm pull-left">
				  <i class="fa fa-bars"></i> ${orgnization.name }人员信息 <span class="label rounded-2x label-u">正常</span>
				</h2>
				<div class="pull-right">
				  <a class="btn btn-windows add" href="javascript:void(0)" onClick="addUser();"><i class="fa fa-search-plus"></i> 添加人员</a>
				  <a class="btn btn-windows edit" href="javascript:void(0)" onClick="editUser();"><i class="fa fa-wrench"></i> 修改人员</a>
				  <a class="btn btn-windows delete" href="javascript:void(0)" onClick="delUser();"><i class="fa fa-plus"></i> 删除人员</a>
				</div>
			  </div>
			  <div class="panel panel-grey clear mt5">
				<div class="panel-heading">
				  <h3 class="panel-title"><i class="fa fa-users"></i> ${orgnization.name }人员列表</h3>
				</div>
			  <div class="panel-body">
				<table class="table table-bordered table-condensed table-hover table-striped" id="user">
				  <thead>
					<tr>
					  <th><input type="checkbox" onclick="selectAll();" id="allId" alt="全选"/></th>
					  <th>序号</th>
					  <th>姓名</th>
					  <th>手机</th>
					  <th>电话</th>
					  <!-- <th>传真</th> -->
					  <th>详细地址</th>
					  <th>军网邮箱</th>
				    </tr>
				  </thead>
				  <tbody>
					<c:forEach items="${userlist}" var="u" varStatus="vs">
					  <tr class="cursor">
					    <!-- 选择框 -->
						<td class="tc"><input type="checkbox" name="chkItem" value="${u.id}" /></td>
						<!-- 姓名 -->
						<td class="tc">${vs.index+1}</td>
						<!-- 标题 -->
						<td class="tc">${u.relName}</td>
						<!-- 内容 -->
						<td class="tc">${u.mobile}</td>
						<!-- 创建人-->
						<td class="tc">${u.telephone}</td>
						<!-- 是否发布 -->
						<%-- <td class="tc" onclick="show('${u.id}');">${p.gender}</td> --%>
						<!-- 是否发布 -->
						<td class="tc">${u.address}</td>
						<!-- 是否发布 -->
						<td class="tc">${u.email}</td>
					  </tr>
					</c:forEach>
				  </tbody>
				</table>
			  </div>
			</div>
			<div class="panel-heading overflow-h margin-bottom-20 no-padding" id="ztree_title">
			  <h2 class="panel-title heading-sm pull-left">
				<i class="fa fa-bars"></i> 
				<c:choose>
				  <c:when test="${orgnization.typeName!=null && orgnization.typeName==0 }">管理部门信息</c:when>
				  <c:when test="${ orgnization.typeName!=null && orgnization.typeName==1 }">采购机构信息</c:when>
				  <c:otherwise>管理部门信息</c:otherwise>
				</c:choose> 
				<span class="label rounded-2x label-u">正常</span>
			  </h2>
			</div>
			<div class="panel panel-grey clear mt5">
			  <div class="panel-heading">
				<h3 class="panel-title">
				  <i class="fa fa-users"></i> 
				  <c:choose>
					<c:when test="${orgnization.typeName!=null && orgnization.typeName==0 }">管理部门信息</c:when>
					<c:when test="${orgnization.typeName!=null && orgnization.typeName==1 }">采购机构信息</c:when>
					<c:when test="${orgnization.typeName!=null && orgnization.typeName==2}">管理部门信息</c:when>
				  </c:choose> 
				</h3>
			  </div>
			  <div class="panel-body">
				<table class="table table-bordered table-condensed table-hover table-striped">
				  <thead>
					<tr>
					  <th>序号</th>
					  <th>名称</th>
					  <th>简称</th>
					  <th>组织机构代码</th>
					  <th>电话</th>
					  <th>所在地市</th>
					  <th>详细地址</th>
					  <th>邮编</th>
					  <th>传真</th>
					  <th>网站地址</th>
					  <th>负责人</th>
					  <th>监管负责人身份证号码</th>
					  <th>监管机构性质</th>
					</tr>
				  </thead>
				  <tbody>
					<c:forEach items="${oList}" var="p" varStatus="vs">
					  <tr class="cursor">
						<!-- 序号 -->
						<td class="tc" onclick="show('${p.id}');">${vs.index+1}</td>
						<!-- 标题 -->
						<td class="tc" onclick="show('${p.id}');">${p.name}</td>
						<!-- 内容 -->
						<td class="tc" onclick="show('${p.id}');">${p.shortName}</td>
						<!-- 创建人-->
						<td class="tc" onclick="show('${p.id}');">${p.orgCode}</td>
						<!-- 是否发布 -->
						<td class="tc" onclick="show('${p.id}');">${p.mobile}</td>
						<!-- 是否发布 -->
						<td class="tc" onclick="show('${p.id}');">${p.areaId}</td>
						<!-- 是否发布 -->
						<td class="tc" onclick="show('${p.id}');">${p.detailAddr}</td>
						<!-- 是否发布 -->
						<td class="tc" onclick="show('${p.id}');">${p.postCode}</td>
						<!-- 是否发布 -->
						<td class="tc" onclick="show('${p.id}');">${p.fax}</td>
						<!-- 创建人-->
						<td class="tc" onclick="show('${p.id}');">${p.website}</td>
						<!-- 是否发布 -->
						<td class="tc" onclick="show('${p.id}');">${p.princinpal}</td>
						<!-- 是否发布 -->
						<%-- <td class="tc" onclick="show('${p.id}');">${p.quaCode}</td> --%>
						<!-- 是否发布 -->
						<td class="tc" onclick="show('${p.id}');">${p.princinpalIdCard}</td>
						<!-- 是否发布 -->
						<td class="tc" onclick="show('${p.id}');">${p.nature}</td>
					  </tr>
					</c:forEach>
				  </tbody>
				</table>
			  </div>
			</div>
		  </div>
		  <!-- active 是展示 -->
		  <div class="tab-pane fade in" id="dep_tab-1">
			<div class="content-boxes-v2 space-lg-hor content-sm ">
			  <h2 class="heading-sm">
				<i class="icon-custom icon-sm icon-bg-red fa fa-lightbulb-o"></i>
				<span>抱歉，没有找到相关信息。</span>
			  </h2>
			</div>
		  </div>
		</div>
	  </div>
	</div>
  </div>
</div>
</body>
</html>