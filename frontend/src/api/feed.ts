import { apiClient } from './client';
import type { FeedItem, PageResponse } from './types';

export interface FeedQueryParams {
  page?: number;
  size?: number;
}

export const fetchFeed = async (
  params: FeedQueryParams = {},
): Promise<PageResponse<FeedItem>> => {
  const { data } = await apiClient.get<PageResponse<FeedItem>>(
    '/api/v1/feed',
    { params },
  );
  return data;
};

export const fetchFeedByStock = async (
  stockId: number,
  params: FeedQueryParams = {},
): Promise<PageResponse<FeedItem>> => {
  const { data } = await apiClient.get<PageResponse<FeedItem>>(
    `/api/v1/feed/stock/${stockId}`,
    { params },
  );
  return data;
};

