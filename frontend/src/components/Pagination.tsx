import '../styles/Pagination.css'

interface PaginationProps {
  currentPage: number
  totalPages: number
  totalElements: number
  loading: boolean
  onPrevious: () => void
  onNext: () => void
}

export function Pagination({
  currentPage,
  totalPages,
  totalElements,
  loading,
  onPrevious,
  onNext,
}: PaginationProps) {
  if (totalPages <= 1) return null

  return (
    <div className="pagination">
      <button
        onClick={onPrevious}
        disabled={currentPage === 0 || loading}
        className="btn-pagination"
      >
        ← Anterior
      </button>
      <span className="page-info">
        Página <strong>{currentPage + 1}</strong> de <strong>{totalPages}</strong>
        <span className="total-count">({totalElements} tareas)</span>
      </span>
      <button
        onClick={onNext}
        disabled={currentPage >= totalPages - 1 || loading}
        className="btn-pagination"
      >
        Siguiente →
      </button>
    </div>
  )
}