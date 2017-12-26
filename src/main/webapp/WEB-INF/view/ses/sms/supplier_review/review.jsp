<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp" %>
    <script type="text/javascript">
      function reviewEnd(){
    	  //显示重新复审、返回按钮
    	  $("#checkList").removeClass("hidden");
    	  //显示重新复审、返回按钮
    	  $("#review").removeClass("hidden");
    	  //隐藏复核结束、暂存按钮
    	  $("#reviewEnd").addClass("hidden");
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
          	<a href="javascript:void(0)">供应商管理</a>
          </li>
          <li>
          	<a href="javascript:void(0)">供应商复核</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>

    <div class="container container_box">
      <div class=" content height-350">
        <div class="col-md-12 tab-v2 job-content">
          <ul class="nav nav-tabs bgwhite">

          </ul>

          <h2 class="count_flow"><i>1</i>审核信息</h2>
          <ul class="ul_list hand">
            <table class="table table-bordered table-condensed table-hover">
              <thead>
                <tr>
                  <th class="info w50">序号</th>
                  <th class="info">项目</th>
                  <th class="info">扫描件</th>
                  <th class="info">原件与扫描件是否一致</th>
                  <th class="info">理由</th>
                </tr>
              </thead>
            </table>
          </ul>
          
          <h2 class="count_flow"><i>2</i>复核意见</h2>
          <ul class="ul_list hand">
            <li>
              <div class="select_check">
					      <input type="radio" value="1">复核合格
					      <input type="radio" value="0">复核不合格
				      </div>
            </li>
            <div><span type="text" name="cate_result" id="cate_result"></span></div>
						<li class="mt10">
						  <textarea id="opinion" class="col-md-12 col-xs-12 col-sm-12 h80">${auditOpinion.opinion}</textarea>
						</li>
          </ul>
          
          <h2 class="count_flow"><i>3</i>下载供应商复核表</h2>
          <ul class="ul_list hand">
          
          </ul>
          
          <div id="checkList" class="hidden">
            <h2 class="count_flow"><i>4</i>上传供应商复核表</h2>
	          <ul class="ul_list hand">
	          
	          </ul>
          </div>
        </div>
      </div>
    </div>
    
    <div class="col-md-12 col-sm-12 col-xs-12 add_regist tc" id="reviewEnd">
      <a class="btn padding-left-20 padding-right-20 btn_back margin-5">上一步</a>
      <a class="btn padding-left-20 padding-right-20 btn_back margin-5">暂存</a>
      <a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="reviewEnd();">复核结束</a>
    </div>
    
    <div class="col-md-12 col-sm-12 col-xs-12 add_regist tc hidden" id="review">
      <a class="btn padding-left-20 padding-right-20 btn_back margin-5">重新复核</a>
      <a class="btn padding-left-20 padding-right-20 btn_back margin-5">返回</a>
    </div>
  </body>

</html>