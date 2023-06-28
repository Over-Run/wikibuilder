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

import org.overrun.ktwiki.*

val publicCss = Stylesheet("public") {
    +"""
        body {
            margin: 2em 2em;
            font-family: DejaVu Sans, Bitstream Vera Sans, Luxi Sans, Verdana, Arial, Helvetica, sans-serif;
            font-size: 1em;
            line-height: 1.4;
        }
        a { text-decoration: none; }
        a:link { color: #437291; }
        a:visited { color: #666666; }
        a:hover { color: #E76F00; }
        a:active { color: #E76F00; }
        #main {
            float: right;
            width: 64em;
            padding-right: 2em;
        }
        #sidebar {
            font-size: 0.8em;
            width: 11em;
            padding-right: 2em;
            margin-left: 0em;
        }
        #footer {
            padding-top: 4em;
            text-align: center;
            font-size: 0.7em;
            clear: both;
        }
        #footer, #footer a, #footer a:link, #footer a:visited, div.links { color: #888; }
        div.links, div.buttons, div.about {
            padding-top: .5ex; padding-right: 0em; line-height: 1.3;
        }
        div.links div.link { margin-left: 1em; text-indent: -1em; }
        """.trimIndent()
}

/**
 * @author squid233
 * @since 0.1.0
 */
fun main() = site("ktwiki Wiki") {
    val sidebar = div(id = "sidebar") {
        +div(`class` = "links") {
            +div(`class` = "link") { +a(href = "ktwiki/", content = "Index") }
        }
    }
    val footer = div(id = "footer") {
        -"Copyright (c) 2023 Overrun Organization"
        +br
        -"License: ${a(href = "https://github.com/Over-Run/ktwiki/blob/main/LICENSE", content = "MIT")}"
    }

    +publicCss
    +Page("Index", path = null, stylesheets = listOf(publicCss)) {
        +div(id = "main") {
            +"Welcome to ktwiki Wiki!"
            +("Introduction" to 2)
            +"ktwiki is a Kotlin DSL that allows you to generate your own wiki or pages."
            +"An official theme will be available soon."
            +("View on GitHub" to "https://github.com/Over-Run/ktwiki")
        }
        +sidebar
        +footer
    }
}
