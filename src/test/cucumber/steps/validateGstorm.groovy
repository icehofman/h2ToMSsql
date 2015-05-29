import groovy.sql.Sql
import gstorm.*
import static cucumber.api.groovy.EN.*

class Person { String name; int age }

//H2
def dbh2 = [url:'jdbc:h2:things', user:'sa', password:'sa', driver:'org.h2.Driver']

def sqlh2 = Sql.newInstance(dbh2.url, dbh2.user, dbh2.password, dbh2.driver)

def gh2 = new Gstorm(sqlh2)

Given(~/^the class 'Person'$/) { ->
    assert 'Person' ==  Person.name
}


//H2
When(~/^using the Gstorm on Htwo$/) { ->
    if (sqlh2) {
        gh2.stormify(Person)

        def result = sqlh2.rows("select count(*) from information_schema.columns where table_name = 'person'")

        assert 1 == result.size()
    }
}

//H2
Then(~/^must create a new record on Htwo$/) { ->
    if (sqlh2) {
        new Person(name: 'Batman', age: 35).save()

        new Person(name: 'Spiderman', age: 30).save()

        println "all records -> ${Person.all}"

        assert Person.all.collect {it.name} == gh2.sql.rows("select * from person").collect {it.name}
    }
}
