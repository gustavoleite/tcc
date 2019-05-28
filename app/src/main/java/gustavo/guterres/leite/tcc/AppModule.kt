package gustavo.guterres.leite.tcc

import gustavo.guterres.leite.tcc.components.content.ContentViewItemAdapter
import gustavo.guterres.leite.tcc.utils.extensions.resource.ResourceProvider
import gustavo.guterres.leite.tcc.utils.extensions.resource.ResourceProviderImpl
import org.koin.dsl.module.module

val resourceProviderModule = module {
    single<ResourceProvider> { ResourceProviderImpl(context = get()) }
}

val viewModelModule = module {
    //viewModel { ContentViewItemViewModel(get(), get()) }
}

val factoryModule = module {
    factory { ContentViewItemAdapter(get()) }
}