<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>进口供应商注册</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link href="${pageContext.request.contextPath}/public/ZHH/css/import_supplier.css" media="screen" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/common.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/bootstrap.min.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/style.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/line-icons.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/app.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/application.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/header-v4.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/footer-v2.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/img-hover.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/page_job.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/shop.style.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/upload/upload.css" type="text/css" />
<link href="${pageContext.request.contextPath}/public/layer/skin/layer.css" media="screen" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/public/layer/skin/layer.ext.css" media="screen" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/supplier/css/supplier.css" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHQ/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/upload/upload.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHQ/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHQ/js/supplier/validateSupplier.js"></script>
<script src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
<script src="${pageContext.request.contextPath}/public/lodop/LodopFuncs.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHQ/js/expert/TestAddress1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHQ/js/expert/TestChooseAddress.js"></script>

<script type="text/javascript">
var parentId ;
var addressId="${is.address}";
$.ajax({
		url : "${pageContext.request.contextPath}/area/find_by_id.do",
		data:{"id":addressId},
		success:function(obj){
			//alert(JSON.stringify(obj));
			var data = eval('(' + obj+ ')');
			$.each(data,function(i,result){
				if(addressId == result.id){
					parentId = result.areaType;
				$("#haha").append("<option selected='true' value='"+result.id+"'>"+result.name+"</option>");
				}else{
					$("#haha").append("<option value='"+result.id+"'>"+result.name+"</option>");
				}
				
			});
			//alert(JSON.stringify(data));
			//alert(parentId);
			
		},
		error:function(obj){
			
		}
		
	});
//鼠标移动显示全部内容
	function out(content){
	if(content.length >= 10){
	layer.msg(content, {
	        offset:'200px',
		    skin: 'demo-class',
			shade:false,
			area: ['600px'],
			time : 0    //默认消息框不关闭
		});//去掉msg图标
	}else{
		layer.closeAll();//关闭消息框
	}
}
$(function(){
	$.ajax({
			url : "${pageContext.request.contextPath}/area/listByOne.do",
			success:function(obj){
				var data = eval('(' + obj + ')');
				$.each(data,function(i,result){
					if(parentId == result.id){
						$("#hehe").append("<option selected='true' value='"+result.id+"'>"+result.name+"</option>");
					}else{
					$("#hehe").append("<option value='"+result.id+"'>"+result.name+"</option>");
					}
				});
				
				//alert(JSON.stringify(obj));
			},
			error:function(obj){
				
			}
			
		});
});
</script>
</head>
<body>
 <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">支撑系统</a></li><li><a href="#">进口供应商登记</a></li><li class="active"><a href="#">查看进口供应商</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
		<div class="container content height-350">
			<div class="row magazine-page">
				<div class="col-md-12 tab-v2 job-content">
					<div class="padding-top-10">
						<div class="tab-content padding-top-20">
							<div class="tab-pane fade active in height-450">
						<table class="table table-bordered">
							<tbody>
		                        <tr>
									<td class="bggrey tr" style="width:17%">企业名称：</td>
									<td style="width:16%" onmouseover="out('${is.name}')">${is.name}</td>
									<td class="bggrey tr" style="width:17%"> 企业类别：</td>
									<td style="width:17%">${is.supplierType }</td>
									<td style="width:17%" class="bggrey tr">中文译名：</td>
									<td style="width:17%">${is.chinesrName }</td>
								</tr>
								 <tr>
									<td class="bggrey tr">法定代表人：</td>
									<td>${is.legalName}</td>
									<td class="bggrey tr">企业注册地址：</td>
									<td><select id="hehe" onchange="fun();">
													<option>-请选择-</option>
												</select>
												<select name="address" id="haha">
													<option>-请选择-</option>
												</select></td>
									<td class="bggrey tr">邮政编码：</td>
									<td colspan="3">${is.postCode }</td>
								</tr>
								<tr>
									<td class="bggrey tr">经营产品大类：</td>
									<td>${is.productType}</td>
									<td class="bggrey tr"> 主营产品：</td>
									<td>${is.majorProduct }</td>
									<td class="bggrey tr">兼营产品：</td>
									<td>${is.byproduct }</td>
								</tr>
								<tr>
									<td class="bggrey tr">生产商名称：</td>
									<td onmouseover="out('${is.producerName}')">${is.producerName}</td>
									<td class="bggrey tr"> 联系人：</td>
									<td>${is.contactPerson }</td>
									<td class="bggrey tr">电话：</td>
									<td>${is.telephone }</td>
								</tr>
								<tr>
									<td class="bggrey tr">传真：</td>
									<td>${is.fax}</td>
									<td class="bggrey tr"> 电子邮件：</td>
									<td>${is.email }</td>
									<td class="bggrey tr">企业网址：</td>
									<td>${is.website }</td>
								</tr>
								<tr>
									<td class="bggrey tr">国内供货业绩：</td>
									<td colspan="5" onmouseover="out('${is.civilAchievement}')">${is.civilAchievement}</td>
								</tr>
								<tr>
									<td class="bggrey tr"> 企业简介：</td>
									<td  colspan="5"  onmouseover="out('${is.remark}')">${is.remark}</td>
								</tr>
							</tbody>
						</table>
									<div class="tc mt20 clear col-md-11">
											<button class="btn btn-windows reset" onclick="history.go(-1)" type="button">返回</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
</body>
</html>
