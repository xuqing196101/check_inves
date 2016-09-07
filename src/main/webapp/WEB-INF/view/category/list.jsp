<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>My JSP 'category.jsp' starting page</title>

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/public/ztree/css/zTreeStyle.css">
<script type="text/javascript"
	src="<%=request.getContextPath()%>/public/ztree/jquery.ztree.core.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/public/ztree/jquery.ztree.excheck.js"></script>


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
				data:{
					keep:{
						parent:true,
					},
				key:{
					children:"nodes",
				},
					
					simpleData:{
						enable:true,
						idKey:"id",
						pIdKey:"pId",
						rootPId:0,
					}
			    },
			   check:{
					enable: true
			   },
		
			    callback:{
			    	onClick:zTreeOnClick
			    },
     };
     		/* function setParent(){
     			var treeobj=$.fn.zTree.getZTreeObj("ztree");
     			var Nodes=treeobj.getNodes();
     			Nodes[a].isParent=true;
     		} */
  
  
			    var treeObj=$.fn.zTree.init($("#ztree"),setting,datas);
			    treeObj.expandAll(false);
			    var id="${id}";
			    if (id!=''&&id!=null) {
					getList(id);
				}else{
					id="a";
					getList(id);
				
			    }
            });
      /*   function zTreeOnClick(event,treeId,treeNode){
            	$("#checkedAll").attr("checked",false);
            	getList(treeid);
            } */
            
            function  zTreeOnClick(event,treeId,treeNode){
            	/* alert(treeNode.id + ", " + treeNode.name); */
            /* 	treeid=treeNode.id */
            	
            /* 	$("#checkedAll").attr("checked",false); */
            	getList(id);
            }
            
          /*添加*/
		function add(){
		if (id==null) {
	alert("请选择一个节点",{offset: ['222px', '390px']});
				return;		
		}else{
		window.location.href="<%=basePath%>category/add.do?id="+id;
		}
		}
 		/*修改*/
 		function update(){
 		if (id==null) {
			alert("请选择一个节点");
		}else{
 			window.location.href="<%=basePath%>category/update.do?id="+id;
 		}
 		}
	 	function ros(){
 		widow.location.hre="<%=basePath%>";
 		}
 		
/* 	/** 全选全不选 */
	/* function selectAll(){
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
	/* function check(){
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
 	} */
	function getList(id){
		$.ajax({
			type:"POST",
			dataType:"json",
			async:false,
			url:"${pageContext.request.contextPath}/category/findListByParent.do?id="+id,
			success:function(data){
			console.info(data);
				var list=data.cateList;
				console.info(list);
				var html="";
				var addhtml="";
				for ( var i = 0;  i< list.length; i++) {
			
				  html = html + "<tr>";
            	 /*  html += "<th class='tc'><input type='checkbox' class='checkboxes' onclick='check()'  value='"+list[i].id+","+list[i].ancestry+"'></th>" */
            	  html = html + "<th >"+(i+1)+"</th>";
            	  html = html + "<th>"+list[i].name+"</th>";
            	/*   html = html + "<td class='tc acur' onclick='checklist("+list[i].treeid+")'>"+list[i].ancestry+"</td>"; */
            	 /*  html = html + "<td  onclick='checklist("+list[i].id+")'>"+list[i].status+"</td>";
            	  html = html + "<td  onclick='checklist("+list[i].id+")'>"+list[i].orderNum+"</td>";
            	  html = html + "<td  onclick='checklist("+list[i].id+")'>"+list[i].code+"</td>";
            	  html = html + "<td  onclick='checklist("+list[i].id+")'>"+list[i].attchment+"</td>";
            	  html = html + "<td  onclick='checklist("+list[i].id+")'>"+list[i].description+"</td>"; */
            	  html = html + "</tr>";
            	 
				}
				idhtml="<input id='parent_id' type='hidden' value='"+data.id+"'/>";
				addhtml="<a href='javascription:void(0);' onclick='add("+data.id+")'新增</a>"
				 $("#Result tr:gt(0)").remove(); 
				$("#add a").remove;
				$("#add").append(addhtml)
				$("#parent_id").remove;
				$("#query_form").append(idhtml);
				$("Result").append(html);
				
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

	<div>采购目录管理</div>
	<div id="ztree" class="ztree"></div>
		<div >
			<span id="add" class="result"><a href="javascript:void(0);" onclick="add(${id})" class="btn btn-window ">新增 </a></span> 
			<span class="result"><a href="javascript:void(0);" onclick="update()"  class="btn btn-window ">修改</a></span> 
			<span class="result"><a href="javascript:void(0);" onclick="ros()"  class="btn btn-window ">激活/休眠</a></span>
		<form id="query_form">
		<input id="parent_id" type="hidden" value=""/>
	</form>
		<table id="Result" >    
            <thead >
			    <tr>
			      <th ><input id="checkedAll" type="checkbox" name="checkedAll" onclick="selectAll()"/></th>
				  <th >序号</th>
				  <th >目录名称</th> 
				  <th >父节点</th>
				  <th >状态</th>
				  <th >排序</th> 
				  <th >前台展示优先级</th> 
				  <th >图片</th> 
				  <th >描述 </th> 
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
