$(function () {
    $(".container").css({opacity: .8});   //设置透明度
    $.backstretch([
        "/image/dyzm.jpg",
        "/image/dyzm2.jpg"
    ], {duration: 3000, fade: 750});
});