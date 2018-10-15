package com.landongavin.repositories;

import com.landongavin.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User getUserById(long userId);
    Set<User> findFirst50ByNameLikeIgnoreCase(String string);
    Set<User> findAllUserByName(String string);
    Set<User> findAll();
    //Page<User> findAllByUser(Pageable page);
    User save(User user);
    boolean existsUserById(long id);
    @Transactional
    int deleteUserById(long id);
}
