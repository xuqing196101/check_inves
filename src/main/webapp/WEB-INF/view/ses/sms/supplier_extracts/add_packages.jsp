<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en">
<!--<![endif]-->
<head>
    <%@ include file="/WEB-INF/view/common/tags.jsp" %>
    <%@ include file="../../../common.jsp"%>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/public/supplier/css/supplieragents.css"
          type="text/css">

    <!-- Meta -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <script type="text/javascript">
        /**添加包信息*/
        function add(){
            $.ajax({
                cache: true,
                type: "POST",
                dataType : "json",
                url:'${pageContext.request.contextPath}/SupplierExtracts/addPackage.do',
                data:$('#form1').serialize(),// 你的formid
                async: false,
                success: function(data) {
                    if(data.status != 'ERROR'){
                        /* parent.$('#packageId').val(data.status);
                         parent.$('#packageName').val(data.packagesName);
                         */
                        var cityObj = parent.$("#packageName");
                        cityObj.attr("value", data.packagesName);
                        cityObj.attr("title", data.packagesName);
                        parent.$("#packageId").attr("value",data.status);

                        var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                        parent.layer.close(index);
                    }else{
                        layer.msg("不能为空");
                    }
                },
                error:function(data){
                    layer.msg("不能为空");
                }
            });
            //


        }


        /**添加*/
        function addBranch(btn){
            var html = "<tr>"+
                "<td class='tc'><input type='text' name='packagesName' /> </td>"+
                "<td class='tc'>"+
                "<input type='button' onclick='addBranch(this)' class='btn list_btn' value='十'/>"+
                "<input type='button' onclick='delBranch(this)' class='btn list_btn' value='一'/>"+
                "</td>"+
                "</tr>";

            $("#tbody").append(html);
        }

        /**删除*/
        function delBranch(btn){
            var le=$("#tbody tr").length;
            if(le==1){
                $("#tbody").find(":input").not(":button,:submit,:reset,:hidden").val("");
            }else{
                $(btn).parent().parent().remove();
            }

        }

    </script>
</head>
<body>
<!-- 修改订列表开始-->
<div class="container margin-top-30">
    <form action="${pageContext.request.contextPath}/SupplierExtracts/listSupplier.do"
          method="post" id="form1">
        <input name="projectId" type="hidden" value="${projectId}" />
        <table class="table table-bordered table-condensed table_input left_table">
            <thead>
            <tr>
                <th class="info"><span class="red" id="red0">*</span>包名</th>
                <th class="info">操作</th>
            </tr>
            </thead>
            <tbody id="tbody">
            <c:if test="${fn:length(list)==0}">
                <tr>
                    <td class="tc"><input type="text" name="packagesName"  /> </td>
                    <td class="tc w90">
                        <input type="button" onclick="addBranch(this)" class="btn list_btn" value="十"/>
                        <input type="button" onclick="delBranch(this)" class="btn list_btn" value="一"/>
                    </td>
                </tr>
            </c:if>
            </tbody>
        </table>
    </form>
</div>
</body>
</html>
