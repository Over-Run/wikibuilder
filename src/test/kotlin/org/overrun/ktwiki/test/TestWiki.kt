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
            font-size: 1.0em;
            line-height: 1.4;
            width: 54em;
        }
        a { text-decoration: none; }
        a:link { color: #437291; }
        a:visited { color: #666666; }
        a:hover { color: #E76F00; }
        a:active { color: #E76F00; }
        #main {
            float: right;
            width: 42em;
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
        div.about { font-weight: bold; color: #555; }
        div.link .pageCurr { color: black; }
        pre { padding-left: 2em; margin: 1ex 0; font-size: inherit; }
        pre, code, tt {
            font-family: DejaVu Sans Mono, JetBrains Mono, Bitstream Vera Sans Mono, Luxi Mono, Courier New, monospace;
        }
        h1 { font-size: 1.3em; font-weight: bold;
            padding: 0pt; margin: 2ex .5ex 1ex 0pt; }
        h2 { font-size: 1.1em; font-weight: bold;
            padding: 0pt; margin: 2ex 0pt 1ex 0pt; }
        h3 { font-size: 1.0em; font-weight: bold; font-style: italic;
            padding: 0pt; margin: 1ex 0pt 1ex 0pt; }
        h4 { font-size: 0.9em; font-weight: bold;
            padding: 0pt; margin: 1ex 0pt 1ex 0pt; }
        h1:first-child, h2:first-child {
            margin-top: 0ex;
        }
    """.trimIndent()
}
private val stylesheets: List<Stylesheet> = listOf(publicCss)

val indexID = "_ktwiki_index" to "Index"
val downloadID = "download" to "Download"
val basicFunctionsID = "basic_functions" to "Basic functions"
val allFeaturesID = "all_features" to "All features"

/**
 * @author squid233
 * @since 0.1.0
 */
fun main() = site("ktwiki Wiki") {
    // We can actually define templates.
    val color = fun(color: String, content: String): Node = literal("<span style=\"color:$color\">$content</span>")
    val relativeLink = fun(pageId: String, link: String, content: String): Node = RelativeLink(
        href = {
            if (it == pageId) return@RelativeLink null
            if (it == "_ktwiki_index") return@RelativeLink link
            return@RelativeLink "../$link"
        },
        content = content, classCurr = "pageCurr"
    )
    val main = fun(content: DivNode.() -> Unit): Node = div(id = "main", content = content)
    val sidebar = div(id = "sidebar") {
        +div(`class` = "links") {
            +div(`class` = "link") {
                +relativeLink(indexID.first, "", indexID.second)
            }
            +div(`class` = "link") {
                +relativeLink(downloadID.first, "${downloadID.second}/", downloadID.second)
            }
        }
        +div(`class` = "links") {
            +div(`class` = "link") {
                +relativeLink(basicFunctionsID.first, "${basicFunctionsID.second}/", basicFunctionsID.second)
            }
            +div(`class` = "link") {
                +relativeLink(allFeaturesID.first, "${allFeaturesID.second}/", allFeaturesID.second)
            }
        }
    }
    val footer = div(id = "footer") {
        -"Copyright (c) 2023 Overrun Organization"
        +br
        -"License: ${
            a(href = "https://github.com/Over-Run/ktwiki/blob/main/LICENSE", content = "MIT")
        } · ${a(href = "https://github.com/Over-Run/ktwiki", content = "Source")}"
    }

    +publicCss
    +Page(indexID, path = null, stylesheets = stylesheets) {
        +main {
            +"Welcome to ktwiki Wiki!"
            +("Introduction" to 2)
            +"ktwiki is a Kotlin DSL that allows you to generate your own wiki or pages."
            +"An official theme will be available soon."
            +"Check the sidebar for more information."
        }
        +sidebar
        +footer
    }
    +Page(downloadID, stylesheets = stylesheets) {
        +main {
            +("Download" to 1)
            +"You can download the source on ${a(href = "https://github.com/Over-Run/ktwiki", content = "GitHub")}."
            +"You need to use Java 17 and Kotlin 1.8.22 to run your generator."
            +("Add to Dependencies" to 2)
            +("Gradle" to 3)
            +"Simply add this code to your ${code("build.gradle")}:"
            !"""
                dependencies {
                    implementation "io.github.over-run:ktwiki:+"
                }
            """.trimIndent()
            +("Maven" to 3)
            +"Simply add this code to your ${code("pom.xml")}:"
            !"""
                &lt;dependencies>
                    &lt;dependency>
                        &lt;groupId>io.github.over-run&lt;/groupId>
                        &lt;artifactId>ktwiki&lt;/artifactId>
                        &lt;version><i>The version</i>&lt;/version>
                    &lt;/dependency>
                &lt;/dependencies>
            """.trimIndent()
        }
        +sidebar
        +footer
    }
    +Page(basicFunctionsID, stylesheets = stylesheets) {
        +main {
            +("Basic functions" to 1)
            +"TODO"
        }
        +sidebar
        +footer
    }
    +Page(allFeaturesID, stylesheets = stylesheets) {
        +main {
            +("All features" to 1)
            +("Heading 1" to 1)
            +("Heading 2" to 2)
            +("Heading 3" to 3)
            +("Heading 4" to 4)
            +("Heading 5" to 5)
            +("Heading 6" to 6)
            +"This is a paragraph"
            -"This is an literal text."
            -" Appending"
            !"""
                This is a code block.
                Another line
            """.trimIndent()
            +"We haven’t support to highlighting yet."
            +codeBlock(
                """
                ${color("#475F63", "1")}    ${color("#546E7A", "/* This is a code block */")}
                ${color("#475F63", "2")}    ${color("#C792EA", "void")} ${color("#82AAFF", "main")}() {               ${
                    color("#546E7A", "System.out.")
                }
                ${color("#475F63", "3")}        ${color("#82AAFF", "printf")}(${color("#C3E88D", "\"Hello world!\"")});
                ${color("#475F63", "4")}    }
                """.trimIndent(), style = "background-color:#263238;color:#89DDFF"
            )
            +"sidebar and footer are added as ${code("div")}."
            +a(href = "https://github.com/Over-Run/overrungl", content = "A link that opens in a new tab")
            +a(href = "https://github.com/Over-Run/ktwiki", content = h(6, p("Another link with ${code("h6")}")))
            +relativeLink(indexID.first, "", p("Jump to index").toString())
        }
        +sidebar
        +footer
    }
}
