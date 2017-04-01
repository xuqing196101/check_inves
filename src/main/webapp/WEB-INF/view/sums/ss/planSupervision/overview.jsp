<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <%@ include file="/WEB-INF/view/common/webupload.jsp"%>

    <script type="text/javascript">
      $(function() {
        $("#onmouse").addClass("btmfixs");
      });
      function viewDemand() {
        layer.open({
          type: 1, //page层
          area: ['1300px', '300px'],
          title: '请上传更改附件',
          shade: 0.01, //遮罩透明度
          moveType: 1, //拖拽风格，0是默认，1是传统拖动
          shift: 1, //0-6的动画形式，-1不开启
          shadeClose: true,
          content: $("#file")
        });
      }

      function viewUpload(id) {
        var projectId = "${project.id}";
        var a = "2";
        openViewDIv(projectId, id, a, null, null);
      }

      function audit(id, type) {
        layer.open({
          type: 2, //page层
          area: ['800px', '500px'],
          title: '查看审核意见',
          shade: 0.01, //遮罩透明度
          moveType: 1, //拖拽风格，0是默认，1是传统拖动
          shift: 1, //0-6的动画形式，-1不开启
          shadeClose: true,
          content: '${pageContext.request.contextPath}/Auditbidding/viewAudit.html?projectId=' + id + '&type=' + type
        });
      }

      /** 文件发售 **/
      function sell(id, type) {
        layer.open({
          type: 2, //page层
          area: ['1000px', '500px'],
          title: '查看文件发售',
          shade: 0.01, //遮罩透明度
          moveType: 1, //拖拽风格，0是默认，1是传统拖动
          shift: 1, //0-6的动画形式，-1不开启
          shadeClose: true,
          content: '${pageContext.request.contextPath}/planSupervision/viewSell.html?packageId=' + id + '&type=' + type,
        });
      }

      /** 开标 **/
      function bid(id) {
        layer.open({
          type: 2, //page层
          area: ['1000px', '500px'],
          title: '查看开标',
          shade: 0.01, //遮罩透明度
          moveType: 1, //拖拽风格，0是默认，1是传统拖动
          shift: 1, //0-6的动画形式，-1不开启
          shadeClose: true,
          content: '${pageContext.request.contextPath}/planSupervision/bidAnnouncement.html?packageId=' + id,
        });
      }

      function openPrint(projectId, packageId) {
        window.open("${pageContext.request.contextPath}/packageExpert/openPrint.html?packageId=" + packageId + "&projectId=" + projectId, "打印检查汇总表");
      }

      function openPrints(projectId, packageId) {
        window.open("${pageContext.request.contextPath}/packageExpert/expertConsult.html?packageId=" + packageId + "&projectId=" + projectId + "&flag=1", "评审汇总表");
      }

      function report(id) {
        window.open("${pageContext.request.contextPath}/planSupervision/report.html?packageId=" + id, "专家评审报告");
      }
      
      function info(id) {
        layer.open({
          type: 2, //page层
          area: ['1000px', '500px'],
          title: '查看质检',
          shade: 0.01, //遮罩透明度
          moveType: 1, //拖拽风格，0是默认，1是传统拖动
          shift: 1, //0-6的动画形式，-1不开启
          shadeClose: true,
          content: '${pageContext.request.contextPath}/pqinfo/view.html?id=' + id,
        });
      }
      
      function viewAuditPerson(id,type) {
        layer.open({
          type: 2, //page层
          area: ['1000px', '500px'],
          title: '查看审核意见',
          shade: 0.01, //遮罩透明度
          moveType: 1, //拖拽风格，0是默认，1是传统拖动
          shift: 1, //0-6的动画形式，-1不开启
          shadeClose: true,
          content: '${pageContext.request.contextPath}/planSupervision/viewAuditPerson.html?id=' + id + '&type=' + type,
        });
      }
      
      function graded(id,packageId) {
        layer.open({
          type: 2, //page层
          area: ['1000px', '500px'],
          title: '查看供应商评分排序',
          shade: 0.01, //遮罩透明度
          moveType: 1, //拖拽风格，0是默认，1是传统拖动
          shift: 1, //0-6的动画形式，-1不开启
          shadeClose: true,
          content: '${pageContext.request.contextPath}/planSupervision/graded.html?id=' + id + '&packageId=' + packageId,
        });
      }

      function OpenFile(fileId) {
        setTimeout(open_file(fileId), 5000);
      }

      function open_file(fileId) {
        var obj = document.getElementById("TANGER_OCX");
        obj.Menubar = true;
        obj.Caption = "( 双击可放大 ! )";
        if(fileId != '0') {
          obj.BeginOpenFromURL("${pageContext.request.contextPath}/open_bidding/loadFile.html?fileId=" + fileId, true, false, 'word.document'); // 异步加载, 服务器文件路径
        } else {
          var filePath = "${filePath}";
          if(filePath != null && filePath != undefined && filePath != "") {
            obj.BeginOpenFromURL("${pageContext.request.contextPath}/open_bidding/downloadFile.html?filePath=" + filePath, true, false, 'word.document'); // 异步加载, 服务器文件路径
          }
        }
      }
      
      function bigImg(x){
	      $(x).removeClass("btmfixs");
	      $(x).addClass("btmfix");
	    }
	   
	    function normalImg(x){
	      $(x).removeClass("btmfix");
	      $(x).addClass("btmfixs");
	    }
    </script>

    <!-- 打开文档后调用  -->
    <script type="text/javascript" for="TANGER_OCX" event="OnDocumentOpened(a,b)">
      //声明控件
      var obj = document.getElementById("TANGER_OCX");
      // 转换日期格式  如果是CST 日期  转换 GMT 日期
      function getTaskTime(strDate) {
        if(null == strDate || "" == strDate) {
          return "";
        }
        if(strDate.indexOf("GMT") > 0) {
          return new Date(strDate).Format("yyyy年MMdd日hh时mm分");
        }
        var dateStr = strDate.trim().split(" ");
        var strGMT = dateStr[0] + " " + dateStr[1] + " " + dateStr[2] + " " + dateStr[5] + " " + dateStr[3] + " GMT+0800";
        var date = new Date(Date.parse(strGMT));
        var y = date.getFullYear();
        var m = date.getMonth() + 1;
        m = m < 10 ? ('0' + m) : m;
        var d = date.getDate();
        d = d < 10 ? ('0' + d) : d;
        var h = date.getHours();
        var minute = date.getMinutes();
        minute = minute < 10 ? ('0' + minute) : minute;
        var second = date.getSeconds();
        second = second < 10 ? ('0' + second) : second;
        return y + "年" + m + "月" + d + "日" + h + "时" + minute + "分";
      };
      //通用方法 判断是否存在 存在则行
      function replaceContent(begin, end, date) {
        if(obj.ActiveDocument.Bookmarks.Exists(begin) && obj.ActiveDocument.Bookmarks.Exists(end)) {
          obj.ActiveDocument.Range(ActiveDocument.Bookmarks(begin).Range.End, ActiveDocument.Bookmarks(end).Range.End).Select();
          obj.ActiveDocument.Application.Selection.Editors.Add(-1); //增加可编辑区域
          obj.ActiveDocument.Application.Selection.TypeText(date);
          obj.ActiveDocument.Bookmarks.Add(end);
        }
      }

      function loadWord(begin, end, url) {
        obj.ActiveDocument.Range(ActiveDocument.Bookmarks(begin).Range.End, ActiveDocument.Bookmarks(end).Range.Start).Select();
        obj.ActiveDocument.Application.Selection.Editors.Add(-1); //增加可编辑区域
        obj.AddTemplateFromURL(url, false, true);

      }
      /**
       * ntko 控件加载玩之后调用
       * **/
      $(function() {
        // 组合word文档
        var marks = obj.ActiveDocument.Bookmarks; //获取所有的书签
        var filePath = "${filePath}";
        if(filePath != null && filePath != "") {
          var pathArray = filePath.split(",");
          if(pathArray.length > 1) {
            //项目名称
            replaceContent("SYS_1", "SYS_1_1", "${project.name}");
            //项目编号
            replaceContent("SYS_2", "SYS_2_2", "${project.projectNumber}");
            //招标人
            replaceContent("SYS_3", "SYS_3_1", "${project.sectorOfDemand}");
            //项目名称
            replaceContent("SYS_20171200", "SYS_20171201", "${project.name}");
            //项目编号
            replaceContent("SYS_20171202", "SYS_20171203", "${project.projectNumber}");
            //投标截止时间
            replaceContent("SYS_20171204", "SYS_20171205", "${project.deadline}");
            // 投标地点
            replaceContent("SYS_20171206", "SYS_20171207", "${project.bidAddress}");
            // 开标时间
            replaceContent("SYS_20171208", "SYS_20171209", "${project.bidDate}");
            //开标地点
            replaceContent("SYS_20171210", "SYS_20171211", "${project.bidAddress}");
            //招标人
            replaceContent("SYS_20171212", "SYS_20171213", "${project.sectorOfDemand}");
            //招标人
            replaceContent("SYS_20171214", "SYS_20171215", "${project.sectorOfDemand}");
            //招标人
            replaceContent("SYS_20171216", "SYS_20171217", "${project.sectorOfDemand}");

            //定位定义标签位置
            loadWord("DW_TWO_TWO", "DW_TWO_THREE", "${pageContext.request.contextPath}/open_bidding/downloadFile.html?filePath=" + pathArray[1]);
            loadWord("DW_THREE_2", "DW_THREE_3", "${pageContext.request.contextPath}/open_bidding/downloadFile.html?filePath=" + pathArray[0]);
            obj.ActiveDocument.DeleteAllEditableRanges(-1); //取消编辑
          }
        }
        for(var i = 1; i <= marks.Count; i++) {
          // 判读 标签 可编辑
          if(marks(i).Name.indexOf("EDITOR") == 0) {
            obj.ActiveDocument.Bookmarks(marks(i).Name).Range.Select(); //选取书签区域保护
            obj.ActiveDocument.Application.Selection.Editors.Add(-1); //增加可编辑区域
            //添加 内容标识显示
            obj.ActiveDocument.ActiveWindow.View.ShadeEditableRanges = true;
            obj.ActiveDocument.ActiveWindow.View.ShowBookmarks = true;
          }
        }
        if(obj.ActiveDocument.ProtectionType == -1) {
          obj.ActiveDocument.Protect(3); //实现文档保护
        }
        obj.ActiveDocument.Bookmarks("OLE_LINK_TOP").Select();
      });
    </script>

    <body>
      <!--面包屑导航开始-->
      <div class="margin-top-10 breadcrumbs ">
        <div class="container">
          <ul class="breadcrumb margin-left-0">
            <li>
              <a href="javascript:void(0)">首页</a>
            </li>
            <li>
              <a href="javascript:void(0)">业务监管系统</a>
            </li>
            <li>
              <a href="javascript:void(0)">采购业务监督</a>
            </li>
            <li class="active">
              <a href="javascript:void(0)">采购计划监督</a>
            </li>
          </ul>
          <div class="clear"></div>
        </div>
      </div>

      <div class="container container_box">
        <div>
          <h2 class="count_flow"><i>1</i>项目基本信息</h2>
          <ul class="ul_list">
            <table class="table table-bordered mt10">
              <tbody>
                <tr>
                  <td width="10%" class="info">项目名称：</td>
                  <td width="25%">${project.name}</td>
                  <td width="10%" class="info">项目编号：</td>
                  <td width="25%">${project.projectNumber}</td>
                </tr>
                <tr>
                  <td width="10%" class="info">计划名称：</td>
                  <td width="25%">${collectPlan.fileName}</td>
                  <td width="10%" class="info">计划编号：</td>
                  <td width="25%">${collectPlan.planNo}</td>
                </tr>
                <tr>
                  <td width="10%" class="info">需求部门：</td>
                  <td width="25%">${detail.department}</td>
                  <td width="10%" class="info">采购管理部门：</td>
                  <td width="25%">${collectPlan.purchaseId}</td>
                </tr>
                <tr>
                  <td width="10%" class="info">项目状态：</td>
                  <td width="25%">${project.status}</td>
                  <td width="10%" class="info">创建人：</td>
                  <td width="25%">${project.appointMan}</td>
                </tr>
                <tr>
                  <td width="10%" class="info">创建日期：</td>
                  <td width="25%">
                    <fmt:formatDate value='${project.createAt}' pattern='yyyy年MM月dd日  HH:mm:ss' />
                  </td>
                  <td width="10%" class="info"></td>
                  <td width="25%"></td>
                </tr>
              </tbody>
            </table>
          </ul>
        </div>
        <div class="padding-top-10 clear">
          <h2 class="count_flow"><i>2</i>流程进度</h2>
          <div class="container">
            <div class="col-md-12 col-xs-12 col-sm-12 flow_more">
              <div class="flow_tips col-md-2 col-sm-2 col-xs-12 pre_btn">
                <div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
                  <a href="#tab-1">
                    <p class="tip_main">采购需求编报</p>
                    <p class="tip_time">
                      <fmt:formatDate value='${purchaseRequired.createdAt}' pattern='yyyy-MM-dd' />
                    </p>
                  </a>
                </div>
                <div class="tip_line col-md-5 col-sm-3"></div>
                <div class="tip_down col-xs-offset-6"></div>
              </div>

              <div class="flow_tips col-md-2 col-sm-2 col-xs-12 pre_btn last_small">
                <div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
                  <a href="#tab-2">
                    <p class="tip_main">采购需求受理</p>
                    <p class="tip_time">
                      <fmt:formatDate value='${auditPerson.createDate}' pattern='yyyy-MM-dd' />
                    </p>
                  </a>
                </div>
                <div class="tip_line col-md-5 col-sm-3"></div>
                <div class="tip_down col-xs-offset-6"></div>
              </div>

              <c:if test="${advancedProject != null}">
                <div class="flow_tips col-md-2 col-sm-2 col-xs-12 pre_btn small_r">
                  <div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
                    <a href="#tab-3">
                      <p class="tip_main">预研任务下达</p>
                      <p class="tip_time">
                        <fmt:formatDate value='${task.giveTime}' pattern='yyyy-MM-dd' />
                      </p>
                    </a>
                  </div>
                  <div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
                  <div class="tip_down col-xs-offset-6"></div>
                </div>
              </c:if>

              <div class="flow_tips col-md-2 col-sm-2 col-xs-12 pre_btn small_r">
                <div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
                  <a href="#tab-4">
                    <p class="tip_main">采购计划审核</p>
                    <c:if test="${listAuditPerson != null}">
                      <p class="tip_time">
                        <fmt:formatDate value='${obj.createDate}' pattern='yyyy-MM-dd' />
                      </p>
                    </c:if>
                    <c:if test="${listAuditPerson eq null}">
                      <p class="tip_time">计划未审核</p>
                    </c:if>
                  </a>
                </div>
                <div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
                <div class="tip_down col-xs-offset-6"></div>
              </div>

              <div class="flow_tips col-md-2 col-sm-2 col-xs-12 pre_btn  ">
                <div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
                  <a href="#tab-5">
                    <p class="tip_main">采购计划下达</p>
                    <p class="tip_time">
                      <fmt:formatDate value='${collectPlan.updatedAt}' pattern='yyyy-MM-dd' />
                    </p>
                  </a>
                </div>
                <div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
                <div class="tip_down col-xs-offset-6"></div>
              </div>

              <c:choose>
                <c:when test="${advancedProject != null}">
                  <div class="flow_tips col-md-2 col-sm-2 col-xs-12 current_btn round_tips round_r">
                </c:when>
                <c:otherwise>
                  <div class="flow_tips col-md-2 col-sm-2 col-xs-12 current_btn round_tips">
                </c:otherwise>
              </c:choose>
              <div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
                <a href="#tab-6">
                  <p class="tip_main">采购任务受领</p>
                  <p class="tip_time">
                    <fmt:formatDate value='${task.acceptTime}' pattern='yyyy年MM月dd日' />
                  </p>
                </a>
              </div>
              <div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
              <div class="tip_down col-xs-offset-6 col-sm-offset-1 col-md-offset-1  col-md-offset-0"></div>
              </div>

              <c:choose>
                <c:when test="${advancedProject != null}">
                  <div class="flow_tips col-md-2 col-sm-2 col-xs-12 last_r">
                    <div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
                      <a href="#tab-7">
                        <p class="tip_main">采购项目立项</p>
                        <p class="tip_time">2016-08-08</p>
                      </a>
                    </div>
                    <div class="tip_down col-xs-offset-6"></div>
                  </div>
                </c:when>
                <c:otherwise>
                  <div class="flow_tips col-md-2 col-sm-2 col-xs-12 round_tips round_l last_r">
                    <div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
                      <a href="#tab-7">
                        <p class="tip_main">采购项目立项</p>
                        <p class="tip_time">
                          <fmt:formatDate value='${project.createAt}' pattern='yyyy-MM-dd' />
                        </p>
                      </a>
                    </div>
                    <div class="tip_down col-xs-offset-6 col-sm-offset-1 col-md-offset-0"></div>
                  </div>
                </c:otherwise>
              </c:choose>

              <div class="flow_tips col-md-2 col-sm-2 col-xs-12 last_r">
                <div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
                  <a href="#tab-8">
                    <p class="tip_main">采购文件编报</p>
                    <p class="tip_time">
                      <fmt:formatDate value='${project.approvalTime}' pattern='yyyy-MM-dd' />
                    </p>
                  </a>
                </div>
                <c:if test="${advancedProject != null}">
                  <div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
                </c:if>
                <div class="tip_down col-xs-offset-6"></div>
              </div>

              <div class="flow_tips col-md-2 col-sm-2 col-xs-12 last_r">
                <div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
                  <a href="#tab-9">
                    <p class="tip_main">采购公告发布</p>
                    <p class="tip_time">
                      <fmt:formatDate value='${articles.createdAt}' pattern='yyyy-MM-dd' />
                    </p>
                  </a>
                </div>
                <div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
                <div class="tip_down col-xs-offset-6"></div>
              </div>

              <div class="flow_tips col-md-2 col-sm-2 col-xs-12 last_r">
                <div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
                  <a href="#tab-10">
                    <p class="tip_main">供应商抽取</p>
                    <p class="tip_time">2016-08-08</p>
                  </a>
                </div>
                <div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
                <div class="tip_down col-xs-offset-6"></div>
              </div>

              <div class="flow_tips col-md-2 col-sm-2 col-xs-12 last_r">
                <div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
                  <a href="#tab-11">
                    <p class="tip_main">采购文件发售</p>
                    <p class="tip_time">2016-08-08</p>
                  </a>
                </div>
                <div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
                <div class="tip_down col-xs-offset-6"></div>
              </div>
              <div class="flow_tips col-md-2 col-sm-2 col-xs-12 round_tips round_l last_r">
                <div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
                  <a href="#tab-12">
                    <p class="tip_main">评审专家抽取</p>
                    <p class="tip_time">2016-08-08</p>
                  </a>
                </div>
                <div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
                <c:if test="${advancedProject != null}">
                  <div class="tip_down col-xs-offset-6 col-sm-offset-1 col-md-offset-0"></div>
                </c:if>
              </div>

              <c:choose>
                <c:when test="${advancedProject != null}">
                  <div class="flow_tips col-md-2 col-sm-2 col-xs-12">
                    <div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
                      <a href="#tab-13">
                        <p class="tip_main">开标</p>
                        <p class="tip_time">
                          <fmt:formatDate value='${project.bidDate}' pattern='yyyy-MM-dd' />
                        </p>
                      </a>
                    </div>
                    <div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
                    <div class="tip_down col-xs-offset-6"></div>
                  </div>
                </c:when>
                <c:otherwise>
                  <div class="flow_tips col-md-2 col-sm-2 col-xs-12 round_tips round_l last_r">
                    <div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
                      <a href="#tab-13">
                        <p class="tip_main">开标</p>
                        <p class="tip_time">
                          <fmt:formatDate value='${project.bidDate}' pattern='yyyy-MM-dd' />
                        </p>
                      </a>
                    </div>
                    <div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
                    <div class="tip_down col-xs-offset-6 col-sm-offset-1 col-md-offset-0"></div>
                  </div>
                </c:otherwise>
              </c:choose>

              <div class="flow_tips col-md-2 col-sm-2 col-xs-12">
                <div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
                  <a href="#tab-14">
                    <p class="tip_main">采购项目评审</p>
                    <p class="tip_time">2016-08-08</p>
                  </a>
                </div>
                <div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
                <div class="tip_down col-xs-offset-6"></div>
              </div>

              <div class="flow_tips col-md-2 col-sm-2 col-xs-12">
                <div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
                  <a href="#tab-15">
                    <p class="tip_main">中标公示发布</p>
                    <p class="tip_time">
                      <fmt:formatDate value='${articleList.createdAt}' pattern='yyyy-MM-dd' />
                    </p>
                  </a>
                </div>
                <div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
                <div class="tip_down col-xs-offset-6"></div>
              </div>

              <div class="flow_tips col-md-2 col-sm-2 col-xs-12">
                <div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
                  <a href="#tab-16">
                    <p class="tip_main">中标供应商确定</p>
                    <p class="tip_time">
                      <fmt:formatDate value='${obj.confirmTime}' pattern='yyyy-MM-dd' />
                    </p>
                  </a>
                </div>
                <div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
                <div class="tip_down col-xs-offset-6"></div>
              </div>

              <div class="flow_tips col-md-2 col-sm-2 col-xs-12">
                <div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
                  <a href="#tab-17">
                    <p class="tip_main">采购合同签订</p>
                    <p class="tip_time">
                      <fmt:formatDate value='${purchaseContract.formalAt}' pattern='yyyy-MM-dd' />
                    </p>
                  </a>
                </div>
                <div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
                <div class="tip_down col-xs-offset-6"></div>
              </div>

              <!-- <div class="flow_tips col-md-2 col-sm-2 col-xs-12 current_btn round_tips round_r">
                <div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
                  <a href="#tab-18">
                    <p class="tip_main">采购合同履约</p>
                    <p class="tip_time">2016-08-08</p>
                  </a>
                </div>
                <div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
                <div class="tip_down col-xs-offset-6 col-sm-offset-1 col-md-offset-1  col-md-offset-0"></div>
              </div> -->

              <div class="flow_tips col-md-2 col-sm-2 col-xs-12">
                <div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
                  <a href="#tab-18">
                    <p class="tip_main">采购质检验收</p>
                    <p class="tip_time">2016-08-08</p>
                  </a>
                </div>
                <div class="tip_down col-xs-offset-6"></div>
              </div>

              </div>

            </div>
          </div>
          <c:set var="flag" value="1" />
          <div class="padding-top-10 clear">
            <h2 class="count_flow"><i>3</i>进度详情</h2>
            <ul class="ul_list">
              <h2 class="count_flow" id="tab-1"><i>${flag}</i>采购需求编报</h2>
              <c:set var="flag" value="${flag+1}" />
              <table class="table table-bordered mt10">
                <tbody>
                  <tr>
                    <th class="info">采购需求名称</th>
                    <th class="info">需求部门</th>
                    <th class="info">编报人</th>
                    <th class="info">提报时间</th>
                  </tr>
                  <tr>
                    <td>${purchaseRequired.planName}</td>
                    <td>${purchaseRequired.department}</td>
                    <td>${purchaseRequired.userId}</td>
                    <td>
                      <fmt:formatDate value='${purchaseRequired.createdAt}' pattern='yyyy年MM月dd日  HH:mm:ss' />
                    </td>
                  </tr>
                </tbody>
              </table>

              <h2 class="count_flow" id="tab-2"><i>${flag}</i>采购需求受理</h2>
              <c:set var="flag" value="${flag+1}" />
              <table class="table table-bordered mt10">
                <tbody>
                  <tr>
                    <th class="info">受理结果</th>
                    <th class="info">采购管理部门</th>
                    <th class="info">受理人</th>
                    <th class="info">受理时间</th>
                  </tr>
                  <tr>
                    <td class="tc"><button class="btn" onclick="viewDemand();" type="button">查看</button></td>
                    <td>${management}</td>
                    <td>${auditPerson.userId}</td>
                    <td>
                      <fmt:formatDate value='${auditPerson.createDate}' pattern='yyyy年MM月dd日  HH:mm:ss' />
                    </td>
                  </tr>
                </tbody>
              </table>

              <c:if test="${advancedProject != null}">
                <h2 class="count_flow" id="tab-3"><i>${flag}</i>预研任务下达</h2>
                <c:set var="flag" value="${flag+1}" />
                <table class="table table-bordered mt10">
                  <tbody>
                    <tr>
                      <th class="info">预研通知书名称</th>
                      <th class="info">采购管理部门</th>
                      <th class="info">下达人</th>
                      <th class="info">下达时间</th>
                    </tr>
                    <tr>
                      <td class="tc">
                        <u:show showId="upload_id" businessId="${advancedProjectId}" sysKey="2" delete="false" typeId="${adviceId}" />
                      </td>
                      <td>${task.orgId}</td>
                      <td>${task.createrId}</td>
                      <td>
                        <fmt:formatDate value='${task.giveTime}' pattern='yyyy年MM月dd日  HH:mm:ss' />
                      </td>
                    </tr>
                  </tbody>
                </table>
              </c:if>

              <h2 class="count_flow" id="tab-4"><i>${flag}</i>采购计划审核</h2>
              <c:set var="flag" value="${flag+1}" />
              <c:choose>
                <c:when test="${listAuditPerson != null}">
                  <table class="table table-bordered mt10">
                    <thead>
                      <tr>
                        <th class="info">审核轮次</th>
                        <th class="info">审核人员</th>
                        <th class="info">审核意见</th>
                        <th class="info">审核时间</th>
                      </tr>
                    </thead>
                    <tbody>
                      <c:forEach items="${listAuditPerson}" var="obj" varStatus="vs">
                        <tr>
                          <td class="tc">第${(vs.index+1)}轮</td>
                          <td>${obj.name}</td>
                          <td class="tc"><button class="btn" onclick="viewAuditPerson('${detailId}','${(vs.index+1)}');" type="button">查看</button> </td>
                          <td>
                            <fmt:formatDate value='${obj.createDate}' pattern='yyyy年MM月dd日  HH:mm:ss' />
                          </td>
                        </tr>
                      </c:forEach>
                    </tbody>
                  </table>
                </c:when>
                <c:otherwise>
                  <p>本计划未审核，直接下达</p>
                </c:otherwise>
              </c:choose>

              <c:if test="${collectPlan.fileName != null}">
                <h2 class="count_flow" id="tab-5"><i>${flag}</i>采购计划下达</h2>
                <c:set var="flag" value="${flag+1}" />
                <table class="table table-bordered mt10">
                  <tbody>
                    <tr>
                      <th class="info">采购计划名称</th>
                      <th class="info">计划文号</th>
                      <th class="info">采购管理部门</th>
                      <th class="info">下达人</th>
                      <th class="info">下达时间</th>
                    </tr>
                    <tr>
                      <td>${collectPlan.fileName}</td>
                      <td>${collectPlan.planNo}</td>
                      <td>${collectPlan.purchaseId}</td>
                      <td>${collectPlan.userId}</td>
                      <td>
                        <fmt:formatDate value='${collectPlan.updatedAt}' pattern='yyyy年MM月dd日  HH:mm:ss' />
                      </td>
                    </tr>
                  </tbody>
                </table>
              </c:if>

              <h2 class="count_flow" id="tab-6"><i>${flag}</i>采购任务受领</h2>
              <c:set var="flag" value="${flag+1}" />
              <table class="table table-bordered mt10">
                <tbody>
                  <tr>
                    <th class="info">采购任务名称</th>
                    <th class="info">采购机构</th>
                    <th class="info">任务性质</th>
                    <th class="info">受领人</th>
                    <th class="info">受领时间</th>
                  </tr>
                  <tr>
                    <td>${task.name}</td>
                    <td>${task.purchaseId}</td>
                    <td>
                      <c:if test="${task.taskNature eq '0'}">正常</c:if>
                      <c:if test="${task.taskNature eq '1'}">预研</c:if>
                    </td>
                    <td>${task.userId}</td>
                    <td>
                      <fmt:formatDate value='${task.acceptTime}' pattern='yyyy年MM月dd日  HH:mm:ss' />
                    </td>
                  </tr>
                </tbody>
              </table>

              <h2 class="count_flow" id="tab-7"><i>${flag}</i>采购项目立项</h2>
              <c:set var="flag" value="${flag+1}" />
              <table class="table table-bordered mt10">
                <tbody>
                  <tr>
                    <th class="info">采购项目名称</th>
                    <th class="info">立项审批文件</th>
                    <th class="info">立项部门</th>
                    <th class="info">项目性质</th>
                    <th class="info">立项人</th>
                    <th class="info">立项时间</th>
                  </tr>
                  <tr>
                    <td>${project.name}</td>
                    <td class="tc"><button class="btn" onclick="viewUpload('${uploadId}');" type="button">查看</button></td>
                    <td>${project.purchaseDepName}</td>
                    <td>
                      <c:if test="${status eq '0'}">正常</c:if>
                      <c:if test="${status eq '1'}">预研</c:if>
                    </td>
                    <td>${project.appointMan}</td>
                    <td>
                      <fmt:formatDate value='${project.createAt}' pattern='yyyy年MM月dd日  HH:mm:ss' />
                    </td>
                  </tr>
                </tbody>
              </table>

              <h2 class="count_flow" id="tab-8"><i>${flag}</i>采购文件编报</h2>
              <c:set var="flag" value="${flag+1}" />
              <table class="table table-bordered mt10">
                <tbody>
                  <tr>
                    <th class="info">采购文件名称</th>
                    <th class="info">编制人</th>
                    <th class="info">提报时间</th>
                    <th class="info">审核意见</th>
                    <th class="info">意见批复时间</th>
                  </tr>
                  <tr>
                    <td>
                      <a href="javascript:void(0)" onclick="OpenFile('${fileId}')">${fileName}</a>
                    </td>
                    <td class="tc">${operatorName}</td>
                    <td>
                      <fmt:formatDate value='${project.approvalTime}' pattern='yyyy年MM月dd日  HH:mm:ss' />
                    </td>
                    <td>
                      <%-- <a href="javascript:void(0)" onclick="audit('${project.id}','1')"></a>
                    <a href="javascript:void(0)" onclick="audit('${project.id}','2')"></a> --%>
                      <a href="javascript:void(0)" onclick="audit('${project.id}','1')">采购管理部门审核意见、事业部门审核意见、财务部门审核意见</a>
                    </td>
                    <td>
                      <fmt:formatDate value='${project.replyTime}' pattern='yyyy年MM月dd日  HH:mm:ss' />
                    </td>
                  </tr>
                </tbody>
              </table>

              <h2 class="count_flow" id="tab-9"><i>${flag}</i>采购公告发布</h2>
              <c:set var="flag" value="${flag+1}" />
              <table class="table table-bordered mt10">
                <tbody>
                  <tr>
                    <th class="info">公告名称</th>
                    <th class="info">编制人</th>
                    <th class="info">编制时间</th>
                  </tr>
                  <tr>
                    <td>${articles.name}</td>
                    <td>${articles.userId}</td>
                    <td>
                      <fmt:formatDate value='${articles.createdAt}' pattern='yyyy年MM月dd日  HH:mm:ss' />
                    </td>
                  </tr>
                </tbody>
              </table>

              <h2 class="count_flow" id="tab-10"><i>${flag}</i>供应商抽取</h2>
              <c:set var="flag" value="${flag+1}" />
              <table class="table table-bordered mt10">
                <tbody>
                  <tr>
                    <th class="info">抽取记录</th>
                    <th class="info">抽取人</th>
                    <th class="info">监督人</th>
                    <th class="info">编制时间</th>
                  </tr>
                  <tr>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td>
                    </td>
                  </tr>
                </tbody>
              </table>

              <h2 class="count_flow" id="tab-11"><i>${flag}</i>采购文件发售</h2>
              <c:set var="flag" value="${flag+1}" />
              <table class="table table-bordered mt10">
                <tbody>
                  <tr>
                    <th class="info">文件发售记录</th>
                    <th class="info">操作人</th>
                    <th class="info">发售时间</th>
                  </tr>
                  <tr>
                    <td class="tc"><button class="btn" onclick="sell('${packageId}','1')" type="button">查看</button></td>
                    <td></td>
                    <td>
	                    ${begin}
	                     <c:if test="${end!=null}">
	                        -${end}
	                     </c:if>
                    </td>
                  </tr>
                </tbody>
              </table>

              <h2 class="count_flow" id="tab-12"><i>${flag}</i>评审专家抽取</h2>
              <c:set var="flag" value="${flag+1}" />
              <table class="table table-bordered mt10">
                <tbody>
                  <tr>
                    <th class="info">抽取记录</th>
                    <th class="info">抽取人</th>
                    <th class="info">监督人</th>
                    <th class="info">抽取时间</th>
                  </tr>
                  <tr>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td>
                    </td>
                  </tr>
                </tbody>
              </table>

              <h2 class="count_flow" id="tab-13"><i>${flag}</i>开标</h2>
              <c:set var="flag" value="${flag+1}" />
              <table class="table table-bordered mt10">
                <tbody>
                  <tr>
                    <th class="info">投标记录</th>
                    <th class="info">开标一览表</th>
                    <th class="info">开标人</th>
                    <th class="info">开标时间</th>
                  </tr>
                  <tr>
                    <td class="tc"><button class="btn" onclick="sell('${packageId}','2')" type="button">查看</button></td>
                    <td class="tc"><button class="btn" onclick="bid('${packageId}')" type="button">查看</button></td>
                    <td></td>
                    <td>
                      <fmt:formatDate value='${project.bidDate}' pattern='yyyy年MM月dd日  HH:mm:ss' />
                    </td>
                  </tr>
                </tbody>
              </table>

              <h2 class="count_flow" id="tab-14"><i>${flag}</i>采购项目评审</h2>
              <c:set var="flag" value="${flag+1}" />
              <table class="table table-bordered mt10">
                <tbody>
                  <tr>
                    <th class="info">文件名称</th>
                    <th class="info">查看评审专家打分表</th>
                    <th class="info">查看汇总表</th>
                    <th class="info">评审时间</th>
                  </tr>
                  <tr>
                    <td>资格性符合性检查</td>
                    <td>
                      <c:forEach items="${experts}" var="obj" varStatus="vs">
                        <c:set value="${vs.index}" var="index"></c:set>
                        <a href="${pageContext.request.contextPath}/packageExpert/printView.html?projectId=${project.id}&packageId=${packageId}&expertId=${experts[index].id}" target="view_window">${experts[index].relName}</a>
                      </c:forEach>
                    </td>
                    <td class="tc"><button class="btn" onclick="openPrint('${project.id}','${packageId}')" type="button">查看</button></td>
                    <td>
                    </td>
                  </tr>
                  <tr>
                    <td>技术商务评分（审查）</td>
                    <td>
                      <c:forEach items="${experts}" var="obj" varStatus="vs">
                        <c:set value="${vs.index}" var="index"></c:set>
                        <a href="${pageContext.request.contextPath}/packageExpert/printView.html?projectId=${project.id}&packageId=${packageId}&expertId=${experts[index].id}&auditType=1" target="view_window">${experts[index].relName}</a>
                      </c:forEach>
                    </td>
                    <td class="tc"><button class="btn" onclick="openPrints('${project.id}','${packageId}')" type="button">查看</button></td>
                    <td>
                    </td>
                  </tr>
                  <c:if test="${DYLY != null}">
                    <tr>
                      <td>专家评审报告</td>
                      <td>
                        <c:forEach items="${expertIdList}" var="obj" varStatus="vs">
                          <c:if test="${obj.isGroupLeader == 1}">组长:${obj.expertId}</c:if>
                        </c:forEach>
                      </td>
                      <td class="tc"><button class="btn" onclick="report('${packageId}')" type="button">查看</button></td>
                      <td>
                      </td>
                    </tr>
                  </c:if>
                </tbody>
              </table>

              <h2 class="count_flow" id="tab-15"><i>${flag}</i>中标公示发布</h2>
              <c:set var="flag" value="${flag+1}" />
              <table class="table table-bordered mt10">
                <tbody>
                  <tr>
                    <th class="info">中标公示名称</th>
                    <th class="info">编制人</th>
                    <th class="info">编制时间</th>
                  </tr>
                  <tr>
                    <td>${articleList.name}</td>
                    <td>${articleList.userId}</td>
                    <td>
                      <fmt:formatDate value='${articleList.createdAt}' pattern='yyyy年MM月dd日  HH:mm:ss' />
                    </td>
                  </tr>
                </tbody>
              </table>

              <h2 class="count_flow" id="tab-16"><i>${flag}</i>中标供应商确定</h2>
              <c:set var="flag" value="${flag+1}" />
              <table class="table table-bordered mt10">
                <tbody>
                  <tr>
                    <th class="info">中标供应商名称</th>
                    <th class="info">评分排序</th>
                    <th class="info">操作人</th>
                    <th class="info">确定时间</th>
                  </tr>
                  <c:forEach items="${listCheckPass}" var="obj" varStatus="vs">
                    <tr>
                      <td>${obj.supplierId}</td>
                      <td class="tc"><button class="btn" onclick="graded('${obj.supplier.id}','${packageId}')" type="button">查看</button></td>
                      <td></td>
                      <td>
                        <fmt:formatDate value='${obj.confirmTime}' pattern='yyyy年MM月dd日  HH:mm:ss' />
                      </td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>

              <h2 class="count_flow" id="tab-17"><i>${flag}</i>采购合同签订</h2>
              <c:set var="flag" value="${flag+1}" />
              <table class="table table-bordered mt10">
                <tbody>
                  <tr>
                    <th class="info">合同名称</th>
                    <th class="info">甲方</th>
                    <th class="info">乙方</th>
                    <th class="info">签订时间</th>
                  </tr>
                  <tr>
                    <td>${purchaseContract.name}</td>
                    <td>${purchaseContract.purchaseDepName}</td>
                    <td>${purchaseContract.supplierDepName}</td>
                    <td>
                      <fmt:formatDate value='${purchaseContract.formalAt}' pattern='yyyy年MM月dd日  HH:mm:ss' />
                    </td>
                  </tr>
                </tbody>
              </table>
              
              <h2 class="count_flow" id="tab-18"><i>${flag}</i>采购质检验收</h2>
              <c:set var="flag" value="${flag+1}" />
              <table class="table table-bordered mt10">
                <tbody>
                  <tr>
                    <th class="info">验收记录</th>
                    <th class="info">质检专家</th>
                    <th class="info">质检单位</th>
                    <th class="info">验收时间</th>
                  </tr>
                  <tr>
                    <td class="tc"><button class="btn" onclick="info('${PqInfo.id}'}" type="button">查看</button></td>
                    <td>${PqInfo.inspectors}</td>
                    <td>${PqInfo.unit}</td>
                    <td>
                      <fmt:formatDate value='${PqInfo.date}' pattern='yyyy年MM月dd日  HH:mm:ss' />
                    </td>
                  </tr>
                </tbody>
              </table>
            </ul>
          </div>
        </div>

        <div id="file" class="dnone">
          <div class="col-md-12 col-sm-12 col-xs-12 p0" id="content">
            <table id="table" class="table table-bordered table-condensed table-hover">
              <thead>
                <tr class="space_nowrap">
                  <th class="info w50">序号</th>
                  <th class="info w80">需求部门</th>
                  <th class="info w80">物资类别<br>及名称</th>
                  <th class="info w80">规格型号</th>
                  <th class="info w80">质量技术标准</br>（技术参数）</th>
                  <th class="info w80">计量<br>单位</th>
                  <th class="info w80">采购<br>数量</th>
                  <th class="info w80">单价<br>（元）</th>
                  <th class="info w80">预算金额</br>（万元）</th>
                  <th class="info w80">交货期限</th>
                  <th class="info w100">采购方式</br>建议</th>
                  <th class="info w80">采购机构</th>
                  <th class="info w100">供应商名称</th>
                  <th class="info w80">是否申请</br>办理免税</th>
                  <th class="info w160">备注</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td class="tc w50">
                    <div class="w50">${required.seq }</div>
                  </td>
                  <td class="tl">
                    <div class="w80">${required.department }</div>
                  </td>
                  <td class="tl">
                    <div class="w80">${required.goodsName }</div>
                  </td>
                  <td class="tl">
                    <div class="w80">${required.stand }</div>
                  </td>
                  <td class="tl">
                    <div class="w80"> ${required.qualitStand }</div>
                  </td>
                  <td class="tc">
                    <div class="w80">${required.item }</div>
                  </td>
                  <td class="tc">
                    <div class="w80">${required.purchaseCount }</div>
                  </td>
                  <td class="tr">
                    <div class="w80">${required.price }</div>
                  </td>
                  <td class="tr">
                    <div class="w80">${required.budget }</div>
                  </td>
                  <td>
                    <div class="w80">${required.deliverDate }</div>
                  </td>
                  <td class="p0">${required.purchaseType }</td>
                  <td class="tc p0">${required.organization }</td>
                  <td class="tl">
                    <div class="w80">${required.supplier }</div>
                  </td>
                  <td class="tc">
                    <div class="w80">${required.isFreeTax }</div>
                  </td>
                  <td class="tl">
                    <div class="w160">${required.memo }</div>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
        <div id="onmouse" onmouseover="bigImg(this)" onmouseout="normalImg(this)">
          <div class="mt5 mb5 tc">
            <button class="btn btn-windows back" onclick="window.history.go(-1)" type="button">返回</button>
          </div>
        </div>
    </body>

</html>