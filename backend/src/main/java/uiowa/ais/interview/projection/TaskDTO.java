package uiowa.ais.interview.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import uiowa.ais.interview.entity.User;
import uiowa.ais.interview.user.UserDTO.UserResponseDTO;
import uiowa.ais.interview.entity.Task;
import uiowa.ais.interview.entity.TaskStatus;
import uiowa.ais.interview.entity.TaskPriority;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Data Transfer Objects for Task entity.
 * DTOs separate API layer from database layer, giving us control over what data is exposed.
 */
public class TaskDTO {

    /**
     * DTO for creating a new task.
     * Contains only the fields needed to create a task (no ID, no createdAt).
     */
    public static class CreateTaskDTO {
        @NotBlank(message = "Title is required")
        @Size(max = 100, message = "Title must not exceed 100 characters")
        private String title;

        @NotBlank(message = "Description is required")
        @Size(max = 1000, message = "Description must not exceed 1000 characters")
        private String description;

        @NotNull(message = "Status is required")
        private TaskStatus status;

        @NotNull(message = "Priority is required")
        private TaskPriority priority;

        @NotNull(message = "Due date is required")
        private LocalDate dueDate;

        @NotNull(message = "Assigned user is required")
        private Long assignedUserId;

        // Constructors
        public CreateTaskDTO() {
        }

        public CreateTaskDTO(String title,
                                String description,
                                TaskStatus status,
                                TaskPriority priority,
                                LocalDate dueDate,
                                Long assignedUserId) {
            this.title = title;
            this.description = description;
            this.status = status;
            this.priority = priority;
            this.dueDate = dueDate;
            this.assignedUserId = assignedUserId;
        }

        // Getters and Setters
        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public TaskStatus getStatus() {
            return status;
        }

        public void setStatus(TaskStatus status) {
            this.status = status;
        }

        public TaskPriority getPriority() {
            return priority;
        }

        public void setPriority(TaskPriority priority) {
            this.priority = priority;
        }

        public LocalDate getDueDate() {
            return dueDate;
        }

        public void setDueDate(LocalDate dueDate) {
            this.dueDate = dueDate;
        }

        public Long getAssignedUserId() {
            return assignedUserId;
        }

        public void setAssignedUserId(Long userId) {
            this.assignedUserId = userId;
        }

        /**
         * Convert DTO to Entity.
         * Used when creating a new task from the API request.
         */
        public Task toEntity(User user) {
            return new Task(this.title, 
                            this.description,
                            this.status,
                            this.priority,
                            this.dueDate,
                            user);
        }
    }

    /**
     * DTO for returning task data in API responses.
     * Contains all task information we want to expose to clients.
     */
    public static class TaskResponseDTO {
        private Long id;
        private String title;
        private String description;
        private TaskStatus status;
        private TaskPriority priority;
        private LocalDate dueDate;
        private UserResponseDTO assignedUser;
        private LocalDateTime createdAt;

        // Constructors
        public TaskResponseDTO() {
        }

        public TaskResponseDTO(Long id,
                                String title,
                                String description,
                                TaskStatus status,
                                TaskPriority priority,
                                LocalDate dueDate,
                                User user,
                                LocalDateTime createdAt) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.status = status;
            this.priority = priority;
            this.dueDate = dueDate;
            this.assignedUser = UserResponseDTO.fromEntity(user);
            this.createdAt = createdAt;
        }

        // Getters and Setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public TaskStatus getStatus() {
            return status;
        }

        public void setStatus(TaskStatus status) {
            this.status = status;
        }

        public TaskPriority getPriority() {
            return priority;
        }

        public void setPriority(TaskPriority priority) {
            this.priority = priority;
        }

        public LocalDate getDueDate() {
            return dueDate;
        }

        public void setDueDate(LocalDate dueDate) {
            this.dueDate = dueDate;
        }

        public UserResponseDTO getAssignedUser() {
            return assignedUser;
        }

        public void setAssignedUser(UserResponseDTO assignedUser) {
            this.assignedUser = assignedUser;
        }

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
        }


        /**
         * Convert Entity to DTO.
         * Used when returning task data in API responses.
         */
        public static TaskResponseDTO fromEntity(Task task) {
            return new TaskResponseDTO(
                    task.getId(),
                    task.getTitle(),
                    task.getDescription(),
                    task.getStatus(),
                    task.getPriority(),
                    task.getDueDate(),
                    task.getUser(),
                    task.getCreatedAt()
            );
        }
    }
}
