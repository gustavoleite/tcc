package gustavo.guterres.leite.tcc.feature

import androidx.lifecycle.ViewModel
import gustavo.guterres.leite.tcc.R
import gustavo.guterres.leite.tcc.components.action.Action
import gustavo.guterres.leite.tcc.components.content.ContentViewItem

class HomeViewModel : ViewModel() {

    val contentList: List<ContentViewItem> = listOf(
        ContentViewItem(R.drawable.ic_hundred_reais, "234"),
        ContentViewItem(R.drawable.ic_hundred_reais),
        ContentViewItem(R.drawable.ic_hundred_reais, "234")
    )

    val actionList : List<Action> = listOf(
        Action("sd2d2", text = "TEXT"),
        Action("sd2d", text = "TEXT"),
        Action("sd2d", text = "TEXT"),
        Action("sd2d24", text = "TEXT")
    )

    val text = "oi"
    val image = "kkk eae man"
}