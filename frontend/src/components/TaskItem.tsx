import '../styles/TaskItem.css'

interface TaskItemProps {
  id: number
  description: string
}

export function TaskItem({ id, description }: TaskItemProps) {
  return (
    <div className="task-item">
      <div className="task-item-header">
        <span className="task-id">{id}</span>
      </div>
      <p className="task-description">{description}</p>
    </div>
  )
}