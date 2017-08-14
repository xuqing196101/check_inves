// 创建复审批次
function create_review_batches(url, ids) {
  $.ajax({
    type: 'POST',
    dataType: 'json',
    url: url,
    data: {
      ids: ids
    },
    success: function (data) {
      var str = '';
      for (var i in data.object.list) {
        str += '<tr>'
                  +'<td class="text-center">'+ (parseInt(i) + 1) +'</td>'
                  +'<td class="text-center">'+ data.object.list[i].relName +'</td>'
                  +'<td class="text-center">'+ data.object.list[i].Major +'</td>'
                  +'<td class="text-center">'+ data.object.list[i].updatedAt +'</td>'
        +'</tr>';
      }
      $('#crb_content').html(str);
      layer.open({
        title: ['创建专家复审批次'],
        shade: 0.3, //遮罩透明度
        type : 1,
        area : [ '40%', '400px'  ], //宽高
        content : $('#create_review_batches'),
        btn: ['创建复审批次', '取消'],
        yes: function(){
          console.log(ids);
        },
        btn2: function(){
          layer.closeAll();
        }
      });
    }
  });
}

// 搜索
function againAudit_search(option, url) {
  $('#list_content').listConstructor({
    data: {
      orgName: option[0],
      updatedAt: option[1]
    },
    url: url
  });
}