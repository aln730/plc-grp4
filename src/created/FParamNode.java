package created;
import java.util.ArrayList;
import provided.*;


public class FParamNode implements JotlinTree{
    private IdNode id;
    private KeywordNode keyword;
    
    public FParamNode(IdNode id, KeywordNode keyword) {
        this.id = id;
        this.keyword = keyword;
    }

    public static FParamNode parse(ArrayList<JotlinToken> tokens) {
        if(tokens.size() < 3) {
            throw new IllegalArgumentException("Not enough tokens for fparam");
        }
        if(tokens.get(0).getTokenType() != TokenType.Id) {
            throw new IllegalArgumentException("Fparam must start with id token");
        }
        IdNode id = IdNode.parse(tokens);
        if(tokens.get(0).getTokenType() != TokenType.Colon) {
            throw new IllegalArgumentException("Fparam must have a ':' as the second character");
        }
        tokens.removeFirst();
        if(tokens.get(0).getTokenType() != TokenType.Keyword) {
            throw new IllegalArgumentException("Fparam must end with keyword token");
        }
        KeywordNode keyword = KeywordNode.parse(tokens);

        return new FParamNode(id,keyword);
    }

    @Override
    public boolean validate() throws Exception {
        return id.validate() && keyword.validate();
    }

    @Override
    public String convertToJava() {
        String type = keyword.convertToJava();
        switch (type) {
            case "Integer":
                type = "int";
                break;
            case "Double":
                type = "double";
                break;
            case "Boolean":
                type = "boolean";
                break;
            case "String":
                type = "String";
                break;
            default:
                break;
        }
        return type + " " + (id != null ?  id.convertToJava():"");
    }

    @Override
    public String convertToJotlin() {
        return (id != null ?  id.convertToJotlin():"") + ":" + (keyword != null ?  keyword.convertToJotlin():"");
    }
    
}
