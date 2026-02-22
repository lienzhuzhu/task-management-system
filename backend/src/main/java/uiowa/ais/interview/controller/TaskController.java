package uiowa.ais.interview.task;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uiowa.ais.interview.task.TaskDTO.CreateTaskDTO;
import uiowa.ais.interview.task.TaskDTO.TaskResponseDTO;

/**
 * REST Controller for Task management.
 * Handles HTTP requests and returns appropriate responses.
 *
 * Base URL: /api/tasks
 */
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Create a new task.
     *
     * POST /api/tasks
     * Request body: {
     *                  "name": "Task Title",
     *                  "description": "Task Description"
     *                  "status": "TODO",
     *                  "priority": "HIGH",
     *                  "dueDate": "2024-12-31", /* NOTE: check the format
     *                  "assignedUser": "johndoe" /* NOTE: this is the name of the user, not the ID. The service layer will handle the lookup and association
     *               }
     *
     * @param dto Task data (validated)
     * @return Created task with 201 status
     */
    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(@Valid @RequestBody CreateTaskDTO dto) {
        TaskResponseDTO createdTask = taskService.createTask(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    /**
     * Update an existing task.
     *
     * PUT /api/tasks/{id}
     * Request body: { "name": "Jane Doe", "email": "jane@example.com" }
     *
     * @param id  task ID
     * @param dto Updated task data (validated)
     * @return Updated task with 200 status
     */
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody CreateTaskDTO dto) {
        TaskResponseDTO updatedtask = taskService.updateTask(id, dto);
        return ResponseEntity.ok(updatedtask);
    }

    /**
     * Delete a task.
     *
     * DELETE /api/tasks/{id}
     *
     * @param id task ID
     * @return 204 No Content on success
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get a single task by ID.
     *
     * GET /api/tasks/{id}
     *
     * @param id task ID
     * @return task data with 200 status
     */
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable Long id) {
        TaskResponseDTO task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    /**
     * Get all tasks with pagination and optional status filter.
     *
     * GET /api/tasks?status=TODO&page=0&size=10
     *
     * Query parameters:
     * - status (optional): Filter by status (case-insensitive partial match)
     * - page (optional, default=0): Page number (0-based)
     * - size (optional, default=10): Number of items per page
     *
     * Response includes:
     * - content: Array of tasks
     * - totalElements: Total number of tasks matching the filter
     * - totalPages: Total number of pages
     * - number: Current page number
     * - size: Page size
     *
     * @param status Optional status filter
     * @param page Page number (default: 0)
     * @param size Page size (default: 10)
     * @return Page of tasks with metadata
     */
    @GetMapping
    public ResponseEntity<Page<TaskResponseDTO>> getAllTasks(
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<TaskResponseDTO> tasks = taskService.getAllTasks(status, page, size);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Get total task count.
     * Useful for dashboard statistics.
     *
     * GET /api/tasks/count
     *
     * @return Total number of tasks
     */
    @GetMapping("/count")
    public ResponseEntity<Long> getTotalTaskCount() {
        long count = taskService.getTotalTaskCount();
        return ResponseEntity.ok(count);
    }
}
