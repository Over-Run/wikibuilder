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

package org.overrun.ktwiki.theme.builtin

import org.overrun.ktwiki.*
import java.io.BufferedReader

/**
 * @author squid233
 * @since 0.1.0
 */
class BuiltinTheme(private val indexID: PageID) {
    val builtinCss = Stylesheet(
        "builtin",
        BuiltinTheme::class.java.getResourceAsStream("/builtin.css")!!.bufferedReader().use(BufferedReader::readText)
    )
    val builtinCssList: List<Stylesheet> = listOf(builtinCss)

    var sidebar = sidebar { }
    var footer = footer { }

    fun main(content: TagListNode.() -> Unit): Node = div(id = "main", content = content)
    fun sidebar(content: TagListNode.() -> Unit): Node = div(id = "sidebar", content = content)
    fun footer(content: TagListNode.() -> Unit): Node = div(id = "footer", content = content)
    fun page(pageId: PageID, content: TagListNode.() -> Unit) = Page(pageId, stylesheets = builtinCssList) {
        +main(content)
        +sidebar
        +footer
    }

    fun links(content: TagListNode.() -> Unit): Node = div(`class` = "links", content = content)
    fun link(content: TagListNode.() -> Unit): Node = div(`class` = "link", content = content)
    fun about(content: TagListNode.() -> Unit): Node = div(`class` = "about", content = content)

    fun color(color: String, content: String): Node = literal("<span style=\"color: $color;\">$content</span>")
    fun spoiler(content: String): Node = literal("<span class=\"spoiler\">$content</span>")

    fun relativeLink(pageId: PageID, lang: String = LANG_EN_US, content: String = pageId.name(lang)): Node =
        RelativeLink(
            href = {
                if (it == pageId.id) return@RelativeLink null
                if (it == indexID.id) return@RelativeLink pageId.path
                return@RelativeLink "../${pageId.path}"
            },
            content = content, classCurr = "pageCurr"
        )
}

fun builtin(indexId: PageID, action: BuiltinTheme.() -> Unit) {
    BuiltinTheme(indexId).action()
}
