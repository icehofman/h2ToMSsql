import groovy.sql.Sql
import gstorm.*
import static cucumber.api.groovy.EN.*

class Person { String name; int age }

def db = [url:'jdbc:h2:things', user:'sa', password:'sa', driver:'org.h2.Driver']

def sqlh2 = Sql.newInstance(db.url, db.user, db.password, db.driver)

def g = new Gstorm(sqlh2)

g.stormify(Person)

Given(~/^the class 'Person'$/) { ->
    assert 'Person' ==  Person.name
}

When(~/^using the Gstorm on Htwo$/) { ->
    if (sqlh2) {
        def result = sqlh2.rows("select count(*) from information_schema.columns where table_name = 'person'")

        assert 1 == result.size()
    }
}

Then(~/^must create a new record on Htwo$/) { ->
    if (sqlh2) {
        def batman = new Person(name: 'Batman', age: 35).save()

        def spiderman = new Person(name: 'Spiderman', age: 30).save()

        println "all records -> ${Person.all}"

        assert Person.all.collect {it.name} == g.sql.rows("select * from person").collect {it.name}
    }
}
