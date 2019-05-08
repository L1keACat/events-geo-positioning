ymaps.ready(init);

function init() {
    var myPlacemark,
        myMap = new ymaps.Map('map', {
            center: [53.902496, 27.561481],
            zoom: 12
        });

    var address_form = document.getElementById("address");
    var coord_form = document.getElementById("coord");

    if (address_form.value.length !== 0) {
        var coords_text = coord_form.value;
        var longitude = coords_text.substring(0, coords_text.indexOf(','));
        var latitude = coords_text.substring(coords_text.indexOf(',') + 1, coords_text.length);

        mainFunction([longitude,latitude]);
    }

    document.getElementById("address").onchange = function () {
        var address = "Минск, " + document.getElementById("address").value;
        mainFunction(address);
    };

    function mainFunction(coord) {

        ymaps.geocode(coord, {
            results: 1
        }).then(function (res) {
            var firstGeoObject = res.geoObjects.get(0),
                coords,
                bounds = firstGeoObject.properties.get('boundedBy');

            if (coord === "Минск, " + address_form.value) {
                coords = firstGeoObject.geometry.getCoordinates();
            }
            else {
                coords = coord;
            }

            if (myPlacemark) {
                myPlacemark.geometry.setCoordinates(coords);
            }
            // Если нет – создаем.
            else {
                myPlacemark = createPlacemark(coords);
                myMap.geoObjects.add(myPlacemark);
                myMap.setBounds(bounds, {
                    // Проверяем наличие тайлов на данном масштабе.
                    checkZoomRange: true
                });
                // Слушаем событие окончания перетаскивания на метке.
                myPlacemark.events.add('dragend', function () {
                    getAddress(myPlacemark.geometry.getCoordinates());
                });
            }
            getAddress(coords);
        });
    }

    myMap.events.add('click', function (e) {
        var coords = e.get('coords');

        // Если метка уже создана – просто передвигаем ее.
        if (myPlacemark) {
            myPlacemark.geometry.setCoordinates(coords);
        }
        // Если нет – создаем.
        else {
            myPlacemark = createPlacemark(coords);
            myMap.geoObjects.add(myPlacemark);
            // Слушаем событие окончания перетаскивания на метке.
            myPlacemark.events.add('dragend', function () {
                getAddress(myPlacemark.geometry.getCoordinates());
            });
        }
        getAddress(coords);
    });

    // Создание метки.
    function createPlacemark(coords) {
        return new ymaps.Placemark(coords, {
            iconCaption: 'поиск...'
        }, {
            preset: 'islands#violetDotIconWithCaption',
            draggable: true
        });
    }

    // Определяем адрес по координатам (обратное геокодирование).
    function getAddress(coords) {
        myPlacemark.properties.set('iconCaption', 'поиск...');
        ymaps.geocode(coords).then(function (res) {
            var firstGeoObject = res.geoObjects.get(0);

            myPlacemark.properties
                .set({
                    // Формируем строку с данными об объекте.
                    iconCaption: [
                        // Название населенного пункта или вышестоящее административно-территориальное образование.
                        firstGeoObject.getLocalities().length ? firstGeoObject.getLocalities() : firstGeoObject.getAdministrativeAreas(),
                        // Получаем путь до топонима, если метод вернул null, запрашиваем наименование здания.
                        firstGeoObject.getThoroughfare() || firstGeoObject.getPremise()
                    ].filter(Boolean).join(', '),
                    // В качестве контента балуна задаем строку с адресом объекта.
                    balloonContent: firstGeoObject.getAddressLine()
                });
            address_form.value = firstGeoObject.getAddressLine().replace('Беларусь, Минск, ','');
            coord_form.value = firstGeoObject.geometry.getCoordinates();
        });
    }
}

