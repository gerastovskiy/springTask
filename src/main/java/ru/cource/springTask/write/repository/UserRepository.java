package ru.cource.springTask.write.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.cource.springTask.write.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUserName(String userName);
}
