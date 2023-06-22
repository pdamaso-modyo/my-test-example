export const configs = {
    "smoke": {
      options: {
        vus: 1,
        thresholds: {
          checks_call_success: ['count>0'],
          checks_call_errors: ['count==0'],
          http_req_duration: [
            "p(99)<500"
          ]
        }
      }
    },
    "fixedVUS": {
      options: {
        vus: 64,
        thresholds: {
          checks_call_success: ['count>0'],
          checks_call_errors: ['count==0'],
          http_req_duration: [
            "p(99)<500"
          ]
        }
      }
    },
    "performance": {
      options: {
        stages: [
          {duration: "30s", target: 128},
          {duration: "3m", target: 128},
          {duration: "30s", target: 0}
        ],
        thresholds: {
          http_req_duration: [
            "p(95)<1000", "p(99)<1500"
          ],
          checks_call_success: ['count>0'],
          checks_call_errors: ['count==0']
        }
      }
    },
    "stress": {
      options:
          {
            executor: "ramping-arrival-rate",
            preAllocatedVUs: 500,
            timeUnit: "1s",
            stages: [
              {duration: "1m", target: 50},
              {duration: "3m", target: 50},
              {duration: "1m", target: 100},
              {duration: "3m", target: 100},
              {duration: "1m", target: 125},
              {duration: "3m", target: 125},
              {duration: "1m", target: 150},
              {duration: "3m", target: 150},
              {duration: "10m", target: 0},
            ],
            thresholds: {
              checks_call_success: ['count>0'],
              checks_call_errors: ['count==0'],
              http_req_duration: [
                "p(95)<1000",
                "p(99)<1500"
              ]
            }
          }
    }
  }
  