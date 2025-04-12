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

// https://github.com/planemad/lookup-csv
const lookupCSV = require('lookup-csv');

// Create a lookup table using lookup column name to use from the csv data
const lookupTable = lookupCSV('./airports.csv', 'ident')

// Function to fetch flight data 
async function fetchFlightData() {
  var depIcao = document.getElementById('departure_icao').innerText;
  var arrIcao = document.getElementById('arrival_icao').innerText;
  // Get rows matching lookup value
  matchingRowsDepAirport = lookupTable.get(depIcao);
  matchingRowsArrAirport = lookupTable.get(arrIcao);
  document.getElementById("dep_air_data").innerHTML = matchingRowsDepAirport;
  document.getElementById("arr_air_data").innerHTML = matchingRowsArrAirport;
}

// Function to draw flight path on the map
function drawFlightPath(flightData) {
    const departureCoords = fromLonLat([flightData.departure.lon, flightData.departure.lat]);
    const arrivalCoords = fromLonLat([flightData.arrival.lon, flightData.arrival.lat]);

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

// Fetch data and draw the flight path
fetchFlightData();

