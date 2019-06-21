$(function() {
    var images = ['b0.jpg','b1.jpg','b2.jpg','b3.jpg','b5.jpg'];
    $(document.getElementById("myBody")).css({'background-image': 'url(/images/' + images[Math.floor(Math.random() * images.length)] + ')'});
    // $('#body').css({'background-image': 'url(images/' + images[Math.floor(Math.random() * images.length)] + ')'});
});