<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../common.jsp"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>信息发布</title>
    
    <script type="text/javascript" src="<%=request.getContextPath()%>/public/layer/layer.js"></script>
    
<script type="text/javascript">
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
    
	function view(id){
		window.location.href="<%=basePath%>article/view.html?id="+id;
	}
	
	function add(){
		window.location.href="<%=basePath%>article/add.html";
	}
	
	function find(){
    	var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(id.length==1){
			
			window.location.href="<%=basePath%>article/view.html?id="+id;
		}else if(id.length>1){
			layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
		}else{
			layer.alert("请选择需要查看的信息",{offset: ['222px', '390px'], shade:0.01});
		}
    }
	
	function edit(){
    	var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(id.length==1){
			window.location.href="<%=basePath%>article/edit.html?id="+id;
		}else if(id.length>1){
			layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
		}else{
			layer.alert("请选择需要修改的信息",{offset: ['222px', '390px'], shade:0.01});
		}
    }
	
    function del(){
    	var ids =[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			ids.push($(this).val()); 
		}); 
		if(ids.length>0){
			layer.confirm('您确定要删除吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
				layer.close(index);
				window.location.href="<%=basePath%>article/delete.html?ids="+ids;
			});
		}else{
			layer.alert("请选择要删除的信息",{offset: ['222px', '390px'], shade:0.01});
		}
    }
    
    function sub(){
    	window.location.href="<%=basePath%>article/sublist.html?status=0";
    }
    
    function audit(){
    	window.location.href="<%=basePath%>article/auditlist.html?status=1";
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
		   <li><a href="#"> 首页</a></li><li><a href="#">信息管理</a></li></ul>
		<div class="clear"></div>
	  </div>
   </div>
  
	<div class="container margin-top-5 fr">
	   <input type="hidden" id="depid" name="depid">
	  	<div class="content padding-left-25 padding-right-25 padding-top-5">
	  		<h4>信息列表</h4>
	  		<button class="btn btn-windows git" type="button" onclick="sub()">提交信息列表 </button>
			<button class="btn btn-windows git" type="button" onclick="audit()">审核信息列表</button>
			</br>
	  		<button class="btn btn-windows add" type="button" onclick="add()">新增信息</button>
			<button class="btn btn-windows edit" type="button" onclick="edit()">修改信息</button>
			<button class="btn btn-windows delete" type="button" onclick="del()">删除信息</button>
			<button class="btn btn-windows delete" type="button" onclick="find()">查看信息</button>
		  <table class="table table-bordered table-condensed">
		  	<thead>
	  			<tr>
	  				<th class="info"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
	  				<th class="info">序号</th>
	  				<th class="info">信息标题</th>
	  				<th class="info">发布范围</th>
	  				<th class="info">录入时间</th>
	  				<th class="info">信息类型</th>
	  				<th class="info">是否发布</th>
	  				<th class="info">下载量</th>
	  			</tr>
	  		</thead>
	  		<c:forEach items="${list}" var="list" varStatus="vs">
		  		<tr>
		  			<td class="tc"><input onclick="check()" type="checkbox" name="chkItem" value="${list.id }" /></td>
		  			<td class="tc" onclick="view('${list.id }')">${vs.index + 1 }</td>
		  			<td class="tc" onclick="view('${list.id }')">${list.name }</td>
		  			<td class="tc" onclick="view('${list.id }')">
		  				<c:if test="${list.range=='0' }">
		  					内网
		  				</c:if>
		  				<c:if test="${list.range=='1' }">
		  					外网
		  				</c:if>
		  				<c:if test="${list.range=='2' }">
		  					内网/外网
		  				</c:if>
		  			</td>
		  			<td class="tc" onclick="view('${list.id }')">
		  				<fmt:formatDate value='${list.createdAt }' pattern="yyyy年MM月dd日   HH:mm:ss" />
		  			</td>
		  			<td class="tc" onclick="view('${list.id }')">${list.articleType.name }</td>
		  			<td class="tc">
		  				<c:if test="${list.status=='0' }">
		  					<input type="hidden" name="status" value="${list.status }">暂存
		  				</c:if>
		  				<c:if test="${list.status=='1' }">
		  					<input type="hidden" name="status" value="${list.status }">已提交
		  				</c:if>
		  				<c:if test="${list.status=='2' }">
		  					<input type="hidden" name="status" value="${list.status }">发布
		  				</c:if>
		  				<c:if test="${list.status=='3' }">
		  					<input type="hidden" name="status" value="${list.status }">审核未通过
		  				</c:if>
		  			</td>
		  			<td class="tc"><a href="">${list.status }下载量</a></td>
		  		</tr>
	  		</c:forEach>
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
    </div>
  
  </body>
</html>
