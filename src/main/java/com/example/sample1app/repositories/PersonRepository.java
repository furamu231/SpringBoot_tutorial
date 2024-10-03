package com.example.sample1app.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import com.example.sample1app.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    // リポジトリそのものには詳細な処理を記述することはありません
    
    public Optional<Person> findById(Long name);
}

   