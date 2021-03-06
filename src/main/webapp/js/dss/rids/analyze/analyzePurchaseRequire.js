$(function () {
    /**
     * bar
     */
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
                        findAllPurchaseRequire();
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
                            table += '<td class="bgdd analyze_resource">'+axisData[i].name+'</td>';
                            table += "<td class='analyze_resource'><a href=\"javascript:;\" onclick=\"findTypeRequireMoney('"+axisData[i].id+"')\">"+axisData[i].value+"</a></td>";
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
            name: '需求金额',
        }]
    };

    /**
     * 采购需求柱状图
     */
    optionRequire = {
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
                        findAllPurchaseRequire();
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
                            table += '<td class="bgdd analyze_resource">'+axisData[i].srcData.name+'</td>';
                            table += "<td class='analyze_resource'><a href=\"javascript:;\" onclick=\"findNearFiveYearAllBudgetRequire('"+axisData[i].srcData.name+"', "+axisData[i].srcData.value+")\">"+axisData[i].srcData.value+"</a></td>";
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
        /*xAxis: [{
            axisLabel: {
                interval: 0,
                formatter: function (val) {
                    return val.split("").join("\n");
                }
            }
        }],*/
        series: [
            {
                name: "总金额",
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
     * 去掉实心的bar
     */
    optionBarExHeart = {
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
                        findAllPurchaseRequire();
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
                            table += '<td class="bgdd analyze_resource">'+axisData[i].name+'</td>';
                            table += "<td class='analyze_resource'><a href=\"javascript:;\" onclick=\"findRequireMoneyByOrg('"+axisData[i].id+"')\">"+axisData[i].value+"</a></td>";
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
                magicType: {
                    show: true,
                    type: ['pie', 'funnel'],
                    option: {
                        funnel: {
                            x: '25%',
                            width: '50%',
                            funnelAlign: 'center',
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
            name: '需求金额',
            radius: ['50%', '70%']
        }]
    };

    // 统计采购需求近五年采购金额
    $.ajax({
        url: globalPath + "/resAnalyze/selectNearFiveYearAllBudget.do",
        type: "POST", // 请求方式
        dataType: "json", // 返回格式为json
        success: function (data) {
            $("#nearFiveYearAllBudget").echartsTemplate({
                dataList: data,
                XField: 'name',
                YField: 'value',
                groupField: 'group',
                chartType: 'bar',
                XTitle: '年份',
                YTitle: '金额/万元',
                customEchartsOptions: function (res, dataList, option) {
                    return optionRequire;
                }
            });
            // 获取echart对应的对象
            var supplierOrg = $("#nearFiveYearAllBudget").echartsTemplate("getMyChart", null);
            supplierOrg.on('click', function (params) {
                window.location.href = globalPath + "/purchaser/viewDetamd.html?date=" + params.name;
            });
        }
    });

    // 各类型需求金额
    $.ajax({
        url: globalPath + "/resAnalyze/selectBudget.do",
        type: "POST", // 请求方式
        dataType: "json", // 返回格式为json
        success: function (data) {
            $("#typeRequireMoney").echartsTemplate({
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
            var supplierCateType = $("#typeRequireMoney").echartsTemplate("getMyChart", null);
            supplierCateType.on('click', function (params) {
                window.location.href = globalPath + "/purchaser/viewDetamd.html?planType=" + params.data.id;
            });
        }
    });

    // 各管理部门受理需求金额
    $.ajax({
        url: globalPath + "/resAnalyze/selectOrgBudget.do",
        type: "POST", // 请求方式
        dataType: "json", // 返回格式为json
        success: function (data) {
            $("#requireMoneyByOrg").echartsTemplate({
                dataList: data,
                YField: 'value',
                groupField: 'group',
                chartType: 'pie',
                customEchartsOptions: function (res, dataList, option) {
                    return optionBarExHeart;
                }
            });
            // 获取echart对应的对象
            var expertNature = $("#requireMoneyByOrg").echartsTemplate("getMyChart", null);
            expertNature.on('click', function (params) {
                window.location.href = globalPath + "/purchaser/viewDetamd.html?orgId=" + params.data.id;
            });
        }
    });
});

function findAllPurchaseRequire() {
    window.location.href = globalPath + "/purchaser/viewDetamd.html";
}

function findNearFiveYearAllBudgetRequire(name, value){
    if(value == 0){
        layer.msg("暂无数据");
        return;
    }
    window.location.href = globalPath + "/purchaser/viewDetamd.html?date=" + name;
}

function findTypeRequireMoney(id){
    window.location.href = globalPath + "/purchaser/viewDetamd.html?planType=" + id;
}

function findRequireMoneyByOrg(id){
    window.location.href = globalPath + "/purchaser/viewDetamd.html?orgId=" + id;
}