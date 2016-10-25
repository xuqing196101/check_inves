<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en">
<!--<![endif]-->
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title></title>

	<!-- Meta -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/bootstrap.min.css" media="screen" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/public/ZHH/css/common.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/style.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/line-icons.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/app.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/application.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/header-v4.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/header-v5.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/footer-v2.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/img-hover.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/page_job.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/shop.style.css" media="screen" rel="stylesheet">
	
	
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/ztree/css/zTreeStyle.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/oms/css/consume.css">
    <script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/public/ztree/jquery.ztree.core.js"></script>
    <!--导航js-->
    <script src="${pageContext.request.contextPath}/public/ZHH/js/jquery_ujs.js"></script>
    <script src="${pageContext.request.contextPath}/public/ZHH/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/public/oms/js/select-tree.js"></script>
<script src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
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
		 $("#tab tr:gt(0)").remove();
		 array=[];
		 console.dir(typeName);
		 if(typeName!=null && typeName!="" && typeName=="0"){
		 	$(".monitor").show();
		 }else{
		 	$(".monitor").hide();
		 }
	}
</script>
<script type="text/javascript">
   $(document).ready(function(){
         //<tr/>居中
        $("#tab tr").attr("align","center");
        //增加<tr/>
        /* $("#dynamicAdd").click(function(){
        	var typeName = $("#typeName").val();
        	showiframe("需求部门新增",1000,600,"${pageContext.request.contextPath}/purchaseManage/addPurchaseOrg.do?typeName="+typeName,"-4");
        })   */   
    });
    function dynamicadd(){
    	var typeName = $("#typeName").val();
        showiframe("添加机构",1000,600,"${pageContext.request.contextPath}/purchaseManage/addPurchaseOrg.do?typeName="+typeName,"-4");
    }
    function deltr1(a){
    	//var str = a;
    	console.dir(a);
    	console.dir(a.id);
    }
    //删除<tr/>
    var deltr =function(index,name)
    {
        //var _len = $("#tab tr").length;
        //console.dir(index);
        //console.dir(index.id);
        //console.dir($("tr[id='" + index.id + "']"));
        var deldata = index+","+name;
        array.remove(deldata);
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
		for(var j=0;j<array.length;j++){
			var id ="";
			if(array[j].indexOf(",")!=-1){
				id = array[j].substr(0,array[j].indexOf(","));
			}
			depIds += id;
			depIds += ",";
		}
		depIds = depIds.substr(0,depIds.length-1);
		$("#depIds").val(depIds);
		return true;
	}
	//提交表单前测试  获取选择的id
	function selectIds(){
		var depIds="";
		var num = $("#tab tbody tr").length;
		var trs = $("#tab tbody tr");
		console.dir(trs);
		for (i = 0; i < num; i++) {
		
			$("#tab tbody tr:eq('"+i+"')").find("td:eq(2)").each(function(i) {
				//$(this).text(i + 1);
				console.dir($(this).text());
				depIds += $(this).text();
				depIds += ",";
			});
		}
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
	        title: [titles,"background-color:#83b0f3;color:#fff;font-size:16px;text-align:center;"],
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
		<form action="${pageContext.request.contextPath}/purchaseManage/create.do" method="post" onsubmit="return check();" id="formID">
			<div>
				<div class="headline-v2">
					<h2>新增机构</h2>
				</div>
				<input type="hidden" name="depIds" id="depIds"/>
				<ul class="list-unstyled list-flow p0_20">
					<li class="col-md-6 p0"><span class="">名称：</span>
						<div class="input-append">
							<input class="span2" name="name" type="text"> <span
								class="add-on">i</span>
						</div></li>
					<li class="col-md-6 p0"><span class="">简称：</span>
						<div class="input-append">
							<input class="span2" name="shortName" type="text"> <span
								class="add-on">i</span>
						</div></li>
					<li class="col-md-6 p0"><span class="">类型：</span>
							<select class="span2" name="typeName" id="typeName" type="text" onchange="show();"> 
								<option value="2">需求部门</option>
								<option value="1">采购机构</option>
								<option value="0">管理部门</option>
							</select>
					</li>
					<li class="col-md-6  p0 "><span class="">上级：</span>
						<div class="input-append">
							<input id="proSec" type="text" readonly value="${orgnization.parentName }" name="parentName" style="width:120px;" onclick="showMenu(); return false;"/>
							<input type="hidden"  id="treeId" name="parentId" value="${orgnization.parentId }"  class="text"/>
						</div></li>
					</li>
					<li class="col-md-6 p0"><span class="">电话：</span>
						<div class="input-append">
							<input class="span2" name="mobile" type="text"> <span
								class="add-on">i</span>
						</div></li>
					<li class="col-md-6  p0 "><span class="">地址：</span>
						<div class="input-append">
							<input class="span2" name="address" type="text"> <span
								class="add-on">i</span>
						</div></li>
					
					<li class="col-md-6  p0 "><span class="">邮编：</span>
						<div class="input-append">
							<input class="span2" name="postCode" type="text"> <span
								class="add-on">i</span>
						</div></li>
					<li class="col-md-6  p0 "><span class="">传真：</span>
						<div class="input-append">
							<input class="span2" name="fax" type="text"> <span
								class="add-on">i</span>
						</div></li>
					<li class="col-md-6 p0 hide"><span class="">类型1：</span>
							<select class="span2" name="provinceId" type="text"> 
								<option value="2">需求部门</option>
								<option value="1">采购机构</option>
								<option value="0">管理部门</option>
							</select>
					</li>
					<li class="col-md-6 p0 hide"><span class="">类型2：</span>
							<select class="span2" name="cityId" type="text"> 
								<option value="2">需求部门</option>
								<option value="1">采购机构</option>
								<option value="0">管理部门</option>
							</select>
					</li>
					<li class="col-md-6  p0 hide monitor"><span class="">组织机构代码：</span>
						<div class="input-append">
							<input class="span2" name="orgCode" type="text"> <span
								class="add-on">i</span>
						</div></li>
					<li class="col-md-6  p0 hide monitor"><span class="">详细地址：</span>
						<div class="input-append">
							<input class="span2" name="detailAddr" type="text"> <span
								class="add-on">i</span>
						</div></li>
					<li class="col-md-6  p0 hide monitor"><span class="">网站地址：</span>
						<div class="input-append">
							<input class="span2" name="website" type="text"> <span
								class="add-on">i</span>
						</div></li>
					<li class="col-md-6  p0 hide monitor"><span class="">负责人：</span>
						<div class="input-append">
							<input class="span2" name="princinpal" type="text"> <span
								class="add-on">i</span>
						</div></li>
					<li class="col-md-6  p0 hide monitor"><span class="">负责人身份证号：</span>
						<div class="input-append">
							<input class="span2" name="princinpalIdCard" type="text"> <span
								class="add-on">i</span>
						</div></li>
					<li class="col-md-6  p0 hide monitor"><span class="">监管机构性质：</span>
						<div class="input-append">
							<input class="span2" name="nature" type="text"> <span
								class="add-on">i</span>
						</div></li>
				</ul>
				<div class="mt40 mb50">
					<button type="button" class="btn  padding-right-20 btn_back margin-5" id="dynamicAdd" onclick="dynamicadd();">添加</button>
				</div>
				<div class="content padding-left-25 padding-right-25 padding-top-5">
					<table class="table table-bordered table-condensed" id="tab">
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
							
						</tbody>
					</table>
				</div>
			</div>
			<div class="col-md-12">
				<div class="mt40 tc mb50">
					<button type="submit" class="btn  padding-right-20 btn_back margin-5">保存</button>
				</div>
			</div>
		</form>
		<!-- tree -->
		<div id="menuContent" class="menuContent divpopups menutree">
			<ul id="treeDemo" class="ztree"></ul>
		</div>
	</div>
</body>
</html>
