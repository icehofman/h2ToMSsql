import groovy.sql.Sql
import gstorm.*
import static cucumber.api.groovy.EN.*

class Person { String name; int age }

//H2
def dbh2 = [url:'jdbc:h2:things', user:'sa', password:'sa', driver:'org.h2.Driver']

def sqlh2 = Sql.newInstance(dbh2.url, dbh2.user, dbh2.password, dbh2.driver)

def gh2 = new Gstorm(sqlh2)

//SqlServer
def dbsqlserver = [url:'jdbc:jtds:sqlserver://ICEHOFMAN-PC:1433/groovy', user:'groovy', password:'groovy', driver:'net.sourceforge.jtds.jdbc.Driver']

def sqlServer

try {
    sqlServer = Sql.newInstance(dbsqlserver.url, dbsqlserver.user, dbsqlserver.password, dbsqlserver.driver)
}
catch (all){
    println "#### Error SqlServer ####"
}

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

//SqlServer
When(~/^using the Gstorm on SqlServer$/) { ->
    if (sqlServer) {
        def createTbl = '''
        CREATE TABLE person (
          id uniqueidentifier PRIMARY KEY NOT NULL
          DEFAULT newid(),
          name NVARCHAR(100),
          age INT
        )
        '''

        def dropTable = '''
        IF  EXISTS (
        SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'person') AND type in (N'U')
        )
        DROP TABLE person
        '''

        sqlServer.execute(dropTable)

        sqlServer.execute(createTbl)

        def result = sqlServer.rows("SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'person') AND type in (N'U')")

        assert 1 == result.size()
    }
}

//SqlServer
Then(~/^must create a new record on SqlServer$/) { ->

    if (sqlServer) {
        def gSqlserver = new Gstorm(sqlServer)

        gSqlserver.stormify(Person)

        new Person(name: 'Batman', age: 35).save()

        new Person(name: 'Spiderman', age: 30).save()

        println "all records -> ${Person.all}"

        assert Person.all.collect {it.name} == gSqlserver.sql.rows("select * from person").collect {it.name}
    }
}
