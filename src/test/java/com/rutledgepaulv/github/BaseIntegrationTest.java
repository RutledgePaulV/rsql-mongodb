package com.rutledgepaulv.github;

import com.google.common.reflect.TypeToken;
import com.mongodb.DBObject;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.function.Consumer;

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
        converter = new TreeToCriteriaConverter(CLAZZ, new Conversions(), mongoMappingContext);
    }

    protected Query query(String rsql) {
        Node rootNode = new RSQLParser().parse(rsql);
        Criteria criteria = rootNode.accept(new CriteriaBuildingVisitor(converter));
        return Query.query(criteria);
    }

    protected void check(String rsql, String mongo) {
        assertEquals(mongo, query(rsql).getQueryObject().toString());
    }

    protected void check(String rsql, Consumer<DBObject> consumer) {
        consumer.accept(query(rsql).getQueryObject());
    }

    private class Conversions extends DefaultConversionService {

        private class StringToCalendarConverter implements Converter<String, Calendar> {
            @Override
            public Calendar convert(String source) {
                String format = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault()) ;
                SimpleDateFormat dateformat = new SimpleDateFormat(format, Locale.getDefault());
                try {
                    calendar.setTime(dateformat.parse(source));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return calendar;
            }
        }

        public Conversions() {
            addDefaultConverters(this);
            this.addConverter(new StringToCalendarConverter());
        }
    }

    @SpringBootApplication
    public static class TestApplication {

        public static void main(String[] args) {
            SpringApplication.run(TestApplication.class, args);
        }

    }

}
