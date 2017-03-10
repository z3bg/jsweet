/* 
 * JSweet transpiler - http://www.jsweet.org
 * Copyright (C) 2015 CINCHEO SAS <renaud.pawlak@cincheo.fr>
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package org.jsweet.transpiler.model;

import org.jsweet.transpiler.model.support.CaseElementSupport;
import org.jsweet.transpiler.model.support.ExtendedElementSupport;
import org.jsweet.transpiler.model.support.SelectElementSupport;
import org.jsweet.transpiler.model.support.IdentifierElementSupport;
import org.jsweet.transpiler.model.support.LiteralElementSupport;
import org.jsweet.transpiler.model.support.MethodInvocationElementSupport;
import org.jsweet.transpiler.model.support.NewClassElementSupport;

import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.JCCase;
import com.sun.tools.javac.tree.JCTree.JCFieldAccess;
import com.sun.tools.javac.tree.JCTree.JCIdent;
import com.sun.tools.javac.tree.JCTree.JCLiteral;
import com.sun.tools.javac.tree.JCTree.JCMethodInvocation;
import com.sun.tools.javac.tree.JCTree.JCNewClass;

/**
 * A factory to create extended elements. It defines an overloaded create method
 * that wraps the given javac tree node with the appropriate element.
 * 
 * @author Renaud Pawlak
 */
public class ExtendedElementFactory {

	public final static ExtendedElementFactory INSTANCE = new ExtendedElementFactory();

	@SuppressWarnings("unchecked")
	public static <T extends JCTree> T toTree(ExtendedElement element) {
		return (T) ((ExtendedElementSupport) element).getTree();
	}

	public ExtendedElement create(JCTree tree) {
		if (tree == null) {
			return null;
		}
		switch (tree.getTag()) {
		case APPLY:
			return new MethodInvocationElementSupport((JCMethodInvocation) tree);
		case SELECT:
			return new SelectElementSupport((JCFieldAccess) tree);
		case NEWCLASS:
			return new NewClassElementSupport((JCNewClass) tree);
		case IDENT:
			return new IdentifierElementSupport((JCIdent) tree);
		case LITERAL:
			return new LiteralElementSupport((JCLiteral) tree);
		case CASE:
			return new CaseElementSupport((JCCase) tree);
		default:
			return new ExtendedElementSupport(tree);
		}
	}

}
