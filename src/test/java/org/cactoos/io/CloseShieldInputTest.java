/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2022 Yegor Bugayenko
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
package org.cactoos.io;

import java.io.InputStream;
import org.cactoos.text.TextOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Test case for {@link CloseShieldInput}.
 * @since 1.0.0
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
final class CloseShieldInputTest {

    @Test
    @SuppressWarnings("try")
    void preventsOriginalStreamToBeClosed() throws Exception {
        try (FakeInputStream origin = new FakeInputStream()) {
            // @checkstyle EmptyBlockCheck (5 lines)
            try (
                InputStream stream =
                    new CloseShieldInput(new InputOf(origin)).stream()
            ) {
            }
            new Assertion<>(
                "Must not close origin stream",
                origin.isClosed(),
                new IsEqual<>(false)
            ).affirm();
        }
    }

    @Test
    void readsContent() throws Exception {
        final String content = "Text content";
        try (
            InputStream in = new InputStreamOf(content)
        ) {
            new Assertion<>(
                "Must read text",
                new TextOf(new CloseShieldInput(new InputOf(in))),
                new IsText(content)
            ).affirm();
        }
    }
}
