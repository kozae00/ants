import type { ReactNode } from 'react';

interface CardProps {
  title?: string;
  actions?: ReactNode;
  children: ReactNode;
}

export const Card = ({ title, actions, children }: CardProps) => {
  return (
    <section className="card">
      {(title || actions) && (
        <header className="card__header">
          <h3>{title}</h3>
          {actions && <div className="card__actions">{actions}</div>}
        </header>
      )}
      <div className="card__body">{children}</div>
    </section>
  );
};

