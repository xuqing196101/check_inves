<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<html class=" js cssanimations csstransitions" lang="en">
<head>
<%@ include file="/WEB-INF/view/common.jsp"%>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <script type="text/javascript">
       
        $(function(){
        	loadDynOrg();
        });
    	
        /** 动态加载组织机构信息 */
        function loadDynOrg(){
        	var typeName = $("#type_name").val();
        	if (typeName == '2'){
        		$(".monitor").show();
        	} else {
        		$(".monitor").hide();
        	}
        }
     
    	/** 添加人员 */
    	function addUser(){
    		var typeName = $("#type_name").val();
    		if (typeName == '1'){
    			window.location.href="${pageContext.request.contextPath}/purchase/add.html?origin=2&orgId="+selectedTreeId;
    		} else {
    			window.location.href= "${pageContext.request.contextPath}/purchaseManage/addUser.do?orgId="+selectedTreeId;
    		}
    	}
    	
    	/** 编辑 **/
    	function editUser(){
    		var ids = getSelectIds();
    		var len = ids.length;
    		var titles="";
    		if(ids.length != 1){
    			layer.msg("请选择一个用户,进行修改");
    			return;
    		}
    		var typeName = $("#type_name").val();
    		if (typeName == '1'){
    			window.location.href = "${pageContext.request.contextPath}/purchase/edit.html?purchaserId=" + ids + "&origin=2&orgId=" + selectedTreeId; 
    		} else {
    			window.location.href= "${pageContext.request.contextPath}/user/edit.do?origin=origin&userId=" + ids + "&orgId=" + selectedTreeId + "&typeName=" + typeName;  
    		}
    	}
    	
    	/** 删除用户 **/
    	function delUser(id){
    		var ids = getSelectIds();
    		var len = ids.length;
    		var type = $("#type_name").val();
    		if(len<=0){
    			layer.msg("请选择需要删除的用户");
    			return;
		    }
    		layer.confirm('您确定要删除吗?', {
				btn:['确认','取消']
			},function(){
				delUserAjax(ids.toString(),type);
			});
			
    	}
    	
    	//ajax删除用户
    	function delUserAjax(ids,type){
    		$.ajax({
				type : 'post',
				url : "${pageContext.request.contextPath}/purchaseManage/deleteUser.do?orgType=" + type ,
				data : {ids:ids},
				success : function(msg) {
					showRes(msg);
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
    	
		
		/** 删除成功后调用 */
		function showRes(msg){
			if (msg == "ok"){
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
  <input id="type_name" value="${orgnization.typeName}" type="hidden">
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
					<tr>
					  <td class="col-xs-3 bggrey tl">名称：</td>
					  <td class="col-xs-3">${orgnization.name }</td>
					  <td class="col-xs-3 bggrey tl">简称：</td>
					  <td class="col-xs-3">${orgnization.shortName }</td>
					</tr>
					<tr>
					  <td class="col-xs-3 bggrey tl">省/直辖市：</td>
					  <td class="col-xs-3">${orgnization.provinceName}</td>
					  <td class="col-xs-3 bggrey tl">市/区：</td>
					  <td class="col-xs-3">${orgnization.cityName}</td>
					</tr>
					<tr>
					  <td class="col-xs-3 bggrey tl">详细地址：</td>
					  <td class="col-xs-3">${orgnization.address}</td>
					  <td class="col-xs-3 bggrey tl">值班室电话：</td>
					  <td class="col-xs-3">${orgnization.telephone}</td>
					</tr>
					<tr>
					  <td class="col-xs-3 bggrey tl">邮编：</td>
					  <td class="col-xs-3">${orgnization.postCode}</td>
					  <td class="col-xs-3 bggrey tl">传真：</td>
					  <td class="col-xs-3">${orgnization.fax}</td>
					</tr>
					<tr class="hide monitor">
					  <td class="col-xs-3 bggrey tl">负责人：</td>
					  <td class="col-xs-3">${orgnization.princinpal}</td>
					  <td class="col-xs-3 bggrey tl">采购管理部门等级：</td>
					  <td class="col-xs-3">${orgnization.purchaseLevel}</td>
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
				<table class="table table-bordered table-condensed table-hover table-striped break-all" id="user">
				  <thead>
					<tr>
					  <th><input type="checkbox" onclick="selectAll();" id="allId" alt="全选"/></th>
					  <th>序号</th>
					  <th>姓名</th>
					  <th>手机</th>
					  <th>电话</th>
					  <th>详细地址</th>
					  <th>军网邮箱</th>
				    </tr>
				  </thead>
				  <tbody>
					<c:forEach items="${userlist}" var="u" varStatus="vs">
					  <tr class="cursor">
						<td class="tc"><input type="checkbox" name="chkItem" value="${u.id}" /></td>
						<td class="tc">${vs.index+1}</td>
						<td class="tc">${u.relName}</td>
						<td class="tc">${u.mobile}</td>
						<td class="tc">${u.telephone}</td>
						<td class="tl pl20">${u.address}</td>
						<td class="tl pl20">${u.email}</td>
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
				  <c:when test="${orgnization.typeName!=null && orgnization.typeName == '0'}">关联采购管理部门信息</c:when>
				  <c:when test="${orgnization.typeName!=null && orgnization.typeName == '1'}">关联采购管理部门信息</c:when>
				  <c:when test="${orgnization.typeName!=null && orgnization.typeName == '2'}">采购机构信息</c:when>
				</c:choose> 
				<span class="label rounded-2x label-u">正常</span>
			  </h2>
			</div>
			<div class="panel panel-grey clear mt5">
			  <div class="panel-heading">
				<h3 class="panel-title">
				  <i class="fa fa-users"></i> 
				  <c:choose>
					<c:when test="${orgnization.typeName!=null && orgnization.typeName == '0' }">采购管理部门信息</c:when>
					<c:when test="${orgnization.typeName!=null && orgnization.typeName == '1' }">采购管理部门信息</c:when>
					<c:when test="${orgnization.typeName!=null && orgnization.typeName == '2'}">采购机构信息</c:when>
				  </c:choose> 
				</h3>
			  </div>
			  <div class="panel-body">
				<table class="table table-bordered table-condensed table-hover table-striped">
				  <thead>
					<tr>
					  <th>序号</th>
					  <th>名称</th>
					  <th>值班室电话</th>
					  <th>所在地市</th>
					  <th>详细地址</th>
					  <th>负责人</th>
					</tr>
				  </thead>
				  <tbody>
					<c:forEach items="${oList}" var="p" varStatus="vs">
					  <tr class="cursor">
						<td class="tc">${vs.index+1}</td>
						<td class="tl pl20">${p.name}</td>
						<td class="tc">${p.telephone}</td>
						<td class="tl pl20">${p.provinceName}${p.cityName}</td>
						<td class="tl pl20">${p.address}</td>
						<td class="tc">${p.contactName}</td>
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