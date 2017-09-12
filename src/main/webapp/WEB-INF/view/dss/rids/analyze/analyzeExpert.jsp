<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
    <%@ include file="/WEB-INF/view/common.jsp" %>
    <%@ include file="/WEB-INF/view/common/map.jsp" %>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/js/dss/rids/analyze/analyzeExpert.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/js/system/analyze/echartsTemplate.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/dss/rids/analyze/list.js"></script>

    <script src="${pageContext.request.contextPath}/public/highmap/js/modules/map.js"></script>
    <script src="${pageContext.request.contextPath}/public/highmap/js/modules/data.js"></script>
    <script src="${pageContext.request.contextPath}/public/highmap/js/modules/drilldown.js"></script>
    <script src="${pageContext.request.contextPath}/public/highmap/js/modules/exporting.js"></script>
    <script src="${pageContext.request.contextPath}/public/highmap/js/cn-china-by-peng8.js"></script>
    <script src="${pageContext.request.contextPath}/public/echarts/china.js"></script>
    <link href="${pageContext.request.contextPath}/public/highmap/js/font-awesome.css" media="screen" rel="stylesheet">
    <title>统计页面</title>
    <script type="text/javascript">
        $(function () {
            option = {
                tooltip: {
                    trigger: 'item'
                },
                legend: {
                    orient: 'vertical',
                    x: 'left',
                    data: ['']
                },
                dataRange: {
                    min: 0,
                    max: '${maxCount}',
                    x: 'left',
                    y: 'bottom',
                    text: ['高', '低'], // 文本，默认为数值文本
                    calculable: true
                },
                toolbox: {
                    show: true,
                    orient: 'vertical',
                    x: 'right',
                    y: 'center',
                    feature: {
                        mark: {
                            show: true
                        },
                        myTool: {
                            show: true,
                            title: '查询所有',
                            icon: 'image://' + globalPath + '/public/backend/images/pie.png',
                            onclick: function () {
                                findAllExpert();
                            }
                        },
                        dataView: {
                            show: true,
                            readOnly: false,
                            optionToContent: function (opt) {
                                var axisData = opt.series[0].data; //坐标数据
                                var table = '<table border="1" style="margin-left:20px;border-collapse:collapse;font-size:14px;text-align:center"><tbody>';
                                table += "<tr>";
                                for (var i = 0, l = axisData.length; i < l; i++) {
                                    table += '<td style="padding: 5px 30px 5px 30px;" class="bgdd">'+axisData[i].name+'</td>';
                                    table += "<td style='padding: 5px 30px 5px 30px;'><a href=\"javascript:;\" onclick=\"findByAreaName('"+axisData[i].id+"', "+axisData[i].value+")\">"+axisData[i].value+"</a></td>";
                                    if(i != 0 && (i+1) % 4 == 0){
                                        table += "</tr>";
                                        table += "<tr>";
                                    }
                                    if(i % 4 != 0 && (i+1) == axisData.length){
                                        table += "</tr>";
                                    }
                                }
                                table += '</tbody></table>';
                                return table;
                            }
                        },
                        /*restore: {
                            show: true
                        },*/
                        saveAsImage: {
                            show: true
                        }
                    }
                },
                roamController: {
                    show: true,
                    x: 'right',
                    mapTypeControl: {
                        'china': true
                    }
                },
                series: [{
                    name: '中国',
                    type: 'map',
                    mapType: 'china',
                    roam: false,
                    itemStyle: {
                        normal: {
                            label: {
                                show: true
                            }
                        },
                        emphasis: {
                            label: {
                                show: true
                            }
                        }
                    },
                    data: eval('${data}'),
                }]
            };
            var myChart = echarts.init(document.getElementById("expertProvince"));
            myChart.setOption(option);
            myChart.hideLoading();
            myChart.on('click', function (params) {
                window.location.href = "${pageContext.request.contextPath}/expertQuery/readOnlyList.html?address=" + params.data.id;
            });
        });

        function findByAreaName(id,value){
            if(value == 0){
                layer.msg("该地区没有入库专家");
                return;
            }
            window.location.href = "${pageContext.request.contextPath}/expertQuery/readOnlyList.html?address=" + id;
        }
    </script>
</head>
<body>
<!--  -->
<div class="margin-top-10 breadcrumbs ">
    <div class="container">
        <ul class="breadcrumb margin-left-0">
            <li><a href="javascript:void(0)">首页</a></li>
            <li><a href="javascript:void(0)">决策支持</a></li>
            <li><a href="javascript:void(0)">采购资源综合展示</a></li>
            <li class="active"><a href="${pageContext.request.contextPath}/resAnalyze/list.html">采购资源展示</a>
            <li class="active"><a href="javascript:void(0)">专家</a>
        </ul>
        <div class="clear"></div>
    </div>
</div>
<!-- 内容 -->
<div class="container content job-content">
    <button class="btn btn-windows back mb20" type="button" id="backAnalyzePage">返回</button>
    <div class="m-chart-head text-center">
        <span class="mch-tit">入库专家总量：</span> <span class="mch-num">${ totalCount }</span>
    </div>
    <ul class="ul_list">
        <h2 class="count_flow">
            <span class="m-chart-icon"></span> 各省数量
        </h2>
        <div id="expertProvince" class="supplierAnalyze"></div>
    </ul>
    <ul class="ul_list">
        <h2 class="count_flow">
            <span class="m-chart-icon"></span> 各类型数量
        </h2>
        <div id="expertCateType" class="analyze"></div>
    </ul>
    <ul class="ul_list">
        <h2 class="count_flow">
            <span class="m-chart-icon"></span> 军地数量
        </h2>
        <div id="expertNature" class="analyze"></div>
    </ul>
    <ul class="ul_list">
        <h2 class="count_flow">
            <span class="m-chart-icon"></span> 各采购机构专家数量
        </h2>
        <div id="expertOrg" class="analyze"></div>
    </ul>
</div>
</body>
</html>
