import './style.css';
import { Map, View } from 'ol';
import TileLayer from 'ol/layer/Tile';
import OSM from 'ol/source/OSM';
import { fromLonLat } from 'ol/proj';
import Feature from 'ol/Feature';
import VectorLayer from 'ol/layer/Vector';
import VectorSource from 'ol/source/Vector';
import { LineString } from 'ol/geom';
import Stroke from 'ol/style/Stroke';
import Style from 'ol/style/Style';

// Get flight IATA & airline from URL parameters
const urlParams = new URLSearchParams(window.location.search);
const iata = urlParams.get('iata');
const airline = urlParams.get('airline');

window.addEventListener('DOMContentLoaded', async () => {
    const depIcao = document.getElementById('departure_icao').innerText.trim();
    const arrIcao = document.getElementById('arrival_icao').innerText.trim();
  
    Papa.parse('/static/airports.csv', {
    download: true,
      header: true,
      complete: function(results) {
        const rows = results.data;
  
        const depRow = rows.find(row => row.ident === depIcao);
        const arrRow = rows.find(row => row.ident === arrIcao);
  
        document.getElementById("dep_air_data").innerText = depRow ? `${depRow.name}, lat: ${depRow.latitude_deg}, lon: ${depRow.longitude_deg}`
          : "Departure airport not found";
  
        document.getElementById("arr_air_data").innerText = arrRow ? `${arrRow.name}, lat: ${arrRow.latitude_deg}, lon: ${arrRow.longitude_deg}`
          : "Arrival airport not found";
      }
    });
  });
  

// Function to draw flight path on the map
function drawFlightPath(flightData) {
    const departureCoords = fromLonLat([depRow.latitude_deg, depRow.longitude_deg]);
    const arrivalCoords = fromLonLat([arrRow.latitude_deg, arrRow.longitude_deg]);

    // Function to create a curved arc
    function createArc(coords1, coords2) {
        const midPoint = [
            (coords1[0] + coords2[0]) / 2,
            (coords1[1] + coords2[1]) / 2 + 0.5
        ];
        return new LineString([coords1, midPoint, coords2]);
    }

    // Create and style the flight path
    const flightArc = createArc(departureCoords, arrivalCoords);
    const flightFeature = new Feature(flightArc);
    flightFeature.setStyle(new Style({
        stroke: new Stroke({
            color: 'red',
            width: 2,
            lineDash: [5, 5]
        })
    }));

    // Vector layer for the flight arc
    const flightLayer = new VectorLayer({
        source: new VectorSource({
            features: [flightFeature]
        })
    });

    // Initialize map with flight path
    const Map = new Map({
        target: 'map-container',
        layers: [
            new TileLayer({ source: new OSM() }),
            flightLayer
        ],
        view: new View({
            center: departureCoords,
            zoom: 3
        })
    });
}

