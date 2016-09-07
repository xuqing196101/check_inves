<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<title>Coloring events</title>
	<script src="<%=basePath%>public/ZHH/js/jquery.min.js"></script>
	<script src="<%=basePath%>public/codebase/dhtmlxscheduler.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=basePath%>public/prototype1.7.2/moment.min.js"></script>
	<link rel="stylesheet" href="<%=basePath%>public/codebase/dhtmlxscheduler.css" type="text/css" media="screen" title="no title" charset="utf-8">
	
	<style type="text/css" media="screen">
		html, body{
			margin:0;
			padding:0;
			height:100%;
			overflow:hidden;
		}

		.dhx_cal_event div.dhx_footer,
		.dhx_cal_event.past_event div.dhx_footer,
		.dhx_cal_event.event_2 div.dhx_footer,
		.dhx_cal_event.event_1 div.dhx_footer,
		.dhx_cal_event.event_3 div.dhx_footer{
			background-color: transparent !important;
		}
		.dhx_cal_event .dhx_body{
			-webkit-transition: opacity 0.1s;
			transition: opacity 0.1s;
			opacity: 0.7;
		}
		.dhx_cal_event .dhx_title{
			line-height: 12px;
		}
		.dhx_cal_event_line:hover,
		.dhx_cal_event:hover .dhx_body,
		.dhx_cal_event.selected .dhx_body,
		.dhx_cal_event.dhx_cal_select_menu .dhx_body{
			opacity: 1;
		}

		.dhx_cal_event.event_1 div, .dhx_cal_event_line.event_1{
			background-color: red !important;
			border-color: #a36800 !important;
		}
		.dhx_cal_event_clear.event_1{
			color:red !important;
		}

		.dhx_cal_event.event_3 div, .dhx_cal_event_line.event_3{
			background-color: #36BD14 !important;
			border-color: #698490 !important;
		}
		.dhx_cal_event_clear.event_3{
			color:#36BD14 !important;
		}

		.dhx_cal_event.event_2 div, .dhx_cal_event_line.event_2{
			background-color: orange !important;
			border-color: #839595 !important;
		}
		.dhx_cal_event_clear.event_2{
			color:orange !important;
		}
		option{
		min-height:2.2em;
		}
	</style>

	<script type="text/javascript" charset="utf-8">
	
	scheduler.locale = {  
    date:{  
        month_full:["1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"],  
        month_short:["1","2","3","4","5","6","7","8","9","10","11","12"],  
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
		section_time:"时间"		
    }  
}; 
	
	$(function(){
		

	
	//	function init() {
			scheduler.config.xml_date="%Y-%m-%d %H:%i";
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
				{name:"description", height:43, map_to:"text", type:"textarea" , focus:true},
				{name:"级别", height:20, type:"select", options: subject, map_to:"subject" },
				{name:"time", height:72, type:"time", map_to:"auto" }
			];

			scheduler.init('scheduler_here', new Date(), "month");
 
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
 	var s= dataTstring(start_date);
	var e= dataTstring(end_date);
    $("#uid").val(id);
    $("#ucontent").val(text);
    $("#ustartDate").val(s);
    $("#uendDate").val(e);
    $("#ulevel").val(sub);
    
    $.ajax({
    	url:"<%=basePath%>usertask/update.do",
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
   	var s= dataTstring(start_date);
	var e= dataTstring(end_date);
    $("#uid").val(id);
    $("#ucontent").val(text);
    $("#ustartDate").val(s);
    $("#uendDate").val(e);
    $("#ulevel").val(sub);
    $.ajax({
    	url:"<%=basePath%>usertask/add.do",
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
    	url:"<%=basePath%>usertask/delet.do",
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
	






	</script>
</head>
<body onload="init();">
	<div id="scheduler_here" class="dhx_cal_container" style='width:800px; height:500px;'>
		<div class="dhx_cal_navline">
		<!-- 	<div class="dhx_cal_prev_button">&nbsp;</div>
			<div class="dhx_cal_next_button">&nbsp;</div> -->
			<div class="dhx_cal_date"></div>
			
		</div>
		<div class="dhx_cal_header">
		</div>
		<div class="dhx_cal_data">
		</div>		
	</div>
	<span style="margin-left: 100px;margin-top: 10px;"></span>
	紧急: <input type="text" style="width:100px;height:18px;background-color:red;border:1px solid #ccc;margin-top: 10px;"/> 
	重要: <input type="text" style="width:100px;height:18px;background-color:orange;border:1px solid #ccc;margin-top: 10px;"/> 
	普通: <input type="text" style="width:100px;height:18px;background-color:#36BD43;border:1px solid #ccc;margin-top: 10px;"/> 
	
	<form id="usertask_form" action="<%=basePath%>usertask/add.do" method="post">
	<input type="hidden" name="id" id="uid">
	<input type="hidden" name="content" id="ucontent">
	<input type="hidden" name="startDate" id="ustartDate">
	<input type="hidden" name="endDate" id="uendDate">
	<input type="hidden" name="level" id="ulevel">
	</form>
	
</body>

</html>