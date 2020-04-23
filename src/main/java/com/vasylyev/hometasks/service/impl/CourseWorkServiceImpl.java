package com.vasylyev.hometasks.service.impl;

import com.vasylyev.hometasks.dto.CourseDto;
import com.vasylyev.hometasks.dto.CourseWorkDto;
import com.vasylyev.hometasks.exception.ElementNotFoundException;
import com.vasylyev.hometasks.google.GoogleSheetsService;
import com.vasylyev.hometasks.mapper.CourseWorkMapper;
import com.vasylyev.hometasks.model.CourseWorkModel;
import com.vasylyev.hometasks.repository.CourseWorkRepository;
import com.vasylyev.hometasks.service.CourseService;
import com.vasylyev.hometasks.service.CourseWorkService;
import com.vasylyev.hometasks.telegram.TelegramNotifier;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
@Service
@AllArgsConstructor
public class CourseWorkServiceImpl implements CourseWorkService {

    private final CourseWorkRepository courseWorkRepository;
    private final CourseWorkMapper courseWorkMapper;
    private final CourseService courseService;
    private final TelegramNotifier telegramNotifier;
    private final GoogleSheetsService googleSheetsService;

    private final String regex = "[^\\p{L}\\p{N}\\p{P}\\p{Z}]";

    @Override
    public CourseWorkDto addCourseWork(CourseWorkDto courseWorkDto) {
        CourseWorkModel courseWorkModel = courseWorkRepository.save(courseWorkMapper.toModel(courseWorkDto));
        log.info("Course Work saved. id:" + courseWorkModel.getId());
        return courseWorkMapper.toDto(courseWorkModel);
    }

    @Override
    public void addCourseWorks(List<CourseWorkDto> courseWorkDtoList) {

        Pattern pattern = Pattern.compile(regex, Pattern.UNICODE_CHARACTER_CLASS);

        List<String> courseWorkIds = courseWorkDtoList.stream()
                .map(c -> c.getId())
                .collect(Collectors.toList());
        List<CourseWorkModel> courseWorkModelList = courseWorkRepository.findAllById(courseWorkIds);
        for (CourseWorkDto courseWorkDto : courseWorkDtoList) {
            if (isNull(courseWorkModelList.stream()
                    .filter(c -> c.getId().equals(courseWorkDto.getId())).findFirst().orElse(null))) {

                if (nonNull(courseWorkDto.getDescription())) {
                    Matcher matcher = pattern.matcher(courseWorkDto.getDescription());
                    courseWorkDto.setDescription(matcher.replaceAll(""));
                }

                courseWorkRepository.save(courseWorkMapper.toModel(courseWorkDto));
                log.info("Course Work saved. id:" + courseWorkDto.getId());

                //send to telegram
                telegramNotifier.sendToTelegram("New Hometask: "
                        + "\nCourse: " + courseWorkDto.getCourse().getName()
                        + "\nTitle: " + courseWorkDto.getTitle()
                        + "\nLink: " + courseWorkDto.getAlternateLink()
                        + (nonNull(courseWorkDto.getDueDate()) ? "\nDue date: " + courseWorkDto.getDueDate().toString() : "")
                );

                //update google sheets
                try {
                    googleSheetsService.appendRow(courseWorkDto);
                } catch (IOException e) {
                    log.error("Error updating google sheet: " + e.getMessage());
                } catch (GeneralSecurityException e) {
                    log.error("Error updating google sheet: " + e.getMessage());
                }

            } //else {
                //log.info("Course Work already exist. id:" + courseWorkDto.getId());
            //}
        }
    }

    @Override
    public CourseWorkDto findById(String id) {
        return courseWorkMapper.toDto(courseWorkRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Course Work not found. id:" + id)));
    }

    @Override
    public List<CourseWorkDto> findAll() {
        return courseWorkRepository.findAll().stream()
                .map(c -> courseWorkMapper.toDto(c))
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseWorkDto> fillCourseById(List<CourseWorkDto> courseWorkDtoList) {

        List<String> courseIds = courseWorkDtoList.stream()
                .map(c -> c.getCourseId())
                .collect(Collectors.toList());
        List<CourseDto> courseDtoList = courseService.findByIds(courseIds);
        courseWorkDtoList.stream()
                .forEach(cw -> cw.setCourse(
                        courseDtoList.stream().filter(c -> c.getId().equals(cw.getCourseId())).findFirst().get()
                        )
                );
        return courseWorkDtoList;
    }
}
