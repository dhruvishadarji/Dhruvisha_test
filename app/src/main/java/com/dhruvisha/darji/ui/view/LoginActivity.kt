package com.dhruvisha.darji.view

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.dhruvisha.darji.R
import com.dhruvisha.darji.utils.SharedPrefsHelp
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_login.*
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity() {
    private var isEmailOk: Boolean = false
    private var isPasswordOk: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (SharedPrefsHelp.getBoolean(this, "logIn", false)) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        RxTextView.afterTextChangeEvents(edt_emailid)
            .skipInitialValue()
            .map {
                updateLoginButtonState()
                txt_email.error = null
                isEmailOk=true
                it.view().text.toString()
            }
            .debounce(1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread())
            .compose(verifyEmailPattern)
            .compose(retryWhenError {
                txt_email.error = it.message
                isEmailOk=false
                updateLoginButtonState()

            })
            .subscribe()

        RxTextView.afterTextChangeEvents(edt_password)
            .skipInitialValue()
            .map {
                updateLoginButtonState()
                txt_password.error = null
                isPasswordOk=true
                it.view().text.toString()
            }
            .debounce(1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread())
            .compose(lengthGreaterThanSix)
            .compose(retryWhenError {
                txt_password.error = it.message
                updateLoginButtonState()
                isPasswordOk=false

            })
            .subscribe()
        updateLoginButtonState()
        acbtn_signin.setOnClickListener {
            clpb_loader.visibility=View.VISIBLE
            if (edt_emailid.text.toString()=="hello@yopmail.com" && edt_password.text.toString()=="Password@123"){
                clpb_loader.visibility=View.GONE
                SharedPrefsHelp.setBoolean(this, "logIn", true);
                 val intent=Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                Snackbar.make(root_view,"enter valid credentials",Snackbar.LENGTH_LONG).show()
                clpb_loader.visibility=View.GONE
            }
        }
    }

    private fun updateLoginButtonState() {
        when {
            !(edt_emailid.text.toString().trim().isEmpty()
                    || edt_password.text.toString().trim().isEmpty()) -> {
                acbtn_signin.isEnabled = isEmailOk && isPasswordOk
            }
            else -> {
                acbtn_signin.isEnabled = false
            }


        }

    }
    private inline fun retryWhenError(crossinline onError: (ex: Throwable) -> Unit): ObservableTransformer<String, String> = ObservableTransformer { observable ->
        observable.retryWhen { errors ->
            errors.flatMap {
                onError(it)
                Observable.just("")
            }
        }
    }

    private val lengthGreaterThanSix = ObservableTransformer<String, String> { observable ->
        observable.flatMap {
            Observable.just(it).map { it.trim() }
                .filter { it.length > 6 }
                .singleOrError()
                .onErrorResumeNext {
                    if (it is NoSuchElementException) {
                        Single.error(Exception("Length should be greater than 6"))
                    } else {
                        Single.error(it)
                    }
                }
                .toObservable()
        }
    }

    private val verifyEmailPattern = ObservableTransformer<String, String> { observable ->
        observable.flatMap {
            Observable.just(it).map { it.trim() }
                .filter {
                    Patterns.EMAIL_ADDRESS.matcher(it).matches()
                }
                .singleOrError()
                .onErrorResumeNext {
                    if (it is NoSuchElementException) {
                        Single.error(Exception("Invalid EmailId"))
                    } else {
                        Single.error(it)
                    }
                }
                .toObservable()

        }
    }




}