package com.utils.lib

import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

/**
 * Goach All Rights Reserved
 *User: Goach
 *Email: goach0728@gmail.com
 *Des:
 */
@GlideModule
class GlideConfiguration:AppGlideModule() {
    override fun isManifestParsingEnabled() = false
}