package dev.lottery.tms.respository;

import dev.lottery.tms.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
