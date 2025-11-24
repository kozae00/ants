interface EmptyStateProps {
  title?: string;
  description?: string;
}

export const EmptyState = ({
  title = '표시할 데이터가 없습니다.',
  description = '조건을 변경하거나 새로 고침해 보세요.',
}: EmptyStateProps) => {
  return (
    <div className="empty-state">
      <p className="empty-state__title">{title}</p>
      <p className="empty-state__description">{description}</p>
    </div>
  );
};

