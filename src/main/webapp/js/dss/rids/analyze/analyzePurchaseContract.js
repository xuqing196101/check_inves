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
                        findAllPurchaseContract();
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
            name: '合同数量',
        }]
    };

    // 各采购机构签订合同数量及总金额
    optionOrgContract = {
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
                        findAllPurchaseContract();
                    }
                },
                dataView: {
                    show: true,
                    readOnly: false,
                    optionToContent: function (opt) {
                        var axisData = opt.series[0].data; //坐标数据
                        var bxisData = opt.series[1].data; //坐标数据
                        var table = '<div class="analyze_resource_div"><table border="1" class="analyze_resource_table"><tbody>';
                        table += "<tr>";
                        table += "<td class='bgdd analyze_resource'>采购机构</td>";
                        table += "<td class='bgdd analyze_resource'>合同总金额</td>";
                        table += "<td class='bgdd analyze_resource'>合同数量</td>";
                        for (var i = 0, l = axisData.length; i < l; i++) {
                            table += '<tr>';
                            table += '<td class="bgdd analyze_resource">'+axisData[i].srcData.name+'</td>';
                            table += "<td class='analyze_resource'><a href=\"javascript:;\" onclick=\"findPurContractTotal('"+axisData[i].srcData.id+"', "+axisData[i].srcData.value+")\">"+axisData[i].srcData.value+"</a></td>";
                            table += "<td class='analyze_resource'><a href=\"javascript:;\" onclick=\"findPurContractTotal('"+axisData[i].srcData.id+"', "+axisData[i].srcData.value+")\">"+bxisData[i].srcData.value+"</a></td>";
                            table += '</tr>';
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
                    /*data : [
                     {type : 'max', name: '最大值'},
                     {type : 'min', name: '最小值'}
                     ],*/
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

    // 各采购机构完成采购合同数量及总金额
    $.ajax({
        url: globalPath + "/resAnalyze/selectPurContractCountAndMoney.do",
        type: "POST", // 请求方式
        dataType: "json", // 返回格式为json
        success: function (data) {
            $("#purContractTotal").echartsTemplate({
                dataList: data,
                XField: 'name',
                YField: 'value',
                groupField: 'group',
                chartType: 'bar',
                XTitle: '机构',
                YTitle: '总金额',
                customEchartsOptions: function (res, dataList, option) {
                    return optionOrgContract;
                }
            });
            // 获取echart对应的对象
            var supplierOrg = $("#purContractTotal").echartsTemplate("getMyChart", null);
            supplierOrg.on('click', function (params) {
                window.location.href = globalPath + "/purchaseContract/readOnlyList.html?status=2&purchaseDepName=" + params.data.id;
            });
        }
    });

    //各产品类型签订采购合同数量
    /*$.ajax({
        url: globalPath + "/resAnalyze/selectpurContractByProductType.do",
        type: "POST", // 请求方式
        dataType: "json", // 返回格式为json
        success: function (data) {
            $("#purContractByProductType").echartsTemplate({
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
            var supplierCateType = $("#purContractByProductType").echartsTemplate("getMyChart", null);
            supplierCateType.on('click', function (params) {
                layer.msg("请稍等...");
                //window.location.href = globalPath + "/project/selectByProject.html?purchaseType="+ele.id;
            });
        }
    });*/
});

function findAllPurchaseContract(){
    window.location.href = globalPath + "/purchaseContract/readOnlyList.html?status=2";
}

function findPurContractTotal(id, value){
    if(value == 0){
        layer.msg("暂无数据");
        return;
    }
    window.location.href = globalPath + "/purchaseContract/readOnlyList.html?status=2&purchaseDepName=" + id;
}