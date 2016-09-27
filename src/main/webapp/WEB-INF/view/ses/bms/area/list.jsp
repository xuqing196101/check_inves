<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>地区管理</title>
<script src="<%=basePath%>public/ZHH/js/jquery.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/public/ztree/css/zTreeStyle.css">
<script type="text/javascript"
	src="<%=request.getContextPath()%>/public/ztree/jquery.ztree.core.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/public/ztree/jquery.ztree.excheck.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/public/layer/layer.js"></script>
<script type="text/javascript">
        var datas;
        var setting;
        function loadTree(name) {
            
            var url = "<%=basePath%>area/listByOne.do";
            if (name) {
                url = "<%=basePath%>area/listByOne.do?name=" + name; 
            }
	        /*树的设置*/
	        setting={
	            async:{
	                autoParam:["id"],
	                enable:true,
	                url:url,
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
	               
	            }
	        };
	         var treeObj=$.fn.zTree.init($("#tree"),setting,datas);
        }
        
     $(function(){
      
        loadTree("");
        
        treeObj.expandAll(false);
    });
     
     /**
     function InitialZtree(){
        var treeObj=$.fn.zTree.init($("#tree"),setting,datas);
        treeObj.expandAll(false);
    }  

    function zTreeOnClick(event,treeId,treeNode){
        var zTree = $.fn.zTree.getZTreeObj("tree");
            //获得选中的节点
            var nodes = zTree.getSelectedNodes(),
            v = "";
            //根据id排序
            nodes.sort(function compare(a, b) { return a.id - b.id; });
            for (var i = 0, l = nodes.length; i < l; i++) {
                v += nodes[i].name + ",";
            }
            //将选中节点的名称显示在文本框内
            if (v.length > 0) v = v.substring(0, v.length - 1);
            var cityObj = $("#search_condition");
            cityObj.attr("value", v);
            //隐藏zTree
            hideMenu();
            return false;
    };
    //显示树
function showMenu() {
            var cityObj = $("#search_condition");
            var cityOffset = $("#search_condition").offset();
            $("#menuContent").css({ left: cityOffset.left + "px", top: cityOffset.top + cityObj.outerHeight() + "px" }).slideDown("fast");
}
       
 //隐藏树
function hideMenu() {
            $("#menuContent").fadeOut("fast");
            $("body").unbind("mousedown", onBodyDown);
}

   ///根据文本框的关键词输入情况自动匹配树内节点 进行模糊查找
function AutoMatch(txtObj) {
            if (txtObj.value.length > 0) {
                InitialZtree();
                var zTree = $.fn.zTree.getZTreeObj("tree");
                var nodeList = zTree.getNodesByParamFuzzy("name", txtObj.value);
                //将找到的nodelist节点更新至Ztree内
                $.fn.zTree.init($("#tree"), setting, nodeList);
                showMenu();
            } else {
                //隐藏树
                hideMenu();
                InitialZtree();                
            }              
} 
   function search_ztree( treeId, searchCondition){
        searchByFlag_ztree(treeId, searchCondition);
    }
     
  
    function searchByFlag_ztree(treeId, searchCondition){
        //<1>.搜索条件
        var searchCondition = $('#' + treeNode).val();
        //<2>.得到模糊匹配搜索条件的节点数组集合
        var highlightNodes = new Array();
        if (searchCondition != "") {
            var treeObj = $.fn.zTree.getZTreeObj(treeId);
            highlightNodes = treeObj.getNodesByParamFuzzy("name", searchCondition);
        }
    } */
    
    function selectTree() {
        var name = $("input[name='name']").val();
        loadTree(name);
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
								<a href=""> <img alt="Logo" src="images/logo_2.png"
									id="logo-header"> </a>
							</div>
							<!--菜单开始-->
							<div class="col-md-8 topbar-v1 col-md-12 ">
								<ul class="top-v1-data padding-0">
									<li><a href="#">
											<div>
												<img src="images/top_01.png" />
											</div> <span>决策支持</span> </a>
									</li>
									<li><a href="#">
											<div>
												<img src="images/top_02.png" />
											</div> <span>业务监管</span> </a>
									</li>
									<li><a href="#">
											<div>
												<img src="images/top_03.png" />
											</div> <span>障碍作业</span> </a>
									</li>
									<li><a href="#">
											<div>
												<img src="images/top_04.png" />
											</div> <span>信息服务</span> </a>
									</li>
									<li><a href="#">
											<div>
												<img src="images/top_05.png" />
											</div> <span>支撑环境</span> </a>
									</li>
									<li><a href="#">
											<div>
												<img src="images/top_06.png" />
											</div> <span>配置配置</span> </a>
									</li>
									<li><a href="#">
											<div>
												<img src="images/top_07.png" />
											</div> <span>后台首页</span> </a>
									</li>
									<li><a href="#">
											<div>
												<img src="images/top_08.png" />
											</div> <span>安全退出</span> </a>
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
					<li><a href="#">首页</a></li>
					<li><a href="">单位及用户管理</a></li>
					<li><a href="">组织机构管理</a></li>
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
					   <div class="tab-content">
                            <%--   <form action="<%=basePath%>area/listByOne.do" method="post" id="form1"> --%>
                            <label class="fl">地区名称：<input type="text"
                                id="search_condition" name="name" /> </label>
                            <button
                                class="btn padding-left-10 padding-right-10 btn_back fl margin-top-5"
                                onclick="selectTree();">查询</button>
                            
                            <!-- </form> -->
                        </div>
						<div id="menuContent" class="tag-box tag-box-v3">

							<div id="tree" class="ztree"></div>

						</div>


					</div>
					<div class="tag-box tag-box-v4 col-md-9" id="show_content_div">

<span id="add"><a href="<%=basePath%>area/add.do"
                                class="btn btn-window ">新增 </a> </span>
					
					</div>


				</div>
				<!-- End Content -->
			</div>
		</div>
		<!--/container-->
		<!--=== End Content Part ===-->
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
