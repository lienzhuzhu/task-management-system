package uiowa.ais.interview.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uiowa.ais.interview.entity.User;
import uiowa.ais.interview.exception.ResourceNotFoundException;
import uiowa.ais.interview.user.UserDTO.CreateUserDTO;
import uiowa.ais.interview.user.UserDTO.UserResponseDTO;

/**
 * Service layer for User operations.
 * Contains business logic and acts as a bridge between Controller and Repository.
 */
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Create a new user.
     *
     * @param dto User data from the API request
     * @return Created user with ID and timestamps
     */
    public UserResponseDTO createUser(CreateUserDTO dto) {
        User user = dto.toEntity();
        User savedUser = userRepository.save(user);
        return UserResponseDTO.fromEntity(savedUser);
    }

    /**
     * Update an existing user.
     *
     * @param id  User ID to update
     * @param dto Updated user data
     * @return Updated user
     * @throws ResourceNotFoundException if user not found
     */
    public UserResponseDTO updateUser(Long id, CreateUserDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());

        User updatedUser = userRepository.save(user);
        return UserResponseDTO.fromEntity(updatedUser);
    }

    /**
     * Delete a user by ID.
     *
     * @param id User ID to delete
     * @throws ResourceNotFoundException if user not found
     */
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    /**
     * Get a single user by ID.
     *
     * @param id User ID
     * @return User data
     * @throws ResourceNotFoundException if user not found
     */
    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return UserResponseDTO.fromEntity(user);
    }

    /**
     * Get all users with pagination and optional name filter.
     * This demonstrates how to handle optional filters with pagination.
     *
     * @param name Optional name filter (case-insensitive partial match)
     * @param page Page number (0-based)
     * @param size Number of items per page
     * @return Page of users with metadata
     */
    @Transactional(readOnly = true)
    public Page<UserResponseDTO> getAllUsers(String name, int page, int size) {
        // Create a Pageable object with sorting by ID descending (newest first)
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        Page<User> userPage;

        // If name filter is provided and not empty, use filtered query
        if (name != null && !name.trim().isEmpty()) {
            userPage = userRepository.findByNameContainingIgnoreCase(name.trim(), pageable);
        } else {
            // Otherwise, get all users
            userPage = userRepository.findAll(pageable);
        }

        // Convert Page<User> to Page<UserResponseDTO>
        // The map() method applies the conversion to each user in the page
        return userPage.map(UserResponseDTO::fromEntity);
    }

    /**
     * Get total count of all users.
     * Useful for dashboard statistics.
     *
     * @return Total number of users
     */
    @Transactional(readOnly = true)
    public long getTotalUserCount() {
        return userRepository.count();
    }
}
