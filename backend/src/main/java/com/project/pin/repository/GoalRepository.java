package com.project.pin.repository;
import com.project.pin.entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface GoalRepository extends JpaRepository<Goal, Long> {
    Optional<Goal> findByCompradorId(Long compradorId);
}
