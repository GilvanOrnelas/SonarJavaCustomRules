package com.goff.rule.domain.clause_else;

import org.junit.Before;
import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

import com.goff.rule.domain.clause_else.ElseClauseCheck;

public class ElseClauseCheckTest {

    private ElseClauseCheck check;

    @Before
    public void load() {
        check = new ElseClauseCheck();
    }

    @Test
    public void givenIfBlockWithReturnInAllIfsAndElseClause_shallHasOneIssue() {
        JavaCheckVerifier.verify("src/test/filesSamplesToEvaluate/elseClause/ElseWithReturn.java", check);
    }

    @Test
    public void givenIfClauseWithoutElse_shallHasNoIssue() {
        JavaCheckVerifier.verifyNoIssue("src/test/filesSamplesToEvaluate/elseClause/IfWithoutElse.java", check);
    }

}
