$(document).ready(function () {
    $("#logoutLink").on("click", function (e) {
        e.preventDefault();
        document.logoutForm.submit();
    })
    customizeDropdownMenu();
})

function customizeDropdownMenu() {
    // $(".navbar .dropdown").hover(
    //     function (){
    //         $(this).find('.dropdown-menu').first().stop(true, true).delay(250).slideDown();
    //     },
    //     function (){
    //         $(this).find('.dropdown-menu').first().stop(true, true).delay(10).slideUp();
    //     }
    // )
    $(".dropdown > a").click(
        // location.href = this.href;
        function (){
            $(this).find('.dropdown-menu').first().stop(true, true).delay(250).slideDown();
        },
        function (){
            $(this).find('.dropdown-menu').first().stop(true, true).delay(10).slideUp();
        }
    )
}