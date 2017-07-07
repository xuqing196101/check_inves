/**
 * 初始化  页签 分组显示
 */
function init(){
    var i=0;
    var liclass;
    var supplierTypes=$("#supplierTypes").val();
    if(!supplierTypes){
        return;
    }
    if(supplierTypes.indexOf('PRODUCT') !='-1'){
        i++;
        $("#tab_1").addClass("active in");
        $("#page_ul_id").append("<li class=\"active\"   id=\"productId\"> "+
            " <a aria-expanded=\"true\" href=\"#tab_1\" onclick=\"initDivHide('tab_1','productId','PRODUCT','content_1')\" data-toggle=\"tab\">物资-生产型专业信息</a>"+
            " </li>");
        initDivHide('tab_1','productId','PRODUCT','content_1');
    }
    if(supplierTypes.indexOf('SALES')  !='-1'){
        i++;
        if(i==0){
            $("#tab_2").addClass("active in");
            liclass=" class=\"active\"";
        }else{
            liclass=" class='activeliCountEng'  ";
        }
        $("#page_ul_id").append("<li "+liclass+" id=\"salesId\"> "+
            " <a aria-expanded=\"false\" href=\"#tab_2\" onclick=\"initDivHide('tab_2','salesId','SALES','content_2')\"  data-toggle=\"tab\">物资-销售型专业信息</a>"+
            " </li>");
        if(i==0){
            initDivHide('tab_2','productId','SALES','content_2');
        }
    }
    if(supplierTypes.indexOf('PROJECT')  !='-1'){
        i++;
        if(i==0){
            $("#tab_3").addClass("active in");
            liclass=" class=\"active\" ";
        }else{
            liclass=" class='activeliCountEng' ";
        }
        $("#page_ul_id").append(" <li "+liclass+" id='projectId' > "+
            " <a aria-expanded=\"false\" href=\"#tab_3\" onclick=\"initDivHide('tab_3','projectId','PROJECT','content_3')\" data-toggle=\"tab\">工程专业信息</a>"+
            " </li>");
        if(i==0){
            initDivHide('tab_3','projectId','PROJECT','content_3');
        }
    }
    if(supplierTypes.indexOf('SERVICE')  !='-1'){
        i++;
        if(i==0){
            $("#tab_4").addClass("active in");
            liclass=" class=\"active\" ";
        }else{
            liclass=" class='activeliCountEng' ";
        }
        $("#page_ul_id").append("<li "+liclass+" id=\"serviecId\" >"+
            " <a aria-expanded=\"false\" href=\"#tab_4\" onclick=\"initDivHide('tab_4','serviecId','SERVICE','content_4')\" data-toggle=\"tab\">服务专业信息</a>"+
            " </li>");
        if(i==0){
            initDivHide('tab_4','serviecId','SERVICE','content_4');
        }
    }
}

//第一个 tab 初始化时
function initDivHide(showId,type,typeId,tableId){
    //模糊匹配 隐藏
    $("div[id^='tab_']").hide();
    $("#"+showId+"").show();
    findDate(typeId,tableId);
}

//获取 数据
function findDate(type,tablerId) {
    var index = layer.load(0, {
        shade : [ 0.1, '#fff' ],
        offset : [ '40%', '50%' ]
    });
    $("[name=supplierType]").val(type);
    $.ajax({
        type : "POST",
        url : globalPath + "/supplierAudit/overAptitude.do",
        data : $("#form_id").serializeArray(),
        success : function(obj) {
            if (obj) {
                listPage(obj.data.pages, obj.data.total, obj.data.startRow,
                    obj.data.endRow, obj.data.pageNum,type);
                showData(obj.data,tablerId,type);
            } else {
                layer.msg(obj.msg);
            }
            layer.close(index);
        },
        error : function(data) {
            layer.msg("请求异常!");
            layer.close(index);
        },
    });
}


/** 分页* */
function listPage(pages, total, startRow, endRow, pageNum,type,tablerId) {
    laypage({
        cont : $("#pagediv"), // 容器。值支持id名、原生dom对象，jquery对象,
        pages : pages, // 总页数
        skin : '#2c9fA6', // 加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
        skip : true, // 是否开启跳页
        total : total,
        startRow : startRow,
        endRow : endRow,
        groups : pages >= 3 ? 3 : pages, // 连续显示分页数
        curr : function() { // 通过url获取当前页，也可以同上（pages）方式获取
            return pageNum;
        }(),
        jump : function(e, first) { // 触发分页后的回调
            if (!first) { // 一定要加此判断，否则初始时会无限刷新
                $("#page").val(e.curr);
                findDate(type,tablerId);
            }
        }
    });
}

//封装填充 数据
function showData(obj,tablerId,typeId) {
    $("#"+tablerId+" tbody").empty();
    $(obj.list).each(
        function(index, item) {
            var ind=((index+1)+(obj.pageNum-1)*(obj.pageSize));
            // 根据类型 判断
            $("#"+tablerId+" tbody").append("<tr>"+
                "<td class=\"tc info\">" + ind+ "</td>"+
                "<td>"+isNull(item.rootNode)+"</td>"+
                "<td>"+isNull(item.firstNode)+"</td>"+
                "<td>"+isNull(item.secondNode)+"</td>"+
                "<td>"+isNull(item.thirdNode)+"</td>"+
                "<td>"+isNull(item.fourthNode)+"</td>"+
                "</tr>"
            );
        });
}

/**
 * 判断单元格内容是否为空
 * @param obj
 * @returns {*}
 */
function isNull(obj){
    if(obj){
        return obj;
    }else{
        return "";
    }
}

/**
 * 时间戳转时间格式
 * @param format
 * @param timestamp
 * @returns
 */
function timestampToDate(format, timestamp){
    var date = new Date(timestamp);
    return date.format(format);
}

/**
 * 时间格式化
 * @param format
 * @returns
 */
Date.prototype.format = function(fmt){
    var o = {
        "M+" : this.getMonth()+1,                 //月份
        "d+" : this.getDate(),                    //日
        "h+" : this.getHours(),                   //小时
        "m+" : this.getMinutes(),                 //分
        "s+" : this.getSeconds(),                 //秒
        "q+" : Math.floor((this.getMonth()+3)/3), //季度
        "S"  : this.getMilliseconds()             //毫秒
    };
    if(/(y+)/.test(fmt)) {
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    }
    for(var k in o) {
        if(new RegExp("("+ k +")").test(fmt)){
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        }
    }
    return fmt;
}