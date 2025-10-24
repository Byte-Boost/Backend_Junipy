package net.byteboost.junipy.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;

import net.byteboost.junipy.dto.DayOfWeekEnum;

@Document(collection = "user_daily_diets")
@CompoundIndexes({
    @CompoundIndex(name = "unique_user_day", def = "{'userId': 1, 'dayOfWeek': 1}", unique = true)
})
public class UserDailyDiet {
    @Id
    private String id;

    private String userId;

    private DayOfWeekEnum dayOfWeek;

    private String markdownDiet;

    public UserDailyDiet() {}

    public UserDailyDiet(String userId, DayOfWeekEnum dayOfWeek, String markdownDiet) {
        this.userId = userId;
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
