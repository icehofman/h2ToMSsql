import static cucumber.api.groovy.EN.*
import groovy.sql.Sql

def db = [url:'jdbc:jtds:sqlserver://ICEHOFMAN-PC:1433/groovy', user:'groovy', password:'groovy', driver:'net.sourceforge.jtds.jdbc.Driver']

def sql

try {
    sql = Sql.newInstance(db.url, db.user, db.password, db.driver)
}
catch (all){

}

Given(~/^database$/) { ->
      db
}

When(~/^connect$/) { ->
    sql
}

Then(~/^connection is sucessful$/) { ->
    if(sql){
        if(!sql.connection.isClosed()){
            sql.close()
        }
    }
}