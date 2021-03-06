<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="up" uri="/tld/upload" %>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
    <%@ include file="/WEB-INF/view/common.jsp" %>
    <%@ include file="/WEB-INF/view/common/webupload.jsp" %>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/ses/ems/expertFinalInspect/merge_jump.js"></script>
    <style type="text/css">
        input {
            cursor: pointer;
        }

        textarea {
            cursor: pointer;
        }
    </style>

    <script type="text/javascript">
        $(function () {
            $("#reverse_of_one").attr("class","active");
            $("#reverse_of_one").removeAttr("onclick");

            $(":input").each(function () {
                var onMouseMove = "this.style.background='#E8E8E8'";
                var onmouseout = "this.style.background='#FFFFFF'";
                $(this).attr("onMouseMove", onMouseMove);
                $(this).attr("onmouseout", onmouseout);
            });

            //为只读
            $(":input").each(function () {
                $(this).attr("readonly", "readonly");
            });


        });
    </script>

    <script type="text/javascript">

        function trim(str) { //删除左右两端的空格
            return str.replace(/(^\s*)|(\s*$)/g, "");
        }

        //获取选中子节点id
        $(function () {
            var ids = new Array();
            var checklist1 = document.getElementsByName("chkItem_1");
            for (var i = 0; i < checklist1.length; i++) {
                var vals = checklist1[i].value;
                if (checklist1[i].checked) {
                    ids.push(vals);
                }
            }
            var checklist2 = document.getElementsByName("chkItem_2");
            for (var i = 0; i < checklist2.length; i++) {
                var vals = checklist2[i].value;
                if (checklist2[i].checked) {
                    ids.push(vals);
                }
            }
            var isEdit = "${isEdit}";
            if (isEdit == "0") {
                // 没有修改过
                var index = layer.confirm('该专家在上次审核退回后未做任何修改!', {
                    btn: ['确定'],
                    offset: '100px'
                }, function () {
                    layer.close(index);
                });
            }
        });

        //下一步
        function nextStep() {
            var action = "${pageContext.request.contextPath}/finalInspect/expertType.html";
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
                <a href="javascript:void(0)">支撑系统</a>
            </li>
            <li>
                <a href="javascript:void(0)">专家管理</a>
            </li>
            <c:if test="${sign == 1}">
                <li>
                    <a href="javascript:void(0)" onclick="jumppage('${pageContext.request.contextPath}/expertAudit/list.html?sign=1')">专家初审</a>
                </li>
            </c:if>
            <c:if test="${sign == 2}">
                <li>
                    <a href="javascript:void(0)" onclick="jumppage('${pageContext.request.contextPath}/expertAgainAudit/findBatchDetailsList.html?batchId=${batchId}')">专家复审</a>
                </li>
            </c:if>
            <c:if test="${sign == 3}">
                <li>
                    <a href="javascript:void(0)" onclick="jumppage('${pageContext.request.contextPath}/expertAudit/list.html?sign=3')">专家复查</a>
                </li>
            </c:if>

        </ul>
        <div class="clear"></div>
    </div>
</div>
<div class="container container_box pb70">
    <div class=" content height-350">
        <div class="col-md-12 tab-v2 job-content">
            <%@include file="/WEB-INF/view/ses/ems/expertFinalInspect/common_jump.jsp" %>
            <h2 class="count_flow"><i>1</i>专家个人信息</h2>
            <ul class="ul_list">
                <%-- <li class="col-md-3 col-sm-6 col-xs-12 pl15">
                    <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">专家来源：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="expertsFrom" <c:if test="${fn:contains(editFields,'getExpertsFrom')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('expertsFrom','getExpertsFrom','1');"</c:if> value="${expertsFrom }" type="text"  "/>
                    </div>
                </li> --%>
                <li class="col-md-3 col-sm-6 col-xs-12 pl15">
                    <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">专家姓名：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="relName" <c:if test="${fn:contains(editFields,'getRelName')}"> style="border: 1px solid #FF8C00;" onmouseover="isCompare('relName','getRelName','0');"</c:if> <c:if test="${fn:contains(conditionStr,'专家姓名')}"> style="border: 1px solid red;"</c:if> value="${expert.relName}" type="text" "/>
                        <c:if test="${fn:contains(conditionStr,'专家姓名')}">
                            <div class='abolish'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></div>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="hand col-md-12 col-xs-12 col-sm-12 padding-left-5"  <c:if test="${fn:contains(fileModify,'50')}"> style="border: 1px solid #FF8C00; height: 20px !important;"</c:if> onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'" id="recentPhotos"  ">近期免冠彩色证件照：</span>
                    <div class="col-md-12 col-sm-12 col-xs-12 p0 mb25 h30">
                        <up:show showId="show50" delete="false" businessId="${sysId}" sysKey="${expertKey}" typeId="50"/>
                        <a style="visibility:hidden" id="recentPhotos1" class='abolish'><img style="padding-left: 125px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        <c:if test="${fn:contains(conditionStr,'近期免冠彩色证件照')}"><p id="recentPhotos11" class='abolish'><img style="padding-left: 125px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'></p></c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">性别：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="gender" <c:if test="${fn:contains(conditionStr,'性别')}"> style="border: 1px solid red;"</c:if> <c:if test="${fn:contains(editFields,'getGender')}"> style="border: 1px solid #FF8C00;" style="border: 1px solid #FF8C00;" onmouseover="isCompare('gender','getGender','1');"</c:if> value="${gender }" type="text"  "/>
                        <c:if test="${fn:contains(conditionStr,'性别')}">
                            <div class='abolish'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></div>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">出生日期：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input readonly="readonly" <c:if test="${fn:contains(conditionStr,'出生日期')}"> style="border: 1px solid red;"</c:if> <c:if test="${fn:contains(editFields,'getBirthday')}"> style="border: 1px solid #FF8C00;" style="border: 1px solid #FF8C00;" onmouseover="isCompare('birthday','getBirthday','2');"</c:if> value="<fmt:formatDate type='date' value='${expert.birthday}' dateStyle='default' pattern='yyyy-MM-dd'/>" id="birthday" type="text"  "/>
                        <c:if test="${fn:contains(conditionStr,'出生日期')}">
                            <div class='abolish'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></div>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">政治面貌：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="politicsStatus" <c:if test="${fn:contains(conditionStr,'政治面貌')}"> style="border: 1px solid red;"</c:if>
                               <c:if test="${fn:contains(editFields,'getPoliticsStatus')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('politicsStatus','getPoliticsStatus','1');"</c:if> value="${politicsStatus }" type="text"  "/>
                        <c:if test="${fn:contains(conditionStr,'政治面貌')}">
                            <div class='abolish'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></div>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">民族：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input value="${expert.nation}" <c:if test="${fn:contains(conditionStr,'民族')}"> style="border: 1px solid red;"</c:if>
                               <c:if test="${fn:contains(editFields,'getNation')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('nation','getNation','0');"</c:if> id="nation" type="text"  "/>
                        <c:if test="${fn:contains(conditionStr,'民族')}">
                            <div class='abolish'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></div>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">健康状态：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input value="${expert.healthState}" <c:if test="${fn:contains(conditionStr,'健康状态')}"> style="border: 1px solid red;"</c:if>
                               <c:if test="${fn:contains(editFields,'getHealthState')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('healthState','getHealthState','0');"</c:if> id="healthState" type="text"  "/>
                        <c:if test="${fn:contains(conditionStr,'健康状态')}">
                            <div class='abolish'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></div>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">手机：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input value="${expert.mobile}" <c:if test="${fn:contains(conditionStr,'手机')}"> style="border: 1px solid red;"</c:if>
                               <c:if test="${fn:contains(editFields,'getMobile')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('mobile','getMobile','0');"</c:if> readonly="readonly" id="mobile" type="text"  "/>
                        <c:if test="${fn:contains(conditionStr,'手机')}">
                            <div class='abolish'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></div>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">居民身份证号码：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input value="${expert.idCardNumber}" <c:if test="${fn:contains(conditionStr,'居民身份证号码')}"> style="border: 1px solid red;"</c:if>
                               <c:if test="${fn:contains(editFields,'getIdCardNumber')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('idCardNumber','getIdCardNumber','0');"</c:if> id="idCardNumber" type="text"  "/>
                        <c:if test="${fn:contains(conditionStr,'居民身份证号码')}">
                            <div class='abolish'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></div>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="hand col-md-12 col-xs-12 col-sm-12 padding-left-5" <c:if test="${fn:contains(idCard,'idCard')}"> style="border: 1px solid #FF8C00; height: 20px !important;"</c:if> onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'" id="idCardNumberFile"  ">身份证复印件（正反面）：</span>
                    <div class="col-md-12 col-sm-12 col-xs-12 p0 mb25 h30">
                        <up:show showId="show3" delete="false" businessId="${sysId}" sysKey="${expertKey}" typeId="3"/>
                        <a style="visibility:hidden" id="idCardNumberFile1" class='abolish'><img style="padding-left: 125px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        <c:if test="${fn:contains(conditionStr,'身份证复印件（正反面）')}"><p id="idCardNumberFile11" class='abolish'><img style="padding-left: 125px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'></p></c:if>
                    </div>
                </li>
                <%-- <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">缴纳社会保险证明：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input value="${expert.coverNote}" <c:if test="${fn:contains(editFields,'getCoverNote')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('coverNote','getCoverNote','0');"</c:if> id="coverNote" type="text"  "/>
                    </div>
                </li> --%>


                <%-- <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">省：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="parentName" value="${parentName }" type="text"  " />
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">市：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="sonName" value="${sonName }" type="text"  " <c:if test="${fn:contains(editFields,'getAddress')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('address','getAddress','1');"</c:if>/>
                    </div>
                </li> --%>
                <%--如果是民--%>
                <c:if test="${froms eq 'LOCAL'}">
                    <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">是否缴纳社会保险：</span>
                        <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0" id="coverNote">
                            <c:if test="${expert.coverNote eq '1'}">
                                <input value="是"
                                       <c:if test="${fn:contains(editFields,'getCoverNote')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('coverNote','getCoverNote','1');"</c:if> id="idNumber" type="text"  " <c:if test="${fn:contains(conditionStr,'是否缴纳社会保险')}"> style="border: 1px solid red;"</c:if>/>
                            </c:if>
                            <c:if test="${expert.coverNote eq '2'}">
                                <input value="否"
                                       <c:if test="${fn:contains(editFields,'getCoverNote')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('coverNote','getCoverNote','1');"</c:if> id="idNumber" type="text"  " <c:if test="${fn:contains(conditionStr,'是否缴纳社会保险')}"> style="border: 1px solid red;"</c:if>/>
                            </c:if>
                            <c:if test="${fn:contains(conditionStr,'是否缴纳社会保险')}">
                                <div class='abolish'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></div>
                            </c:if>
                        </div>
                    </li>
                    <c:if test="${expert.coverNote eq '1'}">
                        <li class="col-md-3 col-sm-6 col-xs-12">
                            <span <c:if test="${fn:contains(nsurance,'nsurance')}"> style="border: 1px solid #FF8C00;"</c:if> class="col-md-12 col-xs-12 col-sm-12 padding-left-5" onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'" id="coverNoteFile"  ">缴纳社会保险证明：</span>
                            <div class="col-md-12 col-sm-12 col-xs-12 p0 mb25 h30">
                                <up:show showId="show2" delete="false" businessId="${sysId}" sysKey="${expertKey}" typeId="1"/>
                                <a style="visibility:hidden" id="coverNoteFile1" class='abolish'><img style="padding-left: 125px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                                <c:if test="${fn:contains(conditionStr,'缴纳社会保险证明')}"><p  id="coverNoteFile11" class='abolish'><img style="padding-left: 125px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'></p></c:if>
                            </div>
                        </li>
                    </c:if>
                    <c:if test="${expert.coverNote eq '2'}">
                        <li class="col-md-3 col-sm-6 col-xs-12">
                            <span <c:if test="${fn:contains(retire,'retire')}"> style="border: 1px solid #FF8C00;"</c:if> class="col-md-12 col-xs-12 col-sm-12 padding-left-5" onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'" id="coverNoteFile"  ">退休证书或退休证明：</span>
                            <div class="input-append h30 input_group col-sm-12 col-xs-12 col-md-12 p0">
                                <up:show showId="show2" delete="false" businessId="${sysId}" sysKey="${expertKey}" typeId="2"/>
                                <a style="visibility:hidden" id="coverNoteFile1" class='abolish'><img style="padding-left: 125px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                                <c:if test="${fn:contains(conditionStr,'退休证书或退休证明')}"><p id="coverNoteFile11" class='abolish'><img style="padding-left: 125px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'></p></c:if>
                            </div>
                        </li>
                    </c:if>
                </c:if>

                <%--如果用户是军--%>
                <c:if test="${froms eq 'ARMY'}">
                    <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5 hand">军队人员身份证件类型：</span>
                        <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                            <input id="idType" <c:if test="${fn:contains(conditionStr,'军队人员身份证件类型')}"> style="border: 1px solid red;"</c:if>
                                   <c:if test="${fn:contains(editFields,'getIdType')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('idType','getIdType','1');"</c:if> value="${idType }" type="text"  "/>
                            <c:if test="${fn:contains(conditionStr,'军队人员身份证件类型')}">
                                <div class='abolish'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></div>
                            </c:if>
                        </div>
                    </li>
                    <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">证件号码：</span>
                        <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                            <input value="${expert.idNumber}" <c:if test="${fn:contains(conditionStr,'证件号码')}"> style="border: 1px solid red;"</c:if>
                                   <c:if test="${fn:contains(editFields,'getIdNumber')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('idNumber','getIdNumber','0');"</c:if> id="idNumber" type="text"  "/>
                            <c:if test="${fn:contains(conditionStr,'证件号码')}">
                                <div class='abolish'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></div>
                            </c:if>
                        </div>
                    </li>
                    <li class="col-md-3 col-sm-6 col-xs-12">
                        <span class="hand col-md-12 col-xs-12 col-sm-12 padding-left-5" <c:if test="${fn:contains(fileModify,'12')}"> style="border: 1px solid #FF8C00; height: 20px !important;"</c:if> onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'" id="idNumberFile"  ">军队人员身份证件：</span>
                        <div class="col-md-12 col-sm-12 col-xs-12 p0 mb25 h30">
                            <up:show showId="show1" delete="false" businessId="${sysId}" sysKey="${expertKey}" typeId="12"/>
                            <a style="visibility:hidden" id="idNumberFile1" class='abolish'><img style="padding-left: 125px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                            <c:if test="${fn:contains(conditionStr,'军队人员身份证件')}"><p id="idNumberFile11" class='abolish'><img style="padding-left: 212px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'></p></c:if>
                        </div>
                    </li>
                </c:if>

                <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">固定电话：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input value="${expert.telephone}" <c:if test="${fn:contains(conditionStr,'固定电话')}"> style="border: 1px solid red;"</c:if>
                               <c:if test="${fn:contains(editFields,'getTelephone')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('telephone','getTelephone','0');"</c:if> id="telephone" type="text"  "/>
                        <c:if test="${fn:contains(conditionStr,'固定电话')}">
                            <div class='abolish'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></div>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> 传真电话：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input value="${expert.fax}" <c:if test="${fn:contains(conditionStr,'传真电话')}"> style="border: 1px solid red;"</c:if>
                               <c:if test="${fn:contains(editFields,'getFax')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('fax','getFax','0');"</c:if> id="fax" type="text"  "/>
                        <c:if test="${fn:contains(conditionStr,'传真电话')}">
                            <div class='abolish'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></div>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">个人邮箱：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input value="${expert.email}" <c:if test="${fn:contains(conditionStr,'个人邮箱')}"> style="border: 1px solid red;"</c:if>
                               <c:if test="${fn:contains(editFields,'getEmail')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('email','getEmail','0');"</c:if> id="email" type="text"  "/>
                        <c:if test="${fn:contains(conditionStr,'个人邮箱')}">
                            <div class='abolish'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></div>
                        </c:if>
                    </div>
                </li>
            </ul>

            <div class="padding-top-10 clear">
                <h2 class="count_flow"><i>2</i>专业信息（包括学历和专业）</h2>
                <ul class="ul_list">
                    <li class="col-md-3 col-sm-6 col-xs-12 pl15"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">所在单位：</span>
                        <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                            <input value="${expert.workUnit}" <c:if test="${fn:contains(conditionStr,'所在单位')}"> style="border: 1px solid red;"</c:if>
                                   <c:if test="${fn:contains(editFields,'getWorkUnit')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('workUnit','getWorkUnit','0');"</c:if> id="workUnit" type="text"  "/>
                            <c:if test="${fn:contains(conditionStr,'所在单位')}">
                                <div class='abolish'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></div>
                            </c:if>
                        </div>
                    </li>
                    <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">地区：</span>
                        <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                            <input id="range" value="${parentName }${sonName }" type="text"  " <c:if test="${fn:contains(conditionStr,'地区')}"> style="border: 1px solid red;"</c:if> <c:if test="${fn:contains(editFields,'getAddress')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('range','getAddress','1');"</c:if>/>
                            <c:if test="${fn:contains(conditionStr,'地区')}">
                                <div class='abolish'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></div>
                            </c:if>
                        </div>
                    </li>
                    <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">单位地址：</span>
                        <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                            <input value="${expert.unitAddress}" <c:if test="${fn:contains(conditionStr,'单位地址')}"> style="border: 1px solid red;"</c:if>
                                   <c:if test="${fn:contains(editFields,'getUnitAddress')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('unitAddress','getUnitAddress','0');"</c:if> id="unitAddress" type="text"  "/>
                            <c:if test="${fn:contains(conditionStr,'单位地址')}">
                                <div class='abolish'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></div>
                            </c:if>
                        </div>
                    </li>
                    <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> 单位邮编：</span>
                        <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                            <input value="${expert.postCode}" <c:if test="${fn:contains(conditionStr,'单位邮编')}"> style="border: 1px solid red;"</c:if>
                                   <c:if test="${fn:contains(editFields,'getPostCode')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('postCode','getPostCode','0');"</c:if> id="postCode" type="text"  "/>
                            <c:if test="${fn:contains(conditionStr,'单位邮编')}">
                                <div class='abolish'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></div>
                            </c:if>
                        </div>
                    </li>
                    <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> 现任职务：</span>
                        <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                            <input value="${expert.atDuty}" <c:if test="${fn:contains(conditionStr,'现任职务')}"> style="border: 1px solid red;"</c:if>
                                   <c:if test="${fn:contains(editFields,'getAtDuty')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('atDuty','getAtDuty','0');"</c:if> id="atDuty" type="text"  "/>
                            <c:if test="${fn:contains(conditionStr,'现任职务')}">
                                <div class='abolish'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></div>
                            </c:if>
                        </div>
                    </li>
                    <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">从事专业：</span>
                        <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                            <input value="${expert.major}" <c:if test="${fn:contains(conditionStr,'从事专业')}"> style="border: 1px solid red;"</c:if>
                                   <c:if test="${fn:contains(editFields,'getMajor')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('major','getMajor','0');"</c:if> id="major" type="text"  "/>
                            <c:if test="${fn:contains(conditionStr,'从事专业')}">
                                <div class='abolish'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></div>
                            </c:if>
                        </div>
                    </li>
                    <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> 从事专业起始年月：</span>
                        <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                            <input <c:if test="${fn:contains(editFields,'getTimeStartWork')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('timeStartWork','getTimeStartWork','3');"</c:if>
                            		<c:if test="${fn:contains(conditionStr,'专业起始年月')}"> style="border: 1px solid red;"</c:if>
                                     value="<fmt:formatDate type='date' value='${expert.timeStartWork}' dateStyle='default' pattern='yyyy-MM'/>" readonly="readonly" id="timeStartWork" type="text"  "/>
                            <c:if test="${fn:contains(conditionStr,'专业起始年月')}">
                                <div class='abolish'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></div>
                            </c:if>
                        </div>
                    </li>
                    <c:if test="${froms eq 'LOCAL'}">
                      <li class="col-md-3 col-sm-6 col-xs-12">
                        <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">专业技术职称：</span>
                        <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                            <input maxlength="20" <c:if test="${fn:contains(editFields,'getProfessTechTitles')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('professTechTitles','getProfessTechTitles','0');"</c:if>
                            	<c:if test="${fn:contains(conditionStr,'专业技术职称')}"> style="border: 1px solid red;"</c:if>
                                    value="${expert.professTechTitles}" name="professTechTitles" id="professTechTitles" type="text"  "/>
                            <c:if test="${fn:contains(conditionStr,'专业技术职称')}">
                                <div class='abolish'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></div>
                            </c:if>
                        </div>
	                    </li>
	                    <li class="col-md-3 col-sm-6 col-xs-12">
	                        <span <c:if test="${fn:contains(title,'title')}"> style="border: 1px solid #FF8C00; height: 20px !important;"</c:if> class="hand col-md-12 col-xs-12 col-sm-12 padding-left-5" id="titleFile" onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'"  " id="professTechTitlesFile">专业技术职称证书：</span>
	                        <div class="col-md-12 col-sm-12 col-xs-12 p0 mb25 h30">
	                            <up:show showId="show4" delete="false" businessId="${sysId}" sysKey="${expertKey}" typeId="4"/>
	                            <a style="visibility:hidden" id="titleFile1" class='abolish'><img style="padding-left: 125px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
	                            <c:if test="${fn:contains(conditionStr,'专业技术职称证书')}"><p id="titleFile11" class='abolish'><img style="padding-left: 125px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'></p></c:if>
	                        </div>
	                    </li>
	                    <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">取得技术职称时间：</span>
	                       <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
	                           <input <c:if test="${fn:contains(conditionStr,'取得技术职称时间')}"> style="border: 1px solid red;"</c:if>
	                                   <c:if test="${fn:contains(editFields,'getMakeTechDate')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('makeTechDate','getMakeTechDate','3');"</c:if> value="<fmt:formatDate type='date' value='${expert.makeTechDate}' dateStyle='default' pattern='yyyy-MM'/>" readonly="readonly" id="makeTechDate" type="text"  "/>
	                           <c:if test="${fn:contains(conditionStr,'取得技术职称时间')}">
	                               <div class='abolish'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></div>
	                           </c:if>
	                       </div>
	                    </li>
                    </c:if>
                    <c:if test="${froms eq 'ARMY'}">
	                    <c:if test="${expert.teachTitle eq '1'}">
		                    <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">有无专业技术职称：</span>
		                       <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
		                           <input value="有" <c:if test="${fn:contains(editFields,'getTeachTitle')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('teachTitle','getTeachTitle','0');"</c:if>
		                           		<c:if test="${fn:contains(conditionStr,'有无专业技术职称')}"> style="border: 1px solid red;"</c:if>
		                                   id="teachTitle" type="text"  "/>
		                           <c:if test="${fn:contains(conditionStr,'有无专业技术职称')}">
		                               <div class='abolish'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></div>
		                           </c:if>
		                       </div>
		                    </li>
		                    <li class="col-md-3 col-sm-6 col-xs-12">
                        <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">专业技术职称：</span>
                        <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                            <input maxlength="20"  <c:if test="${fn:contains(editFields,'getProfessTechTitles')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('professTechTitles','getProfessTechTitles','0');"</c:if>
                            	<c:if test="${fn:contains(conditionStr,'专业技术职称')}"> style="border: 1px solid red;"</c:if>
                                   value="${expert.professTechTitles}" name="professTechTitles" id="professTechTitles" type="text"  "/>
                            <c:if test="${fn:contains(conditionStr,'专业技术职称')}">
                                <div class='abolish'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></div>
                            </c:if>
                        </div>
                      </li>
                      <li class="col-md-3 col-sm-6 col-xs-12">
                          <span <c:if test="${fn:contains(fileModify,'4')}"> style="border: 1px solid #FF8C00;"</c:if> class="hand col-md-12 col-xs-12 col-sm-12 padding-left-5" id="titleFile" onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'"  " id="professTechTitlesFile">专业技术职称证书：</span>
                          <div class="col-md-12 col-sm-12 col-xs-12 p0 mb25 h30">
                              <up:show showId="show4" delete="false" businessId="${sysId}" sysKey="${expertKey}" typeId="4"/>
                              <a style="visibility:hidden" id="titleFile1" class='abolish'><img style="padding-left: 125px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                              <c:if test="${fn:contains(conditionStr,'专业技术职称证书')}"><p id="titleFile11" class='abolish'><img style="padding-left: 125px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'></p></c:if>
                          </div>
                      </li>
                      <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">取得技术职称时间：</span>
                         <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                             <input <c:if test="${fn:contains(conditionStr,'取得技术职称时间')}"> style="border: 1px solid red;"</c:if>
                                     <c:if test="${fn:contains(editFields,'getMakeTechDate')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('makeTechDate','getMakeTechDate','3');"</c:if> value="<fmt:formatDate type='date' value='${expert.makeTechDate}' dateStyle='default' pattern='yyyy-MM'/>" readonly="readonly" id="makeTechDate" type="text"  "/>
                             <c:if test="${fn:contains(conditionStr,'取得技术职称时间')}">
                                 <div class='abolish'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></div>
                             </c:if>
                         </div>
                      </li>
		                  </c:if>
		                  <c:if test="${expert.teachTitle eq '2'}">
	                        <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">有无专业技术职称：</span>
	                            <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
	                                <input value="无" <c:if test="${fn:contains(conditionStr,'有无专业技术职称')}"> style="border: 1px solid red;"</c:if>
	                                       <c:if test="${fn:contains(editFields,'getTeachTitler')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('teachTitle','getTeachTitle','0');"</c:if> id="teachTitle" type="text"  "/>
	                                <c:if test="${fn:contains(conditionStr,'有无专业技术职称')}">
	                                    <div class='abolish'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></div>
	                                </c:if>
	                            </div>
	                        </li>
	                    </c:if>
                    </c:if>
                    <li class="col-md-3 col-sm-6 col-xs-12 pl15"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">毕业院校及专业：</span>
                        <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                            <input value="${expert.graduateSchool}" <c:if test="${fn:contains(conditionStr,'毕业院校及专业')}"> style="border: 1px solid red;"</c:if>
                                   <c:if test="${fn:contains(editFields,'getGraduateSchool')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('graduateSchool','getGraduateSchool','0');"</c:if> id="graduateSchool" type="text"  "/>
                            <c:if test="${fn:contains(conditionStr,'毕业院校及专业')}">
                                <div class='abolish'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></div>
                            </c:if>
                        </div>
                    </li>
                    <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">最高学历：</span>
                        <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                            <input id="hightEducation" <c:if test="${fn:contains(conditionStr,'最高学历')}"> style="border: 1px solid red;"</c:if>
                                   <c:if test="${fn:contains(editFields,'getHightEducation')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('hightEducation','getHightEducation','1');"</c:if> value="${hightEducation }" type="text"  "/>
                            <c:if test="${fn:contains(conditionStr,'最高学历')}">
                                <div class='abolish'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></div>
                            </c:if>
                        </div>
                    </li>
                    <li class="col-md-3 col-sm-6 col-xs-12">
                        <span class="hand col-md-12 col-xs-12 col-sm-12 padding-left-5" <c:if test="${fn:contains(graduation,'graduation')}"> style="border: 1px solid #FF8C00; height: 20px !important;"</c:if> onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'" id="academic"  ">毕业证书：</span>
                        <div class="col-md-12 col-sm-12 col-xs-12 p0 mb25 h30">
                            <up:show showId="show5" delete="false" businessId="${sysId}" sysKey="${expertKey}" typeId="5"/>
                            <a style="visibility:hidden" id="academic1" class='abolish'><img style="padding-left: 125px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                            <c:if test="${fn:contains(conditionStr,'毕业证书')}"><p id="academic11" class='abolish'><img style="padding-left: 212px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'></p></c:if>
                        </div>
                    </li>
                    <li class="col-md-3 col-sm-6 col-xs-12">
                        <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> 最高学位：</span>
                        <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                            <input value="${degree}" <c:if test="${fn:contains(conditionStr,'最高学位')}"> style="border: 1px solid red;"</c:if>
                                   <c:if test="${fn:contains(editFields,'getDegree')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('degree','getDegree','1');"</c:if> id="degree" type="text"  "/>
                            <c:if test="${fn:contains(conditionStr,'最高学位')}">
                                <div class='abolish'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></div>
                            </c:if>
                        </div>
                    </li>
                    <li class="col-md-3 col-sm-6 col-xs-12">
                        <span class="hand col-md-12 col-xs-12 col-sm-12 padding-left-5" <c:if test="${fn:contains(fileModify,'6')}"> style="border: 1px solid #FF8C00; height: 20px !important;"</c:if> onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'" id="degreeFile"  ">学位证书：</span>
                        <div class="col-md-12 col-sm-12 col-xs-12 p0 mb25 h30">
                            <up:show showId="show6" delete="false" businessId="${sysId}" sysKey="${expertKey}" typeId="6"/>
                            <a style="visibility:hidden" id="degreeFile1" class='abolish'><img style="padding-left: 125px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                            <c:if test="${fn:contains(conditionStr,'学位证书')}"><p id="degreeFile11" class='abolish'><img style="padding-left: 212px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'></p></c:if>
                        </div>
                    </li>
                    <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> 参加工作时间：</span>
                        <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                            <input readonly="readonly" <c:if test="${fn:contains(conditionStr,'参加工作时间')}"> style="border: 1px solid red;"</c:if>
                                   <c:if test="${fn:contains(editFields,'getTimeToWork')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('timeToWork','getTimeToWork','3');"</c:if> value="<fmt:formatDate value='${expert.timeToWork}' pattern='yyyy-MM'/>" id="timeToWork" type="text"  "/>
                            <c:if test="${fn:contains(conditionStr,'参加工作时间')}">
                                <div class='abolish'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></div>
                            </c:if>
                        </div>
                    </li>
                </ul>
                
                <div class="clear"></div>
                <h2 class="count_flow"><i>3</i>推荐信</h2>
                <ul class="ul_list">

                    <li class="col-md-3 col-sm-6 col-xs-12">
                        <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">相关机关事业部门推荐信：</span>
                        <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 h30">
                            <c:if test="${expert.isReferenceLftter eq '2'}">
                                <input value="无" <c:if test="${fn:contains(conditionStr,'相关机关事业部门推荐信')}"> style="border: 1px solid red;"</c:if>
                                       <c:if test="${fn:contains(editFields,'getIsReferenceLftter')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('isReferenceLftter','getIsReferenceLftter','0');"</c:if> id="isReferenceLftter" type="text"  "/>
                                <c:if test="${fn:contains(conditionStr,'相关机关事业部门推荐信')}">
                                    <div class='abolish'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></div>
                                </c:if>
                            </c:if>
                            <c:if test="${expert.isReferenceLftter eq '1'}">
                                <input value="有" <c:if test="${fn:contains(conditionStr,'相关机关事业部门推荐信')}"> style="border: 1px solid red;"</c:if>
                                       <c:if test="${fn:contains(editFields,'getIsReferenceLftter')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('isReferenceLftter','getIsReferenceLftter','0');"</c:if> id="isReferenceLftter" type="text"  "/>
                                <c:if test="${fn:contains(conditionStr,'相关机关事业部门推荐信')}">
                                    <div class='abolish'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></div>
                                </c:if>
                            </c:if>
                        </div>
                    </li>

                    <c:if test="${expert.isReferenceLftter eq '1'}">
                        <li class="col-md-3 col-sm-6 col-xs-12">
                            <span class="hand col-md-12 col-xs-12 col-sm-12 padding-left-5" <c:if test="${fn:contains(fileModify,'8')}"> style="border: 1px solid #FF8C00; height: 20px !important;"</c:if> onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'" id="recommend"  ">推荐信：</span>
                            <div class="col-md-12 col-sm-12 col-xs-12 p0 mb25 h30">
                                <up:show showId="show8" delete="false" businessId="${sysId}" sysKey="${expertKey}" typeId="8"/>
                                <a style="visibility:hidden" id="recommend1" class='abolish'><img style="padding-left: 125px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                                <c:if test="${fn:contains(conditionStr,'推荐信')}"><p id="recommend11" class='abolish'><img style="padding-left: 125px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'></p></c:if>
                            </div>
                        </li>
                    </c:if>
                </ul>
                
                <!-- 主要工作经历-->
                <div class="clear"></div>
                <h2 class="count_flow"><i>4</i>主要工作经历</h2>
                <ul class="ul_list">
                    <li class="col-md-12 col-sm-12 col-xs-12">
                        <div class="col-md-12 col-sm-12 col-xs-12 p0">
                            <textarea rows="10" <c:if test="${fn:contains(conditionStr,'主要工作经历')}"> style="border: 1px solid red;"</c:if>
                                      <c:if test="${fn:contains(editFields,'getJobExperiences')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('jobExperiences','getJobExperiences','0');"</c:if> id="jobExperiences" style="height: 150px; width: 100%; resize: none;"  class="col-md-12 col-xs-12 col-sm-12 h80">${expert.jobExperiences}</textarea>
                            <c:if test="${fn:contains(conditionStr,'主要工作经历')}">
                                <div class='abolish'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></div>
                            </c:if>
                        </div>
                    </li>
                </ul>
                
                <div class="clear"></div>
                <h2 class="count_flow"><i>5</i>获奖证书(限国家科技进步三等或军队科技进步二等以上奖项)</h2>
                <ul class="ul_list">
                    <li class="col-md-3 col-sm-6 col-xs-12">
                        <span class="hand" <c:if test="${fn:contains(fileModify,'7')}"> style="border: 1px solid #FF8C00; height: 20px !important;"</c:if> id="degreeTypeid" onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'" id="titleType"  ">获奖证书：</span>
                        <div class="m_inline"><up:show showId="show7" delete="false" businessId="${sysId}" sysKey="${expertKey}" typeId="7"/></div>
                        <a style="visibility:hidden" id="degreeTypeid1" class='abolish'><img style="padding-left: 125px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        <c:if test="${fn:contains(conditionStr,'获奖证书')}"> <div id="degreeTypeid11" class='abolish t0 f0'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></div></c:if>
                    <li>
                </ul>

                <!-- 专业学术成果 -->
                <div class="clear"></div>
                <h2 class="count_flow"><i>6</i>专业学术成果</h2>
                <ul class="ul_list">
                    <li class="col-md-12 col-sm-12 col-xs-12">
                        <div class="col-md-12 col-sm-12 col-xs-12 p0">
                            <textarea rows="10" <c:if test="${fn:contains(conditionStr,'专业学术成果')}"> style="border: 1px solid red;"</c:if>
                                      <c:if test="${fn:contains(editFields,'getAcademicAchievement')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('academicAchievement','getAcademicAchievement','0');"</c:if> id="academicAchievement" style="height: 150px; width: 100%; resize: none;"  class="col-md-12 col-xs-12 col-sm-12 h80">${expert.academicAchievement}</textarea>
                            <c:if test="${fn:contains(conditionStr,'专业学术成果')}">
                                <div class='abolish'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></div>
                            </c:if>
                        </div>
                    </li>
                </ul>

                <!-- 主要工作经历-->
                <div class="clear"></div>
                <h2 class="count_flow"><i>7</i>参加军队地方采购评审情况</h2>
                <ul class="ul_list">
                    <li class="col-md-12 col-sm-12 col-xs-12">
                        <div class="col-md-12 col-sm-12 col-xs-12 p0">
                            <textarea rows="10" <c:if test="${fn:contains(conditionStr,'参加军队地方采购评审情况')}"> style="border: 1px solid red;"</c:if>
                                      <c:if test="${fn:contains(editFields,'getReviewSituation')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('reviewSituation','getReviewSituation','0');"</c:if> id="reviewSituation" style="height: 150px; width: 100%; resize: none;"  class="col-md-12 col-xs-12 col-sm-12 h80">${expert.reviewSituation}</textarea>
                            <c:if test="${fn:contains(conditionStr,'参加军队地方采购评审情况')}">
                                <div class='abolish'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></div>
                            </c:if>
                        </div>
                    </li>
                </ul>

                <!-- 主要工作经历-->
                <div class="clear"></div>
                <h2 class="count_flow"><i>8</i>需要申请回避的情况</h2>
                <ul class="ul_list">
                    <li class="col-md-12 col-sm-12 col-xs-12">
                        <div class="col-md-12 col-sm-12 col-xs-12 p0">
                            <textarea rows="10" <c:if test="${fn:contains(conditionStr,'需要申请回避的情况')}"> style="border: 1px solid red;"</c:if>
                                      <c:if test="${fn:contains(editFields,'getAvoidanceSituation')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('avoidanceSituation','getAvoidanceSituation','0');"</c:if> id="avoidanceSituation" style="height: 150px; width: 100%; resize: none;"  class="col-md-12 col-xs-12 col-sm-12 h80">${expert.avoidanceSituation}</textarea>
                            <c:if test="${fn:contains(conditionStr,'需要申请回避的情况')}">
                                <div class='abolish'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></div>
                            </c:if>
                        </div>
                    </li>
                </ul>
            </div>

            <!-- 专家专业信息 -->
            <%-- <h2 class="count_flow"><i>4</i>专家类别</h2>
            <ul class="ul_list" id="expertType"  ">
                <li class="col-md-3 col-sm-6 col-xs-12 pl10" >
                    <div class="input-append col-sm-12 col-xs-12 col-md-12 p0" >
                        <c:forEach items="${spList}" var="sp" >
                          <span><input type="checkbox" name="chkItem_1" value="${sp.id}" />${sp.name} </span>
                      </c:forEach>
                      <c:forEach items="${jjList}" var="jj" >
                            <span><input type="checkbox" name="chkItem_2"  value="${jj.id}" />${jj.name} </span>
                        </c:forEach>
                    </div>
                </li>
            </ul> --%>
        </div>
        <div class="add_regist tc m_btn_ab">
          <a class="btn mb0 mr0" type="button" onclick="nextStep();">下一步</a>
          <a class="btn" type="button" onclick="jump('expertAttachment')">转至复查</a>
        </div>
    </div>
</div>
<input value="${expertId}" id="expertId" type="hidden">
<form id="form_id" action="" method="post">
    <input name="expertId" value="${expertId}" type="hidden">
    <input name="sign" value="${sign}" type="hidden">
    <input name="batchId" value="${batchId}" type="hidden">
    <input name="isReviewRevision" value="${isReviewRevision}" type="hidden">
    <input name="isCheck" value="${isCheck}" type="hidden">
    <input id="notCount" name="notCount" value="${notCount}" type="hidden">\
    <input id="over" name="over" value="${over}" type="hidden">
    <input id="finalInspectNumber" name="finalInspectNumber" value="${expert.finalInspectCount==null?1:expert.finalInspectCount+1}" type="hidden">
</form>
<input id="status" value=" ${expert.status}" type="hidden">
</body>
</html>