package com.poly.easylearning.service.impl;

import com.poly.easylearning.constant.ResourceBundleConstant;
import com.poly.easylearning.payload.response.RestResponse;
import com.poly.easylearning.dto.LessonDTO;
import com.poly.easylearning.entity.Lesson;
import com.poly.easylearning.exception.DataNotFoundException;
import com.poly.easylearning.repo.ILessonRepo;
import com.poly.easylearning.payload.response.LessonResponse;
import com.poly.easylearning.service.ILessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

@RequiredArgsConstructor
public class LessonServiceImpl implements ILessonService {
    private final ILessonRepo lessonRepo;

    @Override
    public RestResponse<Page<LessonResponse>> getListLesson(String keyword, PageRequest pageRequest) {
        return RestResponse.ok(ResourceBundleConstant.LSN_4003, lessonRepo.searchLesson(keyword, pageRequest)
                .map(LessonResponse::fromLesson));
    }

    @Override
    public RestResponse<LessonResponse> getOneLesson(UUID id) throws DataNotFoundException {
        return lessonRepo.getLessonById(id)
                .map(LessonResponse::fromLesson)
                .map(item -> RestResponse.ok(ResourceBundleConstant.LSN_4004, item))
                .orElseThrow(() -> new DataNotFoundException(ResourceBundleConstant.LSN_4004));
    }

    @Override
    public RestResponse<LessonResponse> createLesson(LessonDTO lessonDTO) {
        Lesson newLesson = Lesson.builder()
                .name(lessonDTO.getName())
                .description(lessonDTO.getDescription())
                .isPublic(lessonDTO.isPublic())
                .imageUrl(lessonDTO.getImageUrl())
                .build();

        lessonRepo.save(newLesson);
        LessonResponse response = LessonResponse.fromLesson(newLesson);
        return RestResponse.created(ResourceBundleConstant.LSN_4002, response);
    }

    @Override
    public RestResponse<LessonResponse> updateLesson(UUID id, LessonDTO lessonDTO) throws DataNotFoundException {
        Lesson existingLesson =
                lessonRepo.getLessonById(id).orElseThrow(() -> new DataNotFoundException(ResourceBundleConstant.LSN_4001));
        existingLesson.setName(lessonDTO.getName());
        existingLesson.setDescription(lessonDTO.getDescription());
        existingLesson.setPublic(lessonDTO.isPublic());
        existingLesson.setImageUrl(lessonDTO.getImageUrl());

        lessonRepo.save(existingLesson);

        LessonResponse response = LessonResponse.fromLesson(existingLesson);
        return RestResponse.accepted(ResourceBundleConstant.LSN_4005, response);
    }

    @Override
    public void deleteLesson(UUID id) throws DataNotFoundException {
        //Xóa mềm
        Lesson existingLesson = lessonRepo.getLessonById(id).orElseThrow(() -> new DataNotFoundException(ResourceBundleConstant.LSN_4001));
        existingLesson.setIsDeleted(true);
        lessonRepo.save(existingLesson);
    }
}
