package uiowa.ais.interview.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import uiowa.ais.interview.entity.User;

import java.time.LocalDateTime;

/**
 * Data Transfer Objects for User entity.
 * DTOs separate API layer from database layer, giving us control over what data is exposed.
 */
public class UserDTO {

    /**
     * DTO for creating a new user.
     * Contains only the fields needed to create a user (no ID, no createdAt).
     */
    public static class CreateUserDTO {
        @NotBlank(message = "Name is required")
        @Size(max = 100, message = "Name must not exceed 100 characters")
        private String name;

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        private String email;

        // Constructors
        public CreateUserDTO() {
        }

        public CreateUserDTO(String name, String email) {
            this.name = name;
            this.email = email;
        }

        // Getters and Setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        /**
         * Convert DTO to Entity.
         * Used when creating a new user from the API request.
         */
        public User toEntity() {
            return new User(this.name, this.email);
        }
    }

    /**
     * DTO for returning user data in API responses.
     * Contains all user information we want to expose to clients.
     */
    public static class UserResponseDTO {
        private Long id;
        private String name;
        private String email;
        private LocalDateTime createdAt;

        // Constructors
        public UserResponseDTO() {
        }

        public UserResponseDTO(Long id, String name, String email, LocalDateTime createdAt) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.createdAt = createdAt;
        }

        // Getters and Setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
        }

        /**
         * Convert Entity to DTO.
         * Used when returning user data in API responses.
         */
        public static UserResponseDTO fromEntity(User user) {
            return new UserResponseDTO(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getCreatedAt()
            );
        }
    }
}
