# Gestor de Tareas - Full Stack

Aplicación web full-stack para gestión de tareas con paginación, desarrollada con Spring Boot (backend) y React + TypeScript (frontend).

## Stack Tecnológico

### Backend
- Java 21+ con Spring Boot 3
- Spring Data JPA para persistencia
- MySQL 8.0 como base de datos
- Maven para gestión de dependencias
- API RESTful con paginación usando headers HTTP

### Frontend
- React 18 con TypeScript
- Vite como build tool
- CSS modules para estilos
- Arquitectura basada en componentes

## Instalación y Ejecución

### Prerrequisitos
- Docker (para MySQL)
- Java 21+ y Maven
- Node.js 18+ y npm

### 1. Base de Datos (MySQL con Docker)

Inicia el contenedor MySQL:

```bash
docker run --name mysql-desafioawer \
  -e MYSQL_ROOT_PASSWORD=root123 \
  -e MYSQL_DATABASE=desafioawer_db \
  -p 3306:3306 \
  -d mysql:8.0
```

Crea las tablas ejecutando el schema:

```bash
docker exec -i mysql-desafioawer \
  mysql -uroot -proot123 desafioawer_db \
  < backend/src/main/resources/schema.sql
```

Opcionalmente, inserta datos de prueba:

```bash
docker exec -i mysql-desafioawer \
  mysql -uroot -proot123 desafioawer_db \
  < backend/src/main/resources/data.sql
```

### 2. Backend (Spring Boot)

Navega a la carpeta del backend:

```bash
cd backend
```

Ejecuta la aplicación:

```bash
./mvnw spring-boot:run
```

O con Maven instalado globalmente:

```bash
mvn spring-boot:run
```

El backend estará disponible en http://localhost:8080

### 3. Frontend (React + Vite)

Navega a la carpeta del frontend:

```bash
cd frontend
```

Instala dependencias:

```bash
npm install
```

Inicia el servidor de desarrollo:

```bash
npm run dev
```

El frontend estará disponible en http://localhost:5173

## API Endpoints

### Base URL
```
http://localhost:8080
```

### GET /tasks

Obtiene tareas paginadas.

Query Parameters:
- page (default: 0) - Número de página
- size (default: 10) - Tareas por página
- sortBy (default: "id") - Campo para ordenar
- sortDirection (default: "ASC") - Dirección de ordenamiento

Response Headers:
- X-Total-Pages - Total de páginas
- X-Total-Count - Total de tareas
- X-Current-Page - Página actual
- X-Page-Size - Tamaño de página
- Link - Enlaces de navegación (first, prev, next, last)

Ejemplo:
```bash
curl "http://localhost:8080/tasks?page=0&size=10"
```

### POST /tasks

Crea una nueva tarea.

Body (JSON):
```json
{
  "description": "Nueva tarea de ejemplo"
}
```

Ejemplo:
```bash
curl -X POST http://localhost:8080/tasks \
  -H "Content-Type: application/json" \
  -d '{"description":"Completar el proyecto"}'
```

## Estructura del Proyecto

```
desafio-awer/
├── backend/
│   ├── src/main/java/com/awer/backend/
│   │   ├── controller/
│   │   ├── model/
│   │   ├── repository/
│   │   ├── services/
│   │   └── exception/
│   └── src/main/resources/
│       ├── application.properties
│       ├── schema.sql
│       └── data.sql
│
└── frontend/
    ├── src/
    │   ├── components/
    │   │   ├── Header.tsx
    │   │   ├── TaskInput.tsx
    │   │   ├── TaskItem.tsx
    │   │   └── Pagination.tsx
    │   ├── styles/
    │   ├── App.tsx
    │   └── main.tsx
    └── package.json
```

## Características

### Backend
- API RESTful siguiendo convenciones HTTP
- Paginación con metadata en headers (estándar REST)
- Validación de datos con Bean Validation
- Manejo global de excepciones
- CORS configurado para desarrollo local
- Persistencia con JPA/Hibernate

### Frontend
- Interfaz responsive con tema oscuro
- Componentes modulares y reutilizables
- TypeScript para type safety
- Paginación con navegación prev/next
- Loading states y manejo de errores
- Diseño moderno con CSS custom

## Autor

Ian Cruz Oniszczuk