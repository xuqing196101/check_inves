<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>基本信息</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/common.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/bootstrap.min.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/style.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/line-icons.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/app.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/application.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/header-v4.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/footer-v2.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/img-hover.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/page_job.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/shop.style.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/supplier/css/supplier.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/upload/upload.css" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHQ/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHQ/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/upload/upload.js"></script>
<script type="text/javascript">
		/** 全选全不选 */
	function selectAll(){
		 var checklist = document.getElementsByName ("chkItem");
		 var checkAll = document.getElementById("checkAll");
		   if(checkAll.checked){
			   for(var i=0;i<checklist.length;i++)
			   {
			      checklist[i].checked = true;
			   } 
			 }else{
			  for(var j=0;j<checklist.length;j++)
			  {
			     checklist[j].checked = false;
			  }
		 	}
		}
	
	/** 单选 */
	function check(){
		 var count=0;
		 var checklist = document.getElementsByName ("chkItem");
		 var checkAll = document.getElementById("checkAll");
		 for(var i=0;i<checklist.length;i++){
			   if(checklist[i].checked == false){
				   checkAll.checked = false;
				   break;
			   }
			   for(var j=0;j<checklist.length;j++){
					 if(checklist[j].checked == true){
						   checkAll.checked = true;
						   count++;
					   }
				 }
		   }
	}
	
	//更新
   function update(){
    	var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(id.length==1){
			window.location.href="<%=basePath%>expert/editBlacklist.html?id="+id;
		}else if(id.length>1){
			layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
		}else{
			layer.alert("请选择需要修改的信息",{offset: ['222px', '390px'], shade:0.01});
		}
    }
</script>
</head>
<body>

<!--面包屑导航开始-->
  <!--  <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">供应商注册管理</a></li><li><a href="#">供应商审核</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div> -->
   
   <div class="container clear margin-top-30">
   	<div class="col-md-12 tab-v2 job-content">
	<ul class="nav nav-tabs bgdd">
		<li class="active"><a class="s_news f18" href="${pageContext.request.contextPath}/supplierAudit/essential.html">基本信息</a></li>
		<li class="active"><a class="s_news f18" href="${pageContext.request.contextPath}/supplierAudit/financial.html">财务信息</a></li>
		<li class="active"><a class="s_news f18" href="${pageContext.request.contextPath}/supplierAudit/shareholder.html">股东信息</a></li>
		<li class="active"><a class="s_news f18" href="${pageContext.request.contextPath}/supplierAudit/materialProduction.html">物资-生产型专业信息</a></li>
		<li class="active"><a class="s_news f18">物资-销售型专业信息</a></li>
		<li class="active"><a class="s_news f18">工程-专业信息</a></li>
		<li class="active"><a class="s_news f18">服务-专业信息</a></li>
		<li class="active"><a class="s_news f18">品目信息</a></li>
		<li class="active"><a class="s_news f18">产品信息</a></li>
		<li class="active"><a class="s_news f18">审核汇总</a></li>
	</ul>
	</div>
   </div>
   <!-- 表格开始-->
  <div class="container margin-top-5">
		<div class="content padding-left-25 padding-right-25 padding-top-5">
			<div class=" margin-bottom-0">
				<h2 class="f16 jbxx">
				<i>01</i>企业基本信息
				</h2>
				<ul class="list-unstyled list-flow">
					<li class="col-md-6 p0 "><span class=""><i class="red">＊</i>企业名称：</span>
						<div class="input-append">
							<input class="span3" id="supplierName" name="supplierName" value=" " type="text">
							<div class="b f18 fl ml10 red hand">√</div><div onclick="reason()" class="b f18 fl ml10 hand">×</div>
						</div>
					</li>
					<li class="col-md-6 p0 "><span class=""><i class="red">＊</i>公司网址：</span>
						<div class="input-append">
							<input class="span3" id="supplierTepe" name="supplierTepe" value=" "  type="text">
							<div class="b f18 fl ml10 red hand">√</div><div onclick="reason()" class="b f18 fl ml10 hand">×</div>
						</div>
					</li>
					<li class="col-md-6 p0 "><span class=""><i class="red">＊</i>成立日期：</span>
						<div class="input-append">
							<input class="span3" id="supplierChinesrName" name="supplierChinesrName" value=" " type="text">
							<div class="b f18 fl ml10 red hand">√</div><div onclick="reason()" class="b f18 fl ml10 hand">×</div>
						</div>
					</li>
					<li class="col-md-6 p0 "><span class=""><i class="red">＊</i>营业执照登记类型：</span>
						<div class="input-append">
							<input class="span3" id="legalName" name="legalName" value=" " type="text">
							<div class="b f18 fl ml10 red hand">√</div><div onclick="reason()" class="b f18 fl ml10 hand">×</div>
						</div>
					</li>
					<li class="col-md-6 p0"><span class=""><i class="red">＊</i>地址：</span>
						<div class="input-append">
							<input class="span3" id="legalName" name="legalName" value=" " type="text">
							<div class="b f18 fl ml10 red hand">√</div><div onclick="reason()" class="b f18 fl ml10 hand">×</div>
						</div>
					</li>
					<li class="col-md-6 p0 "><span class=""><i class="red">＊</i>开户行名称：</span>
						<div class="input-append">
							<input class="span3" id="supplierZipCode" name="supplierZipCode" value=" "  type="text">
							<div class="b f18 fl ml10 red hand">√</div><div onclick="reason()" class="b f18 fl ml10 hand">×</div>
						</div>
					</li>
					<li class="col-md-6 p0 "><span class=""><i class="red">＊</i>开户行账户：</span>
						<div class="input-append">
							<input class="span3" id="productType" name="productType" value=" " type="text">
							<div class="b f18 fl ml10 red hand">√</div><div onclick="reason()" class="b f18 fl ml10 hand">×</div>
						</div>
					</li>
					<li class="col-md-6 p0 "><span class=""><i class="red">＊</i>邮编：</span>
						<div class="input-append">
							<input class="span3" id="majorProduct" name="majorProduct" value=" " type="text">
							<div class="b f18 fl ml10 red hand">√</div><div onclick="reason()" class="b f18 fl ml10 hand">×</div>
						</div>
					</li>
					<li class="col-md-6 p0 "><span class=""><i class="red">＊</i>近三个月完税凭证：</span>
						<div class="input-append">
							<a >附件下载</a>
						</div>
					</li>
					<li class="col-md-6 p0 "><span class=""><i class="red">＊</i>近三年银行账单：</span>
						<div class="input-append">
							<a>附件下载</a>
						</div>
					</li>
					<li class="col-md-6 p0 "><span class=""><i class="red">＊</i>近三个月保险凭证：</span>
						<div class="input-append">
							<a>附件下载</a>
						</div>
					</li>
					<li class="col-md-6 p0 "><span class=""><i class="red">＊</i>近三年违法记录：</span>
						<div class="input-append">
							<a>附件下载</a>
						</div>
					</li>
				</ul>
			</div>
			
			<div class=" margin-bottom-0">
				<h2 class="f16 jbxx">
				<i>02</i>法人代表人信息
				</h2>
				<ul class="list-unstyled list-flow">
					<li class="col-md-6 p0 "><span class=""><i class="red">＊</i>姓名：</span>
						<div class="input-append">
							<input class="span3" id="supplierName" name="supplierName" value=" " type="text">
							<div class="b f18 fl ml10 red hand">√</div><div onclick="reason()" class="b f18 fl ml10 hand">×</div>
						</div>
					</li>
					<li class="col-md-6 p0 "><span class=""><i class="red">＊</i>身份证号：</span>
						<div class="input-append">
							<input class="span3" id="supplierTepe" name="supplierTepe" value=" "  type="text">
						</div>
					</li>
					<li class="col-md-6 p0 "><span class=""><i class="red">＊</i>固定电话：</span>
						<div class="input-append">
							<input class="span3" id="supplierChinesrName" name="supplierChinesrName" value=" " type="text">
						</div>
					</li>
					<li class="col-md-6 p0 "><span class=""><i class="red">＊</i>手机：</span>
						<div class="input-append">
							<input class="span3" id="legalName" name="legalName" value=" " type="text">
						</div>
					</li>
				</ul>
			</div>
			
			<div class=" margin-bottom-0">
				<h2 class="f16 jbxx">
				<i>03</i>联系人信息
				</h2>
				<ul class="list-unstyled list-flow">
					<li class="col-md-6 p0 "><span class=""><i class="red">＊</i>姓名：</span>
						<div class="input-append">
							<input class="span3" id="supplierName" name="supplierName" value=" " type="text">
							<div class="b f18 fl ml10 red hand">√</div><div onclick="reason()" class="b f18 fl ml10 hand">×</div>
						</div>
					</li>
					<li class="col-md-6 p0 "><span class=""><i class="red">＊</i>传真：</span>
						<div class="input-append">
							<input class="span3" id="supplierTepe" name="supplierTepe" value=" "  type="text">
						</div>
					</li>
					<li class="col-md-6 p0 "><span class=""><i class="red">＊</i>固定电话：</span>
						<div class="input-append">
							<input class="span3" id="supplierChinesrName" name="supplierChinesrName" value=" " type="text">
						</div>
					</li>
					<li class="col-md-6 p0 "><span class=""><i class="red">＊</i>手机：</span>
						<div class="input-append">
							<input class="span3" id="legalName" name="legalName" value=" " type="text">
						</div>
					</li>
					<li class="col-md-6 p0 "><span class=""><i class="red">＊</i>邮箱：</span>
						<div class="input-append">
							<input class="span3" id="legalName" name="legalName" value=" " type="text">
						</div>
					</li>
					<li class="col-md-6 p0 "><span class=""><i class="red">＊</i>地址：</span>
						<div class="input-append">
							<input class="span3" id="legalName" name="legalName" value=" " type="text">
						</div>
					</li>
				</ul>
			</div>
			
			<div class=" margin-bottom-0">
				<h2 class="f16 jbxx">
				<i>04</i>营业执照
				</h2>
				<ul class="list-unstyled list-flow">
					<li class="col-md-6 p0 "><span class=""><i class="red">＊</i>统一社会信用代码：</span>
						<div class="input-append">
							<input class="span3" id="supplierName" name="supplierName" value=" " type="text">
							<div class="b f18 fl ml10 red hand">√</div><div onclick="reason()" class="b f18 fl ml10 hand">×</div>
						</div>
					</li>
					<li class="col-md-6 p0 "><span class=""><i class="red">＊</i>登记机关：</span>
						<div class="input-append">
							<input class="span3" id="supplierTepe" name="supplierTepe" value=" "  type="text">
						</div>
					</li>
					<li class="col-md-6 p0 "><span class=""><i class="red">＊</i>注册资本：</span>
						<div class="input-append">
							<input class="span3" id="supplierChinesrName" name="supplierChinesrName" value=" " type="text">
						</div>
					</li>
					<li class="col-md-6 p0 "><span class=""><i class="red">＊</i>营业期限：</span>
						<div class="input-append">
							<input class="span3" id="legalName" name="legalName" value=" " type="text">
						</div>
					</li>
					<li class="col-md-12 p0 mt10"><span class="fl"><i class="red">＊</i>经营范围：</span>
						<div class="col-md-9 mt5">
							<div class="row">
								<textarea class="text_area col-md-12" id="supplyLevel" name="supplyLevel"  title="不超过800个字" > </textarea>
							</div>
						</div>
					</li>
					<li class="col-md-6 p0 "><span class="fl"><i class="red">＊</i>生产或经营地址：</span>
						<div class="input-append">
							<input class="span3" id="legalName" name="legalName" value=" " type="text">
						</div>
					</li>
					<li class="col-md-6 p0 "><span class=""><i class="red">＊</i>邮编：</span>
						<div class="input-append">
							<input class="span3" id="legalName" name="legalName" value=" " type="text">
						</div>
					</li>
					<li class="col-md-6 p0 "><span class=""><i class="red">＊</i>境外分支机构：</span>
						<div class="input-append">
							<input class="span3" id="legalName" name="legalName" value=" " type="text">
						</div>
					</li>
					<li class="col-md-6 p0 "><span class=""><i class="red">＊</i>国家：</span>
						<div class="input-append">
							<input class="span3" id="legalName" name="legalName" value=" " type="text">
						</div>
					</li>
					<li class="col-md-6 p0 "><span class=""><i class="red">＊</i>详细地址：</span>
						<div class="input-append">
							<input class="span3" id="legalName" name="legalName" value=" " type="text">
						</div>
					</li>
					<li class="col-md-6 p0 "><span class=""><i class="red">＊</i>机构名称：</span>
						<div class="input-append">
							<input class="span3" id="legalName" name="legalName" value=" " type="text">
						</div>
					</li>
					<li class="col-md-6 p0 "><span class=""><i class="red">＊</i>生产经营范围：</span>
						<div class="input-append">
							<input class="span3" id="legalName" name="legalName" value=" " type="text">
						</div>
					</li>
				</ul>
			</div>
			
		</div>
	</div>
	
	
<!--底部代码开始-->

</body>
</html>
