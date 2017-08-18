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

// 初始化专家列表和批次列表
function init_list(list_url, newGroup_url) {
  $('#list_content').listConstructor({
    url: list_url,
    newGroup_url: newGroup_url,
    data: {
      batchId: getUrlParam('batchId'),
      status: '14'
    },
    data_new: {
      batchId: getUrlParam('batchId')
    }
  });
}

// 创建新分组
function add_batch() {
  if (select_ids.length > 0) {
    var ids = select_ids.join(',');
    $.ajax({
      type: 'POST',
      dataType: 'json',
      url: add_url,
      data:{
        batchId: batch_id,
        ids: ids
      },
      success: function () {
        init_list(list_url, newGroup_url);
      }
    });
    
    select_ids = [];
  } else {
    layer.msg('请选择专家', {
      offset: '100px'
    });
  }
}

// 删除操作
function del_group(el) {
  var group_ids = [];  // 分组id集合
  var str_group_ids = '';
  $(el).parents('.group_batch_list').find('.select_item').each(function () {
    if ($(this).is(':checked')) {
      group_ids.push($(this).val());
    }
  });
  
  str_group_ids = group_ids.join(',');
  $.ajax({
    type: 'POST',
    dataType: 'json',
    url: del_url,
    data: {
      ids: str_group_ids
    },
    success: function (data) {
      init_list(list_url, newGroup_url);
    }
  });
}

// 添加至已有分组
function show_hasGroud() {
  if (select_ids.length > 0) {
    $.ajax({
      type: 'POST',
      dataType: 'json',
      url: getGroup_url,
      data:{
        batchId: getUrlParam('batchId')
      },
      success: function (data) {
        var list_content = data.object;
        var str = '';
        var ids = select_ids.join(',');
        for (var i in list_content) {
          if (i === '0') {
            str += '<tr>'
                  +'  <td class="w50 text-center"><input name="groupId" type="radio" checked="checked" value="'+ list_content[i].groupId +'" class="select_item"></td>'
                  +'  <td class="text-center">'+ list_content[i].groupName +'</td>'
                  +'</tr>';
          } else {
            str += '<tr>'
                  +'  <td class="w50 text-center"><input name="groupId" type="radio" value="'+ list_content[i].groupId +'" class="select_item"></td>'
                  +'  <td class="text-center">'+ list_content[i].groupName +'</td>'
                  +'</tr>';
          }
        }
        
        str = '<div class="p20"><table class="table table-bordered table-hover table-striped m0">'
            +'   <thead><tr><th>选择</th><th>审核组</th></tr></thead>'
            +'   <tbody>'+ str +'</tbody>'
            +'</table></div>';
        
        $('#group_list').html(str);
        
        var index = layer.open({
          title: ['添加至已有分组'],
          shade: 0.3, //遮罩透明度
          type : 1,
          area : ['300px'], //宽高
          content : $('#group_list'),
          btn: ['确定', '取消'],
          yes: function() {
            $('#group_list').find('.select_item').each(function () {
              if ($(this).is(':checked')) {
                select_groupId = $(this).val();
                return false;
              }
            });
            
            $.ajax({
              type: 'POST',
              dataType: 'json',
              url: addGroup_url,
              data: {
                groupId: select_groupId,
                ids: ids
              },
              success: function (data) {
                layer.msg(data.message, {
                  offset: '100px'
                });
                select_ids = [];
                select_groupId = '';
                init_list(list_url, newGroup_url);
                layer.close(index);
              }
            });
          },
          btn2: function() {
            select_groupId = '';
            layer.close(index);
          }
        });
      }
    });
  } else {
    layer.msg('请选择专家', {
      offset: '100px'
    });
  }
}

// 获取地址栏参数
function getUrlParam(name) {
  var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
  var r = window.location.search.substr(1).match(reg);
  if (r!=null) return unescape(r[2]); return null;
}