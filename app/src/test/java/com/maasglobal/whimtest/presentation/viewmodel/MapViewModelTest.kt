package com.maasglobal.whimtest.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import com.maasglobal.data.entities.GeoSearchResponses
import com.maasglobal.data.rx.RxSingleSchedulers
import com.maasglobal.domain.usecase.NearbyArticleUseCase
import com.maasglobal.whimtest.presentation.util.State
import io.reactivex.Single
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner.Silent::class)
class MapViewModelTest {


    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    @Mock
    lateinit var nearbyArticleUseCase: NearbyArticleUseCase


    private lateinit var viewModel: MapViewModel

    @Spy
    lateinit var observer: Observer<State<GeoSearchResponses>>

    @Mock
    lateinit var lifecycleOwner: LifecycleOwner
    lateinit var lifecycle: Lifecycle


    @Before
    @Throws(Exception::class)
    fun setUp() {
        lifecycle = LifecycleRegistry(lifecycleOwner)
        viewModel = MapViewModel(nearbyArticleUseCase, RxSingleSchedulers.TEST_SCHEDULER)
        observer.let { viewModel.listOfGeoSearchData.observeForever(it) }
    }



    @Test
    fun testNull() {
        `when`(nearbyArticleUseCase.call("")).thenReturn(null)
        assertNotNull(viewModel.listOfGeoSearchData)
        assertTrue(viewModel.listOfGeoSearchData.hasObservers())
    }


    @Test
    fun testApiFetchDataSuccess() {
        `when`(nearbyArticleUseCase.call("60.1831906|24.9285439")).thenReturn(Single.just(GeoSearchResponses()))
        viewModel.loadArticlesNearby("60.1831906|24.9285439")
        verify(observer)?.onChanged(State.success(data = GeoSearchResponses()))
    }


    @Test
    fun testApiFetchDataError() {
        `when`(nearbyArticleUseCase.call("60.1831906|24.9285439")).thenReturn(Single.error(Throwable("Error fetching Wikipedia Articles Data")))
        viewModel.loadArticlesNearby("60.1831906|24.9285439")
        verify(observer)?.onChanged(State.error(message = "Error fetching Wikipedia Articles Data"))
    }


    @After
    @Throws(Exception::class)
    fun tearDown() {

    }


}