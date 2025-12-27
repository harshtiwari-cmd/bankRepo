package omnicore.com.Repository;

import omnicore.com.entity.AtmEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AtmRepository extends JpaRepository<AtmEntity,Long> {
}
