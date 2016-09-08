<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>My JSP 'category.jsp' starting page</title>

 <link rel="stylesheet" type="text/css" href="<%=basePath%>/public/ztree/css/zTreeStyle.css"> 
<%-- <link rel="stylesheet" type="text/css" href="<%=basePath%>/public/ztree/css/demo.css"> --%>

<script type="text/javascript" src="<%=basePath%>/public/ztree/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=basePath%>/public/ztree/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="<%=basePath%>/public/ztree/jquery.ztree.exedit.js"></script>

<script type="text/javascript">
  $(function(){
 	var datas;
	var treeid;
	 var setting={
		async:{
					autoParam:["id"],
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
			    }, 
				data:{
					keep:{
						parent:true,
					},					
					simpleData:{
						enable:true,
						idKey:"id",
						pIdKey:"pId",
						rootPId:0,
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
			   }
			    
  };
			    var treeObj=$.fn.zTree.init($("#ztree"),setting,datas);
			    treeObj.expandAll(false);
			    var id="${id}";
			    if (id!=''&&id!=null) {
					getList(id);
				}else{
					id="a";
					getList(id);
				
			    }
    
          
})
   /*添加*/
		function news(){
			if (treeid==null) {
			alert("请选择一个节点",{offset: ['222px', '390px']});
					return;		
			}else{
			window.location.href="<%=basePath%>category/add.do?id="+treeid;
			}
		}
		  function  zTreeOnClick(event,treeId,treeNode){
           		treeid=treeNode.id
            	getList(treeid);
            }
     
 		function update(){
	 		if (treeid==null) {
				alert("请选择一个节点");
			}else{
	 			window.location.href="<%=basePath%>category/update.do?id="+treeid;
	 		}
 		}
 		/*休眠-激活*/
	 	function ros(){
 			widow.location.href="<%=basePath%>";
 		}
 		
 		
 		function zTreeOnRemove(event, treeId, treeNode,isCancel) {
				
				
		}
 		function zTreeOnRename(event, treeId, treeNode, isCancel) {
				 alert(treeNode.tId + ", " + treeNode.name); 
				
		}

 	function zTreeBeforeRemove(treeId, treeNode){
 	  	"<%=basePath%>category/del.do?id="+treeNode.id
	}
		function zTreeBeforeRename(treeId, newName,treeNode,isCancel){
			
    	return true;
		} 
 		
	/*获取后台json列表展示*/
	function getList(treeid){
		$.ajax({
			type:"POST",
			dataType:"json",
			async:false,
			url:"${pageContext.request.contextPath}/category/findListByParent.do?id="+treeid,
			success:function(data){
			console.info(data);
				var list=data.cateList;
				console.info(list);
				var html="";
				var addhtml="";
				for ( var i = 0;  i< list.length; i++) {
			
				  html = html + "<tr>";
            	  html += "<th class='tc'><input type='checkbox' class='checkboxes' onclick='check()'  value='"+list[i].id+","+list[i].ancestry+"'/></th>"
            	  html = html + "<th >"+(i+1)+"</th>";
            	  html = html + "<th>"+list[i].name+"</th>";
            	　 /* html = html + "<td class='tc acur' onclick='checklist("+list[i].tr+")'>"+list[i].ancestry+"</td>";  */
            	  html = html + "<td >"+list[i].status+"</td>";
            	   html = html + "<td >"+list[i].ancestry+"</td>";  
            	  html = html + "<td  >"+list[i].orderNum+"</td>";
            	  html = html + "<td  >"+list[i].code+"</td>";
            	  html = html + "<td  >"+list[i].attchment+"</td>";
            	  html = html + "<td >"+list[i].description+"</td>"; 
            	  html = html + "</tr>";
            	 
				}
				idhtml="<input id='parent_id' type='hidden' value='"+data.id+"'/>";
				addhtml="<a href='javascription:void(0);' onclick='add("+data.id+")'新增</a>"
				 $("#Result tr:gt(0)").remove(); 
				$("#add a").remove;
				$("#add").append(addhtml)
				$("#parent_id").remove;
				$("#query_form").append(idhtml);
				$("#Result").append(html);
				
			}
		})
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

	<div id="ztree" class="ztree"></div>
		<div >
			<span id="add"><a href="javascript:void(0);" onclick="news()" class="btn btn-window ">新增 </a></span> 
			<span><a href="javascript:void(0);" onclick="update()"  class="btn btn-window ">修改</a></span> 
			<span><a href="javascript:void(0);" onclick="ros()"  class="btn btn-window ">激活/休眠</a></span>
		
			<span ><a href="javascript:void(0);" onclick="()"  class="btn btn-window ">导入</a></span>
			<span ><a href="javascript:void(0);" onclick="()"  class="btn btn-window ">导出</a></span>
			
			
		<form id="query_form">
		<input id="parent_id" type="hidden" value=""/>
	</form>
		<table id="Result" >    
            <thead >
			    <tr>
			      <th ><input id="checkedAll" type="checkbox" name="checkedAll" onclick="selectAll()"/></th>
				  <th class="tc">序号</th>
				  <th class="tc">目录名称</th> 
				  <th class="tc">父节点</th>
				  <th class="tc">状态</th>
				  <th class="tc">排序</th> 
				  <th class="tc">前台展示优先级</th> 
				  <th class="tc">图片</th> 
				  <th class="tc">描述 </th> 
				</tr>
	  		</thead>  
		<%-- <tr>
					<c:forEach  var="i" items="${request.list}" varStatus="vs">
					    <td><input onclick="check()" type="checkbox" name="chkItem" value=""/></td>
					    <td>${(l-1)*10+vs.index+1} </td>
						<td>${i.name}</td>
						<td>${i.ancestry}</td>
						<td>${i.status }</td>
						<td>${i.orderNum }</td>
						<td>${i.code}</td>
						<td>${i.attchment }</td>
						<td>${i.description }</td>
					</c:forEach>
				</tr>  --%>
  </table>
</div>
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
