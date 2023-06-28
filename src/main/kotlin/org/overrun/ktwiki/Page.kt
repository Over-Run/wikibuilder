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

import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path

/**
 * A wiki page.
 *
 * @param[name] the name of the page. For special pages, start with "_".
 * @author squid233
 * @since 0.1.0
 */
class Page(
    private val name: String,
    private val path: String? = name,
    private val stylesheets: List<Stylesheet> = emptyList(),
    action: Page.() -> Unit
) : ListBackedNode() {
    init {
        action()
    }

    fun generate(site: Site, basePath: String) {
        val finalPath = Path.of(basePath).let { if (path != null) it.resolve(path) else it }
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
                <title>${name} - ${site.name}</title>
            """.trimIndent()
            )
            if (stylesheets.isNotEmpty()) {
                stylesheets.forEach {
                    appendLine("<link rel=\"stylesheet\" type=\"text/css\" href=\"${if (path != null) "../css/${it.name}.css" else "css/${it.name}.css"}\">")
                }
            }
            appendLine(
                """
                </head>
                <body>
            """.trimIndent()
            )
            content.forEach(::append)
            append(
                """
                </body>
                </html>
            """.trimIndent()
            )
        }, StandardCharsets.UTF_8)
    }

    override fun toString(): String {
        throw UnsupportedOperationException()
    }
}
