package com.ohdab.mistakenote.controller.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetMistakeNoteInfoOfClassroomRes {

    private List<String> students;
    private List<GetMistakeNoteInfoRes> mistakeNoteInfo;
}
