<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE html>

<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
    
    <%@ include file="/WEB-INF/view/common.jsp"%>
  
</head>

<body>
  
<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
           <li><a >首页</a></li><li><a >采购项目管理</a></li><li><a>拟制招标文件</a></li> 
        </ul>
      </div>
   </div>

    <div class="container content">
        <div class="row">
            <div class="col-md-3 col-xs-4 col-sm-12" id="show_tree_div">
			    <div class="tag-box tag-box-v3">
			        <ul id="ztree_show" class="ztree">
			          <li id="ztree_show_1" class="level0" tabindex="0" hidefocus="true" treenode="">
			          </li>
			        </ul>
			     </div>		
    		</div>
		</div>
    </div>



</body>
</html>


