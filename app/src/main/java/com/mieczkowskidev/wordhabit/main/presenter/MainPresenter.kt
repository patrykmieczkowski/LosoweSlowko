package com.mieczkowskidev.wordhabit.main.presenter

import android.content.Context
import com.google.firebase.messaging.FirebaseMessaging
import com.mieczkowskidev.wordhabit.NotificationConfig
import com.mieczkowskidev.wordhabit.NotificationProvider
import com.mieczkowskidev.wordhabit.main.contract.MainContract
import com.mieczkowskidev.wordhabit.app.prefs
import com.mieczkowskidev.wordhabit.utils.BuildTypeHelper

/**
 * Created by Patryk Mieczkowski on 15.04.2018
 */
class MainPresenter : MainContract.Presenter {

    private var view: MainContract.View? = null

    override fun attach(view: MainContract.View) {
        this.view = view

        registerToTopic()

    }

    private fun registerToTopic() {

        subscribeToTopic(prefs.topic)
        BuildTypeHelper.onlyDebug().let { view?.showInfoHeader(prefs.topic) }
    }

    private fun subscribeToTopic(topic: String) {
        FirebaseMessaging.getInstance().subscribeToTopic(topic)

    }

    private fun unsubscribeFromTopic(topic: String) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic)
    }

    override fun switchTopicClicked() {
        if (prefs.topic == NotificationConfig.DEFAULT_TOPIC) {
            switchTopicLogic(NotificationConfig.QA_TOPIC)
        } else if (prefs.topic == NotificationConfig.QA_TOPIC) {
            switchTopicLogic(NotificationConfig.DEFAULT_TOPIC)
        }
    }

    private fun switchTopicLogic(newTopic: String) {
        unsubscribeFromTopic(prefs.topic)
        prefs.topic = newTopic
        subscribeToTopic(prefs.topic)
        view?.showInfoHeader(prefs.topic)
    }

    override fun generateMockNotification(context: Context) {
        NotificationProvider().createNotificationAndReceiver(context, NotificationConfig().getMockNotification())
    }

    override fun detach() {
        this.view = null
    }
}