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

package org.overrun.ktwiki.test

import org.overrun.ktwiki.Page
import org.overrun.ktwiki.Stylesheet
import org.overrun.ktwiki.linkNode
import org.overrun.ktwiki.site

/**
 * @author squid233
 * @since 0.1.0
 */
fun main() = site("ktwiki Wiki") {
    val publicCss = Stylesheet("public") {
        +"""
            body {
                font-family: DejaVu Sans, Bitstream Vera Sans, Luxi Sans, Verdana, Arial, Helvetica, sans-serif;
                font-size: 12pt;
                line-height: 1.4;
            }
        """.trimIndent()
        +"""
            a { text-decoration: none; }
            a:link { color: #437291; }
            a:visited { color: #666666; }
            a:hover { color: #E76F00; }
            a:active { color: #E76F00; }
        """.trimIndent()
    }
    +publicCss
    +Page("Index", path = null, stylesheets = listOf(publicCss)) {
        +"Welcome to ktwiki Wiki!"
        +("View on GitHub" to "https://github.com/Over-Run/ktwiki")
    }
}
