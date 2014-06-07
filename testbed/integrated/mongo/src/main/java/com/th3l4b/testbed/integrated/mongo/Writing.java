package com.th3l4b.testbed.integrated.mongo;

import java.util.Date;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class Writing {
    
    public static void main(DB db) {
        
        
        
        DBCollection countries = db.getCollection("countries");

        //------------------------------------------------- create
        
        DBObject iceland = new BasicDBObject("name", "Iceland")
                                     .append("area", 39770)
                                     .append("population", 321857)
                                     .append("continent", new BasicDBObject("name", "Europe"));
        // insert
        countries.insert(iceland);
        
        
        DBObject panama = new BasicDBObject("name", "Panama")
                                    .append("area", 75517)
                                    .append("population", 3661868)
                                    .append("continent", new BasicDBObject("name", "Asia"));
        // save (does automatic upsert)
        countries.save(panama);
        

        DBObject egyptQuery = new BasicDBObject("name", "Egypt");
        DBObject egypt = new BasicDBObject("name", "Egypt")
                                   .append("area", 387048)
                                   .append("population", 84550000)
                                   .append("continent", new BasicDBObject("name", "Africa"));
        // update with upsert flag set to true
        countries.update(egyptQuery, egypt, true, false);

        //------------------------------------------------- update
        
        // get a document, change it, save it
        DBObject panamaUpdated = countries.findOne(new BasicDBObject("name", "Panama"));
        panamaUpdated.put("continent", new BasicDBObject("name", "South America"));
        countries.save(panamaUpdated);

        
        // update a document by setting a property
        DBObject newZealandQuery = new BasicDBObject("name", "New Zealand");
        DBObject newZealandUpdate = new BasicDBObject("$set", new BasicDBObject("area", 104464));
        countries.update(newZealandQuery, newZealandUpdate);
        
        
        // increment a value in multiple documents
        DBObject europeQuery = new BasicDBObject("continent.name", "Europe");
        DBObject europeUpdate = new BasicDBObject("$inc", new BasicDBObject("population", 10000));
        countries.update(europeQuery, europeUpdate, false, true);      
        
        
        // rename and remove field
        DBObject renameAndRemove = new BasicDBObject();
        renameAndRemove.put("$rename", new BasicDBObject("population", "pop"));
        renameAndRemove.put("$unset", new BasicDBObject("area", ""));
        countries.update(new BasicDBObject(), renameAndRemove, false, true);   
        
        
        // only set field when inserting
        DBObject thailandQuery = new BasicDBObject("name", "Thailand");
        DBObject thailandUpdate = new BasicDBObject("$setOnInsert", new BasicDBObject("insertedAt", new Date()));
        countries.update(thailandQuery, thailandUpdate, true, false);  

        //------------------------------------------------- delete
        
        BasicDBObject greece = new BasicDBObject("name", "Greece");
        countries.remove(greece);

        
        BasicDBObject betweenQuery = new BasicDBObject("pop", new BasicDBObject("$gt", 3000000).append("$lt", 30000000));
        countries.remove(betweenQuery);
        
        
        displayDocumentsFromCollection(countries);
    }
    
    //------------------------------------------------- display results

    private static void displayDocumentsFromCollection(final DBCollection countries) {
        DBCursor countryCursor = countries.find();
        System.out.println("\n" + countryCursor);
        try {
            while(countryCursor.hasNext()) {
                System.out.println("  " + countryCursor.next());
            }
        } finally {
            countryCursor.close();
        }
    }

}