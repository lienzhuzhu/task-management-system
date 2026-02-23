package uiowa.ais.interview.task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uiowa.ais.interview.entity.Task;
import uiowa.ais.interview.entity.User;
import uiowa.ais.interview.exception.ResourceNotFoundException;
import uiowa.ais.interview.task.TaskDTO.CreateTaskDTO;
import uiowa.ais.interview.task.TaskDTO.TaskResponseDTO;
import uiowa.ais.interview.user.UserRepository;
import uiowa.ais.interview.entity.TaskStatus;


/**
 * Service layer for Task operations.
 * Contains business logic and acts as a bridge between Controller and Repository.
 */
@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    /**
     * Create a new task.
     *
     * @param dto Task data from the API request
     * @return Created task with ID and timestamps
     */
    public TaskResponseDTO createTask(CreateTaskDTO dto) {
        User user = userRepository.findById(dto.getAssignedUserId())
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + dto.getAssignedUserId()));
        
        Task task = dto.toEntity(user);
        Task savedTask = taskRepository.save(task);
        return TaskResponseDTO.fromEntity(savedTask);
    }

    /**
     * Update an existing task.
     *
     * @param id  Task ID to update
     * @param dto Updated task data
     * @return Updated task
     * @throws ResourceNotFoundException if task not found
     */
    public TaskResponseDTO updateTask(Long id, CreateTaskDTO dto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus());
        task.setPriority(dto.getPriority());
        task.setDueDate(dto.getDueDate());
        task.setUser(userRepository.findById(dto.getAssignedUserId())
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + dto.getAssignedUserId())));

        Task updatedTask = taskRepository.save(task);
        return TaskResponseDTO.fromEntity(updatedTask);
    }

    /**
     * Delete a task by ID.
     *
     * @param id Task ID to delete
     * @throws ResourceNotFoundException if task not found
     */
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new ResourceNotFoundException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
    }

    /**
     * Get a single task by ID.
     *
     * @param id Task ID
     * @return Task data
     * @throws ResourceNotFoundException if task not found
     */
    @Transactional(readOnly = true)
    public TaskResponseDTO getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
        return TaskResponseDTO.fromEntity(task);
    }

    /**
     * Get all tasks with pagination and optional status filter.
     * This demonstrates how to handle optional filters with pagination.
     *
     * @param status Optional status filter
     * @param page Page number (0-based)
     * @param size Number of items per page
     * @return Page of tasks with metadata
     */
    @Transactional(readOnly = true)
    public Page<TaskResponseDTO> getAllTasks(String status, int page, int size) {
        // Create a Pageable object with sorting by ID descending (newest first)
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        Page<Task> taskPage;

        // If status filter is provided and not empty, use filtered query
        if (status != null && !status.trim().isEmpty()) {
            TaskStatus taskStatus = TaskStatus.valueOf(status.trim());
            taskPage = taskRepository.findByStatus(taskStatus, pageable);
        } else {
            // Otherwise, get all tasks
            taskPage = taskRepository.findAll(pageable);
        }

        // Convert Page<Task> to Page<TaskResponseDTO>
        // The map() method applies the conversion to each task in the page
        return taskPage.map(TaskResponseDTO::fromEntity);
    }

    /**
     * Get total count of all tasks.
     * Useful for dashboard statistics.
     *
     * @return Total number of tasks
     */
    @Transactional(readOnly = true)
    public long getTotalTaskCount() {
        return taskRepository.count();
    }
}
