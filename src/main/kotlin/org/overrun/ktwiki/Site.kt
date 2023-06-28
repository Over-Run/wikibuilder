/*
 * MIT License
 *
 * Copyright (c) 2023 Overrun Organization
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 */

package org.overrun.ktwiki

const val LANG_EN_US = "en-US"
const val LANG_ZH_HANS = "zh-Hans"
const val LANG_ZH_HANT = "zh-Hant"

/** Gets the root directory for the current language of the wiki. */
fun rootDir(pageID: PageID): String = "../".repeat(pageID.path.count { it == '/' })

/** Gets the root directory of the wiki. */
fun rootDir(pageID: PageID, lang: String): String =
    "../".repeat(pageID.path.count { it == '/' } + (if (lang != LANG_EN_US) 1 else 0))

/**
 * The website.
 *
 * @param[name] the name of this wiki.
 * @author squid233
 * @since 0.1.0
 */
class Site(val name: String, val lang: String) {
    private val stylesheets: MutableList<Stylesheet> = ArrayList()
    private val pages: MutableList<Page> = ArrayList()

    operator fun Stylesheet.unaryPlus() {
        stylesheets += this
    }

    operator fun Page.unaryPlus() {
        pages += this
    }

    fun generate() {
        stylesheets.forEach { it.generate(this, "docs") }
        pages.forEach { it.generate(this, "docs") }
    }
}

fun site(
    name: String,
    lang: String = LANG_EN_US,
    action: Site.() -> Unit
) {
    Site(name, lang).also(action).generate()
}
