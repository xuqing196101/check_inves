<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/public/oms/css/demo.css"> 
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/public/oms/css/zTreeStyle/zTreeStyle.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/public/oms/css/purchase.css"> 
	
    <script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/public/ztree/jquery.ztree.core.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/public/ztree/jquery.ztree.excheck.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/public/ztree/jquery.ztree.exedit.js"></script>
    <!--导航js-->
    <script src="${pageContext.request.contextPath}/public/ZHH/js/jquery_ujs.js"></script>
    <script src="${pageContext.request.contextPath}/public/ZHH/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
    <script src="${pageContext.request.contextPath}/public/oms/js/select-tree.js"></script>
    <script src="${pageContext.request.contextPath}/public/laypage-v1.3/laypage/laypage.js"></script>
    <script src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
    <script src="${pageContext.request.contextPath}/public/layer/extend/layer.ext.js"></script>
    <%
    	String packageId = (String)request.getAttribute("packageId");
     %>
<script type="text/javascript">
	/* layer.config({
		    extend: 'extend/layer.ext.js'
		}); */
    //${pageContext.request.contextPath}/intelligentScore/getMarkTermTree.do?packageId=A1CC4A603B6F466C936BBA2BA7BC2317
     var id = $("#packageId").val();
     var setting = {  
        async : {  
            enable : true,//开启异步加载处理  
            url : "${pageContext.request.contextPath}/intelligentScore/getMarkTermTree.do?packageId=<%=packageId%>",  
            autoParam : [ "id" ],  
            dataFilter : filter,  
            contentType : "application/json",  
            type : "post"  
        },  
        view : {  
            expandSpeed : "",  
            addHoverDom : addHoverDom,  
            removeHoverDom : removeHoverDom,  
            selectedMulti : false  
        },  
        edit : {  
            enable : true  
        },  
        data : {  
            simpleData : {  
                enable : true  
            }  
        },  
        callback : {  
            beforeRemove : beforeRemove,  
            beforeRename : beforeRename, 
            onClick:zTreeOnClick 
        }  
    };  
    function filter(treeId, parentNode, childNodes) {  
        if (!childNodes)  
            return null;  
        for (var i = 0, l = childNodes.length; i < l; i++) {  
            //childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');  
        }  
        return childNodes;  
    }  
    function beforeRemove(treeId, treeNode) {  
    	var content = "您确认删除节点--" + treeNode.name + "--吗?";
    	var packageId = $("#packageId").val();
    	layer.confirm(content, {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
				$.post("${pageContext.request.contextPath}/intelligentScore/operatorNode.do",{id:treeNode.id,method:"delnode",packageId:packageId}); 
       		   // $("#addBtn_" + treeNode.tId).unbind().remove();  
				layer.close(index); 
		});
    }  
    function beforeRename(treeId, treeNode, newName) {  
    	var packageId = $("#packageId").val();
    	console.dir(packageId);
        if (newName.length == 0) {  
            alert("节点名称不能为空.");  
            return false;  
        }  
        var param = "id=" + treeNode.id + "&name=" + newName;  
        $.post("${pageContext.request.contextPath}/intelligentScore/operatorNode.do",{id:treeNode.id,name:newName,method:"updatenode",packageId:packageId});  
        return true;  
    }  
  
    function addHoverDom(treeId, treeNode) { 
    	var packageId = $("#packageId").val();
        var sObj = $("#" + treeNode.tId + "_span");  
        if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0)  
            return;  
        var addStr = "<span class='button add' id='addBtn_" + treeNode.tId  
                + "' title='add node' onfocus='this.blur();'></span>";  
        sObj.after(addStr);  
        var btn = $("#addBtn_" + treeNode.tId);  
        if (btn)  
            btn.bind("click", function() {  
                //var Ppname = prompt("请输入新节点名称");  
                var Ppname="";
               layer.prompt({
                    title: '输入节点名称',
                    formType: 2,
                    offset:'100px'
                }, function (str,index) {
                    if (str) {
                        //需要执行的方法
                        if (str == null) {  
                    return;  
                } else if (str == "") {  
                    alert("节点名称不能为空");  
                } else {  
                     
                    var zTree = $.fn.zTree.getZTreeObj("treeDemo");  
                    $.post(  
                           "${pageContext.request.contextPath}/intelligentScore/operatorNode.do",
                           {pid:treeNode.id,name:str,method:"addnode",packageId:packageId}, function(data) {  
                                if (data.success) {  
                                    //var treenode = $.trim(data);  
                                    zTree.addNodes(treeNode, { 
                                    	id:data.message, 
                                        pId : treeNode.id,  
                                        name : str  
                                    }, true);  
                                }  
                            });  
                } 
                        layer.close(index);
                    }else{
                    	consolr.dir(2);
                    };
                });
               
				 //
				
				 //
                 
  
            });  
    };  
    function removeHoverDom(treeId, treeNode) {  
       $("#addBtn_" + treeNode.tId).unbind().remove();  
    };  
    $(document).ready(function() {  
        $.fn.zTree.init($("#treeDemo"), setting);  
  
    });  
    function zTreeOnClick(event,treeId,treeNode){
    	var id = $("#packageId").val();
		console.dir(treeNode);
		if(!treeNode.isParent){
			$("#treebody").attr("src","${pageContext.request.contextPath}/intelligentScore/gettreebody.do?id="+treeNode.id+"&packageId="+id+"&name="+encodeURI(encodeURI(treeNode.name)));
		}
	}
	function choseModel(){
		var model = $("#model").val();
		console.dir(model);
		if(model==""){
			$("#show_table tbody tr").remove();
		}else if(model=="0"){
			$("#show_table tbody tr").remove();
			$("#model1 tbody tr").clone().appendTo("#show_table tbody");
		}else if(model=="1"){
			$("#show_table tbody tr").remove();
			$("#model21 tbody tr").clone().appendTo("#show_table tbody");
		}else if(model=="2"){
			$("#show_table tbody tr").remove();
			$("#model3 tbody tr").clone().appendTo("#show_table tbody");
		}else if(model=="3"){
			$("#show_table tbody tr").remove();
			$("#model4 tbody tr").clone().appendTo("#show_table tbody");
		}else if(model=="4"){
			$("#show_table tbody tr").remove();
			$("#model5 tbody tr").clone().appendTo("#show_table tbody");
		}else if(model=="5"){
			$("#show_table tbody tr").remove();
			$("#model6 tbody tr").clone().appendTo("#show_table tbody");
		}else if(model=="6"){
			$("#show_table tbody tr").remove();
			$("#model7 tbody tr").clone().appendTo("#show_table tbody");
		}else if(model=="7"){
			$("#show_table tbody tr").remove();
			$("#model8 tbody tr").clone().appendTo("#show_table tbody");
		}
	}
	function modelTwoAddSubstact(){
		var model = $("#model").val();
		if(model=="0"){
			$("#show_table tbody tr").remove();
			$("#model21 tbody tr").clone().appendTo("#show_table tbody");
		}else{
			$("#show_table tbody tr").remove();
			$("#model22 tbody tr").clone().appendTo("#show_table tbody");
		}
	}
	function reList(projectId){
		window.location.href = "${pageContext.request.contextPath}/intelligentScore/packageList.do?projectId="+projectId;
	}
</script>  
<style type="text/css">  
.ztree li span.button.add {  
    margin-left: 2px;  
    margin-right: -1px;  
    background-position: -144px 0;  
    vertical-align: top;  
    *vertical-align: middle  
}  
</style>  
</head>  
<body>
	<input id="packageId" type="hidden" value="${packageId }">
	<div class="container content height-350">
		<div class="row">
			<div class="mt40  mb50">
				<input type="button" class="btn  padding-right-20 btn_back margin-5"
					onclick="reList('${projectId}');" value="返回">
			</div>
			<div class="col-md-12" style="min-height:400px;">
				<div class="col-md-3 md-margin-bottom-40 fl" id="show_tree_div">
					<div class="tag-box tag-box-v3">
						<ul id="treeDemo" class="ztree">
						</ul>
					</div>

				</div>
				<div class="tag-box tag-box-v4 col-md-9 fr" id="show_content_div" style="overflow: auto;">
				
					<%-- <div>
						<form
							action="${pageContext.request.contextPath}/purchaseManage/create.do"
							method="post" onsubmit="return check();" id="formID">
							<div class="mt20 mr20">
								<span>选择模型</span> <select id="model" name="" onchange="choseModel();">
									<option value="">请选择</option>
									<option value="0">模型1:是否判断</option>
									<option value="1">模型2:按项加减分</option>
									<option value="2">模型3:评审数额最高递减</option>
									<option value="3">模型4:评审数额最低递增</option>
									<option value="4">模型5:评审数额高计算</option>
									<option value="5">模型6:评审数额低计算</option>
									<option value="6">模型7:评审数额地区间递增</option>
									<option value="7">模型8:评审数额高区间递减</option>
								</select>
							</div>
							<table class="table table-striped table-bordered table-hover" id="show_table"
								style="width: 386px;">
								<tbody>
								</tbody>
							</table>
						</form>

					</div> --%>
					 <iframe id="treebody" name="treeframe"
				src=""
				frameborder="0" style="width: 100%;height: 100%;"> </iframe> 
				 <%-- <iframe id="treebody" name="treeframe"
				src="${pageContext.request.contextPath}/intelligentScore/gettreebody.do?packgeId=${packageId}"
				frameborder="0" style="width: 100%;height: 100%;"> </iframe>  --%>
				</div>
			</div>
		</div>
	</div>
	<!-- 八大模型 -->
	<table id="model1" style="display: none;width: 386px;">
		<tbody>
			<tr>
				<td style="width: 300px;">标准分值</td>
				<td><input name="standardScore" id="standardScore" title="该项的满分值为多少"></td>
			</tr>
			<tr>
				<td>判断内容</td>
				<td><input name="judgeContent" id="judgeContent" title="该项内容为判断的唯一依据"></td>
			</tr>
			<tr>
				<td>翻译成白话文内容</td>
				<td><textarea readonly="readonly" class="wh212-67" name="easyUnderstandContent" id="easyUnderstandContent"></textarea></td>
			</tr>
			<tr>
				<td>当前模型标准解释</td>
				<td><textarea class="wh212-67" name="standExplain" id="standExplain" value="" readonly="readonly">是否判断.采购文件明确满足或不满足项的临界值或有无的项目要求。评审系统自动识别满足不满足，生成通过或否决的结果，如(必要设备，关键技术，员工人数等)</textarea></td>
			</tr>
		</tbody>
	</table>
	<table id="model21" style="display: none;width: 386px;">
		<tbody>
			<tr>
				<td style="width: 300px;">评审参数</td>
				<td><input name="reviewParam" id="reviewParam" title="例如，近五年获得省以上工商部门颁发知名品牌的数量，一个得1分"></td>
			</tr>
			<tr>
				<td>加减分类型</td>
				<td><select name="AddSubtractTypeName" id="AddSubtractTypeName" onchange="modelTwoAddSubstact();"><option value="0" selected="selected">加分</option><option value="1">减分</option></select></td>
			</tr>
			<tr>
				<td style="width: 300px;">起始参数</td>
				<td><input name="reviewStandScore" id="reviewStandScore" value="0" title="该项的起始分值为多少，默认是0"></td>
			</tr>
			<tr>
				<td style="width: 300px;">最高分</td>
				<td><input name="maxScore" id="maxScore" title="该项的满分值是多少"></td>
			</tr>
			<tr>
				<td style="width: 300px;">每单位分值</td>
				<td><input name="unitScore" id="unitScore" title="每项单位得分值是多少"></td>
			</tr>
			<tr>
				<td style="width: 300px;">单位</td>
				<td><input name="unit" id="unit" title="评审参数的单位"></td>
			</tr>
			<tr>
				<td>翻译成白话文内容</td>
				<td><textarea readonly="readonly" class="wh212-67" name="easyUnderstandContent" id="easyUnderstandContent"></textarea></td>
			</tr>
			<tr>
				<td>当前模型标准解释</td>
				<td><textarea class="wh212-67" name="standExplain" id="standExplain"  readonly="readonly">按项加减分.采购文件明确标准分值，加减分项，加减分值和最高最低分值限制，按照加减分项的项目名称，系统自动计算得分。如(正偏离，负偏离)</textarea></td>
			</tr>
		</tbody>
	</table>
	<table id="model22" style="display: none;width: 386px;">
		<tbody>
			<tr>
				<td style="width: 300px;">评审参数</td>
				<td><input name="reviewParam" id="reviewParam" title="例如，近五年获得省以上工商部门颁发知名品牌的数量，一个得1分"></td>
			</tr>
			<tr>
				<td>加减分类型</td>
				<td><select name="AddSubtractTypeName" id="AddSubtractTypeName"><option value="0">加分</option><option value="1" selected="selected">减分</option></select></td>
			</tr>
			<tr>
				<td style="width: 300px;">基准分值</td>
				<td><input name="reviewStandScore" id="reviewStandScore" value="0" title="该项的起始分值为多少，默认是0"></td>
			</tr>
			<tr>
				<td style="width: 300px;">最低分</td>
				<td><input name="minScore" id="minScore" title="该项的满分值是多少"></td>
			</tr>
			<tr>
				<td style="width: 300px;">每单位分值</td>
				<td><input name="unitScore" id="unitScore" title="每项单位得分值是多少"></td>
			</tr>
			<tr>
				<td style="width: 300px;">单位</td>
				<td><input name="unit" id="unit" title="评审参数的单位"></td>
			</tr>
			<tr>
				<td>翻译成白话文内容</td>
				<td><textarea readonly="readonly" class="wh212-67" name="easyUnderstandContent" id="easyUnderstandContent"></textarea></td>
			</tr>
			<tr>
				<td>当前模型标准解释</td>
				<td><textarea name="standExplain" id="standExplain"  readonly="readonly">按项加减分.采购文件明确标准分值，加减分项，加减分值和最高最低分值限制，按照加减分项的项目名称，系统自动计算得分。如(正偏离，负偏离)</textarea></td>
			</tr>
		</tbody>
	</table>
	<!-- 八大模型 -->
	 <%-- <div class="content_wrap">
		<div class="zTreeDemoBackground fl">
			<ul id="treeDemo" class="ztree"></ul>
		</div>
	</div>
		<div class=" right">
			<iframe id="treebody" name="treeframe"
				src="${pageContext.request.contextPath}/intelligentScore/gettreebody.do"
				frameborder="0" style="width: 100%;height: 100%;"> </iframe>
		</div>
 --%>

</body>  
</html>  