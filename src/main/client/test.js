const http = require('k6/http');
const { sleep } = require('k6');

export let options = {
  vus: 10, // Virtual Users
  duration: '30s', // Test duration
};

export default function () {
  http.get('http://localhost:8080/search?airline=british+airways');
  sleep(1);
}
