/**
 * 依赖于 jQuery, myPagination, underScore
 */

var App = App || {}, pageUtil = {
    /**
     * 页面分页form POST提交
     * @param  url 请求地址 
     *         data post数据
     *         callback 成功回调
     */
     pageForm : function(pageCon, pageForm){
    	  var pagesize = $("input[name='pageSize']", $(pageForm)).val(),
    	      currpage = $("input[name='currPage']", $(pageForm)).val(),
    	      pagecount = $("input[name='pageCount']", $(pageForm)).val();
    	 $(pageCon).myPagination({
				cssStyle:"black",
				pageSize:parseInt(pagesize) || 1,
				currPage:parseInt(currpage) || 0,
		        pageCount:parseInt(pagecount) || 0,
		        pageNumber:5,
		        panel:{
		        	first:"&nbsp;",
		        	last:"&nbsp;",
		        	prev:"&nbsp;",
		        	next:"&nbsp;"
		        },
		       ajax : {
		    	     onClick : function(page){
		    		    $("input[name='pageNo']", $(pageForm)).val(page);
		    		   $(pageForm).submit();
		    	   }
		       }
		    });
     },
     pageAjaxJson : function(pageCon, url, pdata, callback){
    	 var xhrPage = $(pageCon).myPagination({
				cssStyle:"black",
		        panel:{
		        	first:"&nbsp;",
		        	last:"&nbsp;",
		        	prev:"&nbsp;",
		        	next:"&nbsp;"
		        },
		       ajax : {
		    	     on : true,
		    	     url : url,
		    	     type : "post",
		    	     dataType : "json",
		    	     pageCountId : "totalPageCount",
		    	     param : pdata,
		    	     ajaxStart: function() {
		    	    	 dialogUtil.msgbox.show("数据拉取中...", 6, 100000);
		             },
		    	     onClick : function(page){
		    	   },
		    	   callback : callback
		       }
		    });
    	 return xhrPage;
    }
};