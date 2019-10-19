package gustavo.guterres.leite.tcc.data.entity.mapper

import gustavo.guterres.leite.tcc.data.entity.model.Student
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
                hits,
                mistakes,
                currentLevel,
                accumulatedPoints
            )
        }
    }
}