package br.com.lowlevel.promotion_app.repositories;


import br.com.lowlevel.promotion_app.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {}
