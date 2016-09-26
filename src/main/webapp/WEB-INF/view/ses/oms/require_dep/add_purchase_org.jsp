<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title></title>

	<!-- Meta -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/bootstrap.min.css" media="screen" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/public/ZHH/css/common.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/style.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/line-icons.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/app.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/application.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/header-v4.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/header-v5.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/footer-v2.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/img-hover.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/page_job.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/shop.style.css" media="screen" rel="stylesheet">
	
	
	<link href="<%=basePath%>public/oms/css/consume.css"  rel="stylesheet">
	
	
    <script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.min.js"></script>
    <!--导航js-->
    <script src="${pageContext.request.contextPath}/public/ZHH/js/jquery_ujs.js"></script>
    <script src="${pageContext.request.contextPath}/public/ZHH/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/public/oms/js/select-tree.js"></script>
	<script src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
    
    <%--  <script type="text/javascript" src="${pageContext.request.contextPath}/public/oms/js/bxCarousel.js"></script> --%>
    <script type="text/javascript">
    	<%-- $(function(){
		   laypage({
			    cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
			    pages: "${result.pages}", //总页数
			    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
			    skip: true, //是否开启跳页
			    groups: "${result.pages}">=3?3:"${result.pages}", //连续显示分页数
			    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
// 			        var page = location.search.match(/page=(\d+)/);
// 			        return page ? page[1] : 1;
					return "${result.pageNum}";
			    }(), 
			    jump: function(e, first){ //触发分页后的回调
			        if(!first){ //一定要加此判断，否则初始时会无限刷新
			        	$("#page").val(e.curr);
			        	$("#form1").submit();
			        	
			          //location.href = '<%=basePath%>purchase/list.do?page='+e.curr;
			        }
			    }
			});
	 	 });  --%>
   /** 全选全不选 */
    Array.prototype.indexOf = function(val) {
		for (var i = 0; i < this.length; i++) {
			if (this[i] == val) return i;
		}
		return -1;
	};
	Array.prototype.remove = function(val) {
		var index = this.indexOf(val);
			if (index > -1) {
				this.splice(index, 1);
			}
	};
    var arr=[];
	function selectAll(){
		//alert($("#allId").prop("checked"));
		if ($("#allId").prop("checked")) {  
            $("input[name=items]").each(function() {  
                $(this).prop("checked", true);  
            });  
        } else {  
            $("input[name=items]").each(function() {  
                $(this).prop("checked", false);  
            });  
        }   
	}
	function add(){
		console.dir(arr);
		console.dir(parent.array);
		var index = parent.layer.getFrameIndex(window.name);
		//parent.$("#tab tr").remove();
		parent.$("#tab tr:gt(0)").remove();
		for(var i=0;i<parent.array.length;i++){
			var id = parent.array[i].substr(0,parent.array[i].indexOf(","));
			var name = parent.array[i].substr(parent.array[i].indexOf(",")+1,parent.array[i].length);
			//id = "\'"+id+"\'";
			parent.$("#tab").append("<tr id="+id+" align='center'>"
            					+"<td><input type='checkbox'/></td>"     
                                +"<td>"+Number(i+1)+"</td>"
                                //+"<td><input type='text' name='desc"+_len+"' id='name"+_len+"' value='"+_len+"' /></td>"
                                +"<td>"+name+"</td>"
                                 +"<td class='hide'>"+id+"</td>"
                                +"<td><a href=\'#\' onclick=\'deltr("+"\""+id+"\""+","+"\""+name+"\""+")\'>删除</a></td>"
                            +"</tr>"); 
		}
		//var _len="dddd";
		 
        parent.layer.close(index); //执行关闭
		/*  var _len = arr[i];        
            $("#tab").append("<tr id="+_len+" align='center'>"
            					+"<td><input type='checkbox'/></td>"     
                                +"<td>"+_len+"</td>"
                                //+"<td><input type='text' name='desc"+_len+"' id='name"+_len+"' value='"+_len+"' /></td>"
                                 +"<td>"+_len+"</td>"
                                +"<td>"+_len+"</td>"
                                +"<td><a href=\'#\' onclick=\'deltr("+_len+")\'>删除</a></td>"
                            +"</tr>");     */
			
	}
	function check(id,name){
		var data = id+","+name;
		//单选时间
		if($("#"+id).prop("checked")){
			arr.push(data);
			console.dir(parent.array);
			console.dir(data);
			console.dir(parent.array.indexOf(data)==0);
			if(parent.array.indexOf(data)==-1){
				parent.array.push(data);
			}
		}else{
			arr.remove(data);
			parent.array.remove(data);
		}
		//console.dir(id);
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
								<a href=""> <img alt="Logo" src="${pageContext.request.contextPath}/public/ZHH/images/logo_2.png"
									id="logo-header"> </a>
							</div>
							<!--菜单开始-->
							<div class="col-md-8 topbar-v1 col-md-12 ">
								<ul class="top-v1-data padding-0">
									<li><a href="#">
											<div>
												<img src="${pageContext.request.contextPath}/public/ZHH/images/top_01.png" />
											</div> <span>决策支持</span> </a></li>
									<li><a href="#">
											<div>
												<img src="${pageContext.request.contextPath}/public/ZHH/images/top_02.png" />
											</div> <span>业务监管</span> </a></li>
									<li><a href="#">
											<div>
												<img src="${pageContext.request.contextPath}/public/ZHH/images/top_03.png" />
											</div> <span>障碍作业</span> </a></li>
									<li><a href="#">
											<div>
												<img src="${pageContext.request.contextPath}/public/ZHH/images/top_04.png" />
											</div> <span>信息服务</span> </a></li>
									<li><a href="#">
											<div>
												<img src="${pageContext.request.contextPath}/public/ZHH/images/top_05.png" />
											</div> <span>支撑环境</span> </a></li>
									<li><a href="#">
											<div>
												<img src="${pageContext.request.contextPath}/public/ZHH/images/top_06.png" />
											</div> <span>配置配置</span> </a></li>
									<li><a href="#">
											<div>
												<img src="${pageContext.request.contextPath}/public/ZHH/images/top_07.png" />
											</div> <span>后台首页</span> </a></li>
									<li><a href="#">
											<div>
												<img src="${pageContext.request.contextPath}/public/ZHH/images/top_08.png" />
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
					<li class="active"><a href="#">采购人管理</a>
					</li>
				</ul>
			</div>
		</div>
		<div class="container">
			<div class="headline-v2">
				<h2>采购机构人员列表</h2>
		</div>
		<form action="<%=basePath%>purchaseManage/addPurchaseOrg.html" method="post"
			id="form1" enctype="multipart/form-data" class="registerform">
			<input type="hidden" name="page" id="page"> <input
				type="hidden" name="flag" value="0">
				<input type="hidden" value="${orgnization.typeName }"/>
			<div align="center">
				<table>
					<tr>
						<td><span>机构名称：</span><input type="text" name="name"
							value="${orgnization.name }">
						</td>
						<td><span class="input-group-btn">
								<input class="btn-u" name="commit" value="搜索" type="submit"> 
								<input class="btn btn-default" type="button" onclick="add();" value="添加"/>
							</span>
						</td>
					</tr>
				</table>

			</div>
			<!-- 表格开始-->
			<div class="container">
				
			</div>

			<div class="container margin-top-5">
				<div class="content padding-left-25 padding-right-25 padding-top-5">
					<table class="table table-bordered table-condensed">
						<thead>
							<tr>
								<th class="info w30"><input type="checkbox"
									onclick="selectAll();" id="allId" alt="全选"></th>
								<th class="info w50">序号</th>
								<th class="info">机构名称</th>
								<th class="info">类型</th>
							</tr>
						</thead>

						<tbody>
							<c:forEach items="${orgnizationList}" var="p" varStatus="vs">
								<tr class="cursor">
									<!-- 选择框 -->
									<td class="tc"><input onclick="check('${p.id}','${p.name }')"
										type="checkbox" name="items" id="${p.id }" value="${p.id}" /></td>
									<!-- 序号 -->
									<td class="tc">${vs.index+1}</td>
									<!-- 内容 -->
									<td class="tc">${p.name}</td>
									<!-- 创建人-->
									<td class="tc">${p.typeName}</td>
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
