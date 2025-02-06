import 'ol/ol.css';
import Map from 'ol/Map';
import View from 'ol/View';
import TileLayer from 'ol/layer/Tile';
import OSM from 'ol/source/OSM';

function initMap() {
    new Map({
        target: 'map',  // This should match the div ID in your HTML
        layers: [
            new TileLayer({
                source: new OSM()
            })
        ],
        view: new View({
            center: [0, 0],  // Center of the map in lon/lat
            zoom: 2
        })
    });
}

initMap();
