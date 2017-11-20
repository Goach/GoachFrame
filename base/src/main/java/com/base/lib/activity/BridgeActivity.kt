package com.base.lib.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.base.lib.utils.ActivityResultHelp

/**
 * Goach All Rights Reserved
 *User: Goach
 *Email: goach0728@gmail.com
 */
class BridgeActivity:Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(null)
        val intent = intent
        startActivityForResult(intent.getParcelableExtra<Intent>("intent"),intent.getIntExtra("requestCode", 0))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        ActivityResultHelp.onActivityResult(requestCode, resultCode, data)
        finish()
    }
}