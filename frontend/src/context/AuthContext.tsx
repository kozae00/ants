import {
  createContext,
  useCallback,
  useContext,
  useEffect,
  useMemo,
  useState,
  type ReactNode,
} from 'react';
import {
  login as loginRequest,
  signup as signupRequest,
  fetchProfile,
  type LoginPayload,
  type SignupPayload,
} from '../api/auth';
import type { Member } from '../api/types';

interface AuthContextValue {
  token: string | null;
  member: Member | null;
  isBootstrapping: boolean;
  login: (payload: LoginPayload) => Promise<void>;
  signup: (payload: SignupPayload) => Promise<Member>;
  logout: () => void;
  refreshProfile: () => Promise<void>;
}

const AuthContext = createContext<AuthContextValue | undefined>(undefined);

export const AuthProvider = ({ children }: { children: ReactNode }) => {
  const [token, setToken] = useState<string | null>(() =>
    window.localStorage.getItem('ants_token'),
  );
  const [member, setMember] = useState<Member | null>(null);
  const [isBootstrapping, setIsBootstrapping] = useState(true);

  const restoreProfile = useCallback(async () => {
    const storedToken = window.localStorage.getItem('ants_token');

    if (!storedToken) {
      setToken(null);
      setMember(null);
      return;
    }

    try {
      const profile = await fetchProfile();
      setToken(storedToken);
      setMember(profile);
    } catch (error) {
      console.error('프로필 정보를 불러오지 못했습니다.', error);
      window.localStorage.removeItem('ants_token');
      setToken(null);
      setMember(null);
    }
  }, []);

  useEffect(() => {
    const init = async () => {
      await restoreProfile();
      setIsBootstrapping(false);
    };
    void init();
  }, [restoreProfile]);

  const login = useCallback(async (payload: LoginPayload) => {
    const { token: nextToken } = await loginRequest(payload);
    window.localStorage.setItem('ants_token', nextToken);
    setToken(nextToken);
    await restoreProfile();
  }, [restoreProfile]);

  const signup = useCallback(async (payload: SignupPayload) => {
    const result = await signupRequest(payload);
    return result;
  }, []);

  const logout = useCallback(() => {
    window.localStorage.removeItem('ants_token');
    setToken(null);
    setMember(null);
  }, []);

  const refreshProfile = useCallback(async () => {
    await restoreProfile();
  }, [restoreProfile]);

  const value = useMemo(
    () => ({
      token,
      member,
      isBootstrapping,
      login,
      signup,
      logout,
      refreshProfile,
    }),
    [token, member, isBootstrapping, login, signup, logout, refreshProfile],
  );

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth는 AuthProvider 하위에서만 사용할 수 있습니다.');
  }
  return context;
};

