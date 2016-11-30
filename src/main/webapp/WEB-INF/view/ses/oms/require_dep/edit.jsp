<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ include file="../../../common.jsp"%>
<%
	 List jsList = (List) request.getAttribute("strlist"); 
%>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en">
<link href="${pageContext.request.contextPath}/public/ztree/css/ztree-extend.css" type="text/css" rel="stylesheet" >
<script src="${pageContext.request.contextPath}/js/oms/purchase/jquery.metadata.js"></script>
<script src="${pageContext.request.contextPath}/js/oms/purchase/layer-extend.js"></script>
<script src="${pageContext.request.contextPath}/js/oms/purchase/select-tree.js"></script>
<script src="${pageContext.request.contextPath}/js/oms/purchase/validate-extend.js"></script>
<!--<![endif]-->
<head>
<script type="text/javascript">
	 Array.prototype.indexOf = function(val) {
		for (var i = 0; i < this.length; i++) {
			if (this[i] == val) return i;
		}
		return -1;
	};
	Array.prototype.remove = function(val) {
		var index = this.indexOf(val);
			if (index > -1) {
				this.splice(index, 1);
			}
	};
	var array =[];
	var setting = {
		view : {
			dblClickExpand : false
		},
		async : {
			autoParam : [ "id" ],
			enable : true,
			url : "${pageContext.request.contextPath}/purchaseManage/gettree.do",
			dataType : "json",
			type : "post",
		},
		data : {
			simpleData : {
				enable : true,
				idKey : "id",
				pId : "pId",
				rootPId : -1,
			}
		},
		view: {
			showLine: false
		},
		callback : {
			beforeClick : beforeClick,
			onClick : onClick
		}
	};
	$(document).ready(function() {
		$.fn.zTree.init($("#treeDemo"), setting, datas);
	});
	function save() {
		var index = parent.layer.getFrameIndex(window.name);
		var pid = parent.$("#parentid").val();
		console.dir(pid);
		$
				.ajax({
					type : 'post',
					url : "${pageContext.request.contextPath}/purchaseManage/saveOrg.do?",
					data : $.param({
						'parentId' : pid
					}) + '&' + $('#formID').serialize(),
					//data: {'pid':pid,$("#formID").serialize()},
					success : function(data) {
						truealert(data.message, data.success == false ? 5 : 1);
					}
				});

	}
	function truealert(text, iconindex) {
		layer.open({
			content : text,
			icon : iconindex,
			shade : [ 0.3, '#000' ],
			yes : function(index) {
				//do something
				parent.location.reload();
				layer.closeAll();
				parent.layer.close(index); //执行关闭
				//parent.location.href="${pageContext.request.contextPath}/purchaseManage/list.do";
			}
		});
	}
	//需求部门、采购机构、监管部门切换注册页面   0  是监管部门
	function show(){
		 var typeName = $("#typeName").val();
		 console.dir(typeName);
		 if(typeName!=null && typeName!="" && typeName=="0"){
		 	$(".monitor").show();
		 }else{
		 	$(".monitor").hide();
		 }
	}

 
    function dynamicadd(){
    	var typeName = $("#typeName").val();
    	var title = "";
    	if(typeName!=undefined && typeName==0){
    		title = "添加采购机构";
    	}else{
    		title = "添加监管部门";
    	}
    	layer.open({
			type : 2, //page层
			area : [ '900px', '550px' ],
			title : title,
			shade : 0.01, //遮罩透明度
			moveType : 1, //拖拽风格，0是默认，1是传统拖动
			shift : 1, //0-6的动画形式，-1不开启
			offset : [ '120px', '130px' ],
			shadeClose : true,
			content : '${pageContext.request.contextPath}/purchaseManage/addPurchaseOrg.html?typeName='+typeName
		 });
    }
    
    //删除<tr/>
    var deltr =function(index,name)
    {
        
        $("tr[id='" + index + "']").remove();//删除当前行   
		var num = $("#tab tbody tr").length;
		var trs = $("#tab tbody tr");
		for (i = 0; i < num; i++) {
			trs.find("td:eq(1)").each(function(i) {
				$(this).text(i + 1);
			});
		}
	};
	//提交表单前测试  获取选择机构id
	function check(){
		var depIds="";
		$("input[name='selectedItem']").each(function(){
			depIds += $(this).val() + ",";
		});
		depIds = depIds.substr(0,depIds.length-1);
		$("#depIds").val(depIds);
		return true;
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
</script>
</head>
<body>

	<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a href="#"> 首页</a>
				</li>
				<li><a href="#">支撑系统</a>
				</li>
				<li><a href="#">后台管理</a>
				</li>
				<li class="active"><a href="#">机构管理</a>
				</li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>

	<!-- 修改订列表开始-->
	<div class="container">
		<sf:form action="${pageContext.request.contextPath}/purchaseManage/update.do" method="post" onsubmit="return check();" id="formID" modelAttribute="orgnization">
			<div>
			     <h2 class="count_flow"><i>1</i>修改基本信息</h2>
				<input type="hidden" name="depIds" id="depIds"/>
				<input type="hidden" name="id" value="${orgnization.id}"/>
				<ul class="ul_list">
					<li class="col-md-3 col-sm-6 col-xs-12 pl15"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span>名称</span>
						<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
							<input class="input_group" name="name" type="text" value="${orgnization.name }"> <span
								class="add-on">i</span>
								<div class="cue"><sf:errors path="name"/></div>
						</div></li>
					<li class="col-md-3 col-sm-6 col-xs-12"> <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span>简称</span>
						<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
							<input class="input_group" name="shortName" type="text" value="${orgnization.shortName }"> <span
								class="add-on">i</span>
								<div class="cue"><sf:errors path="shortName"/></div>
						</div></li>
					<li class="col-md-3 col-sm-6 col-xs-12"> <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">类型</span>
							<select  class="input_group" name="typeName"  id="typeName" type="text" onchange="show();"> 
							    <option value="0" <c:if test="${orgnization.typeName == '0' }">selected="true"</c:if>>请选择</option>
								<option value="1" <c:if test="${orgnization.typeName == '1' }">selected="true"</c:if>>采购机构</option>
								<option value="2" <c:if test="${orgnization.typeName == '2' }">selected="true"</c:if>>管理部门</option>
							</select>
					</li>
					<li class="col-md-3 col-sm-6 col-xs-12"> <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">上级</span>
						<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
							<input id="proSec" class="input_group" type="text" readonly value="${orgnization.parentName }" name="parentName" onclick="showMenu(); return false;"/>
							<input type="hidden"  id="treeId" name="parentId" value="${orgnization.parentId }"  class="text"/>
						</div></li>
					<li class="col-md-3 col-sm-6 col-xs-12"> <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">电话</span>
						<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
							<input class="input_group" name="mobile" type="text" value="${orgnization.mobile }"> <span
								class="add-on">i</span>
						</div></li>
					<li class="col-md-3 col-sm-6 col-xs-12"> <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">地址</span>
						<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
							<input class="input_group" name="address" type="text" value="${orgnization.address }"> <span
								class="add-on">i</span>
						</div></li>
					
					<li class="col-md-3 col-sm-6 col-xs-12"> <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">邮编</span>
						<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
							<input class="input_group" name="postCode" type="text" value="${orgnization.postCode }"> <span
								class="add-on">i</span>
						</div></li>
					<li class="col-md-3 col-sm-6 col-xs-12"> <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">传真</span>
						<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
							<input class="input_group" name="fax" type="text" value="${orgnization.fax }"> <span
								class="add-on">i</span>
						</div></li>
					
					<li class="col-md-3 col-sm-6 col-xs-12 hide monitor"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">组织机构代码</span>
						<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
							<input class="input_group" name="orgCode" type="text" value="${orgnization.orgCode }"> <span
								class="add-on">i</span>
						</div></li>
					<li class="col-md-3 col-sm-6 col-xs-12 hide monitor"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">详细地址</span>
						<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
							<input class="input_group" name="detailAddr" type="text" value="${orgnization.detailAddr }"> <span
								class="add-on">i</span>
						</div></li>
					<li class="col-md-3 col-sm-6 col-xs-12 hide monitor"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">网站地址</span>
						<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
							<input class="input_group" name="website" type="text" value="${orgnization.website }"> <span
								class="add-on">i</span>
						</div></li>
					<li class="col-md-3 col-sm-6 col-xs-12 hide monitor"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">负责人</span>
						<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
							<input class="input_group" name="princinpal" type="text" value="${orgnization.princinpal }"> <span
								class="add-on">i</span>
						</div></li>
					<li class="col-md-3 col-sm-6 col-xs-12 hide monitor"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">负责人身份证号</span>
						<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
							<input class="input_group" name="princinpalIdCard" type="text" value="${orgnization.princinpalIdCard }"> <span
								class="add-on">i</span>
						</div></li>
					<li class="col-md-3 col-sm-6 col-xs-12 hide monitor"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">监管机构性质</span>
						<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
							<input class="input_group" name="nature" type="text" value="${orgnization.nature }"> <span
								class="add-on">i</span>
						</div></li>
				</ul>
				<div class="padding-top-10 clear">
                    <h2 class="count_flow"><i>2</i><c:choose>
                    	<c:when test="${orgnization.typeName==1}">关联采购机构</c:when>
                    	<c:when test="${orgnization.typeName==2}">关联管理部门</c:when>
                    	<c:when test="${orgnization.typeName==0}">关联管理部门</c:when>
                    </c:choose></h2>
                       <ul class="ul_list">
                           <div class="col-md-12 pl20 mt10">
                               <button type="button" class="btn btn-windows add"  id="dynamicAdd" onclick="dynamicadd();">添加</button>
                            </div>
                               <div class="content table_box">
                                    <table class="table table-bordered table-condensed table-hover table-striped" id="tab">
                                        <thead>
                                                <tr>
                                                    <th class="info w30"><input id="checkAll" type="checkbox"
                                                        onclick="selectAll()" /></th>
                                                    <th class="info w50">序号</th>
                                                    <th class="info">机构名称</th>
                                                    <th class="hide">机构id</th>
                                                    <th class="info">操作</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach  items="${relaList}" var="dept" varStatus="deptStatus">
                                                	<tr class="tc" id="${dept.id}">
                                                		<td>
                                                			<input type="checkbox" name="selectedItem" value="${dept.id}" />
                                                	    </td>
                                                	    <td>${deptStatus.index +1}</td>
                                                	    <td>${dept.name}</td>
                                                	    <td><a href="javascript:deltr('${dept.id}','${dept.name}')">删除</a></td>
                                                	</tr>
                                                </c:forEach>
                                            </tbody>
                                    </table>
                                </div>
                       </ul>
                   </div>
			</div>
			<div class="col-md-12">
				<div class="mt40 tc mb50">
					<button type="submit" class="btn btn-windows save">保存</button>
					 <input type="button" class="btn btn-windows cancel" onclick="history.go(-1)" value="取消"/>
				</div>
			</div>
		</sf:form>
		<!-- tree -->
		<div id="menuContent" class="menuContent divpopups menutree">
			<ul id="treeDemo" class="ztree"></ul>
		</div>
	</div>
</body>
</html>
