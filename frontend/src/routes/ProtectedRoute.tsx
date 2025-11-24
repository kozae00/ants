import { Navigate, Outlet } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

interface ProtectedRouteProps {
  redirectPath?: string;
}

export const ProtectedRoute = ({
  redirectPath = '/login',
}: ProtectedRouteProps) => {
  const { token, isBootstrapping } = useAuth();

  if (isBootstrapping) {
    return <p className="page-message">세션을 확인하는 중입니다...</p>;
  }

  if (!token) {
    return <Navigate to={redirectPath} replace />;
  }

  return <Outlet />;
};

