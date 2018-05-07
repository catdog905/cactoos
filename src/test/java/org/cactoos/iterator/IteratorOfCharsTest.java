/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2018 Yegor Bugayenko
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
package org.cactoos.iterator;

import java.util.NoSuchElementException;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

/**
 * Iterator that returns the set of chars.
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @author Krzysztof Krason (Krzysztof.Krason@gmail.com)
 * @version $Id$
 * @since 0.32
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class IteratorOfCharsTest {
    @Test
    public void emptyIteratorDoesNotHaveNext() {
        MatcherAssert.assertThat(
            "Can't create empty iterator",
            new IteratorOfChars().hasNext(),
            CoreMatchers.equalTo(false)
        );
    }

    @Test(expected = NoSuchElementException.class)
    public void emptyIteratorThrowsException() {
        new IteratorOfChars().next();
    }

    @Test
    public void nonEmptyIteratorDoesNotHaveNext() {
        MatcherAssert.assertThat(
            "Can't create non empty iterator",
            this.iteratorWithFetchedElements().hasNext(),
            CoreMatchers.equalTo(false)
        );
    }

    @Test(expected = NoSuchElementException.class)
    public void nonEmptyIteratorThrowsException() {
        this.iteratorWithFetchedElements().next();
    }

    private IteratorOfChars iteratorWithFetchedElements() {
        final IteratorOfChars iterator = new IteratorOfChars(
            'a', 'b', 'c'
        );
        iterator.next();
        iterator.next();
        iterator.next();
        return iterator;
    }
}
