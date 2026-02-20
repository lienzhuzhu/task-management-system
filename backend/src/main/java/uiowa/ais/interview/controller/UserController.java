package uiowa.ais.interview.user;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uiowa.ais.interview.user.UserDTO.CreateUserDTO;
import uiowa.ais.interview.user.UserDTO.UserResponseDTO;

/**
 * REST Controller for User management.
 * Handles HTTP requests and returns appropriate responses.
 *
 * Base URL: /api/users
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Create a new user.
     *
     * POST /api/users
     * Request body: { "name": "John Doe", "email": "john@example.com" }
     *
     * @param dto User data (validated)
     * @return Created user with 201 status
     */
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody CreateUserDTO dto) {
        UserResponseDTO createdUser = userService.createUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    /**
     * Update an existing user.
     *
     * PUT /api/users/{id}
     * Request body: { "name": "Jane Doe", "email": "jane@example.com" }
     *
     * @param id  User ID
     * @param dto Updated user data (validated)
     * @return Updated user with 200 status
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody CreateUserDTO dto) {
        UserResponseDTO updatedUser = userService.updateUser(id, dto);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Delete a user.
     *
     * DELETE /api/users/{id}
     *
     * @param id User ID
     * @return 204 No Content on success
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get a single user by ID.
     *
     * GET /api/users/{id}
     *
     * @param id User ID
     * @return User data with 200 status
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        UserResponseDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    /**
     * Get all users with pagination and optional name filter.
     *
     * GET /api/users?name=john&page=0&size=10
     *
     * Query parameters:
     * - name (optional): Filter by name (case-insensitive partial match)
     * - page (optional, default=0): Page number (0-based)
     * - size (optional, default=10): Number of items per page
     *
     * Response includes:
     * - content: Array of users
     * - totalElements: Total number of users matching the filter
     * - totalPages: Total number of pages
     * - number: Current page number
     * - size: Page size
     *
     * @param name Optional name filter
     * @param page Page number (default: 0)
     * @param size Page size (default: 10)
     * @return Page of users with metadata
     */
    @GetMapping
    public ResponseEntity<Page<UserResponseDTO>> getAllUsers(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<UserResponseDTO> users = userService.getAllUsers(name, page, size);
        return ResponseEntity.ok(users);
    }

    /**
     * Get total user count.
     * Useful for dashboard statistics.
     *
     * GET /api/users/count
     *
     * @return Total number of users
     */
    @GetMapping("/count")
    public ResponseEntity<Long> getTotalUserCount() {
        long count = userService.getTotalUserCount();
        return ResponseEntity.ok(count);
    }
}
