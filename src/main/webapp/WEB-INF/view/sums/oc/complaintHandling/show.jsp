<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/view/common.jsp"%>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<title>投诉处理详细页面</title>
<script type="text/javascript">
     
     function openDiv(){
    	 layer.open({
			  type: 1,
			  title: '请注明缺失内容',
			  skin: 'layui-layer-rim',
			  shadeClose: true,
			  area: ['580px','510px'],
			  content: $("#openDiv")
			});
}
function cancel(){
	layer.closeAll();
     }
</script>
</head>
<body>
	<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a href="javascript:void(0)"> 首页</a></li>
				<li><a href="javascript:void(0)">业务监管</a></li>
				<li><a href="javascript:void(0)">网上投诉处理</a></li>
				<li class="active"><a href="javascript:void(0)">投诉处理</a></li>
				<li class="active"><a href="javascript:void(0)">投诉处理详细页</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>

	
	<div class="container">
		<div>
			<form action="" method="post" class="mb0">
				<ul class="ul_list">
					<li class="col-md-3 col-sm-6 col-xs-12  pl15">
					    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">投诉人类型</span>
						<div class="col-md-12 mb20 col-sm-12 col-xs-12 p0">
							<input class="" name="" type="text" value="">
						</div> 
				  </li>
				  <li class="col-md-3 col-sm-6 col-xs-12 pl15"  >
	                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">投诉人名称（姓名）</span>
	                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                             <input class="" name="PerSonName" type="text" value="">
                        </div>
	              </li>	
	              
				  <li class="col-md-3 col-sm-6 col-xs-12 pl15">
				  	<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">投诉对象</span>
				  	<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                             <input class="" name="PerSonName" type="text" value="">
                     </div>
				  </li>
				  <li class="col-md-12 col-sm-12 col-xs-12">
	                <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">投诉事项</span>
	                 <div class="col-md-12 col-sm-12 col-xs-12 p0">
                      <textarea class="col-md-12 col-sm-12 col-xs-12" style="height:130px" title="不超过800个字"></textarea>
                     </div>
	              </li> 
				  <li class="col-md-3 col-sm-6 col-xs-12">
	 	             <span class="zzzx col-md-12 col-sm-12 col-xs-12 padding-left-5">投诉文件</span>
     	              <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
     	                 <input type="file" id="file" value="" />
                      </div>
	             </li>
	             <li class="col-md-3 col-sm-6 col-xs-12" hidden="hidden" id = "idcad">
	 	             <span class="zzzx col-md-12 col-sm-12 col-xs-12 padding-left-5">身份证照片</span>
     	              <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
     	                 <input type="file" id="file" value="" />
                      </div>
	             </li>
	             <div>
	             
	              </div>
		         <div class="clear"></div>    
		        <div class="col-md-12 col-sm-12 col-xs-12 tc mt5">
		             <button class="btn" type="button" onclick="y();">立项</button>
			         <button class="btn" type="button" onclick="openDiv();">无法立项</button>
		        </div>
		        <div id="openDiv" class="dnone layui-layer-wrap" >
		  <div class="drop_window">
			  <ul class="list-unstyled">
			    
			    <li class="col-md-12">
	    	      <label class="col-md-12 padding-left-5">备注</label>
                  <textarea id="remarks" name="remarks" class="col-md-12 h80 p0" rows="3" cols="1"></textarea>
	            </li>
			    
	            <div class="clear"></div>
			  </ul>
              <div class="tc mt10 col-md-12">
                <input class="btn" id="inputb" name="addr" onclick="alert('自己实现')" value="确定" type="button"> 
				<input class="btn" id="inputa" name="addr" onclick="cancel();" value="取消" type="button"> 
              </div>
		    </div>
		   </div>
		</ul> 
		</form>
	</div>
	</div>
</body>
</html>