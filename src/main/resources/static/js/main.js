ymaps.ready(init);

function init(){
    var myMap = new ymaps.Map("map", {
        center: [53.902496, 27.561481],
        zoom: 12
    });

    myMap.geoObjects.removeAll();

    var events = document.getElementsByClassName("event");
    var names = document.getElementsByClassName("name");
    var addresses = document.getElementsByClassName("address");
    var coords = document.getElementsByClassName("coord");

    var placemarks = [];

    for (var i = 0; i < addresses.length; i++) {
        var name = names[i].innerText;
        var address = addresses[i].innerText;
        var coords_text = coords[i].innerText;
        var longitude = coords_text.substring(0, coords_text.indexOf(','));
        var latitude = coords_text.substring(coords_text.indexOf(',') + 1, coords_text.length);
        var myPlacemark = new ymaps.Placemark([longitude,latitude], {
            iconContent: name,
            balloonContent: address + '\n' + coords_text
        }, {
            preset: 'islands#violetStretchyIcon'
        });

        placemarks[i] = myPlacemark;
    }

    var objects = ymaps.geoQuery(placemarks);

    objects.searchInside(myMap)
        .addToMap(myMap);

    myMap.events.add('boundschange', function () {
        var visibleObjects = objects.searchInside(myMap).addToMap(myMap);
        objects.remove(visibleObjects).removeFromMap(myMap);
    });

    for (var i = 0; i < events.length; i++) {
        (function(){
        var coords_text2 = coords[i].innerText;
        var longitude2 = coords_text2.substring(0, coords_text2.indexOf(','));
        var latitude2 = coords_text2.substring(coords_text2.indexOf(',') + 1, coords_text2.length);
        events[i].onclick = function () {
            ymaps.geocode([longitude2,latitude2], {
                results: 1
            }).then(function (res) {
                var firstGeoObject = res.geoObjects.get(0),
                    bounds = firstGeoObject.properties.get('boundedBy');
                myMap.setBounds(bounds, {
                    // Проверяем наличие тайлов на данном масштабе.
                    checkZoomRange: true
                });
            });
        };
        })();
    }
}