import { useState } from 'react';
import type { FormEvent } from 'react';
import { useAuth } from '../../context/AuthContext';

interface SignupFormProps {
  onSuccess?: () => void;
}

export const SignupForm = ({ onSuccess }: SignupFormProps) => {
  const { signup, login } = useAuth();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [nickname, setNickname] = useState('');
  const [error, setError] = useState<string | null>(null);
  const [isSubmitting, setIsSubmitting] = useState(false);

  const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setError(null);
    setIsSubmitting(true);
    try {
      await signup({ email, password, nickname });
      await login({ email, password });
      onSuccess?.();
    } catch (err) {
      console.error(err);
      setError('회원가입에 실패했습니다. 입력값을 다시 확인해주세요.');
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <form className="form auth-form" onSubmit={handleSubmit}>
      <label>
        <span>닉네임</span>
        <input
          className="input"
          type="text"
          value={nickname}
          onChange={(event) => setNickname(event.target.value)}
          required
        />
      </label>
      <label>
        <span>이메일</span>
        <input
          className="input"
          type="email"
          value={email}
          onChange={(event) => setEmail(event.target.value)}
          required
        />
      </label>
      <label>
        <span>비밀번호</span>
        <input
          className="input"
          type="password"
          value={password}
          onChange={(event) => setPassword(event.target.value)}
          minLength={8}
          required
        />
      </label>
      {error && <p className="form__error">{error}</p>}
      <button type="submit" disabled={isSubmitting}>
        {isSubmitting ? '가입 중...' : '회원가입'}
      </button>
    </form>
  );
};

