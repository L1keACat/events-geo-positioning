ymaps.ready(init);

function init(){
    var myMap = new ymaps.Map("map", {
        center: [53.902496, 27.561481],
        zoom: 12
    });

    myMap.geoObjects.removeAll();

    var names = document.getElementsByClassName("name");
    var addresses = document.getElementsByClassName("address");
    var coords = document.getElementsByClassName("coord");

    var placemarks = [];

    /*for (var i = 0; i < addresses.length; i++) {
        (function () {
            var name = names[i].innerText;
            ymaps.geocode("Минск, " + addresses[i].innerText, {
                results: 1
            }).then(function (res) {
                var firstGeoObject = res.geoObjects.get(0),
                    coords = firstGeoObject.geometry.getCoordinates();

                var myPlacemark = new ymaps.Placemark(coords, {
                    iconContent: name,
                    balloonContent: 'Содержимое балуна <strong>моей метки</strong>'
                }, {
                    preset: 'islands#violetStretchyIcon'
                });
                myMap.geoObjects.add(myPlacemark);

                objects[i] = myPlacemark;
            })
        })();
    }*/

    for (var i = 0; i < addresses.length; i++) {
        var name = names[i].innerText;
        var coords_text = coords[i].innerText;
        var longitude = coords_text.substring(0, coords_text.indexOf(','));
        var latitude = coords_text.substring(coords_text.indexOf(',') + 1, coords_text.length);
        var myPlacemark = new ymaps.Placemark([longitude,latitude], {
            iconContent: name,
            balloonContent: 'Содержимое балуна <strong>моей метки</strong>'
        }, {
            preset: 'islands#violetStretchyIcon'
        });
        //myMap.geoObjects.add(myPlacemark);

        placemarks[i] = myPlacemark;
    }

    var objects = ymaps.geoQuery(placemarks);

    //myMap.geoObjects.removeAll();

    objects.searchInside(myMap)
    // И затем добавим найденные объекты на карту.
        .addToMap(myMap);

    myMap.events.add('boundschange', function () {
        var visibleObjects = objects.searchInside(myMap).addToMap(myMap);
        // Оставшиеся объекты будем удалять с карты.
        objects.remove(visibleObjects).removeFromMap(myMap);
    });
}