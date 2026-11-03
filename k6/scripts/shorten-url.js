import http from "k6/http";
import { check } from "k6";
import { BASE_URL } from "../config.js";

const params = {
  headers: {
    "Content-Type": "application/json",
  },
};

export const options = {
  scenarios: {
    shorten_url: {
      executor: "ramping-arrival-rate",
      startRate: 0,
      timeUnit: "1s",
      preAllocatedVUs: 30,
      maxVUs: 50,
      stages: [
        { duration: "5m", target: 60 },
        { duration: "20m", target: 60 },
        { duration: "5m", target: 0 },
      ],
    },
  },
  thresholds: {
    http_req_duration: ["p(95)<100", "p(99)<200"],
    http_req_failed: ["rate<0.001"],
  },
  summaryTrendStats: ["avg", "min", "med", "max", "p(90)", "p(95)", "p(99)"],
};

export default function () {
  const url = `${BASE_URL}/api/urls`;
  const originalUrl = `https://example.com/${__VU}_${__ITER}_${Date.now()}`;
  const payload = JSON.stringify({ originalUrl });

  const res = http.post(url, payload, params);

  check(res, {
    "status is 201": (r) => r.status === 201,
  });
}
