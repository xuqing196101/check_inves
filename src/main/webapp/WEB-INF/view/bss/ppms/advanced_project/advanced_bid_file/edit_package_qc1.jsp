<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:include page="/WEB-INF/view/common.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

<head>
  <title>My JSP 'view.jsp' starting page</title>

  <meta http-equiv="pragma" content="no-cache">
  <meta http-equiv="cache-control" content="no-cache">
  <meta http-equiv="expires" content="0">
  <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
  <meta http-equiv="description" content="This is my page">
  <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
</head>
<script type="text/javascript">
  //新增一个评审项
  function addItem(obj, kindId) {
    $("#remainScore").val('');
    $("#remark").val('');
    $("#name").val('');
    $("#id").val('');
    //得到点击坐标。
    var y;
    oRect = obj.getBoundingClientRect();
    y = oRect.top;
    $("#typeName").val(kindId);
    layer.open({
      type: 1,
      title: '添加评审项信息',
      area: ['70%', '300px'],
      closeBtn: 1,
      shade: 0.01, //遮罩透明度
      moveType: 1, //拖拽风格，0是默认，1是传统拖动
      shift: 1, //0-6的动画形式，-1不开启
      offset: y,
      shadeClose: false,
      content: $("#openDiv"),
    });
  }

  function editItem(id) {
    $.ajax({
      type: "get",
      url: "${pageContext.request.contextPath}/adIntelligentScore/editScore.do?id=" + id,
      dataType: 'json',
      success: function(result) {
        $("#openDiv").removeClass("dnone");
        $("#remainScore").val(result.remainScore);
        $("#remark").val(result.remark);
        $("#name").val(result.name);
        $("#id").val(result.id);
        $("#typeName").val(result.type);
        layer.open({
          type: 1,
          title: '添加评审项信息',
          area: ['70%', '300px'],
          closeBtn: 1,
          shade: 0.01, //遮罩透明度
          moveType: 1, //拖拽风格，0是默认，1是传统拖动
          shift: 1, //0-6的动画形式，-1不开启
          offset: 100,
          shadeClose: false,
          content: $("#openDiv"),
        });
      },
      error: function(result) {
        layer.msg("添加失败", {
          offset: ['150px']
        });
      }
    });
  }

  function addModel(obj, kindId, status) {
    var projectId = $("#projectId").val();
    var packageId = $("#packageId").val();
    var flowDefineId = "${flowDefineId}";
    var name = encodeURI(obj);
    name = encodeURI(name);
    window.location.href = "${pageContext.request.contextPath}/adIntelligentScore/gettreebody.html?projectId=" + projectId + "&packageId=" + packageId + "&id=" + kindId + "&name=" + name + "&addStatus=" + status + "&flowDefineId="+ flowDefineId;
  }

  //删除评审项 
  function delItem(id, status) {
    var projectId = $("#projectId").val();
    var packageId = $("#packageId").val();
    var flowDefineId = "${flowDefineId}";
    //为2 为顶级结点     1 为子节点
    window.location.href = "${pageContext.request.contextPath}/adIntelligentScore/deleteScoreModel.html?id=" + id + "&deleteStatus=" + status + "&projectId=" + projectId + "&packageId=" + packageId + "&flowDefineId="+ flowDefineId;
  }

  //关闭弹窗
  function cancel() {
    layer.closeAll();
  }

  //保存评审项
  function saveItem() {
  	var flowDefineId = "${flowDefineId}";
    $.ajax({
      type: "POST",
      url: "${pageContext.request.contextPath}/adIntelligentScore/saveScore.html",
      data: $('#form2').serializeArray(),
      dataType: 'json',
      success: function(result) {
        if(!result.success) {
          layer.msg(result.msg, {
            offset: ['150px']
          });
        } else {
          var packageId = $("#packageId").val();
          var projectId = $("#projectId").val();
          window.location.href = '${pageContext.request.contextPath}/adIntelligentScore/editPackageScore.html?packageId=' + packageId + '&projectId=' + projectId + '&flowDefineId=' + flowDefineId;
          layer.closeAll();
          layer.msg(result.msg, {
            offset: ['150px']
          });
        }
      },
      error: function(result) {
        layer.msg("添加失败", {
          offset: ['150px']
        });
      }
    });
  }

  //引入模板内容
  function loadTemplat(projectId, packageId) {
  	var flowDefineId = "${flowDefineId}";
    var index = layer.load(1, {
      shade: [0.2, '#BFBFBF'] //0.1透明度的白色背景
    });
    var fatId = $("#fatId").val();
    if(fatId != null && fatId != "") {
      $('#loadTemp').attr("disabled", true);
      $.ajax({
        type: "POST",
        url: "${pageContext.request.contextPath}/adIntelligentScore/loadTemplat.html",
        data: {
          "id": fatId,
          "projectId": projectId,
          "packageId": packageId
        },
        dataType: 'json',
        success: function(result) {
          if(!result.success) {
            layer.msg(result.msg, {
              offset: ['150px']
            });
          } else {
            $('#loadTemp').removeAttr("disabled");
            var packageId = $("#packageId").val();
            var projectId = $("#projectId").val();
            window.location.href = '${pageContext.request.contextPath}/adIntelligentScore/editPackageScore.html?packageId=' + packageId + '&projectId=' + projectId + '&flowDefineId=' + flowDefineId;
            layer.closeAll();
            layer.msg(result.msg, {
              offset: ['150px']
            });
          }
        },
        error: function(result) {
          layer.msg("添加失败", {
            offset: ['150px']
          });
        }
      });
    } else {
      layer.msg("请选择模板", {
        offset: ['150px']
      });
    }
  }

  //引入其他项目包的评审项
  function loadOtherPackage(packageId, projectId) {
  	var flowDefineId = "${flowDefineId}";
    layer.open({
      type: 2,
      title: '引入模板',
      area: ['800px', '360px'],
      closeBtn: 1,
      shade: 0.01, //遮罩透明度
      moveType: 1, //拖拽风格，0是默认，1是传统拖动
      shift: 1, //0-6的动画形式，-1不开启
      offset: '20px',
      shadeClose: false,
      content: '${pageContext.request.contextPath}/adIntelligentScore/loadOtherPackage.html?oldPackageId=' + packageId + '&oldProjectId=' + projectId + '&flowDefineId=' + flowDefineId
    });

  }

  /*点击事件*/
  function zTreeOnClick(event, treeId, treeNode) {
    if(treeNode.isParent == true) {
      layer.msg("请选择末节点", {
        offset: ['150px']
      });
      return false;
    }
    if(!treeNode.isParent) {
      $("#cId").val(treeNode.id);
      $("#categorySel").val(treeNode.name);
      hideCategory();
      findTem();
    }
  }

  function showCategory(tempId) {
    var rootCode = null;
    var zTreeObj;
    var zNodes;
    var setting = {
      async: {
        autoParam: ["id"],
        enable: true,
        url: "${pageContext.request.contextPath}/auditTemplat/categoryTree.do",
        otherParam: {
          "tempId": tempId,
          "rootCode": rootCode,
        },
        dataFilter: ajaxDataFilter,
        dataType: "json",
        type: "post"
      },
      view: {
        dblClickExpand: false
      },
      data: {
        simpleData: {
          enable: true
        }
      },
      callback: {
        onClick: zTreeOnClick,
      }
    };
    zTreeObj = $.fn.zTree.init($("#treeCategory"), setting, zNodes);
    zTreeObj.expandAll(true); //全部展开
    var cityObj = $("#categorySel");
    var cityOffset = $("#categorySel").offset();
    $("#categoryContent").css({
      left: cityOffset.left + "px",
      top: cityOffset.top + cityObj.outerHeight() + "px"
    }).slideDown("fast");
    $("body").bind("mousedown", onBodyDownOrg);
  }

  function ajaxDataFilter(treeId, parentNode, childNodes) {
    // 判断是否为空
    if(childNodes) {
      // 判断如果父节点是第二级,则将查询出来的子节点全部改为isParent = false
      if(parentNode != null && parentNode != "undefined" && parentNode.level == 1) {
        for(var i = 0; i < childNodes.length; i++) {
          childNodes[i].isParent += false;
        }
      }
    }
    return childNodes;
  }

  function hideCategory() {
    $("#categoryContent").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDownOrg);
  }

  function onBodyDownOrg(event) {
    if(!(event.target.id == "menuBtn" || event.target.id == "categorySel" || event.target.id == "categoryContent" || $(event.target).parents("#categoryContent").length > 0)) {
      hideCategory();
    }
  }

  function searchs(tempId) {
    var name = $("#search").val();
    if(name != "") {
      var zNodes;
      var zTreeObj;
      var setting = {
        async: {
          autoParam: ["id"],
          enable: true,
          url: "${pageContext.request.contextPath}/auditTemplat/categoryTree.do",
          otherParam: {
            "tempId": tempId,
          },
          dataFilter: ajaxDataFilter,
          dataType: "json",
          type: "post"
        },
        view: {
          dblClickExpand: false
        },
        data: {
          simpleData: {
            enable: true
          }
        },
        callback: {
          onClick: zTreeOnClick,
        }
      };
      // 加载中的菊花图标
      var loading = layer.load(1);

      $.ajax({
        url: "${pageContext.request.contextPath}/auditTemplat/searchCategory.do",
        data: {
          "name": encodeURI(name)
        },
        async: false,
        dataType: "json",
        success: function(data) {
          if(data.length == 3) {
            layer.msg("没有符合查询条件的产品类别信息！", {
              offset: ['150px']
            });
          } else {
            zNodes = data;
            zTreeObj = $.fn.zTree.init($("#treeCategory"), setting, zNodes);
            zTreeObj.expandAll(true); //全部展开
          }
          // 关闭加载中的菊花图标
          layer.close(loading);
        }
      });
    } else {
      showCategory();
    }
  }

  function getTotal() {
    var allTr = document.getElementsByTagName("tr");
    var totalScore = 0.0;
    for(var i = 1; i < allTr.length; i++) {
      var score = $(allTr[i]).find("td:last").text();
      var reg = /^\d+\.?\d*$/;
      var flag = false;
      if(!reg.exec(score)) {
        score = 0;
      }
      totalScore += parseFloat(score);
    };
    $("#totalScore").text(totalScore);
    var score = $("#totalScore").text();
    var projectId = '${projectId}';
    var packageId = '${packageId}';
    if(score == 100) {
      $.ajax({
        type: "get",
        url: "${pageContext.request.contextPath}/adIntelligentScore/checkIsCheck.do?projectId=" + projectId + "&packageId=" + packageId,
        dataType: 'json',
        success: function(result) {
          if(result == 1) {
            layer.msg("请选择评审计算价格得分的唯一标识,必须要有一个", {
              offset: ['150px']
            });
          }
        },
        error: function(result) {}
      });
    }
  }

  $(function() {
		initTem();
	});
	
	function initTem(){
		var html = "<option value=''>请选择</option>";
		$.ajax({
				url: "${pageContext.request.contextPath}/adFirstAudit/find.do",
				data: {"type" : "REVIEW_ET"},
				dataType: 'json',
				success: function(result){
					$("#fatId").empty();	
					if (result.success == false && typeof(result.success) != "undefined") {
						//layer.msg(result.msg,{offset: ['150px']});
					} else {
						if (result.length > 0) {
							for (var i = 0; i < result.length; i++) {
								html += "<option value='"+result[i].id+"'>"+result[i].name+"</option>";	
							}
						}
					}
					$("#fatId").append(html);
				}
			});
			
			var bool = "${flag}";
    	if(bool){
    		$("a").each(function() {
         	$(this).removeAttr("onclick");
         	$(this).removeClass();
      	});
    	}
	}

  function findTem() {
    var categoryId = $("#cId").val();
    var html = "<option value=''>请选择</option>";
    $.ajax({
      url: "${pageContext.request.contextPath}/adFirstAudit/find.do",
      data: {
        "categoryId": categoryId,
        "type": "REVIEW_ET"
      },
      dataType: 'json',
      success: function(result) {
        $("#fatId").empty();
        if(result.success == false && typeof(result.success) != "undefined") {
          layer.msg(result.msg, {
            offset: ['150px']
          });
        } else {
          if(result.length > 0) {
            for(var i = 0; i < result.length; i++) {
              html += "<option value='" + result[i].id + "'>" + result[i].name + "</option>";
            }
          }
        }
        $("#fatId").append(html);
      }
    });
  }
  
  function clearSearch() {
		$("#categorySel").val("");
     $("#fatId option:selected").removeAttr("selected");
     initTem();
   }
</script>

<body onload="getTotal()">
  <div id="categoryContent" class="categoryContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
    <div class=" input_group col-md-3 col-sm-6 col-xs-6 col-lg-12 p0">
      <div class="w100p">
        <input type="text" id="search" class="fl m0">
        <img alt="" style="position:absolute; top:8px;right:10px;" src="${pageContext.request.contextPath }/public/backend/images/view.png" onclick="searchs()">
      </div>
      <ul id="treeCategory" class="ztree" style="margin-top:0;"></ul>
    </div>
  </div>
  <h2 class="list_title">${packages.name}  经济技术审查项编辑</h2>
  <c:if test="${project.confirmFile != 1 && isView != 1 && flag ne '1'}">
    <div class="search_detail ml0">
      <ul class="demand_list">
        <li>
          <label class="fl">模板选择--></label>
          <label class="fl">所属产品目录：</label>
          <div class="input_group w200">
            <input id="cId" name="categoryId" type="hidden" value="${categoryId}">
            <input id="categorySel" type="text" name="categoryName" readonly value="${categoryName}" onclick="showCategory();" />
          </div>
        </li>
        <li>
          <select id="fatId" class="w180">
          </select>
        </li>

        <button type="button" onclick="loadTemplat('${projectId}','${packageId}')" id="loadTemp" class="btn">确定选择</button>
        <button type="button" onclick="loadOtherPackage('${packageId}','${projectId}')" class="btn">引入历史数据</button>
        <button type="reset" class="btn" onclick="clearSearch();">重置</button>
      </ul>
      <div class="clear"></div>
    </div>
  </c:if>
  <div class="content">
    <table class="table table-bordered table-condensed table-hover">
      <thead>
        <tr>
          <th class="info" width="15%">评审类别</th>
          <th class="info" width="20%">评审项目</th>
          <th class="info" width="15%">评审指标</th>
          <th class="info" width="10%">所属模型</th>
          <th class="info" width="30%">评审内容</th>
          <th class="info" width="10%">分值</th>
        </tr>
      </thead>
      ${str}
    </table>
    <div class="tr col-md-12 col-sm-12 col-xs-12">
      <div><b>总分:</b><span class="red f16" id="totalScore"></span></div>
    </div>
  </div>
  <div class="mt40 tc mb50">
    <c:if test="${project.confirmFile != 1 && flag ne '1'}">
      <button class="btn btn-windows back" onclick="window.location.href='${pageContext.request.contextPath}/adIntelligentScore/packageList.html?projectId=${projectId}&flowDefineId=${flowDefineId}'">返回</button>
    </c:if>
  </div>
  <div id="openDiv" class="dnone layui-layer-wrap">
    <form id="form2" method="post">
      <div class="drop_window">
        <input type="hidden" name="projectId" id="projectId" value="${projectId}">
        <input type="hidden" name="packageId" id="packageId" value="${packageId}">
        <input type="hidden" name="typeName" id="typeName">
        <input type="hidden" name="id" id="id">
        <ul class="list-unstyled">
          <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6 pl15">
            <label class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>评审项目</label>
            <span class="col-md-12 col-sm-12 col-xs-12 p0">
              <input name="name" id="name" maxlength="30" type="text">
            </span>
          </li>
          <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6">
            <label class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>序号</label>
            <div class="col-md-12 col-sm-12 col-xs-12 p0">
              <input name="remainScore" id="remainScore" maxlength="10" type="text">
            </div>
          </li>
          <li class="col-md-12 col-sm-12 col-xs-12 mb20">
            <label class="col-md-12 pl20 col-xs-12 padding-left-5">评审内容</label>
            <span class="col-md-12 col-sm-12 col-xs-12 p0">
              <textarea class="col-md-12 col-sm-12 col-xs-12 h80" id="remark" name="remark" maxlength="200" title="" placeholder=""></textarea>
            </span>
          </li>
        </ul>
        <div class="mt40 tc mb50">
          <input class="btn btn-windows save" onclick="saveItem();" value="保存" type="button">
          <input class="btn btn-windows back" onclick="cancel();" value="取消" type="button">
        </div>
      </div>
    </form>
  </div>
</body>

</html>