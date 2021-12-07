package com.maasglobal.whimtest.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import com.maasglobal.data.entities.GeoSearchResponses
import com.maasglobal.domain.usecase.NearbyArticleUseCase
import com.maasglobal.whimtest.presentation.util.State
import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.Callable


@RunWith(MockitoJUnitRunner::class)
class MapViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    @Mock
    lateinit var nearbyArticleUseCase: NearbyArticleUseCase


    private lateinit var viewModel: MapViewModel

    @Spy
    lateinit var observer: Observer<State<GeoSearchResponses>>

    @Mock
    var lifecycleOwner: LifecycleOwner? = null
    var lifecycle: Lifecycle? = null


    @Before
    fun setUp() {
//        MockitoAnnotations.initMocks(this)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler {
                scheduler: Callable<Scheduler?>? -> Schedulers.trampoline() }
        lifecycle = lifecycleOwner?.let { LifecycleRegistry(it) }
        viewModel = MapViewModel(nearbyArticleUseCase)
        observer.let { viewModel.listOfGeoSearchData.observeForever(it) }
    }


    @Test
    fun testNull() {
        `when`(nearbyArticleUseCase.call("")).thenReturn(null)
        assertNotNull(viewModel.listOfGeoSearchData)
        viewModel.listOfGeoSearchData.hasObservers().let { assertTrue(it) }
    }



    @After
    fun tearDown() {

    }
}