package dev.lottery.tms.respository;

import dev.lottery.tms.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findByAssigneeId(Long assigneeId, Pageable pageable);

    Page<Task> findByAuthorId(Long authorId, Pageable pageable);
}
