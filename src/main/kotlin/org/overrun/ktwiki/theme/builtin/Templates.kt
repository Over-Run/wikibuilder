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

/**
 * @author squid233
 * @since 0.1.0
 */
package org.overrun.ktwiki.theme.builtin

import org.overrun.ktwiki.Node
import org.overrun.ktwiki.literal

fun BuiltinTheme.color(color: String, content: String): Node = literal("<span style=\"color: $color;\">$content</span>")
fun BuiltinTheme.spoiler(content: String): Node = literal("<span class=\"spoiler\">$content</span>")
