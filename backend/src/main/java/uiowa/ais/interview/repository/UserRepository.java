package uiowa.ais.interview.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uiowa.ais.interview.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find all users with pagination support.
     * This is the default method provided by JpaRepository.
     */
    Page<User> findAll(Pageable pageable);

    /**
     * Find users by name (case-insensitive partial match) with pagination.
     * This demonstrates how to create a custom query method for filtering.
     *
     * Example usage:
     * - findByNameContainingIgnoreCase("john", pageable) will find "John", "Johnny", "johnson", etc.
     */
    Page<User> findByNameContainingIgnoreCase(String name, Pageable pageable);

    /**
     * Alternative: Using @Query annotation for more complex queries.
     * This achieves the same result as the method name query above.
     * Uncomment to use this instead of the method name approach.
     */
    // @Query("SELECT u FROM User u WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    // Page<User> searchByName(@Param("name") String name, Pageable pageable);
}
