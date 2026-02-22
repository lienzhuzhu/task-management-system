package uiowa.ais.interview.task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uiowa.ais.interview.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    /**
     * Find all tasks with pagination support.
     * This is the default method provided by JpaRepository.
     */
    Page<Task> findAll(Pageable pageable);

    /**
     * Find tasks by status (case-insensitive partial match) with pagination.
     * This demonstrates how to create a custom query method for filtering.
     *
     * Example usage:
     * - findByStatusContainingIgnoreCase("todo", pageable) will find tasks with status "TODO", "Todo", etc.
     */
    Page<Task> findByStatusIgnoreCase(String status, Pageable pageable);

}
