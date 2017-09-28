$(function () {
    list(1);
});

/**
 * 封装数据列表
 * @param page
 */
function list(curr) {
    var index = layer.load(0, {
        shade: [0.1, '#fff'],
        offset: ['40%', '50%']
    });
    $.ajax({
        url: globalPath + "/cacheManage/cachemanageAjax.do",
        data: {
            page: curr
        },
        type: "post",
        dataType: "json",
        success: function (res) {
            if (res.status == 200) {
                var obj = res.data;
                loadList(obj.list, obj.pageNum, obj.pageSize);
                loadPage(obj.pages, obj.total, obj.startRow, obj.endRow, curr);
            }
            layer.close(index);
        }
    });
}

/**
 * 分页
 * @param pages 总页数
 * @param total 总条数
 * @param startRow 开始索引
 * @param endRow 结束索引
 * @param curr 当前页
 */
function loadPage(pages, total, startRow, endRow, curr) {
    laypage({
        cont: $("#pagediv"),
        pages: pages,
        skin: '#2c9fA6',
        skip: true,
        total: total,
        startRow: startRow,
        endRow: endRow,
        groups: pages >= 5 ? 5 : pages,
        curr: curr,
        jump: function (e, first) {
            if (!first) {
                list(e.curr);
            }
        }
    });
}
/**
 * 遍历数据集合
 * @param data
 * @param pageNum
 * @param pageSize
 */
function loadList(dataArr, pageNum, pageSize) {
    // 将原先的内容清空
    $("#dataTable tbody").empty();
    if (dataArr != null && dataArr.length > 0) {
        for (var i = 0; i < dataArr.length; i++) {
            loadData(dataArr[i], i, pageNum, pageSize);
        }
    }
}

/**
 * 加载数据
 * @param data
 * @param pageNum
 * @param pageSize
 */
function loadData(data, index, pageNum, pageSize) {
    var html = "<tr>"
        + " <td class='tc'><input onclick='check()' type='checkbox' name='chkItem' value='" + data.name + "," + data.type + "'</td>"
        + " <td class='tc'>" + ((index + 1) + (pageNum - 1) * pageSize) + "</td>"
        + " <td><a href=\"javascript:;\" onclick=\"detail('" + data.name + "," + data.type + "')\">" + data.name + "</a></td>"
        + " <td class='tl'>" + data.type + "</td>"
        + "<td class='tl' id='" + data.name + "'>"
    if (data.time == -1) {
        html += " 无"
    } else {
        html += " <script type=\"text/javascript\">addTimer('" + data.name + "', '" + data.time + "')</script>"
    }
    html += "</tr>";
    $("#dataTable tbody").append(html);
}

/** 全选全不选 */
function selectAll() {
    var checklist = document.getElementsByName("chkItem");
    var checkAll = document.getElementById("checkAll");
    if (checkAll.checked) {
        for (var i = 0; i < checklist.length; i++) {
            checklist[i].checked = true;
        }
    } else {
        for (var j = 0; j < checklist.length; j++) {
            checklist[j].checked = false;
        }
    }
}

/** 单选 */
function check() {
    var count = 0;
    var checklist = document.getElementsByName("chkItem");
    var checkAll = document.getElementById("checkAll");
    for (var i = 0; i < checklist.length; i++) {
        if (checklist[i].checked == false) {
            checkAll.checked = false;
            break;
        }
        for (var j = 0; j < checklist.length; j++) {
            if (checklist[j].checked == true) {
                checkAll.checked = true;
                count++;
            }
        }
    }
}

/**
 * 选中清除缓存
 */
function clearSignalCache() {
    var id = [];
    var status = "";
    var data = "";
    $('input[name="chkItem"]:checked').each(function () {
        id.push($(this).val());
        data = id[0].split(",");
        status = $(this).parent().next().text();
    });
    if (id.length == 1) {
        layer.confirm('确认要清除缓存吗？', {
            btn: ['是', '否']
            //按钮
        }, function () {
            $.ajax({
                url: globalPath + "/cacheManage/clearStringCache.do",
                data: {
                    cacheKey: data[0],
                    cacheType: data[1]
                },
                type: "POST",
                dataType: "json",
                success: function (data) {
                    // 成功后提示
                    layer.confirm(data.data, {
                            btn: ['确定']
                        }, function () {
                            location.reload();
                        }
                    )
                }
            });
        }, function () {
            layer.close();
        });
    } else if (id.length > 1) {
        layer.alert("只能选择一个", {
            offset: ['222px', '390px'],
            shade: 0.01
        });
    } else {
        layer.alert("请选择需要删除缓存的信息", {
            offset: ['222px', '390px'],
            shade: 0.01
        });
    }
}

/**
 * 清除所有缓存
 */
function clearAllCache() {
    layer.confirm('确认要全部清除缓存吗？', {
        btn: ['是', '否']
        //按钮
    }, function () {
        $.ajax({
            url: globalPath + "/cacheManage/clearAllCache.do",
            type: "POST",
            dataType: "json",
            success: function (data) {
                // 成功后提示
                layer.confirm(data.data, {
                        btn: ['确定']
                    }, function () {
                        location.reload();
                    }
                )
            }
        });
    }, function () {
        layer.close();
    });
}

/**
 * 时间倒计时
 */
var addTimer = function () {
    var list = [],
        interval;
    return function (id, time) {
        if (!interval)
            interval = setInterval(go, 1000);
        list.push({ele: document.getElementById(id), time: time});
    }

    function go() {
        for (var i = 0; i < list.length; i++) {
            list[i].ele.innerHTML = getTimerString(list[i].time ? list[i].time -= 1 : 0);
            if (!list[i].time)
                list.splice(i--, 1);
        }
    }

    function getTimerString(time) {
        var not0 = !!time,
            d = Math.floor(time / 86400),
            h = Math.floor((time %= 86400) / 3600),
            m = Math.floor((time %= 3600) / 60),
            s = time % 60;
        if (not0)
            return "<font style='color:red'>还有" + d + "天" + h + "小时" + m + "分" + s + "秒</font>";
        else return "时间到";
    }
}();

// 获取对应的值
function detail(obj) {
    var types = obj.split(",");
    var key = types[0];
    var type = types[1];
    layer.open({
        type: 2,
        title: '详情',
        area: [$(document).width() - 100 + 'px', '400px'],
        content: globalPath + '/cacheManage/getValueByKey.html?cacheKey=' + key + "&cacheType=" + type
    });
}