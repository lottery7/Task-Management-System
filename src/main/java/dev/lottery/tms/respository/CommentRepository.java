package dev.lottery.tms.respository;

import dev.lottery.tms.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findAll(Pageable pageable);

    Page<Comment> findByTaskId(Long taskId, Pageable pageable);

    Page<Comment> findByUserId(Long userId, Pageable pageable);

    Page<Comment> findByTaskIdAndUserId(Long taskId, Long userId, Pageable pageable);
}
