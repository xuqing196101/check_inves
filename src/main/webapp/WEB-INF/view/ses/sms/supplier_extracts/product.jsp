<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en">
<!--<![endif]-->
<head>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<%@ include file="../../../common.jsp"%>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<link rel="stylesheet"
    href="${pageContext.request.contextPath}/public/supplier/css/supplieragents.css"
    type="text/css">

<!-- Meta -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<script type="text/javascript">
 /*    var datas;
    var treeObj; */
  /* $(function(){
	  var setting={
              async:{
                          autoParam:["id"],
                          enable:true,
                          url:"${pageContext.request.contextPath}/SupplierExtracts/getTree.do?projectId=${projectId}",
                          dataType:"json",
                          type:"post",
                      },
                      callback:{
                          onClick:zTreeOnClick,//点击节点触发的事件
                          onCheck:zTreeOnCheck
                          
                      }, 
                      data:{
                          simpleData:{
                              enable:true,
                              idKey:"id",
                              pIdKey:"pId",
                              rootPId:0,
                          }
                      },
                     check:{
                          chkboxType:{"Y" : "ps", "N" : "ps"},//勾选checkbox对于父子节点的关联关系  
                          chkStyle:"checkbox", 
                          enable: true
                     }
        };
	     treeObj=$.fn.zTree.init($("#ztree"),setting,datas);
	     
    }); */
    
    var key;
    $(function() {
      var zTreeObj;
      var zNodes;
      loadZtree();

      function loadZtree() {
        var setting = {
          async: {
            autoParam: ["id"],
            enable: true,
            url: "${pageContext.request.contextPath}/SupplierExtracts/getTree.do?supplierTypeCode=${supplierTypeCode}",
            otherParam: {
              categoryIds: "${categoryIds}",
            },
            dataType: "json",
            type: "post",
          },
          check: {
            enable: true,
            chkboxType: {
              "Y": "s",
              "N": "s"
            }
          },
          data: {
            simpleData: {
              enable: true,
              idKey: "id",
              pIdKey: "parentId"
            }
          },
          view: {
            fontCss: getFontCss
          }
        };
        zTreeObj = $.fn.zTree.init($("#ztree"), setting, zNodes);
        key = $("#key");
        key.bind("focus", focusKey)
          .bind("blur", blurKey)
          .bind("propertychange", searchNode)
          .bind("input", searchNode);
      }
    });

    function focusKey(e) {
      if(key.hasClass("empty")) {
        key.removeClass("empty");
      }
    }

    function blurKey(e) {
      if(key.get(0).value === "") {
        key.addClass("empty");
      }
    }
    var lastValue = "",
      nodeList = [],
      fontCss = {};

    function clickRadio(e) {
      lastValue = "";
      searchNode(e);
    }

    function searchNode(e) {
      var zTree = $.fn.zTree.getZTreeObj("ztree");
      var value = $.trim(key.get(0).value);
      var keyType = "name";
      if(key.hasClass("empty")) {
        value = "";
      }
      if(lastValue === value) return;
      lastValue = value;
      if(value === "") return;
      updateNodes(false);
      nodeList = zTree.getNodesByParamFuzzy(keyType, value);
      updateNodes(true);
    }

    function updateNodes(highlight) {
      var zTree = $.fn.zTree.getZTreeObj("ztree");
      for(var i = 0, l = nodeList.length; i < l; i++) {
        nodeList[i].highlight = highlight;
        zTree.updateNode(nodeList[i]);
      }
    }

    function getFontCss(treeId, treeNode) {
      return(!!treeNode.highlight) ? {
        color: "#A60000",
        "font-weight": "bold"
      } : {
        color: "#333",
        "font-weight": "normal"
      };
    }

    function filter(node) {
      return !node.isParent && node.isFirstNode;
    }
    
    
    
    var treeid=null;
  /*树点击事件*/
  function zTreeOnClick(event,treeId,treeNode){
      treeid=treeNode.id;
  }
  /*树点击事件*/
  var check;
  function zTreeOnCheck(event,treeId,treeNode){
      treeid=treeNode.id;
     
  }
  //获取选中子节点id
  function getChildren(cate){
      var Obj=$.fn.zTree.getZTreeObj("ztree");  
       var nodes=Obj.getCheckedNodes(true);  
       var ids = new Array();
       var names=new Array();
       for(var i=0;i<nodes.length;i++){ 
           if(!nodes[i].isParent){
          //获取选中节点的值  
           ids+=nodes[i].id+","; 
           names+=nodes[i].name+",";
           }
       } 
        //专家类型
        parent.$("#exttypeid").val();
        //是否满足
       var issatisfy=$('input[name="radio"]:checked ').val();
         
       if(cate!=null){
           $(cate).val(names.substring(0,names.length-1));/* 将选中目录名称显示在输入框中 */
           $(cate).parents("li").find(".isSatisfy").val(issatisfy);
           $(cate).parents("li").find(".categoryId").val(ids.substring(0,ids.length-1));
        /*    $(cate).parent().parent().parent().parent().parent().find("#extCategoryNames").val(names.substring(0,names.length-1));
           $(cate).parent().parent().parent().parent().parent().find("#extCategoryId").val(ids.substring(0,ids.length-1));
           $(cate).parent().parent().parent().parent().parent().find("#isSatisfy").val(issatisfy); */
           
       }
	             var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	             parent.layer.close(index);
  }     
  function resetQuery(){
	  alert("sds");
//       $("#form1").find(":input").not(":button,:submit,:reset,:hidden").val("10");
  }
  
</script>
</head>
<body>
	<!-- 修改订列表开始-->
	<div class="container margin-top-30">
		<form action="${pageContext.request.contextPath}/SupplierExtracts/listSupplier.do"
			method="post" id="form1">
			<div>
				<ul class="list-unstyled list-flow p0_20">
					<li class="col-md-6  p0 ">
						<div class="fl mr10">
							<input type="radio" name="radio" id="radio" checked="checked"
								value="1" class="fl" />
							<div class="ml5 fl">满足某一产品条件即可</div>
						</div>
						<div class="fl mr10">
							<input type="radio" name="radio" id="radio" value="2" class="fl" />
							<div class="ml5 fl">同时满足多个产品条件</div>
						</div>
					</li>
				</ul>
				<br />
			</div>
			 <div align="center"><input type="text" id="key" class="empty" > </div>
			<div id="ztree" class="ztree margin-left-13"></div>
		</form>
	</div>
</body>
</html>
