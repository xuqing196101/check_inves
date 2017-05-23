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
		   <li class="active"><a href="javascript:void(0);">页面样式列表</a></li><li class="active"><a href="javascript:void(0);">上下结构表单页面</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
<!-- 修改订列表开始-->
   <div class="container container_box">
   <div class="mt10">
	    <button class="btn btn-windows back" type="button" onclick="history.go(-1)">返回</button>
   </div>
   <form>
   <div>
    <h2 class="count_flow"><i>1</i>修改订单</h2>
   <ul class="ul_list">
     <li class="col-md-3 col-sm-6 col-xs-12 pl15">
	   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">采购单位</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li>
     <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">发票抬头</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li>
     <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">采购单位联系人</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">采购单位联系人座机</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">采购单位联系人手机</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">采购单位地址</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 ">供应商名称</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li>  
     <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 ">供应商单位联系人</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">供应商单位联系人座机</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li>  
     <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">供应商单位联系人手机 </span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">供应商单位地址 </span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">交付日期</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="Wdate w230" id="appendedInput" type="text">
       </div>
	 </li> 
     <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">预算金额（元）</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">发票编号</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li>
	 <li class="col-md-3 col-sm-6 col-xs-12">
	 	<span class="zzzx col-md-12 col-sm-12 col-xs-12 padding-left-5">营业执照（三证合一）：</span>
     	<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
          <a href="#" class="upload"><i></i>上传附件</a>
        </div>
	 </li>
	 <li class="col-md-3 col-sm-6 col-xs-12">
	 	<div class="col-md-12 col-sm-12 col-xs-12 padding-left-5">文件下载：<span class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">供应商注册须知</span><a href="#" class="download"></a></div>
	 </li>
     <li class="col-md-12 col-sm-12 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">备注</span>
	   <div class="col-md-12 col-sm-12 col-xs-12 p0">
        <textarea class="col-md-12 col-sm-12 col-xs-12" style="height:130px" title="不超过800个字"></textarea>
       </div>
	 </li> 

   </ul>
  </div> 
   
  <!-- 产品明细开始-->
  <div class="padding-top-10 clear">
    <h2 class="count_flow"><i>2</i>产品明细</h2>
   <ul class="ul_list">
     <li class="col-md-3 col-sm-6 col-xs-12 pl15">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">品目</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" type="text">
       </div>
	 </li> 
     <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">品牌</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li>  
     <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">型号</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">版本号</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li>  
     <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">市场单价（元）</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">成交单价（元）</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">数量</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">单位</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">小计（元）</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">下拉框</span>
	   <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
        <select>
          <option>选项一</option>
          <option>选项二</option>
        </select>
       </div>
	 </li> 
     <li class="col-md-3 col-sm-6 col-xs-12">
     <div class="select_check">
	   <input type="checkbox">多选
	 </div>
	 </li> 
     <li class="col-md-3 col-sm-6 col-xs-12">
     <div class="select_check">
	   <input type="radio">单选
	 </div>
	 </li>
     <li class="col-md-12 col-sm-12 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">备注</span>
	   <div class="col-md-12 col-sm-12 col-xs-12 p0">
        <textarea class="col-md-12 col-sm-12 col-xs-12" style="height:130px" title="不超过800个字"></textarea>
       </div>
	 </li> 
	 </ul>
	 <ul class="ul_list padding-left-20">
     <li class="col-md-4 col-sm-6 col-xs-12 pl15">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">运费（元）</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-4 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">其他费用（元）</span>
	   <div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-4 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">其他费用说明</span>
	   <div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
   </ul>
    <div class="clear total f22"><span class="fl block">总计：</span><span>¥10000</span></div>
    <h2 class="count_flow"><i>3</i>修改订单</h2>
  </div>
  <div class="ul_list">
  <div class="clear" >
   <ul class="list-unstyled  bg8 padding-20">
    <li>1 . 仅支持jpg、jpeg、png、pdf等格式的文件;</li>
	<li>2 . 单个文件大小不能超过1M;</li>
	<li>3 . 上传文件的数量不超过10个;</li>
   </ul>
  </div>
  
  <div  class="col-md-12 col-sm-12 col-xs-12 p0">
   <div class="fl">
    <button class="btn btn-windows add" type="submit">新增</button>
	<button class="btn btn-windows delete" type="submit">删除</button>
	<button class="btn btn-windows save" type="submit">保存</button>
	<button class="btn btn-windows reset" type="submit">重置</button>
	</div>
	<div class="fr select_check">
	  <input type="checkbox"/>
	  <span>选中全部文件</span>
	</div>
  </div>
  </form>
  <div class="col-md-12 col-sm-12 col-xs-12 clear mt5 p0">
   <ul class="list-unstyled bgdd">
	<li> 
    <div class="col-md-4 col-sm-6 col-xs-12 padding-10 fl">
	 <div class="col-md-3 col-sm-3 col-xs-2 tc h60 fl"><input type="checkbox"/></div>
	 <div class="col-md-9 col-sm-9 col-xs-10 padding-0 fl">
	   <div class="fl suolue"> 
        <a href="#" class="thumbnail mb0 suolue">
         <img src="${pageContext.request.contextPath}/public/backend/images/suolue.jpg" class="suolue"/>
        </a>
	   </div>
	 </div>
	</div>
	<div class="col-md-8 col-sm-6 col-xs-12 padding-10 h60 fl">
	 <div class="col-md-9 col-sm-6 col-xs-12 fl">1oa－1000乘370.jpg</div>
	 <div class="col-md-3 col-sm-6 col-xs-12 fl">614.82KB</div>
	</div>
	<div class="clear"></div>
  </li>
  <li> 
    <div class="col-md-4 col-sm-6 col-xs-12 padding-10 fl">
	 <div class="col-md-3 col-sm-3 col-xs-2 tc h60 fl"><input type="checkbox"/></div>
	 <div class="col-md-9 col-sm-9 col-xs-10 padding-0 fl">
	   <div class="fl suolue"> 
        <a href="#" class="thumbnail mb0 suolue">
         <img src="${pageContext.request.contextPath}/public/backend/images/suolue.jpg" class="suolue"/>
        </a>
	   </div>
	 </div>
	</div>
	<div class="col-md-8 col-sm-6 col-xs-12 padding-10 h60 fl">
	 <div class="col-md-9 col-sm-6 col-xs-12 fl">1oa－1000乘370.jpg</div>
	 <div class="col-md-3 col-sm-6 col-xs-12 fl">614.82KB</div>
	</div>
	<div class="clear"></div>
  </li>
  </ul>
  </div>
 </div>
</body>
</html>
