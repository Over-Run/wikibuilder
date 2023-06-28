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
import org.overrun.ktwiki.theme.builtin.builtin

val indexID = PageID("_ktwiki_index", {
    when (it) {
        LANG_ZH_HANS -> "索引"
        else -> "Index"
    }
}, "")
val downloadID = PageID("download", {
    when (it) {
        LANG_ZH_HANS -> "下载"
        else -> "Download"
    }
})
val basicFunctionsID = PageID("basic_functions", {
    when (it) {
        LANG_ZH_HANS -> "基本函数"
        else -> "Basic functions"
    }
})
val allFeaturesID = PageID("all_features", {
    when (it) {
        LANG_ZH_HANS -> "特性一览"
        else -> "All features"
    }
})

/**
 * @author squid233
 * @since 0.1.0
 */
fun main() = builtin(
    indexID,
    orgName = "Overrun Organization",
    license = a(href = "https://github.com/Over-Run/ktwiki/blob/main/LICENSE", content = "MIT").toString(),
    source = "https://github.com/Over-Run/ktwiki"
) {
    site("ktwiki Wiki") {
        sidebar = sidebar {
            +links {
                +link { +relativeLink(indexID) }
                +link { +relativeLink(downloadID) }
            }
            +links {
                +link { +relativeLink(basicFunctionsID) }
                +link { +relativeLink(allFeaturesID) }
            }
            +links {
                +about { -"Other languages" }
                +link { pid -> +a(href = "${rootDir(pid)}$LANG_ZH_HANS/", content = "简体中文") }
            }
        }

        +builtinCss
        +page(indexID) {
            +"Welcome to ktwiki Wiki!"
            +("Introduction" to 2)
            +"ktwiki is a Kotlin DSL that allows you to generate your own wiki or pages."
            +"An official theme is available."
            +"Check the sidebar for more information."
        }
        +page(downloadID) {
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
        +page(basicFunctionsID) {
            fun defFun(name: String) = +a(href = "#$name", content = h(2, "${code(name)}", id = name))
            fun refFun(name: String) = a(href = "#$name", content = code(name), openInBlank = false)
            fun sign(content: String) {
                +("Signatures" to 3)
                !content
            }

            fun param(vararg params: Pair<String, String>) {
                +("Parameters" to 3)
                +ul {
                    params.forEach { (name, desc) ->
                        -"${code(name)}: $desc"
                    }
                }
            }

            +("Basic functions" to 1)

            defFun("PageID")
            sign("PageID(id: String, name: (String) -> String, path: String = \"\$name/\")")
            param(
                "id" to "An unique identifier of the page. For special pages, start with \"${code("_")}\".",
                "name" to "The name of the page for each language.",
                "path" to "The path of the page. Defaults to the name of the page."
            )
            +"Creates a page ID."

            defFun("builtin")
            sign("${b("fun")} builtin(indexId: PageID, action: BuiltinTheme.() -> Unit)")
            param(
                "indexId" to "The ${refFun("PageID")} of the index.",
                "action" to "The code of the generator."
            )
            +"Uses the builtin theme. Enables to use builtin stylesheet and templates."
        }
        +page(allFeaturesID) {
            +("All features" to 1)
            +("Heading 1" to 1)
            +("Heading 2" to 2)
            +("Heading 3" to 3)
            +("Heading 4" to 4)
            +("Heading 5" to 5)
            +("Heading 6" to 6)
            +"This is a paragraph"
            -"This is a literal text."
            -" Appending"
            !"""
                This is a code block.
                Another line
            """.trimIndent()
            +"We haven’t supported to highlighting yet."
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
            +relativeLink(indexID, content = p("Jump to index").toString())
            +relativeLink(allFeaturesID, content = "Cannot click (reference to this page)")
            +p { +spoiler("Just kidding") }
            +p {
                +b("BOLD")
                +i("ITALIC")
                +s("strikethrough")
                +u("underline")
                +strong("IMPORTANT TEXT")
                +em("emphasized text")
            }
            +p {
                +small("small text")
                +sup("2")
                +sub("n")
            }
            !"void ${del("mian")}${ins("main")}() {"
            +ul {
                -"Test"
                +b("Bold")
                +"Paragraph"
            }
            +ol {
                -"Test"
                +b("Bold")
                +"Paragraph"
            }
        }
    }

    site("ktwiki Wiki", lang = LANG_ZH_HANS) {
        sidebar = sidebar {
            +links {
                +link { +relativeLink(indexID, lang = this@site.lang) }
                +link { +relativeLink(downloadID, lang = this@site.lang) }
            }
            +links {
                +link { +relativeLink(basicFunctionsID, lang = this@site.lang) }
            }
            +links {
                +about { -"其他语言" }
                +link { pid -> +a(href = rootDir(pid, this@site.lang), content = "English (United States)") }
            }
        }

        +builtinCss
        +page(indexID) {
            +"欢迎来到 ktwiki Wiki！"
            +("介绍" to 2)
            +"ktwiki 是一个 Kotlin DSL，允许您生成自己的 wiki 或页面。"
            +"自带一个官方主题。"
            +"查看边栏了解更多信息。"
        }
        +page(downloadID) {
            +("下载" to 1)
            +"可在 ${a(href = "https://github.com/Over-Run/ktwiki", content = "GitHub")} 上下载源码。"
            +"您需要 Java 17 和 Kotlin 1.8.22 来运行生成器。"
            +("添加到依赖" to 2)
            +("Gradle" to 3)
            +"添加以下代码到 ${code("build.gradle")}:"
            !"""
                dependencies {
                    implementation "io.github.over-run:ktwiki:+"
                }
            """.trimIndent()
            +("Maven" to 3)
            +"添加以下代码到 ${code("pom.xml")}:"
            !"""
                &lt;dependencies>
                    &lt;dependency>
                        &lt;groupId>io.github.over-run&lt;/groupId>
                        &lt;artifactId>ktwiki&lt;/artifactId>
                        &lt;version><i>版本</i>&lt;/version>
                    &lt;/dependency>
                &lt;/dependencies>
            """.trimIndent()
        }
        +page(basicFunctionsID) {
            fun defFun(name: String) = +a(href = "#$name", content = h(2, "${code(name)}", id = name))
            fun refFun(name: String) = a(href = "#$name", content = code(name), openInBlank = false)
            fun sign(content: String) {
                +("函数签名" to 3)
                !content
            }

            fun param(vararg params: Pair<String, String>) {
                +("参数" to 3)
                +ul {
                    params.forEach { (name, desc) ->
                        -"${code(name)}: $desc"
                    }
                }
            }

            +("基本函数" to 1)

            defFun("PageID")
            sign("PageID(id: String, name: (String) -> String, path: String = \"\$name/\")")
            param(
                "id" to "页面的唯一标识符。特殊页面以 \"${code("_")}\" 开头。",
                "name" to "页面每种语言的名称。",
                "path" to "页面的路径。默认为页面名称。"
            )
            +"创建一个页面 ID。"

            defFun("builtin")
            sign("${b("fun")} builtin(indexId: PageID, action: BuiltinTheme.() -> Unit)")
            param(
                "indexId" to "索引页面的 ${refFun("PageID")}。",
                "action" to "生成器代码。"
            )
            +"使用内置主题，启用内置样式表和模板。"
        }
    }
}
