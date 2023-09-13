<div align="center">
<h1>Oh!Dab</h1> 
<p>수학학원 학생들의 오답관리 서비스</p>

<div align="center">
    <img src="https://img.shields.io/badge/Java-11-182D44?style=for-the-badge&logoColor=white"/>
    <img src="https://img.shields.io/badge/Spring Boot-2.7.14-80EA6E?style=for-the-badge&logoColor=white"/>
    <img src="https://img.shields.io/badge/Spring Data JPA-2.7.14-80EA6E?style=for-the-badge&logoColor=white"/>
    <img src="https://img.shields.io/badge/MyBatis-2.2.0-4D3D3F?style=for-the-badge&logoColor=white"/>
    <img src="https://img.shields.io/badge/JUnit-5-DC514B?style=for-the-badge&logoColor=white"/>
</div>
<br />
</div>

## Activity

![Alt](https://repobeats.axiom.co/api/embed/b57f402847a7063c4a1e2420951848e40a4741ea.svg "Repobeats analytics image")

## API 명세서
- [**명세서**](https://oh-dab.github.io/server-api/index.html)

## ERD 다이어그램
```mermaid
classDiagram
direction BT
class CLASSROOM {
   datetime(6) created_at
   datetime(6) updated_at
   varchar(255) classroomInfo_description
   int classroomInfo_grade
   varchar(255) classroomInfo_name
   bigint teacher_id
   bigint classroom_id
}
class MEMBER {
   datetime(6) created_at
   datetime(6) updated_at
   varchar(255) member_name
   varchar(255) member_password
   varchar(255) status
   bigint member_id
}
class MEMBER_AUTHORITY {
   bigint member_id
   varchar(255) authorities_role
}
class MISTAKENOTE {
   datetime(6) created_at
   datetime(6) updated_at
   bigint student_id
   bigint workbook_id
   bigint mistakenote_id
}
class MISTAKE_RECORD {
   int count
   bigint mistakenote_id
   int problem_number
}
class STUDENT_LIST {
   bigint classroom_id
   bigint students_id
}
class WORKBOOK {
   datetime(6) created_at
   datetime(6) updated_at
   bigint classroom_teacher_id
   varchar(255) workbook_description
   int workbook_ending_number
   varchar(255) workbook_name
   int workbook_starting_number
   bigint workbook_id
}
class WORKBOOK_LIST {
   bigint classroom_id
   bigint workbooks_id
}
class hibernate_sequence {
   bigint next_val
}

MEMBER_AUTHORITY  -->  MEMBER : member_id
MISTAKE_RECORD  -->  MISTAKENOTE : mistakenote_id
STUDENT_LIST  -->  CLASSROOM : classroom_id
WORKBOOK_LIST  -->  CLASSROOM : classroom_id
```

## 팀원
<table>
  <tbody>
    <tr>
      <td align="center" valign="top" width="5%"><a href="http://github.com/simhani1"><img src="https://avatars.githubusercontent.com/u/48800281?v=4"/></a></td>
      <td align="center" valign="top" width="5%"><a href="https://github.com/linirini"><img src="https://avatars.githubusercontent.com/u/101927543?v=4"/></a></td>
      <td align="center" valign="top" width="5%"><a href="https://github.com/Fixtar"><img src="https://avatars.githubusercontent.com/u/58902013?v=4"/></a></td>
    </tr>
    <tr>
        <td align="center"><a href="https://github.com/oh-dab/server-api/pulls?q=is%3Apr+is%3Aclosed+assignee%3Asimhani1" title="Closed Pull Requests">심종한</a></td>
        <td align="center"><a href="https://github.com/oh-dab/server-api/pulls?q=is%3Apr+is%3Aclosed+assignee%3Alinirini" title="Closed Pull Requests">이예린</a></td>
        <td align="center"><a href="https://github.com/oh-dab/server-api/pulls?q=is%3Apr+is%3Aclosed+assignee%3AFixtar" title="Closed Pull Requests">황성하</a></td>
    </tr>
  </tbody>
</table>