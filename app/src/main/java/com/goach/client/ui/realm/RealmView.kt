package com.goach.client.ui.realm

import com.base.lib.view.Mvp
import com.data.lib.db.User
import io.realm.RealmResults

/**
 * Goach All Rights Reserved
 *User: Goach
 *Email: goach0728@gmail.com
 */
interface RealmView: Mvp.View {
    fun UpdateUI(results:RealmResults<User>)
}