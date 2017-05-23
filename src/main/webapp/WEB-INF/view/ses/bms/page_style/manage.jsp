<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
	
	<jsp:include page="backend_common.jsp"></jsp:include>	
</head>

<body>
  
<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		<ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0);"> 首页</a></li><li><a href="javascript:void(0);">支撑环境</a></li><li><a href="javascript:void(0);">后台管理</a></li>
		   <li class="active"><a href="javascript:void(0);">页面样式列表</a></li><li class="active"><a href="javascript:void(0);">左右布局页面</a></li> 
		</ul>
	  </div>
   </div>
        <!--=== End Breadcrumbs ===-->

        <!--=== Content Part ===-->
        <div class="container content height-350">
        <div class="ml20 mt10">
	    	<button class="btn btn-windows back" type="button" onclick="history.go(-1)">返回</button>
        </div>
            <div class="row mt10">
                <!-- Begin Content -->
                <div class="col-md-12 col-sm-12 col-xs-12" style="min-height:400px;">
                     <div class="col-md-3 col-sm-12 col-xs-12 md-margin-bottom-40" id="show_tree_div">
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
<div class="tag-box tag-box-v4 col-md-9 col-sm-12 col-xs-12" id="show_content_div">
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
		<div class="overflow-h mb20" id="ztree_title">
		  <h2 class="panel-title heading-sm pull-left">
	        <i class="fa fa-bars"></i> xxxx有限公司 <span class="label rounded-2x label-u">正常</span>
          </h2>
          <div class="pull-right">
	        <a class="btn" href="javascript:void(0)" onClick=""><i class="fa fa-search-plus"></i> 详细</a> 
			<a class="btn" href="javascript:void(0)" onClick=""><i class="fa fa-wrench"></i> 修改</a> 
			<a class="btn" href="javascript:void(0)" onClick=""><i class="fa fa-plus"></i> 增加下属单位</a> 
			<a class="btn" data-toggle="modal" href=""><i class="fa fa-plus"></i> 增加人员</a>
          </div>
		 </div>
		 <div>
          <div class="tab-v2">
            <ul class="nav nav-tabs bgwhite">
              <li class="active"><a href="#dep_tab-0" data-toggle="tab" class="s_news f18">详细信息</a></li>
			  <li><a href="#dep_tab-1" data-toggle="tab" class="fujian f18">附件</a></li>
			  <li><a href="#dep_tab-2" data-toggle="tab" class="record f18">历史记录</a></li>
          </ul>
          <div class="tab-content">
            <div class="tab-pane fade in active" id="dep_tab-0">
			  <div class="show_obj">
			  <table class="table table-bordered">
			    <tbody>
				  <tr>
				    <td width="25%" class="bggrey tl">单位名称：</td>
				    <td width="25%">xxxx有限公司</td>
				    <td width="25%" class="bggrey tl">单位简称：</td>
				    <td width="25%">服务公司</td>
				  </tr>
				  <tr>
				    <td width="25%" class="bggrey tl">曾用名：</td>
				    <td width="25%">xxxx有限公司</td>
				    <td width="25%" class="bggrey tl">单位类型：</td>
				    <td width="25%">独立核算单位</td>
				  </tr>
				  <tr>
				    <td width="25%" class="bggrey tl">邮政编码：</td>
				    <td width="25%">100044</td>
				    <td width="25%" class="bggrey tl">所在地区：</td>
				    <td width="25%">北京</td></tr><tr>
				    <td width="25%" class="bggrey tl">详细地址：</td>
				    <td width="25%">北京市西四环中路343号院23号楼</td>
				    <td width="25%" class="bggrey tl">电话（总机）：</td>
				    <td width="25%">88016942</td>
				  </tr>
				  <tr>
				    <td width="25%" class="bggrey tl">传真：</td>
				    <td width="25%">-</td>
				    <td  width="25%" class="bggrey tl"></td>
				    <td width="25%"></td>
				  </tr>
				 </tbody>
			 </table>
		</div>
		  <div class="">
			<a class="btn btn-windows add" href="javascript:void(0)" onClick=""> 新增</a> 
			<a class="btn btn-windows edit" href="javascript:void(0)" onClick="">修改</a> 
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
					<td><a href="javascript:void(0);">zclfwgs</a></td>
					<td>张飒飒</td>
					<td>18610023457 / 88012322</td>

					<td align="center"><span class="label rounded-2x label-u">正常</span></td>
				</tr>
				<tr>
					<td align="center"><input type="checkbox"/></td>
					<td><a href="javascript:void(0);">zclfwgs</a></td>
					<td>测试</td>
					<td>- / -</td>
					<td align="center"><span class="label rounded-2x label-dark">已冻结</span></td>
				</tr>
				<tr>
					<td align="center"><input type="checkbox"/></td>
					<td><a href="javascript:void(0);">zclfwgs</a></td>
					<td>阳溯溯</td>
					<td>- / -</td>
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
       <span>抱歉，没有找到相关信息。</span>
     </h2>
    </div>
    </div>
	<div class="tab-pane fade in" id="dep_tab-2">
 <div class="tml_container">
				 <div class="dingwei">
				  <div class="tml_spine">
					<span class="tml_spine_bg"></span>
					
					<span id="timeline_start_point" class="start_point"></span>
				  </div>
				  <div class="tml_poster" id="post_area" style=""><div class="poster" id="poster_1">
                   <div class=" margin-bottom-0">
                       <h2 class="f16 history_icon">修改数据</h2>
				        <div class="padding-left-10">
					       <div class="cbp_tmlabel">
					          <div class="margin-bottom-10">
					            <div class="headline">
					              <h3 class="heading-sm">修改详细信息</h3>
					              <div class="f14 fr"><span class="mr5">2016-08-01</span><span>14:27:16</span></div>
					            </div>
					            <table class="table table-bordered mb0">
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
					          <div class="cbp_detail"><div class="mr15 fl">状态：<span class="label rounded-2x label-u">正常</span></div><span>姓名：李四</span><span>ID：154234</span><span>单位：xxxxx有限公司</span><span>IP地址：61.136.254.125|北京市</span></div>
					        </div>
					    </div>
                   </div>
				  </div>
				  <div class="period_header"><span>11:17:41 2015-11-18</span></div>
				  <span class="ui_left_arrow">
				    <span class="ui_arrow"></span>
				  </span>
				 </div>
                </div>
			   </div>
		  
			  
              <div class=" margin-bottom-0">
                <div class="tml_container">
				  <div class="dingwei">
				  <div class="tml_spine">
					<span class="tml_spine_bg"></span>
					
					<span id="timeline_start_point" class="start_point"></span>
				  </div>
				  <div class="tml_poster" id="post_area" ><div class="poster" id="poster_1">
                   <div class=" margin-bottom-0">
                       <h2 class="f16 history_icon">修改数据</h2>
						  <div class="padding-left-10">
					        <div class="cbp_tmlabel">
					          <div class="margin-bottom-10"><div class="headline">
					            <h3 class="heading-sm">修改详细信息</h3>
					            <div class="f14 fr"> <span class="mr5">2016-08-01</span><span>14:27:16</span></div>
					          </div>
					          <table class="table table-bordered mb0"><thead><tr><th>参数名称</th><th>修改前</th><th>修改后</th></tr></thead><tbody><tr><td>详细地址</td><td>北京市西四环中路156号院845号楼（金沟河桥东南角）</td><td>北京市西四环中路123号院34号楼</td></tr></tbody></table></div>
					          <div class="cbp_detail"><div class="mr15 fl">状态：<span class="label rounded-2x label-u">正常</span></div><span>姓名：张三</span><span>ID：151234</span><span>单位：xxxxx有限公司</span><span>IP地址：61.135.234.125|北京市</span></div>
					        </div>
					    </div>
                     </div>
				  </div>
				  <div class="period_header"><span>11:17:41 2015-11-18</span></div>
				  <span class="ui_left_arrow">
				    <span class="ui_arrow"></span>
				  </span>
				 </div>
                </div>
               </div>
			  </div>


</div>
 </div><!--/container-->


</body>
</html>
