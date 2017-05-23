<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
	<jsp:include page="backend_common.jsp"></jsp:include>	
</head>
<body>

<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0);"> 首页</a></li><li><a href="javascript:void(0);">支撑环境</a></li><li><a href="javascript:void(0);">后台管理</a></li>
		   <li class="active"><a href="javascript:void(0);">页面样式列表</a></li><li class="active"><a href="javascript:void(0);">详情页面</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   
<!--查看订单流程开始-->
  <div class="container clear mt20">
   <div class="mt10">
	    <button class="btn btn-windows back" type="button" onclick="history.go(-1)">返回</button>
   </div>
   <div class="padding-10 breadcrumbs-v3 mt10">
    <span>
	  <a href="#" class="img-v1 green_link">下单</a>
	  <span class="green_link">→</span>
	</span>
	<span>
	  <a href="#" class="img-v2 orange_link">卖方确认</a>
	  <span class="">→</span>
	</span>
	<span>
	  <a href="#" class="img-v3">买方确认</a>
	  <span class="">→</span>
	</span>
    <span>
	  <a href="#" class="img-v4">评价</a>
	  <span class="">→</span>
	</span>
    <span>
	  <a href="#" class="img-v5">完成</a>
	</span>
   </div>
  </div>
  
  
<!-- 项目戳开始 -->
  <div class="container clear mt20">
   <h2 class="padding-10 p0">
	 <span class="font_sblck">2015-06-20</span><span class="margin-left-10 font_sblck">台式机采购项目</span><div class="btn bround14 margin-left-20">选定成交人</div>
   </h2>
  </div>
  
  
<!--详情开始-->
<div class="container content pt0">
 <div class="row magazine-page">
   <div class="col-md-12 tab-v2">
        <div class="padding-top-10">
          <ul class="nav nav-tabs bgwhite">
            <li class="active"><a aria-expanded="true" href="#tab-1" data-toggle="tab" class="s_news f18">详细信息</a></li>
            <li class=""><a aria-expanded="false" href="#tab-2" data-toggle="tab" class="fujian f18">附件</a></li>
			<li class=""><a aria-expanded="false" href="#tab-3" data-toggle="tab" class="record f18">历史纪录</a></li>
          </ul>
          <div class="tab-content padding-top-20">
            <div class="tab-pane fade active in" id="tab-1">
                <h2 class="count_flow jbxx">基本信息</h2>
				<table class="table table-bordered">
				 <tbody>
				 <tr>
				  <td class="bggrey">采购单位：</td>
				  <td>****管理总公司</td>
				  <td class="bggrey ">发票抬头：</td>
				  <td>****管理总公司</td>
				  <td class="bggrey ">采购单位联系人：</td>
				  <td>系统管理员</td>
				 </tr> 
				 <tr>
				  <td class="bggrey ">采购单位联系人座机：</td>
				  <td> －</td>
				  <td class="bggrey ">采购单位联系人手机：</td>
				  <td>－</td>
				  <td class="bggrey ">采购单位地址：</td>
				  <td>北京市西城区西直门外大街甲</td>
				 </tr> 
				 <tr>
				  <td class="bggrey ">供应商名称：</td>
				  <td>****有限公司</td>
				  <td class="bggrey ">供应商单位联系人：</td>
				  <td>联系人</td>
				  <td class="bggrey ">供应商单位联系人座机：</td>
				  <td>56229828</td>
				 </tr> 
				 <tr>
				  <td class="bggrey ">供应商单位联系人手机：</td>
				  <td>13011111111</td>
				  <td class="bggrey ">供应商单位地址：</td>
				  <td>****有限公司</td>
				  <td class="bggrey ">交付日期：</td>
				  <td>  2016-06-30 </td>
				 </tr> 
				 <tr>
				  <td class="bggrey ">预算金额：</td>
				  <td> 10000.0</td>
				  <td class="bggrey ">发票编号：</td>
				  <td></td>
				  <td class="bggrey "></td>
				  <td></td>
				 </tr> 
				 <tr>
				  <td class="bggrey ">备注：</td>
				  <td colspan="5"></td>
				 </tr> 
				 
				</tbody>
			   </table>
                <h2 class="count_flow jbxx">详情页两栏表格</h2>
				<table class="table table-bordered">
				 <tbody>
				 <tr>
				  <td class="bggrey ">供应商名称：</td>
				  <td>****有限公司</td>
				  <td class="bggrey ">供应商单位联系人：</td>
				  <td>联系人</td>
				 </tr> 
				 <tr>
				  <td class="bggrey ">供应商单位联系人手机：</td>
				  <td>13011111111</td>
				  <td class="bggrey ">供应商单位地址：</td>
				  <td>****有限公司</td>
				 </tr>  
				 <tr>
				  <td class="bggrey ">供应商单位联系人手机：</td>
				  <td>13011111111</td>
				  <td class="bggrey ">供应商单位地址：</td>
				  <td>****有限公司</td>
				 </tr> 	 
				</tbody>
			   </table>
                <h2 class="count_flow jbxx">产品明细</h2>
				<table class="table table-bordered">
				 <tbody>
				 <tr>
				  <td class="bggrey">品目：</td>
				  <td>台式机</td>
				  <td class="bggrey">品牌：</td>
				  <td>123</td>
				  <td class="bggrey">型号：</td>
				  <td>123</td>
				  <td class="bggrey">版本号：</td>
				  <td>123</td>
				 </tr> 
				 <tr>
				  <td class="bggrey">市场单价（元）：</td>
				  <td>1200.0</td>
				  <td class="bggrey">成交单价（元）：</td>
				  <td>1000.0</td>
				  <td class="bggrey">数量：</td>
				  <td>1000.0</td>
				  <td class="bggrey">单位：</td>
				  <td>123</td>
				 </tr> 
				 <tr>
				  <td class="bggrey">小计（元）：</td>
				  <td>10000.0</td>
				  <td class="bggrey">供应商单位联系人：</td>
				  <td>联系人</td>
				  <td class="bggrey">供应商单位联系人座机：</td>
				  <td>56229828</td>
				  <td class="bggrey"></td>
				  <td></td>
				 </tr> 
				 <tr>
				  <td class="bggrey">备注：</td>
				  <td colspan="7"></td>
				 </tr> 
				</tbody>
			   </table>
              <h2 class=""><span>总计：</span>￥1000.0</h2>
            </div>
            <div class="tab-pane fade " id="tab-2">
              <div class="margin-bottom-0  categories">

              </div>
            </div>
			
            <div class="tab-pane fade " id="tab-3" style="position：relative">
              <div class=" margin-bottom-0">
                <div class="tml_container padding-top-0">
				  <div class="dingwei">
				  <div class="tml_spine">
					<span class="tml_spine_bg"></span>
					<span id="timeline_start_point" class="start_point"></span>
				  </div>
				  <div class="tml_poster" id="post_area" ><div class="poster" id="poster_1">
                   <div class=" margin-bottom-0">
                       <h2 class="history_icon">分公司审核</h2>
				        <div class="padding-left-40">
				 		  <span>确认并结束审核流程，理由是：同意采购。</span>
						   <ul>
						   <li class="margin-left-0">状态：<span>暂存</span></li>
						   <li>姓名：<span>张洋</span></li>
						   <li>ID：<span>152260</span></li>
						   <li>单位：<span>军队采购网</span></li>
						   <li>IP地址：<span>124.65.26.100｜北京市</span></li>
						   </ul>
					    </div>
                     </div>
				  </div>
				  <div class="period_header"><span>11:17:41 2015-11-18</span></div>
				  <span class="ui_left_arrow">
				    <span class="ui_arrow"></span>
				  </span>
				  <div class="clear"></div>
				 </div>
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
				  <div class="tml_poster" id="post_area" style=""><div class="poster" id="poster_1">
                   <div class=" margin-bottom-0">
                       <h2 class="f16 history_icon">选择中标人</h2>
				        <div class="padding-left-40">
				 		  <span>选择中标人成功！请等待分公司审核。选择［****有限公司］为中标单位</span>
						   <ul class="list-unstyled margin-bottom-0">
						   <li class="fl margin-left-0">状态：<span>暂存</span></li>
						   <li class="fl">姓名：<span>张洋</span></li>
						   <li class="fl">ID：<span>152260</span></li>
						   <li class="fl">单位：<span>军队采购网</span></li>
						   <li class="">IP地址：<span>124.65.26.100｜北京市</span></li>
						   </ul>
					    </div>
                   </div>
				  </div>
				  <div class="period_header"><span>11:17:41 2015-11-18</span></div>
				  <span class="ui_left_arrow">
				    <span class="ui_arrow"></span>
				  </span>
				  <div class="clear"></div>
				 </div>
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
                       <h2 class="f16 history_icon">报价</h2>
				        <div class="padding-left-40">
				 		  <span>［****有限公司］报价成功！</span>
						   <ul class="list-unstyled margin-bottom-0">
						   <li class="fl margin-left-0">状态：<span>暂存</span></li>
						   <li class="fl">姓名：<span>张洋</span></li>
						   <li class="fl">ID：<span>152260</span></li>
						   <li class="fl">单位：<span>军队采购网</span></li>
						   <li class="">IP地址：<span>124.65.26.100｜北京市</span></li>
						   </ul>
					    </div>
                     </div>
				  </div>
				  <div class="period_header"><span>11:17:41 2015-11-18</span></div>
				  <span class="ui_left_arrow">
				    <span class="ui_arrow"></span>
				  </span>
				  <div class="clear"></div>
				 </div>
                </div>
               </div>
			  </div>
          </div> 
		</div> 
     </div>
  </div>
</div>
</div>

</body>
</html>
