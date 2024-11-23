package com.Feedback_service.Feedback_service.Feddback;
import org.springframework.stereotype.Service;
import com.Feedback_service.Feedback_service.Exceptions.BusinessException;
import com.Feedback_service.Feedback_service.User.UserClient;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final UserClient userclient;
    private final FeedbackRepository repository;
    private final FeedbackMapper mapper;
    public Integer createFeedback(FeedbackRequest request) {
//check if the customer exists -> openFeign
var user = this.userclient.findById(request.userId())
                .orElseThrow(() -> new BusinessException("Cannot create feedback:: No user exists with the provided ID"));


        var feedback = this.repository.save(mapper.toFeedback(request));
     return feedback.getId();}


    public List<FeedbackResponse> findAllfeedbacks() {
        return this.repository.findAll()
                .stream()
                .map(this.mapper::toResponse)
                .collect(Collectors.toList());
    }

    public void updateFeedback(Integer id, FeedbackRequest request) {
        var feedback = this.repository.findById(id)
                .orElseThrow(() -> new BusinessException("Feedback not found with ID: " + id));
        feedback.setComment(request.comment());
        feedback.setRating(request.rating());
        feedback.setUserId(request.userId());
        this.repository.save(feedback);
    }

    public void deleteFeedback(Integer id) {
        if (!this.repository.existsById(id)) {
            throw new BusinessException("Feedback not found with ID: " + id);
        }
        this.repository.deleteById(id);
    }

    public List<FeedbackResponse> findFeedbacksByUserId(String userId) {
        // Vérifier si l'utilisateur existe
        var user = this.userclient.findById(userId)
                .orElseThrow(() -> new BusinessException("No user found with ID: " + userId));

        // Trouver les feedbacks liés à cet utilisateur
        return this.repository.findByUserId(userId)
                .stream()
                .map(this.mapper::toResponse)
                .collect(Collectors.toList());
    }
}


