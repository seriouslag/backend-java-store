package com.chiefsretro.repositories;

import com.chiefsretro.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User getUserByUserEmail(String userEmail);
    Set<User> findDistinctByUserEmailLikeIgnoreCaseOrderByUserId(String userEmail);

    Set<User> getAllByOrderByUserId();
    Page<User> findAllByOrderByUserId(Pageable page);
    Page<User> findAllByOrderByUserEmail(Pageable page);

}
