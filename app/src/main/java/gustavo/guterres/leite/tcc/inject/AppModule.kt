package gustavo.guterres.leite.tcc.inject

import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import gustavo.guterres.leite.tcc.components.action.ActionViewItemAdapter
import gustavo.guterres.leite.tcc.components.content.ContentViewItemAdapter
import gustavo.guterres.leite.tcc.components.onboarding.OnboardingViewModel
import gustavo.guterres.leite.tcc.data.entity.model.Onboarding
import gustavo.guterres.leite.tcc.data.repository.*
import gustavo.guterres.leite.tcc.data.room.TCCDatabase
import gustavo.guterres.leite.tcc.feature.home.HomeViewModel
import gustavo.guterres.leite.tcc.feature.home.LevelItemAdapter
import gustavo.guterres.leite.tcc.feature.level.LevelViewModel
import gustavo.guterres.leite.tcc.feature.login.LoginViewModel
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
    viewModel { HomeViewModel(get(), get()) }
    viewModel { OriginationViewModel(get(), get()) }
    viewModel { LoginViewModel(get(), get()) }
    viewModel { (onboarding: Onboarding) -> OnboardingViewModel(onboarding) }
}

val adaptersModule = module {
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

val firebaseModule = module {

    factory {
        FirebaseAuth.getInstance()
    }

    factory {
        FirebaseDatabase.getInstance()
    }

    factory {
        OriginationRepositoryImpl(get())
    } bind OriginationRepository::class

    factory {
        LoginRepositoryImpl(get())
    } bind LoginRepository::class

    factory {
        HomeRepositoryImpl(get())
    } bind HomeRepository::class
}