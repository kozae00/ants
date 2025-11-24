import type { PageResponse, PostResponse } from '../../api/types';
import { Card } from '../common/Card';
import { EmptyState } from '../common/EmptyState';
import { formatDateTime } from '../../lib/date';

interface PostListProps {
  posts?: PageResponse<PostResponse>;
  isLoading: boolean;
  onPageChange: (page: number) => void;
}

export const PostList = ({
  posts,
  isLoading,
  onPageChange,
}: PostListProps) => {
  const page = posts?.number ?? 0;

  return (
    <Card
      title="게시글"
      actions={
        <div className="pagination">
          <button
            type="button"
            onClick={() => onPageChange(Math.max(page - 1, 0))}
            disabled={!posts || page === 0 || isLoading}
          >
            이전
          </button>
          <span>
            {page + 1} / {posts?.totalPages ?? 1}
          </span>
          <button
            type="button"
            onClick={() => onPageChange(page + 1)}
            disabled={!posts || posts.last || isLoading}
          >
            다음
          </button>
        </div>
      }
    >
      {isLoading && <p>게시글을 불러오는 중입니다...</p>}
      {!isLoading && posts?.content.length === 0 && (
        <EmptyState title="게시글이 없습니다." />
      )}
      <ul className="post-list">
        {posts?.content.map((post) => (
          <li key={post.id} className="post-item">
            <div className="post-item__meta">
              <span className="badge">{post.stockName}</span>
              <span>{post.memberNickname}</span>
              <span>{formatDateTime(post.createdAt)}</span>
            </div>
            <h4>{post.title}</h4>
            <p>{post.content}</p>
            <small>조회수 {post.viewCount}</small>
          </li>
        ))}
      </ul>
    </Card>
  );
};

