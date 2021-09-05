/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2020 Yegor Bugayenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.cactoos.text;

import java.util.regex.Pattern;
import org.cactoos.Func;
import org.cactoos.func.FuncOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsText;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link Strict}.
 *
 * @since 1.0
 * @checkstyle MagicNumber (500 line)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
final class StrictTest {

    /**
     * Ensures that Strict accepts a CharSequence predicate.
     */
    @Test
    void acceptsCharSequencePredicate() {
        final Func<CharSequence, Boolean> lengthy = new FuncOf<>(
            seq -> seq.length() > 3
        );
        new Assertion<>(
            "Must be equal strings",
            new Strict(lengthy, new TextOf("sequence")),
            new IsText("sequence")
        ).affirm();
    }

    /**
     * Ensures that Strict is failing on a negative predicate result.
     * @throws Exception If fails
     */
    @Test
    void failsIfPredicateIsNegative() {
        new Assertion<>(
            "Must throw IllegalArgumentException",
            () -> new Strict(s -> false, new TextOf("text")).asString(),
            new Throws<>(
                "String 'text' does not match a given predicate",
                IllegalArgumentException.class
            )
        ).affirm();
    }

    /**
     * Ensures that Strict is returning unchanged string on a positive
     * predicate result.
     */
    @Test
    void returnsUnchangedIfPredicateIsPositive() {
        new Assertion<>(
            "Given strings are not equal",
            new Strict(s -> true, new TextOf("text")),
            new IsText("text")
        ).affirm();
    }

    /**
     * Ensures that Strict is failing on a text not matching a pattern.
     * @throws Exception If fails
     */
    @Test
    void failsIfNotMatchedWithPattern() {
        new Assertion<>(
            "Must throw IllegalArgumentException",
            () -> new Strict(
                Pattern.compile("^[a-zA-Z]+$"),
                new TextOf("text12")
            ).asString(),
            new Throws<>(
                "String 'text12' does not match a given predicate",
                IllegalArgumentException.class
            )
        ).affirm();
    }

    /**
     * Ensures that Strict is returning unchanged string
     * on a matched with pattern string.
     */
    @Test
    void returnsUnchangedIfMatchedWithPattern() {
        new Assertion<>(
            "Given strings are not equal",
            new Strict(
                Pattern.compile("^[a-zA-Z0-9]+$"),
                new TextOf("text1")
            ),
            new IsText("text1")
        ).affirm();
    }
}
