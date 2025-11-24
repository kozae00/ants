import { useMemo, useState } from 'react';
import type { StockResponse } from '../../api/types';
import { Card } from '../common/Card';
import { EmptyState } from '../common/EmptyState';

interface StockSelectorProps {
  stocks?: StockResponse[];
  isLoading: boolean;
  selectedStockId: number | null;
  onChange: (stockId: number | null) => void;
}

export const StockSelector = ({
  stocks = [],
  isLoading,
  selectedStockId,
  onChange,
}: StockSelectorProps) => {
  const [keyword, setKeyword] = useState('');

  const filteredStocks = useMemo(() => {
    if (!keyword) {
      return stocks;
    }
    return stocks.filter((stock) =>
      `${stock.name}${stock.code}`.toLowerCase().includes(keyword.toLowerCase()),
    );
  }, [keyword, stocks]);

  return (
    <Card
      title="관심 종목"
      actions={
        <button
          className="text-button"
          onClick={() => onChange(null)}
          disabled={selectedStockId === null}
        >
          전체
        </button>
      }
    >
      <div className="stock-selector">
        <input
          className="input"
          type="search"
          placeholder="종목명 또는 코드 검색"
          value={keyword}
          onChange={(event) => setKeyword(event.target.value)}
        />
        <div className="stock-selector__list">
          {isLoading && <p>종목 목록을 불러오는 중입니다...</p>}
          {!isLoading && filteredStocks.length === 0 && (
            <EmptyState title="검색 결과가 없습니다." />
          )}
          {!isLoading &&
            filteredStocks.map((stock) => (
              <button
                key={stock.id}
                className={`stock-selector__item ${
                  selectedStockId === stock.id ? 'is-active' : ''
                }`}
                type="button"
                onClick={() => onChange(stock.id)}
              >
                <span>{stock.name}</span>
                <small>{stock.code}</small>
              </button>
            ))}
        </div>
      </div>
    </Card>
  );
};

