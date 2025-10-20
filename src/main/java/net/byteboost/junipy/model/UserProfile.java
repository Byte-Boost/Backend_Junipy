package net.byteboost.junipy.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "user_profiles")
public class UserProfile {
    @Id
    private String id;

    @Indexed(unique = true)
    private String userId;

    private String birthDate;
    private String gender;
    private String occupation;
    private String consultationReason;

    private List<String> healthConditions;
    private List<String> allergies;
    private List<String> surgeries;

    private String activityType;
    private String activityFrequency;
    private String activityDuration;

    private String sleepQuality;
    private String wakeDuringNight;
    private String bowelFrequency;
    private String stressLevel;

    private String alcoholConsumption;
    private String smoking;
    private String hydrationLevel;

    private String takesMedication;
    private String medicationDetails;

    public UserProfile() {}

    public UserProfile(String userId) {
        this.userId = userId;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getBirthDate() { return birthDate; }
    public void setBirthDate(String birthDate) { this.birthDate = birthDate; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getOccupation() { return occupation; }
    public void setOccupation(String occupation) { this.occupation = occupation; }

    public String getConsultationReason() { return consultationReason; }
    public void setConsultationReason(String consultationReason) { this.consultationReason = consultationReason; }

    public List<String> getHealthConditions() { return healthConditions; }
    public void setHealthConditions(List<String> healthConditions) { this.healthConditions = healthConditions; }

    public List<String> getAllergies() { return allergies; }
    public void setAllergies(List<String> allergies) { this.allergies = allergies; }

    public List<String> getSurgeries() { return surgeries; }
    public void setSurgeries(List<String> surgeries) { this.surgeries = surgeries; }

    public String getActivityType() { return activityType; }
    public void setActivityType(String activityType) { this.activityType = activityType; }

    public String getActivityFrequency() { return activityFrequency; }
    public void setActivityFrequency(String activityFrequency) { this.activityFrequency = activityFrequency; }

    public String getActivityDuration() { return activityDuration; }
    public void setActivityDuration(String activityDuration) { this.activityDuration = activityDuration; }

    public String getSleepQuality() { return sleepQuality; }
    public void setSleepQuality(String sleepQuality) { this.sleepQuality = sleepQuality; }

    public String getWakeDuringNight() { return wakeDuringNight; }
    public void setWakeDuringNight(String wakeDuringNight) { this.wakeDuringNight = wakeDuringNight; }

    public String getBowelFrequency() { return bowelFrequency; }
    public void setBowelFrequency(String bowelFrequency) { this.bowelFrequency = bowelFrequency; }

    public String getStressLevel() { return stressLevel; }
    public void setStressLevel(String stressLevel) { this.stressLevel = stressLevel; }

    public String getAlcoholConsumption() { return alcoholConsumption; }
    public void setAlcoholConsumption(String alcoholConsumption) { this.alcoholConsumption = alcoholConsumption; }

    public String getSmoking() { return smoking; }
    public void setSmoking(String smoking) { this.smoking = smoking; }

    public String getHydrationLevel() { return hydrationLevel; }
    public void setHydrationLevel(String hydrationLevel) { this.hydrationLevel = hydrationLevel; }

    public String getTakesMedication() { return takesMedication; }
    public void setTakesMedication(String takesMedication) { this.takesMedication = takesMedication; }

    public String getMedicationDetails() { return medicationDetails; }
    public void setMedicationDetails(String medicationDetails) { this.medicationDetails = medicationDetails; }
}
