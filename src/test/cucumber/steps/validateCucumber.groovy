import static cucumber.api.groovy.EN.*
import com.santaba.agent.groovyapi.http.*

def learn
def doc

Given(~/^I am learning Cucumber$/) { ->
    def ok = learn
}

When(~/^I want to see documentation$/) { ->
    def ok = doc
}

Then(~/^the site is available$/) { ->
    String html = 'http://cukes.info'.toURL().text
    html.contains('Cucumber')
}
