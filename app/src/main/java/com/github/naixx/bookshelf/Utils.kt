package com.github.naixx.bookshelf

import android.app.ProgressDialog
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.bumptech.glide.Glide
import common.handleError
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

@BindingAdapter("android:src", "placeholder", requireAll = false)
fun ImageView.loadImage(url: String?, placeholder: Drawable?) {
    Glide.with(this).load(url).placeholder(placeholder).into(this)
}

internal val main = Handler(Looper.getMainLooper())

fun <T> Observable<T>.withProgress(context: Context, cancellable: Boolean = false): Observable<T> {
    val p = ProgressDialog(context)
    p.setCancelable(cancellable)
    return this.observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { main.post { p.show() } }
        .doOnTerminate { main.post { p.dismiss() } }
}

fun <T> Observable<T>.withProgress(view: View): Observable<T> {
    return this.observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { view.post { view.visibility = View.VISIBLE } }
        .doOnTerminate { view.post { view.visibility = View.GONE } }
}

interface RxObserveDsl : LifecycleOwner {

    fun getContext(): Context?

    fun <T> Observable<T>.withProgress(cancellable: Boolean = false) = this.withProgress(getContext()!!, cancellable)

    fun <T> Observable<T>.observe(onNext: (T) -> Unit = {}): Disposable = observe(onNext, *emptyArray())

    /**
     * A convenience method to show toasts for backend api error codes. Does not respect fragment's view lifecycle
     */
    fun <T> Observable<T>.observe(onNext: (T) -> Unit = {}, vararg apiErrors: Pair<Int, Int>): Disposable {
        val subscription = this
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                onNext,
                getContext()?.handleError(*apiErrors)
            )
        this@RxObserveDsl.lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                if (event == Lifecycle.Event.ON_DESTROY)
                    subscription.dispose()
            }
        })
        return subscription
    }
}
