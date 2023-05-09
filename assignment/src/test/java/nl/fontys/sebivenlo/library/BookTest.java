/*
 * Copyright 2022 ibrahimkouzak.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nl.fontys.sebivenlo.library;

import java.nio.file.NoSuchFileException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;

/**
 *
 * @author ibrahimkouzak
 */
public class BookTest {

    @Test
    public void testGetIsbn() {
        Book bookRef = new Book(0, "title", "Peter", "1234567", "Peter", Book.Language.ENGLISH, 2022);
        assertThat(bookRef.getIsbn()).isEqualTo("1234567");
    }

    @Test
    public void testGetPublisher() {
        Book bookRef = new Book(0, "title", "Peter", "1234567", "Peter", Book.Language.ENGLISH, 2022);
        assertThat(bookRef.getPublisher()).isEqualTo("Peter");
    }

    @Test
    public void testGetLanguage() {
        Book bookRef = new Book(0, "title", "Peter", "1234567", "Peter", Book.Language.ENGLISH, 2022);
        assertThat(bookRef.getLanguage()).isEqualTo(Book.Language.ENGLISH);
    }

    @Test
    public void testHashCodeAndEquals() {
        Book bookRef = new Book(0, "title", "Peter", "1234567", "Peter", Book.Language.ENGLISH, 2022);
        Book bookEqu = new Book(0, "title", "Peter", "1234567", "Peter", Book.Language.ENGLISH, 2022);
        Book bookUnEq = new Book(1, "title", "Peter", "1234567", "Peter", Book.Language.ENGLISH, 2022);
        TestUtils.verifyEqualsAndHashCode(bookRef, bookEqu, bookUnEq);
    }

    @Test
    public void testGetYearOfPublication() {
        Book bookRef = new Book(0, "title", "Peter", "1234567", "Peter", Book.Language.ENGLISH, 2022);
        assertThat(bookRef.getYearOfPublication()).isEqualTo(2022);
    }

    @Test
    public void testLoadFromFile() throws Exception {
        ThrowingCallable code = () -> Book.loadFromFile("nonexisting.txt");
        
        assertThatCode(code).doesNotThrowAnyException();
    }

}
