import type { Member } from '../../api/types';
import { Card } from '../common/Card';
import { formatDateTime } from '../../lib/date';

interface ProfileCardProps {
  member: Member | null;
}

export const ProfileCard = ({ member }: ProfileCardProps) => {
  return (
    <Card title="내 정보">
      {member ? (
        <dl className="profile">
          <div>
            <dt>닉네임</dt>
            <dd>{member.nickname}</dd>
          </div>
          <div>
            <dt>이메일</dt>
            <dd>{member.email}</dd>
          </div>
          <div>
            <dt>가입일</dt>
            <dd>{formatDateTime(member.createdAt)}</dd>
          </div>
        </dl>
      ) : (
        <p>로그인 정보를 확인할 수 없습니다.</p>
      )}
    </Card>
  );
};

