// Inspired by C grammar by Terence Parr
// http://www.antlr.org/grammar/1153358328744/C.g

grammar SRMGrammar;

options {
    backtrack = true;
    memoize = true;
    k = 2;
}

@header {
    package com.th3l4b.srm.parser.grammar;

    import java.util.HashMap;
    import java.util.ArrayList;
    
    import com.th3l4b.srm.base.DefaultField;
    import com.th3l4b.srm.base.original.DefaultEntity;
    import com.th3l4b.srm.base.original.DefaultRelationship;
    import com.th3l4b.srm.base.original.DefaultModel;
	import com.th3l4b.srm.base.original.IEntity;
    import com.th3l4b.srm.base.original.IModel;
	import com.th3l4b.srm.base.original.IRelationship;
	import com.th3l4b.srm.base.original.RelationshipType;
	import com.th3l4b.srm.parser.ParserUtils;
}

// http://www.jguru.com/faq/view.jsp?EID=16185
@lexer::header {
    package com.th3l4b.srm.parser.grammar;
}

@members {

	IModel _model = new DefaultModel();

	public IModel getModel () {
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
	model
    (entity | relationship)+
;

model:
	'model' n = string { ParserUtils.setName(n, getModel()); }
	(p = properties { ParserUtils.addProperties(p, getModel()); })?
	';'
;

properties returns [ HashMap<String, String> r = new HashMap(); ]:
	'properties' 
	p = propertiesList { r = p; } 
;

propertiesList returns [ HashMap<String, String> r = new HashMap(); ]:
	'{'
	(key = string '=' value = string ';' { r.put(key, value); })*
	'}'
;


entity returns [ DefaultEntity r = new DefaultEntity(); ]:
    'entity' n = string { ParserUtils.setName(n, r); } '{'
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
    n = string { ParserUtils.setName(n, r); }
    t = string { ParserUtils.setType(t, r); }
    (p = properties { ParserUtils.addProperties(p, r); })?
    ';'
;

relationship returns [ DefaultRelationship r = new DefaultRelationship(); ]:
    'relationship'
    from = string { ParserUtils.setFrom(from, r); }
    to = string { ParserUtils.setTo(to, r); }
    (
	    ( 'many-to-one' { ParserUtils.setType(RelationshipType.manyToOne, r); } ) |
		(
			'many-to-many' { ParserUtils.setType(RelationshipType.manyToMany, r); }
			( based = string { ParserUtils.setEntity(based, r); } )?
		)
	)
    (
    	'direct'
    	direct = string { ParserUtils.setDirectName(direct, r); }
    	(directProperties = propertiesList { ParserUtils.addDirectProperties(directProperties, r); })?
	)?
    (
    	'reverse'
    	reverse = string { ParserUtils.setReverseName(reverse, r); }
    	(reverseProperties = propertiesList { ParserUtils.addReverseProperties(reverseProperties, r); })?
	)?
    (p = properties { ParserUtils.addProperties(p, r); })?
    ';'
    {
    	ParserUtils.applyRelationshipName(r);
    }
;

string returns [ String r = null; ]:
	s = STRING_LITERAL { r = s.getText().substring(1, s.getText().length() - 1); }
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

