package com.revjobs.user.controller;

import com.revjobs.user.model.SavedJob;
import com.revjobs.user.repository.SavedJobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users/{userId}/saved-jobs")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SavedJobController {

    private final SavedJobRepository savedJobRepository;

    @PostMapping("/{jobId}")
    public ResponseEntity<SavedJob> saveJob(@PathVariable Long userId, @PathVariable Long jobId) {
        // Check if already saved
        if (savedJobRepository.existsByUserIdAndJobId(userId, jobId)) {
            return ResponseEntity.ok(savedJobRepository.findByUserIdAndJobId(userId, jobId).orElse(null));
        }

        SavedJob savedJob = new SavedJob();
        savedJob.setUserId(userId);
        savedJob.setJobId(jobId);

        SavedJob saved = savedJobRepository.save(savedJob);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{jobId}")
    public ResponseEntity<Void> unsaveJob(@PathVariable Long userId, @PathVariable Long jobId) {
        savedJobRepository.deleteByUserIdAndJobId(userId, jobId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<SavedJob>> getSavedJobs(@PathVariable Long userId) {
        List<SavedJob> savedJobs = savedJobRepository.findByUserIdOrderBySavedDateDesc(userId);
        return ResponseEntity.ok(savedJobs);
    }

    @GetMapping("/{jobId}/exists")
    public ResponseEntity<Map<String, Boolean>> checkIfSaved(@PathVariable Long userId, @PathVariable Long jobId) {
        boolean exists = savedJobRepository.existsByUserIdAndJobId(userId, jobId);
        return ResponseEntity.ok(Map.of("saved", exists));
    }
}
