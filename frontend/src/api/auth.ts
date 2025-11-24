import { apiClient } from './client';
import type { Member, TokenResponse } from './types';

export interface LoginPayload {
  email: string;
  password: string;
}

export interface SignupPayload {
  email: string;
  password: string;
  nickname: string;
}

export const login = async (payload: LoginPayload): Promise<TokenResponse> => {
  const { data } = await apiClient.post<TokenResponse>(
    '/api/v1/members/login',
    payload,
  );
  return data;
};

export const signup = async (payload: SignupPayload): Promise<Member> => {
  const { data } = await apiClient.post<Member>(
    '/api/v1/members/signup',
    payload,
  );
  return data;
};

export const fetchProfile = async (): Promise<Member> => {
  const { data } = await apiClient.get<Member>('/api/v1/members/me');
  return data;
};

