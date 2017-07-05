<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>

<html>
<head>
    <%@ include file="/WEB-INF/view/common.jsp" %>
    <%@ include file="/WEB-INF/view/common/webupload.jsp" %>
    <title>详细信息</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
<%-- <script src="${pageContext.request.contextPath}/js/ses/sms/supplier_audit/merge_aptitude.js"></script> --%>
    <style type="text/css">
        input {
            cursor: pointer;
        }

        textarea {
            cursor: pointer;
        }
    </style>
    <script type="text/javascript">
        /* $(function() {
         layer.alert('点击审核项,弹出不通过理由框！', {
         title: '审核操作说明：',
         skin: 'layui-layer-molv', //样式类名
         closeBtn: 0,
         offset: '100px',
         shift: 4 //动画类型
         });
         }); */

        //隐藏叉
        $(function () {
            $(":input").each(function () {
                /* $(this).parent("div").find("div").hide(); */
                var onMouseMove = "this.style.background='#E8E8E8'";
                var onmouseout = "this.style.background='#FFFFFF'";
                $(this).attr("onMouseMove", onMouseMove);
                $(this).attr("onmouseout", onmouseout);
            });

            $("li").each(function () {
                $(this).find("p").hide();
            });


            $("td").each(function () {
                $(this).find("a").eq(0).hide();
            });
        });

        //审核input框
        function reason(obj) {
            var supplierId = $("#id").val();
            var auditField = obj.id;
            ;
            var auditContent;
            var auditFieldName;
            var html = "<a class='abolish'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>";
            $("#" + obj.id + "").each(function () {
                auditFieldName = $(this).parents("li").find("span").text().replace("：", "").trim();
                auditContent = $(this).parents("li").find("input").val();
                if (auditField == "businessScope" || auditField == "description" || auditField == "purchaseExperience") {
                    auditContent = $(this).parents("li").find("textarea").text();
                }
            });
            var index = layer.prompt({
                    title: '请填写不通过的理由：',
                    formType: 2,
                    offset: '100px',
                    maxlength: '100'
                },
                function (text) {
                    var text = trim(text);
                    if (text != null && text != "") {
                        $.ajax({
                            url: "${pageContext.request.contextPath}/supplierAudit/auditReasons.html",
                            type: "post",
                            dataType: "json",
                            data: {
                                "auditType": "basic_page",
                                "auditFieldName": auditFieldName,
                                "auditContent": auditContent,
                                "suggest": text,
                                "supplierId": supplierId,
                                "auditField": auditField
                            },
                            success: function (result) {
                                result = eval("(" + result + ")");
                                if (result.msg == "fail") {
                                    layer.msg('该条信息已审核过！', {
                                        shift: 6, //动画类型
                                        offset: '100px'
                                    });
                                }
                            }
                        });
                        $(obj).after(html);
                        $("#" + obj.id + "").css('border-color', '#FF0000'); //边框变红色
                        layer.close(index);
                    } else {
                        layer.msg('不能为空！', {offset: '100px'});
                    }
                });
        }


        /* function reason(id, auditField) {
         var offset = "";
         if (window.event) {
         e = event || window.event;
         var x = "";
         var y = "";
         x = e.clientX + 20 + "px";
         y = e.clientY + 20 + "px";
         offset = [y, x];
         } else {
         offset = "200px";
         }
         var w = window.screen.height / 2 + "px";

         var supplierId = $("#id").val();
         var id2 = id + "2";
         var id3 = id + "3";
         var auditFieldName = $("#" + id2 + "").text().replace("：", ""); //审批的字段名字
         var auditContent = document.getElementById("" + id + "").value; //审批的字段内容
         var index = layer.prompt({
         title: '请填写不通过的理由：',
         formType: 2,
         offset: '100px',
         },
         function(text) {
         $.ajax({
         url: "${pageContext.request.contextPath}/supplierAudit/auditReasons.html",
         type: "post",
         dataType: "json",
         data: "auditType=basic_page" + "&auditFieldName=" + auditFieldName + "&auditContent=" + auditContent + "&suggest=" + text + "&supplierId=" + supplierId + "&auditField=" + auditField,
         success: function(result) {
         result = eval("(" + result + ")");
         if(result.msg == "fail") {
         layer.msg('该条信息已审核过！', {
         shift: 6, //动画类型
         offset: '100px'
         });
         }
         }
         });
         $("#" + id3 + "").show();
         $("#" + id3 + "").parents("li").find("input").css("padding-right", "30px");
         layer.close(index);
         $("input[name='auditType']").val(auditType);
         $("input[name='auditField']").val(auditField);
         $("input[name='auditContent']").val(auditContent);
         $("input[name='suggest']").val(text);
         $("#save_reaeon").submit();
         });
         } */

        function reason1(ele, auditField) {
            var supplierId = $("#id").val();
            var auditFieldName = $(ele).parents("li").find("span").text().replace("：", "").replace("view", ""); //审批的字段名字
            var index = layer.prompt({
                title: '请填写不通过的理由：',
                formType: 2,
                offset: '100px',
                maxlength: '100',
            }, function (text) {
                var text = trim(text);
                if (text != null && text != "") {
                    $.ajax({
                        url: "${pageContext.request.contextPath}/supplierAudit/auditReasons.html",
                        type: "post",
                        data: "&auditFieldName=" + auditFieldName + "&suggest=" + text + "&supplierId=" + supplierId + "&auditType=basic_page" + "&auditContent=附件" + "&auditField=" + auditField,
                        dataType: "json",
                        success: function (result) {
                            result = eval("(" + result + ")");
                            if (result.msg == "fail") {
                                layer.msg('该条信息已审核过！', {
                                    shift: 6, //动画类型
                                    offset: '100px'
                                });
                            }
                        }
                    });
                    $(ele).parents("li").find("p").show(); //显示叉
                    layer.close(index);
                } else {
                    layer.msg('不能为空！', {offset: '100px'});
                }
            });
        }

        //审核列表
        function auditList(id, str, type) {
            var supplierId = $("#id").val();
            var auditContent;
            var auditFieldName;
            if (type == "售后服务机构") {
                auditContent = str + "分支机构信息"; //审批的字段内容
                auditFieldName = "售后服务机构";
            }
            if (type == "地址信息") {
                auditContent = str + "地址信息"; //审批的字段内容
                auditFieldName = "地址信息";
            }
            var index = layer.prompt({
                    title: '请填写不通过的理由：',
                    formType: 2,
                    offset: '100px',
                    maxlength: '100'
                },
                function (text) {
                    var text = trim(text);
                    if (text != null && text != "") {
                        $.ajax({
                            url: "${pageContext.request.contextPath}/supplierAudit/auditReasons.html",
                            type: "post",
                            data: {
                                "auditType": "basic_page",
                                "auditFieldName": auditFieldName,
                                "auditContent": auditContent,
                                "suggest": text,
                                "supplierId": supplierId,
                                "auditField": id
                            },
                            dataType: "json",
                            success: function (result) {
                                result = eval("(" + result + ")");
                                if (result.msg == "fail") {
                                    layer.msg('该条信息已审核过！', {
                                        shift: 6, //动画类型
                                        offset: '100px'
                                    });
                                }
                            }
                        });
                        $("#" + id + "_hidden").hide();
                        $("#" + id + "_show").show();
                        layer.close(index);
                    } else {
                        layer.msg('不能为空！', {offset: '100px'});
                    }
                });
        }

        function nextStep() {
            $("#form_id").submit();
        }

        //文件下載
        function downloadFile(fileName) {
            $("input[name='fileName']").val(fileName);
            $("#download_form_id").submit();
        }

        //为只读
        $(function () {
            $(":input").each(function () {
                $(this).attr("readonly", "readonly");
            });
        });

        // 提示之前的信息
        function isCompare(field) {
            var supplierId = $("#id").val();
            $.ajax({
                url: "${pageContext.request.contextPath}/supplierAudit/showModify.do",
                data: {"supplierId": supplierId, "beforeField": field, "modifyType": "basic_page", "listType": "0"},
                async: false,
                success: function (result) {
                    layer.tips("修改前:" + result, "#" + field, {
                        tips: 3
                    });
                }
            });
        }

        //删除左右两端的空格
        function trim(str) {
            return str.replace(/(^\s*)|(\s*$)/g, "");
        }

        // 提示修改之前的信息(列表)
        function showContent(field, id, type) {
            var supplierId = $("#id").val();
            var showId = field + "_" + id;
            $.ajax({
                url: "${pageContext.request.contextPath}/supplierAudit/showModify.do",
                data: {
                    "supplierId": supplierId,
                    "beforeField": field,
                    "modifyType": "basic_page",
                    "relationId": id,
                    "listType": type
                },
                async: false,
                success: function (result) {
                    layer.tips("修改前:" + result, "#" + showId,
                        {
                            tips: 3
                        });
                }
            });
        }
        
        //暂存
        function zhancun(){
         var supplierId = $("#id").val();
          $.ajax({
            url: "${pageContext.request.contextPath}/supplierAudit/temporaryAudit.do",
            dataType: "json",
            data:{supplierId : supplierId},
            success : function (result) {
                layer.msg(result, {offset : [ '100px' ]});
            },error : function(){
              layer.msg("暂存失败", {offset : [ '100px' ]});
            }
          });
        }
    </script>

    <script type="text/javascript">
          function jump(str) {
            var action;
            if (str == "essential") {
                action = "${pageContext.request.contextPath}/supplierAudit/essential.html";
            }
            if (str == "financial") {
                action = "${pageContext.request.contextPath}/supplierAudit/financial.html";
            }
            if (str == "shareholder") {
                action = "${pageContext.request.contextPath}/supplierAudit/shareholder.html";
            }
            if (str == "materialProduction") {
                action = "${pageContext.request.contextPath}/supplierAudit/materialProduction.html";
            }
            if (str == "materialSales") {
                action = "${pageContext.request.contextPath}/supplierAudit/materialSales.html";
            }
            if (str == "engineering") {
                action = "${pageContext.request.contextPath}/supplierAudit/engineering.html";
            }
            if (str == "serviceInformation") {
                action = "${pageContext.request.contextPath}/supplierAudit/serviceInformation.html";
            }
            if (str == "items") {
                action = "${pageContext.request.contextPath}/supplierAudit/items.html";
            }
            if (str == "aptitude") {
                action = "${pageContext.request.contextPath}/supplierAudit/aptitude.html";
            }
            if (str == "contract") {
                action = "${pageContext.request.contextPath}/supplierAudit/contract.html";
            }
            if (str == "applicationForm") {
                action = "${pageContext.request.contextPath}/supplierAudit/applicationForm.html";
            }
            if (str == "reasonsList") {
                action = "${pageContext.request.contextPath}/supplierAudit/reasonsList.html";
            }
            if (str == "supplierType") {
                action = "${pageContext.request.contextPath}/supplierAudit/supplierType.html";
            }
            $("#form_id").attr("action", action);
            $("#form_id").submit();
        } 
    </script>
</head>

<body>
<!--面包屑导航开始-->
<div class="margin-top-10 breadcrumbs ">
    <div class="container">
        <ul class="breadcrumb margin-left-0">
            <li>
                <a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a>
            </li>
            <li>
                <a>支撑环境</a>
            </li>
            <li>
                <a>供应商管理</a>
            </li>
            <c:if test="${sign == 1}">
                <li>
                    <a href="javascript:jumppage('${pageContext.request.contextPath}/supplierAudit/supplierAll.html?sign=1')">供应商审核</a>
                </li>
            </c:if>
            <c:if test="${sign == 2}">
                <li>
                    <a href="javascript:jumppage('${pageContext.request.contextPath}/supplierAudit/supplierAll.html?sign=2')">供应商复核</a>
                </li>
            </c:if>
            <c:if test="${sign == 3}">
                <li>
                    <a href="javascript:jumppage('${pageContext.request.contextPath}/supplierAudit/supplierAll.html?sign=3')">供应商实地考察</a>
                </li>
            </c:if>
        </ul>
    </div>
</div>
<div class="container container_box">
    <div class=" content height-350">
        <div class="col-md-12 tab-v2 job-content">
            <%-- <ul class="nav nav-tabs bgdd">
  <li class="active"><a >详细信息</a></li>
  <li class=""><a >财务信息</a></li>
  <li class=""><a >股东信息</a></li>
  <c:if test="${fn:contains(supplierTypeNames, '生产')}">
  <li class=""><a >物资-生产专业信息</a></li>
  </c:if>
  <c:if test="${fn:contains(supplierTypeNames, '销售')}">
  <li class=""><a >物资-销售专业信息</a></li>
  </c:if>
  <c:if test="${fn:contains(supplierTypeNames, '工程')}">
  <li class=""><a >工程-专业信息</a></li>
  </c:if>
  <c:if test="${fn:contains(supplierTypeNames, '服务')}">
  <li class=""><a >服务-专业信息</a></li>
  </c:if>
  <li class=""><a >品目信息</a></li>
  <li class=""><a >产品信息</a></li>
  <li class=""><a>申请表</a></li>
  <li class=""><a>审核汇总</a></li>
  </ul> --%>
            <!-- <ul class="nav nav-tabs bgdd"> -->
            <ul class="flow_step">
                <li onclick="jump('essential')" class="active">
                    <a aria-expanded="false" data-toggle="tab">基本信息</a>
                    <i></i>
                </li>
                <li onclick="jump('financial')">
                    <a aria-expanded="true">财务信息</a>
                    <i></i>
                </li>
                <li onclick="jump('shareholder')">
                    <a aria-expanded="false">股东信息</a>
                    <i></i>
                </li>
                <%--<c:if test="${fn:contains(supplierTypeNames, '生产')}">
                    <li onclick="jump('materialProduction')">
                        <a aria-expanded="false">生产信息</a>
                        <i></i>
                    </li>
                </c:if>
                <c:if test="${fn:contains(supplierTypeNames, '销售')}">
                    <li onclick="jump('materialSales')">
                        <a aria-expanded="false">销售信息</a>
                        <i></i>
                    </li>
                </c:if>
                <c:if test="${fn:contains(supplierTypeNames, '工程')}">
                    <li onclick="jump('engineering')">
                        <a aria-expanded="false">工程信息</a>
                        <i></i>
                    </li>
                </c:if>
                <c:if test="${fn:contains(supplierTypeNames, '服务')}">
                    <li onclick="jump('serviceInformation')">
                        <a aria-expanded="false">服务信息</a>
                        <i></i>
                    </li>
                </c:if>
                --%>
                <li onclick="jump('supplierType')">
                    <a aria-expanded="false">供应商类型</a>
                    <i></i>
                </li>
                <!-- <li onclick="jump('items')">
                    <a aria-expanded="false">产品类别</a>
                    <i></i>
                </li> -->
                <li onclick="jump('aptitude')">
                    <a aria-expanded="false">资质文件维护</a>
                    <i></i>
                </li>
                <li onclick="jump('contract')">
                    <a aria-expanded="false">销售合同</a>
                    <i></i>
                </li>
                 <!-- <li onclick="jump('aptitude')">
                    <a aria-expanded="false">产品类别及资质合同</a>
                    <i></i>
                </li> -->
                <li onclick="jump('applicationForm')">
                    <a aria-expanded="false">承诺书和申请表</a>
                    <i></i>
                </li>
                <li onclick="jump('reasonsList')">
                    <a aria-expanded="false">审核汇总</a>
                </li>
            </ul>

            <form id="form_id" action="${pageContext.request.contextPath}/supplierAudit/financial.html" method="post">
                <input name="supplierId" id="id" value="${suppliers.id }" type="hidden">
                <input name="supplierStatus" value="${suppliers.status }" type="hidden">
                <input type="hidden" name="sign" value="${sign}">
            </form>

            <h2 class="count_flow"><i>1</i>供应商信息</h2>
            <ul class="ul_list">
                <li class="col-md-3 col-sm-6 col-xs-12 pl15">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">供应商名称：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input title="${suppliers.supplierName }" id="supplierName" onclick="reason(this)"
                               value="${suppliers.supplierName } " type="text"
                               <c:if test="${fn:contains(field,'supplierName')}">style="border: 1px solid #FF8C00;"
                               onMouseOver="isCompare('supplierName');"</c:if>
                               <c:if test="${fn:contains(passedField,'supplierName')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(passedField,'supplierName')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">网址：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input title="${suppliers.website }" class="hand " id="website" value="${suppliers.website }"
                               type="text" onclick="reason(this)"
                               <c:if test="${fn:contains(field,'website')}">style="border: 1px solid #FF8C00;"
                               onMouseOver="isCompare('website');"</c:if>
                               <c:if test="${fn:contains(passedField,'website')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(passedField,'website')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">成立日期：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="foundDate" onclick="reason(this)" class="hand "
                               value="<fmt:formatDate value='${suppliers.foundDate}' pattern='yyyy-MM-dd'/>" type="text"
                               <c:if test="${fn:contains(field,'foundDate')}">style="border: 1px solid #FF8C00;"
                               onMouseOver="isCompare('foundDate');"</c:if>
                               <c:if test="${fn:contains(passedField,'foundDate')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(passedField,'foundDate')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">企业性质：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="businessNature" class="hand " value="${suppliers.businessNature } " type="text"
                               onclick="reason(this)"
                               <c:if test="${fn:contains(field,'businessNature')}">style="border: 1px solid #FF8C00;"
                               onMouseOver="isCompare('businessNature');"</c:if>
                               <c:if test="${fn:contains(passedField,'businessNature')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(passedField,'businessNature')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">基本账户开户银行：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input title="${suppliers.bankName }" id="bankName" class="hand "
                               value="${suppliers.bankName } " type="text" onclick="reason(this)"
                               <c:if test="${fn:contains(field,'bankName')}">style="border: 1px solid #FF8C00;"
                               onMouseOver="isCompare('bankName');"</c:if>
                               <c:if test="${fn:contains(passedField,'bankName')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(passedField,'bankName')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">银行账号：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="bankAccount" class="hand " value="${suppliers.bankAccount } " type="text"
                               onclick="reason(this)"
                               <c:if test="${fn:contains(field,'bankAccount')}">style="border: 1px solid #FF8C00;"
                               onMouseOver="isCompare('bankAccount');"</c:if>
                               <c:if test="${fn:contains(passedField,'bankAccount')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(passedField,'bankAccount')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span
                            <c:if test="${fn:contains(fileModifyField,supplierDictionaryData.supplierBank)}">style="border: 1px solid #FF8C00;"</c:if>
                            class="hand" onclick="reason1(this,'supplierBank');"
                            onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'">基本账户开户许可证：</span>
                    <u:show showId="bank_show" delete="false"
                            groups="bank_show,taxcert_show,billcert_show,curitycert_show,bearchcert_show,bearchcert_up_show,business_show"
                            businessId="${suppliers.id}" sysKey="${sysKey}"
                            typeId="${supplierDictionaryData.supplierBank}"/>
                    <p class='abolish'><img style="padding-left: 125px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'></p>
                    <c:if test="${fn:contains(passedField,'supplierBank')}">
                      <a class='abolish'>
                      <img style="padding-left: 125px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'>
                      </a>
                    </c:if>
                </li>
            </ul>

            <h2 class="count_flow"><i>2</i>营业执照</h2>
            <ul class="ul_list">
                <li class="col-md-3 col-sm-6 col-xs-12 pl15">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">营业执照登记类型：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="businessType" class="hand " value="${suppliers.businessType } " type="text"
                               onclick="reason(this)"
                               <c:if test="${fn:contains(field,'businessType')}">style="border: 1px solid #FF8C00;"
                               onMouseOver="isCompare('businessType');"</c:if>
                               <c:if test="${fn:contains(passedField,'website')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(passedField,'businessType')}">
                            <a class='abolish'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">统一社会信用代码：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="creditCode" class="hand " value="${suppliers.creditCode } " type="text"
                               onclick="reason(this)"
                               <c:if test="${fn:contains(field,'creditCode')}">style="border: 1px solid #FF8C00;"
                               onMouseOver="isCompare('creditCode');"</c:if>
                               <c:if test="${fn:contains(passedField,'creditCode')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(passedField,'creditCode')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">登记机关：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="registAuthority" class="hand " value="${suppliers.registAuthority } " type="text"
                               onclick="reason(this)"
                               <c:if test="${fn:contains(field,'registAuthority')}">style="border: 1px solid #FF8C00;"
                               onMouseOver="isCompare('registAuthority');"</c:if>
                               <c:if test="${fn:contains(passedField,'registAuthority')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(passedField,'registAuthority')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">注册资本（人民币：万元）：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="registFund" class="hand " value="${suppliers.registFund } " type="text"
                               onclick="reason(this)"
                               <c:if test="${fn:contains(field,'registFund')}">style="border: 1px solid #FF8C00;"
                               onMouseOver="isCompare('registFund');"</c:if>
                               <c:if test="${fn:contains(passedField,'registFund')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(passedField,'registFund')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">营业期限 ：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="businessStartDate" class="hand " onclick="reason(this)"
                               value="<c:choose><c:when test="${suppliers.branchName eq '1'}">长期有效</c:when><c:otherwise> <fmt:formatDate value='${suppliers.businessStartDate}' pattern='yyyy-MM-dd'/></c:otherwise></c:choose>"
                               type="text"
                               <c:if test="${fn:contains(field,'businessStartDate')}">style="border: 1px solid #FF8C00;"
                               onMouseOver="isCompare('businessStartDate');"</c:if>
                               <c:if test="${fn:contains(passedField,'businessStartDate')}">style="border: 1px solid red;"</c:if>/>
                        <c:if test="${fn:contains(passedField,'businessStartDate')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <%-- <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">营业截止时间：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="businessStartDate" class="hand " onclick="reason(this)" value="<fmt:formatDate value='${suppliers.businessEndDate}' pattern='yyyy-MM-dd'/>" type="text" />
                    </div>
                </li> --%>
                <%-- <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="fl" id="businessAddress2">生产或经营地址：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="businessAddress" class="hand " value="${suppliers.businessAddress } " type="text" onclick="reason(this.id,'businessAddress')">
                        <div id="businessAddress3" class="abolish">×</div>
                    </div>
                </li> --%>
                <%-- <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">邮编：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="businessPostCode" class="hand " value="${suppliers.businessPostCode } " type="text" onclick="reason(this)" <c:if test="${fn:contains(field,'businessPostCode')}">style="border: 1px solid #FF8C00;"  onMouseOver="isCompare('businessPostCode');"</c:if>>
                    </div>
                </li> --%>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span
                            <c:if test="${fn:contains(fileModifyField,supplierDictionaryData.supplierBusinessCert)}">style="border: 1px solid #FF8C00;"</c:if>
                            class="hand" onmouseover="this.style.background='#E8E8E8'"
                            onmouseout="this.style.background='#FFFFFF'"
                            onclick="reason1(this,'businessCert');">营业执照：</span>
                    <div class="col-md-12 col-sm-12 col-xs-12 p0 mb25 h30">
                        <u:show showId="business_show" delete="false"
                                groups="bank_show,taxcert_show,billcert_show,curitycert_show,bearchcert_show,bearchcert_up_show,business_show"
                                businessId="${suppliers.id}" sysKey="${sysKey}"
                                typeId="${supplierDictionaryData.supplierBusinessCert}"/>
                        <c:if test="${fn:contains(passedField,'businessCert')}">
                            <a class='abolish'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                        <p class='abolish'><img style="padding-left: 125px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'></p>
                    </div>
                </li>
                <li class="col-md-12 col-sm-12 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">营业范围（按照营业执照上填写）：</span>
                    <div class="col-md-12 col-sm-12 col-xs-12 p0">
                        <textarea class="col-md-12 col-xs-12 col-sm-12 h80" id="businessScope" onclick="reason(this)"
                                  <c:if test="${fn:contains(field,'businessScope')}">style="border: 1px solid #FF8C00;"
                                  onMouseOver="isCompare('businessScope');"</c:if>
                                  <c:if test="${fn:contains(passedField,'businessScope')}">style="border: 1px solid red;"</c:if>>${suppliers.businessScope }</textarea>
                        <c:if test="${fn:contains(passedField,'businessScope')}">
                            <a class='abolish'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
            </ul>

            <h2 class="count_flow"><i>3</i>法定代表人信息</h2>
            <ul class="ul_list">
                <li class="col-md-3 col-sm-6 col-xs-12 pl15">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">姓名：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="legalName" class="hand " value="${suppliers.legalName } " type="text"
                               onclick="reason(this)"
                               <c:if test="${fn:contains(field,'legalName')}">style="border: 1px solid #FF8C00;"
                               onMouseOver="isCompare('legalName');"</c:if>
                               <c:if test="${fn:contains(passedField,'legalName')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(passedField,'legalName')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">身份证号：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="legalIdCard" class="hand " value="${suppliers.legalIdCard } " type="text"
                               onclick="reason(this)"
                               <c:if test="${fn:contains(field,'legalIdCard')}">style="border: 1px solid #FF8C00;"
                               onMouseOver="isCompare('legalIdCard');"</c:if>
                               <c:if test="${fn:contains(passedField,'legalIdCard')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(passedField,'legalIdCard')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">固定电话：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="legalMobile" class="hand " value="${suppliers.legalMobile } " type="text"
                               onclick="reason(this)"
                               <c:if test="${fn:contains(field,'legalMobile')}">style="border: 1px solid #FF8C00;"
                               onMouseOver="isCompare('legalMobile');"</c:if>
                               <c:if test="${fn:contains(passedField,'legalMobile')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(passedField,'legalMobile')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">手机：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="legalTelephone" class="hand " value="${suppliers.legalTelephone } " type="text"
                               onclick="reason(this)"
                               <c:if test="${fn:contains(field,'legalTelephone')}">style="border: 1px solid #FF8C00;"
                               onMouseOver="isCompare('legalTelephone');"</c:if>
                               <c:if test="${fn:contains(passedField,'legalTelephone')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(passedField,'legalTelephone')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <%-- <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="hand" onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'" onclick="reason1(this,'supplierIdentityUp');">身份证正面: </span>
                    <u:show showId="bearchcert_up_show" delete="false" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show" businessId="${suppliers.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierIdentityUp}" />
                    <p class="b f18 ml10 red">×</p>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="hand" onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'" onclick="reason1(this,'supplierIdentitydown');">身份证反面: </span>
                    <u:show showId="identity_down_show" delete="false" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show" businessId="${suppliers.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierIdentitydown}" />
                    <p class="b f18 ml10 red">×</p>
                </li> --%>

                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span
                            <c:if test="${fn:contains(fileModifyField,supplierDictionaryData.supplierIdentityUp)}">style="border: 1px solid #FF8C00;"</c:if>
                            class="hand" onmouseover="this.style.background='#E8E8E8'"
                            onmouseout="this.style.background='#FFFFFF'" onclick="reason1(this,'supplierIdentityUp');"> 身份证复印件（正反面在一张上）:</span>
                    <div class="col-md-12 col-sm-12 col-xs-12 p0 mb25 h30">
                        <u:show showId="bearchcert_up_show" delete="false"
                                groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show"
                                businessId="${suppliers.id}" sysKey="${sysKey}"
                                typeId="${supplierDictionaryData.supplierIdentityUp}"/>
                        <p class='abolish'><img style="padding-left: 125px;"
                                src='${pageContext.request.contextPath}/public/backend/images/sc.png'></p>
                        <c:if test="${fn:contains(passedField,'supplierIdentityUp')}">
                            <a class='abolish'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
            </ul>

            <h2 class="count_flow"><i>4</i>地址信息</h2>
            <ul class="ul_list hand">
                <li class="col-md-3 col-sm-6 col-xs-12 pl15"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">住所邮编：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="postCode" class="hand " value="${suppliers.postCode }" type="text"
                               onclick="reason(this)"
                        <c:if test="${fn:contains(field,'postCode')}"> style="border: 1px solid #FF8C00;"
                               onMouseOver="isCompare('postCode');"</c:if>
                               <c:if test="${fn:contains(passedField,'postCode')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(passedField,'postCode')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"> 住所地址：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="address" class="hand " value="${parentAddress}${sonAddress } " type="text"
                               onclick="reason(this)"
                        <c:if test="${fn:contains(field,'address')}"> style="border: 1px solid #FF8C00;"
                               onMouseOver="isCompare('address');"</c:if>
                               <c:if test="${fn:contains(passedField,'address')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(passedField,'address')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">住所详细地址：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="detailAddress" class="hand fl" onclick="reason(this)" type="text"
                               value="${suppliers.detailAddress}"
                               <c:if test="${fn:contains(field,'detailAddress')}">style="border: 1px solid #FF8C00;"
                               onMouseOver="isCompare('detailAddress');"</c:if>
                               <c:if test="${fn:contains(passedField,'detailAddress')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(passedField,'detailAddress')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <div class="clear"></div>
                <!-- 遍历生产地址 -->
                <%-- <c:forEach items="${supplierAddress }" var="supplierAddress" varStatus="vs">
                    <li class="col-md-3 col-sm-6 col-xs-12">
                        <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">生产或经营地址邮编：</span>
                        <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                            <input type="text" id="code_${supplierAddress.id }" value="${supplierAddress.code}" class="hand " onclick="reason(this)" <c:if test="${fn:contains(fieldAddress,supplierAddress.id.concat('_code'))}">style="border: 1px solid #FF8C00;" onMouseOver="showContent('code','${supplierAddress.id}','1');"</c:if> <c:if test="${fn:contains(passedField,'code_'.concat(supplierAddress.id))}">style="border: 1px solid red;"</c:if>>
                            <c:if test="${fn:contains(passedField,'code_'.concat(supplierAddress.id))}">
                                <a class="abolish">
                                    <img style="padding-left: 125px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'>
                                </a>
                            </c:if>
                        </div>
                    </li>
                    <li class="col-md-3 col-sm-6 col-xs-12">
                        <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">生产或经营地址：</span>
                        <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                            <input type="text" id="residence_${supplierAddress.id }" value="${supplierAddress.parentName }${supplierAddress.subAddressName }" class="hand " onclick="reason(this)" <c:if test="${fn:contains(fieldAddress,supplierAddress.id.concat('_residence'))}">style="border: 1px solid #FF8C00;" onMouseOver="showContent('residence','${supplierAddress.id}','1');"</c:if> <c:if test="${fn:contains(passedField,'residence_'.concat(supplierAddress.id))}">style="border: 1px solid red;"</c:if>>
                            <c:if test="${fn:contains(passedField,'residence_'.concat(supplierAddress.id))}">
                                <a class="abolish">
                                    <img style="padding-left: 125px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'>
                                </a>
                            </c:if>
                        </div>
                    </li>
                    <li class="col-md-3 col-sm-6 col-xs-12 pl10">
                        <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">生产或经营详细地址：</span>
                        <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                            <input type="text" id="detailedResidence_${supplierAddress.id }" value="${supplierAddress.detailAddress}" class="hand " onclick="reason(this)"  <c:if test="${fn:contains(fieldAddress,supplierAddress.id.concat('_detailedResidence'))}">style="border: 1px solid #FF8C00;" onMouseOver="showContent('detailedResidence','${supplierAddress.id}','1');"</c:if> <c:if test="${fn:contains(passedField,'detailedResidence_'.concat(supplierAddress.id))}">style="border: 1px solid red;"</c:if>>
                            <c:if test="${fn:contains(passedField,'detailedResidence_'.concat(supplierAddress.id))}">
                                <a class="abolish">
                                    <img style="padding-left: 125px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'>
                                </a>
                            </c:if>
                        </div>
                    </li>
                    <li class="col-md-3 col-sm-6 col-xs-12 pl10" >
                    <span <c:if test="${fn:contains(houseFileModifyField,supplierAddress.id.concat(supplierDictionaryData.supplierHousePoperty))}">style="border: 1px solid #FF8C00;"</c:if> class="hand" onclick="reason1(this,'supplierHousePoperty_${supplierAddress.id}');" onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'">房产证明或租赁协议：</span>
                    <div class="col-md-12 col-sm-12 col-xs-12 p0 mb25 h30">
                        <u:show delete="false" showId="house_show_${vs.index+1}" businessId="${supplierAddress.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierHousePoperty}" />
                        <p><img style="padding-left: 100px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'></p>
                        <c:if test="${fn:contains(passedField,'supplierHousePoperty_'.concat(supplierAddress.id))}">
                            <img style="padding-left: 100px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'>
                        </c:if>
                    </div>
                </li>
                    <div class="clear"></div>
                </c:forEach> --%>

                <table class="table table-bordered  table-condensed table-hover">
                    <thead>
                    <tr>
                        <th class="info w50">序号</th>
                        <th class="info">生产或经营地址邮编</th>
                        <th class="info">生产或经营地址（填写所有地址）</th>
                        <th class="info">生产或经营详细地址</th>
                        <th class="info">房产证明或租赁协议</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody id="finance_attach_list_tbody_id">
                    <c:forEach items="${supplierAddress}" var="supplierAddress" varStatus="vs">
                        <tr>
                            <td class="tc">${vs.index+1}</td>
                            <td id="code_${supplierAddress.id}"
                                <c:if test="${fn:contains(fieldAddress,supplierAddress.id.concat('_code'))}">style="border: 1px solid #FF8C00;"
                                onMouseOver="showContent('code','${supplierAddress.id}','1');"</c:if>>${supplierAddress.code}</td>
                            <td id="residence_${supplierAddress.id}"
                                <c:if test="${fn:contains(fieldAddress,supplierAddress.id.concat('_residence'))}">style="border: 1px solid #FF8C00;"
                                onMouseOver="showContent('residence','${supplierAddress.id}','1');"</c:if>>${supplierAddress.parentName}${supplierAddress.subAddressName}</td>
                            <td id="detailedResidence_${supplierAddress.id}"
                                <c:if test="${fn:contains(fieldAddress,supplierAddress.id.concat('_detailedResidence'))}">style="border: 1px solid #FF8C00;"
                                onMouseOver="showContent('detailedResidence','${supplierAddress.id}','1');"</c:if>>${supplierAddress.detailAddress}</td>
                            <td
                                    <c:if test="${fn:contains(houseFileModifyField,supplierAddress.id.concat(supplierDictionaryData.supplierHousePoperty))}">style="border: 1px solid #FF8C00;"</c:if>>
                                <u:show delete="false" showId="house_show_${vs.index+1}"
                                        businessId="${supplierAddress.id}" sysKey="${sysKey}"
                                        typeId="${supplierDictionaryData.supplierHousePoperty}"/>
                            </td>
                            <td class="tc w50 hand">
                                <a id="${supplierAddress.id}_show"><img
                                        src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                                <p onclick="auditList('${supplierAddress.id}','${supplierAddress.parentName}${supplierAddress.subAddressName}','地址信息');"
                                   id="${supplierAddress.id}_hidden" class="editItem"><c:if
                                        test="${!fn:contains(passedField,supplierAddress.id)}"><img
                                        src='${pageContext.request.contextPath}/public/backend/images/light_icon.png'></c:if><c:if
                                        test="${fn:contains(passedField,supplierAddress.id)}"><img
                                        src='${pageContext.request.contextPath}/public/backend/images/light_icon.png'
                                        class="hidden"></c:if></p>
                                <c:if test="${fn:contains(passedField,supplierAddress.id)}">
                                    <img src='${pageContext.request.contextPath}/public/backend/images/sc.png'>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </ul>

            <h2 class="count_flow"><i>5</i>资质资信</h2>
            <ul class="ul_list hand">
                <li class="col-md-3 col-sm-6 col-xs-12 pl15">
                    <span
                            <c:if test="${fn:contains(fileModifyField,supplierDictionaryData.supplierTaxCert)}">style="border: 1px solid #FF8C00;"</c:if>
                            class="hand" onclick="reason1(this,'taxCert');"
                            onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'">近三个月完税凭证：</span>
                    <div class="col-md-12 col-sm-12 col-xs-12 p0 mb25 h30">
                        <u:show showId="taxcert_show" delete="false"
                                groups="bank_show,taxcert_show,billcert_show,curitycert_show,bearchcert_show,bearchcert_up_show,business_show"
                                businessId="${suppliers.id}" sysKey="${sysKey}"
                                typeId="${supplierDictionaryData.supplierTaxCert}"/>
                        <p class='abolish'><img style="padding-left: 125px;"
                                src='${pageContext.request.contextPath}/public/backend/images/sc.png'></p>
                        <c:if test="${fn:contains(passedField,'taxCert')}">
                          <a class='abolish'>
                            <img style="padding-left: 125px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'>
                          </a>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span
                            <c:if test="${fn:contains(fileModifyField,supplierDictionaryData.supplierBillCert)}">style="border: 1px solid #FF8C00;"</c:if>
                            class="hand" onclick="reason1(this,'billCert');"
                            onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'">近三年银行基本账户年末对账单：</span>
                    <div class="col-md-12 col-sm-12 col-xs-12 p0 mb25 h30">
                        <u:show showId="billcert_show" delete="false"
                                groups="bank_show,taxcert_show,billcert_show,curitycert_show,bearchcert_show,bearchcert_up_show,business_show"
                                businessId="${suppliers.id}" sysKey="${sysKey}"
                                typeId="${supplierDictionaryData.supplierBillCert}"/>
                        <p class='abolish'><img style="padding-left: 125px;"
                                src='${pageContext.request.contextPath}/public/backend/images/sc.png'></p>
                        <c:if test="${fn:contains(passedField,'billCert')}">
                          <a class='abolish'>
                            <img style="padding-left: 125px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'>
                          </a>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="hand"
                          <c:if test="${fn:contains(fileModifyField,supplierDictionaryData.supplierSecurityCert)}">style="border: 1px solid #FF8C00;"</c:if>
                          onclick="reason1(this,'securityCert');" onmouseover="this.style.background='#E8E8E8'"
                          onmouseout="this.style.background='#FFFFFF'">近三个月缴纳社会保险金凭证：</span>
                    <div class="col-md-12 col-sm-12 col-xs-12 p0 mb25 h30">
                        <u:show showId="curitycert_show" delete="false"
                                groups="bank_show,taxcert_show,billcert_show,curitycert_show,bearchcert_show,bearchcert_up_show,business_show"
                                businessId="${suppliers.id}" sysKey="${sysKey}"
                                typeId="${supplierDictionaryData.supplierSecurityCert}"/>
                        <p class='abolish'><img style="padding-left: 125px;"
                                src='${pageContext.request.contextPath}/public/backend/images/sc.png'></p>
                        <c:if test="${fn:contains(passedField,'securityCert')}">
                          <a class='abolish'>
                            <img style="padding-left: 125px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'>
                          </a>
                        </c:if>
                    </div>
                </li>
                <%-- <li class="col-md-3 col-sm-6 col-xs-12"><span class="hand" onclick="reason1(this,'breachCert');" onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'">近三年内无重大违法记录声明：</span>
                    <u:show showId="bearchcert_show" groups="bank_show,taxcert_show,billcert_show,curitycert_show,bearchcert_show,bearchcert_up_show,business_show" delete="false" businessId="${suppliers.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBearchCert}" />
                    <p><img style="padding-left: 125px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'></p>
                </li> --%>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">近三年内有无重大违法记录：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <c:if test="${'1' eq suppliers.isIllegal }">
                            <input id="isIllegal" class="hand " value="有" type="text" onclick="reason(this)"
                                   <c:if test="${fn:contains(passedField,'isIllegal')}">style="border: 1px solid red;"</c:if>
                                   <c:if test="${fn:contains(field,'isIllegal')}">style="border: 1px solid #FF8C00;"
                                   onMouseOver="isCompare('isIllegal');"</c:if> >
                        </c:if>
                        <c:if test="${'0' eq suppliers.isIllegal }">
                            <input id="isIllegal" class="hand " value="无" type="text" onclick="reason(this)"
                                   <c:if test="${fn:contains(passedField,'isIllegal')}">style="border: 1px solid red;"</c:if>
                                   <c:if test="${fn:contains(field,'isIllegal')}">style="border: 1px solid #FF8C00;"
                                   onMouseOver="isCompare('isIllegal');"</c:if> >
                        </c:if>
                        <c:if test="${fn:contains(passedField,'isIllegal')}"><a class='abolish'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a></c:if>
                    </div>
                </li>
                <c:if test="${suppliers.isHavingConCert eq '0'}">
                    <li class="col-md-3 col-sm-6 col-xs-12">
                        <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">国家或军队保密资格证书：</span>
                        <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                            <input id="isHavingConCert" class="hand " value="无" type="text" onclick="reason(this)"
                                   <c:if test="${fn:contains(passedField,'isHavingConCert')}">style="border: 1px solid red;"</c:if>
                                   <c:if test="${fn:contains(field,'isHavingConCert')}">style="border: 1px solid #FF8C00;"
                                   onMouseOver="isCompare('isHavingConCert');"</c:if> >
                            <c:if test="${fn:contains(passedField,'isHavingConCert')}"><a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a></c:if>
                        </div>
                    </li>
                </c:if>
                <c:if test="${suppliers.isHavingConCert eq '1'}">
                    <li class="col-md-3 col-sm-6 col-xs-12">
                        <span
                                <c:if test="${fn:contains(fileModifyField,supplierDictionaryData.supplierBearchCert)}">style="border: 1px solid #FF8C00;"</c:if>
                                class="hand" onclick="reason1(this,'supplierBearchCert');"
                                onmouseover="this.style.background='#E8E8E8'"
                                onmouseout="this.style.background='#FFFFFF'">保密资格证书：</span>
                        <div class="col-md-12 col-sm-12 col-xs-12 p0 mb25 h30">
                            <u:show showId="bearchcert_show" delete="false"
                                    groups="bank_show,taxcert_show,billcert_show,curitycert_show,bearchcert_show,bearchcert_up_show,business_show"
                                    businessId="${suppliers.id}" sysKey="${sysKey}"
                                    typeId="${supplierDictionaryData.supplierBearchCert}"/>
                            <p class='abolish'><img style="padding-left: 125px;"
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></p>
                            <c:if test="${fn:contains(passedField,'supplierBearchCert')}">
                              <a class='abolish'>
                                <img style="padding-left: 125px;"  src='${pageContext.request.contextPath}/public/backend/images/sc.png'>
                              </a>
                            </c:if>
                        </div>
                    </li>
                </c:if>
            </ul>

            <h2 class="count_flow"><i>6</i>注册联系人</h2>
            <ul class="ul_list">
                <li class="col-md-3 col-sm-6 col-xs-12 pl15">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">姓名：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="contactName" class="hand " value="${suppliers.contactName } " type="text"
                               onclick="reason(this)"
                               <c:if test="${fn:contains(field,'contactName')}">style="border: 1px solid #FF8C00;"
                               onMouseOver="isCompare('contactName');"</c:if>
                               <c:if test="${fn:contains(passedField,'contactName')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(passedField,'contactName')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">传真：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="contactFax" class="hand " value="${suppliers.contactFax } " type="text"
                               onclick="reason(this)"
                               <c:if test="${fn:contains(field,'contactFax')}">style="border: 1px solid #FF8C00;"
                               onMouseOver="isCompare('contactFax');"</c:if>
                               <c:if test="${fn:contains(passedField,'contactFax')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(passedField,'contactFax')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">固定电话：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="contactMobile" class="hand " value="${suppliers.contactMobile } " type="text"
                               onclick="reason(this)"
                               <c:if test="${fn:contains(field,'contactMobile')}">style="border: 1px solid #FF8C00;"
                               onMouseOver="isCompare('contactMobile');"</c:if>
                               <c:if test="${fn:contains(passedField,'contactMobile')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(passedField,'contactMobile')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">手机：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="mobile" class="hand " value="${suppliers.mobile } " type="text"
                               onclick="reason(this)"
                               <c:if test="${fn:contains(field,'mobile')}">style="border: 1px solid #FF8C00;"
                               onMouseOver="isCompare('mobile');"</c:if>
                               <c:if test="${fn:contains(passedField,'mobile')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(passedField,'mobile')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">邮箱：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="contactEmail" class="hand " value="${suppliers.contactEmail } " type="text"
                               onclick="reason(this)"
                               <c:if test="${fn:contains(field,'contactEmail')}">style="border: 1px solid #FF8C00;"
                               onMouseOver="isCompare('contactEmail');"</c:if>
                               <c:if test="${fn:contains(passedField,'contactEmail')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(passedField,'contactEmail')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">地址：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="concatCity" class="hand " value="${parentConcatProvince } ${sonConcatProvince}"
                               type="text" onclick="reason(this)"
                               <c:if test="${fn:contains(field,'concatCity')}">style="border: 1px solid #FF8C00;"
                               onMouseOver="isCompare('concatCity');"</c:if>
                               <c:if test="${fn:contains(passedField,'concatCity')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(passedField,'concatCity')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">详细地址：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="contactAddress" class="hand " value="${suppliers.contactAddress } " type="text"
                               onclick="reason(this)"
                               <c:if test="${fn:contains(field,'contactAddress')}">style="border: 1px solid #FF8C00;"
                               onMouseOver="isCompare('contactAddress');"</c:if>
                               <c:if test="${fn:contains(passedField,'contactAddress')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(passedField,'contactAddress')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
            </ul>

            <h2 class="count_flow"><i>7</i>本单位军队业务联系人</h2>
            <ul class="ul_list">
                <li class="col-md-3 col-sm-6 col-xs-12 pl15">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">姓名：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="armyBusinessName" class="hand " value="${suppliers.armyBusinessName } " type="text"
                               onclick="reason(this)"
                               <c:if test="${fn:contains(field,'armyBusinessName')}">style="border: 1px solid #FF8C00;"
                               onMouseOver="isCompare('armyBusinessName');"</c:if>
                               <c:if test="${fn:contains(passedField,'armyBusinessName')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(passedField,'armyBusinessName')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">传真：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="armyBusinessFax" class="hand " value="${suppliers.armyBusinessFax } " type="text"
                               onclick="reason(this)"
                               <c:if test="${fn:contains(field,'armyBusinessFax')}">style="border: 1px solid #FF8C00;"
                               onMouseOver="isCompare('armyBusinessFax');"</c:if>
                               <c:if test="${fn:contains(passedField,'armyBusinessFax')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(passedField,'armyBusinessFax')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">固定电话：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="armyBuinessMobile" class="hand " value="${suppliers.armyBuinessMobile } " type="text"
                               onclick="reason(this)"
                               <c:if test="${fn:contains(field,'armyBuinessMobile')}">style="border: 1px solid #FF8C00;"
                               onMouseOver="isCompare('armyBuinessMobile');"</c:if>
                               <c:if test="${fn:contains(passedField,'armyBuinessMobile')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(passedField,'armyBuinessMobile')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">手机：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="armyBuinessTelephone" class="hand " value="${suppliers.armyBuinessTelephone } "
                               type="text" onclick="reason(this)"
                               <c:if test="${fn:contains(field,'armyBuinessTelephone')}">style="border: 1px solid #FF8C00;"
                               onMouseOver="isCompare('armyBuinessTelephone');"</c:if>
                               <c:if test="${fn:contains(passedField,'armyBuinessTelephone')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(passedField,'armyBuinessTelephone')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" onclick="reason(this)">邮箱：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="armyBuinessEmail" class="hand " value="${suppliers.armyBuinessEmail } " type="text"
                               onclick="reason(this)"
                               <c:if test="${fn:contains(field,'armyBuinessEmail')}">style="border: 1px solid #FF8C00;"
                               onMouseOver="isCompare('armyBuinessEmail');"</c:if>
                               <c:if test="${fn:contains(passedField,'armyBuinessEmail')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(passedField,'armyBuinessEmail')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" onclick="reason(this)">地址：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="armyBuinessCity" class="hand "
                               value="${parentArmyBuinessProvince} ${sonArmyBuinessProvince}" type="text"
                               onclick="reason(this)"
                               <c:if test="${fn:contains(field,'armyBuinessCity')}">style="border: 1px solid #FF8C00;"
                               onMouseOver="isCompare('armyBuinessCity');"</c:if>
                               <c:if test="${fn:contains(passedField,'armyBuinessCity')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(passedField,'armyBuinessCity')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">详细地址：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 p0">
                        <input id="armyBuinessAddress" class="hand " value="${suppliers.armyBuinessAddress } "
                               type="text" onclick="reason(this)"
                               <c:if test="${fn:contains(field,'armyBuinessAddress')}">style="border: 1px solid #FF8C00;"
                               onMouseOver="isCompare('armyBuinessAddress');"</c:if>
                               <c:if test="${fn:contains(passedField,'armyBuinessAddress')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(passedField,'armyBuinessAddress')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
            </ul>

            <h2 class="count_flow"><i>8</i>境外分支</h2>
            <ul class="ul_list">
                <li class="col-md-3 col-sm-6 col-xs-12 pl15">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">境外分支机构：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <c:if test="${suppliers.overseasBranch == 0}">
                            <input id="overseasBranch" class="hand " value="无" type="text" onclick="reason(this)"
                                   <c:if test="${fn:contains(passedField,'overseasBranch')}">style="border: 1px solid red;"</c:if>>
                        </c:if>
                        <c:if test="${suppliers.overseasBranch == 1}">
                            <input id="overseasBranch" class="hand " value="有" type="text" onclick="reason(this)"
                                   <c:if test="${fn:contains(passedField,'overseasBranch')}">style="border: 1px solid red;"</c:if>>
                        </c:if>
                        <c:if test="${fn:contains(passedField,'overseasBranch')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <div class="clear"></div>
                <c:forEach items="${supplierBranchList }" var="supplierBranch" varStatus="vs">
                    <c:if test="${suppliers.overseasBranch == 1}">
                        <li class="col-md-3 col-sm-6 col-xs-12">
                            <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">机构名称：</span>
                            <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                                <input id="organizationName_${supplierBranch.id }" class="hand "
                                       value="${supplierBranch.organizationName } " type="text" onclick="reason(this)"
                                       <c:if test="${fn:contains(passedField,'organizationName_'.concat(supplierBranch.id))}">style="border: 1px solid red;"</c:if>
                                       <c:if test="${fn:contains(fieldBranch,supplierBranch.id.concat('_organizationName'))}">style="border: 1px solid #FF8C00;"
                                       onMouseOver="showContent('organizationName','${supplierBranch.id}','2');"</c:if>>
                                <c:if test="${fn:contains(passedField,'organizationName_'.concat(supplierBranch.id))}">
                                    <a class='abolish'><img
                                            src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                                </c:if>
                            </div>
                        </li>
                        <li class="col-md-3 col-sm-6 col-xs-12 pl15">
                            <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">所在国家(地区)：</span>
                            <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                                <input id="countryName_${supplierBranch.id }" class="hand "
                                       value="${supplierBranch.countryName } " type="text" onclick="reason(this)"
                                       <c:if test="${fn:contains(passedField,'countryName_'.concat(supplierBranch.id))}">style="border: 1px solid red;"</c:if>
                                       <c:if test="${fn:contains(fieldBranch,supplierBranch.id.concat('_countryName'))}">style="border: 1px solid #FF8C00;"
                                       onMouseOver="showContent('countryName','${supplierBranch.id}','2');"</c:if>>
                                <c:if test="${fn:contains(passedField,'countryName_'.concat(supplierBranch.id))}">
                                    <a class='abolish'><img
                                            src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                                </c:if>
                            </div>
                        </li>
                        <li class="col-md-3 col-sm-6 col-xs-12 ">
                            <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">详细地址：</span>
                            <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                                <input id="detailAddress_${supplierBranch.id }" class="hand "
                                       value="${supplierBranch.detailAddress } " type="text" onclick="reason(this)"
                                       <c:if test="${fn:contains(passedField,'detailAddress_'.concat(supplierBranch.id))}">style="border: 1px solid red;"</c:if>
                                       <c:if test="${fn:contains(fieldBranch,supplierBranch.id.concat('_detailAddress'))}">style="border: 1px solid #FF8C00;"
                                       onMouseOver="showContent('detailAddress','${supplierBranch.id}','2');"</c:if>>
                                <c:if test="${fn:contains(passedField,'detailAddress_'.concat(supplierBranch.id))}">
                                    <a class='abolish'><img
                                            src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                                </c:if>
                            </div>
                        </li>
                        <li class="col-md-12 col-sm-12 col-xs-12">
                            <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">分支生产经营范围：</span>
                            <div class="col-md-12 col-sm-12 col-xs-12 p0">
                                <textarea class="col-md-12 col-xs-12 col-sm-12 h80"
                                          id="businessSope_${supplierBranch.id}" onclick="reason(this)"
                                          <c:if test="${fn:contains(passedField,'businessSope_'.concat(supplierBranch.id))}">style="border: 1px solid red;"</c:if>
                                          <c:if test="${fn:contains(fieldBranch,supplierBranch.id.concat('_businessSope'))}">style="border: 1px solid #FF8C00;"
                                          onMouseOver="showContent('businessSope','${supplierBranch.id}','2');"</c:if>>${supplierBranch.businessSope }</textarea>
                                <c:if test="${fn:contains(passedField,'businessSope_'.concat(supplierBranch.id))}">
                                    <a class='abolish'><img
                                            src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                                </c:if>
                            </div>
                        </li>
                    </c:if>
                </c:forEach>
            </ul>

            <h2 class="count_flow"><i>9</i>售后服务机构</h2>
            <ul class="ul_list">

                <table class="table table-bordered  table-condensed table-hover">
                    <thead>
                    <tr>
                        <th class="info w50">序号</th>
                        <th class="info">分支（或服务）机构名称</th>
                        <th class="info">类别</th>
                        <th class="info">所在省市县</th>
                        <th class="info">负责人</th>
                        <th class="info">联系电话</th>
                        <th class="info">操作</th>
                    </tr>
                    </thead>
                    <tbody id="finance_attach_list_tbody_id">
                    <c:forEach items="${listSupplierAfterSaleDep}" var="a" varStatus="vs">
                        <tr>
                            <td class="tc w50">${vs.index + 1}</td>
                            <td class="tc" id="name_${a.id}"
                                <c:if test="${fn:contains(fieldAfterSaleDep,a.id.concat('_name'))}">style="border: 1px solid #FF8C00;"
                                onMouseOver="showContent('name','${a.id}','11');"</c:if>>${a.name}</td>
                            <td class="tc" id="type_${a.id}"
                                <c:if test="${fn:contains(fieldAfterSaleDep,a.id.concat('_type'))}">style="border: 1px solid #FF8C00;"
                                onMouseOver="showContent('type','${a.id}','11');"</c:if>>
                                <c:if test="${a.type == 1}">自营</c:if>
                                <c:if test="${a.type == 2}">合作</c:if>
                            </td>
                            <td class="tc" id="address_${a.id }"
                                <c:if test="${fn:contains(fieldAfterSaleDep,a.id.concat('_address'))}">style="border: 1px solid #FF8C00;"
                                onMouseOver="showContent('address','${a.id}','11');"</c:if>>${a.address}</td>
                            <td class="tc" id="leadName_${a.id}"
                                <c:if test="${fn:contains(fieldAfterSaleDep,a.id.concat('_leadName'))}">style="border: 1px solid #FF8C00;"
                                onMouseOver="showContent('leadName','${a.id}','11');"</c:if>>${a.leadName}</td>
                            <td class="tc" id="mobile_${a.id}"
                                <c:if test="${fn:contains(fieldAfterSaleDep,a.id.concat('_mobile'))}">style="border: 1px solid #FF8C00;"
                                onMouseOver="showContent('mobile','${a.id}','11');"</c:if>>${a.mobile}</td>
                            <td class="tc w50 hand">
                                <a id="${a.id}_show"><img
                                        src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                                <p onclick="auditList('${a.id}','${a.name}','售后服务机构');" id="${a.id}_hidden"
                                   class="editItem"><c:if test="${!fn:contains(passedField,a.id)}"><img
                                        src='${pageContext.request.contextPath}/public/backend/images/light_icon.png'></c:if><c:if
                                        test="${fn:contains(passedField,a.id)}"><img
                                        src='${pageContext.request.contextPath}/public/backend/images/light_icon.png'
                                        class="hidden"></c:if></p>
                                <c:if test="${fn:contains(passedField,a.id)}">
                                    <img src='${pageContext.request.contextPath}/public/backend/images/sc.png'>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </ul>
            <h2 class="count_flow"><i>10</i>参加政府或军队采购经历</h2>
            <ul class="ul_list">
                <li class="col-md-12 col-sm-12 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"
                          style="display:none">参加政府或军队采购经历登记表：</span>
                    <div class="col-md-12 col-sm-12 col-xs-12 p0">
                        <textarea class="col-md-12 col-xs-12 col-sm-12 h80" id="purchaseExperience"
                                  onclick="reason(this)" <c:if
                                test="${fn:contains(field,'purchaseExperience')}"> style="border: 1px solid #FF8C00;" onMouseOver="isCompare('purchaseExperience');"</c:if>
                                  <c:if test="${fn:contains(passedField,'purchaseExperience')}">style="border: 1px solid red;"</c:if>>${suppliers.purchaseExperience }</textarea>
                        <c:if test="${fn:contains(passedField,'purchaseExperience')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
            </ul>
            <h2 class="count_flow"><i>11</i>公司简介</h2>
            <ul class="ul_list">
                <li class="col-md-12 col-sm-12 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" style="display:none">公司简介：</span>
                    <div class="col-md-12 col-sm-12 col-xs-12 p0">
                        <textarea class="col-md-12 col-xs-12 col-sm-12 h80" id="description" onclick="reason(this)"<c:if
                                test="${fn:contains(field,'description')}"> style="border: 1px solid #FF8C00;" onMouseOver="isCompare('description');"</c:if>
                                  <c:if test="${fn:contains(passedField,'description')}">style="border: 1px solid red;"</c:if>>${suppliers.description }</textarea>
                        <c:if test="${fn:contains(passedField,'description')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
            </ul>
        </div>

        <div class="col-md-12 col-sm-12 col-xs-12 add_regist tc">
            <a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="zhancun();">暂存</a>
            <a class="btn" type="button" onclick="nextStep();">下一步</a>
        </div>
    </div>
    <form target="_blank" id="download_form_id" action="${pageContext.request.contextPath}/supplierAudit/download.html"
          method="post">
        <input type="hidden" name="fileName"/>
    </form>
</div>
</body>

</html>