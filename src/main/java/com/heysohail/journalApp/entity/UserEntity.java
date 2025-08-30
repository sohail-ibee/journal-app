package com.heysohail.journalApp.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Document(collection = "user_entity")
public class UserEntity {

    @Id
    private String id;

    @NonNull
    @Indexed(unique = true)
    private String email;

    @NonNull
    private String password;

    private List<JournalEntity> journalEntities = new ArrayList<>();
}
