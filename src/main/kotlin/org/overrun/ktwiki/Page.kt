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

import java.nio.file.Files
import kotlin.io.path.Path

/**
 * An identifier of a page.
 *
 * @param[id] the unique identifier for the page. For special pages, start with "_"
 * @author squid233
 * @since 0.1.0
 */
data class PageID(val id: String, val name: (String) -> String, val path: String = "${name(LANG_EN_US)}/")

/**
 * A wiki page.
 *
 * @param[id] the identifier of the page.
 * @author squid233
 * @since 0.1.0
 */
class Page(
    private val id: PageID,
    private val stylesheets: List<Stylesheet> = emptyList(),
    action: Page.() -> Unit
) : ListBackedNode() {
    init {
        action()
    }

    fun generate(site: Site, basePath: String) {
        val finalPath = Path(basePath)
            .let { if (site.lang != LANG_EN_US) it.resolve(site.lang) else it }
            .resolve(id.path)
        Files.createDirectories(finalPath)
        Files.writeString(finalPath.resolve("index.html"), buildString {
            appendLine(
                """
                <!-- auto generated file. DO NOT EDIT -->
                <!DOCTYPE html>
                <html lang="${site.lang}">
                <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1">
                <title>${id.name(site.lang)} - ${site.name}</title>
            """.trimIndent()
            )
            if (stylesheets.isNotEmpty()) {
                stylesheets.forEach { ss ->
                    appendLine(
                        "<link rel=\"stylesheet\" type=\"text/css\" href=\"${rootDir(id)}css/${ss.name}.css\">"
                    )
                }
            }
            appendLine(
                """
                </head>
                <body>
            """.trimIndent()
            )
            contentList.forEach { append(it.generate(id)) }
            append(
                """
                </body>
                </html>
            """.trimIndent()
            )
        }, Charsets.UTF_8)
    }

    override fun toString(): String = throw UnsupportedOperationException()
    override fun generate(pageID: PageID): String = throw UnsupportedOperationException()
}
