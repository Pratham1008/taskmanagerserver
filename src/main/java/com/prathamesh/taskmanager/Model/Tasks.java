package com.prathamesh.taskmanager.Model;

import com.mongodb.lang.NonNull;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "Tasks")
public class Tasks {
    @MongoId
    private String id;
    @NonNull
    private String task;
    @NonNull
    private String dueDate;
    @NonNull
    private Priority priority;
    @NonNull
    @DBRef
    private Users user;
    private Boolean done = false;
}
