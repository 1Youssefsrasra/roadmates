package com.Feedback_service.Feedback_service.Feddback;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/feedbacks")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService service;

    @PostMapping
    public ResponseEntity<Integer> createOrder(
            @RequestBody @Valid FeedbackRequest request
    ) {
        return ResponseEntity.ok(this.service.createFeedback(request));
    }
    @GetMapping
    public ResponseEntity<List<FeedbackResponse>> findAll() {
        return ResponseEntity.ok(this.service.findAllfeedbacks());
    }


    @PutMapping("/{id}")
    public ResponseEntity<Void> updateFeedback(
            @PathVariable("id") Integer id,
            @RequestBody @Valid FeedbackRequest request
    ) {
        this.service.updateFeedback(id, request);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeedback(@PathVariable("id") Integer id) {
        this.service.deleteFeedback(id);
        return ResponseEntity.accepted().build();
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FeedbackResponse>> findFeedbacksByUserId(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(this.service.findFeedbacksByUserId(userId));
    }
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Test endpoint is working!");
    }

}
