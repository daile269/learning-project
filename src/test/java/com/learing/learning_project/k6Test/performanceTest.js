import http from "k6/http";
import { check } from "k6";

export let options = {
  vus: 10,
  duration: "20s",
  ext: {
    loadimpact: {
      projectID: 1234567,
      name: "MyTest"
    },
    influxDB: {
        url: 'http://localhost:8086',
      database: 'k6',
      tags: {
        project: 'user-service'
      },
    },
  },
};

export default function () {
  let url = "http://localhost:8013/api/users/1";
  let response = http.get(url);

  check(response, {
    "is status 200": (r) => r.status === 200,
    "response time is less than 500ms": (r) => r.timings.duration < 500,
  });

  let influxDbUrl = 'http://localhost:8086/write?db=k6';
  let payload = `http_req_duration,project=user-service value=${response.timings.duration}`;


  http.post(influxDbUrl, payload);

  // let postResponse = http.post('http://localhost:8013/api/users', JSON.stringify({
  //   username: "newuser",
  //   password: "12345678",
  // }), { headers: { 'Content-Type': 'application/json' } });
  // check(postResponse, {
  //   'POST status is 200': (r) => r.status === 200,
  // });
  //


  // Kiểm tra DELETE
  let deleteResponse = http.del('http://localhost:8013/api/users/5');
  check(deleteResponse, {
    'DELETE status is 200': (r) => r.status === 200,
  });
   // PUT
  let putResponse = http.put('http://localhost:8013/api/users/4', JSON.stringify({
    "username":"daile2s69",
    "password":"daile2s69",
    "email": "daile26ss92003@gmail.com"
  }), { headers: { 'Content-Type': 'application/json' } });
  check(putResponse, {
    'PUT status is 200': (r) => r.status === 200,
  });
}
