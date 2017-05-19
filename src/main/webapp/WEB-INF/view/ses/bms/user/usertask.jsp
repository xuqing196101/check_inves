<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
<head>
	<script src="${pageContext.request.contextPath}/public/codebase/dhtmlxscheduler.js" type="text/javascript" charset="utf-8"></script>
     <script src="${pageContext.request.contextPath}/public/codebase/locale/recurring/locale_recurring_cn.js" type="text/javascript" charset="utf-8"></script>
	 <script src="${pageContext.request.contextPath}/public/codebase/locale/locale_cn.js" type="text/javascript" charset="utf-8"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/public/codebase/dhtmlxscheduler.css" type="text/css" media="screen" title="no title" charset="utf-8">

	<script type="text/javascript" charset="utf-8">
	
	scheduler.locale = {  
    date:{  
        month_full:["1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"],  
        month_short:["1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"],  
        day_full:["星期日","星期一","星期二","星期三","星期四","星期五","星期六"],  
        day_short:["日","一","二","三","四","五","六"]  
    },  
    labels:{  
        dhx_cal_today_button:"今天",  
        day_tab:"日",  
        week_tab:"周",  
        month_tab:"月",  
        new_event:"新日程安排",  
        icon_save:"保存",  
        icon_cancel:"取消",  
        icon_details:"详细",  
        icon_edit:"编辑",  
        icon_delete:"删除",  
        confirm_closing:"",  
        confirm_deleting:"确定要删除该工作计划?",  
        section_description:"工作计划",
		section_content:"内容",
		section_time:"时间",
		message_ok:"确定",
		message_cancel:"取消"		
    }  
}; 
	
	$(function(){
		

	
	//	function init() {
			//scheduler.config.xml_date="%Y-%m-%d %H:%i";
			 scheduler.config.time_step=30; 
        scheduler.config.api_date="%Y-%m-%d %H:%i"; 
        scheduler.config.xml_date="%Y-%m-%d %H:%i"; 
        scheduler.config.hour_date="%H:%i"; 
        scheduler.config.default_date="%Y年%m月%d日"; 
        scheduler.config.month_date="%Y年 %m月"; 
        scheduler.config.day_date="%D %m月%d日"; 
        
			scheduler.config.time_step = 30;
			scheduler.config.multi_day = true;
			scheduler.locale.labels.section_subject = "Subject";
			scheduler.config.first_hour = 6;
			scheduler.config.limit_time_select = true;
			scheduler.config.details_on_dblclick = true;
			scheduler.config.details_on_create = true;


			/** 
 * 配置弹出计划详细框 
 */  
scheduler.config.details_on_dblclick = true;  
scheduler.config.details_on_create = true;  
scheduler.config.icons_select = ["icon_details","icon_delete"];  



			scheduler.templates.event_class=function(start, end, event){
				var css = "";

				if(event.subject) // if event has subject property then special class should be assigned
					css += "event_"+event.subject;

				if(event.id == scheduler.getState().select_id){
					css += " selected";
				}
				return css; // default return

				/*
					Note that it is possible to create more complex checks
					events with the same properties could have different CSS classes depending on the current view:

					var mode = scheduler.getState().mode;
					if(mode == "day"){
						// custom logic here
					}
					else {
						// custom logic here
					}
				*/
			};

			
			
			
			var subject = [
				// { key: '', label: '普通' }, //這是普通情況下
				{ key: '1', label: '紧急' },//紧急情況下
				{ key: '2', label: '重要' },//重要
				{ key: '3', label: '普通' }//普通
			];

			scheduler.config.lightbox.sections=[
				{name:"description", height:33, map_to:"text", type:"textarea" , focus:true},
				 {name:"详细安排", height:80, type:"textarea", map_to:"details"  },
				{name:"级别", height:40,type:"select", options: subject, map_to:"subject" },
				{name:"time", height:72, type:"time", map_to:"auto" , time_format:["%Y","%m","%d","%H:%i"]}
			];
	 
			scheduler.init('scheduler_here',new Date(${year},${month},1), "month");
 
			var s=${data};
			scheduler.parse(s, "json");

		// }
		
	
	});	
		
scheduler.attachEvent("onEventChanged", function(event_id, event_object){
  
    var id = event_object.id;
    var text = event_object.text;
    
    var start_date = event_object.start_date;
    var end_date = event_object.end_date;
    var sub = event_object.subject;
    var details = event_object.details;
    $("#udetail").val(details);
    $("#uid").val(id);
    $("#ucontent").val(text);
    $("#ustartDate").val(start_date);
    $("#uendDate").val(end_date);
    $("#ulevel").val(sub);
    
    $.ajax({
    	url:"${pageContext.request.contextPath}/usertask/update.html",
    	type:"post",
    	data:$("#usertask_form").serialize(),
    	success:function(data){
    	},
    	error:function(data){
    	} 
    });
    
    
    // 向数据库提交
   
});


scheduler.attachEvent("onEventAdded", function(event_id, event_object){
	  
    var id = event_object.id;
    var text = event_object.text;
   
    var start_date = event_object.start_date;
    var end_date = event_object.end_date;
    var sub = event_object.subject;
    var details = event_object.details;
    $("#udetail").val(details);
    $("#uid").val(id);
    $("#ucontent").val(text);
    $("#ustartDate").val(start_date);
    $("#uendDate").val(end_date);
    $("#ulevel").val(sub);
    $.ajax({
    	url:"${pageContext.request.contextPath}/usertask/add.html",
    	type:"post",
    	data:$("#usertask_form").serialize(),
    	success:function(data){
    		 
    	},
    	error:function(data){
    
    	} 
    });
 	 
    // 向数据库提交
   
});


scheduler.attachEvent("onBeforeEventDelete", function(event_id, event_object){
	  
    var id = event_object.id;
    var text = event_object.text;
    
    var start_date = event_object.start_date;
    var end_date = event_object.end_date;
    var details = event_object.subject;
    $.ajax({
    	url:"${pageContext.request.contextPath}/usertask/delet.html",
    	type:"post",
    	data:{
    		"id":id
    	},
    	success:function(data){
    	},
    	error:function(data){
    	} 
    }); 		
    // 向数据库提交
	    return true;
});


function dataTstring(date){
	 var d = new Date(date);
	 var s= d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate() + ' ' + d.getHours() + ':' + d.getMinutes() + ':' + d.getSeconds();
	return s;
}
	


scheduler.attachEvent("onBeforeViewChange", function(old_mode,old_date,mode,date){
    
	if(old_date!=null){
		window.location.href='${pageContext.request.contextPath}/usertask/getmonth.do?date='+date;
	}
 
    return true;
 
});


scheduler.attachEvent("onMouseMove", function (event_id, event_object){
	
	    var x,y;  
	    oRect = $(event_object).getBoundingClientRect();  
		// x=oRect.clientX;  
		y=oRect.top;  
		alert(y);
		var ev = scheduler.getEvent(event_id);  
		if(ev!=null){
			var id = event_id;
			 $.ajax({
			    	url:"${pageContext.request.contextPath}/usertask/detail.html",
			    	type:"post",
			    	data:{
			    		id:id
			    	},
			    	success:function(data){
			    		layer.msg(data, {
			    		    skin: 'demo-class',
			    			shade:false,
			    			area: ['300px'],
			    			offset : [y,x],
			    			time : 3000    //默认消息框不关闭
			    			
			    		});//去掉msg图标
			    		
			    	},
			    	error:function(data){
			    	} 
			    }); 
		}
		
		return true;
});


	</script>
</head>
<body >
<div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="${pageContext.request.contextPath}"> 首页</a></li><li><a href="#">支撑系统</a></li><li><a href="#">后台管理</a></li><li class="active"><a href="${pageContext.request.contextPath}/usertask/getmonth.html">用户任务管理</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   
   <div class="container mt20">
	<div id="scheduler_here" class="dhx_cal_container date"  >
		<div class="dhx_cal_navline">
			<div class="dhx_cal_prev_button">&nbsp;</div>
			<div class="dhx_cal_next_button">&nbsp;</div>
			<div class="dhx_cal_date"></div>
			
		</div>
		<div class="dhx_cal_header">
		</div>
		<div class="dhx_cal_data">
		</div>		
	</div>

	<div class="jzp">
	紧急: <input type="text" disabled="true" id="jinji" /><br/> 
	重要: <input type="text" disabled="true"  id="zhongyao" /> <br/>
	普通: <input type="text" disabled="true" id="putong" /> <br/>
	今天: <input type="text" disabled="true" id="dangqian" /> <br/>
	</div>


	
	<form id="usertask_form" action="" method="post">
	<input type="hidden" name="id" id="uid">
	<input type="hidden" name="content" id="ucontent">
	<input type="hidden" name="sDate" id="ustartDate">
	<input type="hidden" name="eDate" id="uendDate">
	
	<input type="hidden" name="startDate" id="sd">
	<input type="hidden" name="endDate" id="ed">
	<input type="hidden" name="status" id="ustatus">
	<input type="hidden" name="createdAt" id="ucreatedAt">
	<input type="hidden" name="updatedAt" id="uupdatedAt">
	<input type="hidden" name="memo" id="umemo">
	<input type="hidden" name="userId" id="uuserId">
	<input type="hidden" name="level" id="ulevel">
	<input type="hidden" name="detail" id="udetail">
	</form>
	
	<input type="hidden" id="hdate" value="${date }"> 
	</div>
</body>

</html>