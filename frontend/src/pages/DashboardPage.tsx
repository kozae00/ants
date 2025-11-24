import { useState } from 'react';
import { useQuery } from '@tanstack/react-query';
import { AppHeader } from '../components/layout/AppHeader';
import { ProfileCard } from '../components/dashboard/ProfileCard';
import { StockSelector } from '../components/stocks/StockSelector';
import { FeedList } from '../components/feed/FeedList';
import { PostList } from '../components/posts/PostList';
import { PostComposer } from '../components/posts/PostComposer';
import { useAuth } from '../context/AuthContext';
import { fetchStocks } from '../api/stocks';
import { fetchFeed, fetchFeedByStock } from '../api/feed';
import { fetchPosts, fetchPostsByStock } from '../api/posts';

export const DashboardPage = () => {
  const { member, token } = useAuth();
  const [selectedStockId, setSelectedStockId] = useState<number | null>(null);
  const [postPage, setPostPage] = useState(0);

  const stocksQuery = useQuery({
    queryKey: ['stocks'],
    queryFn: fetchStocks,
    enabled: Boolean(token),
  });

  const feedQuery = useQuery({
    queryKey: ['feed', selectedStockId],
    queryFn: () =>
      selectedStockId
        ? fetchFeedByStock(selectedStockId, { size: 10 })
        : fetchFeed({ size: 10 }),
    enabled: Boolean(token),
  });

  const postsQuery = useQuery({
    queryKey: ['posts', selectedStockId, postPage],
    queryFn: () =>
      selectedStockId
        ? fetchPostsByStock(selectedStockId, { page: postPage, size: 5 })
        : fetchPosts({ page: postPage, size: 5 }),
    enabled: Boolean(token),
    placeholderData: (prev) => prev,
  });

  const handleStockChange = (stockId: number | null) => {
    setSelectedStockId(stockId);
    setPostPage(0);
  };

  return (
    <div className="dashboard">
      <AppHeader />
      <div className="dashboard__body">
        <aside className="dashboard__sidebar">
          <ProfileCard member={member} />
          <StockSelector
            stocks={stocksQuery.data}
            isLoading={stocksQuery.isLoading}
            selectedStockId={selectedStockId}
            onChange={handleStockChange}
          />
          <PostComposer
            stocks={stocksQuery.data}
            defaultStockId={selectedStockId}
            onSuccess={() => {
              setPostPage(0);
            }}
          />
        </aside>
        <section className="dashboard__content">
          <FeedList
            feed={feedQuery.data}
            isLoading={feedQuery.isLoading}
            onReload={() => feedQuery.refetch()}
          />
          <PostList
            posts={postsQuery.data}
            isLoading={postsQuery.isFetching}
            onPageChange={setPostPage}
          />
        </section>
      </div>
    </div>
  );
};

