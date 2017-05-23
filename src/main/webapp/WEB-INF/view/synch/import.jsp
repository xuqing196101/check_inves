<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/synch/import.js"></script>
    
    <script type="text/javascript">
    function synchImport(){
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
		  <li><a href="javascript:void(0);"> 首页</a></li>
		  <li><a>保障系统</a></li>
		  <li><a>数据同步</a></li>
		  <li class="active"><a>数据导入</a></li>
	    </ul>
	    <div class="clear"></div>
    </div>
  </div>

  <div class="container">
    <input type="hidden" id="operType" name="operType" value="${operType}"/>
	<div class="mt10 pl20">
	
	<h2 class="count_flow"><i>1</i>导入设置</h2>
      <ul class="ul_list">
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
		
		<li class="col-md-12 col-sm-12 col-xs-12 pl15">
		  <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>同步类型</span>
		  <div>
			<c:forEach  items="${dataTypeList}" var="type">
			  <input type="checkbox" name="dataType" value="${type.code}"/> ${type.name}
			</c:forEach> 
			<input type="checkbox" name="dataType" value="inner_out"/> 供应商退回修改导出外网
			<input type="checkbox" name="dataType" value="outter_in"/> 供应商退回修改导入内网
			<input type="checkbox" name="dataType" value="temp_in"/> 临时供应商退回修改导入内网
			<input type="checkbox" name="dataType" value="expert_out"/> 专家退回修改导出外网
			<input type="checkbox" name="dataType" value="expert_again_inner"/> 专家退回修改导入内网
		  </div>
		</li>
		
		 <div class="clear mt10 tc">
		  <button class="btn" onclick="synchImport();">导入</button>
	    </div>
      </ul>
      
	
    </div>
    
    <div class="padding-top-10 clear" id="relaDeptId">
    
      <div class="search_detail">
          <ul class="demand_list">
            <li>
              <label class="fl">类型：</label>
              <select name="searchType" id="searchType">
                <option value="">请选择</option>
                <c:forEach items="${dataTypeList}" var="dataType">
                  <option value="${dataType.id}">${dataType.name}</option>
                </c:forEach>
              </select>
            </li>
            
            <li>
              <label>导出时间：</label>
              <input type="text" class="Wdate" id="searchStartTime" name="searchStartTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
              <span>至</span>
              <input type="text" class="Wdate" id="searchEndTime" name="searchEndTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
            </li>
            
            <button type="button" onclick="query();" class="btn fl mt1">查询</button>
	    	<button type="reset" class="btn fl mt1" onclick="reset();" >重置</button>  	
          </ul>
          <div class="clear"></div>
        </div>
    
	  <div class="content table_box">
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