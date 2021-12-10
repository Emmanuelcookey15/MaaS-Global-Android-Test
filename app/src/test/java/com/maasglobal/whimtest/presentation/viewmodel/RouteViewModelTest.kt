package com.maasglobal.whimtest.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import com.maasglobal.data.entities.DirectionResponses
import com.maasglobal.data.rx.RxSingleSchedulers
import com.maasglobal.domain.usecase.DirectionUseCase
import com.maasglobal.whimtest.presentation.util.State
import io.reactivex.Single
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class RouteViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    @Mock
    lateinit var directionUseCase: DirectionUseCase


    private lateinit var viewModel: RouteViewModel

    @Spy
    lateinit var observer: Observer<State<DirectionResponses?>>

    @Mock
    lateinit var lifecycleOwner: LifecycleOwner
    lateinit var lifecycle: Lifecycle

    @Before
    @Throws(Exception::class)
    fun setUp() {

        lifecycle = LifecycleRegistry(lifecycleOwner)
        viewModel = RouteViewModel(directionUseCase, RxSingleSchedulers.TEST_SCHEDULER)
        observer.let { viewModel.pointData.observeForever(it) }
    }


    @Test
    fun testNull() {
        Mockito.`when`(directionUseCase.call("","", "")).thenReturn(null)
        Assert.assertNotNull(viewModel.pointData)
        Assert.assertTrue(viewModel.pointData.hasObservers())
    }

    @Test
    fun testApiFetchDetailsDataSuccess() {
        // Mock API response
        Mockito.`when`(directionUseCase.call("", "", "")).thenReturn(Single.just(DirectionResponses()))
        viewModel.getRoutes("", "", "")
        Mockito.verify(observer)?.onChanged(State.success(DirectionResponses()))
    }

    @Test
    fun testApiFetchDetailsDataError() {
        Mockito.`when`(directionUseCase.call("", "", "")).thenReturn(Single.error(Throwable("Error fetching Route Details")))
        viewModel.getRoutes("", "", "")
        Mockito.verify(observer)?.onChanged(State.error(message = "Error fetching Route Details"))
    }


    @After
    fun tearDown() {
    }
}