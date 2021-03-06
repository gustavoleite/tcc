package gustavo.guterres.leite.tcc

import android.app.Application
import gustavo.guterres.leite.tcc.inject.adaptersModule
import gustavo.guterres.leite.tcc.inject.firebaseModule
import gustavo.guterres.leite.tcc.inject.resourceProviderModule
import gustavo.guterres.leite.tcc.inject.roomModule
import gustavo.guterres.leite.tcc.inject.viewModelModule
import org.koin.android.ext.android.startKoin

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setupDepedencyInjector()
    }

    private fun setupDepedencyInjector() {
        startKoin(
            this,
            listOf(
                viewModelModule,
                resourceProviderModule,
                adaptersModule,
                roomModule,
                firebaseModule
            )
        )
    }
}
