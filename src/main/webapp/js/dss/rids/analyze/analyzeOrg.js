$(function () {
    // 各采购机构人员数量
    optionOrgSup = {
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
                        findAllOrg();
                    }
                },
                dataView: {
                    show: true,
                    readOnly: false,
                    optionToContent: function (opt) {
                        var axisData = opt.series[0].data; //坐标数据
                        var table = '<div class="analyze_resource_div"><table border="1" class="analyze_resource_table"><tbody>';
                        table += "<tr>";
                        for (var i = 0, l = axisData.length; i < l; i++) {
                            table += '<td class="bgdd analyze_resource">'+axisData[i].srcData.name+'</td>';
                            table += "<td class='analyze_resource'><a href=\"javascript:;\" onclick=\"findOrgMemNum('"+axisData[i].srcData.id+"', "+axisData[i].srcData.value+")\">"+axisData[i].srcData.value+"</a></td>";
                            if(i != 0 && (i+1) % 4 == 0){
                                table += "</tr>";
                                table += "<tr>";
                            }
                            if(i % 4 != 0 && (i+1) == axisData.length){
                                table += "</tr>";
                            }
                        }
                        table += '</tbody></table></div>';
                        return table;
                    }
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
        yAxis: [
            {
                type: "value"
            }
        ],
        series: [
            {
                name: "数量/人",
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

    // 当年各采购机构受领任务总金额
    optionOrgContractMoney = {
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
                        findAllOrg();
                    }
                },
                dataView: {
                    show: true,
                    readOnly: false,
                    optionToContent: function (opt) {
                        var axisData = opt.series[0].data; //坐标数据
                        var table = '<div class="analyze_resource_div"><table border="1" class="analyze_resource_table"><tbody>';
                        table += "<tr>";
                        for (var i = 0, l = axisData.length; i < l; i++) {
                            table += '<td class="bgdd analyze_resource">'+axisData[i].srcData.name+'</td>';
                            table += "<td class='analyze_resource'><a href=\"javascript:;\" onclick=\"findNowYearOrgAcceptTaskMoney('"+axisData[i].srcData.id+"', "+axisData[i].srcData.value+")\">"+axisData[i].srcData.value+"</a></td>";
                            if(i != 0 && (i+1) % 4 == 0){
                                table += "</tr>";
                                table += "<tr>";
                            }
                            if(i % 4 != 0 && (i+1) == axisData.length){
                                table += "</tr>";
                            }
                        }
                        table += '</tbody></table></div>';
                        return table;
                    }
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
                name: "金额/万元",
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

    // 各采购机构人员数量
    $.ajax({
        url: globalPath + "/resAnalyze/selectMemNumByOrg.do",
        type: "POST", // 请求方式
        dataType: "json", // 返回格式为json
        success: function (data) {
            // 专家企业性质
            $("#orgMemNum").echartsTemplate({
                dataList: data,
                XField: 'name',
                YField: 'value',
                groupField: 'group',
                chartType: 'bar',
                XTitle: '机构',
                YTitle: '数量/人',
                customEchartsOptions: function (res, dataList, option) {
                    return optionOrgSup;
                }
            });
            // 获取echart对应的对象
            var supplierOrg = $("#orgMemNum").echartsTemplate("getMyChart", null);
            supplierOrg.on('click', function (params) {
                window.location.href = globalPath + "/purchase/readOnlyList.html?reqType=analyzeOrg&purchaseDepId=" + params.data.id;
            });
        }
    });

    // 当年各采购机构受领任务总金额
    $.ajax({
        url: globalPath + "/resAnalyze/selectNowYearOrgAcceptTaskMoney.do",
        type: "POST", // 请求方式
        dataType: "json", // 返回格式为json
        success: function (data) {
            // 专家企业性质
            $("#nowYearOrgAcceptTaskMoney").echartsTemplate({
                dataList: data,
                XField: 'name',
                YField: 'value',
                groupField: 'group',
                chartType: 'bar',
                XTitle: '机构',
                YTitle: '金额/万元',
                customEchartsOptions: function (res, dataList, option) {
                    return optionOrgContractMoney;
                }
            });
            // 获取echart对应的对象
            var supplierOrg = $("#nowYearOrgAcceptTaskMoney").echartsTemplate("getMyChart", null);
            supplierOrg.on('click', function (params) {
                window.location.href = globalPath + "/purchaseManage/readOnlyList.html?orgId=" + params.data.id;
            });
        }
    });

    // 当年各采购机构签订采购合同金额
    $.ajax({
        url: globalPath + "/resAnalyze/selectNowYearOrgContractMoney.do",
        type: "POST", // 请求方式
        dataType: "json", // 返回格式为json
        success: function (data) {
            // 专家企业性质
            $("#nowYearOrgContractMoney").echartsTemplate({
                dataList: data,
                XField: 'name',
                YField: 'value',
                groupField: 'group',
                chartType: 'bar',
                XTitle: '机构',
                YTitle: '金额/万元',
                customEchartsOptions: function (res, dataList, option) {
                    return optionOrgContractMoney;
                }
            });
            // 获取echart对应的对象
            var supplierOrg = $("#nowYearOrgContractMoney").echartsTemplate("getMyChart", null);
            supplierOrg.on('click', function (params) {
                window.location.href = globalPath + "/purchaseManage/readOnlyList.html?orgId=" + params.data.id;
            });
        }
    });
});
function findAllOrg(){
    window.location.href = globalPath + "/purchaseManage/readOnlyList.html";
}

function findOrgMemNum(id,value){
    if(value == 0){
        layer.msg("暂无数据");
        return;
    }
    window.location.href = globalPath + "/purchase/readOnlyList.html?reqType=analyzeOrg&purchaseDepId=" + id;
}

function findNowYearOrgAcceptTaskMoney(id,value){
    if(value == 0){
        layer.msg("暂无数据");
        return;
    }
    window.location.href = globalPath + "/purchaseManage/readOnlyList.html?orgId=" + id;
}