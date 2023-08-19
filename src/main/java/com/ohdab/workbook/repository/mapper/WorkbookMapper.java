package com.ohdab.workbook.repository.mapper;

import com.ohdab.classroom.service.dto.ClassroomDetailDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WorkbookMapper {

    List<ClassroomDetailDto.WorkbookInfoDto> findAllWorkbookForClassroomInfo(
            List<Long> workbookIdList);
}
