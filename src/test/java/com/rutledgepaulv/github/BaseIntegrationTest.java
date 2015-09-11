package com.rutledgepaulv.github;
import com.google.common.reflect.TypeToken;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("unchecked")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BaseIntegrationTest.TestApplication.class)
public abstract class BaseIntegrationTest<T> {

    protected Class<T> CLAZZ = (Class<T>)(new TypeToken<T>(getClass()){}).getRawType();

    @Autowired
    protected MongoMappingContext mongoMappingContext;

    protected TreeToCriteriaConverter converter;

    @Before
    public void setUp() {
        converter = new TreeToCriteriaConverter(CLAZZ, mongoMappingContext);
    }

    protected Query query(String rsql) {
        Node rootNode = new RSQLParser().parse(rsql);
        Criteria criteria = rootNode.accept(new CriteriaBuildingVisitor(converter));
        return Query.query(criteria);
    }

    protected void check(String rsql, String mongo) {
        assertEquals(mongo, query(rsql).getQueryObject().toString());
    }

    @SpringBootApplication
    public static class TestApplication {

        public static void main(String[] args) {
            SpringApplication.run(TestApplication.class, args);
        }

    }

}
