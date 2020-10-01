jQuery(function($){
    app.initialize();

    $("#lancer").click(function() {
        if($.trim($("#destination").val()) === '') {
            var valid = true;
            var message = '';

            if($.trim($("#latitude").val()) === '' || !is_numeric($.trim($("#latitude").val()))) {
                valid = false;
                message+= "You must enter a latitude";
            }

            if($.trim($("#longitude").val()) === '' || !is_numeric($.trim($("#longitude").val()))) {
                valid = false;

                if(message === '') {
                    message = "You must enter a longitude";
                } else {
                    message+= " and a longitude or a destination";
                }
            }

            if(valid) {
                localStorage.setItem("destination", false);
                localStorage.setItem("latitude", $.trim($("#latitude").val()));
                localStorage.setItem("longitude", $.trim($("#longitude").val()));

                var dialog = document.querySelector('#attention');
                dialog.toggle();
            } else {
                error(message);
            }
        } else {
            localStorage.setItem("destination", $.trim($("#destination").val()));
            localStorage.setItem("latitude", false);
            localStorage.setItem("longitude", false);

            var dialog = document.querySelector('#attention');
            dialog.toggle();
        }
    });

    $("#cancel").click(function() {
        var dialog = document.querySelector('#attention');
        dialog.toggle();
    });

    $("#launch").click(function() {
        var dialog = document.querySelector('#attention');
        dialog.toggle();

        var loading = document.querySelector('#loading');
        loading.toggle();

        var audio = new Audio('sounds/vortex.ogg');
        audio.volume = 1;
        audio.play();

        var progress = document.querySelector('paper-progress');
        progress.value = 0;
        var interval = setInterval(function() {
            if(progress.value < 100) {
                progress.value = progress.value + 1;
                $("#loading").find('.current span').text(progress.value);
            } else {
                clearInterval(interval);
                loading.toggle();
                $("#index").fadeOut(400, function() {
                    teleporation();
                    $("#prev, #navicon").fadeIn(400);
                    $("#maps").fadeIn(400);
                });
            }
        }, 24);
    });
});

function error(text) {
    $("#error").html(text).slideDown(250);

    setTimeout(function() {
        $("#error").slideUp(250);
    }, 3000);
}

function is_numeric(mixed_var) {
    var whitespace =
        " \n\r\t\f\x0b\xa0\u2000\u2001\u2002\u2003\u2004\u2005\u2006\u2007\u2008\u2009\u200a\u200b\u2028\u2029\u3000";
    return (typeof mixed_var === 'number' || (typeof mixed_var === 'string' && whitespace.indexOf(mixed_var.slice(-1)) === -
        1)) && mixed_var !== '' && !isNaN(mixed_var);
}