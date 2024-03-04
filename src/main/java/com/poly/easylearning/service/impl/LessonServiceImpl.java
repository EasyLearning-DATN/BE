package com.poly.easylearning.service.impl;

import com.poly.easylearning.constant.ResourceBundleConstant;
import com.poly.easylearning.exception.ApiRequestException;
import com.poly.easylearning.payload.response.ListResponse;
import com.poly.easylearning.payload.response.RestResponse;
import com.poly.easylearning.payload.request.LessonRequest;
import com.poly.easylearning.entity.Lesson;
import com.poly.easylearning.exception.DataNotFoundException;
import com.poly.easylearning.repo.ILessonRepo;
import com.poly.easylearning.payload.response.LessonResponse;
import com.poly.easylearning.service.ILessonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class LessonServiceImpl implements ILessonService {
    private final ILessonRepo lessonRepo;

    @Override
    public RestResponse<ListResponse<LessonResponse>> getListLesson(String keyword, PageRequest pageRequest) {
        Page<Lesson> pageReponse = lessonRepo.searchLesson(keyword, pageRequest);
        List<LessonResponse> lessonResponses = pageReponse.get().map(LessonResponse::fromLesson).toList();
        ListResponse<LessonResponse> listResponse = ListResponse.build(pageReponse.getTotalPages(), lessonResponses);
        return RestResponse.ok(ResourceBundleConstant.LSN_4003,
                listResponse);
    }

    @Override
    public RestResponse<LessonResponse> getOneLesson(UUID id) throws DataNotFoundException {
        Lesson lesson = lessonRepo.getLessonById(id).orElseThrow(() -> new DataNotFoundException(ResourceBundleConstant.LSN_4001));
        LessonResponse lessonResponse = LessonResponse.fromLesson(lesson);

        return RestResponse.ok(ResourceBundleConstant.LSN_4004, lessonResponse);
    }

    @Override
    public RestResponse<LessonResponse> createLesson(LessonRequest lessonRequest) {
        Lesson newLesson = Lesson.builder()
                .name(lessonRequest.getName())
                .description(lessonRequest.getDescription())
                .isPublic(lessonRequest.isPublic())
                .imageUrl(lessonRequest.getImageUrl())
                .build();

        Lesson lesson = lessonRepo.save(newLesson);
        LessonResponse response = LessonResponse.fromLesson(lesson);
        return RestResponse.created(ResourceBundleConstant.LSN_4002, response);
    }

    @Override
    public RestResponse<LessonResponse> updateLesson(UUID id, LessonRequest lessonRequest) throws DataNotFoundException {
        Lesson existingLesson =
                lessonRepo.getLessonById(id).orElseThrow(() -> new DataNotFoundException(ResourceBundleConstant.LSN_4001));
        existingLesson.setName(lessonRequest.getName());
        existingLesson.setDescription(lessonRequest.getDescription());
        existingLesson.setPublic(lessonRequest.isPublic());
        existingLesson.setImageUrl(lessonRequest.getImageUrl());

        Lesson lesson = lessonRepo.save(existingLesson);

        LessonResponse response = LessonResponse.fromLesson(lesson);
        return RestResponse.accepted(ResourceBundleConstant.LSN_4008, response);
    }

    @Override
    public void deleteLesson(UUID id) throws DataNotFoundException {
        Lesson existingLesson = lessonRepo.getLessonById(id).orElseThrow(() -> new DataNotFoundException(ResourceBundleConstant.LSN_4001));
        existingLesson.setIsDeleted(true);
        lessonRepo.save(existingLesson);
    }

    @Override
    public Lesson findLessonEntityById(UUID lessonID) {
        return lessonRepo.getLessonById(lessonID)
                .orElseThrow(()-> new ApiRequestException(ResourceBundleConstant.LSN_4001));
    }
}
