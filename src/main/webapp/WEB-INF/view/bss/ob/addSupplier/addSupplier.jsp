<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
		<link href="${pageContext.request.contextPath }/public/select2/css/select2.css" rel="stylesheet" />
	<title>添加质检供应商页面</title>
	<script type="text/javascript">
	//加载供应商
	$(function(){
		$("#extensionId").val("bmp,pmg,jpg,gif,png");
		$.ajax({
			url: "${pageContext.request.contextPath }/obSupplier/findAllSupplier.do",
			contentType: "application/json;charset=UTF-8",
			dataType: "json", //返回格式为json
			type: "POST", //请求方式           
		success: function(data) {
			if (data) {
				list=data;
				$.each(data, function(i, supplier) {
					if(supplier.supplierName != null){
						$("#supplierId").append("<option  value=" + supplier.id + ">" + supplier.supplierName + "</option>");
					}
				});
			} 
			$("#supp").remove();
			$("#supplierId").show();
			$("#supplierId").select2();
			$("#supplierId").select2("val", "${obSupplier.supplierId}");
		}
		});
		intiTree();
	});
	
	function change(){
		var option=$("#supplierId option:selected").val();
		$.ajax({
			url: "${pageContext.request.contextPath }/obSupplier/findUsccById.do",
			data:{
				option : option
			},
			type: "POST", //请求方式           
		success: function(data) {
				$("#uscc").val(data);
			}
		});
	}
	
	function yichu(){
		$("#shangchuan").html("");
	}
	
	/** 判断是否为根节点 */
    function isRoot(node){
    	if (node.pId == 0){
    		return true;
    	} 
    	return false;
    }
 
 /*点击事件*/
    function zTreeOnClick(event,treeId,treeNode){
  	  if (isRoot(treeNode)){
  		  layer.msg("不可选择根节点");
  		  return;
  	  }
	  if(!treeNode.isParent) {
		  $("#citySel4").val(treeNode.name);
          $("#categorieId4").val(treeNode.id);
          $("#categoryLevel").val(treeNode.level+1);
          hideMenu();
	  }
    }
	
    function intiTree(){
  	  /* 加载目录信息 */
  		var datas;
  		var setting={
  				   async:{
  							autoParam:["id"],
  							enable:true,
  							otherParam:{"otherParam":"zTreeAsyncTest"},  
  							dataType:"json",
  							type:"get",
  						},
  						callback:{
  					    	onClick:zTreeOnClick,//点击节点触发的事件
  		       			    
  					    }, 
  						data:{
  							keep:{
  								parent:true
  							},
  							key:{
  								title:"title"
  							},
  							simpleData:{
  								enable:true,
  								idKey:"id",
  								pIdKey:"pId",
  								rootPId:"0",
  							}
  					    },
  					   view:{
  					        selectedMulti: false,
  					        showTitle: false,
  					         showLine: true
  					   },
  		         };
  		$.ajax({
			url: "${pageContext.request.contextPath}/obSupplier/createtreeByproduct.do",
			async: false,
			dataType: "json",
			success: function(data){
				if (data.length == 1) {
					layer.msg("没有符合查询条件的产品类别信息！");
				} else {
					zNodes = data;
					zTreeObj = $.fn.zTree.init($("#treeDemo"),setting,zNodes);
				}
			}
		});
  	    
    }
   
      function showMenu() {
  		var cityObj = $("#citySel4");
  		var cityOffset = $("#citySel4").offset();
  		$("#menuContent").css({}).slideDown("fast");
  		$("body").bind("mousedown", onBodyDown);
  	}//left: "1049px", top: "282px"
      function hideMenu() {
  		$("#menuContent").fadeOut("fast");
  		$("body").unbind("mousedown", onBodyDown);
  	}
  	function onBodyDown(event) {
  		if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
  			hideMenu();
  		}
  	} 
	
	function searchs(){
		var name=$("#search").val();
		if(name!=""){
		 var zNodes;
			var zTreeObj;
			var datas;
	  		var setting={
	  				   async:{
	  							autoParam:["id"],
	  							enable:true,
	  							otherParam:{"otherParam":"zTreeAsyncTest"},  
	  							dataType:"json",
	  							type:"get",
	  						},
	  						callback:{
	  					    	onClick:zTreeOnClick,//点击节点触发的事件
	  		       			    
	  					    }, 
	  						data:{
	  							keep:{
	  								parent:true
	  							},
	  							key:{
	  								title:"title"
	  							},
	  							simpleData:{
	  								enable:true,
	  								idKey:"id",
	  								pIdKey:"pId",
	  								rootPId:"0",
	  							}
	  					    },
	  					   view:{
	  					        selectedMulti: false,
	  					        showTitle: false,
	  					         showLine: true
	  					   },
	  		         };
			// 加载中的菊花图标
			 var loading = layer.load(1);
			
				$.ajax({
					url: "${pageContext.request.contextPath}/obSupplier/createtreeByproduct.do",
					data: { "name" : encodeURI(name)},
					async: false,
					dataType: "json",
					success: function(data){
						if (data.length == 1) {
							layer.msg("没有符合查询条件的产品类别信息！");
						} else {
							zNodes = data;
							zTreeObj = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
							zTreeObj.expandAll(true);//全部展开
						}
						// 关闭加载中的菊花图标
						layer.close(loading);
						
					}
				});
		}else{
			intiTree();
		} 
	}
	
	</script>
</head>
<body>
<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)"> 首页</a></li><li><a href="javascript:void(0)">保障作业</a></li><li><a href="javascript:void(0)">定型产品竞价</a></li>
		   <li class="active"><a href="javascript:void(0)">添加质检供应商</a></li><li class="active"><a href="javascript:void(0)">添加质检供应商</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>

<!-- 修改订列表开始-->
   <div class="container container_box">
   <form action="${pageContext.request.contextPath}/obSupplier/add.html" method="post">
	<input name = "id" value = "${obSupplier.id }" style="display: none;">
   <div>
    <h2 class="list_title">添加质检供应商</h2>
   <ul class="ul_list">
     <li class="col-md-3 col-sm-6 col-xs-12 pl15">
	   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="red star_red">*</div>供应商名称</span>
	   <div class="select_common col-md-12 col-sm-12 col-xs-12 p0" id="supplierselect">
		<select id="supplierId" name="supplierId" class="hide" onchange="change()">
  			<option value=""></option>
		</select>
		<select  id="supp" class="">
  			<option value=""></option>
		</select>
		<div class="cue">${errorName }</div>
		</div>
	 </li>
	 
     <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>证书有效期至</span>
	   <div class="input-append input_group col-sm-12 col-xs-12 p0">
        <input class="input_group" id="" value="<fmt:formatDate value="${obSupplier.certValidPeriod }" pattern="yyyy-MM-dd" /> " name = "certValidPeriod" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="readonly">
        <span class="add-on">i</span>
        <div class="cue">${errorCertValidPeriod }</div>
       </div>
	 </li>
     <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="red star_red">*</div>质检机构</span>
	   <div class="input-append input_group col-sm-12 col-xs-12 p0">
        <input class="input_group" name = "qualityInspectionDep" id="" type="text" value="${obSupplier.qualityInspectionDep }">
        <span class="add-on">i</span>
        <div class="cue">${errorQualityInspectionDep }</div>
       </div>
	 </li> 
     <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>联系人姓名</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" name = "contactName" id="" type="text" value="${obSupplier.contactName }">
        <span class="add-on">i</span>
        <div class="cue">${errorContactName }</div>
       </div>
	 </li> 
     <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>联系人电话</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" id="" name = "contactTel" type="text" value="${obSupplier.contactTel }">
        <span class="add-on">i</span>
        <div class="cue">${errorContactTel }</div>
       </div>
	 </li> 
     <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>资质证书编号</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" id="" name = "certCode" type="text" value="${obSupplier.certCode }">
        <span class="add-on">i</span>
        <div class="cue">${errorCertCode }</div>
       </div>
	 </li> 
     <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><div class="red star_red">*</div>统一社会信用代码</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" name = "uscc" id="uscc" type="text" readonly="readonly" value="${obSupplier.uscc }">
        <span class="add-on">i</span>
        <div class="cue">${errorUscc }</div>
       </div>
	 </li>
	 <li class="col-md-3 col-sm-6 col-xs-12">
	 	<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><div class="red star_red">*</div>产品目录</span>
	 	<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        	<input class="input_group" id="citySel4" type="text" value="${catName }" onclick=" showMenu(); return false;" readonly="readonly" >
        	<input id="categorieId4" name="smallPointsId" value="${obSupplier.smallPointsId }" type="hidden">
        	<span class="add-on">i</span>
        	<div class="cue">${errorsmallPoints }</div>
        <!-- 目录框 -->
		<div id="menuContent" class="menuContent col-md-12 col-xs-12 col-sm-12 p0 tree_drop" style="z-index:10000;position:absolute;top:30px;left:0px" hidden="hidden">
			<div class="col-md-12 col-xs-8 col-sm-8 p0">
			    <input type="text" id="search" class="input_group">
			    <img alt="" src="${pageContext.request.contextPath }/public/backend/images/view.png" style="position: absolute; right: 10px;top: 5px;" onclick="searchs()">
			</div>
			<ul id="treeDemo" class="ztree slect_option clear" style="max-height: 340px;"></ul>
		</div>
        </div>
	 </li>
	<li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="red star_red">*</div>资质证书：</span>
        <div class="input-append input_group col-sm-12 col-xs-12 p0 " onmousedown="yichu()">
        <u:upload id="post_attach_up" businessId="${obSupplier.id }" sysKey="2" typeId="46" multiple="true" auto="true" />
		<u:show showId="post_attach_show" businessId="${obSupplier.id }" sysKey="2" typeId="46"/>
     	<div class="cue" id = "shangchuan">${errorShangchuan }</div>
     	</div>
     	
	 </li>
	 
   </ul>
   <div class="col-md-12 clear tc mt10">
	    	<button class="btn btn-windows save" type="submit">保存</button>
	    	<button class="btn btn-windows back" type="button" onclick="window.location.href='${pageContext.request.contextPath}/obSupplier/supplier.html'">返回</button>
	    </div>
       <div class="clear"></div> 
  </div> 
  </form>
  </div>
  
</body>
</html>