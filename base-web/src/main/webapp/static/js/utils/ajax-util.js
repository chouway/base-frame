/**
 * 依赖于 jQuery dialog-util
 */

var App = App || {}, ajaxUtil = {
    /**
     * 普通ajax请求
     * @param  url 请求地址 
     *         data post数据
     *         callback 成功回调
     */
     ajax : function(url, data, callback){
    		$.ajax({
				type:'POST',
				url: url,
				data: data,
				dataType : 'json',
				success: function(data) {
					callback && callback(data);
				},
				error: function() {
					dialogUtil.msgbox.show("通讯失败，请稍后再试...", 5, 2000);
				}
		 });
     },
	/**
	 * ajax同步请求
	 * @param  url 请求地址 
	 *         data post数据
	 *         callback 成功回调
	 */
	 ajaxAsync : function(url, data, callback){
			$.ajax({
				type:'POST',
				async:false,
				url: url,
				data: data,
				dataType : 'json',
				success: function(data) {
					callback && callback(data);
				},
				error: function() {
					dialogUtil.msgbox.show("通讯失败，请稍后再试...", 5, 2000);
				}
		 });
	 }
};