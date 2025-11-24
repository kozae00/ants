import { useAuth } from '../../context/AuthContext';

export const AppHeader = () => {
  const { member, logout } = useAuth();

  return (
    <header className="app-header">
      <div>
        <p className="app-header__title">ANTS Dashboard</p>
        <small>백엔드 API 검증용 미니 UI</small>
      </div>
      <div className="app-header__actions">
        {member && (
          <span className="app-header__user">
            {member.nickname} ({member.email})
          </span>
        )}
        <button type="button" onClick={logout}>
          로그아웃
        </button>
      </div>
    </header>
  );
};

