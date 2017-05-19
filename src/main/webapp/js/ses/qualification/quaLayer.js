var flg=false;
var choseIds="";
var choseNames="";
$(function(){
	list(1);
	var ids = $("#ids").val();
	var names = $("#names").val();
	$("#choseIds").val(ids);
	$("#choseNames").val(names);
	choseIds=$("#choseIds").val();
	choseNames=$("#choseNames").val();
	selectedValue(ids);
});

/**
 * 初始化
 * @returns
 */

function list(curr){
	if(!flg){
		var names = $("#names").val();
		var ids = $("#ids").val();
		$("#choseIds").val(ids+",");
		$("#choseNames").val(names+",");
	}
	var choseIdsOld=$("#choseIds").val();
	var choseNamesOld=$("#choseNames").val();
	var checkItem=$("input[name='chkItem']:checked");
	if(choseIdsOld.length>0){
		if(checkItem.length>0){
			var cId=choseIdsOld.split(",");
			var leng=0;
				if(cId[cId.length-1]==','){
					leng=cId.length-1;
				}else{
					leng=cId.length;
				}
			 for(var i=0;i<checkItem.length;i++){
				 var blg=false;
				 for(var j=0;j<leng;j++){
					 if($(checkItem[i]).val()==cId[j]){
						 blg=true;
					 }
				 }
				 if(!blg){
					 choseIds=choseIdsOld+$(checkItem[i]).val();
					 if(choseIds.substring(choseIds.length-1, choseIds.length)!=','){
						 choseIds=choseIds+",";
					 }
					 choseNames=choseNamesOld+$(checkItem[i]).parents('tr').find('td').eq(2).text();
					 if(choseNames.substring(choseNames.length-1, choseNames.length)!=','){
						 choseNames=choseNames+",";
					 }
				 }else{
					 choseIds=choseIdsOld;
					 if(choseIds.substring(choseIds.length-1, choseIds.length)!=','){
						 choseIds=choseIds+",";
					 }
					 choseNames=choseNamesOld;
					 if(choseNames.substring(choseNames.length-1, choseNames.length)!=','){
						 choseNames=choseNames+",";
					 }
				 }
			 }
			$("#choseIds").val(choseIds);
			$("#choseNames").val(choseNames);
		}
	}
	var name = $("#name").val();
	var type = $("#type").val();
	var ids = $("#choseIds").val();
	//如果是查物资生产型和物资销售型都查type为2的资质
	if (type == 2 || type == 3) {
		type = 2;
	}
	$.ajax({
		url: globalPath + "/qualification/list.do",
		type:"post",
		data:{'name' : name, 'type' : type,'page': curr,'ids':ids},
		dataType:"json",
		async: false,
		success:function(res){
			if (res.success){
				flg=true;
				var obj = res.obj;
				loadList(obj.list,obj.pageNum,obj.pageSize);
				loadPage(obj.pages,obj.total,obj.startRow,obj.endRow,curr);
				if(choseIds.length>0){
					 if(choseIds.substring(choseIds.length-1, choseIds.length)!=','){
						 selectedValue(choseIds);
					 }else{
						 selectedValue(choseIds.substring(0, choseIds.length-1));
					 }
					
				}
				
			}
		}
	});
}

/**
 * 分页
 * @param pages
 * @param total
 * @param start
 * @param end
 * @param current
 * @returns
 */
function loadPage(pages,total,start,end, current){
	laypage({
	    cont: $("#pagediv"),
	    pages: pages, 
	    skin: '#2c9fA6', 
	    skip: true, 
	    total: total,
	    startRow: start,
	    endRow: end,
	    groups: pages >= 3 ? 3 : pages, 
	    curr: current, 
	    jump: function(e, first){ 
	        if(!first){ 
	        	list(e.curr);
	        }
	    }
	  });
}

/**
 * 加载数据
 * @param data
 * @returns
 */
function loadList(data,pageNum,pageSize){
	$("#dataTable tbody").empty();
	if (data != null && data.length > 0){
		for (var i =0;i<data.length; i++){
			loadData(data[i],i,pageNum,pageSize);
		}
	}
}

/** 全选全不选 */
function selectAll(){
	var choseedIds =""
	var choseedNames ="";
	var ids = [], names = [];
   var checklist = document.getElementsByName ("chkItem");
   var checkAll = document.getElementById("checkAll");
   if(checkAll.checked){
	   for(var i=0;i<checklist.length;i++)
	   {
	      checklist[i].checked = true;
	      ids.push($(checklist[i]).val());
		  names.push($(checklist[i]).parents('tr').find('td').eq(2).text()+ " ");
	   } 
	 }else{
	  for(var j=0;j<checklist.length;j++)
	  {
	     checklist[j].checked = false;
	  }
	  ids=[];
	  names=[];
	  choseedIds='';
	  choseedNames='';
 	}
   if (choseedIds == '' || choseedIds == null) {
		choseedIds += ids.toString();
	} else {
		for ( var i = 0; i < ids.length; i++) {
			if(choseedIds.indexOf(ids[i]) == -1 ){
				choseedIds += ","+ids[i];
			} 
		}
	}
	if (choseedNames == '' || choseedNames == null) {
		choseedNames += names.toString();
	} else {
		for ( var i = 0; i < names.length; i++) {
			if(choseedNames.indexOf(names[i]) == -1 ){
				choseedNames += ","+names[i];
			} 
		}
	}
	$("#choseIds").val(choseedIds);
	$("#choseNames").val(choseedNames);
}

/**
 * 新增加载数据
 * @param data 
 * @returns
 */
function loadData(data,index,pageNum,pageSize){
	var html = "<tr> "
		     + "  <td class='tc'>"
	         + "    <input  type='checkbox' name='chkItem' value='"+data.id+"' onclick='choseedValue(this)'/>"
		     + "  </td>"
		     + "  <td class='tc'>"+((index+1) +  (pageNum -1) * pageSize) +"</td>"
		     + "  <td class='tl pl20'>"+data.name+"</td>"
		     + "</tr>";
	$("#dataTable tbody").append(html);
}

function choseedValue(obj){
	var ids ="", names = "";
	ids=$(obj).val();
	names=$(obj).parents('tr').find('td').eq(2).text();
	
	if(typeof($(obj).attr("checked"))=="undefined"||$(obj).attr("checked")==""){
		$(obj).attr("checked",true);
	}else{
		$(obj).attr("checked",false);
	}
	if($(obj).attr("checked")=="checked"){
		if(choseIds.substring(choseIds.length-1, choseIds.length)==','||choseIds.length==0){
			choseIds +=ids;
		}else{
			choseIds +=","+ids;
		}
		if(choseNames.substring(choseNames.length-1, choseNames.length)==','||choseNames.length==0){
			choseNames +=names;
		}else{
			choseNames +=","+names;
		}
	}else{
		var cId=[];
		var cName=[];
		if(choseIds.length>0){
			cId=choseIds.split(",");
			cName=choseNames.split(",");
			var cIdLength=0;
			var cNameLngth=0;
			if(cId[cId.length-1]==","){
				cIdLength=cId.length-1;
			}else{
				cIdLength=cId.length;
			}
			if(cName[cName.length-1]==","){
				cNameLngth=cName.length-1;
			}else{
				cNameLngth=cName.length;
			}
			for(var i=0;i<cIdLength;i++){
				if(cId[i]==ids){
					cId.splice(i, 1);
				}
			}
			for(var i=0;i<cNameLngth;i++){
				if(cName[i]==names){
					cName.splice(i, 1);
				}
			}
			choseIds=cId.join(",");
			choseNames=cName.join(",");
		}
	}
	$("#choseIds").val(choseIds);
	$("#choseNames").val(choseNames);
	
}


/**
 * 
 * @returns
 */
function ok(){
	var choseedIdss = $("#choseIds").val();
	var choseedNamess = $("#choseNames").val();
	if(choseedIdss.length>0){
		if(choseedIdss.substring(choseedIdss.length-1, choseedIdss.length)!=','){
			choseedIdss=choseedIdss;
			choseedNamess=choseedNamess;
		}else{
			choseedIdss=choseedIdss.substring(0,choseedIdss.length-1);
			choseedNamess=choseedNamess.substring(0,choseedNamess.length-1);
		}
		
	}
	var ids = [], names = [];
	/*$("input[name='chkItem']:checked").each(function(){
		ids.push($(this).val());
		names.push($(this).parents('tr').find('td').eq(2).text()+ " ");
	});
	
	if (ids.length == 0){
		layer.msg("请选择资质信息");
		return ;
	}*/
	if (choseedIdss == '' || choseedIdss == null) {
		//layer.msg("请选择资质信息");
		//return ;
	}
	var type = $("#type").val(); 
	/*if (type == 1){
		parent.addGeneralValue(ids.toString(),names.toString());
	}
	if (type == 2){
		parent.addProfileValue(ids.toString(),names.toString());
	}
	if (type == 3){
		parent.addProfileSalesValue(ids.toString(),names.toString());
	}*/
	if (type == 1){
		parent.addGeneralValue(choseedIdss,choseedNamess);
	}
	if (type == 2 || type == 4){
		parent.addProfileValue(choseedIdss,choseedNamess);
	}
	if (type == 3){
		parent.addProfileSalesValue(choseedIdss,choseedNamess);
	}
	cancel();
}

/**
 * 取消
 * @returns
 */
function cancel(){
	parent.layer.closeAll();
}

/**
 * 选中值
 * @param ids
 * @returns
 */
function selectedValue(ids){
	if (ids != ""){
		var idArray = ids.split(",");
		if (idArray.length > 0){
			$("input[name='chkItem']").each(function(){
				var obj = $(this).val();
				for (var i = 0;i<idArray.length;i++){
					 if (obj == idArray[i]){
						 $(this).attr("checked","checked");
					 }
				}
			});
		}
	}
}

/**
 * 查询
 * @returns
 */
function search(){
	list(1);
}

/**
 * 重置
 * @returns
 */
function resetQuery(){
	$("#name").val("");
	list(1);
}