# Princeton University - Algorithms, Part I

The first part of this course covers elementary data structures, sorting, and searching algorithms.

Here you are going to find my Java solutions for the [Coursera Princeton University - Algorithms, Part I course](https://www.coursera.org/learn/algorithms-part1/)

The code requires the algs4.jar lib to run that can be downloaded from [here](http://algs4.cs.princeton.edu/code/)

## Week 1

### Union-Find

As part of this section we studied and analize the dynamic connectivity problem.

Assigmnent: [Percolation](http://coursera.cs.princeton.edu/algs4/assignments/percolation.html)

Write a program to estimate the value of the percolation threshold via Monte Carlo simulation. Used WeightedQuickUnion data structure to efficiently model system percolation.

## Week 2

### Deques and Randomized Queues

On this section we consider two fundamental data types for storing collections of objects: the stack and the queue.

Assigmnent: [Deques and Randomized Queues](http://coursera.cs.princeton.edu/algs4/assignments/queues.html)

Write a generic data type for a deque and a randomized queue. The goal of this assignment is to implement elementary data structures using arrays and linked lists.

## Week 3

### Pattern Recognition

Mergesort and Quicksort algorithms.

Assigmnent: [Pattern Recognition](http://coursera.cs.princeton.edu/algs4/assignments/collinear.html)

Write a program to recognize line patterns in a given set of points.

## Week 4

### Priority Queues

Assigmnent: [8 Puzzle](http://coursera.cs.princeton.edu/algs4/assignments/8puzzle.html)

Write a program to solve the 8-puzzle problem (and its natural generalizations) using the A* search algorithm.

## Week 5

### Balanced Search Trees

Assigmnent: [Kd-Trees](http://coursera.cs.princeton.edu/algs4/assignments/kdtree.html)

Write a mutable data type PointSET.java that represents a set of points in the unit square. Implement the following API by using a red-black BST.
Write a mutable data type KdTree.java that uses a 2d-tree to implement the same API (but replace PointSET with KdTree). A 2d-tree is a generalization of a BST to two-dimensional keys. The idea is to build a BST with points in the nodes, using the x- and y-coordinates of the points as keys in strictly alternating sequence.