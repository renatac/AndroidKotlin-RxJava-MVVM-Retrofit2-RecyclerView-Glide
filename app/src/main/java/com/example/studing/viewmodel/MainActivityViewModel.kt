package com.example.studing.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.studing.network.BookListModel
import com.example.studing.network.RetroInstance
import com.example.studing.network.RetroService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.Observer

class MainActivityViewModel: ViewModel() {

    lateinit var bookList: MutableLiveData<BookListModel>
    init {
        bookList = MutableLiveData()
    }

    fun getBookListObserver(): MutableLiveData<BookListModel>{
        return bookList
    }

    fun makeApiCall(query: String) {
        val retroInstance = RetroInstance.getRetrofitInstance().create(RetroService::class.java)
        retroInstance.getBookListFromApi(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getBookListObserverRx())
    }

    private fun getBookListObserverRx(): Observer<BookListModel> {
        return object : Observer<BookListModel> {
            override fun onComplete() {
                //hide progress indicator
            }

            override fun onSubscribe(d: Disposable) {
                //start showing progress indicator
            }

            override fun onNext(t: BookListModel) {
                bookList.postValue(t)
            }

            override fun onError(e: Throwable) {
                bookList.postValue(null)
            }
        }

    }
}