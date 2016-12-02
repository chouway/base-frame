var globalObj = globalObj || {};

require(["common"],function(){

    globalObj = $.extend(globalObj, {
        init : function(){
            this.initEvent();
            $('.index-div').eq(-1).height($(window).height() - 88);
        },
        initEvent:function() {
            var doc = $(document),
                that = this,
                curItem = 1,
                aniFlag = true;
            console.info("-->base web js inint");
        }
    });

    $(function(){
        globalObj.init();
    });
});