package net.byteboost.junipy.dto;

import jakarta.validation.constraints.NotNull;

public class UserDietAnalysisSendDto {
    @NotNull(message = "Review Status is required")
    private ReviewStatusEnum status;
    private String comments;

    public UserDietAnalysisSendDto() {}
    public UserDietAnalysisSendDto(ReviewStatusEnum status, String comments) {
        this.status = status;
        this.comments = comments;
    }

    public ReviewStatusEnum getStatus() { return status; }
    public void setStatus(ReviewStatusEnum status) { this.status = status; }
    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }
    
}
