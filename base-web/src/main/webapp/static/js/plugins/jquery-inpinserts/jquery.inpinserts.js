
;(function($){
	var settings = {
		val : ''	
	};
	$.fn.inpinserts = function(options){
		if(typeof options === 'string') {
			settings.val = options;
		} else if(Object.prototype.toString.call(options) === '[object Object]') {
			$.extend(settings, options);
		}
		var _this = $(this)[0];
		
		//IE下
		if(document.selection){
			$(_this).focus();

			//输入元素textara获取焦点
			var fus = document.selection.createRange();
			
			//获取光标位置
			fus.text = settings.val;

			//在光标位置插入值
			$(_this).focus();	
			
			//输入元素textara获取焦点
		}else if(_this.selectionStart || _this.selectionStart == '0'){
			
			var start = _this.selectionStart; 
			var end = _this.selectionEnd;
			var top = _this.scrollTop;
			//光标位置插入值
			
			_this.value = _this.value.substring(0, start) + settings.val + _this.value.substring(end, _this.value.length);
			this.focus();
		}else{
			//在输入元素textara没有定位光标的情况
			this.value += settings.val;
			this.focus();	
		};
		return $(this);
	};  
}(jQuery));