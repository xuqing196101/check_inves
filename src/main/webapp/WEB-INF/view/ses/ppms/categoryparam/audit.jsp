<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'audit.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->


  

<script type="text/javascript" src="<%=basePath%>/public/ztree/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=basePath%>/public/ztree/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="<%=basePath%>/public/ztree/jquery.ztree.exedit.js"></script>

<script type="text/javascript">
	var datas;
	var treeid=null;
 $(document).ready(function(){
	 var setting={
		async:{
					autoParam:["id","name"],
					enable:true,
					url:"<%=basePath%>category/createtree.do",
					dataType:"json",
					type:"post",
				},
				callback:{
			    	onClick:zTreeOnClick,//点击节点触发的事件
			    	beforeRemove: zTreeBeforeRemove,
			    	beforeRename: zTreeBeforeRename, 
					onRemove: zTreeOnRemove,
       			    onRename: zTreeOnRename,
       			  /*    onNodeCreated: zTreeOnNodeCreated, */
       			   
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
			    edit:{
			    	enable:true,
					editNameSelectAll:true,
					showRemoveBtn: true,
					showRenameBtn: true,
					removeTitle: "删除",
					renameTitle:"重命名",
				},
			   check:{
					enable: true
			   },
			
  };
	 
    $.fn.zTree.init($("#ztree"),setting,datas);
    var lastValue ="", nodeList=[];
    var value ="";
    key.bind("propertychange",searchNode).bind("input",searchNode);
}) 
    var ztree = $.fn.zTree.getZTreeObj("ztree");
    var key =$("#key");
	
    function searchNode(e){
	     alert(4);
    	value = $.trim(key.get(0).value);
    	var keyType ="name";
    	alert(3);
    	if(lastValue== value){
    		alert(1);
    		return;
    	}
    	lastValue= value;
    	updateNodes(false);
    	if(value==""){
    		alert(2);
    		return;
    	}
    	nodeList = ztree.getNodesByParamFuzzy(keyType,value);
    	console.info(nodeList);
    	updateNodes(true);
    	//遍历隐藏其他父节点
    }	
    function updateNodes(highlight){
    	var pnodes= ztree.getNodes();
    	for(var j=0;j<pnodes.length;j++){
    		ztree.showNode(pnodes[j]);
    		if(highlight){
				var have = false;
				for(var i=0, l=nodeList.length; i<l; i++) {
					if(pnodes[j].isParent && nodeList[i].pId == pnodes[j].id){
						have = true;
					}
				}
				if(have){
					for(var q = 0;q<pnodes[j].children.length;q++){
						for(var k = 0;k<nodeList.length;k++){
							if(pnodes[j].children[q].name==nodeList[k].name){
								ztree.showNode(pnodes[j].children[q]);
								break;
							}else if(k == nodeList.length-1){
								if(value.length == 1){
									ztree.showNode(pnodes[j].children[q]);
								}else{ 
									ztree.hideNode(pnodes[j].children[q]);
								}
								
							}
						}  
					}
				}
				if(!have){
					ztree.hideNode(pnodes[j]);
				}
			}else{
				ztree.showNode(pnodes[j]);
			}
       	}
		for( var i=0, l=nodeList.length; i<l; i++) {
			nodeList[i].highlight = highlight;
			ztree.updateNode(nodeList[i]);
		}
	}
    	
    function filter(node) {
		return !node.isParent && node.isFirstNode;
	}
    
	
   /**删除图片*/
   function deletepic(treeid,obj){
		$(obj).prev().remove();
		$(obj).next().remove();
		$(obj).remove();
	}

    /**点击事件*/
    function zTreeOnClick(event,treeId,treeNode){
		treeid=treeNode.id
    }
  
	/**修改节点信息*/
    function update(){
	 		if (treeid==null) {
				alert("请选择一个节点");
			}else{
				
			}
 		}
  
    
 
 		
 	/**重命名和删除的回调函数*/	
    function zTreeOnRemove(event, treeId, treeNode,isCancel) {
		}
    function zTreeOnRename(event, treeId, treeNode, isCancel) {
				 alert(treeNode.tId + ", " + treeNode.name); 
				
		}
	/**删除目录信息*/
    function zTreeBeforeRemove(treeId, treeNode){
	 		$.ajax({
	 			type:"post",
	 			url:"<%=basePath%>category/del.do?id="+treeNode.id,
	 		});
		}
	 	
	/**节点重命名*/
    function zTreeBeforeRename(treeId,treeNode,newName,isCancel){
			$.ajax({
	 			type:"post",
	 			url:"<%=basePath%>category/rename.do?id="+treeNode.id+"&name="+newName,
	 		});
		} 
  
    
</script>
</head>

<body>

<div class="wrapper">

  <div class="header-v4 header-v5">
    <!-- Navbar -->
    <div class="navbar navbar-default mega-menu" role="navigation">
      <div class="container">
        <!-- logo和搜索 -->
        <div class="navbar-header">
          <div class="row container">
            <div class="col-md-4 padding-bottom-30">
              <a href="">
                 <img alt="Logo" src="<%=basePath%>public/ZHH/images/logo_2.png" id="logo-header">
              </a>
            </div>
			<!--搜索开始-->
            <div class="col-md-8 topbar-v1 col-md-12 padding-0">
              <ul class="top-v1-data padding-0">
			    <li>
				<a href="#">
				  <div><img src="<%=basePath%>public/ZHH/images/top_01.png"/></div>
				  <span>决策支持</span>
				 </a>
				</li>
			    <li>
				<a href="#">
				  <div><img src="<%=basePath%>public/ZHH/images/top_02.png"/></div>
				  <span>业务监管</span>
				 </a>
				</li>
			    <li>
				<a href="#">
				  <div><img src="<%=basePath%>public/ZHH/images/top_03.png"/></div>
				  <span>障碍作业</span>
				 </a>
				</li>	
			    <li>
				<a href="#">
				  <div><img src="<%=basePath%>public/ZHH/images/top_04.png"/></div>
				  <span>信息服务</span>
				 </a>
				</li>
			    <li>
				<a href="#">
				  <div><img src="<%=basePath%>public/ZHH/images/top_05.png"/></div>
				  <span>支撑环境</span>
				 </a>
				</li>
			    <li>
				<a href="#">
				  <div><img src="<%=basePath%>public/ZHH/images/top_06.png"/></div>
				  <span>配置配置</span>
				 </a>
				</li>
			    <li>
				<a href="#">
				  <div><img src="<%=basePath%>public/ZHH/images/top_07.png"/></div>
				  <span>后台首页</span>
				 </a>
				</li>
			    <li>
				<a href="#">
				  <div><img src="<%=basePath%>public/ZHH/images/top_08.png"/></div>
				  <span>安全退出</span>
				 </a>
				</li>
				
			  </ul>
			</div>
          </div>
	     </div>
	    </div>
       </div>
    </div>
	<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a><><li><a href="#">采购目录管理</a><><li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <div class="container">
   <div class="col-md-3">

	 <div><ul id="ztree" class="ztree"></ul></div>
	</div>
		<form action="<%=basePath%>categoryparam/audit.do" method="post">
		<table>
		    <tr><td>参数名称：</td><td><input name="name" value="${cate.name}"/></td></tr>
		    <tr><td>公布范围：</td>
			<td>
			    <span><input type="checkbox" value="0" name="range"/>外网</span>
			    <span><input type="checkbox" value="1" name="range"/>内网</span>
			</td></tr> 
		</table>
		</form>
	</div>
	
	<!--底部代码开始-->
    <div class="footer-v2 clear" id="footer-v2">
      <div class="footer">
            <!-- Address -->
              <address class="">
			  Copyright © 2016 版权所有：中央军委后勤保障部 京ICP备09055519号
              </address>
              <div class="">
		       浏览本网主页，建议将电脑显示屏的分辨率调为1024*768
              </div> 
            <!-- End Address -->
<!--/footer--> 
    </div>
    </div>
  </body>
</html>
