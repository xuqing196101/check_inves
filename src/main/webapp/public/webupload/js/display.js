/**
 * dom加载...
 */
$(function(){
	showInit();
});

/**
 * 初始化
 */
function showInit(){
	var singleId = $("#showId").val();
	var groups = $("#show_groupId").val();
	if (!webIsNull(groups)){
		var arr = groups.split(",");
		$.each(arr,function(){
			packParam(this);
		});
	} else {
		packParam(singleId);
	}
}


/**
 * 包装参数
 */
function packParam(id){
	var businessId = $('#'+id+'_downBsId').val();
	var typeId = $("#"+id+"_downBstypeId").val();
	var key = $("#"+id+"_downBsKeyId").val();
	var del = $("#"+id+"_showdel").val();
	var param_del = transBoolean(del);
	var params = {businessId: businessId,typeId: typeId,key: key};
	display(params,id,param_del);
}

//判断是否为空
webIsNull = function(value){
	 if (value == null || value == "undefined" || value == "" || value == "null") {
		 return true;
	 }
	 return false;
}

//boolean类型转换
transBoolean = function(str){
		if (str === "true"){
			return true;
		}
		if (str === "false"){
			return false;
		}
	}


/**
 * 显示附件
 * @param params
 */
function display(params,id,del){
	var key = params.key;
	$.ajax({
		url: globalPath + '/file/displayFile.do',
		data: params,
		async:true,
		dataType: 'json',
		success:function(datas){
			disFiles(datas,key,id,del);
		}
	});
}

/**
 * 显示每一个li
 * @param data @link Array
 * @param key 系统对应的key
 */
function disFiles(data,key,id,del){
	var $ul = $("#"+id+"_disFileId");
	$ul.empty();
	if (data != null && data.length > 0) {
		for (var i =0 ;i < data.length; i++){
			disFile($ul,data[i],key,del);
		}
	}
}

/**
 * 生产li标签
 * @param html 父级html
 * @param obj  传入的对象
 * @param key  对应的系统key
 */
function disFile(html,obj,key,del){
	var li = '<li id='+obj.id+'><a href=\javascript:download("'+obj.id+'","' + key +'");>' + obj.name+ '</a>';
	if (del){
		li += '<span onclick=\'removeFile("'+ obj.id +'","'+key+'");\' style=\'color:red;cursor:pointer;width:20px;\'>×</span>';
	}
	var fileName = obj.path;
	var fileExt = fileName.substring(fileName.indexOf(".")+1,fileName.length).toLowerCase();
	if (/(gif|jpg|jpeg|png|bmp)$/.test(fileExt)) {
		li += '<span onclick=\'view("' + obj.path + '",this);\' style=\'color:red;cursor:pointer;width:30px;\'>view</span>';
	}
	li += '</li>';
	html.append(li);
}



/**
 * 删除文件
 * @param id  附件Id
 * @param key 系统对应的key
 */
function removeFile(id,key){
	$.ajax({
		url: globalPath + '/file/deleteFile.html',
		data: {id: id, key: key},
		async:true,
		success:function(msg){
			if (msg == "ok"){
				$("#" + id).text("删除成功.");
				$("#" + id).remove();
			}
		}
	});
}

/**
 * 预览
 * */
function view(path,obj){
	var url = globalPath + '/file/viewFile.html?path=' + path;
<<<<<<< Updated upstream
	packingHtml(url,obj);
=======
	
	packingHtml(url);
>>>>>>> Stashed changes
}

/**
 * 包装为预览html
 * @param url 请求的url
 */
function packingHtml(url,obj){
	var html = "<div id='uploadView'> "
		   + " <div class='filelist'> "
		   + " <div id='imgDivId'> "
		   + " <div id='imgId' class='imgWrap'><img  src='"+ url +"' /></div>"
		   + "	<div id='btnsid' class='file-panel'> "
           + "       <span class='uploadCancel'>删除</span> "
           + "        <span class='rotateRight'>向右旋转</span> "
           + "         <span class='rotateLeft'>向左旋转</span>"
           + "   </div> "
		   + " </div> "
		   + " </div></div>";
	layer.open({
		  type: 1,
		  title: false,
		  closeBtn: 0,
		  shadeClose: true,
		  area: ['600px','530px'],
		  offset: [$(obj).height()],
		  content: html
		});
	preview();
}

/**
 * 旋转
 */
function preview(){
	
	$btns = $("#btnsid");
	$wrap = $("#imgId");
	$("#imgDivId").on('mouseenter',function(){
		$btns.stop().animate({height: 30});
	});
	$("#imgDivId").on('mouseleave',function(){
		$btns.stop().animate({height: 0});
	});
	
	var rotation =0;
	supportTransition = (function(){
	 var s = document.createElement('p').style,
	     r = 'transition' in s ||
	             'WebkitTransition' in s ||
	             'MozTransition' in s ||
	             'msTransition' in s ||
	             'OTransition' in s;
	 s = null;
	 return r;
	})(),
	
	$btns.on( 'click', 'span', function() {
	 var index = $(this).index(),
	     deg;
	
	 switch ( index ) {
	     case 0:
	         layer.closeAll();
	         return;
	     case 1:
	         rotation += 90;
	         break;
	
	     case 2:
	         rotation -= 90;
	         break;
	 }
	
	 if ( supportTransition ) {
	     deg = 'rotate(' + rotation + 'deg)';
	     $wrap.css({
	         '-webkit-transform': deg,
	         '-mos-transform': deg,
	         '-o-transform': deg,
	         'transform': deg
	     });
	 } else {
	     $wrap.css( 'filter', 'progid:DXImageTransform.Microsoft.BasicImage(rotation='+ (~~((rotation/90)%4 + 4)%4) +')');
	 }
	
	});
}


/**
 * 附件下载
 * @param id 主键
 * @param key 对应系统的key
 */
function download(id,key){
	var form = $("<form>");   
	    form.attr('style', 'display:none');   
	    form.attr('method', 'post');
	    form.attr('action', globalPath + '/file/download.html?id='+ id +'&key='+key);
	    $('body').append(form); 
	    form.submit();
}