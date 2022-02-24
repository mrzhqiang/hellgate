$(function () {
    $(".container").css({opacity: .8});   //设置透明度
    $.backstretch([
        "/images/dyzm.jpg",
        "/images/dyzm2.jpg"
    ], {duration: 3000, fade: 750});
});