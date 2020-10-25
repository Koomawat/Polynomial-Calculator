package poly;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class implements evaluate, add and multiply for polynomials.
 * 
 * @author runb-cs112
 *
 */
public class Polynomial {
	
	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage format
	 * of the polynomial is:
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * with the guarantee that degrees will be in descending order. For example:
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * which represents the polynomial:
	 * <pre>
	 *      4*x^5 - 2*x^3 + 2*x + 3 
	 * </pre>
	 * 
	 * @param sc Scanner from which a polynomial is to be read
	 * @throws IOException If there is any input error in reading the polynomial
	 * @return The polynomial linked list (front node) constructed from coefficients and
	 *         degrees read from scanner
	 */
	public static Node read(Scanner sc) 
	throws IOException {
		Node poly = null;
		while (sc.hasNextLine()) {
			Scanner scLine = new Scanner(sc.nextLine());
			poly = new Node(scLine.nextFloat(), scLine.nextInt(), poly);
			scLine.close();
		}
		return poly;
	}
	
	/**
	 * Returns the sum of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list
	 * @return A new polynomial which is the sum of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node add(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		
		Node zero = new Node(0,0,null);
		Node first = new Node(0,0,null);
		Node calc = first;
		
		// Null check
		while(poly1 != null || poly2 != null) {
									
			// Checks
			if(poly1 == null) {
				//for debug check System.out.println("1");
				calc.next = new Node(poly2.term.coeff, poly2.term.degree, null);
				poly2 = poly2.next;
			}
			else if(poly2 == null) {
				//for debug check System.out.println("2");
				calc.next = new Node(poly1.term.coeff, poly1.term.degree, null);
				poly1 = poly1.next;
			}		
			else if(poly1.term.degree < poly2.term.degree) {
				//for debug check System.out.println("3");
				calc.next = new Node(poly1.term.coeff, poly1.term.degree, null);
				poly1 = poly1.next;
			}
			else if(poly1.term.degree > poly2.term.degree) {
				//for debug check System.out.println("4");
				calc.next = new Node(poly2.term.coeff, poly2.term.degree, null);
				poly2 = poly2.next;
			}
			else {
				//for debug check System.out.println("5");
				calc.next = new Node(poly1.term.coeff+poly2.term.coeff, poly1.term.degree, null);				
					poly1 = poly1.next;
					poly2 = poly2.next;				
			}	
			//for debug check System.out.println(last.term.coeff+","+last.term.degree);
			calc = calc.next;
		}	
		//for debug check System.out.println(last.term.coeff+","+last.term.degree);
		first = first.next;
		
		// Checking for zero polynomial
		Node ptr = first;
		while(ptr != null) {
			if(ptr.term.coeff != 0) {
				return first;
			}
			ptr = ptr.next;
		}
		return zero;
	}
	
	/**
	 * Returns the product of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list)
	 * @return A new polynomial which is the product of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node multiply(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		
		if (poly1 == null || poly2 == null)
			return null;
		
		// Setting new nodes
		Node p1 = poly1;
		Node p2 = poly2;
		
		// New variables
		float newCoeff = 0;
		int newDeg = 0;
		int maxDeg = 0;
		
		// Calculated nodes
		Node prod = null;
		
		while(p1 != null) {
			while (p2 != null) {		
				newCoeff = p1.term.coeff * p2.term.coeff;
				newDeg = p1.term.degree + p2.term.degree;
				prod = new Node(newCoeff, newDeg, prod);				
				if(newDeg > maxDeg) {
					maxDeg = newDeg;
				}				
				p2 = p2.next;
			}
			p1 = p1.next;
			p2 = poly2;
		}
		// Combining like terms
		Node combine = null;
		for(int i=maxDeg; i >= 0; i--) {
			Node hold = prod;
			float newSum = 0;
			while (hold != null) {
				if(hold.term.degree == i) {
					newSum += hold.term.coeff;
				}
				hold = hold.next;
			}
			if(newSum != 0) {
				combine = new Node(newSum, i, combine);
			}
		}
		return combine;
	}
		
		
	/**
	 * Evaluates a polynomial at a given value.
	 * 
	 * @param poly Polynomial (front of linked list) to be evaluated
	 * @param x Value at which evaluation is to be done
	 * @return Value of polynomial p at x
	 */
	public static float evaluate(Node poly, float x) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
			
		Node ptr = poly;
		float val = (float) 0.0;
		
		while(ptr != null) {
			val += (float) ((ptr.term.coeff) * Math.pow(x, ptr.term.degree)); 
			System.out.println(val);
			ptr = ptr.next;
		}
		
		return val;
	}
	
	/**
	 * Returns string representation of a polynomial
	 * 
	 * @param poly Polynomial (front of linked list)
	 * @return String representation, in descending order of degrees
	 */
	public static String toString(Node poly) {
		if (poly == null) {
			return "0";
		} 
		
		String retval = poly.term.toString();
		for (Node current = poly.next ; current != null ;
		current = current.next) {
			retval = current.term.toString() + " + " + retval;
		}
		return retval;
	}	
}