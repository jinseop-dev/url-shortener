if (!__ENV.BASE_URL) {
  throw new Error("BASE_URL is required");
}

export const BASE_URL = __ENV.BASE_URL;
