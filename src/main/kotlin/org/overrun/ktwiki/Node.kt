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
    override fun toString(): String
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
    override fun toString(): String = buildString {
        append("<div")
        if (id != null) append(" id=\"$id\"")
        if (`class` != null) append(" class=\"$`class`\"")
        append('>')
        content.forEach(::append)
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
}

fun literal(string: String): Node = object : Node {
    override fun toString(): String = string
}

fun p(string: String): Node = literal("<p>$string</p>\n")

fun a(href: String, content: Node): Node = a(href, content.toString())
fun a(href: String, content: String): Node = literal(
    "<a href=\"$href\" target=\"_blank\" rel=\"noopener noreferrer\">$content</a>\n"
)

fun h(level: Int, content: Node): Node = h(level, content.toString())
fun h(level: Int, content: String): Node {
    check(level in 1..6) { "Must be 1 to 6" }
    return literal("<h$level>$content</h$level>\n")
}

fun div(
    id: String? = null,
    `class`: String? = null,
    content: DivNode.() -> Unit
): Node = DivNode(id, `class`).also(content)
