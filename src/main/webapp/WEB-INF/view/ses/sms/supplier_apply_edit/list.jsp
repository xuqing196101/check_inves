<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
<link href="${pageContext.request.contextPath}/public/ZHH/css/import_supplier.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/common.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/bootstrap.min.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/style.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/animate.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/ui-dialog.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/dialog-select.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/line-icons.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/font-awesome.min.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/jquery.fileupload-ui.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/zTreeStyle.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/sky-forms.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/custom-sky-forms.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/jquery.fancybox.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/owl.carousel.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/owl.theme.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/style-switcher.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/shortcode_timeline2.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/app.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/blocks.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/datepicker.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/WdatePicker.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/select2.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/application.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/header-v4.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/footer-v2.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/img-hover.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/brand-buttons.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/brand-buttons-inversed.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/blog_magazine.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/page_job.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/page_log_reg_v1.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/shop.style.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/header-v5.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/footer-v4.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/masterslider.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/james.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/WdatePicker(1).css" rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath}/public/ZHH/js/hm.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery-migrate-1.2.1.min.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery_ujs.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/back-to-top.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.query.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/dialog-plus-min.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.fancybox.pack.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/smoothScroll.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.parallax.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/app.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/common.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/dota.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.dragsort-0.5.2.min.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/fancy-box.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/style-switcher.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/owl.carousel.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/owl-carousel.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/owl-recent-works.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.mCustomScrollbar.concat.min.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/WdatePicker.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.form.min.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.validate.min.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.maskedinput.min.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery-ui.min.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/masking.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/datepicker.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/timepicker.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/dialog-select.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/locale.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.ui.widget.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/load-image.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/canvas-to-blob.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/tmpl.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.iframe-transport.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.fileupload.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.fileupload-fp.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.fileupload-ui.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery-fileupload.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/form.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/select2.min.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/select2_locale_zh-CN.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/application.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.counterup.min.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/modernizr.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/touch.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/product-quantity.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/master-slider.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/shop.app.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/masterslider.min.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.easing.min.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/james.js"></script>
<script src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
<script src="${pageContext.request.contextPath}/public/laypage-v1.3/laypage/laypage.js"></script>
<script type="text/javascript">
	 $(function(){
		  laypage({
			    cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
			    pages: "${seList.pages}", //总页数
			    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
			    skip: true, //是否开启跳页
			    total: "${seList.total}",
			    startRow: "${seList.startRow}",
			    endRow: "${seList.endRow}",
			    groups: "${seList.pages}">=5?5:"${seList.pages}", //连续显示分页数
			    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
			        var page = location.search.match(/page=(\d+)/);
			        return page ? page[1] : 1;
			    }(), 
			    jump: function(e, first){ //触发分页后的回调
			        if(!first){ //一定要加此判断，否则初始时会无限刷新
			             location.href = '${pageContext.request.contextPath}/supplier_edit/list.do?page='+e.curr;
			        }
			    }
			});
	  });
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
  	function show(id){
  		window.location.href="${pageContext.request.contextPath}/supplier_edit/view.html?id="+id;
  	}
  	function add(){
  		 window.location.href="${pageContext.request.contextPath}/supplier_edit/add.html?id="+'${id}'; 
  	}
</script>
</head>
<body>
   <div class="margin-top-10 breadcrumbs">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">支撑系统</a></li><li><a href="#">供应商管理</a></li><li><a href="#">供应商信息变更记录</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
		<div class="container clear margin-top-30">
		    <button class="btn btn-windows add" type="button" onclick="add()">变更</button>
		  <table id="tb1"  class="table table-striped table-bordered table-hover tc">
		      <thead>
				<tr>
					<th class="info w50 tc">序号</th>
					<th class="info">供应商名称</th>
					<th class="info">变更时间</th>
					<th class="info">变更状态</th>
				</tr>
			  </thead>
			  <tbody>
				 <c:forEach items="${seList.list }" var="se" varStatus="vs">
					<tr>
					    <td>${(vs.index+1)+(seList.pageNum-1)*(seList.pageSize)}</td>
						<td><a onclick="show('${se.id}')" class="pointer">${se.supplierName }</a></td>
						<td><fmt:formatDate value="${se.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>
							<c:if test="${se.status==0 }">
								未审核
							</c:if>
							<c:if test="${se.status==1}">
								审核通过
							</c:if>
							<c:if test="${se.status==2 }">
								审核退回
							</c:if>
						</td>
					</tr>
				</c:forEach> 
			  </tbody>
		 </table>
			<div id="pagediv" align="right"></div>
		 </div>		 
</body>
</html>
