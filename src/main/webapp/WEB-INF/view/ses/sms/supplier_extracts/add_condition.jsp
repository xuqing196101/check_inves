<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>
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

<link rel="stylesheet"
    href="<%=basePath%>public/ztree/css/zTreeStyle.css" type="text/css">

<link rel="stylesheet"
    href="<%=basePath%>public/supplier/css/supplieragents.css"
    type="text/css">
<script type="text/javascript"
    src="<%=basePath%>public/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
    src="<%=basePath%>public/ztree/jquery.ztree.core.js"></script>
<script type="text/javascript"
    src="<%=basePath%>public/ztree/jquery.ztree.excheck.js"></script>
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
              content: '<%=basePath%>SupplierExtracts/addHeading.do?id='+id, //iframe的url
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
                          url:"<%=basePath%>SupplierExtracts/dels.do?delids="+ids,
                          data:{delids:ids},
                          success: function(data){
                        
                          }
                      });
            });
        }else{
            layer.alert("请要删除的条件",{offset: ['222px', '390px'], shade:0.01});
        }
    }
    function cityt(){
        $("#address").val($("#area option:selected").text()+$("#city option:selected").text());
        $("#expertId").val($("#city option:selected").val());
        $.ajax({
            cache: true,
            type: "POST",
            dataType : "json",
            url:'<%=basePath%>SupplierCondition/saveSupplierCondition.do',
            data:$('#form1').serialize(),// 你的formid
            async: false,
            success: function(data) {
            	$("#supervise").text("");
            	   $("#array").text("");
            	var map =data;
            	$("#supervise").text(map.supervise);
            	$("#array").text(map.array);
            	if(map.sccuess=="sccuess"){
            		  window.location.href = '<%=basePath%>/SupplierExtracts/Extraction.do?id=${projectId}&&typeclassId=${typeclassId}';
            	}
            }
        });
        
return false;
}
    
    function supervise(){
    //  iframe层
    var iframeWin;
        layer.open({
          type: 2,
          title:"选择监督人员",
          shadeClose: true,
          shade: 0.01,
          offset: '20px',
          move: false,
          area: ['90%', '50%'],
          content: '<%=basePath%>SupplierExtracts/showSupervise.do',
          success: function(layero, index){
              iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
            },
          btn: ['保存', '关闭'] 
              ,yes: function(){
                  iframeWin.add();
              
              }
              ,btn2: function(){
                layer.closeAll();
              }
        }); 
    }
        function opens(){
        //  iframe层
          var iframeWin;
            layer.open({
              type: 2,
              title:"选择条件",
              shadeClose: true,
              shade: 0.01,
              area: ['430px', '400px'],
              offset: '20px',
              content: '<%=basePath%>SupplierExtracts/addHeading.do', //iframe的url
              success: function(layero, index){
                  iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                },
              btn: ['保存', '重置'] 
                  ,yes: function(){
                      iframeWin.getChildren();
                  
                  }
                  ,btn2: function(){
                	  opens();
                  }
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
	<c:if test="${typeclassId==null  || typeclassId=='' || typeclassId==0}">
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li><a href="#"> 首页</a></li>
					<li><a href="#">支撑环境系统</a></li>
					<li><a href="#">供应商管理</a></li>
					<li><a href="#">供应商抽取</a></li>
					<li class="active"><a href="#">添加供应商抽取</a></li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
	</c:if>
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
		<form id="form1" method="post">
				<!--         条件id-->
				<input type="hidden" name="id" id="id" value="${ExpExtCondition.id}">
				<!--         专家所在地区 -->
				<input type="hidden" name="address" id="address" value="">
				<!--          项目id -->
				<input type="hidden" name="projectId" id="pid" value="${projectId}">
				<!--          监督人员 -->
				<input type="hidden" name="sids" id="sids" value="${userId}" />
				<ul class="demand_list">
					<li><label class="fl"><span class="red">*</span>供应商所在地区：</label><span>
							<select class="w150" id="area" onchange="areas();">
								<c:forEach items="${listArea }" var="area" varStatus="index">
									<option value="${area.id }">${area.name }</option>
								</c:forEach>
						</select> <select name="extractionSites" class="w96" id="city"></select>
						  <div class="b f14 red tip w200"></div>
					</span></li>
<!-- 					<li><label class="fl"><span class="red">*</span>供应商等级：</label><span> -->
<!-- 							<select class="w250"> -->
<!-- 								<option>一级</option> -->
<!-- 								<option>二级</option> -->
<!-- 								<option>三级</option> -->
<!-- 								<option>四级</option> -->
<!-- 								<option>五级</option> -->
<!-- 						</select> -->
<!-- 						  <div class="b f14 red tip w200"></div> -->
<!-- 					</span></li> -->
    
					<li><label class="fl"><span class="red">*</span>监督人员：</label><span>
							<input class=" w250" readonly id="supervises" title="${userName}"
							value="${userName}" onclick="supervise();" type="text">
							<div class="b f14 red tip w200" id="supervise"></div>

					</span></li>

				</ul>

			<div align="right" >
				<button class="btn padding-left-10 padding-right-10 btn_back"
					id="save" onclick="opens();" type="button">添加</button>
				<!--                 <button class="btn padding-left-10 padding-right-10 btn_back" -->
				<!--                     id="update" onclick="updates();" type="button">修改</button> -->
				<button class="btn padding-left-10 padding-right-10 btn_back"
					id="backups" onclick="del();" type="button">删除</button>
				<table class="table table-bordered table-condensed mt5">
					<thead>
						<tr>
							<th class="info w30"><input type="checkbox" id="checkAll"
								onclick="selectAll()" alt=""></th>
							<th class="info">供应商类型</th>
							<th class="info">供应商抽取数量</th>
							<th class="info">产品类别</th>
						</tr>
					</thead>
					<tbody id="tbody">
						<c:forEach items="${ExpExtCondition.conTypes}" var="conTypes">
							<tr>
								<input class="hide" type="hidden" name="typeId"
									value="${conTypes.id}">
								<input class="hide" type="hidden" name="expertsTypeId"
									value="${conTypes.supplieTypeId }">
								<input class="hide" type="hidden" name="extCategoryId"
									value="${conTypes.categoryId }">
								<input class="hide" type="hidden" name="isSatisfy"
									value="${conTypes.isMulticondition }">
								<td class='tc w30'><input type="checkbox"
									value="${conTypes.categoryId}" name="chkItem" onclick="check()"></td>
								<td class="tc"><c:if
										test="${conTypes.supplieTypeId=='1^2^' }">
										<input readonly="readonly" class="hide" type="text"
											value="生产型,销售型">
									</c:if> <c:if test="${conTypes.supplieTypeId=='1^'}">
										<input readonly="readonly" class="hide" type="text"
											value="生产型">
									</c:if> <c:if test="${conTypes.supplieTypeId=='2^' }">
										<input readonly="readonly" class="hide" type="text"
											value="销售型">
									</c:if></td>
								<td class="tc"><input class="hide" readonly="readonly"
									name="extCount" type="text" value="${conTypes.supplieCount }"></td>
								<td class="tc"><input class="hide" readonly="readonly"
									name="extCategoryName" type="text"
									value="${conTypes.categoryName }"></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div align="right" class="padding-10">
					<div class="col-md-12 b f14 red tip" id="array"></div>
					<button class="btn btn-windows save" id="save" onclick="cityt();"
						type="button">保存抽取条件</button>
				</div>
			</div>

		</form>
	</div>
</body>
</html>
