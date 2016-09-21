<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
	
	
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/public/ztree/css/zTreeStyle.css">
	
    <script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/public/ztree/jquery.ztree.core.js"></script>
    <!--导航js-->
    <script src="${pageContext.request.contextPath}/public/ZHH/js/jquery_ujs.js"></script>
    <script src="${pageContext.request.contextPath}/public/ZHH/js/bootstrap.min.js"></script>
<script type="text/javascript">
	$(function(){
		/*这是要初始化成树的后台数据*/
		var datas;
		/*页面初始化时加载数据转换成json数据*/
		
		
		/*树的设置*/
		var setting={
			async:{
				autoParam:["id"],
				enable:true,
				url:"${pageContext.request.contextPath}/purchaseManage/gettree.do",
				dataType:"json",
				type:"post",
			},
			data:{
				simpleData:{
					enable:true,
					idKey:"id",
					pId:"pId",
					rootPId:-1,
				}
			},
			callback:{
				onClick:zTreeOnClick
			}
		};
		var treeObj=$.fn.zTree.init($("#ztree"),setting);
		treeObj.expandAll(false);
		//var id="${id}";
		/* if(id!=''&&id!=null){
			getList(id);
		}else{
			id="1";
			getList(id);
		} */
	});
	
	/*这个方法的treeNode.id就是对应的id。treeNode.name就是对应的name*/
	function zTreeOnClick(event,treeId,treeNode){
		console.dir(treeNode);
		$("#parentid").val(treeNode.id);
		if(treeNode.isParent==true){
			//console.dir("true");
		}else{
			getDetail(treeNode.id);
		}
		//getDetail(treeNode.id);
	};
	function getDetail(id){
		$.ajax({   
            type: "POST",  
            dataType: "json",
            async:false,
            url: "${pageContext.request.contextPath}/purchaseManage/getDetail.do?id="+id,      //提交到一般处理程序请求数据   
            success: function(data) {
            	console.dir(data.orgnization.name);
            	$("#org_name").html(data.orgnization.name);
            	$("#org_address").html(data.orgnization.address);
            	$("#org_mobile").html(data.orgnization.mobile);
            	$("#org_postCode").html(data.orgnization.postCode);
            	//var html ="";
                //$("#Result tr:gt(0)").remove();        //移除Id为Result的表格里的行，从第二行开始（这里根据页面布局不同页变）
               //$("#Result").append(html); 
            }  
        });
	}
	
	function add(){
		showiframe("需求部门新增",1000,600,"${pageContext.request.contextPath}/purchaseManage/add.do?","-4");
	}
	function edit(){
		var id = $("#parentid").val();
		showiframe("需求部门修改",1000,600,"${pageContext.request.contextPath}/purchaseManage/edit.do?id="+id,"-4");
    }
    function del(){
		var id = $("#parentid").val();
		layer.confirm('您确定要删除吗？',{icon:3,offset:295}, function(index){
			$.post("${pageContext.request.contextPath}/purchaseManage/updateOrg.do",
			{is_deleted:1,id:id},		
			function(data){
				showalert(data.message,1,"295");
				location.reload();
			},"json"); 
    	});
		showiframe("需求部门修改",1000,600,"${pageContext.request.contextPath}/purchaseManage/updateOrg.do?is_deleted=1&id="+id,"-4");
    }
   function showiframe(titles,width,height,url,top){
		 if(top == null || top == "underfined"){
		  top = 120;
		 }
		layer.open({
	        type: 2,
	        title: [titles,"background-color:#83b0f3;color:#fff;font-size:16px;text-align:center;"],
	        maxmin: true,
	        shade: [0.3, '#000'],
	       	offset: top+"px",
	        shadeClose: false, //点击遮罩关闭层 
	        area : [width+"px" , height+"px"],
	        content: url
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
                 <img alt="Logo" src="images/logo_2.png" id="logo-header">
              </a>
            </div>
			<!--菜单开始-->
            <div class="col-md-8 topbar-v1 col-md-12 ">
              <ul class="top-v1-data padding-0">
			    <li>
				<a href="#">
				  <div><img src="images/top_01.png"/></div>
				  <span>决策支持</span>
				 </a>
				</li>
			    <li>
				<a href="#">
				  <div><img src="images/top_02.png"/></div>
				  <span>业务监管</span>
				 </a>
				</li>
			    <li>
				<a href="#">
				  <div><img src="images/top_03.png"/></div>
				  <span>障碍作业</span>
				 </a>
				</li>	
			    <li>
				<a href="#">
				  <div><img src="images/top_04.png"/></div>
				  <span>信息服务</span>
				 </a>
				</li>
			    <li>
				<a href="#">
				  <div><img src="images/top_05.png"/></div>
				  <span>支撑环境</span>
				 </a>
				</li>
			    <li>
				<a href="#">
				  <div><img src="images/top_06.png"/></div>
				  <span>配置配置</span>
				 </a>
				</li>
			    <li>
				<a href="#">
				  <div><img src="images/top_07.png"/></div>
				  <span>后台首页</span>
				 </a>
				</li>
			    <li>
				<a href="#">
				  <div><img src="images/top_08.png"/></div>
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
					<li><a href="#"> 首页</a>
					</li>
					<li><a href="#">支撑系统</a>
					</li>
					<li><a href="#">后台管理</a>
					</li>
					<li class="active"><a href="#">部门管理</a>
					</li>
				</ul>
	  </div>
   </div>
        <!--=== End Breadcrumbs ===-->

        <!--=== Content Part ===-->
        <div class="container content height-350">
            <div class="row">
                <!-- Begin Content -->
                <div class="col-md-12" style="min-height:400px;">
					<div class="col-md-3 md-margin-bottom-40" id="show_tree_div">
						<div class="tag-box tag-box-v3">
							<ul id="ztree_show" class="ztree">
								<div id="ztree" class="ztree"></div>
							</ul>
						</div>
						<div class="btn-group-vertical" id="rMenu"
							style=" ">
							<button class="btn" style="font-size:12px;"
								onClick="addTreeNode();">
								<i class="icon-plus"></i> 增加
							</button>
							<button class="btn" style="font-size:12px;"
								onClick="editTreeNode();">
								<i class="icon-wrench"></i> 修改
							</button>
						</div>
					</div>
					<div class="btn-group-vertical" id="rMenu"
							style=" ">
							<button class="btn btn-windows edit" type="button" style="font-size:12px;" onClick="addTreeNode();"
						><i class="icon-plus"></i> 增加1</button>
							<button class="btn" style="font-size:12px;"
								onClick="addTreeNode();">
								<i class="icon-plus"></i> 增加1
							</button>
							<button class="btn" style="font-size:12px;"
								onClick="editTreeNode();">
								<i class="icon-wrench"></i> 修改1
							</button>
						</div>
					<div class="tag-box tag-box-v4 col-md-9" id="show_content_div">
						<div aria-hidden="true" aria-labelledby="opt_dialog_Label"
							role="dialog" tabindex="-1" id="opt_dialog" class="modal fade"
							style="display: none;">
							<div class="modal-dialog">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal"
											aria-hidden="true">×</button>
										<h4 class="modal-title" id="opt_dialog_Label">提示</h4>
									</div>
									<div class="modal-body"></div>
								</div>
							</div>
						</div>

						<div class="tab-content">
							<div class="tab-pane fade active in" id="show_ztree_content">
								<div
									class="panel-heading overflow-h margin-bottom-20 no-padding"
									id="ztree_title">
									<h2 class="panel-title heading-sm pull-left">
										<i class="fa fa-bars"></i> xxxx有限公司 <span
											class="label rounded-2x label-u">正常</span>
									</h2>
									<div class="pull-right">
										<a class="btn btn-sm btn-default" href="javascript:void(0)"
											onClick=""><i class="fa fa-search-plus"></i> 详细</a> <a
											class="btn btn-sm btn-default" href="javascript:void(0)"
											onClick=""><i class="fa fa-wrench"></i> 修改</a> <a
											class="btn btn-sm btn-default" href="javascript:void(0)"
											onClick=""><i class="fa fa-plus"></i> 增加下属单位</a> <a
											class="btn btn-sm btn-default" data-toggle="modal" href=""><i
											class="fa fa-plus"></i> 增加人员</a>
									</div>
								</div>
								<div id="ztree_content">
									<div class="tab-v2">
										<ul class="nav nav-tabs bgwhite">
											<li class="active"><a href="#dep_tab-0"
												data-toggle="tab" class="s_news"><h4>详细信息</h4>
											</a>
											</li>
											<li><a href="#dep_tab-1" data-toggle="tab"
												class="fujian"><h4>附件</h4>
											</a>
											</li>
											<li><a href="#dep_tab-2" data-toggle="tab"
												class="record"><h4>历史记录</h4>
											</a>
											</li>
										</ul>
										<div class="tab-content">
											<div class="tab-pane fade in active" id="dep_tab-0">
												<div class="show_obj">
													<table class="table table-striped table-bordered">
														<tbody>
															<tr>
																<td width="25%">单位名称：</td>
																<td width="25%">xxxx有限公司</td>
																<td width="25%">单位简称：</td>
																<td width="25%">服务公司</td>
															</tr>
															<tr>
																<td width="25%">曾用名：</td>
																<td width="25%">xxxx有限公司</td>
																<td width="25%">单位类型：</td>
																<td width="25%">独立核算单位</td>
															</tr>
															<tr>
																<td width="25%">邮政编码：</td>
																<td width="25%">100044</td>
																<td width="25%">所在地区：</td>
																<td width="25%">北京</td>
															</tr>
															<tr>
																<td width="25%">详细地址：</td>
																<td width="25%">北京市西四环中路16号院8号楼</td>
																<td width="25%">电话（总机）：</td>
																<td width="25%">88016942</td>
															</tr>
															<tr>
																<td width="25%">传真：</td>
																<td width="25%">-</td>
																<td></td>
																<td></td>
															</tr>
														</tbody>
													</table>
												</div>
												<div class="">
													<a class="btn btn-sm btn-default" href="javascript:void(0)"
														onClick=""> 新增</a> <a class="btn btn-sm btn-default"
														href="javascript:void(0)" onClick="">修改</a>
												</div>
												<div class="panel panel-grey clear mt5">
													<div class="panel-heading">
														<h3 class="panel-title">
															<i class="fa fa-users"></i> 用户列表
														</h3>
													</div>
													<div class="panel-body">
														<table class="table table-bordered table-hover">
															<thead>
																<tr>
																	<th><input type="checkbox" />
																	</th>
																	<th>用户名</th>
																	<th>姓名</th>
																	<th>联系方式</th>
																	<th>状态</th>
																</tr>
															</thead>
															<tbody>
																<tr>
																	<td align="center"><input type="checkbox" />
																	</td>
																	<td><a href="#">zclfwgs</a>
																	</td>
																	<td>佀洪涛</td>
																	<td>18610028857 / 88016942</td>
																	<td align="center"><span
																		class="label rounded-2x label-u">正常</span>
																	</td>
																</tr>
																<tr>
																	<td align="center"><input type="checkbox" /></i>
																	</td>
																	<td><a href="#">zclfwgs</a>
																	</td>
																	<td>零零一</td>
																	<td>- / 88016617</td>
																	<td align="center"><span
																		class="label rounded-2x label-u">正常</span>
																	</td>
																</tr>
																<tr>
																	<td align="center"><input type="checkbox" />
																	</td>
																	<td><a href="#">zclfwgs</a>
																	</td>
																	<td>杨立苗</td>
																	<td></td>
																	<td align="center"><span
																		class="label rounded-2x label-u">正常</span>
																	</td>
																</tr>
																<tr>
																	<td align="center"><input type="checkbox" />
																	</td>
																	<td><a href="#">zclfwgs</a>
																	</td>
																	<td>高晓江</td>
																	<td>88016862</td>
																	<td align="center"><span
																		class="label rounded-2x label-u">正常</span>
																	</td>
																</tr>
																<tr>
																	<td align="center"><input type="checkbox" />
																	</td>
																	<td><a href="#">zclfwgs</a>
																	</td>
																	<td>张圃兹</td>
																	<td>15010992543 / 88016801</td>
																	<td align="center"><span
																		class="label rounded-2x label-u">正常</span>
																	</td>
																</tr>
																<tr>
																	<td align="center"><input type="checkbox" />
																	</td>
																	<td><a href="#">zclfwgs</a>
																	</td>
																	<td>测试账号</td>
																	<td>- / -</td>
																	<td align="center"><span
																		class="label rounded-2x label-dark">已冻结</span>
																	</td>
																</tr>
																<tr>
																	<td align="center"><input type="checkbox" />
																	</td>
																	<td><a href="#">zclfwgs</a>
																	</td>
																	<td>王松</td>
																	<td>18631539699 / 88016802</td>
																	<td align="center"><span
																		class="label rounded-2x label-u">正常</span>
																	</td>
																</tr>
																<tr>
																	<td align="center"><input type="checkbox" />
																	</td>
																	<td><a href="#">zclfwgs</a>
																	</td>
																	<td>汪喜波</td>
																	<td>13910223096 / 88016732</td>
																	<td align="center"><span
																		class="label rounded-2x label-u">正常</span>
																	</td>
																</tr>
																<tr>
																	<td align="center"><input type="checkbox" />
																	</td>
																	<td><a href="#">zclfwgs</a>
																	</td>
																	<td>赵亭</td>
																	<td>- / 88016927</td>
																	<td align="center"><span
																		class="label rounded-2x label-u">正常</span>
																	</td>
																</tr>
																<tr>
																	<td align="center"><input type="checkbox" />
																	</td>
																	<td><a href="#">zclfwgs</a>
																	</td>
																	<td>测试</td>
																	<td>- / -</td>
																	<td align="center"><span
																		class="label rounded-2x label-dark">已冻结</span>
																	</td>
																</tr>
																<tr>
																	<td align="center"><input type="checkbox" />
																	</td>
																	<td><a href="#">zclfwgs</a>
																	</td>
																	<td>江平</td>
																	<td>- / -</td>
																	<td align="center"><span
																		class="label rounded-2x label-u">正常</span>
																	</td>
																</tr>
																<tr>
																	<td align="center"><input type="checkbox" />
																	</td>
																	<td><a href="#">zclfwgs</a>
																	</td>
																	<td>许锡炎</td>
																	<td>- / 68776617</td>
																	<td align="center"><span
																		class="label rounded-2x label-u">正常</span>
																	</td>
																</tr>
																<tr>
																	<td align="center"><input type="checkbox" />
																	</td>
																	<td><a href="#">zclfwgs</a>
																	</td>
																	<td>许锡炎</td>
																	<td>- / -</td>
																	<td align="center"><span
																		class="label rounded-2x label-u">正常</span>
																	</td>
																</tr>
																<tr>
																	<td align="center"><input type="checkbox" />
																	</td>
																	<td><a href="#">zclfwgs</a>
																	</td>
																	<td>孙玮</td>
																	<td>- / -</td>
																	<td align="center"><span
																		class="label rounded-2x label-u">正常</span>
																	</td>
																</tr>
																<tr>
																	<td align="center"><input type="checkbox" />
																	</td>
																	<td><a href="#">zclfwgs</a>
																	</td>
																	<td>1111</td>
																	<td>1 / 1</td>
																	<td align="center"><span
																		class="label rounded-2x label-u">正常</span>
																	</td>
																</tr>
															</tbody>
														</table>
													</div>
												</div>
											</div>
											<div class="tab-pane fade in" id="dep_tab-1">
												<div class="content-boxes-v2 space-lg-hor content-sm ">
													<h2 class="heading-sm">
														<i
															class="icon-custom icon-sm icon-bg-red fa fa-lightbulb-o"></i>
														<span>抱歉，没有找到相关信息。</span>
													</h2>
												</div>
											</div>
											<div class="tab-pane fade in" id="dep_tab-2">
												<ul class="timeline-v2">
													<li><time class="cbp_tmtime" datetime="">
															<span>14:27:16</span> <span>2016-08-01</span>
														</time> <i class="cbp_tmicon rounded-x hidden-xs"></i>
														<div class="cbp_tmlabel">
															<h4>
																<i class="fa fa-chevron-circle-down"></i> 修改数据 <i
																	class="fa fa-wrench"></i>
															</h4>
															<div class="margin-bottom-10">
																<div class="headline">
																	<h3 class="heading-sm">修改详细信息</h3>
																</div>
																<table class="table table-bordered">
																	<thead>
																		<tr>
																			<th>参数名称</th>
																			<th>修改前</th>
																			<th>修改后</th>
																		</tr>
																	</thead>
																	<tbody>
																		<tr>
																			<td>详细地址</td>
																			<td>北京市西四环中路16号院8号楼（金沟河桥东南角）</td>
																			<td>北京市西四环中路16号院8号楼</td>
																		</tr>
																	</tbody>
																</table>
															</div>
															<p>
																状态:<span class="label rounded-2x label-u">正常</span>&nbsp;&nbsp;姓名:许锡炎&nbsp;&nbsp;ID:151234&nbsp;&nbsp;单位:xxxxx有限公司&nbsp;&nbsp;IP地址:61.135.234.125|北京市
															</p>
														</div></li>
													<li><time class="cbp_tmtime" datetime="">
															<span>10:17:11</span> <span>2016-01-26</span>
														</time> <i class="cbp_tmicon rounded-x hidden-xs"></i>
														<div class="cbp_tmlabel">
															<h4>
																<i class="fa fa-chevron-circle-down"></i> 修改单位信息 <i
																	class="fa fa-wrench"></i>
															</h4>
															<div class="margin-bottom-10">
																<font class="view_logs_detail">修改痕迹</font>
																<div class="logs_detail">
																	<table class="table table-bordered">
																		<tbody>
																			<tr>
																				<td>参数名</td>
																				<td>修改前</td>
																				<td>修改后</td>
																			</tr>
																			<tr>
																				<td>详细地址</td>
																				<td>北京市西四环中路16号生物技术中心8号楼（金沟河桥东南角）</td>
																				<td>北京市西四环中路16号院8号楼（金沟河桥东南角）</td>
																			</tr>
																		</tbody>
																	</table>
																</div>
															</div>
															<p>
																状态:<span class="label rounded-2x label-u">正常</span>姓名:许锡炎&nbsp;&nbsp;ID:151234&nbsp;&nbsp;单位:xxx有限公司&nbsp;&nbsp;IP地址:61.135.234.125|北京市
															</p>
														</div></li>
													<li><time class="cbp_tmtime" datetime="">
															<span>14:57:41</span> <span>2015-11-02</span>
														</time> <i class="cbp_tmicon rounded-x hidden-xs"></i>
														<div class="cbp_tmlabel">
															<h4>
																<i class="fa fa-chevron-circle-down"></i> 修改单位信息 <i
																	class="fa fa-wrench"></i>
															</h4>
															<div class="margin-bottom-10">
																<font class="view_logs_detail">修改痕迹</font>
																<div class="logs_detail">
																	<table class="table table-bordered">
																		<tbody>
																			<tr>
																				<td>参数名</td>
																				<td>修改前</td>
																				<td>修改后</td>
																			</tr>
																			<tr>
																				<td>详细地址</td>
																				<td>西直门外大街甲143号凯旋大厦A座</td>
																				<td>北京市西四环中路16号生物技术中心8号楼（金沟河桥东南角）</td>
																			</tr>
																		</tbody>
																	</table>
																</div>
															</div>
															<p>
																状态:<span class="label rounded-2x label-u">正常</span>&nbsp;&nbsp;姓名:彭威&nbsp;&nbsp;ID:151234&nbsp;&nbsp;单位:xxx有限公司&nbsp;&nbsp;IP地址:111.198.187.189|欧洲
															</p>
														</div></li>
													<li><time class="cbp_tmtime" datetime="">
															<span>14:41:56</span> <span>2013-04-18</span>
														</time> <i class="cbp_tmicon rounded-x hidden-xs"></i>
														<div class="cbp_tmlabel">
															<h4>
																<i class="fa fa-chevron-circle-down"></i> 修改采购单位信息 <i
																	class="fa fa-wrench"></i>
															</h4>
															<div class="margin-bottom-10"></div>
															<p>
																状态:<span class="label rounded-2x label-u">正常</span>&nbsp;&nbsp;姓名:许锡炎&nbsp;&nbsp;ID:146393&nbsp;&nbsp;单位:xxxx有限公司&nbsp;&nbsp;IP地址:172.16.25.117|局域网
															</p>
														</div></li>
													<li><time class="cbp_tmtime" datetime="">
															<span>19:12:33</span> <span>2013-04-06</span>
														</time> <i class="cbp_tmicon rounded-x hidden-xs"></i>
														<div class="cbp_tmlabel">
															<h4>
																<i class="fa fa-chevron-circle-down"></i> 修改采购单位信息 <i
																	class="fa fa-wrench"></i>
															</h4>
															<div class="margin-bottom-10"></div>
															<p>
																状态:<span class="label rounded-2x label-u">正常</span>&nbsp;&nbsp;姓名:许锡炎&nbsp;&nbsp;ID:146393&nbsp;&nbsp;单位:xxxx有限公司&nbsp;&nbsp;IP地址:172.16.25.12|局域网
															</p>
														</div></li>
													<li><time class="cbp_tmtime" datetime="">
															<span>19:01:00</span> <span>2013-04-06</span>
														</time> <i class="cbp_tmicon rounded-x hidden-xs"></i>
														<div class="cbp_tmlabel">
															<h4>
																<i class="fa fa-chevron-circle-down"></i> 修改采购单位信息 <i
																	class="fa fa-wrench"></i>
															</h4>
															<div class="margin-bottom-10"></div>
															<p>
																状态:<span class="label rounded-2x label-u">正常</span>&nbsp;&nbsp;姓名:许锡炎&nbsp;&nbsp;ID:146393&nbsp;&nbsp;单位:xxxx有限公司&nbsp;&nbsp;IP地址:172.16.25.12|局域网
															</p>
														</div></li>
												</ul>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>


				</div>
                <!-- End Content -->
            </div>
        </div><!--/container-->
        <!--=== End Content Part ===-->
<!--底部代码开始-->
<div class="footer-v2" id="footer-v2">

      <div class="footer">

            <!-- Address -->
              <address class="">
			  Copyright  2016 版权所有：中央军委后勤保障部 京ICP备09055519号
              </address>
              <div class="">
		       浏览本网主页，建议将电脑显示屏的分辨率调为1024*768
              </div> 
            <!-- End Address -->
       </div>
<!--/footer--> 
</div>
    </div>
</body>
</html>
