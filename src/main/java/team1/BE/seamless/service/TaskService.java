package team1.BE.seamless.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team1.BE.seamless.DTO.TaskDTO;
import team1.BE.seamless.entity.MemberEntity;
import team1.BE.seamless.entity.ProjectEntity;
import team1.BE.seamless.entity.TaskEntity;
import team1.BE.seamless.mapper.TaskMapper;
import team1.BE.seamless.repository.MemberRepository;
import team1.BE.seamless.repository.ProjectRepository;
import team1.BE.seamless.repository.TaskRepository;
import team1.BE.seamless.util.errorException.BaseHandler;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;
    private final TaskMapper taskMapper;

    @Autowired
    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository, MemberRepository memberRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.memberRepository = memberRepository;
        this.taskMapper = taskMapper;
    }

    public List<TaskEntity> getTaskList(Long projectId) {
        ProjectEntity projectEntity = projectRepository.findById(projectId).orElseThrow(() -> new BaseHandler(HttpStatus.NOT_FOUND, "존재하지 않는 프로젝트"));

        return taskRepository.findByProjectEntity(projectEntity);
    }

    public TaskEntity createTask(TaskDTO req) {
        ProjectEntity projectEntity = projectRepository.findById(req.getProjectId()).orElseThrow(() -> new BaseHandler(HttpStatus.NOT_FOUND, "존재하지 않는 프로젝트"));

        MemberEntity memberEntity = memberRepository.findById(req.getOwnerId()).orElseThrow(() -> new BaseHandler(HttpStatus.NOT_FOUND, "존재하지 않는 멤버"));

        TaskEntity taskEntity = taskMapper.toEntity(
            req.getName(),
            req.getRemark(),
            projectEntity,
            memberEntity,
            req.getStartDate(),
            req.getEndDate()
        );

        taskRepository.save(taskEntity);
        return taskEntity;
    }

    @Transactional
    public TaskEntity updateTask(Long taskId, TaskDTO req) {
        TaskEntity taskEntity = taskRepository.findById(taskId).orElseThrow(() -> new BaseHandler(HttpStatus.NOT_FOUND, "존재하지 않는 태스크"));

        ProjectEntity projectEntity = projectRepository.findById(req.getProjectId()).orElseThrow(() -> new BaseHandler(HttpStatus.NOT_FOUND, "존재하지 않는 프로젝트"));

        MemberEntity memberEntity = memberRepository.findById(req.getOwnerId()).orElseThrow(() -> new BaseHandler(HttpStatus.NOT_FOUND, "존재하지 않는 멤버"));

        TaskEntity updatedTask = taskEntity.update(
            req.getName(),
            req.getRemark(),
            req.getProgress(),
            req.getIsDeleted(),
            projectEntity,
            memberEntity,
            req.getStartDate(),
            req.getEndDate()
        );

        taskRepository.save(updatedTask);
        return updatedTask;
    }

    public Long deleteTask(Long taskId) {
        TaskEntity taskEntity = taskRepository.findById(taskId)
            .orElseThrow(() -> new BaseHandler(HttpStatus.NOT_FOUND, "존재하지 않는 태스크"));

        taskRepository.delete(taskEntity);
        return taskEntity.getId();
    }
}
