package br.com.lowlevel.promotion_app.repositories;

import br.com.lowlevel.promotion_app.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
