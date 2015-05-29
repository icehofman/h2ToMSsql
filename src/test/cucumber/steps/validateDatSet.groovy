import groovy.sql.*
import static cucumber.api.groovy.EN.*

def db = [url:'jdbc:jtds:sqlserver://ICEHOFMAN-PC:1433/groovy', user:'groovy', password:'groovy', driver:'net.sourceforge.jtds.jdbc.Driver']

def sqlServer

def setOfSimpleOrder

try {
    sqlServer = Sql.newInstance(db.url, db.user, db.password, db.driver)
    setOfSimpleOrder = new DataSet(sqlServer, 'SimpleOrders')
}
catch (all){
    println "#### Error SqlServer Transaction####"
}

Given(~/^An existing database 'Groovy'$/) { ->
    assert 'jdbc:jtds:sqlserver://ICEHOFMAN-PC:1433/groovy' == db.url
}

Given(~/^its respective table 'SimpleOrders'$/) { ->
    if(sqlServer) {
        def createTbl = '''
        CREATE TABLE SimpleOrders (
          id uniqueidentifier PRIMARY KEY NOT NULL
          DEFAULT newid(),
          OrderNumber NVARCHAR(50),
          OrderDesc NVARCHAR(100)
        )
        '''

        def dropTable = '''
        IF  EXISTS (
        SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'SimpleOrders') AND type in (N'U')
        )
        DROP TABLE SimpleOrders
        '''

        sqlServer.execute(dropTable)

        sqlServer.execute(createTbl)

        def result = sqlServer.rows("SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'SimpleOrders') AND type in (N'U')")

        assert 1 == result.size()
    }
}

When(~/^creating a record with dataset in SqlServer$/) { ->
    if(sqlServer) {
        setOfSimpleOrder.add(orderNumber: '000abc', orderDesc: 'Test01')
    }
}

Then(~/^validate the new record with dataset in SqlServer$/) { ->
    if(sqlServer) {
        def rows = setOfSimpleOrder.rows()
        assert 1 == rows.size()
    }
}