<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp" %>
    <script type="text/javascript">
      $(function() {
        laypage({
          cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
          pages: "${result.pages}", //总页数
          skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
          skip: true, //是否开启跳页
          total: "${result.total}",
          startRow: "${result.startRow}",
          endRow: "${result.endRow}",
          groups: "${result.pages}" >= 3 ? 3 : "${result.pages}", //连续显示分页数
          curr: function() { //合格url获取当前页，也可以同上（pages）方式获取
            return "${result.pageNum}";
          }(),
          jump: function(e, first) { //触发分页后的回调
            if(!first) { //一定要加此判断，否则初始时会无限刷新
              $("#pageNum").val(e.curr);
              $("#formSearch").submit();
            }
          }
        });
      });
    </script>

    <script type="text/javascript">
        function view(expertId,sign){
            window.location.href = "${pageContext.request.contextPath}/expertAudit/basicInfo.html?expertId="+expertId+"&sign="+sign;
        }
      //审核
      function shenhe(id) {
          if (id == null) {
              var size = $(":checkbox:checked").size();
              if (size == 0) {
                  layer.msg("请选择专家 !", {
                      offset: '100px',
                  });
                  return;
              }
              var id = $(":checkbox:checked").val();
              if (size > 1) {
                  layer.msg("只能选择一项 !", {
                      offset: '100px',
                  });
                  return;
              }
          }
        var state = $("#" + id + "").parent("tr").find("td").eq(11).text(); //.trim();
        state = trim(state);
        /* var isExtract = $("#" + id + "_isExtract").text(); */
        if(state == "公示中" ||state == "初审合格" || state == "初审未合格" || state == "退回修改" || state == "初审退回" || state == "复查合格" || state == "复查未合格" || state == "复审合格" || state == "复审不合格") {
          layer.msg("请选择待审核项 !", {
            offset: '100px',
          });
          return;
        }
        /* //抽取之后的才能复核
        if(isExtract != 1 && state == "待复核") {
          layer.msg("该专家未抽取 !", {
            offset: '100px',
          });
          return;
        } */
        layer.alert('点击审核项,弹出不合格理由框！', {
            title: '审核操作说明：',
            skin: 'layui-layer-molv', //样式类名
            closeBtn: 0,
            offset: '100px',
            shift: 4 //动画类型
          },
          function() {
            $("input[name='expertId']").val(id);
            $("#form_id").attr("action", "${pageContext.request.contextPath}/expertAudit/basicInfo.html");
            $("#form_id").submit();
          });
      }

      function trim(str) { //删除左右两端的空格
        return str.replace(/(^\s*)|(\s*$)/g, "");
      }

      //下载
      function downloadTable(str) {
        var size = $(":checkbox:checked").size();
        if(size == 0) {
          layer.msg("请选择专家 !", {
            offset: '100px',
          });
        } else if(size == 1) {
          var id = $(":checkbox:checked").val();
          var state = $("#" + id + "").parent("tr").find("td").eq(11).text(); //.trim();
          state = trim(state);
          if(state =="预初审合格" || state =="预初审不合格" || state == "复审预合格" || state == "初审合格" || state == "初审未合格" || state == "退回修改" || state == "复审合格" || state == "复审不合格" || state == "复查合格" || state == "复查未合格") {
            $("input[name='tableType']").val(str);
            $("input[name='expertId']").val(id);
            $("#form_id").attr("action", "${pageContext.request.contextPath}/expertAudit/download.html");
            $("#form_id").submit();
          } else {
            layer.msg("请选择审核过的专家 !", {
              offset: '100px',
            });
          }
        } else if(size > 1) {
          layer.msg("只能选择一条 !", {
            offset: '100px',
          });
        }
      }

      //重置搜索栏

      function resetForm() {
        $("input[name='relName']").val("");
        $("input[name='auditAt']").val("");
        //还原select下拉列表只需要这一句
        //$("#status option:selected").removeAttr("selected");
        //这里下标1，写的定值，当然可以根据需要得到加载页面过来的值
        document.getElementById('status')[0].selected = true;
        $("#formSearch").submit();
      }

      //发布
      function publish() {
        var id = $(":checkbox:checked").val();
        var size = $(":checkbox:checked").size();
        var state = $("#" + id + "").parents("tr").find("td").eq(11).text(); //.trim();
        state = trim(state);
        if(size == 1) {
          if(state == "复审合格" || state == "待复查" || state == "复查合格" || state == "复查未合格") {
            $.ajax({
              url: "${pageContext.request.contextPath}/expertAudit/publish.html",
              data: "expertId=" + id,
              type: "post",
              datatype: "json",
              success: function(result) {
                result = eval("(" + result + ")");
                if(result == "yes") {
                  layer.msg("发布成功!", {
                    offset: '100px'
                  });
                  window.setTimeout(function() {
                    $("#status option:selected").removeAttr("selected");
                    $("#formSearch").submit();
                  }, 1000);
                } else {
                  layer.msg('该专家已发布过！', {
                    shift: 6,
                    offset: '100px'
                  });
                }
              },
              error: function() {
                layer.msg("发布失败！", {
                  offset: '100px'
                });
              }
            });
          } else {
            layer.msg("请选择复审合格的专家！", {
              offset: '100px'
            });
          }
        } else if(size > 1) {
          layer.msg("只能选择一项 !", {
            offset: '100px',
          });
        } else {
          layer.msg("请选择专家 !", {
            offset: '100px',
          });
        }

      }

      //禁用F12键及右键
      function click(e) {
        if(document.layers) {
          if(e.which == 3) {
            oncontextmenu = 'return false';
          }
        }
      }
      if(document.layers) {
        document.captureEvents(Event.MOUSEDOWN);
      }
      document.onmousedown = click;
      document.oncontextmenu = new Function("return false;");
      document.onkeydown = document.onkeyup = document.onkeypress = function() {
        if(window.event.keyCode == 123) {
          window.event.returnValue = false;
          return(false);
        }
      };

      //添加签字人员
      function tianjia() {
        var ids = [];
        $('input[type="checkbox"]:checked').each(function(i) {
          ids.push($(this).val());
        });
        if(ids.length > 0) {
          $.ajax({
            url: "${pageContext.request.contextPath}/expertAudit/signature.do?ids=" + ids,
            type: "post",
            success: function(result) {
              if(result == "yes") {
                layer.open({
                  type: 2,
                  title: '填写签字人员信息',
                  // skin : 'layui-layer-rim', //加上边框
                  area: ['800px', '500px'], //宽高
                  offset: '20px',
                  scrollbar: false,
                  content: '${pageContext.request.contextPath}/expertAudit/addSignature.html?ids=' + ids, //url
                  closeBtn: 1, //不显示关闭按钮
                });
              } else {
                layer.msg(result + "已添加过！", {
                  offset: '100px',
                });
              }
            }
          });
        } else {
          layer.msg("请选择专家 !", {
            offset: '100px',
          });
        }
      };
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
          <li>
            <c:if test="${sign == 1}">
              <a href="javascript:void(0)" onclick="jumppage('${pageContext.request.contextPath}/expertAudit/list.html?sign=1')">专家初审</a>
            </c:if>
            <c:if test="${sign == 2}">
              <a href="javascript:void(0)" onclick="jumppage('${pageContext.request.contextPath}/expertAudit/list.html?sign=2')">专家复审</a>
            </c:if>
            <c:if test="${sign == 3}">
              <a href="javascript:void(0)" onclick="jumppage('${pageContext.request.contextPath}/expertAudit/list.html?sign=3')">专家复查</a>
            </c:if>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>
    <!-- 我的订单页面开始-->
    <div class="container">
      <div class="headline-v2">
        <h2>专家审核列表</h2>
      </div>
      <h2 class="search_detail">
        <form id="form_id" action="${pageContext.request.contextPath}/expertAudit/basicInfo.html" method="post">
          <input name="expertId" type="hidden" />
          <input name="sign" type="hidden" value="${sign }"/>
          <input name="tableType" type="hidden" value=""/>
        </form>
        <form action="${pageContext.request.contextPath}/expertAudit/list.html" method="post" id="formSearch" class="mb0">
          <input type="hidden" name="pageNum" id="pageNum">
          <input type="hidden" name="sign" value="${sign }">
          <ul class="demand_list">
            <li>
              <label class="fl">专家姓名：</label>
              <input type="text" name="relName" value="${relName }">
            </li>
            <li>
              <label class="fl">状态：</label>
              <select name="status" class="w178" id="status">
                <option value="">全部</option>
                <c:if test="${sign == 1}">
                  <option <c:if test="${state eq '0'}">selected</c:if> value="0">待初审</option>
                  <option <c:if test="${state eq '1'}">selected</c:if> value="1">初审合格</option>
                  <option <c:if test="${state eq '3'}">selected</c:if> value="3">退回修改</option>
                  <option <c:if test="${state eq '2'}">selected</c:if> value="2">初审未合格</option>
                  <option <c:if test="${state eq '15'}">selected</c:if> value="15">预初审合格</option>
                  <option <c:if test="${state eq '16'}">selected</c:if> value="16">预初审不合格</option>
                </c:if>
                <c:if test="${sign == 2}">
                  <option <c:if test="${state eq '1'}">selected</c:if> value="1">待复审</option>
                  <option <c:if test="${state eq '-2'}">selected</c:if> value="-2">复审预合格</option>
                  <option <c:if test="${state eq '-3'}">selected</c:if> value="-3">公示中</option>
                  <option <c:if test="${state eq '4'}">selected</c:if> value="4">复审合格</option>
                  <option <c:if test="${state eq '5'}">selected</c:if> value="5">复审不合格</option>
                </c:if>
                <c:if test="${sign == 3}">
                  <option <c:if test="${state eq '6'}">selected</c:if> value="6">待复查</option>
                  <option <c:if test="${state eq '7'}">selected</c:if> value="7">复查合格</option>
                  <option <c:if test="${state eq '8'}">selected</c:if> value="8">复查未合格</option>
                </c:if>
              </select>
            </li>
            <li>
              <label class="fl">审核时间：</label>
                <span>
                  <input id="auditAt" name="auditAt" class="Wdate w178 fl" value='<fmt:formatDate value="${auditAt}" pattern="YYYY-MM-dd"/>' type="text" onClick="WdatePicker()" />
                </span>
            </li>
          </ul>
          <div class="col-md-12 clear tc mt10">
            <input class="btn" value="查询" type="submit">
          <button onclick="resetForm();" class="btn" type="button">重置</button>
          </div>
          <div class="clear"></div>
        </form>
      </h2>
      <!-- 表格开始-->
      <div class="col-md-12 pl20 mt10">
        <button class="btn btn-windows check" type="button" onclick="shenhe();">审核</button>
        <c:if test="${sign == 2 or sign == 3}">
          <a class="btn btn-windows apply" onclick='publish()' type="button">发布</a>
        </c:if>
        <c:if test="${sign == 1 }">
          <a class="btn btn-windows input" onclick='downloadTable(1)' href="javascript:void(0)">下载初审表</a>
        </c:if>
        <c:if test="${sign == 2 }">
          <a class="btn btn-windows input" onclick='downloadTable(2)' href="javascript:void(0)">下载入库复审表</a>
          <button class="btn btn-windows add" type="button" onclick="tianjia();">添加签字人员</button>
        </c:if>
        <c:if test="${sign == 3 }">
          <a class="btn btn-windows input" onclick='downloadTable(3)' href="javascript:void(0)">下载复查表</a>
        </c:if>
      </div>

      <div class="content table_box">
        <table class="table table-bordered table-condensed table-hover table-striped hand">
          <thead>
            <tr>
              <th class="info w50">选择</th>
              <th class="info w50">序号</th>
              <th class="info">专家姓名</th>
              <th class="info w50">性别</th>
              <th class="info">手机号</th>
              <!-- <th class="info">类型</th> -->
              <th class="info">毕业院校及专业</th>
              <th class="info">工作单位</th>
              <th class="info">审核时间</th>
              <th class="info">提交时间</th>
              <th class="info">采购机构</th>
              <th class="info">发布</th>
              <th class="info">审核状态</th>
              <th class="info">审核人</th>
            </tr>
          </thead>
          <c:forEach items="${expertList}" var="expert" varStatus="vs">
            <tr>
              <td class="tc w50"><input name="id" type="checkbox" value="${expert.id}"></td>
              <td class="tc w50">${(vs.count)+(result.pageNum-1)*(result.pageSize)}</td>
              <td class="tl" title="${expert.relName}">
                <c:if test="${fn:length(expert.relName) >4 }"><a href="javascript:;" onclick="view('${expert.id}',${sign})">${fn:substring(expert.relName,0,4)}...</a></c:if>
                <c:if test="${fn:length(expert.relName) <=4}"><a href="javascript:;" onclick="view('${expert.id}',${sign})">${expert.relName}</a></c:if>
              </td>
              <td class="tc" onclick="shenhe('${expert.id}');">${expert.sex}</td>
              <td class="tc" onclick="shenhe('${expert.id}');">${expert.mobile}</td>
              <%--<td class="tl">${expert.expertsTypeId}</td>--%>
              <td class="tl" onclick="shenhe('${expert.id}');" title="${expert.graduateSchool }">
                <c:if test="${fn:length(expert.graduateSchool) >8 }">${fn:substring(expert.graduateSchool,0,8)}...</c:if>
                <c:if test="${fn:length(expert.graduateSchool) <=8}">${expert.graduateSchool}</c:if>
              </td>
              <td class="tl" onclick="shenhe('${expert.id}');" title="${expert.workUnit }">
                <c:if test="${fn:length(expert.workUnit) >8}">${fn:substring(expert.workUnit,0,8)}...</c:if>
                <c:if test="${fn:length(expert.workUnit) <=8}">${expert.workUnit}</c:if>
              </td>
              <td class="tc" onclick="shenhe('${expert.id}');">
                <fmt:formatDate type='date' value='${expert.auditAt }' dateStyle="default" pattern="yyyy-MM-dd" />
              </td>
              <td class="tc" onclick="shenhe('${expert.id}');">
                <fmt:formatDate type='date' value='${expert.submitAt }' dateStyle="default" pattern="yyyy-MM-dd" />
              </td>
              <td class="tl" onclick="shenhe('${expert.id}');">${expert.orgName }</td>
              <td class="tc" id="${expert.id}" onclick="shenhe('${expert.id}');">
                <c:if test="${expert.isPublish == 1 }"><span class="label rounded-2x label-u">已发布</span></c:if>
                <c:if test="${expert.isPublish == 0 }"><span class="label rounded-2x label-dark">未发布</span></c:if>
              </td>
              <c:if test="${(sign == 1 and expert.status eq '0' and expert.auditTemporary ne '1')}">
                <td class="tc"><span class="label rounded-2x label-u" onclick="shenhe('${expert.id}');">待初审</span></td>
              </c:if>
              <c:if test="${sign == 1 and expert.status eq '0' and expert.auditTemporary eq '1'}">
                <td class="tc"><span class="label rounded-2x label-u" onclick="shenhe('${expert.id}');">初审中</span></td>
              </c:if>
              <c:if test="${sign == 1 and expert.status eq '1' }">
                <td class="tc"><span class="label rounded-2x label-dark" onclick="shenhe('${expert.id}');">初审合格</span></td>
              </c:if>
              <c:if test="${sign == 1 and expert.status eq '2' }">
                <td class="tc"><span class="label rounded-2x label-dark" onclick="shenhe('${expert.id}');">初审未合格</span></td>
              </c:if>
              <c:if test="${sign == 1 and expert.status eq '3' }">
                <td class="tc"><span class="label rounded-2x label-dark" onclick="shenhe('${expert.id}');">退回修改</span></td>
              </c:if>
              <c:if test="${sign == 1 and expert.status eq '15' }">
                <td class="tc"><span class="label rounded-2x label-u" onclick="shenhe('${expert.id}');">预初审合格</span></td>
              </c:if>
              <c:if test="${sign == 1 and expert.status eq '16' }">
                <td class="tc"><span class="label rounded-2x label-u" onclick="shenhe('${expert.id}');">预初审不合格</span></td>
              </c:if>
              <c:if test="${sign == 2 and expert.status eq '1' and expert.auditTemporary ne '2'}">
                <td class="tc"><span class="label rounded-2x label-u" onclick="shenhe('${expert.id}');">待复审</span></td>
              </c:if>
              <c:if test="${sign == 2 and expert.status eq '1' and expert.auditTemporary eq '2'}">
                <td class="tc"><span class="label rounded-2x label-u" onclick="shenhe('${expert.id}');">复审中</span></td>
              </c:if>
              <c:if test="${sign == 2 and expert.status eq '4' }">
                <td class="tc"><span class="label rounded-2x label-dark" onclick="shenhe('${expert.id}');">复审合格</span></td>
              </c:if>
              <c:if test="${sign == 2 and expert.status eq '-2' }">
                <td class="tc"><span class="label rounded-2x label-dark" onclick="shenhe('${expert.id}');">复审预合格</span></td>
              </c:if>
              <c:if test="${sign == 2 and expert.status eq '-3' }">
                <td class="tc"><span class="label rounded-2x label-dark" onclick="shenhe('${expert.id}');">公示中</span></td>
               </c:if>
              <c:if test="${sign == 2 and expert.status eq '5' }">
                <td class="tc"><span class="label rounded-2x label-dark" onclick="shenhe('${expert.id}');">复审不合格</span></td>
              </c:if>
              <c:if test="${sign == 3 and expert.status eq '6' and expert.auditTemporary ne '3'}">
                <td class="tc"><span class="label rounded-2x label-u" onclick="shenhe('${expert.id}');">待复查</span></td>
              </c:if>
              <c:if test="${sign == 3 and expert.status eq '6' and expert.auditTemporary eq '3'}">
                <td class="tc"><span class="label rounded-2x label-u" onclick="shenhe('${expert.id}');">复查中</span></td>
              </c:if>
              <c:if test="${sign == 3 and expert.status eq '7' }">
                <td class="tc"><span class="label rounded-2x label-dark" onclick="shenhe('${expert.id}');">复查合格</span></td>
              </c:if>
              <c:if test="${sign == 3 and expert.status eq '8' }">
                <td class="tc"><span class="label rounded-2x label-dark" onclick="shenhe('${expert.id}');">复查未合格</span></td>
              </c:if>
              <td class="" onclick="shenhe('${expert.id}');">
                <c:choose>
                  <c:when test="${expert.auditor ==null or expert.auditor == ''}">无</c:when>
                  <c:otherwise>${expert.auditor}</c:otherwise>
                </c:choose>
              </td>
            </tr>
          </c:forEach>
        </table>
        <div id="pagediv" align="right"></div>
      </div>
    </div>
  </body>

</html>