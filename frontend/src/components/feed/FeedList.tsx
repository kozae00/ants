import type { FeedItem, PageResponse } from '../../api/types';
import { Card } from '../common/Card';
import { EmptyState } from '../common/EmptyState';
import { formatDateTime } from '../../lib/date';

interface FeedListProps {
  feed?: PageResponse<FeedItem>;
  isLoading: boolean;
  onReload?: () => void;
}

const typeLabelMap: Record<FeedItem['type'], string> = {
  NEWS: '뉴스',
  POST: '게시글',
};

export const FeedList = ({ feed, isLoading, onReload }: FeedListProps) => {
  return (
    <Card
      title="실시간 피드"
      actions={
        <button className="text-button" onClick={onReload} disabled={isLoading}>
          새로고침
        </button>
      }
    >
      {isLoading && <p>피드를 불러오는 중입니다...</p>}
      {!isLoading && feed?.content.length === 0 && (
        <EmptyState title="표시할 피드가 없습니다." />
      )}
      <ul className="feed-list">
        {feed?.content.map((item) => (
          <li key={`${item.type}-${item.id}`} className="feed-item">
            <div className="feed-item__meta">
              <span className={`badge badge-${item.type.toLowerCase()}`}>
                {typeLabelMap[item.type]}
              </span>
              <span className="feed-item__stock">
                {item.stockName} ({item.stockCode})
              </span>
              <span className="feed-item__date">
                {formatDateTime(item.createdAt)}
              </span>
            </div>
            <h4>{item.title}</h4>
            {item.content && <p className="feed-item__content">{item.content}</p>}
            {item.url && (
              <a
                className="text-link"
                href={item.url}
                target="_blank"
                rel="noreferrer"
              >
                원문 보기 ↗
              </a>
            )}
            {item.memberNickname && (
              <small className="feed-item__author">
                작성자: {item.memberNickname}
              </small>
            )}
          </li>
        ))}
      </ul>
    </Card>
  );
};

