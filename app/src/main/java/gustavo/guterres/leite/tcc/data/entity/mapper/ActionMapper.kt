package gustavo.guterres.leite.tcc.data.entity.mapper

import gustavo.guterres.leite.tcc.data.entity.model.Action
import gustavo.guterres.leite.tcc.data.entity.output.ActionEntity

object ActionMapper {

    fun toActionList(entityList: List<ActionEntity>): List<Action> {
        return entityList.map {
            toAction(it)
        }
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
}