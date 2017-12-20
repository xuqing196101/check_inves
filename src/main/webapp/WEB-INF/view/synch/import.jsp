<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/synch/import.js"></script>
    
    <script type="text/javascript">
    function synchImport(){
    	var authType = "${authType}";
    	if(authType != '4'){
    		layer.msg("只有资源服务中心才能操作");
    		return;
    	}
    	var startTime = $("#startTime").val();
    	var endTime = $("#endTime").val();
    	var dataType = [];
    	$("input[name='dataType']:checked").each(function(){
    		dataType.push($(this).val());
    	});
    	if (dataType.length == 0){
    		layer.msg("请选择同步类型");
    		return ;
    	}
    	 var index = layer.load(0, {
			shade : [ 0.1, '#fff' ],
			offset : [ '45%', '53%' ]
		});
    	
  	$.ajax({
    		url: globalPath + "/synchImport/dataImport.do",
    		type:"post",
    		data:{'startTime' : startTime,'endTime': endTime,'synchType': dataType.toString()},
    		dataType:"json",
    		success:function(res){
    			if (res.success){
    			layer.close(index);
    				layer.msg("导入成功");
    				list(1);
    			}else{
    				layer.close(index);
    				layer.msg("导入失败");
    			}
    		},error:function(){
			layer.close(index);
			layer.msg("导入错误！");
		}
    	});
    }
    
    </script>
  </head>
  <body>
   <!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
	  <div class="container">
	    <ul class="breadcrumb margin-left-0">
            <li><a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a></li>
            <li><a>支撑环境</a></li>
            <li><a>数据同步</a></li>
		  <li class="active"><a onclick="jumppage('${pageContext.request.contextPath}/synchImport/initImport.html')">数据导入</a></li>
	    </ul>
	    <div class="clear"></div>
    </div>
  </div>

  <div class="container container_box">
    <input type="hidden" id="operType" name="operType" value="${operType}"/>
	
	<h2 class="count_flow"><i>1</i>导入设置</h2>
      <ul class="ul_list p20">
      <%--   <li class="col-md-3 col-sm-6 col-xs-12 pl15">
		  <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>开始时间</span>
		  <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
			<input class="input_group Wdate mb0 w220" id="startTime" name="startTime" type="text" value="${startTime}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"> 
		  </div>
		</li>
		
		<li class="col-md-3 col-sm-6 col-xs-12 pl15">
		  <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>结束时间</span>
		  <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
			<input class="input_group Wdate mb0 w220" id="endTime" name="endTime" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"> 
		  </div>
		</li> --%>
		
		<li class="w100p">
		  <span class="block"><span class="star_red">*</span>同步类型</span>
		  <div class="m_inputGroup_wrapper mt10">
		  <div class="m_inputGroup">
				<c:forEach  items="${dataTypeList}" var="type">
			  <label class="hand"><input type="checkbox" name="dataType" value="${type.code}"/><span>${type.name}</span></label>
				</c:forEach>
				
				<c:if test="${authType ==  '4'}">
				<label class="hand"><input type="checkbox" name="dataType" value="inner_out"/><span>供应商退回修改导入外网</span></label>
				<%--<input type="checkbox" name="dataType" value="outter_in"/> 供应商退回修改导入内网--%>
				<label class="hand"><input type="checkbox" name="dataType" value="temp_in"/><span>临时供应商退回修改导入内网</span></label>
				<label class="hand"><input type="checkbox" name="dataType" value="expert_out"/><span>专家退回修改导出外网</span></label>
				<label class="hand"><input type="checkbox" name="dataType" value="expert_again_inner"/><span>专家退回修改导入内网</span></label>
				<label class="hand"><input type="checkbox" name="dataType" value="img_inner"/><span>供应商，专家图片导入</span></label>
				</c:if>
			</div>
		  </div>
		</li>
		
		<div class="clear mt20 tc">
		  <button class="btn w200 m0 h40" onclick="synchImport();">导入</button>
	  </div>
    </ul>
      
    
    <div class="padding-top-10 clear" id="relaDeptId">
      <div class="search_detail ml0">
      <div class="m_row_5">
      <div class="row">
        <div class="col-xs-2 col-sm-4 col-md-4 col-lg-3">
          <div class="row">
            <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">类型：</div>
            <div class="col-xs-8 f0 lh0">
              <select name="searchType" id="searchType" class="w100p h32 f14">
                <option value="">请选择</option>
                <c:forEach items="${dataTypeList}" var="dataType">
                  <option value="${dataType.id}">${dataType.name}</option>
                </c:forEach>
              </select>
            </div>
          </div>
        </div>
        
        <div class="col-xs-2 col-sm-4 col-md-4 col-lg-3">
          <div class="row">
            <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">开始导出时间：</div>
            <div class="col-xs-8 f0 lh0">
              <input type="text" class="Wdate w100p h32 f14 mb0" id="searchStartTime" name="searchStartTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
            </div>
          </div>
        </div>
        
        <div class="col-xs-2 col-sm-4 col-md-4 col-lg-3">
          <div class="row">
            <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">结束导出时间：</div>
            <div class="col-xs-8 f0 lh0">
              <input type="text" class="Wdate w100p h32 f14 mb0" id="searchEndTime" name="searchEndTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
            </div>
          </div>
        </div>
        
        <div class="col-xs-2 col-sm-4 col-md-4 col-lg-3">
          <div class="row">
            <div class="col-xs-12 f0">
              <button type="button" onclick="query();" class="btn mb0 h32">查询</button>
      	    	<button type="reset" class="btn mb0 mr0 h32" onclick="reset();">重置</button>
            </div>
          </div>
        </div>
      </div>
      </div>
      </div>
    
	  <div class="content pt10">
		<table class="table table-bordered table-condensed table-hover table-striped" id="dataTable" >
		  <thead>
			<tr>
			  <th class="info w50">序号</th>
			  <th class="info w150">同步类型</th>
			  <th class="info w200">同步时间</th>
			  <th class="info">描述</th>
			 </tr>
		   </thead>
		   <tbody></tbody>
		 </table>
		</div>
	  <div id="pagediv" align="right"></div>
     </div>
    </div>
  </body>

</html>