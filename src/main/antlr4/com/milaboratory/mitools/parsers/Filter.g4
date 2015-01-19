grammar Filter;

//@header{
//import com.milaboratory.core.io.sequence.SequenceRead;
//import com.milaboratory.core.sequence.SequenceQuality;
//import java.util.function.Function;
//import java.util.function.Predicate;
//}

@header{
import com.milaboratory.mitools.processing.SequenceProcessor;
import static com.milaboratory.mitools.processing.ProcessingBlocks.*;
}

actionSet returns [ List<SequenceProcessor> processors ]
     @init
     {
     	$processors = new ArrayList<>();
     }
     :
     is = sequenceProcessor { $processors.add($ctx.is.val); } ( ';' ss = sequenceProcessor { $processors.add($ctx.ss.val); } ) * ';' ? EOF
     ;

description returns [ RDescription val ]:
         'D' readIndex = Int { $val = d(i($readIndex.text) - 1); }
       | 'D' { $val = d(0); }
       ;

quality returns [ RQuality val ]:
         'Q' readIndex = Int { $val = q(i($readIndex.text) - 1); }
       | 'Q' { $val = q(0); }
       | q1 = quality '+' q2 = quality { $val = concat($ctx.q1.val, $ctx.q2.val); }
       ;

sequence returns [ RSequence val ]:
          'S' readIndex = Int { $val = s(i($readIndex.text) - 1); }
        | 'S' { $val = s(0); }
        | s1 = sequence '+' s2 = sequence { $val = concat($ctx.s1.val, $ctx.s2.val);; }
        ;

number returns [ Num val ]:
          num=Int { $val = num(l($num.text)); }
        | 'min(' arg = quality ')' { $val = min($ctx.arg.val); }
        | 'mean(' arg = quality ')' { $val = mean($ctx.arg.val); }
        ;

logicalExpression returns [ LogicalExpression val ]:
                   ln = number '>' rn = number { $val = lt($ctx.rn.val, $ctx.ln.val); }
                 | ln = number '>=' rn = number { $val = le($ctx.rn.val, $ctx.ln.val); }
                 | ln = number '<' rn = number { $val = lt($ctx.ln.val, $ctx.rn.val); }
                 | ln = number '<=' rn = number { $val = le($ctx.ln.val, $ctx.rn.val); }
                 | ln = number '==' rn = number { $val = eq($ctx.ln.val, $ctx.rn.val); }
                 | ll = logicalExpression '&&' rl = logicalExpression { $val = and($ctx.ll.val, $ctx.rl.val); }
                 | ll = logicalExpression '||' rl = logicalExpression { $val = or($ctx.ll.val, $ctx.rl.val); }
                 ;

sequenceProcessor returns [ SequenceProcessor val ]:
                   'filter(' f = logicalExpression ')' { $val = new Filter($ctx.f.val); }
                 | 'skip(' n=Int ')' { $val = new Skip(l($n.text)); }
                 | <assoc=right> sp = sequenceProcessor '.' mn = MethodName '(' i = Int ')' { $val = intMethod($ctx.sp.val, $mn.text, l($i.text)); }
                 ;

catch[RecognitionException e] { throw e; }

MethodName: ('A'..'Z' | 'a'..'z') + ;

Int : [0-9]+ ;

Ws : [ \r\t\n]+ -> skip ;


//intVal: Int
//      | minimalQaulity
//      ;