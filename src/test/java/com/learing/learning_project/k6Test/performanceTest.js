import http from "k6/http";
import { check } from "k6";

export let options = {
  vus: 10,
  duration: "30s",
  ext: {
    loadimpact: {
      projectID: 1234567,
      name: "MyTest"
    },
    influxDB: {
      url: 'http://localhost:8086',
      database: 'k6',
      tags: {
        project: 'my-project'
      },
    },
  },
};

export default function () {
  let url = "http://localhost:8011/api/posts/1";
  let response = http.get(url);

  check(response, {
    "is status 200": (r) => r.status === 200,
    "response time is less than 500ms": (r) => r.timings.duration < 500,
  });
  // let postResponse = http.post('http://localhost:8011/api/posts', JSON.stringify({
  //   title: "New Post",
  //   content: "This is a new post created for testing.",
  //   authorId: 1,
  // }), { headers: { 'Content-Type': 'application/json' } });
  // check(postResponse, {
  //   'POST status is 200': (r) => r.status === 200,
  // });
  //
  // // Kiểm tra PUT
  // let putResponse = http.put('http://localhost:8011/api/posts/1', JSON.stringify({
  //   title: "Updated Post",
  //   content: "This post has been updated for testing.",
  //   authorId: 1,
  // }), { headers: { 'Content-Type': 'application/json' } });
  // check(putResponse, {
  //   'PUT status is 200': (r) => r.status === 200,
  // });

  // // Kiểm tra DELETE
  // let deleteResponse = http.del('http://localhost:8011/api/posts/6992');
  // check(deleteResponse, {
  //   'DELETE status is 200': (r) => r.status === 200,
  // });
}
