/*
 * Copyright © 2018 Commerce Technologies, LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.commercehub.gradle.plugin.avro

import static org.gradle.testkit.runner.TaskOutcome.SUCCESS

class ExamplesFunctionalSpec extends FunctionalSpec {
    def "setup"() {
        applyAvroPlugin()
        addAvroDependency()
    }

    def "inline example is valid"() {
        given:
        copyResource("/examples/inline/Cat.avsc", avroDir)

        when:
        def result = run()

        then:
        taskInfoAbsent || result.task(":generateAvroJava").outcome == SUCCESS
        taskInfoAbsent || result.task(":compileJava").outcome == SUCCESS
        projectFile(buildOutputClassPath("example/Cat.class")).file
        projectFile(buildOutputClassPath("example/Breed.class")).file
    }

    def "separate example is valid"() {
        given:
        copyResource("/examples/separate/Breed.avsc", avroDir)
        copyResource("/examples/separate/Cat.avsc", avroDir)

        when:
        def result = run()

        then:
        taskInfoAbsent || result.task(":generateAvroJava").outcome == SUCCESS
        taskInfoAbsent || result.task(":compileJava").outcome == SUCCESS
        projectFile(buildOutputClassPath("example/Cat.class")).file
        projectFile(buildOutputClassPath("example/Breed.class")).file
    }
}
