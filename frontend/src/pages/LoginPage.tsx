import { useState } from 'react';
import { Navigate, useNavigate } from 'react-router-dom';
import { LoginForm } from '../components/auth/LoginForm';
import { SignupForm } from '../components/auth/SignupForm';
import { useAuth } from '../context/AuthContext';

type AuthMode = 'login' | 'signup';

export const LoginPage = () => {
  const { token } = useAuth();
  const navigate = useNavigate();
  const [mode, setMode] = useState<AuthMode>('login');

  if (token) {
    return <Navigate to="/" replace />;
  }

  return (
    <main className="auth-page">
      <section className="auth-panel">
        <h1>ANTS</h1>
        <p>백엔드 API 기반 MVP</p>
        <div className="auth-tabs">
          <button
            type="button"
            className={mode === 'login' ? 'is-active' : ''}
            onClick={() => setMode('login')}
          >
            로그인
          </button>
          <button
            type="button"
            className={mode === 'signup' ? 'is-active' : ''}
            onClick={() => setMode('signup')}
          >
            회원가입
          </button>
        </div>
        {mode === 'login' ? (
          <LoginForm onSuccess={() => navigate('/')} />
        ) : (
          <SignupForm onSuccess={() => setMode('login')} />
        )}
      </section>
    </main>
  );
};

