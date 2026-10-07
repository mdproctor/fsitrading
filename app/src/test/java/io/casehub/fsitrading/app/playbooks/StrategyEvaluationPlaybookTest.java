package io.casehub.fsitrading.app.playbooks;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.casehub.yaml.core.step.DeclarationFile;
import io.casehub.yaml.core.step.DeclarationParser;
import io.casehub.yaml.jackson.YamlMappers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class StrategyEvaluationPlaybookTest {

    private static final String PLAYBOOK_PATH = "playbooks/strategy-evaluation-cycle.yaml";
    private static final ObjectMapper YAML = YamlMappers.create();
    private static Set<String> catalogActions;
    private static Map<String, Object> playbook;

    @BeforeAll
    @SuppressWarnings("unchecked")
    static void load() throws IOException {
        try (var stream = StrategyEvaluationPlaybookTest.class.getClassLoader()
                .getResourceAsStream("steps/trading-steps.yaml")) {
            Map<String, Object> yaml = YAML.readValue(stream, Map.class);
            DeclarationFile stepFile = DeclarationParser.parse(yaml);
            catalogActions = stepFile.actions().keySet().stream()
                    .map(a -> "fsitrading." + a).collect(Collectors.toSet());
        }
        try (var stream = StrategyEvaluationPlaybookTest.class.getClassLoader()
                .getResourceAsStream(PLAYBOOK_PATH)) {
            assertThat(stream).as("playbook must exist").isNotNull();
            playbook = YAML.readValue(stream, Map.class);
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    void hasPlaybookMetadata() {
        Map<String, Object> meta = (Map<String, Object>) playbook.get("playbook");
        assertThat(meta).isNotNull();
        assertThat(meta.get("name")).isEqualTo("strategy-evaluation-cycle");
    }

    @Test
    void allStepReferencesResolve() throws IOException {
        String content = new String(getClass().getClassLoader()
                .getResourceAsStream(PLAYBOOK_PATH).readAllBytes());
        Pattern lineStepRef = Pattern.compile("^\\s*-?\\s*(fsitrading\\.[a-z-]+):", Pattern.MULTILINE);
        Matcher matcher = lineStepRef.matcher(content);
        while (matcher.find()) {
            assertThat(catalogActions).as("Step '%s'", matcher.group(1)).contains(matcher.group(1));
        }
    }

    @Test
    void hasQuorumConfiguration() throws IOException {
        String content = new String(getClass().getClassLoader()
                .getResourceAsStream(PLAYBOOK_PATH).readAllBytes());
        assertThat(content).contains("quorum");
        assertThat(content).contains("threshold: 2");
    }

    @Test
    void hasRiskGateImport() throws IOException {
        String content = new String(getClass().getClassLoader()
                .getResourceAsStream(PLAYBOOK_PATH).readAllBytes());
        assertThat(content).contains("import: risk-gate");
    }
}
