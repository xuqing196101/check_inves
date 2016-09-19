<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>
    <script src="<%=basePath%>public/ZHH/js/highmaps/js/highmaps.js"></script>
<script src="<%=basePath%>public/ZHH/js/highmaps/js/modules/exporting.js"></script>
<script src="<%=basePath%>public/ZHH/js/highmaps/js/china-data.js"></script>


    
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
    var data = [
        ${data}
    ];

    $('#container').highcharts('Map', {
        title : {
            text : 'xxx量统计'
        },
        subtitle : {
            text : '地区分布'
        },
        mapNavigation: {
            enabled: true,
            buttonOptions: {
                verticalAlign: 'bottom'
            }
        },
        colorAxis: {
            min: 0
        },
        series : [{
            data : data,
            mapData: Highcharts.maps['countries/china'],
            joinBy: 'hc-key',
            name: 'xxx分布',
            states: {
                hover: {
                    color: '#BADA55'
                }
            },
            dataLabels: {
                enabled: true,
                format: '{point.name}'
            },
            point: {
               events: {
                   click: function () { 
                      alert(1);
                    }
                  }
           }
        }]
    });
});
	
	
	</script>
  </head>
  
  <body>
  <div id="container"></div>
  </body>
</html>
