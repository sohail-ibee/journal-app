package com.heysohail.journalApp.service;

import com.heysohail.journalApp.entity.JournalEntity;
import com.heysohail.journalApp.entity.UserEntity;
import com.heysohail.journalApp.repository.JournalRepository;
import com.heysohail.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalService {

    @Autowired
    private JournalRepository journalRepo;

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<List<JournalEntity>> getAllEntries() {
        try {
            List<JournalEntity> list = journalRepo.findAll();

            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<JournalEntity>> getAllEntriesOfAUser(ObjectId userId) {
        try {
            Optional<UserEntity> user = userRepository.findById(userId);

            if (user.isPresent()) {
                return new ResponseEntity<>(user.get().getJournalEntities(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    public ResponseEntity<JournalEntity> getEntityById(ObjectId id) {
//        try {
//            JournalEntity entry = journalRepo.findById(id).orElse(null);
//
//            if (entry != null) {
//                return new ResponseEntity<>(entry, HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            }
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @Transactional
    public ResponseEntity<?> createEntry(JournalEntity entry, ObjectId userId) {
        try {
            Optional<UserEntity> user = userRepository.findById(userId);

            if (user.isPresent()) {
                entry.setDate(LocalDateTime.now());
                JournalEntity savedEntry = journalRepo.save(entry);

                user.get().getJournalEntities().add(entry);
                user.get().setEmail(null);
                userRepository.save(user.get());

                return new ResponseEntity<>(savedEntry.getId(), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<JournalEntity> editEntry(ObjectId id, ObjectId journalId,JournalEntity entry) {
        try {
            JournalEntity oldEntry = journalRepo.findById(id).orElse(null);

            if (oldEntry != null) {
                oldEntry.setTitle(entry.getTitle() != null && !entry.getTitle().isEmpty() ? entry.getTitle() : oldEntry.getTitle());
                oldEntry.setContent(entry.getContent() != null && !entry.getContent().isEmpty() ? entry.getContent() : oldEntry.getContent());
                journalRepo.save(oldEntry);
                return new ResponseEntity<>(oldEntry, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ResponseEntity<?> deleteEntry(ObjectId journalId, ObjectId userId) {
        try {
            UserEntity user = userRepository.findById(userId).orElse(null);

            if (user!=null) {
                user.getJournalEntities().removeIf(x -> x.getId().equals(journalId));
                userRepository.save(user);
                journalRepo.deleteById(journalId);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
