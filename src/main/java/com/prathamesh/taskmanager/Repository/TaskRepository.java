package com.prathamesh.taskmanager.Repository;

import com.prathamesh.taskmanager.Model.Tasks;
import com.prathamesh.taskmanager.Model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends MongoRepository<Tasks, String> {
    List<Tasks> findByUserOrderByDueDateAsc(Users user);
}
