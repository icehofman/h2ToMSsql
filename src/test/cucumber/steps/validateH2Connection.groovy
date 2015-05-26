import static cucumber.api.groovy.EN.*
import groovy.sql.Sql

def db = [url:'jdbc:h2:things', user:'sa', password:'sa', driver:'org.h2.Driver']

def sql = Sql.newInstance(db.url, db.user, db.password, db.driver)

Given(~/^database groovy on HTwo$/) { ->
    db
}

When(~/^connect to HTwo$/) { ->
    sql
}

Then(~/^connection is successful  to HTwo$/) { ->
    def createTbl = '''
CREATE TABLE things (
  id INT PRIMARY KEY,
  thing1 VARCHAR(50),
  thing2 VARCHAR(100)
)
'''
    sql.execute("DROP TABLE IF EXISTS things")
    sql.execute(createTbl)
    sql.execute("INSERT INTO things VALUES(:id, :thing1, :thing2)", [id: 0, thing1: 'I am thing1', thing2: 'I am thing2'])
    sql.execute("INSERT INTO things VALUES(:id, :thing1, :thing2)", [id: 1, thing1: 'foo', thing2: 'bar'])
    sql.execute("INSERT INTO things VALUES(:id, :thing1, :thing2)", [id: 2, thing1: 'Alisa', thing2: 'Yeoh'])

    def query = "SELECT * FROM things"

    sql.eachRow(query) { row ->
        println "$row.id - ${row.thing1}::$row.thing2"
    }

    if(sql){
        if(!sql.connection.isClosed()){
            sql.close()
        }
    }
}