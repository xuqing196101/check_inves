<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
    <%@ include file="/WEB-INF/view/common.jsp" %>
    <%@ include file="/WEB-INF/view/common/webupload.jsp" %>
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/upload/ajaxfileupload.js"></script>
    <script type="text/javascript">
        /*分页  */
        $(function () {
            laypage({
                cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
                pages: "${info.pages}", //总页数
                skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
                total: "${info.total}",
                startRow: "${info.startRow}",
                endRow: "${info.endRow}",
                skip: true, //是否开启跳页
                groups: "${info.pages}" >= 3 ? 3 : "${info.pages}", //连续显示分页数
                curr: function () { //通过url获取当前页，也可以同上（pages）方式获取
                    //			        var page = location.search.match(/page=(\d+)/);
                    //			        return page ? page[1] : 1;
                    return "${info.pageNum}";
                }(),
                jump: function (e, first) { //触发分页后的回调
                    if (!first) { //一定要加此判断，否则初始时会无限刷新
                        $("#page").val(e.curr);
                        $("#add_form").submit();

                    }
                }
            });
        });

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

        function view(no) {
            window.location.href = "${pageContext.request.contextPath}/purchaser/queryByNo.html?planNo=" + no + "&&type=1";
        }

        function edit() {
            var id = [];
            $('input[name="chkItem"]:checked').each(function () {
                id.push($(this).val());
            });
            if (id.length == 1) {

                window.location.href = "${pageContext.request.contextPath}/purchaser/queryByNo.html?planNo=" + id + "&&type=2";
                ;
            } else if (id.length > 1) {
                layer.alert("只能选择一个", {
                    offset: ['222px', '390px'],
                    shade: 0.01
                });
            } else {
                layer.alert("请选择需要修改的版块", {
                    offset: ['222px', '390px'],
                    shade: 0.01
                });
            }
        }

        function del() {
            var id = [];
            $('input[name="chkItem"]:checked').each(function () {
                id.push($(this).val());
            });
            if (id.length > 0) {
                layer.confirm('您确定要删除吗?', {
                    title: '提示',
                    offset: ['222px', '360px'],
                    shade: 0.01
                }, function (index) {
                    layer.close(index);
                    $.ajax({
                        url: "${pageContext.request.contextPath}/purchaser/delete.html",
                        type: "post",
                        data: {
                            planNo: $('input[name="chkItem"]:checked').val()
                        },
                        success: function () {
                            window.location.reload();

                        },
                        error: function () {

                        }
                    });
                });
            } else {
                layer.alert("请选择要删除的版块", {
                    offset: ['222px', '390px'],
                    shade: 0.01
                });
            }
        }
        var index;

        function collect() {
            var no = generateMixed();
            $("#cno").val(no);
            var flag = true;
            var ceck = $('input[name="chkItem"]:checked:first').prev().val();

            var id = [];
            $('input[name="chkItem"]:checked').each(function () {
                var type = $(this).prev().val();
                if (ceck != type) {
                    flag = false;
                }
                id.push($(this).val());
            });
            /* 	if(flag == false) {
             layer.alert("类别不一致，不可汇总", {
             offset: ['222px', '390px'],
             shade: 0.01
             });
             } else
             */
            if (id.length >= 1) {
                var status = $("input[name='chkItem']:checked").parents("tr").find("td").eq(7).text();
                if ($.trim(status) == "待汇总") {
                    index = layer.open({
                        type: 1, //page层
                        area: ['500px', '300px'],
                        title: '生成采购计划',
                        closeBtn: 1,
                        shade: 0.01, //遮罩透明度
                        moveType: 1, //拖拽风格，0是默认，1是传统拖动
                        shift: 1, //0-6的动画形式，-1不开启
                        offset: ['80px', '600px'],
                        content: $('#content'),
                    });
                } else {
                    layer.alert("请选择待汇总的采购需求！", {
                        offset: ['222px', '390px'],
                        shade: 0.01
                    });
                }
            } else {
                layer.alert("请选中一条", {
                    offset: ['222px', '390px'],
                    shade: 0.01
                });
            }

        }

        //鼠标移动显示全部内容
        function out(content) {
            if (content.length > 10) {
                layer.msg(content, {
                    icon: 6,
                    shade: false,
                    area: ['600px'],
                    time: 1000 //默认消息框不关闭
                }); //去掉msg图标
            } else {
                layer.closeAll(); //关闭消息框
            }
        }

        function loadplan() {
            index = layer.open({
                type: 1, //page层
                area: ['40%', '40%'],
                title: '导入采购需求',
                closeBtn: 1,
                shade: 0.01, //遮罩透明度
                moveType: 1, //拖拽风格，0是默认，1是传统拖动
                shift: 1, //0-6的动画形式，-1不开启
                offset: ['80px', '26%'],
                content: $('#file_div'),
            });
        }

        function closeLayer() {

            var id = [];
            var de = [];
            var type = "";

            $('input[name="chkItem"]:checked').each(function () {
                type = $(this).prev().val();
                var dep = $(this).next().val();
                de.push(dep);

                id.push($(this).val());
            });
            $("#goodsType").val(type);
            $("#uniqueId").val(id);
            $("#dep").val(de);
            var val = $('input[name="fileName"]').val();
            val = $.trim(val);
            if (val.length > 200) {
                layer.alert("采购计划名称长度不能大于200字");
                return;
            }
            if (val == "") {
                layer.alert("计划名称不允许为空");
            } else {
                $("#collect_form").submit();
                layer.close(index);
            }
        }

        function cancels() {
            layer.close(index);
        }
        var ids = [];

        function collected() {
            var flag = true;
            var ceck = $('input[name="chkItem"]:checked:first').prev().val();
            var goodsType = "";
            $('input[name="chkItem"]:checked').each(function () {
                goodsType = $(this).prev().val();
                if (ceck != goodsType) {
                    flag = false;
                }
                ids.push($(this).val());
            });
            /* if(flag == false) {
             layer.alert("物资类别需要一样", {
             offset: ['222px', '390px'],
             shade: 0.01
             });
             } else  */

            if (ids.length >= 1) {
                var status = $("input[name='chkItem']:checked").parents("tr").find("td").eq(7).text();
                if ($.trim(status) == "待汇总") {
                    layer.open({
                        type: 2, //page层
                        area: ['80%', '90%'],
                        title: '汇入采购计划',
                        closeBtn: 1,
                        shade: 0.01, //遮罩透明度
                        moveType: 0, //拖拽风格，0是默认，1是传统拖动
                        shift: 1, //0-6的动画形式，-1不开启
                        offset: ['0px', '10%'],
                        content: '${pageContext.request.contextPath}/collect/collectlist.html?type=' + goodsType,
                    });
                } else {

                    layer.alert("请选择待汇总的采购需求！", {
                        offset: ['222px', '390px'],
                        shade: 0.01
                    });
                }

            } else {
                layer.alert("请选中一条", {
                    offset: ['222px', '390px'],
                    shade: 0.01
                });
            }

        }

        function advanced() {
            var id = [];
            $('input[name="chkItem"]:checked').each(function () {
                id.push($(this).val());
            });
            if (id.length == 1) {
                window.location.href = "${pageContext.request.contextPath}/advancedProject/add.html?id=" + id;
            } else {
                layer.alert("只能选择一条需求下达", {
                    shade: 0.01
                });
            }
        }

        //重置
        function resetQuery() {
            $("#planName").val("");
            /* $("#planNo").val(""); */
            var status = document.getElementById("status").options;
            status[0].selected = true;
        }

        function generateMixed() {
            var chars = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'];
            var res = "";
            for (var i = 0; i < 6; i++) {
                var id = Math.ceil(Math.random() * 35);
                res += chars[id];
            }
            return res;
        }


        function fileup() {
            var planType = $("#wtype").val();
            $.ajaxFileUpload({
                url: "${pageContext.request.contextPath}/collect/upload.do?planType=" + planType,
                secureuri: false,
                fileElementId: 'cgjh_file',
                dataType: 'json',
                success: function (data) {
                    var bool = true;
                    var chars = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'];
                    if (data == "1") {
                        layer.alert("文件格式错误", {offset: ['222px', '390px'], shade: 0.01});

                    }
                    for (var i = 0; i < chars.length; i++) {
                        if (data.indexOf(chars[i]) != -1) {
                            bool = false;
                        }
                    }
                    if (bool != true) {
                        layer.alert(data, {offset: ['222px', '390px'], shade: 0.01});
                        //  layer.msg(data);
                    }
                    else {
                        layer.alert("上传成功", {offset: ['222px', '390px'], shade: 0.01});
                    }
                    layer.close(index);
                }
            });
        }

        /* 		function view(no) {
         window.location.href = "${pageContext.request.contextPath }/collect/view.html?planNo="+no;
         } */

        //下载
        function down() {
            var id = [];
            $('input[name="chkItem"]:checked').each(function () {
                id.push($(this).val());
            });
            if (id.length == 1) {
                window.location.href = "${pageContext.request.contextPath }/accept/excel.html?uniqueId=" + id;
            } else if (id.length > 1) {
                layer.alert("只能选择一个", {
                    offset: ['222px', '390px'],
                    shade: 0.01
                });
            } else {
                layer.alert("请选中一条", {
                    offset: ['222px', '390px'],
                    shade: 0.01
                });
            }
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
                <a href="javascript:void(0);">保障作业系统</a>
            </li>
            <li>
                <a href="javascript:void(0);">采购计划管理</a>
            </li>
            <li class="active">
                <a href="javascript:jumppage('${pageContext.request.contextPath}/collect/list.html');">采购需求汇总</a>
            </li>
        </ul>
        <div class="clear"></div>
    </div>
</div>
<!-- 录入采购计划开始-->
<div class="container">
    <div class="headline-v2 fl">
        <h2>采购需求列表</h2>
    </div>

    <h2 class="search_detail">
        <form id="add_form" class="mb0" action="${pageContext.request.contextPath }/collect/list.html" method="post">
            <ul class="demand_list">
                <li>
                    <label class="fl">采购需求名称：</label>
                    <span>
					    		<input type="hidden" name="page" id="page">
					  	 		<input type="text" name="planName" id="planName" value="${inf.planName }"/>
					    	</span>
                </li>
                <%-- <li>
             <label class="fl">采购需求编号：</label>
                 <span>
                     <input type="text" name="planNo" id="planNo" value="${inf.planNo }"/>
                 </span>
             </li> --%>
                <li>
                    <label class="fl">状态：</label>
                    <span>
				    	 		<select name="status" id="status">
									<option value="total"> 全部</option>
								   	<option value="3" <c:if test="${status=='3'}"> selected</c:if> >待汇总</option>
								   	<option value="5" <c:if test="${status=='5'}"> selected</c:if> >已汇总</option>
						 
			 	   	   		</select>
				    		</span>
                </li>
            </ul>
            <button type="submit" class="btn fl">查询</button>
            <button type="button" onclick="resetQuery()" class="btn fl">重置</button>
            <div class="clear"></div>

        </form>
    </h2>

    <div class="col-md-12 col-xs-12 col-sm-12 pl20 mt10">
        <c:if test="${auth == 'show'}">
            <button class="btn padding-left-10 padding-right-10 btn_back" onclick="collect()">汇总</button>
            <button class="btn padding-left-10 padding-right-10 btn_back" onclick="collected()">添加至已有计划中</button>
            <button class="btn padding-left-10 padding-right-10 btn_back" onclick="advanced()">下达预研项目</button>
            <button class="btn padding-left-10 padding-right-10 btn_back" onclick="loadplan()">导入采购计划</button>
            <button class="btn padding-left-10 padding-right-10 btn_back" onclick="down()">下载打印</button>
        </c:if>
    </div>
    <div class="content table_box">
        <table class="table table-bordered table-condensed table-hover table-striped ">
            <thead>
            <tr>
                <th class="info w30"><input type="checkbox" id="checkAll" onclick="selectAll()" alt=""></th>
                <th class="info w50">序号</th>
                <th class="info" width="28%">采购需求名称</th>
                <th class="info">需求部门</th>
                <th class="info" width="10%">物资类别</th>
                <th class="info" width="10%">提交日期</th>
                <th class="info" width="15%">预算总金额（万元）</th>
                <th class="info" width="10%">状态</th>
            </tr>
            </thead>
            <c:set var="page" value="0"/>
            <c:forEach items="${info.list}" var="obj" varStatus="vs">
                <c:set var="page" value="${page + 1}"/>
                <c:if test="${obj.status!=3 && obj.status!=5}">
                    <c:set var="page" value="${page - 1}"/>
                </c:if>
                <c:if test="${obj.status==3 || obj.status==5 }">
                    <tr class="pointer">
                        <td class="tc w30">
                            <input type="hidden" value="${obj.planType }">
                                <%-- 	 <c:if test="${obj.status==3 }"> --%>
                            <input type="checkbox" value="${obj.uniqueId }" name="chkItem" onclick="check()" alt="">
                                <%--  </c:if> --%>
                                <%-- <c:if test="${obj.status!=3 }"> --%>
                                <%--  <input type="checkbox" disabled="disabled"  value="${obj.uniqueId }" name="chkItem" onclick="check()"  alt=""> --%>
                                <%--  </c:if> --%>
                            <input type="hidden" value="${obj.department }">
                        </td>
                        <td class="tc" onclick="view('${obj.uniqueId}')">${(page)+(info.pageNum-1)*(info.pageSize)}</td>
                        <td class="tl" onclick="view('${obj.uniqueId}')">${obj.planName }</td>
                        <td class="tl" onclick="view('${obj.uniqueId}')">${obj.department}</td>
                        <td class="tl" onclick="view('${obj.uniqueId}')">
                            <c:forEach items="${dic }" var="dic">
                                <c:if test="${obj.planType==dic.id}">
                                    ${dic.name }
                                </c:if>
                            </c:forEach>
                        </td>
                        <td class="tc" onclick="view('${obj.uniqueId}')"><fmt:formatDate value="${obj.createdAt }"/></td>
                        <td class="tr" onclick="view('${obj.uniqueId}')"><fmt:formatNumber type="number" pattern="#,##0.00" value="${obj.budget}"/></td>
                        <td class="tc">
                            <c:if test="${obj.status=='3' }">
                                待汇总
                            </c:if>

                            <c:if test="${obj.status=='5' }">
                                已汇总
                            </c:if>
                            <%--<c:if test="${obj.status=='5' }">
                                已汇总
                            </c:if>
                            <c:if test="${obj.status=='6' }">
                                审核通过
                            </c:if>
                            <c:if test="${obj.status=='7' }">
                                审核暂存
                            </c:if>--%>
                        </td>
                    </tr>
                </c:if>
            </c:forEach>
        </table>
        <div id="pagediv" align="right"></div>
    </div>
</div>

<div id="content" class="dnone layui-layer-wrap">

    <form id="collect_form" action="${pageContext.request.contextPath }/collect/add.html" method="post">
        <div class="drop_window">
            <ul class="list-unstyled">

                <li class="col-md-12 col-sm-12 col-xs-12 pl15">
                    <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><span
                            class="star_red">*</span>采购计划名称</span>
                    <div class="col-md-12 col-xs-12 col-sm-12 input-append input_group p0">
                        <input class="title col-md-12" name="fileName" type="text">
                    </div>
                </li>
                <!-- <li class="col-md-12 col-sm-12 col-xs-12">
                         <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">计划文号</span>
                         <div class="col-md-12 col-xs-12 col-sm-12 input-append input_group p0"> -->
                <input class="col-xs-12 h80 mt6" name="planNo" id="cno" maxlength="300" type="hidden">
                <!--    </div>
                </li> -->
                <!-- <li class="col-sm-6 col-md-6 p0 col-lg-6 col-xs-6">
                 <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">密码</label>
                   <span class="col-md-12 col-xs-12">
                       <input class="title col-md-12" name="password" maxlength="200" type="password">
                   </span>
                      </li> -->
                <div class="clear"></div>
            </ul>
        </div>
        <div class="tc mt10 col-md-12 col-xs-12 col-dm-12">
            <input type="hidden" name="uniqueId" id="uniqueId" value="">
            <input type="hidden" name="department" id="dep" value="">
            <input type="hidden" name="goodsType" id="goodsType" value="">
            <button type="button" class="btn padding-left-10 padding-right-10 btn_back" onclick="closeLayer()">生成采购计划
            </button>
        </div>

    </form>
</div>
<div class="clear margin-top-30" id="file_div" style="display:none;">
    <form id="up_form" action="${pageContext.request.contextPath}/collect/upload.do" method="post"
          enctype="multipart/form-data">
        <select name="planType" id="wtype" onchange="gtype(this)">
            <c:forEach items="${types }" var="obj">
                <option value="${obj.id }">${obj.name }</option>
            </c:forEach>
        </select>
        <div class="col-md-8 col-sm-8 col-xs-12"><input type="file" id="cgjh_file" name="file"></div>
        <div class="col-md-4 col-sm-4 col-xs-12">
            <input type="button" class="btn" onclick="fileup()" value="导入"/>
        </div>
    </form>
</div>
</body>

</html>