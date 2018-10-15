package com.cyl.musiclake.download.ui

import com.cyl.musiclake.base.BasePresenter
import com.cyl.musiclake.download.DownloadLoader
import com.cyl.musiclake.event.DownloadEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import javax.inject.Inject

/**
 * Author   : D22434
 * version  : 2018/1/24
 * function :
 */

class DownloadPresenter @Inject
constructor() : BasePresenter<DownloadContract.View>(), DownloadContract.Presenter {

    override fun attachView(view: DownloadContract.View) {
        super.attachView(view)
        EventBus.getDefault().register(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun reloadDownloadMusic(event: DownloadEvent) {
        loadDownloadMusic(event.isCache)
        loadDownloading(event.isCache)
    }

    override fun detachView() {
        super.detachView()
        EventBus.getDefault().unregister(this)
    }

    override fun loadDownloadMusic(isCache: Boolean) {
        mView?.showLoading()
        doAsync {
            val data = DownloadLoader.getDownloadList(isCache)
            uiThread {
                mView?.showSongs(data)
            }
        }
    }

    override fun loadDownloading(isCache: Boolean) {
        doAsync {
            val data = DownloadLoader.getDownloadingList()
            uiThread {
                mView?.showDownloadList(data)
            }
        }
    }

}
