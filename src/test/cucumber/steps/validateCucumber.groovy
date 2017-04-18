import static cucumber.api.groovy.EN.*

def learn = 'learn'
def doc = 'doc'

Given(~/^I am learning Cucumber$/) { ->
    assert 'learn' == learn
}

When(~/^I want to see documentation$/) { ->
    assert 'doc' == doc
}

Then(~/^the site is available$/) { ->
    def html = 'http://www.mrhaki.com'.toURL().text
    assert true == html.contains('Groovy')
}
