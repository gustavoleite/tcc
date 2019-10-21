package gustavo.guterres.leite.tcc.data.entity.mapper

import gustavo.guterres.leite.tcc.data.entity.model.Classroom
import gustavo.guterres.leite.tcc.data.entity.model.Student
import gustavo.guterres.leite.tcc.data.entity.output.ClassroomOutput
import gustavo.guterres.leite.tcc.data.entity.output.StudentOutput

object ClassroomMapper {

    fun toClassroomList(outputList: List<ClassroomOutput>, studentList: List<Student>): List<Classroom> {
        return outputList.mapIndexed { index, classroom ->
            toClassroom(classroom, studentList).apply { id = index.toString() }
        }
    }

    fun toClassroom(output: ClassroomOutput, studentList: List<Student>): Classroom {
        return with(output) {
            val newStudents = mutableListOf<Student>()

            studentList.map { originStudent ->
                output.students.map { classStudent ->
                    if (originStudent.id == classStudent) {
                        newStudents.add(originStudent)
                    }
                }
            }

            Classroom(
                name,
                newStudents,
                id
            )
        }
    }
}