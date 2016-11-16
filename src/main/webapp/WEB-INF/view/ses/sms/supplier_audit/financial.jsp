<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>财务信息</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/bootstrap.min.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/style.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/app.css" type="text/css" />
<style type="text/css">
td {
  cursor:pointer;
}
</style>
<script type="text/javascript">
  //默认不显示叉
   $(function() {
     $("td").each(function() {
    $(this).find("p").hide();
    });
    
  });

    

function reason(id,auditFieldName){
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
  if(auditFieldName == "财务信息"){
    var auditContent=$("#"+id).text()+"年财务信息"; //审批的字段内容
  }else{
    var auditContent=$("#"+id).text()+"年财务附件";
  }
  
  var index = layer.prompt({
	  title: '请填写不通过的理由：', 
	  formType: 2, 
	  offset: offset
  }, 
  function(text){
    $.ajax({
        url:"${pageContext.request.contextPath}/supplierAudit/auditReasons.html",
        type:"post",
        data:"auditType=finance_page"+"&auditFieldName="+auditFieldName+"&auditContent="+auditContent+"&suggest="+text+"&supplierId="+supplierId+"&auditField="+id,
        dataType:"json",
	      success:function(result){
		      result = eval("(" + result + ")");
		      if(result.msg == "fail"){
		       layer.msg('该条信息已审核过！', {
                shift: 6, //动画类型
                offset:'300px'
            });
		      }
	     }
      });
      if(auditFieldName == "财务信息"){
        $("#"+id+"_show").show();
      }else{
        $("#"+id+"_fileShow").show();
      }
	      layer.close(index);
    });
}

/* function reason1(year, ele,auditField){
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
  var value = $(ele).parents("li").find("span").text().replace("：","");//审批的字段名字
  var auditFieldName=year+"年";//审批的字段名字
  var fail = false;
  var index = layer.prompt({
      title: '请填写不通过的理由：', 
      formType: 2, 
      offset : offset,
    }, 
      function(text){
		      $.ajax({
		          url:"${pageContext.request.contextPath}/supplierAudit/auditReasons.html",
		          type:"post",
		          data:"auditType=finance_page"+"&auditFieldName="+auditFieldName+"&auditContent=附件"+"&suggest="+text+"&supplierId="+supplierId+"&auditField="+auditField,
		          dataType:"json",
			          success:function(result){
			          result = eval("(" + result + ")");
			          if(result.msg == "fail"){
			           layer.msg('该条信息已审核过！', {
		                shift: 6, //动画类型
		                offset:'300px'
		            });
		            }
		          }
		        });
			        $(ele).parent("li").find("div").eq(1).show(); //隐藏勾
			        layer.close(index);
        });
    } */

		function nextStep(){
		  var action = "${pageContext.request.contextPath}/supplierAudit/shareholder.html";
		  $("#form_id").attr("action",action);
		  $("#form_id").submit();
		}

//文件下載
/*   function downloadFile(fileName) {
    $("input[name='fileName']").val(fileName);
    $("#download_form_id").submit();
  } */
  
function download(id,key){
    var form = $("<form>");   
        form.attr('style', 'display:none');   
        form.attr('method', 'post');
        form.attr('action', globalPath + '/file/download.html?id='+ id +'&key='+key);
        $('body').append(form); 
        form.submit();
}
</script>
<script type="text/javascript">
/*   function zhancun(){
    var supplierId=$("#supplierId").val();
    $.ajax({
      url:"${pageContext.request.contextPath}/supplierAudit/temporaryAudit.html",
      type:"post",
      data:"id="+supplierId,
      dataType:"json",
      success:function(result){
        result = eval("(" + result + ")");
        if(result.msg == "success"){
          layer.msg("暂存成功！",{offset:'200px'});
        }
      },error:function(){
        layer.msg("暂存失败！",{offset:'200px'});
      }
    });
  } */
</script>
</head>
    <body>
        <!--面包屑导航开始-->
        <div class="margin-top-10 breadcrumbs ">
            <div class="container">
                <ul class="breadcrumb margin-left-0">
                    <li><a href="#"> 首页</a></li><li><a href="#">供应商管理</a></li><li><a href="#">供应商审核</a></li>
                </ul>
            </div>
        </div> 
        <div class="container container_box">
            <div class="content height-350">
                <div class="col-md-12 tab-v2 job-content">
                    <ul class="nav nav-tabs bgdd">
		              <li class=""><a>详细信息</a></li>
		              <li class="active"><a id="financial">财务信息</a></li>
		              <li class=""><a >股东信息</a></li>
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
		              <li class=""><a >品目信息</a></li>
		              <li class=""><a >产品信息</a></li>
		              <li class=""><a >申请表</a></li>
		              <li class=""><a >审核汇总</a></li>
                    </ul>

                    <form id="form_id" action="" method="post" >
                      <input id="supplierId" name="supplierId" value="${supplierId}" type="hidden">
                    </form>
                    
                    <h2 class="count_flow"><i>1</i>信息表</h2>
                    <ul class="ul_list count_flow">
                        <table class="table table-bordered  table-condensed table-hover">
                            <thead>
                                <tr>
			                       <th class="info w50">序号</th>
			                       <th class="info">年份</th>
			                       <th class="info">会计事务所名称</th>
			                       <th class="info">事务所联系电话</th>
			                       <th class="info">审计人姓名</th>
			                       <th class="info">指标</th>
			                       <th class="info">资产总额</th>
			                       <th class="info">负债总额</th>
			                       <th class="info">净资产总额</th>
			                       <th class="info">营业收入</th>
			                       <th class="info w50"></th>
                                </tr>
                             </thead>
                             <c:forEach items="${financial}" var="f" varStatus="vs">
		                        <tr>
		                          <td class="tc">${vs.index + 1}</td>
		                          <td class="tc" id="${f.id }" onclick="reason('${f.id}','财务信息');">${f.year } </td>
		                          <td class="tc" onclick="reason('${f.id}','财务信息');" >${f.name }</td>
		                          <td class="tc" onclick="reason('${f.id}','财务信息');">${f.telephone }</td>
		                          <td class="tc" onclick="reason('${f.id}','财务信息');">${f.auditors }</td>
		                          <td class="tc" onclick="reason('${f.id}','财务信息');">${f.quota }</td>
		                          <td class="tc" onclick="reason('${f.id}','财务信息');">${f.totalAssets }</td>
		                          <td class="tc" onclick="reason('${f.id}','财务信息');">${f.totalLiabilities }</td>
		                          <td class="tc" onclick="reason('${f.id}','财务信息');">${f.totalNetAssets}</td>
		                          <td class="tc" onclick="reason('${f.id}','财务信息');">${f.taking}</td>
		                          <td class="tc" >
		                              <p id="${f.id}_show" class="b f18 fl ml10 hand red" >×</p>
		                          </td>
		                       </tr>
                            </c:forEach>
                        </table>
                    </ul>

                   <h2 class="count_flow"><i>2</i>附件表</h2>
                   <ul class="ul_list count_flow">
                      <table class="table table-bordered  table-condensed table-hover">
                        <thead>
                            <tr>
                                <th class="w50 info">年份</th>
                                <th class="info">财务利润表</th>
                                <th class="info">审计报告的审计意见</th>
                                <th class="info">资产负债表</th>
                                <th class="info">现金流量表</th>
                                <th class="info">所有者权益变动表</th>
                                <th class="info w50"></th>
                            </tr>
                        </thead>
                        <tbody id="finance_attach_list_tbody_id">
                            <c:forEach items="${financial}" var="finance" varStatus="vs">
                                <tr>
                                    <td class="tc" id="${finance.id }" onclick="reason('${finance.id}','财务附件');">${finance.year}</td>
                                    <td class="tc">
                                        <a class="mt3 color7171C6" href="javascript:download('${finance.auditOpinionId}', '${sysKey}')">${finance.auditOpinion}</a>
                                    </td>
                                    <td class="tc">
                                        <a class="mt3 color7171C6" href="javascript:download('${finance.liabilitiesListId}', '${sysKey}')">${finance.liabilitiesList}</a>
                                    </td>
                                    <td class="tc">
                                        <a class="mt3 color7171C6" href="javascript:download('${finance.profitListId}', '${sysKey}')">${finance.profitList}</a>
                                    </td>
                                    <td class="tc">
                                        <a class="mt3 color7171C6" href="javascript:download('${finance.cashFlowStatementId}', '${sysKey}')">${finance.cashFlowStatement}</a>
                                    </td>
                                    <td class="tc">
                                        <a class="mt3 color7171C6" href="javascript:download('${finance.changeListId}', '${sysKey}')">${finance.changeList}</a>
                                    </td>
                                    <td class="tc" >
                                      <p id="${finance.id}_fileShow" class="b f18 fl ml10 hand red" >×</p>
                                    </td>
                                </tr>
                            </c:forEach>
                          </tbody>
                      </table>
                      </ul>
                </div>
                  <%-- <c:forEach items="${financial}" var="f" varStatus="vs">
                    <div class=" margin-bottom-0 fl">
                    <h2 class="count_flow"><i>${vs.index + 1}</i>${f.year }年财务状况登记表</h2>
                    <ul class="ul_list">
                      <li class="col-md-3 margin-0 padding-0 "><span class="hand" onclick="reason1('${f.year }', this,'auditOpinion');" >财务审计报告意见表：</span>
                        <div class="input-append">
                          <c:if test="${f.auditOpinion != null}">
                            <a class="span5 green" onclick="downloadFile('${f.auditOpinion}')">附件下载</a>
                          </c:if>
                          <c:if test="${f.auditOpinion == null}">
                            <a class="span5 red">无附件下载</a>
                          </c:if>
                          <div  class="b f18 ml10 hand red">×</div>
                        </div>
                      </li>
                      <li class="col-md-3 margin-0 padding-0 "><span class="hand" onclick="reason1('${f.year }', this,'liabilitiesList');">资产负债表：</span>
                        <div class="input-append">
                          <c:if test="${f.liabilitiesList !=null}">
                            <a class="span5 green" onclick="downloadFile('${f.liabilitiesList}')">附件下载</a>
                          </c:if>
                          <c:if test="${f.liabilitiesList == null}">
                            <a class="span5 red">无附件下载</a>
                          </c:if>
                          <div  class="b f18 fl ml10 hand red">×</div>
                        </div>
                      </li>
                      <li class="col-md-3 margin-0 padding-0 "><span class="hand" onclick="reason1('${f.year }', this,'profitList');">利润表：</span>
                        <div class="input-append">
                          <c:if test="${f.profitList !=null}">
                            <a class="span5 green" onclick="downloadFile('${f.profitList}')">附件下载</a>
                          </c:if>
                          <c:if test="${f.profitList == null}">
                            <a class="span5 red">无附件下载</a>
                          </c:if>
                          <div  class="b f18 fl ml10 hand red">×</div>
                        </div>
                      </li>
                      <li class="col-md-3 margin-0 padding-0 "><span class="hand" onclick="reason1('${f.year }', this,'cashFlowStatement');">现金流量表：</span>
                        <div class="input-append">
                          <c:if test="${f.cashFlowStatement !=null}">
                            <a class="span5 green" onclick="downloadFile('${f.cashFlowStatement}')">附件下载</a>
                          </c:if>
                          <c:if test="${f.cashFlowStatement == null}">
                            <a class="span5 red">无附件下载</a>
                          </c:if>
                          <div class="b f18 fl ml10 hand red">×</div>
                        </div>
                      </li>
                      <li class="col-md-3 margin-0 padding-0 "><span class="hand" onclick="reason1('${f.year }', this,'changeList');">所有者权益变动表：</span>
                        <div class="input-append">
                          <c:if test="${f.changeList !=null}">
                            <a class="span5 green" onclick="downloadFile('${f.changeList}')">附件下载</a>
                          </c:if>
                          <c:if test="${f.changeList == null}">
                            <a class="span5 red">无附件下载</a>
                          </c:if>
                          <div class="b f18 fl ml10 hand red">×</div>
                        </div>
                      </li>
                    </ul>
                  </div>
                  </c:forEach> --%>
                <div class="col-md-12 add_regist tc">
                  <!-- <a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="zhancun();">暂存</a> -->
                    <input class="btn btn-windows"  type="button" onclick="nextStep();" value="下一步">
                </div>
              </div>
            </div>

  <form target="_blank" id="download_form_id" action="${pageContext.request.contextPath}/supplierAudit/download.html" method="post">
   <input type="hidden" name="fileName" />
  </form>
</body>
</html>
