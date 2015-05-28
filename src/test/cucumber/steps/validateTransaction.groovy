import groovy.sql.Sql

import static cucumber.api.groovy.EN.*
import groovy.sql.Sql

def db = [url:'jdbc:jtds:sqlserver://ICEHOFMAN-PC:1433/groovy', user:'groovy', password:'groovy', driver:'net.sourceforge.jtds.jdbc.Driver']

def sqlServer

try {
    sqlServer = Sql.newInstance(db.url, db.user, db.password, db.driver)
}
catch (all){
    println "#### Error SqlServer Transaction####"
}


Given(~/^An existing database$/) { ->
    assert 'jdbc:jtds:sqlserver://ICEHOFMAN-PC:1433/groovy' == db.url
}

Given(~/^its respective table$/) { ->
    if(sqlServer) {
        def createTbl = '''
        CREATE TABLE Orders (
          id uniqueidentifier PRIMARY KEY NOT NULL
          DEFAULT newid(),
          OrderNumber NVARCHAR(50),
          OrderDesc NVARCHAR(100)
        )
        '''

        def dropTable = '''
        IF  EXISTS (
        SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'Orders') AND type in (N'U')
        )
        DROP TABLE Orders
        '''

        sqlServer.execute(dropTable)

        sqlServer.execute(createTbl)

        def result = sqlServer.rows("SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'Orders') AND type in (N'U')")

        assert 1 == result.size()
    }
}

When(~/^creating a record in SqlServer$/) { ->

}

Then(~/^use transaction in SqlServer$/) { ->

}
