// 创建新分组
function found_new_batch(url) {
  $.ajax({
    type: 'POST',
    dataType: 'json',
    url: url,
    data:{
      batchId: batch_id,
      Ids: select_ids
    },
    success: function (data) {
      console.log(data);
    }
  });
}

// 添加至已有分组
function add_hasGroud() {
  
}