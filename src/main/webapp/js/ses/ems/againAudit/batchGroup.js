(function($) {
  $.fn.listConstructor = function(options) {
    var list_content = [];  // 初始化数据
    var list_content_new = [];  // 初始化新分组数据
    
    //默认参数
    var defaults = {
      type: 'POST',
      dataType: 'json',
      url: root_url + '/expertAgainAudit/findBatchDetails.do',
      newGroup_url: root_url + '/expertAgainAudit/findExpertGroupDetails.do',
      data: {
        batchId: getUrlParam('batchId'),
        status: '14'
      },
      data_new: {
        batchId: getUrlParam('batchId')
      },
      success: function (data) {
        list_content = data.object;  // 储存所需数据到变量
        
        $('#list_content').html('');
        if (typeof(list_content) != 'undefined') {
          for (var i in list_content.list) {
            if (typeof(list_content.list[i].batchDetailsNumber) === 'undefined') {
              list_content.list[i].batchDetailsNumber = '';
            }
            if (typeof(list_content.list[i].orgName) === 'undefined') {
              list_content.list[i].orgName = '';
            }
            if (typeof(list_content.list[i].realName) === 'undefined') {
              list_content.list[i].realName = '';
            }
            if (typeof(list_content.list[i].gender) === 'undefined') {
              list_content.list[i].gender = '';
            }
            if (typeof(list_content.list[i].workUnit) === 'undefined') {
              list_content.list[i].workUnit = '';
            }
            if (typeof(list_content.list[i].professTechTitles) === 'undefined') {
              list_content.list[i].professTechTitles = '';
            }
            if (typeof(list_content.list[i].updateTime) === 'undefined') {
              list_content.list[i].updateTime = '';
            }
            
            $('#list_content').append('<tr>'
              +'<td class="text-center"><input name="id" type="checkbox" value="'+ list_content.list[i].id +'" class="select_item"></td>'
              +'<td class="text-center break-all">'+ list_content.list[i].batchDetailsNumber +'</td>'
              +'<td class="text-center break-all">'+ list_content.list[i].orgName +'</td>'
              +'<td class="text-center break-all">'+ list_content.list[i].realName +'</td>'
              +'<td class="text-center break-all">'+ list_content.list[i].gender +'</td>'
              +'<td class="text-center break-all">'+ list_content.list[i].expertsFrom +'</td>'
              +'<td class="text-center break-all">'+ list_content.list[i].expertsTypeId +'</td>'
              +'<td class="text-center break-all">'+ list_content.list[i].workUnit +'</td>'
              +'<td class="text-center break-all">'+ list_content.list[i].professTechTitles +'</td>'
              +'<td class="text-center break-all">'+ list_content.list[i].updateTime +'</td>'
            +'</tr>');
          }
          
          if (list_content.list.length > 0) {
            batch_id = list_content.list[0].batchId;  // 获取批次id
          }
          
          // 勾选翻页之前选中的项
          for (var i in select_ids) {
            $('.select_item').each(function () {
              if ($(this).val() === select_ids[i]) {
                $(this).prop('checked', true);
                return false;
              }
            });
          }
          
          // 绑定列表框点击事件，获取选中id集合
          var select_checkbox = $('.againAudit_table').find('.select_item');
          if (select_checkbox.length > 0) {
            select_checkbox.bind('click', function () {
              var this_val = $(this).val().toString();
              if ($(this).is(':checked')) {
                select_ids.push(this_val);
              } else {
                for (var i in select_ids) {
                  if (select_ids[i] == this_val) {
                    select_ids.splice(i, 1);
                    break;
                  }
                }
              }
            });
          }
          
          // 构造分页
          // laypageConstructor();
        }
      },
      success_new: function (data) {
        var str = '';
        var str_tr = '';
        list_content_new = data.object;  // 储存所需数据到变量
        
        $('#group_batch_box').html('');
        if (typeof(list_content_new) != 'undefined') {
          for (var i in list_content_new) {
            for (var ii in list_content_new[i].expertList) {
              if (typeof(list_content_new[i].expertList[ii].batchDetailsNumber) === 'undefined') {
                list_content_new[i].expertList[ii].batchDetailsNumber = '';
              }
              if (typeof(list_content_new[i].expertList[ii].orgName) === 'undefined') {
                list_content_new[i].expertList[ii].orgName = '';
              }
              if (typeof(list_content_new[i].expertList[ii].realName) === 'undefined') {
                list_content_new[i].expertList[ii].realName = '';
              }
              if (typeof(list_content_new[i].expertList[ii].gender) === 'undefined') {
                list_content_new[i].expertList[ii].gender = '';
              }
              if (typeof(list_content_new[i].expertList[ii].workUnit) === 'undefined') {
                list_content_new[i].expertList[ii].workUnit = '';
              }
              if (typeof(list_content_new[i].expertList[ii].professTechTitles) === 'undefined') {
                list_content_new[i].expertList[ii].professTechTitles = '';
              }
              if (typeof(list_content_new[i].expertList[ii].updateTime) === 'undefined') {
                list_content_new[i].expertList[ii].updateTime = '';
              }
              
              str_tr += '<tr>'
                +'<td class="text-center break-all"><input name="id" type="checkbox" value="'+ list_content_new[i].expertList[ii].id +'" class="select_item"></td>'
                +'<td class="text-center break-all">'+ list_content_new[i].expertList[ii].batchDetailsNumber +'</td>'
                +'<td class="text-center break-all">'+ list_content_new[i].expertList[ii].orgName +'</td>'
                +'<td class="text-center break-all">'+ list_content_new[i].expertList[ii].realName +'</td>'
                +'<td class="text-center break-all">'+ list_content_new[i].expertList[ii].gender +'</td>'
                +'<td class="text-center break-all">'+ list_content_new[i].expertList[ii].expertsFrom +'</td>'
                +'<td class="text-center break-all">'+ list_content_new[i].expertList[ii].expertsTypeId +'</td>'
                +'<td class="text-center break-all">'+ list_content_new[i].expertList[ii].workUnit +'</td>'
                +'<td class="text-center break-all">'+ list_content_new[i].expertList[ii].professTechTitles +'</td>'
                +'<td class="text-center break-all">'+ list_content_new[i].expertList[ii].updateTime +'</td>'
              +'</tr>';
            }
            str += '<div class="group_batch_list">'
                  +'<div class="gbl_tit"><span class="count_flow hand mt0 shrink" onclick="toggle_list(this)">'+ list_content_new[i].name +'</span></div>'
                  +'<div class="gbl_content hide">'
                  +'  <div class="mt10 mb10"><button type="button" class="btn btn-windows delete" onclick="del_group(this)">删除</button></div>'
                  +'  <table class="table table-bordered table-condensed table-hover table-striped groupBatch_table">'
                  +'    <thead>'
                  +'      <tr>'
                  +'        <th class="info w40">选择</th>'
                  +'        <th class="info w150">批次编号</th>'
                  +'        <th class="info w90">采购机构</th>'
                  +'        <th class="info w100">专家姓名</th>'
                  +'        <th class="info w50">性别</th>'
                  +'        <th class="info w80">专家类型</th>'
                  +'        <th class="info w180">专家类别</th>'
                  +'        <th class="info">工作单位</th>'
                  +'        <th class="info w100">专业职称</th>'
                  +'        <th class="info w100">初审合格时间</th>'
                  +'      </tr>'
                  +'    </thead>'
                  +'    <tbody>'+ str_tr +'</tbody>'
                  +'  </table>'
                  +'</div>'
            +'</div>';
            $('#group_batch_box').append(str);
            str = str_tr = '';
          }
        }
      }
    };

    //覆盖默认参数
    var opts = $.extend(defaults, options);
    var el = this;

    var start = function() {
      el.each(function() {
        $.ajax({
          type: opts.type,
          dataType: opts.dataType,
          url: opts.url,
          data: opts.data,
          success: opts.success
        });
        
        $.ajax({
          type: opts.type,
          dataType: opts.dataType,
          url: opts.newGroup_url,
          data: opts.data_new,
          success: opts.success_new
        });
      });
    }

    return start();
  }
})(jQuery);