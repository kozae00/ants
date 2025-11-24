import { useEffect, useState } from 'react';
import type { FormEvent } from 'react';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import type { StockResponse } from '../../api/types';
import { Card } from '../common/Card';
import { createPost } from '../../api/posts';

interface PostComposerProps {
  stocks?: StockResponse[];
  defaultStockId: number | null;
  onSuccess?: () => void;
}

export const PostComposer = ({
  stocks = [],
  defaultStockId,
  onSuccess,
}: PostComposerProps) => {
  const queryClient = useQueryClient();
  const [stockId, setStockId] = useState<number | ''>(defaultStockId ?? '');
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [errorMessage, setErrorMessage] = useState<string | null>(null);

  useEffect(() => {
    setStockId(defaultStockId ?? '');
  }, [defaultStockId]);

  const mutation = useMutation({
    mutationFn: createPost,
    onSuccess: () => {
      setTitle('');
      setContent('');
      setErrorMessage(null);
      void queryClient.invalidateQueries({ queryKey: ['posts'] });
      void queryClient.invalidateQueries({ queryKey: ['feed'] });
      onSuccess?.();
    },
    onError: () => {
      setErrorMessage('게시글 등록에 실패했습니다. 잠시 후 다시 시도해 주세요.');
    },
  });

  const handleSubmit = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    if (!stockId) {
      setErrorMessage('종목을 선택해 주세요.');
      return;
    }
    mutation.mutate({
      stockId: Number(stockId),
      title: title.trim(),
      content: content.trim(),
    });
  };

  return (
    <Card title="새 게시글 작성">
      <form className="form" onSubmit={handleSubmit}>
        <label>
          <span>종목</span>
          <select
            className="input"
            value={stockId}
            onChange={(event) =>
              setStockId(event.target.value ? Number(event.target.value) : '')
            }
          >
            <option value="">종목을 선택하세요</option>
            {stocks.map((stock) => (
              <option key={stock.id} value={stock.id}>
                {stock.name} ({stock.code})
              </option>
            ))}
          </select>
        </label>
        <label>
          <span>제목</span>
          <input
            className="input"
            type="text"
            value={title}
            onChange={(event) => setTitle(event.target.value)}
            required
          />
        </label>
        <label>
          <span>내용</span>
          <textarea
            className="input"
            rows={4}
            value={content}
            onChange={(event) => setContent(event.target.value)}
            required
          />
        </label>
        {errorMessage && <p className="form__error">{errorMessage}</p>}
        <button type="submit" disabled={mutation.isPending}>
          {mutation.isPending ? '등록 중...' : '게시글 등록'}
        </button>
      </form>
    </Card>
  );
};

