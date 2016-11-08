<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>品目信息</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">


<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/common.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/bootstrap.min.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/style.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/line-icons.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/app.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/application.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/header-v4.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/footer-v2.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/img-hover.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/page_job.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/shop.style.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/supplier/css/supplier.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ztree/css/zTreeStyle.css" type="text/css" />
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHQ/js/jquery_ujs.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHQ/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ztree/jquery.ztree.core.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ztree/jquery.ztree.excheck.js"></script>
<script src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
<script src="${pageContext.request.contextPath}/public/layer/extend/layer.ext.js"></script>
<script type="text/javascript">
function tijiao(status){
  $("#status").val(status);
  form1.submit();
}

function nextStep(){
  var action = "${pageContext.request.contextPath}/supplierAudit/product.html";
  $("#form_id").attr("action",action);
  $("#form_id").submit();
}


//填写原因
function reason(id,auditType){
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
  var supplierId=$("#supplierId").val();
  var id1=id+"1";
  var id2=id+"2";
  var auditFieldName = $("#"+id2+"").text().replace("：","");//审批的字段名字
  var index = layer.prompt({
	  title: '请填写不通过的理由：', 
	  formType: 2, 
	  offset: offset
  }, 
  function(text){
      $.ajax({
          url:"${pageContext.request.contextPath}/supplierAudit/auditReasons.html",
          type:"post",
          data:"auditType="+auditType+"&auditFieldName="+auditFieldName+"&suggest="+text+"&supplierId="+supplierId+"&auditField=品目树"+"&auditContent=品目树",
          dataType:"json",
	        success:function(result){
	        result = eval("(" + result + ")");
	        if(result.msg == "fail"){
	          layer.msg('该条信息已审核过！', {
            shift: 6 //动画类型
            });
	          }
        }
        });
	        $("#"+id1).show();
	        layer.close(index);
      });
}

</script>
<script type="text/javascript">
  var zTreeObj;
  var zNodes;
  $(function() {
    $("#page_ul_id").find("li").click(function() {
      var id = $(this).attr("id");
      var page = "tab-" + id.charAt(id.length - 1);
      $("input[name='defaultPage']").val(page);
    });
    var defaultPage = "${defaultPage}";
    if (defaultPage) {
      var num = defaultPage.charAt(defaultPage.length - 1);
      $("#page_ul_id").find("li").each(function(index) {
        var liId = $(this).attr("id");
        var liNum = liId.charAt(liId.length - 1);
        if (liNum == num) {
          $(this).attr("class", "active");
        } else {
          $(this).removeAttr("class");
        }
      });
      $("#tab_content_div_id").find(".tab-pane").each(function() {
        var id = $(this).attr("id");
        if (id == defaultPage) {
          $(this).attr("class", "tab-pane fade height-300 active in");
        } else {
          $(this).attr("class", "tab-pane fade height-300");
        }
      });
    } else {
      $("#page_ul_id").find("li").each(function(index) {
        if (index == 0) {
          $(this).attr("class", "active");
        } else {
          $(this).removeAttr("class");
        }
      });
      $("#tab_content_div_id").find(".tab-pane").each(function(index) {
        if (index == 0) {
          $(this).attr("class", "tab-pane fade height-300 active in");
        } else {
          $(this).attr("class", "tab-pane fade height-300");
        }
      });
    }
    // ztree
    $("#tab_content_div_id").find(".tab-pane").each(function(index) {
      var kind = "";
      var id = $(this).attr("id");
      if (id == "tab-1") kind = "1";
      if (id == "tab-2") kind = "2";
      if (id == "tab-3") kind = "3";
      if (id == "tab-4") kind = "4";
      loadZtree(id, kind);
    });
    
  });
  
  function loadZtree(id, kind) {
    var id = "";
    if (kind == "1") id = "tree_ul_id_1";
    if (kind == "2") id = "tree_ul_id_2";
    if (kind == "3") id = "tree_ul_id_3";
    if (kind == "4") id = "tree_ul_id_4";
    var setting = {
      async : {
        enable : true,
        url : "${pageContext.request.contextPath}/category/find_category.do",
        otherParam : {
          supplierId : "${currSupplier.id}",
          kind : kind
        },
        dataType : "json",
        type : "post",
      },
      check : {
        enable : true,
        chkboxType : {
          "Y" : "s",
          "N" : "s"
        }
      },
      data : {
        simpleData : {
          enable : true,
          idKey : "id",
          pIdKey : "parentId"
        }
      },
      callback: {
        onCheck: onCheck
      }
    };
    zTreeObj = $.fn.zTree.init($("#" + id), setting, zNodes);
  }
  
  function onCheck(e, treeId, treeNode) {
    var ids = "";
    var flag = treeNode.checked;
    var result = checkType();
    var tree = $.fn.zTree.getZTreeObj(result.id);
    var nodes = tree.getChangeCheckedNodes();
    for (var i = 0; i < nodes.length; i++) {
      if (!nodes[i].isParent) {
        if (ids) {
          ids += ",";
        }
        ids += nodes[i].id;
      }
    }
    
    if (ids) {
      $.ajax({
        url : "${pageContext.request.contextPath}/supplier_level/find_credit_ctnt_by_credit_id.do",
        type : "post",
        data : {
          ids : ids,
          flag : flag,
          type : result.type
        },
        dataType : "json",
        success : function(result) {
        },
      });
    }
    
    /**for (var i = 0; i < nodes.length; i++) {
      nodes[i].checkedOld = nodes[i].checked;
    }*/
  }
</script>
</head>
  
<body>
  <!-- 项目戳开始 -->
  <div class="container clear margin-top-30">
    <!--详情开始-->
    <div class="container content height-350">
      <div class="row magazine-page">
        <div class="col-md-12 tab-v2 job-content">
          <div class="padding-top-10">
            <ul class="nav nav-tabs bgdd">
              <li class=""><a>详细信息</a></li>
              <li class=""><a >财务信息</a></li>
              <li class=""><a>股东信息</a></li>
              <c:if test="${fn:contains(supplierTypeNames, '生产型')}">
              <li class=""><a >物资-生产型专业信息</a></li>
              </c:if>
              <c:if test="${fn:contains(supplierTypeNames, '销售型')}">
              <li class=""><a >物资-销售型专业信息</a></li>
              </c:if>
              <c:if test="${fn:contains(supplierTypeNames, '工程')}">
              <li class=""><a >工程-专业信息</a></li>
              </c:if>
              <c:if test="${fn:contains(supplierTypeNames, '服务')}">
              <li class=""><a >服务-专业信息</a></li>
              </c:if>
              <li class="active"><a>品目信息</a></li>
              <li class=""><a >产品信息</a></li>
              <li class=""><a >申请表</a></li>
              <li class=""><a >审核汇总</a></li>
            </ul>
            <div class="padding-top-10">
              <ul id="page_ul_id" class="nav nav-tabs bgdd">
                <c:if test="${fn:contains(supplierTypeNames, '生产型')}">
                  <li id="li_id_1" class="active"><a aria-expanded="true" href="#tab-1" data-toggle="tab" id="production2">物资-生产型品目信息</a></li>
                </c:if>
                <c:if test="${fn:contains(supplierTypeNames, '销售型')}">
                  <li id="li_id_2" class=""><a aria-expanded="false" href="#tab-2" data-toggle="tab" id="sale2">物资-销售型品目信息</a></li>
                </c:if>
                <c:if test="${fn:contains(supplierTypeNames, '工程')}">
                  <li id="li_id_3" class=""><a aria-expanded="false" href="#tab-3" data-toggle="tab" id="engineering2">工程品目信息</a></li>
                </c:if>
                <c:if test="${fn:contains(supplierTypeNames, '服务')}">
                  <li id="li_id_4" class=""><a aria-expanded="false" href="#tab-4" data-toggle="tab" id="service2">服务品目信息</a></li>
                </c:if>
               </ul>
            </div>
                  <form id="form_id" action="" method="post" >
                      <input id="supplierId" name="supplierId" value="${supplierId}" type="hidden">
                  </form>
                  
                  <div class="tab-content padding-top-20" id="tab_content_div_id">
                  <c:if test="${fn:contains(supplierTypeNames, '生产型')}">
                    <!-- 物资生产型 -->
                    <div class="tab-pane fade active in height-300" id="tab-1">
                      <div class="lr0_tbauto w200" onclick="reason(this.id,'item_pro_page')" id="production">
                        <ul id="tree_ul_id_1" class="ztree mt30" ></ul>
                        <div id="production1"  class="b f18 fl ml10 hand red"style="display: none">×</div>
                      </div>
                    </div>
                  </c:if>
                  <c:if test="${fn:contains(supplierTypeNames, '销售型')}">
                    <!-- 物资销售型 -->
                    <div class="tab-pane fade height-300" id="tab-2">
                      <div class="lr0_tbauto w200" onclick="reason(this.id,'item_sell_page')" id="sale">
                        <ul id="tree_ul_id_2" class="ztree mt30"></ul>
                        <div id="sale1"  class="b f18 fl ml10 hand red" style="display: none">×</div>
                      </div>
                    </div>
                  </c:if>
                  <c:if test="${fn:contains(supplierTypeNames, '工程')}">
                  <!-- 服务 -->
                    <div class="tab-pane fade height-200" id="tab-3">
                      <div class="lr0_tbauto w200" onclick="reason(this.id,'item_eng_page')" id="engineering">
                        <ul id="tree_ul_id_3" class="ztree mt30" ></ul>
                        <div id="engineering1"  class="b f18 fl ml10 hand red" style="display: none">×</div>
                      </div>
                    </div>
                  </c:if>
                  <c:if test="${fn:contains(supplierTypeNames, '服务')}">
                    <!-- 生产 -->
                    <div class="tab-pane fade height-200" id="tab-4">
                      <div class="lr0_tbauto w200" onclick="reason(this.id,'item_serve_page')" id="service">
                        <ul id="tree_ul_id_4" class="ztree mt30"></ul>
                        <div id="service1" class="b f18 fl ml10 hand red" style="display: none">×</div>
                      </div>
                    </div>
                  </c:if>
              </div>
          </div>
          <div class="col-md-12 add_regist tc">
            <!-- <a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="zhancun();">暂存</a> -->
            <a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="nextStep();">下一步</a>
          </div>     
        </div>
      </div>
    </div>
  </div>
</body>
</html>
