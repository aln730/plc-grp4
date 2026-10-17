package created;
import java.util.ArrayList;
import provided.*;

public class FParamsNode implements JotlinTree{
    private ArrayList<FParamNode> params;

    public FParamsNode(ArrayList<FParamNode> params) {
        this.params = params;
    }

    public static FParamsNode parse(ArrayList<JotlinToken> tokens) {
        ArrayList<FParamNode> nodes = new ArrayList<>();
        if(tokens.size() < 1) {
            return new FParamsNode(nodes);
        }
        if(tokens.getFirst().getTokenType() != TokenType.R_Arrow) {
            return new FParamsNode(nodes);
        }
        tokens.removeFirst();
        FParamNode node = FParamNode.parse(tokens);
        nodes.add(node);
        while(!tokens.isEmpty() && tokens.getFirst().getTokenType() == TokenType.Comma) {
            tokens.removeFirst();
            try {
                node = FParamNode.parse(tokens);
                nodes.add(node);
            }
            catch(IllegalArgumentException e) {
                throw new IllegalArgumentException("Expected FParam after comma");
            }
        }
        return new FParamsNode(nodes);
    }

    @Override
    public boolean validate() throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validate'");
    }

    @Override
    public String convertToJava() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convertToJava'");
    }

    @Override
    public String convertToJotlin() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convertToJotlin'");
    }
    
}
