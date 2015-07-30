package org.zalando.problem;

/*
 * ⁣​
 * Problem
 * ⁣⁣
 * Copyright (C) 2015 Zalando SE
 * ⁣⁣
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
 * ​⁣
 */

import org.junit.Test;

import javax.ws.rs.core.Response.Status;
import java.net.URI;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasToString;
import static org.hobsoft.hamcrest.compose.ComposeMatchers.hasFeature;
import static org.junit.Assert.assertThat;

public final class ProblemStaticFactoryTest {

    @Test
    public void shouldCreateGenericProblem() {
        final Problem problem = Problem.create(Status.NOT_FOUND);

        assertThat(problem, hasFeature("type", Problem::getType, hasToString("http://httpstatus.es/404")));
        assertThat(problem, hasFeature("title", Problem::getTitle, equalTo("Not Found")));
        assertThat(problem, hasFeature("status", Problem::getStatus, equalTo(Status.NOT_FOUND)));
    }

    @Test
    public void shouldCreateGenericProblemWithDetail() {
        final Problem problem = Problem.create(Status.NOT_FOUND, "Order 123");

        assertThat(problem, hasFeature("type", Problem::getType, hasToString("http://httpstatus.es/404")));
        assertThat(problem, hasFeature("title", Problem::getTitle, equalTo("Not Found")));
        assertThat(problem, hasFeature("status", Problem::getStatus, equalTo(Status.NOT_FOUND)));
        assertThat(problem, hasFeature("detail", Problem::getDetail, equalTo(Optional.of("Order 123"))));
    }

    @Test
    public void shouldCreateProblem() {
        final URI type = URI.create("https://example.org/out-of-stock");
        final Problem problem = Problem.create(type, "Out of Stock", MoreStatus.UNPROCESSABLE_ENTITY);

        assertThat(problem, hasFeature("type", Problem::getType, equalTo(type)));
        assertThat(problem, hasFeature("title", Problem::getTitle, equalTo("Out of Stock")));
        assertThat(problem, hasFeature("status", Problem::getStatus, equalTo(MoreStatus.UNPROCESSABLE_ENTITY)));
    }

    @Test
    public void shouldCreateProblemWithDetail() {
        final URI type = URI.create("https://example.org/out-of-stock");
        final Problem problem = Problem.create(type, "Out of Stock", MoreStatus.UNPROCESSABLE_ENTITY, "Product ABC");

        assertThat(problem, hasFeature("type", Problem::getType, equalTo(type)));
        assertThat(problem, hasFeature("title", Problem::getTitle, equalTo("Out of Stock")));
        assertThat(problem, hasFeature("status", Problem::getStatus, equalTo(MoreStatus.UNPROCESSABLE_ENTITY)));
        assertThat(problem, hasFeature("detail", Problem::getDetail, equalTo(Optional.of("Product ABC"))));
    }

}
