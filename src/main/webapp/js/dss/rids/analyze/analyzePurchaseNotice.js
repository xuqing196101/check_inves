$(function () {
    // 各类型数量
    optionCateType = {
        title: {
            x: 'center'
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        toolbox: {
            show: true,
            feature: {
                mark: {
                    show: true
                },
                myTool: {
                    show: true,
                    title: '查询所有',
                    icon: 'image://' + globalPath + '/public/backend/images/pie.png',
                    onclick: function () {
                        findAllPurchaseNotice();
                    }
                },
                dataView: {
                    show: true,
                    readOnly: false
                },
                magicType: {
                    show: true,
                    type: ['pie', 'funnel'],
                    option: {
                        funnel: {
                            x: '25%',
                            width: '50%',
                            funnelAlign: 'left',
                            max: 1548
                        }
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
        calculable: true,
        series: [{
            name: '信息数量',
        }]
    };

    // 公告柱形图
    optionNotice = {
        title: {
            top: 0,
            left: '43%'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            bottom: 0
        },
        toolbox: {
            show: true,
            feature: {
                myTool: {
                    show: true,
                    title: '查询所有',
                    icon: 'image://' + globalPath + '/public/backend/images/pie.png',
                    onclick: function () {
                        findAllPurchaseNotice();
                    }
                },
                dataView: {
                    show: true,
                    readOnly: false
                },
                magicType: {
                    show: true,
                    type: ['line', 'bar']
                },
                /*restore: {
                    show: true
                },*/
                saveAsImage: {
                    show: true
                }
            },
            dataView: {show: true, readOnly: true},
        },
        calculable: true,
        xAxis: [{
            axisLabel: {
                interval: 0,
                formatter: function (val) {
                    return val.split("").join("\n");
                }
            }
        }],
        series: [
            {
                name: "采购公告发布数量",
                itemStyle: {
                    normal: {
                        color: function (params) {
                            // build a color map as your need.
                            var colorList = [
                                '#C1232B', '#B5C334', '#FCCE10', '#E87C25', '#27727B',
                                '#FE8463', '#9BCA63', '#FAD860', '#F3A43B', '#60C0DD',
                                '#D7504B', '#C6E579', '#F4E001', '#F0805A', '#26C0C0'
                            ];
                            return colorList[params.dataIndex]
                        },
                        label: {
                            show: true,
                            position: 'top',
                            //formatter: '{b}\n{c}'
                        }
                    }
                },
                markPoint: {
                    tooltip: {
                        trigger: 'item',
                        backgroundColor: 'rgba(0,0,0,0)',
                        formatter: function (params) {
                            return '<img src="'
                                + params.data.symbol.replace('image://', '')
                                + '"/>';
                        }
                    },
                }
            }
        ]
    };

    /**
     * 近5年采购公告发布数量
     */
    $.ajax({
        url: globalPath + "/resAnalyze/selectNearFiveYearPurchaseNoticeCount.do",
        type: "POST", // 请求方式
        dataType: "json", // 返回格式为json
        success: function (data) {
            $("#nearFiveYearNoticeCount").echartsTemplate({
                dataList: data,
                XField: 'name',
                YField: 'value',
                groupField: 'group',
                chartType: 'bar',
                XTitle: '年份',
                YTitle: '数量/个',
                customEchartsOptions: function (res, dataList, option) {
                    return optionNotice;
                }
            });
            // 获取echart对应的对象
            var supplierCateType = $("#nearFiveYearNoticeCount").echartsTemplate("getMyChart", null);
            supplierCateType.on('click', function (params) {
                window.location.href = globalPath + "/article/readOnlyList.html?status=2&publishYear=" + params.name;
            });
        }
    });

    /**
     * 各栏目信息数量
     */
    $.ajax({
        url: globalPath + "/resAnalyze/selectNoticeByArticleType.do",
        type: "POST", // 请求方式
        dataType: "json", // 返回格式为json
        success: function (data) {
            $("#articleTypeCount").echartsTemplate({
                dataList: data,
                XField: 'name',
                YField: 'value',
                groupField: 'group',
                chartType: 'pie',
                customEchartsOptions: function (res, dataList, option) {
                    return optionCateType;
                }
            });
            // 获取echart对应的对象
            var supplierCateType = $("#articleTypeCount").echartsTemplate("getMyChart", null);
            supplierCateType.on('click', function (params) {
                window.location.href = globalPath + "/article/readOnlyList.html?status=2&articleTypeId=" + params.data.id;
            });
        }
    });

    /**
     * 根据各类型公告查询
     */
    $.ajax({
        url: globalPath + "/resAnalyze/selectNoticeByCateType.do",
        type: "POST", // 请求方式
        dataType: "json", // 返回格式为json
        success: function (data) {
            $("#articleNoticeByCateType").echartsTemplate({
                dataList: data,
                XField: 'name',
                YField: 'value',
                groupField: 'group',
                chartType: 'pie',
                customEchartsOptions: function (res, dataList, option) {
                    return optionCateType;
                }
            });
            // 获取echart对应的对象
            var supplierCateType = $("#articleNoticeByCateType").echartsTemplate("getMyChart", null);
            supplierCateType.on('click', function (params) {
                window.location.href = globalPath + "/article/readOnlyList.html?status=2&threeArticleTypeId=" + params.data.id;
            });
        }
    });

    /**
     * 发布排名前10的产品类别数量
     */
    $.ajax({
        url: globalPath + "/resAnalyze/selectNoticeByProductCate.do",
        type: "POST", // 请求方式
        dataType: "json", // 返回格式为json
        success: function (data) {
            $("#articleNoticeByProductCate").echartsTemplate({
                dataList: data,
                XField: 'name',
                YField: 'value',
                groupField: 'group',
                chartType: 'bar',
                XTitle: '类别',
                YTitle: '数量/个',
                customEchartsOptions: function (res, dataList, option) {
                    return optionNotice;
                }
            });
            // 获取echart对应的对象
            var supplierCateType = $("#articleNoticeByProductCate").echartsTemplate("getMyChart", null);
            supplierCateType.on('click', function (params) {
                window.location.href = globalPath + "/article/readOnlyList.html?status=2&categoryId=" + params.data.id;
            });
        }
    });

    /**
     * 根据各采购方式公告查询
     */
    $.ajax({
        url: globalPath + "/resAnalyze/selectNoticeByPurWay.do",
        type: "POST", // 请求方式
        dataType: "json", // 返回格式为json
        success: function (data) {
            $("#articleNoticeByPurWay").echartsTemplate({
                dataList: data,
                XField: 'name',
                YField: 'value',
                groupField: 'group',
                chartType: 'pie',
                customEchartsOptions: function (res, dataList, option) {
                    return optionCateType;
                }
            });
            // 获取echart对应的对象
            var supplierCateType = $("#articleNoticeByPurWay").echartsTemplate("getMyChart", null);
            supplierCateType.on('click', function (params) {
                window.location.href = globalPath + "/article/readOnlyList.html?status=2&fourArticleTypeId=" + params.data.id;
            });
        }
    });
});

function findAllPurchaseNotice(){
    window.location.href = globalPath + "/article/readOnlyList.html?status=2";
}