## Defunct
This library has been superseded by the more general (and just better) [rest-query-engine](https://github.com/rutledgepaulv/rest-query-engine).


[![Build Status](https://travis-ci.org/RutledgePaulV/rsql-mongodb.svg)](https://travis-ci.org/RutledgePaulV/rsql-mongodb)

### What

An open source implementation for converting [rsql](https://github.com/jirutka/rsql-parser) to mongo queries for spring
data. RSQL is a flexible and readable query language based off of apache's FIQL. Providing this adapater for mongo queries
makes RSQL a natural choice for APIs that want to provide flexible querying and whose underlying datastore is mongodb.


### How to use it
```java

@Autowired
private MongoOperations mongoOperations;

@Autowired
private MongoMappingContext mappingContext;

// build the converter. You can define a list of conversions instead, but using a spring conversion service and mongo mapping context
// works really quite well. Some applications will have a conversion service available from the application context that can include
// custom converters (iso -> date, etc)
ComparisonToCriteriaConverter converter = new ComparisonToCriteriaConverter(new DefaultConversionService(), mongoMappingContext);

// build the actual rsql string -> mongo criteria adapter
RsqlMongoAdapter adapter = new RsqlMongoAdapter(converter);

// convert the rsql to some criteria and add that criteria to the mongo query object
Query query = Query.query(adapter.getCriteria("firstName==joe", Person.class));

// make your query!
List<Person> personsNamedJoe = mongoOperations.find(query, Person.class);

```


### Examples of supported cases. For the full list, please see tests.

```
# basic equality
firstName==joe -> { "firstName" : "joe"}

# basic inequality
firstName!=joe -> { "firstName" : { "$ne" : "joe"}}

# basic greater than
createDate=gt=300 -> { "createDate" : { "$gt" : 300}}

# basic greater than or equal
createDate=ge=300 -> { "createDate" : { "$gte" : 300}}

# basic less than
createDate=lt=300 -> { "createDate" : { "$lt" : 300}}

# basic less than or equal
createDate=le=300 -> { "createDate" : { "$lte" : 300}}

# basic element appears in list
firstName=in=(billy,bob,joel) -> { "firstName" : { "$in" : [ "billy" , "bob" , "joel"]}}

# basic element does not appear in list
firstName=out=(billy,bob,joel) -> { "firstName" : { "$nin" : [ "billy" , "bob" , "joel"]}}

# anding of two basic conditions
firstName!=joe;lastName==dummy -> { "$and" : [ { "firstName" : { "$ne" : "joe"}} , { "lastName" : "dummy"}]}

# oring of two basic conditions
firstName!=john,lastName==doe -> { "$or" : [ { "firstName" : { "$ne" : "john"}} , { "lastName" : "doe"}]}

# anding of two oring conditions of anding conditions
((firstName==john;lastName==doe),(firstName==aaron;lastName==carter));((age==21;height==90),(age==30;height==100)) -> 

    {
       "$and":[
          {
             "$or":[
                {
                   "$and":[
                      {
                         "firstName":"john"
                      },
                      {
                         "lastName":"doe"
                      }
                   ]
                },
                {
                   "$and":[
                      {
                         "firstName":"aaron"
                      },
                      {
                         "lastName":"carter"
                      }
                   ]
                }
             ]
          },
          {
             "$or":[
                {
                   "$and":[
                      {
                         "age":21
                      },
                      {
                         "height":90
                      }
                   ]
                },
                {
                   "$and":[
                      {
                         "age":30
                      },
                      {
                         "height":100
                      }
                   ]
                }
             ]
          }
       ]
    }
    
```


### License

This project is licensed under [MIT license](http://opensource.org/licenses/MIT).
