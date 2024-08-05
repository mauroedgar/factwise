package br.com.livecenografia.fact_wise2.repos;

import br.com.livecenografia.fact_wise2.domain.MyRoles;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MyRolesRepository extends JpaRepository<MyRoles, Long> {

    boolean existsByRoleNameIgnoreCase(String roleName);

}
