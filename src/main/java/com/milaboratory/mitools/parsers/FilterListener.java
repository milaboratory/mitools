// Generated from com/milaboratory/mitools/parsers/Filter.g4 by ANTLR 4.3
package com.milaboratory.mitools.parsers;

import com.milaboratory.mitools.processing.SequenceProcessor;
import static com.milaboratory.mitools.processing.ProcessingBlocks.*;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link FilterParser}.
 */
public interface FilterListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link FilterParser#sequence}.
	 * @param ctx the parse tree
	 */
	void enterSequence(@NotNull FilterParser.SequenceContext ctx);
	/**
	 * Exit a parse tree produced by {@link FilterParser#sequence}.
	 * @param ctx the parse tree
	 */
	void exitSequence(@NotNull FilterParser.SequenceContext ctx);

	/**
	 * Enter a parse tree produced by {@link FilterParser#description}.
	 * @param ctx the parse tree
	 */
	void enterDescription(@NotNull FilterParser.DescriptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FilterParser#description}.
	 * @param ctx the parse tree
	 */
	void exitDescription(@NotNull FilterParser.DescriptionContext ctx);

	/**
	 * Enter a parse tree produced by {@link FilterParser#quality}.
	 * @param ctx the parse tree
	 */
	void enterQuality(@NotNull FilterParser.QualityContext ctx);
	/**
	 * Exit a parse tree produced by {@link FilterParser#quality}.
	 * @param ctx the parse tree
	 */
	void exitQuality(@NotNull FilterParser.QualityContext ctx);

	/**
	 * Enter a parse tree produced by {@link FilterParser#actionSet}.
	 * @param ctx the parse tree
	 */
	void enterActionSet(@NotNull FilterParser.ActionSetContext ctx);
	/**
	 * Exit a parse tree produced by {@link FilterParser#actionSet}.
	 * @param ctx the parse tree
	 */
	void exitActionSet(@NotNull FilterParser.ActionSetContext ctx);

	/**
	 * Enter a parse tree produced by {@link FilterParser#sequenceProcessor}.
	 * @param ctx the parse tree
	 */
	void enterSequenceProcessor(@NotNull FilterParser.SequenceProcessorContext ctx);
	/**
	 * Exit a parse tree produced by {@link FilterParser#sequenceProcessor}.
	 * @param ctx the parse tree
	 */
	void exitSequenceProcessor(@NotNull FilterParser.SequenceProcessorContext ctx);

	/**
	 * Enter a parse tree produced by {@link FilterParser#number}.
	 * @param ctx the parse tree
	 */
	void enterNumber(@NotNull FilterParser.NumberContext ctx);
	/**
	 * Exit a parse tree produced by {@link FilterParser#number}.
	 * @param ctx the parse tree
	 */
	void exitNumber(@NotNull FilterParser.NumberContext ctx);

	/**
	 * Enter a parse tree produced by {@link FilterParser#logicalExpression}.
	 * @param ctx the parse tree
	 */
	void enterLogicalExpression(@NotNull FilterParser.LogicalExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FilterParser#logicalExpression}.
	 * @param ctx the parse tree
	 */
	void exitLogicalExpression(@NotNull FilterParser.LogicalExpressionContext ctx);
}