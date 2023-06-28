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

/**
 * @author squid233
 * @since 0.1.0
 */
interface Node {
    /** Attempts to generate string without using the page id. */
    override fun toString(): String
    fun generate(pageId: String): String
}

/**
 * @author squid233
 * @since 0.1.0
 */
abstract class ListBackedNode : Node {
    protected val content: MutableList<Node> = ArrayList()

    operator fun Node.unaryPlus() {
        content += this
    }

    operator fun String.unaryPlus() {
        +p(this)
    }

    operator fun String.unaryMinus() {
        +literal(this)
    }

    operator fun String.not() {
        +codeBlock(this)
    }

    @JvmName("addLink")
    operator fun Pair<String, String>.unaryPlus() {
        +a(href = this.second, content = this.first)
    }

    @JvmName("addHeading")
    operator fun Pair<String, Int>.unaryPlus() {
        +h(level = this.second, content = this.first)
    }
}

/**
 * @author squid233
 * @since 0.1.0
 */
class DivNode(
    private val id: String? = null,
    private val `class`: String? = null
) : ListBackedNode() {
    override fun toString(): String = throw UnsupportedOperationException()
    override fun generate(pageId: String): String = buildString {
        append("<div")
        if (id != null) append(" id=\"$id\"")
        if (`class` != null) append(" class=\"$`class`\"")
        appendLine('>')
        content.forEach { append(it.generate(pageId)) }
        appendLine("</div>")
    }
}

/**
 * @author squid233
 * @since 0.1.0
 */
@Suppress("ClassName")
object br : Node {
    override fun toString(): String = "<br>"
    override fun generate(pageId: String): String = toString()
}

/**
 * A relative link that auto-converts with the current page.
 *
 * @param[classCurr] the CSS class used when [href] returns `null`.
 * @author squid233
 * @since 0.1.0
 */
class RelativeLink(
    private val href: (String) -> String?,
    private val content: String,
    private val classCurr: String? = null
) : Node {
    override fun toString(): String = throw UnsupportedOperationException()
    override fun generate(pageId: String): String {
        val s = href(pageId)
        return if (s != null)
            "<a href=\"$s\" target=\"_blank\" rel=\"noopener noreferrer\">$content</a>\n"
        else "<b${classCurr?.let { " class=\"$it\"" } ?: ""}>$content</b>\n"
    }
}

private fun style(style: String?): String = if (style != null) " style=\"$style\"" else ""

fun literal(string: String): Node = object : Node {
    override fun toString(): String = string
    override fun generate(pageId: String): String = toString()
}

fun literal(string: (String) -> String): Node = object : Node {
    override fun toString(): String = throw UnsupportedOperationException()
    override fun generate(pageId: String): String = string(pageId)
}

fun p(string: String, style: String? = null): Node = literal("<p${style(style)}>$string</p>\n")

fun a(href: String, content: Node, style: String? = null): Node = a(href, content.toString())
fun a(href: String, content: String, style: String? = null): Node = literal(
    "<a href=\"$href\" target=\"_blank\" rel=\"noopener noreferrer\"${style(style)}>$content</a>\n"
)

fun h(level: Int, content: Node, style: String? = null): Node = h(level, content.toString(), style)
fun h(level: Int, content: String, style: String? = null): Node {
    check(level in 1..6) { "Must be 1 to 6" }
    return literal("<h$level${style(style)}>$content</h$level>\n")
}

fun div(
    id: String? = null,
    `class`: String? = null,
    content: DivNode.() -> Unit
): Node = DivNode(id, `class`).also(content)

fun code(content: String, style: String? = null): Node = literal("<code${style(style)}>$content</code>\n")
fun codeBlock(content: String, style: String? = null): Node =
    literal("<pre${style(style)}><code>$content</code></pre>\n")
