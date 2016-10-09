<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title></title>

	<!-- Meta -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
    <link href="<%=basePath%>public/ZHH/css/bootstrap.min.css" media="screen" rel="stylesheet">
	<link href="<%=basePath%>public/ZHH/css/common.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/style.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/line-icons.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/app.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/application.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/header-v4.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/header-v5.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/footer-v2.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/img-hover.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/page_job.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/shop.style.css" media="screen" rel="stylesheet">

    <script src="<%=basePath%>public/ZHH/js/jquery.min.js"></script>
    <!--导航js-->
    <script src="<%=basePath%>public/ZHH/js/jquery_ujs.js"></script>
    <script src="<%=basePath%>public/ZHH/js/bootstrap.min.js"></script>
</head>

<body>
  
<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		<ul class="breadcrumb margin-left-0">
		   <li><a href="#">首页</a></li><li><a href="">单位及用户管理</a></li><li><a href="">组织机构管理</a></li> 
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
		  <li id="ztree_show_1" class="level0" tabindex="0" hidefocus="true" treenode="">
		    <span id="ztree_show_1_switch" title="" class="button level0 switch root_close" treenode_switch=""></span>
			<a id="ztree_show_1_a" class="level0" treenode_a="" onClick="" target="_blank" style="" title="xxxx有限公司">
			<span id="ztree_show_1_ico" title="" treenode_ico="" class="button ico_close" style=""></span>
			<span id="ztree_show_1_span">xxxx有限公司</span></a>
		  </li>
		</ul>
	 </div>

	 <div class="btn-group-vertical" id="rMenu" style="position:absolute; visibility:hidden;">
        <button class="btn" style="font-size:12px;" onClick="addTreeNode();">
          <i class="icon-plus"></i> 增加
        </button>
        <button class="btn" style="font-size:12px;" onClick="editTreeNode();">
          <i class="icon-wrench"></i> 修改
        </button>
	</div>
</div>
<div class="tag-box tag-box-v4 col-md-9" id="show_content_div">
	<div aria-hidden="true" aria-labelledby="opt_dialog_Label" role="dialog" tabindex="-1" id="opt_dialog" class="modal fade" style="display: none;">
	  <div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				<h4 class="modal-title" id="opt_dialog_Label">提示</h4>
			</div>
			<div class="modal-body">	
			</div>
		</div>
	</div>
</div>

	<div class="tab-content">
	  <div class="tab-pane fade active in" id="show_ztree_content">
		<div class="panel-heading overflow-h margin-bottom-20 no-padding" id="ztree_title">
		  <h2 class="panel-title heading-sm pull-left">
	        <i class="fa fa-bars"></i> xxxx有限公司 <span class="label rounded-2x label-u">正常</span>
          </h2>
          <div class="pull-right">
	        <a class="btn btn-sm btn-default" href="javascript:void(0)" onClick=""><i class="fa fa-search-plus"></i> 详细</a> 
			<a class="btn btn-sm btn-default" href="javascript:void(0)" onClick=""><i class="fa fa-wrench"></i> 修改</a> 
			<a class="btn btn-sm btn-default" href="javascript:void(0)" onClick=""><i class="fa fa-plus"></i> 增加下属单位</a> 
			<a class="btn btn-sm btn-default" data-toggle="modal" href=""><i class="fa fa-plus"></i> 增加人员</a>
          </div>
		 </div>
		 <div id="ztree_content">
          <div class="tab-v2">
            <ul class="nav nav-tabs bgwhite">
              <li class="active"><a href="#dep_tab-0" data-toggle="tab" class="s_news"><h4> 详细信息</h4></a></li>
			  <li><a href="#dep_tab-1" data-toggle="tab" class="fujian"><h4> 附件</h4></a></li>
			  <li><a href="#dep_tab-2" data-toggle="tab" class="record"><h4>历史记录</h4></a></li>
          </ul>
          <div class="tab-content">
            <div class="tab-pane fade in active" id="dep_tab-0">
			  <div class="show_obj"><table class="table table-bordered">
			    <tbody>
				  <tr>
				    <td width="25%" class="bggrey tr">单位名称：</td>
				    <td width="25%">xxxx有限公司</td>
				    <td width="25%" class="bggrey tr">单位简称：</td>
				    <td width="25%">服务公司</td>
				  </tr>
				  <tr>
				    <td width="25%" class="bggrey tr">曾用名：</td>
				    <td width="25%">xxxx有限公司</td>
				    <td width="25%" class="bggrey tr">单位类型：</td>
				    <td width="25%">独立核算单位</td>
				  </tr>
				  <tr>
				    <td width="25%" class="bggrey tr">邮政编码：</td>
				    <td width="25%">100044</td>
				    <td width="25%" class="bggrey tr">所在地区：</td>
				    <td width="25%">北京</td></tr><tr>
				    <td width="25%" class="bggrey tr">详细地址：</td>
				    <td width="25%">北京市西四环中路16号院8号楼</td>
				    <td width="25%" class="bggrey tr">电话（总机）：</td>
				    <td width="25%">88016942</td>
				  </tr>
				  <tr>
				    <td width="25%" class="bggrey tr">传真：</td>
				    <td width="25%">-</td>
				    <td  width="25%" class="bggrey tr"></td>
				    <td width="25%"></td>
				  </tr>
				 </tbody>
			 </table>
		</div>
		  <div class="">
			<a class="btn btn-sm btn-default" href="javascript:void(0)" onClick=""> 新增</a> 
			<a class="btn btn-sm btn-default" href="javascript:void(0)" onClick="">修改</a> 
          </div>
       <div class="panel panel-grey clear mt5">
	    <div class="panel-heading">
		 <h3 class="panel-title"><i class="fa fa-users"></i> 用户列表</h3>
	    </div>
	    <div class="panel-body">
		  <table class="table table-bordered table-hover">
			<thead>
				<tr>
					<th><input type="checkbox"/></th>
					<th>用户名</th>
					<th>姓名</th>
					<th>联系方式</th>
					<th>状态</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td align="center"><input type="checkbox"/></td>
					<td><a href="#">zclfwgs</a></td>
					<td>张三</td>
					<td>18610028857 / 88016942</td>
					<td align="center"><span class="label rounded-2x label-u">正常</span></td>
				</tr>
				<tr>
					<td align="center"><input type="checkbox"/></i></td>
					<td><a href="#">zclfwgs</a></td>
					<td>零零一</td>
					<td>- / 88016617</td>
					<td align="center"><span class="label rounded-2x label-u">正常</span></td>
				</tr>
				<tr>
					<td align="center"><input type="checkbox"/></td>
					<td><a href="#">zclfwgs</a></td>
					<td>王五</td>
					<td></td>
					<td align="center"><span class="label rounded-2x label-u">正常</span></td>
				</tr>
				<tr>
					<td align="center"><input type="checkbox"/></td>
					<td><a href="#">zclfwgs</a></td>
					<td>李四</td>
					<td>88016862</td>
					<td align="center"><span class="label rounded-2x label-u">正常</span></td>
				</tr>
				<tr>
					<td align="center"><input type="checkbox"/></td>
					<td><a href="#">zclfwgs</a></td>
					<td>郑王</td>
					<td>15010992543 / 88016801</td>
					<td align="center"><span class="label rounded-2x label-u">正常</span></td>
				</tr>
				<tr>
					<td align="center"><input type="checkbox"/></td>
					<td><a href="#">zclfwgs</a></td>
					<td>测试账号</td>
					<td>- / -</td>
					<td align="center"><span class="label rounded-2x label-dark">已冻结</span></td>
				</tr>
				<tr>
					<td align="center"><input type="checkbox"/></td>
					<td><a href="#">zclfwgs</a></td>
					<td>大帝</td>
					<td>18631539699 / 88016802</td>
					<td align="center"><span class="label rounded-2x label-u">正常</span></td>
				</tr>
				<tr>
					<td align="center"><input type="checkbox"/></td>
					<td><a href="#">zclfwgs</a></td>
					<td>周立波</td>
					<td>13910223096 / 88016732</td>
					<td align="center"><span class="label rounded-2x label-u">正常</span></td>
				</tr>
				<tr>
					<td align="center"><input type="checkbox"/></td>
					<td><a href="#">zclfwgs</a></td>
					<td>赵四</td>
					<td>- / 88016927</td>
					<td align="center"><span class="label rounded-2x label-u">正常</span></td>
				</tr>
				<tr>
					<td align="center"><input type="checkbox"/></td>
					<td><a href="#">zclfwgs</a></td>
					<td>测试</td>
					<td>- / -</td>
					<td align="center"><span class="label rounded-2x label-dark">已冻结</span></td>
				</tr>
				<tr>
					<td align="center"><input type="checkbox"/></td>
					<td><a href="#">zclfwgs</a></td>
					<td>夏雨</td>
					<td>- / -</td>
					<td align="center"><span class="label rounded-2x label-u">正常</span></td>
				</tr>
				<tr>
					<td align="center"><input type="checkbox"/></td>
					<td><a href="#">zclfwgs</a></td>
					<td>宋彪伟</td>
					<td>- / 68776617</td>
					<td align="center"><span class="label rounded-2x label-u">正常</span></td>
				</tr>
				<tr>
					<td align="center"><input type="checkbox"/></td>
					<td><a href="#">zclfwgs</a></td>
					<td>王刚</td>
					<td>- / -</td>
					<td align="center"><span class="label rounded-2x label-u">正常</span></td>
				</tr>
				<tr>
					<td align="center"><input type="checkbox"/></td>
					<td><a href="#">zclfwgs</a></td>
					<td>李伟</td>
					<td>- / -</td>
					<td align="center"><span class="label rounded-2x label-u">正常</span></td>
				</tr>
				<tr>
					<td align="center"><input type="checkbox"/></td>
					<td><a href="#">zclfwgs</a></td>
					<td>1111</td>
					<td>1 / 1</td>
					<td align="center"><span class="label rounded-2x label-u">正常</span></td>
				</tr>
			</tbody>
		</table>
	</div>
   </div>
  </div>
  <div class="tab-pane fade in" id="dep_tab-1">
    <div class="content-boxes-v2 space-lg-hor content-sm ">
     <h2 class="heading-sm">
       <i class="icon-custom icon-sm icon-bg-red fa fa-lightbulb-o"></i>
       <span>抱歉，没有找到相关信息。</span>
     </h2>
    </div>
    </div>
	<div class="tab-pane fade in" id="dep_tab-2">
	 <ul class="timeline-v2">
      <li>
        <time class="cbp_tmtime" datetime=""><span>14:27:16</span> <span>2016-08-01</span></time>
        <div class="cbp_tmlabel">
          <h4>修改数据 </h4>
          <div class="margin-bottom-10"><div class="headline"><h3 class="heading-sm">修改详细信息</h3></div><table class="table table-bordered mb0"><thead><tr><th>参数名称</th><th>修改前</th><th>修改后</th></tr></thead><tbody><tr><td>详细地址</td><td>北京市西四环中路16号院8号楼（金沟河桥东南角）</td><td>北京市西四环中路16号院8号楼</td></tr></tbody></table></div>
          <p>状态:<span class="label rounded-2x label-u">正常</span>&nbsp;&nbsp;姓名:李伟&nbsp;&nbsp;ID:151234&nbsp;&nbsp;单位:xxxxx有限公司&nbsp;&nbsp;IP地址:61.135.234.125|北京市</p>
        </div>
      </li>
      <li>
        <time class="cbp_tmtime" datetime=""><span>14:27:16</span> <span>2016-08-01</span></time>
        <div class="cbp_tmlabel">
          <h4>修改数据 </h4>
          <div class="margin-bottom-10"><div class="headline"><h3 class="heading-sm">修改详细信息</h3></div><table class="table table-bordered mb0"><thead><tr><th>参数名称</th><th>修改前</th><th>修改后</th></tr></thead><tbody><tr><td>详细地址</td><td>北京市西四环中路16号院8号楼（金沟河桥东南角）</td><td>北京市西四环中路16号院8号楼</td></tr></tbody></table></div>
          <p>状态:<span class="label rounded-2x label-u">正常</span>&nbsp;&nbsp;姓名:李伟&nbsp;&nbsp;ID:151234&nbsp;&nbsp;单位:xxxxx有限公司&nbsp;&nbsp;IP地址:61.135.234.125|北京市</p>
        </div>
      </li>
      <li>
        <time class="cbp_tmtime" datetime=""><span>14:27:16</span> <span>2016-08-01</span></time>
        <div class="cbp_tmlabel">
          <h4>修改数据 </h4>
          <div class="margin-bottom-10"><div class="headline"><h3 class="heading-sm">修改详细信息</h3></div><table class="table table-bordered mb0"><thead><tr><th>参数名称</th><th>修改前</th><th>修改后</th></tr></thead><tbody><tr><td>详细地址</td><td>北京市西四环中路16号院8号楼（金沟河桥东南角）</td><td>北京市西四环中路16号院8号楼</td></tr></tbody></table></div>
          <p>状态:<span class="label rounded-2x label-u">正常</span>&nbsp;&nbsp;姓名:李伟&nbsp;&nbsp;ID:151234&nbsp;&nbsp;单位:xxxxx有限公司&nbsp;&nbsp;IP地址:61.135.234.125|北京市</p>
        </div>
      </li>
      <li>
        <time class="cbp_tmtime" datetime=""><span>14:41:56</span> <span>2013-04-18</span></time>
        <div class="cbp_tmlabel">
          <h4>修改采购单位信息</h4>
          <p>状态:<span class="label rounded-2x label-u">正常</span>&nbsp;&nbsp;姓名:李伟&nbsp;&nbsp;ID:146393&nbsp;&nbsp;单位:xxxx有限公司&nbsp;&nbsp;IP地址:172.16.25.117|局域网</p>
        </div>
      </li>


      </ul>
     </div>
    </div>
   </div>
  </div>
 </div>
</div>
</div>
</div>
</div>
 </div><!--/container-->


</body>
</html>
