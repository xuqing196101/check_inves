<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
		<link href="${pageContext.request.contextPath }/public/select2/css/select2.css" rel="stylesheet" />
	    <script type="text/javascript" src="${pageContext.request.contextPath}/public/upload/ajaxfileupload.js"></script>
	<title>发布竞价信息页面</title>
<script type="text/javascript">
	
	/** 全选全不选 */
	function selectAll(){
		 var checklist = document.getElementsByName ("product_id");
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
		 var checklist = document.getElementsByName ("product_id");
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
	/*选择删除*/
	function del(){
	var ids =[];
	 $('input[name="product_id"]:checked').each(function(){ 
	 ids.push($(this).val());
	  }); 
	  if(ids.length>0){
			layer.confirm('您确定要删除吗?', {title:'提示',offset: '222px',shade:0.01}, function(index){
				layer.close(index);
				$('input[name="product_id"]:checked').each(function(){ 
		       $(this).parent().parent().remove(); 
		       });
			});
		}else{
			layer.alert("请选择",{offset: '222px', shade:0.01});
		}
    }
    //下载模板
    function down(){
     window.location.href ="${pageContext.request.contextPath}/ob_project/download.html";
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
	//定义采购集合
	var list=null;
	//定义产品集合
	var productList=null;
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
						$("#orgId").append("<option  value=" + user.id + ">" + user.name + "</option>");
					});
				} 
			 $("#orgId").select2();
			}
		});
		
		//加载产品相关信息 下拉数据
		  $.ajax({
			url: "${pageContext.request.contextPath }/ob_project/product.html",
			contentType: "application/json;charset=UTF-8",
			dataType: "json", //返回格式为json
			type: "POST", //请求方式           
			success: function(data) {
				if (data) {
				productList=data;
					$.each(data, function(i, user) {
						$("#product_name").append("<option  value=" + user.id + ">" + user.name + "</option>");
						//alert(user.obSupplierList.length);
						
					});
				} 
			 $("#product_name").select2();
			}
		 });
	});
	//根据下拉框信息改变 采购联系人 采购联系电话
	function changSelect(){
	   if(list){
	  	var value=  $("#orgId").val();
	  	$.each(list, function(i, user) {
	    	$("#orgContactTel").val(user.contactMobile);
	    	 $("#orgContactName").val(user.contactName);
	    	 
	 	});
	  }
	}
	//
	// 弹出导入框
	var index;
	function uploadExcl(){
	 index = layer.open({
		type: 1, //page层
		area: ['400px', '300px'],
		title: '导入定型产品',
		closeBtn: 1,
		shade: 0.01, //遮罩透明度
		moveType: 1, //拖拽风格，0是默认，1是传统拖动
		shift: 1, //0-6的动画形式，-1不开启
		offset: ['80px', '400px'],
		content: $('#file_div'),
		});
	}
	function addTr(product_id,product_name,product_money,product_count,product_remark){
	$("#table2").append("<tr><td class=\"tc w30\"><input onclick=\"check()\" type=\"checkbox\" id=\"product_id\" name=\""+product_id+"\" value=\"product_id\"/></td>"+
	"<td class=\"p0\"><select id=\"product_name\" name=\"product_name\" onchange=\"changSelect()\" > </td>"+
	"<td class=\"p0\"><input id=\"product_money\" name=\"product_money\" value=\""+product_money+"\" type=\"text\" class=\"w230 mb0\"></td>"+
	 "<td class=\"p0\"><input id=\"product_count\" name=\"product_count\" value=\""+product_count+"\" type=\"text\" class=\"w230 mb0\"></td>"+
	  "<td class=\"p0\"><input id=\"product_remark\" name=\"product_remark\" value=\""+product_remark+"\" type=\"text\" class=\"w230 mb0\"></td></tr>");
	}
	//导入excl 
	function fileUpload(){
	 $.ajaxFileUpload ({
	               url: "${pageContext.request.contextPath}/ob_project/upload.do?",  
	               secureuri: false,  
	               fileElementId: 'fileName', 
	               dataType: 'json',
	               success: function (data) { 
	             
	               var bool=true;
	               var chars = ['A','B','C','D'];
	               if(data=="1"){
				     layer.alert("文件格式错误",{offset: ['222px', '390px'], shade:0.01});
					 } 
					 for(var i = 0; i < chars.length ; i ++) {
						 if(data.indexOf(chars[i])!=-1){
						  	 bool=false;
						}
						 }
						if(bool!=true){
						 	   layer.alert(data,{offset: ['222px', '390px'], shade:0.01});
						  }else{
						 	   layer.alert("上传成功",{offset: ['222px', '390px'], shade:0.01});
						       layer.close(index);
						         $.each(data, function(index, value) {
									addTr(index,value.code,value.standardModel,value.isDeleted,value.remark)
								}); 
	                         }
	                       }
	         }); 
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
  <form action="${pageContext.request.contextPath}/ob_project/add.do" method="post" class="mb0">
   <div class="wrapper mt10">
  <div class="container">
     <h2 class="count_flow"><i>1</i>竞价基本信息</h2>
     <ul class="ul_list">
  <table class="table table-bordered left_table">
		<tbody>
		  <tr>
			<td class="bggrey tr"><span class="red star_red">*</span>竞价标题：</td>
			<td class="p0"><input id="name" name="name" value="" type="text" class="w230 mb0"></td>
			<td class="bggrey tr"><span class="red star_red">*</span>交货截止时间：</td>
			<td class="p0"><input value="<fmt:formatDate type='date' value='${project.deadline }'  pattern=" yyyy-MM-dd HH:mm:ss "/>"
			 name="deliveryDeadline" id="deliveryDeadline" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" onfocus="getValue()" class="Wdate" /></td>
		  </tr>
		  <tr>
			<td class="bggrey tr"><span class="red star_red">*</span>交货地点：</td>
			<td class="p0"><input id="deliveryAddress" name="deliveryAddress" value="" type="text" class="w230 mb0"></td>
			<td class="bggrey tr"><span class="red star_red">*</span>成交供应商数：</td>
			<td class="p0"><input id="tradedSupplierCount" name="tradedSupplierCount" value="" type="text" class="w230 mb0"></td>
		  </tr>
		  <tr>
			<td class="bggrey tr"><span class="red star_red">*</span>运杂费：</td>
			<td class="p0"><input id="transportFees" name="transportFees" value="" type="text" class="w230 mb0"></td>
			<td class="bggrey tr"><span class="red star_red">*</span>需求单位：</td>
			<td class="p0"><input id="demandUnit" name="demandUnit" value="" type="text" class="w230 mb0"></td>
		  </tr>
		  <tr>
			<td class="bggrey tr"><span class="red star_red">*</span>联系人：</td>
			<td class="p0"><input id="contactName" name="contactName" value="" type="text" class="w230 mb0"></td>
			<td class="bggrey tr"><span class="red star_red">*</span>联系电话：</td>
			<td class="p0"><input id="contactTel" name="contactTel" value="" type="text" class="w230 mb0"></td>
		  </tr>
		  <tr>
			<td class="bggrey tr"><span class="red star_red">*</span>采购机构：</td>
			<td class="p0"><div class="w200">
			<select id="orgId" name="orgId" onchange="changSelect()" >
			  <option value=""></option>
			</select></div></td>
			<td class="bggrey tr"><span class="red star_red">*</span>采购联系电话：</td>
			<td class="p0"><input id="orgContactTel" name="orgContactTel" disabled="disabled" type="text" class="w230 mb0"></td>
		  </tr>
		   <tr>
			<td class="bggrey tr"><span class="red star_red">*</span>采购联系人：</td>
			<td class="p0" colspan="3" ><input id="orgContactName" disabled="disabled" name="orgContactName"  type="text" class="w230 mb0"></td>
		  </tr>
		  <tr>
			<td class="bggrey tr"><span class="red star_red">*</span>竞价开始时间：</td>
			<td class="p0"><input value="<fmt:formatDate type='date' value='${project.deadline }'  
			pattern=" yyyy-MM-dd HH:mm:ss "/>" name="startTime" id="startTime" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" onfocus="getValue()" class="Wdate" /></td>
		  <td class="bggrey tr"><span class="red star_red">*</span>竞价结束时间：</td>
			<td class="p0"><input id="endTime" name="endTime" value="<fmt:formatDate type='date' value='${project.deadline }'  
			pattern=" yyyy-MM-dd HH:mm:ss "/>"  onfocus="getValue()" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate" type="text" ></td>
		  </tr>
		 
		  <tr>
			<td class="bggrey tr"><span class="red star_red">*</span>竞价内容：</td>
			<td colspan="3" class="p0">
		   		<div class="col-md-12 col-sm-12 col-xs-12 p0">
  					<textarea class="col-md-12 col-sm-12 col-xs-12" id="content" name="content" style="height:130px"></textarea>
 				</div>
			 </td>
		  </tr>
		  <tr>
			<td class="bggrey tr">竞价文件：</td>
			<td colspan="3" class="p0">
			<div>
                <u:upload id="attachmentId" buttonName="上传文档"  businessId="${userId}" sysKey="${sysKey}" typeId="${typeId }" multiple="true" auto="true" />
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
		<input type="button" class="btn btn-windows add" onclick="addTr('product_id','','','','')" value="添加">
		<input type="button"  class="btn btn-windows delete" value="删除" onclick="del()">
		<input type="button"  class="btn btn-windows output" value="下载EXCEL模板" onclick="down()">
		<input type="button"  class="btn btn-windows input" value="导入EXCEL"  onclick="uploadExcl()">
	</div>   
	<div class="content table_box">
    	<table class="table table-bordered left_table" id ="table2">
		<tr>
		  <th class="w50 info"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
		  <th class="info"><span class="red star_red">*</span>定型产品名称</th>
		  <th class="info"><span class="red star_red">*</span>限价（元）</th>
		  <th class="info"><span class="red star_red">*</span>采购数量</th>
		  <th class="info"><span class="red star_red">*</span>备注</th>
		</tr>
		<tr>
		  <td class="tc w30"><input onclick="check()" type="checkbox" name="product_id" id="product_id" value="product_id" /></td>
		  <td class="p0"><div class="w200"><select id="product_name" name="product_name" onchange="changSelectCount()" ><option value=""></option></select></div>
		  <input id="count" name="count" value="" type="text" class="w230 mb0">
		  </td>
		  <td class="p0"><input id="product_money" name="product_money" value="" type="text" class="w230 mb0"></td>
		  <td class="p0"><input id="product_count" name="product_count" value="" type="text" class="w230 mb0"></td>
		  <td class="p0"><input id="product_remark" name="product_remark" value="" type="text" class="w230 mb0"></td>
		</tr>
	</table>
   </div>
   <h2 class="tc">温馨提示：能够提供当前产品的供应商数量为<span id="gys_count" ></span>家</h2>
	<div class="col-md-12 clear tc mt10">
	<button class="btn btn-windows save mb20" type="submit" onclick="">暂存</button>
	<button class="btn btn-windows apply mb20" type="submit" onclick="">发布</button>
   </div>
   </ul>
	</div>
  </div>
  </form>
  </div>
  
  <div  class=" clear margin-top-30" id="file_div"  style="display:none;" >
   	  <div class="col-md-12 col-sm-12 col-xs-12">
 		   <input type="file" id="fileName" class="input_group" name="file" >
 		</div>
 		<div class="col-md-12 col-sm-12 col-xs-12 mt20 tc">
    	    <input type="button" class="btn input" onclick="fileUpload()" value="导入" />
    	</div>
    </div>
</body>
</html>