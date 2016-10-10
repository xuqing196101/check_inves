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
	 <link rel="stylesheet" href="${pageContext.request.contextPath}/public/supplier/css/supplier.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/upload/upload.css" type="text/css" />
<style type="text/css">
.panel-title>a {
	color: #333
}
</style>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/ztree/css/zTreeStyle.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/oms/css/consume.css">
    <script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/public/ztree/jquery.ztree.core.js"></script>
    <!--导航js-->
    <script src="${pageContext.request.contextPath}/public/ZHH/js/jquery_ujs.js"></script>
    <script src="${pageContext.request.contextPath}/public/ZHH/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/public/oms/js/select-tree.js"></script>
<script src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/upload/upload.js"></script>
<script type="text/javascript">
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
</script>
<script type="text/javascript">
	 $(document).ready(function(){
	 	var proviceId = $("#pid").val();
		//console.dir(proviceId);
		
	 	$.ajax({
		    type: 'post',
		    url: "${pageContext.request.contextPath}/purchaseManage/getProvinceList.do?",
		    data : {pid:1},
		    success: function(data) {
		    	$("#city").append("<option value='-1'>请选择</option>");
		    	$("#province").append("<option value='-1'>请选择</option>");
			    $.each(data, function(idx, item) {
					if(item.id==proviceId){
						var html = "<option value='" + item.id + "' selected>" + item.name
						+ "</option>";
						$("#province").append(html);
						loadCities(proviceId);
					}else{
						var html = "<option value='" + item.id + "'>" + item.name
						+ "</option>";
						$("#province").append(html);
					}
				});
				if(proviceId!=null && proviceId!="" && proviceId!=undefined){
					//loadCities(proviceId);
				}
            	/*  var optionHTML="<select name=\"province\" onchange=\"loadCities(this.value)\">";
	             var optionHTML="";
				  optionHTML+="<option value=\""+"-1"+"\">"+"清选择"+"</option>"; 
				  for(var i=0;i<data.length;i++){
			       // console.dir(data[i].id);
			        optionHTML+="<option value=\""+data[i].id+"\">"+data[i].name+"</option>"; 
				  }
				  optionHTML+="</select>";
				  $("#province").html(optionHTML);//将数据填充到省份的下拉列表中
				  console.dir(optionHTML); */
		    }
		});
		
    });
    function loadCities(pid){
    	$("#pid").val(pid);
    	var cityId = $("#cid").val();
    	$.ajax({
		    type: 'post',
		    url: "${pageContext.request.contextPath}/purchaseManage/getProvinceList.do?",
		    data : {pid:pid},
		    success: function(data) {
		    	$.each(data, function(idx, item) {
					if(item.id==cityId){
						var html = "<option value='" + item.id + "' selected>" + item.name
						+ "</option>";
						$("#city").append(html);
					}else{
						var html = "<option value='" + item.id + "'>" + item.name
						+ "</option>";
						$("#city").append(html);
					}
					
				});
             /* var optionHTML="";
			  optionHTML+="<option value=\""+"-1"+"\">"+"清选择"+"</option>"; 
			  for(var i=0;i<data.length;i++){
		       // console.dir(data[i].id);
		        optionHTML+="<option value=\""+data[i].id+"\">"+data[i].name+"</option>"; 
			  }
			  optionHTML+="</select>";
			  $("#city").html(optionHTML);//将数据填充到省份的下拉列表中
			  //console.dir(optionHTML); */
		    }
		});
    }
    function loadTown(pid){
    	$("#cid").val(pid);
    }
	function create(){
		$.ajax({
		    type: 'post',
		    url: "${pageContext.request.contextPath}/purchase/createAjax.do?",
		    data : $('#formID').serialize(),
		    //data: {'pid':pid,$("#formID").serialize()},
		    success: function(data) {
		        truealert(data.message,data.success == false ? 5:1);
		    }
		});
		
	}
	function truealert(text,iconindex){
		layer.open({
		    content: text,
		    icon: iconindex,
		    shade: [0.3, '#000'],
		    yes: function(index){
		        //do something
		         //parent.location.reload();
		    	 layer.closeAll();
		    	 //parent.layer.close(index); //执行关闭
		    	 parent.location.href="${pageContext.request.contextPath}/purchase/list.do";
		    }
		});
	}
	function pageOnload(){
		var proviceId = $("#pid").val();
		console.dir(proviceId);
		var cityId = $("#cid").val();
		var isAudit = $("#cid").val();
		$("#province").val('A4CCB12438AD4E49AADE355B3B02910C');
		$("#province").get(0).selectedIndex=proviceId;
		$("#province option[value ='"+proviceId+"']").attr("selected", true);//val(2);
		$("#city").val(cityId);
		//$("#provinceId").val(proviceId);
		
	}
</script>
</head>
<body onload="pageOnload();">

	<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a href="#"> 首页</a>
				</li>
				<li><a href="#">支撑系统</a>
				</li>
				<li><a href="#">采购机构管理</a>
				</li>
				<li class="active"><a href="#">修改采购机构</a>
				</li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>

	<!-- 修改订列表开始-->
	<div class="container">
		<div id="menuContent" class="menuContent divpopups menutree">
			<ul id="treeDemo" class="ztree"></ul>
		</div>
		<form action="${pageContext.request.contextPath}/purchase/create.do" method="post" id="formID">
			<!-- <input type="hidden" value="1" name="typeName"/> -->
			<div>
				<div class="headline-v2">
					<h2>修改采购人信息</h2>
				</div>
				<!-- 伸缩层 -->
				<div class="panel-group" id="accordion">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-parent="#accordion" 
								   href="#collapseOne">
									1、基本信息
								</a>
							</h4>
						</div>
						<div id="collapseOne" class="panel-collapse collapse in">
							<div class="panel-body">
								<ul class="list-unstyled list-flow p0_20">
									<li class="col-md-6 p0"><span class="">姓名：</span>
										<div class="input-append">
											<input class="span2" name="relName" type="text" value="${purchaseInfo.relName }"> <span
												class="add-on">i</span>
											<div class="b f18 ml10 red hand">${name_msg}</div>
										</div>
									</li>
									<li class="col-md-6  p0 "><span class="">性别：</span>
										<div class="select_common mb10">
											<select class="span2 w250" name="gender">
												<option value="">-请选择-</option>
												<option value="M">男</option>
												<option value="F">女</option>
											</select> 
										</div>
									</li>
									<li class="col-md-6  p0 "><span class="">省：</span>
										<div class="select_common mb10">
											<select class="span2 w250" name="provinceId" id="province" onchange="loadCities(this.value);"> 
											</select>
											<input type="hidden" name="purchaseInfo.provinceId" id="pid" value="${purchaseInfo.provinceId }">
										</div>
									</li>
									<li class="col-md-6  p0 "><span class="">市：</span>
										<div class="select_common mb10">
											<select class="span2 w250" name="cityId" id="city" onchange="loadTown(this.value);">
											</select> 
											<input type="hidden" name="purchaseInfo.cityId" id="cid" value="${purchaseInfo.cityId }">
										</div>
									</li>
									<li class="col-md-6  p0 "><span class="">民族：</span>
										<div class="input-append">
											<input class="span2" name="nation" value="${purchaseInfo.subordinateOrgName }"
												type="text"> <span class="add-on">i</span>
										</div>
									</li>
									<li class="col-md-6  p0 "><span class="">政治面貌：</span>
										<div class="input-append">
											<input class="span2" name="political" value="${purchaseInfo.businessRange }"
												type="text"> <span class="add-on">i</span>
										</div>
									</li>
									<li class="col-md-6  p0 "><span class=""><i class="red">＊</i>出生年月：</span>
										<div class="input-append">
											<input class="span2" type="text" readonly="readonly" onClick="WdatePicker()" name="birthAt" value="<fmt:formatDate value="${purchaseInfo.quaStartDate}" pattern="yyyy-MM-dd" />" /> 
											<span class="add-on"> <img src="${pageContext.request.contextPath}/public/ZHQ/images/time_icon.png" class="mb10" /> </span>
										</div>
									</li>
									<li class="col-md-6  p0 "><span class="">身份证号：</span>
										<div class="input-append">
											<input class="span2" name="idCard" value="${ purchaseInfo.postCode}"
												type="text"> <span class="add-on">i</span>
										</div>
									</li>
									<li class="col-md-6  p0 "><span class="">人员类别：</span>
										<div class="select_common mb10">
											<select class="span2 w250" name="purcahserType">
												<option value="">-请选择-</option>
												<option value="0">军人</option>
												<option value="1">文职</option>
												<option value="2">职工</option>
												<option value="3">战士</option>
											</select>
										</div> 
									</li>
									<li class="col-md-6  p0 "><span class="">职称：</span>
										<div class="input-append">
											<input class="span2" name="professional" type="text" value="${ purchaseInfo.professional}"> <span
												class="add-on">i</span>
										</div>
									</li>
									<li class="col-md-6  p0 "><span class="">所属采购机构：</span>
										<div class="select_common">
											<input id="proSec" class="w250" type="text" readonly value="${purchaseInfo.purchaseDepName }" name="purchaseDepName" style="width:120px;" onclick="showMenu(); return false;"/>
											<input type="hidden"  id="treeId" name="purchaseDepId" value="${purchaseInfo.purchaseDepId }"  class="text"/>
											 <i class="input_icon "
												onclick="showMenu();"> <img
												src="${pageContext.request.contextPath}/public/ZHH/images/down.png"
												class="margin-bottom-5" /> </i>
										</div>
									</li>
									<li class="col-md-6  p0 "><span class="">职务：</span>
										<div class="input-append">
											<input class="span2" name="duties" type="text" value="${ purchaseInfo.duties}"> <span
												class="add-on">i</span>
										</div>
									</li>
									<li class="col-md-6  p0 "><span class="">年龄：</span>
										<div class="input-append">
											<input class="span2" name="age" type="text" value="${ purchaseInfo.age}"> <span
												class="add-on">i</span>
										</div>
									</li>
								</ul>
							</div>
						</div>
					</div>
					<!--  class="panel panel-default" -->
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-parent="#accordion" 
								   href="#collapseTwo">
									2、专业信息
								</a>
							</h4>
						</div>
						<div id="collapseTwo" class="panel-collapse collapse">
							<div class="panel-body">
								<ul class="list-unstyled list-flow p0_20">
									
									<li class="col-md-6  p0 "><span class="">学历：</span>
										<div class="input-append">
											<input class="span2" name="topStudy" type="text"> <span
												class="add-on">i</span>
										</div>
									</li>
									<li class="col-md-6  p0 "><span class="">毕业院校：</span>
										<div class="input-append">
											<input class="span2" name="graduateSchool" type="text"> <span
												class="add-on">i</span>
										</div>
									</li>
									<!-- <li class="col-md-6  p0 "><span class="">专业：</span>
										<div class="input-append">
											<input class="span2" name="quaCode" type="text"> <span
												class="add-on">i</span>
										</div>
									</li> -->
									<li class="col-md-12 p0"><span class="fl">工作经历：</span>
										<div class="col-md-12 pl200 fn mt5 pwr9">
											<textarea class="text_area col-md-12 " name="workExperience"
												maxlength="400" title="" placeholder=""></textarea>
										</div>
									</li>
									<li class="col-md-12 p0"><span class="fl">培训经历：</span>
										<div class="col-md-12 pl200 fn mt5 pwr9">
											<textarea class="text_area col-md-12 " name="trainExperience"
												maxlength="400" title="" placeholder=""></textarea>
										</div>
									</li>
								</ul>
							</div>
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-parent="#accordion" 
								   href="#collapseThree">
									3、资质信息
								</a>
							</h4>
						</div>
						<div id="collapseThree" class="panel-collapse collapse">
							<div class="panel-body">
								<ul class="list-unstyled list-flow p0_20">
									<li class="col-md-6  p0 "><span class="">资质编号：</span>
										<div class="input-append">
											<input class="span2" name="quaCode" type="text"> <span
												class="add-on">i</span>
										</div>
									</li>
									<li class="col-md-6  p0 "><span class="">采购资质范围：</span>
										<div class="input-append">
											<input class="span2" name="quaRank" type="text"> <span
												class="add-on">i</span>
										</div>
									</li>
									<li class="col-md-6  p0 "><span class=""><i class="red">＊</i>采购资质开始日期：</span>
										<div class="input-append">
											<input class="span2" type="text" readonly="readonly" onClick="WdatePicker()" name="quaStartDate" /> 
											<span class="add-on"> <img src="${pageContext.request.contextPath}/public/ZHQ/images/time_icon.png" class="mb10" /> </span>
										</div>
									</li>
									<li class="col-md-6  p0 "><span class=""><i class="red">＊</i>采购资质截止日期：</span>
										<div class="input-append">
											<input class="span2" type="text" readonly="readonly" onClick="WdatePicker()" name="quaEdndate" /> 
											<span class="add-on"> <img src="${pageContext.request.contextPath}/public/ZHQ/images/time_icon.png" class="mb10" /> </span>
										</div>
									</li>
									
									<li class="col-md-6  p0 "><span class="">采购资质等级：</span>
										<div class="select_common mb10">
											<select class="span2 w250" name="quaLevel">
												<option value="">-请选择-</option>
												<option value="0">初</option>
												<option value="1">中</option>
												<option value="2">高</option>
											</select> 
										</div>
									</li>
									<li class="col-md-6 p0"><span class=""><i class="red">＊</i>采购资格证书图片：</span>
										<div class="uploader orange m0">
											<input type="text" class="filename h32 m0 fz11" readonly="readonly" value="未选择任何文件..." /> 
											<input type="button" class="button" value="选择文件..." /> 
											<input type="file" size="30" accept="image/*" />
										</div>
									</li>
									
								</ul>
							</div>
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-parent="#accordion" 
								   href="#collapseFour">
									4、人员信息
								</a>
							</h4>
						</div>
						<div id="collapseFour" class="panel-collapse collapse">
							<div class="panel-body">
								<ul class="list-unstyled list-flow p0_20">
									<li class="col-md-6  p0 "><span class="">手机号码：</span>
										<div class="input-append">
											<input class="span2" name="mobile" type="text"> <span
												class="add-on">i</span>
										</div>
									</li>
									<li class="col-md-6  p0 "><span class="">办公号码：</span>
										<div class="input-append">
											<input class="span2" name="telephone" type="text"> <span
												class="add-on">i</span>
										</div>
									</li>
									<li class="col-md-6  p0 "><span class="">传真号码：</span>
										<div class="input-append">
											<input class="span2" name="fax" type="text"> <span
												class="add-on">i</span>
										</div>
									</li>
									<li class="col-md-6  p0 "><span class="">邮政编码：</span>
										<div class="input-append">
											<input class="span2" name="postCode" type="text"> <span
												class="add-on">i</span>
										</div>
									</li>
									<li class="col-md-12 p0"><span class="fl">联系地址：</span>
										<div class="col-md-12 pl200 fn mt5 pwr9">
											<textarea class="text_area col-md-12 " name="address"
												maxlength="400" title="" placeholder=""></textarea>
										</div>
									</li>
								</ul>
							</div>
						</div>
					</div>
				</div>
				<!-- 伸缩层 -->
			</div>

			<div class="col-md-12">
				<div class="mt40 tc  mb50 ">
					<button type="button" class="btn  padding-right-20 btn_back margin-5" onclick="create();">确认</button>
					<!-- <button type="button" class="btn  padding-right-20 btn_back margin-5" onclick="stash();">暂存</button> -->
					<button type="button" class="btn  padding-right-20 btn_back margin-5" onclick="history.go(-1)">取消</button>
				</div>
			</div>
		</form>
	</div>
</body>
</html>
