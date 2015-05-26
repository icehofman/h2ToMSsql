import static cucumber.api.groovy.EN.*
import groovy.sql.Sql

def db = [url:'jdbc:jtds:sqlserver://ICEHOFMAN-PC:1433/groovy', user:'groovy', password:'groovy', driver:'net.sourceforge.jtds.jdbc.Driver']

def sql

try {
    sql = Sql.newInstance(db.url, db.user, db.password, db.driver)
}
catch (all){

}

Given(~/^database groovy on SqlServer$/) { ->
      db
}

When(~/^connect to SqlServer$/) { ->
    sql
}

Then(~/^connection is successful to SqlServer$/) { ->
    if(sql){
        if(!sql.connection.isClosed()){
            sql.close()
        }
    }
}