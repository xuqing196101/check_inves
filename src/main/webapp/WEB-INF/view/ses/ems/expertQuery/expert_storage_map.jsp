<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp" %>
    <script src="${pageContext.request.contextPath}/public/highmap/js/highcharts.js"></script>
    <script src="${pageContext.request.contextPath}/public/highmap/js/modules/map.js"></script>
    <script src="${pageContext.request.contextPath}/public/highmap/js/modules/data.js"></script>
    <script src="${pageContext.request.contextPath}/public/highmap/js/modules/drilldown.js"></script>
    <script src="${pageContext.request.contextPath}/public/highmap/js/modules/exporting.js"></script>
    <script src="${pageContext.request.contextPath}/public/highmap/js/cn-china-by-peng8.js"></script>
    <script src="${pageContext.request.contextPath}/public/echarts/china.js"></script>
    <link href="${pageContext.request.contextPath}/public/highmap/js/font-awesome.css" media="screen" rel="stylesheet">
    <script type="text/javascript">
      $(function() {
        option = {
          /* title : {
              text: '专家数量统计',
              x:'center'
          }, */
          tooltip: {
            trigger: 'item'
          },
          legend: {
            orient: 'vertical',
            x: 'left',
            data: ['']
          },
          dataRange: {
            min: 0,
            max: '${maxCount}',
            x: 'left',
            y: 'bottom',
            text: ['高', '低'], // 文本，默认为数值文本
            calculable: true
          },
          toolbox: {
            show: true,
            orient: 'vertical',
            x: 'right',
            y: 'center',
            feature: {
              mark: {
                show: true
              },
              dataView: {
                show: true,
                readOnly: false
              },
              restore: {
                show: true
              },
              saveAsImage: {
                show: true
              }
            }
          },
          roamController: {
            show: true,
            x: 'right',
            mapTypeControl: {
              'china': true
            }
          },
          series: [{
            name: '中国',
            type: 'map',
            mapType: 'china',
            roam: false,
            itemStyle: {
              normal: {
                label: {
                  show: true
                }
              },
              emphasis: {
                label: {
                  show: true
                }
              }
            },
            data: eval('${data}'),
          }]
        };

        var myChart = echarts.init(document.getElementById("container"));
        myChart.setOption(option);
        myChart.hideLoading();
        myChart.on('click', function(params) {
          var address = encodeURI(params.name);
          address = encodeURI(address);
          window.location.href = "${pageContext.request.contextPath}/expertQuery/list.html?addressName=" + address +"&flag=1";
        });

      });
    </script>
    <script type="text/javascript">
	    function clearSearch() {
        $("#relName").attr("value", "");
        $("#status option:selected").removeAttr("selected");
        $("#mobile").attr("value", "");
        $("#idCardNumber").attr("value", "");
        $("#orgName").attr("value", "");
        $("#expertsFrom option:selected").removeAttr("selected");
        $("#expertsTypeId option:selected").removeAttr("selected");
        $("#orgName option:selected").removeAttr("selected");
        $("#formSearch").submit();
	    }
	    
	    //查看列表
	    function checkList(){
		    window.location.href = "${pageContext.request.contextPath}/expertQuery/list.html";
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
            <a href="javascript:void(0)">支撑环境</a>
          </li>
          <li>
            <a href="javascript:void(0)">专家管理</a>
          </li>
          <li>
            <a href="javascript:jumppage('${pageContext.request.contextPath}/expertQuery/list.html')">专家入库查询</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>
    <!-- 我的订单页面开始-->
    <div class="container">
      <div class="headline-v2">
        <h2>入库专家列表</h2>
      </div>
      <h2 class="search_detail">  
      <form action="${pageContext.request.contextPath}/expertQuery/expertStorageMap.html"  method="post" id="formSearch"  class="mb0"> 
      <input type="hidden" name="page" id="page">
      <div class="m_row_5">
      <div class="row">
        <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3 mb10">
          <div class="row">
            <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">专家姓名：</div>
            <div class="col-xs-8 f0 lh0">
              <input class="w100p h32 f14 mb0" type="text" id="relName" name="relName" value="${expert.relName }">
            </div>
          </div>
        </div>
        
        <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3 mb10">
          <div class="row">
            <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">身份证号：</div>
            <div class="col-xs-8 f0 lh0">
              <input class="w100p h32 f14 mb0" type="text" id="idCardNumber" name="idCardNumber" value="${expert.idCardNumber }">
            </div>
          </div>
        </div>
        
        <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3 mb10">
          <div class="row">
            <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">手机号：</div>
            <div class="col-xs-8 f0 lh0">
              <input class="w100p h32 f14 mb0" type="text" id="mobile" name="mobile" value="${expert.mobile }">
            </div>
          </div>
        </div>
        
        <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3 mb10">
          <div class="row">
            <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">专家类型：</div>
            <div class="col-xs-8 f0 lh0">
              <select  name="expertsFrom" id="expertsFrom" class="w100p h32 f14">
                <option selected="selected" value="">全部</option>
                <c:forEach items="${expertFromList }" var="from" varStatus="vs"> 
                  <option <c:if test="${expert.expertsFrom eq from.id }">selected="selected"</c:if> value="${from.id}">${from.name}</option>
                </c:forEach>
              </select>
            </div>
          </div>
        </div>
        
        <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3 mb10">
          <div class="row">
            <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">审核状态：</div>
            <div class="col-xs-8 f0 lh0">
              <select name="status" id="status" class="w100p h32 f14">
                <option selected="selected" value=''>全部</option>
                <option <c:if test="${expert.status =='6' }">selected</c:if> value="6">入库(待复查)</option>
                <option <c:if test="${expert.status eq 'reviewLook'}">selected</c:if> value="reviewLook">复查中</option>
                <option <c:if test="${expert.status =='19' }">selected</c:if> value="19">预复查结束</option>
                <option <c:if test="${expert.status =='7' }">selected</c:if> value="7">复查合格</option>
                <option <c:if test="${expert.status =='13' }">selected</c:if> value="13">无产品专家</option>
                <option <c:if test="${expert.status =='17' }">selected</c:if> value="17">资料不全</option>
              </select>
            </div>
          </div>
        </div>
        
        <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3 mb10">
          <div class="row">
            <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">采购机构：</div>
            <div class="col-xs-8 f0 lh0">
              <select name="orgName" id="orgName" class="w100p h32 f14">
                <option value=''>全部</option>
                <c:forEach items="${allOrg}" var="org">
                <c:if test="${org.isAuditSupplier == 1}">
                  <option value="${org.shortName}" <c:if test="${expert.orgName eq org.shortName}">selected</c:if>>${org.shortName}</option>
                </c:if>
                </c:forEach>
              </select>
            </div>
          </div>
        </div>
        
        <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3 mb10">
          <div class="row">
            <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">专家类别：</div>
            <div class="col-xs-8 f0 lh0">
              <select name="expertsTypeId" id="expertsTypeId" class="w100p h32 f14">
                <option selected="selected"  value=''>全部</option>
                <c:forEach items="${expTypeList}" var="exp">
                  <option <c:if test="${expert.expertsTypeId == exp.id}">selected</c:if> value="${exp.id}">${exp.name}</option>
                </c:forEach>          
              </select>
            </div>
          </div>
        </div>
      </div>
      </div>
      
      <div class="tc">
        <input class="btn mb0" value="查询" type="submit">
        <input class="btn mb0" onclick="clearSearch();" value="重置" type="reset">
        <input class="btn mb0 mr0" onclick="checkList();" value="切换到列表" type="reset">
      </div>
      </form>
      </h2>
    </div>
    <div id="container" style="height: 700px;min-width: 310px;margin: 0 auto;width: 800px;"></div>
  </body>

</html>