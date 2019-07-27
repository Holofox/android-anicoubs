package ru.holofox.anicoubs

import android.app.Application
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

import ru.holofox.anicoubs.data.db.*
import ru.holofox.anicoubs.data.network.*
import ru.holofox.anicoubs.data.network.api.TimeLineApiService
import ru.holofox.anicoubs.data.network.data.TimeLineNetworkDataSource
import ru.holofox.anicoubs.data.network.data.TimeLineNetworkDataSourceImpl
import ru.holofox.anicoubs.data.network.data.VKWallDataSource
import ru.holofox.anicoubs.data.network.data.VKNetworkDataSourceImpl
import ru.holofox.anicoubs.data.provider.ConnectivityProvider
import ru.holofox.anicoubs.data.provider.ConnectivityProviderImpl
import ru.holofox.anicoubs.data.provider.UnitProvider
import ru.holofox.anicoubs.data.provider.UnitProviderImpl
import ru.holofox.anicoubs.data.repository.AnicoubsRepository
import ru.holofox.anicoubs.data.repository.AnicoubsRepositoryImpl
import ru.holofox.anicoubs.data.repository.VKWallRepository
import ru.holofox.anicoubs.data.repository.VKWallRepositoryImpl
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
        bind() from singleton { TimeLineApiService(instance()) }

        // Data source
        bind<TimeLineNetworkDataSource>() with singleton { TimeLineNetworkDataSourceImpl(instance()) }
        bind<VKWallDataSource>() with singleton { VKNetworkDataSourceImpl(instance()) }

        // Repository
        bind<AnicoubsRepository>() with singleton { AnicoubsRepositoryImpl(instance(), instance()) }
        bind<VKWallRepository>() with singleton { VKWallRepositoryImpl(instance(), instance()) }

        // Provider
        bind<UnitProvider>() with singleton { UnitProviderImpl(instance()) }
        bind<ConnectivityProvider>() with singleton { ConnectivityProviderImpl(instance()) }
        bind() from provider { MainViewProvider(instance()) }
        bind() from provider { PostponedListViewModelProvider(instance(), instance()) }
        bind() from provider { TimeLineListViewModelProvider(instance(), instance(), instance()) }
    }

    private val tokenTracker = object: VKTokenExpiredHandler {
        override fun onTokenExpired() {
            LoginActivity.startFrom(this@AnicoubsApplication)
        }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        VK.addTokenExpiredHandler(tokenTracker)
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false)
    }
}