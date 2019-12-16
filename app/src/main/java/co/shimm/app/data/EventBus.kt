package co.shimm.app.data

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

object EventBus {
    val publisher : PublishSubject<Any> = PublishSubject.create()

    inline fun <reified T> subscribe(): Observable<T>{
        return publisher.filter{
            it is T
        }.map{
            it as T
        }
    }

    fun post(event: Any){
        publisher.onNext(event)
    }
}