(function($) {
  $.fn.listConstructor = function(options) {
    var list_content = [];  // 初始化数据
    var list_content_new = [];  // 初始化新分组数据
    
    //默认参数
    var defaults = {
      type: 'POST',
      dataType: 'json',
      url: '',
      newGroup_url: '',
      data: {},
      data_new: {},
      success: function (data) {
        list_content = data.object;  // 储存所需数据到变量
        $('#list_content').html('');
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
            +'<td class="text-center">'+ list_content.list[i].batchDetailsNumber +'</td>'
            +'<td class="text-center">'+ list_content.list[i].orgName +'</td>'
            +'<td class="text-center">'+ list_content.list[i].realName +'</td>'
            +'<td class="text-center">'+ list_content.list[i].gender +'</td>'
            +'<td class="text-center">'+ list_content.list[i].workUnit +'</td>'
            +'<td class="text-center">'+ list_content.list[i].professTechTitles +'</td>'
            +'<td class="text-center">'+ list_content.list[i].updateTime +'</td>'
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
        laypageConstructor();
      },
      success_new: function (data) {
        var str = '';
        var str_tr = '';
        list_content_new = data.object;  // 储存所需数据到变量
        $('#group_batch_box').html('');
        for (var i in list_content_new) {
          for (var ii in list_content_new[i].expertList) {
            str_tr += '<tr>'
              +'<td class="text-center"><input name="id" type="checkbox" value="'+ list_content_new[i].expertList[ii].id +'" class="select_item"></td>'
              +'<td class="text-center">'+ list_content_new[i].expertList[ii].batchDetailsNumber +'</td>'
              +'<td class="text-center">'+ list_content_new[i].expertList[ii].orgName +'</td>'
              +'<td class="text-center">'+ list_content_new[i].expertList[ii].realName +'</td>'
              +'<td class="text-center">'+ list_content_new[i].expertList[ii].gender +'</td>'
              +'<td class="text-center">'+ list_content_new[i].expertList[ii].workUnit +'</td>'
              +'<td class="text-center">'+ list_content_new[i].expertList[ii].professTechTitles +'</td>'
              +'<td class="text-center">'+ list_content_new[i].expertList[ii].updateTime +'</td>'
            +'</tr>';
          }
          str += '<div class="group_batch_list">'
                +'<div class="gbl_tit"><span class="gbl_icon">'+ (parseInt(i) + 1) +'</span><span>'+ list_content_new[i].name +'</span></div>'
                +'<div class="mt10 mb10"><button type="button" class="btn btn-windows delete" onclick="del_group(this)">删除</button></div>'
                +'<table class="table table-bordered table-condensed table-hover table-striped groupBatch_table">'
                +'  <thead>'
                +'    <tr>'
                +'      <th class="info w50">选择</th>'
                +'      <th class="info w100">批次编号</th>'
                +'      <th class="info">采购机构</th>'
                +'      <th class="info">专家姓名</th>'
                +'      <th class="info">性别</th>'
                +'      <th class="info">工作单位</th>'
                +'      <th class="info">专业职称</th>'
                +'      <th class="info">提交复审时间</th>'
                +'    </tr>'
                +'  </thead>'
                +'  <tbody>'+ str_tr +'</tbody>'
                +'</table>'
          +'</div>';
          $('#group_batch_box').append(str);
          str = str_tr = '';
        }
      },
      error: function (data) {
        layer.msg(data.message, {
          offset: '100px'
        });
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
    
    // 分页
    function laypageConstructor() {
      laypage({
        cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
        pages: list_content.pages, //总页数
        skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
        skip: true, //是否开启跳页
        total: list_content.total,
        startRow: list_content.startRow,
        endRow: list_content.endRow,
        groups: list_content.pages >= 3 ? 3 : list_content.pages, //连续显示分页数
        curr: function() { //合格url获取当前页，也可以同上（pages）方式获取
          return list_content.pageNum;
        }(),
        jump: function(e, first) { //触发分页后的回调
          if(!first) { //一定要加此判断，否则初始时会无限刷新
            $("#pageNum").val(e.curr);
            $.ajax({
              type: opts.type,
              dataType: opts.dataType,
              url: opts.url,
              data: {
                pageNum: e.curr,
                status: '14'
              },
              success: opts.success
            });
          }
        }
      });
    }

    return start();
  }
})(jQuery);