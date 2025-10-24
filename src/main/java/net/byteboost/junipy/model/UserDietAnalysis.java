package net.byteboost.junipy.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.CompoundIndex;

import java.time.Instant;
import net.byteboost.junipy.dto.ReviewStatusEnum;

@Document(collection = "diet_analyses")
@CompoundIndex(name = "unique_diet_analysis", def = "{'dietId': 1}", unique = true) // one analysis per diet
public class UserDietAnalysis {

    @Id
    private String id;

    @Indexed
    private String dietId;

    @Indexed
    private String subjectUserId;

    private String nutritionistUserId;

    private ReviewStatusEnum status = ReviewStatusEnum.IN_PROGRESS;

    private Instant submittedAt;

    private Instant reviewedAt;

    private String comments;

    public UserDietAnalysis() {}

    public UserDietAnalysis(String dietId, String subjectUserId) {
        this.dietId = dietId;
        this.subjectUserId = subjectUserId;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getDietId() { return dietId; }
    public void setDietId(String dietId) { this.dietId = dietId; }

    public String getSubjectUserId() { return subjectUserId; }
    public void setSubjectUserId(String subjectUserId) { this.subjectUserId = subjectUserId; }

    public String getNutritionistUserId() { return nutritionistUserId; }
    public void setNutritionistUserId(String nutritionistUserId) { this.nutritionistUserId = nutritionistUserId; }

    public ReviewStatusEnum getStatus() { return status; }
    public void setStatus(ReviewStatusEnum status) { 
        this.status = status; 
        if (status == ReviewStatusEnum.IN_PROGRESS) {
            this.submittedAt = null;
            this.reviewedAt = null;
            this.submittedAt =  Instant.now();
        }
        if (status == ReviewStatusEnum.APPROVED || status == ReviewStatusEnum.DECLINED) {
            this.reviewedAt = Instant.now();
        }
    }

    public Instant getSubmittedAt() { return submittedAt; }
    public Instant getReviewedAt() { return reviewedAt; }

    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }

}
