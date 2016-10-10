<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]-->
  <head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title></title>

	<!-- Meta -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
	<link href="<%=basePath%>public/ZHH/css/common.css" media="screen" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>public/ZHH/css/bootstrap.min.css" media="screen" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>public/ZHH/css/style.css" media="screen" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>public/ZHH/css/line-icons.css" media="screen" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>public/ZHH/css/app.css" media="screen" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>public/ZHH/css/application.css" media="screen" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>public/ZHH/css/header-v4.css" media="screen" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>public/ZHH/css/header-v5.css" media="screen" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>public/ZHH/css/brand-buttons.css" media="screen" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>public/ZHH/css/footer-v2.css" media="screen" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>public/ZHH/css/img-hover.css" media="screen" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>public/ZHH/css/page_job.css" media="screen" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>public/ZHH/css/shop.style.css" media="screen" rel="stylesheet" type="text/css">
	
	<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=path %>/public/ZHH/js/ajaxfileupload.js"></script>
	<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery_ujs.js"></script>
	<script type="text/javascript" src="<%=basePath%>public/lodop/LodopFuncs.js"></script>
	<script type="text/javascript" src="<%=basePath%>public/ZHH/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>public/My97DatePicker/WdatePicker.js"></script>
  	<script src="<%=basePath%>public/layer/layer.js"></script>
  	<script src="<%=basePath%>public/laypage-v1.3/laypage/laypage.js"></script>
  </head>
  <script type="text/javascript">
	  $(function(){
		  laypage({
			    cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
			    pages: "${list.pages}", //总页数
			    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
			    skip: true, //是否开启跳页
			    total: "${list.total}",
			    startRow: "${list.startRow}",
			    endRow: "${list.endRow}",
			    groups: "${list.pages}">=5?5:"${list.pages}", //连续显示分页数
			    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
			        var page = location.search.match(/page=(\d+)/);
			        return page ? page[1] : 1;
			    }(), 
			    jump: function(e, first){ //触发分页后的回调
			        if(!first){ //一定要加此判断，否则初始时会无限刷新
			            location.href = '<%=basePath%>user/list.html?page='+e.curr;
			        }
			    }
			});
	  });
   
  
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
  		window.location.href="<%=basePath%>user/show.html?id="+id;
  	}
  	
    function edit(){
    	var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(id.length==1){
			var currPage = ${list.pageNum};
			window.location.href="<%=basePath%>user/edit.html?id="+id+"&page="+currPage;
		}else if(id.length>1){
			layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
		}else{
			layer.alert("请选择需要修改的用户",{offset: ['222px', '390px'], shade:0.01});
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
				window.location.href="<%=basePath%>user/delete_soft.html?ids="+ids;
			});
		}else{
			layer.alert("请选择要删除的用户",{offset: ['222px', '390px'], shade:0.01});
		}
    }
    
    function add(){
    	window.location.href="<%=basePath%>user/add.html";
    }
    
    function openPreMenu(){
		var ids =[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			ids.push($(this).val()); 
		}); 
		if(ids.length==1){
			var iframeWin;
			layer.open({
			  type: 2, //page层
			  area: ['300px', '500px'],
			  title: '配置权限',
			  closeBtn: 1,
			  shade:0.01, //遮罩透明度
			  moveType: 1, //拖拽风格，0是默认，1是传统拖动
			  shift: 1, //0-6的动画形式，-1不开启
			  offset: ['180px', '550px'],
			  shadeClose: false,
			  content: '<%=basePath%>user/openPreMenu.html?id='+ids,
			  success: function(layero, index){
			    iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
			  },
			  btn: ['保存', '关闭'] 
			  ,yes: function(){
			    iframeWin.onCheck(ids);
			  }
			  ,btn2: function(){
			    layer.closeAll();
			  }
			});
		}else if(ids.length>1){
			layer.alert("只能同时选择一个用户",{offset: ['222px', '390px'], shade:0.01});
		}else{
			layer.alert("请选择一个用户",{offset: ['222px', '390px'], shade:0.01});
		}
	
	}
	
	function query(){
		$("#form1").submit();
	}
  </script>
  <body>
   <!--面包屑导航开始-->
	   <div class="margin-top-10 breadcrumbs ">
	      <div class="container">
			   <ul class="breadcrumb margin-left-0">
			   <li><a href="#"> 首页</a></li><li><a href="#">支撑系统</a></li><li><a href="#">后台管理</a></li><li class="active"><a href="#">用户管理</a></li>
			   </ul>
			<div class="clear"></div>
		  </div>
	   </div>
      <div class="container">
		  <div class="headline-v2">
			  <h2>用户管理</h2>
		  </div>
		  <div class="p10_25">
		     <h2 class="padding-10 border1">
		       	<form action="<%=basePath %>user/list.html" id="form1" method="post" class="mb0">
			    	<ul class="demand_list">
			    	  <li class="fl">
				    	<label class="fl">用户名：</label><span><input type="text" id="topic" name="loginName" class=""/></span>
				      </li>
			    	  <li class="fl">
				    	<label class="fl">姓名：</label><span><input type="text" id="topic" name="relName" class=""/></span>
				      </li>
			    	  <li class="fl">
				    	<label class="fl">用户类型：</label>
				    	<div class="select_common mb10">
					        <select class="w180 " name="typeName">
					        	<option value="">请选择</option>
					        	<option value="2">需求人员</option>
					        	<option value="1">采购人员</option>
					        	<option value="0">采购管理人员</option>
					        	<option value="3">其他人员</option>
					        	<option value="4">供应商</option>
					        	<option value="5">专家</option>
					        	<option value="6">进口供应商</option>
					        	<option value="7">进口代理商</option>
					        </select>
				        </div>
				      </li> 
				    	<button type="button" onclick="query()" class="btn">查询</button>
				    	<button type="reset" class="btn">重置</button>  	
			    	</ul>
		    	  	<div class="clear"></div>
		        </form>
		     </h2>
	   	  </div>
      </div>
   	  <!-- 表格开始-->
	  <div class="container">
		  <div class="col-md-8">
			    <button class="btn btn-windows add" type="button" onclick="add()">新增</button>
				<button class="btn btn-windows edit" type="button" onclick="edit()">修改</button>
				<button class="btn btn-windows delete" type="button" onclick="del();">删除</button>
				<button class="btn btn-windows edit" type="button" onclick="openPreMenu()">设置权限</button>
			</div>
	  </div>
   
	  <div class="container margin-top-5">
		  <div class="content padding-left-25 padding-right-25 padding-top-5">
		       <table class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
					  <th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
					  <th class="info w50">序号</th>
					  <th class="info">用户名</th>
					  <th class="info">姓名</th>
					  <th class="info">单位</th>
					  <th class="info">联系电话</th>
					  <th class="info">类型</th>
					</tr>
				</thead>
				<c:forEach items="${list.list}" var="user" varStatus="vs">
					<tr>
					  <td class="tc"><input onclick="check()" type="checkbox" name="chkItem" value="${user.id}" /></td>
					  <td class="tc">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
					  <td class="tc" ><a href="#" onclick="view('${user.id}');">${user.loginName}</a></td>
					  <td class="tc">${user.relName}</td>
					  <td class="tc">${user.org.name}</td>
					  <td class="tc">${user.telephone}</td>
					  <td class="tc">
						<c:if test="${user.typeName == 0}">
							采购管理人员
						</c:if>
						<c:if test="${user.typeName == 1}">
							采购机构人员
						</c:if>
						<c:if test="${user.typeName == 2}">
							需求人员
						</c:if>
						<c:if test="${user.typeName == 3}">
							其他人员
						</c:if>
						<c:if test="${user.typeName == 4}">
							供应商
						</c:if>
						<c:if test="${user.typeName == 5}">
							专家
						</c:if>
						<c:if test="${user.typeName == 6}">
							进口供应商
						</c:if>
						<c:if test="${user.typeName == 7}">
							进口代理商
						</c:if>
					  </td>
					</tr>
				</c:forEach>
		       </table>
		    </div>
		  <div id="pagediv" align="right"></div>
	  </div>
  </body>
</html>
