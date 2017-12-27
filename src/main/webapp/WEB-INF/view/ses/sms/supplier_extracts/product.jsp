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
</head>
<body>
	<!-- 修改订列表开始-->
	<div class="container margin-top-30">
		<form action="${pageContext.request.contextPath}/SupplierExtracts_new/listSupplier.do"
			method="post" id="form1">
			<div>
				<ul class="list-unstyled list-flow p0_20">
					<li class="col-md-6  p0 isSatisfy">
						<div class="fl mr10">
							<input type="radio"  name="radio"
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
<script type="text/javascript">
	var loadFlag = true;
    var key;
    var zTreeObj = "";
    var categoryId="";
    var temp = new Array();
    var isCheckParent = true; //搜索的时候结果选中子节点 不会选中父节点
    var categoryName = new Object();
    $(function() {
      categoryId = sessionStorage.getItem("categoryId");
      categoryName = sessionStorage.getItem("categoryName");
      //temp = categoryId.split(",");
      categoryName = categoryName.split(",");
      var isMulticondition = sessionStorage.getItem("isMulticondition");
      //$("[name='radio']").prop("checked",false);
      $(".isSatisfy [value='"+isMulticondition+"']").prop("checked",true);
      loadZtree(null);
		
    });
    
    function loadZtree(obj) {
     	var zNodes = "";
        var setting = {
          async: {
            autoParam: ["id"],
            enable: true,
            url: "${pageContext.request.contextPath}/SupplierExtracts_new/getTree.do?supplierTypeCode=${supplierTypeCode}",
            otherParam: {
              categoryId: categoryId,
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
            beforeCheck: checkBefore,
		 	onAsyncSuccess: showNodes,
		 	onCheck: checkNode
		  },
          view: {
            fontCss: getFontCss
          }
        };
        
        if(obj){
        	// 加载中的菊花图标
			var cateName = $("#cateName").val();
			var cateCode = $("#cateCode").val();
			//名称和编号有一个不为空才回去搜索
			if(cateName || cateCode){
				loading = layer.load(1);
				isCheckParent = !obj;
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
							showNodes(zTreeObj,"ztree",null,zNodes);
							/* zTreeObj.expandAll(true);//全部展开
							// 如果搜索到的最后一个节点是父节点，折叠最后一个节点
							var allNodes = zTreeObj.transformToArray(zTreeObj.getNodes());
							if(allNodes && allNodes.length > 0){
								// 最后一个节点
								var lastNode = allNodes[allNodes.length-1];
								if(lastNode.isParent){
									zTreeObj.expandNode(lastNode, false);//折叠最后一个节点
								}
							} */
						}
						// 关闭加载中的菊花图标
						layer.close(loading);
					}
				});
			}else{
				//名称和编号全都为空，重新加载品目树
				loadZtree(false);
			}
        }else{
       		isCheckParent = !obj;
	        zTreeObj = $.fn.zTree.init($("#ztree"), setting, zNodes);
        }
      }
      
      
    //展开节点
    function showNodes(zTreeObj, treeId, treeNode, nodes){
    	var zTreeObj = $.fn.zTree.getZTreeObj("ztree");
    	//搜索
    	if(!isCheckParent){
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
    	}else if(loadFlag){
    		var nodes = zTreeObj.getCheckedNodes(true);
    		for ( var k in nodes) {
				openParentNode(nodes[k],zTreeObj);
			}
			loadFlag = false;
    	}
    }  
      
     
    //递归父节点并将其展开
    function openParentNode(node,treeObj){
    	var pn = node.getParentNode();
    	if(pn){
   			treeObj.expandNode(pn, true);
    		openParentNode(pn,treeObj);
    	}
    }
    
    //选中前
    function checkBefore(treeId,treeNode){
    	var treeObj=$.fn.zTree.getZTreeObj(treeId);
    	if(treeNode.treeLevel>5 || treeNode.treeLevel<4){
    		treeObj.checkNode(treeNode, false,false);
    		layer.msg("当前选择的是"+treeNode.treeLevel+"级品目,仅能选择4,5级品目");
    		return false;
    		//showcheckedNode(treeObj);
    	}
    }
      
    //选中时回调
    function checkNode(event,treeId,treeNode){
    	var treeObj=$.fn.zTree.getZTreeObj(treeId);
    	//当前节点取消选中，递归取消父节点选中状态
		dischecked(treeNode,treeObj);
		if(treeNode.checked && isCheckParent){
			//子节点全部选中，父节点选中
			checkAllChildCheckParent(treeNode,treeObj);
		}
		
		//异步加载子节点不展开无法回显，对之前选中的进行处理
		/* if(!treeNode.checked){
			deleteParentId(treeNode);
			removeByValue(categoryName,treeNode.name);
		} */
    }
  
  	//删除父节点id  
    function deleteParentId(treeNode){
    	removeByValue(temp,treeNode.id);
    	var parentNode = treeNode.getParentNode();
    	if(parentNode){
    		deleteParentId(parentNode);
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
    	//需求变动，若是父节点为4级以上品目，不要选中
    		if(parentNode && parentNode.treeLevel>3){
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
    
  function ajaxDataFilter(treeId, parentNode, responseData){
  
	//选中父节点，勾选子节点
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
    var cateName = new Array();
    var cateId = new Array();
    
    if(!isCheckParent){
     //表示当前是搜索
     categoryId.length>0? Array.prototype.push.apply(cateId,categoryId.split(",")):null;
     categoryName.length>0? Array.prototype.push.apply(cateName,categoryName):null;
    }
    
    for(var i=0;i<nodes.length;i++){ 
	    //判断当前节点不存在存在于temp集合 就添加到cate集合中
	    if(!contains(temp,nodes[i].id)){
	    	cateId.push(nodes[i].id);
	    	cateName.push(nodes[i].name);
	    	//若是父节点查询当前的节点的所有子节点
	    	temp.push(nodes[i].id);
	    	if(nodes[i].isParent){
	    		//递归其全部子节点
	    		selectAllChildNode(nodes[i]);
	    	}
	    }
     } 
        //是否满足
       var issatisfy=$('input[name="radio"]:checked ').val();
       if(nodes.length<1 && (!categoryId)){
          layer.alert("产品类别必选");
          return false;
       }
       if("PROJECT"!=typeCode){
           $(cate).parents("li").find(".categoryId").val(cateId.toString());
       }else{
       		var ppid = modifParentOrChild(nodes);
       		$(cate).parents("li").find(".categoryId").val(ppid.substring(0,ppid.lastIndexOf(",")));
       }
           $(cate).parents("li").find(".isSatisfy").val(issatisfy);
           $(cate).val(cateName.toString());/* 将选中目录名称显示在输入框中 */
       var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
       parent.layer.close(index);
  } 
  
  //回显选中节点
  function showcheckedNode(treeObj){
  	for ( var i in categoryId) {
		treeObj.checkNode(categoryId[i], true, true);
	}
  }
  
  //判断数组中是否包含此元素
  function contains (arr,val) {
    for (i in arr) {
      if (arr[i] == val) 
      return true;
    }
    return false;
  }
  
  //递归子节点,存储进临时数组
  function selectAllChildNode(node){
  	var childNode = node.children;
  	if(childNode && childNode.length>0){
	  	for(var i=0;i<childNode.length;i++){
	  	if(childNode[i].checked){
		  	temp.push(childNode[i].id);
		  		if(childNode[i].isParent){
		  			selectAllChildNode(childNode[i]);
		  		}
		  	}
	  	}
  	}
  }
   //删除数组中元素
  function removeByValue(arr, val) {
  	for(var i=0; i<arr.length; i++) {
      if(arr[i] == val) {
     	arr.splice(i, 1);
     	break;
      }
  	}
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

</html>
