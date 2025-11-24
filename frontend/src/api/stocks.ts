import { apiClient } from './client';
import type { StockResponse } from './types';

export const fetchStocks = async (): Promise<StockResponse[]> => {
  const { data } = await apiClient.get<StockResponse[]>('/api/v1/stocks');
  return data;
};

export const searchStocks = async (keyword: string): Promise<StockResponse[]> => {
  const { data } = await apiClient.get<StockResponse[]>(
    '/api/v1/stocks/search',
    { params: { keyword } },
  );
  return data;
};

