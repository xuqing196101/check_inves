<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>物资-生产型专业信息</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">

<style type="text/css">
td {
  cursor:pointer;
}
input {
  cursor:pointer;
}
</style>
<script type="text/javascript">
  //默认不显示叉
   $(function() {
    $("td").each(function() {
    $(this).parent("tr").find("td").eq(7).find("a").hide();
    });
    
    $(":input").each(function() {
      $(this).parent("div").find("div").hide();
      
      var onmouseover = "this.style.border='solid 1px #FF0000'";
      var onmouseout = "this.style.border='solid 1px #D3D3D3'";
       $(this).attr("onmouseover",onmouseover);
       $(this).attr("onmouseout",onmouseout);
    });
  });

function reason(id){
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
  var auditContent = "生产-资质证书信息";
  var index = layer.prompt({
	    title: '请填写不通过的理由：', 
	    formType: 2, 
	    offset: offset
    }, 
    function(text){
    $.ajax({
        url:"${pageContext.request.contextPath}/supplierAudit/auditReasons.html",
        type:"post",
        data:"auditType=mat_pro_page"+"&auditFieldName=生产-资质证书"+"&auditContent="+auditContent+"&suggest="+text+"&supplierId="+supplierId+"&auditField="+id,
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
        $("#"+id+"_show").show();
        layer.close(index);
    });
}


function reason1(id,auditField){
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
  var id2=id+"2";
  var id3=id+"3";
  var auditFieldName=$("#"+id2+"").text().replace("：",""); //审批的字段名字
  var auditContent= document.getElementById(""+id+"").value; //审批的字段内容
  var index = layer.prompt({
	  title: '请填写不通过的理由：', 
	  formType: 2, 
	  offset: offset
  }, 
  function(text){
    $.ajax({
        url:"${pageContext.request.contextPath}/supplierAudit/auditReasons.html",
        type:"post",
        data:"auditType=mat_pro_page"+"&auditFieldName="+auditFieldName+"&auditContent="+auditContent+"&suggest="+text+"&supplierId="+supplierId+"&auditField="+auditField,
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
       $("#"+id3).show();
       layer.close(index);
    });
}


function nextStep(url){
  $("#form_id").attr("action",url);
  $("#form_id").submit();
}


//文件下載
 /*  function downloadFile(fileName) {
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
  
  //只读
  $(function() {
    $(":input").each(function() {
      $(this).attr("readonly", "readonly");
    });
  });
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
            <div class="content">
                <div class="col-md-12 col-sm-12 col-xs-12 tab-v2 job-content">
		            <%-- <ul class="nav nav-tabs bgdd">
		              <li class=""><a >详细信息</a></li>
		              <li class=""><a >财务信息</a></li>
		              <li class=""><a >股东信息</a></li>
		              <c:if test="${fn:contains(supplierTypeNames, '生产')}">
		              <li class="active"><a >物资-生产专业信息</a></li>
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
		              <li class=""><a >申请表</a></li>
		              <li class=""><a >审核汇总</a></li>
		            </ul> --%>
		            
		            <ul class="flow_step">
                        <li >
                            <a aria-expanded="false" href="#tab-1" >详细信息</a>
                            <i></i>
                        </li>
                        <li >
                            <a aria-expanded="false" href="#tab-2" >财务信息</a>
                            <i></i>                            
                        </li>
                        <li >
                            <a aria-expanded="false" href="#tab-3" >股东信息</a>
                            <i></i>
                        </li>
                        <c:if test="${fn:contains(supplierTypeNames, '生产')}">
                            <li class="active">
                                <a aria-expanded="true" href="#tab-4" data-toggle="tab">生产信息</a>
                                <i></i>
                            </li>
                        </c:if>
                        <c:if test="${fn:contains(supplierTypeNames, '销售')}">
                            <li>
                                <a aria-expanded="false" href="#tab-4" >销售信息</a>
                                <i></i>
                            </li>
                        </c:if>
                        <c:if test="${fn:contains(supplierTypeNames, '工程')}">
                            <li>
                                <a aria-expanded="false" href="#tab-4" >工程信息</a>
                                <i></i>
                            </li>
                        </c:if>
                        <c:if test="${fn:contains(supplierTypeNames, '服务')}">
                            <li>
                                <a aria-expanded="false" href="#tab-4" >服务信息</a>
                                <i></i>
                            </li>
                        </c:if>
                        <li>
                            <a aria-expanded="false" href="#tab-4" >产品信息</a>
                            <i></i>
                        </li>
                        <li>
                            <a aria-expanded="false" href="#tab-4" >申请表</a>
                            <i></i>
                        </li>
                        <li>
                            <a aria-expanded="false" href="#tab-4" >审核汇总</a>
                        </li>
                    </ul>
    
                    <form id="form_id" action="" method="post" >
                        <input id="supplierId" name="supplierId" value="${supplierId}" type="hidden">
                    </form>
                    
                    <h2 class="count_flow"><i>1</i>供应商资质证书</h2>
                    <ul class="ul_list count_flow">
                        <table class="table table-bordered table-condensed table-hover">
		                    <thead>
		                      <tr>
		                        <th class="info w50">序号</th>
		                        <th class="info">资质证书名称</th>
		                        <th class="info">资质等级</th>
		                        <th class="info">发证机关</th>
		                        <th class="info">有效期(起止时间)</th>
		                        <th class="info">是否年检</th>
		                        <th class="info">附件</th>
		                        <th class="info w50"></th>
		                      </tr>
		                      </thead>
		                      <c:forEach items="${materialProduction}" var="m" varStatus="vs">
		                        <tr>
		                          <td class="tc">${vs.index + 1}</td>
		                          <td class="tc" id="${m.id}" onclick="reason('${m.id}');">${m.name }</td>
		                          <td class="tc" onclick="reason('${m.id}');">${m.levelCert}</td>
		                          <td class="tc" onclick="reason('${m.id}');">${m.licenceAuthorith }</td>
		                          <td class="tc" onclick="reason('${m.id}');">
		                            <fmt:formatDate value="${m.expStartDate }" pattern='yyyy-MM-dd'/>  至  
		                            <fmt:formatDate value="${m.expEndDate }" pattern='yyyy-MM-dd'/>
		                          </td>
		                          <td class="tc" onclick="reason('${m.id}');">
		                           <c:if test="${m.mot==0 }">否</c:if>
		                           <c:if test="${m.mot==1 }">是</c:if>
		                          </td>
		                          <td class="tc">
		                            <c:if test="${m.attach !=null}">
		                              <a class="mt3 color7171C6" href="javascript:download('${m.attachId}', '${sysKey}')">${m.attach}</a>
		                            </c:if>
		                            <c:if test="${m.attach ==null}">
		                              <a class="red">无附件下载</a>
		                            </c:if>
		                          </td>
		                          <td class="tc">
		                            <a  id="${m.id }_show" class="b f18 fl ml10 hand red">×</a>
		                          </td>
		                        </tr>
		                      </c:forEach>
		                  </table>
                      </ul>
                      
                      <h2 class="count_flow"><i>2</i>组织结构和人员</h2>
                      <ul class="ul_list count_flow">
                      <li class="col-md-3 margin-0 padding-0 ">
                        <span class="col-md-12 padding-left-5" id="orgName2">组织机构：</span>
                        <div class="input-append">
                          <input id="orgName" class="span5" type="text" value="${supplierMatPros.orgName }" onclick="reason1(this.id,'supplierMatPro.orgName')"/>
                          <div id="orgName3"  class="b f18 fl ml10 hand red">×</div>
                        </div>
                      </li>
                      <li class="col-md-3 margin-0 padding-0 ">
                        <span class="col-md-12 padding-left-5" id="totalPerson2">人员总数：</span>
                        <div class="input-append">
                          <input id="totalPerson" class="span5" type="text" value="${supplierMatPros.totalPerson }" onclick="reason1(this.id,'supplierMatPro.totalPerson')"/>
                          <div id="totalPerson3" class="b f18 fl ml10 hand red">×</div>
                        </div>
                      </li>
                      <li class="col-md-3 margin-0 padding-0 ">
                        <span class="col-md-12 padding-left-5" id="totalMange2">管理人员：</span>
                        <div class="input-append">
                          <input id="totalMange" class="span5" type="text"  value="${supplierMatPros.totalMange }" onclick="reason1(this.id,'supplierMatPro.totalMange')"/>
                          <div id="totalMange3" class="b f18 fl ml10 hand red">×</div>
                        </div>
                      </li>
                      <li class="col-md-3 margin-0 padding-0 ">
                        <span class="col-md-12 padding-left-5" id="totalTech2">技术人员：</span>
                        <div class="input-append">
                          <input id="totalTech" class="span5" type="text"  value="${supplierMatPros.totalTech }" onclick="reason1(this.id,'supplierMatPro.totalTech')"/>
                          <div id="totalTech3" class="b f18 fl ml10 hand red">×</div>
                        </div>
                      </li>
                      <li class="col-md-3 margin-0 padding-0 ">
                        <span class="col-md-12 padding-left-5" id="totalWorker2">工人(职员)：</span>
                        <div class="input-append">
                          <input id="totalWorker" class="span5" type="text" value="${supplierMatPros.totalWorker }" onclick="reason1(this.id,'supplierMatPro.totalWorker')"/>
                          <div id="totalWorker3" class="b f18 fl ml10 hand red">×</div>
                        </div>
                      </li>
                    </ul>
                      
                    <h2 class="count_flow"><i>3</i>产品研发能力</h2>
                      <ul class="ul_list count_flow">
                      <li class="col-md-3 margin-0 padding-0 ">
                        <span class="col-md-12 padding-left-5" id="scaleTech2">技术人员比例：</span>
                        <div class="input-append">
                          <input id="scaleTech" class="span5" type="text" value="${supplierMatPros.scaleTech }" onclick="reason1(this.id,'supplierMatPro.scaleTech')" />
                          <div id="scaleTech3" class="b f18 fl ml10 hand red">×</div>
                        </div>
                      </li>
                      <li class="col-md-3 margin-0 padding-0 ">
                        <span class="col-md-12 padding-left-5" id="scaleHeightTech2">高级技术人员比例：</span>
                        <div class="input-append">
                          <input id="scaleHeightTech" class="span5" type="text" value="${supplierMatPros.scaleHeightTech }" onclick="reason1(this.id,'supplierMatPro.scaleHeightTech')" />
                          <div id="scaleHeightTech3" class="b f18 fl ml10 hand red">×</div>
                        </div>
                      </li>
                      <li class="col-md-3 margin-0 padding-0 ">
                        <span class="col-md-12 padding-left-5" id=researchName2>研发部门名称：</span>
                        <div class="input-append">
                          <input id="researchName" class="span5" type="text"  value="${supplierMatPros.researchName }" onclick="reason1(this.id,'supplierMatPro.researchName')" />
                          <div id="researchName3" class="b f18 fl ml10 hand red">×</div>
                        </div>
                      </li>
                      <li class="col-md-3 margin-0 padding-0 ">
                        <span class="col-md-12 padding-left-5" id="totalResearch2">研发部门人数：</span>
                        <div class="input-append">
                          <input id="totalResearch" class="span5" type="text"  value="${supplierMatPros.totalResearch }" onclick="reason1(this.id,'supplierMatPro.totalResearch')" />
                        <div id="totalResearch3" class="b f18 fl ml10 hand red">×</div>
                        </div>
                      </li>
                      <li class="col-md-3 margin-0 padding-0 ">
                        <span class="col-md-12 padding-left-5" id="researchLead2">研发部门负责人：</span>
                        <div class="input-append">
                          <input id="researchLead" class="span5" type="text" value="${supplierMatPros.researchLead }" onclick="reason1(this.id,'supplierMatPro.researchLead')"/>
                          <div id="researchLead3"  class="b f18 fl ml10 hand red">×</div>
                        </div>
                      </li>
                      <li class="col-md-3 margin-0 padding-0 ">
                        <span class="col-md-12 padding-left-5" id="countryPro2">国家军队科研项目：</span>
                        <div class="input-append">
                            <input id="countryPro" class="span5" type="text" onclick="reason1(this.id,'supplierMatPro.countryPro')" value="${supplierMatPros.countryPro }">
                            <div id="countryPro3" class="b f18 fl ml10 hand red">×</div>
                        </div>
                      </li>
                      <li class="col-md-3 margin-0 padding-0 ">
                        <span class="col-md-12 padding-left-5" id="countryReward2">国家军队科技奖项：</span>
                        <div class="input-append">
                            <input id="countryReward" class="span5" type="text" onclick="reason1(this.id,'supplierMatPro.countryReward')" value="${supplierMatPros.countryReward }">
                            <div id="countryReward3" onclick="reason1(this.id)" class="b f18 fl ml10 hand red">×</div>
                        </div>
                      </li>
                    </ul>
                    
                    <h2 class="count_flow"><i>4</i>供应商生产能力</h2>
                      <ul class="ul_list count_flow">
                      <li class="col-md-3 margin-0 padding-0 ">
                        <span class="col-md-12 padding-left-5" id="totalBeltline2">生产线名称数量：</span>
                        <div class="input-append">
                          <input id="totalBeltline" class="span5" type="text" value="${supplierMatPros.totalBeltline }"  onclick="reason1(this.id,'supplierMatPro.totalBeltline')" />
                          <div id="totalBeltline3" class="b f18 fl ml10 hand red">×</div>
                        </div>
                      </li>
                      <li class="col-md-3 margin-0 padding-0 ">
                        <span class="col-md-12 padding-left-5" id="totalDevice2">生产设备名称数量：</span>
                        <div class="input-append">
                          <input id="totalDevice" class="span5" type="text" value="${supplierMatPros.totalDevice }"  onclick="reason1(this.id,'supplierMatPro.totalDevice')"/>
                          <div id="totalDevice3" class="b f18 fl ml10 hand red">×</div>
                        </div>
                      </li>
                   </ul>
                  
                  <h2 class="count_flow"><i>5</i>物资生产型供应商质量检测登记</h2>
                    <ul class="ul_list count_flow">
                      <li class="col-md-3 margin-0 padding-0 ">
                        <span class="col-md-12 padding-left-5" id="qcName2">质量检测部门：</span>
                        <div class="input-append">
                          <input id="qcName" class="span5" type="text" value="${supplierMatPros.qcName }"  onclick="reason1(this.id,'supplierMatPro.qcName')"/>
                          <div id="qcName3" class="b f18 fl ml10 hand red">×</div>
                        </div>
                      </li>
                      <li class="col-md-3 margin-0 padding-0 ">
                        <span class="col-md-12 padding-left-5" id="totalQc2">质量检测人数：</span>
                        <div class="input-append">
                          <input id="totalQc"  class="span5" type="text" value="${supplierMatPros.totalQc }" onclick="reason1(this.id,'supplierMatPro.totalQc')"/>
                          <div id="totalQc3"  class="b f18 fl ml10 hand red">×</div>
                        </div>
                      </li>
                      <li class="col-md-3 margin-0 padding-0 ">
                        <span class="col-md-12 padding-left-5" id="qcLead2">质检部门负责人：</span>
                        <div class="input-append">
                          <input id="qcLead" class="span5" type="text" value="${supplierMatPros.qcLead }" onclick="reason1(this.id,'supplierMatPro.qcLead')"/>
                        <div id="qcLead3" class="b f18 fl ml10 hand red">×</div>
                        </div>
                      </li>
                      <li class="col-md-3 margin-0 padding-0 ">
                        <span class="col-md-12 padding-left-5" id="qcDevice2">质量检测设备名称：</span>
                        <div class="input-append">
                            <input id="qcDevice" class="span5" type="text" onclick="reason1(this.id,'supplierMatPro.qcDevice')"value="${supplierMatPros.qcDevice }">
                            <div id="qcDevice3" class="b f18 fl ml10 hand red">×</div>
                        </div>
                      </li>
                    </ul>
                    <div class="col-md-12 col-sm-12 col-xs-12 add_regist tc">
                        <!-- <a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="zhancun();">暂存</a> -->
                        <a class="btn"  type="button" onclick="nextStep('${url}');">下一步</a>
                    </div>
                 </div>
                </div>
            </div>
        
        <form target="_blank" id="download_form_id" action="${pageContext.request.contextPath}/supplierAudit/download.html" method="post">
            <input type="hidden" name="fileName" />
        </form>   
    </body>
</html>