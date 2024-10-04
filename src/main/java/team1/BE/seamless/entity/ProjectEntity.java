package team1.BE.seamless.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "project")
public class ProjectEntity extends BaseEntity{

    public ProjectEntity() {

    }

    public ProjectEntity(String name, UserEntity user,
        LocalDateTime startDate,
        LocalDateTime endDate) {
        this.name = name;
        this.isDelete = 0;
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "project_id")
    private Long id;

    @Column(name = "name")
    private String name;

//    @Column(name = "view_type")
//    private Object viewType;

    @Column(name = "is_delete")
    private Integer isDelete;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(mappedBy = "projectEntity", cascade = CascadeType.ALL)
    private List<MemberEntity> memberEntities;

    @OneToMany(mappedBy = "projectEntity", cascade = CascadeType.ALL)
    private List<ProjectOption> options;

    @OneToMany(mappedBy = "projectEntity", cascade = CascadeType.ALL)
    private List<TaskEntity> taskEntity;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    //Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public Integer getIsDelete() {
        return isDelete;
    }

    public UserEntity getUser() {
        return user;
    }

    public List<MemberEntity> getMemberEntities() {
        return memberEntities;
    }

    public List<ProjectOption> getOptions() {
        return options;
    }

    public List<TaskEntity> getTaskEntity() {
        return taskEntity;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public ProjectEntity update(String name, LocalDateTime startDate, LocalDateTime endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        return this;
    }

}