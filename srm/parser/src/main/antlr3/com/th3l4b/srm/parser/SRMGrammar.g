// Inspired by C grammar by Terence Parr
// http://www.antlr.org/grammar/1153358328744/C.g

grammar SRMGrammar;

options {
    backtrack = true;
    memoize = true;
    k = 2;
}

@header {
    package com.th3l4b.srm.parser;

    import java.util.HashMap;
    import java.util.ArrayList;
}

// http://www.jguru.com/faq/view.jsp?EID=16185
@lexer::header {
    package com.th3l4b.srm.parser;
}

@members {

    public static void main(String[] args) throws Exception {
        SRMGrammarLexer lex = new SRMGrammarLexer(new ANTLRInputStream(System.in));
        CommonTokenStream tokens = new CommonTokenStream(lex);

        SRMGrammarParser parser = new SRMGrammarParser(tokens);

        try {
            parser.document();
        } catch (RecognitionException e)  {
            e.printStackTrace();
        }
    }
}

document :
    entity+
;

entity returns [ HashMap<String, Object> r = new HashMap<String, Object>(); ]:
    'entity' id = IDENTIFIER '{'
        (f = field { r.put("_field_" + f.get("name"), f); })*
        e = enumeration { r.put("_enum", e); }
        ('query' code = CODE_LITERAL ';' {
            r.put("_query", code.getText());
        })?
        ('validation' code = CODE_LITERAL ';' {
            r.put("_code", code.getText());
        })?
    '}'
    {
        r.put("name", id.getText());
    }
;

field returns [ HashMap<String, String> r = new HashMap(); ]:
    'field' id = IDENTIFIER { r.put("name", id.getText()); }
        (s = STRING_LITERAL { r.put("text", s.getText()); })?
    ';'
;

enumeration returns [ ArrayList<String> r = null; ]:
    'enumeration' '{'
        r2 = identifier_list { r = r2; }
    '}'
;

identifier_list returns [ ArrayList<String> r = new ArrayList<String>(); ]:
    (() | (i = IDENTIFIER { r.add(i.getText()); } (',' i=IDENTIFIER { r.add(i.getText()); })*))
;

IDENTIFIER
    :   LETTER (LETTER|'0'..'9')*
    ;
    
fragment
LETTER
    :   'A'..'Z'
    |   'a'..'z'
    |   '_'
    ;

CHARACTER_LITERAL
    :   '\'' ( ~('\''|'\\') ) '\''
    ;

STRING_LITERAL
    :  '"' ( ~('\\'|'"') )* '"'
    ;
    
CODE_LITERAL
    :  '@' ( ~('@') )* '@'
    ;

WS  :  (' '|'\r'|'\t'|'\u000C'|'\n') {$channel = HIDDEN;}
    ;

COMMENT
    :   '/*' ( options {greedy = false;} : . )* '*/' {$channel = HIDDEN;}
    ;

LINE_COMMENT
    : '//' ~('\n'|'\r')* '\r'? '\n' {$channel = HIDDEN;}
    ;

