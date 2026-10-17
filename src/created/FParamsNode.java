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
        if (params == null) {
            return false;
        }
        for (FParamNode param : params) {
            if (param == null || !param.validate()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String convertToJava() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < params.size(); i++) {
            if (i > 0) {
                result.append(", ");
            }
            result.append(params.get(i).convertToJava());
        }
        return result.toString();
    }

    @Override
    public String convertToJotlin() {
        StringBuilder result = new StringBuilder();
        if (params.isEmpty()) {
            return "";
        }
        StringBuilder result = new StringBuilder(" -> ");
        for (int i = 0; i < params.size(); i++) {
            if (i > 0) {
                result.append(", ");
            }
            result.append(params.get(i).convertToJotlin());
        }
        return result.toString();    
    }
    
}
