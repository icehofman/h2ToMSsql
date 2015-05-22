import cucumber.api.CucumberOptions
import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith

@RunWith(Cucumber.class)
@CucumberOptions(
        format = ["pretty", "html:build/reports/cucumber"],
        tags = ["~@manual", "~@review"],
        features = ["src/test/cucumber"],
        glue = ["src/test/cucumber/steps"]
)
public class RunCukesTest {
//leave me empty!
}