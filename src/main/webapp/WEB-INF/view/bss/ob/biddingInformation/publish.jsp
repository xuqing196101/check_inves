<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
	<title>发布竞价信息页面</title>
<script type="text/javascript">
	
	/** 全选全不选 */
	function selectAll(){
		 var checklist = document.getElementsByName ("chkItem");
		 var checkAll = document.getElementById("checkAll");
		   if(checkAll.checked){
			   for(var i=0;i<checklist.length;i++)
			   {
			      checklist[i].checked = true;
			   } 
			 }else{
			  for(var j=0;j<checklist.length;j++)
			  {
			     checklist[j].checked = false;
			  }
		 	}
		}
	
	/** 单选 */
	function check(){
		 var count=0;
		 var checklist = document.getElementsByName ("chkItem");
		 var checkAll = document.getElementById("checkAll");
		 for(var i=0;i<checklist.length;i++){
			   if(checklist[i].checked == false){
				   checkAll.checked = false;
				   break;
			   }
			   for(var j=0;j<checklist.length;j++){
					 if(checklist[j].checked == true){
						   checkAll.checked = true;
						   count++;
					   }
				 }
		   }
	}
</script>
</head>
<body>
<!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)"> 首页</a></li><li><a href="javascript:void(0)">保障作业</a></li><li><a href="javascript:void(0)">定型产品竞价</a></li>
		   <li class="active"><a href="javascript:void(0)">竞价信息管理</a></li><li class="active"><a href="javascript:void(0)">发布竞价信息</a></li>
		   </ul>
        <div class="clear"></div>
      </div>
    </div>
    
    <!-- 修改订列表开始-->
  <form action="" method="post" class="mb0">
   <div class="wrapper mt10">
  <div class="container">
     <h2 class="count_flow"><i>1</i>竞价基本信息</h2>
  <div class="mt10"></div> 
  <table class="table table-bordered mt10">
		<tbody>
		  <tr>
			<td class="bggrey tr">竞价标题：</td>
			<td><input id="" name="" value="" type="text" class="w230 mb0"></td>
			<td class="bggrey tr">交货截止时间：</td>
			<td><input id="" name="" value="" type="text" class="w230 mb0"></td>
		  </tr>
		  <tr>
			<td class="bggrey tr">交货地点：</td>
			<td><input id="" name="" value="" type="text" class="w230 mb0"></td>
			<td class="bggrey tr">成交供应商数：</td>
			<td><input id="" name="" value="" type="text" class="w230 mb0"></td>
		  </tr>
		  <tr>
			<td class="bggrey tr">运杂费：</td>
			<td><input id="" name="" value="" type="text" class="w230 mb0"></td>
			<td class="bggrey tr">需求单位：</td>
			<td><input id="" name="" value="" type="text" class="w230 mb0"></td>
		  </tr>
		  <tr>
			<td class="bggrey tr">联系人：</td>
			<td><input id="" name="" value="" type="text" class="w230 mb0"></td>
			<td class="bggrey tr">联系电话：</td>
			<td><input id="" name="" value="" type="text" class="w230 mb0"></td>
		  </tr>
		  <tr>
			<td class="bggrey tr">采购机构：</td>
			<td><input id="" name="" value="" type="text" class="w230 mb0"></td>
			<td class="bggrey tr">采购联系人：</td>
			<td><input id="" name="" value="" type="text" class="w230 mb0"></td>
		  </tr>
		  <tr>
			<td class="bggrey tr">联系电话：</td>
			<td><input id="" name="" value="" type="text" class="w230 mb0"></td>
			<td class="bggrey tr">竞价开始时间：</td>
			<td><input id="" name="" value="" type="text" class="w230 mb0"></td>
		  </tr>
		  <tr>
			<td class="bggrey tr">竞价结束时间：</td>
			<td colspan="3"><input id="" name="" value="" type="text" class="w230 mb0"></td>
		  </tr>
		  <tr>
			<td class="bggrey tr">竞价内容：</td>
			<td colspan="3">
		   		<div class="col-md-12 col-sm-12 col-xs-12 p0">
  					<textarea class="col-md-12 col-sm-12 col-xs-12" style="height:130px"></textarea>
 				</div>
			 </td>
		  </tr>
		  <tr>
			<td class="bggrey tr">竞价文件：</td>
			<td><input id="" name="" value="" type="text" class="w230 mb0"></td>
			<td class="tc"><button class="btn">浏览</button></td>
		  </tr>
		 </tbody>
	 </table>
	</div>
	<div class="container">
	<h2 class="count_flow"><i>2</i>产品信息</h2>
	<div class="mt10"></div> 
  <div class="col-md-12 pl20 mt10">
		<button class="btn btn-windows add" type="submit">添加</button>
		<button class="btn btn-windows delete" type="submit">删除</button>
		<button class="btn btn-windows input" type="submit">导入EXCEL</button>
	</div>   
	<div class="content table_box">
    	<table class="table table-bordered table-condensed">
		<thead>
		<tr>
		  <th class="w50 info"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
		  <th class="info">定型产品名称</th>
		  <th class="info">限价（元）</th>
		  <th class="info">采购数量</th>
		  <th class="info">备注</th>
		</tr>
		</thead>
		<tr>
		  <td class="tc w30"><input onclick="check()" type="checkbox" name="chkItem" value="" /></td>
		  <td><input id="" name="" value="台式计算机" type="text" class="w230 mb0"></td>
		  <td><input id="" name="" value="1000" type="text" class="w230 mb0"></td>
		  <td><input id="" name="" value="2" type="text" class="w230 mb0"></td>
		  <td><input id="" name="" value="CPU :AD300 内存：2G 硬盘：200G" type="text" class="w230 mb0"></td>
		</tr>
		<tr>
		  <td class="tc w30"><input onclick="check()" type="checkbox" name="chkItem" value="" /></td>
		  <td><input id="" name="" value="便携式计算机" type="text" class="w230 mb0"></td>
		  <td><input id="" name="" value="3000" type="text" class="w230 mb0"></td>
		  <td><input id="" name="" value="5" type="text" class="w230 mb0"></td>
		  <td><input id="" name="" value="CPU :AD300 内存：2G 硬盘：200G" type="text" class="w230 mb0"></td>
		</tr>
		<tr>
		  <td class="tc w30"><input onclick="check()" type="checkbox" name="chkItem" value="" /></td>
		  <td><input id="" name="" value="服务器" type="text" class="w230 mb0"></td>
		  <td><input id="" name="" value="4000" type="text" class="w230 mb0"></td>
		  <td><input id="" name="" value="5" type="text" class="w230 mb0"></td>
		  <td><input id="" name="" value="CPU :AD300 内存：2G 硬盘：200G" type="text" class="w230 mb0"></td>
		</tr>
	</table>
   </div>
   <h2 class="tc">温馨提示：能够提供当前产品的供应商数量为5家</h2>
	<div class="col-md-12 clear tc mt10">
	<button class="btn btn-windows save mb20" type="submit">暂存</button>
	<button class="btn btn-windows apply mb20" type="submit">发布</button>
   </div>
	</div>
  </div>
  </form>
</body>
</html>