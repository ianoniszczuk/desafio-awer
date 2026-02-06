import { type SyntheticEvent } from 'react'
import '../styles/TaskInput.css'

interface TaskInputProps {
  value: string
  onChange: (value: string) => void
  onSubmit: (e: SyntheticEvent<HTMLFormElement>) => void
  loading: boolean
}

export function TaskInput({ value, onChange, onSubmit, loading }: TaskInputProps) {
  return (
    <form onSubmit={onSubmit} className="task-input-form">
      <div className="task-input-wrapper">
        <input
          type="text"
          value={value}
          onChange={(e) => onChange(e.target.value)}
          placeholder="Escribe una nueva tarea..."
          disabled={loading}
          className="task-input"
        />
        <button 
          type="submit" 
          disabled={loading || !value.trim()} 
          className="task-input-btn"
        >
          {loading ? (
            <span className="btn-spinner">⏳</span>
          ) : (
            <>
              <span className="btn-icon">+</span>
              <span className="btn-text">Agregar</span>
            </>
          )}
        </button>
      </div>
    </form>
  )
}