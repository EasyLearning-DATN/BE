package com.poly.easylearning.service.impl;

import com.poly.easylearning.constant.DefaultValueConstants;
import com.poly.easylearning.constant.ResourceBundleConstant;
import com.poly.easylearning.entity.Image;
import com.poly.easylearning.exception.ApiRequestException;
import com.poly.easylearning.payload.response.GetOneLessonResponse;
import com.poly.easylearning.payload.response.ListResponse;
import com.poly.easylearning.payload.response.RestResponse;
import com.poly.easylearning.payload.request.LessonRequest;
import com.poly.easylearning.entity.Lesson;
import com.poly.easylearning.exception.DataNotFoundException;
import com.poly.easylearning.repo.IImageRepo;
import com.poly.easylearning.repo.ILessonRepo;
import com.poly.easylearning.payload.response.GetListLessonResponse;
import com.poly.easylearning.service.ILessonService;
import com.poly.easylearning.utils.DateUtil;
import com.poly.easylearning.utils.SecurityContextUtils;
import com.poly.easylearning.utils.ValidateUserUpdateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class LessonServiceImpl implements ILessonService {
    private final ILessonRepo lessonRepo;
    private final IImageRepo imageRepo;

    @Override
    public RestResponse<ListResponse<GetListLessonResponse>> getListLesson(String keyword, String id, String dateStart, String dateEnd, String createdBy, String isPublic, PageRequest pageRequest) {
        UUID idMapper = null;
        UUID createdByMapper = null;
        Boolean isPublicMapper = null;
        if (!id.isEmpty()) {
            try {
                idMapper = UUID.fromString(id);
            } catch (Exception e) {
                throw new InvalidParameterException(ResourceBundleConstant.DAT_8002);
            }
        }
        if (!createdBy.isEmpty()) {
            try {
                createdByMapper = UUID.fromString(createdBy);
            } catch (Exception e) {
                throw new InvalidParameterException(ResourceBundleConstant.DAT_8002);
            }
        }
        if (!isPublic.isEmpty() && (isPublic.equalsIgnoreCase("false") || isPublic.equalsIgnoreCase("true"))) {
            isPublicMapper = Boolean.parseBoolean(isPublic);
        }


        Page<Lesson> pageReponse = lessonRepo.searchLesson(keyword, idMapper, DateUtil.fromString(dateStart), DateUtil.fromString(dateEnd), createdByMapper, isPublicMapper, pageRequest);
        List<GetListLessonResponse> lessonResponses = pageReponse.get().map(GetListLessonResponse::fromLesson).toList();
        ListResponse<GetListLessonResponse> listResponse = ListResponse.build(pageReponse.getTotalPages(), lessonResponses);
        return RestResponse.ok(ResourceBundleConstant.LSN_4003,
                listResponse);
    }

    @Override
    public RestResponse<GetListLessonResponse> getOneLesson(UUID id) throws DataNotFoundException {
        Lesson lesson = lessonRepo.getLessonById(id).orElseThrow(() -> new DataNotFoundException(ResourceBundleConstant.LSN_4001));
        GetListLessonResponse lessonResponse = GetListLessonResponse.fromLesson(lesson);

        return RestResponse.ok(ResourceBundleConstant.LSN_4004, lessonResponse);
    }

    @Override
    public RestResponse<GetOneLessonResponse> getOneLessonIncrementAccess(UUID id) throws DataNotFoundException {
        Lesson lesson = lessonRepo.getLessonById(id).orElseThrow(() -> new DataNotFoundException(ResourceBundleConstant.LSN_4001));
        lesson.setAccessTimes(lesson.getAccessTimes() + 1);
        Lesson lessonNew = lessonRepo.save(lesson);
        GetOneLessonResponse lessonResponse = GetOneLessonResponse.fromLesson(lessonNew);

        return RestResponse.ok(ResourceBundleConstant.LSN_4004, lessonResponse);
    }

    @Override
    public RestResponse<GetListLessonResponse> createLesson(LessonRequest lessonRequest) {
        Image image = imageRepo.findByPublicId(lessonRequest.getImageId())
                .orElse(imageRepo.findByPublicId(DefaultValueConstants.IMAGE_LESSON_DEFAULT)
                        .orElseThrow(() -> new DataNotFoundException(ResourceBundleConstant.IMG_3005)));

        Lesson newLesson = Lesson.builder()
                .name(lessonRequest.getName())
                .description(lessonRequest.getDescription())
                .isPublic(lessonRequest.isPublic())
                .image(image)
                .userInfo(SecurityContextUtils.getCurrentUser().getUserInfo())
                .accessTimes(0)
                .build();

        Lesson lesson = lessonRepo.save(newLesson);
        GetListLessonResponse response = GetListLessonResponse.fromLesson(lesson);
        return RestResponse.created(ResourceBundleConstant.LSN_4002, response);
    }

    @Override
    public RestResponse<GetListLessonResponse> updateLesson(UUID id, LessonRequest lessonRequest) throws DataNotFoundException {
        Lesson existingLesson =
                lessonRepo.getLessonById(id).orElseThrow(() -> new DataNotFoundException(ResourceBundleConstant.LSN_4001));
        ValidateUserUpdateUtils.checkUserUpdate(existingLesson);
        existingLesson.setName(lessonRequest.getName());
        existingLesson.setDescription(lessonRequest.getDescription());
        existingLesson.setPublic(lessonRequest.isPublic());
        Image image = imageRepo.findByPublicId(lessonRequest.getImageId())
                .orElse(existingLesson.getImage());

        existingLesson.setImage(image);
        Lesson lesson = lessonRepo.save(existingLesson);

        GetListLessonResponse response = GetListLessonResponse.fromLesson(lesson);
        return RestResponse.accepted(ResourceBundleConstant.LSN_4008, response);
    }

    @Override
    public void deleteLesson(UUID id) throws DataNotFoundException {
        Lesson existingLesson = lessonRepo.getLessonById(id).orElseThrow(() -> new DataNotFoundException(ResourceBundleConstant.LSN_4001));
        ValidateUserUpdateUtils.checkUserUpdate(existingLesson);
        existingLesson.setIsDeleted(true);
        lessonRepo.save(existingLesson);
    }

    @Override
    public Lesson findLessonEntityById(UUID lessonID) {
        return lessonRepo.getLessonById(lessonID)
                .orElseThrow(() -> new ApiRequestException(ResourceBundleConstant.LSN_4001));
    }
}
