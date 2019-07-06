package gustavo.guterres.leite.tcc.inject

import androidx.room.Room
import gustavo.guterres.leite.tcc.components.action.ActionViewItemAdapter
import gustavo.guterres.leite.tcc.components.content.ContentViewItemAdapter
import gustavo.guterres.leite.tcc.data.entity.model.Step
import gustavo.guterres.leite.tcc.data.repository.LevelRepository
import gustavo.guterres.leite.tcc.data.repository.LevelRepositoryImpl
import gustavo.guterres.leite.tcc.data.room.TCCDatabase
import gustavo.guterres.leite.tcc.feature.home.HomeViewModel
import gustavo.guterres.leite.tcc.feature.home.LevelItemAdapter
import gustavo.guterres.leite.tcc.feature.level.LevelViewModel
import gustavo.guterres.leite.tcc.feature.origination.OriginationViewModel
import gustavo.guterres.leite.tcc.feature.step.StepViewModel
import gustavo.guterres.leite.tcc.utils.extensions.resource.ResourceProvider
import gustavo.guterres.leite.tcc.utils.extensions.resource.ResourceProviderImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val resourceProviderModule = module {
    single<ResourceProvider> { ResourceProviderImpl(androidContext()) }
}

val viewModelModule = module {
    viewModel { StepViewModel() }
    viewModel { LevelViewModel(get(), get()) }
    viewModel { HomeViewModel() }
    viewModel { OriginationViewModel() }
}

val factoryModule = module {
    factory { ContentViewItemAdapter(get()) }
    factory { ActionViewItemAdapter(get()) }
    factory { LevelItemAdapter() }
}

val roomModule = module {

    single {
        LevelRepositoryImpl(get()) as LevelRepository
    }

    single {
        Room
            .inMemoryDatabaseBuilder(
                androidApplication(),
                TCCDatabase::class.java
            )
            .build()
    }

    single {
        get<TCCDatabase>().levelDao()
    }
}