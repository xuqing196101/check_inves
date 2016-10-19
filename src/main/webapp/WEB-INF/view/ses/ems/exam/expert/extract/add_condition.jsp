<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>添加菜单</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
<link rel="stylesheet" href="<%=basePath%>public/ztree/css/demo.css"
	type="text/css">
<link rel="stylesheet"
	href="<%=basePath%>public/ztree/css/zTreeStyle.css" type="text/css">

<link rel="stylesheet"
	href="<%=basePath%>public/supplier/css/supplieragents.css"
	type="text/css">

<script type="text/javascript"
	src="<%=basePath%>public/ztree/jquery.ztree.core.js"></script>
<script type="text/javascript"
	src="<%=basePath%>public/ztree/jquery.ztree.excheck.js"></script>
<script src="<%=basePath%>public/layer/layer.js"></script>
<script src="<%=basePath%>public/layer/extend/layer.ext.js"></script>
<link rel="stylesheet" type="text/css" href="css/jquery-ui.css" /> 
<script type="text/javascript">
    $(function (){
        var areas="${listArea[0].id}";
         $.ajax({
              type:"POST",
              url:"<%=basePath%>SupplierExtracts/city.do",
              data:{area:areas},
              dataType:"json",
              success: function(data){
                   var list = data;
                   $("#city").empty();
                   for(var i=0;i<list.length;i++){
                        $("#city").append("<option value="+list[i].id+">"+list[i].name+"</option>");
                   }
              }
          });
    });
    function areas(){
      var areas=$("#area").find("option:selected").val();
      $.ajax({
          type:"POST",
          url:"<%=basePath%>SupplierExtracts/city.do",
          data:{area:areas},
          dataType:"json",
          success: function(data){
               var list = data;
               $("#city").empty();
               for(var i=0;i<list.length;i++){
                    $("#city").append("<option value="+list[i].id+">"+list[i].name+"</option>");
               }
          }
      });
    }
    
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
    
    function updates(){
    
        var id=[]; 
        $('input[name="chkItem"]:checked').each(function(){ 
            id.push($(this).val());
        }); 
        if(id.length==1){
        	  //  iframe层
            layer.open({
              type: 2,
              title:"修改条件",
              shadeClose: true,
              shade: 0.01,
              area: ['430px', '400px'],
              offset: '20px',
              content: '<%=basePath%>ExpExtract/addHeading.do?id='+id, //iframe的url
            });
        	
        }else if(id.length>1){
            layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
        }else{
            layer.alert("请现在要修改的条件",{offset: ['222px', '390px'], shade:0.01});
        }
    }      
    function del(){
        var ids =[]; 
        $('input[name="chkItem"]:checked').each(function(){ 
            ids.push($(this).val()); 
        }); 
        if(ids.length>0){
            layer.confirm('您确定要删除吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
                $('input[name="chkItem"]:checked').each(function(){ 
                    this.parentNode.parentNode.remove();
                 }); 
                 layer.close(index); 
            	
            	$.ajax({
            	          type:"POST",
            	          url:"<%=basePath%>ExtCondition/dels.do?delids="+ids,
            	          data:{delids:ids},
            	          success: function(data){
            	        
            	          }
            	      });
            });
        }else{
            layer.alert("请选择查询的条件",{offset: ['222px', '390px'], shade:0.01});
        }
    }
    function cityt(){
        $("#address").val($("#area option:selected").text()+$("#city option:selected").text());
        $("#expertId").val($("#city option:selected").val());

 
return true;
}
    
    function supervise(){
    //  iframe层
        layer.open({
          type: 2,
          title:"选择监督人员",
          shadeClose: true,
          shade: 0.01,
          offset: '20px',
          move: false,
          area: ['1000px', '500px'],
          content: '<%=basePath%>SupplierExtracts/showSupervise.do' //iframe的url
        }); 
    }
	    function opens(){
	    //  iframe层
	        layer.open({
	          type: 2,
	          title:"选择条件",
	          shadeClose: true,
	          shade: 0.01,
	          area: ['430px', '400px'],
	          offset: '20px',
	          content: '<%=basePath%>ExpExtract/addHeading.do', //iframe的url
	      
	        });
	    }
    
    </script>
<script type="text/javascript">
        var setting = {
            check: {
                enable: true,
                chkStyle: "radio",
                radioType: "all"
            },
            view: {
                dblClickExpand: false
            },
            data: {
                simpleData: {
                    enable: true
                }
            },
            callback: {
                onClick: onClick,
                onCheck: onCheck
            }
        };
        function onClick(e, treeId, treeNode) {
            var zTree = $.fn.zTree.getZTreeObj("treeDemo");
            zTree.checkNode(treeNode, !treeNode.checked, null, true);
            return false;
        }
        function onCheck(e, treeId, treeNode) {
            var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
            nodes = zTree.getCheckedNodes(true),
            v = "";
            for (var i=0, l=nodes.length; i<l; i++) {
                v += nodes[i].name + ",";
                $("#pid").val(nodes[i].id);
            }
            if (v.length > 0 ) v = v.substring(0, v.length-1);
            var cityObj = $("#citySel");
            cityObj.attr("value", v);
            
            hideMenu();
        }
        function showMenu() {
            $.ajax({
             type: "GET",
             async: false, 
             url: "<%=basePath%>preMenu/treedata.do?",
			dataType : "json",
			success : function(zNodes) {
				for (var i = 0; i < zNodes.length; i++) {
					if (zNodes[i].isParent) {

					} else {
						//zNodes[i].icon = "${ctxStatic}/images/532.ico";//设置图标  
					}
				}
				tree = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
				tree.expandAll(true);//全部展开
			}
		});
		var cityObj = $("#citySel");
		var cityOffset = $("#citySel").offset();
		$("#menuContent").css({
			left : cityOffset.left + "px",
			top : cityOffset.top + cityObj.outerHeight() + "px"
		}).slideDown("fast");
		$("body").bind("mousedown", onBodyDown);
	}
	function hideMenu() {
		$("#menuContent").fadeOut("fast");
		$("body").unbind("mousedown", onBodyDown);
	}
	function onBodyDown(event) {
		if (!(event.target.id == "menuBtn" || event.target.id == "citySel"
				|| event.target.id == "menuContent" || $(event.target).parents(
				"#menuContent").length > 0)) {
			hideMenu();
		}
	}
</script>
</head>
<script type="text/javascript">
	
</script>
<body>
	<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a href="#"> 首页</a></li>
				<li><a href="#">业务管理</a></li>
				<li><a href="#">协议采购</a></li>
				<li class="active"><a href="#">我的订单</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>
	<div class="container">
		<div class="headline-v2">
			<h2>抽取条件</h2>
		</div>
	</div>
	<div class="container">
		<div id="menuContent" class="menuContent"
			style="display: none; position: absolute; left: 0px; top: 0px; z-index: 999;">
			<ul id="treeDemo" class="ztree" style="width: 220px"></ul>
		</div>
		<form action="<%=basePath%>ExtCondition/saveExtCondition.html"
			id="form1" method="post">
			<div>
				<!--         专家所在地区 -->
				<input type="hidden" name="id" id="id" value="${ExpExtCondition.id}">
				<!--         专家所在地区 -->
				<input type="hidden" name="address" id="address" value="">
				<!--         专家所在地区id-->
				<input type="hidden" name="expertId" id="expertId" value="">

				<input type="hidden" name="projectId" id="pid" value="${projectId}">
				<!-- 				监督人员 -->
				<input type="hidden" name="sids" id="sids" value="${userId}"  />
				<ul class="list-unstyled mt10 p0_20">
					<li class="col-md-6 p0"><span class="fl mt5">专家所在地区：</span>
						<div class="input-append">
							<select class="form-control input-lg mr15 w150" id="area"
								onchange="areas();">
								<c:forEach items="${listArea }" var="area" varStatus="index">
									<option value="${area.id }">${area.name }</option>
								</c:forEach>
							</select> <select name="extractionSites" class="form-control input-lg w100"
								id="city"></select>
						</div></li>
					<li class="col-md-6 p0"><span class="fl mt5">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;专家来源：</span>
						<div class="select_common mb10">
							<select class="w250" name="expertsFrom">
								<c:choose>
									<c:when test="${ExpExtCondition.expertsFrom eq '军队'}">
										<option selected="selected" value="军队">军内</option>
										<option value="地方">地方</option>
										<option value="其他">不限</option>
									</c:when>
									<c:when test="${ExpExtCondition.expertsFrom eq '地方'}">
										<option value="军队">军内</option>
										<option selected="selected" value="地方">地方</option>
										<option value="其他">不限</option>
									</c:when>
									<c:when test="${ExpExtCondition.expertsFrom eq '其他'}">
										<option value="军队">军内</option>
										<option value="地方">地方</option>
										<option selected="selected" value="其他">不限</option>
									</c:when>
									<c:otherwise>
										<option value="军队">军内</option>
										<option value="地方">地方</option>
										<option value="其他">不限</option>
									</c:otherwise>
								</c:choose>
							</select>
						</div></li>
					<li class="col-md-6 p0 "><span class="">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;开标时间：</span>
						<div class="input-append">
							<input class="span2  Wdate w250" name="tenderTime"
								value="<fmt:formatDate value='${ExpExtCondition.tenderTime}'
                                pattern='yyyy-MM-dd' />"
								maxlength="30" onclick="WdatePicker();" type="text">
						</div></li>
					<li class="col-md-6 p0 "><span class="">专家响应时限:</span>
						<div class="input-append">
							<input class="span2 " style="width:75px;" name="responseTime"
								value="" maxlength="30"
								type="text">
								<input class="span2 " style="width:50px;" value="${hour}" readonly="readonly" name="hour"
                                value="时" maxlength="30"
                                type="text"> 
                                <input class="span2 " value="${minute}" style="width:75px;" name="minute"
                                value="" maxlength="30"
                                type="text">  
								    <input class="span2 " readonly="readonly" style="width:50px;" name="responseTime"
                                value="分" maxlength="30" onblur="blur(this);"   type="text"> 
						</div></li>
					<li class="col-md-6 p0 "><span class="">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年龄：</span>
						<div class="input-append">
							<input class="" 
								maxlength="30" style="width: 100px;" name="actionage" type="text">
								<input class="hide" name="" style="width: 50px;" maxlength="30" value=" — " type="text" >
								    <input class="" name="endage" 
                                maxlength="30" style="width: 100px;"  type="text">
						</div></li>
					<li class="col-md-6 p0 "><span class="">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;监督人员:</span>
						<div class="input-append">
						<input class="span2 w250" readonly id="supervises" value="${userName}"  onclick="supervise();" type="text"> 
						</div></li>
			</div>
			<div align="right" class="col-md-12">
				<button class="btn padding-left-10 padding-right-10 btn_back"
					id="save" onclick="opens();" type="button">添加</button>
<!-- 				<button class="btn padding-left-10 padding-right-10 btn_back" -->
<!-- 					id="update" onclick="updates();" type="button">修改</button> -->
				<button class="btn padding-left-10 padding-right-10 btn_back"
					id="backups" onclick="del();" type="button">删除</button>
				<table class="table table-bordered table-condensed mt5">
					<thead>
						<tr>
							<th class="info w30"><input type="checkbox" id="checkAll"
								onclick="selectAll()" alt=""></th>
							<th class="info">专家类型</th>
							<th class="info">专家数量</th>
							<th class="info">执业资格</th>
							<th class="info">产品类别</th>
						</tr>
					</thead>
					<tbody id="tbody">
						<c:forEach items="${ExpExtCondition.conTypes}" var="conTypes">
							<tr>
								<input class="hide" type="hidden" name="typeId"
									value="${conTypes.id}">
								<input class="hide" type="hidden" name="expertsTypeId"
									value="${conTypes.expertsTypeId }">
								<input class="hide" type="hidden" name="extCategoryId"
									value="${conTypes.categoryId }">
								<input class="hide" type="hidden" name="isSatisfy"
									value="${conTypes.isMulticondition }">
								<td class='tc w30'><input type="checkbox"
									value="${conTypes.categoryId},${conTypes.expertsTypeId},${conTypes.expertsCount},${conTypes.expertsQualification}"
									name="chkItem" onclick="check()"></td>
								<td class="tc"><c:if test="${conTypes.expertsTypeId==1 }">
										<input readonly="readonly" class="hide" type="text" value="技术">
									</c:if> <c:if test="${conTypes.expertsTypeId==2}">
										<input readonly="readonly" class="hide" type="text" value="法律">
									</c:if> <c:if test="${conTypes.expertsTypeId==3 }">
										<input readonly="readonly" class="hide" type="text"
											value="商务 ">

									</c:if></td>
								<td class="tc"><input class="hide" readonly="readonly"
									name="extCount" type="text" value="${conTypes.expertsCount }"></td>
								<td class="tc"><input class="hide" readonly="readonly"
									name="extQualifications" type="text"
									value="${conTypes.expertsQualification }"></td>
								<td class="tc"><input class="hide" readonly="readonly"
									name="extCategoryName" type="text"
									value="${conTypes.categoryName }"></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div align="right" class=" padding-10">
				<button class="btn btn-windows save" id="save" onclick="cityt();"
					type="submit">保存抽取条件</button>
			</div>
		</form>
	</div>
</body>
</html>
