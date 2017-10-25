<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../../../common.jsp"%>
<%@ include file="/WEB-INF/view/common/map.jsp"%>
<script type="text/javascript">
	$(function () {
	var address;
    Highcharts.setOptions({
        lang:{
            drillUpText:"返回 > {series.name}"
        }
    });

    var data = Highcharts.geojson(Highcharts.maps['countries/cn/custom/cn-all-china']),small = $('#mapsId').width() < 400;
    // 给城市设置随机数据
    $.each(data, function (i) {
        this.drilldown = this.properties['drill-key'];
		 
        this.value = i;
    });
		function getPoint(e){
			console.log(e.point.name);
		}
	function getShow(e){
		alert(1);
	}
    //初始化地图
    $('#mapsId').highcharts('Map', {

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
            text : ''
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
                   		var name = $("#name").val();
                   		var quaStartDate = $("#quaStartDate").val();
                   		var quaEdndate = $("#quaEdndate").val();
                       window.location.href="${pageContext.request.contextPath}/purchaseManage/purchaseDepdetailList.html?parentName="+address+"&name="+name+"&quaStartDate="+quaStartDate+"&quaEdndate="+quaEdndate;
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

var base64EncodeChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";  
var base64DecodeChars = new Array(  
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63,  
    52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1,  
    -1,  0,  1,  2,  3,  4,  5,  6,  7,  8,  9, 10, 11, 12, 13, 14,  
    15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1,  
    -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,  
    41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1);  


function base64decode(str) {  
    var c1, c2, c3, c4;  
    var i, len, out;  
  
    len = str.length;  
    i = 0;  
    out = "";  
    while(i < len) {  
    /* c1 */  
    do {  
        c1 = base64DecodeChars[str.charCodeAt(i++) & 0xff];  
    } while(i < len && c1 == -1);  
    if(c1 == -1)  
        break;  
  
    /* c2 */  
    do {  
        c2 = base64DecodeChars[str.charCodeAt(i++) & 0xff];  
    } while(i < len && c2 == -1);  
    if(c2 == -1)  
        break;  
  
    out += String.fromCharCode((c1 << 2) | ((c2 & 0x30) >> 4));  
  
    /* c3 */  
    do {  
        c3 = str.charCodeAt(i++) & 0xff;  
        if(c3 == 61)  
        return out;  
        c3 = base64DecodeChars[c3];  
    } while(i < len && c3 == -1);  
    if(c3 == -1)  
        break;  
  
    out += String.fromCharCode(((c2 & 0XF) << 4) | ((c3 & 0x3C) >> 2));  
  
    /* c4 */  
    do {  
        c4 = str.charCodeAt(i++) & 0xff;  
        if(c4 == 61)  
        return out;  
        c4 = base64DecodeChars[c4];  
    } while(i < len && c4 == -1);  
    if(c4 == -1)  
        break;  
    out += String.fromCharCode(((c3 & 0x03) << 6) | c4);  
    }  
    return out;  
}  
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
		var auth='${authType}';
  	    if(auth !='4'){
  	    layer.msg("只有资源服务中心可以操作");
  	    return;
  	    }
		form1.submit();
}
function chongzhi(){
	var auth='${authType}';
	    if(auth !='4'){
	    layer.msg("只有资源服务中心可以操作");
	    return;
	    }
	$("#name").val('');
	$("#quaStartDate").val('');
	$("#quaEdndate").val('');
	$("option")[0].selected = true;
}
</script>
</head>
  <body>
  	 <div class="container">
    <div class="headline-v2">
     <h2>采购机构数量统计</h2>
   </div> 
     <c:if test="${authType == 4}">
        <h2 class="search_detail">
  			<form id="form1" action="${pageContext.request.contextPath}/purchaseManage/purchaseDepMapList.html" class="mb0" method="post">
		       <input type="hidden" name="page" id="page">
		          <ul class="demand_list">
		               <li>
			            <label class="fl">采购机构名称：</label><span><input id="name" name="name" value="${purchaseDep.name }" type="text" ></span>
			          </li>
			           <li>
                        <label class="fl">资质起止日期：</label>
                        <input id="quaStartDate" name="quaStartDate" class="Wdate w150" type="text"  value='<fmt:formatDate value="${purchaseDep.quaStartDate }" pattern="YYYY-MM-dd"/>'
                        onFocus="var endDate=$dp.$('endDate');WdatePicker({onpicked:function(){quaStartDate.focus();},maxDate:'#F{$dp.$D(\'quaEdndate\')}'})"/>
                        <span  class="f14" >至</span>
                        <input id="quaEdndate" name="quaEdndate" value='<fmt:formatDate value="${purchaseDep.quaEdndate }" pattern="YYYY-MM-dd"/>' class="Wdate w150" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'quaStartDate\')}'})"/>
                      </li>
                      <li>
                        <label class="fl">上级监管部门：</label>
                        <span class="fl">
			              <select name="" class="w100">
			                         <option selected="selected" value=''>-请选择-</option>
                                    <option  value="生产型">部门1</option>
                                    <option  value="销售型">部门2</option>
                                    <option  value="军区采购">军区采购</option>
			              </select>
			            </span>
                      </li>
		          </ul>
	                  <input class="btn fl mt1" onclick="submit()" type="button" value="查询">
                      <input class="btn fl mt1" onclick="chongzhi()" type="button" value="重置"> 
		       				 <div class="clear"></div>
		     </form>
     </h2>
  	
  <div id="mapsId"></div>
  </c:if>
  </div>
  </body>
</html>
