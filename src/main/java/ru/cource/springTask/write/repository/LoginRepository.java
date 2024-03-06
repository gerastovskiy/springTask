package ru.cource.springTask.write.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.cource.springTask.write.model.Login;
import ru.cource.springTask.write.model.User;

@Repository
public interface LoginRepository extends CrudRepository<Login,Long> {
    Login findByUser(User user);
}