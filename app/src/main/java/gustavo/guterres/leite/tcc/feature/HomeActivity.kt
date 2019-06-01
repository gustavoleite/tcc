package gustavo.guterres.leite.tcc.feature

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import gustavo.guterres.leite.tcc.R
import gustavo.guterres.leite.tcc.components.content.ContentViewItem
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        home_content_view.setList(
            listOf(
                ContentViewItem(R.drawable.ic_hundred_reais, "234"),
                ContentViewItem(R.drawable.ic_hundred_reais),
                ContentViewItem(R.drawable.ic_hundred_reais, "234")
            )
        )

//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
    }
}