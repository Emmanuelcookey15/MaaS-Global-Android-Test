package com.maasglobal.whimtest.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import com.google.gson.JsonObject
import com.maasglobal.data.rx.RxSingleSchedulers
import com.maasglobal.domain.usecase.ArticleDetailsUseCase
import com.maasglobal.whimtest.presentation.util.State
import io.reactivex.Single
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class DetailsViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    @Mock
    lateinit var articleDetailsUseCase: ArticleDetailsUseCase


    private lateinit var viewModel: DetailsViewModel

    @Spy
    lateinit var observer: Observer<State<JsonObject>>

    @Mock
    lateinit var lifecycleOwner: LifecycleOwner
    lateinit var lifecycle: Lifecycle


    @Before
    @Throws(Exception::class)
    fun setUp() {
        lifecycle = LifecycleRegistry(lifecycleOwner)
        viewModel = DetailsViewModel(articleDetailsUseCase, RxSingleSchedulers.TEST_SCHEDULER)
        observer.let { viewModel.geoDetailData.observeForever(it) }
    }


    @Test
    fun testNull() {
        Mockito.`when`(articleDetailsUseCase.call("")).thenReturn(null)
        Assert.assertNotNull(viewModel.geoDetailData)
        Assert.assertTrue(viewModel.geoDetailData.hasObservers())
    }

    @Test
    fun testApiFetchDetailsDataSuccess() {
        // Mock API response
        Mockito.`when`(articleDetailsUseCase.call("234455")).thenReturn(Single.just(JsonObject()))
        verify(observer)?.onChanged(State.success(JsonObject()))
    }



    @Test
    fun testApiFetchDetailsDataError() {

        Mockito.`when`(articleDetailsUseCase.call("234455")).thenReturn(Single.error(Throwable("Error fetching Articles Details")))
        viewModel.getDetailOfPOI("234455")
        verify(observer)?.onChanged(State.error(message = "Error fetching Articles Details"))

    }



    @After
    fun tearDown() {
    }
}