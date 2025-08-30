package com.heysohail.journalApp.controllers;

import com.heysohail.journalApp.entity.JournalEntity;
import com.heysohail.journalApp.service.JournalService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//https://chatgpt.com/c/68b338b7-ee20-832a-a1f6-55f2f2f9ebf7
@RestController
@RequestMapping("/api/journal")
public class JournalController {

    @Autowired
    private JournalService journalAppService;

    @GetMapping("/all")
    public ResponseEntity<List<JournalEntity>> getAllEntries() {
        return journalAppService.getAllEntries();
    }

    @GetMapping("/userId/{userId}/all")
    public ResponseEntity<List<JournalEntity>> getAllEntriesOfAUser(@PathVariable ObjectId userId) {
        return journalAppService.getAllEntriesOfAUser(userId);
    }

//    Implement this in Authorization
//    @GetMapping("/id/{id}")
//    public ResponseEntity<JournalEntity> getEntityById(@PathVariable ObjectId id) {
//        return journalAppService.getEntityById(id);
//    }

    @PostMapping("/create/userId/{userId}")
    public ResponseEntity<?> createEntry(@RequestBody JournalEntity entry, @PathVariable ObjectId userId) {
        return journalAppService.createEntry(entry, userId);
    }

    @PutMapping("/edit/userId/{userId}/journalId/{journalId}")
    public ResponseEntity<JournalEntity> editEntry(@PathVariable ObjectId userId, @PathVariable ObjectId journalId, @RequestBody JournalEntity entry) {
        return journalAppService.editEntry(userId, journalId, entry);
    }

    @DeleteMapping("/delete/journalId/{journalId}/userId/{userId}")
    public ResponseEntity<?> deleteEntry(@PathVariable ObjectId journalId, @PathVariable ObjectId userId) {
        return journalAppService.deleteEntry(journalId, userId);
    }
}
