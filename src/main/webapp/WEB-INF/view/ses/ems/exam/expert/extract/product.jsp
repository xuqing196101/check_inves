<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
<script type="text/javascript">
    var key;
    var zTreeObj;
    $(function() {
      
      var zNodes;
      loadZtree();

      function loadZtree() {
        var setting = {
          async: {
            autoParam: ["id"],
            enable: true,
            url: "${pageContext.request.contextPath}/extractExpert/getTree.do?code=${type}&&ids=${ids}",
            otherParam: {
              categoryIds: "${categoryIds}",
            },
            dataFilter: ajaxDataFilter,
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
          },
			callback: {
				onCheck: onCheck
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

    
    function onCheck(e, treeId, treeNode) {
    	var index = parent.layer.load(2);
    	parent.layer.close(index);
	};
    
    
function ajaxDataFilter(treeId, parentNode, responseData){
	if(responseData[0].name!="物资" && responseData[0].name!="工程" && responseData[0].name!="服务"){
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

  function onloadChildrens(treeId, treeNode) {
    //alert("befor");
    return true;
  };

  //获取选中子节点id
  function getChildren(cate){
      var Obj=$.fn.zTree.getZTreeObj("ztree");  
       var nodes=Obj.getCheckedNodes(true);  
       var ids = new Array();
       var names=new Array();
  
       for(var i=0;i<nodes.length;i++){ 
           if(nodes[i].level != 0){
          //获取选中节点的值  
           ids+=nodes[i].id+","; 
           names+=nodes[i].name+",";
           }
       }
       //是否满足
       var issatisfy=$('input[name="radio"]:checked ').val();
         
       /* $(cate).val("");
       $(cate).parent().parent().parent().parent().parent().find("#categoryId").val("");
       $(cate).parent().parent().parent().parent().parent().find("#isSatisfy").val("");
       
       if(cate!=null && names != null && names != '' ){
           $(cate).val(names.substring(0,names.length-1));
           $(cate).parent().parent().parent().parent().parent().find("#categoryId").val(ids.substring(0,ids.length-1));
           $(cate).parent().parent().parent().parent().parent().find("#isSatisfy").val(issatisfy);
       } */
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
  
  function exptype(){
	   $("#ztree").css("display","none");
	   $("#liradio").css("display","none");
	   var x=document.getElementsByName("radio");  
	    for(var i=0;i<x.length;i++){ //对所有结果进行遍历，如果状态是被选中的，则将其选择取消  
	        if (x[i].checked==true)  
	        {  
	            x[i].checked=false;  
	        }  
	    }  
  }
  function exptype1(){
      $("#ztree").css("display","block");
      $("#liradio").css("display","block");
      var x=document.getElementsByName("radio");  //获取所有name=brand的元素  
              x[0].checked=true;  
   
 }

  //品目搜索
function searchCate() {
	  	var code = '${type}';
	  	var ids = '${ids}';
        var zTreeObj;
        var setting = {
          check: {
            enable: true,
            autoCheckTrigger: true,
            chkStyle: "checkbox",
            chkboxType: {
              "Y": "s",
              "N": "s"
            }, //勾选checkbox对于父子节点的关联关系  
          },
          data: {
            simpleData: {
              enable: true,
              autoCheckTrigger: true,
              idKey: "id",
              pIdKey: "parentId"
            }
          },
          callback: {
        	  onCheck: onCheck
          },
          view: {
        	  fontCss: getFontCss,
            showLine: true
          },
        };
        var index = layer.load(1, {
          shade: [0.1, '#fff'] //0.1透明度的白色背景
        });
        var cateName = $("#key").val();
        if(cateName == "") {
        	location.reload();
        } else {
          $.ajax({
            url: "${pageContext.request.contextPath}/extractExpert/searchCate.do",
            type:"post",
            data: {
              "code": code,
              "cateName": cateName,
              "ids": ids,
            },
            async: false,
            dataType: "json",
            success: function(data) {
              zTreeObj = $.fn.zTree.init($("#ztree"), setting, data);
              zTreeObj.expandAll(true); //全部展开
            }
          });
        }
        layer.close(index);
        // 过滤掉四级以下的节点
        setTimeout(function() {
          var treeObj = $.fn.zTree.getZTreeObj("ztree");
          var nodes = treeObj.getNodes();
          for (var i = 0; i < nodes.length; i++) {
            if (nodes[i].level > 3) {
              treeObj.removeNode(nodes[i]);
            }
          }
        }, 200);
      }
</script>
</head>
<body>
	<!-- 修改订列表开始-->
	<div class="container padding-top-20   ">
			<div>
				<ul class="list-unstyled list-flow p0_20">
					<input class="span2" name="code" value="${type}" type="hidden">
					<input class="span2" name="ids" value="${ids}" type="hidden">
					<li class="col-md-6  p0 " id="liradio">
						<div class="fl mr10">
							<input type="radio" name="radio" id="radio" 
								<c:if test="${isSatisfy == null || isSatisfy !='2'}">checked="checked" </c:if>
								
								value="1" class="fl" />
							<div class="ml5 fl">满足某一产品条件即可</div>
						</div>
						<div class="fl mr10">
							<input type="radio" name="radio" id="radio" value="2"
								<c:if test="${isSatisfy!=null && isSatisfy =='2'}">checked="checked"</c:if>
								 class="fl" />
							<div class="ml5 fl">同时满足多个产品条件</div>
						</div>
					</li>
				</ul>
				
			</div>
			<div align="center">
				<input type="text" id="key" class="empty" name="cateName">
				<input type="button" id="search" class="btn" value="搜索" onclick="searchCate()">
				<div class="clear"></div>
			</div>
			<div id="ztree" class="ztree margin-left-13" ></div>
	</div>
</body>
</html>
