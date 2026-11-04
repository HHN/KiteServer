package com.hhn.kite2server.data;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DataExportControllerTest {

    @Test
    @DisplayName("null becomes empty string")
    void nullToEmpty() {
        assertThat(DataExportController.toCsvField(null)).isEmpty();
    }

    @Test
    @DisplayName("Plain values pass through unquoted")
    void plainValueUnquoted() {
        assertThat(DataExportController.toCsvField("hello world")).isEqualTo("hello world");
    }

    @Test
    @DisplayName("Comma triggers quoting")
    void commaIsQuoted() {
        assertThat(DataExportController.toCsvField("a,b")).isEqualTo("\"a,b\"");
    }

    @Test
    @DisplayName("Double quotes are escaped by doubling")
    void quotesAreEscaped() {
        assertThat(DataExportController.toCsvField("say \"hi\"")).isEqualTo("\"say \"\"hi\"\"\"");
    }

    @Test
    @DisplayName("Newlines and carriage returns trigger quoting")
    void lineBreaksAreQuoted() {
        assertThat(DataExportController.toCsvField("line1\nline2")).isEqualTo("\"line1\nline2\"");
        assertThat(DataExportController.toCsvField("line1\rline2")).isEqualTo("\"line1\rline2\"");
    }

    @Test
    @DisplayName("CSV injection: leading = is neutralized with a single quote")
    void formulaInjectionEquals() {
        assertThat(DataExportController.toCsvField("=SUM(A1:A5)")).isEqualTo("'=SUM(A1:A5)");
    }

    @Test
    @DisplayName("CSV injection: leading + - @ are neutralized too")
    void formulaInjectionOtherChars() {
        assertThat(DataExportController.toCsvField("+cmd|'/c calc'!A0")).isEqualTo("'+cmd|'/c calc'!A0");
        assertThat(DataExportController.toCsvField("-2+3")).isEqualTo("'-2+3");
        assertThat(DataExportController.toCsvField("@cmd")).isEqualTo("'@cmd");
    }

    @Test
    @DisplayName("Formula protection and quoting combine correctly")
    void formulaAndQuotingCombined() {
        // formula char AND comma: quote prefix goes inside the CSV quotes
        assertThat(DataExportController.toCsvField("=1,2")).isEqualTo("\"'=1,2\"");
    }
}