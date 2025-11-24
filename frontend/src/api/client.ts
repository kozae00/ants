import axios from 'axios';

const normalizedBaseUrl =
  import.meta.env.VITE_API_BASE_URL?.replace(/\/$/, '') ?? '';

export const apiClient = axios.create({
  baseURL: normalizedBaseUrl,
  headers: {
    'Content-Type': 'application/json',
  },
});

apiClient.interceptors.request.use((config) => {
  const token = window.localStorage.getItem('ants_token');
  if (token) {
    config.headers = config.headers ?? {};
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

apiClient.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      window.localStorage.removeItem('ants_token');
    }
    return Promise.reject(error);
  },
);

