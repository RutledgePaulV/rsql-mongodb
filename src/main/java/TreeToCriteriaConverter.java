import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.LogicalNode;
import cz.jirutka.rsql.parser.ast.Node;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.List;
import java.util.stream.Collectors;

public class TreeToCriteriaConverter {

    public Criteria createCriteria(Node node) {
        if (node instanceof LogicalNode) {
            return createCriteria((LogicalNode) node);
        }
        if (node instanceof ComparisonNode) {
            return createCriteria((ComparisonNode) node);
        }
        return null;
    }


    private Criteria createCriteria(LogicalNode logicalNode) {

        List<Criteria> specs = logicalNode.getChildren().stream()
                .map(this::createCriteria).filter(node -> node != null)
                .collect(Collectors.toList());

        Criteria parent = new Criteria();

        switch (logicalNode.getOperator()) {
            case AND:
                return parent.andOperator(specs.toArray(new Criteria[specs.size()]));
            case OR:
                return parent.orOperator(specs.toArray(new Criteria[specs.size()]));
        }

        return parent;
    }

    private Criteria createCriteria(ComparisonNode comparisonNode) {
        return new RSQLNodeAsCriteria(comparisonNode.getSelector(), comparisonNode.getOperator(),
                comparisonNode.getArguments()).toCriteria();
    }

}
