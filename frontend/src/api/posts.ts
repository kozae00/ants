import { apiClient } from './client';
import type { PageResponse, PostResponse } from './types';

export interface PostQueryParams {
  page?: number;
  size?: number;
}

export interface CreatePostPayload {
  stockId: number;
  title: string;
  content: string;
}

export const fetchPosts = async (
  params: PostQueryParams = {},
): Promise<PageResponse<PostResponse>> => {
  const { data } = await apiClient.get<PageResponse<PostResponse>>(
    '/api/v1/posts',
    { params },
  );
  return data;
};

export const fetchPostsByStock = async (
  stockId: number,
  params: PostQueryParams = {},
): Promise<PageResponse<PostResponse>> => {
  const { data } = await apiClient.get<PageResponse<PostResponse>>(
    `/api/v1/posts/stock/${stockId}`,
    { params },
  );
  return data;
};

export const createPost = async (
  payload: CreatePostPayload,
): Promise<PostResponse> => {
  const { data } = await apiClient.post<PostResponse>(
    '/api/v1/posts',
    payload,
  );
  return data;
};

