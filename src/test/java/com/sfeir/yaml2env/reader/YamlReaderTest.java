package com.sfeir.yaml2env.reader;

import com.sfeir.yaml2env.exception.Yaml2EnvException;
import org.apache.tools.ant.filters.StringInputStream;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

public class YamlReaderTest {

    @Test
    void should_return_key_value_when_read_simple_key() {
        // GIVEN
        var yaml = """
                url: http://www.google.fr
                """;

        // WHEN
        var env = YamlReader.convertToEnvList(new StringInputStream(yaml));

        // THEN
        var expectedEnv = List.of("URL=http://www.google.fr");
        assertThat(env).containsExactlyInAnyOrderElementsOf(expectedEnv);
    }

    @Test
    void should_return_flatten_keys_when_read_nested_keys() {
        // GIVEN
        var yaml = """
                url.google: http://www.google.fr
                url2:
                    google: http://www.google.fr
                """;

        // WHEN
        var env = YamlReader.convertToEnvList(new StringInputStream(yaml));

        // THEN
        var expectedEnv = List.of("URL_GOOGLE=http://www.google.fr", "URL2_GOOGLE=http://www.google.fr");
        assertThat(env).containsExactlyInAnyOrderElementsOf(expectedEnv);
    }

    @Test
    void should_return_indexed_keys_when_read_string_list() {
        // GIVEN
        var yaml = """
                url:
                  google:
                    - http://www.google.fr
                    - http://www.google.com
                """;

        // WHEN
        var env = YamlReader.convertToEnvList(new StringInputStream(yaml));

        // THEN
        var expectedEnv = List.of("URL_GOOGLE_0=http://www.google.fr", "URL_GOOGLE_1=http://www.google.com");
        assertThat(env).containsExactlyInAnyOrderElementsOf(expectedEnv);
    }

    @Test
    void should_return_indexed_keys_when_read_nested_object_list() {
        // GIVEN
        var yaml = """
                bla.google:
                  - url: http://www.google.fr
                  - url: http://www.google.com
                  """;

        // WHEN
        var env = YamlReader.convertToEnvList(new StringInputStream(yaml));

        // THEN
        var expectedEnv = List.of("BLA_GOOGLE_0_URL=http://www.google.fr", "BLA_GOOGLE_1_URL=http://www.google.com");
        assertThat(env).containsExactlyInAnyOrderElementsOf(expectedEnv);
    }

    @Test
    void should_return_value_with_double_quote_when_read_value_with_space() {
        // GIVEN
        var yaml = """
                spaced: bla bla bla
                  """;

        // WHEN
        var env = YamlReader.convertToEnvList(new StringInputStream(yaml));

        // THEN
        var expectedEnv = List.of("SPACED=\"bla bla bla\"");
        assertThat(env).containsExactlyInAnyOrderElementsOf(expectedEnv);
    }

    @Test
    void should_return_key_without_dash_when_read_key_with_dash() {
        // GIVEN
        var yaml = """
                dashed-key: bla
                  """;

        // WHEN
        var env = YamlReader.convertToEnvList(new StringInputStream(yaml));

        // THEN
        var expectedEnv = List.of("DASHEDKEY=bla");
        assertThat(env).containsExactlyInAnyOrderElementsOf(expectedEnv);
    }

    @Test
    void should_throw_exception_when_yaml_structure_is_invalid() {
        // GIVEN
        var yaml = """
                this is an invalid yaml structure :)
                  """;

        // WHEN
        var exception = catchThrowableOfType(() -> YamlReader.convertToEnvList(new StringInputStream(yaml)), Yaml2EnvException.class);

        // THEN
        assertThat(exception)
                .isNotNull()
                .hasMessage("Yaml file structure is invalid.");
    }
}
