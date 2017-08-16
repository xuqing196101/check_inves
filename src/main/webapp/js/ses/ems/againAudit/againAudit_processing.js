// 创建复审批次
function create_review_batches(url, batch_url, ids) {
  ids = ids.join(',');  // 将获得到的id数组转换成字符串（为后台处理）
  var batchName_obj = $('[name=batchName]');  // 批次名称
  var batchNumber_obj = $('[name=batchNumber]');  // 批次编号
  
  if (ids === '') {
    layer.msg('请至少选择一名专家', {
      offset: '100px'
    });
  } else {
    $.ajax({
      type: 'POST',
      dataType: 'json',
      url: url,
      data: {
        batchIds: ids
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
        var index = layer.open({
          title: ['创建专家复审批次'],
          shade: 0.3, //遮罩透明度
          type : 1,
          area : ['40%', '400px'], //宽高
          content : $('#create_review_batches'),
          btn: ['创建复审批次', '取消'],
          yes: function() {
            if (batchName_obj.val() === '') {
              layer.msg('请填写批次名称', {
                offset: '100px'
              });
              return false;
            } else if (batchNumber_obj.val() === '') {
              layer.msg('请填写批次编号', {
                offset: '100px'
              });
              return false;
            } else {
              $.ajax({
                type: 'POST',
                dataType: 'json',
                url: batch_url,
                data: {
                  ids: ids,
                  batchName: batchName_obj.val(),
                  batchNumber: batchNumber_obj.val()
                },
                success: function (data) {
                  layer.msg(data.message, {
                    offset: '100px'
                  });
                  batchName_obj.val('');
                  batchNumber_obj.val('');
                  $('#list_content').listConstructor({
                    url: url
                  });
                  layer.close(index);
                },
                error: function (data) {
                  layer.msg(data.message, {
                    offset: '100px'
                  });
                }
              });
            }
          },
          btn2: function() {
            batchName_obj.val('');
            batchNumber_obj.val('');
            layer.close(index);
          }
        });
      }
    });
  }
}

// 搜索
function againAudit_search(url) {
  var relName = $('[name=relName]').val();  // 获取采购机构名称
  var auditAt = $('[name=auditAt]').val();  // 获取提交复审时间
  $('#list_content').listConstructor({
    data: {
      orgName: relName,
      updatedAt: auditAt
    },
    url: url
  });
}