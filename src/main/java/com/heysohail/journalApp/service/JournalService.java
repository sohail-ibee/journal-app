package com.heysohail.journalApp.service;

import com.heysohail.journalApp.entity.JournalEntity;
import com.heysohail.journalApp.entity.UserEntity;
import com.heysohail.journalApp.repository.JournalRepository;
import com.heysohail.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class JournalService {

    @Autowired
    private JournalRepository journalRepo;

    @Autowired
    private UserRepository userRepository;

    public List<JournalEntity> getAllEntries() {
        return journalRepo.findAll();
    }

    public List<JournalEntity> getAllEntriesOfAUser(ObjectId userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User Id is not valid."));

        return user.getJournalEntities();
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
    public ObjectId createEntry(JournalEntity entry, ObjectId userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User Id is not valid."));

        entry.setDate(LocalDateTime.now());
        JournalEntity savedEntry = journalRepo.save(entry);

        user.getJournalEntities().add(entry);
        userRepository.save(user);

        return savedEntry.getId();
    }

    public JournalEntity editEntry(ObjectId id, ObjectId journalId, JournalEntity entry) {
        JournalEntity oldEntry = journalRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("User Id is not valid."));

        oldEntry.setTitle(!entry.getTitle().isEmpty() ? entry.getTitle() : oldEntry.getTitle());
        oldEntry.setContent(entry.getContent() != null && !entry.getContent().isEmpty() ? entry.getContent() : oldEntry.getContent());
        journalRepo.save(oldEntry);
        return oldEntry;
    }

    @Transactional
    public void deleteEntry(ObjectId journalId, ObjectId userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User Id is not valid."));

        user.getJournalEntities().removeIf(x -> x.getId().equals(journalId));
        userRepository.save(user);
        journalRepo.deleteById(journalId);
    }
}
