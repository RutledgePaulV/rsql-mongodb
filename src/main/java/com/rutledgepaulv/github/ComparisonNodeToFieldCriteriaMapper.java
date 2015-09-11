package com.rutledgepaulv.github;

import cz.jirutka.rsql.parser.ast.ComparisonNode;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.util.List;
import java.util.stream.Collectors;

public class ComparisonNodeToFieldCriteriaMapper {

    private ConversionService conversionService;
    private MongoMappingContext mongoMappingContext;

    public ComparisonNodeToFieldCriteriaMapper(ConversionService conversionService, MongoMappingContext mappingContext) {
        this.mongoMappingContext = mappingContext;
        this.conversionService = conversionService;
    }

    public FieldCriteria convertComparisonNode(ComparisonNode node, Class<?> entity) {
        List<Object> args = mapArgumentsToAppropriateTypesBasedOnModelFields(node, entity);
        return new FieldCriteria(node.getSelector(), node.getOperator(), args);
    }

    private List<Object> mapArgumentsToAppropriateTypesBasedOnModelFields(ComparisonNode node, Class<?> entity) {
        String pathToField = node.getSelector();
        Class<?> targetFieldType = mongoMappingContext.getPersistentPropertyPath(pathToField, entity)
                                                      .getLeafProperty().getType();

        return node.getArguments().stream()
                   .map(arg -> conversionService.convert(arg, targetFieldType))
                   .map(convertedArg -> (Object) convertedArg)
                   .collect(Collectors.toList());
    }

}

