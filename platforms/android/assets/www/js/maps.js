jQuery(function($){
    $("#prev").click(function() {
        $("#maps").fadeOut(400, function() {
            $("#prev, #navicon").fadeOut(400);
            $("#index").fadeIn(400);
        });
    });
});

function teleporation() {
    var map = null;
    var marker = null;

    if(localStorage.destination) {
        var geocoder = new google.maps.Geocoder();

        geocoder.geocode({'address': localStorage.destination}, function(results, status) {
            localStorage.removeItem("destination");
            localStorage.removeItem("latitude");
            localStorage.removeItem("longitude");

            if(status == google.maps.GeocoderStatus.OK) {
                map = new google.maps.Map(document.getElementById('map'), {
                    zoom: 17,
                    center: results[0].geometry.location,
                    mapTypeId: google.maps.MapTypeId.ROADMAP,
                    disableDefaultUI: true,
                    zoomControl: false
                });

                marker = new google.maps.Marker({
                    position: results[0].geometry.location,
                    map: map,
                    animation: google.maps.Animation.DROP
                });
            } else {
                var dialog = document.querySelector('#erreur');
                dialog.toggle();

                $("#return").click(function() {
                    var dialog = document.querySelector('#erreur');
                    dialog.toggle();

                    $("#maps").fadeOut(400, function() {
                        $("#prev, #navicon").fadeOut(400);
                        $("#index").fadeIn(400);
                    });
                });
            }
        });
    } else if(localStorage.latitude && localStorage.longitude) {
        var latlng = new google.maps.LatLng(localStorage.latitude, localStorage.longitude);

        localStorage.removeItem("destination");
        localStorage.removeItem("latitude");
        localStorage.removeItem("longitude");

        map = new google.maps.Map(document.getElementById('map'), {
            zoom: 17,
            center: latlng,
            mapTypeId: google.maps.MapTypeId.ROADMAP,
            disableDefaultUI: true,
            zoomControl: false
        });

        marker = new google.maps.Marker({
            position: latlng,
            map: map,
            animation: google.maps.Animation.DROP
        });
    } else {
        var dialog = document.querySelector('#erreur');
        dialog.toggle();

        $("#return").click(function() {
            var dialog = document.querySelector('#erreur');
            dialog.toggle();

            $("#maps").fadeOut(400, function() {
                $("#prev, #navicon").fadeOut(400);
                $("#index").fadeIn(400);
            });
        });
    }
}