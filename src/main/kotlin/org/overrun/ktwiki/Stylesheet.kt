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
 * A CSS stylesheet.
 *
 * @param[name] the name of the stylesheet.
 * @author squid233
 * @since 0.1.0
 */
class Stylesheet(val name: String, private val content: String) {
    fun generate(site: Site, basePath: String) {
        val finalPath = Path(basePath).let { if (site.lang != LANG_EN_US) it.resolve(site.lang) else it }.resolve("css")
        Files.createDirectories(finalPath)
        Files.writeString(
            finalPath.resolve("$name.css"),
            "/* auto generated file. DO NOT EDIT */\n$content",
            Charsets.UTF_8
        )
    }
}
