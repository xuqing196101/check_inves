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
    <script src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
    <script src="${pageContext.request.contextPath}/public/oms/js/select-tree.js"></script>
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
		$("isroot").val(treeNode.id);
		$("#treebody").attr("src","${pageContext.request.contextPath}/purchaseManage/gettreebody.do?id="+treeNode.id);
		//window.location.href="${pageContext.request.contextPath}/purchase/list.do";
		if(treeNode.isParent==true){
			//console.dir("true");
		}else{
			//getDetail(treeNode.id);
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
	//正常增加、修改
	function addTreeNode(){
		window.location.href="${pageContext.request.contextPath}/purchaseManage/add.do?";
	}
	function editTreeNode(){
		//var id = $("#defaultid").val();
		//父页面获取iframe页面元素值
		var iframeObj=$(window.frames["treeframe"].document); 
		var id = iframeObj.find("#defaultid").val();
		$("#orgid").val(id);
		/* $.ajax({
			type : 'post',
			url : "${pageContext.request.contextPath}/purchaseManage/redirectUrl.do?",
			data : {id:id,url:url},
			//data: {'pid':pid,$("#formID").serialize()},
			success : function(data) {
				//truealert(data.message, data.success == false ? 5 : 1);
				window.location.href="${pageContext.request.contextPath}/purchaseManage/redirect.do?id=1";
			}
		}); */
		$("#hform").submit();
	}
	function delTreeNode(){
		var iframeObj=$(window.frames["treeframe"].document); 
		var id = iframeObj.find("#defaultid").val();
		//id ="AA463D92BE804A68B14842EF0C947A0B,EB15F7ECB27B4447AD8CBD91CB1C13F5";//批量删除
		 $.ajax({
			type : 'post',
			url : "${pageContext.request.contextPath}/purchaseManage/delOrg.do?",
			data : {ids:id},
			//data: {'pid':pid,$("#formID").serialize()},
			success : function(data) {
				//truealert(data.message, data.success == false ? 5 : 1);
				layer.open({
				    content: data.message,
				    icon: 1,
				    time:2000,
				    shade: [0.3, '#000'],
				    yes: function(index){
				        //do something
				    	 layer.closeAll();
				    	 
				    }
				});
				location.reload();
			}
		}); 
	}
	function pageOnLoad(){
		//test();
	}
</script>
</head>

<body onload="pageOnLoad();">
  <div class="wrapper">
   <%-- <div class="header-v4 header-v5">
    <!-- Navbar -->
    <div class="navbar navbar-default mega-menu" role="navigation">
      <div class="container">
        <!-- logo和搜索 -->
        <div class="navbar-header">
          <div class="row container">
            <div class="col-md-4 padding-bottom-30">
              <a href="">
                 <img alt="Logo" src="${pageContext.request.contextPath}/public/ZHH/images/logo_2.png" id="logo-header">
              </a>
            </div>
			<!--菜单开始-->
            <div class="col-md-8 topbar-v1 col-md-12 ">
              <ul class="top-v1-data padding-0">
			    <li>
				<a href="#">
				  <div><img src="${pageContext.request.contextPath}/public/ZHH/images/top_01.png"/></div>
				  <span>决策支持</span>
				 </a>
				</li>
			    <li>
				<a href="#">
				  <div><img src="${pageContext.request.contextPath}/public/ZHH/images/top_02.png"/></div>
				  <span>业务监管</span>
				 </a>
				</li>
			    <li>
				<a href="#">
				  <div><img src="${pageContext.request.contextPath}/public/ZHH/images/top_03.png"/></div>
				  <span>障碍作业</span>
				 </a>
				</li>	
			    <li>
				<a href="#">
				  <div><img src="${pageContext.request.contextPath}/public/ZHH/images/top_04.png"/></div>
				  <span>信息服务</span>
				 </a>
				</li>
			    <li>
				<a href="#">
				  <div><img src="${pageContext.request.contextPath}/public/ZHH/images/top_05.png"/></div>
				  <span>支撑环境</span>
				 </a>
				</li>
			    <li>
				<a href="#">
				  <div><img src="${pageContext.request.contextPath}/public/ZHH/images/top_06.png"/></div>
				  <span>配置配置</span>
				 </a>
				</li>
			    <li>
				<a href="#">
				  <div><img src="${pageContext.request.contextPath}/public/ZHH/images/top_07.png"/></div>
				  <span>后台首页</span>
				 </a>
				</li>
			    <li>
				<a href="#">
				  <div><img src="${pageContext.request.contextPath}/public/ZHH/images/top_08.png"/></div>
				  <span>安全退出</span>
				 </a>
				</li>
				
			  </ul>
			</div>
          </div>
	    </div>
	 </div>
   </div> --%>
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
					</div>
					<div style="margin-bottom: 6px; ">
						<div class="btn-group" id="rMenu"
							style=" ">
							<button type="button" class="btn btn-default" style="font-size:12px;"
								onClick="addTreeNode();">
								<i class="icon-plus"></i> 新增
							</button>
						</div>
						<div class="btn-group" id="rMenu"
							style=" ">
							<button type="button" class="btn btn-default" style="font-size:12px;"
								onClick="editTreeNode();">
								<i class="icon-plus"></i> 修改
							</button>
						</div>	
						<div class="btn-group" id="rMenu"
							style=" ">
							<button type="button" class="btn btn-default" style="font-size:12px;"
								onClick="delTreeNode();">
								<i class="icon-plus"></i> 删除
							</button>
						</div>	
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
						<!-- 隐藏域 -->
						<div>
							<input type="hidden" id="parentid" value="${orgnization.id }"/>
						</div>
						<!-- 隐藏域 -->
						<!-- iframe层 -->
						<iframe id="treebody" name="treeframe" src="${pageContext.request.contextPath}/purchaseManage/gettreebody.html" frameborder="0" style="width: 100%;height: 100%;">
						
						</iframe>
						<!-- iframe层-->
						<!-- 伪表单 跳转编辑页面 post传参数-->
						<form id="hform" action="${pageContext.request.contextPath}/purchaseManage/edit.do" method="post">
							<input type="hidden" id="orgid" name="id"/>
						</form>
						<!-- 伪表单-->
						
					</div>


				</div>
                <!-- End Content -->
            </div>
        </div><!--/container-->
        <!--=== End Content Part ===-->
<!--底部代码开始-->

    </div>
</body>
</html>
