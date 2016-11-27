<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>
<html class=" js cssanimations csstransitions" lang="en">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <script type="text/javascript">
    	function addUser(){
    		var depid = $("#defaultid").val();
    		var typeName = $("#typeName").val();
    		//$("#hform").submit();
    		parent.showiframe("添加机构人员",550,400,"${pageContext.request.contextPath}/purchaseManage/addUser.do?typeName="+typeName+"&org.id="+depid,"-4");
    	}
    	function editUser(){
    		var ids = getSelectIds();
    		var len = ids.length;
    		var titles="";
    		if(len>1){
    			titles="只能选择一条记录";
    		}else if(len<=0){
    			titles="至少选择一条记录";
    		}
    		if(len>1||len<=0){
    			truealert(titles,5);
    			return;
		    };
    		//console.dir(array[0].defaultValue);
    		parent.showiframe("修改机构人员",1000,600,"${pageContext.request.contextPath}/purchaseManage/editUser.do?id="+ids[0],"-4");
    	}
    	function delUser(id){
    		var ids = getSelectIds();
    		var len = ids.length;
    		var titles="至少选择一条记录";;
    		//console.dir(ids);
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
				url : "${pageContext.request.contextPath}/purchaseManage/deleteUser.do?",
				data : {ids:idstr},
				//data: {'pid':pid,$("#formID").serialize()},
				success : function(data) {
					truealertReload(data.message, data.success == false ? 5 : 1);
				}
			});
    	}
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
    	function showiframe(titles,width,height,url,top){
			 if(top == null || top == "underfined"){
			  top = 120;
			 }
			layer.open({
		        type: 2,
		        title: [titles],
		        maxmin: true,
		        shade: [0.3, '#000'],
		       	offset: top+"px",
		        shadeClose: false, //点击遮罩关闭层 
		        area : [width+"px" , height+"px"],
		        content: url
		    });
		}
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
		function truealertReload(text,iconindex){
			if(top == null || top == "" || top == "underfined"){
			  top = 120;
			}
			layer.open({
			    content: text,
			    icon: iconindex,
			    offset: top+"px",
			    shade: [0.3, '#000'],
			    yes: function(index){
			         parent.location.reload();
			    	 layer.closeAll();
			    }
			});
		}
		
		function pageOnLoad(){
			var type = $("#type_name").val(); 
			if(type!=null && type!='' && type!=undefined && type==1){
			
			}else if(type==0){
			
			}
		}
    </script>
    </head>
<body onload="pageOnLoad();">
<input id="type_name" value="${orgnization.typeName }" type="hidden">
<div class="tab-content">
	<div class="tab-pane fade active in" id="show_ztree_content">
		<div class="panel-heading overflow-h margin-bottom-20 no-padding"
			id="ztree_title">
			<h2 class="panel-title heading-sm pull-left">
				<i class="fa fa-bars"></i> ${orgnization.name } <span
					class="label rounded-2x label-u">正常</span>
			</h2>
			<!-- <div class="pull-right">
				<a class="btn btn-sm btn-default" href="javascript:void(0)"
					onClick=""><i class="fa fa-search-plus"></i> 详细</a> <a
					class="btn btn-sm btn-default" href="javascript:void(0)" onClick=""><i
					class="fa fa-wrench"></i> 修改</a> <a class="btn btn-sm btn-default"
					href="javascript:void(0)" onClick=""><i class="fa fa-plus"></i>
					增加下属单位</a> <a class="btn btn-sm btn-default" data-toggle="modal"
					href=""><i class="fa fa-plus"></i> 增加人员</a>
			</div> -->
		</div>
		<div id="ztree_content">
			<div class="tab-v2">
				<!-- <ul class="nav nav-tabs bgwhite">
					<li class="active"><a href="#dep_tab-0" data-toggle="tab"
						class="s_news"><h4>详细信息</h4> </a></li>
				</ul> -->
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
						<div class="panel-heading overflow-h margin-bottom-20 no-padding"
							id="ztree_title">
							<h2 class="panel-title heading-sm pull-left">
								<i class="fa fa-bars"></i> ${orgnization.name }人员信息 <span
									class="label rounded-2x label-u">正常</span>
							</h2>
							<div class="pull-right">
								<a class="btn btn-sm btn-default" href="javascript:void(0)"
									onClick="addUser();"><i class="fa fa-search-plus"></i> 添加人员</a> <a
									class="btn btn-sm btn-default" href="javascript:void(0)"
									onClick="editUser();"><i class="fa fa-wrench"></i> 修改人员</a> <a
									class="btn btn-sm btn-default" href="javascript:void(0)"
									onClick="delUser();"><i class="fa fa-plus"></i> 删除人员</a> <a
									class="btn btn-sm btn-default" data-toggle="modal" href=""><i
									class="fa fa-plus"></i> 人员授权</a>
							</div>
						</div>
						<div class="panel panel-grey clear mt5">
							<div class="panel-heading">
								<h3 class="panel-title">
									<i class="fa fa-users"></i> ${orgnization.name }人员列表
								</h3>
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
												<td class="tc"><input
													type="checkbox" name="chkItem" value="${u.id}" />
												</td>
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
						<div class="panel-heading overflow-h margin-bottom-20 no-padding"
							id="ztree_title">
							<h2 class="panel-title heading-sm pull-left">
								<i class="fa fa-bars"></i> 
								<c:choose>
									<c:when test="${orgnization.typeName!=null && orgnization.typeName==0 }">
										采购机构单位信息
									</c:when>
									<c:when test="${ orgnization.typeName!=null && orgnization.typeName==1 }">
										采购管理部门信息
									</c:when>
							     </c:choose> 
								<span
									class="label rounded-2x label-u">正常</span>
							</h2>
						</div>
						<div class="panel panel-grey clear mt5">
							<div class="panel-heading">
								<h3 class="panel-title">
									<i class="fa fa-users"></i> 
									<c:choose>
										<c:when test="${orgnization.typeName!=null && orgnization.typeName==0 }">
											采购机构单位信息
										</c:when>
										<c:when test="${ orgnization.typeName!=null && orgnization.typeName==1 }">
											采购管理部门信息
										</c:when>
							    	 </c:choose> 
								</h3>
							</div>
							<div class="panel-body">
								<table class="table table-bordered table-condensed table-hover table-striped">
									<thead>
										<tr>
											<!-- <th><input type="checkbox" /></th> -->
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
												<!-- 选择框 -->
												<%-- <td onclick="null" class="tc"><input onclick="check()"
													type="checkbox" name="chkItem" value="${p.id}" />
												</td> --%>
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