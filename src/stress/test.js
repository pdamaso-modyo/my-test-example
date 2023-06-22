/*
In order to execute the test you need to install k6
https://k6.io/docs/get-started/installation/

Then just run:

k6 run -e TEST_TYPE=xxxxx test.js

where xxxxx is one of the test types defined in config/testoptions.js
- smoke
- fixedVUS
- performance
- stress

*/

import {Counter} from 'k6/metrics';
import http from 'k6/http';
import {check} from 'k6';
import {configs} from "./config/testoptions.js";

const config = configs;
const testType = __ENV.TEST_TYPE === undefined ? "smoke" : __ENV.TEST_TYPE.toLowerCase();

const params = {
  headers: {
    'Content-Type': 'application/json'
  }
};

const successCounter = new Counter('checks_call_success');
const allErrors = new Counter('checks_call_errors');

export const options = config[testType].options;

export function setup() {
    console.log("Running config: " + testType);
    console.log("Starting at "+ (new Date()));
}

export function teardown(data) {
console.log("Ending at "+ (new Date()));
}

export default function () {
  let response = http.get('http://localhost:8080/greeting', params);
  doCheck(response);
}

function doCheck(response) {
  if (response.status === 200) {
    check(response, {
      'verify expected response': () => {
        return true;
      }
    });
    successCounter.add(1);
  } else {
    allErrors.add(1);
  }
}
