<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<link href="${pageContext.request.contextPath}/public/ZHH/css/import_supplier.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/common.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/bootstrap.min.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/style.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/animate.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/ui-dialog.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/dialog-select.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/line-icons.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/font-awesome.min.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/jquery.fileupload-ui.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/zTreeStyle.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/sky-forms.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/custom-sky-forms.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/jquery.fancybox.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/owl.carousel.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/owl.theme.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/style-switcher.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/shortcode_timeline2.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/app.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/blocks.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/datepicker.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/WdatePicker.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/select2.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/application.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/header-v4.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/footer-v2.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/img-hover.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/brand-buttons.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/brand-buttons-inversed.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/blog_magazine.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/page_job.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/page_log_reg_v1.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/shop.style.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/header-v5.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/footer-v4.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/masterslider.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/james.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/WdatePicker(1).css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="<%=basePath%>public/ztree/css/demo.css" type="text/css">
<link rel="stylesheet" href="<%=basePath%>public/ztree/css/zTreeStyle.css" type="text/css">
<script src="<%=basePath%>public/ZHH/js/hm.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery-migrate-1.2.1.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery_ujs.js"></script>
<script src="<%=basePath%>public/ZHH/js/bootstrap.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/back-to-top.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.query.js"></script>
<script src="<%=basePath%>public/ZHH/js/dialog-plus-min.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.fancybox.pack.js"></script>
<script src="<%=basePath%>public/ZHH/js/smoothScroll.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.parallax.js"></script>
<script src="<%=basePath%>public/ZHH/js/app.js"></script>
<script src="<%=basePath%>public/ZHH/js/common.js"></script>
<script src="<%=basePath%>public/ZHH/js/dota.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.dragsort-0.5.2.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/fancy-box.js"></script>
<script src="<%=basePath%>public/ZHH/js/style-switcher.js"></script>
<script src="<%=basePath%>public/ZHH/js/owl.carousel.js"></script>
<script src="<%=basePath%>public/ZHH/js/owl-carousel.js"></script>
<script src="<%=basePath%>public/ZHH/js/owl-recent-works.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.mCustomScrollbar.concat.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.form.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.validate.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.maskedinput.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery-ui.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/masking.js"></script>
<script src="<%=basePath%>public/ZHH/js/datepicker.js"></script>
<script src="<%=basePath%>public/ZHH/js/timepicker.js"></script>
<script src="<%=basePath%>public/ZHH/js/dialog-select.js"></script>
<script src="<%=basePath%>public/ZHH/js/locale.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.ui.widget.js"></script>
<script src="<%=basePath%>public/ZHH/js/load-image.js"></script>
<script src="<%=basePath%>public/ZHH/js/canvas-to-blob.js"></script>
<script src="<%=basePath%>public/ZHH/js/tmpl.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.iframe-transport.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.fileupload.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.fileupload-fp.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.fileupload-ui.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery-fileupload.js"></script>
<script src="<%=basePath%>public/ZHH/js/form.js"></script>
<script src="<%=basePath%>public/ZHH/js/select2.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/select2_locale_zh-CN.js"></script>
<script src="<%=basePath%>public/ZHH/js/application.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.counterup.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/modernizr.js"></script>
<script src="<%=basePath%>public/ZHH/js/touch.js"></script>
<script src="<%=basePath%>public/ZHH/js/product-quantity.js"></script>
<script src="<%=basePath%>public/ZHH/js/master-slider.js"></script>
<script src="<%=basePath%>public/ZHH/js/shop.app.js"></script>
<script src="<%=basePath%>public/ZHH/js/masterslider.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.easing.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/james.js"></script>
<script src="<%=basePath%>public/layer/layer.js"></script>
<script src="<%=basePath%>public/My97DatePicker/WdatePicker.js"></script>
<script src="<%=basePath%>public/laypage-v1.3/laypage/laypage.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/highmap/js/themes/jquery-1.8.2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHQ/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/public/highmap/js/highcharts.js"></script>
<script src="${pageContext.request.contextPath}/public/highmap/js/modules/map.js"></script>
<script src="${pageContext.request.contextPath}/public/highmap/js/modules/data.js"></script>
<script src="${pageContext.request.contextPath}/public/highmap/js/modules/drilldown.js"></script>
<script src="${pageContext.request.contextPath}/public/highmap/js/modules/exporting.js"></script>
<script src="${pageContext.request.contextPath}/public/highmap/js/cn-china-by-peng8.js"></script>
	<script type="text/javascript" src="<%=basePath%>public/ztree/jquery.ztree.core.js"></script>
	<script type="text/javascript" src="<%=basePath%>public/ztree/jquery.ztree.excheck.js"></script>
<link href="<%=basePath%>public/highmap/js/font-awesome.css" media="screen" rel="stylesheet">
<title>My JSP 'index.jsp' starting page</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">    
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<style type="text/css">
#container {
	height: 700px;
	min-width: 310px;
	max-width: 800px;
	margin: 0 auto;
}

.loading {
	margin-top: 10em;
	text-align: center;
	color: gray;
}
</style>
<script type="text/javascript">
	$(function () {
	var address;
    Highcharts.setOptions({
        lang:{
            drillUpText:"返回 > {series.name}"
        }
    });

    var data = Highcharts.geojson(Highcharts.maps['countries/cn/custom/cn-all-china']),small = $('#container').width() < 400;
    // 给城市设置随机数据
    $.each(data, function (i) {
        this.drilldown = this.properties['drill-key'];
        if(i==0){
        //上海
         if(getNum("上海")==0){
	        	this.value=0;
	        }else{
	        	this.value=getNum("上海");
	        }
        } else if(i==1){
        //浙江
        this.value=getNum("浙江");
        }else if(i==2){
        }else if(i==3){
        //澳门
        this.value=getNum("澳门");
        }else if(i==4){
        //台湾
        this.value=getNum("台湾");
        } else if(i==5){
        //甘肃
        this.value=getNum("甘肃");
        }else if(i==6){
        this.value=getNum("香港");
        //香港
        }else if(i==7){
        this.value=getNum("宁夏");
        //宁夏
        }else if(i==8){
        this.value=getNum("陕西");
        //陕西
        }else if(i==9){
        this.value=getNum("安徽");
        //安徽
        }else if(i==10){
        this.value=getNum("湖北");
        //湖北
        }else if(i==11){
        this.value=getNum("广东");
        //广东
        }else if(i==12){
        this.value=getNum("福建");
        //福建
        }else if(i==13){
        this.value=getNum("北京");
        //北京
        }else if(i==14){
        this.value=getNum("河北");
        //河北
        }else if(i==15){
        this.value=getNum("山东");
        //山东
        }else if(i==16){
        this.value=getNum("天津");
        //天津
        }else if(i==17){
        this.value=getNum("江苏");
        //江苏
        }else if(i==18){
        this.value=getNum("海南");
        //海南
        }else if(i==19){
        this.value=getNum("青海");
        //青海
        }else if(i==20){
        this.value=getNum("吉林");
        //吉林
        }else if(i==21){
        this.value=getNum("西藏");
        //西藏
        }else if(i==22){
        this.value=getNum("新疆");
        //新疆
        }else if(i==23){
        this.value=getNum("河南");
        //河南
        }else if(i==24){
        this.value=getNum("内蒙古");
        //内蒙古
        }else if(i==25){
        this.value=getNum("黑龙江");
        //黑龙江
        }else if(i==26){
        this.value=getNum("云南");
        //云南
        }else if(i==27){
        this.value=getNum("广西");
        //广西
        }else if(i==28){
        this.value=getNum("辽宁");
        //辽宁
        }else if(i==29){
        this.value=getNum("四川");
        //四川
        }else if(i==30){
        this.value=getNum("重庆");
        //重庆
        }else if(i==31){
        this.value=getNum("贵州");
        //贵州
        }else if(i==32){
        this.value=getNum("湖南");
        //湖南
        }else if(i==33){
        this.value=getNum("山西");
        //山西
        }else if(i==34){
        this.value=getNum("江西");
        //江西
        } 
        
    });
    
    function getNum(addName){
    var data='${data}';
    	 var index=data.indexOf(addName);
					   var indexStart=index+addName.length;
					   var indexEnd=indexStart+2;
					   var supplierNum=data.substring(indexStart,indexEnd );
					   if("0123456789".indexOf(supplierNum.substring(supplierNum.length-1, supplierNum.length))==-1){
					   		supplierNum=supplierNum.substring(0,1);
					   }
					  return supplierNum;
    }
		function getPoint(e){
			console.log(e.point.name);
		}
	function getShow(e){
		alert(1);
	}
    //初始化地图
    $('#container').highcharts('Map', {

        chart : {
					spacingBottom:30,
            events: {
               
            }
        },
			tooltip: { 
				formatter:function(){
					var htm="";
					if(this.point.drilldown){
					    htm+=this.point.properties["cn-name"];
					}else{
						 htm+=this.point.name;
					}
					address=htm;
					 var data='${data}';
				    if(data==''){
				     	htm+=":"+0; 
				    }else{
					   var index=data.indexOf(htm);
					   var indexStart=index+htm.length;
					   var indexEnd=indexStart+2;
					   var supplierNum=data.substring(indexStart,indexEnd );
					   if("0123456789".indexOf(supplierNum.substring(supplierNum.length-1, supplierNum.length))==-1){
					   		supplierNum=supplierNum.substring(0,1);
					   }
					   htm+=":"+supplierNum; 
					 }
					return htm;
				}
					},
        credits:{
					href:"javascript:goHome()",
            text:""
        },
        title : {
            text : '供应商数量统计'
        },

        subtitle: {
            text: '中国',
            floating: true,
            align: 'right',
            y: 50,
            style: {
                fontSize: '16px'
            }
        },

        legend: small ? {} : {
					 // enabled: false,
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle'
        },
        //tooltip:{
        //pointFormat:"{point.properties.cn-name}:{point.value}"
        //},
        colorAxis: {
            min: 0,
            minColor: '#E6E7E8',
            maxColor: '#005645',
					labels:{
						style:{
								"color":"red","fontWeight":"bold"
						}
					}
        },

        mapNavigation: {
            enabled: true,
            buttonOptions: {
                verticalAlign: 'bottom'
            }
        },

        plotOptions: {
            map: {
                states: {
                    hover: {
                        color: '#EEDD66'
                    }
                }
            }
        },

        series : [{
            data : data,
            name: '中国',
            dataLabels: {
                enabled: true,
                format: '{point.properties.cn-name}'
            },
            point: {
               events: {
                   click: function () { 
                   address=encodeURI(address);
                   address=encodeURI(address);
                       window.location.href="<%=basePath%>supplierQuery/findSupplierByPriovince.html?address="+address;
                    }
                  }
           }
        }],

        drilldown: {
					
            activeDataLabelStyle: {
                color: '#FFFFFF',
                textDecoration: 'none',
                textShadow: '0 0 3px #000000'
            },
            drillUpButton: {
                relativeTo: 'spacingBox',
                position: {
                    x: 0,
                    y: 60
                }
            }
        }
    });
});
function goHome(){
	window.open("http://www.peng8.net/");
}
/* function getGithub()
	{
		$.getJSON("https://api.github.com/repos/peng8/GeoMap/contents/json/bei_jing.geo.json", function(data){
		console.log(base64decode(data.content));
		}); 
	}*/
function submit(){
	form1.submit();
}
function chongzhi(){
	$("#supplierName").val('');
	$("#startDate").val('');
	$("#endDate").val('');
	$("#contactName").val('');
	$("option")[0].selected = true;
	$("#category").val('');
	$("#supplierType").val('');
	$("#categoryIds").val('');
	$("#supplierTypeIds").val('');
}
$(function() {
		var optionNodes = $("option");
		for ( var i = 1; i < optionNodes.length; i++) {
			if ("${sup.supplierType}" == $(optionNodes[i]).val()) {
				optionNodes[i].selected = true;
			}
			if ("${sup.status}" == $(optionNodes[i]).val()) {
				optionNodes[i].selected = true;
			}
		}
	});
	
			function beforeClickCategory(treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treeRole");
			zTree.checkNode(treeNode, !treeNode.checked, null, true);
			return false;
		    }
		    function beforeClick(treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treeSupplierType");
			zTree.checkNode(treeNode, !treeNode.checked, null, true);
			return false;
		    }
		
		function onCheckCategory(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treeRole"),
			nodes = zTree.getCheckedNodes(true),
			v = "";
			var rid = "";
			for (var i=0, l=nodes.length; i<l; i++) {
				v += nodes[i].name + ",";
				rid += nodes[i].id + ",";
			}
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			if (rid.length > 0 ) rid = rid.substring(0, rid.length-1);
			var cityObj = $("#category");
			cityObj.attr("value", v);
			$("#categoryIds").val(rid); 
		}
		function onCheck(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treeSupplierType"),
			nodes = zTree.getCheckedNodes(true),
			v = "";
			var rid = "";
			for (var i=0, l=nodes.length; i<l; i++) {
				v += nodes[i].name + ",";
				rid += nodes[i].id + ",";
			}
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			if (rid.length > 0 ) rid = rid.substring(0, rid.length-1);
			var cityObj = $("#supplierType");
			cityObj.attr("value", v);
			$("#supplierTypeIds").val(rid); 
		}
			function showCategory() {
			var setting = {
			check: {
					enable: true,
					chkboxType: {"Y":"", "N":""}
				},
				view: {
					dblClickExpand: false
				},
				data : {
				simpleData : {
					enable : true,
					idKey : "id",
					pIdKey : "parentId"
				}
			},
				callback: {
					beforeClick: beforeClickCategory,
					onCheck: onCheckCategory
				}
			};
	        $.ajax({
             type: "GET",
             async: false, 
             url: "<%=basePath%>/category/query_category.do?categoryIds="+" ",
             dataType: "json",
             success: function(zNodes){
                     for (var i = 0; i < zNodes.length; i++) { 
			            if (zNodes[i].isParent) {  
			  
			            } else {  
			                //zNodes[i].icon = "${ctxStatic}/images/532.ico";//设置图标  
			            }  
			        }  
			        tree = $.fn.zTree.init($("#treeRole"), setting, zNodes);  
			        tree.expandAll(true);//全部展开
               }
         	});
			var cityObj = $("#category");
			var cityOffset = $("#category").offset();
			$("#roleContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
			$("body").bind("mousedown", onBodyDownOrg);
		}
		function showSupplierType() {
			var setting = {
			check: {
					enable: true,
					chkboxType: {"Y":"", "N":""}
				},
				view: {
					dblClickExpand: false
				},
				data : {
				simpleData : {
					enable : true,
					idKey : "id",
					pIdKey : "parentId"
				}
			},
				callback: {
					beforeClick: beforeClick,
					onCheck: onCheck
				}
			};
	        $.ajax({
             type: "GET",
             async: false, 
             url: "<%=basePath%>/supplier_type/find_supplier_type.do?supplierId='${supplierId}'",
             dataType: "json",
             success: function(zNodes){
                     for (var i = 0; i < zNodes.length; i++) { 
			            if (zNodes[i].isParent) {  
			  
			            } else {  
			                //zNodes[i].icon = "${ctxStatic}/images/532.ico";//设置图标  
			            }  
			        }  
			        tree = $.fn.zTree.init($("#treeSupplierType"), setting, zNodes);  
			        tree.expandAll(true);//全部展开
               }
         	});
			var cityObj = $("#supplierType");
			var cityOffset = $("#supplierType").offset();
			$("#supplierTypeContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
			$("body").bind("mousedown", onBodyDownSupplierType);
		}
		function hideRole() {
			$("#roleContent").fadeOut("fast");
			$("body").unbind("mousedown", onBodyDownOrg);
			
		}
		function hideSupplierType() {
			$("#supplierTypeContent").fadeOut("fast");
			$("body").unbind("mousedown", onBodyDownOrg);
			
		}
		function onBodyDownOrg(event) {
			if (!(event.target.id == "menuBtn" || event.target.id == "roleSel" || event.target.id == "roleContent" || $(event.target).parents("#roleContent").length>0)) {
				hideRole();
			}
		}
		function onBodyDownSupplierType(event) {
			if (!(event.target.id == "menuBtn" || $(event.target).parents("#supplierTypeContent").length>0)) {
				hideSupplierType();
			}
		}
</script>
</head>
<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		  <li><a href="#"> 首页</a></li><li><a href="#">支撑系统</a></li><li><a href="#">供应商管理</a></li><li class="active"><a href="#">供应商查询</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   	   <div id="roleContent" class="roleContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
			<ul id="treeRole" class="ztree" style="margin-top:0;"></ul>
	   </div>
	    <div id="supplierTypeContent" class="supplierTypeContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
			<ul id="treeSupplierType" class="ztree" style="margin-top:0;"></ul>
	   </div>
  <body>
  	<div class="container clear margin-top-30">
  			<form id="form1" action="<%=basePath %>supplierQuery/highmaps.html" method="post">
		       <input type="hidden" name="page" id="page">
		       <table class="table table-bordered table-condensed tc">
		       	<tbody>
		       		<tr>
		       			<td style="text-align:right">供应商名称：</td>
		       			<td style="text-align:right"><input class="span2" id="supplierName" name="supplierName" value="${sup.supplierName }" type="text"></td>
		       			<td style="text-align:right">注册时间：</td>
		       			<td colspan="3">
		       			<div class="mt5">
		       			<input id="startDate" name="startDate" class="span2 fl" type="text"  value='<fmt:formatDate value="${sup.startDate }" pattern="YYYY-MM-dd"/>'
		       			onFocus="var endDate=$dp.$('endDate');WdatePicker({onpicked:function(){endDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})"/>
		       			<span class="add-on fl"><img src="${pageContext.request.contextPath}/public/ZHQ/images/time_icon.png" class="mb10" /> </span>
		       			<span class="fl mt5">至</span>
		       			<input id="endDate" name="endDate" value='<fmt:formatDate value="${sup.endDate }" pattern="YYYY-MM-dd"/>' class="span2 ml10" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})"/>
		       			<span class="add-on fl"><img src="${pageContext.request.contextPath}/public/ZHQ/images/time_icon.png" class="mb10" /> </span>
		       			</div>
		       			</td>
		       		</tr>
		       		<tr>
		       			<td style="text-align:right">联系人：</td>
		       			<td><input class="span2" id="contactName" name="contactName" value="${sup.contactName }" type="text"></td>
		       			<td style="text-align:right">供应商类型：</td>
		       			<td>
		       			      <input id="supplierType" class="span2" type="text" name="supplierType"  readonly value="${supplierType }" onclick="showSupplierType();" />
		       			      <input   type="hidden" name="supplierTypeIds"  id="supplierTypeIds" value="${supplierTypeIds }" />
		       			</td>
		       			<td  style="text-align:right">供应商状态:</td>
		       			<td> <select name="status" class="fl" >
							   		<option  selected="selected" value=''>-请选择-</option>
									<option  value="-1">暂存、未提交</option>
							   		<option  value="0">待初审</option>
							   		<option  value="1">待复审</option>
							   		<option  value="2">初审不通过</option>
							   		<option  value="3">复审通过</option>
							   		<option  value="4">复审不通过</option>
							   </select>
							   </td>
		       		</tr>
		       		<tr>
		       			<td  style="text-align:right">品目：</td>
		       			<td> 
		       			   <input id="category" class="span2" type="text" name="categoryNames" value="${categoryNames }" readonly onclick="showCategory();" />
		       			   <input type="hidden" name="categoryIds"  id="categoryIds" value="${categoryIds }"   />
		       			</td>
		       			<td colspan="4">
		       				 <input class="btn padding-left-20 padding-right-20 btn_back" onclick="submit()" type="button" value="查询">
		     				 <input class="btn padding-left-20 padding-right-20 btn_back" onclick="chongzhi()" type="button" value="重置"> 
		       			</td>
		       		</tr>
		       	</tbody>
		       </table>
		     </form>
     </div>
  <div id="container"></div>
  </body>
</html>
