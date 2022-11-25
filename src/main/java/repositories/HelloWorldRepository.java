package repositories;

import model.HelloWorldModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HelloWorldRepository extends JpaRepository<HelloWorldModel, Long> {
}