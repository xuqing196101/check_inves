<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML >
<html>
<head>
    <%@ include file="../../../common.jsp" %>
    <base href="${pageContext.request.contextPath}/">

    <title>站内消息</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <link href="${pageContext.request.contextPath}/public/supplier/css/supplieragents.css"
          media="screen" rel="stylesheet">
    <script type="text/javascript">
        $(function () {
            laypage({
                cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
                pages: "${listStationMessage.pages}", //总页数
                skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
                skip: true, //是否开启跳页
                groups: "${listStationMessage.pages}" >= 3 ? 3 : "${listStationMessage.pages}", //连续显示分页数
                curr: function () { //通过url获取当前页，也可以同上（pages）方式获取
// 		        var page = location.search.match(/page=(\d+)/);
                    return "${listStationMessage.pageNum}";
                }(),
                jump: function (e, first) { //触发分页后的回调
                    if (!first) { //一定要加此判断，否则初始时会无限刷新
                        $("#pages").val(e.curr);
                        $("form:first").submit();
                    }
                }
            });
        });

        function extract(id, btn) {
//    layer.open({
//           type: 2, //page层
//           area: ['90%', '50%'],
//           title: '供应商抽取 项目名称： ${projectName}',
//           closeBtn: 1,
//           shade:0.01, //遮罩透明度
//           shadeClose: true,
//           offset: '30px',
//           move:false,
//           content: '${pageContext.request.contextPath}/SupplierExtracts/extractCondition.html?cId='+id,
//           end:function(){
//            window.location.reload();
//           }
//         });
            window.location.href = "${pageContext.request.contextPath}/SupplierExtracts/Extraction.html?id=" + id + "&&typeclassId=${typeclassId}";
//    $(btn).next().remove();
//   $(btn).parent().parent().find("td:eq(2)").html("抽取中");
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
        function edit() {
            var id = [];
            $('input[name="chkItem"]:checked').each(function () {
                id.push($(this).val());
            });
            if (id.length == 1) {
                window.location.href = "${pageContext.request.contextPath}/StationMessage/showStationMessage.do?id=" + id + "&&type='edit'";
            } else if (id.length > 1) {
                layer.alert("只能选择一个", {offset: ['222px', '390px'], shade: 0.01});
            } else {
                layer.alert("请选择需要修改的用户", {offset: ['222px', '390px'], shade: 0.01});
            }
        }
        function del() {
            var ids = [];
            $('input[name="chkItem"]:checked').each(function () {
                ids.push($(this).val());
            });
            if (ids.length > 0) {
                layer.confirm('您确定要删除吗?', {title: '提示', offset: ['222px', '360px'], shade: 0.01}, function (index) {
                    layer.close(index);
                    window.location.href = "${pageContext.request.contextPath}/StationMessage/deleteSoftSMIsDelete.do?ids=" + ids;
                });
            } else {
                layer.alert("请选择要删除的用户", {offset: ['222px', '390px'], shade: 0.01});
            }
        }
        function add() {
            window.location.href = "${pageContext.request.contextPath}/StationMessage/showInsertSM.do";
        }

        function continues(id) {
            window.location.href = "${pageContext.request.contextPath}/SupplierExtracts/conditions.do?id=" + id;
        }
        function operation(select) {
            layer.confirm('确定本次操作吗？', {
                btn: ['确定', '取消'], offset: ['100px', '200px'], shade: 0.01
            }, function (index) {
                var strs = new Array();
                var v = select.value;
                strs = v.split(",");
                layer.close(index);
                if (strs[2] == "3") {
                    layer.prompt({
                        formType: 2,
                        shade: 0.01,
                        title: '不参加理由'
                    }, function (value, index, elem) {
                        ajaxs(select.value, value);
                        layer.close(index);
                    });
                } else {
                    select.disabled = true;
                    ajaxs(select.value, '');
                }
            }, function (index) {
                layer.close(index);
            });
        }

        function ajaxs(id, v) {
            $.ajax({
                type: "POST",
                url: "${pageContext.request.contextPath}/SupplierExtracts/resultextract.do",
                data: {id: id, reason: v},
                dataType: "json",
                success: function (data) {
                    list = data;
                    if ('sccuess' == list) {
                        alert("ss");
                    } else {
                        var tex = '';
                        for (var i = 0; i < list.length; i++) {
                            if (list[i] != null) {
                                if (list[0] != null) {
                                    var html = "";
                                    $("#extcontype").empty();
                                    for (var l = 0; l < list[0].conType.length; l++) {

                                        if (list[0].conType[l].categoryName != null) {
                                            var cName = list[0].conType[l].categoryName.replace("^", ",");
                                            cName = cName.substring(0, cName.length - 1);
                                            html += "抽取品目:" + cName + ",";
                                        }
                                        if (list[0].conType[l].isMulticondition == 1) {
                                            html += "满足一个条件,";
                                        } else if (list[0].conType[l].isMulticondition == 2) {
                                            html += "满足多个条件,";
                                        }
                                        html += "抽取数量:" + list[0].conType[l].alreadyCount + "/" + list[0].conType[l].supplieCount;
                                        html += "<br/>";
                                    }
                                    $("#extcontype").append(html);
                                }

                                tex += "<tr class='cursor'>" +
                                    "<td class='tc' onclick='show();'>" + (i + 1) + "</td>" +
                                    "<td class='tc' onclick='show();'>" + list[i].supplier.supplierName + "</td>" +
                                    "<td class='tc' onclick='show();'>" + list[i].supplier.supplierName + "</td>" +
                                    "<td class='tc' onclick='show();'>" + list[i].supplier.contactName + "</td>" +
                                    "<td class='tc' onclick='show();'>" + list[i].supplier.contactTelephone + "</td>" +
                                    "<td class='tc' onclick='show();'>" + list[i].supplier.contactMobile + "</td>" +
                                    " <td class='tc' >" +
                                    "<select id='select' onchange='operation(this);'>";

                                if (list[i].operatingType == 1) {
                                    tex += "<option value='" + list[i].id + "," + list[i].supplierConditionId + ",1' selected='selected' disabled='disabled'>能参加</option>";
                                } else if (list[i].operatingType == 2) {
                                    tex += "<option value='" + list[i].id + "," + list[i].supplierConditionId + ",1'>能参加</option>" +
                                        "<option value='" + list[i].id + "," + list[i].supplierConditionId + ",3'>不能参加</option>" +
                                        "<option selected='selected' value='" + list[i].id + "," + list[i].supplierConditionId + ",2'>待定</option>";
                                } else if (list[i].operatingType == 3) {
                                    tex += "<option value='" + list[i].id + "," + list[i].supplierConditionId + ",1' selected='selected' disabled='disabled'>不能参加</option>";
                                } else {
                                    tex += "<option >请选择</option>" +
                                        "<option value='" + list[i].id + "," + list[i].supplierConditionId + ",1'>能参加</option>" +
                                        "<option value='" + list[i].id + "," + list[i].supplierConditionId + ",3'>不能参加</option>" +
                                        "<option  value='" + list[i].id + "," + list[i].supplierConditionId + ",2'>待定</option>";
                                }
                                tex += "</select>" +
                                    "</td>" +
                                    "</tr>";
                            }
                        }
                        $('#tbody tr:lt(' + list.length + ')').remove();
                        $("#tbody").prepend(tex);
                    }
                }
            });
        }
    </script>
<body>

<div>
    <!--               <h2 class="count_flow"><i>1</i>抽取条件</h2> -->
    <ul class="ul_list">
        <li class="col-md-3 col-sm-6 col-xs-12 pl15">
            <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span>所在地区：</span>
            <div class="input-append input_group col-sm-12 col-xs-12 p0">
                <select class="w93 input_group fl" id="area" onchange="areas();">
                </select> <select name="extractionSites" class=" w93 input_group fl" id="city"></select>
            </div>
        </li>
        <li class="col-md-4 col-sm-6 col-xs-12">
            <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>品目：</span>
            <div class="input-append input_group col-sm-12 col-xs-12 p0">
                <input class="input_group " readonly id="extCategoryName" onclick="opens(this);" type="text">
                <span class="add-on">i</span>
                <div class=" f12 red tip w150 fl" id="dCategoryName"></div>
            </div>
        </li>
        <li class="col-md-4 col-sm-6 col-xs-12">
            <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>抽取数量：</span>
            <div class="input-append input_group col-sm-12 col-xs-12 p0">
                <input class="input_group" id="eCount" type="text">
                <span class="add-on">i</span>
                <div class=" f12 red tip w150 fl" id="dCount"></div>
            </div>
        </li>

        <div class=" w300 pl20 mt10">
            <button class="btn btn-windows add"
                    id="save" onclick="extract('12wde');" type="button">继续抽取
            </button>
        </div>
        <div align="right" class="padding-10">
            <div class="col-md-12  f12 red tip" id="array"></div>

        </div>
    </ul>
</div>

</body>
</html>
