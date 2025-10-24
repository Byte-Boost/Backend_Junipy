package net.byteboost.junipy.dto;

import jakarta.validation.constraints.NotNull;

public class UserDailyDietCreateDto {
    private String id;
    private String userId;
    @NotNull(message = "dayOfWeek is required")
    private DayOfWeekEnum dayOfWeek;
    @NotNull(message = "markdownDiet is required")
    private String markdownDiet;

    public UserDailyDietCreateDto() {}

    public UserDailyDietCreateDto(DayOfWeekEnum dayOfWeek, String markdownDiet) {
        this.dayOfWeek = dayOfWeek;
        this.markdownDiet = markdownDiet;
    }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public DayOfWeekEnum getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(DayOfWeekEnum dayOfWeek) { this.dayOfWeek = dayOfWeek; }

    public String getMarkdownDiet() { return markdownDiet; }
    public void setMarkdownDiet(String markdownDiet) { this.markdownDiet = markdownDiet; }
}
