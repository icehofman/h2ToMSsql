import static cucumber.api.groovy.EN.*
import groovy.sql.Sql

def db = [url:'jdbc:jtds:sqlserver://ICEHOFMAN-PC:1433/groovy', user:'groovy', password:'groovy', driver:'net.sourceforge.jtds.jdbc.Driver']

def sqlServer

try {
    sqlServer = Sql.newInstance(db.url, db.user, db.password, db.driver)
}
catch (all){
    println "#### Error SqlServer ####"
}

Given(~/^database groovy on SqlServer$/) { ->
    assert 'jdbc:jtds:sqlserver://ICEHOFMAN-PC:1433/groovy' == db.url
}

When(~/^connect to SqlServer$/) { ->
    if (sqlServer) assert 'ICEHOFMAN-PC' == sqlServer.useConnection.serverName
}

Then(~/^create table 'things' on SqlServer$/) { ->
    if(sqlServer) {
        def createTbl = '''
        CREATE TABLE things (
          id uniqueidentifier PRIMARY KEY NOT NULL
          DEFAULT newid(),
          thing1 VARCHAR(50),
          thing2 VARCHAR(100)
        )
        '''

        def dropTable = '''
        IF  EXISTS (
        SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'things') AND type in (N'U')
        )
        DROP TABLE things
        '''

        sqlServer.execute(dropTable)
        sqlServer.execute(createTbl)

        def valuesThings = 'thing1,thing2'

        sqlServer.execute "INSERT INTO things ($valuesThings) VALUES(:thing1, :thing2)", [thing1: 'I am thing1', thing2: 'I am thing2']
        sqlServer.execute "INSERT INTO things ($valuesThings) VALUES(:thing1, :thing2)", [thing1: 'foo', thing2: 'bar']
        sqlServer.execute "INSERT INTO things ($valuesThings) VALUES(:thing1, :thing2)", [thing1: 'Alisa', thing2: 'Yeoh']
        sqlServer.execute "INSERT INTO things ($valuesThings) VALUES(:thing1, :thing2)", [thing1: 'Israel', thing2: 'Hofman']

        def query = "SELECT * FROM things"

        sqlServer.eachRow(query) { row ->
            println "$row.id - ${row.thing1}::$row.thing2"
        }

        def result = sqlServer.rows(query)

        assert 4 == result.size()
    }
}

Then(~/^close connection to SqlServer$/) { ->
    if(sqlServer) {
        if (!sqlServer.connection.isClosed()) {
            sqlServer.close()
        }

        assert sqlServer.connection.isClosed() == sqlServer.connection.isClosed()
    }
}