package ru.holofox.anicoubs

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import androidx.lifecycle.SavedStateHandle
import androidx.preference.PreferenceManager
import com.jakewharton.threetenabp.AndroidThreeTen

import com.vk.api.sdk.VK
import com.vk.api.sdk.VKTokenExpiredHandler

import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

import ru.holofox.anicoubs.features.domain.network.api.CoubApiService
import ru.holofox.anicoubs.features.domain.network.api.HolofoxApiService
import ru.holofox.anicoubs.features.domain.network.api.VKApiService
import ru.holofox.anicoubs.features.data.db.AnicoubsDatabase
import ru.holofox.anicoubs.features.data.network.ConnectivityInterceptorImpl
import ru.holofox.anicoubs.features.data.network.datasources.CoubNetworkDataSourceImpl
import ru.holofox.anicoubs.features.data.network.datasources.HolofoxNetworkDataSourceImpl
import ru.holofox.anicoubs.features.data.network.datasources.VKNetworkDataSourceImpl
import ru.holofox.anicoubs.features.data.providers.ConnectivityProviderImpl
import ru.holofox.anicoubs.features.data.providers.LocaleManagerProviderImpl
import ru.holofox.anicoubs.features.data.providers.UnitProviderImpl
import ru.holofox.anicoubs.features.data.repositories.CoubRepositoryImpl
import ru.holofox.anicoubs.features.data.repositories.HolofoxRepositoryImpl
import ru.holofox.anicoubs.features.data.repositories.vk.VKUsersRepositoryImpl
import ru.holofox.anicoubs.features.data.repositories.vk.VKVideoRepositoryImpl
import ru.holofox.anicoubs.features.data.repositories.vk.VKWallRepositoryImpl
import ru.holofox.anicoubs.features.domain.network.ConnectivityInterceptor
import ru.holofox.anicoubs.features.domain.network.data.CoubNetworkDataSource
import ru.holofox.anicoubs.features.domain.network.data.HolofoxNetworkDataSource
import ru.holofox.anicoubs.features.domain.network.data.VKNetworkDataSource
import ru.holofox.anicoubs.features.domain.providers.ConnectivityProvider
import ru.holofox.anicoubs.features.domain.providers.LocaleManagerProvider
import ru.holofox.anicoubs.features.domain.providers.UnitProvider
import ru.holofox.anicoubs.features.domain.repositories.CoubRepository
import ru.holofox.anicoubs.features.domain.repositories.HolofoxRepository
import ru.holofox.anicoubs.features.domain.repositories.vk.VKUsersRepository
import ru.holofox.anicoubs.features.domain.repositories.vk.VKVideoRepository
import ru.holofox.anicoubs.features.domain.repositories.vk.VKWallRepository
import ru.holofox.anicoubs.ui.LoginActivity
import ru.holofox.anicoubs.ui.main.MainViewProvider
import ru.holofox.anicoubs.ui.postponed.list.PostponedListViewModelProvider
import ru.holofox.anicoubs.ui.timeline.list.TimeLineListViewModelProvider

class AnicoubsApplication: Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@AnicoubsApplication))

        bind() from singleton { AnicoubsDatabase(instance()) }
        bind() from singleton { instance<AnicoubsDatabase>().postFeedDao() }
        bind() from singleton { instance<AnicoubsDatabase>().vkDao()}

        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind<SavedStateHandle>() with singleton { SavedStateHandle() }

        // Api
        bind() from singleton { CoubApiService(instance()) }
        bind() from singleton { VKApiService(instance()) }
        bind() from singleton { HolofoxApiService(instance()) }

        // Data source
        bind<CoubNetworkDataSource>() with singleton { CoubNetworkDataSourceImpl(instance()) }
        bind<VKNetworkDataSource>() with singleton { VKNetworkDataSourceImpl(instance()) }
        bind<HolofoxNetworkDataSource>() with singleton { HolofoxNetworkDataSourceImpl(instance()) }

        // Repository
        bind<CoubRepository>() with singleton { CoubRepositoryImpl(instance(), instance()) }
        bind<HolofoxRepository>() with singleton { HolofoxRepositoryImpl(instance()) }
        bind<VKWallRepository>() with singleton { VKWallRepositoryImpl(instance(), instance()) }
        bind<VKVideoRepository>() with singleton { VKVideoRepositoryImpl(instance(), instance()) }
        bind<VKUsersRepository>() with singleton { VKUsersRepositoryImpl(instance()) }

        // Provider
        bind<UnitProvider>() with singleton { UnitProviderImpl(instance()) }
        bind<LocaleManagerProvider>() with singleton { LocaleManagerProviderImpl }
        bind<ConnectivityProvider>() with singleton { ConnectivityProviderImpl(instance()) }

        bind() from provider { MainViewProvider(instance(), instance(), instance(), instance(),
            instance(), instance(), instance()) }
        bind() from provider { PostponedListViewModelProvider(instance(), instance(), instance()) }
        bind() from provider { TimeLineListViewModelProvider(instance(), instance(), instance()) }
    }

    private val localeManagerProvider: LocaleManagerProvider by instance()

    private val tokenTracker = object: VKTokenExpiredHandler {
        override fun onTokenExpired() {
            LoginActivity.startFrom(this@AnicoubsApplication)
        }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(localeManagerProvider.setLocale(base))
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        VK.addTokenExpiredHandler(tokenTracker)

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false)
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        localeManagerProvider.setLocale(this)
    }

}