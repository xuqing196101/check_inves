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
    var key;
    var zTreeObj;
    var zNodes;
    var categoryId;
    $(function() {
      
      categoryId = sessionStorage.getItem("categoryId");
      loadZtree(null);
		
    });
    
    function loadZtree(obj) {
        var setting = {
          async: {
            autoParam: ["id"],
            enable: true,
            url: "${pageContext.request.contextPath}/SupplierExtracts_new/getTree.do?supplierTypeCode=${supplierTypeCode}",
            otherParam: {
              categoryId: categoryId,
              //"${categoryId}",
            },
            dataType: "json",
            dataFilter: ajaxDataFilter,
            type: "post",
          },
          check: {
            enable: true,
            chkStyle: "checkbox",
            chkboxType: { "Y": "s", "N": "ps" }
            /* autoCheckTrigger: true */
          },
          data: {
            simpleData: {
              enable: true,
              idKey: "id",
              pIdKey: "parentId"
            }
          },
          callback: {
		 	onCheck: checkNode
		  },
          view: {
            fontCss: getFontCss
          }
        };
        
        if(obj){
        	// 加载中的菊花图标
			loading = layer.load(1);
			var cateName = $("#cateName").val();
			var cateCode = $("#cateCode").val();
			$.ajax({
				url: "${pageContext.request.contextPath}/SupplierCondition_new/searchCate.do",
				type:"post",
				data: {"typeId" : "${supplierTypeCode}", "cateName" : cateName,"cateCode":cateCode},
				async: false,
				dataType: "json",
				success: function(data){
					if (!data || data.length<1) {
						layer.msg("没有符合查询条件的产品类别信息！");
					} else {
						zNodes = data;
						zTreeObj = $.fn.zTree.init($("#ztree"), setting, zNodes);
						zTreeObj.expandAll(true);//全部展开
						// 如果搜索到的最后一个节点是父节点，折叠最后一个节点
						var allNodes = zTreeObj.transformToArray(zTreeObj.getNodes());
						if(allNodes && allNodes.length > 0){
							// 最后一个节点
							var lastNode = allNodes[allNodes.length-1];
							if(lastNode.isParent){
								zTreeObj.expandNode(lastNode, false);//折叠最后一个节点
							}
						}
					}
					// 关闭加载中的菊花图标
					layer.close(loading);
				}
			});
        }else{
	        zTreeObj = $.fn.zTree.init($("#ztree"), setting, zNodes);
        }
        
        key = $("#key");
        key.bind("focus", focusKey)
          .bind("blur", blurKey)
          .bind("propertychange", searchNode)
          .bind("input", searchNode);
      }
    
    //选中时间回调
    function checkNode(event,treeId,treeNode){
    	var treeObj=$.fn.zTree.getZTreeObj("ztree");
		dischecked(treeNode,treeObj);
		
		if(treeNode.checked){
			checkAllChildCheckParent(treeNode,treeObj);
		}
    }
    
    //递归取消父节点选中状态
	function dischecked(treeNode,treeObj){
		var node = treeNode.getParentNode();
		if(null !=node){
			treeObj.checkNode(node, false);
			dischecked(node,treeObj);
		}
	}
    
    /**
	 * 子节点全部选中，选中父节点
	 * @param node
	 * @returns
	 */
    //递归父节点
    function checkAllChildCheckParent(node,treeObj){
    	var flag = preIsCheck(node) && nextIsCheck(node);
    	var parentNode = node.getParentNode();
    	if(flag){
    		if(parentNode){
    			treeObj.checkNode(parentNode, true,false,true);
    			checkAllChildCheckParent(parentNode,treeObj);
    		}
    	}
    }
    
    
   //工程品目处理父子节点
      function modifParentOrChild(nodes){
	    var ppid = "";
	    var curId = "";
	    var ppName = "";
         	//zTree = $.fn.zTree.getZTreeObj("ztree");
		for ( var i in nodes) {
			
			//递归根节点，
			var rootNode = getRootNode(nodes[i]);
			if(rootNode.name == "工程勘察" || rootNode.code == "工程设计"  ){
				
				//判断是工程勘察或设计
				var flag = preIsCheck(nodes[i]) && nextIsCheck(nodes[i]);
				//flag = preIsCheck(nodes[i]) && nextIsCheck(nodes[i]);
				if(flag){
					if(ppid.search(nodes[i].getParentNode().id) == -1){
						ppid += nodes[i].getParentNode().id + ",";
						ppName += nodes[i].getParentNode().name + ",";
					}
				}else{
					ppid += nodes[i].id + ",";
					ppName += nodes[i].name + ",";
				}
			}else{
     			ppid+=nodes[i].id+","; 
			}
		}
		return ppid;
		
      }
    //递归根节点
    function getRootNode(node){
    	var rootNode = node;
    	if(node.getParentNode()){
    	 rootNode = getRootNode(node.getParentNode());
    	}
    	return rootNode;
    }
    
    
   //判断前一个节点是否选中
    function preIsCheck(treeNode){
  	 	var pre = treeNode.getPreNode();
  	 	var flag = treeNode.checked;
    	if(pre){
    		flag &=  preIsCheck(pre) ;
    	}
    	return flag;
    }
    
    //判断后一个节点是否选中
 	function nextIsCheck(treeNode){
 		var next = treeNode.getNextNode();
 		var flag = treeNode.checked;
 		if(next){
 			flag &=  nextIsCheck(next) ;
    	}
    	return	flag;
 	}   
    
    
    
//选中父节点，勾选子节点
  function ajaxDataFilter(treeId, parentNode, responseData){
	if(typeof(parentNode)!="undefined"){
		if(parentNode.checked==true){
        	 for(var i=0;i<responseData.length;i++){
        		 responseData[i].checked=true;
        	 }
        }
	}
		return responseData;
	}
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

	//搜索
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

  
  //获取选中子节点id
  function getChildren(cate){
  	var typeCode = $(cate).attr("typeCode");
    var Obj=$.fn.zTree.getZTreeObj("ztree");  
    var nodes=Obj.getCheckedNodes(true);  
    var ids  = "";
    var names = "";
    var parentId = "";
    var parentName="";
    for(var i=0;i<nodes.length;i++){ 
         if(!nodes[i].isParent){
          //获取选中节点的值  
         	ids+=nodes[i].id+","; 
            names+=nodes[i].name+",";
         }else{
       		parentId += nodes[i].id+",";
       		parentName+=nodes[i].name+",";
         }
     } 
        //是否满足
       var issatisfy=$('input[name="radio"]:checked ').val();
       var cname = parentName+names;
       if("PROJECT"!=typeCode){
           $(cate).parents("li").find(".categoryId").val(ids.substring(0,ids.lastIndexOf(",")));
           $(cate).parents("li").find(".parentId").val(parentId.substring(0,parentId.lastIndexOf(",")));
       }else{
       		var ppid = modifParentOrChild(nodes);
       		$(cate).parents("li").find(".categoryId").val(ppid.substring(0,ppid.lastIndexOf(",")));
       }
           $(cate).parents("li").find(".isSatisfy").val(issatisfy);
           $(cate).val(cname.substring(0,cname.lastIndexOf(",")));/* 将选中目录名称显示在输入框中 */
           
           
       var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
       parent.layer.close(index);
  }     
  function resetQuery(){
	  alert("sds");
//       $("#form1").find(":input").not(":button,:submit,:reset,:hidden").val("10");
  }
  
  function showTitle(){
  	layer.alert("您选择的是与关系");
  }
  
  function resetSerch(){
  	$("#cateName").val("");
  	$("#cateCode").val("");
  }
  
  
  
</script>
</head>
<body>
	<!-- 修改订列表开始-->
	<div class="container margin-top-30">
		<form action="${pageContext.request.contextPath}/SupplierExtracts_new/listSupplier.do"
			method="post" id="form1">
			<div>
				<ul class="list-unstyled list-flow p0_20">
					<li class="col-md-6  p0 ">
						<div class="fl mr10">
							<input type="radio" name="radio" checked="checked"
								value="1" class="fl" />
							<div class="ml5 fl">满足某一产品条件即可</div>
						</div>
						<div class="fl mr10">
							<input type="radio" name="radio"  value="2" class="fl"/>
							<div class="ml5 fl">同时满足多个产品条件</div>
						</div>
					</li>
				</ul>
				<br />
			</div>
			<div>
				品目名称：<input type="text" id="cateName" class="mr3 empty w125">
     		         品目编码：<input type="text" id="cateCode" class="mr3 empty w125"><br/>
				<div class="tc">
				  <input type="button" class="btn" onclick="loadZtree('true')" value="搜索">
				  <input type="button" class="btn" onclick="resetSerch()" value="重置">
				</div>
        <div class="clear"></div>
			</div>
			<div id="ztree" class="ztree margin-left-13"></div>
		</form>
	</div>
</body>
</html>
