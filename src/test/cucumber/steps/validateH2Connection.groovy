import static cucumber.api.groovy.EN.*
import groovy.sql.Sql

def db = [url:'jdbc:h2:things', user:'sa', password:'sa', driver:'org.h2.Driver']

def sqlH2 = Sql.newInstance(db.url, db.user, db.password, db.driver)

Given(~/^database groovy on HTwo$/) { ->
    assert 'jdbc:h2:things' == db.url
}

When(~/^connect to HTwo$/) { ->
    sqlH2
    if (sqlH2) assert 'SA' == sqlH2.useConnection.user
}

Then(~/^create table 'things' on HTwo$/) { ->
    if(sqlH2){
        def createTbl = '''
                        CREATE TABLE things (
                          id UUID PRIMARY KEY,
                          thing1 VARCHAR(50),
                          thing2 VARCHAR(100)
                        )
                        '''

        sqlH2.execute("DROP TABLE IF EXISTS things")

        sqlH2.execute(createTbl)

        sqlH2.execute("INSERT INTO things VALUES(:id, :thing1, :thing2)", [id:  java.util.UUID.randomUUID(), thing1: 'I am thing1', thing2: 'I am thing2'])
        sqlH2.execute("INSERT INTO things VALUES(:id, :thing1, :thing2)", [id:  java.util.UUID.randomUUID(), thing1: 'foo', thing2: 'bar'])
        sqlH2.execute("INSERT INTO things VALUES(:id, :thing1, :thing2)", [id:  java.util.UUID.randomUUID(), thing1: 'Alisa', thing2: 'Yeoh'])

        def query = "SELECT * FROM things"

        sqlH2.eachRow(query) { row ->
            println "$row.id - ${row.thing1}::$row.thing2"
        }

        def result = sqlH2.rows(query)

        assert 3 == result.size()
    }
}

Then(~/^close connection to HTwo$/) { ->
    if(sqlH2) {
        if (!sqlH2.connection.isClosed()) {
            sqlH2.close()
        }
    }
}