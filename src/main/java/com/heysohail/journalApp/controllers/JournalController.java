package com.heysohail.journalApp.controllers;

import com.heysohail.journalApp.entity.JournalEntity;
import com.heysohail.journalApp.service.JournalService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/journal")
@Tag(name = "Journal APIs")
public class JournalController {

    @Autowired
    private JournalService journalAppService;

    @GetMapping("/all")
    public ResponseEntity<List<JournalEntity>> getAllEntries() {
        try {
            List<JournalEntity> entries = journalAppService.getAllEntries();
            return new ResponseEntity<>(entries, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/userId/{userId}/all")
    public ResponseEntity<List<JournalEntity>> getAllEntriesOfAUser(@PathVariable ObjectId userId) {
        try {
            List<JournalEntity> allEntriesOfAUser = journalAppService.getAllEntriesOfAUser(userId);
            return new ResponseEntity<>(allEntriesOfAUser, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    Implement this in Authorization
//    @GetMapping("/id/{id}")
//    public ResponseEntity<JournalEntity> getEntityById(@PathVariable ObjectId id) {
//        return journalAppService.getEntityById(id);
//    }

    @PostMapping("/create/userId/{userId}")
    public ResponseEntity<ObjectId> createEntry(@RequestBody JournalEntity entry, @PathVariable ObjectId userId) {
        try {
            ObjectId objectId = journalAppService.createEntry(entry, userId);
            return new ResponseEntity<>(objectId, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/edit/userId/{userId}/journalId/{journalId}")
    public ResponseEntity<JournalEntity> editEntry(@PathVariable ObjectId userId, @PathVariable ObjectId journalId, @RequestBody JournalEntity entry) {
        try {
            JournalEntity journalEntity = journalAppService.editEntry(userId, journalId, entry);
            return new ResponseEntity<>(journalEntity, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/journalId/{journalId}/userId/{userId}")
    public ResponseEntity<Void> deleteEntry(@PathVariable ObjectId journalId, @PathVariable ObjectId userId) {
        try {
            journalAppService.deleteEntry(journalId, userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
