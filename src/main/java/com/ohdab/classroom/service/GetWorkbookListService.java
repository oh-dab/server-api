package com.ohdab.classroom.service;

import com.ohdab.classroom.service.dto.ClassroomWorkbookListDto.Response;
import com.ohdab.classroom.service.usecase.GetWorkbookListUsecase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetWorkbookListService implements GetWorkbookListUsecase {

    @Override
    public List<Response> getWorkbookListByClassroomId(long classroomId) {
        return null;
    }
}
