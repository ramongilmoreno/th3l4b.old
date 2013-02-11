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
    
    import com.th3l4b.srm.base.DefaultField;
    import com.th3l4b.srm.base.original.DefaultEntity;
    import com.th3l4b.srm.base.original.DefaultRelationship;
    import com.th3l4b.srm.base.original.IModel;
    import com.th3l4b.srm.base.original.DefaultModel;
	import com.th3l4b.srm.base.original.IEntity;
	import com.th3l4b.srm.base.original.IRelationship;
	import com.th3l4b.srm.parser.ParserUtils;
}

// http://www.jguru.com/faq/view.jsp?EID=16185
@lexer::header {
    package com.th3l4b.srm.parser;
}

@members {

	IModel _model = new DefaultModel();

	protected IModel getModel () {
		return _model;
	}

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
	name
    (entity | relationship)+
;

name:
	'name' n = STRING_LITERAL { ParserUtils.setName(n.getText(), getModel()); }
	(p = properties { ParserUtils.addProperties(p, getModel()); })?
;

properties returns [ HashMap<String, String> r = new HashMap(); ]:
	'{'
	(key = STRING_LITERAL '=' value = STRING_LITERAL ';' { r.put(key.getText(), value.getText()); })*
	'}'
;

entity returns [ DefaultEntity r = new DefaultEntity(); ]:
    'entity' n = STRING_LITERAL { ParserUtils.setName(n.getText(), r); } '{'
        (f = field { ParserUtils.addField(f, r); })*
    '}'
    (p = properties { ParserUtils.addProperties(p, r); })?
    ';'
    {
    	ParserUtils.addEntity(r, getModel());
    }
;

field returns [ DefaultField r = new DefaultField(); ]:
    'field'
    n = STRING_LITERAL { ParserUtils.setName(n.getText(), r); }
    t = STRING_LITERAL { ParserUtils.setType(t.getText(), r); }
    (p = properties { ParserUtils.addProperties(p, r); })?
    ';'
;

relationship returns [ DefaultRelationship r = new DefaultRelationship(); ]:
    'relationship'
    from = STRING_LITERAL { ParserUtils.setFrom(from.getText(), r); }
    to = STRING_LITERAL { ParserUtils.setTo(to.getText(), r); }
    ('direct' direct = STRING_LITERAL { ParserUtils.setDirectName(direct.getText(), r); })+
    ('reverse' reverse = STRING_LITERAL { ParserUtils.setReverseName(reverse.getText(), r); })+
    (p = properties { ParserUtils.addProperties(p, r); })?
    ';'
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
    : ('//'|'#') ~('\n'|'\r')* '\r'? '\n' {$channel = HIDDEN;}
    ;

