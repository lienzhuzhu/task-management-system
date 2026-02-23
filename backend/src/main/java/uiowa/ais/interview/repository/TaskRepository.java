package uiowa.ais.interview.task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uiowa.ais.interview.entity.Task;
import uiowa.ais.interview.entity.TaskStatus;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    /**
     * Find all tasks with pagination support.
     * This is the default method provided by JpaRepository.
     */
    Page<Task> findAll(Pageable pageable);

    /**
     * Find tasks by status with pagination.
     * This demonstrates how to create a custom query method for filtering.
     *
     * Example usage:
     * - findByStatus(TaskStatus.TODO, pageable) will find tasks with TaskStatus TODO.
     */
    Page<Task> findByStatus(TaskStatus status, Pageable pageable);

}
