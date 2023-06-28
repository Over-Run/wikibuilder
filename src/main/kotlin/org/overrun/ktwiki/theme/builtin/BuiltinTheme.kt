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
import java.time.Clock
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * @author squid233
 * @since 0.1.0
 */
class BuiltinTheme(
    private val indexID: PageID,
    private val orgName: String?,
    private val license: String?,
    private val source: String?
) {
    val builtinCss = Stylesheet(
        "builtin",
        BuiltinTheme::class.java.getResourceAsStream("/builtin.css")!!.bufferedReader().use(BufferedReader::readText)
    )
    val builtinCssList: List<Stylesheet> = listOf(builtinCss)

    var sidebar = sidebar { }
    var footer = footer {
        orgName?.also {
            -"© ${LocalDate.now().year} $it"
            +br
        }
        license?.also {
            -"License: $it"
            if (source == null) +br
        }
        source?.also {
            if (license != null) -" · "
            +a(href = it, content = "View source")
            +br
        }
        -"Last generated: ${LocalDateTime.now(Clock.systemUTC()).format(DateTimeFormatter.ISO_DATE_TIME)}"
    }

    fun main(content: TagListNode.(PageID) -> Unit): Node = div(id = "main", content = content)
    fun sidebar(content: TagListNode.(PageID) -> Unit): Node = div(id = "sidebar", content = content)
    fun footer(content: TagListNode.(PageID) -> Unit): Node = div(id = "footer", content = content)
    fun page(pageID: PageID, content: TagListNode.(PageID) -> Unit) = Page(pageID, stylesheets = builtinCssList) {
        +main(content)
        +sidebar
        +footer
    }

    fun links(content: TagListNode.(PageID) -> Unit): Node = div(`class` = "links", content = content)
    fun link(content: TagListNode.(PageID) -> Unit): Node = div(`class` = "link", content = content)
    fun about(content: TagListNode.(PageID) -> Unit): Node = div(`class` = "about", content = content)

    fun color(color: String, content: String): Node = literal("<span style=\"color: $color;\">$content</span>")
    fun spoiler(content: String): Node = literal("<span class=\"spoiler\">$content</span>")

    fun relativeLink(pageID: PageID, lang: String = LANG_EN_US, content: String = pageID.name(lang)): Node =
        RelativeLink(
            href = {
                if (it.id == pageID.id) return@RelativeLink null
                if (it.id == indexID.id) return@RelativeLink pageID.path
                return@RelativeLink "../${pageID.path}"
            },
            content = content, classCurr = "pageCurr"
        )
}

fun builtin(
    indexId: PageID,
    orgName: String? = null,
    license: String? = null,
    source: String? = null,
    action: BuiltinTheme.() -> Unit
) {
    BuiltinTheme(indexId, orgName, license, source).action()
}
