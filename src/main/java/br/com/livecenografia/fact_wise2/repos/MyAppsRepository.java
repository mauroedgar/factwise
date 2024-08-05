package br.com.livecenografia.fact_wise2.repos;

import br.com.livecenografia.fact_wise2.domain.MyApps;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MyAppsRepository extends JpaRepository<MyApps, Long> {
}
