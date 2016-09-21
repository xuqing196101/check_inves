<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>采购机构查询列表</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link href="<%=basePath%>public/oms/css/consume.css"  rel="stylesheet">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->


</head>
<script src="<%=basePath%>public/layer/layer.js"></script>
<script src="<%=basePath%>public/laypage-v1.3/laypage/laypage.js"></script>
<script type="text/javascript">
  /* $(function(){
	  laypage({
		    cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
		    pages: "${listStationMessage.pages}", //总页数
		    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
		    skip: true, //是否开启跳页
		    groups: "${listStationMessage.pages}">=3?3:"${listStationMessage.pages}", //连续显示分页数
		    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
// 		        var page = location.search.match(/page=(\d+)/);
		        return "${listStationMessage.pageNum}";
		    }(), 
		    jump: function(e, first){ //触发分页后的回调
		        if(!first){ //一定要加此判断，否则初始时会无限刷新
		        	$("#pages").val(e.curr);
		        	$("form:first").submit();
		        }
		    }
		});
  }); */
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
  		window.location.href="<%=basePath%>StationMessage/showStationMessage.do?id="+id+"&&type='view'";
  	}
    function edit(){
    	var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(id.length==1){
			window.location.href="<%=basePath%>StationMessage/showStationMessage.do?id="+id+"&&type='edit'";
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
				window.location.href="<%=basePath%>StationMessage/deleteSoftSMIsDelete.do?ids="+ids;
			});
		}else{
			layer.alert("请选择要删除的用户",{offset: ['222px', '390px'], shade:0.01});
		}
    }
    function add(){
    	window.location.href="<%=basePath%>purchaseManage/addPurchaseDep.do";
    }
    function show(id){
    	window.location.href="<%=basePath%>StationMessage/showStationMessage.do?id="+id+"&&type=view";
    }
  </script>
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
								<a href=""> <img alt="Logo" src="images/logo_2.png"
									id="logo-header"> </a>
							</div>
							<!--菜单开始-->
							<div class="col-md-8 topbar-v1 col-md-12 ">
								<ul class="top-v1-data padding-0">
									<li><a href="#">
											<div>
												<img src="images/top_01.png" />
											</div> <span>决策支持</span> </a></li>
									<li><a href="#">
											<div>
												<img src="images/top_02.png" />
											</div> <span>业务监管</span> </a></li>
									<li><a href="#">
											<div>
												<img src="images/top_03.png" />
											</div> <span>障碍作业</span> </a></li>
									<li><a href="#">
											<div>
												<img src="images/top_04.png" />
											</div> <span>信息服务</span> </a></li>
									<li><a href="#">
											<div>
												<img src="images/top_05.png" />
											</div> <span>支撑环境</span> </a></li>
									<li><a href="#">
											<div>
												<img src="images/top_06.png" />
											</div> <span>配置配置</span> </a></li>
									<li><a href="#">
											<div>
												<img src="images/top_07.png" />
											</div> <span>后台首页</span> </a></li>
									<li><a href="#">
											<div>
												<img src="images/top_08.png" />
											</div> <span>安全退出</span> </a></li>

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
					<li><a href="#"> 首页</a>
					</li>
					<li><a href="#">支撑系统</a>
					</li>
					<li><a href="#">后台管理</a>
					</li>
					<li class="active"><a href="#">采购机构管理</a>
					</li>
				</ul>
			</div>
		</div>
		<div class="container">
			<div class="headline-v2">
				<h2>采购机构列表</h2>
		</div>
		<form action="<%=basePath%>purchaseDep/list.html" method="post"
			id="form1" enctype="multipart/form-data" class="registerform">
			<input type="hidden" name="page" id="page"> <input
				type="hidden" name="flag" value="0">
			<div align="center">
				<table>
					<tr>
						<td><span>名称：</span><input type="text" name="name"
							value="${purchaseDep.name }">
						</td>
						<td><span>上级监管部门：</span> <select name="purchaseDepName"
							id="purchaseDepName">
								<option value=''>-请选择-</option>
								<option
									<c:if test="${purchaseInfo.purchaseDepName =='军队' }">selected = "true"</c:if>
									value="军队">军队</option>
								<option
									<c:if test="${purchaseInfo.purchaseDepName =='地方' }">selected = "true"</c:if>
									value="地方">地方</option>
								<option
									<c:if test="${purchaseInfo.purchaseDepName =='其他' }">selected = "true"</c:if>
									value="其他">其他</option>
						</select>
						</td>
						<td><span class="input-group-btn"> <input
								class="btn-u" name="commit" value="搜索" type="submit"> </span>
						</td>
					</tr>
				</table>

			</div>
			<!-- 表格开始-->
			<div class="container">
				<div class="col-md-8">
					<button class="btn btn-windows edit" type="button"
						onclick="edit();">新增</button>
					<button class="btn btn-windows edit" type="button"
						onclick="edit();">修改</button>
					<button class="btn btn-windows delete" type="button"
						onclick="dell();">删除</button>
					<button class="btn btn-windows git" type="button" onclick="show()">查看</button>
					<button class="btn btn-windows add" type="button" onclick="purchaseManage()">采购人员管理</button>
					<button class="btn btn-windows edit" type="button" onclick="stash()">资质暂停</button>
					<button class="btn btn-windows edit" type="button" onclick="over()">资质终止</button>
				</div>
			</div>

			<div class="container margin-top-5">
				<div class="content padding-left-25 padding-right-25 padding-top-5">
					<table class="table table-bordered table-condensed">
							<thead>
								<tr>
									<th class="info w30"><input id="checkAll" type="checkbox"
										onclick="selectAll()" />
									</th>
									<th class="info w50">序号</th>
									<th class="info">采购机构名称</th>
									<th class="info">邮编</th>
									<th class="info">单位地址</th>
									<th class="info">采购业务范围</th>
									<th class="info">采购资质编号</th>
									<th class="info">采购业务等级</th>
									<th class="info">采购资质范围</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${purchaseDepList}" var="p" varStatus="vs">
									<tr class="cursor">
										<!-- 选择框 -->
										<td onclick="null" class="tc"><input onclick="check()"
											type="checkbox" name="chkItem" value="${p.id}" />
										</td>
										<!-- 序号 -->
										<td class="tc" onclick="show('${p.id}');">${vs.index+1}</td>
										<!-- 标题 -->
										<td class="tc" onclick="show('${p.id}');">${p.name}</td>
										<!-- 内容 -->
										<td class="tc" onclick="show('${p.id}');">${p.postCode}</td>
										<!-- 创建人-->
										<td class="tc" onclick="show('${p.id}');">${p.address}</td>
										<!-- 是否发布 -->
										<td class="tc" onclick="show('${p.id}');">${p.businessRange}</td>
										<!-- 是否发布 -->
										<td class="tc" onclick="show('${p.id}');">${p.quaCode}</td>
										<!-- 是否发布 -->
										<td class="tc" onclick="show('${p.id}');">${p.quaLevel}</td>
										<!-- 是否发布 -->
										<td class="tc" onclick="show('${p.id}');">${p.quaRange}</td>
									</tr>
								</c:forEach>
							</tbody>
					</table>
					<!-- <div id="page" align="right"></div> -->
					<p class="pagestyle">${pagesql}</p>

				</div>
			</div>
		</form>
			<!--/container-->
<!--底部代码开始-->
		<div class="footer-v2" id="footer-v2">
			<div class="footer">
				<!-- Address -->
				<address class="">Copyright 2016 版权所有：中央军委后勤保障部
					京ICP备09055519号</address>
				<div class="">浏览本网主页，建议将电脑显示屏的分辨率调为1024*768</div>
				<!-- End Address -->
			</div>
			<!--/footer-->
		</div>
	</div>
</body>
</html>
