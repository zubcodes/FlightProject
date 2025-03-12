import './style.css';
import {Map, View} from 'ol';
import TileLayer from 'ol/layer/Tile';
import OSM from 'ol/source/OSM';
import Point from 'ol/geom/Point';
import {fromLonLat} from 'ol/proj';
import Feature from 'ol/Feature.js';
import Polygon from 'ol/geom/Polygon.js';


const heathrowCoords = [-0.454, 51.470];

const map = new Map({
  target: 'map-container',
  layers: [
    new TileLayer({
      source: new OSM()
    })
  ],
  view: new View({
    center: fromLonLat(heathrowCoords),
    zoom: 7
  })



})