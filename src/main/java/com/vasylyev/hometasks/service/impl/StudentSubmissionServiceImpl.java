package com.vasylyev.hometasks.service.impl;

import com.vasylyev.hometasks.dto.CourseDto;
import com.vasylyev.hometasks.dto.CourseWorkDto;
import com.vasylyev.hometasks.dto.StudentSubmissionDto;
import com.vasylyev.hometasks.mapper.StudentSubmissionMapper;
import com.vasylyev.hometasks.model.StudentSubmissionModel;
import com.vasylyev.hometasks.repository.StudentSubmissionRepository;
import com.vasylyev.hometasks.service.CourseService;
import com.vasylyev.hometasks.service.CourseWorkService;
import com.vasylyev.hometasks.service.StudentSubmissionService;
import com.vasylyev.hometasks.telegram.TelegramNotifier;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
@Service
@AllArgsConstructor
public class StudentSubmissionServiceImpl implements StudentSubmissionService {

    private final CourseService courseService;
    private final CourseWorkService courseWorkService;
    private final StudentSubmissionMapper studentSubmissionMapper;
    private final StudentSubmissionRepository studentSubmissionRepository;
    private final TelegramNotifier telegramNotifier;

    @Override
    public StudentSubmissionDto save(StudentSubmissionDto studentSubmissionDto) {
        StudentSubmissionModel studentSubmissionModel = studentSubmissionRepository.save(studentSubmissionMapper.toModel(studentSubmissionDto));
        log.info("Student submission saved. course id:" + studentSubmissionModel.getCourseId() + ". submission id:" + studentSubmissionModel.getId());
        return studentSubmissionMapper.toDto(studentSubmissionModel);
    }

    @Override
    public StudentSubmissionDto findById(String id) {
        StudentSubmissionModel studentSubmissionModel = studentSubmissionRepository.findById(id).orElse(null);
        if (Objects.nonNull(studentSubmissionModel)) {
            return studentSubmissionMapper.toDto(studentSubmissionModel);
        }
        return null;
    }

    @Override
    public List<StudentSubmissionDto> findByCourseWorkId(String courseworkId) {
        return studentSubmissionRepository.findByCourseWorkId(courseworkId).stream()
                .map(s -> studentSubmissionMapper.toDto(s))
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentSubmissionDto> fillCourseAndCourseWorkById(List<StudentSubmissionDto> studentSubmissionDtoList) {
        List<String> courseIds = studentSubmissionDtoList.stream()
                .map(c -> c.getCourseId())
                .collect(Collectors.toList());
        List<CourseDto> courseDtoList = courseService.findByIds(courseIds);
        studentSubmissionDtoList.stream()
                .forEach(cw -> cw.setCourse(
                        courseDtoList.stream().filter(c -> c.getId().equals(cw.getCourseId())).findFirst().get()
                        )
                );

        List<String> courseworkIds = studentSubmissionDtoList.stream()
                .map(c -> c.getCourseWorkId())
                .collect(Collectors.toList());
        List<CourseWorkDto> courseWorkDtoList = courseWorkService.findByIds(courseworkIds);
        studentSubmissionDtoList.stream()
                .forEach(cw -> cw.setCourseWork(
                        courseWorkDtoList.stream().filter(c -> c.getId().equals(cw.getCourseWorkId())).findFirst().get()
                        )
                );

        return studentSubmissionDtoList;
    }

    @Override
    public List<String> findAllCourseWorkIdWithGrades() {
        return studentSubmissionRepository.findAllCourseWorkWithGrade();
    }

    @Override
    public void addStudentSubmission(List<StudentSubmissionDto> studentSubmissionDtoList) {
        List<String> studentSubmissionIds = studentSubmissionDtoList.stream()
                .map(c -> c.getId())
                .collect(Collectors.toList());
        List<StudentSubmissionModel> studentSubmissionModelList = studentSubmissionRepository.findAllById(studentSubmissionIds);

        for (StudentSubmissionDto studentSubmissionDto : studentSubmissionDtoList) {
            StudentSubmissionModel studentSubmissionExist = studentSubmissionModelList.stream()
                    .filter(c -> c.getId().equals(studentSubmissionDto.getId()))
                    //.filter(c -> Double.compare(c.getAssignedGrade(), studentSubmissionDto.getAssignedGrade()) == 0)
                    .findFirst().orElse(null);
            Double existingGrade = 0D;
            if (nonNull(studentSubmissionExist)){
                existingGrade = studentSubmissionExist.getAssignedGrade();
            }
            if (isNull(studentSubmissionExist) ||
                    !(Double.compare(existingGrade, studentSubmissionDto.getAssignedGrade()) == 0)) {
                studentSubmissionRepository.save(studentSubmissionMapper.toModel(studentSubmissionDto));
                log.info("Student submission saved. course id:" + studentSubmissionDto.getCourseId() + ". submission id:" + studentSubmissionDto.getId());

                //send to telegram
                if (!(Double.compare(existingGrade, studentSubmissionDto.getAssignedGrade()) == 0)) {
                    telegramNotifier.sendToTelegram(studentSubmissionDto.getCourse().getAccount(),
                            "New Grade: "
                                    + "\nAccount: " + studentSubmissionDto.getCourse().getAccount().getName()
                                    + "\nCourse: " + studentSubmissionDto.getCourse().getName()
                                    + "\nGrade: " + studentSubmissionDto.getAssignedGrade()
                                    + "\nTitle: " + studentSubmissionDto.getCourseWork().getTitle()
                                    + "\nLink: " + studentSubmissionDto.getAlternateLink()
                    );
                }
            }
        }
    }

}
