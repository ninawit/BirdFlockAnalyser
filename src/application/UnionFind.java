package application;

import java.util.HashSet;

public class UnionFind {
	public static HashSet<Integer> birds = new HashSet<>();
	
	/* Quick union of disjoint sets containing elements 'p' and 'q' */
	public static void union(int[] a, int p, int q) {
		a[find(a,q)]=find(a,p); // The root of 'q' is made reference p
	}
	/* Recursive version of find with path compression */
	public static int find(int[] a, int id) { 
		if(a[id] < 0) return a[id];
		if(a[id] == id) return id;
		else return   find(a,a[id]);
	}
}
 