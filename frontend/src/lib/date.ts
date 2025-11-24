const dateFormatter = new Intl.DateTimeFormat('ko-KR', {
  dateStyle: 'medium',
  timeStyle: 'short',
});

export const formatDateTime = (value?: string | null) => {
  if (!value) {
    return '-';
  }
  return dateFormatter.format(new Date(value));
};

