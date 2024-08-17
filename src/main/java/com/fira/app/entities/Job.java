package com.fira.app.entities;

import com.fira.app.enums.JobStatus;
import com.fira.app.repository.AccountRepository;
import com.fira.app.repository.UserJobRepository;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Job {
    @jakarta.persistence.Id
    private String Id;

    private String title;

    @OneToMany
    private List<UserJob> userJobs = new ArrayList<>();

    private String attachment;

    @ManyToOne
    private Account manager;

    @OneToOne
    private JobDetail jobDetail;

    @CreationTimestamp
    private LocalDate createdAt;

    @UpdateTimestamp
    private LocalDate updatedAt;


    public Job() {
        this.Id = UUID.randomUUID().toString();
    }

    public void addStaffToJob(Account user, UserJobRepository userJobRepository) {
        UserJob userJob = new UserJob();
        userJob.setProgress(0);
        userJob.setCachedProgress(0);
        userJob.setStatus(JobStatus.PROCESS);
        userJob.setUser(user);
        userJob.setJobId(this.Id);
        this.userJobs.add(userJobRepository.save(userJob));
    }

    public void removeStaff(Account user, UserJobRepository userJobRepository) {
        UserJob userJob = userJobRepository.findByUserAndJobId(user, this.Id);
        if (userJob != null) {
            this.userJobs.remove(userJob);
        }
    }
}
