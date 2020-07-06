# Access Optimized Priority Search Tree
## Abstract
The priority search tree is a data structure introduced by Edward McCreight in 1985 with the primary purpose of storing a set of n points in ℝ² in a way that allows insertion, 
deletion, and various search algorithms to have an average and worst case time complexity of O(log n). This ideal complexity is achieved by combining the completeness of heaps 
with the efficient search algorithm of binary search trees. Because of these properties, and as we will show in this paper, priority search trees can be slightly altered to 
improve upon splay trees. The structural adjustments that result in this improvement include storing elements of the data set to be searched in the x-coordinate and the number 
of times the element has been accessed in the y-coordinate of a maximum priority search tree node. Like splay trees, this new data structure, which we will call an access 
optimized priority search tree, utilizes the principle of locality that is so often seen in computing. However, whereas splay trees have a worst case complexity of  O(n) for 
searching, access optimized priority search trees manage to reduce this worst case to O(log n) while preserving splay trees’ average time complexity.

