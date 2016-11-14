<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]-->
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>基本信息</title>
<!-- Meta -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<script type="text/javascript">
function reason(id){
  var supplierId=$("#id").val();
  var id1=id+"1";
  var id2=id+"2";
  var id3=id+"3";
  var auditField=$("#"+id2+"").text().replaceAll("＊","").replaceAll("：",""); //审批的字段名字
  var  auditContent= document.getElementById(""+id3+"").value; //审批的字段内容
  var auditType=$("#essential").text(); //审核类型
  layer.prompt({title: '请填写不通过理由', formType: 2}, function(text){
    $.ajax({
        url:"${pageContext.request.contextPath}/supplierAudit/auditReasons.html",
        type:"post",
        data:"auditType="+auditType+"&auditField="+auditField+"&auditContent="+auditContent+"&suggest="+text+"&supplierId="+supplierId,
      });
  $("#"+id1+"").hide();
  layer.msg("审核不通过的理由是："+text);    
    });
}
function tijiao(str){
  var action;
  if(str=="essential"){
     action ="${pageContext.request.contextPath}/supplierQuery/essential.html";
  }
  if(str=="financial"){
    action = "${pageContext.request.contextPath}/supplierQuery/financial.html";
  }
  if(str=="shareholder"){
    action = "${pageContext.request.contextPath}/supplierQuery/shareholder.html";
  }
  if(str=="materialProduction"){
    action = "${pageContext.request.contextPath}/supplierQuery/materialProduction.html";
  }
  if(str=="materialSales"){
    action = "${pageContext.request.contextPath}/supplierQuery/materialSales.html";
  }
  if(str=="engineering"){
    action = "${pageContext.request.contextPath}/supplierQuery/engineering.html";
  }
  if(str=="service"){
    action = "${pageContext.request.contextPath}/supplierQuery/serviceInformation.html";
  }
  if(str=="chengxin"){
    action = "${pageContext.request.contextPath}/supplierQuery/list.html";
  }
  if(str=="item"){
     action = "${pageContext.request.contextPath}/supplierQuery/item.html";
  }
  $("#form_id").attr("action",action);
  $("#form_id").submit();
}

function downloadFile(fileName){
	  fileName=encodeURI(fileName);
      fileName=encodeURI(fileName);
	  window.location.href="${pageContext.request.contextPath}/supplierQuery/downLoadFile.html?fileName="+fileName;
}
function fanhui(){
	  window.location.href="${pageContext.request.contextPath}/supplierQuery/findSupplierByPriovince.html?address="+encodeURI(encodeURI('${suppliers.address}'))+"&status=${status}";
}
   $(function() {
    var zTreeObj;
	var zNodes;
	loadZtree();
	function loadZtree() {
		var setting = {
			async : {
				enable : true,
				url : "${pageContext.request.contextPath}/category/find_category_and_disabled.do",
				otherParam : {
					supplierId : "${id}",
				},
				dataType : "json",
				type : "post",
			},
			check : {
				enable : true,
				chkboxType : {
					"Y" : "s",
					"N" : "s"
				}
			},
			data : {
				simpleData : {
					enable : true,
					idKey : "id",
					pIdKey : "parentId"
				}
			},
		};
		zTreeObj = $.fn.zTree.init($("#ztree"), setting, zNodes);
	}
	});
</script>

<style type="text/css">
.jbxx1{
  background:url(../images/down_icon.png) no-repeat 5px !important;
  padding-left:40px !important;
}
.jbxx1 i{
    width: 24px;
    height: 30px;
    background: url(../../../../../zhbj/public/ZHQ/images/round.png) no-repeat center;
    color: #ffffff;
    font-size: 12px;
    text-align: center;
    display: block;
    float: left;
    line-height: 30px;
    font-style: normal;
    margin-right: 10px;
}
.line{
	border: 4px solid #fff0;
}

</style>
</head>
  
<body>
 <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">支撑系统</a></li><li><a href="#">供应商查看</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
  <!-- 项目戳开始 -->
  <div class="container clear margin-top-30">
  <!--详情开始-->
  <div class="container content height-350">
    <div class="row magazine-page">
      <div class="col-md-12 tab-v2 job-content">
          <ul class="nav nav-tabs bgdd">
            <li class=""><a aria-expanded="true" href="#tab-1" data-toggle="tab" onclick="tijiao('essential');">基本信息</a></li>
          <li class=""><a aria-expanded="false" href="#tab-2" data-toggle="tab" onclick="tijiao('financial');">财务信息</a></li>
            <li class=""><a aria-expanded="fale" href="#tab-3" data-toggle="tab" onclick="tijiao('shareholder');">股东信息</a></li>
          <li class=""><a aria-expanded="fale" href="#tab-2" data-toggle="tab" onclick="tijiao('materialProduction');">物资-生产型专业信息</a></li>
            <li class=""><a aria-expanded="fale" href="#tab-3" data-toggle="tab" onclick="tijiao('materialSales');">物资-销售型专业信息</a></li>
            <li class=""><a aria-expanded="false" href="#tab-3" data-toggle="tab" onclick="tijiao('engineering');">工程-专业信息</a></li>
            <li class=""><a aria-expanded="false" href="#tab-3" data-toggle="tab" onclick="tijiao('service');">服务-专业信息</a></li>
            <li class="active"><a aria-expanded="false" href="#tab-2" data-toggle="tab" onclick="tijiao('item');">品目信息</a></li>
            <li class=""><a aria-expanded="false" href="#tab-3" data-toggle="tab" >产品信息</a></li>
            <li class=""><a aria-expanded="false" href="#tab-2" data-toggle="tab" onclick="tijiao('chengxin');">诚信记录</a></li>
          </ul>
            <div class="tab-content padding-top-20">
              <div class="tab-pane fade active in height-450" id="tab-1">
                <form id="form_id" action="" method="post">
                    <input name="supplierId" id="id" value="${id }" type="hidden">
                  </form> 
                  <div class="col-md-3">
	 					<div id="ztree" class="ztree"></div>
					</div>                
              </div>
          </div>
      </div>
    </div>
  </div>
</div>
</body>
</html>
