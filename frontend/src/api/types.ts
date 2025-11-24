export interface TokenResponse {
  token: string;
  email: string;
}

export interface Member {
  id: number;
  email: string;
  nickname: string;
  createdAt: string;
}

export interface PostResponse {
  id: number;
  stockId: number;
  stockCode: string;
  stockName: string;
  memberId: number;
  memberNickname: string;
  title: string;
  content: string;
  viewCount: number;
  createdAt: string;
  updatedAt: string;
}

export interface FeedItem {
  type: 'NEWS' | 'POST';
  id: number;
  stockId: number;
  stockCode: string;
  stockName: string;
  memberId: number | null;
  memberNickname: string | null;
  title: string;
  content: string;
  url: string | null;
  imageUrl: string | null;
  viewCount: number | null;
  createdAt: string;
  updatedAt: string;
}

export interface StockResponse {
  id: number;
  code: string;
  name: string;
  createdAt: string;
}

export interface PageResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
  first: boolean;
  last: boolean;
  empty: boolean;
}

