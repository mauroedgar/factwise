package br.com.livecenografia.fact_wise2.repos;

import br.com.livecenografia.fact_wise2.domain.MyRoles;
import br.com.livecenografia.fact_wise2.domain.MyUsers;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MyUsersRepository extends JpaRepository<MyUsers, Long> {

    MyUsers findFirstByRoles(MyRoles myRoles);

    boolean existsByEmailIgnoreCase(String email);

}
