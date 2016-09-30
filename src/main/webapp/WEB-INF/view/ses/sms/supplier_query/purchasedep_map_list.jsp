<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/highmap/js/themes/jquery-1.8.2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHQ/js/jquery.min.js"></script>



<script src="${pageContext.request.contextPath}/public/highmap/js/highcharts.js"></script>
<script src="${pageContext.request.contextPath}/public/highmap/js/modules/map.js"></script>
<script src="${pageContext.request.contextPath}/public/highmap/js/modules/data.js"></script>
<script src="${pageContext.request.contextPath}/public/highmap/js/modules/drilldown.js"></script>
<script src="${pageContext.request.contextPath}/public/highmap/js/modules/exporting.js"></script>
<script src="${pageContext.request.contextPath}/public/highmap/cn-china-by-peng8.js"></script>
<link href="<%=basePath%>public/highmap/font-awesome.css" media="screen" rel="stylesheet">
<script type="text/javascript" src="http://sandbox.runjs.cn/uploads/rs/228/zroo4bdf/cn-china-by-peng8.js"></script>

<link href="http://netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.css" rel="stylesheet">

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

    Highcharts.setOptions({
        lang:{
            drillUpText:"返回 > {series.name}"
        }
    });

    var data = Highcharts.geojson(Highcharts.maps['countries/cn/custom/cn-all-china']),small = $('#container').width() < 400;

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
    $('#container').highcharts('Map', {

        chart : {
					spacingBottom:30,
				 
            events: {
                drilldown: function (e) {

                    if (!e.seriesOptions) {
                        var chart = this;
                              /*   mapKey = 'countries/china/' + e.point.drilldown + '-all',
                              fail = setTimeout(function () {
                                    if (!Highcharts.maps[mapKey]) {
                                        chart.showLoading('<i class="icon-frown"></i> 加载失败 ' + e.point.name);

                                        fail = setTimeout(function () {
                                            chart.hideLoading();
                                        }, 1000);
                                    }
                                }, 10000);*/
                        var cname=e.point.properties["cn-name"];
                        alert(cname);
                        chart.showLoading('<i class="icon-spinner icon-spin icon-3x"></i>');
                        // 加载城市数据
                        $.ajax({
                            type: "GET",
                            url: "http://data.hcharts.cn/jsonp.php?filename=GeoMap/json/"+ e.point.drilldown+".geo.json",
                            contentType: "application/json; charset=utf-8",
                            dataType:'jsonp',
                            crossDomain: true,
                            success: function(json) {
                                data = Highcharts.geojson(json);
															 
                                $.each(data, function (i) {
																		 
                                    this.value = i;
																		this.events={};
																		this.events.click=getPoint;
																		
                                });
                                chart.hideLoading();

                                chart.addSeriesAsDrilldown(e.point, {
                                    name: e.point.name,
                                    data: data,
																		events:{
																			show:function(){
																				alert(1);
																			}
																		},
                                    dataLabels: {
                                        enabled: true,
                                        format: '{point.name}'
                                    }
                                });
                            },
                            error: function (XMLHttpRequest, textStatus, errorThrown) {

                            }
                        });
                    }


                    this.setTitle(null, { text: cname });
                },
                drillup: function () {
                    this.setTitle(null, { text: '中国' });
                }
            }
        },
			tooltip: { 
				formatter:function(){
					
					var htm="你为什么这么犀利？<br/>";
					 
					if(this.point.drilldown){
					    htm+=this.point.properties["cn-name"];
					}else{
						 htm=this.point.name;
					}
					//alert("-"+htm+"-"+"西藏");
					 if(htm="西藏"){
						htm+=":"+100;
					} 
					//htm+=":"+this.point.value;
					 return htm;
					 
				}
					},
        credits:{
					href:"javascript:goHome()",
            text:"www.peng8.net"
        },
        title : {
            text : 'highmap中国地图By peng8'
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
                   var address=this.name;
                   address=encodeURI(address);
                   address=encodeURI(address);
                       window.location.href="<%=basePath%>supplierQuery/findSupplierByPriovince.html?address="+address+"&status=3";
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
function getGithub()
	{
		$.getJSON("https://api.github.com/repos/peng8/GeoMap/contents/json/bei_jing.geo.json", function(data){
		console.log(base64decode(data.content));
});
		
	}
</script>
</head>
  <body>
  	<div class="container clear margin-top-30">
  			<form id="form1" action="" method="post">
		       <input type="hidden" name="page" id="page">
		       <table class="table table-bordered table-condensed tc">
		       	<tbody>
		       		<tr>
		       			<td style="text-align:right">公司名称：</td>
		       			<td><input class="span2" name="supplierName" value="${supplierName }" type="text"></td>
		       			<td style="text-align:right">供应商类型：</td>
		       			<td><input class="span2" name="supName" value="${name }" type="text"></td>
		       			<td>联系人：</td>
		       			<td><input class="span2" name="contactName" value="${contactName }" type="text"></td>
		       		</tr>
		       		<tr>
		       			<td style="text-align:right">产品分类目录：</td>
		       			<td><input class="span2" name="supName" value="${name }" type="text"></td>
		       			<td>注册时间：</td>
		       			<td colspan="3">
		       			<div class="input-append mt5">
		       			<input id="startDate" name="startDate" class="span2 fl" type="text" 
		       			onFocus="var endDate=$dp.$('endDate');WdatePicker({onpicked:function(){endDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})"/>
		       			<span class="add-on fl"><img src="${pageContext.request.contextPath}/public/ZHQ/images/time_icon.png" class="mb10" /> </span>
		       			<span class="fl">~</span>
		       			<input id="endDate" name="endDate" class="span2 ml10" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})"/>
		       			<span class="add-on fl"><img src="${pageContext.request.contextPath}/public/ZHQ/images/time_icon.png" class="mb10" /> </span>
		       			</div>
		       			</td>
		       		</tr>
		       	</tbody>
		       </table>
			   <div class="tc">
			  <input class="btn padding-left-20 padding-right-20 btn_back" onclick="submit()" type="button" value="查询">
		      <input class="btn padding-left-20 padding-right-20 btn_back" onclick="reset()" type="reset" value="重置"> 
		      </div>
		     </form>
     </div>
  <div id="container"></div>
  </body>
</html>
