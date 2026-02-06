import { useState, useEffect, type SyntheticEvent } from 'react'
import { Header } from './components/Header'
import { TaskInput } from './components/TaskInput'
import { TaskItem } from './components/TaskItem'
import './App.css'

interface Task {
  id: number
  description: string
}

function App() {
  const [tasks, setTasks] = useState<Task[]>([])
  const [currentPage, setCurrentPage] = useState(0)
  const [totalPages, setTotalPages] = useState(0)
  const [totalElements, setTotalElements] = useState(0)
  const [newTask, setNewTask] = useState('')
  const [loading, setLoading] = useState(false)

  const API_URL = 'http://localhost:8080/tasks'

  const fetchTasks = async (page: number) => {
    setLoading(true)
    try {
      const response = await fetch(`${API_URL}?page=${page}&size=6`)
      
      const totalPagesHeader = response.headers.get('X-Total-Pages')
      const totalElementsHeader = response.headers.get('X-Total-Count')
      const currentPageHeader = response.headers.get('X-Current-Page')
      
      if (totalPagesHeader) setTotalPages(parseInt(totalPagesHeader))
      if (totalElementsHeader) setTotalElements(parseInt(totalElementsHeader))
      if (currentPageHeader) setCurrentPage(parseInt(currentPageHeader))
      
      const data: Task[] = await response.json()
      setTasks(data)
    } catch (error) {
      console.error('Error fetching tasks:', error)
    } finally {
      setLoading(false)
    }
  }

  const addTask = async (e: SyntheticEvent<HTMLFormElement>) => {
    e.preventDefault()
    if (!newTask.trim()) return

    try {
      const response = await fetch(API_URL, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ description: newTask }),
      })

      if (response.ok) {
        setNewTask('')
        fetchTasks(currentPage)
      }
    } catch (error) {
      console.error('Error adding task:', error)
    }
  }

  useEffect(() => {
    fetchTasks(0)
  }, [])

  return (
    <div className="app">
      <div className="container">
        <Header />

        <TaskInput
          value={newTask}
          onChange={setNewTask}
          onSubmit={addTask}
          loading={loading}
        />

        {loading && tasks.length === 0 ? (
          <div className="loading">
            <div className="spinner"></div>
            <p>Cargando tareas...</p>
          </div>
        ) : (
          <>
            <div className="task-list">
              {tasks.length === 0 ? (
                <div className="empty-state">
                  <p>🎉 No hay tareas. ¡Agrega una para empezar!</p>
                </div>
              ) : (
                tasks.map((task) => (
                  <TaskItem key={task.id} id={task.id} description={task.description} />
                ))
              )}
            </div>

            {totalPages > 1 && (
              <div className="pagination">
                <button
                  onClick={() => fetchTasks(currentPage - 1)}
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
                  onClick={() => fetchTasks(currentPage + 1)}
                  disabled={currentPage >= totalPages - 1 || loading}
                  className="btn-pagination"
                >
                  Siguiente →
                </button>
              </div>
            )}
          </>
        )}
      </div>
    </div>
  )
}

export default App