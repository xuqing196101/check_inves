<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
		<link href="${pageContext.request.contextPath }/public/select2/css/select2.css" rel="stylesheet" />
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
	//选择采购机构
	function getMechanism(){
	       $.ajax({
                type: "POST",
                url: "${pageContext.request.contextPath}/ob_project/mechanism.html",
				dataType: "json", //返回格式为json
                data:{"currFlowDefineId":nextFlowDefineId ,"currUpdateUserId":nextUpdateUserId, "projectId":projectId},
                success: function(data) {
                    if(data.success){
                    	layer.msg(data.flowDefineName+ "经办人设置成功",{offset: '100px'});
                    }
                },
                error: function(data){
                    layer.msg("请稍后再试",{offset: '100px'});
                }
            });
	}
	var list=null;
	//加载采购机构 下拉数据
	$(function(){
		$.ajax({
			url: "${pageContext.request.contextPath }/ob_project/mechanism.html",
			contentType: "application/json;charset=UTF-8",
			dataType: "json", //返回格式为json
			type: "POST", //请求方式           
			success: function(data) {
				if (data) {
				list=data;
					$.each(data, function(i, user) {
						$("#principal").append("<option  value=" + user.id + ">" + user.name + "</option>");
					});
				} 
			 $("#principal").select2();
			}
		});
		
	}); 
	//根据下拉框信息改变 采购联系人 采购联系电话
	function changSelect(){
	   if(list){
	  	var value=  $("#principal").val();
	  	$.each(list, function(i, user) {
	    	$("#contact_tel").val(user.contactMobile);
	    	 $("#contact_name").val(user.contactName);
	 	});
	  }
	}
	function addTr(){
	$("#table2").append("<tr><td class=\"tc w30\"><input onclick=\"check()\" type=\"checkbox\" name=\"chkItem\" /></td>"+
	"<td class=\"p0\"><input id=\"\" name=\"\" value=\"台式计算机\" type=\"text\" class=\"w230 mb0\"></td>"+
	"<td class=\"p0\"><input id=\"\" name=\"\" value=\"1000\" type=\"text\" class=\"w230 mb0\"></td>"+
	 "<td class=\"p0\"><input id=\"\" name=\"\" value=\"2\" type=\"text\" class=\"w230 mb0\"></td>"+
	  "<td class=\"p0\"><input id=\"\" name=\"\" value=\"CPU :AD300 内存：2G 硬盘：200G\" type=\"text\" class=\"w230 mb0\"></td></tr>");
	
	}
</script>
</head>
<body>
<!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)"> 首页</a></li><li><a href="javascript:void(0)">保障作业</a></li><li><a href="javascript:void(0)">网上竞价</a></li>
		   <li class="active"><a href="javascript:void(0)">竞价信息管理</a></li><li class="active"><a href="javascript:void(0)">发布竞价信息</a></li>
		   </ul>
        <div class="clear"></div>
      </div>
    </div>
    <div class="tab-content">
    
    <!-- 修改订列表开始-->
  <form action="" method="post" class="mb0">
   <div class="wrapper mt10">
  <div class="container">
     <h2 class="count_flow"><i>1</i>竞价基本信息</h2>
     <ul class="ul_list">
  <table class="table table-bordered left_table">
		<tbody>
		  <tr>
			<td class="bggrey tr"><span class="red star_red">*</span>竞价标题：</td>
			<td class="p0"><input id="" name="" value="" type="text" class="w230 mb0"></td>
			<td class="bggrey tr"><span class="red star_red">*</span>交货截止时间：</td>
			<td class="p0"><input value="<fmt:formatDate type='date' value='${project.deadline }'  pattern=" yyyy-MM-dd HH:mm:ss "/>"
			 name="deadline" id="deadline" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" onfocus="getValue()" class="Wdate" /></td>
		  </tr>
		  <tr>
			<td class="bggrey tr"><span class="red star_red">*</span>交货地点：</td>
			<td class="p0"><input id="" name="" value="" type="text" class="w230 mb0"></td>
			<td class="bggrey tr"><span class="red star_red">*</span>成交供应商数：</td>
			<td class="p0"><input id="" name="" value="" type="text" class="w230 mb0"></td>
		  </tr>
		  <tr>
			<td class="bggrey tr"><span class="red star_red">*</span>运杂费：</td>
			<td class="p0"><input id="" name="" value="" type="text" class="w230 mb0"></td>
			<td class="bggrey tr"><span class="red star_red">*</span>需求单位：</td>
			<td class="p0"><input id="" name="" value="" type="text" class="w230 mb0"></td>
		  </tr>
		  <tr>
			<td class="bggrey tr"><span class="red star_red">*</span>联系人：</td>
			<td class="p0"><input id="" name="" value="" type="text" class="w230 mb0"></td>
			<td class="bggrey tr"><span class="red star_red">*</span>联系电话：</td>
			<td class="p0"><input id="" name="" value="" type="text" class="w230 mb0"></td>
		  </tr>
		  <tr>
			<td class="bggrey tr"><span class="red star_red">*</span>采购机构：</td>
			<td class="p0"><div class="w200">
			<select id="principal" name="principal" onchange="changSelect()" >
			  <option value=""></option>
			</select></div></td>
			<td class="bggrey tr"><span class="red star_red">*</span>采购联系电话：</td>
			<td class="p0"><input id="contact_tel" name="contact_tel" disabled="disabled" type="text" class="w230 mb0"></td>
		  </tr>
		   <tr>
			<td class="bggrey tr"><span class="red star_red">*</span>采购联系人：</td>
			<td class="p0" colspan="3" ><input id="contact_name" disabled="disabled" name="contact_name"  type="text" class="w230 mb0"></td>
		  </tr>
		  <tr>
			<td class="bggrey tr"><span class="red star_red">*</span>竞价开始时间：</td>
			<td class="p0"><input value="<fmt:formatDate type='date' value='${project.deadline }'  pattern=" yyyy-MM-dd HH:mm:ss "/>" name="deadline" id="deadline" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" onfocus="getValue()" class="Wdate" /></td>
		  <td class="bggrey tr"><span class="red star_red">*</span>竞价结束时间：</td>
			<td class="p0"><input id="" name="" value="" disabled="disabled" type="text" class="w230 mb0"></td>
		  </tr>
		 
		  <tr>
			<td class="bggrey tr"><span class="red star_red">*</span>竞价内容：</td>
			<td colspan="3" class="p0">
		   		<div class="col-md-12 col-sm-12 col-xs-12 p0">
  					<textarea class="col-md-12 col-sm-12 col-xs-12" style="height:130px"></textarea>
 				</div>
			 </td>
		  </tr>
		  <tr>
			<td class="bggrey tr">竞价文件：</td>
			<td colspan="3" class="p0">
			<div>
                <u:upload id="a" buttonName="上传文档"  businessId="${userId}" sysKey="${sysKey}" typeId="${typeId }" multiple="true" auto="true" />
                <u:show showId="b" groups="b,c,d"  businessId="${userId}" sysKey="${sysKey}" typeId="${typeId }" />
              </div>
			
			</td>
		  </tr>
		 </tbody>
	 </table>
	 </ul>
	</div>
	<div class="container">
	<h2 class="count_flow"><i>2</i>产品信息</h2>
	<div class="mt10"></div> 
	 <ul class="ul_list">
  <div class="col-md-12 pl20 mt10">
		<input type="button" class="btn btn-windows add" onclick="addTr()" value="添加">
		<input type="button"  class="btn btn-windows delete" value="删除">
		<input type="button"  class="btn btn-windows output" value="下载EXCEL模板">
		<input type="button"  class="btn btn-windows input" value="导入EXCEL">
	</div>   
	<div class="content table_box">
    	<table class="table table-bordered left_table" id ="table2">
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
		  <td class="tc w30"><input onclick="check()" type="checkbox" name="chkItem" value="product_id" /></td>
		  <td class="p0"><input id="product_name" name="product_name" value="台式计算机" type="text" class="w230 mb0"></td>
		  <td class="p0"><input id="product_money" name="product_money" value="1000" type="text" class="w230 mb0"></td>
		  <td class="p0"><input id="product_count" name="product_count" value="2" type="text" class="w230 mb0"></td>
		  <td class="p0"><input id="product_remark" name="product_remark" value="CPU :AD300 内存：2G 硬盘：200G" type="text" class="w230 mb0"></td>
		</tr>
	</table>
   </div>
   <h2 class="tc">温馨提示：能够提供当前产品的供应商数量为5家</h2>
	<div class="col-md-12 clear tc mt10">
	<button class="btn btn-windows save mb20" type="submit">暂存</button>
	<button class="btn btn-windows apply mb20" type="submit">发布</button>
   </div>
   </ul>
	</div>
  </div>
  </form>
  </div>
</body>
</html>