import cz.jirutka.rsql.parser.ast.AndNode;
import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.OrNode;
import cz.jirutka.rsql.parser.ast.RSQLVisitor;
import org.springframework.data.mongodb.core.query.Criteria;

public class CriteriaBuildingVisitor implements RSQLVisitor<Criteria, Void> {

    private TreeToCriteriaConverter adapter = new TreeToCriteriaConverter();

    public Criteria visit(AndNode andNode, Void aVoid) {
        return adapter.createCriteria(andNode);
    }

    public Criteria visit(OrNode orNode, Void aVoid) {
        return adapter.createCriteria(orNode);
    }

    public Criteria visit(ComparisonNode comparisonNode, Void aVoid) {
        return adapter.createCriteria(comparisonNode);
    }

}
