//封装 数据
function content(tablerId,ind,item){
    var parents=$("#"+tablerId+" #qualifications"+ind+"",window.parent.document).parent();
    var content;
    var root=parents.find("td").eq(1).text();
    var first=parents.find("td").eq(2).text();
    var second=parents.find("td").eq(3).text();
    var third=parents.find("td").eq(4).text();
    var fourth=parents.find("td").eq(5).text();
    if(root){
        content=root;
    }
    if(first){
        content=content+"/"+first;
    }
    if(second){
        content=content+"/"+second;
    }
    if(third){
        content=content+"/"+third;
    }
    if(fourth){
        content=content+"/"+fourth;
    }
    return content+'('+item+')';
}
//封装 数据
function contentParent(tablerId,ind,items){
    var parents=$("#"+tablerId+" #qualifications"+ind+"").parent();
    var content;
    var root=parents.find("td").eq(1).text();
    var first=parents.find("td").eq(2).text();
    var second=parents.find("td").eq(3).text();
    var third=parents.find("td").eq(4).text();
    var fourth=parents.find("td").eq(5).text();
    if(root){
        content=root;
    }
    if(first){
        content=content+"/"+first;
    }
    if(second){
        content=content+"/"+second;
    }
    if(third){
        content=content+"/"+third;
    }
    if(fourth){
        content=content+"/"+fourth;
    }
    return content+'('+items+')';;
}