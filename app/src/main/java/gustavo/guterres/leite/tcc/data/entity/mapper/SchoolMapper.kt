package gustavo.guterres.leite.tcc.data.entity.mapper

import gustavo.guterres.leite.tcc.data.entity.model.School
import gustavo.guterres.leite.tcc.data.entity.model.Student
import gustavo.guterres.leite.tcc.data.entity.output.SchoolOutput
import gustavo.guterres.leite.tcc.data.entity.output.StudentOutput

object SchoolMapper {

    fun toSchoolList(outputList: List<SchoolOutput>, studentList: List<Student>): List<School> {
        return outputList.map {
            toSchool(it, studentList)
        }
    }

    fun toSchool(output: SchoolOutput, studentList: List<Student>): School {
        return with(output) {
            School(
                name,
                ClassroomMapper.toClassroomList(classrooms, studentList),
                id,
                teacherId
            )
        }
    }
}