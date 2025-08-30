package com.heysohail.journalApp.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "journal_entry")
@Getter
@Setter
public class JournalEntity {
    @Id
    private ObjectId id;

    @Indexed
    @NonNull
    private String title;

    private String content;
    private LocalDateTime date;

}
