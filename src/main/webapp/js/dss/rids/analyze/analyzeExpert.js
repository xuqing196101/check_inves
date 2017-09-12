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
                myTool: {
                    show: true,
                    title: '查询所有',
                    icon: 'image://' + globalPath + '/public/backend/images/pie.png',
                    onclick: function () {
                        findAllExpert();
                    }
                },
                mark: {
                    show: true
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
                            table += "<td class='analyze_resource'><a href=\"javascript:;\" onclick=\"findExpertCateType('"+axisData[i].id+"')\">"+axisData[i].value+"</a></td>";
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
            name: '专家数量',
        }]
    };

    // 专家企业性质
    optionNature = {
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
                            table += '<td class="bgdd analyze_resource">'+axisData[i].name+'</td>';
                            table += "<td class='analyze_resource'><a href=\"javascript:;\" onclick=\"findExpertNature('"+axisData[i].id+"')\">"+axisData[i].value+"</a></td>";
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
            name: '专家数量',
            radius: ['50%', '70%']
        }]
    };

    // 各采购机构专家数量
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
                            table += '<td class="bgdd analyze_resource">'+axisData[i].srcData.name+'</td>';
                            table += "<td class='analyze_resource'><a href=\"javascript:;\" onclick=\"findExpertOrg('"+axisData[i].srcData.id+"', "+axisData[i].srcData.value+")\">"+axisData[i].srcData.value+"</a></td>";
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
                name: "专家数量",
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

    // 各品目下的专家数量
    $.ajax({
        url: globalPath + "/resAnalyze/selectExpertCountByCategory.do",
        type: "POST", // 请求方式
        dataType: "json", // 返回格式为json
        success: function (data) {
            $("#expertCateType").echartsTemplate({
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
            var supplierCateType = $("#expertCateType").echartsTemplate("getMyChart", null);
            supplierCateType.on('click', function (params) {
                window.location.href = globalPath + "/expertQuery/readOnlyList.html?expertsTypeId=" + params.data.id;
            });
        }
    });

    // 专家军地数量
    $.ajax({
        url: globalPath + "/resAnalyze/selectExpertsCountByArmyType.do",
        type: "POST", // 请求方式
        dataType: "json", // 返回格式为json
        success: function (data) {
            $("#expertNature").echartsTemplate({
                dataList: data,
                YField: 'value',
                groupField: 'group',
                chartType: 'pie',
                customEchartsOptions: function (res, dataList, option) {
                    return optionNature;
                }
            });
            // 获取echart对应的对象
            var expertNature = $("#expertNature").echartsTemplate("getMyChart", null);
            expertNature.on('click', function (params) {
                window.location.href = globalPath + "/expertQuery/readOnlyList.html?expertsFrom=" + params.data.id;
            });
        }
    });


    // 统计不同组织机构下的专家
    $.ajax({
        url: globalPath + "/resAnalyze/selectExpByOrg.do",
        type: "POST", // 请求方式
        dataType: "json", // 返回格式为json
        success: function (data) {
            $("#expertOrg").echartsTemplate({
                dataList: data,
                XField: 'name',
                YField: 'value',
                groupField: 'group',
                chartType: 'bar',
                XTitle: '机构',
                YTitle: '数量/个',
                customEchartsOptions: function (res, dataList, option) {
                    return optionOrgSup;
                }
            });
            // 获取echart对应的对象
            var supplierOrg = $("#expertOrg").echartsTemplate("getMyChart", null);
            supplierOrg.on('click', function (params) {
                window.location.href = globalPath + "/expertQuery/readOnlyList.html?orgId=" + params.data.id;
            });
        }
    });
});

function findAllExpert(){
    window.location.href = globalPath + "/expertQuery/readOnlyList.html";
}
function findExpertCateType(id){
    window.location.href = globalPath + "/expertQuery/readOnlyList.html?expertsTypeId=" + id;
}
function findExpertNature(id){
    window.location.href = globalPath + "/expertQuery/readOnlyList.html?expertsFrom=" + id;
}
function findExpertOrg(id, value){
    if(value == 0){
        layer.msg("该采购入库专家数量为0");
        return;
    }
    window.location.href = globalPath + "/expertQuery/readOnlyList.html?orgId=" + id;
}