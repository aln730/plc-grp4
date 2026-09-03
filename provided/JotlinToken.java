package provided;

public class JotlinToken {
    private String token;
    private TokenType tokenType;
    private String fileName;
    private int lineNumber;

    public JotlinToken(String token, TokenType tokenType,
                       String fileName, int lineNumber) {
        this.token = token;
        this.tokenType = tokenType;
        this.fileName = fileName;
        this.lineNumber = lineNumber;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String toString(){
        return token + ":"  + tokenType + ":" + fileName + ":" + lineNumber;
    }
}
