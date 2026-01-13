package CarSalesPlatform.authentication.microservice.Repository;

import CarSalesPlatform.authentication.microservice.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	Optional<Role> findByName(String name);
}
