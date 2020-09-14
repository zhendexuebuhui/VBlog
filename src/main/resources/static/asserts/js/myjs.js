//左侧导航栏收缩功能动画效果

syncAnimation();
function animation(){
    var i=parseInt(sessionStorage.getItem('animationVar'));
    //这里定义一个全局变量来方便判断动画收缩的效果,也就是放在最外面
    if(i==0){
        $(".layui-side").animate({width:'50px'});
        $(".layui-body").animate({left:'50px'});
        $(".layui-footer").animate({left:'50px'});
        $("#animation").attr("class","layui-icon layui-icon-spread-left");
        
            i=1;
    }else{
        $(".layui-side").animate({width:'200px'});
        $(".layui-body").animate({left:'200px'});
        $(".layui-footer").animate({left:'200px'});
        $("#animation").attr("class","layui-icon layui-icon-shrink-right");
        
            i=0;
    }
    sessionStorage.setItem('animationVar',i.toString());
}

function syncAnimation() {
    var i=parseInt(sessionStorage.getItem('animationVar'));
    if(i==1){
        $(".layui-side").width(50);
        $(".layui-body").animate({left:'50px'},1);
        $(".layui-footer").animate({left:'50px'},1);
        $("#animation").attr("class","layui-icon layui-icon-spread-left");
    }else{
        $(".layui-side").width(200);
        $(".layui-body").animate({left:'200px'},1);
        $(".layui-footer").animate({left:'200px'},1);
        $("#animation").attr("class","layui-icon layui-icon-shrink-right");
    }
}
