package gustavo.guterres.leite.tcc.data.entity.mapper

import gustavo.guterres.leite.tcc.data.entity.model.Student
import gustavo.guterres.leite.tcc.data.entity.model.StudentLevel
import gustavo.guterres.leite.tcc.data.entity.output.StudentLevelOutput
import gustavo.guterres.leite.tcc.data.entity.output.StudentOutput

object StudentMapper {

    fun toStudentList(outputList: List<StudentOutput>): List<Student> {
        return outputList.map {
            toStudent(it)
        }
    }

    fun toStudent(output: StudentOutput): Student {
        return with(output) {
            Student(
                id,
                name,
                toStudentLevelList(studentLevel ?: arrayListOf()),
                currentLevel,
                accumulatedPoints
            )
        }
    }

    fun toStudentLevelList(output: MutableList<StudentLevelOutput>): MutableList<StudentLevel> {
        return output.map {
            toStudentLevel(it)
        }.toMutableList()
    }

    fun toStudentLevel(output: StudentLevelOutput): StudentLevel {
        return with(output) {
            StudentLevel(
                id,
                hits,
                mistakes
            )
        }
    }
}
