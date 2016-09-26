//弹出层"'"+titles+"'"
function showiframe(titles,width,height,url,top){
	 if(top == null || top == "underfined"){
	  top = 120;
	 }
	layer.open({
        type: 2,
        title: [titles,"background-color:#83b0f3;color:#fff;font-size:16px;text-align:center;"],
        maxmin: true,
        shade: [0.3, '#000'],
       	offset: top+"px",
        shadeClose: false, //点击遮罩关闭层 
        area : [width+"px" , height+"px"],
        content: url
    });
}