package gustavo.guterres.leite.tcc.data.entity.mapper

import gustavo.guterres.leite.tcc.data.entity.model.Action
import gustavo.guterres.leite.tcc.data.entity.output.ActionEntity
import gustavo.guterres.leite.tcc.data.entity.output.ActionOutput
import java.lang.Exception

object ActionMapper {

    fun toActionList(entityList: List<ActionEntity>): List<Action> {
        return entityList.map {
            toAction(it)
        }
    }

    fun toList(entityList: List<ActionOutput>?): List<Action> {
        return entityList?.map {
            toAction(it)
        } ?: throw Exception("No action found")
    }

    fun toAction(entity: ActionEntity): Action {
        return with(entity) {
            Action(
                id.toString(),
                text,
                ImageMapper.toDrawableId(image)
            )
        }
    }

    fun toAction(entity: ActionOutput): Action {
        return with(entity) {
            Action(
                id.toString(),
                text,
                ImageMapper.toDrawableId(image)
            )
        }
    }
}
