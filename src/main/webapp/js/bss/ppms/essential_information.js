/**
 * 列表数据
 * @param datas
 */
function loadUpload(datas){
    $("#loadUpload").empty();
    var data = datas.data;
    var relName = datas.msg;
    if(data != null && data.length > 0){
        for(var i = 0; i < data.length; i++){
            loadUploadData(data[i], i, relName);
        }
    }

}
/**
 * 新增加载数据
 * @param data
 * @returns
 */
function loadUploadData(data, i, relName){
    var html = "<tr> "
            + " <td class='tc w30'><input type='checkbox' value="+data.id+" name='chkItem' onclick='check()'></td>"
            + " <td class='tc w50'>"+(i+1)+"</td>"
            + " <td class='tl'><a href='javascript:void(0)' onclick=\"views('"+data.typeId+"')\">"+data.name+"</a></td>"
            + " <td class='tl' onclick=\"views('"+data.typeId+"')\">"+relName+"</td>"
            + " <td>"+timestampToDate('yyyy-MM-dd hh:mm:ss', data.createDate)+"</td>"
            html += "</tr>";
    $("#loadUpload").append(html);
}

{/*<c:forEach items="${uploadFiles}" var="data" varStatus="vs">
  <tr>
    <td class="tc w30">
      <input type="checkbox" value="${data.id }" name="chkItem" onclick="check()">
    </td>
    <td class="tc w50">${vs.index+1}</td>
    <td class="tl">
      <a href="javascript:void(0)" onclick="views('${data.typeId}');">${data.name}</a>
    </td>
    <td class="tl" onclick="views('${data.typeId}')">${user.relName}</td>
    <td class="tl">
      <fmt:formatDate type='date' value='${data.createDate}' pattern=" yyyy-MM-dd HH:mm:ss " />
    </td>
  </tr>
</c:forEach>*/}

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

/**
 * 加载附件
 */
function commonLoadFile(){
    $.ajax({
        type: 'POST',
        url: globalPath + "/project/findUploadFileOfEssential.do",
        async: false,
        dataType: 'json',
        data : {
            "projectId": projectIdUpload
        },
        success: function (data) {
            loadUpload(data);
        }
    });
}