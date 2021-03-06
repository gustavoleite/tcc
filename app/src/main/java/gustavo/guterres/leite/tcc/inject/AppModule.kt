package gustavo.guterres.leite.tcc.inject

import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import gustavo.guterres.leite.tcc.components.action.ActionViewItemAdapter
import gustavo.guterres.leite.tcc.components.content.ContentViewItemAdapter
import gustavo.guterres.leite.tcc.components.onboarding.OnboardingViewModel
import gustavo.guterres.leite.tcc.data.entity.model.Onboarding
import gustavo.guterres.leite.tcc.data.repository.LoginRepository
import gustavo.guterres.leite.tcc.data.repository.LoginRepositoryImpl
import gustavo.guterres.leite.tcc.data.repository.HomeRepository
import gustavo.guterres.leite.tcc.data.repository.HomeRepositoryImpl
import gustavo.guterres.leite.tcc.data.repository.OriginationRepository
import gustavo.guterres.leite.tcc.data.repository.OriginationRepositoryImpl
import gustavo.guterres.leite.tcc.data.repository.StudentRepository
import gustavo.guterres.leite.tcc.data.repository.StudentRepositoryImpl
import gustavo.guterres.leite.tcc.data.room.TCCDatabase
import gustavo.guterres.leite.tcc.feature.home.HomeViewModel
import gustavo.guterres.leite.tcc.feature.home.LevelItemAdapter
import gustavo.guterres.leite.tcc.feature.level.LevelViewModel
import gustavo.guterres.leite.tcc.feature.levelonboarding.LevelOnboardingViewModel
import gustavo.guterres.leite.tcc.feature.login.LoginViewModel
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
    viewModel { LevelViewModel(get()) }
    viewModel { HomeViewModel(get(), get(), get()) }
    viewModel { LoginViewModel(get(), get(), get()) }
    viewModel { (onboarding: Onboarding) -> OnboardingViewModel(onboarding) }
    viewModel { (totalScreens: Int) -> LevelOnboardingViewModel(totalScreens) }
}

val adaptersModule = module {
    factory { ContentViewItemAdapter(get()) }
    factory { ActionViewItemAdapter(get()) }
    factory { LevelItemAdapter(get()) }
}

val roomModule = module {

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
        OriginationRepositoryImpl(auth = get(), database = get())
    } bind OriginationRepository::class

    factory {
        LoginRepositoryImpl(auth = get(), database = get())
    } bind LoginRepository::class

    factory {
        HomeRepositoryImpl(firebaseDatabase = get())
    } bind HomeRepository::class

    factory {
        StudentRepositoryImpl(database = get(), auth = get())
    } bind StudentRepository::class
}
